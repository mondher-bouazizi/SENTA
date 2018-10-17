package backend.classifiers;

import java.io.File;
import java.util.ArrayList;

import javax.annotation.Nonnull;

import commons.constants.Commons;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.items.ClassifierParameters;
import main.items.Parameters;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.lazy.KStar;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ArffLoader;

public class KStarClassifier {

	public static final String TRAINING_FILENAME = "Training.arff";
	public static final String TEST_FILENAME = "Test.arff";

	private static KStar kStar;
	
	protected static StringProperty outputText = new SimpleStringProperty("");
	
	/**
	 * Initialize the classifier and set its parameters
	 */
	public static void setParameters() {
		
		outputText.set("");

		kStar = new KStar();
		
		// Setting the parameters
		kStar.setBatchSize(ClassifierParameters.getkStarbatchSize() + "");
		kStar.setDebug(ClassifierParameters.iskStardebug());
		kStar.setDoNotCheckCapabilities(ClassifierParameters.iskStardoNotCheckCapabilities());
		kStar.setEntropicAutoBlend(ClassifierParameters.iskStarentropicAutoBlend());
		kStar.setGlobalBlend(ClassifierParameters.getkStarglobalBlend());
		
		int indexOfTag = 3;
		
		
		if (ClassifierParameters.getkStarmissingMode() != null) {
			if (ClassifierParameters.getkStarmissingMode().equals(ClassifierParameters.KStarMissingMode.Ignore_The_Instances_With_Missing_Values)) {
				indexOfTag = 0;
			} else if (ClassifierParameters.getkStarmissingMode().equals(ClassifierParameters.KStarMissingMode.Treat_Missing_Values_As_Maximally_Different)) {
				indexOfTag = 1;
			} else if (ClassifierParameters.getkStarmissingMode().equals(ClassifierParameters.KStarMissingMode.Normalize_Over_The_Attributes)) {
				indexOfTag = 2;
			} else if (ClassifierParameters.getkStarmissingMode().equals(ClassifierParameters.KStarMissingMode.Avg_Column_Entropy_Curves)) {
				indexOfTag = 3;
			} 
		}
		kStar.setMissingMode(new SelectedTag(indexOfTag, KStar.TAGS_MISSING));

		kStar.setNumDecimalPlaces(ClassifierParameters.getkStarnumDecimalPlaces());
	}

