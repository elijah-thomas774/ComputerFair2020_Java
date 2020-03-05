package pencilsketch;

import java.awt.image.*;

import static pixlab.Helpers.*;

import static pixlab.PointProcesses.negate;
import pixlab.SimplePicture;

/**
 * The Sketch class offers the functionality of making the image have strokes. 
 * @author Elijah Thomas
 *
 */
public class Sketch {



	public static BufferedImage sketch3(BufferedImage image, int k, double sigma, double linescale, double dThreshMin) {
		int height = image.getHeight();
		int width = image.getWidth();
		grayScaleBuff(image);
		image = filterBuff(image, k, sigma);
		

		
		return image;
	}
	
	/**
	 * generates a kernel
	 * @param k
	 * @param sigma
	 * @return
	 */
	public static float[] kernel(int k, double sigma){
		float[][] kernel = new float[k*2+1][k*2+1];
		float[] kern2 = new float[(2*k+1)*(2*k+1)];
		float sum = 0;
		for(int i = -k; i <= k; i++){
			for(int j = -k; j <= k; j++){
				kernel[i + k][j + k] = (float) ((1.0 / (2 * Math.PI * sigma * sigma)) * Math.exp(-1 *((i * i) + (j * j)) / (2 * sigma * sigma)));
				sum += kernel[i+k][j+k];
			}
		}
		for(int i = 0; i < kernel.length; i++){
			for(int j = 0; j < kernel[0].length; j++){
				kernel[i][j] /= sum;
				kern2[i * (2*k+1) + j] = kernel[i][j];
			}
		}
		return kern2;
	}
	
	/**
	 * 
	 * @param image
	 * @param k
	 * @param sigma
	 * @return
	 */
	public static BufferedImage filterBuff(BufferedImage image, int k, double sigma) {
	    Kernel kernel = new Kernel(k*2+1, k*2*1, kernel(k,sigma));
	    BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
	    image = op.filter(image, null);
	    return image;
	}
	
