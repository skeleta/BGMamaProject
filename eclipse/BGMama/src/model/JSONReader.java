package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import model.Comment.ClassType;

public class JSONReader {
	
	public static void readEnStream() {
		try {
			File folder = new File("E:/Projects/TripAdvisorJson");
			File[] listOfFiles = folder.listFiles();

			int br = 0;
			
			XMLWriter.createFile("en_train_data_" + br, "training_set");
			for (int i = 0; i <= 30; i++) {
				File file = listOfFiles[i];
				System.out.println(file);
				JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				Gson gson = new GsonBuilder().create();

				// Read file in stream mode
				System.out.println("Searching..." + file.getAbsolutePath());
				reader.beginArray();
				while (reader.hasNext()) {

					// Read data into object model

					ENMessageObject msg = gson.fromJson(reader, ENMessageObject.class);
					ArrayList<ENReviews> reviews = msg.Reviews;
					for (ENReviews review : reviews) {
						if (br % 10000 == 0) {
							XMLWriter.closeFile();
							XMLWriter.createFile("en_train_data_" + br, "training_set");
							System.out.println(br);
						}
						br++;
						XMLWriter.writeObjectInfoFile(review);
					}
				}
				System.out.println("Reader was closed.");
				XMLWriter.closeFile();
				reader.close();
			}

		} catch (UnsupportedEncodingException ex) {
			XMLWriter.closeFile();
			System.out.println("Exception" + ex);
		} catch (IOException ex) {
			XMLWriter.closeFile();
			System.out.println("Exception" + ex);
		}
	}

	public static void readStream() {
		try {
			File file = new File("D:/Information_Extraction/Dictionaries/BGMamma Data/fmi.json");
			JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			Gson gson = new GsonBuilder().create();

			// Read file in stream mode
			reader.beginArray();
			System.out.println("Searching...");
			int br = 0;
			XMLWriter.createFile("unknown_data" + br, "unknown_data");
			while (reader.hasNext()) {
				// Read data into object model
				MessageObject msg = gson.fromJson(reader, MessageObject.class);

				String topic = msg.msgcontent.msg.idtopic;
				String topicName = msg.msgcontent.msg.msgsubject;
				if (topicName.toLowerCase().contains("хотел")) {
					if (br % 100 == 0) {
						XMLWriter.closeFile();
						XMLWriter.createFile("en_unknown_data_" + br, "unknown_data");
						System.out.println(br);
					}
					br++;
					XMLWriter.writeObjectInfoFile(msg);
				}
			}
			System.out.println("Reader was closed.");
			XMLWriter.closeFile();
			reader.close();
		} catch (UnsupportedEncodingException ex) {
			XMLWriter.closeFile();
			System.out.println("Exception" + ex);
		} catch (IOException ex) {
			XMLWriter.closeFile();
			System.out.println("Exception" + ex);
		}
	}
}
