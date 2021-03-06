package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.util.FeatureBearer;
import gate.util.persistence.PersistenceManager;

public class LoadGApp {
	private static final String BGMAMMAPROJECT_PATH = "D:/FMI_Projects/BGMamaProject";
	private static final String GAPP_PATH = BGMAMMAPROJECT_PATH + "/resources/app/LocationSearch.gapp";
	private static final String DOC_PATH = BGMAMMAPROJECT_PATH + "/resources/documents/unknown_data_3100.xml";
	private static final String TEST_DOC_PATH = BGMAMMAPROJECT_PATH + "/resources/documents/test_f1.xml";
	private static final String CORPUS_NAME = "HotelsFinder Corpus";
	private static final String UTF_8 = "utf-8";
	
	public static ArrayList<Hotel> executeGApp() {
		AnnotationSet annotations = makeAnnotationSet(DOC_PATH);
		return formHotelsList(annotations);	
	}
	
	public static ArrayList<Hotel> executeGAppWithTestComment() {
		AnnotationSet annotations = makeAnnotationSet(TEST_DOC_PATH);
		return formHotelsList(annotations);	
	}
	
	private static AnnotationSet makeAnnotationSet(String path) {
		try {
			Gate.init();
			CorpusController application;
			application = (CorpusController) PersistenceManager.loadObjectFromFile(new File(GAPP_PATH));
			Corpus corpus =  Factory.newCorpus(CORPUS_NAME);
			File docPath = new File(path);
	        Document doc = Factory.newDocument(docPath.toURI().toURL(), UTF_8);
			Document docAnnotaded = runGApp(doc, corpus, application);
			return docAnnotaded.getAnnotations();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static ArrayList<Hotel> formHotelsList(AnnotationSet docAnnotaded){
		// print matched hottels
		// docAnnotaded.getAnnotations().get("Hotel_Name").iterator().next().getFeatures().get("matchStr")
		ArrayList<Hotel> hotels = new ArrayList<>();
		AnnotationSet hotelsAnn = docAnnotaded.get("Hotel_Name"); 
        Iterator hotelsAnnIt = hotelsAnn.iterator(); 
        while (hotelsAnnIt.hasNext()) {
        	FeatureMap featureMap = ((FeatureBearer) hotelsAnnIt.next()).getFeatures();
        	String hotelName = featureMap.get("matchStr").toString();
        	hotels.add(new Hotel(hotelName));
        	System.out.println(featureMap);
        }
        System.out.println("Total matched: " + hotelsAnn.size());
        return hotels;
	}
	private static Document runGApp(Document doc, Corpus corpus, CorpusController application) throws Exception {
        corpus.clear();
        corpus.add(doc);
        application.setCorpus(corpus);
        application.execute(); 
        corpus.clear();
        return doc;
	}
}
