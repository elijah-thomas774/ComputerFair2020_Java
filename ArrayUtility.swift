//
//  ArrayUtility.swift
//  PencilSketch
//
//  Created by Eric Johns on 9/1/19.
//  Commented by Eli Siron on 3/2/2020.
//  Copyright © 2019 Eric Johns. All rights reserved.
//

import Foundation
import SwiftImage

class ArrayUtility {
    static func flipHorizontal(array: [[Int]]) -> [[Int]] {
        var arrayCopy: [[Int]] = array
        
        //methodically swaps elements at the top and bottom of each column, moving inward until it reaches the center
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
        
        //methodically swaps elements at the beginning and end of each row, moving inward until it reaches the center
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
        var array: [[Int]] = Array(repeating: Array(repeating: 0, count: image.width), count: image.height)
        
        //creates a new image of binary form from a color image
        for y in 0..<image.height {
            for x in 0..<image.width {
                //weighted average of the color channels
                let red = Double(Int(image[x, y].red)) * 0.21
                let green = Double(Int(image[x, y].green)) * 0.72
                let blue = Double(Int(image[x, y].blue)) * 0.07
                
                //checks and sets the appropriate binary values for the current element
                array[y][x] = (red + green + blue) > 127 ? 255 : 0
            }
        }
        
        return(array)
    }
    
    static func toIntArray(array: [[Double]]) -> [[Int]] {
        var intArray: [[Int]] = Array(repeating: Array(repeating: 0, count: array[0].count), count: array.count)
        
        //casts every element to int
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                intArray[y][x] = Int(array[y][x])
            }
        }
        
        return(intArray)
    }
    
    static func toDoubleArray(array: [[Int]]) -> [[Double]] {
        var doubleArray: [[Double]] = Array(repeating: Array(repeating: 0, count: array[0].count), count: array.count)
        
        //casts every element to double
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                doubleArray[y][x] = Double(array[y][x])
            }
        }
        
        return(doubleArray)
    }
    
    static func negate(array: [[Int]]) -> [[Int]] {
        var negated: [[Int]] = Array(repeating: Array(repeating: 0, count: array[0].count), count: array.count)
        
        //subtracts every element from 255 to create a negated image
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
        
        //finds the maximum and minimum values of the array
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                if (max < array[y][x]) {
                    max = array[y][x]
                } else if (min > array[y][x]) {
                    min = array[y][x]
                }
            }
        }
        
        //adjusts the range of the array to be 0-255, altering values as needed
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                normalized[y][x] = Int((255.0 / Double(max - min)) * Double(array[y][x] - min))
            }
        }
        
        return(normalized)
    }
    
    static func copyRange(image1: inout [[Int]], copyTo: inout [[Int]], startX: Int, endX: Int, startY: Int, endY: Int, pasteX: Int, pasteY: Int) {
        var copyPortion: [[Int]] = Array(repeating: Array(repeating: 0, count: endX - startX), count: endY - startY)
        
        //copies the desired portion to an all new array
        for y in startY..<endY {
            for x in startX..<endX {
                copyPortion[y - startY][x - startX] = image1[y][x]
            }
        }
        //copies the desired portion's new array to the desired location of the output
        for y in pasteY..<min(copyPortion.count + pasteY, copyTo.count) {
            for x in pasteX..<min(copyPortion[0].count + pasteX, copyTo[0].count) {
                copyTo[y][x] = copyPortion[y - pasteY][x - pasteX]
            }
        }
    }
    
    static func inBounds(i: Int, j: Int, y: Int, x: Int) -> Bool {
        var inBound: Bool = true
        //checks if a set of coordinates is within the bounds of the image
        if (i < 0 || i >= y || j < 0 || j >= x) {
            inBound = false
        }
        
        return(inBound)
    }
}
