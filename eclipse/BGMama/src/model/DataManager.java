package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Comment.ClassType;
import model.XMLParser.DataType;

public class DataManager {

	public static final String bgTrainingDataFilePath = new String("src/Supporting Files/TrainingData.xml");
	public static final String enTrainingDataFilePath = new String("src/Supporting Files/TrainingDataEN.xml");

	public static final String bgTestDataFilePath = new String("src/Supporting Files//TestData.xml");
	public static final String translatedTestDataFilePath = new String("src/Supporting Files//TestDataENTranslated.xml");

	private static final String unknownDataFilePath = new String("src/Supporting Files/UnknownDataAll.xml");
	private static final String bgStopWordsFilePath = new String("src/Supporting Files/bulgarianST.txt");
	private static final String testCommentHotelsPath = new String("src/Supporting Files/hotels_in_test.txt");

	private static ArrayList<Comment> trainingData = null;
	private static ArrayList<Comment> testData = null;
	private static ArrayList<Comment> translatedTestData = null;
	private static ArrayList<Comment> unknownData = null;

	private static ArrayList<String> bgStopWords = null;
	private static ArrayList<String> testCommentHotels = null;

	public enum DataLanguage {
		DataLanguageOrigin, DataLanguageTranslated
	}

	public static ArrayList<Comment> getTrainingData(String trainingDataFilePath) {
		if (trainingData == null) {
			loadTrainingData(trainingDataFilePath);
		}
		return trainingData;
	}

	public static ArrayList<Comment> getTestData(String testDataPath) {
		if (testData == null) {
			loadTestData(testDataPath);
		}
		return testData;
	}

	public static ArrayList<Comment> getUnknownData() {
		if (unknownData == null) {
			loadUnknownData();
		}
		return unknownData;
	}

	public static ArrayList<String> getBgStopWords() {
		if (bgStopWords == null) {
			bgStopWords = new ArrayList<>();
			saveData(bgStopWords, bgStopWordsFilePath);
		}
		return bgStopWords;
	}

	public static ArrayList<String> getTestCommentHotels() {
		if (testCommentHotels == null) {
			testCommentHotels = new ArrayList<>();
			saveData(testCommentHotels, testCommentHotelsPath);
		}
		return testCommentHotels;
	}

	public static ArrayList<Comment> getTranslatedTestData(String translatedDataFilePath) {
		if (translatedTestData == null) {
			loadTranslatedTestData(translatedDataFilePath);
		}
		return translatedTestData;
	}
	
	public static Comment getSingleComment (String commentId, ArrayList<Comment> data){
		for (Comment comment : data) {
			if(commentId.equals(comment.getCommentId())){
				return comment;
			}
		}
		return null;
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

	public static void setTranslatedTestData(ArrayList<Comment> translatedTestData) {
		DataManager.translatedTestData = translatedTestData;
	}

	public static void loadTrainingData(String trainingDataFilePath) {
		setTrainingData(new ArrayList<Comment>());
		setTrainingData(XMLParser.parseFile(trainingDataFilePath));
	}

	public static void loadTestData(String testDataPath) {
		setTestData(new ArrayList<Comment>());
		setTestData(XMLParser.parseFile(testDataPath));
	}

	public static void loadTranslatedTestData(String translatedTestDataPath) {
		setTranslatedTestData(new ArrayList<Comment>());
		setTranslatedTestData(XMLParser.parseFile(translatedTestDataPath));
	}

	public static void loadUnknownData() {
		setUnknownData(new ArrayList<Comment>());
		setUnknownData(XMLParser.parseFile(unknownDataFilePath));
	}

	public static int trueGuessedClassesInTestDataCount(ClassType type) {
		int n = 0;
		for (Comment comment : testData) {
			if (comment.getCommentCategory() == type && comment.getCommentCategory() == comment.getClassifiedType()) {
				// System.out.println("\n" + comment.toString());
				n++;
			}
		}
		return n;
	}

	public static int falseGuessedClassesInTestDataCount(ClassType type) {
		int n = 0;
		for (Comment comment : testData) {
			if (comment.getClassifiedType() == type && comment.getCommentCategory() != comment.getClassifiedType()) {
				// System.out.println("\n" + comment.toString());
				n++;
			}
		}
		return n;
	}

	public static int notSelectedCommentsFromClass(ClassType type) {
		int n = 0;
		for (Comment comment : testData) {
			if (comment.getCommentCategory() == type && comment.getCommentCategory() != comment.getClassifiedType()) {
				// System.out.println("\n" + comment.toString());
				n++;
			}
		}
		return n;
	}

	public static void printTrainingData(String trainingDataFilePath) {
		printData(getTrainingData(trainingDataFilePath));
	}

	public static void printTestData(String testDataPath) {
		printData(getTestData(testDataPath));
	}

	public static void printUnknownData() {
		printData(getUnknownData());
	}

	private static void printData(ArrayList<Comment> data) {
		System.out.println(data.toString());
	}

	// help methods
	private static void saveData(ArrayList<String> array, String path) {
		BufferedReader br = null;

		try {
			FileReader fileReader = new FileReader(new File(path));
			br = new BufferedReader(fileReader);
			String line = br.readLine();
			while (line != null) {
				array.add(line);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			try {
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
