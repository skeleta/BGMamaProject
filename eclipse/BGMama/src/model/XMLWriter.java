package model;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter {
	private static Document openDocument;
	private static Element rootElement;
	private static Transformer transformer;
	private static DOMSource source;
	private static StreamResult result;

	public static void createFile(String name, String rootElementName) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;

		try {
			docBuilder = docFactory.newDocumentBuilder();
			// root elements
			openDocument = docBuilder.newDocument();

			rootElement = openDocument.createElement(rootElementName);
			openDocument.appendChild(rootElement);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			source = new DOMSource(openDocument);
			result = new StreamResult(new File("src/Supporting Files/" + name +".xml"));

		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void writeObjectInfoFile(MessageObject obj) {
		try {
			Element comment = openDocument.createElement("comment");
			rootElement.appendChild(comment);

			comment.setAttribute("id", obj.msgcontent.msg.idmsg);
			comment.setAttribute("category", "unknown");
			comment.appendChild(openDocument.createTextNode(obj.msgcontent.msg.getMsgbody()));
			
			Element subject = openDocument.createElement("topic");
			subject.appendChild(openDocument.createTextNode(obj.msgcontent.msg.getMsgsubject()));
			comment.appendChild(subject);
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeObjectInfoFile(ENReviews obj) {
		try {
			Element comment = openDocument.createElement("comment");
			rootElement.appendChild(comment);
			
			comment.setAttribute("id", obj.ReviewID);
			comment.setAttribute("category", Comment.className(obj.Ratings.ratingType()));
			comment.appendChild(openDocument.createTextNode(obj.Content));
			
			Element subject = openDocument.createElement("topic");
			subject.appendChild(openDocument.createTextNode("none"));
			comment.appendChild(subject);
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closeFile(){

		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
