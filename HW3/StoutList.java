package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import edu.iastate.cs228.hw2.Point;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  @Override
  public int size()
  {
    return size;
  }
  
  @Override
  public boolean add(E item)
  {
	// Check whether the item is null.
	if(item == null) {
		throw new NullPointerException();
	}
	// Else, add the item to the end of the list.
	
	// If no nodes exist, add one, then add item to the first spot in the list
	if (size == 0) {
		Node n = new Node();
		n.addItem(item);
		head.next = n;
		n.next = tail;
		n.previous = head;
		tail.previous = n;
	}
		
	// Else if, a node exists, and it is not full, add item to the next available spot
	else if (tail.previous.count < nodeSize) {
		tail.previous.addItem(item);
	}
	
	// Else, a node exists, and it is full, create a new one and add it to the end
	else {
		Node n = new Node();
		n.addItem(item);
		Node temp = tail.previous;
		temp.next = n;
		n.previous = temp;
		n.next = tail;
		tail.previous = n;
	}
	size++;
    return true;
  }

  @Override
  public void add(int pos, E item)
  {  
	  NodeInfo info = find(pos);
	  Node n = info.node;
	  int offset = info.offset;
	 
//	  if the list is empty, create a new node and put X at offset 0
	  if (head.next == tail) {
		  add(item);
	  }
	  
//	  otherwise if off = 0 and one of the following two cases occurs,
	  else if (offset == 0) {
//		  if n has a predecessor which has fewer than M elements (and is not the head), put X in n’s predecessor
		  if (n.previous != null && n.previous.count < nodeSize && n.previous != head) {
			  n.previous.addItem(item);
		  }
//		  if n is the tail node and n’s predecessor has M elements, create a new node and put X at offset 0
		  if (n == tail && n.previous.count == nodeSize) {
			  add(item);
		  }
	  }
	  
//	  otherwise if there is space in node n, put X in node n at offset off, shifting array elements as necessary
	  else if (n.count < nodeSize) {
		  n.addItem(offset, item);
	  }
	  
//	  otherwise, perform a split operation: move the last M/2 elements of node n into a new successor node n', and then
	  else {
		  Node successor = new Node();
		  for (int i = 0; i < (nodeSize / 2); i++) {
			  successor.addItem(n.data[(nodeSize / 2)]);
			  n.removeItem((nodeSize / 2));
		  }
//		  if off <= M/2, put X in node n at offset off
		  if (offset <= nodeSize / 2) {
			  n.addItem(offset, item);
		  }
//		  if off > M/2, put X in node n' at offset (off - M/2)
		  if (offset > nodeSize / 2) {
			  n.addItem(offset - (nodeSize / 2), item);
		  }
		  
		  Node temp = n.next;
		  n.next = successor;
		  successor.next = temp;
		  temp.previous = successor;
	  }
    size++;
  }

  @Override
  public E remove(int pos)
  {	  	  
	  NodeInfo info = find(pos);
	  Node n = info.node;
	  int offset = info.offset;
	  E ret = n.data[offset];
	  
//	  if the node n containing X is the last node and has only one element, delete it
	  if (n.next == tail && n.count == 1) {
		  Node temp = n.previous;
		  temp.next = n.next;
		  n.next.previous = temp;
		  n = null;
	  }
//	  otherwise, if n is the last node (thus with two or more elements) , or if n has more than 
//	  M/2 elements, remove X from n, shifting elements as necessary;
	  else if ((n.next == tail && n.count >= 2) || n.count > (nodeSize / 2)) {
		  n.removeItem(offset);
	  }
//	  otherwise (the node n must have at most M/2 elements), look at its successor n' (note that we don’t
//	  look at the predecessor of n) and perform a merge operation as follows:
	  else {
		  Node successor = n.next;
		  n.removeItem(offset);
//		  if the successor node n' has more than M/2 elements, move the first element from n' to n.
//		  (mini-merge)
		  if (successor.count > (nodeSize / 2)) {
			  n.addItem(successor.data[0]);
			  successor.removeItem(0);
		  }
//		  if the successor node n' has M/2 or fewer elements, then move all elements from n' to n and
//		  delete n' (full merge)
		  else if (successor.count <= (nodeSize / 2)) {
			  for(int i = 0; i < successor.count; i++) {
				  if (successor.data[i] != null) {
					  n.addItem(successor.data[i]);
				  }
			  }
			  Node temp = successor.previous;
			  temp.next = successor.next;
			  successor.next.previous = temp;
			  successor = null;
		  }
	  }
	size--;
    return ret;
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
	  E[] array = (E[]) new Comparable[size];
	  Comparator<E> comparator = new Comparator<E>() {
		@Override
		public int compare(E o1, E o2) {
			// TODO Auto-generated method stub
			return o1.compareTo(o2);
		}
	  };
	  
	  array = getAllData();
	  
	  insertionSort(array, comparator);
	  size = 0;
	  for(int i = 0; i < array.length; i++) {
		  add(array[i]);
	  }
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
	E[] array = (E[]) new Comparable[size];
	
	array = getAllData();
	bubbleSort(array);
	
	// Make the new linked list
	size = 0;
	for (E e: array) {
		add(e);
	}
	  
  }
  
  @Override
  public Iterator<E> iterator()
  {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }

  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
 
  private class StoutListIterator implements ListIterator<E>
  {
	// constants you possibly use ...   
	  
	// instance variables ... 
	  int position;
	  
	  // -1 after a call to next(), 1 after a call to previous()
	  int lastReturned;	 
	  
	  // Array that stores all of the information from the nodes
	  E[] totalData;
	  
    /**
     * Default constructor 
     */
    public StoutListIterator()
    {
    	position = -1;
    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
    	position = pos - 1;
    }

    @Override
    public boolean hasNext()
    {
    	// If the position is less than the size, true. Else false
    	if (position <= size) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    @Override
    public E next()
    {
    	totalData = getAllData();
    	// If hasNext() is true, move the cursor to the right one spot, else throw an exception
    	if (hasNext()) {
    		position++;
    		E val = totalData[position];
    		lastReturned = -1;
    		
    		return val;
    	} else {
    		throw new NoSuchElementException();
    	}
    }

    @Override
    public void remove()
    {
    	// Last thing called was next()
    	if (lastReturned == -1) {
   				StoutList.this.remove(position);
   				totalData = getAllData();
   				position--;
   				if (position < -1) {
   					position = -1;
   				}
   				lastReturned = 0;
 		}
   		// Last thing called was previous()
    	else if (lastReturned == 1) {
    		StoutList.this.remove(position + 1);
    		totalData = getAllData();
    		lastReturned = 0;
    	}
   		else {
   			throw new IllegalStateException();
 		}
    }
    
    // Other methods you may want to add or override that could possibly facilitate 
    // other operations, for instance, addition, access to the previous element, etc.
    // 
    // ...
    // 

	@Override
	public boolean hasPrevious() {
		// If the position is less than the size, true. Else false
    	if (position >= 0) {
    		return true;
    	} else {
    		return false;
    	}
	}

	@Override
	public E previous() {
		totalData = getAllData();
		// If hasNext() is true, move the cursor to the right one spot, else throw an exception
    	if (hasPrevious()) {
    		position--;
    		E val = totalData[position + 1];
    		lastReturned = 1;
    		
    		return val;
    	} else {
    		throw new NoSuchElementException();
    	}
	}

	@Override
	public int nextIndex() {
		if (position == size) {
			return size;
		}
		else return position + 1;
	}
  
	@Override
	public int previousIndex() {
//		if (position == 0) {
//			return -1;
//		}
//		else {
//		above not needed because position is initialized to -1
			return position;
//		}
	}

	@Override
	public void set(E e) {
		// Last thing called was next()
		if (lastReturned == -1) {
			NodeInfo info = find(position);
			Node node = info.node;
			node.data[info.offset] = e;
		}
		// Last thing called was previous()
		else if (lastReturned == 1) {
			NodeInfo info = find(position + 1);
			Node node = info.node;
			node.data[info.offset] = e;
		}
		else {
			throw new IllegalStateException();
		}
		lastReturned = 0;
	}

	@Override
	public void add(E e) {
		StoutList.this.add(position + 1, e);
		position++;
		totalData = getAllData();
		lastReturned = -1;
	}
  }
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  for (int i = 1; i < arr.length; i++) {
			E temp = arr[i];
			int j = i - 1;
			
			while(j >= 0 && comp.compare(arr[j], temp) > 0) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = temp;
		}
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	  boolean swappedNums = true;
	  
	  while (swappedNums) {
		  swappedNums = false;
		  
		  for (int i = 0; i < arr.length - 1; i++) {
			  if (arr[i].compareTo(arr[i+1]) < 0) {
				  swappedNums = true;
				  E temp = arr[i];
				  arr[i] = arr[i + 1];
				  arr[i + 1] = temp;				  
			  }
		  }
	  }
  }
  
  /**
   * Helper method used to get information about a given Node.
   * 
   * @author logan
   *
   */
  private class NodeInfo { 
	  public Node node; 
	  public int offset; 
      
	  /**
	   * Constructs a new instance of NodeInfo, allowing
	   * information about the node and its offset to be 
	   * accessed.
	   * 
	   * @param node
	   * @param offset
	   */
	  public NodeInfo(Node node, int offset) { 
          this.node = node; 
          this.offset = offset; 
      } 
  } 
  
  /**
   * Helper method that converts the data in the nodes to an ArrayList.
   * @return An array of all of the elements in the nodes
   */
  public E[] getAllData() {
  		// Array of data from the nodes	  
	  	E[] totalData = (E[]) new Comparable[size];
	  
  		// Temporary node used to extract data
  		Node temp = head.next;
  		int iterations = 0;
  		// Loop through the data for each node
  		while (iterations < size){
  			for (E eachItem: temp.data) {
  				if (eachItem != null) {
  					totalData[iterations] = eachItem;
  					iterations++;
  				}
  			}
  			// This is what iterates through the nodes
  			temp = temp.next;
  		}

  		return totalData;
  }
 
  /**
   * Returns the node and offset for the given logical index.
   * 
   * @param pos the position of the node
   * @return node and offset for the position given
   */
  public NodeInfo find(int pos) {
	  Node curNode = head.next;
	  // Counter that gets incremented and it used in an instance of NodeInfo();
	  int counter = 0;
	  // Loop through the linked list
	  while (curNode != tail) {
		  
		  if (counter + curNode.count <= pos) {
			  counter += curNode.count;
			  curNode = curNode.next;
			  // Do this until the condition is not satisfied
			  continue;
		  }
		  
		  NodeInfo curNodeInfo = new NodeInfo(curNode, pos - counter);
		  return curNodeInfo;
	  }
	  // The position was never found
	  return null;
  } 

}