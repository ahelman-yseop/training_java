package spelling;

import java.util.TreeSet;

/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class DictionaryBST implements Dictionary {
   private TreeSet<String> dict;
	
    // Week 5 assignment.
   	// Constructor
   	public DictionaryBST() {
   		dict = new TreeSet<String>();
   	}
    
    /** Add this word to the dictionary.  Convert it to lower case first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
    public boolean addWord(String word) {
        // Week 5 assignment.
    	
    	if (word == null) return false;
    	
        String lowerWord = word.toLowerCase();
        if (!dict.contains(lowerWord)) {
            dict.add(lowerWord);
            return true;
        }
        return false;
    }


    /** Return the number of words in the dictionary */
    public int size() {
    	// Week 5 assignment.
    	
    	return dict.size();
    }

    
    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
    	// Week 5 assignment.
    	
    	if (s == null) return false;
        return dict.contains(s.toLowerCase());
    }

}
