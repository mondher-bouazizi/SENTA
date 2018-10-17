package backend.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import commons.constants.Commons;
import commons.io.Reader;

import main.items.Features;
import main.items.Parameters;
import main.items.Pattern;
import main.items.PatternNumeric;
import main.items.TopWord;
import main.items.Tweet;
import main.items.Word;
import main.items.Features.TypeOfFeature;
import main.start.Loader;

public class TweetProcessing {
	

	//=================================//
	//          PREPROCESSING          //
	//=================================//
	
	/**
	 * Preprocess a Tweet to use the longer form of words, simplifying HTMLs Emails, etc.
	 * @param tweet
	 */
	public static void preprocessTweets(Tweet tweet) {
		tweet.setPreprocessedText(TextProcessing.preprocess(tweet.getText()));
	}
	
	/**
	 * Extract the basic components of the tweet which are [Tokens / Lemmas(of the tokens) / Pos-Tags / Simplified Pos Tags / Negation prefixes]
	 * @param tweet : the tweet to process
	 */
	public static void addBasicComponents(Tweet tweet) {
		
		tweet.setTokens(TextProcessing.tokenize(tweet.getPreprocessedText()));
		tweet.setTags(TextProcessing.posTag(tweet.getTokens()));
		tweet.setLemmas(TextProcessing.lemmatize(tweet.getTokens(), tweet.getTags()));
		tweet.setSimplifiedTags(TextProcessing.getSimplifiedPosTags(tweet.getTags()));
		tweet.setPolaritySwitcher(TextProcessing.generateNegationIndex(tweet.getTokens()));
		tweet.setCoefficients(TextProcessing.generateBoostingCoefficients(tweet.getLemmas()));
		
		tweet.setTokensPolarity(TextProcessing.getWordPolarity(tweet.getLemmas(), tweet.getPolaritySwitcher()));
		tweet.setTokensScore(TextProcessing.getWordsSentimentScores(tweet.getLemmas(), tweet.getPolaritySwitcher(), tweet.getCoefficients()));
	}
	
	
	//=================================//
	//          BASIC FEATURES         //
	//=================================//
	
	/**
	 * Extract basic sentiment features from the tweet
	 * ALL sentiment features are extracted [because they don't occupy much space, and they don't take time to generate]
	 * TODO check if it might be faster to extract only the needed features
	 * @param tweet
	 */
	public static void addBasicSentimentFeatures(Tweet tweet) {
		
		tweet.setPositiveWords(TextProcessing.getPositiveWords(tweet.getLemmas(), tweet.getPolaritySwitcher(), tweet.getTags()));
		tweet.setNegativeWords(TextProcessing.getNegativeWords(tweet.getLemmas(), tweet.getPolaritySwitcher(), tweet.getTags()));
		
		// Textual Components
		tweet.setNumberOfPositiveWords(TextProcessing.countPositiveWords(tweet.getPositiveWords()));
		tweet.setNumberOfNegativeWords(TextProcessing.countNegativeWords(tweet.getNegativeWords()));
		tweet.setNumberOfHighlyEmoPositiveWords(TextProcessing.countHighlyEmotionalPositiveWords(tweet.getTokensScore(), 3));
		tweet.setNumberOfHighlyEmoNegativeWords(TextProcessing.countHighlyEmotionalNegativeWords(tweet.getTokensScore(), 3));
		tweet.setNumberOfCapitalizedPositiveWords(TextProcessing.countCapitalizedPositiveWords(tweet.getTokens(), tweet.getPolaritySwitcher(), tweet.getTags()));
		tweet.setNumberOfCapitalizedNegativeWords(TextProcessing.countCapitalizedNegativeWords(tweet.getTokens(), tweet.getPolaritySwitcher(), tweet.getTags()));
		tweet.setRatioOfEmotionalWords(TextProcessing.getRatioOfEmotionalWords(tweet.getTokensScore()));
		
		// Emoticons
		tweet.setNumberOfPositiveEmoticons(TextProcessing.countPositiveEmoticons(tweet.getText()));
		tweet.setNumberOfNegativeEmoticons(TextProcessing.countPositiveEmoticons(tweet.getText()));
		tweet.setNumberOfNeutralEmoticons(TextProcessing.countPositiveEmoticons(tweet.getText()));
		tweet.setNumberOfJokingEmoticons(TextProcessing.countPositiveEmoticons(tweet.getText()));
		
		// Hashtags
		tweet.setNumberOfPositiveHashtags(TextProcessing.countPositiveHashtags(tweet.getTokens()));
		tweet.setNumberOfNegativeHashtags(TextProcessing.countNegativeHashtags(tweet.getTokens()));
		
		// SlangWords
		tweet.setNumberOfPositiveSlangs(TextProcessing.countPositiveSlangs(tweet.getTokens()));
		tweet.setNumberOfNegativeSlangs(TextProcessing.countNegativeSlangs(tweet.getTokens()));
		
		// Contrast
		if (tweet.getNumberOfPositiveWords()>0 && tweet.getNumberOfNegativeWords()>0) {
			tweet.setContrastWordsVsWords(true);
		}
		if (tweet.getNumberOfPositiveWords()>0 && tweet.getNumberOfNegativeHashtags()>0 || tweet.getNumberOfNegativeWords()>0 && tweet.getNumberOfPositiveHashtags()>0) {
			tweet.setContrastWordsVsWords(true);
		}
		if (tweet.getNumberOfPositiveWords()>0 && tweet.getNumberOfNegativeEmoticons()>0 || tweet.getNumberOfNegativeWords()>0 && tweet.getNumberOfPositiveEmoticons()>0) {
			tweet.setContrastWordsVsEmoticons(true);
		}
		if (tweet.getNumberOfPositiveHashtags()>0 && tweet.getNumberOfNegativeHashtags()>0) {
			tweet.setContrastHashtagsVsHashtags(true);
		}
		if (tweet.getNumberOfPositiveHashtags()>0 && tweet.getNumberOfNegativeEmoticons()>0 || tweet.getNumberOfNegativeHashtags()>0 && tweet.getNumberOfPositiveEmoticons()>0) {
			tweet.setContrastWordsVsEmoticons(true);
		}
		
	}
	
	/**
	 * Extract punctuation features from the tweet
	 * ALL punctuation features are extracted [because they don't occupy much space, and they don't take time to generate]
	 * TODO check if it might be faster to extract only the needed features
	 * @param tweet
	 */
	public static void addPunctuationFeatures(Tweet tweet) {
		// Punctuation Marks
		tweet.setNumberOfDots(TextProcessing.countDots(tweet.getText()));
		tweet.setNumberOfCommas(TextProcessing.countCommas(tweet.getText()));
		tweet.setNumberOfSemicolons(TextProcessing.countSemiColons(tweet.getText()));
		tweet.setNumberOfExclamationMarks(TextProcessing.countExclamationMarks(tweet.getText()));
		tweet.setNumberOfQuestionMarks(TextProcessing.countQuestionMarks(tweet.getText()));
		
		// Parenthesis and others
		tweet.setNumberOfParentheses(TextProcessing.countParentheses(tweet.getText()));
		tweet.setNumberOfBrackets(TextProcessing.countBrackets(tweet.getText()));
		tweet.setNumberOfBraces(TextProcessing.countBraces(tweet.getText()));
		
		// Words and Characters
		tweet.setTotalNumberOfCharacters(TextProcessing.countCharacters(tweet.getText()));
		tweet.setTotalNumberOfWords(TextProcessing.countNumberOfWords(tweet.getTokens()));
		tweet.setTotalNumberOfSentences(TextProcessing.countSentences(tweet.getText()));
		if (tweet.getTotalNumberOfSentences()!=0) {
			tweet.setAverageNumberOfCharactersPerSentence((double)tweet.getTotalNumberOfCharacters() / (double)tweet.getTotalNumberOfSentences());
			tweet.setAverageNumberOfWordsPerSentence((double)tweet.getTotalNumberOfWords() / (double)tweet.getTotalNumberOfSentences());
		}
		
		
		// Apostrophes and others
		tweet.setNumberOfApostrophes(TextProcessing.countApostrophes(tweet.getText()));
		tweet.setNumberOfQuotationMarks(TextProcessing.countQuotes(tweet.getText()));
		
	}
	
	/**
	 * Extract stylistic and syntactic features from the tweet
	 * ALL stylistic features are extracted [because they don't occupy much space, and they don't take time to generate]
	 * TODO check if it might be faster to extract only the needed features
	 * @param tweet
	 */
	public static void addStylisticFeatures(Tweet tweet) {
		// Use of content words
		tweet.setNumberOfNouns(TextProcessing.countNouns(tweet.getTags()));
		tweet.setNumberOfVerbs(TextProcessing.countVerbs(tweet.getTags()));
		tweet.setNumberOfAdjectives(TextProcessing.countAdjectives(tweet.getTags()));
		tweet.setNumberOfAdverbs(TextProcessing.countAdverbs(tweet.getTags()));
		tweet.setRatioOfNouns(TextProcessing.countNounsRatio(tweet.getTags()));
		tweet.setRatioOfVerbs(TextProcessing.countVerbsRatio(tweet.getTags()));
		tweet.setRatioOfAdjectives(TextProcessing.countAdjectivesRatio(tweet.getTags()));
		tweet.setRatioOfAdverbs(TextProcessing.countAdverbsRatio(tweet.getTags()));
		
		// Syntactic
		tweet.setUseOfSymbols(TextProcessing.countSymbols(tweet.getTags())>0);
		tweet.setUseOfComparativeForm(TextProcessing.useComparativeForm(tweet.getTags()));
		tweet.setUseOfSuperlativeForm(TextProcessing.useSuperlativeForm(tweet.getTags()));
		tweet.setUseOfProperNouns(TextProcessing.useProperNouns(tweet.getTags()));
		
		// Use of words
		tweet.setTotalNumberOfParticles(TextProcessing.countParticles(tweet.getTags()));
		tweet.setTotalNumberOfInterjections(TextProcessing.countInterjections(tweet.getTags()));
		tweet.setTotalNumberOfPronouns(TextProcessing.countPronouns(tweet.getTags()));
		tweet.setTotalNumberOfPronounsGroup1(TextProcessing.countPronounsGroupI(tweet.getTags(), tweet.getLemmas()));
		tweet.setTotalNumberOfPronounsGroup2(tweet.getTotalNumberOfPronouns() - tweet.getTotalNumberOfPronounsGroup1());
		if(tweet.getTags().length!=0) {
			tweet.setAverageNumberOfParticles((double)tweet.getTotalNumberOfParticles() / (double)tweet.getTags().length);
			tweet.setAverageNumberOfInterjections((double)tweet.getTotalNumberOfInterjections() / (double)tweet.getTags().length);
			tweet.setAverageNumberOfPronouns((double)tweet.getTotalNumberOfPronouns() / (double)tweet.getTags().length);
			tweet.setAverageNumberOfPronounsGroup1((double)tweet.getTotalNumberOfPronounsGroup1() / (double)tweet.getTags().length);
			tweet.setAverageNumberOfPronounsGroup2((double)tweet.getTotalNumberOfPronounsGroup2() / (double)tweet.getTags().length);
		}
		for (boolean neg : tweet.getPolaritySwitcher()) {
			if (neg) {
				tweet.setUseOfNegation(true);
				break;
			}
		}
		tweet.setNumberOfUncommonWords(TextProcessing.countUncommonWords(tweet.getLemmas()));
		if (tweet.getNumberOfUncommonWords()!=0) {
			tweet.setUseOfUncommonWords(true);
		}
	}
	
