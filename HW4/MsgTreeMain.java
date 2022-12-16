import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * This class is used in conjuction with MsgTree. It contains a file reading method,
 * and a main method which is used to print the output of the program.
 * 
 * @author logan
 *
 */
public class MsgTreeMain {
	
	// Directions for building a binary tree
	public static String encodingString = "";
	
	// Encoded message to unzip
	public static String codes = "";
	

	/**
	 * Searches a given file and sets encodedString and codes to the
	 * appropriate values.
	 * 
	 * @param filename
	 * @return contents of the file
	 * @throws IOException 
	 */
	public void fileReader(String filename) throws IOException {
			
		Path path = Paths.get(filename);
		
		String contents = Files.readString(path);
		contents = contents.trim();
		
		int stop = contents.lastIndexOf("\n");
		
		encodingString = contents.substring(0, stop);
		codes = contents.substring(stop);
	}
	
	
	public static void main (String[] args) throws IOException {
		
		System.out.println("Please enter filename to decode:  ");
		
		Scanner scnr = new Scanner(System.in);
		
		// Get user input for the file name
		String usrFile = scnr.next();
		
		// Instantiate a MsgTreeMain object
		MsgTreeMain m = new MsgTreeMain();
		
		// Read the file and assign the values of encodedString
		m.fileReader(usrFile);
		
		System.out.println("character code\n"
				+ "-------------------------");
		
		MsgTree test = new MsgTree(encodingString);
		
		scnr.close();
		
		MsgTree.printCodes(test, codes);
		
		System.out.println("MESSAGE:");
		test.decode(test, codes);
	}	
}
