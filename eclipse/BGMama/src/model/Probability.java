package model;

class Probability {
	private double positiveProbability;
	private double negativeProbability;
	
	public Probability(double positiveProbability, double negativeProbability) {
		super();
		this.positiveProbability = positiveProbability;
		this.negativeProbability = negativeProbability;
	}
	
	public double getPositiveProbability() {
		return positiveProbability;
	}
	public double getNegativeProbability() {
		return negativeProbability;
	}	
}
