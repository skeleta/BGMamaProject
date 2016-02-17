package model;

public class Hotel {
	
	private static final String mapsBaseURL = "www.google.bg/maps/search/";
	
	private String name = "";
	private String url = "";
	private boolean isFound;
	
	
	public Hotel(String name) {
		super();
		setName(name);		
		setUrl(formURL(name));
		setFound(false);
	}
	
	private String formURL(String name){
		String newName = name.replaceAll(" ", "");
		String url = mapsBaseURL + newName;
		return url;
	}

	public void setFound(boolean isFound) {
		this.isFound = isFound;
	}

	private void setUrl(String url) {
		this.url = url;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	public boolean isFound() {
		return isFound;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getName() {
		return name;
	}
	
	
	

}
