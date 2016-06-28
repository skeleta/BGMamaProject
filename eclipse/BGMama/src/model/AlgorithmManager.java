package model;

public class AlgorithmManager {
	
	private static BayesAlgorithm bgBayesAlgorithm = null;
	private static BayesAlgorithm enBayesAlgorithm = null;
	
	public static BayesAlgorithm getBgBayesAlgorithm() {
		if (bgBayesAlgorithm == null) {
			BayesAlgorithm bayesAlgBg = new BayesAlgorithm(DataManager.getTrainingData(DataManager.bgTrainingDataFilePath));
			bgBayesAlgorithm = bayesAlgBg;
		}
		return bgBayesAlgorithm;
	}
	public static BayesAlgorithm getEnBayesAlgorithm() {
		return enBayesAlgorithm;
	}
	
	
	

}
