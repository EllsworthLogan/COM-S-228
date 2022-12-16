package edu.iastate.cs228.hw1;

/**
 * Represents a customer in the town. Customers change types each month
 * based on who their neighbors are.
 * 
 * @author Logan Ellsworth
 */
public abstract class TownCell {

	/**
	 * Respresents the Town object that the cell belongs to,
	 * and the row and column in which it is positioned
	 */
	protected Town plain;
	protected int row;
	protected int col;
	
	/**
	 * Relates a position in the grid to an int value
	 */
	int middle = 0;
	int topRow = 1;
	int bottomRow = 2;
	int leftSide = 3;
	int rightSide = 4;
	int tlCorner = 5;
	int trCorner = 6;
	int blCorner = 7;
	int brCorner = 8;
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	/**
	 * Constructs a new TownCell object
	 * 
	 * @param Town p
	 * @param row
	 * @param column
	 */
	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 

		// check the position to see which ways you can check
		if(this.determinePosition() == middle) {
			//above
			this.increment(plain.grid[row - 1][col].who(), nCensus); 
			//below
			this.increment(plain.grid[row + 1][col].who(), nCensus);
			//left
			this.increment(plain.grid[row][col - 1].who(), nCensus);
			//right
			this.increment(plain.grid[row][col + 1].who(), nCensus);
			//upleft
			this.increment(plain.grid[row - 1][col - 1].who(), nCensus);
			//upright
			this.increment(plain.grid[row - 1][col + 1].who(), nCensus);
			//downleft
			this.increment(plain.grid[row + 1][col - 1].who(), nCensus);
			//downright
			this.increment(plain.grid[row + 1][col + 1].who(), nCensus);
		}
		else if(this.determinePosition() == topRow) {
			//left
			this.increment(plain.grid[row][col - 1].who(), nCensus);
			//right
			this.increment(plain.grid[row][col + 1].who(), nCensus);
			//accounts for grids of length 1
			if(plain.getLength() > 1) {
				//below
				this.increment(plain.grid[row + 1][col].who(), nCensus);
				//downleft
				this.increment(plain.grid[row + 1][col - 1].who(), nCensus);
				//downright
				this.increment(plain.grid[row + 1][col + 1].who(), nCensus);
			}
		}
		else if(this.determinePosition() == bottomRow) {
			//left
			this.increment(plain.grid[row][col - 1].who(), nCensus);
			//right
			this.increment(plain.grid[row][col + 1].who(), nCensus);
			//accounts for towns of length 1
			if(plain.getLength() > 1) {
				//above
				this.increment(plain.grid[row - 1][col].who(), nCensus);
				//upleft
				this.increment(plain.grid[row - 1][col - 1].who(), nCensus);
				//upright
				this.increment(plain.grid[row - 1][col + 1].who(), nCensus);
			}
		}
		else if(this.determinePosition() == leftSide) {
			//above
			this.increment(plain.grid[row - 1][col].who(), nCensus);
			//below
			this.increment(plain.grid[row + 1][col].who(), nCensus);
			//accounts for towns of width 1
			if(plain.getWidth() > 1) {
				//right
				this.increment(plain.grid[row][col + 1].who(), nCensus);
				//upright
				this.increment(plain.grid[row - 1][col + 1].who(), nCensus);
				//downright
				this.increment(plain.grid[row + 1][col + 1].who(), nCensus);
			}
		}
		else if(this.determinePosition() == rightSide) {
			//above
			this.increment(plain.grid[row - 1][col].who(), nCensus);
			//below
			this.increment(plain.grid[row + 1][col].who(), nCensus);
			//accounts for grids of width 1
			if(plain.getWidth() > 1) {
				//left
				this.increment(plain.grid[row][col - 1].who(), nCensus);
				//upleft
				this.increment(plain.grid[row - 1][col - 1].who(), nCensus);
				//downleft
				this.increment(plain.grid[row + 1][col - 1].who(), nCensus);
			}
		}
		else if(this.determinePosition() == tlCorner) {
			if(plain.getWidth() > 1) {
				//right
				this.increment(plain.grid[row][col + 1].who(), nCensus);
			}
			if(plain.getLength() > 1) {
				//below
				this.increment(plain.grid[row + 1][col].who(), nCensus);
			}
			if(plain.getWidth() > 1 && plain.getLength() > 1) {
				//downright
				this.increment(plain.grid[row + 1][col + 1].who(), nCensus);
			}
		}
		else if(this.determinePosition() == trCorner) {
			if(plain.getWidth() > 1) {
				//left
				this.increment(plain.grid[row][col - 1].who(), nCensus);
			}
			if(plain.getLength() > 1) {
				//below
				this.increment(plain.grid[row + 1][col].who(), nCensus);
			}
			if(plain.getWidth() > 1 && plain.getLength() > 1) {
				//downleft
				this.increment(plain.grid[row + 1][col - 1].who(), nCensus);
			}
		}
		else if(this.determinePosition() == blCorner) {
			if(plain.getWidth() > 1) {
				//right
				this.increment(plain.grid[row][col + 1].who(), nCensus);
			}
			if(plain.getLength() > 1) {
				//above
				this.increment(plain.grid[row - 1][col].who(), nCensus);
			}
			if(plain.getWidth() > 1 && plain.getLength() > 1) {
				//upright
				this.increment(plain.grid[row - 1][col + 1].who(), nCensus);
			}
		}
		else if(this.determinePosition() == brCorner) {
			if(plain.getWidth() > 1) {
				//left
				this.increment(plain.grid[row][col - 1].who(), nCensus);
			}
			if(plain.getLength() > 1) {
				//above
				this.increment(plain.grid[row - 1][col].who(), nCensus);
			}
			if(plain.getWidth() > 1 && plain.getLength() > 1) {
				//upleft
				this.increment(plain.grid[row - 1][col - 1].who(), nCensus);
			}
		}
	}
		
	/**
	 * Determines the position of the TownCell Object
	 * in relation to the entire Town grid.
	 * 
	 * @return position as an int
	 */
	public int determinePosition() {
		// fartop
		if (row == 0) {
			// Top left corner
			if (col == 0) {
				return tlCorner;
			}
			// Top right corner
			else if (col == plain.getWidth() - 1) {
				return trCorner;
			}
			//not in a corner
			else {
				return topRow;
			}
		}
		// farBottom
		else if (row == plain.getLength() - 1) {
			// Bottom left corner
			if (col == 0) {
				return blCorner;
			}
			// Bottom right corner
			else if (col == plain.getWidth() - 1) {
				return brCorner;
			}
			// not in a corner
			else {
				return bottomRow;
			}
		}
		// farLeft and not top or bottom (implicitly implied by the else ifs)
		else if (col == 0) {
			return leftSide;
		}
		// farRight
		else if (col == plain.getWidth() - 1) {
			return rightSide;
		}
		// in "middle" of town
		else {
			return middle;
		}
	}
	
	/** 
	 * Increments the nCensus array for each of the State types in the neighborhood
	 * 
	 * @param State, int nCensus[]
	 */
	public void increment(State state, int[] nCensus) {
		if (state == State.RESELLER){
			nCensus[0] += 1;
		}
		else if (state == State.EMPTY){
			nCensus[1] += 1;
		}
		else if (state == State.CASUAL){
			nCensus[2] += 1;
		}
		else if (state == State.OUTAGE){
			nCensus[3] += 1;
		}
		//state == State.STREAMER
		else {
			nCensus[4] += 1;
		}
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}
