package edu.iastate.cs228.hw1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 * 
 * @author Logan Ellsworth
 */
public class ISPBusiness {
	
	/**
	 * Respresents the total number of Casuals
	 * throughout the entire 12 month cycle
	 */
	public static double profit = 0;
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * 
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		for (int i = 0; i < tOld.getLength(); i++) {
			for (int j = 0; j < tOld.getWidth(); j++) {
				tNew.grid[i][j] = tOld.grid[i][j].next(tNew);
			}
		}
		tOld = deepCopyTown(tOld, tNew);
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * 
	 * @param town
	 * @return number of Casuals in the town
	 */
	public static int getProfit(Town town) {
		int numCasuals = 0;
		// loop through the entire town
		for (int i = 0; i < town.getLength(); i++) {
			for (int j = 0; j < town.getWidth(); j++) {
				if (town.grid[i][j].who() == State.CASUAL) {
					numCasuals += 1;
				}
			}
		}
		profit += numCasuals;
		return numCasuals;
	}
	
	/**
	 * Helper method to deep copy the tNew town in order
	 * to reuse tOld and tNew
	 * 
	 * @param tOld, tNew
	 * @return tOld
	 */
	public static Town deepCopyTown(Town tOld, Town tNew) {
		for (int i = 0; i < tOld.getLength(); i++) {
			for (int j = 0; j < tOld.getWidth(); j++) {
				tOld.grid[i][j] = tNew.grid[i][j];
			}
		}
		return tOld;
	}
	
	/**
	 * Helper method used to calculate total profit
	 * 
	 * @param town
	 * @return total profit
	 */
	public static double getTotalProfit(Town town) {
		double numPossible = town.getLength() * town.getWidth();
		double totalProfit = (100 * profit) / (numPossible * 12);
		return totalProfit;
	}

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String []args) {
		Scanner scnr = new Scanner(System.in);
		int gridType = 0;
		boolean askType = true;
		Town usrTown = new Town(0, 0);
		while (askType == true) {
			System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed");
			try {
				gridType = scnr.nextInt();
			}
			catch (Exception e){
				askType = false;
				System.out.println("Program terminated. Invalid value.");
				scnr.close();
				return;
			}
			if(gridType == 1) {
				askType = false;
				// create and read a file
				// assign values to correct variables.
				System.out.println("Please enter file path: ");
				try {
					usrTown = new Town(scnr.next());
				} catch (FileNotFoundException e) {
					System.out.println("Program terminated. Invalid file name.");
					scnr.close();
					return;
				}
			}
			// Need not worry about validating the values of rows and columns input by the user. 
			// We are assuming that user is only providing positive integers for these. 
			else if (gridType == 2) {
				askType = false;
				System.out.println("Provide rows, cols and seed integer separated by spaces: ");
				int length = scnr.nextInt();
				int width = scnr.nextInt();
				int seed = scnr.nextInt();
				usrTown = new Town(length, width);
				usrTown.randomInit(seed);
			}
		}
		scnr.close();
		// simulates the 12 month billing cycle
		for(int i = 0; i < 12; i++) {
			updatePlain(usrTown);
			getProfit(usrTown);
		}
		// prints the profit as a formatted double to 2 decimal places
		System.out.println(String.format("%.2f", getTotalProfit(usrTown)) + "%");
	}
}
