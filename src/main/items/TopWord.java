package main.items;

import java.util.HashMap;

import commons.constants.Commons;

public class TopWord implements Comparable<TopWord> {

	protected String word;
	protected String pos;

	protected HashMap<String, Integer> occurrences;

	protected int posOccurrences;
	protected int negOccurrences;

	protected double posOccurrencesRatio;
	protected double negOccurrencesRatio;
	
	
	// =================================//
	//           CONSTRUCTOR            //
	// =================================//

	public TopWord(String word, String pos) {
		super();
		this.word = word;
		this.pos = pos;
		this.occurrences = new HashMap<>();
		for(String cl : Parameters.getClasses()) {
			this.occurrences.put(cl, 0);
		}
		this.posOccurrences = 0;
		this.negOccurrences = 0;
		this.posOccurrencesRatio = 0;
		this.negOccurrencesRatio = 0;
	}
	
	
	// =================================//
	//        GETTERS AND SETTERS       //
	// =================================//

	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}

	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}

	public HashMap<String, Integer> getOccurrences() {
		return occurrences;
	}
	public void setOccurrences(HashMap<String, Integer> occurrences) {
		this.occurrences = occurrences;
	}

	public int getPosOccurrences() {
		return posOccurrences;
	}
	public void setPosOccurrences(int posOccurrences) {
		this.posOccurrences = posOccurrences;
	}

	public int getNegOccurrences() {
		return negOccurrences;
	}
	public void setNegOccurrences(int negOccurrences) {
		this.negOccurrences = negOccurrences;
	}

	public double getPosOccurrencesRatio() {
		return posOccurrencesRatio;
	}
	public void setPosOccurrencesRatio(double posOccurrencesRatio) {
		this.posOccurrencesRatio = posOccurrencesRatio;
	}

	public double getNegOccurrencesRatio() {
		return negOccurrencesRatio;
	}
	public void setNegOccurrencesRatio(double negOccurrencesRatio) {
		this.negOccurrencesRatio = negOccurrencesRatio;
	}

	
	// =================================//
	//        COMPARISON METHODS        //
	// =================================//

	@Override
	public boolean equals(Object word) {
		if (!(word instanceof TopWord)) {
			return false;
		}

		TopWord wd = (TopWord) word;

		if (wd.getWord().equalsIgnoreCase(this.word) && wd.getPos().equalsIgnoreCase(this.pos)) {
			return true;
		}

		return false;

	}

	@Override
	public int hashCode() {
		int hash = 7;
		for (int i = 0; i < this.word.length(); i++) {
			hash = hash * 31 + word.toLowerCase().charAt(i);
		}
		for (int i = 0; i < this.pos.length(); i++) {
			hash = hash * 31 + pos.toLowerCase().charAt(i);
		}
		return hash;
	}

	@Override
	public int compareTo(TopWord word) {
		return this.hashCode() - word.hashCode();
	}

	
	// =================================//
	//           OTHER METHODS          //
	// =================================//
	
	public void measureOccurrenceRatios() {
		
		for (String cl : this.occurrences.keySet()) {
			if (Commons.isPositiveSentiment(cl)) {
				posOccurrences = posOccurrences + this.occurrences.get(cl);
			} else if (Commons.isNegativeSentiment(cl)) {
				negOccurrences = negOccurrences + this.occurrences.get(cl);
			}
			
			if (negOccurrences!=0) {
				posOccurrencesRatio = (double) posOccurrences / (double) negOccurrences;
			} else {
				posOccurrencesRatio = 2;
			}
			
			if (posOccurrences!=0) {
				negOccurrencesRatio = (double) negOccurrences / (double) posOccurrences;
			} else {
				negOccurrencesRatio = 2;
			}
		}
	}
	
	
}
