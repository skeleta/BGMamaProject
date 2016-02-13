package main;

import model.BayesAlgorithm;
import model.Comment;
import model.DataManager;
import model.JSONReader;
import model.LoadGApp;
import model.StatisticsManager;

public class MainClass {

	public static void main(String[] args) {

		//		DataManager.loadTrainingData();
				LoadGApp.executeGApp();
//		DataManager.printUnknownData();

		//		DataManager.loadTestData();
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
