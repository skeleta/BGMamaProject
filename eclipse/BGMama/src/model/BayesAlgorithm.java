package model;

import java.util.ArrayList;

import model.Comment.ClassType;

public class BayesAlgorithm {
	private static double negativeProbability = 0;
	private static double positiveProbability = 0;
	
	private ArrayList<Word> termsDictionary = new ArrayList<>();
	
	public BayesAlgorithm(ArrayList<Comment> trainingData) {
		super();
		startTraining(trainingData);
	}

	// training
	public void startTraining(ArrayList<Comment> trainingData) {
		formTermsDictionary(trainingData);
		
		ArrayList<Comment> positiveComments = formDocumentsSet(ClassType.ClassTypePositive, trainingData);
		positiveProbability = calculatePriorProbability(positiveComments, trainingData);
		int nPositive = getWordsCountInComments(positiveComments);
		
		ArrayList<Comment> negativeComments = formDocumentsSet(ClassType.ClassTypeNegative, trainingData);
		negativeProbability = calculatePriorProbability(negativeComments, trainingData);
		int nNegative = getWordsCountInComments(negativeComments);	
		
		calculateWordsProbability(nPositive, nNegative);
		
	}
	
	private void formTermsDictionary(ArrayList<Comment> trainingData) {
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
	
	private int getWordsCountInComments(ArrayList<Comment> comments) {
		int wordCount = 0;
		for (Comment comment : comments) {
			wordCount += comment.getWordCount();
		}
		return wordCount;
	}
	
	private double calculatePriorProbability(ArrayList<Comment> comments, ArrayList<Comment> trainingData) {		
		double probabilityType = (double) comments.size() / trainingData.size();
		return probabilityType;		
	}
	
	private void calculateWordsProbability(int nPositive, int nNegative){
		// calculate n - common count of diff positions in textj
		
		for (Word word : termsDictionary) {
			double positiveProbability = (word.getPositiveOccurence() + 1.0)/(nPositive + termsDictionary.size());
			word.setPositiveProbability(positiveProbability);
			
			double negativeProbability = (word.getNegativeOccurence() + 1.0)/(nNegative + termsDictionary.size());
			word.setNegativeProbablity(negativeProbability);
		}
	}
	
	private ArrayList<Comment> formDocumentsSet(Comment.ClassType type, ArrayList<Comment> trainingData) {
		ArrayList<Comment> documentsSet = new ArrayList<>();
		for (Comment comment : trainingData) {
			if (comment.getCommentCategory() == type) {
				documentsSet.add(comment);
			}
		}		
		return documentsSet;
	}
	
	public double getNegativeProbability() {
		return negativeProbability;
	}

	public double getPositiveProbability() {
		return positiveProbability;
	}
	
	// classifying
	public ClassificationResult classifyComment(Comment comment) {
		ArrayList<Word> allMatchingWords = findAllMatchingWords(comment);
		double wordsPositiveProbability = 0.0; //(P(word|positiveClass)
		double wordsNegativeProbability = 0.0; //(P(word|negativeClass)
		for (Word word : allMatchingWords) {
			wordsPositiveProbability += Math.log(word.getPositiveProbability());
			wordsNegativeProbability += Math.log(word.getNegativeProbablity());
		}
		
		wordsPositiveProbability += Math.log(positiveProbability);
		wordsNegativeProbability += Math.log(negativeProbability);
		
		return mostProbableClass(wordsPositiveProbability, wordsNegativeProbability);		
	}
	
	private ArrayList<Word> findAllMatchingWords(Comment comment){
		ArrayList<Word> allMatchingWords = new ArrayList<>();
		for (Word word : comment.getUniqueWords()) {
			for (Word dictionaryWord : termsDictionary) {
				if(word.equals(dictionaryWord)) {
					allMatchingWords.add(dictionaryWord);
				}
			}
		}
		return allMatchingWords;
	}
	
	private ClassificationResult mostProbableClass(double positiveProb, double negativeProb) {
		ClassType type = ClassType.ClassTypeUnknown;
		double maxProbability = 0;
		if (positiveProb > negativeProb) {
			type = ClassType.ClassTypePositive;
			maxProbability = positiveProb;
		} else if (positiveProb < negativeProb) {
			type = ClassType.ClassTypeNegative;
			maxProbability = negativeProb;
		} else {
			if (positiveProbability > negativeProbability) {
				type = ClassType.ClassTypePositive;
				maxProbability = positiveProb;
			} else if (positiveProbability < negativeProbability) {
				type = ClassType.ClassTypeNegative;
				maxProbability = negativeProb;
			}
		}
		return new ClassificationResult(type, maxProbability);
	}	
}
