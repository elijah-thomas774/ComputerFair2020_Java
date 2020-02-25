//package pixlab;
//
//import java.io.File;
//import java.util.Scanner;
//
//import static pixlab.Helpers.*;
//import static pixlab.PointProcesses.*;
//import static pixlab.EdgeDetection.*;
//
///**
// * Main driver for the PixLab project as modified by Tricia Flynn.
// * 
// * Overall changes include:
// * Added pixLab package & moved arrow icons into package
// * Added separate driver (this file) with starter code 
// * Modified the FileChooser class to open in project directory
// * Added myImages and newImages directories
// * -Eliminated simple example solutions
// * Eliminated main methods in instantiable classes
// * Modified write method to go to directory of choice
// * Added Helpers, PointProcesses, and EdgeDetection classes
// * Set up to allow for modification of image data structure
// * A bunch of other stuff I can't think of right now...
// * 
// * The following convention for image directories are used in this project:
// * images: stock images provided by Barb Ericson for testing purposes
// * myImages: your own images you will use for experimentation 
// * newImages: directory you will write your results to
// * 
// * @author Tricia Flynn
// * @author Eric Johns
// * 
// */
//public class PixLabDriver {
//	
//	/** Helpers */
//	private static Helpers h = new Helpers();
//	
//	/** PointProcesses */
//	private static PointProcesses pp = new PointProcesses();
//	
//	/** EdgeDetection */
//	private static EdgeDetection e = new EdgeDetection();
//	
//	/** Scanner for key press check */
//	private static Scanner keyboard = new Scanner(System.in);
//	
////----------------------------------------------------------------------------
//	/**
//	 * Main driver for PixLab project
//	 * 
//	 * @param args Command line input
//	 */
//	public static void main(String[] args) {
//		/*
//		 * This code creates strings for the absolute paths for the images,
//		 * myImages, and newImages directories to allow for hardcoding
//		 * of opening and writing images and other functionality.
//		 */
//		File myImagesDir = new File("myImages");
//		File imagesDir = new File("images");
//		File newImagesDir = new File("newImages");
//		String myImagesPath = myImagesDir.getAbsolutePath() + "\\";
//		String imagesPath = imagesDir.getAbsolutePath() + "\\";
//		String newImagesPath = newImagesDir.getAbsolutePath() + "\\";
//		
//		/*
//		 * Calls to example code method followed by calls to each phase
//		 * of the image processing assignment (helpers, point processes,
//		 * and edge detection). You can comment out what you are not
//		 * currently using.
//		 */
//		//examples(myImagesPath, imagesPath, newImagesPath);
//		//help(myImagesPath, imagesPath, newImagesPath);
//		//pointProcesses(myImagesPath, imagesPath, newImagesPath);
//		edgeDetection(myImagesPath, imagesPath, newImagesPath);
//		
//		/* 
//		 * Pressing Enter will cause proper shut down
//		 */
//		System.out.println("\n\nPress Enter to close PixLab...");
//		if(keyboard.hasNextLine()) {
//			System.exit(0);
//		}
//	}
//	
////----------------------------------------------------------------------------
//	
//	public static void help(String myImagesPath, 
//							 String imagesPath, 
//							 String newImagesPath) {
//		
//		//TODO Put your calls to test the helper methods here.
//
//		Picture cat = new Picture(imagesPath + "CumberlandIsland.jpg");
//		cat.show(); // show original picture
//		
//		int[][][] pixels = toArray(cat.getPixels2D());
//		show(pixels);	// toArray test
//		
//		Picture pixelPicture = toPicture(pixels);	// convert back to picture
//		pixelPicture.show(); // test images to be same
//		
//		double[][][] doubleArray = toDoubleArray(pixels);
//		int[][][] intArray = toIntArray(doubleArray);
//		show(toIntArray(doubleArray));
//		show(intArray);	// test toInt and toDouble arrays
//		
//		// I use array.clone() for copy methods and thats been tested in another class
//		
//		int[][] grayscale = colorToGray(pixels);
//		show(grayscale);	// test color to gray and show grayscale
//		explore(grayscale); // also explore it
//		
//		explore(pixels);	// test exploring a color array
//		show(pixels);		// test showing a color array
//		
//		write(pixels, newImagesPath + "test.jpg");	// test writing a file
//		write(grayscale, newImagesPath + "testGray.jpg");	// test writing grayscale
//		
//		int[][][] grayToColor = grayToColor(grayscale);
//		show(grayToColor);	// test grayToColor and show
//		
//		int[][][] scaled = scale(pixels, 2.5, 2.5);
//		show(scaled);
//		
//		int[][] scaledGray = scale(grayscale, 1.5, 1.5);
//		show(scaledGray);
//		
//		double[][] normalized = normalize(toDoubleArray(colorToGray(pixels)));
//		toPicture(grayToColor(toIntArray(normalized))).explore();
//		
//		double[][][] normalized2 = normalize(toDoubleArray(pixels));
//		toPicture(toIntArray(normalized2)).explore(); 	//test double normalized
//		
//		int[][] normalizedInt = normalize(colorToGray(pixels));	// test int normalized
//		toPicture(grayToColor(normalizedInt)).explore();
//		
//		toPicture(normalize(pixels)).explore(); 
//		
//		double[][][] test = {{{100, 120, 140}, {100, 120, 140}, {100, 120, 140}},
//				{{100, 120, 140}, {256, 275, 257}, {100, 120, 140}},
//				{{100, 120, 140}, {100, 120, 140}, {100, 120, 140}}};
//		Picture testPic = toPicture(scale(toIntArray(normalize(test)), 50, 50));
//		testPic.explore(); 
//		
//		System.out.println(distance(pixels[4][5], pixels[7][8]));	// test distance
//		
//	}
//	
////----------------------------------------------------------------------------
//	
//	/**
//	 * 
//	 * @param myImagesPath Path to myImages directory
//	 * @param imagesPath Path to images directory
//	 * @param newImagesPath Path to newly created images directory
//	 */
//	public static void pointProcesses(String myImagesPath, 
//			 String imagesPath, 
//			 String newImagesPath) {
//
//		//TODO Put your calls to test and run the Point Process methods here.
//
//		Picture cat = new Picture(imagesPath + "caterpillar.jpg");
//		Picture cat2 = new Picture(newImagesPath + "caterpillar2.jpg");
//		Picture message = new Picture(newImagesPath + "message.jpg");
//		Picture snowman = new Picture(imagesPath + "snowman.jpg");
//		Picture temple = new Picture(imagesPath + "temple.jpg");
//		
//		// colorToBinary ------------------------------------------------------
//		
//		Picture binary = toPicture(grayToColor(colorToBinary(toArray(temple.getPixels2D())))); 
//		binary.show();
//		binary.write(newImagesPath + "colorToBinary.png");
//		
//		// flipHorizontal -----------------------------------------------------
//	
//		Picture flipHorizontal = toPicture(flipHorizontal(toArray(cat.getPixels2D())));
//		flipHorizontal.show();
//		flipHorizontal.write(newImagesPath + "flipHorizontal.png");
//		flipHorizontal.write(newImagesPath + "flipHorizontal.png");
//		
//		// flipVertical -------------------------------------------------------
//		
//		Picture flipVertical = toPicture(flipVertical(toArray(cat.getPixels2D())));
//		flipVertical.show();
//		flipVertical.write(newImagesPath + "flipVertical.png");
//		
//		// flipDiagonal -------------------------------------------------------
//		
//		Picture flipDiagonal = toPicture(flipDiagonal(toArray(cat.getPixels2D())));
//		flipDiagonal.show();
//		flipDiagonal.write(newImagesPath + "flipDiagonal.png");
//		
//		// rotate90 -------------------------------------------------------
//		
//		Picture rotate90 = toPicture(rotate90(toArray(cat.getPixels2D())));
//		rotate90.show();
//		rotate90.write(newImagesPath + "rotate90.png");
//
//		// mirrorHorizontal -------------------------------------------------------
//		
//		Picture mirrorHorizontal = toPicture(mirrorHorizontal(toArray(cat.getPixels2D())));
//		mirrorHorizontal.show();
//		mirrorHorizontal.write(newImagesPath + "mirrorHorizontal.png");
//		
//		// mirrorVertical -------------------------------------------------------
//		
//		Picture mirrorVertical = toPicture(mirrorVertical(toArray(cat.getPixels2D())));
//		mirrorVertical.show();
//		mirrorVertical.write(newImagesPath + "mirrorVertical.png");
//		
//		// mirrorDiagonal -------------------------------------------------------
//		
//		Picture mirrorDiagonal = toPicture(mirrorDiagonal(toArray(cat.getPixels2D())));
//		mirrorDiagonal.show();
//		mirrorDiagonal.write(newImagesPath + "mirrorDiagonal.png");
//
//		// negate -------------------------------------------------------
//		
//		Picture negate = toPicture(negate(toArray(cat.getPixels2D())));
//		negate.show();
//		negate.write(newImagesPath + "negate.png");
//		
//		// zero -------------------------------------------------------
//
//		Picture zeroRed = toPicture(zero(toArray(cat.getPixels2D()), 0));	// red
//		zeroRed.show();
//		zeroRed.write(newImagesPath + "zeroRed.png");
//		
//		Picture zeroGreen = toPicture(zero(toArray(cat.getPixels2D()), 1));	// green
//		zeroGreen.show();
//		zeroGreen.write(newImagesPath + "zeroGreen.png");
//		
//		Picture zeroBlue = toPicture(zero(toArray(cat.getPixels2D()), 2));	// blue
//		zeroBlue.show();
//		zeroBlue.write(newImagesPath + "zeroBlue.png");
//		
//		// keep -------------------------------------------------------
//
//		Picture keepRed = toPicture(keep(toArray(cat.getPixels2D()), 0));	// red
//		keepRed.show();
//		keepRed.write(newImagesPath + "keepRed.png");
//
//		Picture keepGreen = toPicture(keep(toArray(cat.getPixels2D()), 1));	// green
//		keepGreen.show();
//		keepGreen.write(newImagesPath + "keepGreen.png");
//
//		Picture keepBlue = toPicture(keep(toArray(cat.getPixels2D()), 2));	// blue
//		keepBlue.show();
//		keepBlue.write(newImagesPath + "keepBlue.png");
//		
//		// swapColors -------------------------------------------------------
//
//		Picture swapRedGreen = toPicture(swapColors(toArray(cat.getPixels2D()), 0, 1));
//		swapRedGreen.show();
//		swapRedGreen.write(newImagesPath + "swapRedGreen.png");
//		
//		Picture swapRedBlue = toPicture(swapColors(toArray(cat.getPixels2D()), 0, 2));
//		swapRedBlue.show();
//		swapRedBlue.write(newImagesPath + "swapRedBlue.png");
//		
//		Picture swapGreenBlue = toPicture(swapColors(toArray(cat.getPixels2D()), 1, 2));
//		swapGreenBlue.show();
//		swapGreenBlue.write(newImagesPath + "swapGreenBlue.png");
//		
//		// subtract -------------------------------------------------------
//
//		Picture subtract = toPicture(subtract(toArray(cat2.getPixels2D()), toArray(cat.getPixels2D())));
//		subtract.show();
//		subtract.write(newImagesPath + "subtract.png");
//		
//		// add ------------------------------------------------------------
//		
//		Picture add = toPicture(add(toArray(cat2.getPixels2D()), toArray(cat.getPixels2D())));
//		add.show();
//		add.write(newImagesPath + "add.png");
//		
//		// blend ------------------------------------------------------------
//		
//		Picture blend = toPicture(blend(toArray(cat2.getPixels2D()), flipHorizontal(toArray(cat.getPixels2D())), 0.5));
//		blend.show();
//		blend.write(newImagesPath + "blend.png");
//		
//		// mirrorArms ------------------------------------------------------------
//
//		snowman.explore();
//		Picture mirrorArms = toPicture(mirrorArms(toArray(snowman.getPixels2D())));
//		mirrorArms.show();
//		mirrorArms.write(newImagesPath + "mirrorArms.png");
//
//		// fixTemple ------------------------------------------------------------
//		
//		Picture fixTemple = toPicture(fixTemple(toArray(temple.getPixels2D())));
//		fixTemple.show();
//		fixTemple.write(newImagesPath + "fixTemple.png");
//
//		// copyRange ------------------------------------------------------------
//		
//		Picture copyRange = toPicture(copyRange(toArray(cat.getPixels2D()), toArray(snowman.getPixels2D()), 21, 98, 0, 54, 170, 113));
//		copyRange.show();
//		copyRange.write(newImagesPath + "copyRange.png");
//
//		// createCollage ------------------------------------------------------------
//		
//		Picture createCollage = toPicture(createCollage());
//		createCollage.show();
//		createCollage.write(newImagesPath + "createCollage.png");
//
//		// encode ------------------------------------------------------------
//		
//		int[][] messageInt = colorToBinary(toArray(message.getPixels2D()));
//		Picture encode = toPicture(encode(toArray(cat.getPixels2D()), messageInt));
//		encode.show();
//		encode.write(newImagesPath + "encode.png");
//
//		// decode ------------------------------------------------------------
//		
//		Picture decode = toPicture(grayToColor(decode(toArray(encode.getPixels2D()))));
//		decode.show();
//		decode.write(newImagesPath + "decode.png");
//		
//		// chroma ------------------------------------------------------------
//
//		Picture fight = new Picture(imagesPath + "fight2.jpg");
//		Picture boxingRing = new Picture(imagesPath + "boxingRing.jpg");
//
//		Picture chroma = toPicture(chroma(toArray(fight.getPixels2D()), toArray(boxingRing.getPixels2D()), 125, 246, 167, 100));
//		chroma.show();
//		chroma.write(newImagesPath + "chroma.png");
//		
//		// -------------------------------------------------------------------
//		
//		/* // chroma fail with my image
//		Picture greenscreen = new Picture(imagesPath + "greenscreen.jpg");
//		Picture cliff = new Picture(imagesPath + "cliff.jpg");
//		Picture chromaFail1 = toPicture(chroma(flipHorizontal(toArray(greenscreen.getPixels2D())), toArray(cliff.getPixels2D()), 86, 182, 119));
//		chromaFail1.write(newImagesPath + "chromaFail1.png");
//		
//		//	chroma example with moon surface
//		Picture blue = new Picture(imagesPath + "blue-mark.jpg");
//		Picture moon = new Picture(imagesPath + "moon-surface.jpg");
//		Picture chromaFail2 = toPicture(chroma(toArray(blue.getPixels2D()), toArray(moon.getPixels2D()), 11, 34, 65));
//		chromaFail2.write(newImagesPath + "chromaFail2.png"); */
//	}
//	
////----------------------------------------------------------------------------
//	
//	/**
//	 * 
//	 * @param myImagesPath Path to myImages directory
//	 * @param imagesPath Path to images directory
//	 * @param newImagesPath Path to newly created images directory
//	 */
//	public static void edgeDetection(String myImagesPath, 
//			 String imagesPath, 
//			 String newImagesPath) {
////
////		String picture = "Mark";
////		Picture borderImage = new Picture(imagesPath + picture.toLowerCase() + ".jpg");
////		borderImage.show();
////		int[][][] borderPixels = toArray(borderImage.getPixels2D());
////		
//////		write(colorToGray(borderPixels), newImagesPath + "grayscale" + picture + ".png");
////		
////		int[][] borderReflect = createBorderReflect(colorToGray(borderPixels), 2);
//////		write(borderReflect, newImagesPath + "borderReflect" + picture + ".png");
////		show(borderReflect);
////		
////		double[][] kernel = createKernel(2, 1.3);
////		
////		double[][] filtered = filter(kernel, toDoubleArray(borderReflect));
//////		write(grayToColor(toIntArray(filtered)), newImagesPath + "filter" + picture + ".png");
////		show(toIntArray(filtered));
////			
////		double[][] calcX = calcGradientX(filtered);
//////		write(grayToColor(normalize(toIntArray(calcX))), newImagesPath + "calcGradientX" + picture + ".png");
////		show(normalize(toIntArray(calcX)));
////		
////		double[][] calcY = calcGradientY(filtered);
//////		write(grayToColor(normalize(toIntArray(calcY))), newImagesPath + "calcGradientY" + picture + ".png");
////		show(normalize(toIntArray(calcY)));
////		
////		double[][] gradMag = calcGradientMag(calcX, calcY);
//////		write(grayToColor(normalize(toIntArray(gradMag))), newImagesPath + "calcGradientMag" + picture + ".png");
////		show(normalize(toIntArray(gradMag)));
////		
////		double[][] gradAngle = calcGradientAngle(calcX, calcY);
//////		write(grayToColor(normalize(toIntArray(gradAngle))), newImagesPath + "calcGradientAngle" + picture + ".png");
////		show(normalize(toIntArray(gradAngle)));
////		
////		int[][] gradAngleAdj = adjustGradAngle(gradAngle);
//////		write(grayToColor(normalize(gradAngleAdj)), newImagesPath + "adjustGradAngle" + picture + ".png");
////		show(normalize(gradAngleAdj));
////		
////		double[][] calcNonMaxSupp = calcNonMaxSupp(gradMag, gradAngleAdj);
//////		write(grayToColor(normalize(toIntArray(calcNonMaxSupp))), newImagesPath + "calcNonMaxSupp" + picture + ".png");
////		show(normalize(toIntArray(calcNonMaxSupp)));
////
////		int[][] doubleThresh = doubleThresh(toDoubleArray(normalize(toIntArray(calcNonMaxSupp))), 8, 24);
//////		write(grayToColor(doubleThresh), newImagesPath + "doubleThresh" + picture + ".png");
////		show(doubleThresh);
////		
////		int[][] calcEdge = calcEdge(doubleThresh);
//////		write(grayToColor(calcEdge), newImagesPath + "calcEdge" + picture + ".png");
////		show(calcEdge);
////		
////		int[][][] finalImage = negate(grayToColor(calcEdge));
//////		write(finalImage, newImagesPath + "finalImage" + picture + ".png");
////		show(finalImage);
//	}
//	
////----------------------------------------------------------------------------
//	
//	/**
//	 * Examples for opening, displaying, and writing images.
//	 * 
//	 * @param myImagesPath Path to myImages directory
//	 * @param imagesPath Path to images directory
//	 * @param newImagesPath Path to newly created images directory
//	 */
//	public static void examples(String myImagesPath, 
//								String imagesPath, 
//								String newImagesPath) {
//				
//		/*
//		 * Example of instantiating a Picture using the above path names
//		 * combined with hardcoded file names and then using the explore()
//		 * and show() mehtods. You can comment out this code.
//		 */
//		Picture cat = new Picture(imagesPath + "caterpillar.jpg");
//		cat.explore();
//		cat.show();
//		    
//		/*
//		 * Example of instantiating a Picture using the FileChooser and
//		 * then using the explore() and show() methods. You can comment
//		 * out this code.
//		 */
//		Picture pic = new Picture(FileChooser.pickAFile());
//	    pic.explore();
//	    pic.show();
//	    
//	    /*
//	     * Example of writing a file to the newImages directory using a hardcoded 
//	     * new filename. Note that this just writes an unmodified image from above.
//	     * You can comment out this code. 
//	     */
//	    String filename = "newCat.jpg";
//	    if(cat.write(newImagesPath + filename)){
//	    	System.out.println("Successfully wrote " + filename);
//	    }
//	    else {
//	    	System.out.println("Could not write file " + filename);
//	    }
//
//	}
//	
////----------------------------------------------------------------------------
//}
