package main.items;


public class ClassifierParameters {

	// =====================================//
	//             CLASSIFIERS              //
	// =====================================//

	public static enum Classifier {
		NONE, RANDOM_FOREST, NAIVE_BAYES, NAIVE_BAYES_UPDATEABLE, J48, K_STAR, HOEFFDING_TREE, SVM
	}

	protected static Classifier classifier;
	
	
	// =====================================//
	//       RANDOM FOREST PARAMETERS       //
	// =====================================//
	
	private static boolean rfIsNotFirstTime;
	
	protected static int rfBagSizePercent;
	protected static int rfBatchSize;
	
	protected static boolean rfBreakTiesRandomly;
	protected static boolean rfCalcOutOfBag;
	protected static boolean rfComputeAttributeImportance;
	protected static boolean rfDebug;
	protected static boolean rfDoNotCheckCapabilities;
	
	protected static int rfMaxDepth;
	protected static int rfNumDecimalPlaces;
	protected static int rfNumExecutionSlots;
	protected static int rfNumFeatures;
	protected static int rfNumIterations;
	
	protected static boolean rfOutOfBagComplexityStatistics;
	protected static boolean rfPrintClassifiers;
	
	protected static int rfSeed;
	
	protected static boolean rfStoreOutOfBagPredictions;
	
	
	// =====================================//
	//        NAIVE BAYES PARAMETERS        //
	// =====================================//
	
	private static boolean nbIsNotFirstTime;
	
	protected static int nbBatchSize;

	protected static boolean nbDebug;
	protected static boolean nbComputeAttributeImportance;
	protected static boolean nbDoNotCheckCapabilities;
	
	protected static int nbNumDecimalPlaces;
	
	protected static boolean nbUserKernelEstimator;
	protected static boolean nbUseSupervisedDisc;
	
	
	// =====================================//
	//   NAIVE BAYES UPDATEABLE PARAMETERS  //
	// =====================================//
	
	private static boolean nbuIsNotFirstTime;
	
	protected static int nbuBatchSize;

	protected static boolean nbuDebug;
	protected static boolean nbuDisplayModelInOldFormat;
	protected static boolean nbuDoNotCheckCapabilities;
	
	protected static int nbuNumDecimalPlaces;
	
	protected static boolean nbuUserKernelEstimator;
	protected static boolean nbuUseSupervisedDisc;
	
	
	// =====================================//
	//    Iterative Dichotomiser 3 (J48)    //
	// =====================================//
	
	private static boolean j48IsNotFirstTime;
	
	protected static int j48batchSize;
	
	protected static boolean j48binarySplit;
	protected static boolean j48collapseTree;
	
	protected static double j48confidenceFactor;
	
	protected static boolean j48debug;
	protected static boolean j48doNotCheckCapabilities;
	protected static boolean j48doNotMakeSplitPAV;
	
	protected static int j48minNumObj;
	protected static int j48numDecimalPlaces;
	protected static int j48numFolds;
	
	protected static boolean j48reduceErrorPruning;
	protected static boolean j48saveInstanceData;
	
	protected static int j48seed;
	
	protected static boolean j48subTreeRaising;
	protected static boolean j48unpruned;
	protected static boolean j48useLaplace;
	protected static boolean j48useMDLcorrection;	
	
	
	// =====================================//
	//               K-Star                 //
	// =====================================//
	
	public static enum KStarMissingMode {
		Ignore_The_Instances_With_Missing_Values,
		Treat_Missing_Values_As_Maximally_Different,
		Normalize_Over_The_Attributes,
		Avg_Column_Entropy_Curves
	}
	
	private static boolean kStarIsNotFirstTime;
	
	protected static int kStarbatchSize;
	
	protected static boolean kStardebug;
	protected static boolean kStardoNotCheckCapabilities;
	protected static boolean kStarentropicAutoBlend;
	
	protected static int kStarglobalBlend;
	
	protected static KStarMissingMode kStarmissingMode;
	
	protected static int kStarnumDecimalPlaces;
	
	
	// =====================================//
	//            Heoffding Tree            //
	// =====================================//
	
	public static enum HoeffdingTreeLPS{
		Majority_Class,
		Naive_Bayes,
		Naive_Bayes_Adaptive
	}
	
	public static enum HoeffdingTreeSplitCriterion{
		Gini_Split,
		Info_Gain_Split
	}
	
	private static boolean htIsNotFirstTime;
	
	protected static int htbatchSize;
	
	protected static boolean htdebug;
	protected static boolean htdoNotCheckCapabilities;
	
