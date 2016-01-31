package main;

import model.DataManager;

public class MainClass {

	public static void main(String[] args) {
		DataManager.loadTrainingData();
		DataManager.printTrainingData();
	}

}
