package main.start;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import backend.wordnet.explorer.Digger;
import backend.wordnet.explorer.SynNode;
import backend.wordnet.explorer.WordPOS;
import commons.constants.Constants;
import commons.io.Reader;
import main.items.Features;
import main.items.HashMapExtended;
import main.items.Parameters;
import main.items.Word;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;
import net.sf.extjwnl.dictionary.MorphologicalProcessor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.lemmatizer.SimpleLemmatizer;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class Loader {
	
	//=================================//
	//         TYPE OF PROJECT         //
	//=================================//
	
	public enum ProjectType {
		OPEN_PROJECT,
		OPEN_FEATURES_FILE,
		START_NEW_PROJECT
	}
	
	public enum ProjectGoal {
		CLASSIFICATION,
		QUANTIFICATION
	}
	
	// TODO change this to use the local enumeration instead of the one in Constants.
	protected static Constants.TypeOfProject option;
	
	public static Constants.TypeOfProject getOption() {
		return option;
	}
	public static void setOption(Constants.TypeOfProject option) {
		Loader.option = option;
	}

	protected static ProjectGoal projectGoal;
	
	public static ProjectGoal getProjectGoal() {
		return projectGoal;
	}
	public static void setProjectGoal(ProjectGoal projectGoal) {
		Loader.projectGoal = projectGoal;
	}
	
	
	
	//=================================//
	//         OPENNLP-RELATED         //
	//=================================//
	
	private static SentenceDetectorME sdetector;
	private static Tokenizer tokenizer;
	private static POSTaggerME tagger;
	private static NameFinderME nameFinder;
	private static SimpleLemmatizer lemmatizer;
	
	//=================================//
	//         WORDNET-RELATED         //
	//=================================//
	
	protected static Dictionary dictionary;
	protected static MorphologicalProcessor morph;
	
	
	//=================================//
	//   LOCAL CACHES FOR FAST LOOKUP  //
	//=================================//

	private static HashMap<String, Object> relvCache;
	
	protected static HashMap<String, Object> posList1;
	protected static HashMap<String, Object> posList2;
	protected static HashMap<String, Object> posList3;
	protected static HashMap<String, Object> posList4;
	
	private static HashMap<String, Object> englishWords;
	private static HashMap<String, Integer> wordsProbablities;
	
	private static HashMap<String, Object> stopCache;
	private static HashMap<String, Integer> boosters;

	private static HashMap<String, String> apastropheWords;
	protected static HashMap<String, Object> laughExpressions;
	
	private static HashMap<String, Integer> sentimentalWords;
	private static HashMap<String, Integer> positiveCache;
	private static HashMap<String, Integer> negativeCache;

	private static HashMap<String, Integer> emoticonPolarities;
	private static HashMap<String, String> emoticonMeanings;

	private static HashMap<String, Integer> slangPolarities;
	private static HashMap<String, String> slangMeanings;
	
	private static HashMap<String, String> simplifiedPosTagsConverter;
	
	private static HashMap<String, String> domainNames;
	
	protected static HashMapExtended<String, Integer> wordsSerialized;
	protected static HashMapExtended<String, Integer> posSerialized;
	
	
	//=================================//
	//          RECENT FILES           //
	//=================================//
	
	private static LinkedList<String> recentFiles;

	
	//------------------------------------------------------------------------------------//
	
	//=================================//
	//         OPENNLP-RELATED         //
	//=================================//
	
	public static SimpleLemmatizer getLemmatizer() {
		if (lemmatizer == null) {
			try {
				lemmatizer = new SimpleLemmatizer(new FileInputStream(Constants.lemmatizerModel));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return lemmatizer;
	}

	public static NameFinderME getNameFinder() {
		if (nameFinder == null) {
			TokenNameFinderModel nameFinderModel;
			try {
				nameFinderModel = new TokenNameFinderModel(new FileInputStream(Constants.nameFinderModel));
				nameFinder = new NameFinderME(nameFinderModel);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return nameFinder;
	}

	public static POSTaggerME getTagger() {
		if (tagger == null) {
			POSModel posTaggerModel = new POSModelLoader().load(new File(Constants.taggerModel));
			tagger = new POSTaggerME(posTaggerModel);
		}
		return tagger;
	}

	public static SentenceDetectorME getSDetector() {
		if (sdetector == null) {
			try {
				SentenceModel enSentModel = new SentenceModel(
						new FileInputStream(Constants.sentenceDetectorModel));
				sdetector = new SentenceDetectorME(enSentModel);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sdetector;
	}

	public static Tokenizer getTokenizer() {
		if (tokenizer == null) {
			try {
				TokenizerModel tokenizerModel = new TokenizerModel(
						new FileInputStream(Constants.tokenizerModel));
				tokenizer = new TokenizerME(tokenizerModel);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return tokenizer;
	}

	
	//=================================//
	//         WORDNET-RELATED         //
	//=================================//
	
	public static Dictionary getDictionary() {
		if (dictionary == null) {
			try {
				dictionary = Dictionary.getDefaultResourceInstance();
			} catch (JWNLException e) {
				e.printStackTrace();
			}
		}
		return dictionary;
	}
	
	public static MorphologicalProcessor getMorph() {
		if (morph == null) {
			getDictionary();
			morph = dictionary.getMorphologicalProcessor();
		}
		return morph;
	}

	
	//=================================//
	//   LOCAL CACHES FOR FAST LOOKUP  //
	//=================================//
	
	public static HashMap<String, Object> getRelvCache() {
		if (relvCache == null || relvCache.keySet().isEmpty()) {
			relvCache = new HashMap<String, Object>();
			for (String t : Constants.relevantPOS) {
				relvCache.put(t, null);
			}
		}
		return relvCache;
	}
	
	public static HashMap<String, Object> getPosList1() {
		if (posList1 == null) {
			posList1 = new HashMap<>();
			for(String key : Features.getCategoriesMap().keySet()) {
				if (Features.getCategoriesMap().get(key).equals(Features.Categories.cat1)) {
					posList1.put(key, null);
				}
			}
		}
		return posList1;
	}
	
	public static HashMap<String, Object> getPosList2() {
		if (posList2 == null) {
			posList2 = new HashMap<>();
			for(String key : Features.getCategoriesMap().keySet()) {
				if (Features.getCategoriesMap().get(key).equals(Features.Categories.cat2)) {
					posList2.put(key, null);
				}
			}
		}
		return posList2;
	}
	
	public static HashMap<String, Object> getPosList3() {
		if (posList3 == null) {
			posList3 = new HashMap<>();
			for(String key : Features.getCategoriesMap().keySet()) {
				if (Features.getCategoriesMap().get(key).equals(Features.Categories.cat3)) {
					posList3.put(key, null);
				}
			}
		}
		return posList3;
	}
	
	public static HashMap<String, Object> getPosList4() {
		if (posList4 == null) {
			posList4 = new HashMap<>();
			for(String key : Features.getCategoriesMap().keySet()) {
				if (Features.getCategoriesMap().get(key).equals(Features.Categories.cat4)) {
					posList4.put(key, null);
				}
			}
		}
		return posList4;
	}
	
	public static HashMap<String, Object> getEnglishWords() {
		if (englishWords == null || englishWords.keySet().isEmpty()) {
			englishWords = Reader.getWords(Constants.lemmatizerModel);
		}
		return englishWords;
	}

	public static HashMap<String, Integer> getWordsProbablities() {
		if (wordsProbablities == null || wordsProbablities.keySet().isEmpty()) {
			wordsProbablities = Reader.getWordsProbability(Constants.englishDictionaryFile, Constants.bigTextFile);
		}
		return wordsProbablities;
	}
	
	public static HashMap<String, Object> getStopCache() {
		if (stopCache == null || stopCache.keySet().isEmpty()) {
			stopCache = Reader.getWords(Constants.stopWordsFile);
		}
		return stopCache;
	}
	
	public static HashMap<String, Integer> getBoosters() {
		if (boosters == null || boosters.keySet().isEmpty()) {
			boosters = Reader.getWordScores(Constants.boostersFile);
		}
		return boosters;
	}
	
	public static HashMap<String, String> getApastropheWords() {
		if (apastropheWords == null || apastropheWords.keySet().isEmpty()) {
			apastropheWords = Reader.getWordsMeaning(Constants.wordsWithApastropheeFile);
		}
		return apastropheWords;
	}
	
	public static HashMap<String, Object> getLaughExpressions() {
		if (laughExpressions == null || laughExpressions.keySet().isEmpty()) {
			laughExpressions = Reader.getWords(Constants.laughExpressionsFile);
		}
		return laughExpressions;
	}
	
	public static HashMap<String, Integer> getSentimentalWords() {
		if (sentimentalWords == null || sentimentalWords.keySet().isEmpty()) {
			sentimentalWords = Reader.getWordScores(Constants.sentimentalWordsFile);
		}
		return sentimentalWords;
	}
	
	public static HashMap<String, Integer> getPositiveCache() {
		if (positiveCache == null || positiveCache.keySet().isEmpty()) {
			HashMap<String, Integer> emotionalWords = Reader.getWordScores(Constants.sentimentalWordsFile);
			positiveCache = new HashMap<String, Integer>();

			for (String s : emotionalWords.keySet()) {
				if (emotionalWords.get(s) > 0) {
					positiveCache.put(s, emotionalWords.get(s));
				}

			}
		}
		return positiveCache;
	}

	public static HashMap<String, Integer> getNegativeCache() {
		if (negativeCache == null || negativeCache.keySet().isEmpty()) {
			HashMap<String, Integer> emotionalWords = Reader.getWordScores(Constants.sentimentalWordsFile);
			negativeCache = new HashMap<String, Integer>();

			for (String s : emotionalWords.keySet()) {
				if (emotionalWords.get(s) < 0) {
					negativeCache.put(s, emotionalWords.get(s));
				}

			}
		}
		return negativeCache;
	}
	
	public static HashMap<String, Integer> getEmoticonPolarities() {
		if (emoticonPolarities == null || emoticonPolarities.keySet().isEmpty()) {
			emoticonPolarities = Reader.getWordsPolarity(Constants.emoticonsFile);
		}
		return emoticonPolarities;
	}

	public static HashMap<String, String> getEmoticonMeanings() {
		if (emoticonMeanings == null || emoticonMeanings.keySet().isEmpty()) {
			emoticonMeanings = Reader.getWordsMeaning(Constants.emoticonsFile);
		}
		return emoticonMeanings;
	}

	public static HashMap<String, Integer> getSlangPolarities() {
		if (slangPolarities == null || slangPolarities.keySet().isEmpty()) {
			slangPolarities = Reader.getWordsPolarity(Constants.slangsFile);
		}
		return slangPolarities;
	}

	public static HashMap<String, String> getSlangMeanings() {
		if (slangMeanings == null || slangMeanings.keySet().isEmpty()) {
			slangMeanings = Reader.getWordsMeaning(Constants.slangsFile);
		}
		return slangMeanings;
	}
	
	public static HashMap<String, String> getSimplifiedPosTagsConverter() {
		if (simplifiedPosTagsConverter == null || simplifiedPosTagsConverter.keySet().isEmpty()) {
			simplifiedPosTagsConverter = new HashMap<>();
			
			simplifiedPosTagsConverter.put("CC", "COORDCONJUNCTION");
			simplifiedPosTagsConverter.put("CD", "CARDINAL");
			simplifiedPosTagsConverter.put("DT", "DETERMINER");
			simplifiedPosTagsConverter.put("EX", "EXISTTHERE");
			simplifiedPosTagsConverter.put("FW", "FOREIGNWORD");
			simplifiedPosTagsConverter.put("IN", "PREPOSITION");
			simplifiedPosTagsConverter.put("JJ", "ADJECTIVE");
			simplifiedPosTagsConverter.put("JJR", "ADJECTIVE");
			simplifiedPosTagsConverter.put("JJS", "ADJECTIVE");
			simplifiedPosTagsConverter.put("LS", "LISTMARKER");
			simplifiedPosTagsConverter.put("MD", "MODAL");
			simplifiedPosTagsConverter.put("NN", "NOUN");
			simplifiedPosTagsConverter.put("NNS", "NOUN");
			simplifiedPosTagsConverter.put("NNP", "NOUN");
			simplifiedPosTagsConverter.put("NNPS", "NOUN");
			simplifiedPosTagsConverter.put("PDT", "PREDETERMINER");
			simplifiedPosTagsConverter.put("POS", "POSSESSIVEEND");
			simplifiedPosTagsConverter.put("PRP", "PRONOUN");
			simplifiedPosTagsConverter.put("PRP$", "PRONOUN");
			simplifiedPosTagsConverter.put("RB", "ADVERB");
			simplifiedPosTagsConverter.put("RBR", "ADVERB");
			simplifiedPosTagsConverter.put("RBS", "ADVERB");
			simplifiedPosTagsConverter.put("RP", "PARTICLE");
			simplifiedPosTagsConverter.put("SYM", "SYMBOL");
			simplifiedPosTagsConverter.put("TO", "TO");
			simplifiedPosTagsConverter.put("UH", "INTERJECTION");
			simplifiedPosTagsConverter.put("VB", "VERB");
			simplifiedPosTagsConverter.put("VBD", "VERB");
			simplifiedPosTagsConverter.put("VBG", "VERB");
			simplifiedPosTagsConverter.put("VBN", "VERB");
			simplifiedPosTagsConverter.put("VBP", "VERB");
			simplifiedPosTagsConverter.put("VBZ", "VERB");
			simplifiedPosTagsConverter.put("WDT", "WHDETERMINER");
			simplifiedPosTagsConverter.put("WP", "WHDETERMINER");
			simplifiedPosTagsConverter.put("WP$", "WHDETERMINER");
			simplifiedPosTagsConverter.put("WRB", "WHDETERMINER");
			simplifiedPosTagsConverter.put(".", ".");

		}
		
		return simplifiedPosTagsConverter;
	}
	
	public static HashMap<String, String> getDomainNames() {
		if (domainNames == null || domainNames.keySet().isEmpty()) {
			domainNames = Reader.getWordsMeaning(Constants.domainNamesFile);
		}
		return domainNames;
	}

	public static HashMapExtended<String, Integer> getWordsSerialized() {
		if (wordsSerialized == null || wordsSerialized.keySet().isEmpty()) {
			HashMap<String, Integer> temp = Reader.getSerializedWords(Constants.lemmatizerModel);
			wordsSerialized = new HashMapExtended<String, Integer>(-2000000);
			for (String key : temp.keySet()) {
				wordsSerialized.put(key, temp.get(key));
			}
		}
		return wordsSerialized;
	}

	public static HashMapExtended<String, Integer> getPosSerialized() {
		if (posSerialized == null || posSerialized.keySet().isEmpty()) {
			// PoS-tags
			posSerialized = new HashMapExtended<>(-3000000);
			posSerialized.put("CC", -1);
			posSerialized.put("CD", -2);
			posSerialized.put("DT", -3);
			posSerialized.put("EX", -4);
			posSerialized.put("FW", -5);
			posSerialized.put("IN", -6);
			posSerialized.put("JJ", -7);
			posSerialized.put("JJR", -8);
			posSerialized.put("JJS", -9);
			posSerialized.put("LS", -10);
			posSerialized.put("MD", -11);
			posSerialized.put("NN", -12);
			posSerialized.put("NNS", -13);
			posSerialized.put("NNP", -14);
			posSerialized.put("NNPS", -15);
			posSerialized.put("PDT", -16);
			posSerialized.put("POS", -17);
			posSerialized.put("PRP", -18);
			posSerialized.put("PRP$", -19);
			posSerialized.put("RB", -20);
			posSerialized.put("RBR", -21);
			posSerialized.put("RBS", -22);
			posSerialized.put("RP", -23);
			posSerialized.put("SYM", -24);
			posSerialized.put("TO", -25);
			posSerialized.put("UH", -26);
			posSerialized.put("VB", -27);
			posSerialized.put("VBD", -28);
			posSerialized.put("VBG", -29);
			posSerialized.put("VBN", -30);
			posSerialized.put("VBP", -31);
			posSerialized.put("VBZ", -32);
			posSerialized.put("WDT", -33);
			posSerialized.put("WP", -34);
			posSerialized.put("WP$", -35);
			posSerialized.put("WRB", -36);
			posSerialized.put(".", -37);	
			
			//Simplified PoS-tags
			posSerialized.put("COORDCONJUNCTION", -38);
			posSerialized.put("CARDINAL", -39);
			posSerialized.put("DETERMINER", -40);
			posSerialized.put("EXISTTHERE", -41);
			posSerialized.put("FOREIGNWORD", -42);
			posSerialized.put("PREPOSITION", -43);
			posSerialized.put("ADJECTIVE", -44);
			posSerialized.put("LISTMARKER", -45);
			posSerialized.put("MODAL", -46);
			posSerialized.put("NOUN", -47);
			posSerialized.put("PREDETERMINER", -48);
			posSerialized.put("POSSESSIVEEND", -49);
			posSerialized.put("PRONOUN", -50);
			posSerialized.put("ADVERB", -51);
			posSerialized.put("PARTICLE", -52);
			posSerialized.put("SYMBOL", -53);
			posSerialized.put("TO", -54);
			posSerialized.put("INTERJECTION", -55);
			posSerialized.put("VERB", -56);
			posSerialized.put("WHDETERMINER", -57);
			
			// Sentiments
			posSerialized.put("POSITIVE", -100);
			posSerialized.put("NEGATIVE", -200);
			posSerialized.put("NEUTRAL", -300);
			
			posSerialized.put("HAPPINESS", -400);
			posSerialized.put("LOVE", -500);
			posSerialized.put("ENTHUSIASM", -600);
			posSerialized.put("FUN", -700);
			posSerialized.put("RESPECT", -800);
			posSerialized.put("CALM", -900);
			posSerialized.put("SADNESS", -1000);
			posSerialized.put("HATE", -1100);
			posSerialized.put("INDIFFERENCE", -1200);
			posSerialized.put("BOREDOM", -1300);
			posSerialized.put("WORRY", -1400);
			posSerialized.put("DISRESPECT", -1500);
			posSerialized.put("ANGER", -1600);
			posSerialized.put("JEALOUSY", -1700);
			
		}
		return posSerialized;
	}


	
	
	
	//=================================//
	//          RECENT FILES           //
	//=================================//
	
	public static LinkedList<String> getRecentFiles() {
		if (Loader.recentFiles==null) {
			Reader.getRecentFiles();
		}
		return recentFiles;
	}
	
	public static void setRecentFiles(LinkedList<String> recentFiles) {
		Loader.recentFiles = recentFiles;
	}
	
	public static void addRecentFile(String recentFile) {
		Loader.recentFiles.addFirst(recentFile);
	}
	
	
	//=================================//
	//       MC-SENTIMENTAL WORDS      //
	//=================================//
	
	public static void loadDefaultSeedWords() {
		Parameters.setSeeds(Reader.importEmotionalWords(Constants.seedWordsFile));
	}
	
	public static void loadSeedWords(String file) {
		ArrayList<Word> words = (Reader.importEmotionalWords(file));
		Parameters.getSeeds().addAll(words);
	}
	
	public static void loadDefaultMCWords() {
		Parameters.setUnigrams(Reader.importEmotionalWords(Constants.emotionalWordsFile));
		
	}
	
	public static void loadMCWords(String file) {
		Parameters.setUnigrams(Reader.importEmotionalWords(file));
	}
	
	public static void enrich() {
		// Add the unigrams (type Word)
		Parameters.setUnigrams(enrich(Parameters.getSeeds(), true, false, 0, true, Features.getDepth(), false, 0));
		
		// Add the raw unigrams (type String)
		HashMap<String, ArrayList<String>> rawUnigrams = new HashMap<>();
		
		for (String sentiment : Parameters.getClasses()) {
			rawUnigrams.put(sentiment, new ArrayList<String>());
		}
		
		for (Word word : Parameters.getUnigrams()) {
			String wd = word.getWord();
			String cl = word.getEmotion();
			
			if (rawUnigrams.containsKey(cl)) {
				rawUnigrams.get(cl).add(wd);
			}
		}
		
		Parameters.setRawUnigrams(rawUnigrams);
	}
	
	private static ArrayList<Word> enrich(ArrayList<Word> initialSet,
			boolean syn, 
			boolean hyper, int hyperDepth,
			boolean hypo, int hypoDepth,
			boolean causes, int causesDepth) {

		ArrayList<Word> extendedSet = new ArrayList<Word>();
		
		for (Word word : initialSet) {
			
			extendedSet.add(word);
			
			ArrayList<String> associatedWords = new ArrayList<String>();

			WordPOS wordPos = new WordPOS(word.getWord(), word.getPosTag());
			ArrayList<Synset> synsets = wordPos.getSynsets();
			ArrayList<SynNode> nodes = new ArrayList<SynNode>();

			if (synsets != null) {
				for (Synset synset : synsets) {
					if (synset != null) {
						nodes.add(new SynNode(synset));
					}
				}
			}
			for (SynNode node : nodes) {
				// Synonyms
				if (syn == true) {
					for (String wd : Digger.getSynonyms(node)) {
						if (!associatedWords.contains(wd)) {
							associatedWords.add(wd);
						}
					}
				}

				// Hypernyms
				if (hyper) {
					for (String wd : Digger.getHyponyms(node, hyperDepth)) {
						if (!associatedWords.contains(wd)) {
							associatedWords.add(wd);
						}
					}
				}
				
				// Hyponyms
				if (hypo) {
					for (String wd : Digger.getHyponyms(node, hypoDepth)) {
						if (!associatedWords.contains(wd)) {
							associatedWords.add(wd);
						}
					}
				}
				
				// Causes
				if (causes) {
					for (String wd : Digger.getHyponyms(node, causesDepth)) {
						if (!associatedWords.contains(wd)) {
							associatedWords.add(wd);
						}
					}
				}
			}
			
			for (String wd : associatedWords) {
				if (Constants.positiveClasses.contains(word.getEmotion().toUpperCase())) {
					if (Loader.getSentimentalWords().containsKey(wd)) {
						if (Loader.getSentimentalWords().get(wd) > 0) {
							Word newWord = new Word(wd, word.getPosTag(), word.getEmotion());
							if (!extendedSet.contains(newWord)) {
								extendedSet.add(newWord);
							}
						} 
					}
				} else if (Constants.negativeClasses.contains(word.getEmotion().toUpperCase())) {
					if (Loader.getSentimentalWords().containsKey(wd)) {
						if (Loader.getSentimentalWords().get(wd) < 0) {
							Word newWord = new Word(wd, word.getPosTag(), word.getEmotion());
							if (!extendedSet.contains(newWord)) {
								extendedSet.add(newWord);
							}
						} 
					}
				}
			}
		}
		return extendedSet;
	}
	
		
	
	//=================================//
	//   INITIALIZE, LOAD AND UNLOAD   //
	//=================================//
	
	public static void load() {
		// TODO See if it makes sense to initialize the default parameters of the different classifiers here
		main.items.ClassifierParameters.randomForestInitialize();
		main.items.ClassifierParameters.naiveBayesInitialize();
		main.items.ClassifierParameters.j48Initialize();
	}
	
	public static void unload() {
		
	}

	
}