	/**
	 * Extract semantic features from the tweet
	 * Basic semantic features are extracted [because they don't occupy much space, and they don't take time to generate]
	 * TODO check if it might be faster to extract only the needed features
	 * @param tweet
	 */
	public static void addBasicSemanticFeatures(Tweet tweet) {
		tweet.setUseOfOpinionWords(TextProcessing.useOpinionWords(tweet.getText(), tweet.getLemmas()));
		tweet.setUseOfHighlySentimentalWords(tweet.getNumberOfHighlyEmoNegativeWords()!=0 || tweet.getNumberOfHighlyEmoPositiveWords()!=0);
		tweet.setUseOfUncertaintyWords(TextProcessing.useUncertaintyWords(tweet.getText(), tweet.getLemmas()));
		tweet.setUseOfActiveForm(TextProcessing.useActiveForm(tweet.getTags()));
		tweet.setUseOfPassiveForm(TextProcessing.usePassiveForm(tweet.getLemmas(), tweet.getTags()));
	}

	/**
	 * Extract Unigram features from the tweet
	 * @param tweet
	 */
	public static void addUnigramFeatures(Tweet tweet) {
		if (Features.isUseUnigramFeatures()) {
			if (Features.getUnigramTypeOfPos()!=null) {
				if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
					// Create the list of words and scores
					ArrayList<Word> words = new ArrayList<>();

					HashMap<String, Integer> scores = new HashMap<>();
					for (String cl : Parameters.getClasses()) {
						scores.put(cl, 0);
					}

					// Collect the useful words of the tweet [nouns, verbs, adjectives and adverbs]
					for (int i = 0; i < tweet.getTags().length; i++) {
						if (tweet.getTags()[i].startsWith("NN") || tweet.getTags()[i].startsWith("VB") || tweet.getTags()[i].startsWith("JJ") || tweet.getTags()[i].startsWith("RB")) {
							
							words.add(new Word(tweet.getLemmas()[i], Loader.getSimplifiedPosTagsConverter().get(tweet.getTags()[i]), tweet.getPolaritySwitcher()[i]));
						}
					}

					// Compare the words of the tweet with the unigrams we have
					for (Word word : words) {
						for (Word wd : Parameters.getUnigrams()) {
							if (wd.isCandidate(word)) {
								if (scores.containsKey(wd.getEmotion())) {
									if (!word.isNegated()) {
										scores.replace(wd.getEmotion(), scores.get(wd.getEmotion()) + 1);
									} else {
										scores.replace(wd.getEmotion(), scores.get(wd.getEmotion()) - 1);
									}
								}
							}
						}
					}
					
					
					// Attribute the scores to the tweet
					tweet.setUnigramCountPerClass(scores);
					
					// This is for quantification
					int[] quantificationScores = new int[Parameters.getClasses().size()];
					for (int i = 0; i< Parameters.getClasses().size(); i++) {
						quantificationScores[i] = scores.get(Parameters.getClasses().get(i));
					}
					tweet.setUnigramScores(quantificationScores);

				} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
					// Create the list of words and scores
					ArrayList<Word> nouns = new ArrayList<>();
					ArrayList<Word> verbs = new ArrayList<>();
					ArrayList<Word> adjectives = new ArrayList<>();
					ArrayList<Word> adverbs = new ArrayList<>();

					HashMap<String, Integer> nounScores = new HashMap<>();
					for (String cl : Parameters.getClasses()) {
						nounScores.put(cl, 0);
					}

					HashMap<String, Integer> verbScores = new HashMap<>();
					for (String cl : Parameters.getClasses()) {
						verbScores.put(cl, 0);
					}

					HashMap<String, Integer> adjectiveScores = new HashMap<>();
					for (String cl : Parameters.getClasses()) {
						adjectiveScores.put(cl, 0);
					}

					HashMap<String, Integer> adverbScores = new HashMap<>();
					for (String cl : Parameters.getClasses()) {
						adverbScores.put(cl, 0);
					}

					// Collect the useful words of the tweet [nouns, verbs, adjectives and adverbs]
					for (int i = 0; i < tweet.getTags().length; i++) {
						if (tweet.getTags()[i].startsWith("NN")) {
							nouns.add(new Word(tweet.getLemmas()[i], "NOUN", tweet.getPolaritySwitcher()[i]));
						} else if (tweet.getTags()[i].startsWith("VB")) {
							verbs.add(new Word(tweet.getLemmas()[i], "VERB", tweet.getPolaritySwitcher()[i]));
						} else if (tweet.getTags()[i].startsWith("JJ")) {
							adjectives.add(new Word(tweet.getLemmas()[i], "ADJECTIVE", tweet.getPolaritySwitcher()[i]));
						} else if (tweet.getTags()[i].startsWith("RB")) {
							adverbs.add(new Word(tweet.getLemmas()[i], "ADVERB", tweet.getPolaritySwitcher()[i]));
						}
					}
					// Compare the words of the tweet with the unigrams we have
					for (Word word : nouns) {
						for (Word wd : Parameters.getUnigrams()) {
							if (wd.isCandidate(word)) {
								if (nounScores.containsKey(wd.getEmotion())) {
									if (!word.isNegated()) {
										nounScores.replace(wd.getEmotion(), nounScores.get(wd.getEmotion()) + 1);
									} else {
										nounScores.replace(wd.getEmotion(), nounScores.get(wd.getEmotion()) - 1);
									}
									
								}
							}
						}
					}

					for (Word word : verbs) {
						for (Word wd : Parameters.getUnigrams()) {
							if (wd.isCandidate(word)) {
								if (verbScores.containsKey(wd.getEmotion())) {
									if (!word.isNegated()) {
										verbScores.replace(wd.getEmotion(), verbScores.get(wd.getEmotion()) + 1);
									} else {
										verbScores.replace(wd.getEmotion(), verbScores.get(wd.getEmotion()) - 1);
									}
									
								}
							}
						}
					}

					for (Word word : adjectives) {
						for (Word wd : Parameters.getUnigrams()) {
							if (wd.isCandidate(word)) {
								if (adjectiveScores.containsKey(wd.getEmotion())) {
									if (!word.isNegated()) {
										adjectiveScores.replace(wd.getEmotion(), adjectiveScores.get(wd.getEmotion()) + 1);
									} else {
										adjectiveScores.replace(wd.getEmotion(), adjectiveScores.get(wd.getEmotion()) - 1);
									}
									
								}
							}
						}
					}

					for (Word word : adverbs) {
						for (Word wd : Parameters.getUnigrams()) {
							if (wd.isCandidate(word)) {
								if (adverbScores.containsKey(wd.getEmotion())) {
									if (!word.isNegated()) {
										adverbScores.replace(wd.getEmotion(), adverbScores.get(wd.getEmotion()) + 1);
									} else {
										adverbScores.replace(wd.getEmotion(), adverbScores.get(wd.getEmotion()) - 1);
									}
									
								}
							}
						}
					}

					// Attribute the scores to the tweet
					tweet.setNounCountPerClass(nounScores);
					tweet.setVerbCountPerClass(verbScores);
					tweet.setAdjectiveCountPerClass(adjectiveScores);
					tweet.setAdverbCountPerClass(adverbScores);
					
					// This is for quantification
					int[] quantificationScores = new int[Parameters.getClasses().size()];
					for (int i = 0; i< Parameters.getClasses().size(); i++) {
						
						String sent = Parameters.getClasses().get(i);
						quantificationScores[i] = nounScores.get(sent) + verbScores.get(sent) + adjectiveScores.get(sent) + adverbScores.get(sent);
					}
					tweet.setUnigramScores(quantificationScores);
				} 
			}
		}
	}

	/**
	 * Extract the top words features from the tweet
	 * [THIS REQUIRE THAT THE TOP WORDS HAVE ALREADY BEEN EXTRACTED FROM THE TRAINING SET !!!
	 * @param tweet
	 */
	public static void addTopWordsFeatures(Tweet tweet) {
		if (Features.getTopWordsTypeOfPos()!=null) {
			// CASE [PoS are considered together]
			if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
				ArrayList<String> words = TextProcessing.getImportantWordsAsList(tweet.getLemmas(), tweet.getTags());

				// case: Each word is a single feature [features are booleans]
				if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
					ArrayList<Boolean> topWordsFeatures = new ArrayList<>();
					for (String sentiment : Parameters.getTopWords().keySet()) {
						for (String word : Parameters.getTopWords().get(sentiment)) {
							if (words.contains(word)) {
								topWordsFeatures.add(true);
							} else {
								topWordsFeatures.add(false);
							}
						}
					}
					boolean[] tweetTopWordsFeatures = new boolean[topWordsFeatures.size()];
					for (int i = 0; i < topWordsFeatures.size(); i++) {
						tweetTopWordsFeatures[i] = topWordsFeatures.get(i);
					}
					tweet.setTopWordsSeparatelyBoolean(tweetTopWordsFeatures);

					// case: all words for the same class are summed [features are integers]
				} else

				if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
					int[] tweetTopWordsFeatures = new int[Parameters.getClasses().size()];

					for (int i = 0; i < Parameters.getClasses().size(); i++) {
						String sentiment = Parameters.getClasses().get(i);
						tweetTopWordsFeatures[i] = 0;
						for (String word : Parameters.getTopWords().get(sentiment)) {
							if (words.contains(word)) {
								tweetTopWordsFeatures[i]++;
							}
						}
					}
					tweet.setTopWordSummed(tweetTopWordsFeatures);
				}

				// CASE [PoS are considered separately]
			} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
				ArrayList<String> words = TextProcessing.getImportantWordsAsList(tweet.getLemmas(), tweet.getTags());

				// case: Each word is a single feature [features are booleans]
				if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
					ArrayList<Boolean> topWordsFeatures = new ArrayList<>();
					// Nouns
					if (Features.isUseTopWordsNouns()) {
						for (String sentiment : Parameters.getTopNouns().keySet()) {
							for (String word : Parameters.getTopNouns().get(sentiment)) {
								if (words.contains(word)) {
									topWordsFeatures.add(true);
								} else {
									topWordsFeatures.add(false);
								}
							}
						}
					}

					// Verbs
					if (Features.isUseTopWordsVerbs()) {
						for (String sentiment : Parameters.getTopVerbs().keySet()) {
							for (String word : Parameters.getTopVerbs().get(sentiment)) {
								if (words.contains(word)) {
									topWordsFeatures.add(true);
								} else {
									topWordsFeatures.add(false);
								}
							}
						}
					}

					// Adjectives
					if (Features.isUseTopWordsAdjectives()) {
						for (String sentiment : Parameters.getTopAdjectives().keySet()) {
							for (String word : Parameters.getTopAdjectives().get(sentiment)) {
								if (words.contains(word)) {
									topWordsFeatures.add(true);
								} else {
									topWordsFeatures.add(false);
								}
							}
						}
					}
					// Adverbs
					if (Features.isUseTopWordsAdverbs()) {
						for (String sentiment : Parameters.getTopAdverbs().keySet()) {
							for (String word : Parameters.getTopAdverbs().get(sentiment)) {
								if (words.contains(word)) {
									topWordsFeatures.add(true);
								} else {
									topWordsFeatures.add(false);
								}
							}
						}
					}
					boolean[] tweetTopWordsFeatures = new boolean[topWordsFeatures.size()];
					for (int i = 0; i < topWordsFeatures.size(); i++) {
						tweetTopWordsFeatures[i] = topWordsFeatures.get(i);
					}
					tweet.setTopWordsSeparatelyBoolean(tweetTopWordsFeatures);
				} else

				// case: all words for the same class are summed [features are integers]
				if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {

					int countOfPos = 0;
					if (Features.isUseTopWordsNouns())
						countOfPos++;
					if (Features.isUseTopWordsVerbs())
						countOfPos++;
					if (Features.isUseTopWordsAdjectives())
						countOfPos++;
					if (Features.isUseTopWordsAdverbs())
						countOfPos++;

					int[] tweetTopWordsFeatures = new int[Parameters.getClasses().size() * countOfPos];

					int j = 0;

					for (int i = 0; i < Parameters.getClasses().size(); i++) {
						String sentiment = Parameters.getClasses().get(i);
						// Nouns
						tweetTopWordsFeatures[j] = 0;
						for (String word : Parameters.getTopNouns().get(sentiment)) {
							if (words.contains(word)) {
								tweetTopWordsFeatures[j]++;
							}
						}
						j++;
						// Verbs
						tweetTopWordsFeatures[j] = 0;
						for (String word : Parameters.getTopVerbs().get(sentiment)) {
							if (words.contains(word)) {
								tweetTopWordsFeatures[j]++;
							}
						}
						j++;
						// Adjectives
						tweetTopWordsFeatures[j] = 0;
						for (String word : Parameters.getTopAdjectives().get(sentiment)) {
							if (words.contains(word)) {
								tweetTopWordsFeatures[j]++;
							}
						}
						j++;
						// Adverbs
						tweetTopWordsFeatures[j] = 0;
						for (String word : Parameters.getTopAdverbs().get(sentiment)) {
							if (words.contains(word)) {
								tweetTopWordsFeatures[j]++;
							}
						}
						j++;
					}
					tweet.setTopWordSummed(tweetTopWordsFeatures);
				}

			} 
		}
	}
	
	/**
	 * Extract the pattern features from the tweet
	 * [THIS REQUIRE THAT THE PATTERNS HAVE ALREADY BEEN EXTRACTED FROM THE TRAINING SET !!!
	 * @param tweet
	 */
	public static void addBasicPatternsFeatures(Tweet tweet) {
		// TODO This is very slow. Find a faster way to store/process/compare patterns
		if (Features.getPatternFeaturesType()==null) {
			int numberOfFeatures = 0;
			double[] scores = new double[numberOfFeatures];
			tweet.setPatternScores(scores);
		} else  {
			// Case of unique features
			if (Features.getPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {

				int numberOfFeatures = 0;

				for (String sentiment : Parameters.getSingleLengthPatterns().keySet()) {
					numberOfFeatures = numberOfFeatures + Parameters.getSingleLengthPatterns().get(sentiment).size();
				}

				double[] scores = new double[numberOfFeatures];
				String[] fullPattern = TextProcessing.extractFullPatternVecor(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore());
				
				int i = 0;

				for (String sentiment : Parameters.getSingleLengthPatterns().keySet()) {
					for (Pattern pattern : Parameters.getSingleLengthPatterns().get(sentiment)) {
						scores[i] = PatternProcessing.comparePatterns(fullPattern, pattern.getPattern(), Features.getAlpha(), Features.getGamma());
						i++;
					}
				}
				tweet.setPatternScores(scores);
			} else

			// Case of summed features
			if (Features.getPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
				double[] scores = new double[Parameters.getClasses().size() * (Features.getMaxPatternLength() - Features.getMinPatternLength() + 1)];
				
				int numSent = Parameters.getClasses().size();
				int numLength = (Features.getMaxPatternLength() - Features.getMinPatternLength() + 1);
				
				double[][] scoreMatrix = new double[numSent][numLength]; // Used for quantification
				
				int globalCounter = 0;
				int i = 0;
				
				
				String[] fullPattern = TextProcessing.extractFullPatternVecor(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore());

				
				for (String sentiment : Parameters.getMultiLengthPatterns().keySet()) {
					int j = 0;
					for (int length : Parameters.getMultiLengthPatterns().get(sentiment).keySet()) {
						double[] tempScores = new double[Features.getBasicPatternsKnn()];
						for (Pattern pattern : Parameters.getMultiLengthPatterns().get(sentiment).get(length)) {
							
							double score = PatternProcessing.comparePatterns(fullPattern, pattern.getPattern(),	Features.getAlpha(), Features.getGamma());
							if(score>tempScores[0]) {
								tempScores[0] = score;
								tempScores = Commons.sort(tempScores);
							}
							if (tempScores[0] == 1) {
								break;
							}
						}
						scores[globalCounter] = Commons.sumArray(tempScores);
						scoreMatrix[i][j] = Commons.sumArray(tempScores);  // Used for quantification
						j++;
						globalCounter++;
					}
					i++;
				}
				tweet.setPatternScores(scores);
				tweet.setBasicPatternQuantificationScores(scoreMatrix); // Used for quantification
			} 
		}
	}
	
	/**
	 * Extract the pattern features from the tweet [Numeric format]
	 * [THIS REQUIRE THAT THE PATTERNS HAVE ALREADY BEEN EXTRACTED FROM THE TRAINING SET !!!
	 * @param tweet
	 */
	public static void addBasicPatternsFeaturesNumeric(Tweet tweet) {
		// TODO This is very slow. Find a faster way to store/process/compare patterns
		if (Features.getPatternFeaturesType()==null) {
			int numberOfFeatures = 0;
			double[] scores = new double[numberOfFeatures];
			tweet.setPatternScores(scores);
		} else  {
			// Case of unique features
			if (Features.getPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {

				int numberOfFeatures = 0;

				for (String sentiment : Parameters.getSingleLengthPatternsNumeric().keySet()) {
					numberOfFeatures = numberOfFeatures + Parameters.getSingleLengthPatternsNumeric().get(sentiment).size();
				}

				double[] scores = new double[numberOfFeatures];
				int[] fullPattern = TextProcessing.extractFullNumericPatternVecor(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore());
				
				int i = 0;

				for (String sentiment : Parameters.getSingleLengthPatternsNumeric().keySet()) {
					for (PatternNumeric pattern : Parameters.getSingleLengthPatternsNumeric().get(sentiment)) {
						scores[i] = PatternProcessing.comparePatterns(fullPattern, pattern.getPattern(), Features.getAlpha(), Features.getGamma());
						i++;
					}
				}
				tweet.setPatternScores(scores);
			} else

			// Case of summed features
//			if (Features.getPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
//				double[] scores = new double[Parameters.getClasses().size() * (Features.getMaxPatternLength() - Features.getMinPatternLength() + 1)];
//				
//				int numSent = Parameters.getClasses().size();
//				int numLength = (Features.getMaxPatternLength() - Features.getMinPatternLength() + 1);
//				
//				double[][] scoreMatrix = new double[numSent][numLength]; // Used for quantification
//				
//				int globalCounter = 0;
//				int i = 0;
//				
//				
//				int[] fullPattern = TextProcessing.extractFullNumericPatternVecor(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore());
//
//				
//				for (String sentiment : Parameters.getMultiLengthPatternsNumeric().keySet()) {
//					int j = 0;
//					for (int length : Parameters.getMultiLengthPatternsNumeric().get(sentiment).keySet()) {
//						double[] tempScores = new double[Features.getBasicPatternsKnn()];
//						for (PatternNumeric pattern : Parameters.getMultiLengthPatternsNumeric().get(sentiment).get(length)) {
//							
//							double score = PatternProcessing.comparePatterns(fullPattern, pattern.getPattern(),	Features.getAlpha(), Features.getGamma());
//							if(score>tempScores[0]) {
//								tempScores[0] = score;
//								tempScores = Commons.sort(tempScores);
//							}
//							if (tempScores[0] == 1) {
//								break;
//							}
//						}
//						scores[globalCounter] = Commons.sumArray(tempScores);
//						scoreMatrix[i][j] = Commons.sumArray(tempScores);  // Used for quantification
//						j++;
//						globalCounter++;
//					}
//					i++;
//				}
//				tweet.setPatternScores(scores);
//				tweet.setBasicPatternQuantificationScores(scoreMatrix); // Used for quantification
//			} 
			//--------------------------------------------------------
			if (Features.getPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
				double[] scores = new double[Parameters.getClasses().size() * (Features.getMaxPatternLength() - Features.getMinPatternLength() + 1)];
				
				int numSent = Parameters.getClasses().size();
				int numLength = (Features.getMaxPatternLength() - Features.getMinPatternLength() + 1);
				
				double[][] scoreMatrix = new double[numSent][numLength]; // Used for quantification
				
				int globalCounter = 0;
				int i = 0;
				
				int[] fullPattern = TextProcessing.extractFullNumericPatternVecor(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore());

				for (String sentiment : Parameters.getMultiLengthPatternsNumeric().keySet()) {
					int j = 0;
					for (int length : Parameters.getMultiLengthPatternsNumeric().get(sentiment).keySet()) {
						double[] tempScores = new double[Features.getBasicPatternsKnn()];
						
						int size = Parameters.getMultiLengthPatternsNumeric().get(sentiment).get(length).size();
						
						double[] t = new double[size];
						
						for (int m=0; m<size; m++) {
							PatternNumeric pattern = Parameters.getMultiLengthPatternsNumeric().get(sentiment).get(length).get(m);
							t[m] = PatternProcessing.comparePatterns(fullPattern, pattern.getPattern(),	Features.getAlpha(), Features.getGamma());
						}
						
						tempScores = Commons.findTopNInsersion(t, Features.getBasicPatternsKnn());
						
						scores[globalCounter] = Commons.sumArray(tempScores);
						scoreMatrix[i][j] = Commons.sumArray(tempScores);  // Used for quantification
						j++;
						globalCounter++;
					}
					i++;
				}
				tweet.setPatternScores(scores);
				tweet.setBasicPatternQuantificationScores(scoreMatrix); // Used for quantification
			}
			//--------------------------------------------------------
		}
	}
	
	//=================================//
	//        ADVANCED FEATURES        //
	//=================================//
	
	/**
	 * Extract the advanced sentiment features from the tweet
	 * @param tweet
	 */
	public static void addAdvancedSentimentFeatures(Tweet tweet) {
		// TODO add this for the next work
	}
	
	/**
	 * Extract the advanced semantic features from the tweet
	 * @param tweet
	 */
	public static void addAdvancedSemanticFeatures(Tweet tweet) {
		// TODO add this for the next work
	}
	
	/**
	 * Extract the advanced pattern features from the tweet
	 * [THIS REQUIRE THAT THE PATTERNS HAVE ALREADY BEEN EXTRACTED FROM THE TRAINING SET !!!]
	 * @param tweet
	 */
	public static void addAdvancedPatternsFeatures(Tweet tweet) {
		// TODO This is very slow. Find a faster way to store/process/compare patterns
		if (Features.getAdvancedPatternFeaturesType()==null) {
			int numberOfFeatures = 0;
			double[] scores = new double[numberOfFeatures];
			tweet.setAdvancedPatternScores(scores);
		} else  {
			// Case of unique features
			if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {

				int numberOfFeatures = 0;

				for (String sentiment : Parameters.getSingleLengthAdvancedPatterns().keySet()) {
					numberOfFeatures = numberOfFeatures + Parameters.getSingleLengthAdvancedPatterns().get(sentiment).size();
				}

				double[] scores = new double[numberOfFeatures];
				
				
				int i = 0;
				for (String sentiment : Parameters.getClasses()) {
					String[] fullPattern = TextProcessing.extractFullAdvancedPatternVector(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), sentiment, Parameters.getRawUnigrams().get(sentiment));
					
					for (Pattern pattern : Parameters.getSingleLengthAdvancedPatterns().get(sentiment)) {
						scores[i] = PatternProcessing.comparePatterns(fullPattern, pattern.getPattern(), Features.getAlpha(), Features.getGamma());
						i++;
					}

				}
				tweet.setAdvancedPatternScores(scores);
			} else

			// Case of summed features
			if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
				double[] scores = new double[Parameters.getClasses().size() * (Features.getAdvancedMaxPatternLength() - Features.getAdvancedMinPatternLength() + 1)];
				
				int numSent = Parameters.getClasses().size();
				int numLength = (Features.getAdvancedMaxPatternLength() - Features.getAdvancedMinPatternLength() + 1);
				
				double[][] scoreMatrix = new double[numSent][numLength];
				
				int globalCounter = 0;
				int i = 0;
				
				for (String sentiment : Parameters.getClasses()) {
					String[] fullPattern = TextProcessing.extractFullAdvancedPatternVector(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), sentiment, Parameters.getRawUnigrams().get(sentiment));
					int j = 0;
					for (int length : Parameters.getMultiLengthAdvancedPatterns().get(sentiment).keySet()) {
						double[] tempScores = new double[Features.getAdvancedPatternsKnn()];
						for (Pattern pattern : Parameters.getMultiLengthAdvancedPatterns().get(sentiment).get(length)) {
							
							double score = PatternProcessing.comparePatterns(fullPattern, pattern.getPattern(),	Features.getAlpha(), Features.getGamma());
							if(score>tempScores[0]) {
								tempScores[0] = score;
								tempScores = Commons.sort(tempScores);
							}
							if (tempScores[0] == 1) {
								break;
							}
						}
						scores[globalCounter] = Commons.sumArray(tempScores);
						scoreMatrix[i][j] = Commons.sumArray(tempScores);
						j++;
						globalCounter++;
					}
					i++;
				}

				tweet.setAdvancedPatternScores(scores);
				tweet.setAdvancedPatternQuantificationScores(scoreMatrix);
			} 
		}
		
	}
	
	/**
	 * Extract the advanced pattern features from the tweet [Numeric format]
	 * [THIS REQUIRE THAT THE PATTERNS HAVE ALREADY BEEN EXTRACTED FROM THE TRAINING SET !!!]
	 * @param tweet
	 */
	public static void addAdvancedPatternsFeaturesNumeric(Tweet tweet) {
		// TODO This is very slow. Find a faster way to store/process/compare patterns
		if (Features.getAdvancedPatternFeaturesType()==null) {
			int numberOfFeatures = 0;
			double[] scores = new double[numberOfFeatures];
			tweet.setAdvancedPatternScores(scores);
		} else  {
			// Case of unique features
			if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {

				int numberOfFeatures = 0;

				for (String sentiment : Parameters.getSingleLengthAdvancedPatternsNumeric().keySet()) {
					numberOfFeatures = numberOfFeatures + Parameters.getSingleLengthAdvancedPatternsNumeric().get(sentiment).size();
				}

				double[] scores = new double[numberOfFeatures];
				
				int i = 0;
				for (String sentiment : Parameters.getClasses()) {
					int[] fullPattern = TextProcessing.extractFullNumericAdvancedPatternVector(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), sentiment, Parameters.getRawUnigrams().get(sentiment));
					
					for (PatternNumeric pattern : Parameters.getSingleLengthAdvancedPatternsNumeric().get(sentiment)) {
						scores[i] = PatternProcessing.comparePatterns(fullPattern, pattern.getPattern(), Features.getAlpha(), Features.getGamma());
						i++;
					}

				}
				tweet.setAdvancedPatternScores(scores);
			} else

//			// Case of summed features
//			if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
//				double[] scores = new double[Parameters.getClasses().size() * (Features.getAdvancedMaxPatternLength() - Features.getAdvancedMinPatternLength() + 1)];
//				
//				int numSent = Parameters.getClasses().size();
//				int numLength = (Features.getAdvancedMaxPatternLength() - Features.getAdvancedMinPatternLength() + 1);
//				
//				double[][] scoreMatrix = new double[numSent][numLength];
//				
//				int globalCounter = 0;
//				int i = 0;
//				
//				for (String sentiment : Parameters.getClasses()) {
//					int[] fullPattern = TextProcessing.extractFullNumericAdvancedPatternVector(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), sentiment, Parameters.getRawUnigrams().get(sentiment));
//					int j = 0;
//					for (int length : Parameters.getMultiLengthAdvancedPatternsNumeric().get(sentiment).keySet()) {
//						double[] tempScores = new double[Features.getAdvancedPatternsKnn()];
//						for (PatternNumeric pattern : Parameters.getMultiLengthAdvancedPatternsNumeric().get(sentiment).get(length)) {
//							
//							double score = PatternProcessing.comparePatterns(fullPattern, pattern.getPattern(),	Features.getAlpha(), Features.getGamma());
//							if(score>tempScores[0]) {
//								tempScores[0] = score;
//								tempScores = Commons.sort(tempScores);
//							}
//							if (tempScores[0] == 1) {
//								break;
//							}
//						}
//						scores[globalCounter] = Commons.sumArray(tempScores);
//						scoreMatrix[i][j] = Commons.sumArray(tempScores);
//						j++;
//						globalCounter++;
//					}
//					i++;
//				}
//
//				tweet.setAdvancedPatternScores(scores);
//				tweet.setAdvancedPatternQuantificationScores(scoreMatrix);
//			}
				
			// Case of summed features
			if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
				double[] scores = new double[Parameters.getClasses().size() * (Features.getAdvancedMaxPatternLength() - Features.getAdvancedMinPatternLength() + 1)];
				
				int numSent = Parameters.getClasses().size();
				int numLength = (Features.getAdvancedMaxPatternLength() - Features.getAdvancedMinPatternLength() + 1);
				
				double[][] scoreMatrix = new double[numSent][numLength];
				
				int globalCounter = 0;
				int i = 0;
				
				for (String sentiment : Parameters.getClasses()) {
					int[] fullPattern = TextProcessing.extractFullNumericAdvancedPatternVector(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), sentiment, Parameters.getRawUnigrams().get(sentiment));
					int j = 0;
					for (int length : Parameters.getMultiLengthAdvancedPatternsNumeric().get(sentiment).keySet()) {
						
						double[] tempScores = new double[Features.getAdvancedPatternsKnn()];
						
						int size = Parameters.getMultiLengthAdvancedPatternsNumeric().get(sentiment).get(length).size();
						
						double[] t = new double[size];
						
						for (int m=0; m<size; m++) {
							
							PatternNumeric pattern = Parameters.getMultiLengthAdvancedPatternsNumeric().get(sentiment).get(length).get(m);
							
							t[m] = PatternProcessing.comparePatterns(fullPattern, pattern.getPattern(),	Features.getAlpha(), Features.getGamma());
							
						}
						
						tempScores = Commons.findTopNInsersion(t, Features.getAdvancedPatternsKnn());
						
						scores[globalCounter] = Commons.sumArray(tempScores);
						scoreMatrix[i][j] = Commons.sumArray(tempScores);
						j++;
						globalCounter++;
					}
					i++;
				}

				tweet.setAdvancedPatternScores(scores);
				tweet.setAdvancedPatternQuantificationScores(scoreMatrix);
			} 

		}
		
	}
	
	/**
	 * Extract the advanced unigram features from the tweet
	 * @param tweet
	 */
	public static void addAdvancedUnigramFeatures(Tweet tweet) {
		
		boolean[] scores = new boolean[Parameters.getAdvancedUnigrams().keySet().size()];
		
		if (Features.getIsLemma() != null) {
			if (Features.getIsLemma().equals(Features.IsLemma.Lemmas)) {
				int i = 0;
				for (String unigram : Parameters.getAdvancedUnigrams().keySet()) {
					for (String lemma : tweet.getLemmas()) {
						if (lemma.equals(unigram)) {
							scores[i] = true;
							break;
						}
					}
					i++;
				}
			} else if (Features.getIsLemma().equals(Features.IsLemma.Words)) {
				int i = 0;
				for (String unigram : Parameters.getAdvancedUnigrams().keySet()) {
					for (String token : tweet.getTokens()) {
						if (token.equals(unigram)) {
							scores[i] = true;
							break;
						}
					}
					i++;
				}
			} 
		}
		tweet.setAdvUnigramFeatures(scores);
	}
	
	
	//=================================//
	//     QUANTIFICATION-RELATED      //
	//=================================//
	
	/**
	 * Add the Unigram scores for the different sentiments for a given tweet
	 * @param tweet
	 * @param sentiments
	 */
	public static void addUnigramScores(Tweet tweet, ArrayList<String> sentiments) {
		tweet.setUnigramFinalScores(QuantifierProcessing.generateUnigramScores(sentiments, tweet.getUnigramScores(), tweet.getPredictedClass()));
	}
	
	/**
	 * Add the Basic Pattern scores for the different sentiments for a given tweet
	 * @param tweet
	 * @param sentiments
	 * @param factors
	 */
	public static void addBasicPatternScores(Tweet tweet, ArrayList<String> sentiments, double[] factors) {
		tweet.setBasicPatternsFinalScores(QuantifierProcessing.generatePatternScores(sentiments, tweet.getBasicPatternQuantificationScores(), factors, tweet.getPredictedClass()));
	}
	
	/**
	 * Add the Advanced Pattern scores for the different sentiments for a given tweet
	 * @param tweet
	 * @param sentiments
	 * @param factors
	 */
	public static void addAdvancedPatternScores(Tweet tweet, ArrayList<String> sentiments, double[] factors) {
		tweet.setAdvancedPatternsFinalScores(QuantifierProcessing.generatePatternScores(sentiments, tweet.getAdvancedPatternQuantificationScores(), factors, tweet.getPredictedClass()));
	}
	
	public static void generateFinalScore(Tweet tweet, boolean isUnigrams, boolean isBasicPatterns, boolean isAdvancedPatterns, double tau, double mu, double nu) {
		
		HashMap<String, Double> scoredSentiments = new HashMap<>();
		
		double max = 0; // This has been added after noticing that the max could be something other than 1
		double min = 10000;
		
		for (String sentiment : Parameters.getClasses()) {
			double s1 = 0;
			double s2 = 0;
			double s3 = 0;
			if (isUnigrams) {
				s1 = tweet.getUnigramFinalScores().get(sentiment);
			}
			if (isBasicPatterns) {
				s2 = tweet.getBasicPatternsFinalScores().get(sentiment);
			}
			if (isAdvancedPatterns) {
				s3 = tweet.getAdvancedPatternsFinalScores().get(sentiment);
			}
			scoredSentiments.put(sentiment, tau * s1 + mu * s2 + nu * s3);
			if (tau * s1 + mu * s2 + nu * s3 > max) { // This has been added after noticing that the max could be something other than 1
				max = tau * s1 + mu * s2 + nu * s3;
			}
			if (tau * s1 + mu * s2 + nu * s3 < min && tau * s1 + mu * s2 + nu * s3 !=0) {
				min = tau * s1 + mu * s2 + nu * s3;
			}
		}
		
		if (max > 0) { // This has been added after noticing that the max could be something other than 1 and the min is always 0
			for (String sentiment : Parameters.getClasses()) {
				double s1 = 0;
				double s2 = 0;
				double s3 = 0;
				if (isUnigrams) {
					s1 = tweet.getUnigramFinalScores().get(sentiment);
				}
				if (isBasicPatterns) {
					s2 = tweet.getBasicPatternsFinalScores().get(sentiment);
				}
				if (isAdvancedPatterns) {
					s3 = tweet.getAdvancedPatternsFinalScores().get(sentiment);
				}
				
				double s = 0;
				if (min != max) {
					s = ((tau * s1 + mu * s2 + nu * s3) - min) / (max - min);
				} else {
					min = 0;
					s = ((tau * s1 + mu * s2 + nu * s3) - min) / (max - min);
				}
				scoredSentiments.put(sentiment, s);
			}
		}
		
		
		tweet.setFinalScores(scoredSentiments);
	}
	
	public static void generateFinalScoreBackup(Tweet tweet, boolean isUnigrams, boolean isBasicPatterns, boolean isAdvancedPatterns, double tau, double mu, double nu) {
		
		HashMap<String, Double> scoredSentiments = new HashMap<>();
		
		double max = 0; // This has been added after noticing that the max could be something other than 1
		
		for (String sentiment : Parameters.getClasses()) {
			double s1 = 0;
			double s2 = 0;
			double s3 = 0;
			if (isUnigrams) {
				s1 = tweet.getUnigramFinalScores().get(sentiment);
			}
			if (isBasicPatterns) {
				s2 = tweet.getBasicPatternsFinalScores().get(sentiment);
			}
			if (isAdvancedPatterns) {
				s3 = tweet.getAdvancedPatternsFinalScores().get(sentiment);
			}
			scoredSentiments.put(sentiment, tau * s1 + mu * s2 + nu * s3);
			if (tau * s1 + mu * s2 + nu * s3 > max) { // This has been added after noticing that the max could be something other than 1
				max = tau * s1 + mu * s2 + nu * s3;
			}
		}
		
		if (max > 0) { // This has been added after noticing that the max could be something other than 1
			// Commons.print("------------------------------------------------------");
			for (String sentiment : Parameters.getClasses()) {
				double s1 = 0;
				double s2 = 0;
				double s3 = 0;
				if (isUnigrams) {
					s1 = tweet.getUnigramFinalScores().get(sentiment);
				}
				if (isBasicPatterns) {
					s2 = tweet.getBasicPatternsFinalScores().get(sentiment);
				}
				if (isAdvancedPatterns) {
					s3 = tweet.getAdvancedPatternsFinalScores().get(sentiment);
				}
				
				double s = (tau * s1 + mu * s2 + nu * s3) / max;
				scoredSentiments.put(sentiment, s);
				// Commons.print(sentiment + " =" + s);
			}
		}
		
		
		tweet.setFinalScores(scoredSentiments);
	}
	
	public static void predictClasses(Tweet tweet, double threshold) {
		tweet.setPredictedClasses(QuantifierProcessing.predictClasses(tweet.getFinalScores(), threshold));
	}
	
	//-----------------------------------------------------------------------------------//
	
	// Applied only on the training set
	
	//=================================//
	//    THE TOP WORDS EXTRACTION     //
	//=================================//
	
	public static void importTopWords() {

		if (Reader.isValidTopWordsFile(Parameters.getTopWordsImportedFileLocation())) {
			Reader.importTopWords(Parameters.getTopWordsImportedFileLocation(), Features.getTopWordsTypeOfPos());
		} else {
			Parameters.setTopWords(new HashMap<>());
			Parameters.setTopNouns(new HashMap<>());
			Parameters.setTopVerbs(new HashMap<>());
			Parameters.setTopAdjectives(new HashMap<>());
			Parameters.setTopAdverbs(new HashMap<>());
		}
	}
	
	public static void extractTopWords(ArrayList<Tweet> tweets) {
		
		if (Features.getTopWordsTypeOfPos()!=null) {
			if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
				extractTopWordsTogether(tweets, Features.getTopWordsMinRatio(), Features.getNumberOfTopWordsPerClass());
			} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
				extractTopWordsSeparated(tweets, Features.getTopWordsMinRatio(), Features.getNumberOfTopWordsPerPos());
			} else {
				if (Parameters.getTopWords() == null) {
					Parameters.setTopWords(new HashMap<>());
				}
				if (Parameters.getTopNouns() == null) {
					Parameters.setTopNouns(new HashMap<>());
				}
				if(Parameters.getTopVerbs() == null) {
					Parameters.setTopVerbs(new HashMap<>());
				}
				if(Parameters.getTopAdjectives() == null) {
					Parameters.setTopAdjectives(new HashMap<>());
				}
				if(Parameters.getTopAdverbs() == null) {
					Parameters.setTopAdverbs(new HashMap<>());
				}
					
				
			} 
		}
	}
	
	private static void extractTopWordsTogether(ArrayList<Tweet> tweets, double ratio, int limit) {
		ArrayList<TopWord> topWordsList = new ArrayList<>();
		
		HashMap<String, ArrayList<String>> finalList = new HashMap<>();
		
		// Collect all the words of all the tweets
		if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
			for (Tweet tweet : tweets) {
				for (int i=0; i<tweet.getLemmas().length; i++) {
					if (Commons.isRelevant(tweet.getTags()[i]) && !Loader.getStopCache().containsKey(tweet.getLemmas()[i]) && Loader.getEnglishWords().containsKey(tweet.getLemmas()[i])) {
						TopWord word = new TopWord(tweet.getLemmas()[i].toLowerCase(), tweet.getTags()[i]);
						if (topWordsList.contains(word)) {
							int index = topWordsList.indexOf(word);
							int count = topWordsList.get(index).getOccurrences().get(tweet.getTweetClass()) + 1;
							topWordsList.get(index).getOccurrences().put(tweet.getTweetClass(), count);
							if(Commons.isPositiveSentiment(tweet.getTweetClass())) {
								topWordsList.get(index).setPosOccurrences(topWordsList.get(index).getPosOccurrences() + 1);
							} else if(Commons.isNegativeSentiment(tweet.getTweetClass())) {
								topWordsList.get(index).setNegOccurrences(topWordsList.get(index).getNegOccurrences() + 1);
							}
						} else {
							word.getOccurrences().put(tweet.getTweetClass(), 1);
							topWordsList.add(word);
						}
					}
				}
			}
		} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
			for (Tweet tweet : tweets) {
				for (int i=0; i<tweet.getLemmas().length; i++) {
					if (Commons.isRelevant(tweet.getTags()[i]) && !Loader.getStopCache().containsKey(tweet.getLemmas()[i]) && Loader.getEnglishWords().containsKey(tweet.getLemmas()[i])) {
						TopWord word = new TopWord(tweet.getLemmas()[i].toLowerCase(), tweet.getTags()[i]);
						if (topWordsList.contains(word)) {
							int index = topWordsList.indexOf(word);
							
							for (String sentiment : tweet.getTweetClasses()) {
								int count = topWordsList.get(index).getOccurrences().get(sentiment) + 1;
								topWordsList.get(index).getOccurrences().put(sentiment, count);
							}
							
							if(Commons.isPositiveSentiment(tweet.getTweetClass())) {
								topWordsList.get(index).setPosOccurrences(topWordsList.get(index).getPosOccurrences() + 1);
							} else if(Commons.isNegativeSentiment(tweet.getTweetClass())) {
								topWordsList.get(index).setNegOccurrences(topWordsList.get(index).getNegOccurrences() + 1);
							}
						} else {
							for (String sentiment : tweet.getTweetClasses()) {
								word.getOccurrences().put(sentiment, 1);
							}
							
							topWordsList.add(word);
						}
					}
				}
			}
		}
		
		// All the words in the format [sentiment, <word, occurrences>], only words that satisfy the min ratio condition are taken
		HashMap<String, HashMap<String, Integer>> allWords = new HashMap<>();
		
		for (String sentiment : Parameters.getClasses()) {
			allWords.put(sentiment, new HashMap<>());
		}
		
		for (TopWord word : topWordsList) {
			word.measureOccurrenceRatios();
			for (String sentiment : Parameters.getClasses()) {
				if (Commons.isPositiveSentiment(sentiment)) {
					if (word.getPosOccurrencesRatio() >= ratio) {
						allWords.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
					}
				} else if (Commons.isNegativeSentiment(sentiment)) {
					if (word.getNegOccurrencesRatio() >= ratio) {
						allWords.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
					}
				} else {
					allWords.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
				}
			}
		}
		
		// add the limit of words condition
		for (String sentiment : allWords.keySet()) {
			finalList.put(sentiment, Commons.getTopWords(allWords.get(sentiment), limit));
		}
		
		Parameters.setTopWords(finalList);
	}
	
	private static void extractTopWordsSeparated(ArrayList<Tweet> tweets, double ratio, int limit) {
		// TODO add this, the same way for "extractTopWordsTogether"
		ArrayList<TopWord> topWordsList = new ArrayList<>();
		
		HashMap<String, ArrayList<String>> nouns = new HashMap<>();
		HashMap<String, ArrayList<String>> verbs = new HashMap<>();
		HashMap<String, ArrayList<String>> adjectives = new HashMap<>();
		HashMap<String, ArrayList<String>> adverbs = new HashMap<>();
		
		
		// Collect all the words of all the tweets
		for (Tweet tweet : tweets) {
			for (int i=0; i<tweet.getLemmas().length; i++) {
				if (Commons.isRelevant(tweet.getTags()[i]) && !Loader.getStopCache().containsKey(tweet.getLemmas()[i]) && Loader.getEnglishWords().containsKey(tweet.getLemmas()[i])) {
					TopWord word = new TopWord(tweet.getLemmas()[i], tweet.getTags()[i]);
					if (topWordsList.contains(word)) {
						int index = topWordsList.indexOf(word);
						topWordsList.get(index).getOccurrences().put(tweet.getTweetClass(), topWordsList.get(index).getOccurrences().get(tweet.getTweetClass()) + 1);
						if(Commons.isPositiveSentiment(tweet.getTweetClass())) {
							topWordsList.get(index).setPosOccurrences(topWordsList.get(index).getPosOccurrences() + 1);
						} else if(Commons.isNegativeSentiment(tweet.getTweetClass())) {
							topWordsList.get(index).setNegOccurrences(topWordsList.get(index).getNegOccurrences() + 1);
						}
					} else {
						word.getOccurrences().put(tweet.getTweetClass(), 1);
						topWordsList.add(word);
					}
				}
			}
		}
		
		
		// All the words in the format [sentiment, <word, occurrences>], only words that satisfy the min ratio condition are taken
		HashMap<String, HashMap<String, Integer>> allNouns = new HashMap<>();
		HashMap<String, HashMap<String, Integer>> allVerbs = new HashMap<>();
		HashMap<String, HashMap<String, Integer>> allAdjectives = new HashMap<>();
		HashMap<String, HashMap<String, Integer>> allAdverbs = new HashMap<>();
		
		for (String sentiment : Parameters.getClasses()) {
			allNouns.put(sentiment, new HashMap<>());
			allVerbs.put(sentiment, new HashMap<>());
			allAdjectives.put(sentiment, new HashMap<>());
			allAdverbs.put(sentiment, new HashMap<>());
		}
		
		for (TopWord word : topWordsList) {
			word.measureOccurrenceRatios();
			for (String sentiment : Parameters.getClasses()) {
				if (Commons.isPositiveSentiment(sentiment)) {
					if (word.getPosOccurrencesRatio() >= ratio) {
						if (word.getPos().startsWith("NN")) {
							allNouns.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
						} else if (word.getPos().startsWith("VB")) {
							allVerbs.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
						} else if (word.getPos().startsWith("JJ")) {
							allAdjectives.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
						} else if (word.getPos().startsWith("RB")) {
							allAdverbs.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
						}
						
					}
				} else if (Commons.isNegativeSentiment(sentiment)) {
					if (word.getNegOccurrencesRatio() >= ratio) {
						if (word.getPos().startsWith("NN")) {
							allNouns.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
						} else if (word.getPos().startsWith("VB")) {
							allVerbs.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
						} else if (word.getPos().startsWith("JJ")) {
							allAdjectives.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
						} else if (word.getPos().startsWith("RB")) {
							allAdverbs.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
						}
					}
				} else {
					if (word.getPos().startsWith("NN")) {
						allNouns.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
					} else if (word.getPos().startsWith("VB")) {
						allVerbs.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
					} else if (word.getPos().startsWith("JJ")) {
						allAdjectives.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
					} else if (word.getPos().startsWith("RB")) {
						allAdverbs.get(sentiment).put(word.getWord(), word.getOccurrences().get(sentiment));
					}
				}
			}
		}
		
		// add the limit of words condition
		for (String sentiment : allNouns.keySet()) {
			nouns.put(sentiment, Commons.getTopWords(allNouns.get(sentiment), limit));
		}
		for (String sentiment : allVerbs.keySet()) {
			verbs.put(sentiment, Commons.getTopWords(allVerbs.get(sentiment), limit));
		}
		for (String sentiment : allAdjectives.keySet()) {
			adjectives.put(sentiment, Commons.getTopWords(allAdjectives.get(sentiment), limit));
		}
		for (String sentiment : allAdverbs.keySet()) {
			adverbs.put(sentiment, Commons.getTopWords(allAdverbs.get(sentiment), limit));
		}
		
		Parameters.setTopNouns(nouns);
		Parameters.setTopVerbs(verbs);
		Parameters.setTopAdjectives(adjectives);
		Parameters.setTopAdverbs(adverbs);
	}
	
	
	//=================================//
	//       PATTERN EXTRACTION        //
	//=================================//
	
	/**
	 * Import a set of already saved Patterns from a file
	 */
	public static void importPatterns() {
		if (Reader.isValidPatternsFile(Parameters.getBasicPatternsImportedFileLocation())) {
			Reader.importBasicPatterns(Parameters.getBasicPatternsImportedFileLocation(), Features.getPatternFeaturesType());
		} else {
			Parameters.setSingleLengthPatterns(new HashMap<>());
			Parameters.setMultiLengthPatterns(new HashMap<>());
		}
		
	}
	
	/**
	 * Import a set of already saved Patterns from a file [Numeric Format]
	 */
	public static void importBasicPatternsNumeric() {
		if (Reader.isValidPatternsFile(Parameters.getBasicPatternsImportedFileLocation())) {
			Reader.importBasicPatternsNumeric(Parameters.getBasicPatternsImportedFileLocation(), Features.getPatternFeaturesType());
		} else {
			Parameters.setSingleLengthPatterns(new HashMap<>());
			Parameters.setMultiLengthPatterns(new HashMap<>());
		}
		
	}
	
	
	/**
	 * Extract the set of patterns from the training set
	 * @param tweets : the training set [It MUST be the training set! Not applied for the test set]
	 */
	public static void extractPatterns(ArrayList<Tweet> tweets, Loader.ProjectGoal task) {
		if (Features.getPatternFeaturesType()==null) {
			if (Parameters.getMultiLengthPatterns()==null) {
				Parameters.setMultiLengthPatterns(new HashMap<>());
			}
			if (Parameters.getSingleLengthPatterns()==null) {
				Parameters.setSingleLengthPatterns(new HashMap<>());
			}
		} else {
			if (task.equals(Loader.ProjectGoal.CLASSIFICATION)) {
				if (Features.getPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {
					extractUniquePatterns(tweets, Features.getPatternLength(), Features.getNumberOfPatternsPerClass(), Features.getPatternsMinOccurrence());
				} else if (Features.getPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
					extractSummedPatterns(tweets, Features.getMinPatternLength(), Features.getMaxPatternLength(), Features.getPatternsMinOccurrence());
				} 
			} else if (task.equals(Loader.ProjectGoal.QUANTIFICATION)) {
				extractSummedPatternsForQuantification(tweets, Features.getMinPatternLength(), Features.getMaxPatternLength(), Features.getPatternsMinOccurrence());
			}
		}
	}
	
	private static void extractUniquePatterns(ArrayList<Tweet> tweets, int patternLength, int numberOfPatternPerClass, int minOccurrences) {
		
		HashMap<String, ArrayList<Pattern>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<Pattern>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			ArrayList<String[]> tweetPatterns = TextProcessing.createPatternVectors(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore(), patternLength, patternLength);
			for (String[] ptrn : tweetPatterns) {
				Pattern pattern = new Pattern(ptrn, tweet.getTweetClass());
				if (allPatterns.get(tweet.getTweetClass()).contains(pattern)) {
					int index = allPatterns.get(tweet.getTweetClass()).indexOf(pattern);
					allPatterns.get(tweet.getTweetClass()).get(index).setNumberOfOccurrences(allPatterns.get(tweet.getTweetClass()).get(index).getNumberOfOccurrences() + 1);
				} else {
					allPatterns.get(tweet.getTweetClass()).add(pattern);
					if (Commons.isPositiveSentiment(tweet.getTweetClass())) {
						positivePatterns.add(pattern.getPatternConcatenated());
					} else if (Commons.isNegativeSentiment(tweet.getTweetClass())) {
						negativePatterns.add(pattern.getPatternConcatenated());
					}
				}
			}
		}
		
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<Pattern>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<Pattern>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, ArrayList<Pattern>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			finalPatterns.put(sentimentClass, new ArrayList<Pattern>());
		}
		
		for(String sentimentClass : Parameters.getClasses()) {
			
			int lowestAcceptagleScore = 0;
			
			if (scores.get(sentimentClass).size() > numberOfPatternPerClass) {
				Collections.sort(scores.get(sentimentClass));
				lowestAcceptagleScore = scores.get(sentimentClass).get(numberOfPatternPerClass);
			}
			
			for (Pattern pattern : midPatterns.get(sentimentClass)) {
				if (pattern.getNumberOfOccurrences() >= lowestAcceptagleScore) {
					finalPatterns.get(sentimentClass).add(pattern);
				}
			}
		}		
		Parameters.setSingleLengthPatterns(finalPatterns);
	}
	
	private static void extractSummedPatterns(ArrayList<Tweet> tweets, int minPatternLength, int maxPatternLength, int minOccurrences) {
		
		HashMap<String, ArrayList<Pattern>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<Pattern>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			ArrayList<String[]> tweetPatterns = TextProcessing.createPatternVectors(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore(), minPatternLength, maxPatternLength);
			for (String[] ptrn : tweetPatterns) {
				Pattern pattern = new Pattern(ptrn, tweet.getTweetClass());
				if (allPatterns.get(tweet.getTweetClass()).contains(pattern)) {
					int index = allPatterns.get(tweet.getTweetClass()).indexOf(pattern);
					allPatterns.get(tweet.getTweetClass()).get(index).setNumberOfOccurrences(allPatterns.get(tweet.getTweetClass()).get(index).getNumberOfOccurrences() + 1);
				} else {
					allPatterns.get(tweet.getTweetClass()).add(pattern);
					if (Commons.isPositiveSentiment(tweet.getTweetClass())) {
						positivePatterns.add(pattern.getPatternConcatenated());
					} else if (Commons.isNegativeSentiment(tweet.getTweetClass())) {
						negativePatterns.add(pattern.getPatternConcatenated());
					}
				}
			}
		}
		
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<Pattern>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<Pattern>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, HashMap<Integer, ArrayList<Pattern>>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			HashMap<Integer, ArrayList<Pattern>> hashMap = new HashMap<>();
			for (Integer i = minPatternLength; i<maxPatternLength+1; i++) {
				ArrayList<Pattern> patterns = new ArrayList<>();
				hashMap.put(i, patterns);
			}
			finalPatterns.put(sentimentClass, hashMap);
		}
		
		for(String sentimentClass : Parameters.getClasses()) {	
			for (Pattern pattern : midPatterns.get(sentimentClass)) {
				finalPatterns.get(sentimentClass).get(pattern.getPattern().length).add(pattern);
			}
		}
		
		Parameters.setMultiLengthPatterns(finalPatterns);
	}
	
	private static void extractSummedPatternsForQuantification(ArrayList<Tweet> tweets, int minPatternLength, int maxPatternLength, int minOccurrences) {
		
		HashMap<String, ArrayList<Pattern>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<Pattern>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			ArrayList<String[]> tweetPatterns = TextProcessing.createPatternVectors(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore(), minPatternLength, maxPatternLength);
			for (String[] ptrn : tweetPatterns) {
				
				Pattern pattern = new Pattern(ptrn, tweet.getTweetClass());
				
				for (String sentiment : tweet.getTweetClasses()) {
					if (allPatterns.get(sentiment).contains(pattern)) {
						int index = allPatterns.get(sentiment).indexOf(pattern);
						allPatterns.get(sentiment).get(index).setNumberOfOccurrences(
								allPatterns.get(sentiment).get(index).getNumberOfOccurrences() + 1);
					} else {
						allPatterns.get(sentiment).add(pattern);
						if (Commons.isPositiveSentiment(sentiment)) {
							positivePatterns.add(pattern.getPatternConcatenated());
						} else if (Commons.isNegativeSentiment(sentiment)) {
							negativePatterns.add(pattern.getPatternConcatenated());
						}
					} 
				}
			}
		}
		
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<Pattern>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<Pattern>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, HashMap<Integer, ArrayList<Pattern>>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			HashMap<Integer, ArrayList<Pattern>> hashMap = new HashMap<>();
			for (Integer i = minPatternLength; i<maxPatternLength+1; i++) {
				ArrayList<Pattern> patterns = new ArrayList<>();
				hashMap.put(i, patterns);
			}
			finalPatterns.put(sentimentClass, hashMap);
		}
		
		for(String sentimentClass : Parameters.getClasses()) {	
			for (Pattern pattern : midPatterns.get(sentimentClass)) {
				finalPatterns.get(sentimentClass).get(pattern.getPattern().length).add(pattern);
			}
		}
		
		Parameters.setMultiLengthPatterns(finalPatterns);
	}
	
	
	/**
	 * Extract the set of patterns from the training set [Numeric format]
	 * @param tweets : the training set [It MUST be the training set! Not applied for the test set]
	 */
	public static void extractNumericPatterns(ArrayList<Tweet> tweets, Loader.ProjectGoal task) {
		if (Features.getPatternFeaturesType()==null) {
			if (Parameters.getMultiLengthPatternsNumeric()==null) {
				Parameters.setMultiLengthPatternsNumeric(new HashMap<>());
			}
			if (Parameters.getSingleLengthPatternsNumeric()==null) {
				Parameters.setSingleLengthPatternsNumeric(new HashMap<>());
			}
		} else {
			if (task.equals(Loader.ProjectGoal.CLASSIFICATION)) {
				if (Features.getPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {
					extractUniqueNumericPatterns(tweets, Features.getPatternLength(), Features.getNumberOfPatternsPerClass(), Features.getPatternsMinOccurrence());
				} else if (Features.getPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
					extractSummedNumericPatterns(tweets, Features.getMinPatternLength(), Features.getMaxPatternLength(), Features.getPatternsMinOccurrence());
				} 
			} else if (task.equals(Loader.ProjectGoal.QUANTIFICATION)) {
				extractSummedNumericPatternsForQuantification(tweets, Features.getMinPatternLength(), Features.getMaxPatternLength(), Features.getPatternsMinOccurrence());
			}
		}
	}
	
	private static void extractUniqueNumericPatterns(ArrayList<Tweet> tweets, int patternLength, int numberOfPatternPerClass, int minOccurrences) {
		
		HashMap<String, ArrayList<PatternNumeric>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			ArrayList<int[]> tweetPatterns = TextProcessing.createNumericPatternVectors(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore(), patternLength, patternLength);
			for (int[] ptrn : tweetPatterns) {
				PatternNumeric pattern = new PatternNumeric(ptrn, tweet.getTweetClass());
				if (allPatterns.get(tweet.getTweetClass()).contains(pattern)) {
					int index = allPatterns.get(tweet.getTweetClass()).indexOf(pattern);
					allPatterns.get(tweet.getTweetClass()).get(index).setNumberOfOccurrences(allPatterns.get(tweet.getTweetClass()).get(index).getNumberOfOccurrences() + 1);
				} else {
					allPatterns.get(tweet.getTweetClass()).add(pattern);
					if (Commons.isPositiveSentiment(tweet.getTweetClass())) {
						positivePatterns.add(pattern.getPatternConcatenated());
					} else if (Commons.isNegativeSentiment(tweet.getTweetClass())) {
						negativePatterns.add(pattern.getPatternConcatenated());
					}
				}
			}
		}
		
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<PatternNumeric>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, ArrayList<PatternNumeric>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			finalPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
		}
		
		for(String sentimentClass : Parameters.getClasses()) {
			
			int lowestAcceptagleScore = 0;
			
			if (scores.get(sentimentClass).size() > numberOfPatternPerClass) {
				Collections.sort(scores.get(sentimentClass));
				lowestAcceptagleScore = scores.get(sentimentClass).get(numberOfPatternPerClass);
			}
			
			for (PatternNumeric pattern : midPatterns.get(sentimentClass)) {
				if (pattern.getNumberOfOccurrences() >= lowestAcceptagleScore) {
					finalPatterns.get(sentimentClass).add(pattern);
				}
			}
		}		
		Parameters.setSingleLengthPatternsNumeric(finalPatterns);
	}
	
	private static void extractSummedNumericPatterns(ArrayList<Tweet> tweets, int minPatternLength, int maxPatternLength, int minOccurrences) {
		
		HashMap<String, ArrayList<PatternNumeric>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			ArrayList<int[]> tweetPatterns = TextProcessing.createNumericPatternVectors(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore(), minPatternLength, maxPatternLength);
			for (int[] ptrn : tweetPatterns) {
				PatternNumeric pattern = new PatternNumeric(ptrn, tweet.getTweetClass());
				if (allPatterns.get(tweet.getTweetClass()).contains(pattern)) {
					int index = allPatterns.get(tweet.getTweetClass()).indexOf(pattern);
					allPatterns.get(tweet.getTweetClass()).get(index).setNumberOfOccurrences(allPatterns.get(tweet.getTweetClass()).get(index).getNumberOfOccurrences() + 1);
				} else {
					allPatterns.get(tweet.getTweetClass()).add(pattern);
					if (Commons.isPositiveSentiment(tweet.getTweetClass())) {
						positivePatterns.add(pattern.getPatternConcatenated());
					} else if (Commons.isNegativeSentiment(tweet.getTweetClass())) {
						negativePatterns.add(pattern.getPatternConcatenated());
					}
				}
			}
		}
		
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<PatternNumeric>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			HashMap<Integer, ArrayList<PatternNumeric>> hashMap = new HashMap<>();
			for (Integer i = minPatternLength; i<maxPatternLength+1; i++) {
				ArrayList<PatternNumeric> patterns = new ArrayList<>();
				hashMap.put(i, patterns);
			}
			finalPatterns.put(sentimentClass, hashMap);
		}
		
		for(String sentimentClass : Parameters.getClasses()) {	
			for (PatternNumeric pattern : midPatterns.get(sentimentClass)) {
				finalPatterns.get(sentimentClass).get(pattern.getPattern().length).add(pattern);
			}
		}
		
		Parameters.setMultiLengthPatternsNumeric(finalPatterns);
	}
	
	private static void extractSummedNumericPatternsForQuantification(ArrayList<Tweet> tweets, int minPatternLength, int maxPatternLength, int minOccurrences) {
		
		HashMap<String, ArrayList<PatternNumeric>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			ArrayList<int[]> tweetPatterns = TextProcessing.createNumericPatternVectors(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore(), minPatternLength, maxPatternLength);
			for (int[] ptrn : tweetPatterns) {
				
				PatternNumeric pattern = new PatternNumeric(ptrn, tweet.getTweetClass());
				
				for (String sentiment : tweet.getTweetClasses()) {
					if (allPatterns.get(sentiment).contains(pattern)) {
						int index = allPatterns.get(sentiment).indexOf(pattern);
						allPatterns.get(sentiment).get(index).setNumberOfOccurrences(
								allPatterns.get(sentiment).get(index).getNumberOfOccurrences() + 1);
					} else {
						allPatterns.get(sentiment).add(pattern);
						if (Commons.isPositiveSentiment(sentiment)) {
							positivePatterns.add(pattern.getPatternConcatenated());
						} else if (Commons.isNegativeSentiment(sentiment)) {
							negativePatterns.add(pattern.getPatternConcatenated());
						}
					} 
				}
			}
		}
		
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<PatternNumeric>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			HashMap<Integer, ArrayList<PatternNumeric>> hashMap = new HashMap<>();
			for (Integer i = minPatternLength; i<maxPatternLength+1; i++) {
				ArrayList<PatternNumeric> patterns = new ArrayList<>();
				hashMap.put(i, patterns);
			}
			finalPatterns.put(sentimentClass, hashMap);
		}
		
		for(String sentimentClass : Parameters.getClasses()) {	
			for (PatternNumeric pattern : midPatterns.get(sentimentClass)) {
				finalPatterns.get(sentimentClass).get(pattern.getPattern().length).add(pattern);
			}
		}
		
		Parameters.setMultiLengthPatternsNumeric(finalPatterns);
	}

	
	
	//=================================//
	//   ADVANCED PATTERN EXTRACTION   //
	//=================================//
	
	/**
	 * Import a set of already saved Patterns from a file
	 */
	public static void importAdvancedPatterns() {
		if (Reader.isValidPatternsFile(Parameters.getAdvancedPatternsImportedFileLocation())) {
			Reader.importAdvancedPatterns(Parameters.getAdvancedPatternsImportedFileLocation(), Features.getAdvancedPatternFeaturesType());
		} else {
			Parameters.setSingleLengthAdvancedPatterns(new HashMap<>());
			Parameters.setMultiLengthAdvancedPatterns(new HashMap<>());
		}
		
	}
	
	/**
	 * Import a set of already saved Patterns from a file
	 */
	public static void importAdvancedPatternsNumeric() {
		if (Reader.isValidPatternsFile(Parameters.getAdvancedPatternsImportedFileLocation())) {
			Reader.importAdvancedPatternsNumeric(Parameters.getAdvancedPatternsImportedFileLocation(), Features.getAdvancedPatternFeaturesType());
		} else {
			Parameters.setSingleLengthAdvancedPatterns(new HashMap<>());
			Parameters.setMultiLengthAdvancedPatterns(new HashMap<>());
		}
		
	}
	
	
	/**
	 * Extract the set of patterns from the training set
	 * @param tweets : the training set [It MUST be the training set! Not applied for the test set]
	 */
	public static void extractAdvancedPatterns(ArrayList<Tweet> tweets, Loader.ProjectGoal task) {
		if (Features.getAdvancedPatternFeaturesType()==null) {
			if (Parameters.getMultiLengthAdvancedPatterns()==null) {
				Parameters.setMultiLengthAdvancedPatterns(new HashMap<>());
			}
			if (Parameters.getSingleLengthAdvancedPatterns()==null) {
				Parameters.setSingleLengthAdvancedPatterns(new HashMap<>());
			}
		} else {
			if (task.equals(Loader.ProjectGoal.CLASSIFICATION)) {
				if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {
					extractUniqueAdvancedPatterns(tweets, Parameters.getRawUnigrams(), Features.getAdvancedPatternLength(), Features.getAdvancedNumberOfPatternsPerClass(), Features.getAdvancedPatternsMinOccurrence());
				} else if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
					extractSummedAdvancedPatterns(tweets, Parameters.getRawUnigrams(), Features.getAdvancedMinPatternLength(), Features.getAdvancedMaxPatternLength(), Features.getAdvancedPatternsMinOccurrence());
				}
			} else if (task.equals(Loader.ProjectGoal.QUANTIFICATION)) {
				extractSummedAdvancedPatternsForQuantification(tweets, Parameters.getRawUnigrams(), Features.getMinPatternLength(), Features.getMaxPatternLength(), Features.getPatternsMinOccurrence());
			}
		}
	}
	
	private static void extractUniqueAdvancedPatterns(ArrayList<Tweet> tweets, HashMap<String, ArrayList<String>> sentimentalWords, int patternLength, int numberOfPatternPerClass, int minOccurrences) {
		
		HashMap<String, ArrayList<Pattern>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<Pattern>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			String sentiment = tweet.getTweetClass();
			ArrayList<String[]> tweetPatterns = TextProcessing.createAdvancedPatternVectors(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), sentiment, sentimentalWords.get(sentiment), patternLength, patternLength);
			
			for (String[] ptrn : tweetPatterns) {
				Pattern pattern = new Pattern(ptrn, sentiment);
				if (allPatterns.get(sentiment).contains(pattern)) {
					int index = allPatterns.get(sentiment).indexOf(pattern);
					allPatterns.get(sentiment).get(index).setNumberOfOccurrences(allPatterns.get(sentiment).get(index).getNumberOfOccurrences() + 1);
				} else {
					allPatterns.get(sentiment).add(pattern);
					if (Commons.isPositiveSentiment(sentiment)) {
						positivePatterns.add(pattern.getPatternConcatenated());
					} else if (Commons.isNegativeSentiment(sentiment)) {
						negativePatterns.add(pattern.getPatternConcatenated());
					}
				}
			}
			
		}
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<Pattern>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<Pattern>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, ArrayList<Pattern>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			finalPatterns.put(sentimentClass, new ArrayList<Pattern>());
		}
		
		for(String sentimentClass : Parameters.getClasses()) {
			
			int lowestAcceptagleScore = 0;
			
			if (scores.get(sentimentClass).size() > numberOfPatternPerClass) {
				Collections.sort(scores.get(sentimentClass));
				lowestAcceptagleScore = scores.get(sentimentClass).get(numberOfPatternPerClass);
			}
			
			for (Pattern pattern : midPatterns.get(sentimentClass)) {
				if (pattern.getNumberOfOccurrences() >= lowestAcceptagleScore) {
					finalPatterns.get(sentimentClass).add(pattern);
				}
			}
		}
		
		Parameters.setSingleLengthAdvancedPatterns(finalPatterns);
	}
	
	private static void extractSummedAdvancedPatterns(ArrayList<Tweet> tweets, HashMap<String, ArrayList<String>> sentimentalWords, int minPatternLength, int maxPatternLength, int minOccurrences) {
		
		HashMap<String, ArrayList<Pattern>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<Pattern>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			String sentiment = tweet.getTweetClass();
			ArrayList<String[]> tweetPatterns = TextProcessing.createAdvancedPatternVectors(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), sentiment, sentimentalWords.get(sentiment), minPatternLength, maxPatternLength);
			
			for (String[] ptrn : tweetPatterns) {
				Pattern pattern = new Pattern(ptrn, sentiment);
				if (allPatterns.get(sentiment).contains(pattern)) {
					int index = allPatterns.get(sentiment).indexOf(pattern);
					allPatterns.get(sentiment).get(index).setNumberOfOccurrences(allPatterns.get(sentiment).get(index).getNumberOfOccurrences() + 1);
				} else {
					allPatterns.get(sentiment).add(pattern);
					if (Commons.isPositiveSentiment(sentiment)) {
						positivePatterns.add(pattern.getPatternConcatenated());
					} else if (Commons.isNegativeSentiment(sentiment)) {
						negativePatterns.add(pattern.getPatternConcatenated());
					}
				}
			}
			
		}
		
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<Pattern>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<Pattern>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, HashMap<Integer, ArrayList<Pattern>>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			HashMap<Integer, ArrayList<Pattern>> hashMap = new HashMap<>();
			for (Integer i = minPatternLength; i<maxPatternLength+1; i++) {
				ArrayList<Pattern> patterns = new ArrayList<>();
				hashMap.put(i, patterns);
			}
			finalPatterns.put(sentimentClass, hashMap);
		}
		
		for(String sentimentClass : Parameters.getClasses()) {	
			for (Pattern pattern : midPatterns.get(sentimentClass)) {
				finalPatterns.get(sentimentClass).get(pattern.getPattern().length).add(pattern);
			}
		}
		
		Parameters.setMultiLengthAdvancedPatterns(finalPatterns);
	}
	
	private static void extractSummedAdvancedPatternsForQuantification(ArrayList<Tweet> tweets, HashMap<String, ArrayList<String>> sentimentalWords, int minPatternLength, int maxPatternLength, int minOccurrences) {
		
		HashMap<String, ArrayList<Pattern>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<Pattern>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			
			for (String sentiment : tweet.getTweetClasses()) {
				ArrayList<String[]> tweetPatterns = TextProcessing.createAdvancedPatternVectors(tweet.getTokens(),
						tweet.getLemmas(), tweet.getTags(), sentiment, sentimentalWords.get(sentiment),
						minPatternLength, maxPatternLength);
				for (String[] ptrn : tweetPatterns) {
					Pattern pattern = new Pattern(ptrn, sentiment);
					if (allPatterns.get(sentiment).contains(pattern)) {
						int index = allPatterns.get(sentiment).indexOf(pattern);
						allPatterns.get(sentiment).get(index).setNumberOfOccurrences(
								allPatterns.get(sentiment).get(index).getNumberOfOccurrences() + 1);
					} else {
						allPatterns.get(sentiment).add(pattern);
						if (Commons.isPositiveSentiment(sentiment)) {
							positivePatterns.add(pattern.getPatternConcatenated());
						} else if (Commons.isNegativeSentiment(sentiment)) {
							negativePatterns.add(pattern.getPatternConcatenated());
						}
					}
				} 
			}
			
		}
		
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<Pattern>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<Pattern>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (Pattern pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, HashMap<Integer, ArrayList<Pattern>>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			HashMap<Integer, ArrayList<Pattern>> hashMap = new HashMap<>();
			for (Integer i = minPatternLength; i<maxPatternLength+1; i++) {
				ArrayList<Pattern> patterns = new ArrayList<>();
				hashMap.put(i, patterns);
			}
			finalPatterns.put(sentimentClass, hashMap);
		}
		
		for(String sentimentClass : Parameters.getClasses()) {	
			for (Pattern pattern : midPatterns.get(sentimentClass)) {
				finalPatterns.get(sentimentClass).get(pattern.getPattern().length).add(pattern);
			}
		}
		
		Parameters.setMultiLengthAdvancedPatterns(finalPatterns);
	}
	

	/**
	 * Extract the set of patterns from the training set [Numeric format]
	 * @param tweets : the training set [It MUST be the training set! Not applied for the test set]
	 */
	public static void extractAdvancedNumericPatterns(ArrayList<Tweet> tweets, Loader.ProjectGoal task) {
		if (Features.getAdvancedPatternFeaturesType()==null) {
			if (Parameters.getMultiLengthAdvancedPatternsNumeric()==null) {
				Parameters.setMultiLengthAdvancedPatternsNumeric(new HashMap<>());
			}
			if (Parameters.getSingleLengthAdvancedPatternsNumeric()==null) {
				Parameters.setSingleLengthAdvancedPatternsNumeric(new HashMap<>());
			}
		} else {
			if (task.equals(Loader.ProjectGoal.CLASSIFICATION)) {
				if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {
					extractUniqueAdvancedNumericPatterns(tweets, Parameters.getRawUnigrams(), Features.getAdvancedPatternLength(), Features.getAdvancedNumberOfPatternsPerClass(), Features.getAdvancedPatternsMinOccurrence());
				} else if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
					extractSummedAdvancedNumericPatterns(tweets, Parameters.getRawUnigrams(), Features.getAdvancedMinPatternLength(), Features.getAdvancedMaxPatternLength(), Features.getAdvancedPatternsMinOccurrence());
				}
			} else if (task.equals(Loader.ProjectGoal.QUANTIFICATION)) {
				extractSummedAdvancedNumericPatternsForQuantification(tweets, Parameters.getRawUnigrams(), Features.getMinPatternLength(), Features.getMaxPatternLength(), Features.getPatternsMinOccurrence());
			}
		}
	}
	
	private static void extractUniqueAdvancedNumericPatterns(ArrayList<Tweet> tweets, HashMap<String, ArrayList<String>> sentimentalWords, int patternLength, int numberOfPatternPerClass, int minOccurrences) {
		
		HashMap<String, ArrayList<PatternNumeric>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			String sentiment = tweet.getTweetClass();
			ArrayList<int[]> tweetPatterns = TextProcessing.createNumericAdvancedPatternVectors(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), sentiment, sentimentalWords.get(sentiment), patternLength, patternLength);
			
			for (int[] ptrn : tweetPatterns) {
				PatternNumeric pattern = new PatternNumeric(ptrn, sentiment);
				if (allPatterns.get(sentiment).contains(pattern)) {
					int index = allPatterns.get(sentiment).indexOf(pattern);
					allPatterns.get(sentiment).get(index).setNumberOfOccurrences(allPatterns.get(sentiment).get(index).getNumberOfOccurrences() + 1);
				} else {
					allPatterns.get(sentiment).add(pattern);
					if (Commons.isPositiveSentiment(sentiment)) {
						positivePatterns.add(pattern.getPatternConcatenated());
					} else if (Commons.isNegativeSentiment(sentiment)) {
						negativePatterns.add(pattern.getPatternConcatenated());
					}
				}
			}
			
		}
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<PatternNumeric>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, ArrayList<PatternNumeric>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			finalPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
		}
		
		for(String sentimentClass : Parameters.getClasses()) {
			
			int lowestAcceptagleScore = 0;
			
			if (scores.get(sentimentClass).size() > numberOfPatternPerClass) {
				Collections.sort(scores.get(sentimentClass));
				lowestAcceptagleScore = scores.get(sentimentClass).get(numberOfPatternPerClass);
			}
			
			for (PatternNumeric pattern : midPatterns.get(sentimentClass)) {
				if (pattern.getNumberOfOccurrences() >= lowestAcceptagleScore) {
					finalPatterns.get(sentimentClass).add(pattern);
				}
			}
		}
		
		Parameters.setSingleLengthAdvancedPatternsNumeric(finalPatterns);
	}
	
	private static void extractSummedAdvancedNumericPatterns(ArrayList<Tweet> tweets, HashMap<String, ArrayList<String>> sentimentalWords, int minPatternLength, int maxPatternLength, int minOccurrences) {
		
		HashMap<String, ArrayList<PatternNumeric>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			String sentiment = tweet.getTweetClass();
			ArrayList<int[]> tweetPatterns = TextProcessing.createNumericAdvancedPatternVectors(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), sentiment, sentimentalWords.get(sentiment), minPatternLength, maxPatternLength);
			
			for (int[] ptrn : tweetPatterns) {
				PatternNumeric pattern = new PatternNumeric(ptrn, sentiment);
				if (allPatterns.get(sentiment).contains(pattern)) {
					int index = allPatterns.get(sentiment).indexOf(pattern);
					allPatterns.get(sentiment).get(index).setNumberOfOccurrences(allPatterns.get(sentiment).get(index).getNumberOfOccurrences() + 1);
				} else {
					allPatterns.get(sentiment).add(pattern);
					if (Commons.isPositiveSentiment(sentiment)) {
						positivePatterns.add(pattern.getPatternConcatenated());
					} else if (Commons.isNegativeSentiment(sentiment)) {
						negativePatterns.add(pattern.getPatternConcatenated());
					}
				}
			}
			
		}
		
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<PatternNumeric>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			HashMap<Integer, ArrayList<PatternNumeric>> hashMap = new HashMap<>();
			for (Integer i = minPatternLength; i<maxPatternLength+1; i++) {
				ArrayList<PatternNumeric> patterns = new ArrayList<>();
				hashMap.put(i, patterns);
			}
			finalPatterns.put(sentimentClass, hashMap);
		}
		
		for(String sentimentClass : Parameters.getClasses()) {	
			for (PatternNumeric pattern : midPatterns.get(sentimentClass)) {
				finalPatterns.get(sentimentClass).get(pattern.getPattern().length).add(pattern);
			}
		}
		
		Parameters.setMultiLengthAdvancedPatternsNumeric(finalPatterns);
	}
	
	private static void extractSummedAdvancedNumericPatternsForQuantification(ArrayList<Tweet> tweets, HashMap<String, ArrayList<String>> sentimentalWords, int minPatternLength, int maxPatternLength, int minOccurrences) {
		
		HashMap<String, ArrayList<PatternNumeric>> allPatterns = new HashMap<>();
		
		ArrayList<String> positivePatterns = new ArrayList<>();
		ArrayList<String> negativePatterns = new ArrayList<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			allPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
		}
		
		// Collects the patterns
		for (Tweet tweet : tweets) {
			
			for (String sentiment : tweet.getTweetClasses()) {
				ArrayList<int[]> tweetPatterns = TextProcessing.createNumericAdvancedPatternVectors(tweet.getTokens(),
						tweet.getLemmas(), tweet.getTags(), sentiment, sentimentalWords.get(sentiment),
						minPatternLength, maxPatternLength);
				for (int[] ptrn : tweetPatterns) {
					PatternNumeric pattern = new PatternNumeric(ptrn, sentiment);
					if (allPatterns.get(sentiment).contains(pattern)) {
						int index = allPatterns.get(sentiment).indexOf(pattern);
						allPatterns.get(sentiment).get(index).setNumberOfOccurrences(
								allPatterns.get(sentiment).get(index).getNumberOfOccurrences() + 1);
					} else {
						allPatterns.get(sentiment).add(pattern);
						if (Commons.isPositiveSentiment(sentiment)) {
							positivePatterns.add(pattern.getPatternConcatenated());
						} else if (Commons.isNegativeSentiment(sentiment)) {
							negativePatterns.add(pattern.getPatternConcatenated());
						}
					}
				} 
			}
			
		}
		
		
		// Check which patterns do not appear in the opposite sentiment class too and that the number of occurrences if higher than the limit
		HashMap<String, ArrayList<PatternNumeric>> midPatterns = new HashMap<>();
		HashMap<String, ArrayList<Integer>> scores = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			midPatterns.put(sentimentClass, new ArrayList<PatternNumeric>());
			scores.put(sentimentClass, new ArrayList<Integer>());
		}
		
		for (String sentimentClass : allPatterns.keySet()) {
			if (Commons.isPositiveSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!negativePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else if (Commons.isNegativeSentiment(sentimentClass)) {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (!positivePatterns.contains(pattern.getPatternConcatenated()) && pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			} else {
				for (PatternNumeric pattern : allPatterns.get(sentimentClass)) {
					if (pattern.getNumberOfOccurrences()>= minOccurrences) {
						midPatterns.get(sentimentClass).add(pattern);
						scores.get(sentimentClass).add(pattern.getNumberOfOccurrences());
					}
				}
			}
		}
		
		
		
		// Select the TOP N patterns
		HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> finalPatterns = new HashMap<>();
		
		for (String sentimentClass : Parameters.getClasses()) {
			HashMap<Integer, ArrayList<PatternNumeric>> hashMap = new HashMap<>();
			for (Integer i = minPatternLength; i<maxPatternLength+1; i++) {
				ArrayList<PatternNumeric> patterns = new ArrayList<>();
				hashMap.put(i, patterns);
			}
			finalPatterns.put(sentimentClass, hashMap);
		}
		
		for(String sentimentClass : Parameters.getClasses()) {	
			for (PatternNumeric pattern : midPatterns.get(sentimentClass)) {
				finalPatterns.get(sentimentClass).get(pattern.getPattern().length).add(pattern);
			}
		}
		
		Parameters.setMultiLengthAdvancedPatternsNumeric(finalPatterns);
	}
	
}
