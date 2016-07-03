package model;

import java.io.FileWriter;
import java.io.IOException;

import model.ClassificationResult.ClassificationResultModel;
import model.Comment.ClassType;

public class AlgorithmManager {

	private static final String mixedResultsPath = "src/Supporting files/mixedResults.csv";
	private static final String onlyBgResultsPath = "src/Supporting files/onlyBgResults.csv";
	private static final String onlyEnResultsPath = "src/Supporting files/onlyEnResults.csv";

	private static BayesAlgorithm bgBayesAlgorithm = null;
	private static BayesAlgorithm enBayesAlgorithm = null;
	
	public static void restoreAlgorithms(){
		bgBayesAlgorithm = null;
		enBayesAlgorithm = null;
		DataManager.restoreData();
	}

	public static BayesAlgorithm getBgBayesAlgorithm() {
		if (bgBayesAlgorithm == null) {
			bgBayesAlgorithm = new BayesAlgorithm(
					DataManager.getTrainingData(DataManager.bgTrainingDataFilePath, Comment.BG));
		}
		return bgBayesAlgorithm;
	}

	public static BayesAlgorithm getEnBayesAlgorithm() {
		if (enBayesAlgorithm == null) {
			enBayesAlgorithm = new BayesAlgorithm(
					DataManager.getTranslateTrainingData(DataManager.enTrainingDataFilePath, Comment.EN));
		}
		return enBayesAlgorithm;
	}

	public static void classifyTestSet(String testSetPath, String translateSetPath, String language) {
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(mixedResultsPath);
			fileWriter.append("commentText, realClass, predictedClass, classifier, probability");
			fileWriter.append("\n");

			for (Comment comment : DataManager.getTestData(testSetPath, language)) {
				ClassificationResult result = classifyComment(comment, language, translateSetPath);
				comment.setClassifiedType(result.getClassType());
				comment.setClassificatorModel(result.getClassificatorModelString(result.getModel()));
				fileWriter.append(comment.getCommentText() + "," + comment.getCommentCategoryText() + ","
						+ comment.getClassifiedType() + "," + comment.getClassificatorModel() + ","
						+ result.getProbability());
				fileWriter.append("\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			try {
				fileWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public static void classifyTestSetOnlyEn() {
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(onlyEnResultsPath);
			fileWriter.append("commentText, realClass, predictedClass, classifier, probability");
			fileWriter.append("\n");
			BayesAlgorithm bayesAlgEn = AlgorithmManager.getEnBayesAlgorithm();
			for (Comment comment : DataManager.getTranslatedTestData(DataManager.translatedTestDataFilePath,
					Comment.EN)) {
				ClassificationResult result = bayesAlgEn.classifyComment(comment);
				comment.setClassifiedType(result.getClassType());
				comment.setClassificatorModel(
						result.getClassificatorModelString(ClassificationResultModel.ClassificationResultModelEN));
				fileWriter.append(comment.getCommentText() + "," + comment.getCommentCategoryText() + ","
						+ comment.getClassifiedType() + "," + comment.getClassificatorModel() + ","
						+ result.getProbability());
				fileWriter.append("\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			try {
				fileWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public static void classifyTestSetOnlyBg() {
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(onlyBgResultsPath);
			fileWriter.append("commentText, realClass, predictedClass, classifier, probability");
			fileWriter.append("\n");
			BayesAlgorithm bayesAlgBg = AlgorithmManager.getBgBayesAlgorithm();
			for (Comment comment : DataManager.getTestData(DataManager.bgTestDataFilePath, Comment.BG)) {
				ClassificationResult result = bayesAlgBg.classifyComment(comment);
				comment.setClassifiedType(result.getClassType());
				comment.setClassificatorModel(
						result.getClassificatorModelString(ClassificationResultModel.ClassificationResultModelBG));
				fileWriter.append(comment.getCommentText() + "," + comment.getCommentCategoryText() + ","
						+ comment.getClassifiedType() + "," + comment.getClassificatorModel() + ","
						+ result.getProbability());
				fileWriter.append("\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			try {
				fileWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	private static ClassificationResult classifyComment(Comment comment, String language, String translateSetPath) {
		ClassificationResult bgResult = null;
		ClassificationResult enResult = null;
		Comment translatedComment = null;
		if (language == Comment.BG) {
			translatedComment = DataManager.getSingleComment(comment.getCommentId(),
					DataManager.getTranslatedTestData(translateSetPath, Comment.EN));
			bgResult = getBgBayesAlgorithm().classifyComment(comment);
			enResult = getEnBayesAlgorithm().classifyComment(translatedComment);
		} else if (language == Comment.EN) {
			translatedComment = DataManager.getSingleComment(comment.getCommentId(),
					DataManager.getTranslatedTestData(translateSetPath, Comment.BG));
			enResult = getEnBayesAlgorithm().classifyComment(comment);
			bgResult = getBgBayesAlgorithm().classifyComment(translatedComment);
		}

		enResult.setModel(ClassificationResultModel.ClassificationResultModelEN);
		bgResult.setModel(ClassificationResultModel.ClassificationResultModelBG);

		ClassificationResult maxResult = bgResult.mostProbableResult(enResult);
		return maxResult;
	}

}
