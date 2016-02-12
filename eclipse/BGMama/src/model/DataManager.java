package model;

import java.util.ArrayList;

import model.Comment.ClassType;
import model.XMLParser.DataType;

public class DataManager {
	
	private static final String trainingDataFilePath = new String("src/Supporting Files/TrainingData.xml");
	private static final String testDataFilePath = new String("src/Supporting Files//TestData.xml");
	private static final String unknownDataFilePath = new String("src/Supporting Files/UnknownData.xml");
	
	private static ArrayList<Comment> trainingData = null;
	private static ArrayList<Comment> testData = null;
	private static ArrayList<Comment> unknownData = null;
	
	public static ArrayList<Comment> getTrainingData() {
		if (trainingData == null) {
			loadTrainingData();
		}
		return trainingData;
	}
	
	public static ArrayList<Comment> getTestData() {
		if(testData == null){
			loadTestData();
		}
		return testData;
	}
	
	public static ArrayList<Comment> getUnknownData() {
		if(unknownData == null) {
			loadUnknownData();
		}
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
		setTrainingData(new ArrayList<Comment>());
		XMLParser.parseFile(trainingDataFilePath, DataType.DataTypeTraining);
	}
	
	public static void loadTestData() {
		setTestData(new ArrayList<Comment>());
		XMLParser.parseFile(testDataFilePath, DataType.DataTypeTest);
	}
	
	public static void loadUnknownData() {
		setUnknownData(new ArrayList<Comment>());
		XMLParser.parseFile(unknownDataFilePath, DataType.DataTypeUnknown);
	}
	
	public static int trueGuessedClassesInTestDataCount(ClassType type){
		int n = 0;
		for (Comment comment : testData) {
			if(comment.getCommentCategory() == type && comment.getCommentCategory() == comment.getClassifiedType()){
//				System.out.println("\n" + comment.toString());
				n++;
			}
		}
		return n;
	}
	
	public static int falseGuessedClassesInTestDataCount(ClassType type){
		int n = 0;
		for (Comment comment : testData) {
			if(comment.getClassifiedType() == type && comment.getCommentCategory() != comment.getClassifiedType()){
//				System.out.println("\n" + comment.toString());
				n++;
			}
		}
		return n;
	}
	
	public static int notSelectedCommentsFromClass(ClassType type){
		int n = 0;
		for (Comment comment : testData) {
			if(comment.getCommentCategory() == type && comment.getCommentCategory() != comment.getClassifiedType()){
//				System.out.println("\n" + comment.toString());
				n++;
			}
		}
		return n;
	}
	
	public static void printTrainingData() {
		printData(getTrainingData());
	}
	
	public static void printTestData() {
		printData(getTestData());
	}
	
	public static void printUnknownData () {
		printData(getUnknownData());
	}
	
	private static void printData(ArrayList<Comment> data) {
		System.out.println(data.toString());
	}

}
