package backend.wordnet.explorer;

import java.util.ArrayList;

import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;

public class Digger {
	
	/**
	 * Collects the Synonyms of the node
	 * @param synNode : the Synset node to get the synonyms from
	 * @return the list of words consisting of the synonyms
	 */
	public static ArrayList<String> getSynonyms(SynNode synNode) {
		
		synNode.setSynonyms();
		
		ArrayList<String> relatedWords = new ArrayList<String>();
		
		for (WordPOS wpos : synNode.getSynonyms()) {
		if (!relatedWords.contains(wpos.getWord())) {
			relatedWords.add(wpos.getWord());
		}
	}
		
			
		return relatedWords;
	}
	
	/**
	 * Collects the Hypernyms until the depth defined by the user
	 * [Note: the method is recursive!]
	 * @param synNode : the Synset node to get the Hypernyms from
	 * @param depth : the depth to go down to
	 * @return the list of words consisting of the hypernyms until the depth defined
	 */
	public static ArrayList<String> getHypernyms(SynNode synNode, int depth) {
		
		synNode.setHypernyms();
		
		ArrayList<String> hypernyms = new ArrayList<String>();
		
		if(depth != 0) {
			
			depth = depth -1;
			
			ArrayList<Synset> synsets = synNode.getHypernyms();
			
			for (Synset synset : synsets) {
				for (Word wd : synset.getWords()) {
					String w = wd.getLemma();
					if (!hypernyms.contains(w)) {
						hypernyms.add(w);
					}
				}
				
			}
			
			for (Synset synset : synsets) {
				SynNode node =new SynNode(synset);
				node.setHypernyms();
				ArrayList<String> tempWords = getHypernyms(node, depth);
				for (String w : tempWords) {
					if (!hypernyms.contains(w)) {
						hypernyms.add(w);
					}
				}
			}
			
		}
			
		return hypernyms;
	}
	
	/**
	 * Collects the Hyponyms until the depth defined by the user
	 * [Note: the method is recursive!]
	 * @param synNode : the Synset node to get the Hyponyms from
	 * @param depth : the depth to go down to
	 * @return the list of words consisting of the hyponyms until the depth defined
	 */
	public static ArrayList<String> getHyponyms(SynNode synNode, int depth) {
		
		synNode.setHyponyms();
		
		ArrayList<String> hyponyms = new ArrayList<String>();
		
		if(depth != 0) {
			
			depth = depth -1;
			
			ArrayList<Synset> synsets = synNode.getHyponyms();
			
			for (Synset synset : synsets) {
				for (Word wd : synset.getWords()) {
					String w = wd.getLemma();
					if (!hyponyms.contains(w)) {
						hyponyms.add(w);
					}
				}
				
			}
			
			for (Synset synset : synsets) {
				SynNode node =new SynNode(synset);
				node.setHyponyms();
				ArrayList<String> tempWords = getHyponyms(node, depth);
				for (String w : tempWords) {
					if (!hyponyms.contains(w)) {
						hyponyms.add(w);
					}
				}
			}
			
		}
			
		return hyponyms;
	}
	
	/**
	 * Collects the Meronyms until the depth defined by the user
	 * [Note: the method is recursive!]
	 * @param synNode : the Synset node to get the Meronyms from
	 * @param depth : the depth to go down to
	 * @return the list of words consisting of the Meronyms until the depth defined
	 */
	public static ArrayList<String> getMeronyms(SynNode synNode, int depth) {
		
		synNode.setMeronyms();
		
		ArrayList<String> meronyms = new ArrayList<String>();
		
		if(depth != 0) {
			
			depth = depth -1;
			
			ArrayList<Synset> synsets = synNode.getMeronyms();
			
			for (Synset synset : synsets) {
				for (Word wd : synset.getWords()) {
					String w = wd.getLemma();
					if (!meronyms.contains(w)) {
						meronyms.add(w);
					}
				}
				
			}
			
			for (Synset synset : synsets) {
				SynNode node =new SynNode(synset);
				node.setHyponyms();
				ArrayList<String> tempWords = getMeronyms(node, depth);
				for (String w : tempWords) {
					if (!meronyms.contains(w)) {
						meronyms.add(w);
					}
				}
			}
			
		}
			
		return meronyms;
	}
	
	/**
	 * Collects the Holonyms until the depth defined by the user
	 * [Note: the method is recursive!]
	 * @param synNode : the Synset node to get the Holonyms from
	 * @param depth : the depth to go down to
	 * @return the list of words consisting of the Holonyms until the depth defined
	 */
	public static ArrayList<String> getHolonyms(SynNode synNode, int depth) {
		
		synNode.setHolonyms();
		
		ArrayList<String> holonyms = new ArrayList<String>();
		
		if(depth != 0) {
			
			depth = depth -1;
			
			ArrayList<Synset> synsets = synNode.getHolonyms();
			
			for (Synset synset : synsets) {
				for (Word wd : synset.getWords()) {
					String w = wd.getLemma();
					if (!holonyms.contains(w)) {
						holonyms.add(w);
					}
				}
				
			}
			
			for (Synset synset : synsets) {
				SynNode node =new SynNode(synset);
				node.setHyponyms();
				ArrayList<String> tempWords = getHolonyms(node, depth);
				for (String w : tempWords) {
					if (!holonyms.contains(w)) {
						holonyms.add(w);
					}
				}
			}
			
		}
			
		return holonyms;
	}
	
