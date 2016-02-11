package main;

import model.DataManager;
import model.StatisticsManager;
import model.DataStructure.Comment;
import model.DataStructure.Comment.ClassType;
import model.SemanticAnalyses.BayesAlgorithm;

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
		StatisticsManager statisticsManager = new StatisticsManager(DataManager.getTestData());
		statisticsManager.printStatistics();

		//		JSONReader.readStream();

	}

}
