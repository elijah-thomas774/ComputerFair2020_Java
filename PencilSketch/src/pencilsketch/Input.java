package pencilsketch;
/**
 * The Input Class provides various Input Validation utilities  
 * @author Elijah Thomas
 * @version August 23, 2018
 */

import java.util.Scanner;
public class Input {
	
	/** The Scanner used to take user input from console*/
	private static Scanner keyboard = new Scanner(System.in);

	
	public static String readString(String prompt) {
		System.out.print(prompt);
		return keyboard.nextLine();
	}
	/////////////////////////////////////////////////////////////////////
	/** the readInt method will ask for a number according to the users want, and
	 * verifies if what the user enters is an integer
	 * 
	 * @param prompt the instructions for the user
	 * @return the validated integer 
	 */
	public static int readInt(String prompt){
		int number;	//declares the number user is gonna want
		String message; //this is for reading in what user types, to specifically say what they did wrong
		System.out.print(prompt);//displays prompt given
		while (!keyboard.hasNextInt()){//keeps looping until user enters a valid integer
			message = keyboard.nextLine();//reads in what users entered
			System.out.print(message + " is not a valid integer, try harder\n"
					+ prompt);						//this displays if user entered wrong int
		}
		number = keyboard.nextInt();//sets number into what user entered after seeing if it works
		keyboard.nextLine();//cleans up 
		return number;
	}
	/////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////
	/** the readDouble method will ask for a number according to the users want, and
	 * verifies if what the user enters is a double
	 * 
	 * @param prompt the instructions for the user
	 * @return the validated double
	 */
	public static double readDouble(String prompt){
		//same thing as readInt(), but needs a double
		double number;	
		String message;
		System.out.print(prompt);
		while (!keyboard.hasNextDouble()){
			message = keyboard.nextLine();
			System.out.print(message + " is not a valid double, try harder\n"
					+ prompt);						
		}
		number = keyboard.nextDouble();
		keyboard.nextLine();
		return number;
	}
	/////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////
	/**
	 * this readNum Method will prompt the user for an integer and verify it is,
	 *  but it will have to be less than the max number
	 * 
	 * @param prompt the instructions for the user
	 * @param max the maximum integer that the user can enter
	 * @return the validated integer
	 */
	public static int readNum(String prompt, int max){
		int number;//declares number user wants
		do{//does loop once to start
			number = readInt(prompt);//goes to integer method
			if (number > max)//checking if users number was above max
				System.out.println(number + " is not within range, try harder");
		}while (number > max);//end when the number the user enters is less than max
		return number;//returns final number
	}
	/////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////
	/**
	 * this readNum Method will prompt the user for an integer and verify it is,
	 *  but it will have to be greater than the minimum number
	 * 
	 * @param prompt the instructions for the user
	 * @param min the minimum integer that the user can enter
	 * @return the validated integer
	 */
	public static int readNum(int min, String prompt){
		int number;//declares number
		do{//does loop first time regardless
			number = readInt(prompt);//calls readint to gather an int from user
			if (number < min)//checking for it to be less than min
				System.out.println(number + " is not within range, try harder");
		}while (number < min);		//ensds loop when number is greater than min
		return number;//returns the users number
	}
	/////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////
	/**
	 * this readNum method will prompt the user for an integer and verify it is,
	 * but it will have to be between the minimum and max number
	 * 
	 * @param prompt the instructions for the user
	 * @param min the minimum integer that the user can enter 
	 * @param max the maximum integer that the user can enter
	 * @return the validated integer
	 */
	public static int readNum(String prompt, int min, int max){
		int number;//declare number
		do{//does loop first time regardeless
			number = readInt(prompt);//calls intput check method fo rinteger
			if (number > max ||number < min) //if its outside range, itll try again
				System.out.println(number + " is not within range, try harder");
		}while (number > max || number < min);		//keeps going until in range
		return number;//gives user number
	}
	/////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////
	/**
	 * this readNum method will switch between the readNum for max integer 
	 * or the readNum for min integer
	 * 
	 * @param prompt the instructions for the user
	 * @param limit the integer that is either the maximum or minimum
	 * @param minMax the boolean to change the limit to either min (true) or max (false)
	 * @return the validated integer
	 */
	public static int readNum(String prompt, int limit, boolean minMax){
		int number;//declare number
		if (minMax)//if minMax is true, meaning the limit is min, do the same thing as readNum(min, string)
			number = readNum(limit, prompt);
		else//if minMax is false, meaning the limit is max, do the same thing as readNum(string, max)
			number = readNum(prompt, limit);	
		return number;
	}
	/////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////
	/**
	 * this readNum Method will prompt the user for a double and verify it is,
	 *  but it will have to be less than the max number
	 * 
	 * @param prompt the instructions for the user
	 * @param max the maximum double that the user can enter
	 * @return the validated double
	 */
	public static double readNum(String prompt, double max){
		double number;
		do{
			number = readDouble(prompt);
			if (number > max)
				System.out.println(number + " is not within range, try harder");
		}while (number > max);
		return number;
	}
	/////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////
	/**
	 * this readNum Method will prompt the user for a double and verify it is,
	 *  but it will have to be greater than the minimum number
	 * 
	 * @param prompt the instructions for the user
	 * @param min the minimum double that the user can enter
	 * @return the validated double
	 */
	public static double readNum(double min, String prompt){
		double number;
		do{
			number = readDouble(prompt);
			if (number < min)
				System.out.println(number + " is not within range, try harder");
		}while (number < min);		
		return number;
	}
	/////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////
	/**
	 * this readNum method will prompt the user for a double and verify it is,
	 * but it will have to be between the minimum and max number
	 * 
	 * @param prompt the instructions for the user
	 * @param min the minimum double that the user can enter 
	 * @param max the maximum double that the user can enter
	 * @return the validated double
	 */
	public static double readNum(String prompt, double min, double max){
		double number;
		do{
			number = readDouble(prompt);
			if (number > max ||number < min)
				System.out.println(number + " is not within range, try harder");
		}while (number > max || number < min);		
		return number;
	}
	/////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////
	/**
	 * this readNum method will switch between the readNum for max double 
	 * or the readNum for min double
	 * 
	 * @param prompt the instructions for the user
	 * @param limit the double that is either the maximum or minimum
	 * @param minMax the boolean to change the limit to either min (true) or max (false)
	 * @return the validated double
	 */
	public static double readNum(String prompt, double limit, boolean minMax){
		double number;
		if (minMax)
			number = readNum(limit, prompt);
		else
			number = readNum(prompt, limit);	
		return number;
	}
	/////////////////////////////////////////////////////////////////////
}//end class
