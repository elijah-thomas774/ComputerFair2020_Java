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
//		Sketch.sketch3(im,k,sDev, scaleLineLength, dThreshMin);
//		System.out.println(System.currentTimeMillis() - time + " NEW");
//		time = System.currentTimeMillis();
//		Image image = new Image(picture, k, sDev, dThreshMin, scaleLineLength);
//		explore(normalize(negate(image.sketch2())));
//		System.out.println(System.currentTimeMillis() - time + " OLD");
//		
//		
		//show(negate(image.sketch()));
		int input = 0;
		while (input != -1) {/*
			input = Input.readInt("Enter dThresh Value: ");
			if(input != image.getDThreshMin()) {
				image.setDThreshMin(input);
				image.setDThreshMax(3*input);
			}
			double iNput = Input.readDouble("Enter Line Length for scaling: ");
			if(iNput != image.getLineScale())
				image.setLineScale(iNput);*/
			//String woot = Input.readString("Display add(sketch, Image)?(Y/N): ");
			int[][] blendedSketch = addSketch(image.filtered(), (normalize(image.sketch2())));
			int[][][] blendedSketchColor = addSketch(image.arrPic(), (normalize(image.sketch2())));
			//blendedSketch = blend(image.grayScale(), negate(normalize(image.sketch2())), .45);
			explore((blendedSketch));
			explore(blendedSketchColor);
			explore(image.arrPic());
			//if(woot.equalsIgnoreCase("Y"))
				//explore(add(negate(grayToColor(image.sketch())), image.arrPic()));
			time = System.currentTimeMillis();
			explore(negate(normalize(image.sketch2())));	
			System.out.println(System.currentTimeMillis() - time);
			input = Input.readInt("");
		}
		
	}

	public static int[][] addSketch(double[][] gray, int[][] sketch){
		int[][] newImage = new int[sketch.length][sketch[0].length];
		int[][] grayCopy = copy(toIntArray(gray));
		grayCopy = advNorm(negate(grayCopy), 8, 255);
		int[][] sketchCopy = copy(sketch);	
		advNorm(sketchCopy, 0, 7);
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
					newImage[i][j] =  grayCopy[i][j]-sketch[i][j];
			}
		} 
		return newImage;
	}
	public static int[][][] addSketch(int[][][] image, int[][] sketch){
		int[][][] newImage = new int[sketch.length][sketch[0].length][3];
		int[][][] imageCopy = changeColorOrder(image);
//		imageCopy[0] = advNorm(imageCopy[0], 8, 255);
//		imageCopy[1] = advNorm(imageCopy[0], 8, 255);
//		imageCopy[2] = advNorm(imageCopy[0], 8, 255);
		int[][] sketchCopy = copy(sketch);	
		advNorm(sketchCopy, 0, 7);
		for(int i = 0; i < newImage.length; i++) {
			for(int j = 0; j < newImage[0].length; j++) {
				for(int k = 0; k < 3; k++)
					newImage[i][j][k] =  imageCopy[k][i][j]-sketch[i][j];
			}
		} 
		return newImage;
	}
	public static int[][][] changeColorOrder(int[][][] arr){
		int[][][] image = new int[3][arr.length][arr[0].length];
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				for(int k = 0; k < 3; k++) {
					image[k][i][j] = arr[i][j][k];
				}
			}
		}
		return image;
	}
	public static int[][] advNorm(int[][] arr, int maxNew, int minNew){
		int min = ArrayMath.min(arr);
		int max = ArrayMath.max(arr);
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				arr[i][j] = minNew + (arr[i][j] - min) * (maxNew - minNew)/(max-min);
		return arr;
	}

}