	protected static double htgracePeriod;
	protected static double hthoeffdingTieThreshold;
	
	protected static HoeffdingTreeLPS htleafPredictionStrategy;
	
	protected static double htminimumFractionOfWeightInfoGain;
	protected static double htnaiveBayesPredictionThreshold;
	protected static int htnumDecimalPlaces;
	
	protected static boolean htprintLeafModels;
	protected static double htsplitConfidence;
	
	protected static HoeffdingTreeSplitCriterion htsplitCriterion;
	


	// =====================================//
	//      INITIALIZATION & PARAMETERS     //
	// =====================================//
	
	public static void randomForestInitialize() {
		
		if (!ClassifierParameters.rfIsNotFirstTime) {
			
			ClassifierParameters.rfBagSizePercent = 100;
			ClassifierParameters.rfBatchSize = 100;
			ClassifierParameters.rfBreakTiesRandomly = false;
			ClassifierParameters.rfCalcOutOfBag = false;
			ClassifierParameters.rfComputeAttributeImportance = false;
			ClassifierParameters.rfDebug = false;
			ClassifierParameters.rfDoNotCheckCapabilities = false;
			ClassifierParameters.rfMaxDepth = 0;
			ClassifierParameters.rfNumDecimalPlaces = 2;
			ClassifierParameters.rfNumExecutionSlots = 1;
			ClassifierParameters.rfNumFeatures = 0;
			ClassifierParameters.rfNumIterations = 100;
			ClassifierParameters.rfOutOfBagComplexityStatistics = false;
			ClassifierParameters.rfPrintClassifiers = false;
			ClassifierParameters.rfSeed = 1;
			ClassifierParameters.rfStoreOutOfBagPredictions = false;
			
			ClassifierParameters.rfIsNotFirstTime = true;
			
		}

	}
	public static void randomForestSetParameters(
			int bagSizePercent, int batchSize, int maxDepth, int numDecimalPlaces,
			int numExecutionSlots, int numFeatures, int numIterations, int seed,
			boolean breakTiesRandomly, boolean calcOutOfBag, boolean computeAttributeImportance, boolean debug,
			boolean doNotCheckCapabilities, boolean outputComplexityStats, boolean printClassifier, boolean storeOutOfBag
			) {
		
		ClassifierParameters.rfBagSizePercent = bagSizePercent;
		ClassifierParameters.rfBatchSize = batchSize;
		ClassifierParameters.rfMaxDepth = maxDepth;
		ClassifierParameters.rfNumDecimalPlaces = numDecimalPlaces;
		ClassifierParameters.rfNumExecutionSlots = numExecutionSlots;
		ClassifierParameters.rfNumFeatures = numFeatures;
		ClassifierParameters.rfNumIterations = numIterations;
		ClassifierParameters.rfSeed = seed;
		
		ClassifierParameters.rfBreakTiesRandomly = breakTiesRandomly;
		ClassifierParameters.rfCalcOutOfBag = calcOutOfBag;
		ClassifierParameters.rfComputeAttributeImportance = computeAttributeImportance;
		ClassifierParameters.rfDebug = debug;
		ClassifierParameters.rfDoNotCheckCapabilities = doNotCheckCapabilities;
		ClassifierParameters.rfOutOfBagComplexityStatistics = outputComplexityStats;
		ClassifierParameters.rfPrintClassifiers = printClassifier;
		ClassifierParameters.rfStoreOutOfBagPredictions = storeOutOfBag;
	}

	
	public static void naiveBayesInitialize() {
		if (!ClassifierParameters.nbIsNotFirstTime) {
			ClassifierParameters.nbBatchSize = 100;
			ClassifierParameters.nbDebug = false;
			ClassifierParameters.nbComputeAttributeImportance = false;
			ClassifierParameters.nbDoNotCheckCapabilities = false;
			
			ClassifierParameters.nbNumDecimalPlaces = 2;
			
			ClassifierParameters.nbUserKernelEstimator = false;
			ClassifierParameters.nbUseSupervisedDisc = false;
			
			ClassifierParameters.nbIsNotFirstTime = true;
		}
	}
	public static void naiveBayesSetParameters(int nbBatchSize, int nbNumDecimalPlaces,
			boolean nbDebug, boolean nbComputeAttributeImportance, boolean nbDoNotCheckCapabilities, 
			boolean nbUserKernelEstimator, boolean nbUseSupervisedDisc) {
		
		ClassifierParameters.nbBatchSize = nbBatchSize;
		ClassifierParameters.nbNumDecimalPlaces = nbNumDecimalPlaces;
		
		ClassifierParameters.nbDebug = nbDebug;
		ClassifierParameters.nbComputeAttributeImportance = nbComputeAttributeImportance;
		ClassifierParameters.nbDoNotCheckCapabilities = nbDoNotCheckCapabilities;
		ClassifierParameters.nbUserKernelEstimator = nbUserKernelEstimator;
		ClassifierParameters.nbUseSupervisedDisc = nbUseSupervisedDisc;
		
	}
	
	
	public static void naiveBayesUpdateableInitialize() {
		if (!ClassifierParameters.nbuIsNotFirstTime) {
			ClassifierParameters.nbuBatchSize = 100;
			ClassifierParameters.nbuDebug = false;
			ClassifierParameters.nbuDisplayModelInOldFormat = false;
			ClassifierParameters.nbuDoNotCheckCapabilities = false;
			
			ClassifierParameters.nbuNumDecimalPlaces = 2;
			
			ClassifierParameters.nbuUserKernelEstimator = false;
			ClassifierParameters.nbuUseSupervisedDisc = false;
			
			ClassifierParameters.nbuIsNotFirstTime = true;
		}
	}
	public static void naiveBayesUpdateableSetParameters(int nbuBatchSize, int nbuNumDecimalPlaces,
			boolean nbuDebug, boolean nbuDisplayModelInOldFormat, boolean nbuDoNotCheckCapabilities, 
			boolean nbuUserKernelEstimator, boolean nbuUseSupervisedDisc) {
		
		ClassifierParameters.nbuBatchSize = nbuBatchSize;
		ClassifierParameters.nbuNumDecimalPlaces = nbuNumDecimalPlaces;
		
		ClassifierParameters.nbuDebug = nbuDebug;
		ClassifierParameters.nbuDisplayModelInOldFormat = nbuDisplayModelInOldFormat;
		ClassifierParameters.nbuDoNotCheckCapabilities = nbuDoNotCheckCapabilities;
		ClassifierParameters.nbuUserKernelEstimator = nbuUserKernelEstimator;
		ClassifierParameters.nbuUseSupervisedDisc = nbuUseSupervisedDisc;
		
	}
	
	
	public static void j48Initialize() {
		if (!ClassifierParameters.j48IsNotFirstTime) {
			ClassifierParameters.j48batchSize = 100;
			
			ClassifierParameters.j48binarySplit = false;
			ClassifierParameters.j48collapseTree = true;
			
			ClassifierParameters.j48confidenceFactor = 0.25;
			
			ClassifierParameters.j48debug = false;
			ClassifierParameters.j48doNotCheckCapabilities = false;
			ClassifierParameters.j48doNotMakeSplitPAV = false;
			
			ClassifierParameters.j48minNumObj = 2;
			ClassifierParameters.j48numDecimalPlaces = 2;
			ClassifierParameters.j48numFolds = 3;
			
			ClassifierParameters.j48reduceErrorPruning = false;
			ClassifierParameters.j48saveInstanceData = false;
			
			ClassifierParameters.j48seed = 1;
			
			ClassifierParameters.j48subTreeRaising = true;
			ClassifierParameters.j48unpruned = false;
			ClassifierParameters.j48useLaplace = false;
			ClassifierParameters.j48useMDLcorrection = true;
			
			ClassifierParameters.j48IsNotFirstTime = true;
		}
	}
	public static void j48SetParameters(int batchSize, int minNumObj, int numDecimalPlaces, int numFolds, int seed,
			double confidenceFactor,
			boolean binarySplit, boolean collapseTree, boolean debug, boolean doNotCheckCapabilities,
			boolean doNotMakeSplitPAV, boolean reduceErrorPruning, boolean saveInstanceData, boolean subtreeRaising,
			boolean unpruned, boolean useLaplace, boolean useMDLcorrection) {

		ClassifierParameters.j48batchSize = batchSize;
		ClassifierParameters.j48minNumObj = minNumObj;
		ClassifierParameters.j48numDecimalPlaces = numDecimalPlaces;
		ClassifierParameters.j48numFolds = numFolds;
		ClassifierParameters.j48seed = seed;

		ClassifierParameters.j48confidenceFactor = confidenceFactor;

		ClassifierParameters.j48binarySplit = binarySplit;
		ClassifierParameters.j48collapseTree = collapseTree;
		ClassifierParameters.j48debug = debug;
		ClassifierParameters.j48doNotCheckCapabilities = doNotCheckCapabilities;
		ClassifierParameters.j48doNotMakeSplitPAV = doNotMakeSplitPAV;
		ClassifierParameters.j48reduceErrorPruning = reduceErrorPruning;
		ClassifierParameters.j48saveInstanceData = saveInstanceData;
		ClassifierParameters.j48subTreeRaising = subtreeRaising;
		ClassifierParameters.j48unpruned = unpruned;
		ClassifierParameters.j48useLaplace = useLaplace;
		ClassifierParameters.j48useMDLcorrection = useMDLcorrection;
		
	}
	
	
	public static void kStarInitialize() {
		if (!ClassifierParameters.kStarIsNotFirstTime) {
						
			ClassifierParameters.kStarbatchSize = 100;
			
			ClassifierParameters.kStardebug = false;
			ClassifierParameters.kStardoNotCheckCapabilities = false;
			ClassifierParameters.kStarentropicAutoBlend = false;
			
			ClassifierParameters.kStarglobalBlend = 20;
			
			ClassifierParameters.kStarmissingMode = KStarMissingMode.Avg_Column_Entropy_Curves;
			
			ClassifierParameters.kStarnumDecimalPlaces = 2;
			
			ClassifierParameters.kStarIsNotFirstTime = true;
		}
	}
	public static void kStarSetParameters(int batchSize, boolean debug, boolean doNotCheckCapabilities,
			boolean entropicAutoBlend, int globalBlend, KStarMissingMode missingMode, int numDecimalPlaces) {

		ClassifierParameters.kStarbatchSize = batchSize;
		
		ClassifierParameters.kStardebug = debug;
		ClassifierParameters.kStardoNotCheckCapabilities = doNotCheckCapabilities;
		ClassifierParameters.kStarentropicAutoBlend = entropicAutoBlend;
		
		ClassifierParameters.kStarglobalBlend = globalBlend;
		
		ClassifierParameters.kStarmissingMode = missingMode;
		
		ClassifierParameters.kStarnumDecimalPlaces = numDecimalPlaces;
		
	}
	
	
	public static void heoffdingTreeInitialize() {

		if (!ClassifierParameters.htIsNotFirstTime) {
						
			ClassifierParameters.htbatchSize = 100;
			
			ClassifierParameters.htdebug = false;
			ClassifierParameters.htdoNotCheckCapabilities = false;
			
			ClassifierParameters.htgracePeriod = 200.0;
			ClassifierParameters.hthoeffdingTieThreshold = 0.05;
			ClassifierParameters.htleafPredictionStrategy = HoeffdingTreeLPS.Naive_Bayes_Adaptive;
			ClassifierParameters.htminimumFractionOfWeightInfoGain = 0.01;
			ClassifierParameters.htnaiveBayesPredictionThreshold = 0.0;
			ClassifierParameters.htnumDecimalPlaces = 2;
			ClassifierParameters.htprintLeafModels = false;
			ClassifierParameters.htsplitConfidence = 1.0E-7;
			ClassifierParameters.htsplitCriterion = HoeffdingTreeSplitCriterion.Info_Gain_Split;
			
			ClassifierParameters.htIsNotFirstTime = true;
		}
	}
	public static void heoffdingTreeSetParameters(int htbatchSize, boolean htdebug, boolean htdoNotCheckCapabilities, double htgracePeriod, 
			double htheoffdingTieThreshold, HoeffdingTreeLPS htleafPredictionStrategy, double htminimumFractionOfWeightInfoGain, 
			double htnaiveBayesPredictionThreshold, int htnumDecimalPlaces, boolean htprintLeafModels, 
			double htsplitConfidence, HoeffdingTreeSplitCriterion htsplitCriterion) {
		
		
		ClassifierParameters.htbatchSize = htbatchSize;
		ClassifierParameters.htdebug = htdebug;
		ClassifierParameters.htdoNotCheckCapabilities = htdoNotCheckCapabilities;
		ClassifierParameters.htgracePeriod = htgracePeriod;
		ClassifierParameters.hthoeffdingTieThreshold = htheoffdingTieThreshold;
		ClassifierParameters.htleafPredictionStrategy = htleafPredictionStrategy;
		ClassifierParameters.htminimumFractionOfWeightInfoGain = htminimumFractionOfWeightInfoGain;
		ClassifierParameters.htnaiveBayesPredictionThreshold = htnaiveBayesPredictionThreshold;
		ClassifierParameters.htnumDecimalPlaces = htnumDecimalPlaces;
		ClassifierParameters.htprintLeafModels = htprintLeafModels;
		ClassifierParameters.htsplitConfidence = htsplitConfidence;
		ClassifierParameters.htsplitCriterion = htsplitCriterion;
		
	}
	
	
	
