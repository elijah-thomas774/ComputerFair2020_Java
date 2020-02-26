//
//  EdgeDetection.swift
//  PencilSketch
//
//  Created by Eric Johns on 9/1/19.
//  Copyright © 2019 Eric Johns. All rights reserved.
//

import Foundation
import SwiftImage
import UIKit

class EdgeDetection {
    static func createBorder(kernel: Int, image: inout [[Int]]) -> [[Int]] {
        var borderReflected: [[Int]] = Array(repeating: Array(repeating: 0, count: image[0].count + (2 * kernel)), count: image.count + (2 * kernel))
        var flipVertical: [[Int]] = ArrayUtility.flipVertical(array: image)
        
        ArrayUtility.copyRange(image1: &image, copyTo: &borderReflected, startX: 0, endX: image[0].count, startY: 0, endY: image.count, pasteX: kernel, pasteY: kernel) // copying image
        ArrayUtility.copyRange(image1: &flipVertical, copyTo: &borderReflected, startX: 0, endX: image[0].count, startY: image.count - kernel, endY: image.count, pasteX: kernel, pasteY: 0)    // copy top
        ArrayUtility.copyRange(image1: &flipVertical, copyTo: &borderReflected, startX: 0, endX: image[0].count, startY: 0, endY: kernel, pasteX: kernel, pasteY: borderReflected.count - kernel)   // copy bottom
        
        var flipHorizontal: [[Int]] = ArrayUtility.flipHorizontal(array: borderReflected)
        
        ArrayUtility.copyRange(image1: &flipHorizontal, copyTo: &borderReflected, startX: borderReflected[0].count - (kernel * 2), endX: borderReflected[0].count - kernel, startY: 0, endY: borderReflected.count, pasteX: 0, pasteY: 0)   // copy left
        ArrayUtility.copyRange(image1: &flipHorizontal, copyTo: &borderReflected, startX: kernel, endX: kernel * 2, startY: 0, endY: borderReflected.count, pasteX: borderReflected[0].count - kernel, pasteY: 0)   // copy right
        
        print("Created border reflection")
        
        return(borderReflected)
    }
    
    static func createKernel(kernel: Int, sigma: Double) -> [[Double]] {
        var filter: [[Double]] = Array(repeating: Array(repeating: 0, count: (kernel * 2) + 1), count: (kernel * 2) + 1)
        let coefficient: Double = 1 / (2 * Double.pi * pow(sigma, 2))
        var sum: Double = 0
        
        for u in -kernel..<(kernel + 1) {
            for v in -kernel..<(kernel + 1) {
                filter[u + kernel][v + kernel] = coefficient * exp(-(pow(Double(u), 2) + pow(Double(v), 2)) / Double(2 * pow(sigma, 2)))
                sum += filter[u + kernel][v + kernel]
            }
        }
        
        for y in 0..<filter.count {
            for x in 0..<filter[0].count {
                filter[y][x] /= sum
            }
        }
        
        print("Generated a \(kernel * 2 + 1)x\(kernel * 2 + 1) kernel with a sigma of \(sigma)")
        
        return(filter)
    }
    
    static func filter(kernel: [[Double]], image: [[Int]]) -> [[Double]] {
        let kernelSize: Int = kernel.count / 2
        var filteredImage: [[Double]] = Array(repeating: Array(repeating: 0, count: image[0].count - (2 * kernelSize)), count: image.count - (2 * kernelSize))
        
        print("Starting filter...")
        
        for y in kernelSize..<(image.count - kernelSize) {
            for x in (kernelSize..<image[0].count - kernelSize) {
                var counter: Double = 0
                
                for u in -kernelSize..<(kernelSize + 1) {
                    for v in -kernelSize..<(kernelSize + 1) {
                        counter += kernel[kernelSize + u][kernelSize + v] * Double(image[y + u][x + v])
                    }
                }
                
                filteredImage[y - kernelSize][x - kernelSize] = counter
            }
        }
        
        print("Filter complete")
        
        return(filteredImage)
    }
    
    static func calcGradientX(image: [[Double]]) -> [[Double]] {
        var gradX: [[Double]] = Array(repeating: Array(repeating: 0, count: image[0].count - 1), count: image.count - 1)
        
        for y in 0..<(image.count - 1) {
            for x in 0..<(image[0].count - 1) {
                gradX[y][x] = image[y][x + 1] - image[y][x]
            }
        }
        
        print("Gradient X generated")
        
        return(gradX)
    }
    
    static func calcGradientY(image: [[Double]]) -> [[Double]] {
        var gradY: [[Double]] = Array(repeating: Array(repeating: 0, count: image[0].count - 1), count: image.count - 1)
        
        for y in 0..<(image.count - 1) {
            for x in 0..<(image[0].count - 1) {
                gradY[y][x] = image[y + 1][x] - image[y][x]
            }
        }
        
        print("Gradient Y generated")
        
        return(gradY)
    }
    
    static func calcGradMag(gradX: [[Double]], gradY: [[Double]]) -> [[Double]] {
        var gradMag: [[Double]] = Array(repeating: Array(repeating: 0, count: gradX[0].count), count: gradX.count)
        
        for y in 0..<gradMag.count {
            for x in 0..<gradMag[0].count {
                gradMag[y][x] = sqrt(pow(gradX[y][x], 2) + pow(gradY[y][x], 2))
            }
        }
        
        print("Gradient magnitude calculated")
        
        return(gradMag)
    }
    
