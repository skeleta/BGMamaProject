package model;

public class Hotel {
	
	private static final String mapsBaseURL = "www.google.bg/maps/search/";
	
	private String name = "";
	private String url = "";
	
	
	public Hotel(String name) {
		super();
		setName(name);		
		setUrl(formURL(name));
	}
	
	private String formURL(String name){
		String newName = name.replaceAll(" ", "");
		String url = mapsBaseURL + newName;
		return url;
	}

	private void setUrl(String url) {
		this.url = url;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getName() {
		return name;
	}
	
	
	

}
