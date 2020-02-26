//
//  Prompt.swift
//  PencilSketch
//
//  Created by Eric Johns on 9/6/19.
//  Copyright © 2019 Eric Johns. All rights reserved.
//

import Foundation
import UIKit

class Prompt {
    var controller: ViewController
    
    init(controller: ViewController) {
        self.controller = controller
    }
    
    func promptFeatures() -> UIAlertController {
        let featureDialog = UIAlertController(title: "Test Feature", message: "Select a Feature to Test", preferredStyle: .alert)
        
        featureDialog.addAction(UIAlertAction(title: "Flip Image", style: .default, handler: flipDialog))
        featureDialog.addAction(UIAlertAction(title: "Mirror Image", style: .default, handler: mirrorDialog))
        featureDialog.addAction(UIAlertAction(title: "Channel Manipulation", style: .default, handler: manipDialog))
        featureDialog.addAction(UIAlertAction(title: "Convert To Gray", style: .default, handler: convertToGray))
        featureDialog.addAction(UIAlertAction(title: "Convert To Binary", style: .default, handler: convertToBinary))
        featureDialog.addAction(UIAlertAction(title: "Negate", style: .default, handler: negate))
        featureDialog.addAction(UIAlertAction(title: "Rotate", style: .default, handler: rotateDialog))
        featureDialog.addAction(UIAlertAction(title: "Resize", style: .default, handler: resizeDialog))
        featureDialog.addAction(UIAlertAction(title: "Steganography", style: .default, handler: steganography))
        featureDialog.addAction(UIAlertAction(title: "Gradient Magnitude", style: .default, handler: gradMag))
        featureDialog.addAction(UIAlertAction(title: "Edge Detection", style: .default, handler: edgeDetection))
        featureDialog.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(featureDialog)
    }
    
    func promptFlip() -> UIAlertController {
        let flipDialog = UIAlertController(title: "Flip Features", message: "Select which flip you would like to perform.", preferredStyle: .alert)
        flipDialog.addAction(UIAlertAction(title: "Flip Vertical", style: .default, handler: flipVertical))
        flipDialog.addAction(UIAlertAction(title: "Flip Horizontal", style: .default, handler: flipHorizontal))
        flipDialog.addAction(UIAlertAction(title: "Flip Diagonal", style: .default, handler: flipDiagonal))
        flipDialog.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(flipDialog)
    }
    
    func promptMirror() -> UIAlertController {
        let mirrorDialog = UIAlertController(title: "Mirror Features", message: "Select which mirror you would like to perform.", preferredStyle: .alert)
        mirrorDialog.addAction(UIAlertAction(title: "Mirror Vertical (Top)", style: .default, handler: mirrorVertical))
        mirrorDialog.addAction(UIAlertAction(title: "Mirror Vertical (Bottom)", style: .default, handler: mirrorVerticalBottom))
        mirrorDialog.addAction(UIAlertAction(title: "Mirror Horizontal (Left)", style: .default, handler: mirrorHorizontal))
        mirrorDialog.addAction(UIAlertAction(title: "Mirror Horizontal (Right)", style: .default, handler: mirrorHorizontalRight))
        mirrorDialog.addAction(UIAlertAction(title: "Mirror Diagonal (TL -> BR)", style: .default, handler: mirrorDiagonalLeft))
        mirrorDialog.addAction(UIAlertAction(title: "Mirror Diagonal (BR -> TL)", style: .default, handler: mirrorDiagonalRight))
        mirrorDialog.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(mirrorDialog)
    }
    
    func promptKeep() -> UIAlertController {
        let keepDialog = UIAlertController(title: "Keep Features", message: "Select which color channel you would like to keep.", preferredStyle: .alert)
        keepDialog.addAction(UIAlertAction(title: "Keep Red", style: .default, handler: keepRed))
        keepDialog.addAction(UIAlertAction(title: "Keep Green", style: .default, handler: keepGreen))
        keepDialog.addAction(UIAlertAction(title: "Keep Blue", style: .default, handler: keepBlue))
        keepDialog.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(keepDialog)
    }
    