    static func calcGradAngle(gradX: [[Double]], gradY: [[Double]]) -> [[Double]] {
        var gradAngle: [[Double]] = Array(repeating: Array(repeating: 0, count: gradX[0].count), count: gradX.count)
        
        for y in 0..<gradAngle.count {
            for x in 0..<gradAngle[0].count {
                gradAngle[y][x] = atan2(gradY[y][x], gradX[y][x])
            }
        }
        
        print("Gradient angle calculated")
        
        return(gradAngle)
    }
    
    static func adjustGradAngle(gradAngle: inout [[Double]]) -> [[Int]] {
        var gradAngleAdj: [[Int]] = Array(repeating: Array(repeating: 0, count: gradAngle[0].count), count: gradAngle.count)
        
        for y in 0..<gradAngleAdj.count {
            for x in 0..<gradAngleAdj[0].count {
                if (gradAngle[y][x] < 0) {
                    gradAngle[y][x] += Double.pi
                }
                
                if (gradAngle[y][x] <= ((3 * Double.pi) / 4) + (Double.pi / 8) && gradAngle[y][x] >= ((3 * Double.pi) / 4) - (Double.pi / 8)) {
                    gradAngleAdj[y][x] = 3
                }  else if (gradAngle[y][x] <= (Double.pi / 2) + (Double.pi / 8) && gradAngle[y][x] >= (Double.pi / 2) - (Double.pi / 8)) {
                    gradAngleAdj[y][x] = 2
                } else if (gradAngle[y][x] <= (Double.pi / 4) + (Double.pi / 8) && gradAngle[y][x] >= (Double.pi / 4) - (Double.pi / 8)) {
                    gradAngleAdj[y][x] = 1
                } else if (gradAngle[y][x] <= Double.pi / 8 && gradAngle[y][x] >= (0 - (Double.pi / 8))) {
                    gradAngleAdj[y][x] = 0
                } else {
                    gradAngleAdj[y][x] = 0
                }
            }
        }
        
        print("Adjusted gradient angle")
        
        return(gradAngleAdj)
    }
    
    static func calcNonMaxSupp(gradMag: [[Double]], gradAngleAdj: [[Int]]) -> [[Double]] {
        var calcNonMaxSupp: [[Double]] = Array(repeating: Array(repeating: 0, count: gradMag[0].count), count: gradMag.count)
        
        for y in 1..<(calcNonMaxSupp.count - 1) {
            for x in 1..<(calcNonMaxSupp[0].count - 1) {
                switch (gradAngleAdj[y][x]) {
                    case 0:
                        if (gradMag[y][x] > gradMag[y][x - 1] && gradMag[y][x] > gradMag[y][x + 1]) {
                            calcNonMaxSupp[y][x] = gradMag[y][x]
                        }
                        
                        break
                    case 1:
                        if (gradMag[y][x] > gradMag[y - 1][x - 1] && gradMag[y][x] > gradMag[y + 1][x + 1]) {
                            calcNonMaxSupp[y][x] = gradMag[y][x]
                        }
                        
                        break
                    case 2:
                        if (gradMag[y][x] > gradMag[y - 1][x] && gradMag[y][x] > gradMag[y + 1][x]) {
                            calcNonMaxSupp[y][x] = gradMag[y][x]
                        }
                        
                        break
                    case 3:
                        if (gradMag[y][x] > gradMag[y + 1][x - 1] && gradMag[y][x] > gradMag[y - 1][x + 1]) {
                            calcNonMaxSupp[y][x] = gradMag[y][x]
                        }
                        
                        break
                    default: break
                }
            }
        }
        
        print("Excess pixels suppressed")
        
        return(calcNonMaxSupp)
    }
    
    static func doubleThresh(nonMaxSupp: [[Double]], min: Int, max: Int) -> [[Int]] {
        var doubleThresh: [[Int]] = Array(repeating: Array(repeating: 0, count: nonMaxSupp[0].count), count: nonMaxSupp.count)
        
        for y in 0..<nonMaxSupp.count {
            for x in 0..<nonMaxSupp[0].count {
                if (nonMaxSupp[y][x] > Double(max)) {
                    doubleThresh[y][x] = 255
                } else if (nonMaxSupp[y][x] <= Double(max) && nonMaxSupp[y][x] >= Double(min)) {
                    doubleThresh[y][x] = 127
                }
            }
        }
        
        print("Comparing pixels to thresholds")
        
        return(doubleThresh)
    }
    
    static func calcEdge(doubleThresh: [[Int]]) -> [[Int]] {
        var calcEdge: [[Int]] = Array(repeating: Array(repeating: 0, count: doubleThresh[0].count), count: doubleThresh.count)
        
        for y in 1..<(calcEdge.count - 1) {
            for x in 1..<(calcEdge[0].count - 1) {
                if (doubleThresh[y][x] == 127) {
                    for i in -1..<2 {
                        for j in -1..<2 {
                            if (doubleThresh[y + i][x + j] == 255) {
                                calcEdge[y][x] = 255
                            }
                        }
                    }
                } else if (doubleThresh[y][x] == 255) {
                    calcEdge[y][x] = 255
                }
            }
        }
        
        print("Calculating final edge")
        
        return(removeFinalBorder(calcEdge: calcEdge))
    }
    
    private static func removeFinalBorder(calcEdge: [[Int]]) -> [[Int]] {
        var finalImage: [[Int]] = Array(repeating: Array(repeating: 0, count: calcEdge[0].count - 2), count: calcEdge.count - 2)
        
        for y in 1..<(calcEdge.count - 1) {
            for x in 1..<(calcEdge[0].count - 1) {
                finalImage[y - 1][x - 1] = calcEdge[y][x]
            }
        }
        
        print("Removing final border")
        
        return(finalImage)
    }
}
