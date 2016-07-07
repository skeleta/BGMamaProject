package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	private static final String kDataIdAttribute = "id";
	private static final String kDataCategoryAttribute = "category";
	private static final String kDataCommentNode = "comment";

	public enum DataType {
		DataTypeTraining,
		DataTypeTest,
		DataTypeUnknown
	}

	public static ArrayList<Comment> parseFile(String filePath, String language) {
		Document parsedDocument = openFile(filePath);
		if (parsedDocument!= null){
			return parseToArraylist(parsedDocument, language);
		}
		return null;
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

	private static ArrayList<Comment> parseToArraylist(Document parsedDocument,String language) {
		NodeList nodeList = parsedDocument.getElementsByTagName(kDataCommentNode);
		ArrayList<Comment> array = new ArrayList<>();
		for (int temp = 0; temp < nodeList.getLength(); temp++) {
			Node node = (Node) nodeList.item(temp);
			Comment newComment = createCommentObject(node, language);
			if (newComment != null) {
				array.add(newComment);
			}			
		}

		return array;
	}

	private static Comment createCommentObject(Node node,String language) {	
		Comment newComment = null;
		if (node.getNodeType() == Node.ELEMENT_NODE) {			
			Element element = (Element) node;
			// if the text content is empty we do not need to parse the other data
			if(element.getFirstChild() != null){
			String commentText = element.removeChild(element.getFirstChild()).getTextContent();
			if (commentText != null ) {
				String commentId = element.getAttribute(kDataIdAttribute);
				String commentCategory =  element.getAttribute(kDataCategoryAttribute);
				newComment = new Comment(commentId, commentCategory, commentText, language);
				System.out.println(commentId);
			}	
			}
		}

		return newComment;
	}
}
