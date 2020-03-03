//
//  Sketch.swift
//  PencilSketch
//
//  Created by Eric Johns on 9/5/19.
//  Copyright © 2019 Eric Johns. All rights reserved.
//

import Foundation

class Sketch {
    static func sketch(dThresh: [[Int]], gradAng: [[Double]], lineScale: Double) -> [[Int]] {
        var sketch: [[Int]] = Array(repeating: Array(repeating: 0, count: dThresh[0].count), count: dThresh.count)
        let height: Int = Int(sqrt(Double(sketch.count * sketch[0].count)) * lineScale)
        
        print("Starting sketch...")
        
        for y in 0..<sketch.count {
            for x in 0..<sketch[0].count {
                if (dThresh[y][x] >= 127) {
                    addLine(length: height, array: &sketch, ang: gradAng[y][x] + (Double.pi / 2), r: y, c: x)
                }
                
                if (dThresh[y][x] > 127) {
                    addLine(length: height, array: &sketch, ang: gradAng[y][x] + (Double.pi / 2), r: y, c: x)
                }
            }
        }
        
        print("Sketch complete")
        
        return(sketch)
    }
    
    static func addLine(length: Int, array: inout [[Int]], ang: Double, r: Int, c: Int) {
        var lengthCopy = length
        
        if (lengthCopy % 2 == 0) {
            lengthCopy += 1
        }
        
        var y: Int, x: Int
        let slope: Double = tan(ang)
        
        if (abs(slope) >= 1 - 0.001) {
            for i in (-lengthCopy / 2)..<(lengthCopy / 2 + 1) {
                y = r + i
                x = c + Int(round(Double(i) / slope))
                
                if (x >= 0 && x < array[0].count && y >= 0 && y < array.count && sqrt(pow(Double(y - r), 2) + pow(Double(x - c), 2)) <= Double(lengthCopy / 2)) {
                    
                    array[y][x] += 1
                    
                    if (x - 1 > 0) {
                        array[y][x - 1] += 1
                    }
                }
            }
        } else if (abs(slope) < 1) {
            for i in (-lengthCopy / 2)..<(lengthCopy / 2 + 1) {
                y = r + Int(round(Double(i) * slope))
                x = c + i
                
                if (x >= 0 && x < array[0].count && y >= 0 && y < array.count && sqrt(pow(Double(y - r), 2) + pow(Double(x - c), 2)) <= Double(lengthCopy / 2)) {
                    array[y][x] += 1
                    
                    if (y - 1 > 0) {
                        array[y - 1][x] += 1
                    }
                }
            }
        }
    }
    
    static func sketch2(filtered: [[Double]], lineScale: Double, dThreshMin: Double, dThreshMax: Double) -> [[Int]] 
        //so basically this is detecting edges of the image and drawing lines tangent to the edge.
        var sine: Int, cosine: Int //used for parts of non maximum supresssion in finding corresponding points
        var r: Int, c: Int, u: Int, v: Int //other variables to loop through, r and c are used for a point, u and v are used for another
        var angle: Double //the gradients angle
        var maximum: Double, num1: Double, num2: Double, num3: Double //the values of the points being checked, each num holds a temporary point in the array for checking
        var maxNum: Double = 0 //maximum in the specified angle
        let x: Int = filtered[0].count - 1, y: Int = filtered.count - 1
        var dThresh: [[Double]] = Array(repeating: Array(repeating: 0, count: x), count: y)
        var sketch: [[Int]] = Array(repeating: Array(repeating: 0, count: x), count: y)

        for i in 0..<y {
            for j in 0..<x {
                angle = atan2(filtered[i + 1][j] - filtered[i][j], filtered[i][j + 1] - filtered[i][j])
                angle = 0.25 * Double.pi * trunc(4.5 + (4 * angle / Double.pi))

                sine = Int(round(sin(angle)))
                cosine = Int(round(cos(angle)))
                r = i + sine
                c = j + cosine
                u = i - sine
                v = j - cosine

                num1 = sqrt(pow(filtered[i][j + 1] - filtered[i][j], 2) + pow(filtered[i + 1][j] - filtered[i][j], 2))

                if (!ArrayUtility.inBounds(i: r, j: c, y: y, x: x)) {
                    r = i
                    c = j
                    num2 = num1
                } else {
                    num2 = sqrt(pow(filtered[r][c + 1] - filtered[r][c], 2) + pow(filtered[r + 1][c] - filtered[r][c], 2))
                }

                if (!ArrayUtility.inBounds(i: u, j: v, y: y, x: x)) {
                    u = i
                    v = j
                    num3 = num1
                } else {
                    num3 = sqrt(pow(filtered[u][v + 1] - filtered[u][v], 2) + pow(filtered[u + 1][v] - filtered[u][v], 2))
                }

                maximum = max(max(num1, num2), num3)

                if (abs(num1 - maximum) < 0.0001) {
                    dThresh[i][j] = maximum
                }

                if (maximum > maxNum) {
                    maxNum = maximum
                }
            }
        }

        let height: Int = Int(sqrt(Double(sketch.count * sketch[0].count)) * lineScale)
        var thresh: Int = 0
        var slope: Double

        for i in 0..<y {
            for j in 0..<x {
                thresh = Int((255.0/maxNum) * dThresh[i][j])
                slope = atan2(filtered[i + 1][j] - filtered[i][j], filtered[i][j + 1] - filtered[i][j]) + (Double.pi / 2)

                if (Double(thresh) >= dThreshMin) {
                    addLine(length: height, array: &sketch, ang: slope, r: i, c: j)
                }

                if (Double(thresh) >= dThreshMax) {
                    addLine(length: height, array: &sketch, ang: slope, r: i, c: j)
                }
            }
        }

        return(sketch)
    }
}
