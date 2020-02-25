
package pixlab;


/**
 * Helper methods used to convert the original PixLab project image
 * formats to a 3D format using RGB layers and to convert back
 * and forth between these formats to allow for use of existing
 * display, write, and other methods from the original PixLab project.
 * 
 * @author Elijah Thomas
 *
 */
public class Helpers {

	/**
	 * The toArray method converts a Pixel array from a Picture object and converts it to an 
	 * integer array with 3 dimensions.
	 * @param pixels
	 * @return
	 */
	public static int[][][] toArray(Pixel[][] pixels){
		int[][][] array = new int[pixels.length][pixels[0].length][3];

		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				array[i][j][0] = pixels[i][j].getRed();
				array[i][j][1] = pixels[i][j].getGreen();
				array[i][j][2] = pixels[i][j].getBlue();

			}
		}

		return array; 
	}
	//-------------------------------------------------------------------
	/**
	 * the toPicture method converts a color image into a 
	 *  picture object
	 * @param img the color image
	 * @return img as a Picture
	 */
	public static Picture toPicture(int[][][] img) {
		Picture pic = new Picture(img.length, img[0].length);
		Pixel[][] pix = pic.getPixels2D();
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				pix[i][j].setRed(img[i][j][0]);	
				pix[i][j].setGreen(img[i][j][1]);
				pix[i][j].setBlue(img[i][j][2]);
			}
		}
		return pic;
	}
	//-------------------------------------------------------------------
	/**
	 * the toIntArray converts a double array to 
	 * an integer array of the same size, truncating
	 * @param dA the double array
	 * @return the converted integer array
	 */
	public static int[][][] toIntArray(double[][][] dA){
		int[][][] iA = new int[dA.length][dA[0].length][dA[0][0].length];
		for (int i = 0; i < iA.length; i++) {
			for (int j = 0; j < iA[0].length; j++) {
				for (int u = 0; u < iA[0][0].length; u++) {
					iA[i][j][u] = (int) dA[i][j][u];
				}
			}
		}

		return  iA;
	}
	//-------------------------------------------------------------------
	/**
	 * the toIntArray converts a double array to 
	 * an integer array of the same size, truncating
	 * @param dA the double array
	 * @return the converted integer array
	 */
	public static int[][] toIntArray(double[][] dA){
		int[][] iA = new int[dA.length][dA[0].length];
		for (int i = 0; i < iA.length; i++) {
			for (int j = 0; j < iA[0].length; j++) {
					iA[i][j] = (int) dA[i][j];
			}
		}

		return  iA;
	}
	//-------------------------------------------------------------------
	/**
	 * the toDoubleArray converts an integer array to 
	 * a double array of the same size
	 * @param iA the integer array
	 * @return the converted double array
	 */
	public static double[][][] toDoubleArray(int[][][] iA){
		double[][][] dA = new double[iA.length][iA[0].length][iA[0][0].length];
		for (int i = 0; i < dA.length; i++) {
			for (int j = 0; j < dA[0].length; j++) {
				for (int u = 0; u < iA[0][0].length; u++) {
					dA[i][j][u] = (double) iA[i][j][u];
				}
			}
		}

		return dA;
	}
	//-------------------------------------------------------------------
	/**
	 * the toDoubleArray converts an integer array to 
	 * a double array of the same size
	 * @param iA the integer array
	 * @return the converted double array
	 */
	public static double[][] toDoubleArray(int[][] iA){
		double[][] dA = new double[iA.length][iA[0].length];
		for (int i = 0; i < dA.length; i++) {
			for (int j = 0; j < dA[0].length; j++) {
					dA[i][j] = (double) iA[i][j];
			}
		}

		return dA;
	}
	//-------------------------------------------------------------------
	/**
	 * this overloaded copy method copies a 2D integer array representing 
	 * a gray-scale image
	 * @param toCopy array to be copied
	 * @return the copied array
	 */
	public static int[][] copy(int[][] toCopy){
		int[][] copy = new int[toCopy.length][toCopy[0].length];
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy[0].length; j++) {
				copy[i][j] = toCopy[i][j];
			}
		}
		return copy;
	}
	//------------------------------------------------------------------
	/**
	 * this overload copy method copies a 3D integer array representing a 
	 * color image
	 * @param toCopy array to be copied
	 * @return the copied array
	 */
	public static int[][][] copy(int[][][] toCopy){
		int[][][] copy = new int[toCopy.length][toCopy[0].length][3];
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy[0].length; j++) {
				for (int color = 0; color < 3; color++) {
					copy[i][j][color] = toCopy[i][j][color];
				}
			}
		}
		return copy;
	}
	//-------------------------------------------------------------------
	/**
	 * this overloaded copy method copies a 2D double array representing 
	 * a gray-scale image
	 * @param toCopy array to be copied
	 * @return the copied array
	 */
	public static double[][] copy(double[][] toCopy){
		double[][] copy = new double[toCopy.length][toCopy[0].length];
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy[0].length; j++) {
				copy[i][j] = toCopy[i][j];
			}
		}
		return copy;
	}
	//------------------------------------------------------------------
	/**
	 * this overload copy method copies a 3D double array representing a 
	 * color image
	 * @param toCopy array to be copied
	 * @return the copied array
	 */
	public static double[][][] copy(double[][][] toCopy){
		double[][][] copy = new double[toCopy.length][toCopy[0].length][3];
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy[0].length; j++) {
				for (int color = 0; color < 3; color++) {
					copy[i][j][color] = toCopy[i][j][color];
				}
			}
		}
		return copy;
	}
	//-------------------------------------------------------------------
	/**
	 *  this method acts as a wrapper for the original explore method, but uses 
	 *  a 2D array as the image to explore
	 * @param img image to explore
	 */
	public static void explore(int[][] img) {
		toPicture(grayToColor(img)).explore();
	}
	//-------------------------------------------------------------------
	/**
	 *  this method acts as a wrapper for the original explore method, but uses 
	 *  a 3D array as the image to explore
	 * @param img image to explore
	 */
	public static void explore(int[][][] img) {
		toPicture(img).explore();
	}
	//-------------------------------------------------------------------
	/**
	 * the show method displays the gray-scale image
	 * @param img image to display
	 */
	public static void show(int[][] img) {
		toPicture(grayToColor(img)).show();
	}
	//-------------------------------------------------------------------
	/**
	 * the show method displays the color image
	 * @param img image to display
	 */
	public static void show(double[][][] img) {
		toPicture(toIntArray(img)).show();
	}
	//-------------------------------------------------------------------
	/**
	 * the show method displays the gray-scale image
	 * @param img image to display
	 */
	public static void show(double[][] img) {
		toPicture(grayToColor(toIntArray(img))).show();
	}
	//-------------------------------------------------------------------
	/**
	 * the show method displays the color image
	 * @param img image to display
	 */
	public static void show(int[][][] img) {
		toPicture(img).show();
	}
	//-------------------------------------------------------------------
	/**
	 * the write method takes a gray-scale image and writes 
	 * it to a file.
	 * @param img the image to write to the file
	 * @param name the name of the file
	 */
	public static void write(int[][] img, String name) {
		toPicture(grayToColor(img)).write(name);
	}
	//-------------------------------------------------------------------
	/**
	 * the write method takes a color image and writes it 
	 * to a file.
	 * @param img the image to write to a file
	 * @param name the name of the file
	 */
	public static void write(int[][][] img, String name) {
		toPicture(img).write(name);
	}
	//-------------------------------------------------------------------
	/**
	 * the scale method scales a gray-scale image
	 * @param img image to be scaled
	 * @param x the scale to apply to the width
	 * @param y the scale to apply to the height
	 * @return the scaled image
	 */
	public static int[][] scale(int[][] img, double x, double y) {
		return colorToGray(toArray(toPicture(grayToColor(img)).scale(x, y).getPixels2D())); 
	}
	//-------------------------------------------------------------------
	/**
	 * the scale method scales a color image
	 * @param img image to be scaled
	 * @param x the scale to apply to the width
	 * @param y the scale to apply to the height
	 * @return the scaled image
	 */
	public static int[][][] scale(int[][][] img, double x, double y) {
		return toArray(toPicture(img).scale(x, y).getPixels2D());
	}
	//-------------------------------------------------------------------
	/**
	 *  the colorToGray converts a color image to a gray scale image
	 * @param imgC the color image
	 * @return imgC, but gray-scale
	 */
	public static int[][] colorToGray(int[][][] imgC) {
		int[][] img = new int[imgC.length][imgC[0].length];
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				img[i][j] = (int) ((imgC[i][j][0] * 0.21)+(imgC[i][j][1] * 0.72)+(imgC[i][j][2] * 0.07));
			}
		}
		return img;
	}
	//------------------------------------------------------------------
	/**
	 * the grayToColor method converts a gray-scale image to 
	 * a color representation
	 * @param imgG the gray-scale to convert
	 * @return imgG, but color
	 */
	public static int[][][] grayToColor(int[][] imgG){
		int[][][] img = new int[imgG.length][imgG[0].length][3];
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				for (int RGB = 0; RGB < 3; RGB++) {
					img[i][j][RGB] = imgG[i][j];
				}
			}
		}
		return img;
	}
	//------------------------------------------------------------------
	/**
	 * the normalize method takes a gray-scale image (double[][]) and normalizes 
	 * it
	 * @param input the image to normalize
	 * @return the normalized image
	 */
	public static double[][] normalize(double[][] input){
		double[][] normalized = new double[input.length][input[0].length];
		double max = input[0][0],
				min = input[0][0];
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				if (max < input[i][j])
					max = input[i][j];
				if (min > input[i][j]) 
					min = input[i][j];
			}
		}
		for (int i =0; i < normalized.length; i++) {
			for (int j = 0; j < normalized[0].length; j++) {
				normalized[i][j] = (255.0/(max-min)) * (input[i][j] - min);
			}
		}
		return normalized;

	}
	//------------------------------------------------------------------
	/**
	 * the normalize method takes a color image (double[][][]) and normalizes 
	 * it
	 * @param input the image to normalize
	 * @return the normalized image
	 */
	public static double[][][] normalize(double[][][] input){
		double[][][] normalized = new double[input.length][input[0].length][3];
		double[] red = findMaxMin(input, 0);
		double[] green = findMaxMin(input, 1);
		double[] blue = findMaxMin(input, 2);
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				normalized[i][j][0] = ((255.0/red[0]) * (input[i][j][0] - red[1]));
				normalized[i][j][1] = ((255.0/green[0]) * (input[i][j][1] - green[1]));
				normalized[i][j][2] = ((255.0/blue[0]) * (input[i][j][2] - blue[1]));
			}
		}
		return normalized;
	}
	private static double[] findMaxMin(double[][][] img, int color) {
		double max = img[0][0][color];
		double min = img[0][0][color];

		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
					if (max < img[i][j][color])
						max = img[i][j][color];
					if (min > img[i][j][color]) 
						min = img[i][j][color];
			}
		}
		double[] maxMin = {max - min , (min)};
		
		return maxMin;
	}
	//------------------------------------------------------------------
	/**
	 * the normalize method takes a gray-scale image (int[][]) and normalizes 
	 * it
	 * @param input the image to normalize
	 * @return the normalized image
	 */
	public static int[][] normalize(int[][] input){
		int[][] normalized = new int[input.length][input[0].length];
		int max = input[0][0],
				min = input[0][0];
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				if (max < input[i][j])
					max = input[i][j];
				if (min > input[i][j]) 
					min = input[i][j];
			}
		}
		for (int i =0; i < normalized.length; i++) {
			for (int j = 0; j < normalized[0].length; j++) {
				normalized[i][j] = (int) ((255.0/(max - min)) * (input[i][j] - min));
			}
		}
		return normalized;

	}
	//------------------------------------------------------------------
	/**
	 * the normalize method takes a color image (int[][][]) and normalizes 
	 * it
	 * @param input the image to normalize
	 * @return the normalized image
	 */
	public static int[][][] normalize(int[][][] input){
		int[][][] normalized = new int[input.length][input[0].length][3];
		int[] red = findMaxMin(input, 0);
		int[] green = findMaxMin(input, 1);
		int[] blue = findMaxMin(input, 2);
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				normalized[i][j][0] = (int) ((255.0/red[0]) * (input[i][j][0] - red[1]));
				normalized[i][j][1] = (int) ((255.0/green[0]) * (input[i][j][1] - green[1]));
				normalized[i][j][2] = (int) ((255.0/blue[0]) * (input[i][j][2] - blue[1]));
			}
		}

		return normalized;
	}
	private static int[] findMaxMin(int[][][] img, int color) {
		int max = img[0][0][color];
		int min = img[0][0][color];

		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
					if (max < img[i][j][color])
						max = img[i][j][color];
					if (min > img[i][j][color]) 
						min = img[i][j][color];
				
			}
		}
		int[] maxMin = {max - min , (min)};
		return maxMin;
	}
	//------------------------------------------------------------------
	/**
	 * the distance calculates the color distance between two color
	 * vectors
	 * @param colorVector1 first color vector
	 * @param colorVector2 second color vector
	 * @return the color distance
	 */
	public static double distance(int[] colorVector1, int[] colorVector2) {
		return Math.sqrt(Math.pow(colorVector1[0] - colorVector2[0], 2) + Math.pow(colorVector1[1] - colorVector2[1], 2) + Math.pow(colorVector1[2] - colorVector2[2], 2));
	}
	
	public static double[][][] toYUV(int[][][] rgb){ 
		double[][][] yuv = new double[rgb.length][rgb[0].length][3];
		double y, u ,v;
		for (int i = 0; i < rgb.length; i++) {
			for (int j = 0; j < rgb[0].length; j++) {
				y = .299*(rgb[i][j][0]) + .587*rgb[i][j][1] + .114*rgb[i][j][2];
				u = .492*(rgb[i][j][3] - y);
				v = .877*(rgb[i][j][0] - y);
				yuv[i][j][0] = y;
				yuv[i][j][1] = u;
				yuv[i][j][2] = v;
			}
		}
		return yuv;
	}
	public static int[][][] toRGB(double[][][] yuv){
		int[][][] rgb = new int[yuv.length][yuv[0].length][3];
		for (int i = 0; i < rgb.length; i++) {
			for (int j = 0; j < rgb[0].length; j++) {
				rgb[i][j][0] =(int) (yuv[i][j][0] + 1.14*yuv[i][j][3]);
				rgb[i][j][1] =(int) (yuv[i][j][0] - .395*yuv[i][j][1] - .581*yuv[i][j][2]);
				rgb[i][j][2] =(int) (yuv[i][j][0] + 2.033*yuv[i][j][1]); 
			}
		}
		return rgb;
	}
	
	public static boolean inBounds(int i, int j, int y, int x) {
		boolean inBound = true;
		if(i < 0 || i >= y || j < 0 || j >= x)
			inBound = false;
		return inBound;
	}


}
