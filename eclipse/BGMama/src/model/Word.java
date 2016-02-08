package model;

import model.Comment.ClassType;

public class Word {
	
	private String term;
	private double positiveProbability;
	private double negativeProbablity;	
	
	private int negativeOccurence = 0;
	private int positiveOccurence = 0;
	
	public Word(String term) {
		super();
		setTerm(term);
	}	
	
	public Word(String term, ClassType type) {
		super();
		setTerm(term);
		increaseOccurence(type);
	}	
	
	public int getNegativeOccurence() {
		return negativeOccurence;
	}

	public int getPositiveOccurence() {
		return positiveOccurence;
	}	

	public void setPositiveProbability(double positiveProbability) {
		this.positiveProbability = positiveProbability;
	}

	public void setNegativeProbablity(double negativeProbablity) {
		this.negativeProbablity = negativeProbablity;
	}

	public void increaseOccurence(ClassType type) {
		if (type == ClassType.ClassTypePositive) {
			this.positiveOccurence++;
		}
		
		if (type == ClassType.ClassTypeNegative) {
			this.negativeOccurence++;
		}
	}
	
	private void setTerm(String term) {
		this.term = term.toLowerCase();
	}

	public String getTerm() {
		return term;
	}
	public double getPositiveProbability() {
		return positiveProbability;
	}
	public double getNegativeProbablity() {
		return negativeProbablity;
	}
	
	

}
