package model;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

public class LoadGApp {
	
	public static void loadGApp() {
		
		try {
			Gate.init();
			CorpusController application;
			File gapp = new File("D:/FMI_Projects/BGMamaProject/resources/app/LocationSearch.gapp");
			application = (CorpusController) PersistenceManager.loadObjectFromFile(gapp);
			Corpus corpus =  application.getCorpus();
			Document doc = executeGApp(corpus, application);
			AnnotationSet annotations = doc.getAnnotations();
			
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public static Document executeGApp(Corpus corpus, CorpusController application) throws Exception {
		File docPath = new File("D:/FMI_Projects/BGMamaProject/resources/documents/UnknownData_Small.xml");
        Document doc = Factory.newDocument(docPath.toURI().toURL(), "utf-8");
        
        // remove the document from the corpus
        corpus.clear();
        
        // put the document in the corpus 
        corpus.add(doc); 

        // run the application 
        application.execute(); 

        // remove the document from the corpus again 
        corpus.clear();
        return doc;
	}
}
