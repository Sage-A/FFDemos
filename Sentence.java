import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Represents a sentence, created from Words
 * @author Sage A
 *
 */
public class Sentence {
	private List<Word> sentence = new ArrayList<Word>();
	private int pos;
	private String sentenceStr = "";
	private Map<Word, Integer> wordFreq;
	
	/**
	 * Class constructor
	 * @param sentence sentence
	 * @param pos position
	 * @throws NullPointerException if sentence is null
	 * @throws IllegalArgumentException if posiiton is negative
	 */
	public Sentence(String sentence, int pos) throws NullPointerException, IllegalArgumentException{
		if(sentence == null) {
			throw new NullPointerException();
		}
		else if(pos < 0) {
			throw new IllegalArgumentException();
		}
		else {
			String[] temp = sentence.split(" ");
			for (int i = 0; i < temp.length; ++i) {
				String s = temp[i];
				Word word = new Word(s, i);
				this.sentence.add(word);
				
			/*String[] strArr = sentence.split(" ");
			List<String> list = Arrays.asList(strArr);
			Word word = new Word(list.toString(), pos);
			this.sentence.add(word); */
			//this.pos = pos;
			}
			this.pos = pos;
		}
	}
	
	/**
	 * Number of words in sentence
	 * @return number of words
	 */
	public int length() {
		return sentence.size();
	}
	
	/**
	 * Converts Words to sentence
	 * @return sentence
	 */
	public String toString() {
		for (int i = 0; i < sentence.size() - 1; ++ i) {
			Word word = sentence.get(i);
			sentenceStr = sentenceStr + word.toString() + " ";
		}
		Word word2 = sentence.get(sentence.size() -1 );
		sentenceStr = sentenceStr + word2.toString();
		return sentenceStr;
	}
	
	/**
	 * Gets position of sentence
	 * @return position
	 */
	public int position() {
		return pos;
	}
	
	/**
	 * Gets word at a given index
	 * @param idx index of word
	 * @return Word
	 * @throws IndexOutOfBoundsException if index is bigger than sentence size or negative
	 */
	public Word get(int idx) throws IndexOutOfBoundsException {
		if(idx >= sentence.size() || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		else {
			return sentence.get(idx);
		}
	}
	
	/**
	 * Gets indices of a word
	 * @param w word
	 * @return All indices of a specific word
	 * @throws NullPointerException if word is null
	 */
	public List<Integer> getWordIndices(Word w) throws NullPointerException{
		if (w == null) {
			throw new NullPointerException();
		}
		else {
			List<Integer> indexes = new ArrayList<Integer>();
			for (int i = 0; i < sentence.size(); ++i) {
				if (sentence.get(i).toString().equalsIgnoreCase(w.toString()) == true) {
					indexes.add(sentence.get(i).position());
				}
			}
			return indexes;
		}
	}
	
	/**
	 * Gets frequency of all words in the sentence
	 * @return HashMap of words and frequency
	 */
	public Map<Word, Integer> wordFrequency(){
		Map<Word, Integer> temp = new HashMap<Word, Integer>();
		for(int i = 0; i < sentence.size(); ++i) {
			Word word = sentence.get(i);
			if(temp.containsKey(word.toLowerCase()) == false) {
				temp.put(word,  1);
			}
			else {
				int counter = temp.get(word);
				counter = counter + 1;
				temp.replace(word, counter);
			}
		}
		return temp;
	}
	
	/**
	 * Finds most frequent word
	 * @return most frequent word
	 */
	public Word mostFrequent() {
		Word temp = new Word("", 0); 
		wordFreq = wordFrequency();
		int max = 0;
		for(int i = 0; i < sentence.size(); ++i) {
			Word word = sentence.get(i).toLowerCase();
			int curr = wordFreq.get(word);
			if(curr > max) {
				temp = word;
				max = curr;
			}
		}
		return temp;
	}
}
