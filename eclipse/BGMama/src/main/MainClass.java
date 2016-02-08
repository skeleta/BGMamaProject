package main;

import model.BayesAlgorithm;
import model.Comment;
import model.Comment.ClassType;
import model.DataManager;

public class MainClass {

	public static void main(String[] args) {

		DataManager.loadTrainingData();

		DataManager.loadTestData();
//		DataManager.printTrainingData();
		BayesAlgorithm.startTraining();
		for (Comment comment : DataManager.getTestData()) {
			comment.setClassifiedType(BayesAlgorithm.classifyComment(comment));
//			break;
		}
		DataManager.prindAllTrueGuessedClassesInTestData(ClassType.ClassTypeNegative);
		//		JSONReader.readStream();

	}

}
