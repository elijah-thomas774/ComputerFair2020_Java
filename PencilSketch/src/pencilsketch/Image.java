package pencilsketch;

import pixlab.*;

import static pixlab.EdgeDetection.*;
import static pixlab.PointProcesses.*;
import static pixlab.Helpers.*;

public class Image {



	/** Original picture in Object */
	private Picture pic;

	/** controls number of rows and columns to reflect on borders and the size of kernel*/
	private int kernelSize;
	/** standard deviation of kernel to control smoothing */
	private double sDeviation;
	/** the minimum value to keep for double threshold */
	private int dThreshMin;
	/** the maximum value for mid range (gray) to keep for double threshold */
	private int dThreshMax;
	/** the scaling for the line length (percentage of height*width) */
	private double lineScale;

	private int[][][] arrPic;
	private double[][] kernel;
	private int[][] grayScale;
	private int[][] borderReflect;
	private double[][] filtered;
	private double[][] gradMag;
	private double[][] gradAng;
	private double[][] gradAngAdj;
	private double[][] nonMaxSupp;
	private int[][] dThresh;
	private int[][] edge;
	private int[][] sketch;

	//-------------------------------------------------------
	//CONSTRUCTORS

	/** No Default due to need of a picture, but assigns default values to other variables */
	public Image(Picture pic) {
		this(pic, 5, 1.35, 18, .013);
	}

	//-------------------------------------------------------

	/** Probably most common constructor with control over the smoothing and keeping maximums */
	public Image(Picture pic, int k, double s, int dMin) {
		this(pic, k, s, dMin, .013);
	}

	//-------------------------------------------------------

	/** Most control due to dMax variable. This will be able to control slight edges where more should appear darker */
	public Image(Picture pic, int k, double s, int dMin, double lineScale) {
		this.pic = pic;
		kernelSize = k;
		sDeviation = s;
		dThreshMin = dMin;
		dThreshMax = dMin*3;
		this.lineScale = lineScale;
	}

	//-------------------------------------------------------
	//Main Uses

	/** 
	 * Resets all variables to null so that different values can be used for the same image
	 */
	public void resetAllVars() {
		arrPic = null;
		grayScale = null;
		kernel = null;
		borderReflect = null;
		filtered = null;
		gradMag = null;
		gradAng = null;
		gradAngAdj = null;
		nonMaxSupp = null;
		dThresh = null;
		edge = null;
		sketch = null;
	}

	//-------------------------------------------------------
	/**
	 * assigns a sketch variable if not given and will return the sketch of the image
	 * using the Sketch class.
	 * @return The image with sketch lines 
	 */
	public int[][] sketch(){
		if(sketch == null)
			sketch = normalize(Sketch.sketch(dThresh(), gradAng(), lineScale));
		return sketch;
	}

	public int[][] sketch2(){
		return Sketch.sketch2(filtered(), lineScale, dThreshMin, dThreshMax);
	}
	//-------------------------------------------------------

	/**
	 * will scale the picture in the IMage object.
	 * @param x scale in the x-direction
	 * @param y scale in the y-direction
	 */
	public void scale(double x, double y) {
		pic = pic.scale(x, y);
	}

	//-------------------------------------------------------

	/**
	 * getter used to get the height of the current image
	 * @return The height value of the image
	 */
	public int getHeight() {
		return pic.getHeight();
	}

	//-------------------------------------------------------

	/**
	 * getter used to get the width of the current image
	 * @return The width value of the image
	 */
	public int getWidth() {
		return pic.getWidth();
	}

	//-------------------------------------------------------

	/**
	 * used to get the kernel used to filter the image
	 * @return the kernel with values
	 */
	public double[][] kernel(){
		if (kernel == null)
			kernel = EdgeDetection.kernel(kernelSize, sDeviation);
		return kernel;
	}	

	//-------------------------------------------------------
	
	/**
	 * used to get the three dimensional array representation of the image
	 * @return The 3 dimensional array of the image
	 */
	public int[][][] arrPic(){
		if(arrPic == null) 
			arrPic = toArray(pic.getPixels2D());
		return arrPic;
	}	

	//-------------------------------------------------------

	/**
	 * used to get the two dimensional array representation of the grayscaled image
	 * @return The 2 Dimensional array of the image
	 */
	public int[][] grayScale(){
		if(grayScale == null) 
			grayScale = colorToGray(arrPic());
		return grayScale;
	}	

	//-------------------------------------------------------

	/**
	 * used to get the two dimensional array after the borders were reflected 
	 * @return The 2 Dimensional array of the image
	 */
	public int[][] borderReflect(){
		if(borderReflect == null) 
			borderReflect = createBorderReflect(grayScale(), kernelSize);
		return borderReflect;

	}	

	//-------------------------------------------------------

