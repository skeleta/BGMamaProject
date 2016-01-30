package main;

import model.XMLParser;
import model.XMLParser.DataType;

public class MainClass {

	public static void main(String[] args) {
		String filePath = new String("src/Supporting Files/TrainingData.txt");
		XMLParser.parseFile(filePath, DataType.DataTypeTraining);
		XMLParser.printData(XMLParser.getTrainingData());
	}

}
