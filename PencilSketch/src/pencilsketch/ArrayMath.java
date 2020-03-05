package pencilsketch;

public class ArrayMath {
	//std deviations
	public static double std(int[] arr) {
		double std = 0;
		double mean = mean(arr);
		double size = arr.length;
		for(int i = 0; i < arr.length; i++)
			std += Math.pow(mean - arr[i], 2);
		std = Math.sqrt(std/size);
		return std;
	}
	public static double std(int[][] arr) {
		double std = 0;
		double mean = mean(arr);
		double size = arr.length * arr[0].length;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				std += Math.pow(mean - arr[i][j], 2);
		std = Math.sqrt(std/size);
		return std;
	}
	public static double std(int[][][] arr) {
		double std = 0;
		double mean = mean(arr);
		double size = arr.length * arr[0].length * arr[0][0].length;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				for(int k = 0; k < arr[0][0].length; k++)
					std += Math.pow(mean - arr[i][j][k], 2);
		std = Math.sqrt(std/size);
		return std;
	}
	public static double std(double[] arr) {
		double std = 0;
		double mean = mean(arr);
		double size = arr.length;
		for(int i = 0; i < arr.length; i++)
			std += Math.pow(mean - arr[i], 2);
		std = Math.sqrt(std/size);
		return std;
	}
	public static double std(double[][] arr) {
		double std = 0;
		double mean = mean(arr);
		double size = arr.length * arr[0].length;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				std += Math.pow(mean - arr[i][j], 2);
		std = Math.sqrt(std/size);
		return std;
	}
	public static double std(double[][][] arr) {
		double std = 0;
		double mean = mean(arr);
		double size = arr.length * arr[0].length * arr[0][0].length;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				for(int k = 0; k < arr[0][0].length; k++)
					std += Math.pow(mean - arr[i][j][k], 2);
		std = Math.sqrt(std/size);
		return std;
	}
	//averages
	public static double mean(int[] arr) {
		double mean = 0;
		double size = arr.length;
		for(int i = 0; i < arr.length; i++)
			mean += arr[i];
		mean = mean/size;
		return mean;
	}
	public static double mean(int[][] arr) {
		double mean = 0;
		double size = arr.length * arr[0].length;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				mean += arr[i][j];
		mean = mean/size;
		return mean;
	}
	public static double mean(int[][][] arr) {
		double mean = 0;
		double size = arr.length * arr[0].length * arr[0][0].length;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				for(int k = 0; k < arr[0][0].length; k++)
					mean += arr[i][j][k];
		mean = mean/size;
		return mean;
	}
	public static double mean(double[] arr) {
		double mean = 0;
		double size = arr.length;
		for(int i = 0; i < arr.length; i++)
			mean += arr[i];
		mean = mean/size;
		return mean;
	}
	public static double mean(double[][] arr) {
		double mean = 0;
		double size = arr.length * arr[0].length;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				mean += arr[i][j];
		mean = mean/size;
		return mean;
	}
	public static double mean(double[][][] arr) {
		double mean = 0;
		double size = arr.length * arr[0].length * arr[0][0].length;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				for(int k = 0; k < arr[0][0].length; k++)
					mean += arr[i][j][k];
		mean = mean/size;
		return mean;
	}
	//maximum
	public static int max(int[] arr) {
		int max = arr[0];
		for(int i = 0; i < arr.length; i++)
			max = Math.max(arr[i], max);
		return max;
	}
	public static int max(int[][] arr) {
		int max = arr[0][0];
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				max = Math.max(arr[i][j], max);
		return max;
	}
	public static int max(int[][][] arr) {
		int max = arr[0][0][0];
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				for(int k = 0; k < arr[0][0].length; k++)
					max = Math.max(arr[i][j][k], max);
		return max;
	}
	public static double max(double[] arr) {
		double max = arr[0];
		for(int i = 0; i < arr.length; i++)
			max = Math.max(arr[i], max);
		return max;
	}
	public static double max(double[][] arr) {
		double max = arr[0][0];
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				max = Math.max(arr[i][j], max);
		return max;
	}
	public static double max(double[][][] arr) {
		double max = arr[0][0][0];
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				for(int k = 0; k < arr[0][0].length; k++)
					max = Math.max(arr[i][j][k], max);
		return max;
	}
	//minimum
	public static int min(int[] arr) {
		int min = arr[0];
		for(int i = 0; i < arr.length; i++)
			min = Math.min(arr[i], min);
		return min;
	}
	public static int min(int[][] arr) {
		int min = arr[0][0];
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
			min = Math.min(arr[i][j], min);
		return min;
	}
	public static int min(int[][][] arr) {
		int min = arr[0][0][0];
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				for(int k = 0; k < arr[0][0].length; k++)
					min = Math.min(arr[i][j][k], min);
		return min;
	}
	public static double min(double[] arr) {
		double min = arr[0];
		for(int i = 0; i < arr.length; i++)
			min = Math.min(arr[i], min);
		return min;
	}
	public static double min(double[][] arr) {
		double min = arr[0][0];
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
			min = Math.min(arr[i][j], min);
		return min;
	}
	public static double min(double[][][] arr) {
		double min = arr[0][0][0];
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[0].length; j++)
				for(int k = 0; k < arr[0][0].length; k++)
					min = Math.min(arr[i][j][k], min);
		return min;
	}
}
