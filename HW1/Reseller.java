package edu.iastate.cs228.hw1;

/**
 * Represents a customer of type Reseller.
 * 
 * @author Logan Ellsworth
 */
public class Reseller extends TownCell {
	
	/**
	 * Constructs a Reseller user
	 * 
	 * @param Town p
	 * @param row
	 * @param column
	 */
	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		State s = State.RESELLER;
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
		// if there are 3 or fewer casuals in the neighborhood, reseller to empty
		// also, if there are 3 or more empty cells in the neighborhood, reseller to empty
		this.census(nCensus);
		if (nCensus[2] <= 3) {
			return new Empty(this.plain, this.row, this.col);
		}
		else if (nCensus[1] >= 3) {
			return new Empty(this.plain, this.row, this.col);
		}
		else if (nCensus[2] >= 5) {
			return new Streamer(this.plain, this.row, this.col);
		}
		else return new Reseller(this.plain, this.row, this.col);
	}
}
