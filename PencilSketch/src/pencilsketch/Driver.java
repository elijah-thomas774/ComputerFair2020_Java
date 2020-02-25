package pencilsketch;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static pixlab.Helpers.*;
import static pixlab.PointProcesses.*;
import static pixlab.EdgeDetection.*;

import pixlab.*;

public class Driver {

	public static void main(String[] args) {
		File imagesDir = new File("images");
		String imagesPath = imagesDir.getAbsolutePath() + "\\";

		Picture picture;

		boolean filePick = true;
		String filechosen = "C:\\Users\\elija\\Desktop\\TeamC\\PixLab-elijah-thomas774\\myImages\\" + "spot4" + ".jpg";

		if(filePick) {
			filechosen = FileChooser.pickAFile();
			picture = new Picture(filechosen);
		}
		else {
			//name of image to test. WITHOUT file extension
			String name = "whiteflower";
			//Path of Image.
			String path = imagesPath;
			String imageAndPath = path + name + ".jpg";

			picture = new Picture("C:\\Users\\elija\\Desktop\\TeamC\\PixLab-elijah-thomas774\\myImages\\" + "spot4" + ".jpg");
		}

		// Feel free to mess with these. good default i found (5, 1.35, 18). can improve quality drastically //
		int k = 5; //border reflect and smoothing size. MAKES A DIFFERENCE
		double sDev = 1.35; //standard deviation for smoothing kernel. MAKES A DIFFERENCE
		int dThreshMin = 18; //dThesh uses a normalized gradMag.  usually ranges from 15 - 50 for good results
		double scaleLineLength = .013; //decimal percentage to scale length of sketch lines. .005 - .02

		Image image = new Image(picture, k, sDev, dThreshMin, scaleLineLength);
		Tone.genTone(image.filtered(), image.grayScale());

//		show(normalize(sketch));
//		show(normalize(tone));
//		show(normalize(combine));
//		BufferedImage im;
//		try {
//			 im = ImageIO.read(new File(filechosen));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			im = null;
//		}
//		long time = System.currentTimeMillis();
//		Sketch.sketch3(im,k,sDev, scaleLineLength, dThreshMin);
//		System.out.println(System.currentTimeMillis() - time + " NEW");
//		time = System.currentTimeMillis();
//		Image image = new Image(picture, k, sDev, dThreshMin, scaleLineLength);
//		explore(normalize(negate(image.sketch2())));
//		System.out.println(System.currentTimeMillis() - time + " OLD");
//		
		
//		int input = 0;
//		while (input != -1) {
//			input = Input.readInt("Enter dThresh Value: ");
//			if(input != image.getDThreshMin()) {
//				image.setDThreshMin(input);
//				image.setDThreshMax(3*input);
//			}
//			double iNput = Input.readDouble("Enter Line Length for scaling: ");
//			if(iNput != image.getLineScale())
//				image.setLineScale(iNput);
//			String woot = Input.readString("Display add(sketch, Image)?(Y/N): ");
//			if(woot.equalsIgnoreCase("Y"))
//				show(add(negate(grayToColor(image.sketch())), image.arrPic()));
//			time = System.currentTimeMillis();
//			explore(negate(normalize(image.sketch2());	
//			System.out.println(System.currentTimeMillis() - time);
//		}


	}

}
