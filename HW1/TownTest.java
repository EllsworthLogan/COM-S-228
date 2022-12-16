package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This tests the Town class
 * 
 * @author Logan Ellsworth
 *
 */
class TownTest {

	@BeforeEach
	void setup() {
	Town usrTown = new Town(4, 4);
	usrTown.randomInit(10);
	}
	
	/**
	 * Checks whether the width of the town is properly returned
	 */
	@Test
	void getWidthTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertFalse(usrTown.getWidth() == 5);
	}
	
	/**
	 * Checks whether the length of the town is properly returned
	 */
	@Test
	void getLengthTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertTrue(usrTown.getLength() == 4);
	}
	
	/**
	 * Ensures that randomInit is assigning States to positions
	 */
	@Test
	void randomInitTest() {
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertTrue(usrTown.grid[0][0].who() == State.OUTAGE);
	}
	
	/**
	 * Checks whether toString() is returning the correct String
	 */
	@Test
	void toStringTest() {
		Town usrTown = new Town(1, 1);
		usrTown.randomInit(10);
		assertTrue(usrTown.toString().charAt(0) == 'O');
	}

}
