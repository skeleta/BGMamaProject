package model;

import java.util.ArrayList;

import model.Comment.ClassType;

public class BayesAlgorithm {
	
	private static ArrayList<Word> termsDictionary = new ArrayList<>();
	
	// training
	public static void startTraining() {
		ArrayList<Comment> trainingData = DataManager.getTrainingData();
		formTermsDictionary(trainingData);
		
		ArrayList<Comment> positiveComments = formDocumentsSet(ClassType.ClassTypePositive, trainingData);
		double positiveProbability = calculatePriorProbability(positiveComments, trainingData);
		concatenateComments(positiveComments);
		
		// text from concatenated comments (positive and negative)
		ArrayList<Word> text = null;
		calculateWordsProbability(text, text);
		
		ArrayList<Comment> negativeComments = formDocumentsSet(ClassType.ClassTypeNegative, trainingData);
		double negativeProbability = calculatePriorProbability(negativeComments, trainingData);
		concatenateComments(negativeComments);	
		
	}
	
	private static void formTermsDictionary(ArrayList<Comment> trainingData) {
		//TODO: save all diff words and symbols in the trainingData into termsDictionary with object Word
		termsDictionary = new ArrayList<>();
	}
	
	private static void concatenateComments(ArrayList<Comment> comments) {
		// concatenate all comment in one text - textj		
	}
	
	private static double calculatePriorProbability(ArrayList<Comment> comments, ArrayList<Comment> trainingData) {		
		double probabilityType = (double) comments.size() / trainingData.size();
		return probabilityType;		
	}
	
	private static void calculateWordsProbability(ArrayList<Word> negativeText, ArrayList<Word> positiveText){
		// calculate n - common count of diff positions in textj
		int nNegative = negativeText.size();
		int nPositive = positiveText.size();
		
		for (Word word : termsDictionary) {
			double positiveProbability = (word.getPositiveOccurence() + 1)/(nPositive + termsDictionary.size());
			word.setPositiveProbability(positiveProbability);
			
			double negativeProbability = (word.getNegativeOccurence() + 1)/(nNegative + termsDictionary.size());
			word.setNegativeProbablity(negativeProbability);
		}
	}
	
	private static ArrayList<Comment> formDocumentsSet(Comment.ClassType type, ArrayList<Comment> trainingData) {
		ArrayList<Comment> documentsSet = new ArrayList<>();
		for (Comment comment : trainingData) {
			if (comment.getCommentCategory() == type) {
				documentsSet.add(comment);
			}
		}		
		return documentsSet;
	}
	
	// classifying
}
