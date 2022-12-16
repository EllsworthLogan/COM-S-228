package edu.iastate.cs228.hw1;

/**
 * Represents a customer of type Streamer.
 * 
 * @author Logan Ellsworth
 */
public class Streamer extends TownCell {
	
	/**
	 * Constructs a Streamer user
	 * 
	 * @param Town p
	 * @param row
	 * @param column
	 */
	public Streamer(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		State s = State.STREAMER;
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
		// if there is a reseller in the neighborhood, this reseller goes to outage
		// otherwise if there is any outage in the neighborhood, streamer to empty
		this.census(nCensus);
		if (nCensus[1] + nCensus[3] <= 1) {
				return new Reseller(this.plain, this.row, this.col);
		}
		else if (nCensus[0] > 0) {
			return new Outage(this.plain, this.row, this.col);
		}
		else if (nCensus[3] > 0) {
			return new Empty(this.plain, this.row, this.col);
		}
		else if (nCensus[2] >= 5) {
			return new Streamer(this.plain, this.row, this.col);
		}
		else return new Streamer(this.plain, this.row, this.col);
	}
	

}
