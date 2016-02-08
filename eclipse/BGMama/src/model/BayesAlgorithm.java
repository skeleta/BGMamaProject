package model;

import java.util.ArrayList;

import model.Comment.ClassType;

public class BayesAlgorithm {
	private static double negativeProbability = 0;
	private static double positiveProbability = 0;
	
	private static ArrayList<Word> termsDictionary = new ArrayList<>();
	
	// training
	public static void startTraining() {
		ArrayList<Comment> trainingData = DataManager.getTrainingData();
		formTermsDictionary(trainingData);
		
		ArrayList<Comment> positiveComments = formDocumentsSet(ClassType.ClassTypePositive, trainingData);
		positiveProbability = calculatePriorProbability(positiveComments, trainingData);
		int nPositive = getWordsCountInComments(positiveComments);
		
		ArrayList<Comment> negativeComments = formDocumentsSet(ClassType.ClassTypeNegative, trainingData);
		negativeProbability = calculatePriorProbability(negativeComments, trainingData);
		int nNegative = getWordsCountInComments(negativeComments);	
		
		calculateWordsProbability(nPositive, nNegative);
		
	}
	
	private static void formTermsDictionary(ArrayList<Comment> trainingData) {
		//save all diff words and symbols in the trainingData into termsDictionary with object Word
		termsDictionary = new ArrayList<>();
		ArrayList<String> addedStrings = new ArrayList<>();
		for (Comment comment : trainingData) {
			for (Word word : comment.getUniqueWords()) {
				String term = word.getTerm();
				if (addedStrings.contains(term)) {
					// increase occurence of existing word
					Word addedWord = termsDictionary.get(addedStrings.indexOf(term));
					addedWord.increaseOccurence(comment.getCommentCategory());
				} else {
					// add new word to the dictionary
					termsDictionary.add(word);
					addedStrings.add(term);
				}
			}
		}
	}
	
	private static int getWordsCountInComments(ArrayList<Comment> comments) {
		int wordCount = 0;
		for (Comment comment : comments) {
			wordCount += comment.getWordCount();
		}
		return wordCount;
	}
	
	private static double calculatePriorProbability(ArrayList<Comment> comments, ArrayList<Comment> trainingData) {		
		double probabilityType = (double) comments.size() / trainingData.size();
		return probabilityType;		
	}
	
	private static void calculateWordsProbability(int nPositive, int nNegative){
		// calculate n - common count of diff positions in textj
		
		for (Word word : termsDictionary) {
			double positiveProbability = (word.getPositiveOccurence() + 1.0)/(nPositive + termsDictionary.size());
			word.setPositiveProbability(positiveProbability);
			
			double negativeProbability = (word.getNegativeOccurence() + 1.0)/(nNegative + termsDictionary.size());
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
	
	public static double getNegativeProbability() {
		return negativeProbability;
	}

	public static double getPositiveProbability() {
		return positiveProbability;
	}
	
	
	// classifying
}