	/**
	 * Re-initialize all the parameters to the [null / 0 / false] values
	 */
	public static void reinitialize() {
		rfIsNotFirstTime = false;
		nbIsNotFirstTime = false;
		nbuIsNotFirstTime = false;
		j48IsNotFirstTime = false;
		kStarIsNotFirstTime = false;
		htIsNotFirstTime = false;
	}
	
	// =====================================//
	//         GETTERS AND SETTERS          //
	// =====================================//

	public static Classifier getClassifier() {
		return classifier;
	}
	public static void setClassifier(Classifier classifier) {
		ClassifierParameters.classifier = classifier;
	}


	
	// RANDOM FOREST PARAMETERS
	public static int getRfBagSizePercent() {
		return rfBagSizePercent;
	}
	public static void setRfBagSizePercent(int rfBagSizePercent) {
		ClassifierParameters.rfBagSizePercent = rfBagSizePercent;
	}

	public static int getRfBatchSize() {
		return rfBatchSize;
	}
	public static void setRfBatchSize(int rfBatchSize) {
		ClassifierParameters.rfBatchSize = rfBatchSize;
	}

	public static boolean isRfBreakTiesRandomly() {
		return rfBreakTiesRandomly;
	}
	public static void setRfBreakTiesRandomly(boolean rfBreakTiesRandomly) {
		ClassifierParameters.rfBreakTiesRandomly = rfBreakTiesRandomly;
	}

