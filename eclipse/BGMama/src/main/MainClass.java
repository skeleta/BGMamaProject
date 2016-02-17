package main;

import java.util.ArrayList;

import model.BayesAlgorithm;
import model.Comment;
import model.DataManager;
import model.Hotel;
import model.JSONReader;
import model.LoadGApp;
import model.StatisticsManager;

public class MainClass {

	public static void main(String[] args) {

		//		// Statistics - Bayes
		//		BayesAlgorithm.startTraining();
		//		for (Comment comment : DataManager.getTestData()) {
		//			comment.setClassifiedType(BayesAlgorithm.classifyComment(comment));
		//		}
		//		StatisticsManager statisticsManager = new StatisticsManager(DataManager.getTestData());
		//		statisticsManager.printStatistics();

		//Statistics - Hotels

		ArrayList<Hotel> foundHotels = LoadGApp.executeGAppWithTestComment();
		ArrayList<String> locations = DataManager.getTestCommentHotels();

		int tp = 0;

		for (String string : locations) {
			for (Hotel hotel : foundHotels) {
				if(!hotel.isFound() && string.equals(hotel.getName())) {
					tp++;
					hotel.setFound(true);
				}
			}					
		}

		int fp = foundHotels.size() - tp;
		int fn = locations.size() - tp;

		StatisticsManager statisticsManager2 = new StatisticsManager(tp,fp,fn);
		statisticsManager2.printStatistics();
		//		JSONReader.readStream();

	}

}
