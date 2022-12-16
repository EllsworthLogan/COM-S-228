package edu.iastate.cs228.hw1;

/**
 * Represents a customer of type Empty.
 * 
 * @author Logan Ellsworth
 */
public class Empty extends TownCell {
	
	/**
	 * Constructs an Empty user
	 * 
	 * @param Town p
	 * @param row
	 * @param column
	 */
	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		State s = State.EMPTY;
		return s;
	}
	
	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	@Override
	public TownCell next(Town tNew) {
		// empty becomes casual
		this.census(nCensus);
		if (nCensus[1] + nCensus[3] <= 1) {
			return new Reseller(this.plain, this.row, this.col);
		}
		else
			return new Casual(this.plain, this.row, this.col);
	}
	

}
