//
//  Input.swift
//  PencilSketch
//
//  Created by Eric Johns on 9/20/19.
//  Copyright © 2019 Eric Johns. All rights reserved.
//

import Foundation
import SwiftImage

class Input {
    static var k: Int = 2
    static var sigma: Double = 1.35
    static var min: Int = 8
    static var lineScale: Double = 0.015
    
    static var resizeFactor: Double = 0.5
    static var degrees: Int = 90
    
    static var redChannel: Int = 0
    static var greenChannel: Int = 0
    static var blueChannel: Int = 0
    
    static var addRedChannel: Int = 0
    static var addGreenChannel: Int = 0
    static var addBlueChannel: Int = 0
    
    static var resizeHeight: Int = 500
    static var resizeWidth: Int = 500
    
    static var stegChannel: Int = 0
    static var encodeImage: Image<RGBA<UInt8>> = Image<RGBA<UInt8>>(named: "tree")!
    static var promptEncode: Bool = false
}
