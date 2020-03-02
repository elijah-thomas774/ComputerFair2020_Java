//
//  Picture.swift
//  PencilSketch
//
//  Created by Eric Johns on 8/28/19.
//  Copyright © 2019 Eric Johns. All rights reserved.
//

import UIKit
import Foundation
import SwiftImage

class Picture {
    var image: Image<RGBA<UInt8>>
    
    init() {
        //instntiates default image
        image = Image<RGBA<UInt8>>(named: "townhall")!
    }
    
    init(name: String) {
        //creates an image with the given name
        image = Image<RGBA<UInt8>>(named: name)!
    }
    
    init(image: Image<RGBA<UInt8>>) {
        //instantiates a given image
        self.image = image
    }
    
    init(width: Int, height: Int) {
        //instantiates a black image of the given dimensions
        image = Image<RGBA<UInt8>>(width: width, height: height, pixel: .black)
    }
    
    init(array: [[Double]]) {
        //instantiates an image from a given Double array
        image = Image<RGBA<UInt8>>(width: array[0].count, height: array.count, pixel: .black)
        
        //copies each value from array to pixel
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                image[x, y] = RGBA(red: UInt8(array[y][x]), green: UInt8(array[y][x]), blue: UInt8(array[y][x]), alpha: UInt8(255))
            }
        }
    }
    
    init(array: [[Int]]) {
        //instantiates an image from a given Int array
        image = Image<RGBA<UInt8>>(width: array[0].count, height: array.count, pixel: .black)
        
        //copies each value form array to pixel
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                image[x, y] = RGBA(red: UInt8(array[y][x]), green: UInt8(array[y][x]), blue: UInt8(array[y][x]), alpha: UInt8(255))
            }
        }
    }
    
    func getHeight() -> Int {
        //returns the height of the image
        return image.height
    }
    
    func getWidth() -> Int {
        //returns the width of the image
        return image.width
    }
    
    func getImage() -> Image<RGBA<UInt8>> {
        //returns the image
        return image
    }
    
    func getUIImage() -> UIImage {
        //returns the image usable in a UIImageView
        return image.uiImage
    }
    
    func setPicture(name: String) {
        //sets the name of the image
        image = Image<RGBA<UInt8>>(named: name)!
    }
    
    func setPicture(image: Image<RGBA<UInt8>>) {
        //sets the image
        self.image = image
    }
    
    func setPicture(image: UIImage) {
        //sets the image from a UIImageView image
        self.image = Image<RGBA<UInt8>>(uiImage: image)
    }
    
    func getPixel(x: Int, y: Int) -> RGBA<UInt8> {
        //returns the pixel at a given coordinate (x,y)
        return image.pixelAt(x: x, y: y)!
    }
    
    func setArray(array: [[Int]]) {
        //converts an Int array to an image
        image = Image<RGBA<UInt8>>(width: array[0].count, height: array.count, pixel: .black)
        
        //copies each value from array to pixel
        for y in 0..<array.count {
            for x in 0..<array[0].count {
                image[x, y] = RGBA(red: UInt8(array[y][x]), green: UInt8(array[y][x]), blue: UInt8(array[y][x]), alpha: UInt8(255))
            }
        }
    }
    
    func rotate(degrees: Int) {
        //rotates an image by a given angles
        image = image.rotated(byDegrees: degrees)
    }
    
    func resize(width: Int, height: Int) {
        //resizes an image to given dimensions
        image = image.resizedTo(width: width, height: height)
    }
    
    func resize(factor: Double) {
        
        //scales an image by a given factor
        let newWidth: Int = Int(Double(getWidth()) * factor)
        let newHeight: Int = Int(Double(getHeight()) * factor)
        
        image = image.resizedTo(width: newWidth, height: newHeight)
    }
    
    func resizeExact(width: Int, height: Int) {
        //resizes an image to given dimensions more exactly
        image = image.resizedTo(width: width, height: height)
    }
    
    func convertToGray() {
        //converts every pixel to grayscale
        for index in 0..<(image.width * image.height) {
            let y = index % image.height
            let x = index / image.height
            
            //weighted average
            let red = Double(image.pixelAt(x: x, y: y)!.red) * 0.21
            let green = Double(image.pixelAt(x: x, y: y)!.green) * 0.72
            let blue = Double(image.pixelAt(x: x, y: y)!.blue) * 0.07
            let alpha = image.pixelAt(x: x, y: y)!.alpha
            
            let gray = UInt8(red + green + blue)
            
            //set each pixel to new color
            image[x, y] = RGBA(red: gray, green: gray, blue: gray, alpha: alpha)
        }
    }
    
    func squareImage() {
        //truncates the image to be a perfect square
        if (image.width != image.height) {
            var slice: ImageSlice<RGBA<UInt8>>
            let width = image.width % 2 == 0 ? image.width : image.width - 1
            let height = image.height % 2 == 0 ? image.height : image.height - 1
            
            if (width > image.height) {
                let cropOut = (width - height) / 2
                slice = image[cropOut..<(width - cropOut), 0..<height]
            } else {
                let cropOut = (height - width) / 2
                slice = image[0..<width, cropOut..<(height - cropOut)]
            }
            
            image = Image<RGBA<UInt8>>(slice)
        }
    }

    func flipDiagonal() {
        if (image.width != image.height) {
            squareImage()
        }
        
        for y in 0..<image.height {
            for x in 0..<y {
                let pixelOriginal: RGBA<UInt8> = (image.pixelAt(x: x, y: y))!
                let pixelNew: RGBA<UInt8> = (image.pixelAt(x: y, y: x))!
                image[x, y] = pixelNew
                image[y, x] = pixelOriginal
            }
        }
    }
    
    func flipVertical() {
        image = image.yReversed()
    }
    
    func flipHorizontal() {
        image = image.xReversed()
    }
    
    func channelValue(channel: Int, value: Int) {
        for index in 0..<(image.width * image.height) {
            let y = index % image.height
            let x = index / image.height
            
            switch (channel) {
                case 0: image[x, y].red = UInt8(value); break
                case 1: image[x, y].green = UInt8(value); break
                case 2: image[x, y].blue = UInt8(value); break
                default: break
            }
        }
    }
    
    func addValues(channel: Int, value: Int) {
        for index in 0..<(image.width * image.height) {
            let y = index % image.height
            let x = index / image.height
            
            switch (channel) {
                case 0:
                    var redValue = Int(image[x, y].red) + value
                    
                    if (redValue > 255) {
                        redValue = 255
                    } else if (redValue < 0) {
                        redValue = 0
                    }
                    
                    image[x, y].red = UInt8(redValue)
                    break
                case 1:
                    var greenValue = Int(image[x, y].green) + value
                    
                    if (greenValue > 255) {
                        greenValue = 255
                    } else if (greenValue < 0) {
                        greenValue = 0
                    }
                    
                    image[x, y].green = UInt8(greenValue)
                    break
                case 2:
                    var blueValue = Int(image[x, y].blue) + value
                    
                    if (blueValue > 255) {
                        blueValue = 255
                    } else if (blueValue < 0) {
                        blueValue = 0
                    }
                    
                    image[x, y].blue = UInt8(blueValue)
                    break
                default: break
            }
        }
    }
    
    func addAll(red: Int, green: Int, blue: Int) {
        for index in 0..<(image.width * image.height) {
            let y = index % image.height
            let x = index / image.height

            var redValue = Int(image[x, y].red) + red
            var greenValue = Int(image[x, y].green) + green
            var blueValue = Int(image[x, y].blue) + blue
            
            if (redValue > 255) {
                redValue = 255
            } else if (redValue < 0) {
                redValue = 0
            }
            
            if (greenValue > 255) {
                greenValue = 255
            } else if (greenValue < 0) {
                greenValue = 0
            }
            
            if (blueValue > 255) {
                blueValue = 255
            } else if (blueValue < 0) {
                blueValue = 0
            }
            
            image[x, y].red = UInt8(redValue)
            image[x, y].green = UInt8(greenValue)
            image[x, y].blue = UInt8(blueValue)
        }
    }
    
    func swapChannel(channel1: Int, channel2: Int) {
        for index in 0..<(image.width * image.height) {
            let y = index % image.height
            let x = index / image.height

            let pixel: RGBA<UInt8> = (image.pixelAt(x: x, y: y))!
            
            if (channel1 == 0 && channel2 == 1) {
                image[x, y].green = pixel.red
                image[x, y].red = pixel.green
            } else if (channel1 == 0 && channel2 == 2) {
                image[x, y].blue = pixel.red
                image[x, y].red = pixel.blue
            } else if (channel1 == 1 && channel2 == 2) {
                image[x, y].blue = pixel.green
                image[x, y].green = pixel.blue
            }
        }
    }
    
    func keep(channel: Int) {
        for index in 0..<(image.width * image.height) {
            let y = index % image.height
            let x = index / image.height
            
            switch (channel) {
                case 0: image[x, y].green = 0; image[x, y].blue = 0; break
                case 1: image[x, y].red = 0; image[x, y].blue = 0; break
                case 2: image[x, y].red = 0; image[x, y].green = 0; break
                default: break
            }
        }
    }
    
    func encode(channel: Int, toEncode: Image<RGBA<UInt8>>) {
        for y in 0..<(min(image.height, toEncode.height)) {
            for x in 0..<(min(image.width, toEncode.width)) {
                let red = Double(toEncode[x, y].red) * 0.21
                let green = Double(toEncode[x, y].green) * 0.72
                let blue = Double(toEncode[x, y].blue) * 0.07
                
                if (channel == 0) {
                    image[x, y].red -= image[x, y].red % 2
                    
                    if (red + green + blue <= 127) {
                        image[x, y].red += 1
                    }
                } else if (channel == 1) {
                    image[x, y].green -= image[x, y].green % 2
                    
                    if (red + green + blue <= 127) {
                        image[x, y].green += 1
                    }
                } else {
                    image[x, y].blue -= image[x, y].blue % 2
                    
                    if (red + green + blue <= 127) {
                        image[x, y].blue += 1
                    }
                }
            }
        }
        
        print("Encode successful")
    }
    
    func decode(channel: Int) {
        for y in 0..<(image.height) {
            for x in 0..<(image.width) {
                var value: Int
                
                if (channel == 0) {
                    value = Int(image[x, y].red) % 2
                } else if (channel == 1) {
                    value = Int(image[x, y].green) % 2
                } else {
                    value = Int(image[x, y].blue) % 2
                }
                
                if (value == 1) {
                    image[x, y] = RGBA(red: 0, green: 0, blue: 0)
                } else {
                    image[x, y] = RGBA(red: 255, green: 255, blue: 255)
                }
            }
        }
        
        print("Decode successful")
    }
    
    func mirrorVertical(top: Bool) {
        for x in 0..<(image.width) {
            for y in 0..<(image.height / 2) {
                if (top) {
                    let pixel: RGBA<UInt8> = (image.pixelAt(x: x, y: y))!
                    image[x, image.height - y - 1] = pixel
                } else {
                    let pixel: RGBA<UInt8> = (image.pixelAt(x: x, y: image.height - y - 1))!
                    image[x, y] = pixel
                }
            }
        }
    }
    
    func mirrorHorizontal(left: Bool) {
        for x in 0..<(image.width / 2) {
            for y in 0..<(image.height) {
                if (left) {
                    let pixel: RGBA<UInt8> = (image.pixelAt(x: x, y: y))!
                    image[image.width - x - 1, y] = pixel
                } else {
                    let pixel: RGBA<UInt8> = (image.pixelAt(x: image.width - x - 1, y: y))!
                    image[x, y] = pixel
                }
            }
        }
    }
    
    func mirrorDiagonal(leftRight: Bool) {
        if (image.width != image.height) {
            squareImage()
        }
        
        for x in 0..<image.width {
            for y in 0..<(image.height - x) {
                if (leftRight) {
                    let pixel: RGBA<UInt8> = (image.pixelAt(x: y, y: x))!
                    image[image.width - x - 1, image.height - y - 1] = pixel
                } else {
                    let pixel: RGBA<UInt8> = (image.pixelAt(x: image.height - y - 1, y: image.width - x - 1))!
                    image[x, y] = pixel
                }
            }
        }
    }
    
    func negate() {
        for index in 0..<(image.width * image.height) {
            let y = index % image.height
            let x = index / image.height
            
            let red = 255 - image.pixelAt(x: x, y: y)!.red
            let green = 255 - image.pixelAt(x: x, y: y)!.green
            let blue = 255 - image.pixelAt(x: x, y: y)!.blue
            let alpha = image.pixelAt(x: x, y: y)!.alpha

            image[x, y] = RGBA(red: red, green: green, blue: blue, alpha: alpha)
        }
    }
    
    func convertToBinary() {
        for index in 0..<(image.width * image.height) {
            let y = index % image.height
            let x = index / image.height

            let red = Double(image.pixelAt(x: x, y: y)!.red) * 0.21
            let green = Double(image.pixelAt(x: x, y: y)!.green) * 0.72
            let blue = Double(image.pixelAt(x: x, y: y)!.blue) * 0.07
            let alpha = image.pixelAt(x: x, y: y)!.alpha
            
            let gray = UInt8(red + green + blue)
            
            if (gray > 127) {
                image[x, y] = RGBA(red: 255, green: 255, blue: 255, alpha: alpha)
            } else {
                image[x, y] = RGBA(red: 0, green: 0, blue: 0, alpha: alpha)
            }
        }
    }
    
    static func convertArray(picture: Picture) -> [[Int]] {
        var imageArray: [[Int]] = Array(repeating: Array(repeating: 0, count: picture.getWidth()), count: picture.getHeight())
        
        for y in 0..<imageArray.count {
            for x in 0..<imageArray[0].count {
                imageArray[y][x] = Int(picture.getPixel(x: x, y: y).red)
            }
        }
        
        return(imageArray)
    }
    
    func convertArray() -> [[Int]] {
        var imageArray: [[Int]] = Array(repeating: Array(repeating: 0, count: image.width), count: image.height)
        
        for y in 0..<imageArray.count {
            for x in 0..<imageArray[0].count {
                imageArray[y][x] = Int(getPixel(x: x, y: y).red)
            }
        }
        
        return(imageArray)
    }
    
    func manipulateImage(manipulation: Manipulation) {
        switch (manipulation) {
            case .flipVertical:
                self.flipVertical()
                break
            case .flipHorizontal:
                self.flipHorizontal()
                break
            case .flipDiagonal:
                self.flipDiagonal()
                break
            case .mirrorVerticalTop:
                self.mirrorVertical(top: true)
                break
            case .mirrorVerticalBottom:
                self.mirrorVertical(top: false)
                break
            case .mirrorHorizontalLeft:
                self.mirrorHorizontal(left: true)
                break
            case .mirrorHorizontalRight:
                self.mirrorHorizontal(left: false)
                break
            case .mirrorDiagonalTLBR:
                self.mirrorDiagonal(leftRight: true)
                break
            case .mirrorDiagonalBRTL:
                self.mirrorDiagonal(leftRight: false)
                break
            case .convertToGray:
                self.convertToGray()
                break
            case .convertToBinary:
                self.convertToBinary()
                break
            case .negate:
                self.negate()
                break
            case .rotate:
                self.rotate(degrees: Input.degrees)
                break
            case .resizeFactor:
                self.resize(factor: Input.resizeFactor)
                break
            case .resizeExact:
                self.resizeExact(width: Input.resizeWidth, height: Input.resizeHeight)
                break
            case .squareImage:
                self.squareImage()
                break
            case .setRed:
                self.channelValue(channel: 0, value: Input.redChannel)
                break
            case .setGreen:
                self.channelValue(channel: 1, value: Input.greenChannel)
                break
            case .setBlue:
                self.channelValue(channel: 2, value: Input.blueChannel)
                break
            case .swapRedGreen:
                self.swapChannel(channel1: 0, channel2: 1)
                break
            case .swapRedBlue:
                self.swapChannel(channel1: 0, channel2: 2)
                break
            case .swapGreenBlue:
                self.swapChannel(channel1: 1, channel2: 2)
                break
            case .keepRed:
                self.keep(channel: 0)
                break
            case .keepGreen:
                self.keep(channel: 1)
                break
            case .keepBlue:
                self.keep(channel: 2)
                break
            case .addRed:
                self.addValues(channel: 0, value: Input.addRedChannel)
                break
            case .addGreen:
                self.addValues(channel: 1, value: Input.addGreenChannel)
                break
            case .addBlue:
                self.addValues(channel: 2, value: Input.addBlueChannel)
                break
            case .addRGB:
                self.addAll(red: Input.addRedChannel, green: Input.addGreenChannel, blue: Input.addBlueChannel)
                break
            case .encode:
                self.encode(channel: Input.stegChannel, toEncode: Input.encodeImage)
                break
            case .decode:
                self.decode(channel: Input.stegChannel)
                break
            case .gradMag:
                var array = convertArray()
                let borderReflect: [[Int]] = EdgeDetection.createBorder(kernel: Input.k, image: &array)
                let kernel = EdgeDetection.createKernel(kernel: Input.k, sigma: Input.sigma)
                let filter = EdgeDetection.filter(kernel: kernel, image: borderReflect)
                let gradX = EdgeDetection.calcGradientX(image: filter), gradY = EdgeDetection.calcGradientY(image: filter)
                let gradMag = EdgeDetection.calcGradMag(gradX: gradX, gradY: gradY)
                setArray(array: ArrayUtility.normalize(array: ArrayUtility.toIntArray(array: gradMag)))
                break
            case .edgeDetection:
                var array = convertArray()
                let borderReflect: [[Int]] = EdgeDetection.createBorder(kernel: Input.k, image: &array)
                let kernel = EdgeDetection.createKernel(kernel: Input.k, sigma: Input.sigma)
                let filter = EdgeDetection.filter(kernel: kernel, image: borderReflect)
                let gradX = EdgeDetection.calcGradientX(image: filter), gradY = EdgeDetection.calcGradientY(image: filter)
                let gradMag = EdgeDetection.calcGradMag(gradX: gradX, gradY: gradY)
                var gradAngle = EdgeDetection.calcGradAngle(gradX: gradX, gradY: gradY)
                let gradAngleAdj = EdgeDetection.adjustGradAngle(gradAngle: &gradAngle)
                let calcNonMaxSupp = EdgeDetection.calcNonMaxSupp(gradMag: gradMag, gradAngleAdj: gradAngleAdj)
                let doubleThresh = EdgeDetection.doubleThresh(nonMaxSupp: ArrayUtility.toDoubleArray(array: ArrayUtility.normalize(array: ArrayUtility.toIntArray(array: calcNonMaxSupp))), min: Input.min, max: Input.min * 3)
                let calcEdge = EdgeDetection.calcEdge(doubleThresh: doubleThresh)
                setArray(array: calcEdge)
                break
            case .sketch:
                var array = convertArray()
                let borderReflect: [[Int]] = EdgeDetection.createBorder(kernel: Input.k, image: &array)
                let kernel = EdgeDetection.createKernel(kernel: Input.k, sigma: Input.sigma)
                let filter = EdgeDetection.filter(kernel: kernel, image: borderReflect)
                let gradX = EdgeDetection.calcGradientX(image: filter), gradY = EdgeDetection.calcGradientY(image: filter)
                let gradMag = EdgeDetection.calcGradMag(gradX: gradX, gradY: gradY)
                var gradAngle = EdgeDetection.calcGradAngle(gradX: gradX, gradY: gradY)
                let gradAngleAdj = EdgeDetection.adjustGradAngle(gradAngle: &gradAngle)
                let calcNonMaxSupp = EdgeDetection.calcNonMaxSupp(gradMag: gradMag, gradAngleAdj: gradAngleAdj)
                let doubleThresh = EdgeDetection.doubleThresh(nonMaxSupp: ArrayUtility.toDoubleArray(array: ArrayUtility.normalize(array: ArrayUtility.toIntArray(array: calcNonMaxSupp))), min: Input.min, max: Input.min * 3)
                let sketch = Sketch.sketch(dThresh: doubleThresh, gradAng: gradAngle, lineScale: Input.lineScale)
                setArray(array: ArrayUtility.negate(array: ArrayUtility.normalize(array: sketch)))
                break
            case .sketch2:
                var array = convertArray()
                let borderReflect: [[Int]] = EdgeDetection.createBorder(kernel: Input.k, image: &array)
                let kernel = EdgeDetection.createKernel(kernel: Input.k, sigma: Input.sigma)
                let filter = EdgeDetection.filter(kernel: kernel, image: borderReflect)
                let sketch = Sketch.sketch2(filtered: filter, lineScale: Input.lineScale, dThreshMin: Double(Input.min), dThreshMax: Double(Input.min * 3))
                setArray(array: ArrayUtility.negate(array: ArrayUtility.normalize(array: sketch)))
                break
        }
    }
}

public enum Manipulation: Int {
    case flipHorizontal = 0
    case flipVertical = 1
    case flipDiagonal = 2
    case mirrorVerticalTop = 3
    case mirrorVerticalBottom = 4
    case mirrorHorizontalLeft = 5
    case mirrorHorizontalRight = 6
    case mirrorDiagonalTLBR = 7
    case mirrorDiagonalBRTL = 8
    case convertToGray = 9
    case convertToBinary = 10
    case negate = 11
    case resizeFactor = 12
    case resizeExact = 13
    case rotate = 14
    case squareImage = 15
    case setRed = 16
    case setGreen = 17
    case setBlue = 18
    case swapRedGreen = 19
    case swapRedBlue = 20
    case swapGreenBlue = 21
    case keepRed = 22
    case keepGreen = 23
    case keepBlue = 24
    case addRed = 25
    case addGreen = 26
    case addBlue = 27
    case addRGB = 28
    case gradMag = 29
    case edgeDetection = 30
    case sketch = 31
    case sketch2 = 32
    case encode = 33
    case decode = 34
}
