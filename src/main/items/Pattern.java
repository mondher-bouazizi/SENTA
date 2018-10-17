package main.items;

import commons.constants.Commons;

public class Pattern implements Comparable<Pattern> {
	
	protected String[] pattern;
	protected String patternConcatenated;
	protected int numberOfOccurrences;
	protected String sentimentClass;
	
	
	//=================================//
	//          CONSTRUCTORS           //
	//=================================//
	
	public Pattern() {
		super();
		pattern = new String[50];
		patternConcatenated = "";
		sentimentClass = "";
	}
	
	public Pattern(String[] pattern) {
		this.pattern = pattern;
		if (pattern.length==0) {
			this.patternConcatenated = "";
		} else {
			String patternConcatenated = pattern[0];
			for (int i=1; i<pattern.length; i++) {
				patternConcatenated = patternConcatenated + pattern[i];
			}
			this.patternConcatenated = patternConcatenated.toLowerCase();
		}
		this.numberOfOccurrences = 1;
		this.sentimentClass = "";
	}
	
	public Pattern(String[] pattern, String sentimentClass) {
		this.pattern = pattern;
		if (pattern.length==0) {
			this.patternConcatenated = "";
		} else {
			String patternConcatenated = pattern[0];
			for (int i=1; i<pattern.length; i++) {
				patternConcatenated = patternConcatenated + pattern[i];
			}
			this.patternConcatenated = patternConcatenated.toLowerCase();
		}
		this.numberOfOccurrences = 1;
		this.sentimentClass = sentimentClass;
	}
	
	
	//=================================//
	//       GETTERS AND SETTERS       //
	//=================================//
	
	public String[] getPattern() {
		return pattern;
	}
	public void setPattern(String[] pattern) {
		this.pattern = pattern;
	}
	
	public String getPatternConcatenated() {
		if (patternConcatenated==null) {
			if (pattern.length==0) {
				this.patternConcatenated = "";
			} else {
				String patternConcatenated = pattern[0];
				for (int i=1; i<pattern.length; i++) {
					patternConcatenated = patternConcatenated + pattern[i];
				}
				this.patternConcatenated = patternConcatenated.toLowerCase();
			}
		}
		return patternConcatenated;
	}
	public void setPatternConcatenated(String patternConcatenated) {
		this.patternConcatenated = patternConcatenated;
	}
	
	public int getNumberOfOccurrences() {
		return numberOfOccurrences;
	}
	public void setNumberOfOccurrences(int numberOfOccurrences) {
		this.numberOfOccurrences = numberOfOccurrences;
	}
	
	public String getSentimentClass() {
		return sentimentClass;
	}
	public void setSentimentClass(String sentimentClass) {
		this.sentimentClass = sentimentClass;
	}
	
	
	//=================================//
	//       COMPARISON METHODS        //
	//=================================//
	
	@Override
	public boolean equals(Object otherPattern) {
		if (!(otherPattern instanceof Pattern)) {
			return false;
		}
		
		Pattern p = (Pattern) otherPattern;
		return (Commons.equals(this.pattern, p.getPattern()) && this.sentimentClass.equals(p.getSentimentClass()));
	}

	@Override
	public int hashCode() {
		int hash = 7;
		for (int i = 0; i < this.patternConcatenated.length(); i++) {
			hash = hash * 31 + patternConcatenated.toLowerCase().charAt(i);
		}
		for (int i = 0; i < this.sentimentClass.length(); i++) {
			hash = hash * 31 + sentimentClass.toLowerCase().charAt(i);
		}
		return hash;
	}
	
	@Override
	public int compareTo(Pattern otherPattern) {
		return this.hashCode() - otherPattern.hashCode();
	}
	
	public boolean isSame(Pattern otherPattern) {
		return (this.patternConcatenated.equalsIgnoreCase(otherPattern.getPatternConcatenated()));
	}
	
	public boolean isSame(String otherPatternConcatenated) {
		return (this.patternConcatenated.equalsIgnoreCase(otherPatternConcatenated));
	}

	
	//=================================//
	//          OTHER METHODS          //
	//=================================//
	
	public String getPatternToExport() {
		String output = "";
		if (this.pattern.length>0) {
			output = pattern[0];
			for (int i=1; i<pattern.length; i++) {
				output = output + "#" + pattern[i];
			}
		}
		
		return output;
	}
}
