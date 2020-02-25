package pencilSketch;
import static pixlab.Helpers.*;
public class FinalCombination {

	/**
	 * Combines both main branches of the pencilsketch process
	 * @param tonalTexture The tonalTexture layer
	 * @param sketch The line drawing sketch layer
	 * @return The array of the combined layers
	 */
	public static int[][] combine(double[][] tonalTexture, double[][] sketch)
	{
		double[][] finalProduct = new int[tonalTexture.length][tonalTexture[0].length];
		
		Tone.norm01(tonalTexture);
		Tone.norm01(sketch);		
		
		for(int i = 0; i < finalProduct.length; i++)
		{
			for(int j = 0; j < finalProduct[0].length; j++)
			{
				finalProduct[i][j] = tonalTexture[i][j] * sketch[i][j];
			}
		}
		
		Helpers.toIntArray(Helpers.normalize(finalProduct);
		
		return finalProduct;
	}
	
}