    func promptSwap() -> UIAlertController {
        let swapDialog = UIAlertController(title: "Swap Features", message: "Select which color channel you would like to keep.", preferredStyle: .alert)
        swapDialog.addAction(UIAlertAction(title: "Swap Red and Green", style: .default, handler: swapRedGreen))
        swapDialog.addAction(UIAlertAction(title: "Swap Red and Blue", style: .default, handler: swapRedBlue))
        swapDialog.addAction(UIAlertAction(title: "Swap Green and Blue", style: .default, handler: swapGreenBlue))
        swapDialog.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(swapDialog)
    }
    
    func promptSteganography() -> UIAlertController {
        let stegDialog = UIAlertController(title: "Steganography", message: "Would you like to encode or decode an image?", preferredStyle: .alert)
        stegDialog.addAction(UIAlertAction(title: "Encode", style: .default, handler: encodeDialog))
        stegDialog.addAction(UIAlertAction(title: "Decode", style: .default, handler: decodeDialog))
        stegDialog.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(stegDialog)
    }
    
    func promptChannelManipulation() -> UIAlertController {
        let manipDialog = UIAlertController(title: "Channel Manipulation", message: "Select which type of manipulation you would like to perform.", preferredStyle: .alert)
        
        manipDialog.addAction(UIAlertAction(title: "Swap Channels", style: .default, handler: swapDialog))
        manipDialog.addAction(UIAlertAction(title: "Channel Values", style: .default, handler: channelDialog))
        manipDialog.addAction(UIAlertAction(title: "Add Values", style: .default, handler: addChannelDialog))
        manipDialog.addAction(UIAlertAction(title: "Keep Channel", style: .default, handler: keepDialog))
        manipDialog.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(manipDialog)
    }
    
