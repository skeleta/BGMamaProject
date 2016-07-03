package model;

import model.Comment.ClassType;

public class ClassificationResult {
	private ClassType classType;
	private double probability;
	private ClassificationResultModel model;
	
	public enum ClassificationResultModel {
		ClassificationResultModelBG, ClassificationResultModelEN
	}

	public ClassificationResult(ClassType classType, double probability) {
		super();
		this.classType = classType;
		this.probability = probability;
	}

	public ClassType getClassType() {
		return classType;
	}

	public double getProbability() {
		return probability;
	}

	public ClassificationResultModel getModel() {
		return model;
	}

	public void setModel(ClassificationResultModel model) {
		this.model = model;
	}

	public ClassificationResult mostProbableResult(ClassificationResult other){
		if(this.probability > other.probability) {
			return this;
		} else {
			return other;
		}
	}
	
	public String getClassificatorModelString(ClassificationResultModel model) {
		switch(model) {
		case ClassificationResultModelBG:
			return "ClassificationModelBG";
		case ClassificationResultModelEN:
			return "ClassificationModelEN";
			default:
				return null;
		}
	}

}
