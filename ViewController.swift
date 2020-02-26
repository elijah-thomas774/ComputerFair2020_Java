//
//  ViewController.swift
//  PencilSketch
//
//  Created by Eric Johns on 8/28/19.
//  Copyright © 2019 Eric Johns. All rights reserved.
//

import UIKit
import SwiftImage

class ViewController: UIViewController, UINavigationControllerDelegate, UIImagePickerControllerDelegate, UIScrollViewDelegate {
    @IBOutlet weak var scrollView: UIScrollView!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    
    static var pictureID: Int = 0
    static let picture: Picture = Picture()
    
    static var logNumber: Int = 0
    static var log: String = ""
    
    static var script: String = ""
    
    static var pictureGallery = false
    
    static var originalImage: UIImage = UIImage(named: "townhall")!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
        self.scrollView.minimumZoomScale = 1.0
        self.scrollView.maximumZoomScale = 20.0
        
        imageView.image = ViewController.picture.getUIImage()
        ViewController.originalImage = ViewController.picture.getUIImage()
        
        Input.k = UserDefaults.standard.integer(forKey: "k")
        Input.sigma = UserDefaults.standard.double(forKey: "sigma")
        Input.min = UserDefaults.standard.integer(forKey: "thresh")
        Input.lineScale = UserDefaults.standard.double(forKey: "lineScale")
    
        NotificationCenter.default.addObserver(self, selector: #selector(changePicture), name: NSNotification.Name(rawValue: "changePicture"), object: nil)
        
        let longPress = UILongPressGestureRecognizer(target: self, action: #selector(resetImage))
        imageView.addGestureRecognizer(longPress)
        
        let imageTap = UITapGestureRecognizer(target: self, action: #selector(showLog))
        imageView.addGestureRecognizer(imageTap)
        
        let scriptGesture = UITapGestureRecognizer(target: self, action: #selector(presentScript))
        scriptGesture.numberOfTapsRequired = 3
//        titleLabel.addGestureRecognizer(scriptGesture)    add back later
    }
    
    @objc func changePicture() {
        if (ViewController.pictureID < 17) {
            ViewController.picture.setPicture(name: PictureController.pictures[ViewController.pictureID])
            imageView.image = ViewController.picture.getUIImage()
            ViewController.originalImage = ViewController.picture.getUIImage()
        } else if (ViewController.pictureID == 17) {
            choosePicture()
            ViewController.pictureGallery = false
        } else {
            takePicture()
            ViewController.pictureGallery = true
        }
        
        clearLog()
    }
    
    @objc func resetImage() {
        ViewController.picture.setPicture(image: ViewController.originalImage)
        imageView.image = ViewController.originalImage
        clearLog()
    }
    
    @objc func showLog() {
        var newScript: String = ""
        
        if (ViewController.script.count > 0) {
            newScript = String(ViewController.script.prefix(ViewController.script.count - 1))
        }
        
        self.present(Prompt.prompt(title: "Transformation Log", message: "\(ViewController.log)\n\n\(newScript)"), animated: true, completion: nil)
    }
    
    @objc func presentScript() {
        self.present(Prompt(controller: self).promptScript(), animated: true, completion: nil)
    }
    
    @IBAction func saveImage(_ sender: Any) {
        let saveDialog = UIAlertController(title: "Save image", message: "Select the format you would like to save to.", preferredStyle: .alert)
        
        let png = UIAlertAction(title: "Save as PNG", style: .default, handler: { (action) in
            let data = ViewController.picture.getUIImage().pngData()!
            let pngImage = UIImage(data: data)
            UIImageWriteToSavedPhotosAlbum(pngImage!, nil, nil, nil)
            self.view.makeToast("Successfully saved as PNG")
        })
        
        let jpg = UIAlertAction(title: "Save as JPG", style: .default, handler: { (action) in
            let data = ViewController.picture.getUIImage().jpegData(compressionQuality: 1.0)!
            let jpegImage = UIImage(data: data)
            UIImageWriteToSavedPhotosAlbum(jpegImage!, nil, nil, nil)
            self.view.makeToast("Successfully saved as JPG")
        })
        
        saveDialog.addAction(png)
        saveDialog.addAction(jpg)
        saveDialog.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        self.present(saveDialog, animated: true, completion: nil)
    }
    
    @IBAction func pencilSketch(_ sender: Any) {
        self.present(Prompt(controller: self).promptPencilSketch(), animated: true, completion: nil)
//        let startTime = DispatchTime.now().uptimeNanoseconds
//        ViewController.picture.flipVertical()
//        self.imageView.image = ViewController.picture.getUIImage()
//        let timeTaken = DispatchTime.now().uptimeNanoseconds - startTime
//        ViewController.log += " (\(timeTaken / 1000000)ms) \n"
        
    }
    
    @IBAction func testClick(_ sender: Any) {
        self.present(Prompt(controller: self).promptFeatures(), animated: true, completion: nil)
    }
    
    func manipulateImage(manipulation: Manipulation) {
        let startTime = DispatchTime.now().uptimeNanoseconds
        
        ViewController.logNumber += 1
        
        DispatchQueue.global(qos: .background).async {
            ViewController.picture.manipulateImage(manipulation: manipulation)
            
            DispatchQueue.main.async {
                self.imageView.image = ViewController.picture.getUIImage()
                let timeTaken = DispatchTime.now().uptimeNanoseconds - startTime
                ViewController.log += " (\(timeTaken / 1000000)ms) \n"
            }
        }
    }
    
    func choosePicture() {
        let imagePicker = UIImagePickerController()
        imagePicker.delegate = self
        imagePicker.sourceType = .savedPhotosAlbum
        imagePicker.allowsEditing = false
        
        self.present(imagePicker, animated: true, completion: nil)
    }
    
    func takePicture() {
        if (UIImagePickerController.isSourceTypeAvailable(UIImagePickerController.SourceType.camera)) {

            let imagePicker = UIImagePickerController()
            imagePicker.delegate = self
            imagePicker.sourceType = UIImagePickerController.SourceType.camera
            imagePicker.cameraDevice = .rear
            imagePicker.allowsEditing = false
            self.present(imagePicker, animated: true, completion: nil)
        }
    }
    
    func clearLog() {
        ViewController.logNumber = 0
        ViewController.log = ""
        ViewController.script = ""
    }
    
    func timeFunction(startTime: UInt64) {
        let timeTaken = DispatchTime.now().uptimeNanoseconds - startTime
        ViewController.log += " (\(timeTaken / 1000000)ms) \n"
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        picker.dismiss(animated: true, completion: nil)
        let image = (info[UIImagePickerController.InfoKey.originalImage] as! UIImage).fixedOrientation()
        
        if (!Input.promptEncode) {
            ViewController.picture.setPicture(image: ViewController.pictureGallery ? image.resizeCamera()! : image)
            imageView.image = ViewController.picture.getUIImage()
            ViewController.originalImage = ViewController.picture.getUIImage()
        } else {
            Input.encodeImage = Image<RGBA<UInt8>>(uiImage: image)
            imageView.image = image
            Input.promptEncode = false
        }
    }
    
    func viewForZooming(in scrollView: UIScrollView) -> UIView? {
        return self.imageView
    }
}
