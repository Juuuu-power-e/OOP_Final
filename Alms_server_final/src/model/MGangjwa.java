package model;

public class MGangjwa extends MModel {
	private String id;
	private String name;
	private String lecturer;
	private String credit;
	private String time;
	
	public MGangjwa() {
	}

	public String getId() {	return id;}
	public void setId(String id) {this.id = id;	}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getLecturer() {return lecturer;}
	public void setLecturer(String lecturer) {this.lecturer = lecturer;}
	
	public String getCredit() {	return credit;}
	public void setCredit(String credit) {	this.credit = credit;}
	
	public String getTime() {return time;}
	public void setTime(String time) {this.time = time;}
}
