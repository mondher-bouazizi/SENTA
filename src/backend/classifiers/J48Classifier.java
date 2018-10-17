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
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class J48Classifier {

	public static final String TRAINING_FILENAME = "Training.arff";
	public static final String TEST_FILENAME = "Test.arff";

	private static J48 j48;
	
	protected static StringProperty outputText = new SimpleStringProperty("");
	
	/**
	 * Initialize the classifier and set its parameters
	 */
	public static void setParameters() {
		
		outputText.set("");

		j48 = new J48();
		
		// Setting the parameters
		j48.setBatchSize(ClassifierParameters.getJ48batchSize() + "");
		j48.setBinarySplits(ClassifierParameters.isJ48binarySplit());
		j48.setCollapseTree(ClassifierParameters.isJ48collapseTree());
		j48.setConfidenceFactor((float) ClassifierParameters.getJ48confidenceFactor());
		j48.setDebug(ClassifierParameters.isJ48debug());
		j48.setDoNotCheckCapabilities(ClassifierParameters.isJ48doNotCheckCapabilities());
		j48.setDoNotMakeSplitPointActualValue(ClassifierParameters.isJ48doNotMakeSplitPAV());
		j48.setMinNumObj(ClassifierParameters.getJ48minNumObj());
		j48.setNumDecimalPlaces(ClassifierParameters.getJ48numDecimalPlaces());
		j48.setNumFolds(ClassifierParameters.getJ48numFolds());
		j48.setReducedErrorPruning(ClassifierParameters.isJ48reduceErrorPruning());
		j48.setSaveInstanceData(ClassifierParameters.isJ48saveInstanceData());
		j48.setSeed(ClassifierParameters.getJ48seed());
		j48.setSubtreeRaising(ClassifierParameters.isJ48subTreeRaising());
		j48.setUnpruned(ClassifierParameters.isJ48unpruned());
		j48.setUseLaplace(ClassifierParameters.isJ48useLaplace());
		j48.setUseMDLcorrection(ClassifierParameters.isJ48useMDLcorrection());
	}

	/**
	 * Build the classifier and train it
	 * 
	 * @param trainingData
	 *            : training instances
	 */
	public static void train(@Nonnull Instances trainingData) {

		try {
			j48.buildClassifier(trainingData);
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
			xEvaluation.crossValidateModel(j48, trainingData, 4, trainingData.getRandomNumberGenerator(50));

			StringBuffer results = new StringBuffer();
			
			results.append("== Classification using J48 ==\n\n");
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
			
			
			results.append("== Classification using J48 ==\n\n");
			results.append("=== Training set split [" + percentage + "% of data are used for training] ===\n");
			
			Evaluation testEvaluation = new Evaluation(testData);
			testEvaluation.evaluateModel(j48, testData);

			results.append(testEvaluation.toSummaryString() + "\n");
			results.append(testEvaluation.toMatrixString() + "\n");
			results.append(testEvaluation.toClassDetailsString() + "\n");

			System.out.println(results.toString());
			
			outputText.set(results.toString());

			for (int i = 0; i < testData.numInstances(); i++) {
				// label instances
				Double genderLabel = j48.classifyInstance(testData.instance(i));
				
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
			
			
			results.append("== Classification using J48 ==\n\n");
			results.append("=== ON A TEST SET ===\n");
			
			
			Evaluation testEvaluation = new Evaluation(testData);
			testEvaluation.evaluateModel(j48, testData);

			results.append(testEvaluation.toSummaryString() + "\n");
			results.append(testEvaluation.toMatrixString() + "\n");
			results.append(testEvaluation.toClassDetailsString() + "\n");

			System.out.println(results.toString());
			
			outputText.set(results.toString());

			for (int i = 0; i < testData.numInstances(); i++) {
				// label instances
				Double label = j48.classifyInstance(testData.instance(i));
				
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
		J48Classifier.outputText = outputText;
	}
	
	
}
