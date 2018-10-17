package commons.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import backend.processor.TextProcessing;
import commons.constants.Constants;
import main.items.Features;
import main.items.Parameters;
import main.items.Pattern;
import main.items.PatternNumeric;
import main.items.Tweet;
import main.items.Word;
import main.items.Features.TypeOfFeature;
import main.start.Loader;
import windows.others.AlertBox;

public class Writer {

	//======================================//
	//            PROJECT-RELATED           //
	//======================================//
	
	/**
	 * Save a project in the format "*.senta". The three list (Annotated, non annotated and non valid texts) will be saved within the file
	 */
	public static void saveProject(String file) {

		String t = "\t";
		String n = "\n";

		// WRITE THE CONFIGURATION ON A FILE
		File exportedFile = new File(file);
		File exportedFileDirectory = new File(exportedFile.getParent());

		boolean folderCreated = false;
		if (!exportedFileDirectory.exists()) {
			try {
				exportedFileDirectory.mkdir();
				folderCreated = true;
			} catch (SecurityException se) {
				AlertBox.display("Error", "The folder you want to save in cannot be accessed!", "OK");
			}
		} else {
			folderCreated = true;
		}

		BufferedWriter writer = null;

		if (folderCreated) {

			try {
				if (!exportedFile.exists()) {
					exportedFile.createNewFile();
				}
				writer = new BufferedWriter(new FileWriter(exportedFile));

				// Parameters collection
				writer.write("typeOfProject" + t + Parameters.getTypeOfProject() + n);
				writer.write("projectGoal" + t + Loader.getProjectGoal() + n);
				writer.write("classes" + t);
				writer.write(Parameters.getClasses().get(0));
				for (int i = 1; i < Parameters.getClasses().size(); i++) {
					writer.write("#" + Parameters.getClasses().get(i));
				}
				writer.write(n);

				writer.write("trainingSetLocation" + t + Parameters.getTrainingSetLocation() + n);
				writer.write("testSetLocation" + t + Parameters.getTestSetLocation() + n);
				writer.write("nonAnnotatedDataLocation" + t + Parameters.getNonAnnotatedDataLocation() + n);
				writer.write("projectLocation" + t + Parameters.getProjectLocation() + n);
				writer.write("projectName" + t + Parameters.getProjectName() + n);

				// Features
				String featuresFileLocation = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "-Features.sfs";
				writer.write("featuresFileLocation" + t + featuresFileLocation + n);

				// Top Words
				writer.write("topWordsFileExists" + t + (Parameters.isImportTopWords() || Parameters.isSaveTopWords()) + n);
				if (Parameters.isSaveTopWords()) {
					String fileLocation = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "-top_words.txt";
					writer.write("topWordsFileLocation" + t + fileLocation + n);
				} else {
					writer.write("topWordsFileLocation" + t + Parameters.getTopWordsImportedFileLocation() + n);
				}
				// Basic Patterns
				writer.write("basicPatternsFileExists" + t + (Parameters.isImportBasicPatterns() || Parameters.isSaveBasicPatterns()) + n);
				if (Parameters.isSaveBasicPatterns()) {
					String fileLocation = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "-basic_patterns.txt";
					writer.write("basicPatternsFileLocation" + t + fileLocation + n);
				} else {
					writer.write("basicPatternsFileLocation" + t + Parameters.getBasicPatternsImportedFileLocation() + n);
				}
				// Advanced Patterns
				writer.write("advancedPatternsFileExists" + t + (Parameters.isImportAdvancedPatterns() || Parameters.isSaveAdvancedPatterns()) + n);
				if (Parameters.isSaveAdvancedPatterns()) {
					String fileLocation = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "advanced_patterns.txt";
					writer.write("advancedPatternsFileLocation" + t + fileLocation + n);
				} else {
					writer.write("advancedPatternsFileLocation" + t + Parameters.getAdvancedPatternsImportedFileLocation() + n);
				}

				writer.write("isSaveCsv" + t + Parameters.isSaveCsv() + n);
				writer.write("isSaveTxt" + t + Parameters.isSaveTxt() + n);
				writer.write("isSaveArff" + t + Parameters.isSaveArff() + n);
				writer.write("isSaveTopWords" + t + Parameters.isSaveTopWords() + n);
				writer.write("isSaveBasicPatterns" + t + Parameters.isSaveBasicPatterns() + n);
				writer.write("isSaveAdvancedPatterns" + t + Parameters.isSaveAdvancedPatterns());
				
//				writer.write("textIdPosition" + t + Parameters.getTextIdPosition());
//				writer.write("userNamePosition" + t + Parameters.getUserNamePosition());
//				writer.write("textPosition" + t + Parameters.getTextPosition());
//				writer.write("classPosition" + t + Parameters.getClassPosition());
//				writer.write("indexPosition" + t + Parameters.getIndexPosition());

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void saveFeatures(String file) {
		exportFeatures(file);
	}
	
	public static void saveTopWords(String file) {
		File exportedFile = new File(file);

		BufferedWriter writer = null;

		try {
			if (!exportedFile.exists()) {
				exportedFile.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(exportedFile));
			
			String t = "\t";
			String n = "\n";

			if (Features.getTopWordsTypeOfPos() != null) {
				if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
					for (String sentiment : Parameters.getTopWords().keySet()) {
						for (String word : Parameters.getTopWords().get(sentiment)) {
							writer.write(word + t + sentiment + t + "WORD" + n);
						}
					}
				} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
					for (String sentiment : Parameters.getTopNouns().keySet()) {
						for (String word : Parameters.getTopNouns().get(sentiment)) {
							writer.write(word + t + sentiment + t + "NOUN" + n);
						}
					}
					for (String sentiment : Parameters.getTopVerbs().keySet()) {
						for (String word : Parameters.getTopVerbs().get(sentiment)) {
							writer.write(word + t + sentiment + t + "VERB" + n);
						}
					}
					for (String sentiment : Parameters.getTopAdjectives().keySet()) {
						for (String word : Parameters.getTopAdjectives().get(sentiment)) {
							writer.write(word + t + sentiment + t + "ADJECTIVE" + n);
						}
					}
					for (String sentiment : Parameters.getTopAdverbs().keySet()) {
						for (String word : Parameters.getTopAdverbs().get(sentiment)) {
							writer.write(word + t + sentiment + t + "ADVERB" + n);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void saveBasicPatterns(String file) {

		if (Features.getPatternFeaturesType()!=null) {
			File exportedFile = new File(file);
			BufferedWriter writer = null;
			if (Features.getPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {
				try {
					if (!exportedFile.exists()) {
						exportedFile.createNewFile();
					}
					writer = new BufferedWriter(new FileWriter(exportedFile));

					String t = "\t";
					String n = "\n";

					if (Parameters.getSingleLengthPatterns()!=null) {
						for (String sentiment : Parameters.getSingleLengthPatterns().keySet()) {
							for (Pattern pattern : Parameters.getSingleLengthPatterns().get(sentiment)) {
								writer.write(pattern.getPatternToExport() + t + sentiment + t
										+ pattern.getPattern().length + n);
							}
						} 
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else if (Features.getPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
				try {
					if (!exportedFile.exists()) {
						exportedFile.createNewFile();
					}
					writer = new BufferedWriter(new FileWriter(exportedFile));

					String t = "\t";
					String n = "\n";

					if (Parameters.getMultiLengthPatterns()!=null) {
						for (String sentiment : Parameters.getMultiLengthPatterns().keySet()) {
							for (Integer length : Parameters.getMultiLengthPatterns().get(sentiment).keySet()) {
								for (Pattern pattern : Parameters.getMultiLengthPatterns().get(sentiment).get(length)) {
									writer.write(pattern.getPatternToExport() + t + sentiment + t + length + n);
								}
							}
						} 
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} 
		} else {
			File exportedFile = new File(file);
			try {
				if (!exportedFile.exists()) {
					exportedFile.createNewFile();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveBasicPatternsNumeric(String file) {

		if (Features.getPatternFeaturesType()!=null) {
			File exportedFile = new File(file);
			BufferedWriter writer = null;
			if (Features.getPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {
				try {
					if (!exportedFile.exists()) {
						exportedFile.createNewFile();
					}
					writer = new BufferedWriter(new FileWriter(exportedFile));

					String t = "\t";
					String n = "\n";

					if (Parameters.getSingleLengthPatternsNumeric()!=null) {
						for (String sentiment : Parameters.getSingleLengthPatternsNumeric().keySet()) {
							for (PatternNumeric pattern : Parameters.getSingleLengthPatternsNumeric().get(sentiment)) {
								writer.write(pattern.getPatternToExport() + t + sentiment + t
										+ pattern.getPattern().length + n);
							}
						} 
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else if (Features.getPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
				try {
					if (!exportedFile.exists()) {
						exportedFile.createNewFile();
					}
					writer = new BufferedWriter(new FileWriter(exportedFile));

					String t = "\t";
					String n = "\n";

					if (Parameters.getMultiLengthPatternsNumeric()!=null) {
						for (String sentiment : Parameters.getMultiLengthPatternsNumeric().keySet()) {
							for (Integer length : Parameters.getMultiLengthPatternsNumeric().get(sentiment).keySet()) {
								for (PatternNumeric pattern : Parameters.getMultiLengthPatternsNumeric().get(sentiment).get(length)) {
									writer.write(pattern.getPatternToExport() + t + sentiment + t + length + n);
								}
							}
						} 
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} 
		} else {
			File exportedFile = new File(file);
			try {
				if (!exportedFile.exists()) {
					exportedFile.createNewFile();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void saveAdvancedPatterns(String file) {

		if (Features.getAdvancedPatternFeaturesType()!=null) {
			File exportedFile = new File(file);
			BufferedWriter writer = null;
			if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {
				try {
					if (!exportedFile.exists()) {
						exportedFile.createNewFile();
					}
					writer = new BufferedWriter(new FileWriter(exportedFile));

					String t = "\t";
					String n = "\n";

					for (String sentiment : Parameters.getSingleLengthAdvancedPatterns().keySet()) {
						for (Pattern pattern : Parameters.getSingleLengthAdvancedPatterns().get(sentiment)) {
							writer.write(pattern.getPatternToExport() + t + sentiment + t
									+ pattern.getPattern().length + n);
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
				try {
					if (!exportedFile.exists()) {
						exportedFile.createNewFile();
					}
					writer = new BufferedWriter(new FileWriter(exportedFile));

					String t = "\t";
					String n = "\n";

					for (String sentiment : Parameters.getMultiLengthAdvancedPatterns().keySet()) {

						for (Integer length : Parameters.getMultiLengthAdvancedPatterns().get(sentiment).keySet()) {
							for (Pattern pattern : Parameters.getMultiLengthAdvancedPatterns().get(sentiment)
									.get(length)) {
								writer.write(pattern.getPatternToExport() + t + sentiment + t + length + n);
							}

						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} 
		} else {
			File exportedFile = new File(file);
			try {
				if (!exportedFile.exists()) {
					exportedFile.createNewFile();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveAdvancedPatternsNumeric(String file) {

		if (Features.getAdvancedPatternFeaturesType()!=null) {
			File exportedFile = new File(file);
			BufferedWriter writer = null;
			if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.UNIQUE)) {
				try {
					if (!exportedFile.exists()) {
						exportedFile.createNewFile();
					}
					writer = new BufferedWriter(new FileWriter(exportedFile));

					String t = "\t";
					String n = "\n";

					for (String sentiment : Parameters.getSingleLengthAdvancedPatternsNumeric().keySet()) {
						for (PatternNumeric pattern : Parameters.getSingleLengthAdvancedPatternsNumeric().get(sentiment)) {
							writer.write(pattern.getPatternToExport() + t + sentiment + t
									+ pattern.getPattern().length + n);
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else if (Features.getAdvancedPatternFeaturesType().equals(TypeOfFeature.SUMMED)) {
				try {
					if (!exportedFile.exists()) {
						exportedFile.createNewFile();
					}
					writer = new BufferedWriter(new FileWriter(exportedFile));

					String t = "\t";
					String n = "\n";

					for (String sentiment : Parameters.getMultiLengthAdvancedPatternsNumeric().keySet()) {

						for (Integer length : Parameters.getMultiLengthAdvancedPatternsNumeric().get(sentiment).keySet()) {
							for (PatternNumeric pattern : Parameters.getMultiLengthAdvancedPatternsNumeric().get(sentiment)
									.get(length)) {
								writer.write(pattern.getPatternToExport() + t + sentiment + t + length + n);
							}

						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} 
		} else {
			File exportedFile = new File(file);
			try {
				if (!exportedFile.exists()) {
					exportedFile.createNewFile();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void saveTxtFile(String file, ArrayList<Tweet> tweets) {
		String t = "\t";
		saveOutputFile(file, tweets, t);
	}
	
	public static void saveTxtFileNumeric(String file, ArrayList<Tweet> tweets) {
		String t = "\t";
		saveOutputFileNumeric(file, tweets, t);
	}
	
	
	public static void saveCsvFile(String file, ArrayList<Tweet> tweets) {
		String t = ",";
		saveOutputFile(file, tweets, t);
	}
	
	public static void saveCsvFileNumeric(String file, ArrayList<Tweet> tweets) {
		String t = ",";
		saveOutputFileNumeric(file, tweets, t);
	}

	
	public static void saveArffFile(String file, ArrayList<Tweet> tweets) {
		String t = ",";
		String n = "\n";
		
		File exportedFile = new File(file);
		File exportedFileDirectory = new File(exportedFile.getParent());

		boolean folderCreated = false;
		if (!exportedFileDirectory.exists()) {
			try {
				exportedFileDirectory.mkdir();
				folderCreated = true;
			} catch (SecurityException se) {
				AlertBox.display("Error", "The folder you want to save in cannot be accessed!", "OK");
			}
		} else {
			folderCreated = true;
		}

		BufferedWriter writer = null;

		if (folderCreated) {

			try {
				if (!exportedFile.exists()) {
					exportedFile.createNewFile();
				}
				
		writer = new BufferedWriter(new FileWriter(exportedFile));
		
		// ---------------------------//
		//       Add the titles       //
		// ---------------------------//
		writer.write("@relation Multi_Class_Sentiment_Analysis" + n + n);
		// Sentiment Features
		if (Features.isUseSentimentFeatures()) {
			if (Features.isNumberOfPositiveWords()) {
				writer.write("@attribute Number_Of_Positive_Words numeric" + n);
			}

			if (Features.isNumberOfNegativeWords()) {
				writer.write("@attribute Number_Of_Negative_Words numeric" + n);
			}
			if (Features.isNumberOfHighlyEmoPositiveWords()) {
				writer.write("@attribute Number_Of_Highly_Emo_Positive_Words numeric" + n);
			}
			if (Features.isNumberOfHighlyEmoNegativeWords()) {
				writer.write("@attribute Number_Of_Highly_Emo_Negative_Words numeric" + n);
			}
			if (Features.isNumberOfCapitalizedPositiveWords()) {
				writer.write("@attribute Number_Of_Capitalized_Positive_Words numeric" + n);
			}
			if (Features.isNumberOfCapitalizedNegativeWords()) {
				writer.write("@attribute Number_Of_Capitalized_Negative_Words numeric" + n);
			}
			if (Features.isRatioOfEmotionalWords()) {
				writer.write("@attribute Ratio_Of_Emotional_Words numeric" + n);
			}

			if (Features.isNumberOfPositiveEmoticons()) {
				writer.write("@attribute Number_Of_Positive_Emoticons numeric" + n);
			}
			if (Features.isNumberOfNegativeEmoticons()) {
				writer.write("@attribute Number_Of_Negative_Emoticons numeric" + n);
			}
			if (Features.isNumberOfNeutralEmoticons()) {
				writer.write("@attribute Number_Of_Neutral_Emoticons numeric" + n);
			}
			if (Features.isNumberOfJokingEmoticons()) {
				writer.write("@attribute Number_Of_Joking_Emoticons numeric" + n);
			}

			if (Features.isNumberOfPositiveSlangs()) {
				writer.write("@attribute Number_Of_Positive_Slangs numeric" + n);
			}
			if (Features.isNumberOfNegativeSlangs()) {
				writer.write("@attribute Number_Of_Negative_Slangs numeric" + n);
			}

			if (Features.isNumberOfPositiveHashtags()) {
				writer.write("@attribute NumberOfPositiveHashtags numeric" + n);
			}
			if (Features.isNumberOfNegativeHashtags()) {
				writer.write("@attribute NumberOfNegativeHashtags numeric" + n);
			}

			if (Features.isContrastWordsVsWords()) {
				writer.write("@attribute Contrast_Words_Vs_Words {true, false}" + n);
			}
			if (Features.isContrastWordsVsHashtags()) {
				writer.write("@attribute Contrast_Words_Vs_Hashtags {true, false}" + n);
			}
			if (Features.isContrastWordsVsEmoticons()) {
				writer.write("@attribute Contrast_Words_Vs_Emoticons {true, false}" + n);
			}
			if (Features.isContrastHashtagsVsHashtags()) {
				writer.write("@attribute Contrast_Hashtags_Vs_Hashtags {true, false}" + n);
			}
			if (Features.isContrastHashtagsVsEmoticons()) {
				writer.write("@attribute Contrast_Hashtags_Vs_Emoticons {true, false}" + n);
			}
		}

		// Punctuation Features
		if (Features.isUsePunctuationFeatures()) {
			if (Features.isNumberOfDots()) {
				writer.write("@attribute Number_Of_Dots numeric" + n);
			}
			if (Features.isNumberOfCommas()) {
				writer.write("@attribute Number_Of_Commas numeric" + n);
			}
			if (Features.isNumberOfSemicolons()) {
				writer.write("@attribute Number_Of_Semicolons numeric" + n);
			}
			if (Features.isNumberOfExclamationMarks()) {
				writer.write("@attribute Number_Of_Exclamation_Marks numeric" + n);
			}
			if (Features.isNumberOfQuestionMarks()) {
				writer.write("@attribute Number_Of_Question_Marks numeric" + n);
			}

			if (Features.isNumberOfParentheses()) {
				writer.write("@attribute Number_Of_Parentheses numeric" + n);
			}
			if (Features.isNumberOfBrackets()) {
				writer.write("@attribute Number_Of_Brackets numeric" + n);
			}
			if (Features.isNumberOfBraces()) {
				writer.write("@attribute Number_Of_Braces numeric" + n);
			}

			if (Features.isNumberOfApostrophes()) {
				writer.write("@attribute Number_Of_Apostrophes numeric" + n);
			}
			if (Features.isNumberOfQuotationMarks()) {
				writer.write("@attribute Number_Of_Quotation_Marks numeric" + n);
			}

			if (Features.isTotalNumberOfCharacters()) {
				writer.write("@attribute Total_Number_Of_Characters numeric" + n);
			}
			if (Features.isTotalNumberOfWords()) {
				writer.write("@attribute Total_Number_Of_Words numeric" + n);
			}
			if (Features.isTotalNumberOfSentences()) {
				writer.write("@attribute Total_Number_Of_Sentences numeric" + n);
			}
			if (Features.isAverageNumberOfCharactersPerSentence()) {
				writer.write("@attribute Average_Number_Of_Characters_Per_Sentence numeric" + n);
			}
			if (Features.isAverageNumberOfWordsPerSentence()) {
				writer.write("@attribute Average_Number_Of_Words_Per_Sentence numeric" + n);
			}
		}

		// Stylistic Features
		if (Features.isUseStylisticFeatures()) {

			if (Features.isNumberOfNouns()) {
				writer.write("@attribute Number_Of_Nouns numeric" + n);
			}
			if (Features.isRatioOfNouns()) {
				writer.write("@attribute Ratio_Of_Nouns numeric" + n);
			}
			if (Features.isNumberOfVerbs()) {
				writer.write("@attribute Number_Of_Verbs numeric" + n);
			}
			if (Features.isRatioOfVerbs()) {
				writer.write("@attribute Ratio_Of_Verbs numeric" + n);
			}
			if (Features.isNumberOfAdjectives()) {
				writer.write("@attribute Number_Of_Adjectives numeric" + n);
			}
			if (Features.isRatioOfAdjectives()) {
				writer.write("@attribute Ratio_Of_Adjectives numeric" + n);
			}
			if (Features.isNumberOfAdverbs()) {
				writer.write("@attribute Number_Of_Adverbs numeric" + n);
			}
			if (Features.isRatioOfAdverbs()) {
				writer.write("@attribute Ratio_Of_Adverbs numeric" + n);
			}

			if (Features.isUseOfSymbols()) {
				writer.write("@attribute Use_Of_Symbols {true, false}" + n);
			}
			if (Features.isUseOfComparativeForm()) {
				writer.write("@attribute Use_Of_Comparative_Form {true, false}" + n);
			}
			if (Features.isUseOfSuperlativeForm()) {
				writer.write("@attribute Use_Of_Superlative_Form {true, false}" + n);
			}
			if (Features.isUseOfProperNouns()) {
				writer.write("@attribute Use_Of_Proper_Nouns {true, false}" + n);
			}

			if (Features.isTotalNumberOfParticles()) {
				writer.write("@attribute Total_Number_Of_Particles numeric" + n);
			}
			if (Features.isAverageNumberOfParticles()) {
				writer.write("@attribute Average_Number_Of_Particles numeric" + n);
			}
			if (Features.isTotalNumberOfInterjections()) {
				writer.write("@attribute Total_Number_Of_Interjections numeric" + n);
			}
			if (Features.isAverageNumberOfInterjections()) {
				writer.write("@attribute Average_Number_Of_Interjections numeric" + n);
			}
			if (Features.isTotalNumberOfPronouns()) {
				writer.write("@attribute Total_Number_Of_Pronouns numeric" + n);
			}
			if (Features.isAverageNumberOfPronouns()) {
				writer.write("@attribute Average_Number_Of_Pronouns numeric" + n);
			}
			if (Features.isTotalNumberOfPronounsGroup1()) {
				writer.write("@attribute Total_Number_Of_Pronouns_Group_1 numeric" + n);
			}
			if (Features.isAverageNumberOfPronounsGroup1()) {
				writer.write("@attribute Average_Number_Of_Pronouns_Group_1 numeric" + n);
			}
			if (Features.isTotalNumberOfPronounsGroup2()) {
				writer.write("@attribute Total_Number_Of_Pronouns_Group_2 numeric" + n);
			}
			if (Features.isAverageNumberOfPronounsGroup2()) {
				writer.write("@attribute Average_Number_Of_Pronouns_Group_2 numeric" + n);
			}
			if (Features.isUseOfNegation()) {
				writer.write("@attribute Use_Of_Negation {true, false}" + n);
			}
			if (Features.isUseOfUncommonWords()) {
				writer.write("@attribute Use_Of_Uncommon_Words {true, false}" + n);
			}
			if (Features.isNumberOfUncommonWords()) {
				writer.write("@attribute Number_Of_Uncommon_Words numeric" + n);
			}
		}

		// Semantic Features
		if (Features.isUseSemanticFeatures()) {
			if (Features.isUseOfOpinionWords()) {
				writer.write("@attribute Use_Of_Opinion_Words {true, false}" + n);
			}
			if (Features.isUseOfHighlySentimentalWords()) {
				writer.write("@attribute Use_Of_Highly_Sentimental_Words {true, false}" + n);
			}
			if (Features.isUseOfUncertaintyWords()) {
				writer.write("@attribute Use_Of_Uncertainty_Words {true, false}" + n);
			}
			if (Features.isUseOfActiveForm()) {
				writer.write("@attribute Use_Of_Active_Form {true, false}" + n);
			}
			if (Features.isUseOfPassiveForm()) {
				writer.write("@attribute Use_Of_Passive_Form {true, false}" + n);
			}
		}
		
		// Unigram Features
		if (Features.isUseUnigramFeatures()) {
			if (Features.getUnigramTypeOfPos()!=null) {
				if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
					for (String sentiment : Parameters.getClasses()) {
						writer.write("@attribute Unigrams[" + sentiment + "] numeric" + n);
					}
				} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
					for (String sentiment : Parameters.getClasses()) {
						if (Features.isUseUnigramNouns()) {
							writer.write("@attribute Nouns[" + sentiment + "] numeric" + n);
						}
						if (Features.isUseUnigramVerbs()) {
							writer.write("@attribute Verbs[" + sentiment + "] numeric" + n);
						}
						if (Features.isUseUnigramAdjectives()) {
							writer.write("@attribute Adjectives[" + sentiment + "] numeric" + n);
						}
						if (Features.isUseUnigramAdverbs()) {
							writer.write("@attribute Adverb[" + sentiment + "] numeric" + n);
						}
					}
				} 
			}
		}

		// Top Words Features
		if (Features.isUseTopWords()) {
			if (Features.getTopWordsType()!=null && Features.getTopWordsTypeOfPos()!=null) {
				if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
					if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getTopWords().keySet()) {
							writer.write("@attribute Top_Words[" + sentiment + "] numeric" + n);
						}
					} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopNouns().keySet()) {
								writer.write("@attribute Top_Nouns[" + sentiment + "] numeric" + n);
							}
						}
						if (Features.isUseTopWordsVerbs()) {
							for (String sentiment : Parameters.getTopVerbs().keySet()) {
								writer.write("@attribute Top_Verbs[" + sentiment + "] numeric" + n);
							}
						}
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopAdjectives().keySet()) {
								writer.write("@attribute Top_Adjectives[" + sentiment + "] numeric" + n);
							}
						}
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopAdverbs().keySet()) {
								writer.write("@attribute Top_Adverbs[" + sentiment + "] numeric" + n);
							}
						}
					}
				} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
					if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getTopWords().keySet()) {
							for (String word : Parameters.getTopWords().get(sentiment)) {
								writer.write("@attribute existance[Word_" + sentiment + "_" + word + "] {true, false}" + n);
							}
						}
					} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopNouns().keySet()) {
								for (String word : Parameters.getTopNouns().get(sentiment)) {
									writer.write("@attribute existance[Noun_" + sentiment + "_" + word + "] {true, false}" + n);
								}
							}
						}
						if (Features.isUseTopWordsVerbs()) {
							for (String sentiment : Parameters.getTopVerbs().keySet()) {
								for (String word : Parameters.getTopVerbs().get(sentiment)) {
									writer.write("@attribute existance[Verb_" + sentiment + "_" + word + "] {true, false}" + n);
								}
							}
						}
						if (Features.isUseTopWordsAdjectives()) {
							for (String sentiment : Parameters.getTopAdjectives().keySet()) {
								for (String word : Parameters.getTopAdjectives().get(sentiment)) {
									writer.write("@attribute existance[Adjective_" + sentiment + "_" + word + "] {true, false}" + n);
								}
							}
						}
						if (Features.isUseTopWordsAdverbs()) {
							for (String sentiment : Parameters.getTopAdverbs().keySet()) {
								for (String word : Parameters.getTopAdverbs().get(sentiment)) {
									writer.write("@attribute existance[Adverb_" + sentiment + "_" + word + "] {true, false}" + n);
								}
							}
						}
					}
				} 
			}
		}

		// Pattern Features
		if (Features.isUsePatternFeatures()) {
			if (Features.getPatternFeaturesType()!=null) {
				if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
					for (String sentiment : Parameters.getSingleLengthPatterns().keySet()) {
						for (int i = 0; i < Parameters.getSingleLengthPatterns().get(sentiment).size(); i++) {
							writer.write("@attribute Pattern_" + sentiment + "[" + i + "] numeric" + n);
						}
					}
				} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
					for (String sentiment : Parameters.getMultiLengthPatterns().keySet()) {
						for (int i : Parameters.getMultiLengthPatterns().get(sentiment).keySet()) {
							writer.write("@attribute Patterns_" + sentiment + "[Length=" + i + "] numeric" + n);
						}
					}
				} 
			}
		}
		
		//-----------------------------------------------//
		// TODO add the remaining advanced features here //
		//-----------------------------------------------//
		
		
		
		// Advanced Pattern Features
		if (Features.isUseAdvancedPatternFeatures()) {
			if (Features.getAdvancedPatternFeaturesType()!=null) {
				if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
					for (String sentiment : Parameters.getSingleLengthAdvancedPatterns().keySet()) {
						for (int i = 0; i < Parameters.getSingleLengthAdvancedPatterns().get(sentiment).size(); i++) {
							writer.write("@attribute Adv_Pattern_" + sentiment + "[" + i + "] numeric" + n);
						}
					}
				} else if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
					for (String sentiment : Parameters.getMultiLengthAdvancedPatterns().keySet()) {
						for (int i : Parameters.getMultiLengthAdvancedPatterns().get(sentiment).keySet()) {
							writer.write("@attribute Adv_Patterns_" + sentiment + "[Length=" + i + "] numeric" + n);
						}
					}
				} 
			}
		}
		
		// Advanced Unigram Features
		if (Features.isUseAdvancedUnigramFeatures()) {
			
			for (String unigram : Parameters.getAdvancedUnigrams().keySet()) {
				writer.write("@attribute AdvUnigram[" + unigram + "] {true, false}" + n);
			}
			
		}
		
		
		
		
		// Add the class attribute
		writer.write("@attribute CLASS {");
		
		if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
			writer.write(Parameters.getClasses().get(0));
			for (int i = 1; i < Parameters.getClasses().size(); i++) {
				writer.write(", " + Parameters.getClasses().get(i));
			} 
		} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
			writer.write("POSITIVE, NEGATIVE, NEUTRAL");
		}
		writer.write("}");
		
		writer.write(n);
		
		writer.write(n);
		
		// ---------------------------//
		// Add the Data //
		// ---------------------------//

		writer.write("@data" + n);

		for (Tweet tweet : tweets) {
			
			// Sentiment Features
			if (Features.isUseSentimentFeatures()) {
				if (Features.isNumberOfPositiveWords()) {
					writer.write(tweet.getNumberOfPositiveWords() + t);
				}

				if (Features.isNumberOfNegativeWords()) {
					writer.write(tweet.getNumberOfNegativeWords() + t);
				}
				if (Features.isNumberOfHighlyEmoPositiveWords()) {
					writer.write(tweet.getNumberOfHighlyEmoPositiveWords() + t);
				}
				if (Features.isNumberOfHighlyEmoNegativeWords()) {
					writer.write(tweet.getNumberOfHighlyEmoNegativeWords() + t);
				}
				if (Features.isNumberOfCapitalizedPositiveWords()) {
					writer.write(tweet.getNumberOfCapitalizedPositiveWords() + t);
				}
				if (Features.isNumberOfCapitalizedNegativeWords()) {
					writer.write(tweet.getNumberOfCapitalizedNegativeWords() + t);
				}
				if (Features.isRatioOfEmotionalWords()) {
					writer.write(tweet.getRatioOfEmotionalWords() + t);
				}

				if (Features.isNumberOfPositiveEmoticons()) {
					writer.write(tweet.getNumberOfPositiveEmoticons() + t);
				}
				if (Features.isNumberOfNegativeEmoticons()) {
					writer.write(tweet.getNumberOfNegativeEmoticons() + t);
				}
				if (Features.isNumberOfNeutralEmoticons()) {
					writer.write(tweet.getNumberOfNeutralEmoticons() + t);
				}
				if (Features.isNumberOfJokingEmoticons()) {
					writer.write(tweet.getNumberOfJokingEmoticons() + t);
				}

				if (Features.isNumberOfPositiveSlangs()) {
					writer.write(tweet.getNumberOfPositiveSlangs() + t);
				}
				if (Features.isNumberOfNegativeSlangs()) {
					writer.write(tweet.getNumberOfNegativeSlangs() + t);
				}

				if (Features.isNumberOfPositiveHashtags()) {
					writer.write(tweet.getNumberOfPositiveHashtags() + t);
				}
				if (Features.isNumberOfNegativeHashtags()) {
					writer.write(tweet.getNumberOfNegativeHashtags() + t);
				}

				if (Features.isContrastWordsVsWords()) {
					writer.write(tweet.getContrastWordsVsWords() + t);
				}
				if (Features.isContrastWordsVsHashtags()) {
					writer.write(tweet.getContrastWordsVsHashtags() + t);
				}
				if (Features.isContrastWordsVsEmoticons()) {
					writer.write(tweet.getContrastWordsVsEmoticons() + t);
				}
				if (Features.isContrastHashtagsVsHashtags()) {
					writer.write(tweet.getContrastHashtagsVsHashtags() + t);
				}
				if (Features.isContrastHashtagsVsEmoticons()) {
					writer.write(tweet.getContrastHashtagsVsEmoticons() + t);
				}
			}

			// Punctuation Features
			if (Features.isUsePunctuationFeatures()) {
				if (Features.isNumberOfDots()) {
					writer.write(tweet.getNumberOfDots() + t);
				}
				if (Features.isNumberOfCommas()) {
					writer.write(tweet.getNumberOfCommas() + t);
				}
				if (Features.isNumberOfSemicolons()) {
					writer.write(tweet.getNumberOfSemicolons() + t);
				}
				if (Features.isNumberOfExclamationMarks()) {
					writer.write(tweet.getNumberOfExclamationMarks() + t);
				}
				if (Features.isNumberOfQuestionMarks()) {
					writer.write(tweet.getNumberOfQuestionMarks() + t);
				}

				if (Features.isNumberOfParentheses()) {
					writer.write(tweet.getNumberOfParentheses() + t);
				}
				if (Features.isNumberOfBrackets()) {
					writer.write(tweet.getNumberOfBrackets() + t);
				}
				if (Features.isNumberOfBraces()) {
					writer.write(tweet.getNumberOfBraces() + t);
				}

				if (Features.isNumberOfApostrophes()) {
					writer.write(tweet.getNumberOfApostrophes() + t);
				}
				if (Features.isNumberOfQuotationMarks()) {
					writer.write(tweet.getNumberOfQuotationMarks() + t);
				}

				if (Features.isTotalNumberOfCharacters()) {
					writer.write(tweet.getTotalNumberOfCharacters() + t);
				}
				if (Features.isTotalNumberOfWords()) {
					writer.write(tweet.getTotalNumberOfWords() + t);
				}
				if (Features.isTotalNumberOfSentences()) {
					writer.write(tweet.getTotalNumberOfSentences() + t);
				}
				if (Features.isAverageNumberOfCharactersPerSentence()) {
					writer.write(tweet.getAverageNumberOfCharactersPerSentence() + t);
				}
				if (Features.isAverageNumberOfWordsPerSentence()) {
					writer.write(tweet.getAverageNumberOfWordsPerSentence() + t);
				}
			}

			// Stylistic Features
			if (Features.isUseStylisticFeatures()) {

				if (Features.isNumberOfNouns()) {
					writer.write(tweet.getNumberOfNouns() + t);
				}
				if (Features.isRatioOfNouns()) {
					writer.write(tweet.getRatioOfNouns() + t);
				}
				if (Features.isNumberOfVerbs()) {
					writer.write(tweet.getNumberOfVerbs() + t);
				}
				if (Features.isRatioOfVerbs()) {
					writer.write(tweet.getRatioOfVerbs() + t);
				}
				if (Features.isNumberOfAdjectives()) {
					writer.write(tweet.getNumberOfAdjectives() + t);
				}
				if (Features.isRatioOfAdjectives()) {
					writer.write(tweet.getRatioOfAdjectives() + t);
				}
				if (Features.isNumberOfAdverbs()) {
					writer.write(tweet.getNumberOfAdverbs() + t);
				}
				if (Features.isRatioOfAdverbs()) {
					writer.write(tweet.getRatioOfAdverbs() + t);
				}

				if (Features.isUseOfSymbols()) {
					writer.write(tweet.isUseOfSymbols() + t);
				}
				if (Features.isUseOfComparativeForm()) {
					writer.write(tweet.isUseOfComparativeForm() + t);
				}
				if (Features.isUseOfSuperlativeForm()) {
					writer.write(tweet.isUseOfSuperlativeForm() + t);
				}
				if (Features.isUseOfProperNouns()) {
					writer.write(tweet.isUseOfProperNouns() + t);
				}

				if (Features.isTotalNumberOfParticles()) {
					writer.write(tweet.getTotalNumberOfParticles() + t);
				}
				if (Features.isAverageNumberOfParticles()) {
					writer.write(tweet.getAverageNumberOfParticles() + t);
				}
				if (Features.isTotalNumberOfInterjections()) {
					writer.write(tweet.getTotalNumberOfInterjections() + t);
				}
				if (Features.isAverageNumberOfInterjections()) {
					writer.write(tweet.getAverageNumberOfInterjections() + t);
				}
				if (Features.isTotalNumberOfPronouns()) {
					writer.write(tweet.getTotalNumberOfPronouns() + t);
				}
				if (Features.isAverageNumberOfPronouns()) {
					writer.write(tweet.getAverageNumberOfPronouns() + t);
				}
				if (Features.isTotalNumberOfPronounsGroup1()) {
					writer.write(tweet.getTotalNumberOfPronounsGroup1() + t);
				}
				if (Features.isAverageNumberOfPronounsGroup1()) {
					writer.write(tweet.getAverageNumberOfPronounsGroup1() + t);
				}
				if (Features.isTotalNumberOfPronounsGroup2()) {
					writer.write(tweet.getTotalNumberOfPronounsGroup2() + t);
				}
				if (Features.isAverageNumberOfPronounsGroup2()) {
					writer.write(tweet.getAverageNumberOfPronounsGroup2() + t);
				}
				if (Features.isUseOfNegation()) {
					writer.write(tweet.isUseOfNegation() + t);
				}
				if (Features.isUseOfUncommonWords()) {
					writer.write(tweet.isUseOfUncommonWords() + t);
				}
				if (Features.isNumberOfUncommonWords()) {
					writer.write(tweet.getNumberOfUncommonWords() + t);
				}
			}

			// Semantic Features
			if (Features.isUseSemanticFeatures()) {
				if (Features.isUseOfOpinionWords()) {
					writer.write(tweet.isUseOfOpinionWords() + t);
				}
				if (Features.isUseOfHighlySentimentalWords()) {
					writer.write(tweet.isUseOfHighlySentimentalWords() + t);
				}
				if (Features.isUseOfUncertaintyWords()) {
					writer.write(tweet.isUseOfUncertaintyWords() + t);
				}
				if (Features.isUseOfActiveForm()) {
					writer.write(tweet.isUseOfActiveForm() + t);
				}
				if (Features.isUseOfPassiveForm()) {
					writer.write(tweet.isUseOfPassiveForm() + t);
				}
			}

			// Unigram Features
			if (Features.isUseUnigramFeatures()) {
				if (Features.getUnigramTypeOfPos()!=null) {
					if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getClasses()) {
							if (tweet.getUnigramCountPerClass().keySet().contains(sentiment)) {
								writer.write(tweet.getUnigramCountPerClass().get(sentiment) + t);
							} else {
								writer.write("0" + t);
							}
						}
					} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						for (String sentiment : Parameters.getClasses()) {
							if (Features.isUseUnigramNouns()) {
								if (tweet.getNounCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getNounCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramVerbs()) {
								if (tweet.getVerbCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getVerbCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramAdjectives()) {
								if (tweet.getAdjectiveCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getAdjectiveCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramAdverbs()) {
								if (tweet.getAdverbCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getAdverbCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
						}
					} 
				}
			}

			// Top Words Features
			if (Features.isUseTopWords()) {
				if (Features.getTopWordsType()!=null && Features.getTopWordsTypeOfPos()!=null) {
					if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
						if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
							if (tweet.getTopWordSummed() == null || tweet.getTopWordSummed().length != Parameters.getTopWords().keySet().size()) {
								for (int i = 0; i < Parameters.getTopWords().keySet().size(); i++) {
									writer.write("0" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordSummed().length; i++) {
									writer.write(tweet.getTopWordSummed()[i] + t);
								}
							}
						} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
							int size = 0;
							if (Features.isUseTopWordsNouns()) {
								size = size + Parameters.getTopNouns().keySet().size();
							}
							if (Features.isUseTopWordsVerbs()) {
								size = size + Parameters.getTopVerbs().keySet().size();
							}
							if (Features.isUseTopWordsAdjectives()) {
								size = size + Parameters.getTopAdjectives().keySet().size();
							}
							if (Features.isUseTopWordsAdverbs()) {
								size = size + Parameters.getTopAdverbs().keySet().size();
							}

						}
					} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
						if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {

							int size = 0;
							if (Features.isUseTopWordsNouns()) {
								for (String sentiment : Parameters.getTopNouns().keySet()) {
									size = size + Parameters.getTopNouns().get(sentiment).size();
								}

							}
							if (Features.isUseTopWordsVerbs()) {
								for (String sentiment : Parameters.getTopVerbs().keySet()) {
									size = size + Parameters.getTopVerbs().get(sentiment).size();
								}
							}
							if (Features.isUseTopWordsAdjectives()) {
								for (String sentiment : Parameters.getTopAdjectives().keySet()) {
									size = size + Parameters.getTopAdjectives().get(sentiment).size();
								}
							}
							if (Features.isUseTopWordsAdverbs()) {
								for (String sentiment : Parameters.getTopAdverbs().keySet()) {
									size = size + Parameters.getTopAdverbs().get(sentiment).size();
								}
							}

							if (tweet.getTopWordsSeparatelyBoolean() == null || tweet.getTopWordsSeparatelyBoolean().length != size) {
								for (int i = 0; i < size; i++) {
									writer.write("false" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordsSeparatelyBoolean().length; i++) {
									writer.write(tweet.getTopWordsSeparatelyBoolean()[i] + t);
								}
							}
						} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
							int size = 0;
							for (String sentiment : Parameters.getTopWords().keySet()) {
								size = size + Parameters.getTopWords().get(sentiment).size();
							}
							if (tweet.getTopWordsSeparatelyBoolean() == null
									|| tweet.getTopWordsSeparatelyBoolean().length != size) {
								for (int i = 0; i < size; i++) {
									writer.write("false" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordsSeparatelyBoolean().length; i++) {
									writer.write(tweet.getTopWordsSeparatelyBoolean()[i] + t);
								}
							}

						}
					} 
				}
			}

			// Pattern Features
			if (Features.isUsePatternFeatures()) {
				if (Features.getPatternFeaturesType()!=null) {
					if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
						int size = 0;
						for (String sentiment : Parameters.getSingleLengthPatterns().keySet()) {
							size = size + Parameters.getSingleLengthPatterns().get(sentiment).size();
						}
						if (tweet.getPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getPatternScores().length; i++) {
								writer.write(tweet.getPatternScores()[i] + t);
							}
						}
					} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
						int size = 0;
						for (String sentiment : Parameters.getMultiLengthPatterns().keySet()) {
							size = size + Parameters.getMultiLengthPatterns().get(sentiment).keySet().size();
						}

						if (tweet.getPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getPatternScores().length; i++) {
								writer.write(tweet.getPatternScores()[i] + t);
							}
						}
					} 
				}
			}
			
			// Advanced Pattern Features
			if (Features.isUseAdvancedPatternFeatures()) {
				if (Features.getAdvancedPatternFeaturesType()!=null) {
					if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
						int size = 0;
						for (String sentiment : Parameters.getSingleLengthAdvancedPatterns().keySet()) {
							size = size + Parameters.getSingleLengthAdvancedPatterns().get(sentiment).size();
						}
						if (tweet.getAdvancedPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getAdvancedPatternScores().length; i++) {
								writer.write(tweet.getAdvancedPatternScores()[i] + t);
							}
						}
					} else if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
						int size = 0;
						for (String sentiment : Parameters.getMultiLengthAdvancedPatterns().keySet()) {
							size = size + Parameters.getMultiLengthAdvancedPatterns().get(sentiment).keySet().size();
						}

						if (tweet.getAdvancedPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getAdvancedPatternScores().length; i++) {
								writer.write(tweet.getAdvancedPatternScores()[i] + t);
							}
						}
					} 
				}
			}
			
			// Advanced Unigram Features
			if (Features.isUseAdvancedUnigramFeatures()) {
				for (boolean b : tweet.getAdvUnigramFeatures()) {
					writer.write(b + t);
				}
			}
			
			
			// TODO consider the case where the set of non-annotated tweets is taken
			writer.write(tweet.getTweetClass() + n);
		}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	public static void saveArffFileNumeric(String file, ArrayList<Tweet> tweets) {
		String t = ",";
		String n = "\n";
		
		File exportedFile = new File(file);
		File exportedFileDirectory = new File(exportedFile.getParent());

		boolean folderCreated = false;
		if (!exportedFileDirectory.exists()) {
			try {
				exportedFileDirectory.mkdir();
				folderCreated = true;
			} catch (SecurityException se) {
				AlertBox.display("Error", "The folder you want to save in cannot be accessed!", "OK");
			}
		} else {
			folderCreated = true;
		}

		BufferedWriter writer = null;

		if (folderCreated) {

			try {
				if (!exportedFile.exists()) {
					exportedFile.createNewFile();
				}
				
		writer = new BufferedWriter(new FileWriter(exportedFile));
		
		// ---------------------------//
		//       Add the titles       //
		// ---------------------------//
		writer.write("@relation Multi_Class_Sentiment_Analysis" + n + n);
		// Sentiment Features
		if (Features.isUseSentimentFeatures()) {
			if (Features.isNumberOfPositiveWords()) {
				writer.write("@attribute Number_Of_Positive_Words numeric" + n);
			}

			if (Features.isNumberOfNegativeWords()) {
				writer.write("@attribute Number_Of_Negative_Words numeric" + n);
			}
			if (Features.isNumberOfHighlyEmoPositiveWords()) {
				writer.write("@attribute Number_Of_Highly_Emo_Positive_Words numeric" + n);
			}
			if (Features.isNumberOfHighlyEmoNegativeWords()) {
				writer.write("@attribute Number_Of_Highly_Emo_Negative_Words numeric" + n);
			}
			if (Features.isNumberOfCapitalizedPositiveWords()) {
				writer.write("@attribute Number_Of_Capitalized_Positive_Words numeric" + n);
			}
			if (Features.isNumberOfCapitalizedNegativeWords()) {
				writer.write("@attribute Number_Of_Capitalized_Negative_Words numeric" + n);
			}
			if (Features.isRatioOfEmotionalWords()) {
				writer.write("@attribute Ratio_Of_Emotional_Words numeric" + n);
			}

			if (Features.isNumberOfPositiveEmoticons()) {
				writer.write("@attribute Number_Of_Positive_Emoticons numeric" + n);
			}
			if (Features.isNumberOfNegativeEmoticons()) {
				writer.write("@attribute Number_Of_Negative_Emoticons numeric" + n);
			}
			if (Features.isNumberOfNeutralEmoticons()) {
				writer.write("@attribute Number_Of_Neutral_Emoticons numeric" + n);
			}
			if (Features.isNumberOfJokingEmoticons()) {
				writer.write("@attribute Number_Of_Joking_Emoticons numeric" + n);
			}

			if (Features.isNumberOfPositiveSlangs()) {
				writer.write("@attribute Number_Of_Positive_Slangs numeric" + n);
			}
			if (Features.isNumberOfNegativeSlangs()) {
				writer.write("@attribute Number_Of_Negative_Slangs numeric" + n);
			}

			if (Features.isNumberOfPositiveHashtags()) {
				writer.write("@attribute NumberOfPositiveHashtags numeric" + n);
			}
			if (Features.isNumberOfNegativeHashtags()) {
				writer.write("@attribute NumberOfNegativeHashtags numeric" + n);
			}

			if (Features.isContrastWordsVsWords()) {
				writer.write("@attribute Contrast_Words_Vs_Words {true, false}" + n);
			}
			if (Features.isContrastWordsVsHashtags()) {
				writer.write("@attribute Contrast_Words_Vs_Hashtags {true, false}" + n);
			}
			if (Features.isContrastWordsVsEmoticons()) {
				writer.write("@attribute Contrast_Words_Vs_Emoticons {true, false}" + n);
			}
			if (Features.isContrastHashtagsVsHashtags()) {
				writer.write("@attribute Contrast_Hashtags_Vs_Hashtags {true, false}" + n);
			}
			if (Features.isContrastHashtagsVsEmoticons()) {
				writer.write("@attribute Contrast_Hashtags_Vs_Emoticons {true, false}" + n);
			}
		}

		// Punctuation Features
		if (Features.isUsePunctuationFeatures()) {
			if (Features.isNumberOfDots()) {
				writer.write("@attribute Number_Of_Dots numeric" + n);
			}
			if (Features.isNumberOfCommas()) {
				writer.write("@attribute Number_Of_Commas numeric" + n);
			}
			if (Features.isNumberOfSemicolons()) {
				writer.write("@attribute Number_Of_Semicolons numeric" + n);
			}
			if (Features.isNumberOfExclamationMarks()) {
				writer.write("@attribute Number_Of_Exclamation_Marks numeric" + n);
			}
			if (Features.isNumberOfQuestionMarks()) {
				writer.write("@attribute Number_Of_Question_Marks numeric" + n);
			}

			if (Features.isNumberOfParentheses()) {
				writer.write("@attribute Number_Of_Parentheses numeric" + n);
			}
			if (Features.isNumberOfBrackets()) {
				writer.write("@attribute Number_Of_Brackets numeric" + n);
			}
			if (Features.isNumberOfBraces()) {
				writer.write("@attribute Number_Of_Braces numeric" + n);
			}

			if (Features.isNumberOfApostrophes()) {
				writer.write("@attribute Number_Of_Apostrophes numeric" + n);
			}
			if (Features.isNumberOfQuotationMarks()) {
				writer.write("@attribute Number_Of_Quotation_Marks numeric" + n);
			}

			if (Features.isTotalNumberOfCharacters()) {
				writer.write("@attribute Total_Number_Of_Characters numeric" + n);
			}
			if (Features.isTotalNumberOfWords()) {
				writer.write("@attribute Total_Number_Of_Words numeric" + n);
			}
			if (Features.isTotalNumberOfSentences()) {
				writer.write("@attribute Total_Number_Of_Sentences numeric" + n);
			}
			if (Features.isAverageNumberOfCharactersPerSentence()) {
				writer.write("@attribute Average_Number_Of_Characters_Per_Sentence numeric" + n);
			}
			if (Features.isAverageNumberOfWordsPerSentence()) {
				writer.write("@attribute Average_Number_Of_Words_Per_Sentence numeric" + n);
			}
		}

		// Stylistic Features
		if (Features.isUseStylisticFeatures()) {

			if (Features.isNumberOfNouns()) {
				writer.write("@attribute Number_Of_Nouns numeric" + n);
			}
			if (Features.isRatioOfNouns()) {
				writer.write("@attribute Ratio_Of_Nouns numeric" + n);
			}
			if (Features.isNumberOfVerbs()) {
				writer.write("@attribute Number_Of_Verbs numeric" + n);
			}
			if (Features.isRatioOfVerbs()) {
				writer.write("@attribute Ratio_Of_Verbs numeric" + n);
			}
			if (Features.isNumberOfAdjectives()) {
				writer.write("@attribute Number_Of_Adjectives numeric" + n);
			}
			if (Features.isRatioOfAdjectives()) {
				writer.write("@attribute Ratio_Of_Adjectives numeric" + n);
			}
			if (Features.isNumberOfAdverbs()) {
				writer.write("@attribute Number_Of_Adverbs numeric" + n);
			}
			if (Features.isRatioOfAdverbs()) {
				writer.write("@attribute Ratio_Of_Adverbs numeric" + n);
			}

			if (Features.isUseOfSymbols()) {
				writer.write("@attribute Use_Of_Symbols {true, false}" + n);
			}
			if (Features.isUseOfComparativeForm()) {
				writer.write("@attribute Use_Of_Comparative_Form {true, false}" + n);
			}
			if (Features.isUseOfSuperlativeForm()) {
				writer.write("@attribute Use_Of_Superlative_Form {true, false}" + n);
			}
			if (Features.isUseOfProperNouns()) {
				writer.write("@attribute Use_Of_Proper_Nouns {true, false}" + n);
			}

			if (Features.isTotalNumberOfParticles()) {
				writer.write("@attribute Total_Number_Of_Particles numeric" + n);
			}
			if (Features.isAverageNumberOfParticles()) {
				writer.write("@attribute Average_Number_Of_Particles numeric" + n);
			}
			if (Features.isTotalNumberOfInterjections()) {
				writer.write("@attribute Total_Number_Of_Interjections numeric" + n);
			}
			if (Features.isAverageNumberOfInterjections()) {
				writer.write("@attribute Average_Number_Of_Interjections numeric" + n);
			}
			if (Features.isTotalNumberOfPronouns()) {
				writer.write("@attribute Total_Number_Of_Pronouns numeric" + n);
			}
			if (Features.isAverageNumberOfPronouns()) {
				writer.write("@attribute Average_Number_Of_Pronouns numeric" + n);
			}
			if (Features.isTotalNumberOfPronounsGroup1()) {
				writer.write("@attribute Total_Number_Of_Pronouns_Group_1 numeric" + n);
			}
			if (Features.isAverageNumberOfPronounsGroup1()) {
				writer.write("@attribute Average_Number_Of_Pronouns_Group_1 numeric" + n);
			}
			if (Features.isTotalNumberOfPronounsGroup2()) {
				writer.write("@attribute Total_Number_Of_Pronouns_Group_2 numeric" + n);
			}
			if (Features.isAverageNumberOfPronounsGroup2()) {
				writer.write("@attribute Average_Number_Of_Pronouns_Group_2 numeric" + n);
			}
			if (Features.isUseOfNegation()) {
				writer.write("@attribute Use_Of_Negation {true, false}" + n);
			}
			if (Features.isUseOfUncommonWords()) {
				writer.write("@attribute Use_Of_Uncommon_Words {true, false}" + n);
			}
			if (Features.isNumberOfUncommonWords()) {
				writer.write("@attribute Number_Of_Uncommon_Words numeric" + n);
			}
		}

		// Semantic Features
		if (Features.isUseSemanticFeatures()) {
			if (Features.isUseOfOpinionWords()) {
				writer.write("@attribute Use_Of_Opinion_Words {true, false}" + n);
			}
			if (Features.isUseOfHighlySentimentalWords()) {
				writer.write("@attribute Use_Of_Highly_Sentimental_Words {true, false}" + n);
			}
			if (Features.isUseOfUncertaintyWords()) {
				writer.write("@attribute Use_Of_Uncertainty_Words {true, false}" + n);
			}
			if (Features.isUseOfActiveForm()) {
				writer.write("@attribute Use_Of_Active_Form {true, false}" + n);
			}
			if (Features.isUseOfPassiveForm()) {
				writer.write("@attribute Use_Of_Passive_Form {true, false}" + n);
			}
		}
		
		// Unigram Features
		if (Features.isUseUnigramFeatures()) {
			if (Features.getUnigramTypeOfPos()!=null) {
				if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
					for (String sentiment : Parameters.getClasses()) {
						writer.write("@attribute Unigrams[" + sentiment + "] numeric" + n);
					}
				} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
					for (String sentiment : Parameters.getClasses()) {
						if (Features.isUseUnigramNouns()) {
							writer.write("@attribute Nouns[" + sentiment + "] numeric" + n);
						}
						if (Features.isUseUnigramVerbs()) {
							writer.write("@attribute Verbs[" + sentiment + "] numeric" + n);
						}
						if (Features.isUseUnigramAdjectives()) {
							writer.write("@attribute Adjectives[" + sentiment + "] numeric" + n);
						}
						if (Features.isUseUnigramAdverbs()) {
							writer.write("@attribute Adverb[" + sentiment + "] numeric" + n);
						}
					}
				} 
			}
		}

		// Top Words Features
		if (Features.isUseTopWords()) {
			if (Features.getTopWordsType()!=null && Features.getTopWordsTypeOfPos()!=null) {
				if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
					if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getTopWords().keySet()) {
							writer.write("@attribute Top_Words[" + sentiment + "] numeric" + n);
						}
					} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopNouns().keySet()) {
								writer.write("@attribute Top_Nouns[" + sentiment + "] numeric" + n);
							}
						}
						if (Features.isUseTopWordsVerbs()) {
							for (String sentiment : Parameters.getTopVerbs().keySet()) {
								writer.write("@attribute Top_Verbs[" + sentiment + "] numeric" + n);
							}
						}
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopAdjectives().keySet()) {
								writer.write("@attribute Top_Adjectives[" + sentiment + "] numeric" + n);
							}
						}
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopAdverbs().keySet()) {
								writer.write("@attribute Top_Adverbs[" + sentiment + "] numeric" + n);
							}
						}
					}
				} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
					if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getTopWords().keySet()) {
							for (String word : Parameters.getTopWords().get(sentiment)) {
								writer.write("@attribute existance[Word_" + sentiment + "_" + word + "] {true, false}" + n);
							}
						}
					} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopNouns().keySet()) {
								for (String word : Parameters.getTopNouns().get(sentiment)) {
									writer.write("@attribute existance[Noun_" + sentiment + "_" + word + "] {true, false}" + n);
								}
							}
						}
						if (Features.isUseTopWordsVerbs()) {
							for (String sentiment : Parameters.getTopVerbs().keySet()) {
								for (String word : Parameters.getTopVerbs().get(sentiment)) {
									writer.write("@attribute existance[Verb_" + sentiment + "_" + word + "] {true, false}" + n);
								}
							}
						}
						if (Features.isUseTopWordsAdjectives()) {
							for (String sentiment : Parameters.getTopAdjectives().keySet()) {
								for (String word : Parameters.getTopAdjectives().get(sentiment)) {
									writer.write("@attribute existance[Adjective_" + sentiment + "_" + word + "] {true, false}" + n);
								}
							}
						}
						if (Features.isUseTopWordsAdverbs()) {
							for (String sentiment : Parameters.getTopAdverbs().keySet()) {
								for (String word : Parameters.getTopAdverbs().get(sentiment)) {
									writer.write("@attribute existance[Adverb_" + sentiment + "_" + word + "] {true, false}" + n);
								}
							}
						}
					}
				} 
			}
		}

		// Pattern Features
		if (Features.isUsePatternFeatures()) {
			if (Features.getPatternFeaturesType()!=null) {
				if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
					for (String sentiment : Parameters.getSingleLengthPatternsNumeric().keySet()) {
						for (int i = 0; i < Parameters.getSingleLengthPatternsNumeric().get(sentiment).size(); i++) {
							writer.write("@attribute Pattern_" + sentiment + "[" + i + "] numeric" + n);
						}
					}
				} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
					for (String sentiment : Parameters.getMultiLengthPatternsNumeric().keySet()) {
						for (int i : Parameters.getMultiLengthPatternsNumeric().get(sentiment).keySet()) {
							writer.write("@attribute Patterns_" + sentiment + "[Length=" + i + "] numeric" + n);
						}
					}
				} 
			}
		}
		
