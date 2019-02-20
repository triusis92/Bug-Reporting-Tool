package application;

import java.time.LocalDate;
/**
 * Write a description of class Category here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Projects implements java.io.Serializable
{	private LocalDate date;
    private User projectManager;
    private String projectName;
    private String managerUsername;
    private int projectId;
    
    
    public Projects()
    {
        
    }
    
    public Projects(LocalDate d, User u, String pn, String mu,int pId)
    {
    	setDate(d);   	
    	setProjectName(pn);
    	setManagerUsername(mu);
    	setProjectId(pId);
    }
    
    public void setDate(LocalDate d)
    {
        date=d;
    }
    
    public LocalDate getDate()
    {
        return date;
    }
    
    public void setUser(User u)
    {
    	projectManager=u;;
    }
    
    public User getUser()
    {
        return projectManager;
    }

	public String getProjectName() 
	{
		return projectName;
	}

	public void setProjectName(String projectName) 
	{
		this.projectName = projectName;
	}

	public String getManagerUsername() 
	{
		return managerUsername;
	}

	public void setManagerUsername(String managerUsername) 
	{
		this.managerUsername = managerUsername;
	}

	public int getProjectId() 
	{
		return projectId;
	}

	public void setProjectId(int projectId) 
	{
		this.projectId = projectId;
	}
	
	public String toString()
	{
		return "Project Name: "+getProjectName()+" \nManager Username: "+getManagerUsername()+" \nProject ID: "+getProjectId()+" \nDate: "+getDate() ;
	}

	public void print()
	{
		System.out.println(toString());
	}

}
