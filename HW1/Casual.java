package edu.iastate.cs228.hw1;

/**
 * Represents a customer of type Casual.
 * 
 * @author Logan Ellsworth
 */
public class Casual extends TownCell {

	/**
	 * Constructs a Casual user
	 * 
	 * @param Town p
	 * @param row
	 * @param column
	 */
	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}
	
	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		State s = State.CASUAL;
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
		// if there is a reseller in the neighborhood, casual to outage
		// otherwise if there is a streamer in the neighborhood, casual to streamer 
		this.census(nCensus);
		if (nCensus[1] + nCensus[3] <= 1) {
			return new Reseller(this.plain, this.row, this.col);
		}
		else if (nCensus[0] > 0) {
			return new Outage(this.plain, this.row, this.col);
		}
		else if (nCensus[4] > 0) {
			return new Streamer(this.plain, this.row, this.col);
		}
		else if (nCensus[2] >= 5) {
			return new Streamer(this.plain, this.row, this.col);
		}
		else 
			return new Casual(this.plain, this.row, this.col);
	}
	
}
