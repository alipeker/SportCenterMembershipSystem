package model;

import java.sql.Date;

public class Lesson {
	private String id;
	private String coursename;
	private String branchname;
	private String branchoffice;
	private String trainername;
	private Date startdate;
	private Date enddate;
	private String dates;
	
	public Lesson(String id,String coursename,String branchname,String branchoffice,String trainername,Date startdate,Date enddate,String dates){
		this.setId(id);
		this.coursename=coursename;
		this.branchname=branchname;
		this.branchoffice=branchoffice;
		this.trainername=trainername;
		this.startdate=startdate;
		this.enddate=enddate;
		this.dates=dates;
	}
	
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public String getBranchoffice() {
		return branchoffice;
	}
	public void setBranchoffice(String branchoffice) {
		this.branchoffice = branchoffice;
	}
	public String getTrainername() {
		return trainername;
	}
	public void setTrainername(String trainername) {
		this.trainername = trainername;
	}
	
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
