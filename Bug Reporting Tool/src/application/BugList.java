package application;
import java.util.ArrayList;
import java.util.Date;
public class BugList implements java.io.Serializable
{
    ArrayList<BugReport> list = new ArrayList<BugReport>();
    public BugList()
    {
        list = new ArrayList<BugReport>();
    }

    public boolean add(BugReport b)
    {
        return (list.add(b));
    }
    public BugReport getIndex(int index)
    {
    	return list.get(index);
    }
    public void archive(BugReport b)
    {
       
        	//copy the report to archive list/table
            list.remove(b);//then remove from current list
        
    }
    public BugReport getIndexOfReport(int index)
	{
		return list.get(index);
	}
    
    public ArrayList<BugReport> searchById(String ID)
    {
    	ArrayList<BugReport> bugsById = new ArrayList<>();
        for(int i=0; i<list.size(); i++)//for loop to go through every object
        {
            if(list.get(i).getProjectId().equals(ID)) 
            {
                bugsById.add(list.get(i));//assign the object found to place variable
            }

        }
        return bugsById;
    }
    public ArrayList<BugReport> searchBySeverity(String severity)
    {
    	ArrayList<BugReport> bugsBySeverity = new ArrayList<>();
        for(int i=0; i<list.size(); i++)//for loop to go through every object
        {
            if(list.get(i).getSeverity().equals(severity)) 
            {
            	bugsBySeverity.add(list.get(i));//assign the object found to place variable
            }

        }
        return bugsBySeverity;
    }
    public ArrayList<BugReport> searchByUsername(String user)
    {
    	ArrayList<BugReport> bugsByUsername = new ArrayList<>();
        for(int i=0; i<list.size(); i++)//for loop to go through every object
        {
            if(list.get(i).getUsername().equals(user)) 
            {
            	bugsByUsername.add(list.get(i));//assign the object found to place variable
            }

        }
        return bugsByUsername;
    }
    public ArrayList<BugReport> searchByPCS(String projectID,String creator,String severity)
    {
    	ArrayList<BugReport> bugsByPCS = new ArrayList<>();
        for(int i=0; i<list.size(); i++)//for loop to go through every object
        {
            if(list.get(i).getProjectId().equals(projectID) && list.get(i).getUsername().equals(creator) && list.get(i).getSeverity().equals(severity)) 
            {
            	bugsByPCS.add(list.get(i));//assign the object found to place variable
            }

        }
        return bugsByPCS;
    }
    public ArrayList<BugReport> searchByPC(String projectID,String creator)
    {
    	ArrayList<BugReport> bugsByPandC = new ArrayList<>();
        for(int i=0; i<list.size(); i++)//for loop to go through every object
        {
            if(list.get(i).getProjectId().equals(projectID) && list.get(i).getUsername().equals(creator)) 
            {
            	bugsByPandC.add(list.get(i));//assign the object found to place variable
            }

        }
        return bugsByPandC;
    }
    public ArrayList<BugReport> searchByPS(String projectID,String severity)
    {
    	ArrayList<BugReport> bugsByPandS = new ArrayList<>();
        for(int i=0; i<list.size(); i++)//for loop to go through every object
        {
            if(list.get(i).getProjectId().equals(projectID) && list.get(i).getSeverity().equals(severity)) 
            {
            	bugsByPandS.add(list.get(i));//assign the object found to place variable
            }

        }
        return bugsByPandS;
    }
    public ArrayList<BugReport> searchByCS(String creator,String severity)
    {
    	ArrayList<BugReport> bugsByCandS = new ArrayList<>();
        for(int i=0; i<list.size(); i++)//for loop to go through every object
        {
            if(list.get(i).getUsername().equals(creator) && list.get(i).getSeverity().equals(severity)) 
            {
            	bugsByCandS.add(list.get(i));//assign the object found to place variable
            }

        }
        return bugsByCandS;
    }
    public void printList()
    {
            System.out.println("\n"+list);
    }
}