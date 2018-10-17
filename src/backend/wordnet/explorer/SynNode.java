package backend.wordnet.explorer;

import java.util.ArrayList;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.data.list.PointerTargetNode;
import net.sf.extjwnl.data.list.PointerTargetNodeList;

/**
 * Convenience class to access some features.
 */
public class SynNode {

	public Synset parent;
	public Synset synset;

	public ArrayList<Synset> hypernyms = new ArrayList<Synset>();
	public ArrayList<Synset> hyponyms = new ArrayList<Synset>();
	public ArrayList<Synset> meronyms = new ArrayList<Synset>();
	public ArrayList<Synset> holonyms = new ArrayList<Synset>();
	public ArrayList<Synset> entailments = new ArrayList<Synset>();
	public ArrayList<Synset> coordinateTerms = new ArrayList<Synset>();
	public ArrayList<Synset> causes = new ArrayList<Synset>();
	public ArrayList<Synset> attributes = new ArrayList<Synset>();
	public ArrayList<Synset> pertainyms = new ArrayList<Synset>();

	public ArrayList<WordPOS> synonyms = new ArrayList<WordPOS>();

	
	
	/**
	 * Default constructor
	 * @param synSet
	 */
	public SynNode(Synset synSet) {
		this.synset = synSet;
	}
	
	/**
	 * Constructor which takes the parent into consideration
	 * @param parent
	 * @param synSet
	 */
	public SynNode(Synset parent, Synset synSet) {
		this.parent = parent;
		this.synset = synSet;
	}


	public void setHypernyms() {
		// PointerUtils pointerUtils = PointerUtils.get();
		PointerTargetNodeList phypernyms = new PointerTargetNodeList();
		try {
			phypernyms = PointerUtils.getDirectHypernyms(this.synset);
		} catch (JWNLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("Error finding the  hypernyms");
			e.printStackTrace();
		}

		for (int i = 0; i < phypernyms.size(); i++) {
			PointerTargetNode ptn = (PointerTargetNode) phypernyms.get(i);
			this.hypernyms.add(ptn.getSynset());
		}

	}

	public void setHyponyms() {
		// PointerUtils pointerUtils = PointerUtils.getInstance();
		PointerTargetNodeList phyponyms = new PointerTargetNodeList();
		try {
			phyponyms = PointerUtils.getDirectHyponyms(this.synset);
		} catch (JWNLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("Error finding the  hyponyms");
			e.printStackTrace();
		}

		for (int i = 0; i < phyponyms.size(); i++) {
			PointerTargetNode ptn = (PointerTargetNode) phyponyms.get(i);
			this.hyponyms.add(ptn.getSynset());
		}
	}

	public void setMeronyms() {
		// PointerUtils pointerUtils = PointerUtils.getInstance();
		PointerTargetNodeList pmeronyms = new PointerTargetNodeList();
		try {
			pmeronyms = PointerUtils.getMeronyms(this.synset);
		} catch (JWNLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("Error finding the  meronyms");
			e.printStackTrace();
		}

		for (int i = 0; i < pmeronyms.size(); i++) {
			PointerTargetNode ptn = (PointerTargetNode) pmeronyms.get(i);
			this.meronyms.add(ptn.getSynset());
		}
	}

	public void setHolonyms() {
		// PointerUtils pointerUtils = PointerUtils.getInstance();
		PointerTargetNodeList pholonyms = new PointerTargetNodeList();
		try {
			pholonyms = PointerUtils.getHolonyms(this.synset);
		} catch (JWNLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("Error finding the  holonyms");
			e.printStackTrace();
		}

		for (int i = 0; i < pholonyms.size(); i++) {
			PointerTargetNode ptn = (PointerTargetNode) pholonyms.get(i);
			this.holonyms.add(ptn.getSynset());
		}

	}

	public void setEntailments() {
		// PointerUtils pointerUtils = PointerUtils.get();
		PointerTargetNodeList pentailments = new PointerTargetNodeList();
		try {
			pentailments = PointerUtils.getEntailments(this.synset);
		} catch (JWNLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("Error finding the  hypernyms");
			e.printStackTrace();
		}

		for (int i = 0; i < pentailments.size(); i++) {
			PointerTargetNode ptn = (PointerTargetNode) pentailments.get(i);
			this.entailments.add(ptn.getSynset());
		}

	}

	public void setCoordinateTerms() {
		// PointerUtils pointerUtils = PointerUtils.get();
		PointerTargetNodeList pcoordinateTerms = new PointerTargetNodeList();
		try {
			pcoordinateTerms = PointerUtils.getCoordinateTerms(this.synset);
		} catch (JWNLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("Error finding the  coordinate terms");
			e.printStackTrace();
		}

		for (int i = 0; i < pcoordinateTerms.size(); i++) {
			PointerTargetNode ptn = (PointerTargetNode) pcoordinateTerms.get(i);
			this.coordinateTerms.add(ptn.getSynset());
		}

	}

	public void setCauses() {
		// PointerUtils pointerUtils = PointerUtils.get();
		PointerTargetNodeList pcauses = new PointerTargetNodeList();
		try {
			pcauses = PointerUtils.getCauses(this.synset);
		} catch (JWNLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("Error finding the cause terms");
			e.printStackTrace();
		}

		for (int i = 0; i < pcauses.size(); i++) {
			PointerTargetNode ptn = (PointerTargetNode) pcauses.get(i);
			this.causes.add(ptn.getSynset());
		}

	}

	public void setAttributes() {
		// PointerUtils pointerUtils = PointerUtils.get();
		PointerTargetNodeList pattributes = new PointerTargetNodeList();
		try {
			pattributes = PointerUtils.getAttributes(this.synset);
		} catch (JWNLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("Error finding the attributes");
			e.printStackTrace();
		}

		for (int i = 0; i < pattributes.size(); i++) {
			PointerTargetNode ptn = (PointerTargetNode) pattributes.get(i);
			this.attributes.add(ptn.getSynset());
		}

	}

	public void setPertainyms() {
		// PointerUtils pointerUtils = PointerUtils.get();
		PointerTargetNodeList ppertainyms = new PointerTargetNodeList();
		try {
			ppertainyms = PointerUtils.getPertainyms(this.synset);
		} catch (JWNLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("Error finding the pertainyms");
			e.printStackTrace();
		}

		for (int i = 0; i < ppertainyms.size(); i++) {
			PointerTargetNode ptn = (PointerTargetNode) ppertainyms.get(i);
			this.pertainyms.add(ptn.getSynset());
		}

	}

	public void setSynonyms() {
		for (Word word : synset.getWords())
			synonyms.add(new WordPOS(word.getLemma(), word.getPOS()));
	}

	
	
	public ArrayList<Synset> getHypernyms() {
		return hypernyms;
	}

	public ArrayList<Synset> getHyponyms() {
		return hyponyms;
	}

	public ArrayList<Synset> getMeronyms() {
		return meronyms;
	}

	public ArrayList<Synset> getHolonyms() {
		return holonyms;
	}

	public ArrayList<Synset> getEntailments() {
		return entailments;
	}

	public ArrayList<Synset> getCoordinateTerms() {
		return coordinateTerms;
	}

	public ArrayList<Synset> getCauses() {
		return causes;
	}

	public ArrayList<Synset> getAttributes() {
		return attributes;
	}

	public ArrayList<Synset> getPertainyms() {
		return pertainyms;
	}

	public ArrayList<WordPOS> getSynonyms() {
		return synonyms;
	}

	
	
	public String getGloss() {
		return this.synset.getGloss().toString();
	}

	public long getSynsetID() {
		return this.synset.getOffset();
	}

}
