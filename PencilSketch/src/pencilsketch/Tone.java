package pencilsketch;
import java.util.ArrayList;
import pixlab.*;
import static pixlab.Helpers.*;
public class Tone {
	public static double[][] genTone(double[][] blurredGrayScale, int[][] gray){
		
		
	
		//average of all pixel values in image
		double mean = genMeanArr(blurredGrayScale);
		
		//the high and low thresholds for tone separation
		double[] maxMin = maxMin(blurredGrayScale);
		double[] alphaHighLow = aHL(mean);
		double highThresh = (maxMin[0] - (alphaHighLow[0]*(maxMin[0]-mean)))/255.;
		double lowThresh  = (maxMin[1] + (alphaHighLow[1]*(mean - maxMin[1])))/255.;
		
		System.out.println("highthresh: " + highThresh);
		System.out.println("lowThresh: " + lowThresh);
		//separation of 3 different tone groups, bright, mid and dark
		ArrayList<Double> brightVals = new ArrayList<Double>();
		ArrayList<Double> midVals = new ArrayList<Double>();
		ArrayList<Double> darkVals = new ArrayList<Double>();
		double[][] normBlurredGray = norm0_1(blurredGrayScale, maxMin);
		double[][][] separatedImage = new double[3][normBlurredGray.length][normBlurredGray[0].length];
		double sumBrightPixels = 0, sumMidPixels = 0, sumDarkPixels = 0;
		int numBrightPixels = 0, numMidPixels = 0, numDarkPixels = 0;
		for(int i = 0; i < normBlurredGray.length; i++) {//separation by segmenting through the high and low thresholds
			for(int j = 0; j < normBlurredGray[0].length; j++) {
				double pixelVal = normBlurredGray[i][j];
				if(pixelVal >= highThresh) { //if part of high thresh, becomes bright layer
					sumBrightPixels += pixelVal;
					separatedImage[0][i][j] = pixelVal;
					brightVals.add(pixelVal);
				} else if(pixelVal < highThresh && pixelVal > lowThresh) { //if between thesh, becomes mid layer
					sumMidPixels += pixelVal;
					separatedImage[1][i][j] = pixelVal;
					midVals.add(pixelVal);
				} else { //if less than theshhold becomes dark layer.
					sumDarkPixels += pixelVal;
					separatedImage[2][i][j] = pixelVal;
					darkVals.add(pixelVal);
				}
			}
		}

		numBrightPixels = brightVals.size();
		numMidPixels = midVals.size();
		numDarkPixels = darkVals.size();
	
		//determining weights and variables needed to generate comparison histogram to adjust grayscale values
		int totalNum = normBlurredGray.length * normBlurredGray[0].length;
		double weightBright =(double) numBrightPixels/(totalNum);
		double weightMid = (double) numMidPixels/totalNum;
		double weightDark = (double) numDarkPixels/totalNum;
		double meanBright = sumBrightPixels/numBrightPixels;
		double meanMid = sumMidPixels/numMidPixels;
		double meanDark = sumDarkPixels/numDarkPixels;
		double sigmaDark = 0, sigmaBright = 0, sigmaMid = 0; //sigma meaning standard deviation
		for(Double num : midVals)
			sigmaMid += Math.pow(meanMid - num, 2);
		sigmaMid = Math.sqrt(sigmaMid/numMidPixels);
		for(Double num : darkVals)
			sigmaDark+= Math.pow(meanDark - num, 2);
		sigmaDark = Math.sqrt(sigmaDark/numDarkPixels);
		for(Double num : brightVals)
			sigmaBright += Math.abs(num-1);
		sigmaBright /= numBrightPixels;
		double uA = meanMid - Math.sqrt(3)*sigmaMid;
		double uB = meanMid + Math.sqrt(3)*sigmaMid;
		
		double[] sampleHist = new double[256];
		double SUM = 0;
		double a, b, c, abc;
		for(int i = 0; i < 256; i++) {
			 a = brightPDF(i/255., sigmaBright, meanBright);
			 b = midPDF(i/255., uA, uB);
			 c = darkPDF(i/255., sigmaDark, meanDark);
			 abc = a+b+c;
			if(abc == 0) {
				abc = 1;
			}
			sampleHist[i] = totalNum*(weightBright*a+weightMid*b+weightDark*c)/abc;
			SUM += sampleHist[i];
		}
		System.out.println("weightBright: " + weightBright + "\n"
									   +"weightMid: " + weightMid +
									   "\nWeightDark: " + weightDark);
		System.out.printf("meanBright: %f\nmeanMid: %f\nmeanDark: %f\n", meanBright, meanMid, meanDark); 
		System.out.println("SUM: "+ SUM);
		System.out.println("totalNum: "+ totalNum);
		System.out.println("totalNum: "+ (numBrightPixels + numMidPixels + numDarkPixels ));
		System.out.println("sigmaBright: " + sigmaBright);
		System.out.println("sigmaMid: " + sigmaMid);
		System.out.println("sigmaDark: "+ sigmaDark);
		System.out.println("uA: "+ uA);
		System.out.println("uB: " +uB);
		System.out.println("numBright: "+numBrightPixels);
		System.out.println("numMid: "+ numMidPixels);
		System.out.println("numDark: " + numDarkPixels);
		System.out.println();
		System.out.println();
		
	
		
		displayHist(genImageHistogram(normBlurredGray));
		displayHist(sampleHist);
		
		return null;
		
	}
	/**
	 * 
	 * @param val
	 * @param sigmaBright
	 * @param meanBright
	 * @return
	 */
	private static double brightPDF(double val, double sigmaBright, double meanBright) {
		if(val <= 1)
			return Math.exp((val-1)/sigmaBright)/sigmaBright;
		else
		return 0;
	} 	
	/**
	 * 
	 * @param val
	 * @param uA
	 * @param uB
	 * @return
	 */
	private static double midPDF(double val, double uA, double uB) {
		if(val <= uB && val >= uA)
			return 1.0/(uB-uA);
		return 0;
	}
	/**
	 * 
	 * @param val
	 * @param sigmaDark
	 * @param meanDark
	 * @return
	 */
	private static double darkPDF(double val, double sigmaDark, double meanDark) {
		double value = Math.exp(-1.0*Math.pow(val-meanDark, 2)/(2*Math.pow(sigmaDark, 2)));
				value /= Math.sqrt(2.0* Math.PI * sigmaDark);
		return value;
	}
	/**
	 * Generates the histogram of the pixel values of the input image, needs to be in range of 0-1
	 * Make note: frequency will be VERY large due to larger resolutions, so advice 
	 * to taking a look at it for comparison is to use displayHist(int[] hist) method
	 * which will take what this returns as its input.
	 * The histogram is formated as a 256 element array corresponding to the 0-255 range 
	 * of gray scale values and the value at each index indicates its frequency in the image.
	 * @param gray the input image
	 * @return the histogram of the the values. 
	 */
	public static int[] genImageHistogram(double[][] gray) {
		int[] hist = new int[256];
		/*
		 * the loop loops through evey value of the input image.
		 * Then takes the value which should be in the range
		 * of 0-255, and uses it as the index of the histogram, just
		 * adding one to it.
		 */
		for(int i =0; i < gray.length; i++) {
			for(int j = 0; j < gray[0].length; j++) {
				hist[(int)(255*gray[i][j])]++; 
			}
		}
		return hist;
	}
	/**
	 * displays the histogram. Generally for check use only and doesnt need to be seen.
	 * @param hist the histogram wanted to see
	 */
	public static void displayHist(int[] hist){
		int maxFreq=0;
		for(int i = 0; i < hist.length; i++)
			maxFreq = Math.max(maxFreq, hist[i]);
		int[][] histAsImage = new int[500][1000];
		for(int i = 0; i < 256; i++) 
			for(int j  = 0; j < 500; j++) 
				if(hist[i]/(double) maxFreq * 500 >= j)
						histAsImage[499-j][(int) ((double) i/256. * 1000)] = 255;
		Helpers.show(PointProcesses.negate(histAsImage));
	}	
	/**
	 * displays the histogram. Generally for check use only and doesnt need to be seen.
	 * @param hist the histogram wanted to see
	 */
	public static void displayHist(double[] hist){
		double maxFreq=0;
		for(int i = 0; i < hist.length; i++)
			maxFreq = Math.max(maxFreq, hist[i]);
		//System.out.println(maxFreq);
		int[][] histAsImage = new int[500][1000];
		for(int i = 0; i < hist.length; i++) 
			for(int j =0; j < (500); j++)
				if(hist[i]/(double) maxFreq * 500 >= j)
					histAsImage[499-j][(int) ((double) i/256. * 1000)] = 255;
		Helpers.show(PointProcesses.negate(histAsImage));
	}
	/**
	 * normalizes the image to a range of 0-1
	 * @param arr
	 * @param maxMin
	 * @return
	 */
	private static double[][] norm0_1(double[][] arr, double[] maxMin){
		double[][] normed = new double[arr.length][arr[0].length];
		for(int i = 0; i < arr.length; i++) 
			for(int j = 0; j < arr[0].length; j++) 
				normed[i][j] = (arr[i][j]- maxMin[1])/(maxMin[0]-maxMin[1]);
		return normed;
	}
	/**
	 * finds and returns the maximum and minimum values of an array
	 * @param arr input array
	 * @return the max and min arranged as {max, min}
	 */
	private static double[] maxMin(double[][] arr) {
		double[] maxMin= {arr[0][0], arr[0][0]};
		for(int i =0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				maxMin[0] = Math.max(arr[i][j], maxMin[0]);
				maxMin[1] = Math.min(arr[i][j], maxMin[1]);
			}
		}
		return maxMin;
	}
	/**
	 * used to give high and low alpha values based on the average pixel values
	 * @param mean the average of the arr
	 * @return the alpha high and low values {alphaHigh, AlphaLow}
	 */
	public static double[] aHL(double mean) {
		double[] arr = new double [2];
		if (mean >= 125.) {
			arr[0] = .2;
			arr[1] = .72;
		}
		else if (mean >= 100 && mean < 125) {
			arr[0] = .15;
			arr[1] = .69;
		}
		else {
			arr[0] = .48;
			arr[1] = .5;
		}
		return arr;
	}
	/**
	 * Calculates the average value of the array.
	 * @param arr the array to gather the average from
	 * @return the average of the array
	 */
	private static double genMeanArr(double[][] arr) {
		double total = 0;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				total += arr[i][j];
		return (total / (arr.length * arr[0].length));
	}
}
