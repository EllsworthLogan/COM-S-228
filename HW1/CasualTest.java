package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

/**
 * This tests the Casual class
 * 
 * @author Logan Ellsworth
 *
 */
class CasualTest {

	@BeforeClass
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
		usrTown.grid[1][2].census(usrTown.grid[1][2].nCensus);
		assertTrue(usrTown.grid[1][2].nCensus[0] == 2);
	}
	
	/**
	 * Ensures that position relative to the entire grid is correctly assigned
	 */
	@Test
	void determinePositionTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertTrue(usrTown.grid[1][2].determinePosition() == 0);
	}
	
	/**
	 * Ensures that increment() only increases the correct nCensus index
	 */
	@Test
	void incrementTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		usrTown.grid[1][2].increment(State.RESELLER, usrTown.grid[1][2].nCensus);
		assertTrue(usrTown.grid[1][2].nCensus[0] == 1);
	}
	
	/**
	 * Tests the who() method for Casual
	 */
	@Test
	void whoTest1() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertTrue(usrTown.grid[0][0].who() == State.OUTAGE);
	}
	
	/**
	 * Tests the who() method for Casual
	 */
	@Test
	void whoTest2() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertFalse(usrTown.grid[0][0].who() == State.RESELLER);
	}
	
	/**
	 * Tests the State transformation for Casual
	 */
	@Test
	void nextTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertTrue(usrTown.grid[1][2].next(usrTown).who() == State.OUTAGE);
	}

}
