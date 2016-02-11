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

	@Override
	public String toString() {
		return "Word [term=" + term + ", positiveProbability=" + positiveProbability + ", negativeProbablity="
				+ negativeProbablity + ", negativeOccurence=" + negativeOccurence + ", positiveOccurence="
				+ positiveOccurence + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;		
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}

}
