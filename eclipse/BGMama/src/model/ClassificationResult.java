package model;

import model.Comment.ClassType;

public class ClassificationResult {
	private ClassType classType;
	private double finalProbability;
	private double positiveProbability;
	private double negativeProbability;
	
	private ClassificationResultModel model;
	
	public enum ClassificationResultModel {
		ClassificationResultModelBG, ClassificationResultModelEN
	}
	
	// ClassificationFinalDecisionMixResults get the final decisions from classificator A and B and choose the classificator with max probability
	// ClassificationFinalDecisionSumBothProbabilities get the probabilities from both classificators and sums + and - for both, then get max probability
	public enum ClassificationFinalDecision {
		ClassificationFinalDecisionMixResults, ClassificationFinalDecisionSumBothProbabilities
	}

	public ClassificationResult(ClassType classType, double finalProbability) {
		super();
		this.classType = classType;
		this.finalProbability = finalProbability;
	}
	
	public ClassificationResult(ClassType classType, double finalProbability, double positiveProbability,
			double negativeProbability) {
		super();
		this.classType = classType;
		this.finalProbability = finalProbability;
		this.positiveProbability = positiveProbability;
		this.negativeProbability = negativeProbability;
	}

	public ClassType getClassType() {
		return classType;
	}

	public double getFinalProbability() {
		return finalProbability;
	}

	public double getPositiveProbability() {
		return positiveProbability;
	}

	public void setPositiveProbability(double positiveProbability) {
		this.positiveProbability = positiveProbability;
	}

	public double getNegativeProbability() {
		return negativeProbability;
	}

	public void setNegativeProbability(double negativeProbability) {
		this.negativeProbability = negativeProbability;
	}

	public ClassificationResultModel getModel() {
		return model;
	}

	public void setModel(ClassificationResultModel model) {
		this.model = model;
	}

	public ClassificationResult mostProbableResult(ClassificationResult other, ClassificationFinalDecision finalDecision){
		switch (finalDecision) {
		case ClassificationFinalDecisionMixResults:
			return mixResults(other);
		
		case ClassificationFinalDecisionSumBothProbabilities:
	        return combineResults(other);

		default:
			return null;
		}
	}
	
	private ClassificationResult mixResults(ClassificationResult other){
		if(this.getFinalProbability() > other.getFinalProbability()) {
			return this;
		} else {
			return other;
		}
	}
	
	private ClassificationResult combineResults(ClassificationResult other){
		
		double positiveProbability = this.getPositiveProbability() + other.getPositiveProbability();
		double negativeProbability = this.getNegativeProbability() + other.getNegativeProbability();
		
		double finalProbability = positiveProbability;
		
		if (negativeProbability > positiveProbability) {
			finalProbability = negativeProbability;
		}
		
		return new ClassificationResult(getClassType(), finalProbability);
	}
	
	public String getClassificatorModelString(ClassificationResultModel model) {
		if (model == null) return "combined";
		switch(model) {
		case ClassificationResultModelBG:
			return "ClassificationModelBG";
		case ClassificationResultModelEN:
			return "ClassificationModelEN";
			default:
				return "";
		}
	}
}
