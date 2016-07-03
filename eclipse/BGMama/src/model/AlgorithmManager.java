package model;

import model.Comment.ClassType;

public class AlgorithmManager {
	
	private static BayesAlgorithm bgBayesAlgorithm = null;
	private static BayesAlgorithm enBayesAlgorithm = null;
	
	public static BayesAlgorithm getBgBayesAlgorithm() {
		if (bgBayesAlgorithm == null) {
			bgBayesAlgorithm = new BayesAlgorithm(DataManager.getTrainingData(DataManager.bgTrainingDataFilePath));
		}
		return bgBayesAlgorithm;
	}
	
	public static BayesAlgorithm getEnBayesAlgorithm() {
		if (enBayesAlgorithm == null) {
			enBayesAlgorithm = new BayesAlgorithm(DataManager.getTrainingData(DataManager.enTrainingDataFilePath));
		}
		return enBayesAlgorithm;
	}
	
	public static void classifyTestSet(String testSetPath, String translateSetPath, String language){
		for (Comment comment : DataManager.getTestData(testSetPath)) {
			ClassType type = classifyComment(comment, language, translateSetPath);
			if(type != null) comment.setClassifiedType(type);
		}
		
		DataManager.printTestData(testSetPath);
	}
	
	private static ClassType classifyComment(Comment comment, String language, String translateSetPath) {
		ClassificationResult bgResult = null;
		ClassificationResult enResult = null;
		if (language == Comment.BG) {
			bgResult = getBgBayesAlgorithm().classifyComment(comment);
			enResult = getEnBayesAlgorithm().classifyComment(DataManager.getSingleComment(comment.getCommentId(), DataManager.getTranslatedTestData(translateSetPath)));
		} else if(language == Comment.EN){
			enResult = getEnBayesAlgorithm().classifyComment(comment);
			bgResult = getBgBayesAlgorithm().classifyComment(DataManager.getSingleComment(comment.getCommentId(), DataManager.getTranslatedTestData(translateSetPath)));
		}
		
		ClassificationResult maxResult = bgResult.mostProbableResult(enResult);
		return maxResult.getClassType();
	}

}
