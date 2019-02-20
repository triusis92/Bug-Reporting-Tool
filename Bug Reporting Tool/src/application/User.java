package application;

/**
 * Write a description of class Person here.
 * 
 * @author (Laisvydas vavilovas) 
 * @version (a version number or a date)
 */
public class User implements java.io.Serializable
{
	private String name;
	private String username;
	private String password;
	private String userType;
	private int projectId;
	private int accessLevel;

	

	public User(String n,String u,String p,String ut)
	{
		setName(n);
		setUsername(u);
		setPassword(p);
		setUserType(ut);
		if(ut.equals("Tester"))
		{
			setAccessLevel(1);
		}
		else if(ut.equals("Developer"))
		{
			setAccessLevel(2);
		}
		else if(ut.equals("Project Manager"))
		{
			setAccessLevel(3);
		}
		else if(ut.equals("Super User"))
		{
			setAccessLevel(4);
		}

	}

	public void setName(String n)
	{
		name=n;
	}
	public String getName()
	{
		return name;
	}

	public void setUsername(String u)
	{
		username=u;
	}
	public String getUsername()
	{
		return username;
	}

	public void setPassword(String p)
	{
		password=p;
	}
	public String getPassword()
	{
		return password;
	}

	public void setUserType(String ut)
	{
		userType=ut;
	}


	public String getUserType()
	{
		return userType;
	}
	public int getProjectID() 
	{
		return projectId;
	}

	public void setProjectID(int pId) 
	{
		projectId = pId;
	}
	public int getAccessLevel() 
	{
		return accessLevel;
	}

	public void setAccessLevel(int al) 
	{
		accessLevel = al;
	}

	public String toString()
	{
		return "Name: "+getName()+" Address: "+getUsername()+" Phone: "+getPassword()+" E-mail: "+getUserType()+"Access Level(1,2 or3): "+getAccessLevel() ;
	}

	public void print()
	{
		System.out.println(toString());
	}




}
