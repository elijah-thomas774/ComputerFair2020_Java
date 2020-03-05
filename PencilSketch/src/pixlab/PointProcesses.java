package pixlab;
import static pixlab.Helpers.*;
/**
 * Contains methods for experimentation with point processes.
 * 
 * @author Elijah Thomas
 *
 */
public class PointProcesses {

	/**
	 *  the colorToBinary changes a color image (int[][][3]) to
	 *  a binary array (black and white image)
	 * @param color the color picture
	 * @return the black and white, or binary array
	 */
	public static int[][] colorToBinary(int[][][] color){
		int[][] bW = new int[color.length][color[0].length];
		bW = colorToGray(color);
		for (int i = 0; i < bW.length; i++) {
			for (int j = 0; j < bW[0].length; j++) {
				if (bW[i][j] <= 127)
					bW[i][j] = 0;
				else
					bW[i][j] = 255;
			}
		}
		return bW;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * the flipHorizontal method flips the picture horizontally
	 * @param img the image to flip
	 * @return the flipped image
	 */
	public static int[][][] flipHorizontal(int[][][] img){
		int[][][] image = copy(img);
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length / 2; j++) {
				int[] temp = image[i][j];
				image[i][j] = image[i][image[0].length - j -1];
				image[i][image[0].length - j - 1] = temp;
			}
		}
		return image;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 *  the flipVertical method flips the picture vertically
	 * @param image the image to be flipped
	 * @return the flipped image
	 */
	public static int[][][] flipVertical(int[][][] image){
		int[][][] img = copy(image);
		for (int i = 0; i < img.length / 2; i++) {
			for (int j = 0; j < img[0].length; j++) {
				int[] temp = img[i][j];
				img[i][j] = img[img.length - i - 1][j];
				img[img.length - i -1][j] = temp;
			}
		}
		return img;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * the flipDiagonal method flips an image from the top right to bottom left
	 * @param image the image to flip (will truncate to square)
	 * @return the flipped image
	 */
	public static int[][][] flipDiagonal(int[][][] image){
		int[][][] img = makeSquare(image);
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < i; j++) {
				int[] temp = img[i][j];
				img[i][j] = img[j][i];
				img[j][i] = temp;
			}
		}
		return img;
	}
	//Helper for truncating a rectangular image
	private static int[][][] makeSquare(int[][][] image){
		int len;
		if (image.length > image[0].length)
			len = image[0].length;
		else 
			len = image.length;
		int[][][] square = new int[len][len][3];

		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				square[i][j] = image[i][j];
			}
		}
		return square;		
	}

	//---------------------------------------------------------------------------------------------
	/**
	 * the rotate90 method rotates an image 90 degrees clockwise
	 * @param image the image to rotate
	 * @return the rotated image
	 */
	public static int[][][] rotate90(int[][][] image){
		int[][][] img = flipHorizontal(flipDiagonal(makeSquare(image)));
		return img;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * the mirrorHorizontal method mirrors the left half of the image onto
	 * the right half of the image
	 * @param image the image to mirror
	 * @return the mirrored image
	 */
	public static int[][][] mirrorHorizontal(int[][][] image){
		int[][][] img = copy(image);
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length / 2; j++) {
				img[i][image[0].length - 1 - j] = img[i][j];
			}
		}
		return img;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * the mirrorVertical method mirrors the top half of the image onto
	 * the bottom half of the image
	 * @param image the image to mirror
	 * @return the mirrored image
	 */
	public static int[][][] mirrorVertical(int[][][] image){
		int[][][] img = copy(image);
		for (int i = 0; i < img.length / 2; i++) {
			for (int j = 0; j < img[0].length; j++) {
				img[img.length - 1 - i][j] = img[i][j];
			}
		}
		return img;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * the mirrorDiagonal method mirrors the top left of the image onto the bottom right
	 * @param image the image to mirror (will truncate to a square)
	 * @return the mirrored image
	 */
	public static int[][][] mirrorDiagonal(int[][][] image){
		int[][][] img = makeSquare(image);
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length - i; j++) {
				img[img[0].length - 1 - j][img.length - 1 - i] = img[i][j];
			}
		}
		return img;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * the negate method negates each pixel, inverting the image
	 * @param image the image to negate
	 * @return the negated image
	 */
	public static int[][][] negate(int[][][] image){
		int[][][] img = copy(image);
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				for (int color = 0; color < 3; color++) {
					img[i][j][color] = 255 - img[i][j][color];
				}
			}
		}
		return img;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * the negate method negates each pixel, inverting the image
	 * @param image the image to negate
	 * @return the negated image
	 */
	public static int[][] negate(int[][] image){
		int[][] img = copy(image);
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
					img[i][j] = 255 - img[i][j];
			}
		}
		return img;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * the zero method takes the specified color channel and makes its value zero on the image
	 * @param image the image to zero the color from
	 * @param color the color channel wished to zero (1,2, or 3)
	 * @return the zeroed image
	 */
	public static int[][][] zero(int[][][] image, int color){
		int[][][] img = copy(image);
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				img[i][j][color] = 0;
			}
		}
		return img;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * the keep method takes the specified color channel and makes the other channels values zero on the image
	 * @param image the image to keep the color on
	 * @param color the color channel wished to keep (0,1, or 2)
	 * @return the changed image
	 */
	public static int[][][] keep(int[][][] image, int color){
		image = zero(image, (color + 1)%3);
		image = zero(image, (color + 2)%3);
		return image;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * the swapColors method switches 2 specified colors channels in an image
	 * @param image the image to change
	 * @param color1 the first color channel to switch
	 * @param color2 the other color channel to switch with
	 * @return the switched image
	 */
	public static int[][][] swapColors(int[][][] image, int color1, int color2){
		int[][][] img = copy(image);
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				int temp = img[i][j][color1];
				img[i][j][color1] = img[i][j][color2];
				img[i][j][color2] = temp;
			}
		}
		return img;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * the subtract method takes two images and subtracts the pixel 
	 * values and returns the normalized image
	 * @param image1 the first image
	 * @param image2 the image to subtract from image1
	 * @return the normalized subtracted image
	 */
	public static int[][][] subtract(int[][][] image1, int[][][] image2){
		int[][][][] imgs = makeSameSize((image1), (image2));
		int[][][] img = new int[imgs.length][imgs[0].length][3];
		for (int i = 0; i < imgs.length; i++) {
			for (int j = 0; j < imgs[0].length; j++) {
				img[i][j][0] = (imgs[i][j][0][0] - imgs[i][j][0][1]);
				img[i][j][1] = (imgs[i][j][1][0] - imgs[i][j][1][1]);
				img[i][j][2] = (imgs[i][j][2][0] - imgs[i][j][2][1]);
			}
		}
		return (normalize(img));
	}
	//Helper for making two images the same size
	private static int[][][][] makeSameSize(int[][][] image1, int[][][] image2){
		int[][][] img1 = copy(image1);
		int[][][] img2 = copy(image2);
		int[][][][] imgs = new int[Math.max(img1.length, img2.length)][Math.max(img1[0].length, img2[0].length)][3][2];
		for (int i = 0; i < img1.length; i++) {
			for (int j = 0; j < img1[0].length; j++) {
				imgs[i][j][0][0] = img1[i][j][0];	
				imgs[i][j][1][0] = img1[i][j][1];	
				imgs[i][j][2][0] = img1[i][j][2];	
			}
		}
		for (int i = 0; i < img2.length; i++) {
			for (int j = 0; j < img2[0].length; j++) {
				imgs[i][j][0][1] = img2[i][j][0];	
				imgs[i][j][1][1] = img2[i][j][1];	
				imgs[i][j][2][1] = img2[i][j][2];		
			}
		}
		return imgs;
	}	
	//---------------------------------------------------------------------------------------------
	/**
	 * the add method takes two images and adds the pixel 
	 * values and returns the normalized image
	 * @param image1 the first image
	 * @param image2 the image to add to image1
	 * @return the normalized added image
	 */
	public static int[][][] add(int[][][] image1, int[][][] image2){
		int[][][][] imgs = makeSameSize(image1, image2);
		int[][][] img = new int[imgs.length][imgs[0].length][3];
		for (int i = 0; i < imgs.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				img[i][j][0] = (imgs[i][j][0][0] + imgs[i][j][0][1]);
				img[i][j][1] = (imgs[i][j][1][0] + imgs[i][j][1][1]);
				img[i][j][2] = (imgs[i][j][2][0] + imgs[i][j][2][1]);
			}
		}
		return normalize(img);
	}
	//----------------------------------------------------------------------------------------------
	/**
	 * the blend method blends two images together using an alpha value [0,1]
	 * @param image1 first image
	 * @param image2 second image
	 * @param alpha alpha value wanted [0,1]
	 * @return the blended image
	 */
	public static int[][][] blend(int[][][] image1, int[][][] image2, double alpha){
		int[][][][] imgs = makeSameSize(image1, image2);
		int[][][] img = new int[imgs.length][imgs[0].length][3];
		for (int i = 0; i < imgs.length; i++) {
			for (int j = 0; j < imgs[0].length; j++) {
				img[i][j][0] = (int) ((alpha * imgs[i][j][0][0]) + ((1 - alpha) * imgs[i][j][0][1]));
				img[i][j][1] = (int) ((alpha * imgs[i][j][1][0]) + ((1 - alpha) * imgs[i][j][1][1]));
				img[i][j][2] = (int) ((alpha * imgs[i][j][2][0]) + ((1 - alpha) * imgs[i][j][2][1]));
			}
		}
		return img;
	}
	public static int[][] blend(int[][] image1, int[][] image2, double alpha){
		int[][] img = new int[image2.length][image2[0].length];
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				img[i][j] = (int) ((alpha * image1[i][j] + ((1 - alpha) * image2[i][j])));
			}
		}
		return img;
	}
	//----------------------------------------------------------------------------------------------
	/**
	 * the mirror arms method mirrors the snowman's arms from top to bottom
	 * to make the snowman have 4 arms
	 * @param snow the snowman to mirror arms
	 * @return snowman with 4 arms
	 */
	public static int[][][] mirrorArms(int[][][] snow){
		int[][][] snowman = copy(snow);
		int[][][] armL = new int[68][65][3];
		int[][][] armR = new int[68][65][3];
		armL = copyRange(snowman, 157, 104, 225, 169, armL, 0, 0);
		armR = copyRange(snowman, 157, 239, 225, 304, armR, 0, 0);
		snowman = copyRange(mirrorVertical(armL), 0, 0, 67, 64, snowman, 157, 105);
		snowman = copyRange(mirrorVertical(armR), 0, 0, 67, 64, snowman, 157, 239);
		return snowman;
	}
	//----------------------------------------------------------------------------------------------
	/**
	 * the fixTemple method fixes the broken portion of the temple image
	 * @param temp the temple to fix
	 * @return the fixed temple
	 */
	public static int[][][] fixTemple(int[][][] temp){
		int[][][] temple = copy(temp);
		int[][][] tempHalf = new int[116][261][3];
		tempHalf = copyRange(temple, 28, 16, 144, 277, tempHalf, 0, 0);
		temple = copyRange(flipHorizontal(tempHalf), 0, 0, 115, 260, temple, 28, 278);
		return temple;
	}
	//----------------------------------------------------------------------------------------------
	/**
	 * the copyRange image copies one portion of an image onto a second image
	 * @param image1 image to copy from
	 * @param r1 first image row start
	 * @param c1 first image column start
	 * @param r2 first image row end
	 * @param c2 first image column end
	 * @param image2 image to copy section to
	 * @param startR second image row start
	 * @param startC second image column start
	 * @return second image with copied part of the first image
	 */
	public static int[][][] copyRange(int[][][] image1, int r1, int c1, int r2, int c2, int[][][] image2, int startR, int startC){
		int[][][] img1 = copy(image1);
		int[][][] img2 = copy(image2);
		for(int i = r1, r = startR; i <= r2; i++, r++) {
			for(int j = c1, c =startC; j <= c2; j++, c++) {
				if(r < image2.length && c < image2[0].length) {
					img2[r][c][0] = img1[i][j][0];
					img2[r][c][1] = img1[i][j][1];
					img2[r][c][2] = img1[i][j][2];
				}
			}
		}
		
		return img2;
	}
	//----------------------------------------------------------------------------------------------
	/**
	 * the copyRange image copies one portion of an image onto a second image
	 * @param img1 image to copy from
	 * @param r1 first image row start
	 * @param c1 first image column start
	 * @param r2 first image row end
	 * @param c2 first image column end
	 * @param image2 image to copy section to
	 * @param startR second image row start
	 * @param startC second image column start
	 * @return second image with copied part of the first image
	 */
	public static int[][] copyRange(int[][] img1, int r1, int c1, int r2, int c2, int[][] image2, int startR, int startC){
		int[][] img2 = copy(image2);
		for(int i = r1, r = startR; i <= r2; i++, r++) {
			for(int j = c1, c = startC; j <= c2; j++, c++) {
				if(r < img2.length && c < img2[0].length) {
					img2[r][c] = img1[i][j];
				}
			}
		}
		return img2;
	}
	public static double[][] copyRange(double[][] img1, int r1, int c1, int r2, int c2, double[][] image2, int startR, int startC){
		double[][] img2 = copy(image2);
		for(int i = r1, r = startR; i <= r2; i++, r++) {
			for(int j = c1, c = startC; j <= c2; j++, c++) {
				if(r < img2.length && c < img2[0].length) {
					img2[r][c] = img1[i][j];
				}
			}
		}
		return img2;
	}
	//------------------------------------------------------------------------------------------------
	/**
	 * the encode method takes a message on an image and hides it in the
	 * image specified, which the human eye cannot tell the difference
	 * @param image image to hide message in
	 * @param message the message to hide in the image
	 * @return the image with hidden message
	 */
	public static int[][][] encode(int[][][] image, int[][] message){
		int[][][] img = makeEven(image);
		int[][] msg = makeOneZero(message);
		for(int i = 0; i < img.length; i++ ) {
			for (int j = 0; j < img[0].length; j++) {
				img[i][j][0] += msg[i][j];
			}
		}
		return img;
	}
	//helper for encode
	private static int[][] makeOneZero(int[][] message){
		int[][] msg = copy(message);
		for(int i = 0; i < msg.length; i++) {
			for(int j = 0; j < msg[0].length; j++) {
				if (msg[i][j] > 127) 
					msg[i][j] = 1;
				else
					msg[i][j] = 0;
			}
		}
		return msg;
	}
	//helper for encode
	private static int[][][] makeEven(int[][][] image){
		int[][][] img = copy(image);
		for(int i = 0; i < img.length; i++) {
			for(int j = 0; j < img[0].length; j++) {
				if(img[i][j][0] % 2 == 1) {
					img[i][j][0] -= 1;
				}
			}
		}
		return img;
	}
	//------------------------------------------------------------------------------------------------
	/**
	 * the decode method take and encoded image and 
	 * decodes it to see what the message is
	 * @param encoded the encoded image
	 * @return the hidden message
	 */
	public static int[][] decode(int[][][] encoded){
		int[][][] encode = copy(encoded);
		int[][] msg = new int[encode.length][encode[0].length];
		for(int i = 0; i < encode.length; i++) {
			for(int j = 0; j < encode[0].length; j++) {
				if(encode[i][j][0]%2 == 1) 
					msg[i][j] = 255;
				else
					msg[i][j] = 0;
			}
		}
		return msg;
		
	}
	//------------------------------------------------------------------------------------------------
	/**
	 * the chroma method is what we commonly refer to as green screen,
	 * and it takes the subject image and replaces the one color background 
	 * with the background image specified
	 * @param sub subject image
	 * @param back background image
	 * @param red value of red channel in the subjects background
	 * @param green value of green channel in the subjects background
	 * @param blue value of blue channel in the subjects background
	 * @param end 
	 * @return the "green screen" image
	 */
	public static int[][][] chroma(int[][][] sub, int[][][] back, int red, int green, int blue, int end){
		int[][][] subject = copy(sub);
		int[][][] background = copy(back);
		int[] comparison = {red, green, blue};
		for(int i = 0; i < subject.length; i++) {
			for(int j = 0; j < subject[0].length; j++) {
				if(distance(subject[i][j], comparison) < end) {
					subject[i][j][0] = background[i][j][0];
					subject[i][j][1] = background[i][j][1];
					subject[i][j][2] = background[i][j][2];
				}
			}
		}
		return subject;
	}
	//------------------------------------------------------------------------------------------------
	/**
	 * creatCollage makes my own random collage with 3 different images or the same. i dont care.
	 * @param imageSpam1 first image
	 * @param imageSpam2 second image
	 * @param imageSpam3 third image
	 * @param loop how many times you want it to loop
	 * @return the collage
	 */
	public static int[][][] creatCollage(int[][][] imageSpam1, int[][][] imageSpam2, int[][][] imageSpam3, int loop){
		int[][][] image = new int[1400][1400][3];
		int[][][] imgSpam;
		int[][][] nextStep;
		for(int i = 0; i < loop; i++) {
			//imgSpam = makeSquare(choosRandoImage(imageSpam1, imageSpam2, imageSpam3));
			imgSpam = makeSquare(imageSpam1);
			imgSpam = randomOperation(imgSpam, (int)(Math.random() * 12));
			nextStep  = copyRange(imgSpam, 0, 0, imgSpam.length - 1, imgSpam[0].length - 1, image, (int) (Math.random() * 1400), (int) (Math.random() * 1400));
			image = blend(nextStep, image, .60);
			System.out.println(i);
		}
		return image;
	}
	//helpers for creatCollage
	private static int[][][] choosRandoImage(int[][][] image1, int[][][] image2, int[][][] image3){
		int[][][] chosen = image1;
		switch((int)(Math.random() * 3)) {
		case 0:
			break;
		case 1:
			chosen = image2;
			break;
		case 2:
			chosen = image3;
		}
		return chosen;
	}
	
	private static int[][][] randomOperation(int[][][] image, int choice){
		switch(choice) {
			case 0:
				System.out.println("Horizontal Complete");
				image = flipHorizontal(image);
				break;
			case 1:
				System.out.println("Vertical Complete");
				image = flipVertical(image);
				break;
			case 2:
				System.out.println("Diagonal Complete");
				image = flipDiagonal(image);
				break;
			case 3:
				System.out.println("rotate90 Complete");
				image = rotate90(image);
				break;
			case 4:
				System.out.println("Horizontal mirror Complete");
				image = mirrorHorizontal(image);
				break;
			case 5:
				System.out.println("Vertical mirror Complete");
				image = mirrorVertical(image);
				break;
			case 6:
				System.out.println("Diagonal mirror Complete");
				image = mirrorDiagonal(image);
				break;
			case 7:
				System.out.println("negate Complete");
				image = negate(image);
				break;
			case 8:
				System.out.println("zero Complete");
				image = zero(image, (int) (Math.random() * 3));
				break;
			case 9:
				System.out.println("keep Complete");
				image = keep(image, (int) (Math.random() * 3));
				break;
			case 10: 
				System.out.println("swapColors Complete");
				image = swapColors(image, (int) (Math.random() * 3), (int) (Math.random() * 3));
				break;
			default:
				System.out.println("No Operation");
				break;
		}
		return image;
	}




















}
