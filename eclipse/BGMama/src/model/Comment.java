package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Comment {

	public enum ClassType {
		ClassTypePositive, ClassTypeNegative, ClassTypeUnknown
	}

	public static final String BG = "bg";
	public static final String EN = "en";

	private String commentId;
	private String commentCategoryText;
	private String commentText;
	private ClassType classifiedType;
	private String classificatorModel;
	private String language;

	private int wordCount;

	private ArrayList<Word> uniqueWords = new ArrayList<>();

	private static final String positiveClass = "positive";
	private static final String negativeClass = "negative";
	private static final String unknownClass = "unknown";

	public Comment(String commentId, String commentCategoryText, String commentText, String language) {
		setCommentId(commentId);
		setCommentText(commentText);
		setCommentCategoryText(commentCategoryText);
		setLanguage(language);

		formUniqueWordsArray();
	}

	public String getCommentId() {
		return commentId;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentCategoryText() {
		return commentCategoryText;
	}

	public ClassType getClassifiedType() {
		return classifiedType;
	}

	public void setClassifiedType(ClassType classifiedType) {
		this.classifiedType = classifiedType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getClassificatorModel() {
		return classificatorModel;
	}

	public void setClassificatorModel(String classificatorModel) {
		this.classificatorModel = classificatorModel;
	}

	public ClassType getCommentCategory() {
		if (this.getCommentCategoryText().equals(positiveClass)) {
			return ClassType.ClassTypePositive;
		}

		if (this.getCommentCategoryText().equals(negativeClass)) {
			return ClassType.ClassTypeNegative;
		}

		return ClassType.ClassTypeUnknown;
	}

	public void setCommentCategoryText(String commentCategory) {
		this.commentCategoryText = commentCategory;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}	

	public void setUniqueWords(ArrayList<Word> uniqueWords) {
		this.uniqueWords = uniqueWords;
	}

	public ArrayList<Word> getUniqueWords() {
		return this.uniqueWords;
	}

	public boolean isPositive() {
		return getCommentCategory() == ClassType.ClassTypePositive;
	}

	public boolean isNegative() {
		return getCommentCategory() == ClassType.ClassTypeNegative;
	}

	public static String className(ClassType type) {
		String result = unknownClass;
		switch (type) {
		case ClassTypeNegative:
			result = negativeClass;
			break;
		case ClassTypePositive:
			result = positiveClass;
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public String toString() {
		return "Comment [commentCategoryText=" + commentCategoryText + ", commentText=" + commentText
				+ ", classifiedType=" + classifiedType + "]";
	}

	// private methods
	private void formUniqueWordsArray() {
		ArrayList<String> textArray = formArrayFromComment();
		ArrayList<String> addedStrings = new ArrayList<>();
		ArrayList<Word> uniqueWords = new ArrayList<Word>();
		for (String string : textArray) {
			if (addedStrings.contains(string)) {
				Word word = uniqueWords.get(addedStrings.indexOf(string));
				word.increaseOccurence(this.getCommentCategory());
			} else {
				Word word = new Word(string, this.getCommentCategory());
				uniqueWords.add(word);
				addedStrings.add(string);
			}
		}

		setUniqueWords(uniqueWords);
	}

	private ArrayList<String> formArrayFromComment() {
		// match only bg words with more than 2 letters
		Pattern pattern = Pattern.compile("[A-Za-z]{2,}");

		if (getLanguage() == BG) {
			pattern = Pattern.compile("[А-Яа-я]{2,}");
		}		

		Matcher matcher = pattern.matcher(this.commentText);

		ArrayList<String> textArray = new ArrayList<>();
		while (matcher.find()) {
			String match = matcher.group();
			if(getLanguage() == EN){
				if (!DataManager.getEnStopWords().contains(match)) {
					textArray.add(match);
//					System.out.println("Word: " + match);
				}
			} else {
				textArray.add(match);
			}
		}
		wordCount = textArray.size();
		// HashSet<String> uniqueValues = new HashSet<>(textArray);
		// textArray = new ArrayList<>(uniqueValues);

		return textArray;
	}

}
