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
		double scaleLineLength = .016; //decimal percentage to scale length of sketch lines. .005 - .02

		Image image = new Image(picture, k, sDev, dThreshMin, scaleLineLength);
		//Tone.genTone(image.filtered(), image.grayScale());
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
		long time = System.currentTimeMillis();

		int input = 0;
		while (input != -1) {
			
			//blendedSketch with blurred gray
			time = System.currentTimeMillis();
			int[][] blendedSketchBlurred = addSketch(image.filtered(), (normalize(image.sketch2())));
			System.out.println(System.currentTimeMillis() - time);
			explore(blendedSketchBlurred);
			
			//blended sketch with color (no brightness change
			time = System.currentTimeMillis();
			int[][][] blendedSketchColor = addSketch(image.arrPic(), (normalize(image.sketch2())));
			System.out.println(System.currentTimeMillis() - time);			time = System.currentTimeMillis();
			explore(blendedSketchColor);
			
			//blended sketch with color with brightness change
			time = System.currentTimeMillis();
			int[][][] blendedSketchColorB = addSketch(brightnessChange(image.arrPic(), 1.35), (normalize(image.sketch2())));
			System.out.println(System.currentTimeMillis() - time);
			explore(blendedSketchColorB);
			
			
			time = System.currentTimeMillis();
			int[][] blendedSketch = addSketch(image.grayScale(), (normalize(image.sketch2())));
			System.out.println(System.currentTimeMillis() - time);
			explore((blendedSketch));
			
	

			explore(image.arrPic());

			explore(negate(normalize(image.sketch2())));	
			System.out.println(System.currentTimeMillis() - time);
			input = Input.readInt("");
		}

	}
	public static int[][][] brightnessControl(int[][][] image){
		double[][][] yuv = toYUV(image);
		for(int i = 0; i < yuv.length; i++)
			for(int j = 0; j < yuv[0].length; j++) 
				yuv[i][j][0] *= 1.35;
		int[][][] controlled = toRGB(yuv);
		return controlled;
	}


	public static int[][] addSketch(int[][] gray, int[][] sketch){
		int[][] newImage = new int[sketch.length][sketch[0].length];
		int[][] grayCopy = copy((gray));
		grayCopy = advNorm(negate(grayCopy), 8, 255);
		int[][] sketchCopy = normalize(copy(sketch));	
		//advNorm(sketchCopy, 0, 7);
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				newImage[i][j] =  grayCopy[i][j]-sketch[i][j];
				if(newImage[i][j] < 0) newImage[i][j] = 0;
			}
		} 
		return newImage;
	}
	public static int[][] addSketch(double[][] gray, int[][] sketch){
		int[][] newImage = new int[sketch.length][sketch[0].length];
		int[][] grayCopy = copy(toIntArray(gray));
		grayCopy = advNorm(negate(grayCopy), 8, 255);
		int[][] sketchCopy = normalize(copy(sketch));	
		advNorm(sketchCopy, 0, 7);
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				newImage[i][j] =  grayCopy[i][j]-sketch[i][j];
				if(newImage[i][j] < 0) newImage[i][j] = 0;
			}
		} 
		return newImage;
	}
	public static int[][][] addSketch(int[][][] image, int[][] sketch){
		int[][][] newImage = new int[sketch.length][sketch[0].length][3];
		//int[][][] imageCopy = changeColorOrder(image);

		int[][] sketchCopy = normalize(copy(sketch));	
		advNorm(sketchCopy, 0, 7);
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				for(int k = 0; k < 3; k++) {
					newImage[i][j][k] =  image[i][j][k]-sketch[i][j];
					if(newImage[i][j][k] < 0) newImage[i][j][k] = 0;
				}
			}
		} 
		return newImage;
	}
	public static int[][] advNorm(int[][] arr, int maxNew, int minNew){
		int min = ArrayMath.min(arr);
		int max = ArrayMath.max(arr);
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				arr[i][j] = minNew + (arr[i][j] - min) * (maxNew - minNew)/(max-min);
		return arr;
	}
	
	
	//changes as of 3/5/20 period 5
	//also changed in each add sketch a check for under 0
	public static int[][][] brightnessChange(int[][][] rgb, double multiplier){
		int[][][] newArr = new int[rgb.length][rgb[0].length][3];
		double y, u ,v , r, g ,b;
		for (int i = 0; i < rgb.length; i++) {
			for (int j = 0; j < rgb[0].length; j++) {
				r = rgb[i][j][0]/255.;
				g = rgb[i][j][1]/255.;
				b = rgb[i][j][2]/255.;
				y = (.299*r + .587*g + .114*b) * multiplier;
				u = -.14713*r-.28886*g+.436*b;
				v = .615*r-.51499*g-.10001*b;;
				newArr[i][j][0] =(int) ((y + 1.14*v) * 255);
				newArr[i][j][1] =(int) ((y - .396*u - .581*v)*255);
				newArr[i][j][2] =(int) ((y + 2.033*u)*255); 
			}
		}
		return newArr;
	}
}