	public static boolean isRfCalcOutOfBag() {
		return rfCalcOutOfBag;
	}
	public static void setRfCalcOutOfBag(boolean rfCalcOutOfBag) {
		ClassifierParameters.rfCalcOutOfBag = rfCalcOutOfBag;
	}

	public static boolean isRfComputeAttributeImportance() {
		return rfComputeAttributeImportance;
	}
	public static void setRfComputeAttributeImportance(boolean rfComputeAttributeImportance) {
		ClassifierParameters.rfComputeAttributeImportance = rfComputeAttributeImportance;
	}

	public static boolean isRfDebug() {
		return rfDebug;
	}
	public static void setRfDebug(boolean rfDebug) {
		ClassifierParameters.rfDebug = rfDebug;
	}

	public static boolean isRfDoNotCheckCapabilities() {
		return rfDoNotCheckCapabilities;
	}
	public static void setRfDoNotCheckCapabilities(boolean rfDoNotCheckCapabilities) {
		ClassifierParameters.rfDoNotCheckCapabilities = rfDoNotCheckCapabilities;
	}

	public static int getRfMaxDepth() {
		return rfMaxDepth;
	}
	public static void setRfMaxDepth(int rfMaxDepth) {
		ClassifierParameters.rfMaxDepth = rfMaxDepth;
	}

