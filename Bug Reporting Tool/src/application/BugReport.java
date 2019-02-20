package application;

import java.time.LocalDate;
/**
 * Write a description of class Animal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */



public class BugReport implements java.io.Serializable
{
	private String severity;
	private String reportType;
	private String canReproduce;
	private String summary;
	private String reDescription;
	private String username;
	private String projectId;
	public static int bugCount=1;
	private int bugID;
	private LocalDate foundDate;

	public BugReport() {
		this.bugID=bugCount;
		bugCount++;

	}
	public BugReport(String severity, String reportType,String summary, String canReproduce,String reDescription)
	{
		this.bugID=bugCount;
		bugCount++;
		this.setSeverity(severity);
		//this.setProjectId(projectId);
		this.setReportType(reportType);
		this.setSummary(summary);
		this.setCanReproduce(canReproduce);
		this.setReDescription(reDescription);
	}

	public String getSeverity() 
	{
		return severity;
	}
	public void setSeverity(String severity) 
	{
		this.severity = severity;
	}
	public String getReportType() 
	{
		return reportType;
	}
	public void setReportType(String reportType) 
	{
		this.reportType = reportType;
	}
	public String isCanReproduce() 
	{
		return canReproduce;
	}
	public void setCanReproduce(String canReproduce) 
	{
		this.canReproduce = canReproduce;
	}
	public String getSummary() 
	{
		return summary;
	}
	public void setSummary(String summary) 
	{
		this.summary = summary;
	}
	public String getReDescription() 
	{
		return reDescription;
	}
	public void setReDescription(String reDescription) 
	{
		this.reDescription = reDescription;
	}
	public String getProjectId() 
	{
		return projectId;
	}
	public void setProjectId(String projectId) 
	{
		this.projectId = projectId;
	}
	public int getBugID() 
	{
		return bugID;
	}
	public LocalDate getFoundDate() 
	{
		return foundDate;
	}
	public void setFoundDate(LocalDate foundDate) 
	{
		this.foundDate = foundDate;
	}
	public String getUsername() 
	{
		return username;
	}
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public String toString()
	{
		return "\n Bug Report ID: "+getBugID()+"\nSeverity: "+getSeverity() +"\n Project ID: "+getProjectId() +"\n Report Type: "+getReportType() +"\n Summary: "+getSummary() +"\n Can the problem be reproduced: "+isCanReproduce() +"\n Description of reproduction: "+getReDescription() +"\n Report Date Found: "+getFoundDate()+"\n";
	}

	public void print()
	{
		System.out.println(toString());
	}

}
