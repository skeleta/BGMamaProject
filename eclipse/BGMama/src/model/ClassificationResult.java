package model;

import model.Comment.ClassType;

public class ClassificationResult {
	private ClassType classType;
	private double probability;

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

	public ClassificationResult mostProbableResult(ClassificationResult other){
		if(this.probability > other.probability) {
			return this;
		} else {
			return other;
		}
	}

}