	public static int getRfNumDecimalPlaces() {
		return rfNumDecimalPlaces;
	}
	public static void setRfNumDecimalPlaces(int rfNumDecimalPlaces) {
		ClassifierParameters.rfNumDecimalPlaces = rfNumDecimalPlaces;
	}

	public static int getRfNumExecutionSlots() {
		return rfNumExecutionSlots;
	}
	public static void setRfNumExecutionSlots(int rfNumExecutionSlots) {
		ClassifierParameters.rfNumExecutionSlots = rfNumExecutionSlots;
	}

	public static int getRfNumFeatures() {
		return rfNumFeatures;
	}
	public static void setRfNumFeatures(int rfNumFeatures) {
		ClassifierParameters.rfNumFeatures = rfNumFeatures;
	}

	public static int getRfNumIterations() {
		return rfNumIterations;
	}
	public static void setRfNumIterations(int rfNumIterations) {
		ClassifierParameters.rfNumIterations = rfNumIterations;
	}

	public static boolean isRfOutOfBagComplexityStatistics() {
		return rfOutOfBagComplexityStatistics;
	}
	public static void setRfOutOfBagComplexityStatistics(boolean rfOutOfBagComplexityStatistics) {
		ClassifierParameters.rfOutOfBagComplexityStatistics = rfOutOfBagComplexityStatistics;
	}

	public static boolean isRfPrintClassifiers() {
		return rfPrintClassifiers;
	}
	public static void setRfPrintClassifiers(boolean rfPrintClassifiers) {
		ClassifierParameters.rfPrintClassifiers = rfPrintClassifiers;
	}

	public static int getRfSeed() {
		return rfSeed;
	}
	public static void setRfSeed(int rfSeed) {
		ClassifierParameters.rfSeed = rfSeed;
	}

