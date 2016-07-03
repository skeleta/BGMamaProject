package model;

import java.util.ArrayList;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class Translation {
	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String SUPPORTING_DIR = USER_DIR + "//src//Supporting Files//";
	private static final String CLIENT_ID = "BGmamaProject_v2";
	private static final String CLIENT_SECRET = "IWR5scxH5U90HcK1j/VfpEQeDt7TquobTqY7OdnuT9A=";
	public static final String BG = "bg";
	public static final String EN = "en";
	
	public Translation(){
	    //Replace client_id and client_secret with your own.  
	    Translate.setClientId(CLIENT_ID);
	    Translate.setClientSecret(CLIENT_SECRET);
	}
	
	public String translate(String from, String text){
		String translated = "";
		try {
			if (from.equals(EN)) {
				translated = Translate.execute(text, Language.ENGLISH, Language.BULGARIAN);
			} else {
				translated = Translate.execute(text, Language.BULGARIAN, Language.ENGLISH);
			}
		} catch (Exception e) {
			System.out.println("Unable to translate text due to:\n");
			e.printStackTrace();
		}
		return translated;
	}
	
	public void translateTestData(String from, String sourceFile, String destFile){
		String rootElement = "test_set";
		
		ArrayList<Comment> comments = DataManager.getTestData(SUPPORTING_DIR + sourceFile + ".xml");
		XMLWriter.createFile(destFile, rootElement);
		for(Comment comment:comments){
			Comment translated = new Comment(
						comment.getCommentId(), 
						comment.getCommentCategoryText(), 
						translate(from, comment.getCommentText())
					);
			XMLWriter.writeObjectInfoFile(translated);
		}
		XMLWriter.closeFile();
	}
}
