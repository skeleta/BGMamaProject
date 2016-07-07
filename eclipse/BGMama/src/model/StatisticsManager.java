package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.Comment.ClassType;

public class StatisticsManager {

	private enum StatisticsType {
		StatisticsTypeData, StatisticsTypeNumbers
	}

	private StatisticsType type;

	private int negativeCommentsCount = 0;
	private int positiveCommentsCount = 0;

	private int negativeClassifiedCommentsCount = 0;
	private int positiveClassifiedCommentsCount = 0;

	private int truePositiveClassPositive = 0;
	private int falsePositiveClassPositive = 0;
	private int falseNegativeClassPositive = 0;

	private int truePositiveClassNegative = 0;
	private int falsePositiveClassNegative = 0;
	private int falseNegativeClassNegative = 0;

	ArrayList<Comment> data = null;

	public StatisticsManager(int truePositive, int falsePositive, int falseNegative) {
		super();
		type = StatisticsType.StatisticsTypeNumbers;
		this.truePositiveClassPositive = truePositive;
		this.falsePositiveClassPositive = falsePositive;
		this.falseNegativeClassPositive = falseNegative;
	}

	public StatisticsManager(ArrayList<Comment> data) {
		super();
		this.data = data;
		type = StatisticsType.StatisticsTypeData;
		calculateRealDataClassCommentsCount(data);
		calculateClassifiedClassData(data);
		// first calculate positive comments
		calculateTruePositiveComments();
		// after that calculate false positive
		calculateFalsePositiveComments();
		// and false negative
		calculateFalseNegativeComments();
	}

