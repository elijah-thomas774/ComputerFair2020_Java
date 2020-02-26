//
//  Script.swift
//  PencilSketch
//
//  Created by Eric Johns on 9/9/19.
//  Copyright © 2019 Eric Johns. All rights reserved.
//

import Foundation

class Script {
    var controller: ViewController
    var run: [Int]
    
    init(controller: ViewController, script: String) {
        self.controller = controller
        let functions = script.split(separator: "/")
        
        run = Array(repeating: 0, count: functions.count)
        
        for loop in 0..<functions.count {
            let function = functions[loop]
            
            if (function.contains("(")) {
                if (function.starts(with: "14(")) {  // rotate
                    let degrees = Int(function.replacingOccurrences(of: "14(", with: "").replacingOccurrences(of: ")", with: ""))!
                    Input.degrees = degrees
                    run[loop] = 0
                } else if (function.starts(with: "12(")) {   // resize factor
                    let factor = Double(function.replacingOccurrences(of: "12(", with: "").replacingOccurrences(of: ")", with: ""))!
                    Input.resizeFactor = factor
                    run[loop] = 6
                } else if (function.starts(with: "16(")) {  // set red
                    let value = Int(function.replacingOccurrences(of: "16(", with: "").replacingOccurrences(of: ")", with: ""))!
                    Input.redChannel = (value >= 0 && value <= 255) ? value : 0
                    
                    run[loop] = 11
                } else if (function.starts(with: "17(")) {  // set green
                    let value = Int(function.replacingOccurrences(of: "17(", with: "").replacingOccurrences(of: ")", with: ""))!
                    Input.greenChannel = (value >= 0 && value <= 255) ? value : 0
                    
                    run[loop] = 12
                } else if (function.starts(with: "18(")) {  // set blue
                    let value = Int(function.replacingOccurrences(of: "18(", with: "").replacingOccurrences(of: ")", with: ""))!
                    Input.blueChannel = (value >= 0 && value <= 255) ? value : 0
                    
                    run[loop] = 13
                } else if (function.starts(with: "13(")) {  // resize exact
                    let values = function.replacingOccurrences(of: "13(", with: "").replacingOccurrences(of: ")", with: "")
                    let subValues = values.split(separator: ",")
                    
                    Input.resizeWidth = Int(subValues[0])!
                    Input.resizeHeight = Int(subValues[1])!
                    
                    run[loop] = 23
                } else if (function.starts(with: "25(")) {  // add red
                    let value = Int(function.replacingOccurrences(of: "25(", with: "").replacingOccurrences(of: ")", with: ""))!
                    Input.addRedChannel = value
                    run[loop] = 25
                } else if (function.starts(with: "26(")) {  // add green
                    let value = Int(function.replacingOccurrences(of: "26(", with: "").replacingOccurrences(of: ")", with: ""))!
                    Input.addGreenChannel = value
                    run[loop] = 26
                } else if (function.starts(with: "27(")) {  // add blue
                    let value = Int(function.replacingOccurrences(of: "27(", with: "").replacingOccurrences(of: ")", with: ""))!
                    Input.addBlueChannel = value
                    run[loop] = 27
                } else if (function.starts(with: "28(")) {  // add RGB
                    let values = function.replacingOccurrences(of: "28(", with: "").replacingOccurrences(of: ")", with: "")
                    let subValues = values.split(separator: ",")
                    
                    Input.addRedChannel = Int(subValues[0])!
                    Input.addGreenChannel = Int(subValues[1])!
                    Input.addBlueChannel = Int(subValues[2])!
                    
                    run[loop] = 28
                } else if (function.starts(with: "29(")) {  // grad mag
                    let values = function.replacingOccurrences(of: "29(", with: "").replacingOccurrences(of: ")", with: "")
                    let subValues = values.split(separator: ",")
                    
                    Input.k = Int(subValues[0])!
                    Input.sigma = Double(subValues[1])!
                    
                    run[loop] = 97
                }  else if (function.starts(with: "30(")) { // edge detection
                    let values = function.replacingOccurrences(of: "30(", with: "").replacingOccurrences(of: ")", with: "")
                    let subValues = values.split(separator: ",")
                    
                    Input.k = Int(subValues[0])!
                    Input.sigma = Double(subValues[1])!
                    Input.min = Int(subValues[2])!
                    
                    run[loop] = 98
                } else if (function.starts(with: "31(")) {  // sketch
                    let values = function.replacingOccurrences(of: "31(", with: "").replacingOccurrences(of: ")", with: "")
                    let subValues = values.split(separator: ",")
                    
                    Input.k = Int(subValues[0])!
                    Input.sigma = Double(subValues[1])!
                    Input.min = Int(subValues[2])!
                    Input.lineScale = Double(subValues[3])!
                    
                    run[loop] = 99
                } else if (function.starts(with: "32(")) { // sketch 2
                    let values = function.replacingOccurrences(of: "32(", with: "").replacingOccurrences(of: ")", with: "")
                    let subValues = values.split(separator: ",")
                    
                    Input.k = Int(subValues[0])!
                    Input.sigma = Double(subValues[1])!
                    Input.min = Int(subValues[2])!
                    Input.lineScale = Double(subValues[3])!
                    
                    run[loop] = 100
                }
            } else {
                run[loop] = Int(function)!
            }
        }
    }
    
    func runScript() {
        DispatchQueue.global(qos: .background).async {
            for function in self.run {
                ViewController.picture.manipulateImage(manipulation: Manipulation(rawValue: function)!)
            }
        }
    }
}
