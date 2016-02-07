package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


public class JSONReader {	
	public static void readStream() {
	    try {
	    	File file = new File("D:/Information_Extraction/Dictionaries/BGMamma Data/fmi.json");
	    	JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
	        Gson gson = new GsonBuilder().create();

	        // Read file in stream mode
	        reader.beginArray();
	        System.out.println("Searching...");
	        XMLWriter.createFile();
	        while (reader.hasNext()) {
	            // Read data into object model
	        	MessageObject msg =  gson.fromJson(reader, MessageObject.class);       	
	        	
	        	String topic = msg.msgcontent.msg.idtopic;
	        	String topicName = msg.msgcontent.msg.msgsubject;
	            if (topicName.toLowerCase().contains("хотел")) {
	                XMLWriter.writeObjectInfoFile(msg);
	            }            
	        }
	        System.out.println("Reader was closed.");
	        XMLWriter.closeFile();
	        reader.close();
	    } catch (UnsupportedEncodingException ex) {
//	        ...
	    } catch (IOException ex) {
//	        ...
	    }
	}
}

