import java.util.HashSet;
import java.util.Set;

/**
 * A wrapper around a word (string)
 * @author Sage A
 *
 */
public class Word{
	private String word;
	private int pos;
	
	/**
	 * Class constructor
	 * @param word word
	 * @param pos position
	 * @throws NullPointerException if word is null
	 * @throws IllegalArgumentException if position is negative
	 */
	public Word(String word, int pos) throws NullPointerException, IllegalArgumentException{
		if(word == null) {
			throw new NullPointerException();
		}
		else if(pos < 0){
			throw new IllegalArgumentException();	
		}
		else {
			this.word = word;
			this.pos = pos;
		}
	}
	
	/**
	 * Number of characters in word
	 * @return length
	 */
	public int length(){
		return word.length();
	}
	
	/**
	 * Returns characters of word
	 */
	public String toString() {
		return word;
	}
	
	/**
	 * Converts word to lowercase
	 * @return Lower case Word object
	 */
	public Word toLowerCase() {
		String temp = word.toLowerCase();
		Word lowerCase = new Word(temp, pos);
		return lowerCase;
	}
	
	/**
	 * Gets position of word
	 * @return position
	 */
	public int position() {
		return pos;
	}
	
	/**
	 * Returns amount of unique characters in word
	 * @return number of unique characters
	 */
	public int uniqueChars() {
		String temp = word.toLowerCase();
		char[] charArray = temp.toCharArray();
		Set<Character> uniqueChar = new HashSet<Character>();
		for(char c : charArray) {
			uniqueChar.add(c);
		}
		return uniqueChar.size();
	}
	
	/**
	 * Checks if two words are equal, ignoring case
	 * @return true if equal, false if anything else
	 */
	public boolean equals(Object obj) {
		Word word2;
		if(obj == null) {
			return false;
		}
		else if(obj.getClass().getName().equals("Word") == false) {
			return false;
		}
		else {
			word2 = (Word)obj;
		}
		String temp = word2.toString();
		if(temp.toLowerCase().equals(word.toLowerCase()) == true) {
			return true;
		}
		else {
		return false;
		}
	}
	
	/**
	 * Gets hashCode of lower case word
	 * @return hashCode
	 */
	public int hashCode() {
		String temp = word.toLowerCase();
		return temp.hashCode();
	}
}
