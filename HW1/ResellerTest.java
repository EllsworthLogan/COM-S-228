package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

/**
 * This tests the Reseller class
 * 
 * @author Logan Ellsworth
 *
 */
class ResellerTest {
	
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
		usrTown.grid[0][1].census(usrTown.grid[0][1].nCensus);
		assertTrue(usrTown.grid[0][1].nCensus[0] == 0);
	}
	
	/**
	 * Ensures that position relative to the entire grid is correctly assigned
	 */
	@Test
	void determinePositionTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertTrue(usrTown.grid[0][1].determinePosition() == 1);
	}
	
	/**
	 * Ensures that increment() only increases the correct nCensus index
	 */
	@Test
	void incrementTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		usrTown.grid[0][1].increment(State.RESELLER, usrTown.grid[0][1].nCensus);
		assertTrue(usrTown.grid[0][1].nCensus[0] == 1);
	}
	
	/**
	 * Tests the who() method for Reseller
	 */
	@Test
	void whoTest1() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertTrue(usrTown.grid[0][1].who() == State.RESELLER);
	}
	
	/**
	 * Tests the who() method for Reseller
	 */
	@Test
	void whoTest2() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertFalse(usrTown.grid[0][1].who() == State.EMPTY);
	}
	
	/**
	 * Tests the State transformation for Reseller
	 */
	@Test
	void nextTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertTrue(usrTown.grid[0][1].next(usrTown).who() == State.EMPTY);
	}

}