	public static boolean isRfStoreOutOfBagPredictions() {
		return rfStoreOutOfBagPredictions;
	}
	public static void setRfStoreOutOfBagPredictions(boolean rfStoreOutOfBagPredictions) {
		ClassifierParameters.rfStoreOutOfBagPredictions = rfStoreOutOfBagPredictions;
	}


	
	// NAIVE BAYES PARAMETERS
	public static int getNbBatchSize() {
		return nbBatchSize;
	}
	public static void setNbBatchSize(int nbBatchSize) {
		ClassifierParameters.nbBatchSize = nbBatchSize;
	}

	public static boolean isNbDebug() {
		return nbDebug;
	}
	public static void setNbDebug(boolean nbDebug) {
		ClassifierParameters.nbDebug = nbDebug;
	}

	public static boolean isNbComputeAttributeImportance() {
		return nbComputeAttributeImportance;
	}
	public static void setNbComputeAttributeImportance(boolean nbComputeAttributeImportance) {
		ClassifierParameters.nbComputeAttributeImportance = nbComputeAttributeImportance;
	}

	public static boolean isNbDoNotCheckCapabilities() {
		return nbDoNotCheckCapabilities;
	}
	public static void setNbDoNotCheckCapabilities(boolean nbDoNotCheckCapabilities) {
		ClassifierParameters.nbDoNotCheckCapabilities = nbDoNotCheckCapabilities;
	}

	public static int getNbNumDecimalPlaces() {
		return nbNumDecimalPlaces;
	}
	public static void setNbNumDecimalPlaces(int nbNumDecimalPlaces) {
		ClassifierParameters.nbNumDecimalPlaces = nbNumDecimalPlaces;
	}

	public static boolean isNbUserKernelEstimator() {
		return nbUserKernelEstimator;
	}
	public static void setNbUserKernelEstimator(boolean nbUserKernelEstimator) {
		ClassifierParameters.nbUserKernelEstimator = nbUserKernelEstimator;
	}

	public static boolean isNbUseSupervisedDisc() {
		return nbUseSupervisedDisc;
	}
	public static void setNbUseSupervisedDisc(boolean nbUseSupervisedDisc) {
		ClassifierParameters.nbUseSupervisedDisc = nbUseSupervisedDisc;
	}

	
	
	// NAIVE BAYES UPDATEABLE PARAMETERS
	public static int getNbuBatchSize() {
		return nbuBatchSize;
	}
	public static void setNbuBatchSize(int nbuBatchSize) {
		ClassifierParameters.nbuBatchSize = nbuBatchSize;
	}

	public static boolean isNbuDebug() {
		return nbuDebug;
	}
	public static void setNbuDebug(boolean nbuDebug) {
		ClassifierParameters.nbuDebug = nbuDebug;
	}

	public static boolean isNbuDisplayModelInOldFormat() {
		return nbuDisplayModelInOldFormat;
	}
	public static void setNbuDisplayModelInOldFormat(boolean nbuDisplayModelInOldFormat) {
		ClassifierParameters.nbuDisplayModelInOldFormat = nbuDisplayModelInOldFormat;
	}

	public static boolean isNbuDoNotCheckCapabilities() {
		return nbuDoNotCheckCapabilities;
	}
	public static void setNbuDoNotCheckCapabilities(boolean nbuDoNotCheckCapabilities) {
		ClassifierParameters.nbuDoNotCheckCapabilities = nbuDoNotCheckCapabilities;
	}

	public static int getNbuNumDecimalPlaces() {
		return nbuNumDecimalPlaces;
	}
	public static void setNbuNumDecimalPlaces(int nbuNumDecimalPlaces) {
		ClassifierParameters.nbuNumDecimalPlaces = nbuNumDecimalPlaces;
	}

	public static boolean isNbuUserKernelEstimator() {
		return nbuUserKernelEstimator;
	}
	public static void setNbuUserKernelEstimator(boolean nbuUserKernelEstimator) {
		ClassifierParameters.nbuUserKernelEstimator = nbuUserKernelEstimator;
	}

	public static boolean isNbuUseSupervisedDisc() {
		return nbuUseSupervisedDisc;
	}
	public static void setNbuUseSupervisedDisc(boolean nbuUseSupervisedDisc) {
		ClassifierParameters.nbuUseSupervisedDisc = nbuUseSupervisedDisc;
	}

	
	
	// J48 PARAMETERS
	public static int getJ48batchSize() {
		return j48batchSize;
	}
	public static void setJ48batchSize(int j48batchSize) {
		ClassifierParameters.j48batchSize = j48batchSize;
	}

