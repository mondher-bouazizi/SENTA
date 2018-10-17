package commons.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import commons.constants.Commons;
import commons.constants.Constants;
import main.items.Features;
import main.items.ImportedFeatures;
import main.items.Parameters;
import main.items.Pattern;
import main.items.PatternNumeric;
import main.items.Tweet;
import main.items.Word;
import main.start.Loader;
import windows.others.AlertBox;

public class Reader {
	
	//======================================//
	//         DICTIONARIES-RELATED         //
	//======================================//
	
	/**
	 * Extract the list of ALL words in a file (ONE word per line). Even if the file contains the meaning, polarity, etc.
	 * of the expression separated by a tabulation, only the word is taken
	 *
	 * @param dict this file is the same that is used in the simple Lemmatizer (i.e.,"en-lemmatizer.dict")
	 * @return a list of all the English words
	 */
	public static HashMap<String, Object> getWords(String dict) {

		HashMap<String, Object> words = new HashMap<String, Object>();

		BufferedReader br = null;

		if (dict != null) {
			File file = new File(dict);
			if (file.exists()) {

				try {
					br = new BufferedReader(new FileReader(file));
					String line = br.readLine();
					while (line != null) {
						line = br.readLine();
						if (line != null) {
							// String word = line.split("\\t")[0];
							String word = line.split("\\t")[0].split("\\s")[0]; // Only one word per line can be added
							words.put(word, null);
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} 
		}
		return words;
	}
	
	/**
	 * Extract the list of ALL words in a file (ONE word per line). Even if the file contains the meaning, polarity, etc.
	 * of the expression separated by a tabulation, only the word is taken
	 *
	 * @param dict this file is the same that is used in the simple Lemmatizer (i.e.,"en-lemmatizer.dict")
	 * @return a list of all the English words
	 */
	public static HashMap<String, Integer> getSerializedWords(String dict) {

		HashMap<String, Integer> words = new HashMap<>();

		BufferedReader br = null;

		if (dict != null) {
			File file = new File(dict);
			if (file.exists()) {

				try {
					int i = 1;
					br = new BufferedReader(new FileReader(file));
					String line = br.readLine();
					while (line != null) {
						line = br.readLine();
						if (line != null) {
							// String word = line.split("\\t")[0];
							String word = line.split("\\t")[0].split("\\s")[0]; // Only one word per line can be added
							words.put(word, i);
						}
						i++;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} 
		}
		return words;
	}
	
	/**
	 * Extract the number of occurrences of words from a big text (all words included in the dictionary)
	 *
	 * @param dictionary: this file is supposed to contain all the English words
	 * @param bigText:    the file to read to extract the number of occurrences of each word
	 * @return a {@link HashMap} of the words and their occurrences in the big text file
	 */
	public static HashMap<String, Integer> getWordsProbability(String dictionary, String bigText) {
		
		HashMap<String, Integer> wordsProbability = new HashMap<String, Integer>(150000);
		
		BufferedReader reader;
		
		// Read the dictionary and gets the list of words
		try {
			reader = new BufferedReader(new FileReader(dictionary));
			String line;
			while ((line = reader.readLine()) != null) {
				wordsProbability.put(line, 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Read the big text and get the words probabilities
		try {
			reader = new BufferedReader(new FileReader(bigText));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] words = line.split("\\b");
				if (words.length > 0) {
					for (String word : words) {
						word = word.replaceAll("('s)", "[*]");
						word = word.toLowerCase().replaceAll("[^a-z]", "").trim();
						if (word.length() != 0) {
							if (wordsProbability.containsKey(word)) {
								int count = wordsProbability.get(word) + 1;
								wordsProbability.put(word, count);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return wordsProbability;
	}
	
	/**
	 * Extract an expression (Emoticons / slang / ...) and their associate polarities [String <-> Integer]
	 *
	 * @param dictionaryFile
	 * @return a {@link HashMap} of the expressions and the integer representing their associate polarities
	 */
	public static HashMap<String, Integer> getWordsPolarity(String dictionaryFile) {
		HashMap<String, Integer> emotionalContentPolarity = new HashMap<String, Integer>();
		BufferedReader reader = null;
		String line = "";
		String splitBy = "\t";

		try {
			reader = new BufferedReader(new FileReader(dictionaryFile));
			while ((line = reader.readLine()) != null) {
				String[] map = line.split(splitBy);
				if (map[2] == null) {
					emotionalContentPolarity.put(map[0], 0);
				} else {
					emotionalContentPolarity.put(map[0], Integer.parseInt(map[2]));
				}


			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			emotionalContentPolarity = null;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return emotionalContentPolarity;
	}
	
	/**
	 * Extract an expression (Emoticons / slang / ...) and their associate meaning [String <-> String]
	 *
	 * @param dictionaryFile: the file to read
	 * @return a {@link HashMap} of the expressions and the integer representing their associate meanings
	 */
	public static HashMap<String, String> getWordsMeaning(String dictionaryFile) {
		HashMap<String, String> emotionalContentMeaning = new HashMap<String, String>();
		BufferedReader br = null;
		String line = "";
		String splitBy = "\t";

		try {
			br = new BufferedReader(new FileReader(dictionaryFile));
			while ((line = br.readLine()) != null) {
				String[] map = line.split(splitBy);
				emotionalContentMeaning.put(map[0], map[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			emotionalContentMeaning = null;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return emotionalContentMeaning;
	}
	
	/**
	 * Extract words and their associate sentiment scores [String <-> Integer]
	 *
	 * @param dictionaryFile
	 * @return a {@link HashMap} of the expressions and the integer representing their associate polarities
	 */
	public static HashMap<String, Integer> getWordScores(String dictionaryFile) {
		HashMap<String, Integer> emotionalContentPolarity = new HashMap<String, Integer>();
		BufferedReader br = null;
		String line = "";
		String splitBy = "\t";

		try {
			br = new BufferedReader(new FileReader(dictionaryFile));
			while ((line = br.readLine()) != null) {
				String[] map = line.split(splitBy);
				if (map[1] == null) {
					emotionalContentPolarity.put(map[0], 0);
				} else {
					emotionalContentPolarity.put(map[0], Integer.parseInt(map[1]));
				}


			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			emotionalContentPolarity = null;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return emotionalContentPolarity;
	}
	
	public static double[][] getCoefficients(String dictionaryFile) {
		int nLines = countLines(dictionaryFile);
		int nRows = 3;
		double[][] coefficients = new double[nLines][nRows];
		
		BufferedReader br = null;

		if (dictionaryFile != null) {
			File file = new File(dictionaryFile);
			if (file.exists()) {

				try {
					br = new BufferedReader(new FileReader(file));
					String line;
					int n = 0;
					while ((line = br.readLine()) != null) {
						
						String[] map = line.split("\\t");
						if (map.length == 3) {
							coefficients[n][0] = Double.parseDouble(map[0]);
							coefficients[n][1] = Double.parseDouble(map[1]);
							coefficients[n][2] = Double.parseDouble(map[2]);
							n++;
						}
						
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} 
		}
		
		return coefficients;
		
	}
	
	
	//======================================//
	//        SEED & EMOTIONAL WORDS        //
	//======================================//
	
	/**
	 * Reads the file containing the different emotional words in the format [lemma     Tag     Class]
	 * @return a list of {@link EmotionalWord}
	 */
	public static ArrayList<Word> importEmotionalWords(String file) {
		ArrayList<Word> words = new ArrayList<Word>();

		try (FileReader fr = new FileReader(file);
				BufferedReader in = new BufferedReader(fr);) {

			String line;

			while ((line = in.readLine()) != null) {
				String[] map = line.split("\t");
				if (map.length >= 3) {
					// TODO check this please
					if (Parameters.getClasses().contains(map[2].toUpperCase())) {
						String pos = "";
						if (map[1].startsWith("JJ") || map[1].toUpperCase().equals("ADJECTIVE")) {
							pos = "ADJECTIVE";
						}
						else if (map[1].startsWith("NN") || map[1].toUpperCase().equals("NOUN")) {
							pos = "NOUN";
						}
						else if (map[1].startsWith("VB") || map[1].toUpperCase().equals("VERB")) {
							pos = "VERB";
						}
						else if (map[1].startsWith("RB") || map[1].toUpperCase().equals("ADVERB")) {
							pos = "ADVERB";
						}
						
						Word word = new Word(map[0], pos, map[2]);
						if (Loader.getSentimentalWords().containsKey(map[0])) {
							word.setSentimentScore(Loader.getSentimentalWords().get(map[0]));
						} else {
							word.setSentimentScore(0);
						}
						// TODO add the feature that checks if zero is accounted or not
						if (!words.contains(word)) {
							words.add(word);
						}
					}
					// If the user have the two classes "POSITIVE" and "NEGATIVE"
					if (Parameters.getClasses().contains("POSITIVE") && Commons.isPositiveSentiment(map[2].toUpperCase())) {
						String pos = "";
						if (map[1].startsWith("JJ") || map[1].toUpperCase().equals("ADJECTIVE")) {
							pos = "ADJECTIVE";
						}
						else if (map[1].startsWith("NN") || map[1].toUpperCase().equals("NOUN")) {
							pos = "NOUN";
						}
						else if (map[1].startsWith("VB") || map[1].toUpperCase().equals("VERB")) {
							pos = "VERB";
						}
						else if (map[1].startsWith("RB") || map[1].toUpperCase().equals("ADVERB")) {
							pos = "ADVERB";
						}
						
						Word word = new Word(map[0], pos, "POSITIVE");
						if (Loader.getSentimentalWords().containsKey(map[0])) {
							word.setSentimentScore(Loader.getSentimentalWords().get(map[0]));
						} else {
							word.setSentimentScore(0);
						}
						// TODO add the feature that checks if zero is accounted or not
						if (!words.contains(word)) {
							words.add(word);
						}
					} else if (Parameters.getClasses().contains("NEGATIVE") && Commons.isNegativeSentiment(map[2].toUpperCase())) {
						String pos = "";
						if (map[1].startsWith("JJ") || map[1].toUpperCase().equals("ADJECTIVE")) {
							pos = "ADJECTIVE";
						}
						else if (map[1].startsWith("NN") || map[1].toUpperCase().equals("NOUN")) {
							pos = "NOUN";
						}
						else if (map[1].startsWith("VB") || map[1].toUpperCase().equals("VERB")) {
							pos = "VERB";
						}
						else if (map[1].startsWith("RB") || map[1].toUpperCase().equals("ADVERB")) {
							pos = "ADVERB";
						}
						
						Word word = new Word(map[0], pos, "NEGATIVE");
						if (Loader.getSentimentalWords().containsKey(map[0])) {
							word.setSentimentScore(Loader.getSentimentalWords().get(map[0]));
						} else {
							word.setSentimentScore(0);
						}
						// TODO add the feature that checks if zero is accounted or not
						if (!words.contains(word)) {
							words.add(word);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return words;
	}

	
	
	//======================================//
	//           PROJECT-RELATED            //
	//======================================//
	
	/**
	 * reads a project file (*.senta) and extract the different related parameters
	 */
	public static HashMap<String, String> openProject(String file) {
		BufferedReader br = null;
		HashMap<String, String> config = new HashMap<>();
		
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			
			while ((line = br.readLine()) != null) {
				String[] map = line.split("\t");
				if (map.length==2) {
					config.put(map[0], map[1]);
				}
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Error, cannot load the file!");
		} catch (IOException e) {
			System.out.println("Error, cannot read the file!");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return config;

	}
	
	/**
	 * Read Raw file and store the set of texts collected
	 * @param file
	 * @param isKnown
	 * @return
	 */
	public static ArrayList<Tweet> readRawFile(String file, boolean isKnown) {
		
		ArrayList<Tweet> texts = new ArrayList<>();
		
		int textIdPosition = Parameters.getTextIdPosition();
		int userNamePosition = Parameters.getUserNamePosition();
		int textPosition = Parameters.getTextPosition();
		int classPosition = Parameters.getClassPosition();
		
		int max;
		
		if (isKnown) {
			max = Math.max(Math.max(textIdPosition, userNamePosition), Math.max(textPosition, classPosition));
		} else {
			max = Math.max(Math.max(textIdPosition, userNamePosition), textPosition);
		}
		
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				
				String[] map = line.split("\t");
				if (map.length > max) {
					String id = map[textIdPosition];
					String userName = map[userNamePosition];
					String text = map[textPosition];
					
					Tweet newText;
					
					if (isKnown) {
						String cla = map[classPosition].split("#")[0];
						newText = new Tweet(id, userName, text, cla);
					} else {
						newText = new Tweet(id, userName, text);
					}
					
					texts.add(newText);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Not a file");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return texts;
	}
	
	/**
	 * Read Raw file and store the set of texts collected
	 * @param file
	 * @param isKnown
	 * @return
	 */
	public static ArrayList<Tweet> readRawFileQuantification(String file, boolean isKnown) {
		
		ArrayList<Tweet> texts = new ArrayList<>();
		
		int textIdPosition = Parameters.getTextIdPosition();
		int userNamePosition = Parameters.getUserNamePosition();
		int textPosition = Parameters.getTextPosition();
		int classPosition = Parameters.getClassPosition();
		
		int max;
		
		if (isKnown) {
			max = Math.max(Math.max(textIdPosition, userNamePosition), Math.max(textPosition, classPosition));
		} else {
			max = Math.max(Math.max(textIdPosition, userNamePosition), textPosition);
		}
		
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				
				String[] map = line.split("\t");
				if (map.length > max) {
					String id = map[textIdPosition];
					String userName = map[userNamePosition];
					String text = map[textPosition];
					
					Tweet newText;
					
					if (isKnown) {
						ArrayList<String> classes = new ArrayList<>(Arrays.asList(map[classPosition].split("#")));
						
						newText = new Tweet(id, userName, text, classes);
					} else {
						newText = new Tweet(id, userName, text);
					}
					texts.add(newText);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Not a file");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return texts;
	}
	
	/**
	 * Import the list of features
	 * @param file Location of the "*.sfs" (SENTA features selection) file containing the features
	 */
	public static void importFeatures(String file) {
		
		HashMap<String, String> features = new HashMap<>();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {

				String[] map = line.split("\t");
				if (map.length == 2) {
					features.put(map[0], map[1]);
				}
			}

		} catch (FileNotFoundException e1) {
			// Load default Users
			AlertBox.display("File not found", "The file you are trying to import does not exist/no longer exists", "OK");
			System.out.println("File not found !!!!!!!!");
		} catch (IOException e) {
			// Load default Users
			AlertBox.display("Could not read the file", "The file you are trying to import could not be read", "OK");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		for (String feature : features.keySet()) {
			
			// SENTIMENT FEATURES
			if (feature.equals("useSentimentFeatures")) {
				Features.setUseSentimentFeatures(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("numberOfPositiveWords")) {
				Features.setNumberOfPositiveWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfNegativeWords")) {
				Features.setNumberOfNegativeWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfHighlyEmoPositiveWords")) {
				Features.setNumberOfHighlyEmoPositiveWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfHighlyEmoNegativeWords")) {
				Features.setNumberOfHighlyEmoNegativeWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfCapitalizedPositiveWords")) {
				Features.setNumberOfCapitalizedPositiveWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfCapitalizedNegativeWords")) {
				Features.setNumberOfCapitalizedNegativeWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("ratioOfEmotionalWords")) {
				Features.setRatioOfEmotionalWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfPositiveEmoticons")) {
				Features.setNumberOfPositiveEmoticons(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfNegativeEmoticons")) {
				Features.setNumberOfNegativeEmoticons(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfNeutralEmoticons")) {
				Features.setNumberOfNeutralEmoticons(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfJokingEmoticons")) {
				Features.setNumberOfJokingEmoticons(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfPositiveSlangs")) {
				Features.setNumberOfPositiveSlangs(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfNegativeSlangs")) {
				Features.setNumberOfNegativeSlangs(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfPositiveHashtags")) {
				Features.setNumberOfPositiveHashtags(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfNegativeHashtags")) {
				Features.setNumberOfNegativeHashtags(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("contrastWordsVsWords")) {
				Features.setContrastWordsVsWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("contrastWordsVsHashtags")) {
				Features.setContrastWordsVsHashtags(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("contrastWordsVsEmoticons")) {
				Features.setContrastWordsVsEmoticons(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("contrastHashtagsVsHashtags")) {
				Features.setContrastHashtagsVsHashtags(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("contrastHashtagsVsEmoticons")) {
				Features.setContrastHashtagsVsEmoticons(Boolean.parseBoolean(features.get(feature)));
			}
			
			
			
			// PUNCTUATION FEATURES
			if (feature.equals("usePunctuationFeatures")) {
				Features.setUsePunctuationFeatures(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("numberOfDots")) {
				Features.setNumberOfDots(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfCommas")) {
				Features.setNumberOfCommas(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfSemicolons")) {
				Features.setNumberOfSemicolons(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfExclamationMarks")) {
				Features.setNumberOfExclamationMarks(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfQuestionMarks")) {
				Features.setNumberOfQuestionMarks(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfParentheses")) {
				Features.setNumberOfParentheses(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfBrackets")) {
				Features.setNumberOfBrackets(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfBraces")) {
				Features.setNumberOfBraces(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfApostrophes")) {
				Features.setNumberOfApostrophes(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfQuotationMarks")) {
				Features.setNumberOfQuotationMarks(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("totalNumberOfCharacters")) {
				Features.setTotalNumberOfCharacters(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("totalNumberOfWords")) {
				Features.setTotalNumberOfWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("totalNumberOfSentences")) {
				Features.setTotalNumberOfSentences(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("averageNumberOfCharactersPerSentence")) {
				Features.setAverageNumberOfCharactersPerSentence(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("averageNumberOfWordsPerSentence")) {
				Features.setAverageNumberOfWordsPerSentence(Boolean.parseBoolean(features.get(feature)));
			}
			
			
			
			// STYLISTIC FEATURES
			if (feature.equals("useStylisticFeatures")) {
				Features.setUseStylisticFeatures(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("numberOfNouns")) {
				Features.setNumberOfNouns(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("ratioOfNouns")) {
				Features.setRatioOfNouns(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfVerbs")) {
				Features.setNumberOfVerbs(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("ratioOfVerbs")) {
				Features.setRatioOfVerbs(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfAdjectives")) {
				Features.setNumberOfAdjectives(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("ratioOfAdjectives")) {
				Features.setRatioOfAdjectives(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfAdverbs")) {
				Features.setNumberOfAdverbs(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("ratioOfAdverbs")) {
				Features.setRatioOfAdverbs(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useOfSymbols")) {
				Features.setUseOfSymbols(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useOfComparativeForm")) {
				Features.setUseOfComparativeForm(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useOfSuperlativeForm")) {
				Features.setUseOfSuperlativeForm(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useOfProperNouns")) {
				Features.setUseOfProperNouns(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("totalNumberOfParticles")) {
				Features.setTotalNumberOfParticles(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("averageNumberOfParticles")) {
				Features.setAverageNumberOfParticles(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("totalNumberOfInterjections")) {
				Features.setTotalNumberOfInterjections(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("averageNumberOfInterjections")) {
				Features.setAverageNumberOfInterjections(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("totalNumberOfPronouns")) {
				Features.setTotalNumberOfPronouns(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("averageNumberOfPronouns")) {
				Features.setAverageNumberOfPronouns(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("totalNumberOfPronounsGroup1")) {
				Features.setTotalNumberOfPronounsGroup1(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("averageNumberOfPronounsGroup1")) {
				Features.setAverageNumberOfPronounsGroup1(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("totalNumberOfPronounsGroup2")) {
				Features.setTotalNumberOfPronounsGroup2(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("averageNumberOfPronounsGroup2")) {
				Features.setAverageNumberOfPronounsGroup2(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useOfNegation")) {
				Features.setUseOfNegation(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useOfUncommonWords")) {
				Features.setUseOfUncommonWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("numberOfUncommonWords")) {
				Features.setNumberOfUncommonWords(Boolean.parseBoolean(features.get(feature)));
			}
			
			
			
			// SEMANTIC FEATURES
			if (feature.equals("useSemanticFeatures")) {
				Features.setUseSemanticFeatures(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("useOfOpinionWords")) {
				Features.setUseOfOpinionWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useOfHighlySentimentalWords")) {
				Features.setUseOfHighlySentimentalWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useOfUncertaintyWords")) {
				Features.setUseOfUncertaintyWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useOfActiveForm")) {
				Features.setUseOfActiveForm(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useOfPassiveForm")) {
				Features.setUseOfPassiveForm(Boolean.parseBoolean(features.get(feature)));
			}
			
			
			
			// UNIGRAM FEATURES
			if (feature.equals("useUnigramFeatures")) {
				Features.setUseUnigramFeatures(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("depth")) {
				String depth = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (depth.equals("")) {
					depth = "0";
				}
				Features.setDepth(Integer.parseInt(depth));
			}
			
			if (feature.equals("useUnigramNouns")) {
				Features.setUseUnigramNouns(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useUnigramVerbs")) {
				Features.setUseUnigramVerbs(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useUnigramAdjectives")) {
				Features.setUseUnigramAdjectives(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("useUnigramAdverbs")) {
				Features.setUseUnigramAdverbs(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("unigramTypeOfPos")) {
				if (features.get(feature).equals("TOGETHER")) {
					Features.setUnigramTypeOfPos(Features.TypeOfPos.TOGETHER);
				} else if (features.get(feature).equals("SEPARATED")) {
					Features.setUnigramTypeOfPos(Features.TypeOfPos.SEPARATED);
				}
					
			}
			
			if (feature.equals("zeroTaken")) {
				Features.setZeroTaken(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("oppositeTaken")) {
				Features.setOppositeTaken(Boolean.parseBoolean(features.get(feature)));
			}
			
			
			
			// TOP WORDS FEATURES
			if (feature.equals("useTopWords")) {
				Features.setUseTopWords(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("useTopWordsNouns")) {
				Features.setUseTopWordsNouns(Boolean.parseBoolean(features.get(feature)));
			}if (feature.equals("useTopWordsVerbs")) {
				Features.setUseTopWordsVerbs(Boolean.parseBoolean(features.get(feature)));
			}if (feature.equals("useTopWordsAdjectives")) {
				Features.setUseTopWordsAdjectives(Boolean.parseBoolean(features.get(feature)));
			}if (feature.equals("useTopWordsAdverbs")) {
				Features.setUseTopWordsAdverbs(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("topWordsTypeOfPos")) {
				if (features.get(feature).equals("TOGETHER")) {
					Features.setTopWordsTypeOfPos(Features.TypeOfPos.TOGETHER);
				} else if (features.get(feature).equals("SEPARATED")) {
					Features.setTopWordsTypeOfPos(Features.TypeOfPos.SEPARATED);
				}
					
			}
			
			if (feature.equals("numberOfTopWordsPerClass")) {
				String number = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (number.equals("")) {
					number = "0";
				}
				Features.setNumberOfTopWordsPerClass(Integer.parseInt(number));
			}
			if (feature.equals("numberOfTopWordsPerPos")) {
				String number = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (number.equals("")) {
					number = "0";
				}
				Features.setNumberOfTopWordsPerPos(Integer.parseInt(number));
			}
			
			if (feature.equals("topWordsType")) {
				if (features.get(feature).equals("UNIQUE")) {
					Features.setTopWordsType(Features.TypeOfFeature.UNIQUE);
				} else if (features.get(feature).equals("SUMMED")) {
					Features.setTopWordsType(Features.TypeOfFeature.SUMMED);
				}
					
			}
			
			if (feature.equals("topWordsMinRatio")) {
				String minRatio = features.get(feature).replaceAll("[^0-9.]", "").trim();
				if (minRatio.equals("")) {
					minRatio = "0";
				}
				Features.setTopWordsMinRatio(Double.parseDouble(minRatio));
			}
			
			if (feature.equals("topWordsMinOccurrence")) {
				String minOcc = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (minOcc.equals("")) {
					minOcc = "0";
				}
				Features.setTopWordsMinOccurrence(Integer.parseInt(minOcc));
			}
			
			
			
			// PATTERN FEATURES
			if (feature.equals("usePatternFeatures")) {
				Features.setUsePatternFeatures(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("patternFeaturesType")) {
				if (features.get(feature).equals("UNIQUE")) {
					Features.setPatternFeaturesType(Features.TypeOfFeature.UNIQUE);
				} else if (features.get(feature).equals("SUMMED")) {
					Features.setPatternFeaturesType(Features.TypeOfFeature.SUMMED);
				}
			}
			
			if (feature.equals("patternLength")) {
				String patternLength = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (patternLength.equals("")) {
					patternLength = "0";
				}
				Features.setPatternLength(Integer.parseInt(patternLength));
			}
			
			if (feature.equals("numberOfPatternsPerClass")) {
				String numberOfPatternsPerClass = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (numberOfPatternsPerClass.equals("")) {
					numberOfPatternsPerClass = "0";
				}
				Features.setNumberOfPatternsPerClass(Integer.parseInt(numberOfPatternsPerClass));
			}
			
			
			if (feature.equals("minPatternLength")) {
				String patternLength = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (patternLength.equals("")) {
					patternLength = "0";
				}
				Features.setMinPatternLength(Integer.parseInt(patternLength));
			}
			if (feature.equals("maxPatternLength")) {
				String patternLength = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (patternLength.equals("")) {
					patternLength = "0";
				}
				Features.setMaxPatternLength(Integer.parseInt(patternLength));
			}
			if (feature.equals("numberOfPosCategories")) {
				String numberOfCategories = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (numberOfCategories.equals("")) {
					numberOfCategories = "0";
				}
				Features.setNumberOfPosCategories(Integer.parseInt(numberOfCategories));
			}
			
			if (feature.equals("category1")) {
				Features.setCategory1(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("category2")) {
				Features.setCategory2(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("category3")) {
				Features.setCategory3(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("category4")) {
				Features.setCategory4(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("action1")) {
				if (features.get(feature).equals("KEEP")) {
					Features.setAction1(Features.PatternActions.KEEP);
				} else if (features.get(feature).equals("LEMMATIZE")) {
					Features.setAction1(Features.PatternActions.LEMMATIZE);
				} else if (features.get(feature).equals("POS")) {
					Features.setAction1(Features.PatternActions.POS);
				} else if (features.get(feature).equals("SIMPLIFIEDPOS")) {
					Features.setAction1(Features.PatternActions.SIMPLIFIEDPOS);
				} else if (features.get(feature).equals("SIMPLIFIEDPOSANDSENTIMENT")) {
					Features.setAction1(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT);
				} else if (features.get(feature).equals("REPLACEWITH")) {
					Features.setAction1(Features.PatternActions.REPLACEWITH);
				}
			}
			
			if (feature.equals("action2")) {
				if (features.get(feature).equals("KEEP")) {
					Features.setAction2(Features.PatternActions.KEEP);
				} else if (features.get(feature).equals("LEMMATIZE")) {
					Features.setAction2(Features.PatternActions.LEMMATIZE);
				} else if (features.get(feature).equals("POS")) {
					Features.setAction2(Features.PatternActions.POS);
				} else if (features.get(feature).equals("SIMPLIFIEDPOS")) {
					Features.setAction2(Features.PatternActions.SIMPLIFIEDPOS);
				} else if (features.get(feature).equals("SIMPLIFIEDPOSANDSENTIMENT")) {
					Features.setAction2(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT);
				} else if (features.get(feature).equals("REPLACEWITH")) {
					Features.setAction2(Features.PatternActions.REPLACEWITH);
				}
			}
			
			if (feature.equals("action3")) {
				if (features.get(feature).equals("KEEP")) {
					Features.setAction3(Features.PatternActions.KEEP);
				} else if (features.get(feature).equals("LEMMATIZE")) {
					Features.setAction3(Features.PatternActions.LEMMATIZE);
				} else if (features.get(feature).equals("POS")) {
					Features.setAction3(Features.PatternActions.POS);
				} else if (features.get(feature).equals("SIMPLIFIEDPOS")) {
					Features.setAction3(Features.PatternActions.SIMPLIFIEDPOS);
				} else if (features.get(feature).equals("SIMPLIFIEDPOSANDSENTIMENT")) {
					Features.setAction3(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT);
				} else if (features.get(feature).equals("REPLACEWITH")) {
					Features.setAction3(Features.PatternActions.REPLACEWITH);
				}
			}
			
			if (feature.equals("action3")) {
				if (features.get(feature).equals("KEEP")) {
					Features.setAction3(Features.PatternActions.KEEP);
				} else if (features.get(feature).equals("LEMMATIZE")) {
					Features.setAction3(Features.PatternActions.LEMMATIZE);
				} else if (features.get(feature).equals("POS")) {
					Features.setAction3(Features.PatternActions.POS);
				} else if (features.get(feature).equals("SIMPLIFIEDPOS")) {
					Features.setAction3(Features.PatternActions.SIMPLIFIEDPOS);
				} else if (features.get(feature).equals("SIMPLIFIEDPOSANDSENTIMENT")) {
					Features.setAction3(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT);
				} else if (features.get(feature).equals("REPLACEWITH")) {
					Features.setAction3(Features.PatternActions.REPLACEWITH);
				}
			}
			
			if (feature.equals("action4")) {
				if (features.get(feature).equals("KEEP")) {
					Features.setAction4(Features.PatternActions.KEEP);
				} else if (features.get(feature).equals("LEMMATIZE")) {
					Features.setAction4(Features.PatternActions.LEMMATIZE);
				} else if (features.get(feature).equals("POS")) {
					Features.setAction4(Features.PatternActions.POS);
				} else if (features.get(feature).equals("SIMPLIFIEDPOS")) {
					Features.setAction4(Features.PatternActions.SIMPLIFIEDPOS);
				} else if (features.get(feature).equals("SIMPLIFIEDPOSANDSENTIMENT")) {
					Features.setAction4(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT);
				} else if (features.get(feature).equals("REPLACEWITH")) {
					Features.setAction4(Features.PatternActions.REPLACEWITH);
				}
			}
			
			if (feature.equals("replacement1")) {
				if (!features.get(feature).equalsIgnoreCase("null")) {
					Features.setReplacement1(features.get(feature));
				}
			}
			if (feature.equals("replacement2")) {
				if (!features.get(feature).equalsIgnoreCase("null")) {
					Features.setReplacement2(features.get(feature));
				}
			}
			if (feature.equals("replacement3")) {
				if (!features.get(feature).equalsIgnoreCase("null")) {
					Features.setReplacement3(features.get(feature));
				}
			}
			if (feature.equals("replacement4")) {
				if (!features.get(feature).equalsIgnoreCase("null")) {
					Features.setReplacement4(features.get(feature));
				}
			}
			
			if (feature.equals("patternsMinOccurrence")) {
				String minOcc = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (minOcc.equals("")) {
					minOcc = "0";
				}
				Features.setPatternsMinOccurrence(Integer.parseInt(minOcc));
			}
			
			if (feature.equals("alpha")) {
				String alpha = features.get(feature).replaceAll("[^0-9.]", "").trim();
				if (alpha.equals("")) {
					alpha = "0";
				}
				Features.setAlpha(Double.parseDouble(alpha));
			}
			
			if (feature.equals("gamma")) {
				String gamma = features.get(feature).replaceAll("[^0-9.]", "").trim();
				if (gamma.equals("")) {
					gamma = "0";
				}
				Features.setGamma(Double.parseDouble(gamma));
			}
			
			if (feature.equals("basicPatternsKnn")) {
				String basicPatternsKnn = features.get(feature).replaceAll("[^0-9.]", "").trim();
				if (basicPatternsKnn.equals("")) {
					basicPatternsKnn = "0";
				}
				Features.setBasicPatternsKnn(Integer.parseInt(basicPatternsKnn));
			}
			
			if (feature.equals("categories")) {
				if(!(features.get(feature).equals(null) || features.get(feature).equals("null"))) {
					String[] cats = features.get(feature).split("#");
					Features.Categories[] categories = new Features.Categories[cats.length];
					for (int i = 0; i < cats.length; i++) {
						if (cats[i].equalsIgnoreCase("cat1")) {
							categories[i] = Features.Categories.cat1;
						} else if (cats[i].equalsIgnoreCase("cat2")) {
							categories[i] = Features.Categories.cat2;
						} else if (cats[i].equalsIgnoreCase("cat3")) {
							categories[i] = Features.Categories.cat3;
						} else if (cats[i].equalsIgnoreCase("cat4")) {
							categories[i] = Features.Categories.cat4;
						}
					}
					Features.setCategories(categories);
				}
			}
			
			//=====================================================================
			if (feature.equalsIgnoreCase("categoriesMap")) {
				if(!(features.get(feature).equals(null) || features.get(feature).equals("null"))) {
					String[] cats = features.get(feature).split("#");
					HashMap<String, Features.Categories> categories = new HashMap<>();
					for (int i=0; i<cats.length; i++) {
						String[] map = cats[i].split("_");
						if (map.length>=2) {
							if (Constants.allPOS.contains(map[0])) {
								String pos = map[0];
								Features.Categories cat = null;
								if (map[1].equalsIgnoreCase("cat1")) {
									cat = Features.Categories.cat1;
								} else if (map[1].equalsIgnoreCase("cat2")) {
									cat = Features.Categories.cat2;
								} else if (map[1].equalsIgnoreCase("cat3")) {
									cat = Features.Categories.cat3;
								} else if (map[1].equalsIgnoreCase("cat4")) {
									cat = Features.Categories.cat4;
								}
								if (cat != null) {
									categories.put(pos, cat);
								}
							} 
						}
					}
					Features.setCategoriesMap(categories);
				}
			}
			//=====================================================================
			
			
			// ADVANCED SENTIMENT FEATURES
			if (feature.equals("useAdvancedSentimentFeatures")) {
				Features.setUseAdvancedSentimentFeatures(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("typeOfAdvancedSentimentFeatures")) {
				if (features.get(feature).equals("POLARITY")) {
					Features.setTypeOfAdvancedSentimentFeatures(Features.AdvancedTypeOfFeatures.POLARITY);
				} else if (features.get(feature).equals("SCORE")) {
					Features.setTypeOfAdvancedSentimentFeatures(Features.AdvancedTypeOfFeatures.SCORE);
				}
			}
			
			if (feature.equals("numberOfWordsBefore")) {
				String numberOfWordsBefore = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (numberOfWordsBefore.equals("")) {
					numberOfWordsBefore = "0";
				}
				Features.setNumberOfWordsBefore(Integer.parseInt(numberOfWordsBefore));
			}
			
			if (feature.equals("numberOfWordsAfter")) {
				String numberOfWordsAfter = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (numberOfWordsAfter.equals("")) {
					numberOfWordsAfter = "0";
				}
				Features.setNumberOfWordsAfter(Integer.parseInt(numberOfWordsAfter));
			}
			
			if (feature.equals("addSentimentClassInformation")) {
				Features.setAddSentimentClassInformation(Boolean.parseBoolean(features.get(feature)));
			}
			
			
			
			// ADVANCED SEMANTIC FEATURES
			if (feature.equals("useAdvancedSemanticFeatures")) {
				Features.setUseAdvancedSemanticFeatures(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("wordsIntoAccount")) {
				if (features.get(feature).equals("ALL")) {
					Features.setWordsIntoAccount(Features.TopicRelated.ALL);
				} else if (features.get(feature).equals("ONLYTOPICRELATED")) {
					Features.setWordsIntoAccount(Features.TopicRelated.ONLYTOPICRELATED);
				}
			}
			
			if (feature.equals("countOtherWords")) {
				Features.setCountOtherWords(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("advancedSemanticPos")) {
				if (features.get(feature).equals("SEPARATED")) {
					Features.setAdvancedSemanticPos(Features.TypeOfPos.SEPARATED);
				} else if (features.get(feature).equals("TOGETHER")) {
					Features.setAdvancedSemanticPos(Features.TypeOfPos.TOGETHER);
				}
			}
			
			if (feature.equals("advUseOfPositiveWords")) {
				Features.setAdvUseOfPositiveWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("advUseOfNegativeWords")) {
				Features.setAdvUseOfNegativeWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("advUseOfHighlyPositiveWords")) {
				Features.setAdvUseOfHighlyPositiveWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("advUseOfHighlyNegativeWords")) {
				Features.setAdvUseOfHighlyNegativeWords(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("advUseOfOpinionWords")) {
				Features.setAdvUseOfOpinionWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("advUseOfUncertaintyWords")) {
				Features.setAdvUseOfUncertaintyWords(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("advUseOfActiveForm")) {
				Features.setAdvUseOfActiveForm(Boolean.parseBoolean(features.get(feature)));
			}
			if (feature.equals("advUseOfPassiveForm")) {
				Features.setAdvUseOfPassiveForm(Boolean.parseBoolean(features.get(feature)));
			}
			
			
			
			// ADVANCED PATTERN FEATURES
			if (feature.equals("useAdvancedPatternFeatures")) {
				Features.setUseAdvancedPatternFeatures(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("advancedPatternFeaturesType")) {
				if (features.get(feature).equals("SUMMED")) {
					Features.setAdvancedPatternFeaturesType(Features.TypeOfFeature.SUMMED);
				} else if (features.get(feature).equals("UNIQUE")) {
					Features.setAdvancedPatternFeaturesType(Features.TypeOfFeature.UNIQUE);
				}
			}
			
			if (feature.equals("advancedPatternLength")) {
				String length = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (length.equals("")) {
					length = "0";
				}
				Features.setAdvancedPatternLength(Integer.parseInt(length));
			}
			
			if (feature.equals("advancedNumberOfPatternsPerClass")) {
				String numberOfPatternsPerClass = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (numberOfPatternsPerClass.equals("")) {
					numberOfPatternsPerClass = "0";
				}
				Features.setAdvancedNumberOfPatternsPerClass(Integer.parseInt(numberOfPatternsPerClass));
			}
			
			
			if (feature.equals("advancedMinPatternLength")) {
				String length = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (length.equals("")) {
					length = "0";
				}
				Features.setAdvancedMinPatternLength(Integer.parseInt(length));
			}
			
			if (feature.equals("advancedMaxPatternLength")) {
				String length = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (length.equals("")) {
					length = "0";
				}
				Features.setAdvancedMaxPatternLength(Integer.parseInt(length));
			}
			
			if (feature.equals("advancedPatternsMinOccurrence")) {
				String minOcc = features.get(feature).replaceAll("[^0-9]", "").trim();
				if (minOcc.equals("")) {
					minOcc = "0";
				}
				Features.setAdvancedPatternsMinOccurrence(Integer.parseInt(minOcc));
			}
			
			if (feature.equals("advancedPatternAlpha")) {
				String alpha = features.get(feature).replaceAll("[^0-9.]", "").trim();
				if (alpha.equals("")) {
					alpha = "0";
				}
				Features.setAdvancedPatternAlpha(Double.parseDouble(alpha));
			}
			
			if (feature.equals("advancedPatternGamma")) {
				String gamma = features.get(feature).replaceAll("[^0-9.]", "").trim();
				if (gamma.equals("")) {
					gamma = "0";
				}
				Features.setAdvancedPatternGamma(Double.parseDouble(gamma));
			}
			
			if (feature.equals("advancedPatternsKnn")) {
				String AdvancedPatternsKnn = features.get(feature).replaceAll("[^0-9.]", "").trim();
				if (AdvancedPatternsKnn.equals("")) {
					AdvancedPatternsKnn = "0";
				}
				Features.setAdvancedPatternsKnn(Integer.parseInt(AdvancedPatternsKnn));
			}
			
			// ADVANCED PATTERN FEATURES
			if (feature.equals("useAdvancedUnigramFeatures")) {
				Features.setUseAdvancedUnigramFeatures(Boolean.parseBoolean(features.get(feature)));
			}
			
			if (feature.equals("isLemma")) {
				if (features.get(feature).equals("Lemmas")) {
					Features.setIsLemma((Features.IsLemma.Lemmas));
				} else if (features.get(feature).equals("Words")) {
					Features.setIsLemma((Features.IsLemma.Words));
				}
			}
			
		}
		
	}
	
	/**
	 * Save the positions of the attributes in the file
	 * 
	 * @param file : the file to read
	 * @return
	 */
	public static void getAttributePositions(String file) {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			String line;

			// Read just the first line to tell whether or not the file is fine
			if ((line = reader.readLine()) != null) {
				String[] map = line.split("\t");
				
				for (int i=0; i<map.length; i++) {
					if (map[i].equals("Tweet ID")) {
						Parameters.setTextIdPosition(i);
						System.out.println("Text ID Position = " + i);
					}
					if (map[i].equals("Username")) {
						Parameters.setUserNamePosition(i);
						System.out.println("User Name Position = " + i);
					}
					if (map[i].equals("Tweet Message")) {
						Parameters.setTextPosition(i);
						System.out.println("Twitter Message Position = " + i);
					}
					if (map[i].equals("Class")) {
						Parameters.setClassPosition(i);
						System.out.println("Class Position = " + i);
					}
					
					if (map[i].equals("Index")) {
						Parameters.setIndexPosition(i);
						Parameters.setUseAdavancedFeaturesAllowed(true);
						System.out.println("Index Position = " + i);
					}
					
				}

				
			}
		} catch (FileNotFoundException e) {
			System.out.println("Not a file");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Save the different attributes of a tweet in the file
	 * 
	 * @param file : the file to read
	 * @return
	 */
	public static ArrayList<String> getAttributes(String file) {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			String line;

			// Read just the first line to tell whether or not the file is fine
			if ((line = reader.readLine()) != null) {
				String[] map = line.split("\t");
				return new ArrayList<>(Arrays.asList(map));
			}
		} catch (FileNotFoundException e) {
			System.out.println("Not a file");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return new ArrayList<>();
	}
	
	/**
	 * Get the different classes from a file
	 * @param file : the file to read
	 */
	public static ArrayList<String> getClasses(String file) {
		
		ArrayList<String> classes = new ArrayList<>();
		int position = Parameters.getClassPosition();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] map = line.split("\t");
				if (map.length > position) {
					String cla = map[position].split("#")[0];
					if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
						if (!classes.contains(cla)) {
							classes.add(cla);
						}
					} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
						String [] multi = cla.split("#");
						for (String c : multi){
							if (!classes.contains(c)) {
								classes.add(c);
							}
						}
					}
				}
			}

			
		} catch (FileNotFoundException e) {
			System.out.println("Not a file");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return classes;
		
	}
	
	/**
	 * Import the list of Top Words from a file
	 * @param file
	 * @param type
	 */
	public static void importTopWords(String file, Features.TypeOfPos type) {
		
		if (type.equals(Features.TypeOfPos.TOGETHER)) {
			HashMap<String, ArrayList<String>> topWords = new HashMap<>();
			
			for (String sentiment : Parameters.getClasses()) {
				topWords.put(sentiment, new ArrayList<>());
			}
			
			BufferedReader reader = null;
			
			try {
				reader = new BufferedReader(new FileReader(file));
				String line;
				while ((line = reader.readLine()) != null) {
					String[] map = line.split("\t");
					if (map.length == 3) {
						String word = map[0];
						String sentiment = map[1];
						
						if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
							topWords.get(sentiment).add(word.toLowerCase());
						}
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("Not a file");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			Parameters.setTopWords(topWords);
			
		} else if (type.equals(Features.TypeOfPos.SEPARATED)) {
			HashMap<String, ArrayList<String>> topNouns = new HashMap<>();
			HashMap<String, ArrayList<String>> topVerbs = new HashMap<>();
			HashMap<String, ArrayList<String>> topAdjectives = new HashMap<>();
			HashMap<String, ArrayList<String>> topAdverbs = new HashMap<>();
			
			for (String sentiment : Parameters.getClasses()) {
				topNouns.put(sentiment, new ArrayList<>());
				topVerbs.put(sentiment, new ArrayList<>());
				topAdjectives.put(sentiment, new ArrayList<>());
				topAdverbs.put(sentiment, new ArrayList<>());
			}
			
			BufferedReader reader = null;
			
			try {
				reader = new BufferedReader(new FileReader(file));
				String line;
				line = reader.readLine();
				while ((line = reader.readLine()) != null) {
					String[] map = line.split("\t");
					if (map.length == 3) {
						String word = map[0];
						String sentiment = map[1];
						String pos = map[2];
						
						if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
							if (pos.equalsIgnoreCase("NOUN")) {
								topNouns.get(sentiment).add(word.toLowerCase());
							} else if (pos.equalsIgnoreCase("VERB")) {
								topVerbs.get(sentiment).add(word.toLowerCase());
							} else if (pos.equalsIgnoreCase("ADJECTIVE")) {
								topAdjectives.get(sentiment).add(word.toLowerCase());
							} else if (pos.equalsIgnoreCase("ADVERB")) {
								topAdverbs.get(sentiment).add(word.toLowerCase());
							}
						}
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("Not a file");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			Parameters.setTopNouns(topNouns);
			Parameters.setTopVerbs(topVerbs);
			Parameters.setTopAdjectives(topAdjectives);
			Parameters.setTopAdverbs(topAdverbs);
		}
		
	}
	
	/**
	 * Import a set of basic patterns from a file
	 * @param file
	 * @param type
	 */
	public static void importBasicPatterns(String file, Features.TypeOfFeature type) {
		if (type!=null) {
			if (type.equals(Features.TypeOfFeature.UNIQUE)) {
				HashMap<String, ArrayList<Pattern>> singleLengthPatterns = new HashMap<String, ArrayList<Pattern>>();
				
				for (String sentiment : Parameters.getClasses()) {
					singleLengthPatterns.put(sentiment, new ArrayList<Pattern>());
				}
				
				BufferedReader reader = null;
				
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					while ((line = reader.readLine()) != null) {
						String[] map = line.split("\t");
						if (map.length == 3) {
							Pattern ptrn = new Pattern(map[0].split("#"));
							String sentiment = map[1];
							
							if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
								singleLengthPatterns.get(sentiment).add(ptrn);
							}
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Not a file");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				Parameters.setSingleLengthPatterns(singleLengthPatterns);
				
			} else if (type.equals(Features.TypeOfFeature.SUMMED)) {
				HashMap<String, HashMap<Integer, ArrayList<Pattern>>> multiLengthPatterns = new HashMap<String, HashMap<Integer, ArrayList<Pattern>>>();
				
				for (String sentiment : Parameters.getClasses()) {
					multiLengthPatterns.put(sentiment, new HashMap<Integer, ArrayList<Pattern>>());
					for (int length = Features.getMinPatternLength(); length <= Features.getMaxPatternLength(); length++) {
						multiLengthPatterns.get(sentiment).put(length, new ArrayList<Pattern>());
					}
					
				}
				
				BufferedReader reader = null;
				
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					while ((line = reader.readLine()) != null) {
						String[] map = line.split("\t");
						if (map.length == 3) {
							String[] tokens = map[0].split("#");
							Integer size = tokens.length;
							Pattern ptrn = new Pattern(tokens);
							String sentiment = map[1];
							
							if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
								if (multiLengthPatterns.get(sentiment).containsKey(size)) {
									multiLengthPatterns.get(sentiment).get(size).add(ptrn);
								}
							}
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Not a file");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				Parameters.setMultiLengthPatterns(multiLengthPatterns);
				
			}
		} else {
			System.err.print("The features type is not set!!");
		}
	}
	
	/**
	 * Import a set of basic patterns from a file [Numeric format]
	 * @param file
	 * @param type
	 */
	public static void importBasicPatternsNumeric(String file, Features.TypeOfFeature type) {
		if (type!=null) {
			if (type.equals(Features.TypeOfFeature.UNIQUE)) {
				HashMap<String, ArrayList<PatternNumeric>> singleLengthPatterns = new HashMap<>();
				
				for (String sentiment : Parameters.getClasses()) {
					singleLengthPatterns.put(sentiment, new ArrayList<PatternNumeric>());
				}
				
				BufferedReader reader = null;
				
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					while ((line = reader.readLine()) != null) {
						String[] map = line.split("\t");
						if (map.length == 3) {
							String[] ptrn = map[0].split("#");
							int[] p = new int[ptrn.length];
							for (int i=0; i<ptrn.length; i++) {
								try {
									p[i] = Integer.parseInt(ptrn[i]);
								} catch (NumberFormatException e) {
									System.out.println(e);
								}
							}
							
							PatternNumeric pn = new PatternNumeric(p);
							
							String sentiment = map[1];
							
							if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
								singleLengthPatterns.get(sentiment).add(pn);
							}
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Not a file");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				Parameters.setSingleLengthPatternsNumeric(singleLengthPatterns);
				
			} else if (type.equals(Features.TypeOfFeature.SUMMED)) {
				HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> multiLengthPatterns = new HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>>();
				
				for (String sentiment : Parameters.getClasses()) {
					multiLengthPatterns.put(sentiment, new HashMap<Integer, ArrayList<PatternNumeric>>());
					for (int length = Features.getMinPatternLength(); length <= Features.getMaxPatternLength(); length++) {
						multiLengthPatterns.get(sentiment).put(length, new ArrayList<PatternNumeric>());
					}
					
				}
				
				BufferedReader reader = null;
				
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					while ((line = reader.readLine()) != null) {
						String[] map = line.split("\t");
						if (map.length == 3) {
							String[] tokens = map[0].split("#");
							int[] p = new int[tokens.length];
							
							for (int i=0; i<tokens.length; i++) {
								try {
									p[i] = Integer.parseInt(tokens[i]);
								} catch (NumberFormatException e) {
									System.out.println(e);
								}
							}
							
							Integer size = tokens.length;
							PatternNumeric ptrn = new PatternNumeric(p);
							String sentiment = map[1];
							
							if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
								if (multiLengthPatterns.get(sentiment).containsKey(size)) {
									multiLengthPatterns.get(sentiment).get(size).add(ptrn);
								}
							}
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Not a file");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				Parameters.setMultiLengthPatternsNumeric(multiLengthPatterns);
				
			}
		} else {
			System.err.print("The features type is not set!!");
		}
	}
	
	/**
	 * Import a set of advanced patterns from a file
	 * @param file
	 * @param type
	 */
	public static void importAdvancedPatterns(String file, Features.TypeOfFeature type) {
		if (type!=null) {
			if (type.equals(Features.TypeOfFeature.UNIQUE)) {
				HashMap<String, ArrayList<Pattern>> singleLengthPatterns = new HashMap<String, ArrayList<Pattern>>();
				
				for (String sentiment : Parameters.getClasses()) {
					singleLengthPatterns.put(sentiment, new ArrayList<Pattern>());
				}
				
				BufferedReader reader = null;
				
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					while ((line = reader.readLine()) != null) {
						String[] map = line.split("\t");
						if (map.length == 3) {
							Pattern ptrn = new Pattern(map[0].split("#"));
							String sentiment = map[1];
							
							if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
								singleLengthPatterns.get(sentiment).add(ptrn);
							}
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Not a file");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				Parameters.setSingleLengthAdvancedPatterns(singleLengthPatterns);
				
			} else if (type.equals(Features.TypeOfFeature.SUMMED)) {
				HashMap<String, HashMap<Integer, ArrayList<Pattern>>> multiLengthPatterns = new HashMap<String, HashMap<Integer, ArrayList<Pattern>>>();
				
				for (String sentiment : Parameters.getClasses()) {
					multiLengthPatterns.put(sentiment, new HashMap<Integer, ArrayList<Pattern>>());
					for (int length = Features.getAdvancedMinPatternLength(); length <= Features.getAdvancedMaxPatternLength(); length++) {
						multiLengthPatterns.get(sentiment).put(length, new ArrayList<Pattern>());
					}
				}
				
				BufferedReader reader = null;
				
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					while ((line = reader.readLine()) != null) {
						String[] map = line.split("\t");
						if (map.length == 3) {
							String[] tokens = map[0].split("#");
							Integer size = tokens.length;
							Pattern ptrn = new Pattern(tokens);
							String sentiment = map[1];
							
							if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
								if (multiLengthPatterns.get(sentiment).containsKey(size)) {
									multiLengthPatterns.get(sentiment).get(size).add(ptrn);
								}
							}
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Not a file");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				Parameters.setMultiLengthAdvancedPatterns(multiLengthPatterns);
				
			}
		} else {
			System.err.print("The features type is not set!!");
		}
		
		
	}
	
	/**
	 * Import a set of advanced patterns from a file [Numeric format]
	 * @param file
	 * @param type
	 */
	public static void importAdvancedPatternsNumeric(String file, Features.TypeOfFeature type) {
		if (type!=null) {
			if (type.equals(Features.TypeOfFeature.UNIQUE)) {
				HashMap<String, ArrayList<PatternNumeric>> singleLengthPatterns = new HashMap<>();
				
				for (String sentiment : Parameters.getClasses()) {
					singleLengthPatterns.put(sentiment, new ArrayList<PatternNumeric>());
				}
				
				BufferedReader reader = null;
				
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					while ((line = reader.readLine()) != null) {
						String[] map = line.split("\t");
						if (map.length == 3) {
							String[] ptrn = map[0].split("#");
							int[] p = new int[ptrn.length];
							for (int i=0; i<ptrn.length; i++) {
								try {
									p[i] = Integer.parseInt(ptrn[i]);
								} catch (NumberFormatException e) {
									System.out.println(e);
								}
							}
							
							PatternNumeric pn = new PatternNumeric(p);
							
							String sentiment = map[1];
							
							if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
								singleLengthPatterns.get(sentiment).add(pn);
							}
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Not a file");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				Parameters.setSingleLengthAdvancedPatternsNumeric(singleLengthPatterns);
				
			} else if (type.equals(Features.TypeOfFeature.SUMMED)) {
				HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> multiLengthPatterns = new HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>>();
				
				for (String sentiment : Parameters.getClasses()) {
					multiLengthPatterns.put(sentiment, new HashMap<Integer, ArrayList<PatternNumeric>>());
					for (int length = Features.getMinPatternLength(); length <= Features.getMaxPatternLength(); length++) {
						multiLengthPatterns.get(sentiment).put(length, new ArrayList<PatternNumeric>());
					}
					
				}
				
				BufferedReader reader = null;
				
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					while ((line = reader.readLine()) != null) {
						String[] map = line.split("\t");
						if (map.length == 3) {
							String[] tokens = map[0].split("#");
							int[] p = new int[tokens.length];
							
							for (int i=0; i<tokens.length; i++) {
								try {
									p[i] = Integer.parseInt(tokens[i]);
								} catch (NumberFormatException e) {
									System.out.println(e);
								}
							}
							
							Integer size = tokens.length;
							PatternNumeric ptrn = new PatternNumeric(p);
							String sentiment = map[1];
							
							if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
								if (multiLengthPatterns.get(sentiment).containsKey(size)) {
									multiLengthPatterns.get(sentiment).get(size).add(ptrn);
								}
							}
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Not a file");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				Parameters.setMultiLengthAdvancedPatternsNumeric(multiLengthPatterns);
				
			}
		} else {
			System.err.print("The features type is not set!!");
		}
	}
	
	
	//======================================//
	//     CHECK THE VALIDITY OF FILES      //
	//======================================//
	
	/**
	 * Checks whether a file is valid and that it is not a directory
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isValidFile(String file) {
		File dir = new File(file);
		if (dir.exists() && !dir.isDirectory()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether a directory is valid or not
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isValidDirectory(String file) {
		File dir = new File(file);
		if (dir.exists() && dir.isDirectory()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether a project file is valid or not
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isValidProjectFile(String file) {
		BufferedReader br = null;
		HashMap<String, String> config = new HashMap<>();
		
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			
			while ((line = br.readLine()) != null) {
				String[] map = line.split("\t");
				if (map.length==2) {
					config.put(map[0], map[1]);
				}
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Error, cannot find the file!");
		} catch (IOException e) {
			System.out.println("Error, cannot read the file!");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		// Check the type of project
		if (config.containsKey("typeOfProject")) {
			if (!config.get("typeOfProject").equals("TESTSET") && !config.get("typeOfProject").equals("NONANNOTATEDSET")) {
				return false;
			}
		} else {
			return false;
		}
		
		// Check the classes
		if (!config.containsKey("classes")) {
			return false;
		}
		
		// Check the project location
		if(!config.containsKey("projectLocation")) {
			return false;
		}
		
		// Check the project name
		if(config.containsKey("projectName")) {
			if (config.get("projectName").equals("")) {
				return false;
			}
		} else {
			return false;
		}
		
		// the rest of the items are not really required for a project to be valid
		
		return true;
	}
	
	/**
	 * Checks whether a raw file from which the set of tweets to annotate is extracted, is valid or not
	 * 
	 * @param file : the file location
	 * @return
	 */
	public static boolean isValidRawFile(String file, boolean isKnown) {
		
		// TODO fix this on the collector. There shouldn't be spaces!
		ArrayList<String> knownClass;
		if (isKnown) {
			knownClass = new ArrayList<String>(Arrays.asList("Tweet ID", "Username", "Tweet Message", "Class"));
		} else {
			knownClass = new ArrayList<String>(Arrays.asList("Tweet ID", "Username", "Tweet Message"));
		}
		
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			String line;

			// Read just the first line to tell whether or not the file is fine
			if ((line = reader.readLine()) != null) {
				ArrayList<String> map = new ArrayList<String>(Arrays.asList(line.split("\t")));
				for (String key : knownClass) {
					if (!map.contains(key)) {
						return false;
					}
				}
				return true;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Not a file");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks whether a file (supposedly containing a set of emotional words) is valid or not [word    PoS tag (or PoS)    Class]
	 * @param file
	 * @return
	 */
	public static boolean isValidEmotionalWordsFile(String file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));

			String line;

			// Read all the lines and check if any of them has the correct format, in which case return true and stop browsing
			while ((line = reader.readLine()) != null) {
				String[] map = line.split("\t");
				if (map.length == 3) {
					if (map[0].equals("") && Commons.isRelevant(map[1]) && Parameters.getClasses().contains(map[2].toUpperCase())) {
						return true;
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Not a file");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks whether a file (supposedly containing a set of patterns) is valid or not [Pattern (#-delimited)    Length(explicitly mentioned)    Class]
	 * @param file
	 * @return
	 */
	public static boolean isValidPatternsFile(String file) {
		
		// TODO add this
		
		return true;
	}
	
	/**
	 * Checks whether a file (supposedly containing a set of Top Words) is valid or not [Top Word    PoS    Class]
	 * @param file
	 * @return
	 */
	public static boolean isValidTopWordsFile(String file) {
		if (Features.getTopWordsTypeOfPos()!=null) {
			if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					line = reader.readLine();
					while ((line = reader.readLine()) != null) {
						String[] map = line.split("\t");
						if (map.length == 3) {
							String sentiment = map[1];
							String pos = map[2];

							if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
								if (pos.equalsIgnoreCase("NOUN") || pos.equalsIgnoreCase("VERB")
										|| pos.equalsIgnoreCase("ADJECTIVE") || pos.equalsIgnoreCase("ADVERB")) {
									return true;
								}
							}
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Not a file");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					line = reader.readLine();
					while ((line = reader.readLine()) != null) {
						String[] map = line.split("\t");
						if (map.length == 3) {
							String sentiment = map[1];
							String pos = map[2];
							if (Parameters.getClasses().contains(sentiment.toUpperCase())) {
								if (pos.equalsIgnoreCase("WORD") || pos.equalsIgnoreCase("NOUN")
										|| pos.equalsIgnoreCase("VERB") || pos.equalsIgnoreCase("ADJECTIVE")
										|| pos.equalsIgnoreCase("ADVERB")) {
									return true;
								}
							}
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Not a file");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} 
		} else {
			// This is supposed to be called only when opening a project file!!!
			// TODO file if needed
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String line;
				line = reader.readLine();
				while ((line = reader.readLine()) != null) {
					String[] map = line.split("\t");
					if (map.length == 3) {
						// String sentiment = map[1];
						String pos = map[2];
						
						if (pos.equalsIgnoreCase("WORD") || pos.equalsIgnoreCase("NOUN")
								|| pos.equalsIgnoreCase("VERB") || pos.equalsIgnoreCase("ADJECTIVE")
								|| pos.equalsIgnoreCase("ADVERB")) {
							return true;
						}

					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("Not a file");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	
	//======================================//
	//        IMPORT EXTRA FEATURES         //
	//======================================//
	
	/**
	 * Collects the extra features from a Table (i.e., a "*.txt" or "*.csv" file )
	 * @param file
	 * @param fileType
	 * @param isIdKnown
	 */
	public static void collectExtraFeaturesFromTable(String file, String fileType, ArrayList<Tweet> tweets, boolean isIdKnown) {
		
		String separator = "\t";
		
		if (fileType.equalsIgnoreCase("csv")) {
			separator = ",";
		} else if (fileType.equalsIgnoreCase("txt")) {
			separator = "\t";
		}
		
		ArrayList<String> featureNames = new ArrayList<>();
		HashMap<String, ArrayList<String>> extraFeatures = new HashMap<>();;
		
		BufferedReader reader = null;
		
		if (isIdKnown) {
			// Case where the tweet ID field is given
			String id = "Tweet ID";
			int position = 0;
			
			try {
				reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				featureNames = new ArrayList<>(Arrays.asList(line.split(separator)));
				
				position = featureNames.indexOf(id);
				
				while ((line = reader.readLine()) != null) {
					ArrayList<String> features = new ArrayList<>(Arrays.asList(line.split(separator)));
					extraFeatures.put(features.get(position), features);
				}
			} catch (FileNotFoundException e) {
				System.out.println("Not a file");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			for (Tweet tweet : tweets) {
				if (extraFeatures.containsKey(tweet.getId())) {
					tweet.setExtraFeatures(extraFeatures.get(tweet.getId()));
				} else {
					System.out.println("Error!!! One tweet ID does not correspond to any of the extra features!");
				}
			}
			
			ImportedFeatures.setImportedFeatures(featureNames);
		} else {
			// Case where the tweet ID field is given
			try {
				reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				featureNames = new ArrayList<>(Arrays.asList(line.split(separator)));
								
				int i = 0;
				while ((line = reader.readLine()) != null) {
					if (tweets.size()>i) {
						tweets.get(i).setExtraFeatures(new ArrayList<>(Arrays.asList(line.split(separator))));
					} else {
						System.out.println("Error!!! The number of tweets is less than that of the number of set of features (lines) extracted!");
					}
					
					i++;
				}
			} catch (FileNotFoundException e) {
				System.out.println("Not a file");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			ImportedFeatures.setImportedFeatures(featureNames);
		}
		
	}
	
	/**
	 * Collects the extra features from a Weka file (i.e., a "*.arff")
	 * @param file
	 * @param fileType
	 * @param isIdKnown
	 */
	public static void collectExtraFeaturesFromWekaFile(String file, ArrayList<Tweet> tweets) {
		// TODO fix this
		ArrayList<String> featureNames = new ArrayList<>();
		HashMap<String, ArrayList<String>> extraFeatures = new HashMap<>();;
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;

			while ((line = reader.readLine()) != null) {
				if (line.startsWith("@attribute")) {
					while (line.contains("  ")) {
						line.replaceAll(" ", " ");
					}
					String[] map = line.split(line, 3);
					featureNames.add(map[1]);
					
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Not a file");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		ImportedFeatures.setImportedFeatures(featureNames);
		
		
	}
	
	
	//======================================//
	//     GET THE LIST OF RECENT FILES     //
	//======================================//
	
	/**
	 * Read the file where the recently opened project files locations are
	 * stored.
	 * 
	 * @param file
	 * @return
	 */
	public static void getRecentFiles() {

		LinkedList<String> recentFiles = new LinkedList<String>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(Constants.RECENTFILESFILE));
			String line = "";

			while ((line = br.readLine()) != null) {
				if (line.contains(":\\") && line.endsWith(".senta")) {
					recentFiles.addFirst(line);
				}
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Error, cannot load recent files!");
		} catch (IOException e) {
			System.out.println("Error, cannot read the recent files log!");
		} catch (NumberFormatException e) {
			System.out.println("Error, One of the lines is corrupted!");
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		Loader.setRecentFiles(recentFiles);
	}

	
	//======================================//
	//         OTHER USEFUL METHODS         //
	//======================================//
	
	public static int countLines(String filename) {
		int nLines = 0;

		BufferedReader br = null;

		if (filename != null) {
			File file = new File(filename);
			if (file.exists()) {
				try {
					br = new BufferedReader(new FileReader(file));
					while (br.readLine() != null) {
						nLines++;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		}
		
		
		return nLines;
	}
	
}