	/**
	 * used to get the filtered version of the image
	 * @return The 2 Dimensional array of the image
	 */
	public double[][] filtered(){
		if(filtered == null) 
			filtered = filter(borderReflect(), kernel());
		return filtered;

	}

	//-------------------------------------------------------

	/**
	 * used to get the gradient magnitude of the image
	 * @return The 2 Dimensional array of the image's gradient magnitude
	 */
	public double[][] gradMag(){
		if(gradMag == null) 
			gradMag =  Sketch.calcGradMag(filtered());
		return gradMag;
	}

	//-------------------------------------------------------

	/**
	 * used to get the array of the gradient magnitude angles
	 * @return The 2 Dimensional array of the images's angles
	 */
	public double[][] gradAng(){
		if(gradAng == null) 
			gradAng = Sketch.calcGradAngle(filtered());
		return gradAng;
	}

	//-------------------------------------------------------

	/**
	 * used to get the array of the image's gradient magnitude angles adjusted to be one of 4 values
	 * @return the 2 dimensional array of the image's angles that have been adjusted
	 */
	public double[][] gradAngAdj(){
		if(gradAngAdj == null)
			gradAngAdj = adjustGradAngle(gradAng());
		return gradAngAdj;
	}	

	//-------------------------------------------------------

	/**
	 * used to get the array of the nonMax Suppressed array
	 * @return the 2 Dimensional array of the image with nonMaxes suppressed
	 */
	public double[][] nonMaxSupp(){
		if(nonMaxSupp == null) 
			nonMaxSupp = calcNonMaxSupp((gradMag()), gradAngAdj());
		return nonMaxSupp;
	}	

	//-------------------------------------------------------

	/**
	 * used to get the array that has had double threshold used on the nonMaxSupressed image
	 * @return the 2 Dimensional array of the image after double thresholding
	 */
	public int[][] dThresh(){
		if(dThresh == null) 
			dThresh = doubleThresh(normalize(nonMaxSupp()), dThreshMin, dThreshMax);
		return dThresh;
	}

	//-------------------------------------------------------

	/**
	 * used to get the array that has had the edges tracked by hysterisis
	 * @return the 2 dimensional array of the image
	 */
	public int[][] edge(){
		if(edge == null) 
			edge = calcEdge(dThresh());
		return edge;
	}	

	//-------------------------------------------------------
	//Setters

	/**
	 * Used to change the Picture
	 * @param pic the new picture
	 */
	public void setPic(Picture pic) {
		resetAllVars();
		grayScale = null;
		arrPic = null;
		this.pic = pic;
	}

	//-------------------------------------------------------

	/**
	 * Used to change the kernel Size. helps adjust sketch and edge detection. 
	 * @param k new kernel size
	 */
	public void setKernelSize(int k) {
		resetAllVars();
		kernelSize = k;
	}

	//-------------------------------------------------------

	/**
	 * Used to change standard deviation for kernel smoothing.
	 * @param s new standard deviation.
	 */
	public void setSDeviation(double s) {
		resetAllVars();
		sDeviation = s;
	}

	//-------------------------------------------------------

	/**
	 * Used to change dTheshMin.
	 * @param dMin new dTheshMin.
	 */
	public void setDThreshMin(int dMin) {
		dThresh = null;
		edge = null;
		sketch = null;
		dThreshMin = dMin;
	}

	//-------------------------------------------------------

	/**
	 * used to change dThreshMax.
	 * @param dMax new dThreshMax.
	 */
	public void setDThreshMax(int dMax) {
		dThresh = null;
		edge = null;
		sketch = null;
		dThreshMax = dMax;
	}

	//-------------------------------------------------------

	/**
	 * used to change linescale length. 
	 * @param lineScale new scaling for length of sketch line.
	 */
	public void setLineScale(double lineScale) {
		sketch = null;
		this.lineScale = lineScale;
	}

	//-------------------------------------------------------
	//Getters

	/**
	 * used to retrieve Picture.
	 * @return the picture
	 */
	public Picture getPic() {
		return pic;
	}

	//-------------------------------------------------------

	/**
	 * used to retrieve kernel size
	 * @return kernel size
	 */
	public int getKernelSize() {
		return kernelSize;
	}

	//-------------------------------------------------------

	/**
	 * used to retrieve standard deviation of kernel.
	 * @return the standard deviation
	 */
	public double getSDeviation() {
		return sDeviation;
	}

	//-------------------------------------------------------

	/**
	 * used to retrieve dThreshMin 
	 * @return dThreshMin
	 */
	public int getDThreshMin() {
		return dThreshMin;
	}

	//-------------------------------------------------------

	/**
	 * used to retrieve dThreshMax
	 * @return dThreshMax
	 */
	public int getDThreshMax() {
		return dThreshMax;
	}

	//-------------------------------------------------------

	/**
	 * used to retrieve lineScale value
	 * @return lineScale
	 */
	public double getLineScale() {
		return lineScale;
	}
}

