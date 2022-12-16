package edu.iastate.cs228.hw2;

/**
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 * 
 * @author Logan Ellsworth
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{	
		// Conducts multiple rounds of comparison of four sorting algorithms.  Within each round, 
		// set up scanning as follows: 
		// 
		//    a) If asked to scan random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		// 
		//    b) Reassigns to the array scanners[] (declared below) the references to four new 
		//       PointScanner objects, which are created using four different values  
		//       of the Algorithm type:  SelectionSort, InsertionSort, MergeSort and QuickSort. 
		// 
		// For each input of points, do the following. 
		// 
		//     a) Initialize the array scanners[].  
		//
		//     b) Iterate through the array scanners[], and have every scanner call the scan() 
		//        method in the PointScanner class.  
		//
		//     c) After all four scans are done for the input, print out the statistics table from
		//		  section 2.
		//
		PointScanner ps = new PointScanner("points.txt", Algorithm.QuickSort);
		ps.scan();
		System.out.println(ps.toString());
		
		Point[] randPts;
		PointScanner[] scanners = new PointScanner[4];
		Scanner usrInput = new Scanner(System.in);
		int numRounds = 1;
		int keys = 0;
		
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning \n"
							+ "\n"
							+ "keys:  1 (random integers)  2 (file input)  3 (exit)");
		System.out.print("Trial " + numRounds + ": ");
		
		// While the key is 1 or 2 do not exit
		do {
			if (keys == 3) {
				// Do nothing
			}
			// Get the key from the user
			keys = usrInput.nextInt();
			// Generate random points
			if (keys == 1) {
				System.out.print("Enter number of random points: ");
				int numPoints = usrInput.nextInt();
				System.out.println();
				Random generator = new Random();
				randPts = generateRandomPoints(numPoints, generator);
				
				scanners = new PointScanner[4]; 
				PointScanner ps1 = new PointScanner(randPts, Algorithm.SelectionSort);
				PointScanner ps2 = new PointScanner(randPts, Algorithm.InsertionSort);
				PointScanner ps3 = new PointScanner(randPts, Algorithm.MergeSort);
				PointScanner ps4 = new PointScanner(randPts, Algorithm.QuickSort);
				scanners[0] = ps1;
				scanners[1] = ps2;
				scanners[2] = ps3;
				scanners[3] = ps4;
				
				System.out.println("algorithm\tsize\ttime (ns)\n"
					+ "----------------------------------");
				for(PointScanner ptScnr : scanners) {
					ptScnr.scan();
					System.out.println(ptScnr.stats());
				}
				System.out.println("----------------------------------\n");
			}
			
			// Read from a file
			else if (keys == 2) {
				System.out.print("Points from a file \n"
						+ "File name: ");
				String usrFile = usrInput.next();
				System.out.println();
				PointScanner usrPtScnr1 = new PointScanner(usrFile, Algorithm.SelectionSort);
				PointScanner usrPtScnr2 = new PointScanner(usrFile, Algorithm.InsertionSort);
				PointScanner usrPtScnr3 = new PointScanner(usrFile, Algorithm.MergeSort);
				PointScanner usrPtScnr4 = new PointScanner(usrFile, Algorithm.QuickSort);
				scanners[0] = usrPtScnr1;
				scanners[1] = usrPtScnr2;
				scanners[2] = usrPtScnr3;
				scanners[3] = usrPtScnr4;
				
				System.out.println("algorithm\tsize\ttime (ns)\n"
						+ "----------------------------------");
					for(PointScanner ptScnr : scanners) {
						ptScnr.scan();
						System.out.println(ptScnr.stats());
					}
					System.out.println("----------------------------------\n");		
			}
			// Increment the number of rounds performed
			numRounds++;
			// Ask the user for keys again.
			if (keys != 3) {
				System.out.print("Trial " + numRounds + ": ");
			}
		} while (keys == 1 || keys == 2);
		
		usrInput.close();
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{
		if (numPts < 1) {
			throw new IllegalArgumentException(); 
		} else {
			Point[] randomPoints = new Point[numPts];;
			Random generator = rand;
			for (int i = 0; i < numPts; i++) {
				int x = generator.nextInt(101) - 50;
				int y = generator.nextInt(101) - 50;
				Point randPt = new Point(x, y);
				randomPoints[i] = randPt;
			}
			return randomPoints; 
		}
	}
}