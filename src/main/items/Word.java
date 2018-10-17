package main.items;

import net.sf.extjwnl.data.POS;

public class Word implements Comparable<Word> {
	
	//=================================//
	//            ATTRIBUTES           //
	//=================================//
	
	protected String word;
	protected String posTag;
	protected POS pos;
	protected String emotion;
	protected int sentimentScore;
	protected boolean isNegated;
	
	
	//=================================//
	//        GETTERS & SETTERS        //
	//=================================//
	
	public Word(String word, String posTag) {
		super();
		this.word = word;
		this.posTag = posTag;
	}
	public Word(String word, String posTag, boolean isNegated) {
		super();
		this.word = word;
		this.posTag = posTag;
		this.isNegated = isNegated;
	}
	public Word(String word, String posTag, String emotion) {
		super();
		this.word = word;
		this.posTag = posTag;
		this.emotion = emotion;
	}
	public Word(String word, String posTag, String emotion, int sentimentScore) {
		super();
		this.word = word;
		this.posTag = posTag;
		this.emotion = emotion;
		this.sentimentScore = sentimentScore;
	}


	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	public String getPosTag() {
		return posTag;
	}
	public void setPosTag(String pos) {
		this.posTag = pos;
	}
	
	public POS getPos() {
		return pos;
	}
	public void setPos(POS pos) {
		this.pos = pos;
	}
	
	public String getEmotion() {
		return emotion;
	}
	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}
	
	public int getSentimentScore() {
		return sentimentScore;
	}
	public void setSentimentScore(int sentimentScore) {
		this.sentimentScore = sentimentScore;
	}
	
	public boolean isNegated() {
		return isNegated;
	}
	public void setNegated(boolean isNegated) {
		this.isNegated = isNegated;
	}
	
	
	//=================================//
	//       COMPARING FUNCTIONS       //
	//=================================//
	
	@Override
	public int compareTo(Word otherWord) {
		if (word.equalsIgnoreCase(otherWord.getWord()) && posTag.equals(otherWord.getPosTag()) && emotion.equals(otherWord.getEmotion())) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public boolean equals(Word otherWord) {
		if (word.equalsIgnoreCase(otherWord.getWord()) && posTag.equals(otherWord.getPosTag()) && emotion.equals(otherWord.getEmotion())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean equals(Object O) {
		if (!(O instanceof Word)) {
			return false;
		} else {
			Word other = (Word) O;
			
			return ((this.getWord().equalsIgnoreCase(other.getWord()))
					&& (this.getPosTag().equalsIgnoreCase(other.getPosTag()))
					&& (this.getEmotion().equalsIgnoreCase(other.getEmotion()))
					);
		}
	}

	public boolean isCandidate(Word otherWord) {
		if (word.equalsIgnoreCase(otherWord.getWord()) && posTag.equals(otherWord.getPosTag())) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public String toString() {
		
		String t = "\t";
		
		return this.word + t + this.posTag + t + this.emotion + t + this.sentimentScore + t + this.isNegated;
	}
	
}
