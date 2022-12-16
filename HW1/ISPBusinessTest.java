package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This tests the ISPBusiness class
 * 
 * @author Logan Ellsworth
 *
 */
class ISPBusinessTest {
	
	@BeforeEach
	void setup() {
	ISPBusiness business = new ISPBusiness();
	Town usrTown = new Town(4, 4);
	usrTown.randomInit(10);
	}
	
	/**
	 * Check whether updating the plain causes the correct next() States
	 */
	@Test
	void updatePlainTest() {
		ISPBusiness business = new ISPBusiness();
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		business.updatePlain(usrTown);
		assertTrue(usrTown.grid[0][0].who() == State.EMPTY);
	}
	
	/**
	 * Check that getting profit does not retun a double,
	 * it leaves that to the helper method
	 */
	@Test
	void getProfitTest() {
		ISPBusiness business = new ISPBusiness();
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		assertTrue(business.getProfit(usrTown) == 1);
	}
	
	/**
	 * Check whether the grid gets deep or shallow copied
	 */
	@Test
	void deepCopyTownTest() {
		ISPBusiness business = new ISPBusiness();
		Town usrTown = new Town(4, 4);
		usrTown.randomInit(10);
		Town blank = new Town(usrTown.getLength(), usrTown.getWidth());
		business.deepCopyTown(blank, usrTown);
		assertTrue(blank.grid[0][0].who() == State.OUTAGE);
	}
	
	/**
	 * Ensures that the total profit is not less than 100
	 */
	@Test
	void getTotalProfitTest() {
		ISPBusiness business = new ISPBusiness();
		Town usrTown = new Town(3, 3);
		business.profit = 9;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				usrTown.grid[i][j] = new Casual(usrTown, i, j);
			}
		}
		assertFalse(business.getTotalProfit(usrTown) == 90.00);
	}

}