    func promptResize() -> UIAlertController {
        let resizeDialog = UIAlertController(title: "Resize", message: "Enter the value you want to resize by.", preferredStyle: .alert)
        
        let resizeFactor = UIAlertAction(title: "Resize to Factor", style: .default) { (alertAction) in
            let factorField = resizeDialog.textFields![0] as UITextField
            
            if (factorField.text == "") {
                Input.resizeFactor = 1.0
            } else {
                Input.resizeFactor = Double(factorField.text!)!
            }
            
            self.controller.manipulateImage(manipulation: .resizeFactor)
        }
        
        let resizeExact = UIAlertAction(title: "Resize Dimensions", style: .default) { (alertAction) in
            let widthField = resizeDialog.textFields![1] as UITextField
            let heightField = resizeDialog.textFields![2] as UITextField
            
            if (widthField.text == "") {
                Input.resizeWidth = 500
            } else {
                Input.resizeWidth = Int(widthField.text!)!
            }
            
            if (heightField.text == "") {
                Input.resizeHeight = 500
            } else {
                Input.resizeHeight = Int(heightField.text!)!
            }
            
            self.controller.manipulateImage(manipulation: .resizeExact)
        }
        
        let makeSquare = UIAlertAction(title: "Square Image", style: .default) { (alertAction) in
            self.controller.manipulateImage(manipulation: .squareImage)
        }
        
        resizeDialog.addTextField { (textField) in
            textField.placeholder = "Resize Factor (default: 0.5)"
            textField.keyboardType = .decimalPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        resizeDialog.addTextField { (textField) in
            textField.placeholder = "Width (default 500)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        resizeDialog.addTextField { (textField) in
            textField.placeholder = "Height (default: 500)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        resizeDialog.addAction(resizeFactor)
        resizeDialog.addAction(resizeExact)
        resizeDialog.addAction(makeSquare)
        resizeDialog.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(resizeDialog)
    }
    
    func promptRotate() -> UIAlertController {
        let rotateDialog = UIAlertController(title: "Rotate", message: "Enter the number of degrees you want to rotate by.", preferredStyle: .alert)
        
        let rotate = UIAlertAction(title: "Rotate Image", style: .default) { (alertAction) in
            let factorField = rotateDialog.textFields![0] as UITextField
            
            if (factorField.text == "") {
                Input.degrees = 90
            } else {
                var degrees = Int(factorField.text!)!
                
                if (degrees > 360) {
                    degrees = (degrees % 360)
                }
                
                Input.degrees = degrees
            }
            
            self.controller.manipulateImage(manipulation: .rotate)
        }
        
        rotateDialog.addTextField { (textField) in
            textField.placeholder = "Rotate (Default 90)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        rotateDialog.addAction(rotate)
        rotateDialog.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(rotateDialog)
    }
    
    func promptAddChannel() -> UIAlertController {
        let addSettings = UIAlertController(title: "Add Channel Values", message: "Adds a number to each pixel in a color channel.", preferredStyle: .alert)
        
        let addRed = UIAlertAction(title: "Add Red", style: .default) { (alertAction) in
            let channelField = addSettings.textFields![0] as UITextField
            
            if (channelField.text == "" || !channelField.text!.checkInt()) {
                Input.addRedChannel = 0
            } else {
                Input.addRedChannel = Int(channelField.text!)!
            }
            
            self.controller.manipulateImage(manipulation: .addRed)
        }
        
        let addGreen = UIAlertAction(title: "Add Green", style: .default) { (alertAction) in
            let channelField = addSettings.textFields![1] as UITextField
            
            if (channelField.text == "" || !channelField.text!.checkInt()) {
                Input.addGreenChannel = 0
            } else {
                Input.addGreenChannel = Int(channelField.text!)!
            }
            
            self.controller.manipulateImage(manipulation: .addGreen)
        }
        
        let addBlue = UIAlertAction(title: "Add Blue", style: .default) { (alertAction) in
            let channelField = addSettings.textFields![2] as UITextField
            
            if (channelField.text == "" || !channelField.text!.checkInt()) {
                Input.addBlueChannel = 0
            } else {
                Input.addBlueChannel = Int(channelField.text!)!
            }
            
            self.controller.manipulateImage(manipulation: .addBlue)
        }
        
        let addAll = UIAlertAction(title: "Add All", style: .default) { (alertAction) in
            let redField = addSettings.textFields![0] as UITextField
            let greenField = addSettings.textFields![1] as UITextField
            let blueField = addSettings.textFields![2] as UITextField
            
            if (redField.text == "" || !redField.text!.checkInt()) {
                Input.addRedChannel = 0
            } else {
                Input.addRedChannel = Int(redField.text!)!
            }
            
            if (greenField.text == "" || !greenField.text!.checkInt()) {
                Input.addGreenChannel = 0
            } else {
                Input.addGreenChannel = Int(greenField.text!)!
            }
            
            if (blueField.text == "" || !blueField.text!.checkInt()) {
                Input.addBlueChannel = 0
            } else {
                Input.addBlueChannel = Int(blueField.text!)!
            }
            
            self.controller.manipulateImage(manipulation: .addRGB)
        }
        
        addSettings.addTextField { (textField) in
            textField.placeholder = "Red Channel (default: 0)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: true)
        }
        
        addSettings.addTextField { (textField) in
            textField.placeholder = "Green Channel (default: 0)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: true)
        }
        
        addSettings.addTextField { (textField) in
            textField.placeholder = "Blue Channel (default: 0)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: true)
        }
        
        addSettings.addAction(addRed)
        addSettings.addAction(addGreen)
        addSettings.addAction(addBlue)
        addSettings.addAction(addAll)
        addSettings.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(addSettings)
    }
    
    func promptChannelValues() -> UIAlertController {
        let channelSettings = UIAlertController(title: "Change Channel Values", message: "Set all pixels of a color channel to one value.", preferredStyle: .alert)
        
        let redChannel = UIAlertAction(title: "Change Red Channel", style: .default) { (alertAction) in
            let channelField = channelSettings.textFields![0] as UITextField
            
            if (channelField.text == "") {
                Input.redChannel = 0
            } else {
                let channelValue = Int(channelField.text!)!
                Input.redChannel = (channelValue >= 0 && channelValue <= 255) ? channelValue : 0
            }
            
            self.controller.manipulateImage(manipulation: .setRed)
        }
        
        let greenChannel = UIAlertAction(title: "Change Green Channel", style: .default) { (alertAction) in
            let channelField = channelSettings.textFields![1] as UITextField
            
            if (channelField.text == "") {
                Input.greenChannel = 0
            } else {
                let channelValue = Int(channelField.text!)!
                Input.greenChannel = (channelValue >= 0 && channelValue <= 255) ? channelValue : 0
            }
            
            self.controller.manipulateImage(manipulation: .setGreen)
        }
        
        let blueChannel = UIAlertAction(title: "Change Blue Channel", style: .default) { (alertAction) in
            let channelField = channelSettings.textFields![2] as UITextField
            
            if (channelField.text == "") {
                Input.blueChannel = 0
            } else {
                let channelValue = Int(channelField.text!)!
                Input.blueChannel = (channelValue >= 0 && channelValue <= 255) ? channelValue : 0
            }
            
            self.controller.manipulateImage(manipulation: .setBlue)
        }
        
        channelSettings.addTextField { (textField) in
            textField.placeholder = "Red Channel (default: 0)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        channelSettings.addTextField { (textField) in
            textField.placeholder = "Green Channel (default: 0)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        channelSettings.addTextField { (textField) in
            textField.placeholder = "Blue Channel (default: 0)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        channelSettings.addAction(redChannel)
        channelSettings.addAction(greenChannel)
        channelSettings.addAction(blueChannel)
        channelSettings.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(channelSettings)
    }
    
    func promptEncode() -> UIAlertController {
        let encodeSettings = UIAlertController(title: "Encode", message: "Enter which color channel you would like to encode. You will be prompted for an image once you select \"Encode Image\"", preferredStyle: .alert)
        
        let runEncode = UIAlertAction(title: "Encode Image", style: .default) { (alertAction) in
            let channelField = encodeSettings.textFields![0] as UITextField
            
            if (channelField.text == "") {
                Input.stegChannel = 0
            } else {
                Input.stegChannel = Int(channelField.text!)!
            }
            
            Input.promptEncode = true
            self.controller.choosePicture()
            
            self.controller.manipulateImage(manipulation: .encode)
        }
        
        encodeSettings.addTextField { (textField) in
            textField.placeholder = "Color Channel (default: 0)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        encodeSettings.addAction(runEncode)
        encodeSettings.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(encodeSettings)
    }
    
    func promptDecode() -> UIAlertController {
        let decodeSettings = UIAlertController(title: "Decode", message: "Enter which color channel you want to decode.", preferredStyle: .alert)
        
        let runDecode = UIAlertAction(title: "Decode Image", style: .default) { (alertAction) in
            let channelField = decodeSettings.textFields![0] as UITextField
            
            if (channelField.text == "") {
                Input.stegChannel = 0
            } else {
                Input.stegChannel = Int(channelField.text!)!
            }
            
            self.controller.manipulateImage(manipulation: .decode)
        }
        
        decodeSettings.addTextField { (textField) in
            textField.placeholder = "Color Channel (default: 0)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        decodeSettings.addAction(runDecode)
        decodeSettings.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(decodeSettings)
    }
    
    func promptGradMag() -> UIAlertController {
        let gradMagSettings = UIAlertController(title: "Gradient Magnitude Settings", message: "Editable values of gradient magnitude.", preferredStyle: .alert)
        
        let runGradMag = UIAlertAction(title: "Run Gradient Magnitude", style: .default) { (alertAction) in
            let kField = gradMagSettings.textFields![0] as UITextField
            let sigmaField = gradMagSettings.textFields![1] as UITextField
            
            if (kField.text == "") {
                Input.k = 2
            } else {
                Input.k = Int(kField.text!)!
            }
            
            if (sigmaField.text == "") {
                Input.sigma = 1.35
            } else {
                Input.sigma = Double(sigmaField.text!)!
            }
            
            self.controller.manipulateImage(manipulation: .gradMag)
        }
        
        gradMagSettings.addTextField { (textField) in
            textField.placeholder = "Kernel Size (default: 2)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        gradMagSettings.addTextField { (textField) in
            textField.placeholder = "Sigma (default: 1.35)"
            textField.keyboardType = .decimalPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        gradMagSettings.addAction(runGradMag)
        gradMagSettings.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(gradMagSettings)
    }
    
    func promptEdgeDetection() -> UIAlertController {
        let edgeSettings = UIAlertController(title: "Edge Detection Settings", message: "Editable values of edge detection.", preferredStyle: .alert)
        let runEdgeDetection = UIAlertAction(title: "Run Edge Detection", style: .default) { (alertAction) in
            let kField = edgeSettings.textFields![0] as UITextField
            let sigmaField = edgeSettings.textFields![1] as UITextField
            let threshField = edgeSettings.textFields![2] as UITextField
            
            if (kField.text == "") {
                Input.k = 2
            } else {
                Input.k = Int(kField.text!)!
            }
            
            if (sigmaField.text == "") {
                Input.sigma = 1.35
            } else {
                Input.sigma = Double(sigmaField.text!)!
            }
            
            if (threshField.text == "") {
                Input.min = 8
            } else {
                Input.min = Int(threshField.text!)!
            }
            
            self.controller.manipulateImage(manipulation: .edgeDetection)
        }
        
        edgeSettings.addTextField { (textField) in
            textField.placeholder = "Kernel Size (default: 2)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        edgeSettings.addTextField { (textField) in
            textField.placeholder = "Sigma (default: 1.35)"
            textField.keyboardType = .decimalPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        edgeSettings.addTextField { (textField) in
            textField.placeholder = "Lower Threshold (default: 8)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        edgeSettings.addAction(runEdgeDetection)
        edgeSettings.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(edgeSettings)
    }
    
    func promptPencilSketch() -> UIAlertController {
        let sketchSettings = UIAlertController(title: "Pencil Sketch Settings", message: "Editable values of pencil sketch.", preferredStyle: .alert)
        
        let sketch = UIAlertAction(title: "Run Pencil Sketch", style: .default, handler: {
            action in self.pencilSketchAlert(alert: action, alertController: sketchSettings)
            self.controller.manipulateImage(manipulation: .sketch)
        })
        
        let sketch2 = UIAlertAction(title: "Run Pencil Sketch 2", style: .default, handler: {
            action in self.pencilSketchAlert(alert: action, alertController: sketchSettings)
            self.controller.manipulateImage(manipulation: .sketch2)
        })
        
        sketchSettings.addTextField { (textField) in
            textField.placeholder = "Kernel Size (default: 5)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        sketchSettings.addTextField { (textField) in
            textField.placeholder = "Sigma (default: 1.35)"
            textField.keyboardType = .decimalPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        sketchSettings.addTextField { (textField) in
            textField.placeholder = "Lower Threshold (default: 18)"
            textField.keyboardType = .numberPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        sketchSettings.addTextField { (textField) in
            textField.placeholder = "Line Scale Length (default: 0.015)"
            textField.keyboardType = .decimalPad
            textField.addNumericAccessory(addPlusMinus: false)
        }
        
        sketchSettings.addAction(sketch)
        sketchSettings.addAction(sketch2)
        sketchSettings.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(sketchSettings)
    }
    
    func pencilSketchAlert(alert: UIAlertAction!, alertController: UIAlertController) {
        let kField = alertController.textFields![0] as UITextField
        let sigmaField = alertController.textFields![1] as UITextField
        let threshField = alertController.textFields![2] as UITextField
        let lineScaleField = alertController.textFields![3] as UITextField
        
        if (kField.text == "") {
            Input.k = 5
        } else {
            Input.k = Int(kField.text!)!
        }
        
        if (sigmaField.text == "") {
            Input.sigma = 1.35
        } else {
            Input.sigma = Double(sigmaField.text!)!
        }
        
        if (threshField.text == "") {
            Input.min = 18
        } else {
            Input.min = Int(threshField.text!)!
        }
        
        if (lineScaleField.text == "") {
            Input.lineScale = 0.015
        } else {
            Input.lineScale = Double(lineScaleField.text!)!
        }
    }
    
    func promptScript() -> UIAlertController {
        let scriptDialog = UIAlertController(title: "Script", message: "Run a script.", preferredStyle: .alert)
        
        let runScript = UIAlertAction(title: "Run Script", style: .default) { (alertAction) in
//            let scriptField = scriptDialog.textFields![0] as UITextField
//            
//            Script(controller: self.controller, script: scriptField.text!).runScript()
            self.controller.view.makeToast("Request denied.")
        }
        
        let viewCurrentScript = UIAlertAction(title: "View Script", style: .default) { (alertAction) in
            let trimmedScript = String(ViewController.script.prefix(ViewController.script.count - 1))
            
            self.controller.present(Prompt.prompt(title: "Current Script", message: trimmedScript), animated: true, completion: nil)
        }
        
        let copyScript = UIAlertAction(title: "Copy Script", style: .default) {
            (alertAction) in
            
            UIPasteboard.general.string = String(ViewController.script.prefix(ViewController.script.count - 1))
        }
        
        scriptDialog.addTextField { (textField) in
            textField.placeholder = "Script"
            textField.keyboardType = .default
        }
        
        scriptDialog.addAction(runScript)
        scriptDialog.addAction(viewCurrentScript)
        scriptDialog.addAction(copyScript)
        scriptDialog.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        return(scriptDialog)
    }
    
    static func prompt(title: String, message: String) -> UIAlertController {
        let dialog = UIAlertController(title: title, message: message, preferredStyle: .alert)
        dialog.addAction(UIAlertAction(title: "Close", style: .default, handler: nil))
        return(dialog)
    }
    
    func convertToGray(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .convertToGray)   }
    func flipVertical(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .flipVertical)   }
    func flipHorizontal(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .flipHorizontal)   }
    func negate(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .negate)   }
    func convertToBinary(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .convertToBinary)   }
    func scaleDown(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .resizeFactor)  }
    func mirrorVertical(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .mirrorVerticalTop)  }
    func mirrorHorizontal(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .mirrorHorizontalLeft)  }
    func mirrorVerticalBottom(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .mirrorVerticalBottom)  }
    func mirrorHorizontalRight(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .mirrorHorizontalRight)  }
    func swapRedGreen(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .swapRedGreen)  }
    func swapRedBlue(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .swapRedBlue)  }
    func swapGreenBlue(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .swapGreenBlue)  }
    func keepRed(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .keepRed)  }
    func keepGreen(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .keepGreen)  }
    func keepBlue(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .keepBlue)  }
    func flipDiagonal(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .flipDiagonal)   }
    func mirrorDiagonalLeft(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .mirrorDiagonalBRTL)   }
    func mirrorDiagonalRight(alert: UIAlertAction!) {  controller.manipulateImage(manipulation: .mirrorDiagonalTLBR)   }
    
    func flipDialog(alert: UIAlertAction!) {    controller.present(promptFlip(), animated: true, completion: nil)   }
    func keepDialog(alert: UIAlertAction!) {    controller.present(promptKeep(), animated: true, completion: nil)   }
    func swapDialog(alert: UIAlertAction!) {    controller.present(promptSwap(), animated: true, completion: nil)   }
    func mirrorDialog(alert: UIAlertAction!) {  controller.present(promptMirror(), animated: true, completion: nil)   }
    func rotateDialog(alert: UIAlertAction!) {    controller.present(promptRotate(), animated: true, completion: nil)   }
    func resizeDialog(alert: UIAlertAction!) {  controller.present(promptResize(), animated: true, completion: nil)   }
    func channelDialog(alert: UIAlertAction!) {     controller.present(promptChannelValues(), animated: true, completion: nil)}
    func addChannelDialog(alert: UIAlertAction!) {     controller.present(promptAddChannel(), animated: true, completion: nil)}
    func manipDialog(alert: UIAlertAction!) {     controller.present(promptChannelManipulation(), animated: true, completion: nil)}
    func gradMag(alert: UIAlertAction!) {
         controller.present(promptGradMag(), animated: true, completion: nil)
    }
    func edgeDetection(alert: UIAlertAction!) {     controller.present(promptEdgeDetection(), animated: true, completion: nil)
    }
    func steganography(alert: UIAlertAction!) {
        controller.present(promptSteganography(), animated: true, completion: nil)
    }
    func encodeDialog(alert: UIAlertAction!) {
//        Input.promptEncode = true
//        self.controller.choosePicture()
        
//        self.controller.manipulateImage(manipulation: .encode)
        controller.present(promptEncode(), animated: true, completion: nil)
    }
    func decodeDialog(alert: UIAlertAction!) {
//        self.controller.manipulateImage(manipulation: .decode)
        controller.present(promptDecode(), animated: true, completion: nil)
    }
}
