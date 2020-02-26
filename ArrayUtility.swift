//
//  ArrayUtility.swift
//  PencilSketch
//
//  Created by Eric Johns on 9/1/19.
//  Copyright © 2019 Eric Johns. All rights reserved.
//

import Foundation
import SwiftImage

class ArrayUtility {
    static func flipHorizontal(array: [[Int]]) -> [[Int]] {
        var arrayCopy: [[Int]] = array
        
        for y in 0..<(arrayCopy.count) {
            for x in 0..<(arrayCopy[0].count / 2) {
                let placehold = arrayCopy[y][x]
                arrayCopy[y][x] = arrayCopy[y][arrayCopy[0].count - x - 1]
                arrayCopy[y][arrayCopy[0].count - x - 1] = placehold
            }
        }
        
        return(arrayCopy)
    }
    
    static func flipVertical(array: [[Int]]) -> [[Int]] {
        var arrayCopy: [[Int]] = array
        
        for y in 0..<(arrayCopy.count / 2) {
            for x in 0..<(arrayCopy[0].count) {
                let placehold = arrayCopy[y][x]
                arrayCopy[y][x] = arrayCopy[arrayCopy.count - y - 1][x]
                arrayCopy[arrayCopy.count - y - 1][x] = placehold
            }
        }
        
        return(arrayCopy)
    }
    
    static func binary(image: Image<RGBA<UInt8>>) -> [[Int]] {
        print("Attempted")
        var array: [[Int]] = Array(repeating: Array(repeating: 0, count: image.width), count: image.height)
        
        for y in 0..<image.height {
            for x in 0..<image.width {
                let red = Double(Int(image[x, y].red)) * 0.21
                let green = Double(Int(image[x, y].green)) * 0.72
                let blue = Double(Int(image[x, y].blue)) * 0.07
                
                print(red + green + blue)
                
                array[y][x] = (red + green + blue) > 127 ? 255 : 0
            }
        }
        
        return(array)
    }
    
    static func toIntArray(array: [[Double]]) -> [[Int]] {
        var intArray: [[Int]] = Array(repeating: Array(repeating: 0, count: array[0].count), count: array.count)
        
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                intArray[y][x] = Int(array[y][x])
            }
        }
        
        return(intArray)
    }
    
    static func toDoubleArray(array: [[Int]]) -> [[Double]] {
        var doubleArray: [[Double]] = Array(repeating: Array(repeating: 0, count: array[0].count), count: array.count)
        
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                doubleArray[y][x] = Double(array[y][x])
            }
        }
        
        return(doubleArray)
    }
    
    static func negate(array: [[Int]]) -> [[Int]] {
        var negated: [[Int]] = Array(repeating: Array(repeating: 0, count: array[0].count), count: array.count)
        
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                negated[y][x] = 255 - array[y][x]
            }
        }
        
        return(negated)
    }
    
    static func normalize(array: [[Int]]) -> [[Int]] {
        var normalized: [[Int]] = Array(repeating: Array(repeating: 0, count: array[0].count), count: array.count)
        var max = array[0][0], min = array[0][0]
        
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                if (max < array[y][x]) {
                    max = array[y][x]
                } else if (min > array[y][x]) {
                    min = array[y][x]
                }
            }
        }
        
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                normalized[y][x] = Int((255.0 / Double(max - min)) * Double(array[y][x] - min))
            }
        }
        
        return(normalized)
    }
    
    static func copyRange(image1: inout [[Int]], copyTo: inout [[Int]], startX: Int, endX: Int, startY: Int, endY: Int, pasteX: Int, pasteY: Int) {
        var copyPortion: [[Int]] = Array(repeating: Array(repeating: 0, count: endX - startX), count: endY - startY)
        
        for y in startY..<endY {
            for x in startX..<endX {
                copyPortion[y - startY][x - startX] = image1[y][x]
            }
        }
        
        for y in pasteY..<min(copyPortion.count + pasteY, copyTo.count) {
            for x in pasteX..<min(copyPortion[0].count + pasteX, copyTo[0].count) {
                copyTo[y][x] = copyPortion[y - pasteY][x - pasteX]
            }
        }
    }
    
    static func inBounds(i: Int, j: Int, y: Int, x: Int) -> Bool {
        var inBound: Bool = true
        
        if (i < 0 || i >= y || j < 0 || j >= x) {
            inBound = false
        }
        
        return(inBound)
    }
}