	/**
	 * This is the attempt to avoid as many objects as possible
	 * @param image
	 */
	public static void grayScaleBuff(BufferedImage image) {
		int red, green, blue, alpha, rgB, gray;
		for(int i = 0; i < image.getHeight(); i++) {
			for(int j = 0; j < image.getWidth(); j++) {
				rgB = image.getRGB(j,i);
				alpha = (rgB >> 24) & 0xff;
				red = (int) (((rgB >> 16) & 0xff) * .21);
				green = (int) (((rgB >> 8) & 0xff) * .72);
				blue = (int) ((rgB & 0xff) * .07);
				gray = red + green + blue;
				gray = (alpha << 24) + (gray << 16) + (gray << 8) + gray;
				image.setRGB(j, i, gray);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Sketch2 is the attempt at condensing all of the steps in edge detection down to one method. 
	 * Probably very pointless and only was made to get around the awful looping in android
	 * Does NOT rely on pixlab project within method
	 * @param filtered The blurred image of image
	 * @param lineScale Scaling of the line in reference to the the root of the area in the picture
	 * @param dThreshMin Min for deciding if pixel is an edge
	 * @param dThreshMax max for deciding if pixel is an edge
	 * @return The "sketched array" Is in a small range and for the black on white effect, will need negation and normalization.
	 */
	public static int[][] sketch2(double[][] filtered, double lineScale, double dThreshMin, double dThreshMax){
		short sin, cos;
		short r,c,u,v; //variables for other points of reference (r,c) (u,v)
		double angle; //angle at i,j
		double max, num1, num2, num3; //gradient magnitude variables
		double maxNum = 0;
		short x = (short) (filtered[0].length-1), y = (short) (filtered.length-1); //dimensions of array
		double[][] dThresh = new double[y][x];
		int[][] sketch = new int[y][x];
		double[][] gradAngle = calcGradAngle(filtered);
		double[][] gradMag = calcGradMag(filtered);
		for(short i = 0; i < y; i++) {
			for (short j = 0; j < x; j++) {
				//the angle direction of the 
				//angle = Math.atan2(filtered[i+1][j] - filtered[i][j], filtered[i][j+1] - filtered[i][j]);
				angle = gradAngle[i][j];

				//This formula is boiled down form of grouping into a bunch of bins, but through angle due to the nature of sin and cos on the unit circle
				angle = .25 * Math.PI * (int) (4.5 + 4*angle/Math.PI);

				//Based on the angle determined in the above step, sin and cos will end up being either 1, -1, or 0
				sin = (short) Math.round(Math.sin(angle)); //rounding is important as truncating will result in wrong angle
				cos = (short) Math.round(Math.cos(angle));

				//(i,j) is original point of focus
				//(r,c) is a second point of focus relative to i,j
				//(u,v) is a third point of focus relative to i,j but opposite of (r,c)
				r = (short) (i + sin); 
				c = (short) (j + cos);
				u = (short) (i - sin);
				v = (short) (j - cos);
				//Gradient magnitude of center point
				num1 = Math.sqrt(Math.pow(filtered[i][j+1] - filtered[i][j], 2) + Math.pow(filtered[i+1][j] - filtered[i][j], 2));
				//num1 = gradMag[i][j];
				if (!inBounds(r,c,y,x)) {
					r = i;
					c = j;
					num2 = num1; //if the points referenced for num2 were oob, make it the same as num1
				}
				else { /* If in bounds calculate the magnitude of another point in reference to the center */
//					num2 = gradMag[r][c];
					num2 = Math.sqrt(Math.pow(filtered[r][c+1] - filtered[r][c], 2) + Math.pow(filtered[r+1][c] - filtered[r][c], 2));
				}
				if (!inBounds(u,v,y,x)) {
					u = i;
					v = j;
					num3 = num1;//if the points referenced for num3 were oob, make it the same as num1
				}
				else { /* If in bounds calculate the magnitude of another point in reference to the center, but in other direction */
//					num3 = gradMag[u][v];
					num3 = Math.sqrt(Math.pow(filtered[u][v+1] - filtered[u][v], 2) + Math.pow(filtered[u+1][v] - filtered[u][v], 2));
				}
				//nonMaxSupp Step

				//Calculate The maximum of the three
				max = Math.max(Math.max(num1, num2), num3);

				//Makes sure that center point of focus (i,j) is actually the maximum
				if (Math.abs(num1 - max) < .0001)
					dThresh[i][j] = max;


				if(max > maxNum)
					maxNum = max; // used for next step
			}
		}

		//next step: adding sketch lines and dThresh
		int height = (int) (Math.sqrt(sketch.length * sketch[0].length) * lineScale);//dimension of array used for sketch lines
		int thresh = 0;
		double slope;
		for(int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				thresh = (int) ((255.0/(maxNum)) * (dThresh[i][j]));
				//used to generate line
				slope =  Math.atan2(filtered[i+1][j] - filtered[i][j], filtered[i][j+1] - filtered[i][j]) + Math.PI/2;
				if(thresh >= dThreshMin) {
					addLine(height, sketch, slope, i, j);
				}
				if(thresh >= dThreshMax) {
					addLine(height, sketch, slope, i, j);

				}
			}
		}

		return sketch;
	}


	public static void genThreshArr() {

	}
	/**
	 * controls creating the sketch from one method. Makes a stroke at every spot where dThresh is 
	 * at least 127 and then goes over again on hard edges with dThresh being 255
	 * @param dThresh dThresh for image 
	 * @param gradAng gradAng for image
	 * @param lineScale the scaled length for a sketch line.
	 * @return The image with edges converted into sketch lines.
	 */
	public static int[][] sketch(int[][] dThresh, double[][] gradAng, double lineScale, int[][] edge){
		int[][] sketch = new int[dThresh.length][dThresh[0].length];
		int height = (int) (Math.sqrt(sketch.length * sketch[0].length) * lineScale);
		for(int i = 0; i < sketch.length; i++) {
			for(int j = 0; j < sketch[0].length; j++) {

				if(dThresh[i][j] >= 127) {
					addLine(height, sketch, gradAng[i][j] + Math.PI/2, i, j);
				}
				if(edge[i][j] > 127) {
					addLine(height, sketch, gradAng[i][j] + Math.PI/2, i, j);

				}
			}
		}
		return sketch;
	}
	/**
	 * controls adding a single sketch line at any angle.
	 * @param length the length of the line to sketch 
	 * @param arr the picture to add sketch line to 
	 * @param ang the angle that will be translated into the slope of the line using tangent.
	 * @param r the row at which the image is currently evaluating.
	 * @param c the column at which the image is currently evaluating
	 */
	public static void addLine(int length, int[][] arr, double ang, int r, int c) {
		if(length % 2 == 0)
			length++;

		int y, x;
		double slope = Math.tan(ang);
		if (Math.abs(slope) >= 1.00) {
			for(int i = -length/2; i <= length/2; i++) {
				y = r+i;
				x = c + (int) Math.round(i/slope);
				if(x >=0 && x < arr[0].length && y >=0 && y < arr.length && Math.sqrt(Math.pow(y-r, 2) + Math.pow(x-c,2)) <= length/2){
					arr[y][x]++;
					if(x-1 > 0)	
						arr[y][x-1]++;
				}
			}
		}
		else if (Math.abs(slope) < 1) {
			for(int i = -length/2; i <= length/2; i++) {
				y = r+(int) Math.round(i*slope);
				x = c + i;
				if(x >=0 && x < arr[0].length && y >= 0 && y < arr.length && Math.sqrt(Math.pow(y-r, 2) + Math.pow(x-c, 2)) <= length/2) {
					arr[y][x]++;;
					if(y-1 > 0)	
						arr[y-1][x]++;
				}
			}
		}
	}

	/**
	 * caclGradMag is another variant to save memory.
	 * @param filtered
	 * @return
	 */
	public static double[][] calcGradMag(double[][] filtered){
		double[][] grad = new double[filtered.length-1][filtered[0].length-1];
		for(int i = 0; i < grad.length; i++) {
			for(int j = 0; j < grad[0].length; j++) {
				grad[i][j] = Math.sqrt(Math.pow(filtered[i][j+1] - filtered[i][j], 2) + Math.pow(filtered[i+1][j] - filtered[i][j], 2));
			}
		}
		return grad;
	}
	public static double[][] calcGradAngle(double[][] filtered){
		double[][] ang = new double[filtered.length-1][filtered[0].length-1];
		for(int i = 0; i < ang.length; i++) {
			for(int j = 0; j < ang[0].length; j++) {
				ang[i][j] = Math.atan2(filtered[i+1][j] - filtered[i][j], filtered[i][j+1] - filtered[i][j]);
			}
		}
		return ang;
	}
	public static boolean inBounds(int i, int j, int y, int x) {
		boolean inBound = true;
		if(i < 0 || i >= y || j < 0 || j >= x)
			inBound = false;
		return inBound;
	}
}
