package edu.iastate.cs228.hw1;

/**
 * Represents a customer of type Outage.
 * 
 * @author Logan Ellsworth
 */
public class Outage extends TownCell {
	
	/**
	 * Constructs an Outage user
	 * 
	 * @param Town p
	 * @param row
	 * @param column
	 */
	public Outage(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		State s = State.OUTAGE;
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
		// Outage becomes empty cell
		this.census(nCensus);
		if (nCensus[2] >= 5) {
			return new Streamer(this.plain, this.row, this.col);
		}
		else return new Empty(this.plain, this.row, this.col);
	}
	

}
