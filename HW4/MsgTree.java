import java.util.Stack;

/**
 * This class represents a tree node which contains a left and right child.
 * The node also contains a char variable which stores a char value.
 * 
 * @author logan
 *
 */
public  class  MsgTree {
	
	public char payloadChar;
	
	public MsgTree left;
	
	public MsgTree right;
	
	public static String leafPath;
	
	/*Can use a static char idx to the tree string for recursive 
	solution, but it is not strictly necessary*/
	private static int staticCharIdx = 0;
	
	
	/**
	 * Constructor building the tree from a string.
	 * 
	 * @param encodingString
	 */
	public MsgTree(String encodingString) {
		
		if (encodingString != null && encodingString.length() > 1) {
			
			// Array used to look at individual parts of encodingString
			char[] preOrder = encodingString.toCharArray();
			int index = 0;
			this.payloadChar = preOrder[index++];
			MsgTree tmp = this;
			
			Stack<MsgTree> stack = new Stack<>();
			stack.push(this);
			
			// Changes when a leaf node is reached (i.e. when the stack is popped)
			boolean pushed = true;	
			
			// For the length of encodingString
			while(index < preOrder.length) {
				// Create a new node from the next thing in preOrder and figure out what its left and right need to be
				MsgTree child = new MsgTree(preOrder[index++]);
				// If the last action on the stack wash a push, we know the next item
				// in the array should be the left child of temp.
				if (pushed) {
					tmp.left = child;
					// If we haven't reached a leaf, keep pushing on the stack
					if (child.payloadChar == '^') {
						stack.push(child);
						tmp = child;
						pushed = true;
					}
					// Reached a leaf. Pop the stack
					else {
						if (!stack.isEmpty()) {
							tmp = stack.pop();
							pushed = false;
						}
					}
				}
				// We know the next item in the array should be the right child of temp.
				else if (!pushed) {
					tmp.right = child;
					// If we haven't reached a leaf, keep adding to the stack
					if (child.payloadChar == '^') {
						stack.push(child);
						tmp = child;
						pushed = true;
					}
					// Reached a leaf. Pop the stack
					else {
						if (!stack.isEmpty()) {
							tmp = stack.pop();
							pushed = false;
						}
						
					}
				}
			}
		}
		
	//		Below was used for the recursive constructor (leaving it in final submission in case errors
	//		arise with the iterative method).
	//	
	//		payloadChar = preOrder[staticCharIdx];
	//		staticCharIdx++;
	//		
	//		left = build(preOrder);
	//		right = build(preOrder);
	}
	
	/**
	 * Constructor for a single node with null children.
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		left = null;
		right = null;
	} 
	
	
	/**
	 * Iterative method to print characters and their binary codes.
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code) {	
		
		Stack<MsgTree> stk = new Stack<>();
		
		// Push the root node onto the stack
		stk.push(root);
		
		while (!stk.isEmpty()) {
			MsgTree temp = stk.pop();
			
			if (temp != null) {
				// Do not print "^"
				if (temp.payloadChar != '^') {
					if (temp.payloadChar == '\n') {
						getPath(root, temp.payloadChar, "");
						System.out.println("   " + "\\n" + "     " + leafPath);
						leafPath = "";
					} else {
						getPath(root, temp.payloadChar, "");
						System.out.println("   " + temp.payloadChar + "      " + leafPath);
						leafPath = "";
					}
				}
				stk.push(temp.right);
				stk.push(temp.left);
			}
		}
		
	}

	
	/**
	 * Decodes the message and prints to the console.
	 * 
	 * @param codes
	 * @param msg
	 */
	public void decode(MsgTree codes, String msg) {
		msg = msg.trim();
		char[] chars = msg.toCharArray();
		MsgTree tempRt = codes;
		
		// For the length of the message...
		for (int i = 0; i < chars.length; i++) {
				
			if (chars[i] == '0') {
				// Go left if left is not null
				if (tempRt.left != null) {
					tempRt = tempRt.left;
					// Check if it is a leaf
					if (tempRt.payloadChar != '^') {
						System.out.print(tempRt.payloadChar);
						// Reset temp to the original root node
						tempRt = codes;
					}
				}
			}
			// We know chars[i] == 1
			else {
				// Go right if right is not null
				if (tempRt.right != null) {
					tempRt = tempRt.right;
					// Check if it is a leaf
					if (tempRt.payloadChar != '^') {
						System.out.print(tempRt.payloadChar);
						// Reset temp to the original root node
						tempRt = codes;
					}
				}
			}
		}
	}
	
	
//	Below was used for the recursive constructor (leaving it in final submission in case errors
//	arise with the iterative method).
//	
//	/**
//	 * Helper method to recursively build the MsgTree
//	 * 
//	 * @param encoded
//	 * @return MsgTree root
//	 */
//	public MsgTree build(char[] encoded) {
//		
//		// Base case
//		if (staticCharIdx == encoded.length) {
//			return null;
//		}
//		
//		// Create the current node
//		MsgTree m = new MsgTree(encoded[staticCharIdx]);
//		staticCharIdx++;
//		
//		if (m.payloadChar == '^') {
//			m.left = build(encoded);
//			m.right = build(encoded);
//		}
//		
//		return m;		
//	}
	
	
	/**
	 * Given a root node and a char, returns the path to the char
	 * as a String of ones and zeros. One is equivalent to traversing
	 * right, and zero is equivalent to traversing left through the
	 * tree.
	 * 
	 * @param 
	 * @return
	 */
	public static void getPath(MsgTree root, char c, String path) {
		
		// Base case
		if (root.payloadChar == c) {
			leafPath = path;
		} else {
			// Recursive case
			if (root.left != null) {
				getPath(root.left, c, path + "0");
			}
			if (root.right != null) {
				getPath(root.right, c, path + "1");
			}
		}
	}
}