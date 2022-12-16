package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This tests the TownCell class
 * 
 * @author Logan Ellsworth
 *
 */
class TownCellTest {

	@BeforeEach
	void setup() {
	Town usrTown = new Town(4, 4);
	usrTown.randomInit(10);
	}
	
	/**
	 * Ensures that nCensus[] is storing the ints properly
	 */
	@Test
	void censusTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertTrue(usrTown.grid[0][0].nCensus[0] == 1);
	}
	
	/**
	 * Ensures that position relative to the entire grid is correctly assigned
	 */
	@Test
	void determinePositionTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertTrue(usrTown.grid[0][0].determinePosition() == 5);
	}
	
	/**
	 * Ensures that increment() only increases the correct nCensus index
	 */
	@Test
	void incrementTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		usrTown.grid[0][0].increment(State.RESELLER, usrTown.grid[0][0].nCensus);
		assertTrue(usrTown.grid[0][0].nCensus[0] == 1);
	}
}
