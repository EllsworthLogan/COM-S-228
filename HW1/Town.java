package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 * Represents a Town. Contains a grid[][] in which the TownCell
 * objects are contained.
 * 
 *  @author Logan Ellsworth
 */
public class Town {
	
	/**
	 * Variables representing the length and width of the town and the 2D array grid.
	 */
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		grid = new TownCell[length][width];
		this.length = length;
		this.width = width;
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File usrFile = new File(inputFileName);
		Scanner fileScnr = new Scanner(usrFile);
		int length = fileScnr.nextInt();
		this.length = length;
		int width = fileScnr.nextInt();
		this.width = width;
		Town usrTown = new Town((int)length, (int)width);
			//read through the file and populate the grid
		for (int i = 0; i < usrTown.getLength(); i++) {
			for (int j = 0; j < usrTown.getWidth(); j++) {
				if(fileScnr.hasNext()) {
					String currLetter = fileScnr.next();
					if(currLetter.equals("R")) {
						usrTown.grid[i][j] = new Reseller(this, i, j);
					}
					else if(currLetter.equals("E")) {
						usrTown.grid[i][j] = new Empty(this, i, j);
					}
					else if(currLetter.equals("C")) {
						usrTown.grid[i][j] = new Casual(this, i, j);
					}
					else if(currLetter.equals("O")) {
						usrTown.grid[i][j] = new Outage(this, i, j);
					}
					else if(currLetter.equals("S")) {
						usrTown.grid[i][j] = new Streamer(this, i, j);
					}
				}
			}
			if(fileScnr.hasNext()) {
				fileScnr.nextLine();
			}
		}
		fileScnr.close();
		// point this.grid to the grid created above
		this.grid = usrTown.grid;
	}
	
	/**
	 * Returns width of the grid.
	 * 
	 * @return width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * 
	 * @return length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 * 
	 * @param seed
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		//loop through the col, rows and populate grid
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				//gets a random number for each of the locations and correlates that number to a type
				int randomType = rand.nextInt(5);
				if (randomType == 0)
					grid[i][j] = new Reseller(this, i, j);
				else if (randomType == 1) {
					grid[i][j] = new Empty(this, i, j);
				}
				else if (randomType == 2) {
					grid[i][j] = new Casual(this, i, j);
				}
				else if (randomType == 3) {
					grid[i][j] = new Outage(this, i, j);
				}
				//randomType == 4
				else {
					grid[i][j] = new Streamer(this, i, j);
				}
			}
		}	
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 * 
	 * @return grid as a string
	 */
	@Override
	public String toString() {
		String s = "";	
		// loop through the col, rows and concatonate the string
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				if (grid[i][j].who() == State.RESELLER) {
					s += "R";
				}
				else if (grid[i][j].who() == State.EMPTY) {
					s += "E";
				}
				else if (grid[i][j].who() == State.CASUAL) {
					s += "C";
				}
				else if (grid[i][j].who() == State.OUTAGE) {
					s += "O";
				}
				//Streamer
				else {
					s += "S";
				}
				s += " ";
			}
			s += "\n";
		}
		return s;
	}
}
