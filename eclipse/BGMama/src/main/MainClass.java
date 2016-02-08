package main;

import model.BayesAlgorithm;
import model.DataManager;
import model.JSONReader;

public class MainClass {

	public static void main(String[] args) {

		DataManager.loadTrainingData();

//		DataManager.printTrainingData();
		BayesAlgorithm.startTraining();
		//		JSONReader.readStream();

	}

}
