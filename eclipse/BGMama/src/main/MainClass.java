package main;

import java.util.ArrayList;

import com.memetix.mst.translate.Translate;

import model.AlgorithmManager;
import model.BayesAlgorithm;
import model.Comment;
import model.DataManager;
import model.Hotel;
import model.JSONReader;
import model.LoadGApp;
import model.StatisticsManager;
import model.Translation;
import model.XMLWriter;

public class MainClass {

	public static void main(String[] args) {
		
//		StatisticsManager.saveStatistics("src/Supporting Files/results_EN_1_whithout_enST.txt");
		
//		AlgorithmManager.classifyTestSetOnlyEn();
		XMLWriter.createFile("translated_calassified_bg_train", "training_set");
		for (Comment comment : DataManager.getTranslatedTestData(DataManager.translatedTestDataFilePath,
				Comment.EN)) {
			Comment commentBg = DataManager.getSingleComment(comment.getCommentId(), DataManager.getTrainingData(DataManager.bgTrainingDataFilePath, Comment.BG));
			commentBg.setCommentCategoryText(comment.getCommentCategoryText());
			XMLWriter.writeObjectInfoFile(commentBg);			
		}
		XMLWriter.closeFile();
		
		
//		Translation translateTest = new Translation();
//		translateTest.translateTestData(Comment.BG,"src/Supporting Files//test_data_new.xml","test_data_new_EN_translated");
//		JSONReader.readEnStream();
		// // Statistics - Bayes
//		BayesAlgorithm bayesAlgBg = AlgorithmManager.getBgBayesAlgorithm();
//		for (Comment comment : DataManager.getTestData(DataManager.bgTestDataFilePath)) {
//			comment.setClassifiedType(bayesAlgBg.classifyComment(comment));
//		}
//		StatisticsManager statisticsManager = new StatisticsManager(DataManager.getTestData(DataManager.bgTestDataFilePath));
//		statisticsManager.printStatistics();

		// Statistics - Hotels
//		ArrayList<Hotel> foundHotels = LoadGApp.executeGAppWithTestComment();
//		ArrayList<String> locations = DataManager.getTestCommentHotels();
//
//		int tp = 0;
//
//		for (String string : locations) {
//			for (Hotel hotel : foundHotels) {
//				if (!hotel.isFound() && string.equals(hotel.getName())) {
//					tp++;
//					hotel.setFound(true);
//				}
//			}
//		}
//
//		int fp = foundHotels.size() - tp;
//		int fn = locations.size() - tp;
//
//		StatisticsManager statisticsManager2 = new StatisticsManager(tp, fp, fn);
//		statisticsManager2.printStatistics();
		// JSONReader.readStream();

	}

}
