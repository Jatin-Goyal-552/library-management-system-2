
package LMS;
import java.util.*;
import java.lang.Math; 
public abstract class Person 
{   
    protected int id;           
    protected String password; 
    protected String name; 
    protected String room;
    protected int phoneNo;     
    
    static int currentIdNumber = 0;     
                                       
    public Person(int dd, String n, String a, int p)   // para cons.
    {
        currentIdNumber++;
        
        if(dd==-1)
        {
            id = currentIdNumber;
        }
        else
            id = dd;
        
        password =Integer.toString(id);
        name = n;
        room=a;
        phoneNo = p;
    }        
    
   
    public void setRoom(String a)
    {
        room = a;
    }
    
    public void setPhone(int p)
    {
        phoneNo = p;
    }
    
    public void setName(String n)
    {
        name = n;
    }
    /*----------------------------*/
    
    /*-------Getter FUNCs.--------*/
    public String getName()
    {
        return name;
    }
    
    public String getPassword()
    {
        return password;
    }
    
     public String getRoom()
    {
        return room;
    }
     
     public int getPhoneNumber()
    {
        return phoneNo;
    }
    public int getID()
    {
        return id;
    }
    /*---------------------------*/
    
     public static void setIDCount(int n)
    {
        currentIdNumber=n;
    }
   
} // Person Class Closed
