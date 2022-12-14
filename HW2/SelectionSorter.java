package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 * 
 * This class implements selection sort.  
 * 
 *  @author Logan Ellsworth
 *
 */

public class SelectionSorter extends AbstractSorter
{
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts);
		super.algorithm = "selection sort";
	}	

	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 */
	@Override 
	public void sort()
	{
		for (int i = 0; i < points.length; i++) {
			int min = i;
			for (int j = i + 1; j < points.length; j++) {
				if (pointComparator.compare(points[min], points[j]) > 0) {
					min = j;
				}
			}
		swap(i, min);
		}
	}	
}
