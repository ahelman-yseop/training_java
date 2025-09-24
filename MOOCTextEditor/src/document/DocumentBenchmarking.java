package document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/** A class for timing the EfficientDocument and BasicDocument classes
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 */

public class DocumentBenchmarking {

	
	public static void main(String [] args) {

	    // Run each test more than once to get bigger numbers and less noise.
	    // You can try playing around with this number.
	    int trials = 100;

	    // The text to test on
	    String textfile = "data/warAndPeace.txt";
		
	    // The amount of characters to increment each step
	    // You can play around with this
		int increment = 20000;

		// The number of steps to run.  
		// You can play around with this.
		int numSteps = 20;
		
		// THe number of characters to start with. 
		// You can play around with this.
		int start = 50000;
		
		// Week 3 assignment.
		for (int numToCheck = start; numToCheck < numSteps*increment + start; 
				numToCheck += increment)
		{
			// numToCheck holds the number of characters that you should read from the 
			// file to create both a BasicDocument and an EfficientDocument.
			
			// Step 1 - Print out numToCheck
			System.out.print(numToCheck + "\t"); 
			
			// Step 2 - Read numToCheck characters from the file into a String
	        String text = getStringFromFile(textfile, numToCheck);
	        
	        // Step 3: Time BasicDocument
	        long startTimeBasic = System.nanoTime();
	        for (int i = 0; i < trials; i++) {
	            BasicDocument doc = new BasicDocument(text);
	            doc.getFleschScore();
	        }
	        long endTimeBasic = System.nanoTime();
	        double durationBasic = (endTimeBasic - startTimeBasic) / 1_000_000_000.0;
	        
	        // Step 4 - Print out the time it took to complete the loop in step 3 
	        System.out.print(durationBasic + "\t");
	        
	        // Step 5: Time EfficientDocument
	        long startTimeEff = System.nanoTime();
	        for (int i = 0; i < trials; i++) {
	            EfficientDocument doc = new EfficientDocument(text);
	            doc.getFleschScore();
	        }
	        long endTimeEff = System.nanoTime();
	        double durationEff = (endTimeEff - startTimeEff) / 1_000_000_000.0;

	        // Step 6 - Print out the time it took to complete the loop in step 5
	        System.out.println(durationEff);
			
		}
	
	}
	
	/** Get a specified number of characters from a text file
	 * 
	 * @param filename The file to read from
	 * @param numChars The number of characters to read
	 * @return The text string from the file with the appropriate number of characters
	 */
	public static String getStringFromFile(String filename, int numChars) {
		
		StringBuffer s = new StringBuffer();
		try {
			FileInputStream inputFile= new FileInputStream(filename);
			InputStreamReader inputStream = new InputStreamReader(inputFile);
			BufferedReader bis = new BufferedReader(inputStream);
			int val;
			int count = 0;
			while ((val = bis.read()) != -1 && count < numChars) {
				s.append((char)val);
				count++;
			}
			if (count < numChars) {
				System.out.println("Warning: End of file reached at " + count + " characters.");
			}
			bis.close();
		}
		catch(Exception e)
		{
		  System.out.println(e);
		  System.exit(0);
		}
		
		
		return s.toString();
	}
	
}
