package main.items;

public class RecentFile {
	
	protected String projectName;
	protected String date;
	protected Integer numberOfTexts;
	protected String file;
	
	
	
	public RecentFile() {
		super();
	}
	public RecentFile(String projectName, String date, int numberOfTexts, String file) {
		super();
		this.projectName = projectName;
		this.date = date;
		this.numberOfTexts = numberOfTexts;
		this.file = file;
	}
	
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public Integer getNumberOfTexts() {
		return numberOfTexts;
	}
	public void setNumberOfTexts(Integer numberOfTexts) {
		this.numberOfTexts = numberOfTexts;
	}

}
