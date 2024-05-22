import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Represents a text document, created from sentences
 * @author Sage A
 *
 */
public class Document {
	private List<Sentence> document = new ArrayList<Sentence>();
	private String documentStr = "";
	private Map<Word, Integer> wordFreq;
	
	/**
	 * Class constructor
	 * @param document document
	 * @throws NullPointerException if document is null
	 */
	public Document(String document) throws NullPointerException{
		if(document == null) {
			throw new NullPointerException();
		}
		else {
			String[] doc = document.split("[.]");
			for(int i = 0; i < doc.length; ++i) {
				String s = doc[i];
				Sentence sent = new Sentence(s, i);
				this.document.add(sent);
			}
		}
	}
	
	/**
	 * Gets number of sentences in document
	 * @return number of sentences
	 */
	public int length() {
		return document.size();
	}
	
	/**
	 * Converts sentences to string format
	 * @return document as string
	 */
	public String toString() {
		for (int i = 0; i < document.size(); ++ i) {
			Sentence sent = document.get(i);
			documentStr = documentStr + sent.toString() + ".";
		}
		return documentStr;
	}
	
	/**
	 * Gets total number of words in document
	 * @return number of words
	 */
	public int getNumWords() {
		int sum = 0;
		for(int i = 0; i < document.size(); ++i) {
			Sentence sent = document.get(i);
			sum = sum + sent.length();
		}
		return sum;
	}
	
	/**
	 * Gets sentence found at a given index
	 * @param idx index
	 * @return sentence
	 * @throws IndexOutOfBoundsException if index is bigger than document size or negative
	 */
	public Sentence get(int idx) throws IndexOutOfBoundsException{
		if(idx >= document.size() || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		else {
			return document.get(idx);
		}
	}
	
	/**
	 * Gets frequency of all words in document
	 * @return HashMap of words and thier frequency
	 */
	public Map<Word, Integer> wordFrequency(){
		wordFreq = new HashMap<Word, Integer>();
		for(int i = 0; i < document.size(); ++i) {
			Sentence sentence = document.get(i);
			
			for(int j = 0; j < sentence.length(); ++j) {
				Word word = sentence.get(j);
				if(wordFreq.containsKey(word.toLowerCase()) == false) {
					wordFreq.put(word,  1);
				}
				else {
					int counter = wordFreq.get(word);
					counter = counter + 1;
					wordFreq.replace(word, counter);
				}
			}
		}
		return wordFreq;
	}



}
