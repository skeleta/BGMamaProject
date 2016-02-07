package model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import gate.DataStore;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.corpora.DocumentStaxUtils;
import gate.util.GateException;

public class LoadDocumet {
	public void addNewDoc() throws GateException, XMLStreamException, IOException {
	Gate.init();
	DataStore ds = Factory.openDataStore("gate.creole.annic.SearchableDataStore", "file:/path/to/datastore");
	List docIds = ds.getLrIds("gate.corpora.DocumentImpl");
	for(Object id : docIds) {
	  Document d = (Document) Factory.createResource("gate.corpora.DocumentImpl",
	            gate.Utils.featureMap(DataStore.DATASTORE_FEATURE_NAME, ds,
	                                  DataStore.LR_ID_FEATURE_NAME, id));
	  try {
	    File outputFile = new File(""); // based on doc name, sequential number, etc.
	    DocumentStaxUtils.writeDocument(d, outputFile);
	  } finally {
	    Factory.deleteResource(d);
	  }
	}
	}
}
