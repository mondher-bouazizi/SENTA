package backend.classifiers;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import weka.core.Instances;

public interface Classifier {
	
	public abstract void setParameters();
	
	public abstract void train(@Nonnull Instances trainingData);
	
	public abstract void train(String arffFile);
	
	public abstract void crossValidate(String arffFile, ArrayList<String> classes);
	
	public abstract void trainingSplit(String arffTrainingFile, int percentage, ArrayList<String> classes);
	
	public abstract void test(String arffTrainingFile, String arffTestFile, ArrayList<String> classes);

}