		//-----------------------------------------------//
		// TODO add the remaining advanced features here //
		//-----------------------------------------------//
		
		
		
		// Advanced Pattern Features
		if (Features.isUseAdvancedPatternFeatures()) {
			if (Features.getAdvancedPatternFeaturesType()!=null) {
				if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
					for (String sentiment : Parameters.getSingleLengthAdvancedPatternsNumeric().keySet()) {
						for (int i = 0; i < Parameters.getSingleLengthAdvancedPatternsNumeric().get(sentiment).size(); i++) {
							writer.write("@attribute Adv_Pattern_" + sentiment + "[" + i + "] numeric" + n);
						}
					}
				} else if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
					for (String sentiment : Parameters.getMultiLengthAdvancedPatternsNumeric().keySet()) {
						for (int i : Parameters.getMultiLengthAdvancedPatternsNumeric().get(sentiment).keySet()) {
							writer.write("@attribute Adv_Patterns_" + sentiment + "[Length=" + i + "] numeric" + n);
						}
					}
				} 
			}
		}
		
		// Advanced Unigram Features
		if (Features.isUseAdvancedUnigramFeatures()) {
			
			for (String unigram : Parameters.getAdvancedUnigrams().keySet()) {
				writer.write("@attribute AdvUnigram[" + unigram + "] {true, false}" + n);
			}
			
		}
		
		
		
		
		// Add the class attribute
		writer.write("@attribute CLASS {");
		
		if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
			writer.write(Parameters.getClasses().get(0));
			for (int i = 1; i < Parameters.getClasses().size(); i++) {
				writer.write(", " + Parameters.getClasses().get(i));
			} 
		} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
			writer.write("POSITIVE, NEGATIVE, NEUTRAL");
		}
		writer.write("}");
		
		writer.write(n);
		
		writer.write(n);
		
		// ---------------------------//
		// Add the Data //
		// ---------------------------//

		writer.write("@data" + n);

		for (Tweet tweet : tweets) {
			
			// Sentiment Features
			if (Features.isUseSentimentFeatures()) {
				if (Features.isNumberOfPositiveWords()) {
					writer.write(tweet.getNumberOfPositiveWords() + t);
				}

				if (Features.isNumberOfNegativeWords()) {
					writer.write(tweet.getNumberOfNegativeWords() + t);
				}
				if (Features.isNumberOfHighlyEmoPositiveWords()) {
					writer.write(tweet.getNumberOfHighlyEmoPositiveWords() + t);
				}
				if (Features.isNumberOfHighlyEmoNegativeWords()) {
					writer.write(tweet.getNumberOfHighlyEmoNegativeWords() + t);
				}
				if (Features.isNumberOfCapitalizedPositiveWords()) {
					writer.write(tweet.getNumberOfCapitalizedPositiveWords() + t);
				}
				if (Features.isNumberOfCapitalizedNegativeWords()) {
					writer.write(tweet.getNumberOfCapitalizedNegativeWords() + t);
				}
				if (Features.isRatioOfEmotionalWords()) {
					writer.write(tweet.getRatioOfEmotionalWords() + t);
				}

				if (Features.isNumberOfPositiveEmoticons()) {
					writer.write(tweet.getNumberOfPositiveEmoticons() + t);
				}
				if (Features.isNumberOfNegativeEmoticons()) {
					writer.write(tweet.getNumberOfNegativeEmoticons() + t);
				}
				if (Features.isNumberOfNeutralEmoticons()) {
					writer.write(tweet.getNumberOfNeutralEmoticons() + t);
				}
				if (Features.isNumberOfJokingEmoticons()) {
					writer.write(tweet.getNumberOfJokingEmoticons() + t);
				}

				if (Features.isNumberOfPositiveSlangs()) {
					writer.write(tweet.getNumberOfPositiveSlangs() + t);
				}
				if (Features.isNumberOfNegativeSlangs()) {
					writer.write(tweet.getNumberOfNegativeSlangs() + t);
				}

				if (Features.isNumberOfPositiveHashtags()) {
					writer.write(tweet.getNumberOfPositiveHashtags() + t);
				}
				if (Features.isNumberOfNegativeHashtags()) {
					writer.write(tweet.getNumberOfNegativeHashtags() + t);
				}

				if (Features.isContrastWordsVsWords()) {
					writer.write(tweet.getContrastWordsVsWords() + t);
				}
				if (Features.isContrastWordsVsHashtags()) {
					writer.write(tweet.getContrastWordsVsHashtags() + t);
				}
				if (Features.isContrastWordsVsEmoticons()) {
					writer.write(tweet.getContrastWordsVsEmoticons() + t);
				}
				if (Features.isContrastHashtagsVsHashtags()) {
					writer.write(tweet.getContrastHashtagsVsHashtags() + t);
				}
				if (Features.isContrastHashtagsVsEmoticons()) {
					writer.write(tweet.getContrastHashtagsVsEmoticons() + t);
				}
			}

			// Punctuation Features
			if (Features.isUsePunctuationFeatures()) {
				if (Features.isNumberOfDots()) {
					writer.write(tweet.getNumberOfDots() + t);
				}
				if (Features.isNumberOfCommas()) {
					writer.write(tweet.getNumberOfCommas() + t);
				}
				if (Features.isNumberOfSemicolons()) {
					writer.write(tweet.getNumberOfSemicolons() + t);
				}
				if (Features.isNumberOfExclamationMarks()) {
					writer.write(tweet.getNumberOfExclamationMarks() + t);
				}
				if (Features.isNumberOfQuestionMarks()) {
					writer.write(tweet.getNumberOfQuestionMarks() + t);
				}

				if (Features.isNumberOfParentheses()) {
					writer.write(tweet.getNumberOfParentheses() + t);
				}
				if (Features.isNumberOfBrackets()) {
					writer.write(tweet.getNumberOfBrackets() + t);
				}
				if (Features.isNumberOfBraces()) {
					writer.write(tweet.getNumberOfBraces() + t);
				}

				if (Features.isNumberOfApostrophes()) {
					writer.write(tweet.getNumberOfApostrophes() + t);
				}
				if (Features.isNumberOfQuotationMarks()) {
					writer.write(tweet.getNumberOfQuotationMarks() + t);
				}

				if (Features.isTotalNumberOfCharacters()) {
					writer.write(tweet.getTotalNumberOfCharacters() + t);
				}
				if (Features.isTotalNumberOfWords()) {
					writer.write(tweet.getTotalNumberOfWords() + t);
				}
				if (Features.isTotalNumberOfSentences()) {
					writer.write(tweet.getTotalNumberOfSentences() + t);
				}
				if (Features.isAverageNumberOfCharactersPerSentence()) {
					writer.write(tweet.getAverageNumberOfCharactersPerSentence() + t);
				}
				if (Features.isAverageNumberOfWordsPerSentence()) {
					writer.write(tweet.getAverageNumberOfWordsPerSentence() + t);
				}
			}

			// Stylistic Features
			if (Features.isUseStylisticFeatures()) {

				if (Features.isNumberOfNouns()) {
					writer.write(tweet.getNumberOfNouns() + t);
				}
				if (Features.isRatioOfNouns()) {
					writer.write(tweet.getRatioOfNouns() + t);
				}
				if (Features.isNumberOfVerbs()) {
					writer.write(tweet.getNumberOfVerbs() + t);
				}
				if (Features.isRatioOfVerbs()) {
					writer.write(tweet.getRatioOfVerbs() + t);
				}
				if (Features.isNumberOfAdjectives()) {
					writer.write(tweet.getNumberOfAdjectives() + t);
				}
				if (Features.isRatioOfAdjectives()) {
					writer.write(tweet.getRatioOfAdjectives() + t);
				}
				if (Features.isNumberOfAdverbs()) {
					writer.write(tweet.getNumberOfAdverbs() + t);
				}
				if (Features.isRatioOfAdverbs()) {
					writer.write(tweet.getRatioOfAdverbs() + t);
				}

				if (Features.isUseOfSymbols()) {
					writer.write(tweet.isUseOfSymbols() + t);
				}
				if (Features.isUseOfComparativeForm()) {
					writer.write(tweet.isUseOfComparativeForm() + t);
				}
				if (Features.isUseOfSuperlativeForm()) {
					writer.write(tweet.isUseOfSuperlativeForm() + t);
				}
				if (Features.isUseOfProperNouns()) {
					writer.write(tweet.isUseOfProperNouns() + t);
				}

				if (Features.isTotalNumberOfParticles()) {
					writer.write(tweet.getTotalNumberOfParticles() + t);
				}
				if (Features.isAverageNumberOfParticles()) {
					writer.write(tweet.getAverageNumberOfParticles() + t);
				}
				if (Features.isTotalNumberOfInterjections()) {
					writer.write(tweet.getTotalNumberOfInterjections() + t);
				}
				if (Features.isAverageNumberOfInterjections()) {
					writer.write(tweet.getAverageNumberOfInterjections() + t);
				}
				if (Features.isTotalNumberOfPronouns()) {
					writer.write(tweet.getTotalNumberOfPronouns() + t);
				}
				if (Features.isAverageNumberOfPronouns()) {
					writer.write(tweet.getAverageNumberOfPronouns() + t);
				}
				if (Features.isTotalNumberOfPronounsGroup1()) {
					writer.write(tweet.getTotalNumberOfPronounsGroup1() + t);
				}
				if (Features.isAverageNumberOfPronounsGroup1()) {
					writer.write(tweet.getAverageNumberOfPronounsGroup1() + t);
				}
				if (Features.isTotalNumberOfPronounsGroup2()) {
					writer.write(tweet.getTotalNumberOfPronounsGroup2() + t);
				}
				if (Features.isAverageNumberOfPronounsGroup2()) {
					writer.write(tweet.getAverageNumberOfPronounsGroup2() + t);
				}
				if (Features.isUseOfNegation()) {
					writer.write(tweet.isUseOfNegation() + t);
				}
				if (Features.isUseOfUncommonWords()) {
					writer.write(tweet.isUseOfUncommonWords() + t);
				}
				if (Features.isNumberOfUncommonWords()) {
					writer.write(tweet.getNumberOfUncommonWords() + t);
				}
			}

			// Semantic Features
			if (Features.isUseSemanticFeatures()) {
				if (Features.isUseOfOpinionWords()) {
					writer.write(tweet.isUseOfOpinionWords() + t);
				}
				if (Features.isUseOfHighlySentimentalWords()) {
					writer.write(tweet.isUseOfHighlySentimentalWords() + t);
				}
				if (Features.isUseOfUncertaintyWords()) {
					writer.write(tweet.isUseOfUncertaintyWords() + t);
				}
				if (Features.isUseOfActiveForm()) {
					writer.write(tweet.isUseOfActiveForm() + t);
				}
				if (Features.isUseOfPassiveForm()) {
					writer.write(tweet.isUseOfPassiveForm() + t);
				}
			}

			// Unigram Features
			if (Features.isUseUnigramFeatures()) {
				if (Features.getUnigramTypeOfPos()!=null) {
					if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getClasses()) {
							if (tweet.getUnigramCountPerClass().keySet().contains(sentiment)) {
								writer.write(tweet.getUnigramCountPerClass().get(sentiment) + t);
							} else {
								writer.write("0" + t);
							}
						}
					} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						for (String sentiment : Parameters.getClasses()) {
							if (Features.isUseUnigramNouns()) {
								if (tweet.getNounCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getNounCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramVerbs()) {
								if (tweet.getVerbCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getVerbCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramAdjectives()) {
								if (tweet.getAdjectiveCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getAdjectiveCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramAdverbs()) {
								if (tweet.getAdverbCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getAdverbCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
						}
					} 
				}
			}

			// Top Words Features
			if (Features.isUseTopWords()) {
				if (Features.getTopWordsType()!=null && Features.getTopWordsTypeOfPos()!=null) {
					if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
						if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
							if (tweet.getTopWordSummed() == null || tweet.getTopWordSummed().length != Parameters.getTopWords().keySet().size()) {
								for (int i = 0; i < Parameters.getTopWords().keySet().size(); i++) {
									writer.write("0" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordSummed().length; i++) {
									writer.write(tweet.getTopWordSummed()[i] + t);
								}
							}
						} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
							int size = 0;
							if (Features.isUseTopWordsNouns()) {
								size = size + Parameters.getTopNouns().keySet().size();
							}
							if (Features.isUseTopWordsVerbs()) {
								size = size + Parameters.getTopVerbs().keySet().size();
							}
							if (Features.isUseTopWordsAdjectives()) {
								size = size + Parameters.getTopAdjectives().keySet().size();
							}
							if (Features.isUseTopWordsAdverbs()) {
								size = size + Parameters.getTopAdverbs().keySet().size();
							}

						}
					} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
						if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {

							int size = 0;
							if (Features.isUseTopWordsNouns()) {
								for (String sentiment : Parameters.getTopNouns().keySet()) {
									size = size + Parameters.getTopNouns().get(sentiment).size();
								}

							}
							if (Features.isUseTopWordsVerbs()) {
								for (String sentiment : Parameters.getTopVerbs().keySet()) {
									size = size + Parameters.getTopVerbs().get(sentiment).size();
								}
							}
							if (Features.isUseTopWordsAdjectives()) {
								for (String sentiment : Parameters.getTopAdjectives().keySet()) {
									size = size + Parameters.getTopAdjectives().get(sentiment).size();
								}
							}
							if (Features.isUseTopWordsAdverbs()) {
								for (String sentiment : Parameters.getTopAdverbs().keySet()) {
									size = size + Parameters.getTopAdverbs().get(sentiment).size();
								}
							}

							if (tweet.getTopWordsSeparatelyBoolean() == null || tweet.getTopWordsSeparatelyBoolean().length != size) {
								for (int i = 0; i < size; i++) {
									writer.write("false" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordsSeparatelyBoolean().length; i++) {
									writer.write(tweet.getTopWordsSeparatelyBoolean()[i] + t);
								}
							}
						} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
							int size = 0;
							for (String sentiment : Parameters.getTopWords().keySet()) {
								size = size + Parameters.getTopWords().get(sentiment).size();
							}
							if (tweet.getTopWordsSeparatelyBoolean() == null
									|| tweet.getTopWordsSeparatelyBoolean().length != size) {
								for (int i = 0; i < size; i++) {
									writer.write("false" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordsSeparatelyBoolean().length; i++) {
									writer.write(tweet.getTopWordsSeparatelyBoolean()[i] + t);
								}
							}

						}
					} 
				}
			}

			// Pattern Features
			if (Features.isUsePatternFeatures()) {
				if (Features.getPatternFeaturesType()!=null) {
					if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
						int size = 0;
						for (String sentiment : Parameters.getSingleLengthPatternsNumeric().keySet()) {
							size = size + Parameters.getSingleLengthPatternsNumeric().get(sentiment).size();
						}
						if (tweet.getPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getPatternScores().length; i++) {
								writer.write(tweet.getPatternScores()[i] + t);
							}
						}
					} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
						int size = 0;
						for (String sentiment : Parameters.getMultiLengthPatternsNumeric().keySet()) {
							size = size + Parameters.getMultiLengthPatternsNumeric().get(sentiment).keySet().size();
						}

						if (tweet.getPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getPatternScores().length; i++) {
								writer.write(tweet.getPatternScores()[i] + t);
							}
						}
					} 
				}
			}
			
			// Advanced Pattern Features
			if (Features.isUseAdvancedPatternFeatures()) {
				if (Features.getAdvancedPatternFeaturesType()!=null) {
					if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
						int size = 0;
						for (String sentiment : Parameters.getSingleLengthAdvancedPatternsNumeric().keySet()) {
							size = size + Parameters.getSingleLengthAdvancedPatternsNumeric().get(sentiment).size();
						}
						if (tweet.getAdvancedPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getAdvancedPatternScores().length; i++) {
								writer.write(tweet.getAdvancedPatternScores()[i] + t);
							}
						}
					} else if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
						int size = 0;
						for (String sentiment : Parameters.getMultiLengthAdvancedPatternsNumeric().keySet()) {
							size = size + Parameters.getMultiLengthAdvancedPatternsNumeric().get(sentiment).keySet().size();
						}

						if (tweet.getAdvancedPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getAdvancedPatternScores().length; i++) {
								writer.write(tweet.getAdvancedPatternScores()[i] + t);
							}
						}
					} 
				}
			}
			
			// Advanced Unigram Features
			if (Features.isUseAdvancedUnigramFeatures()) {
				for (boolean b : tweet.getAdvUnigramFeatures()) {
					writer.write(b + t);
				}
			}
			
			
			// TODO consider the case where the set of non-annotated tweets is taken
			writer.write(tweet.getTweetClass() + n);
		}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	
	private static void saveOutputFile(String file, ArrayList<Tweet> tweets, String t) {

		String n = "\n";
		
		File exportedFile = new File(file);
		File exportedFileDirectory = new File(exportedFile.getParent());

		boolean folderCreated = false;
		if (!exportedFileDirectory.exists()) {
			try {
				exportedFileDirectory.mkdir();
				folderCreated = true;
			} catch (SecurityException se) {
				AlertBox.display("Error", "The folder you want to save in cannot be accessed!", "OK");
			}
		} else {
			folderCreated = true;
		}

		BufferedWriter writer = null;

		if (folderCreated) {

			try {
				if (!exportedFile.exists()) {
					exportedFile.createNewFile();
				}
				writer = new BufferedWriter(new FileWriter(exportedFile));

				
		// ---------------------------//
		//  Add the titles of columns //
		// ---------------------------//
				writer.write("Tweet ID" + t);
		// Sentiment Features
		if (Features.isUseSentimentFeatures()) {
			if (Features.isNumberOfPositiveWords()) {
				writer.write("Number_Of_Positive_Words" + t);
			}

			if (Features.isNumberOfNegativeWords()) {
				writer.write("Number_Of_Negative_Words" + t);
			}
			if (Features.isNumberOfHighlyEmoPositiveWords()) {
				writer.write("Number_Of_Highly_Emo_Positive_Words" + t);
			}
			if (Features.isNumberOfHighlyEmoNegativeWords()) {
				writer.write("Number_Of_Highly_Emo_Negative_Words" + t);
			}
			if (Features.isNumberOfCapitalizedPositiveWords()) {
				writer.write("Number_Of_Capitalized_Positive_Words" + t);
			}
			if (Features.isNumberOfCapitalizedNegativeWords()) {
				writer.write("Number_Of_Capitalized_Negative_Words" + t);
			}
			if (Features.isRatioOfEmotionalWords()) {
				writer.write("Ratio_Of_Emotional_Words" + t);
			}

			if (Features.isNumberOfPositiveEmoticons()) {
				writer.write("Number_Of_Positive_Emoticons" + t);
			}
			if (Features.isNumberOfNegativeEmoticons()) {
				writer.write("Number_Of_Negative_Emoticons" + t);
			}
			if (Features.isNumberOfNeutralEmoticons()) {
				writer.write("Number_Of_Neutral_Emoticons" + t);
			}
			if (Features.isNumberOfJokingEmoticons()) {
				writer.write("Number_Of_Joking_Emoticons" + t);
			}

			if (Features.isNumberOfPositiveSlangs()) {
				writer.write("Number_Of_Positive_Slangs" + t);
			}
			if (Features.isNumberOfNegativeSlangs()) {
				writer.write("Number_Of_Negative_Slangs" + t);
			}

			if (Features.isNumberOfPositiveHashtags()) {
				writer.write("NumberOfPositiveHashtags" + t);
			}
			if (Features.isNumberOfNegativeHashtags()) {
				writer.write("NumberOfNegativeHashtags" + t);
			}

			if (Features.isContrastWordsVsWords()) {
				writer.write("Contrast_Words_Vs_Words" + t);
			}
			if (Features.isContrastWordsVsHashtags()) {
				writer.write("Contrast_Words_Vs_Hashtags" + t);
			}
			if (Features.isContrastWordsVsEmoticons()) {
				writer.write("Contrast_Words_Vs_Emoticons" + t);
			}
			if (Features.isContrastHashtagsVsHashtags()) {
				writer.write("Contrast_Hashtags_Vs_Hashtags" + t);
			}
			if (Features.isContrastHashtagsVsEmoticons()) {
				writer.write("Contrast_Hashtags_Vs_Emoticons" + t);
			}
		}

		// Punctuation Features
		if (Features.isUsePunctuationFeatures()) {
			if (Features.isNumberOfDots()) {
				writer.write("Number_Of_Dots" + t);
			}
			if (Features.isNumberOfCommas()) {
				writer.write("Number_Of_Commas" + t);
			}
			if (Features.isNumberOfSemicolons()) {
				writer.write("Number_Of_Semicolons" + t);
			}
			if (Features.isNumberOfExclamationMarks()) {
				writer.write("Number_Of_Exclamation_Marks" + t);
			}
			if (Features.isNumberOfQuestionMarks()) {
				writer.write("Number_Of_Question_Marks" + t);
			}

			if (Features.isNumberOfParentheses()) {
				writer.write("Number_Of_Parentheses" + t);
			}
			if (Features.isNumberOfBrackets()) {
				writer.write("Number_Of_Brackets" + t);
			}
			if (Features.isNumberOfBraces()) {
				writer.write("Number_Of_Braces" + t);
			}

			if (Features.isNumberOfApostrophes()) {
				writer.write("Number_Of_Apostrophes" + t);
			}
			if (Features.isNumberOfQuotationMarks()) {
				writer.write("Number_Of_Quotation_Marks" + t);
			}

			if (Features.isTotalNumberOfCharacters()) {
				writer.write("Total_Number_Of_Characters" + t);
			}
			if (Features.isTotalNumberOfWords()) {
				writer.write("Total_Number_Of_Words" + t);
			}
			if (Features.isTotalNumberOfSentences()) {
				writer.write("Total_Number_Of_Sentences" + t);
			}
			if (Features.isAverageNumberOfCharactersPerSentence()) {
				writer.write("Average_Number_Of_Characters_Per_Sentence" + t);
			}
			if (Features.isAverageNumberOfWordsPerSentence()) {
				writer.write("Average_Number_Of_Words_Per_Sentence" + t);
			}
		}

		// Stylistic Features
		if (Features.isUseStylisticFeatures()) {

			if (Features.isNumberOfNouns()) {
				writer.write("Number_Of_Nouns" + t);
			}
			if (Features.isRatioOfNouns()) {
				writer.write("Ratio_Of_Nouns" + t);
			}
			if (Features.isNumberOfVerbs()) {
				writer.write("Number_Of_Verbs" + t);
			}
			if (Features.isRatioOfVerbs()) {
				writer.write("Ratio_Of_Verbs" + t);
			}
			if (Features.isNumberOfAdjectives()) {
				writer.write("Number_Of_Adjectives" + t);
			}
			if (Features.isRatioOfAdjectives()) {
				writer.write("Ratio_Of_Adjectives" + t);
			}
			if (Features.isNumberOfAdverbs()) {
				writer.write("Number_Of_Adverbs" + t);
			}
			if (Features.isRatioOfAdverbs()) {
				writer.write("Ratio_Of_Adverbs" + t);
			}

			if (Features.isUseOfSymbols()) {
				writer.write("Use_Of_Symbols" + t);
			}
			if (Features.isUseOfComparativeForm()) {
				writer.write("Use_Of_Comparative_Form" + t);
			}
			if (Features.isUseOfSuperlativeForm()) {
				writer.write("Use_Of_Superlative_Form" + t);
			}
			if (Features.isUseOfProperNouns()) {
				writer.write("Use_Of_Proper_Nouns" + t);
			}

			if (Features.isTotalNumberOfParticles()) {
				writer.write("Total_Number_Of_Particles" + t);
			}
			if (Features.isAverageNumberOfParticles()) {
				writer.write("Average_Number_Of_Particles" + t);
			}
			if (Features.isTotalNumberOfInterjections()) {
				writer.write("Total_Number_Of_Interjections" + t);
			}
			if (Features.isAverageNumberOfInterjections()) {
				writer.write("Average_Number_Of_Interjections" + t);
			}
			if (Features.isTotalNumberOfPronouns()) {
				writer.write("Total_Number_Of_Pronouns" + t);
			}
			if (Features.isAverageNumberOfPronouns()) {
				writer.write("Average_Number_Of_Pronouns" + t);
			}
			if (Features.isTotalNumberOfPronounsGroup1()) {
				writer.write("Total_Number_Of_Pronouns_Group_1" + t);
			}
			if (Features.isAverageNumberOfPronounsGroup1()) {
				writer.write("Average_Number_Of_Pronouns_Group_1" + t);
			}
			if (Features.isTotalNumberOfPronounsGroup2()) {
				writer.write("Total_Number_Of_Pronouns_Group_2" + t);
			}
			if (Features.isAverageNumberOfPronounsGroup2()) {
				writer.write("Average_Number_Of_Pronouns_Group_2" + t);
			}
			if (Features.isUseOfNegation()) {
				writer.write("Use_Of_Negation" + t);
			}
			if (Features.isUseOfUncommonWords()) {
				writer.write("Use_Of_Uncommon_Words" + t);
			}
			if (Features.isNumberOfUncommonWords()) {
				writer.write("Number_Of_Uncommon_Words" + t);
			}
		}

		// Semantic Features
		if (Features.isUseSemanticFeatures()) {
			if (Features.isUseOfOpinionWords()) {
				writer.write("Use_Of_Opinion_Words" + t);
			}
			if (Features.isUseOfHighlySentimentalWords()) {
				writer.write("Use_Of_Highly_Sentimental_Words" + t);
			}
			if (Features.isUseOfUncertaintyWords()) {
				writer.write("Use_Of_Uncertainty_Words" + t);
			}
			if (Features.isUseOfActiveForm()) {
				writer.write("Use_Of_Active_Form" + t);
			}
			if (Features.isUseOfPassiveForm()) {
				writer.write("Use_Of_Passive_Form" + t);
			}
		}

		// Unigram Features
		if (Features.isUseUnigramFeatures()) {
			if (Features.getUnigramTypeOfPos()!=null) {
				if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
					for (String sentiment : Parameters.getClasses()) {
						writer.write("Unigrams[" + sentiment + "]" + t);
					}
				} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
					for (String sentiment : Parameters.getClasses()) {
						if (Features.isUseUnigramNouns()) {
							writer.write("Nouns[" + sentiment + "]" + t);
						}
						if (Features.isUseUnigramVerbs()) {
							writer.write("Verbs[" + sentiment + "]" + t);
						}
						if (Features.isUseUnigramAdjectives()) {
							writer.write("Adjectives[" + sentiment + "]" + t);
						}
						if (Features.isUseUnigramAdverbs()) {
							writer.write("Adverb[" + sentiment + "]" + t);
						}
					}
				} 
			}
		}

		// Top Words Features
		if (Features.isUseTopWords()) {
			if (Features.getTopWordsType()!=null && Features.getTopWordsTypeOfPos()!=null) {
				if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
					if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getTopWords().keySet()) {
							writer.write("Top_Words[" + sentiment + "]" + t);
						}
					} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopNouns().keySet()) {
								writer.write("Top_Nouns[" + sentiment + "]" + t);
							}
						}
						if (Features.isUseTopWordsVerbs()) {
							for (String sentiment : Parameters.getTopVerbs().keySet()) {
								writer.write("Top_Verbs[" + sentiment + "]" + t);
							}
						}
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopAdjectives().keySet()) {
								writer.write("Top_Adjectives[" + sentiment + "]" + t);
							}
						}
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopAdverbs().keySet()) {
								writer.write("Top_Adverbs[" + sentiment + "]" + t);
							}
						}
					}
				} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
					if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getTopWords().keySet()) {
							for (String word : Parameters.getTopWords().get(sentiment)) {
								writer.write("existance[Word:" + word + "]" + t);
							}
						}
					} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopNouns().keySet()) {
								for (String word : Parameters.getTopNouns().get(sentiment)) {
									writer.write("existance[Noun:" + word + "]" + t);
								}
							}
						}
						if (Features.isUseTopWordsVerbs()) {
							for (String sentiment : Parameters.getTopVerbs().keySet()) {
								for (String word : Parameters.getTopVerbs().get(sentiment)) {
									writer.write("existance[Verb:" + word + "]" + t);
								}
							}
						}
						if (Features.isUseTopWordsAdjectives()) {
							for (String sentiment : Parameters.getTopAdjectives().keySet()) {
								for (String word : Parameters.getTopAdjectives().get(sentiment)) {
									writer.write("existance[Adjective:" + word + "]" + t);
								}
							}
						}
						if (Features.isUseTopWordsAdverbs()) {
							for (String sentiment : Parameters.getTopAdverbs().keySet()) {
								for (String word : Parameters.getTopAdverbs().get(sentiment)) {
									writer.write("existance[Adverb:" + word + "]" + t);
								}
							}
						}
					}
				} 
			}
		}

		// Pattern Features
		if (Features.isUsePatternFeatures()) {
			if (Features.getPatternFeaturesType()!=null) {
				if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
					for (String sentiment : Parameters.getSingleLengthPatterns().keySet()) {
						for (int i = 0; i < Parameters.getSingleLengthPatterns().get(sentiment).size(); i++) {
							writer.write("Pattern:" + sentiment + "[" + i + "]" + t);
						}
					}
				} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
					for (String sentiment : Parameters.getMultiLengthPatterns().keySet()) {
						for (int i : Parameters.getMultiLengthPatterns().get(sentiment).keySet()) {
							writer.write("Patterns:" + sentiment + "[Length_" + i + "]" + t);
						}
					}
				} 
			}
		}
		
		// --------------------------------------------------//
		// TODO Add the remaining advanced features here!!!! //
		// --------------------------------------------------//
		
		// Advanced Pattern Features
		if (Features.isUseAdvancedPatternFeatures()) {
			if (Features.getAdvancedPatternFeaturesType()!=null) {
				if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
					for (String sentiment : Parameters.getSingleLengthAdvancedPatterns().keySet()) {
						for (int i = 0; i < Parameters.getSingleLengthAdvancedPatterns().get(sentiment).size(); i++) {
							writer.write("AdvPattern:" + sentiment + "[" + i + "]" + t);
						}
					}
				} else if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
					for (String sentiment : Parameters.getMultiLengthAdvancedPatterns().keySet()) {
						for (int i : Parameters.getMultiLengthAdvancedPatterns().get(sentiment).keySet()) {
							writer.write("AdvPatterns:" + sentiment + "[Length_" + i + "]" + t);
						}
					}
				} 
			}
		}
		
		// Advanced Unigrams
		if (Features.isUseAdvancedUnigramFeatures()) {
			
			for (String unigram : Parameters.getAdvancedUnigrams().keySet()) {
				writer.write("AdvUnigram[" + unigram + "]" + t);
			}
			
		}
		
		// Class
		writer.write("Class");
		
		writer.write(n);

		// ---------------------------//
		// Add the Data //
		// ---------------------------//

		for (Tweet tweet : tweets) {
			
			writer.write(tweet.getId() + t);

			// Sentiment Features
			if (Features.isUseSentimentFeatures()) {
				if (Features.isNumberOfPositiveWords()) {
					writer.write(tweet.getNumberOfPositiveWords() + t);
				}

				if (Features.isNumberOfNegativeWords()) {
					writer.write(tweet.getNumberOfNegativeWords() + t);
				}
				if (Features.isNumberOfHighlyEmoPositiveWords()) {
					writer.write(tweet.getNumberOfHighlyEmoPositiveWords() + t);
				}
				if (Features.isNumberOfHighlyEmoNegativeWords()) {
					writer.write(tweet.getNumberOfHighlyEmoNegativeWords() + t);
				}
				if (Features.isNumberOfCapitalizedPositiveWords()) {
					writer.write(tweet.getNumberOfCapitalizedPositiveWords() + t);
				}
				if (Features.isNumberOfCapitalizedNegativeWords()) {
					writer.write(tweet.getNumberOfCapitalizedNegativeWords() + t);
				}
				if (Features.isRatioOfEmotionalWords()) {
					writer.write(tweet.getRatioOfEmotionalWords() + t);
				}

				if (Features.isNumberOfPositiveEmoticons()) {
					writer.write(tweet.getNumberOfPositiveEmoticons() + t);
				}
				if (Features.isNumberOfNegativeEmoticons()) {
					writer.write(tweet.getNumberOfNegativeEmoticons() + t);
				}
				if (Features.isNumberOfNeutralEmoticons()) {
					writer.write(tweet.getNumberOfNeutralEmoticons() + t);
				}
				if (Features.isNumberOfJokingEmoticons()) {
					writer.write(tweet.getNumberOfJokingEmoticons() + t);
				}

				if (Features.isNumberOfPositiveSlangs()) {
					writer.write(tweet.getNumberOfPositiveSlangs() + t);
				}
				if (Features.isNumberOfNegativeSlangs()) {
					writer.write(tweet.getNumberOfNegativeSlangs() + t);
				}

				if (Features.isNumberOfPositiveHashtags()) {
					writer.write(tweet.getNumberOfPositiveHashtags() + t);
				}
				if (Features.isNumberOfNegativeHashtags()) {
					writer.write(tweet.getNumberOfNegativeHashtags() + t);
				}

				if (Features.isContrastWordsVsWords()) {
					writer.write(tweet.getContrastWordsVsWords() + t);
				}
				if (Features.isContrastWordsVsHashtags()) {
					writer.write(tweet.getContrastWordsVsHashtags() + t);
				}
				if (Features.isContrastWordsVsEmoticons()) {
					writer.write(tweet.getContrastWordsVsEmoticons() + t);
				}
				if (Features.isContrastHashtagsVsHashtags()) {
					writer.write(tweet.getContrastHashtagsVsHashtags() + t);
				}
				if (Features.isContrastHashtagsVsEmoticons()) {
					writer.write(tweet.getContrastHashtagsVsEmoticons() + t);
				}
			}

			// Punctuation Features
			if (Features.isUsePunctuationFeatures()) {
				if (Features.isNumberOfDots()) {
					writer.write(tweet.getNumberOfDots() + t);
				}
				if (Features.isNumberOfCommas()) {
					writer.write(tweet.getNumberOfCommas() + t);
				}
				if (Features.isNumberOfSemicolons()) {
					writer.write(tweet.getNumberOfSemicolons() + t);
				}
				if (Features.isNumberOfExclamationMarks()) {
					writer.write(tweet.getNumberOfExclamationMarks() + t);
				}
				if (Features.isNumberOfQuestionMarks()) {
					writer.write(tweet.getNumberOfQuestionMarks() + t);
				}

				if (Features.isNumberOfParentheses()) {
					writer.write(tweet.getNumberOfParentheses() + t);
				}
				if (Features.isNumberOfBrackets()) {
					writer.write(tweet.getNumberOfBrackets() + t);
				}
				if (Features.isNumberOfBraces()) {
					writer.write(tweet.getNumberOfBraces() + t);
				}

				if (Features.isNumberOfApostrophes()) {
					writer.write(tweet.getNumberOfApostrophes() + t);
				}
				if (Features.isNumberOfQuotationMarks()) {
					writer.write(tweet.getNumberOfQuotationMarks() + t);
				}

				if (Features.isTotalNumberOfCharacters()) {
					writer.write(tweet.getTotalNumberOfCharacters() + t);
				}
				if (Features.isTotalNumberOfWords()) {
					writer.write(tweet.getTotalNumberOfWords() + t);
				}
				if (Features.isTotalNumberOfSentences()) {
					writer.write(tweet.getTotalNumberOfSentences() + t);
				}
				if (Features.isAverageNumberOfCharactersPerSentence()) {
					writer.write(tweet.getAverageNumberOfCharactersPerSentence() + t);
				}
				if (Features.isAverageNumberOfWordsPerSentence()) {
					writer.write(tweet.getAverageNumberOfWordsPerSentence() + t);
				}
			}

			// Stylistic Features
			if (Features.isUseStylisticFeatures()) {

				if (Features.isNumberOfNouns()) {
					writer.write(tweet.getNumberOfNouns() + t);
				}
				if (Features.isRatioOfNouns()) {
					writer.write(tweet.getRatioOfNouns() + t);
				}
				if (Features.isNumberOfVerbs()) {
					writer.write(tweet.getNumberOfVerbs() + t);
				}
				if (Features.isRatioOfVerbs()) {
					writer.write(tweet.getRatioOfVerbs() + t);
				}
				if (Features.isNumberOfAdjectives()) {
					writer.write(tweet.getNumberOfAdjectives() + t);
				}
				if (Features.isRatioOfAdjectives()) {
					writer.write(tweet.getRatioOfAdjectives() + t);
				}
				if (Features.isNumberOfAdverbs()) {
					writer.write(tweet.getNumberOfAdverbs() + t);
				}
				if (Features.isRatioOfAdverbs()) {
					writer.write(tweet.getRatioOfAdverbs() + t);
				}

				if (Features.isUseOfSymbols()) {
					writer.write(tweet.isUseOfSymbols() + t);
				}
				if (Features.isUseOfComparativeForm()) {
					writer.write(tweet.isUseOfComparativeForm() + t);
				}
				if (Features.isUseOfSuperlativeForm()) {
					writer.write(tweet.isUseOfSuperlativeForm() + t);
				}
				if (Features.isUseOfProperNouns()) {
					writer.write(tweet.isUseOfProperNouns() + t);
				}

				if (Features.isTotalNumberOfParticles()) {
					writer.write(tweet.getTotalNumberOfParticles() + t);
				}
				if (Features.isAverageNumberOfParticles()) {
					writer.write(tweet.getAverageNumberOfParticles() + t);
				}
				if (Features.isTotalNumberOfInterjections()) {
					writer.write(tweet.getTotalNumberOfInterjections() + t);
				}
				if (Features.isAverageNumberOfInterjections()) {
					writer.write(tweet.getAverageNumberOfInterjections() + t);
				}
				if (Features.isTotalNumberOfPronouns()) {
					writer.write(tweet.getTotalNumberOfPronouns() + t);
				}
				if (Features.isAverageNumberOfPronouns()) {
					writer.write(tweet.getAverageNumberOfPronouns() + t);
				}
				if (Features.isTotalNumberOfPronounsGroup1()) {
					writer.write(tweet.getTotalNumberOfPronounsGroup1() + t);
				}
				if (Features.isAverageNumberOfPronounsGroup1()) {
					writer.write(tweet.getAverageNumberOfPronounsGroup1() + t);
				}
				if (Features.isTotalNumberOfPronounsGroup2()) {
					writer.write(tweet.getTotalNumberOfPronounsGroup2() + t);
				}
				if (Features.isAverageNumberOfPronounsGroup2()) {
					writer.write(tweet.getAverageNumberOfPronounsGroup2() + t);
				}
				if (Features.isUseOfNegation()) {
					writer.write(tweet.isUseOfNegation() + t);
				}
				if (Features.isUseOfUncommonWords()) {
					writer.write(tweet.isUseOfUncommonWords() + t);
				}
				if (Features.isNumberOfUncommonWords()) {
					writer.write(tweet.getNumberOfUncommonWords() + t);
				}
			}

			// Semantic Features
			if (Features.isUseSemanticFeatures()) {
				if (Features.isUseOfOpinionWords()) {
					writer.write(tweet.isUseOfOpinionWords() + t);
				}
				if (Features.isUseOfHighlySentimentalWords()) {
					writer.write(tweet.isUseOfHighlySentimentalWords() + t);
				}
				if (Features.isUseOfUncertaintyWords()) {
					writer.write(tweet.isUseOfUncertaintyWords() + t);
				}
				if (Features.isUseOfActiveForm()) {
					writer.write(tweet.isUseOfActiveForm() + t);
				}
				if (Features.isUseOfPassiveForm()) {
					writer.write(tweet.isUseOfPassiveForm() + t);
				}
			}

			// Unigram Features
			if (Features.isUseUnigramFeatures()) {
				if (Features.getUnigramTypeOfPos()!=null) {
					if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getClasses()) {
							if (tweet.getUnigramCountPerClass().keySet().contains(sentiment)) {
								writer.write(tweet.getUnigramCountPerClass().get(sentiment) + t);
							} else {
								writer.write("0" + t);
							}
						}
					} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						for (String sentiment : Parameters.getClasses()) {
							if (Features.isUseUnigramNouns()) {
								if (tweet.getNounCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getNounCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramVerbs()) {
								if (tweet.getVerbCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getVerbCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramAdjectives()) {
								if (tweet.getAdjectiveCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getAdjectiveCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramAdverbs()) {
								if (tweet.getAdverbCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getAdverbCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
						}
					} 
				}
			}

			// Top Words Features
			if (Features.isUseTopWords()) {
				if (Features.getTopWordsType()!=null && Features.getTopWordsTypeOfPos()!=null) {
					if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
						if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
							if (tweet.getTopWordSummed() == null || tweet.getTopWordSummed().length != Parameters.getTopWords().keySet().size()) {
								for (int i = 0; i < Parameters.getTopWords().keySet().size(); i++) {
									writer.write("0" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordSummed().length; i++) {
									writer.write(tweet.getTopWordSummed()[i] + t);
								}
							}
						} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
							int size = 0;
							if (Features.isUseTopWordsNouns()) {
								size = size + Parameters.getTopNouns().keySet().size();
							}
							if (Features.isUseTopWordsVerbs()) {
								size = size + Parameters.getTopVerbs().keySet().size();
							}
							if (Features.isUseTopWordsAdjectives()) {
								size = size + Parameters.getTopAdjectives().keySet().size();
							}
							if (Features.isUseTopWordsAdverbs()) {
								size = size + Parameters.getTopAdverbs().keySet().size();
							}

						}
					} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
						if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {

							int size = 0;
							if (Features.isUseTopWordsNouns()) {
								for (String sentiment : Parameters.getTopNouns().keySet()) {
									size = size + Parameters.getTopNouns().get(sentiment).size();
								}

							}
							if (Features.isUseTopWordsVerbs()) {
								for (String sentiment : Parameters.getTopVerbs().keySet()) {
									size = size + Parameters.getTopVerbs().get(sentiment).size();
								}
							}
							if (Features.isUseTopWordsAdjectives()) {
								for (String sentiment : Parameters.getTopAdjectives().keySet()) {
									size = size + Parameters.getTopAdjectives().get(sentiment).size();
								}
							}
							if (Features.isUseTopWordsAdverbs()) {
								for (String sentiment : Parameters.getTopAdverbs().keySet()) {
									size = size + Parameters.getTopAdverbs().get(sentiment).size();
								}
							}

							if (tweet.getTopWordsSeparatelyBoolean() == null || tweet.getTopWordsSeparatelyBoolean().length != size) {
								for (int i = 0; i < size; i++) {
									writer.write("false" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordsSeparatelyBoolean().length; i++) {
									writer.write(tweet.getTopWordsSeparatelyBoolean()[i] + t);
								}
							}
						} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
							int size = 0;
							for (String sentiment : Parameters.getTopWords().keySet()) {
								size = size + Parameters.getTopWords().get(sentiment).size();
							}
							if (tweet.getTopWordsSeparatelyBoolean() == null
									|| tweet.getTopWordsSeparatelyBoolean().length != size) {
								for (int i = 0; i < size; i++) {
									writer.write("false" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordsSeparatelyBoolean().length; i++) {
									writer.write(tweet.getTopWordsSeparatelyBoolean()[i] + t);
								}
							}

						}
					} 
				}
			}

			// Pattern Features
			if (Features.isUsePatternFeatures()) {
				if (Features.getPatternFeaturesType()!=null) {
					if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
						int size = 0;
						for (String sentiment : Parameters.getSingleLengthPatterns().keySet()) {
							size = size + Parameters.getSingleLengthPatterns().get(sentiment).size();
						}
						if (tweet.getPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getPatternScores().length; i++) {
								writer.write(tweet.getPatternScores()[i] + t);
							}
						}
					} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
						int size = 0;
						for (String sentiment : Parameters.getMultiLengthPatterns().keySet()) {
							size = size + Parameters.getMultiLengthPatterns().get(sentiment).keySet().size();
						}

						if (tweet.getPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getPatternScores().length; i++) {
								writer.write(tweet.getPatternScores()[i] + t);
							}
						}
					} 
				}
			}
			
			
			// --------------------------------------------------//
			// TODO Add the remaining advanced features here!!!! //
			// --------------------------------------------------//
			
			// Advanced Pattern Features
			if (Features.isUseAdvancedPatternFeatures()) {
				if (Features.getAdvancedPatternFeaturesType()!=null) {
					if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
						int size = 0;
						for (String sentiment : Parameters.getSingleLengthAdvancedPatterns().keySet()) {
							size = size + Parameters.getSingleLengthAdvancedPatterns().get(sentiment).size();
						}
						if (tweet.getAdvancedPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getAdvancedPatternScores().length; i++) {
								writer.write(tweet.getAdvancedPatternScores()[i] + t);
							}
						}
					} else if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
						int size = 0;
						for (String sentiment : Parameters.getMultiLengthAdvancedPatterns().keySet()) {
							size = size + Parameters.getMultiLengthAdvancedPatterns().get(sentiment).keySet().size();
						}

						if (tweet.getAdvancedPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getAdvancedPatternScores().length; i++) {
								writer.write(tweet.getAdvancedPatternScores()[i] + t);
							}
						}
					} 
				}
			}
			
			if (Features.isUseAdvancedUnigramFeatures()) {
				for (boolean b : tweet.getAdvUnigramFeatures()) {
					writer.write(b + t);
				}
			}
			
			writer.write(tweet.getTweetClass());
			
			writer.write(n);
		}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	private static void saveOutputFileNumeric(String file, ArrayList<Tweet> tweets, String t) {

		String n = "\n";
		
		File exportedFile = new File(file);
		File exportedFileDirectory = new File(exportedFile.getParent());

		boolean folderCreated = false;
		if (!exportedFileDirectory.exists()) {
			try {
				exportedFileDirectory.mkdir();
				folderCreated = true;
			} catch (SecurityException se) {
				AlertBox.display("Error", "The folder you want to save in cannot be accessed!", "OK");
			}
		} else {
			folderCreated = true;
		}

		BufferedWriter writer = null;

		if (folderCreated) {

			try {
				if (!exportedFile.exists()) {
					exportedFile.createNewFile();
				}
				writer = new BufferedWriter(new FileWriter(exportedFile));

				
		// ---------------------------//
		//  Add the titles of columns //
		// ---------------------------//
				writer.write("Tweet ID" + t);
		// Sentiment Features
		if (Features.isUseSentimentFeatures()) {
			if (Features.isNumberOfPositiveWords()) {
				writer.write("Number_Of_Positive_Words" + t);
			}

			if (Features.isNumberOfNegativeWords()) {
				writer.write("Number_Of_Negative_Words" + t);
			}
			if (Features.isNumberOfHighlyEmoPositiveWords()) {
				writer.write("Number_Of_Highly_Emo_Positive_Words" + t);
			}
			if (Features.isNumberOfHighlyEmoNegativeWords()) {
				writer.write("Number_Of_Highly_Emo_Negative_Words" + t);
			}
			if (Features.isNumberOfCapitalizedPositiveWords()) {
				writer.write("Number_Of_Capitalized_Positive_Words" + t);
			}
			if (Features.isNumberOfCapitalizedNegativeWords()) {
				writer.write("Number_Of_Capitalized_Negative_Words" + t);
			}
			if (Features.isRatioOfEmotionalWords()) {
				writer.write("Ratio_Of_Emotional_Words" + t);
			}

			if (Features.isNumberOfPositiveEmoticons()) {
				writer.write("Number_Of_Positive_Emoticons" + t);
			}
			if (Features.isNumberOfNegativeEmoticons()) {
				writer.write("Number_Of_Negative_Emoticons" + t);
			}
			if (Features.isNumberOfNeutralEmoticons()) {
				writer.write("Number_Of_Neutral_Emoticons" + t);
			}
			if (Features.isNumberOfJokingEmoticons()) {
				writer.write("Number_Of_Joking_Emoticons" + t);
			}

			if (Features.isNumberOfPositiveSlangs()) {
				writer.write("Number_Of_Positive_Slangs" + t);
			}
			if (Features.isNumberOfNegativeSlangs()) {
				writer.write("Number_Of_Negative_Slangs" + t);
			}

			if (Features.isNumberOfPositiveHashtags()) {
				writer.write("NumberOfPositiveHashtags" + t);
			}
			if (Features.isNumberOfNegativeHashtags()) {
				writer.write("NumberOfNegativeHashtags" + t);
			}

			if (Features.isContrastWordsVsWords()) {
				writer.write("Contrast_Words_Vs_Words" + t);
			}
			if (Features.isContrastWordsVsHashtags()) {
				writer.write("Contrast_Words_Vs_Hashtags" + t);
			}
			if (Features.isContrastWordsVsEmoticons()) {
				writer.write("Contrast_Words_Vs_Emoticons" + t);
			}
			if (Features.isContrastHashtagsVsHashtags()) {
				writer.write("Contrast_Hashtags_Vs_Hashtags" + t);
			}
			if (Features.isContrastHashtagsVsEmoticons()) {
				writer.write("Contrast_Hashtags_Vs_Emoticons" + t);
			}
		}

		// Punctuation Features
		if (Features.isUsePunctuationFeatures()) {
			if (Features.isNumberOfDots()) {
				writer.write("Number_Of_Dots" + t);
			}
			if (Features.isNumberOfCommas()) {
				writer.write("Number_Of_Commas" + t);
			}
			if (Features.isNumberOfSemicolons()) {
				writer.write("Number_Of_Semicolons" + t);
			}
			if (Features.isNumberOfExclamationMarks()) {
				writer.write("Number_Of_Exclamation_Marks" + t);
			}
			if (Features.isNumberOfQuestionMarks()) {
				writer.write("Number_Of_Question_Marks" + t);
			}

			if (Features.isNumberOfParentheses()) {
				writer.write("Number_Of_Parentheses" + t);
			}
			if (Features.isNumberOfBrackets()) {
				writer.write("Number_Of_Brackets" + t);
			}
			if (Features.isNumberOfBraces()) {
				writer.write("Number_Of_Braces" + t);
			}

			if (Features.isNumberOfApostrophes()) {
				writer.write("Number_Of_Apostrophes" + t);
			}
			if (Features.isNumberOfQuotationMarks()) {
				writer.write("Number_Of_Quotation_Marks" + t);
			}

			if (Features.isTotalNumberOfCharacters()) {
				writer.write("Total_Number_Of_Characters" + t);
			}
			if (Features.isTotalNumberOfWords()) {
				writer.write("Total_Number_Of_Words" + t);
			}
			if (Features.isTotalNumberOfSentences()) {
				writer.write("Total_Number_Of_Sentences" + t);
			}
			if (Features.isAverageNumberOfCharactersPerSentence()) {
				writer.write("Average_Number_Of_Characters_Per_Sentence" + t);
			}
			if (Features.isAverageNumberOfWordsPerSentence()) {
				writer.write("Average_Number_Of_Words_Per_Sentence" + t);
			}
		}

		// Stylistic Features
		if (Features.isUseStylisticFeatures()) {

			if (Features.isNumberOfNouns()) {
				writer.write("Number_Of_Nouns" + t);
			}
			if (Features.isRatioOfNouns()) {
				writer.write("Ratio_Of_Nouns" + t);
			}
			if (Features.isNumberOfVerbs()) {
				writer.write("Number_Of_Verbs" + t);
			}
			if (Features.isRatioOfVerbs()) {
				writer.write("Ratio_Of_Verbs" + t);
			}
			if (Features.isNumberOfAdjectives()) {
				writer.write("Number_Of_Adjectives" + t);
			}
			if (Features.isRatioOfAdjectives()) {
				writer.write("Ratio_Of_Adjectives" + t);
			}
			if (Features.isNumberOfAdverbs()) {
				writer.write("Number_Of_Adverbs" + t);
			}
			if (Features.isRatioOfAdverbs()) {
				writer.write("Ratio_Of_Adverbs" + t);
			}

			if (Features.isUseOfSymbols()) {
				writer.write("Use_Of_Symbols" + t);
			}
			if (Features.isUseOfComparativeForm()) {
				writer.write("Use_Of_Comparative_Form" + t);
			}
			if (Features.isUseOfSuperlativeForm()) {
				writer.write("Use_Of_Superlative_Form" + t);
			}
			if (Features.isUseOfProperNouns()) {
				writer.write("Use_Of_Proper_Nouns" + t);
			}

			if (Features.isTotalNumberOfParticles()) {
				writer.write("Total_Number_Of_Particles" + t);
			}
			if (Features.isAverageNumberOfParticles()) {
				writer.write("Average_Number_Of_Particles" + t);
			}
			if (Features.isTotalNumberOfInterjections()) {
				writer.write("Total_Number_Of_Interjections" + t);
			}
			if (Features.isAverageNumberOfInterjections()) {
				writer.write("Average_Number_Of_Interjections" + t);
			}
			if (Features.isTotalNumberOfPronouns()) {
				writer.write("Total_Number_Of_Pronouns" + t);
			}
			if (Features.isAverageNumberOfPronouns()) {
				writer.write("Average_Number_Of_Pronouns" + t);
			}
			if (Features.isTotalNumberOfPronounsGroup1()) {
				writer.write("Total_Number_Of_Pronouns_Group_1" + t);
			}
			if (Features.isAverageNumberOfPronounsGroup1()) {
				writer.write("Average_Number_Of_Pronouns_Group_1" + t);
			}
			if (Features.isTotalNumberOfPronounsGroup2()) {
				writer.write("Total_Number_Of_Pronouns_Group_2" + t);
			}
			if (Features.isAverageNumberOfPronounsGroup2()) {
				writer.write("Average_Number_Of_Pronouns_Group_2" + t);
			}
			if (Features.isUseOfNegation()) {
				writer.write("Use_Of_Negation" + t);
			}
			if (Features.isUseOfUncommonWords()) {
				writer.write("Use_Of_Uncommon_Words" + t);
			}
			if (Features.isNumberOfUncommonWords()) {
				writer.write("Number_Of_Uncommon_Words" + t);
			}
		}

		// Semantic Features
		if (Features.isUseSemanticFeatures()) {
			if (Features.isUseOfOpinionWords()) {
				writer.write("Use_Of_Opinion_Words" + t);
			}
			if (Features.isUseOfHighlySentimentalWords()) {
				writer.write("Use_Of_Highly_Sentimental_Words" + t);
			}
			if (Features.isUseOfUncertaintyWords()) {
				writer.write("Use_Of_Uncertainty_Words" + t);
			}
			if (Features.isUseOfActiveForm()) {
				writer.write("Use_Of_Active_Form" + t);
			}
			if (Features.isUseOfPassiveForm()) {
				writer.write("Use_Of_Passive_Form" + t);
			}
		}

		// Unigram Features
		if (Features.isUseUnigramFeatures()) {
			if (Features.getUnigramTypeOfPos()!=null) {
				if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
					for (String sentiment : Parameters.getClasses()) {
						writer.write("Unigrams[" + sentiment + "]" + t);
					}
				} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
					for (String sentiment : Parameters.getClasses()) {
						if (Features.isUseUnigramNouns()) {
							writer.write("Nouns[" + sentiment + "]" + t);
						}
						if (Features.isUseUnigramVerbs()) {
							writer.write("Verbs[" + sentiment + "]" + t);
						}
						if (Features.isUseUnigramAdjectives()) {
							writer.write("Adjectives[" + sentiment + "]" + t);
						}
						if (Features.isUseUnigramAdverbs()) {
							writer.write("Adverb[" + sentiment + "]" + t);
						}
					}
				} 
			}
		}

		// Top Words Features
		if (Features.isUseTopWords()) {
			if (Features.getTopWordsType()!=null && Features.getTopWordsTypeOfPos()!=null) {
				if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
					if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getTopWords().keySet()) {
							writer.write("Top_Words[" + sentiment + "]" + t);
						}
					} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopNouns().keySet()) {
								writer.write("Top_Nouns[" + sentiment + "]" + t);
							}
						}
						if (Features.isUseTopWordsVerbs()) {
							for (String sentiment : Parameters.getTopVerbs().keySet()) {
								writer.write("Top_Verbs[" + sentiment + "]" + t);
							}
						}
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopAdjectives().keySet()) {
								writer.write("Top_Adjectives[" + sentiment + "]" + t);
							}
						}
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopAdverbs().keySet()) {
								writer.write("Top_Adverbs[" + sentiment + "]" + t);
							}
						}
					}
				} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
					if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getTopWords().keySet()) {
							for (String word : Parameters.getTopWords().get(sentiment)) {
								writer.write("existance[Word:" + word + "]" + t);
							}
						}
					} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopNouns().keySet()) {
								for (String word : Parameters.getTopNouns().get(sentiment)) {
									writer.write("existance[Noun:" + word + "]" + t);
								}
							}
						}
						if (Features.isUseTopWordsVerbs()) {
							for (String sentiment : Parameters.getTopVerbs().keySet()) {
								for (String word : Parameters.getTopVerbs().get(sentiment)) {
									writer.write("existance[Verb:" + word + "]" + t);
								}
							}
						}
						if (Features.isUseTopWordsAdjectives()) {
							for (String sentiment : Parameters.getTopAdjectives().keySet()) {
								for (String word : Parameters.getTopAdjectives().get(sentiment)) {
									writer.write("existance[Adjective:" + word + "]" + t);
								}
							}
						}
						if (Features.isUseTopWordsAdverbs()) {
							for (String sentiment : Parameters.getTopAdverbs().keySet()) {
								for (String word : Parameters.getTopAdverbs().get(sentiment)) {
									writer.write("existance[Adverb:" + word + "]" + t);
								}
							}
						}
					}
				} 
			}
		}

		// Pattern Features
		if (Features.isUsePatternFeatures()) {
			if (Features.getPatternFeaturesType()!=null) {
				if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
					for (String sentiment : Parameters.getSingleLengthPatternsNumeric().keySet()) {
						for (int i = 0; i < Parameters.getSingleLengthPatternsNumeric().get(sentiment).size(); i++) {
							writer.write("Pattern:" + sentiment + "[" + i + "]" + t);
						}
					}
				} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
					for (String sentiment : Parameters.getMultiLengthPatternsNumeric().keySet()) {
						for (int i : Parameters.getMultiLengthPatternsNumeric().get(sentiment).keySet()) {
							writer.write("Patterns:" + sentiment + "[Length_" + i + "]" + t);
						}
					}
				} 
			}
		}
		
		// --------------------------------------------------//
		// TODO Add the remaining advanced features here!!!! //
		// --------------------------------------------------//
		
		// Advanced Pattern Features
		if (Features.isUseAdvancedPatternFeatures()) {
			if (Features.getAdvancedPatternFeaturesType()!=null) {
				if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
					for (String sentiment : Parameters.getSingleLengthAdvancedPatternsNumeric().keySet()) {
						for (int i = 0; i < Parameters.getSingleLengthAdvancedPatternsNumeric().get(sentiment).size(); i++) {
							writer.write("AdvPattern:" + sentiment + "[" + i + "]" + t);
						}
					}
				} else if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
					for (String sentiment : Parameters.getMultiLengthAdvancedPatternsNumeric().keySet()) {
						for (int i : Parameters.getMultiLengthAdvancedPatternsNumeric().get(sentiment).keySet()) {
							writer.write("AdvPatterns:" + sentiment + "[Length_" + i + "]" + t);
						}
					}
				} 
			}
		}
		
		// Advanced Unigrams
		if (Features.isUseAdvancedUnigramFeatures()) {
			
			for (String unigram : Parameters.getAdvancedUnigrams().keySet()) {
				writer.write("AdvUnigram[" + unigram + "]" + t);
			}
			
		}
		
		// Class
		writer.write("Class");
		
		writer.write(n);

		// ---------------------------//
		// Add the Data //
		// ---------------------------//

		for (Tweet tweet : tweets) {
			
			writer.write(tweet.getId() + t);

			// Sentiment Features
			if (Features.isUseSentimentFeatures()) {
				if (Features.isNumberOfPositiveWords()) {
					writer.write(tweet.getNumberOfPositiveWords() + t);
				}

				if (Features.isNumberOfNegativeWords()) {
					writer.write(tweet.getNumberOfNegativeWords() + t);
				}
				if (Features.isNumberOfHighlyEmoPositiveWords()) {
					writer.write(tweet.getNumberOfHighlyEmoPositiveWords() + t);
				}
				if (Features.isNumberOfHighlyEmoNegativeWords()) {
					writer.write(tweet.getNumberOfHighlyEmoNegativeWords() + t);
				}
				if (Features.isNumberOfCapitalizedPositiveWords()) {
					writer.write(tweet.getNumberOfCapitalizedPositiveWords() + t);
				}
				if (Features.isNumberOfCapitalizedNegativeWords()) {
					writer.write(tweet.getNumberOfCapitalizedNegativeWords() + t);
				}
				if (Features.isRatioOfEmotionalWords()) {
					writer.write(tweet.getRatioOfEmotionalWords() + t);
				}

				if (Features.isNumberOfPositiveEmoticons()) {
					writer.write(tweet.getNumberOfPositiveEmoticons() + t);
				}
				if (Features.isNumberOfNegativeEmoticons()) {
					writer.write(tweet.getNumberOfNegativeEmoticons() + t);
				}
				if (Features.isNumberOfNeutralEmoticons()) {
					writer.write(tweet.getNumberOfNeutralEmoticons() + t);
				}
				if (Features.isNumberOfJokingEmoticons()) {
					writer.write(tweet.getNumberOfJokingEmoticons() + t);
				}

				if (Features.isNumberOfPositiveSlangs()) {
					writer.write(tweet.getNumberOfPositiveSlangs() + t);
				}
				if (Features.isNumberOfNegativeSlangs()) {
					writer.write(tweet.getNumberOfNegativeSlangs() + t);
				}

				if (Features.isNumberOfPositiveHashtags()) {
					writer.write(tweet.getNumberOfPositiveHashtags() + t);
				}
				if (Features.isNumberOfNegativeHashtags()) {
					writer.write(tweet.getNumberOfNegativeHashtags() + t);
				}

				if (Features.isContrastWordsVsWords()) {
					writer.write(tweet.getContrastWordsVsWords() + t);
				}
				if (Features.isContrastWordsVsHashtags()) {
					writer.write(tweet.getContrastWordsVsHashtags() + t);
				}
				if (Features.isContrastWordsVsEmoticons()) {
					writer.write(tweet.getContrastWordsVsEmoticons() + t);
				}
				if (Features.isContrastHashtagsVsHashtags()) {
					writer.write(tweet.getContrastHashtagsVsHashtags() + t);
				}
				if (Features.isContrastHashtagsVsEmoticons()) {
					writer.write(tweet.getContrastHashtagsVsEmoticons() + t);
				}
			}

			// Punctuation Features
			if (Features.isUsePunctuationFeatures()) {
				if (Features.isNumberOfDots()) {
					writer.write(tweet.getNumberOfDots() + t);
				}
				if (Features.isNumberOfCommas()) {
					writer.write(tweet.getNumberOfCommas() + t);
				}
				if (Features.isNumberOfSemicolons()) {
					writer.write(tweet.getNumberOfSemicolons() + t);
				}
				if (Features.isNumberOfExclamationMarks()) {
					writer.write(tweet.getNumberOfExclamationMarks() + t);
				}
				if (Features.isNumberOfQuestionMarks()) {
					writer.write(tweet.getNumberOfQuestionMarks() + t);
				}

				if (Features.isNumberOfParentheses()) {
					writer.write(tweet.getNumberOfParentheses() + t);
				}
				if (Features.isNumberOfBrackets()) {
					writer.write(tweet.getNumberOfBrackets() + t);
				}
				if (Features.isNumberOfBraces()) {
					writer.write(tweet.getNumberOfBraces() + t);
				}

				if (Features.isNumberOfApostrophes()) {
					writer.write(tweet.getNumberOfApostrophes() + t);
				}
				if (Features.isNumberOfQuotationMarks()) {
					writer.write(tweet.getNumberOfQuotationMarks() + t);
				}

				if (Features.isTotalNumberOfCharacters()) {
					writer.write(tweet.getTotalNumberOfCharacters() + t);
				}
				if (Features.isTotalNumberOfWords()) {
					writer.write(tweet.getTotalNumberOfWords() + t);
				}
				if (Features.isTotalNumberOfSentences()) {
					writer.write(tweet.getTotalNumberOfSentences() + t);
				}
				if (Features.isAverageNumberOfCharactersPerSentence()) {
					writer.write(tweet.getAverageNumberOfCharactersPerSentence() + t);
				}
				if (Features.isAverageNumberOfWordsPerSentence()) {
					writer.write(tweet.getAverageNumberOfWordsPerSentence() + t);
				}
			}

			// Stylistic Features
			if (Features.isUseStylisticFeatures()) {

				if (Features.isNumberOfNouns()) {
					writer.write(tweet.getNumberOfNouns() + t);
				}
				if (Features.isRatioOfNouns()) {
					writer.write(tweet.getRatioOfNouns() + t);
				}
				if (Features.isNumberOfVerbs()) {
					writer.write(tweet.getNumberOfVerbs() + t);
				}
				if (Features.isRatioOfVerbs()) {
					writer.write(tweet.getRatioOfVerbs() + t);
				}
				if (Features.isNumberOfAdjectives()) {
					writer.write(tweet.getNumberOfAdjectives() + t);
				}
				if (Features.isRatioOfAdjectives()) {
					writer.write(tweet.getRatioOfAdjectives() + t);
				}
				if (Features.isNumberOfAdverbs()) {
					writer.write(tweet.getNumberOfAdverbs() + t);
				}
				if (Features.isRatioOfAdverbs()) {
					writer.write(tweet.getRatioOfAdverbs() + t);
				}

				if (Features.isUseOfSymbols()) {
					writer.write(tweet.isUseOfSymbols() + t);
				}
				if (Features.isUseOfComparativeForm()) {
					writer.write(tweet.isUseOfComparativeForm() + t);
				}
				if (Features.isUseOfSuperlativeForm()) {
					writer.write(tweet.isUseOfSuperlativeForm() + t);
				}
				if (Features.isUseOfProperNouns()) {
					writer.write(tweet.isUseOfProperNouns() + t);
				}

				if (Features.isTotalNumberOfParticles()) {
					writer.write(tweet.getTotalNumberOfParticles() + t);
				}
				if (Features.isAverageNumberOfParticles()) {
					writer.write(tweet.getAverageNumberOfParticles() + t);
				}
				if (Features.isTotalNumberOfInterjections()) {
					writer.write(tweet.getTotalNumberOfInterjections() + t);
				}
				if (Features.isAverageNumberOfInterjections()) {
					writer.write(tweet.getAverageNumberOfInterjections() + t);
				}
				if (Features.isTotalNumberOfPronouns()) {
					writer.write(tweet.getTotalNumberOfPronouns() + t);
				}
				if (Features.isAverageNumberOfPronouns()) {
					writer.write(tweet.getAverageNumberOfPronouns() + t);
				}
				if (Features.isTotalNumberOfPronounsGroup1()) {
					writer.write(tweet.getTotalNumberOfPronounsGroup1() + t);
				}
				if (Features.isAverageNumberOfPronounsGroup1()) {
					writer.write(tweet.getAverageNumberOfPronounsGroup1() + t);
				}
				if (Features.isTotalNumberOfPronounsGroup2()) {
					writer.write(tweet.getTotalNumberOfPronounsGroup2() + t);
				}
				if (Features.isAverageNumberOfPronounsGroup2()) {
					writer.write(tweet.getAverageNumberOfPronounsGroup2() + t);
				}
				if (Features.isUseOfNegation()) {
					writer.write(tweet.isUseOfNegation() + t);
				}
				if (Features.isUseOfUncommonWords()) {
					writer.write(tweet.isUseOfUncommonWords() + t);
				}
				if (Features.isNumberOfUncommonWords()) {
					writer.write(tweet.getNumberOfUncommonWords() + t);
				}
			}

			// Semantic Features
			if (Features.isUseSemanticFeatures()) {
				if (Features.isUseOfOpinionWords()) {
					writer.write(tweet.isUseOfOpinionWords() + t);
				}
				if (Features.isUseOfHighlySentimentalWords()) {
					writer.write(tweet.isUseOfHighlySentimentalWords() + t);
				}
				if (Features.isUseOfUncertaintyWords()) {
					writer.write(tweet.isUseOfUncertaintyWords() + t);
				}
				if (Features.isUseOfActiveForm()) {
					writer.write(tweet.isUseOfActiveForm() + t);
				}
				if (Features.isUseOfPassiveForm()) {
					writer.write(tweet.isUseOfPassiveForm() + t);
				}
			}

			// Unigram Features
			if (Features.isUseUnigramFeatures()) {
				if (Features.getUnigramTypeOfPos()!=null) {
					if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getClasses()) {
							if (tweet.getUnigramCountPerClass().keySet().contains(sentiment)) {
								writer.write(tweet.getUnigramCountPerClass().get(sentiment) + t);
							} else {
								writer.write("0" + t);
							}
						}
					} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						for (String sentiment : Parameters.getClasses()) {
							if (Features.isUseUnigramNouns()) {
								if (tweet.getNounCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getNounCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramVerbs()) {
								if (tweet.getVerbCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getVerbCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramAdjectives()) {
								if (tweet.getAdjectiveCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getAdjectiveCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
							if (Features.isUseUnigramAdverbs()) {
								if (tweet.getAdverbCountPerClass().keySet().contains(sentiment)) {
									writer.write(tweet.getAdverbCountPerClass().get(sentiment) + t);
								} else {
									writer.write("0" + t);
								}
							}
						}
					} 
				}
			}

			// Top Words Features
			if (Features.isUseTopWords()) {
				if (Features.getTopWordsType()!=null && Features.getTopWordsTypeOfPos()!=null) {
					if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
						if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
							if (tweet.getTopWordSummed() == null || tweet.getTopWordSummed().length != Parameters.getTopWords().keySet().size()) {
								for (int i = 0; i < Parameters.getTopWords().keySet().size(); i++) {
									writer.write("0" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordSummed().length; i++) {
									writer.write(tweet.getTopWordSummed()[i] + t);
								}
							}
						} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
							int size = 0;
							if (Features.isUseTopWordsNouns()) {
								size = size + Parameters.getTopNouns().keySet().size();
							}
							if (Features.isUseTopWordsVerbs()) {
								size = size + Parameters.getTopVerbs().keySet().size();
							}
							if (Features.isUseTopWordsAdjectives()) {
								size = size + Parameters.getTopAdjectives().keySet().size();
							}
							if (Features.isUseTopWordsAdverbs()) {
								size = size + Parameters.getTopAdverbs().keySet().size();
							}

						}
					} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
						if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {

							int size = 0;
							if (Features.isUseTopWordsNouns()) {
								for (String sentiment : Parameters.getTopNouns().keySet()) {
									size = size + Parameters.getTopNouns().get(sentiment).size();
								}

							}
							if (Features.isUseTopWordsVerbs()) {
								for (String sentiment : Parameters.getTopVerbs().keySet()) {
									size = size + Parameters.getTopVerbs().get(sentiment).size();
								}
							}
							if (Features.isUseTopWordsAdjectives()) {
								for (String sentiment : Parameters.getTopAdjectives().keySet()) {
									size = size + Parameters.getTopAdjectives().get(sentiment).size();
								}
							}
							if (Features.isUseTopWordsAdverbs()) {
								for (String sentiment : Parameters.getTopAdverbs().keySet()) {
									size = size + Parameters.getTopAdverbs().get(sentiment).size();
								}
							}

							if (tweet.getTopWordsSeparatelyBoolean() == null || tweet.getTopWordsSeparatelyBoolean().length != size) {
								for (int i = 0; i < size; i++) {
									writer.write("false" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordsSeparatelyBoolean().length; i++) {
									writer.write(tweet.getTopWordsSeparatelyBoolean()[i] + t);
								}
							}
						} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
							int size = 0;
							for (String sentiment : Parameters.getTopWords().keySet()) {
								size = size + Parameters.getTopWords().get(sentiment).size();
							}
							if (tweet.getTopWordsSeparatelyBoolean() == null
									|| tweet.getTopWordsSeparatelyBoolean().length != size) {
								for (int i = 0; i < size; i++) {
									writer.write("false" + t);
								}
							} else {
								for (int i = 0; i < tweet.getTopWordsSeparatelyBoolean().length; i++) {
									writer.write(tweet.getTopWordsSeparatelyBoolean()[i] + t);
								}
							}

						}
					} 
				}
			}

			// Pattern Features
			if (Features.isUsePatternFeatures()) {
				if (Features.getPatternFeaturesType()!=null) {
					if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
						int size = 0;
						for (String sentiment : Parameters.getSingleLengthPatternsNumeric().keySet()) {
							size = size + Parameters.getSingleLengthPatternsNumeric().get(sentiment).size();
						}
						if (tweet.getPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getPatternScores().length; i++) {
								writer.write(tweet.getPatternScores()[i] + t);
							}
						}
					} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
						int size = 0;
						for (String sentiment : Parameters.getMultiLengthPatternsNumeric().keySet()) {
							size = size + Parameters.getMultiLengthPatternsNumeric().get(sentiment).keySet().size();
						}

						if (tweet.getPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getPatternScores().length; i++) {
								writer.write(tweet.getPatternScores()[i] + t);
							}
						}
					} 
				}
			}
			
			
			// --------------------------------------------------//
			// TODO Add the remaining advanced features here!!!! //
			// --------------------------------------------------//
			
			// Advanced Pattern Features
			if (Features.isUseAdvancedPatternFeatures()) {
				if (Features.getAdvancedPatternFeaturesType()!=null) {
					if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
						int size = 0;
						for (String sentiment : Parameters.getSingleLengthAdvancedPatternsNumeric().keySet()) {
							size = size + Parameters.getSingleLengthAdvancedPatternsNumeric().get(sentiment).size();
						}
						if (tweet.getAdvancedPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getAdvancedPatternScores().length; i++) {
								writer.write(tweet.getAdvancedPatternScores()[i] + t);
							}
						}
					} else if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
						int size = 0;
						for (String sentiment : Parameters.getMultiLengthAdvancedPatternsNumeric().keySet()) {
							size = size + Parameters.getMultiLengthAdvancedPatternsNumeric().get(sentiment).keySet().size();
						}

						if (tweet.getAdvancedPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								writer.write("0" + t);
							}
						} else {
							for (int i = 0; i < tweet.getAdvancedPatternScores().length; i++) {
								writer.write(tweet.getAdvancedPatternScores()[i] + t);
							}
						}
					} 
				}
			}
			
			if (Features.isUseAdvancedUnigramFeatures()) {
				for (boolean b : tweet.getAdvUnigramFeatures()) {
					writer.write(b + t);
				}
			}
			
			writer.write(tweet.getTweetClass());
			
			writer.write(n);
		}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	//======================================//
	//         RECENT FILES-RELATED         //
	//======================================//

	/**
	 * Save the list of recent files opened
	 */
	public static void saveRecentFiles() {
		if (saveRecentFiles(Constants.RECENTFILESFILE)) {
			System.out.println("Recent Files saved successfully");
		} else {
			System.out.println("Couldn't save Recent Files");
		}
	}
	
	/**
	 * Saves the recent files list on a file
	 * @return
	 */
	private static boolean saveRecentFiles(String file) {

		boolean recentFilesListSaved = false;

		File recentFilesFile = new File(file);
		File recentFilesDir = new File(recentFilesFile.getParent());

		if (!recentFilesDir.exists()) {
			try {
				recentFilesDir.mkdir();
			} catch (SecurityException se) {
				AlertBox.display("Error", "The folder you want to save in cannot be accessed!", "OK");
				return false;
			}
		}

		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(recentFilesFile));
			
			for (String recentFile : Loader.getRecentFiles()) {
				writer.write(recentFile + "\n");
			}
			
			recentFilesListSaved = true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return recentFilesListSaved;
	}

	
	//======================================//
	//            EXPORT FEATURES           //
	//======================================//
	
	/**
	 * Export the values of the parameters of the features
	 * @param file
	 */
	public static void exportFeatures(String file) {
		
		String t = "\t";
		String n = "\n";
		
		String features = "";
		
		
		// SENTIMENT FEATURES
		features = features + "useSentimentFeatures" + t + Features.isUseSentimentFeatures() + n;
		
		features = features + "numberOfPositiveWords" + t + Features.isNumberOfPositiveWords() + n;
		features = features + "numberOfNegativeWords" + t + Features.isNumberOfNegativeWords() + n;
		features = features + "numberOfHighlyEmoPositiveWords" + t + Features.isNumberOfHighlyEmoPositiveWords() + n;
		features = features + "numberOfHighlyEmoNegativeWords" + t + Features.isNumberOfHighlyEmoNegativeWords() + n;
		features = features + "numberOfCapitalizedPositiveWords" + t + Features.isNumberOfCapitalizedPositiveWords() + n;
		features = features + "numberOfCapitalizedNegativeWords" + t + Features.isNumberOfCapitalizedNegativeWords() + n;
		features = features + "ratioOfEmotionalWords" + t + Features.isRatioOfEmotionalWords() + n;
		features = features + "numberOfPositiveEmoticons" + t + Features.isNumberOfPositiveEmoticons() + n;
		features = features + "numberOfNegativeEmoticons" + t + Features.isNumberOfNegativeEmoticons() + n;
		features = features + "numberOfNeutralEmoticons" + t + Features.isNumberOfNeutralEmoticons() + n;
		features = features + "numberOfJokingEmoticons" + t + Features.isNumberOfJokingEmoticons() + n;
		features = features + "numberOfPositiveSlangs" + t + Features.isNumberOfPositiveSlangs() + n;
		features = features + "numberOfNegativeSlangs" + t + Features.isNumberOfNegativeSlangs() + n;
		features = features + "numberOfPositiveHashtags" + t + Features.isNumberOfPositiveHashtags() + n;
		features = features + "numberOfNegativeHashtags" + t + Features.isNumberOfNegativeHashtags() + n;
		features = features + "contrastWordsVsWords" + t + Features.isContrastWordsVsWords() + n;
		features = features + "contrastWordsVsHashtags" + t + Features.isContrastWordsVsHashtags() + n;
		features = features + "contrastWordsVsEmoticons" + t + Features.isContrastWordsVsEmoticons() + n;
		features = features + "contrastHashtagsVsHashtags" + t + Features.isContrastHashtagsVsHashtags() + n;
		features = features + "contrastHashtagsVsEmoticons" + t + Features.isContrastHashtagsVsEmoticons() + n;

		
		// PUNCTUATION FEATURES
		features = features + "usePunctuationFeatures" + t + Features.isUsePunctuationFeatures() + n;
		
		features = features + "numberOfDots" + t + Features.isNumberOfDots() + n;
		features = features + "numberOfCommas" + t + Features.isNumberOfCommas() + n;
		features = features + "numberOfSemicolons" + t + Features.isNumberOfSemicolons() + n;
		features = features + "numberOfExclamationMarks" + t + Features.isNumberOfExclamationMarks() + n;
		features = features + "numberOfQuestionMarks" + t + Features.isNumberOfQuestionMarks() + n;
		features = features + "numberOfParentheses" + t + Features.isNumberOfParentheses() + n;
		features = features + "numberOfBrackets" + t + Features.isNumberOfBrackets() + n;
		features = features + "numberOfBraces" + t + Features.isNumberOfBraces() + n;
		features = features + "numberOfApostrophes" + t + Features.isNumberOfApostrophes() + n;
		features = features + "numberOfQuotationMarks" + t + Features.isNumberOfQuotationMarks() + n;
		features = features + "totalNumberOfCharacters" + t + Features.isTotalNumberOfCharacters() + n;
		features = features + "totalNumberOfWords" + t + Features.isTotalNumberOfWords() + n;
		features = features + "totalNumberOfSentences" + t + Features.isTotalNumberOfSentences() + n;
		features = features + "averageNumberOfCharactersPerSentence" + t + Features.isAverageNumberOfCharactersPerSentence() + n;
		features = features + "averageNumberOfWordsPerSentence" + t + Features.isAverageNumberOfWordsPerSentence() + n;

		
		// STYLISTIC FEATURES
		features = features + "useStylisticFeatures" + t + Features.isUseStylisticFeatures() + n;
		
		features = features + "numberOfNouns" + t + Features.isNumberOfNouns() + n;
		features = features + "ratioOfNouns" + t + Features.isRatioOfNouns() + n;
		features = features + "numberOfVerbs" + t + Features.isNumberOfVerbs() + n;
		features = features + "ratioOfVerbs" + t + Features.isRatioOfVerbs() + n;
		features = features + "numberOfAdjectives" + t + Features.isNumberOfAdjectives() + n;
		features = features + "ratioOfAdjectives" + t + Features.isRatioOfAdjectives() + n;
		features = features + "numberOfAdverbs" + t + Features.isNumberOfAdverbs() + n;
		features = features + "ratioOfAdverbs" + t + Features.isRatioOfAdverbs() + n;
		features = features + "useOfSymbols" + t + Features.isUseOfSymbols() + n;
		features = features + "useOfComparativeForm" + t + Features.isUseOfComparativeForm() + n;
		features = features + "useOfSuperlativeForm" + t + Features.isUseOfSuperlativeForm() + n;
		features = features + "useOfProperNouns" + t + Features.isUseOfProperNouns() + n;
		features = features + "totalNumberOfParticles" + t + Features.isTotalNumberOfParticles() + n;
		features = features + "averageNumberOfParticles" + t + Features.isAverageNumberOfParticles() + n;
		features = features + "totalNumberOfInterjections" + t + Features.isTotalNumberOfInterjections() + n;
		features = features + "averageNumberOfInterjections" + t + Features.isAverageNumberOfInterjections() + n;
		features = features + "totalNumberOfPronouns" + t + Features.isTotalNumberOfPronouns() + n;
		features = features + "averageNumberOfPronouns" + t + Features.isAverageNumberOfPronouns() + n;
		features = features + "totalNumberOfPronounsGroup1" + t + Features.isTotalNumberOfPronounsGroup1() + n;
		features = features + "averageNumberOfPronounsGroup1" + t + Features.isAverageNumberOfPronounsGroup1() + n;
		features = features + "totalNumberOfPronounsGroup2" + t + Features.isTotalNumberOfPronounsGroup2() + n;
		features = features + "averageNumberOfPronounsGroup2" + t + Features.isAverageNumberOfPronounsGroup2() + n;
		features = features + "useOfNegation" + t + Features.isUseOfNegation() + n;
		features = features + "useOfUncommonWords" + t + Features.isUseOfUncommonWords() + n;
		features = features + "numberOfUncommonWords" + t + Features.isNumberOfUncommonWords() + n;

		
		// SEMANTIC FEATURES
		features = features + "useSemanticFeatures" + t + Features.isUseSemanticFeatures() + n;
		
		features = features + "useOfOpinionWords" + t + Features.isUseOfOpinionWords() + n;
		features = features + "useOfHighlySentimentalWords" + t + Features.isUseOfHighlySentimentalWords() + n;
		features = features + "useOfUncertaintyWords" + t + Features.isUseOfUncertaintyWords() + n;
		features = features + "useOfActiveForm" + t + Features.isUseOfActiveForm() + n;
		features = features + "useOfPassiveForm" + t + Features.isUseOfPassiveForm() + n;

		
		// UNIGRAM FEATURES
		features = features + "useUnigramFeatures" + t + Features.isUseUnigramFeatures() + n;

		features = features + "depth" + t + Features.getDepth() + n;
		features = features + "useUnigramNouns" + t + Features.isUseUnigramNouns() + n;
		features = features + "useUnigramVerbs" + t + Features.isUseUnigramVerbs() + n;
		features = features + "useUnigramAdjectives" + t + Features.isUseUnigramAdjectives() + n;
		features = features + "useUnigramAdverbs" + t + Features.isUseUnigramAdverbs() + n;
		features = features + "unigramTypeOfPos" + t + Features.getUnigramTypeOfPos() + n;
		features = features + "zeroTaken" + t + Features.isZeroTaken() + n;
		features = features + "oppositeTaken" + t + Features.isOppositeTaken() + n;
		
		
		// TOP WORDS FEATURES
		features = features + "useTopWords" + t + Features.isUseTopWords() + n;
		
		features = features + "useTopWordsNouns" + t + Features.isUseTopWordsNouns() + n;
		features = features + "useTopWordsVerbs" + t + Features.isUseTopWordsVerbs() + n;
		features = features + "useTopWordsAdjectives" + t + Features.isUseTopWordsAdjectives() + n;
		features = features + "useTopWordsAdverbs" + t + Features.isUseTopWordsAdverbs() + n;
		
		features = features + "topWordsTypeOfPos" + t + Features.getTopWordsTypeOfPos() + n;
		
		features = features + "numberOfTopWordsPerClass" + t + Features.getNumberOfTopWordsPerClass() + n;
		features = features + "numberOfTopWordsPerPos" + t + Features.getNumberOfTopWordsPerPos() + n;
		features = features + "topWordsType" + t + Features.getTopWordsType() + n;
		features = features + "topWordsMinRatio" + t + Features.getTopWordsMinRatio() + n;
		features = features + "topWordsMinOccurrence" + t + Features.getTopWordsMinOccurrence() + n;
		
		
		// PATTERN FEATURES
		features = features + "usePatternFeatures" + t + Features.isUsePatternFeatures() + n;

		features = features + "patternFeaturesType" + t + Features.getPatternFeaturesType() + n;
		features = features + "patternLength" + t + Features.getPatternLength() + n;
		features = features + "numberOfPatternsPerClass" + t + Features.getNumberOfPatternsPerClass() + n;
		features = features + "minPatternLength" + t + Features.getMinPatternLength() + n;
		features = features + "maxPatternLength" + t + Features.getMaxPatternLength() + n;
		features = features + "numberOfPosCategories" + t + Features.getNumberOfPosCategories()+ n;
		
		features = features + "category1" + t + Features.isCategory1() + n;
		features = features + "category2" + t + Features.isCategory2() + n;
		features = features + "category3" + t + Features.isCategory3() + n;
		features = features + "category4" + t + Features.isCategory4() + n;
		
		features = features + "action1" + t + Features.getAction1() + n;
		features = features + "action2" + t + Features.getAction2() + n;
		features = features + "action3" + t + Features.getAction3() + n;
		features = features + "action4" + t + Features.getAction4() + n;
		
		features = features + "replacement1" + t + Features.getReplacement1() + n;
		features = features + "replacement2" + t + Features.getReplacement2() + n;
		features = features + "replacement3" + t + Features.getReplacement3() + n;
		features = features + "replacement4" + t + Features.getReplacement4() + n;
		
		features = features + "patternsMinOccurrence" + t + Features.getPatternsMinOccurrence() + n;
		features = features + "alpha" + t + Features.getAlpha() + n;
		features = features + "gamma" + t + Features.getGamma() + n;
		features = features + "basicPatternsKnn" + t + Features.getBasicPatternsKnn() + n;
		
		features = features + "categories" + t;
		
		if (Features.getCategories() !=null) {
			features = features + Features.getCategories()[0];
			for (int i = 1; i < Features.getCategories().length; i++) {
				features = features + "#" + Features.getCategories()[i];
			} 
		} else {
			features = features + "null";
		}
		features = features + n;
		
		//=====================================================================
		features = features + "categoriesMap" + t;
		
		if (Features.getCategoriesMap() !=null) {
			String mapSerialized = "";
			for (String key : Features.getCategoriesMap().keySet()) {
				mapSerialized = mapSerialized + key+ "_" + Features.getCategoriesMap().get(key) + "#";
			}
			mapSerialized = mapSerialized.substring(0, mapSerialized.length()-1);
			features = features + mapSerialized;
		} else {
			features = features + "null";
		}
		features = features + n;
		//=====================================================================
		
		// ADVANCED SENTIMENT FEATURES
		features = features + "useAdvancedSentimentFeatures" + t + Features.isUseAdvancedSentimentFeatures() + n;
		
		features = features + "typeOfAdvancedSentimentFeatures" + t + Features.getTypeOfAdvancedSentimentFeatures() + n;
		features = features + "numberOfWordsBefore" + t + Features.getNumberOfWordsBefore() + n;
		features = features + "numberOfWordsAfter" + t + Features.getNumberOfWordsAfter() + n;
		
		features = features + "addSentimentClassInformation" + t + Features.isAddSentimentClassInformation() + n;
		
		
		// ADVANCED SEMANTIC FEATURES
		features = features + "useAdvancedSemanticFeatures" + t + Features.isUseAdvancedSemanticFeatures() + n;
		
		features = features + "wordsIntoAccount" + t + Features.getWordsIntoAccount() + n;
		features = features + "countOtherWords" + t + Features.isCountOtherWords() + n;
		features = features + "advancedSemanticPos" + t + Features.getAdvancedSemanticPos() + n;
		
		features = features + "advUseOfPositiveWords" + t + Features.isAdvUseOfPositiveWords() + n;
		features = features + "advUseOfNegativeWords" + t + Features.isAdvUseOfNegativeWords() + n;
		features = features + "advUseOfHighlyPositiveWords" + t + Features.isAdvUseOfHighlyPositiveWords() + n;
		features = features + "advUseOfHighlyNegativeWords" + t + Features.isAdvUseOfHighlyNegativeWords() + n;
		
		features = features + "advUseOfOpinionWords" + t + Features.isAdvUseOfOpinionWords() + n;
		features = features + "advUseOfUncertaintyWords" + t + Features.isAdvUseOfUncertaintyWords() + n;
		features = features + "advUseOfActiveForm" + t + Features.isAdvUseOfActiveForm() + n;
		features = features + "advUseOfPassiveForm" + t + Features.isAdvUseOfPassiveForm() + n;
		
		
		// ADVANCED PATTERN FEATURES
		
		features = features + "useAdvancedPatternFeatures" + t + Features.isUseAdvancedPatternFeatures() + n;
		
		features = features + "advancedPatternFeaturesType" + t + Features.getAdvancedPatternFeaturesType() + n;
		features = features + "advancedPatternLength" + t + Features.getAdvancedPatternLength() + n;
		features = features + "advancedNumberOfPatternsPerClass" + t + Features.getAdvancedNumberOfPatternsPerClass() + n;
		features = features + "advancedMinPatternLength" + t + Features.getAdvancedMinPatternLength() + n;
		features = features + "advancedMaxPatternLength" + t + Features.getAdvancedMaxPatternLength() + n;
		features = features + "advancedPatternsMinOccurrence" + t + Features.getAdvancedPatternsMinOccurrence() + n;
		features = features + "advancedPatternAlpha" + t + Features.getAdvancedPatternAlpha() + n;
		features = features + "advancedPatternGamma" + t + Features.getAdvancedPatternGamma() + n;
		features = features + "advancedPatternsKnn" + t + Features.getAdvancedPatternsKnn() + n;
		
		
		// ADVANCED UNIGRAM FEATURES
		
		features = features + "useAdvancedUnigramFeatures" + t + Features.isUseAdvancedUnigramFeatures() + n;
		features = features + "isLemma" + t + Features.getIsLemma() + n;
		
		// WRITE THE FEATURES ON A FILE

		File exportedFile = new File(file);
		File exportedFileDirectory = new File(exportedFile.getParent());

		boolean folderCreated = false;
		if (!exportedFileDirectory.exists()) {
			try {
				exportedFileDirectory.mkdir();
				folderCreated = true;
			} catch (SecurityException se) {
				AlertBox.display("Error", "The folder you want to save in cannot be accessed!", "OK");
			}
		} else {
			folderCreated = true;
		}

		BufferedWriter writer = null;

		if (folderCreated) {

			try {
				if (!exportedFile.exists()) {
					exportedFile.createNewFile();
				}
				writer = new BufferedWriter(new FileWriter(exportedFile));
				writer.write(features);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	//======================================//
	//        EXPORT EMOTIONAL WORDS        //
	//======================================//
	
	/**
	 * Export the set of Emotional words (after enrichment)
	 * @param words
	 * @param location
	 */
	public static void exportEmotionalWords(ArrayList<Word> words, String location) {
		
		BufferedWriter BW = null;

		try {
			File outputFile = new File(location);
			if (!outputFile.exists())
				outputFile.createNewFile();
			BW = new BufferedWriter(new FileWriter(outputFile));
			
			for (Word word : words) {
				BW.write(word.getWord() + "\t" + word.getPosTag() + "\t" + word.getEmotion() + "\t" + word.getSentimentScore() + "\n");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (BW != null) {
				try {
					BW.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}	
	
	//======================================//
	//            EXPORT PATTERNS           //
	//======================================//
	
	public static void exportPatterns(ArrayList<Tweet> tweets, int patternStdLength, String location) {
		BufferedWriter BW = null;

		try {
			File outputFile = new File(location);
			if (!outputFile.exists())
				outputFile.createNewFile();
			BW = new BufferedWriter(new FileWriter(outputFile));
			
			for (Tweet tweet : tweets) {
				
				String[] pattern = TextProcessing.extractFullPatternVecor(tweet.getTokens(), tweet.getLemmas(), tweet.getTags(), tweet.getTokensScore());
				
				if (pattern.length < patternStdLength) {
					String[] padding = new String[patternStdLength - pattern.length];
					Arrays.fill(padding, "<PAD/>");
					String[] p = (String[])ArrayUtils.addAll(pattern, padding);
					
					String str = String.join("##", p);
					
					BW.write(tweet.getId() + "\t" + str + "\t" + tweet.getTweetClass() + "\n");
				} else {
					for (int i = 0; i < pattern.length - patternStdLength; i++) {
						int beg = i;
						int end = i + patternStdLength;
						
						String[] p = IntStream.range(beg, end).mapToObj(j -> pattern[j]).toArray(String[]::new);
						String str = String.join("##", p);
						BW.write(tweet.getId() + "\t" + str + "\t" + tweet.getTweetClass() + "\n");
					}
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (BW != null) {
				try {
					BW.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	// TODO remove (check if the other method has no issues then remove this)
	public static void saveArffFileBackup(String file, ArrayList<Tweet> tweets) {
		String t = ",";
		String n = "\n";
		
		// ---------------------------//
		//       Add the titles       //
		// ---------------------------//
		String title = "@relation Multi_Class_Sentiment_Analysis" + n + n;
		// Sentiment Features
		if (Features.isUseSentimentFeatures()) {
			if (Features.isNumberOfPositiveWords()) {
				title = title + "@attribute Number_Of_Positive_Words numeric" + n;
			}

			if (Features.isNumberOfNegativeWords()) {
				title = title + "@attribute Number_Of_Negative_Words numeric" + n;
			}
			if (Features.isNumberOfHighlyEmoPositiveWords()) {
				title = title + "@attribute Number_Of_Highly_Emo_Positive_Words numeric" + n;
			}
			if (Features.isNumberOfHighlyEmoNegativeWords()) {
				title = title + "@attribute Number_Of_Highly_Emo_Negative_Words numeric" + n;
			}
			if (Features.isNumberOfCapitalizedPositiveWords()) {
				title = title + "@attribute Number_Of_Capitalized_Positive_Words numeric" + n;
			}
			if (Features.isNumberOfCapitalizedNegativeWords()) {
				title = title + "@attribute Number_Of_Capitalized_Negative_Words numeric" + n;
			}
			if (Features.isRatioOfEmotionalWords()) {
				title = title + "@attribute Ratio_Of_Emotional_Words numeric" + n;
			}

			if (Features.isNumberOfPositiveEmoticons()) {
				title = title + "@attribute Number_Of_Positive_Emoticons numeric" + n;
			}
			if (Features.isNumberOfNegativeEmoticons()) {
				title = title + "@attribute Number_Of_Negative_Emoticons numeric" + n;
			}
			if (Features.isNumberOfNeutralEmoticons()) {
				title = title + "@attribute Number_Of_Neutral_Emoticons numeric" + n;
			}
			if (Features.isNumberOfJokingEmoticons()) {
				title = title + "@attribute Number_Of_Joking_Emoticons numeric" + n;
			}

			if (Features.isNumberOfPositiveSlangs()) {
				title = title + "@attribute Number_Of_Positive_Slangs numeric" + n;
			}
			if (Features.isNumberOfNegativeSlangs()) {
				title = title + "@attribute Number_Of_Negative_Slangs numeric" + n;
			}

			if (Features.isNumberOfPositiveHashtags()) {
				title = title + "@attribute NumberOfPositiveHashtags numeric" + n;
			}
			if (Features.isNumberOfNegativeHashtags()) {
				title = title + "@attribute NumberOfNegativeHashtags numeric" + n;
			}

			if (Features.isContrastWordsVsWords()) {
				title = title + "@attribute Contrast_Words_Vs_Words {true, false}" + n;
			}
			if (Features.isContrastWordsVsHashtags()) {
				title = title + "@attribute Contrast_Words_Vs_Hashtags {true, false}" + n;
			}
			if (Features.isContrastWordsVsEmoticons()) {
				title = title + "@attribute Contrast_Words_Vs_Emoticons {true, false}" + n;
			}
			if (Features.isContrastHashtagsVsHashtags()) {
				title = title + "@attribute Contrast_Hashtags_Vs_Hashtags {true, false}" + n;
			}
			if (Features.isContrastHashtagsVsEmoticons()) {
				title = title + "@attribute Contrast_Hashtags_Vs_Emoticons {true, false}" + n;
			}
		}

		// Punctuation Features
		if (Features.isUsePunctuationFeatures()) {
			if (Features.isNumberOfDots()) {
				title = title + "@attribute Number_Of_Dots numeric" + n;
			}
			if (Features.isNumberOfCommas()) {
				title = title + "@attribute Number_Of_Commas numeric" + n;
			}
			if (Features.isNumberOfSemicolons()) {
				title = title + "@attribute Number_Of_Semicolons numeric" + n;
			}
			if (Features.isNumberOfExclamationMarks()) {
				title = title + "@attribute Number_Of_Exclamation_Marks numeric" + n;
			}
			if (Features.isNumberOfQuestionMarks()) {
				title = title + "@attribute Number_Of_Question_Marks numeric" + n;
			}

			if (Features.isNumberOfParentheses()) {
				title = title + "@attribute Number_Of_Parentheses numeric" + n;
			}
			if (Features.isNumberOfBrackets()) {
				title = title + "@attribute Number_Of_Brackets numeric" + n;
			}
			if (Features.isNumberOfBraces()) {
				title = title + "@attribute Number_Of_Braces numeric" + n;
			}

			if (Features.isNumberOfApostrophes()) {
				title = title + "@attribute Number_Of_Apostrophes numeric" + n;
			}
			if (Features.isNumberOfQuotationMarks()) {
				title = title + "@attribute Number_Of_Quotation_Marks numeric" + n;
			}

			if (Features.isTotalNumberOfCharacters()) {
				title = title + "@attribute Total_Number_Of_Characters numeric" + n;
			}
			if (Features.isTotalNumberOfWords()) {
				title = title + "@attribute Total_Number_Of_Words numeric" + n;
			}
			if (Features.isTotalNumberOfSentences()) {
				title = title + "@attribute Total_Number_Of_Sentences numeric" + n;
			}
			if (Features.isAverageNumberOfCharactersPerSentence()) {
				title = title + "@attribute Average_Number_Of_Characters_Per_Sentence numeric" + n;
			}
			if (Features.isAverageNumberOfWordsPerSentence()) {
				title = title + "@attribute Average_Number_Of_Words_Per_Sentence numeric" + n;
			}
		}

		// Stylistic Features
		if (Features.isUseStylisticFeatures()) {

			if (Features.isNumberOfNouns()) {
				title = title + "@attribute Number_Of_Nouns numeric" + n;
			}
			if (Features.isRatioOfNouns()) {
				title = title + "@attribute Ratio_Of_Nouns numeric" + n;
			}
			if (Features.isNumberOfVerbs()) {
				title = title + "@attribute Number_Of_Verbs numeric" + n;
			}
			if (Features.isRatioOfVerbs()) {
				title = title + "@attribute Ratio_Of_Verbs numeric" + n;
			}
			if (Features.isNumberOfAdjectives()) {
				title = title + "@attribute Number_Of_Adjectives numeric" + n;
			}
			if (Features.isRatioOfAdjectives()) {
				title = title + "@attribute Ratio_Of_Adjectives numeric" + n;
			}
			if (Features.isNumberOfAdverbs()) {
				title = title + "@attribute Number_Of_Adverbs numeric" + n;
			}
			if (Features.isRatioOfAdverbs()) {
				title = title + "@attribute Ratio_Of_Adverbs numeric" + n;
			}

			if (Features.isUseOfSymbols()) {
				title = title + "@attribute Use_Of_Symbols {true, false}" + n;
			}
			if (Features.isUseOfComparativeForm()) {
				title = title + "@attribute Use_Of_Comparative_Form {true, false}" + n;
			}
			if (Features.isUseOfSuperlativeForm()) {
				title = title + "@attribute Use_Of_Superlative_Form {true, false}" + n;
			}
			if (Features.isUseOfProperNouns()) {
				title = title + "@attribute Use_Of_Proper_Nouns {true, false}" + n;
			}

			if (Features.isTotalNumberOfParticles()) {
				title = title + "@attribute Total_Number_Of_Particles numeric" + n;
			}
			if (Features.isAverageNumberOfParticles()) {
				title = title + "@attribute Average_Number_Of_Particles numeric" + n;
			}
			if (Features.isTotalNumberOfInterjections()) {
				title = title + "@attribute Total_Number_Of_Interjections numeric" + n;
			}
			if (Features.isAverageNumberOfInterjections()) {
				title = title + "@attribute Average_Number_Of_Interjections numeric" + n;
			}
			if (Features.isTotalNumberOfPronouns()) {
				title = title + "@attribute Total_Number_Of_Pronouns numeric" + n;
			}
			if (Features.isAverageNumberOfPronouns()) {
				title = title + "@attribute Average_Number_Of_Pronouns numeric" + n;
			}
			if (Features.isTotalNumberOfPronounsGroup1()) {
				title = title + "@attribute Total_Number_Of_Pronouns_Group_1 numeric" + n;
			}
			if (Features.isAverageNumberOfPronounsGroup1()) {
				title = title + "@attribute Average_Number_Of_Pronouns_Group_1 numeric" + n;
			}
			if (Features.isTotalNumberOfPronounsGroup2()) {
				title = title + "@attribute Total_Number_Of_Pronouns_Group_2 numeric" + n;
			}
			if (Features.isAverageNumberOfPronounsGroup2()) {
				title = title + "@attribute Average_Number_Of_Pronouns_Group_2 numeric" + n;
			}
			if (Features.isUseOfNegation()) {
				title = title + "@attribute Use_Of_Negation {true, false}" + n;
			}
			if (Features.isUseOfUncommonWords()) {
				title = title + "@attribute Use_Of_Uncommon_Words {true, false}" + n;
			}
			if (Features.isNumberOfUncommonWords()) {
				title = title + "@attribute Number_Of_Uncommon_Words numeric" + n;
			}
		}

		// Semantic Features
		if (Features.isUseSemanticFeatures()) {
			if (Features.isUseOfOpinionWords()) {
				title = title + "@attribute Use_Of_Opinion_Words {true, false}" + n;
			}
			if (Features.isUseOfHighlySentimentalWords()) {
				title = title + "@attribute Use_Of_Highly_Sentimental_Words {true, false}" + n;
			}
			if (Features.isUseOfUncertaintyWords()) {
				title = title + "@attribute Use_Of_Uncertainty_Words {true, false}" + n;
			}
			if (Features.isUseOfActiveForm()) {
				title = title + "@attribute Use_Of_Active_Form {true, false}" + n;
			}
			if (Features.isUseOfPassiveForm()) {
				title = title + "@attribute Use_Of_Passive_Form {true, false}" + n;
			}
		}
		
		// Unigram Features
		if (Features.isUseUnigramFeatures()) {
			if (Features.getUnigramTypeOfPos()!=null) {
				if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
					for (String sentiment : Parameters.getClasses()) {
						title = title + "@attribute Unigrams[" + sentiment + "] numeric" + n;
					}
				} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
					for (String sentiment : Parameters.getClasses()) {
						if (Features.isUseUnigramNouns()) {
							title = title + "@attribute Nouns[" + sentiment + "] numeric" + n;
						}
						if (Features.isUseUnigramVerbs()) {
							title = title + "@attribute Verbs[" + sentiment + "] numeric" + n;
						}
						if (Features.isUseUnigramAdjectives()) {
							title = title + "@attribute Adjectives[" + sentiment + "] numeric" + n;
						}
						if (Features.isUseUnigramAdverbs()) {
							title = title + "@attribute Adverb[" + sentiment + "] numeric" + n;
						}
					}
				} 
			}
		}

		// Top Words Features
		if (Features.isUseTopWords()) {
			if (Features.getTopWordsType()!=null && Features.getTopWordsTypeOfPos()!=null) {
				if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
					if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getTopWords().keySet()) {
							title = title + "@attribute Top_Words[" + sentiment + "] numeric" + n;
						}
					} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopNouns().keySet()) {
								title = title + "@attribute Top_Nouns[" + sentiment + "] numeric" + n;
							}
						}
						if (Features.isUseTopWordsVerbs()) {
							for (String sentiment : Parameters.getTopVerbs().keySet()) {
								title = title + "@attribute Top_Verbs[" + sentiment + "] numeric" + n;
							}
						}
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopAdjectives().keySet()) {
								title = title + "@attribute Top_Adjectives[" + sentiment + "] numeric" + n;
							}
						}
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopAdverbs().keySet()) {
								title = title + "@attribute Top_Adverbs[" + sentiment + "] numeric" + n;
							}
						}
					}
				} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
					if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getTopWords().keySet()) {
							for (String word : Parameters.getTopWords().get(sentiment)) {
								title = title + "@attribute existance[Word_" + sentiment + "_" + word + "] {true, false}" + n;
							}
						}
					} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						if (Features.isUseTopWordsNouns()) {
							for (String sentiment : Parameters.getTopNouns().keySet()) {
								for (String word : Parameters.getTopNouns().get(sentiment)) {
									title = title + "@attribute existance[Noun_" + sentiment + "_" + word + "] {true, false}" + n;
								}
							}
						}
						if (Features.isUseTopWordsVerbs()) {
							for (String sentiment : Parameters.getTopVerbs().keySet()) {
								for (String word : Parameters.getTopVerbs().get(sentiment)) {
									title = title + "@attribute existance[Verb_" + sentiment + "_" + word + "] {true, false}" + n;
								}
							}
						}
						if (Features.isUseTopWordsAdjectives()) {
							for (String sentiment : Parameters.getTopAdjectives().keySet()) {
								for (String word : Parameters.getTopAdjectives().get(sentiment)) {
									title = title + "@attribute existance[Adjective_" + sentiment + "_" + word + "] {true, false}" + n;
								}
							}
						}
						if (Features.isUseTopWordsAdverbs()) {
							for (String sentiment : Parameters.getTopAdverbs().keySet()) {
								for (String word : Parameters.getTopAdverbs().get(sentiment)) {
									title = title + "@attribute existance[Adverb_" + sentiment + "_" + word + "] {true, false}" + n;
								}
							}
						}
					}
				} 
			}
		}

		// Pattern Features
		if (Features.isUsePatternFeatures()) {
			if (Features.getPatternFeaturesType()!=null) {
				if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
					for (String sentiment : Parameters.getSingleLengthPatterns().keySet()) {
						for (int i = 0; i < Parameters.getSingleLengthAdvancedPatterns().get(sentiment).size(); i++) {
							title = title + "@attribute Pattern_" + sentiment + "[" + i + "] {true, false}" + n;
						}
					}
				} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
					for (String sentiment : Parameters.getMultiLengthPatterns().keySet()) {
						for (int i : Parameters.getMultiLengthPatterns().get(sentiment).keySet()) {
							title = title + "@attribute Patterns_" + sentiment + "[Length=" + i + "] numeric" + n;
						}
					}
				} 
			}
		}
		
		// Add the class attribute
		title = title + "@attribute CLASS {";
		
		title = title + Parameters.getClasses().get(0);
		
		for (int i = 1; i< Parameters.getClasses().size(); i++) {
			title = title + ", " + Parameters.getClasses().get(i);
		}
		title = title + "}";
		
		title = title + n;
		
		title = title + "@data" + n;
		
		// ---------------------------//
		// Add the Data //
		// ---------------------------//

		String data = n;

		for (Tweet tweet : tweets) {
			
			// Sentiment Features
			if (Features.isUseSentimentFeatures()) {
				if (Features.isNumberOfPositiveWords()) {
					data = data + tweet.getNumberOfPositiveWords() + t;
				}

				if (Features.isNumberOfNegativeWords()) {
					data = data + tweet.getNumberOfNegativeWords() + t;
				}
				if (Features.isNumberOfHighlyEmoPositiveWords()) {
					data = data + tweet.getNumberOfHighlyEmoPositiveWords() + t;
				}
				if (Features.isNumberOfHighlyEmoNegativeWords()) {
					data = data + tweet.getNumberOfHighlyEmoNegativeWords() + t;
				}
				if (Features.isNumberOfCapitalizedPositiveWords()) {
					data = data + tweet.getNumberOfCapitalizedPositiveWords() + t;
				}
				if (Features.isNumberOfCapitalizedNegativeWords()) {
					data = data + tweet.getNumberOfCapitalizedNegativeWords() + t;
				}
				if (Features.isRatioOfEmotionalWords()) {
					data = data + tweet.getRatioOfEmotionalWords() + t;
				}

				if (Features.isNumberOfPositiveEmoticons()) {
					data = data + tweet.getNumberOfPositiveEmoticons() + t;
				}
				if (Features.isNumberOfNegativeEmoticons()) {
					data = data + tweet.getNumberOfNegativeEmoticons() + t;
				}
				if (Features.isNumberOfNeutralEmoticons()) {
					data = data + tweet.getNumberOfNeutralEmoticons() + t;
				}
				if (Features.isNumberOfJokingEmoticons()) {
					data = data + tweet.getNumberOfJokingEmoticons() + t;
				}

				if (Features.isNumberOfPositiveSlangs()) {
					data = data + tweet.getNumberOfPositiveSlangs() + t;
				}
				if (Features.isNumberOfNegativeSlangs()) {
					data = data + tweet.getNumberOfNegativeSlangs() + t;
				}

				if (Features.isNumberOfPositiveHashtags()) {
					data = data + tweet.getNumberOfPositiveHashtags() + t;
				}
				if (Features.isNumberOfNegativeHashtags()) {
					data = data + tweet.getNumberOfNegativeHashtags() + t;
				}

				if (Features.isContrastWordsVsWords()) {
					data = data + tweet.getContrastWordsVsWords() + t;
				}
				if (Features.isContrastWordsVsHashtags()) {
					data = data + tweet.getContrastWordsVsHashtags() + t;
				}
				if (Features.isContrastWordsVsEmoticons()) {
					data = data + tweet.getContrastWordsVsEmoticons() + t;
				}
				if (Features.isContrastHashtagsVsHashtags()) {
					data = data + tweet.getContrastHashtagsVsHashtags() + t;
				}
				if (Features.isContrastHashtagsVsEmoticons()) {
					data = data + tweet.getContrastHashtagsVsEmoticons() + t;
				}
			}

			// Punctuation Features
			if (Features.isUsePunctuationFeatures()) {
				if (Features.isNumberOfDots()) {
					data = data + tweet.getNumberOfDots() + t;
				}
				if (Features.isNumberOfCommas()) {
					data = data + tweet.getNumberOfCommas() + t;
				}
				if (Features.isNumberOfSemicolons()) {
					data = data + tweet.getNumberOfSemicolons() + t;
				}
				if (Features.isNumberOfExclamationMarks()) {
					data = data + tweet.getNumberOfExclamationMarks() + t;
				}
				if (Features.isNumberOfQuestionMarks()) {
					data = data + tweet.getNumberOfQuestionMarks() + t;
				}

				if (Features.isNumberOfParentheses()) {
					data = data + tweet.getNumberOfParentheses() + t;
				}
				if (Features.isNumberOfBrackets()) {
					data = data + tweet.getNumberOfBrackets() + t;
				}
				if (Features.isNumberOfBraces()) {
					data = data + tweet.getNumberOfBraces() + t;
				}

				if (Features.isNumberOfApostrophes()) {
					data = data + tweet.getNumberOfApostrophes() + t;
				}
				if (Features.isNumberOfQuotationMarks()) {
					data = data + tweet.getNumberOfQuotationMarks() + t;
				}

				if (Features.isTotalNumberOfCharacters()) {
					data = data + tweet.getTotalNumberOfCharacters() + t;
				}
				if (Features.isTotalNumberOfWords()) {
					data = data + tweet.getTotalNumberOfWords() + t;
				}
				if (Features.isTotalNumberOfSentences()) {
					data = data + tweet.getTotalNumberOfSentences() + t;
				}
				if (Features.isAverageNumberOfCharactersPerSentence()) {
					data = data + tweet.getAverageNumberOfCharactersPerSentence() + t;
				}
				if (Features.isAverageNumberOfWordsPerSentence()) {
					data = data + tweet.getAverageNumberOfWordsPerSentence() + t;
				}
			}

			// Stylistic Features
			if (Features.isUseStylisticFeatures()) {

				if (Features.isNumberOfNouns()) {
					data = data + tweet.getNumberOfNouns() + t;
				}
				if (Features.isRatioOfNouns()) {
					data = data + tweet.getRatioOfNouns() + t;
				}
				if (Features.isNumberOfVerbs()) {
					data = data + tweet.getNumberOfVerbs() + t;
				}
				if (Features.isRatioOfVerbs()) {
					data = data + tweet.getRatioOfVerbs() + t;
				}
				if (Features.isNumberOfAdjectives()) {
					data = data + tweet.getNumberOfAdjectives() + t;
				}
				if (Features.isRatioOfAdjectives()) {
					data = data + tweet.getRatioOfAdjectives() + t;
				}
				if (Features.isNumberOfAdverbs()) {
					data = data + tweet.getNumberOfAdverbs() + t;
				}
				if (Features.isRatioOfAdverbs()) {
					data = data + tweet.getRatioOfAdverbs() + t;
				}

				if (Features.isUseOfSymbols()) {
					data = data + tweet.isUseOfSymbols() + t;
				}
				if (Features.isUseOfComparativeForm()) {
					data = data + tweet.isUseOfComparativeForm() + t;
				}
				if (Features.isUseOfSuperlativeForm()) {
					data = data + tweet.isUseOfSuperlativeForm() + t;
				}
				if (Features.isUseOfProperNouns()) {
					data = data + tweet.isUseOfProperNouns() + t;
				}

				if (Features.isTotalNumberOfParticles()) {
					data = data + tweet.getTotalNumberOfParticles() + t;
				}
				if (Features.isAverageNumberOfParticles()) {
					data = data + tweet.getAverageNumberOfParticles() + t;
				}
				if (Features.isTotalNumberOfInterjections()) {
					data = data + tweet.getTotalNumberOfInterjections() + t;
				}
				if (Features.isAverageNumberOfInterjections()) {
					data = data + tweet.getAverageNumberOfInterjections() + t;
				}
				if (Features.isTotalNumberOfPronouns()) {
					data = data + tweet.getTotalNumberOfPronouns() + t;
				}
				if (Features.isAverageNumberOfPronouns()) {
					data = data + tweet.getAverageNumberOfPronouns() + t;
				}
				if (Features.isTotalNumberOfPronounsGroup1()) {
					data = data + tweet.getTotalNumberOfPronounsGroup1() + t;
				}
				if (Features.isAverageNumberOfPronounsGroup1()) {
					data = data + tweet.getAverageNumberOfPronounsGroup1() + t;
				}
				if (Features.isTotalNumberOfPronounsGroup2()) {
					data = data + tweet.getTotalNumberOfPronounsGroup2() + t;
				}
				if (Features.isAverageNumberOfPronounsGroup2()) {
					data = data + tweet.getAverageNumberOfPronounsGroup2() + t;
				}
				if (Features.isUseOfNegation()) {
					data = data + tweet.isUseOfNegation() + t;
				}
				if (Features.isUseOfUncommonWords()) {
					data = data + tweet.isUseOfUncommonWords() + t;
				}
				if (Features.isNumberOfUncommonWords()) {
					data = data + tweet.getNumberOfUncommonWords() + t;
				}
			}

			// Semantic Features
			if (Features.isUseSemanticFeatures()) {
				if (Features.isUseOfOpinionWords()) {
					data = data + tweet.isUseOfOpinionWords() + t;
				}
				if (Features.isUseOfHighlySentimentalWords()) {
					data = data + tweet.isUseOfHighlySentimentalWords() + t;
				}
				if (Features.isUseOfUncertaintyWords()) {
					data = data + tweet.isUseOfUncertaintyWords() + t;
				}
				if (Features.isUseOfActiveForm()) {
					data = data + tweet.isUseOfActiveForm() + t;
				}
				if (Features.isUseOfPassiveForm()) {
					data = data + tweet.isUseOfPassiveForm() + t;
				}
			}

			// Unigram Features
			if (Features.isUseUnigramFeatures()) {
				if (Features.getUnigramTypeOfPos()!=null) {
					if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
						for (String sentiment : Parameters.getClasses()) {
							if (tweet.getUnigramCountPerClass().keySet().contains(sentiment)) {
								data = data + tweet.getUnigramCountPerClass().get(sentiment) + t;
							} else {
								data = data + "0" + t;
							}
						}
					} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
						for (String sentiment : Parameters.getClasses()) {
							if (Features.isUseUnigramNouns()) {
								if (tweet.getNounCountPerClass().keySet().contains(sentiment)) {
									data = data + tweet.getNounCountPerClass().get(sentiment) + t;
								} else {
									data = data + "0" + t;
								}
							}
							if (Features.isUseUnigramVerbs()) {
								if (tweet.getVerbCountPerClass().keySet().contains(sentiment)) {
									data = data + tweet.getVerbCountPerClass().get(sentiment) + t;
								} else {
									data = data + "0" + t;
								}
							}
							if (Features.isUseUnigramAdjectives()) {
								if (tweet.getAdjectiveCountPerClass().keySet().contains(sentiment)) {
									data = data + tweet.getAdjectiveCountPerClass().get(sentiment) + t;
								} else {
									data = data + "0" + t;
								}
							}
							if (Features.isUseUnigramAdverbs()) {
								if (tweet.getAdverbCountPerClass().keySet().contains(sentiment)) {
									data = data + tweet.getAdverbCountPerClass().get(sentiment) + t;
								} else {
									data = data + "0" + t;
								}
							}
						}
					} 
				}
			}

			// Top Words Features
			if (Features.isUseTopWords()) {
				if (Features.getTopWordsType()!=null && Features.getTopWordsTypeOfPos()!=null) {
					if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
						if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
							if (tweet.getTopWordSummed() == null || tweet.getTopWordSummed().length != Parameters.getTopWords().keySet().size()) {
								for (int i = 0; i < Parameters.getTopWords().keySet().size(); i++) {
									data = data + "0" + t;
								}
							} else {
								for (int i = 0; i < tweet.getTopWordSummed().length; i++) {
									data = data + tweet.getTopWordSummed()[i] + t;
								}
							}
						} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
							int size = 0;
							if (Features.isUseTopWordsNouns()) {
								size = size + Parameters.getTopNouns().keySet().size();
							}
							if (Features.isUseTopWordsVerbs()) {
								size = size + Parameters.getTopVerbs().keySet().size();
							}
							if (Features.isUseTopWordsAdjectives()) {
								size = size + Parameters.getTopAdjectives().keySet().size();
							}
							if (Features.isUseTopWordsAdverbs()) {
								size = size + Parameters.getTopAdverbs().keySet().size();
							}

						}
					} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
						if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {

							int size = 0;
							if (Features.isUseTopWordsNouns()) {
								for (String sentiment : Parameters.getTopNouns().keySet()) {
									size = size + Parameters.getTopNouns().get(sentiment).size();
								}

							}
							if (Features.isUseTopWordsVerbs()) {
								for (String sentiment : Parameters.getTopVerbs().keySet()) {
									size = size + Parameters.getTopVerbs().get(sentiment).size();
								}
							}
							if (Features.isUseTopWordsAdjectives()) {
								for (String sentiment : Parameters.getTopAdjectives().keySet()) {
									size = size + Parameters.getTopAdjectives().get(sentiment).size();
								}
							}
							if (Features.isUseTopWordsAdverbs()) {
								for (String sentiment : Parameters.getTopAdverbs().keySet()) {
									size = size + Parameters.getTopAdverbs().get(sentiment).size();
								}
							}

							if (tweet.getTopWordsSeparatelyBoolean() == null || tweet.getTopWordsSeparatelyBoolean().length != size) {
								for (int i = 0; i < size; i++) {
									data = data + "FALSE" + t;
								}
							} else {
								for (int i = 0; i < tweet.getTopWordsSeparatelyBoolean().length; i++) {
									data = data + tweet.getTopWordsSeparatelyBoolean()[i] + t;
								}
							}
						} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
							int size = 0;
							for (String sentiment : Parameters.getTopWords().keySet()) {
								size = size + Parameters.getTopWords().get(sentiment).size();
							}
							if (tweet.getTopWordsSeparatelyBoolean() == null
									|| tweet.getTopWordsSeparatelyBoolean().length != size) {
								for (int i = 0; i < size; i++) {
									data = data + "FALSE" + t;
								}
							} else {
								for (int i = 0; i < tweet.getTopWordsSeparatelyBoolean().length; i++) {
									data = data + tweet.getTopWordsSeparatelyBoolean()[i] + t;
								}
							}

						}
					} 
				}
			}

			// Pattern Features
			if (Features.isUsePatternFeatures()) {
				if (Features.getPatternFeaturesType()!=null) {
					if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
						int size = 0;
						for (String sentiment : Parameters.getSingleLengthPatterns().keySet()) {
							size = size + Parameters.getSingleLengthPatterns().get(sentiment).size();
						}
						if (tweet.getPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								data = data + "0" + t;
							}
						} else {
							for (int i = 0; i < tweet.getPatternScores().length; i++) {
								data = data + tweet.getPatternScores()[i] + t;
							}
						}
					} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
						int size = 0;
						for (String sentiment : Parameters.getMultiLengthPatterns().keySet()) {
							size = size + Parameters.getMultiLengthPatterns().get(sentiment).keySet().size();
						}

						if (tweet.getPatternScores().length != size) {
							for (int i = 0; i < size; i++) {
								data = data + "0" + t;
							}
						} else {
							for (int i = 0; i < tweet.getPatternScores().length; i++) {
								data = data + tweet.getPatternScores()[i] + t;
							}
						}
					} 
				}
			}
			// TODO consider the case where theset of non-annotated tweets is taken
			data = data + tweet.getTweetClass() + n;
		}
		
		File exportedFile = new File(file);
		File exportedFileDirectory = new File(exportedFile.getParent());

		boolean folderCreated = false;
		if (!exportedFileDirectory.exists()) {
			try {
				exportedFileDirectory.mkdir();
				folderCreated = true;
			} catch (SecurityException se) {
				AlertBox.display("Error", "The folder you want to save in cannot be accessed!", "OK");
			}
		} else {
			folderCreated = true;
		}

		BufferedWriter writer = null;

		if (folderCreated) {

			try {
				if (!exportedFile.exists()) {
					exportedFile.createNewFile();
				}
				writer = new BufferedWriter(new FileWriter(exportedFile));
				writer.write(title);
				writer.write(data);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}


}