	/**
	 * Collects the Entailments until the depth defined by the user
	 * [Note: the method is recursive!]
	 * @param synNode : the Synset node to get the Entailements from
	 * @param depth : the depth to go down to
	 * @return the list of words consisting of the Entailments until the depth defined
	 */
	public static ArrayList<String> getEntailments(SynNode synNode, int depth) {
		
		synNode.setEntailments();
		
		ArrayList<String> entailments = new ArrayList<String>();
		
		if(depth != 0) {
			
			depth = depth -1;
			
			ArrayList<Synset> synsets = synNode.getEntailments();
			
			for (Synset synset : synsets) {
				for (Word wd : synset.getWords()) {
					String w = wd.getLemma();
					if (!entailments.contains(w)) {
						entailments.add(w);
					}
				}
				
			}
			
			for (Synset synset : synsets) {
				SynNode node =new SynNode(synset);
				node.setHyponyms();
				ArrayList<String> tempWords = getEntailments(node, depth);
				for (String w : tempWords) {
					if (!entailments.contains(w)) {
						entailments.add(w);
					}
				}
			}
			
		}
			
		return entailments;
	}
	
	/**
	 * Collects the Coordinate Terms until the depth defined by the user
	 * [Note: the method is recursive!]
	 * @param synNode : the Synset node to get the Coordinate Terms from
	 * @param depth : the depth to go down to
	 * @return the list of words consisting of the Coordinate Terms until the depth defined
	 */
	public static ArrayList<String> getCoordinateTerms(SynNode synNode, int depth) {
		
		synNode.setCoordinateTerms();
		
		ArrayList<String> coordinateTerms = new ArrayList<String>();
		
		if(depth != 0) {
			
			depth = depth -1;
			
			ArrayList<Synset> synsets = synNode.getCoordinateTerms();
			
			for (Synset synset : synsets) {
				for (Word wd : synset.getWords()) {
					String w = wd.getLemma();
					if (!coordinateTerms.contains(w)) {
						coordinateTerms.add(w);
					}
				}
				
			}
			
			for (Synset synset : synsets) {
				SynNode node =new SynNode(synset);
				node.setHyponyms();
				ArrayList<String> tempWords = getCoordinateTerms(node, depth);
				for (String w : tempWords) {
					if (!coordinateTerms.contains(w)) {
						coordinateTerms.add(w);
					}
				}
			}
			
		}
			
		return coordinateTerms;
	}
	
	/**
	 * Collects the Causes until the depth defined by the user
	 * [Note: the method is recursive!]
	 * @param synNode : the Synset node to get the Causes from
	 * @param depth : the depth to go down to
	 * @return the list of words consisting of the Causes until the depth defined
	 */
	public static ArrayList<String> getCauses(SynNode synNode, int depth) {
		
		synNode.setCauses();
		
		ArrayList<String> causes = new ArrayList<String>();
		
		if(depth != 0) {
			
			depth = depth -1;
			
			ArrayList<Synset> synsets = synNode.getCauses();
			
			for (Synset synset : synsets) {
				for (Word wd : synset.getWords()) {
					String w = wd.getLemma();
					if (!causes.contains(w)) {
						causes.add(w);
					}
				}
				
			}
			
			for (Synset synset : synsets) {
				SynNode node =new SynNode(synset);
				node.setHyponyms();
				ArrayList<String> tempWords = getCauses(node, depth);
				for (String w : tempWords) {
					if (!causes.contains(w)) {
						causes.add(w);
					}
				}
			}
			
		}
			
		return causes;
	}
	
	/**
	 * Collects the Attributes until the depth defined by the user
	 * [Note: the method is recursive!]
	 * @param synNode : the Synset node to get the Attributes from
	 * @param depth : the depth to go down to
	 * @return the list of words consisting of the Attributes until the depth defined
	 */
	public static ArrayList<String> getAttributes(SynNode synNode, int depth) {
		
		synNode.setAttributes();
		
		ArrayList<String> attributes = new ArrayList<String>();
		
		if(depth != 0) {
			
			depth = depth -1;
			
			ArrayList<Synset> synsets = synNode.getAttributes();
			
			for (Synset synset : synsets) {
				for (Word wd : synset.getWords()) {
					String w = wd.getLemma();
					if (!attributes.contains(w)) {
						attributes.add(w);
					}
				}
				
			}
			
			for (Synset synset : synsets) {
				SynNode node =new SynNode(synset);
				node.setHyponyms();
				ArrayList<String> tempWords = getAttributes(node, depth);
				for (String w : tempWords) {
					if (!attributes.contains(w)) {
						attributes.add(w);
					}
				}
			}
			
		}
			
		return attributes;
	}
	
	/**
	 * Collects the Pertainyms until the depth defined by the user
	 * [Note: the method is recursive!]
	 * @param synNode : the Synset node to get the Pertainyms from
	 * @param depth : the depth to go down to
	 * @return the list of words consisting of the Pertainyms until the depth defined
	 */
	public static ArrayList<String> getPertainyms(SynNode synNode, int depth) {
		
		synNode.setPertainyms();
		
		ArrayList<String> pertainyms = new ArrayList<String>();
		
		if(depth != 0) {
			
			depth = depth -1;
			
			ArrayList<Synset> synsets = synNode.getPertainyms();
			
			for (Synset synset : synsets) {
				for (Word wd : synset.getWords()) {
					String w = wd.getLemma();
					if (!pertainyms.contains(w)) {
						pertainyms.add(w);
					}
				}
				
			}
			
			for (Synset synset : synsets) {
				SynNode node =new SynNode(synset);
				node.setHyponyms();
				ArrayList<String> tempWords = getPertainyms(node, depth);
				for (String w : tempWords) {
					if (!pertainyms.contains(w)) {
						pertainyms.add(w);
					}
				}
			}
			
		}
			
		return pertainyms;
	}
	
}