	/**
	 * Build the classifier and train it
	 * 
	 * @param trainingData
	 *            : training instances
	 */
	public static void train(@Nonnull Instances trainingData) {

		try {
			kStar.buildClassifier(trainingData);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Build the classifier and train it
	 * 
	 * @param arffFile
	 *            : "*.arff" file containing the training instances
	 */
	public static void train(String arffFile) {
		
		File file = new File(arffFile);

		if (file == null || !file.exists()) {
			System.out.println("something went wrong with the training file: " + file.getPath());
			return;
		}
		
		/* ASSUME WE HAVE THE TRAINING ARFF files at outputLocation now */
		
		ArffLoader trainingLoader = new ArffLoader();
		Instances trainingData = null;

		try {
			trainingLoader.setFile(new File(arffFile));
			trainingData = trainingLoader.getDataSet();
			trainingData.setClassIndex(trainingData.numAttributes() - 1);
			System.out.println("number of training instances : " + trainingData.numInstances());
			train(trainingData);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Cross-validate on the training set
	 * @param projectDirectory
	 */
	public static void crossValidate(String arffFile, ArrayList<String> classes) {
		File file = new File(arffFile);

		if (file == null || !file.exists()) {
			System.out.println("something went wrong with training file: " + file.getPath());
			return;
		}

		/* ASSUME WE HAVE THE TRAINING ARFF files at outputLocation now */
		ArffLoader trainingLoader = new ArffLoader();
		Instances trainingData = null;

		// cross validation
		System.out.println("----------X-VALIDATION------------");
		try {
			trainingLoader.setFile(new File(arffFile));

			trainingData = trainingLoader.getDataSet();
			trainingData.setClassIndex(trainingData.numAttributes() - 1);
			System.out.println("number of training instances : " + trainingData.numInstances());

			train(trainingData);

			Evaluation xEvaluation = new Evaluation(trainingData);
			xEvaluation.crossValidateModel(kStar, trainingData, 4, trainingData.getRandomNumberGenerator(50));

			StringBuffer results = new StringBuffer();
			
			results.append("== Classification using K* ==\n\n");
			results.append("=== X-VALIDATION ===\n");
			
			results.append(xEvaluation.toSummaryString() + "\n");
			results.append(xEvaluation.toMatrixString() + "\n");
			results.append(xEvaluation.toClassDetailsString() + "\n");
			System.out.println(results.toString());
			
			outputText.set(results.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Split the training set into two parts, and use one part for training and the other for test
	 * @param arffTrainingFile : 
	 * @param arffTestFile : 
	 * @param classes : 
	 */
	public static void trainingSplit(String arffTrainingFile, int percentage, ArrayList<String> classes) {
		
		File trainingFile = new File(arffTrainingFile);

		if (trainingFile == null || !trainingFile.exists()) {
			System.out.println("something went wrong with the training file: " + trainingFile.getPath());
			return;
		}

		try {

			// load training ARFF
			ArffLoader dataLoader = new ArffLoader();
			
			Instances allData = null;
			Instances trainingData = null;
			Instances testData = null;

			dataLoader.setFile(new File(arffTrainingFile));

			allData = dataLoader.getDataSet();
			allData.setClassIndex(allData.numAttributes() - 1);
			
			int numberOfTrainingInstances = allData.size() * percentage / 100;
			int numberOfTestInstances = allData.size() - numberOfTrainingInstances;
			
			System.out.println("number of training instances : " + numberOfTrainingInstances);
			System.out.println("number of testing instances : " + numberOfTestInstances);
			
			trainingData = new Instances(allData, 0, numberOfTrainingInstances);
			testData = new Instances(allData, numberOfTrainingInstances, numberOfTestInstances);

			// Train the classifier using the training data
			train(trainingData);

			// Run the classification on the test data
			StringBuffer results = new StringBuffer();
			
			
			results.append("== Classification using K* ==\n\n");
			results.append("=== Training set split [" + percentage + "% of data are used for training] ===\n");
			
			Evaluation testEvaluation = new Evaluation(testData);
			testEvaluation.evaluateModel(kStar, testData);

			results.append(testEvaluation.toSummaryString() + "\n");
			results.append(testEvaluation.toMatrixString() + "\n");
			results.append(testEvaluation.toClassDetailsString() + "\n");

			System.out.println(results.toString());
			
			outputText.set(results.toString());

			for (int i = 0; i < testData.numInstances(); i++) {
				// label instances
				Double genderLabel = kStar.classifyInstance(testData.instance(i));
				
				if (genderLabel.intValue() < classes.size()) {
					Parameters.getTestSet().get(i).setPredictedClass(classes.get(genderLabel.intValue()));
				} else {
					Commons.print("Error: the class " + genderLabel.intValue() + "does not exist");
				}
			}

			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Run the classification on a test set
	 * @param testFileDir
	 */
	public static void test(String arffTrainingFile, String arffTestFile, ArrayList<String> classes) {
		
		File trainingFile = new File(arffTrainingFile);
		File testFile = new File(arffTestFile);

		if (trainingFile == null || !trainingFile.exists()) {
			System.out.println("something went wrong with the training file: " + trainingFile.getPath());
			return;
		} else if (testFile == null || !testFile.exists()) {
			System.out.println("something went wrong with the test file: " + testFile.getPath());
			return;
		}

		try {

			// load training ARFF
			ArffLoader trainingLoader = new ArffLoader();
			Instances trainingData = null;

			trainingLoader.setFile(new File(arffTrainingFile));

			trainingData = trainingLoader.getDataSet();
			trainingData.setClassIndex(trainingData.numAttributes() - 1);
			System.out.println("number of training instances : " + trainingData.numInstances());

			// load test ARFF
			ArffLoader testLoader = new ArffLoader();
			Instances testData = null;
			
			testLoader.setFile(new File(arffTestFile));
			testData = testLoader.getDataSet();
			testData.setClassIndex(testData.numAttributes() - 1);

			System.out.println("number of testing instances : " + testData.numInstances());

			// Train the classifier using the training data
			train(trainingData);

			// Run the classification on the test data
			StringBuffer results = new StringBuffer();
			
			
			results.append("== Classification using K* ==\n\n");
			results.append("=== ON A TEST SET ===\n");
			
			
			Evaluation testEvaluation = new Evaluation(testData);
			testEvaluation.evaluateModel(kStar, testData);

			results.append(testEvaluation.toSummaryString() + "\n");
			results.append(testEvaluation.toMatrixString() + "\n");
			results.append(testEvaluation.toClassDetailsString() + "\n");

			System.out.println(results.toString());
			
			outputText.set(results.toString());

			for (int i = 0; i < testData.numInstances(); i++) {
				// label instances
				Double label = kStar.classifyInstance(testData.instance(i));
				
				if (label.intValue() < classes.size()) {
					// Commons.print("Prediction: " + classes.get(label.intValue()) + "\tActual class: " + Parameters.getTestSet().get(i).getTweetClass());
					Parameters.getTestSet().get(i).setPredictedClass(classes.get(label.intValue()));
				} else {
					Commons.print("Error: the class " + label.intValue() + "does not exist");
				}
			}

			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// =====================================//
	//          GETTERS AND SETTERS         //
	// =====================================//
	
	public static StringProperty getOutputText() {
		return outputText;
	}

	public static void setOutputText(StringProperty outputText) {
		KStarClassifier.outputText = outputText;
	}
	
	
}