	public static boolean isJ48binarySplit() {
		return j48binarySplit;
	}
	public static void setJ48binarySplit(boolean j48binarySplit) {
		ClassifierParameters.j48binarySplit = j48binarySplit;
	}

	public static boolean isJ48collapseTree() {
		return j48collapseTree;
	}
	public static void setJ48collapseTree(boolean j48collapseTree) {
		ClassifierParameters.j48collapseTree = j48collapseTree;
	}

	public static double getJ48confidenceFactor() {
		return j48confidenceFactor;
	}
	public static void setJ48confidenceFactor(double j48confidenceFactor) {
		ClassifierParameters.j48confidenceFactor = j48confidenceFactor;
	}

	public static boolean isJ48debug() {
		return j48debug;
	}
	public static void setJ48debug(boolean j48debug) {
		ClassifierParameters.j48debug = j48debug;
	}

	public static boolean isJ48doNotCheckCapabilities() {
		return j48doNotCheckCapabilities;
	}
	public static void setJ48doNotCheckCapabilities(boolean j48doNotCheckCapabilities) {
		ClassifierParameters.j48doNotCheckCapabilities = j48doNotCheckCapabilities;
	}

	public static boolean isJ48doNotMakeSplitPAV() {
		return j48doNotMakeSplitPAV;
	}
	public static void setJ48doNotMakeSplitPAV(boolean j48doNotMakeSplitPAV) {
		ClassifierParameters.j48doNotMakeSplitPAV = j48doNotMakeSplitPAV;
	}

	public static int getJ48minNumObj() {
		return j48minNumObj;
	}
	public static void setJ48minNumObj(int j48minNumObj) {
		ClassifierParameters.j48minNumObj = j48minNumObj;
	}

	public static int getJ48numDecimalPlaces() {
		return j48numDecimalPlaces;
	}
	public static void setJ48numDecimalPlaces(int j48numDecimalPlaces) {
		ClassifierParameters.j48numDecimalPlaces = j48numDecimalPlaces;
	}

	public static int getJ48numFolds() {
		return j48numFolds;
	}
	public static void setJ48numFolds(int j48numFolds) {
		ClassifierParameters.j48numFolds = j48numFolds;
	}

	public static boolean isJ48reduceErrorPruning() {
		return j48reduceErrorPruning;
	}
	public static void setJ48reduceErrorPruning(boolean j48reduceErrorPruning) {
		ClassifierParameters.j48reduceErrorPruning = j48reduceErrorPruning;
	}

	public static boolean isJ48saveInstanceData() {
		return j48saveInstanceData;
	}
	public static void setJ48saveInstanceData(boolean j48saveInstanceData) {
		ClassifierParameters.j48saveInstanceData = j48saveInstanceData;
	}

	public static int getJ48seed() {
		return j48seed;
	}
	public static void setJ48seed(int j48seed) {
		ClassifierParameters.j48seed = j48seed;
	}

	public static boolean isJ48subTreeRaising() {
		return j48subTreeRaising;
	}
	public static void setJ48subTreeRaising(boolean j48subTreeRaising) {
		ClassifierParameters.j48subTreeRaising = j48subTreeRaising;
	}

	public static boolean isJ48unpruned() {
		return j48unpruned;
	}
	public static void setJ48unpruned(boolean j48unpruned) {
		ClassifierParameters.j48unpruned = j48unpruned;
	}

	public static boolean isJ48useLaplace() {
		return j48useLaplace;
	}
	public static void setJ48useLaplace(boolean j48useLaplace) {
		ClassifierParameters.j48useLaplace = j48useLaplace;
	}

	public static boolean isJ48useMDLcorrection() {
		return j48useMDLcorrection;
	}
	public static void setJ48useMDLcorrection(boolean j48useMDLcorrection) {
		ClassifierParameters.j48useMDLcorrection = j48useMDLcorrection;
	}

	
	
	
	// K STAR PARAMETERS	

	public static int getkStarbatchSize() {
		return kStarbatchSize;
	}

	public static void setkStarbatchSize(int kStarbatchSize) {
		ClassifierParameters.kStarbatchSize = kStarbatchSize;
	}
	

	public static boolean iskStardebug() {
		return kStardebug;
	}

	public static void setkStardebug(boolean kStardebug) {
		ClassifierParameters.kStardebug = kStardebug;
	}
	

	public static boolean iskStardoNotCheckCapabilities() {
		return kStardoNotCheckCapabilities;
	}

	public static void setkStardoNotCheckCapabilities(boolean kStardoNotCheckCapabilities) {
		ClassifierParameters.kStardoNotCheckCapabilities = kStardoNotCheckCapabilities;
	}
	

