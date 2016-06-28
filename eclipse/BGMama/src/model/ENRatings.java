package model;

import model.Comment.ClassType;

public class ENRatings {
	String Overall;
	
	public ClassType ratingType(){
		float overallInt = Float.parseFloat(Overall);
		if (overallInt >= 3f ){
			return ClassType.ClassTypePositive;
		} else {
			return ClassType.ClassTypeNegative;
		}
	}
}
