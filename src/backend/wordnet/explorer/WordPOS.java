package backend.wordnet.explorer;

import java.util.ArrayList;
import java.util.List;

import commons.constants.Commons;
import main.start.Loader;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;

public class WordPOS {

	private String word;
	private POS pos;
	private String posTag;
	public boolean isTarget = false;

	public WordPOS(String word, String tag) throws IllegalArgumentException {
		if (word == null || tag == null) {
			throw new IllegalArgumentException("Args are null");
		}
		this.word = word;
		this.posTag = tag;
		this.pos = Commons.getPOS(tag);
	}

	public WordPOS(String word, POS pos) throws IllegalArgumentException {
		if (word == null || pos == null) {
			throw new IllegalArgumentException("Args are null");
		}
		this.word = word;
		this.pos = pos;
	}

	public String getWord() {
		return word;
	}

	public POS getPOS() {
		return pos;
	}

	public String getPosTag() {
		return posTag;
	}

	/**
	 * Return the synsets (thus the senses) of the current word
	 * 
	 * @return a List of {@link Synset} consisting of the different senses of
	 *         the word
	 */
	public ArrayList<Synset> getSynsets() {

		IndexWord indexWord;
		try {
			indexWord = Loader.getDictionary().lookupIndexWord(pos, word);
			if (indexWord == null) {
				Commons.print("NULL synset probably a POS tagger mistake ! :: [POS] : " + pos.getLabel() + " [word] : "
						+ word);
				return null;
			}
			List<Synset> synsets = indexWord.getSenses();
			return (new ArrayList<Synset>(synsets));
		} catch (JWNLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
