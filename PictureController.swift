//
//  PictureController.swift
//  PencilSketch
//
//  Created by Eric Johns on 9/4/19.
//  Copyright © 2019 Eric Johns. All rights reserved.
//

import UIKit

class PictureCell: UITableViewCell {
    @IBOutlet weak var pictureName: UILabel!
    @IBOutlet weak var picture: UIImageView!
}

class PictureController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet weak var tableView: UITableView!
    
    static let pictureNames = ["Town Hall", "Bus", "Tree", "Bouquet", "Space", "Gorge", "Island", "Kayak", "Eric", "Flower", "Mark", "Nature 1", "Nature 2", "Nature 3", "Nature 4", "Nature 5", "City", "Select Picture", "Take Picture"]
    static let pictures = ["townhall", "bus", "tree", "bouquet", "space", "gorge", "island", "kayak", "eric", "flower", "mark", "nature1", "nature2", "nature3", "nature4", "nature5", "city", "gallery", "camera"]
    
    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.selectRow(at: IndexPath(row: ViewController.pictureID, section: 0), animated: true, scrollPosition: .middle)
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return(PictureController.pictures.count)
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell") as! PictureCell
        cell.pictureName.numberOfLines = 2
        cell.pictureName.lineBreakMode = .byWordWrapping
        cell.pictureName.text = PictureController.pictureNames[indexPath.row]
        cell.picture.image = UIImage(named: "\(PictureController.pictures[indexPath.row])_icon")
        
        return(cell)
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        ViewController.pictureID = indexPath.row
        
        NotificationCenter.default.post(name: NSNotification.Name(rawValue: "changePicture"), object: nil)
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return CGFloat(150)
    }
}
