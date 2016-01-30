package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLParser {

	private static final String kDataIdAttribute = "id";
	private static final String kDataCategoryAttribute = "category";
	private static final String kDataCommentText = "text";
	private static final String kDataCommentNode = "comment";

	public enum DataType {
		DataTypeTraining,
		DataTypeTest,
		DataTypeUnknown
	}

	private static ArrayList<HashMap<String,String>> trainingData = new ArrayList<>();
	private static ArrayList<HashMap<String,String>> testData = new ArrayList<>();
	private static ArrayList<HashMap<String,String>> unknownData = new ArrayList<>();

	public static void parseFile(String filePath, DataType type) {
		Document parsedDocument = openFile(filePath);
		if (parsedDocument!= null){
			saveData(parseToArraylist(parsedDocument), type);
		}

	}



	public static ArrayList<HashMap<String, String>> getTrainingData() {
		return trainingData;
	}



	public static void setTrainingData(ArrayList<HashMap<String, String>> trainingData) {
		XMLParser.trainingData = trainingData;
	}



	public static ArrayList<HashMap<String, String>> getTestData() {
		return testData;
	}



	public static void setTestData(ArrayList<HashMap<String, String>> testData) {
		XMLParser.testData = testData;
	}



	public static ArrayList<HashMap<String, String>> getUnknownData() {
		return unknownData;
	}



	public static void setUnknownData(ArrayList<HashMap<String, String>> unknownData) {
		XMLParser.unknownData = unknownData;
	}


	public static void printData(ArrayList<HashMap<String,String>> data) {
		System.out.println(data.toString());
	}

	private static Document openFile(String filePath) {		
		Document parsedDocument = null;
		try {
			File xmlFile = new File(filePath);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBulder = documentBuilderFactory.newDocumentBuilder();
			parsedDocument = documentBulder.parse(xmlFile);

			//http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			parsedDocument.getDocumentElement().normalize();
			return parsedDocument;

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}


	}

	private static ArrayList<HashMap<String,String>> parseToArraylist(Document parsedDocument) {
		NodeList nodeList = parsedDocument.getElementsByTagName(kDataCommentNode);
		ArrayList<HashMap<String, String>> array = new ArrayList<>();
		for (int temp = 0; temp < nodeList.getLength(); temp++) {
			Node node = (Node) nodeList.item(temp);
			HashMap nodeHashMap = hashMapForNode(node);
			if (nodeHashMap.size() > 0) {
				array.add(nodeHashMap);
			}			
		}

		return array;

	}

	private static HashMap<String, String> hashMapForNode(Node node) {	
		HashMap<String, String> map = new HashMap<>();
		if (node.getNodeType() == Node.ELEMENT_NODE) {			
			Element element = (Element) node;
			// if the text content is empty we do not need to parse the other data
			String commentText = element.getTextContent();
			if (commentText != null ) {
				map.put(kDataCommentText, commentText);

				String commentId = element.getAttribute(kDataIdAttribute);
				if(commentId != null) {
					map.put(kDataIdAttribute, commentId);
				}

				String commentCategory =  element.getAttribute(kDataCategoryAttribute);
				if (commentCategory != null) {
					map.put(kDataCategoryAttribute, commentCategory);
				}
			}	
		}

		return map;
	}

	private static void saveData(ArrayList<HashMap<String, String>> data, DataType type) {
		switch (type) {
		case DataTypeTraining:
			setTrainingData(data);
			break;
		case DataTypeTest:
			setTestData(data);
			break;
		case DataTypeUnknown:
			setUnknownData(data);
			break;
		default:
			break;
		}
	}
}
