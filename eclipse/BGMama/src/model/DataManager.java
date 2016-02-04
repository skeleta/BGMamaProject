package model;

import java.util.ArrayList;

import model.XMLParser.DataType;

public class DataManager {
	
	private static final String trainingDataFilePath = new String("src/Supporting Files/TrainingData.xml");
	private static final String testDataFilePath = new String("src/Supporting Files/TestData.xml");
	private static final String unknownDataFilePath = new String("src/Supporting Files/UnknownData.xml");
	
	private static ArrayList<Comment> trainingData = new ArrayList<>();
	private static ArrayList<Comment> testData = new ArrayList<>();
	private static ArrayList<Comment> unknownData = new ArrayList<>();
	
	public static ArrayList<Comment> getTrainingData() {
		return trainingData;
	}
	
	public static ArrayList<Comment> getTestData() {
		return testData;
	}
	
	public static ArrayList<Comment> getUnknownData() {
		return unknownData;
	}

	protected static void setTrainingData(ArrayList<Comment> trainingData) {
		DataManager.trainingData = trainingData;
	}


	protected static void setTestData(ArrayList<Comment> testData) {
		DataManager.testData = testData;
	}

	protected static void setUnknownData(ArrayList<Comment> unknownData) {
		DataManager.unknownData = unknownData;
	}
	
	public static void loadTrainingData() {
		XMLParser.parseFile(trainingDataFilePath, DataType.DataTypeTraining);
	}
	
	public static void loadTestData() {
		XMLParser.parseFile(testDataFilePath, DataType.DataTypeTest);
	}
	
	public static void loadUnknownData() {
		XMLParser.parseFile(unknownDataFilePath, DataType.DataTypeUnknown);
	}
	
	public static void printTrainingData() {
		printData(trainingData);
	}
	
	public static void printTestData() {
		printData(testData);
	}
	
	public static void printUnknownData () {
		printData(unknownData);
	}
	
	private static void printData(ArrayList<Comment> data) {
		System.out.println(data.toString());
	}



}
