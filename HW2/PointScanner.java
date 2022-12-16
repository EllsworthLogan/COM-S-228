package edu.iastate.cs228.hw2;

import java.io.File;

/**
 * 
 * @author 
 *
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison.
 * 
 * @author Logan Ellsworth
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	private static final String outputFileName = "MCP.txt";
	
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		sortingAlgorithm = algo;
		if (pts != null && pts.length > 0) {
			points = new Point[pts.length];
			for (int i = 0; i < pts.length; i++) {
				points[i] = pts[i];
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		sortingAlgorithm = algo;
		File usrFile = new File(inputFileName);
		Scanner fileScan = new Scanner(usrFile);
		ArrayList<Point> pointList = new ArrayList<Point>();
		Point pt;
		while (fileScan.hasNext()) {
			int x = fileScan.nextInt();
			if(fileScan.hasNext()) {
				int y = fileScan.nextInt();
				pt = new Point(x, y);
				pointList.add(pt);
			} else {
				fileScan.close();
				throw new InputMismatchException();
			}
		}
		fileScan.close();
		//copy the ArrayList of points to the point array
		points = new Point[pointList.size() - 1];
		for (int i = 0; i < pointList.size() - 1; i++) {
			points[i] = pointList.get(i);
		}
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		AbstractSorter aSorter; 
		
		//determine which sorting algorithm to use
		if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
		}
		else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		}
		else if (sortingAlgorithm == Algorithm.QuickSort) {
			aSorter = new QuickSorter(points);
		}
		else if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
		} else return;
		
		long startX = System.nanoTime();
		// Find median X coordinate
		// First, create a comparator
		aSorter.setComparator(0);
		// Then, sort using that comparator
		aSorter.sort();
		int xCoord = aSorter.getMedian().getX();
		long xTime = System.nanoTime() - startX;
		
		long startY = System.nanoTime();
		// Find median Y coordinate
		// First, create a comparator
		aSorter.setComparator(1);
		// Then, sort using that comparator
		aSorter.sort();
		Point middle = new Point(aSorter.getMedian());
		int yCoord = middle.getY();
		long yTime = System.nanoTime() - startY;
		
		scanTime = xTime + yTime;
		
		// set the medianCoordinatePoint to a new Point with the median X and Y values
		medianCoordinatePoint = new Point(xCoord, yCoord);
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		int size = points.length;
		String statistics = sortingAlgorithm.toString() + "\t" + size + "\t" + scanTime;
		return statistics;
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		String MCP = "MCP: " + medianCoordinatePoint.toString();
		return MCP;
	}
	
	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		File outputFile = new File(outputFileName);
		PrintWriter fileWriter = new PrintWriter(outputFile);
		fileWriter.write(toString());
		fileWriter.close();
	}		
}