	public static void saveStatistics(String fileResultsPath) {
		FileWriter fileWriter = null;

		try {
			
			fileWriter = new FileWriter(fileResultsPath);
			AlgorithmManager.classifyTestSet(DataManager.bgTestDataFilePath, DataManager.translatedTestDataFilePath,
			Comment.BG);
			
			StatisticsManager statisticsManager = new StatisticsManager(
					DataManager.getTestData(DataManager.bgTestDataFilePath, Comment.BG));

			fileWriter.append("Results from mixed training \n");
			fileWriter.append(statisticsManager.getStatistics());
			fileWriter.append("\n");
			
			AlgorithmManager.restoreAlgorithms();
			
			AlgorithmManager.classifyTestSetOnlyBg();
			StatisticsManager statisticsManagerBg = new StatisticsManager(
					DataManager.getTestData(DataManager.bgTestDataFilePath, Comment.BG));

			fileWriter.append("Results from bg training only \n");
			fileWriter.append(statisticsManagerBg.getStatistics());
			fileWriter.append("\n");

			AlgorithmManager.restoreAlgorithms();
			
//			AlgorithmManager.classifyTestSetOnlyEn();
//			StatisticsManager statisticsManagerEn = new StatisticsManager(
//					DataManager.getTranslatedTestData(DataManager.translatedTestDataFilePath, Comment.EN));
//
//			fileWriter.append("Results from en training only \n");
//			fileWriter.append(statisticsManagerEn.getStatistics());
//			fileWriter.append("\n");
//
//			fileWriter.close();

			System.out.println("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				fileWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void printStatistics() {
		System.out.println(getStatistics());
	}

	public String getStatistics() {
		String result = printPositiveClassStatistics();
		if (type == StatisticsType.StatisticsTypeData) {
			result = result + printNegativeClassStatistics();
			result = result + printOverallStatistics();
		}
		return result;
	}

	private String printOverallStatistics() {
		String result = String.format("-------------------------------------- %n")
				+ String.format("Precision overall : %1$.5f %n", calculatePrecisionOverall())
				+ String.format("Recall overall : %1$.5f %n", calculateRecallOverall())
				+ String.format("F1 overall : %1$.5f %n", calculateF1Overall());
		return result;
	}

	private String printNegativeClassStatistics() {
		String result = String.format("-------------------------------------- %n")
				+ String.format("Precision negative: %1$.5f %n", calculatePrecisionNegativeClass())
				+ String.format("Recall negative: %1$.5f %n", calculateRecallNegativeClass())
				+ String.format("F1 negative: %1$.5f %n", calculateF1NegativeClass());
		return result;
	}

	private String printPositiveClassStatistics() {
		String addClass = type == StatisticsType.StatisticsTypeData ? "positive" : "";
		String result = String.format("-------------------------------------- %n")
				+ String.format("Precision %s: %1.5f %n", addClass, calculatePrecisionPositiveClass())
				+ String.format("Recall %s: %1.5f %n", addClass, calculateRecallPositiveClass())
				+ String.format("F1 %s: %1.5f %n", addClass, calculateF1PositiveClass());
		return result;
	}

	// precision
	public double calculatePrecisionOverall() {
		int tp = truePositiveClassNegative + truePositiveClassPositive;
		int fp = falsePositiveClassPositive + falsePositiveClassNegative;
		return calculatePrecision(tp, fp);
	}

	public double calculatePrecisionPositiveClass() {
		return calculatePrecision(truePositiveClassPositive, falsePositiveClassPositive);
	}

	public double calculatePrecisionNegativeClass() {
		return calculatePrecision(truePositiveClassNegative, falsePositiveClassNegative);
	}

	private double calculatePrecision(int tp, int fp) {
		double precision = (tp * 1.0) / (tp + fp);
		return precision;
	}

	// recall
	public double calculateRecallOverall() {
		int tp = truePositiveClassNegative + truePositiveClassPositive;
		int fn = falseNegativeClassNegative + falseNegativeClassPositive;
		return calculateRecall(tp, fn);
	}

	public double calculateRecallPositiveClass() {
		return calculateRecall(truePositiveClassPositive, falseNegativeClassPositive);
	}

	public double calculateRecallNegativeClass() {
		return calculateRecall(truePositiveClassNegative, falseNegativeClassNegative);
	}

	private double calculateRecall(int tp, int fn) {
		double recall = (1.0 * tp) / (tp + fn);
		return recall;
	}

	// F1 measure
	public double calculateF1Overall() {
		return calculateF1(calculatePrecisionOverall(), calculateRecallOverall());
	}

	public double calculateF1PositiveClass() {
		return calculateF1(calculatePrecisionPositiveClass(), calculateRecallPositiveClass());
	}

	public double calculateF1NegativeClass() {
		return calculateF1(calculatePrecisionNegativeClass(), calculateRecallNegativeClass());
	}

	private double calculateF1(double precision, double recall) {
		double f1 = (2 * precision * recall) / (precision + recall);
		return f1;
	}

	// Help methods
	private void calculateRealDataClassCommentsCount(ArrayList<Comment> data) {
		for (Comment comment : data) {
			if (comment.isPositive()) {
				positiveCommentsCount++;
			} else if (comment.isNegative()) {
				negativeCommentsCount++;
			}
		}
	}

	private void calculateClassifiedClassData(ArrayList<Comment> data) {
		for (Comment comment : data) {
			if (comment.getClassifiedType() == ClassType.ClassTypePositive) {
				positiveClassifiedCommentsCount++;
			} else if (comment.getClassifiedType() == ClassType.ClassTypeNegative) {
				negativeClassifiedCommentsCount++;
			}
		}
	}

	private void calculateTruePositiveComments() {
		this.truePositiveClassPositive = DataManager.trueGuessedClassesInTestDataCount(ClassType.ClassTypePositive,
				this.data);
		this.truePositiveClassNegative = DataManager.trueGuessedClassesInTestDataCount(ClassType.ClassTypeNegative,
				this.data);
	}

	private void calculateFalsePositiveComments() {
		this.falsePositiveClassPositive = positiveClassifiedCommentsCount - truePositiveClassPositive;
		this.falsePositiveClassNegative = negativeClassifiedCommentsCount - truePositiveClassNegative;
	}

	private void calculateFalseNegativeComments() {
		this.falseNegativeClassPositive = positiveCommentsCount - truePositiveClassPositive;
		this.falseNegativeClassNegative = negativeCommentsCount - truePositiveClassNegative;
	}
}