	public static boolean iskStarentropicAutoBlend() {
		return kStarentropicAutoBlend;
	}

	public static void setkStarentropicAutoBlend(boolean kStarentropicAutoBlend) {
		ClassifierParameters.kStarentropicAutoBlend = kStarentropicAutoBlend;
	}
	

	public static int getkStarglobalBlend() {
		return kStarglobalBlend;
	}

	public static void setkStarglobalBlend(int kStarglobalBlend) {
		ClassifierParameters.kStarglobalBlend = kStarglobalBlend;
	}
	

	public static KStarMissingMode getkStarmissingMode() {
		return kStarmissingMode;
	}
	public static void setkStarmissingMode(KStarMissingMode kStarmissingMode) {
		ClassifierParameters.kStarmissingMode = kStarmissingMode;
	}

	public static int getkStarnumDecimalPlaces() {
		return kStarnumDecimalPlaces;
	}

	public static void setkStarnumDecimalPlaces(int kStarnumDecimalPlaces) {
		ClassifierParameters.kStarnumDecimalPlaces = kStarnumDecimalPlaces;
	}
	
	
	
	// HEOFFDING TREE PARAMETERS
	public static int getHtbatchSize() {
		return htbatchSize;
	}
	public static void setHtbatchSize(int htbatchSize) {
		ClassifierParameters.htbatchSize = htbatchSize;
	}
	
	public static boolean isHtdebug() {
		return htdebug;
	}
	public static void setHtdebug(boolean htdebug) {
		ClassifierParameters.htdebug = htdebug;
	}
	
	public static boolean isHtdoNotCheckCapabilities() {
		return htdoNotCheckCapabilities;
	}
	public static void setHtdoNotCheckCapabilities(boolean htdoNotCheckCapabilities) {
		ClassifierParameters.htdoNotCheckCapabilities = htdoNotCheckCapabilities;
	}
	
	public static double getHtgracePeriod() {
		return htgracePeriod;
	}
	public static void setHtgracePeriod(double htgracePeriod) {
		ClassifierParameters.htgracePeriod = htgracePeriod;
	}
	
	public static double getHthoeffdingTieThreshold() {
		return hthoeffdingTieThreshold;
	}
	public static void setHthoeffdingTieThreshold(double hthoeffdingTieThreshold) {
		ClassifierParameters.hthoeffdingTieThreshold = hthoeffdingTieThreshold;
	}
	
	public static HoeffdingTreeLPS getHtleafPredictionStrategy() {
		return htleafPredictionStrategy;
	}
	public static void setHtleafPredictionStrategy(HoeffdingTreeLPS htleafPredictionStrategy) {
		ClassifierParameters.htleafPredictionStrategy = htleafPredictionStrategy;
	}
	
	public static double getHtminimumFractionOfWeightInfoGain() {
		return htminimumFractionOfWeightInfoGain;
	}
	public static void setHtminimumFractionOfWeightInfoGain(double htminimumFractionOfWeightInfoGain) {
		ClassifierParameters.htminimumFractionOfWeightInfoGain = htminimumFractionOfWeightInfoGain;
	}
	
	public static double getHtnaiveBayesPredictionThreshold() {
		return htnaiveBayesPredictionThreshold;
	}
	public static void setHtnaiveBayesPredictionThreshold(double htnaiveBayesPredictionThreshold) {
		ClassifierParameters.htnaiveBayesPredictionThreshold = htnaiveBayesPredictionThreshold;
	}
	
	public static int getHtnumDecimalPlaces() {
		return htnumDecimalPlaces;
	}
	public static void setHtnumDecimalPlaces(int htnumDecimalPlaces) {
		ClassifierParameters.htnumDecimalPlaces = htnumDecimalPlaces;
	}
	
	public static boolean isHtprintLeafModels() {
		return htprintLeafModels;
	}
	public static void setHtprintLeafModels(boolean htprintLeafModels) {
		ClassifierParameters.htprintLeafModels = htprintLeafModels;
	}
	
	public static double getHtsplitConfidence() {
		return htsplitConfidence;
	}
	public static void setHtsplitConfidence(double htsplitConfidence) {
		ClassifierParameters.htsplitConfidence = htsplitConfidence;
	}
	
	public static HoeffdingTreeSplitCriterion getHtsplitCriterion() {
		return htsplitCriterion;
	}
	public static void setHtsplitCriterion(HoeffdingTreeSplitCriterion htsplitCriterion) {
		ClassifierParameters.htsplitCriterion = htsplitCriterion;
	}
	
	
}
