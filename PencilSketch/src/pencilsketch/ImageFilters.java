package pencilsketch;

import pixlab.*;

import static pixlab.EdgeDetection.*;
import static pixlab.PointProcesses.*;
import static pixlab.Helpers.*;

public class ImageFilters {
	
	private Picture picture;
	
	private int k;
	private double sigma;
	private int dThreshMin;
	
	private int height;
	private int width;
	
	private int[][][] arrPic;
	
	private double[][] kernel;
	private int[][] image;
	private int[][] borderReflect;
	private double[][] filtered;
	private double[][] gradX;
	private double[][] gradY;
	private double[][] gradMag;
	private double[][] gradAngle;
	private double[][] gradAngAdj;
	private double[][] nonMaxSupp;
	private int[][] dThresh;
	private int[][] edge;
	private int[][][] colorEdge;	
	private int[][][] colorFill;
	
	
	public ImageFilters(Picture picture) {
		this(picture, 2, 1.3, 8);
	}
	public ImageFilters(Picture picture, int k, double sigma, int dThreshMin) {
		this.picture = picture;
		arrPic = toArray(this.picture.getPixels2D());
		this.k = k;
		this.sigma = sigma;
		this.dThreshMin = dThreshMin;
		height = arrPic.length-1;
		width = arrPic[0].length-1;
		update();
	}
	
	private void update() {
		kernel = kernel(k, sigma);
		image = colorToGray(arrPic);
		borderReflect = createBorderReflect(image, k);
		filtered = filter(borderReflect, kernel);
		gradX = calcGradientX(filtered);
		gradY = calcGradientY(filtered);
		gradMag = calcGradientMag(gradX, gradY);
		gradAngle = calcGradientAngle(gradX, gradY);
		gradAngAdj = adjustGradAngle(gradAngle);
		nonMaxSupp = calcNonMaxSupp(gradMag, gradAngAdj);
		dThresh = doubleThresh(normalize(nonMaxSupp), dThreshMin , 3*dThreshMin);
		edge = calcEdge(dThresh);
    	colorEdge = addColorEdge(negate(edge), (arrPic), gradAngAdj);	
		colorFill = addColorFill(negate(edge), (arrPic), gradAngAdj);
	}

	public void showAll() {
		show(filtered);
		show(normalize(gradX));
		show(normalize(gradY));
		show(normalize(gradMag));
		show(normalize(gradAngle));
		show(normalize(gradAngAdj));
		show(negate(toIntArray(normalize(nonMaxSupp))));
		show(negate(dThresh));
		show(negate(edge));
		show(colorEdge);
		show(colorFill);
	}
	//----------------------------------------------------
	public Picture getPicture() {
		return picture;
	}

	public int getK() {
		return k;
	}

	public double getSigma() {
		return sigma;
	}

	public int getdThreshMin() {
		return dThreshMin;
	}

	public int[][][] getArrPic() {
		return arrPic;
	}

	public double[][] getKernel() {
		return kernel;
	}

	public int[][] getImage() {
		return image;
	}

	public int[][] getBorderReflect() {
		return borderReflect;
	}

	public double[][] getFiltered() {
		return filtered;
	}

	public double[][] getGradX() {
		return gradX;
	}

	public double[][] getGradY() {
		return gradY;
	}

	public double[][] getGradMag() {
		return gradMag;
	}

	public double[][] getGradAngle() {
		return gradAngle;
	}

	public double[][] getGradAngAdj() {
		return gradAngAdj;
	}

	public double[][] getNonMaxSupp() {
		return nonMaxSupp;
	}

	public int[][] getdThresh() {
		return dThresh;
	}

	public int[][] getEdge() {
		return edge;
	}

	public int[][][] getColorEdge() {
		return colorEdge;
	}

	public int[][][] getColorFill() {
		return colorFill;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
}
