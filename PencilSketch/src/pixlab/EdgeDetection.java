package pixlab;
import static pixlab.Helpers.*;
import static pixlab.PointProcesses.*;

/**
 * Collection of static mehtods used to perform Canny Edge Detection.
 * 
 * @author Elijah Thomas
 *
 */
public class EdgeDetection {

	/**
	 * the createBorderReflect method reflects the specified k value on the edges of the picture to filter the image 
	 * @param image image to reflect border
	 * @param k how many pixels off the border wanted to reflect
	 * @return image with the reflected border
	 */
	public static int[][] createBorderReflect(int[][] image, int k){
		int[][] img = new int[image.length + 2*k][image[0].length + 2*k];
		img = copyRange(image, 0, 0, image.length - 1, image[0].length - 1, img, k, k);
		int var1, var2, var3;
		for(int i = k - 1; i >= 0; i--) {
			var1 = 2*k - i;
			var2 = img.length - 1 - 2*k + i;
			var3 = img[0].length - 1 - 2*k + i;
			//NOTE: uses copyRangeS. this is special for this case due to my intent of overriding the image and this allows for ANY positive k value
			//                   row1   col1        row2             col2                     StartRow1     StartColumn1
			img = copyRangeS(img, var1 , 0    , var1          , img[0].length - 1, img , i                  , 0                    );
			img = copyRangeS(img, var2 , 0    , var2          , img[0].length - 1, img , img.length - i - 1 , 0                    );
			img = copyRangeS(img, 0    , var1 , img.length    , var1             , img , 0                  , i                    );
			img = copyRangeS(img, 0    , var3 , img.length - 1, var3             , img , 0                  , img[0].length - i - 1); 
		}
		return img;
	}
	private static int[][] copyRangeS(int[][] img1, int r1, int c1, int r2, int c2, int[][] img2, int startR, int startC){
		for(int i = r1, r = startR; i <= r2; i++, r++) {
			for(int j = c1, c = startC; j <= c2; j++, c++) {
				if(r < img2.length && c < img2[0].length) {
					img2[r][c] = img1[i][j];
				}
			}
		}
		return img2;
	}
	//------------------------------------------------------------------------
	/**
	 * the filter method smoothes an image out by rubbing a kernel over it to create a blurred effect
	 * @param image the borderReflected image
	 * @param kernel the kernel for rubbing
	 * @return the filtered image
	 */
	public static double[][] filter(int[][] image, double[][] kernel){
		int k = kernel.length/2;
		double[][] img = new double[image.length - 2*k][image[0].length - 2*k];
		double temp = 0;
		for (int i = k; i < image.length - k; i++) {
			for (int j = k; j < image[0].length - k; j ++) {
				for (int u = -k; u <= k; u++) {
					for (int v = -k; v <= k; v++) {
						temp += image[i+u][j+v] * kernel[u+k][v+k];
					}
				}
				img[i-k][j-k] = temp;
				temp = 0;
			}
		}
		return img;
	}
	//------------------------------------------------------------------------
	/**
	 * the calcGradientX method calculates the gradient across the rows
	 * @param image the filtered image
	 * @return gradient going across the rows
	 */
	public static double[][] calcGradientY(double[][] image){
		double[][] gradY = new double[image.length - 1][image[0].length];
		for (int i = gradY.length - 2; i >= 0; i--) {
			for (int j = 0; j < gradY[0].length; j++) {
					gradY[i][j] = image[i + 1][j] - image[i][j];
			}
		}
		return (gradY);
	}
	//------------------------------------------------------------------------
	/**
	 * the calcGradientY method calculates the gradient down the columns
	 * @param image the filtered image
	 * @return gradient going down the columns
	 */
	public static double[][] calcGradientX(double[][] image){
		double[][] gradX = new double[image.length][image[0].length - 1];
		for (int i = 0; i < gradX.length; i++) {
			for (int j = gradX[0].length - 2; j >= 0; j--) {
				gradX[i][j] = image[i][j+1] - image[i][j];
			}
		}
		return (gradX);
	}
	//------------------------------------------------------------------------
	/**
	 * the calcGradientMag calculates the gradient magnitude at each pixel
	 * @param gradX the gradient on x
	 * @param gradY the gradient on y
	 * @return the gradients magnitudes image
	 */
	public static double[][] calcGradientMag(double[][] gradX, double[][] gradY){
		double[][] gradMag = new double[gradY.length][gradX[0].length];
		for (int i = 0; i < gradMag.length; i++) {
			for (int j = 0; j < gradMag[0].length; j++) {
				gradMag[i][j] = Math.sqrt((Math.pow(gradX[i][j], 2) + Math.pow(gradY[i][j], 2)));
			}
		}
		return (gradMag);
	}
	//------------------------------------------------------------------------
	/**
	 * the calcGradientAngle calculates the gradient angle at each pixel
	 * @param gradX the gradient on x
	 * @param gradY the gradient on y
	 * @return the gradients angle image
	 */
	public static double[][] calcGradientAngle(double[][] gradX, double[][] gradY){
		double[][] gradAngle = new double[gradY.length][gradX[0].length];
		for (int i = 0; i < gradAngle.length; i++) {
			for (int j = 0; j < gradAngle[0].length; j++) {
				gradAngle[i][j] = Math.atan2(gradY[i][j], gradX[i][j]);
			}
		}
		return (gradAngle);
	}
	//------------------------------------------------------------------------
	/**
	 * the adjustGradAngle method puts the angles into bins where it generalizes 
	 * where the angle could be
	 * @param gradAngle the calculates gradientAngle array
	 * @return the adjusted angle array
	 */
	public static double[][] adjustGradAngle(double[][] gradAngle){
		double[][] gradAngAdj = new double[gradAngle.length][gradAngle[0].length];
		double angle = 0;
		for(int i = 0; i < gradAngAdj.length; i++) {
			for(int j = 0; j < gradAngAdj[0].length; j++) {
				if (gradAngle[i][j] < 0)
					angle = (gradAngle[i][j] + Math.PI);
				else
					angle = gradAngle[i][j];	

			   if (angle > 15.0*Math.PI/16)
					gradAngAdj[i][j] = 0;
			   else if (angle > 13.0*Math.PI/16)
				   gradAngAdj[i][j] = 7.0*Math.PI/8;
			   else if (angle > 11.0*Math.PI/16)
				   gradAngAdj[i][j] = 6.0*Math.PI/8;
			   else if (angle > 9.0*Math.PI/16)
				   gradAngAdj[i][j] = 5.0*Math.PI/8;
			   else if (angle > 7.0*Math.PI/16)
				   gradAngAdj[i][j] = 4.0*Math.PI/8;
			   else if (angle > 5.0*Math.PI/16)
				   gradAngAdj[i][j] = 3.0*Math.PI/8;
			   else if (angle > 3.0*Math.PI/16)
				   gradAngAdj[i][j] = 2.0*Math.PI/8;
			   else if (angle > Math.PI/16)
				   gradAngAdj[i][j] = 1.0*Math.PI/8;
			   else
				   gradAngAdj[i][j] = 0.0;

			}
		}
		return gradAngAdj;
		/*	double[][] gradAngAdj = new double[gradAngle.length][gradAngle[0].length];
		for(int i = 0; i < gradAngAdj.length; i++) {
			for(int j = 0; j < gradAngAdj[0].length; j++) {
				gradAngAdj[i][j] = .25 * Math.PI * (int) (4.5 + 4*gradAngle[i][j]/Math.PI); 
			}
		}
		return gradAngAdj;
		*/
	}
	//------------------------------------------------------------------------
	/**
	 *	the calcNonMaxSupp method tests each gradient with the adjacent points according
	 *	to its adjusted angle and only keeps the point if its the maximum of its adjacent pairs.
	 * @param gradMag the gradient magnitude array
	 * @param gradAngAdj  the adjusted angle array
	 * @return the array with only kept maximums along its gradient
	 */
	public static double[][] calcNonMaxSupp(double[][] gradMag, double[][] gradAngAdj){
		int y = gradMag.length, x = gradMag[0].length;	
		double[][] nonMaxSupp = new double[y][x];
		double max = 0;
		int r = 0, c = 0, u = 0, v = 0, sin = 0, cos = 0;
		for (int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				sin = (int) Math.round(Math.sin(gradAngAdj[i][j]));
				cos = (int) Math.round(Math.cos(gradAngAdj[i][j]));
				r = i + sin; 
				c = j + cos;
				u = i - sin;
				v = j - cos;
				if (!inBounds(r,c,y,x)) {
					r = i;
					c = j;
				}
				if (!inBounds(u,v,y,x)) {
					u = i;
					v = j;
				}
				max = Math.max(Math.max(gradMag[i][j], gradMag[u][v]), gradMag[r][c]);
				if (gradMag[i][j] == max)
					nonMaxSupp[i][j] = max;

			}
		}
		return nonMaxSupp;
	}
	//------------------------------------------------------------------------
	/**
	 * the doubleThresh method categorizes the values of the
	 * nonMaxSupp methods result and makes them an edge, maybe 
	 * an edge, and not an edge
	 * @param nonMaxSupp result from calcNonMaxSupp
	 * @param low lower bound
	 * @param high higher bound
	 * @return the categorized array
	 */
	public static int[][] doubleThresh(double[][] nonMaxSupp, int low, int high){
		int[][] dThresh = new int[nonMaxSupp.length][nonMaxSupp[0].length];
		for(int i = 0; i < dThresh.length; i++) {
			for(int j = 0; j < dThresh[0].length; j++) {
				if (nonMaxSupp[i][j] < low)
					dThresh[i][j] = 0;
				else if (nonMaxSupp[i][j] > high)
					dThresh[i][j] = 255;
				else 
					dThresh[i][j] = 127;
			}
		}
		return dThresh;
	}
	//------------------------------------------------------------------------
	/**
	 * the calcEdge method uses edge tacking by hysteresis
	 * and focuses on the 127 values to make the final result
	 * @param dThresh the result from the doubleThresh method
	 * @return the image with its hard edges
	 */
	public static int[][] calcEdge(int[][] dThresh){
		int[][] edge = copy(dThresh);
		for(int i = 0; i < edge.length; i++) {
			for(int j = 0; j < edge[0].length; j++) {
				if (dThresh[i][j] == 127) {
					if (nextTo(dThresh, i, j))
						edge[i][j] = 255;
					else
						edge[i][j] = 0;
				}
			}
		}
		return edge;
	}
	private static boolean nextTo(int[][] arr, int i, int j) {
		boolean check = true;
		for(int u = i-1; u <= i+1; u++) {
			for(int v = j-1; v <= j+1; v++) {
				if(inBounds(u,v,arr.length,arr[0].length)) {
					if(arr[u][v] == 0)
						check = false;
				}
			}
		}
		return check;
	}
	//------------------------------------------------------------------------
	/**
	 *  the kernel method calculates the kernel used to filter the images
	 *  based on any k and standard deviation 
	 * @param k the k value
	 * @param sigma the standard deviation
	 * @return the Gaussian kernel
	 */
	public static double[][] kernel(int k, double sigma){
		double[][] kernel = new double[k*2+1][k*2+1];
		double sum = 0;
		for(int i = -k; i <= k; i++){
			for(int j = -k; j <= k; j++){
				kernel[i + k][j + k] = (1.0 / (2 * Math.PI * sigma * sigma)) * Math.exp(-1 *((i * i) + (j * j)) / (2 * sigma * sigma));
				sum += kernel[i+k][j+k];
			}
		}
		for(int i = 0; i < kernel.length; i++){
			for(int j = 0; j < kernel[0].length; j++){
				kernel[i][j] /= sum;
			}
		}
		
		return kernel;
	}
	//------------------------------------------------------------------------

	/**
	 *  the addColorEdge method adds color back to the edges of an image 
	 *  by transposing the color value onto the edge from calcEdge
	 * @param edgeImage the edges of the image
	 * @param originalImage the original color image
	 * @param ang the adjusted gradient angle array for the image
	 * @return image with colored edges on a black background
	 */
	public static int[][][] addColorEdge(int[][] edgeImage, int[][][] originalImage, double[][] ang){
		int y = edgeImage.length, x = edgeImage[0].length;
		int[][][] edge = new int[y][x][3];
		int a = 0, b = 0, sin, cos;
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				sin = (int) Math.round(Math.sin(ang[i][j]));
				cos = (int) Math.round(Math.cos(ang[i][j]));
				if( sin == 0 || cos == 0) {
					a = sin;
					b = cos;
				}
				else {
					a = 0;
					b = 0;
				}
				if(edgeImage[i][j] == 0 && inBounds(i+a, j+b,y,x)) {
					edge[i][j][0] = originalImage[i+a][j+b][0];
					edge[i][j][1] = originalImage[i+a][j+b][1];
					edge[i][j][2] = originalImage[i+a][j+b][2];
				}
			}
		}
		return edge;
	}
	//------------------------------------------------------------------------
	/**
	 *  the addColorFill method adds color back to the space between the edges of an image 
	 *  by transposing the color value onto the space and leaving the edges black
	 * @param edgeImage the edges of the image
	 * @param originalImage the original color image
	 * @param ang the adjusted gradient angle array for the image
	 * @return image with black edges on a colored background
	 */
	public static int[][][] addColorFill(int[][] edgeImage, int[][][] originalImage, double[][] ang){
		int y = edgeImage.length, x = edgeImage[0].length;
		int[][][] edge = new int[y][x][3];
		int a = 0, b = 0, sin, cos;
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				sin = (int) Math.round(Math.sin(ang[i][j]));
				cos = (int) Math.round(Math.cos(ang[i][j]));
				if( sin == 0 || cos == 0) {
					a = sin;
					b = cos;
				}
				else {
					a = 0;
					b = 0;
				}
				if(edgeImage[i][j] == 255 && inBounds(i+a, j+b,y,x)) {
					edge[i][j][0] = originalImage[i+a][j+b][0];
					edge[i][j][1] = originalImage[i+a][j+b][1];
					edge[i][j][2] = originalImage[i+a][j+b][2];
				}
			}
		}
		return edge;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
