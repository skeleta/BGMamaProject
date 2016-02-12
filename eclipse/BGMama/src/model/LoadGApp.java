package model;

import java.io.File;
import java.util.Iterator;

import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.util.FeatureBearer;
import gate.util.persistence.PersistenceManager;

public class LoadGApp {
	private static final String BGMAMMAPROJECT_PATH = "D:/FMI_Projects/BGMamaProject";
	private static final String GAPP_PATH = BGMAMMAPROJECT_PATH + "/resources/app/LocationSearch.gapp";
	private static final String DOC_PATH = BGMAMMAPROJECT_PATH + "/resources/documents/UnknownData_Small.xml";
	private static final String CORPUS_NAME = "HotelsFinder Corpus";
	private static final String UTF_8 = "utf-8";
	
	public static AnnotationSet executeGApp() {
		try {
			Gate.init();
			CorpusController application;
			application = (CorpusController) PersistenceManager.loadObjectFromFile(new File(GAPP_PATH));
			Corpus corpus =  Factory.newCorpus(CORPUS_NAME);
			File docPath = new File(DOC_PATH);
	        Document doc = Factory.newDocument(docPath.toURI().toURL(), UTF_8);
			Document docAnnotaded = runGApp(doc, corpus, application);
			//print matched hottels
			//docAnnotaded.getAnnotations().get("Hotel_Name").iterator().next().getFeatures().get("matchStr")
			AnnotationSet hotelsAnn = doc.getAnnotations().get("Hotel_Name"); 
            Iterator hotelsAnnIt = hotelsAnn.iterator(); 
            while (hotelsAnnIt.hasNext()) {
            	System.out.println(((FeatureBearer) hotelsAnnIt.next()).getFeatures().get("matchStr").toString());
            } 
			return docAnnotaded.getAnnotations();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
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
