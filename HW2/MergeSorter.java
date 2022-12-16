package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 * 
 * This class implements the mergesort algorithm.   
 * 
 * @author Logan Ellsworth
 *
 */

public class MergeSorter extends AbstractSorter
{
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm = "merge sort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		int length = pts.length;
		// Base case
		if (length <= 1) {
			return;
		}
		
		// Recursive case
		int mid = length / 2;
		Point[] leftHalf = new Point[mid];
		Point[] rightHalf = new Point[length - mid];
		int j = 0;
		for(int i = 0; i < length; i++) {
			if (i < mid) {
				leftHalf[i] = pts[i];
			} else {
				rightHalf[j] = pts[i];
				j++;
			}
		}
		mergeSortRec(leftHalf);
		mergeSortRec(rightHalf);
		merge(leftHalf, rightHalf, pts);
	}

	/**
	 * Helper method for mergeSort().
	 * 
	 * Reassembles the elements in the array, now sorted.
	 * 
	 * @param left
	 * @param right
	 * @param pts
	 */
	private void merge(Point[] left, Point[] right, Point[] pts)
	{
		int leftSide = pts.length / 2;
		int rightSide = pts.length - leftSide;
		// Index for pts
		int i = 0;
		// Index for leftHalf
		int l = 0;
		// Index for rightHalf
		int r = 0;
		
		while(l < leftSide && r < rightSide) {
			if (pointComparator.compare(left[l], right[r]) < 0) {
				pts[i] = left[l];
				i++;
				l++;
			} else {
				pts[i] = right[r];
				i++;
				r++;
			}
		}
		while (l < leftSide) {
			pts[i] = left[l];
			i++;
			l++;
		}
		while (r < rightSide) {
			pts[i] = right[r];
			i++;
			r++;
		}
	}
}
