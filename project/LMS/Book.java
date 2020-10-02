
package LMS;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Book {
   
    private int bookID;          
    private String title;        
    private String author;        
    private boolean isIssued;       
    private ArrayList<HoldRequest> holdRequests; 
 
    static int currentIdNumber = 0;    
    
  
    public Book(int id,String t, String a, boolean issued)    
    {
        currentIdNumber++;
        if(id==-1)
        {
            bookID = currentIdNumber;
        }
        else
            bookID=id;
        
        title = t;
        
        author = a;
        isIssued = issued;
        
        holdRequests = new ArrayList();
    }
    
    
    public void addHoldRequest(HoldRequest hr)
    {
        holdRequests.add(hr);
    }
    
    
    public void removeHoldRequest()
    {
        if(!holdRequests.isEmpty())
        {
            holdRequests.remove(0);
        }
    }
    
    
    public void printHoldRequests()
    {
        if (!holdRequests.isEmpty())
        { 
            System.out.println("\nHold Requests are: ");
            
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");            
            System.out.println("No.\t\tBook's Title\t\t\tBorrower's Name\t\t\tRequest Date");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
            
            for (int i = 0; i < holdRequests.size(); i++)
            {                      
                System.out.print(i + "-" + "\t\t");
                holdRequests.get(i).print();
            }
        }
        else
            System.out.println("\nNo Hold Requests.");                                
    }
    
   
    public void printInfo()
    {
        System.out.println(title + "\t\t\t" + author + "\t\t\t" );
    }
    
   
    
    
    public String getTitle()
    {
        return title;
    }

    
    public String getAuthor()
    {
        return author;
    }
    
    public boolean getIssuedStatus()
    {
        return isIssued;
    }
    
    public void setIssuedStatus(boolean s)
    {
        isIssued = s;
    }
    
     public int getID()
    {
        return bookID;
    }
     
     public ArrayList<HoldRequest> getHoldRequests()
    {
        return holdRequests;
    }
    
    public static void setIDCount(int n)
    {
        currentIdNumber = n;
    }
    

    
    
 
    public void placeBookOnHold(Borrower bor)
    {
        HoldRequest hr = new HoldRequest(bor,this, new Date());
        
        addHoldRequest(hr);        
        bor.addHoldRequest(hr);      
        
        System.out.println("\nThe book " + title + " has been successfully placed on hold by borrower " + bor.getName() + ".\n");
    }
    
    


   
    public void makeHoldRequest(Borrower borrower)
    {
        boolean makeRequest = true;

       
        for(int i=0;i<borrower.getBorrowedBooks().size();i++)
        {
            if(borrower.getBorrowedBooks().get(i).getBook()==this)
            {
                System.out.println("\n" + "You have already borrowed " + title);
                return;                
            }
        }
        
        
       
        for (int i = 0; i < holdRequests.size(); i++)
        {
            if ((holdRequests.get(i).getBorrower() == borrower))
            {
                makeRequest = false;    
                break;
            }
        }

        if (makeRequest)
        {
            placeBookOnHold(borrower);
        }
        else
            System.out.println("\nYou already have one hold request for this book.\n");
    }

    
    
    public void serviceHoldRequest(HoldRequest hr)
    {
        removeHoldRequest();
        hr.getBorrower().removeHoldRequest(hr);
    }

    
        
    
    public void issueBook(Borrower borrower, Staff staff)
    {        
        
        Date today = new Date();        
        
        ArrayList<HoldRequest> hRequests = holdRequests;
        
        for (int i = 0; i < hRequests.size(); i++)
        {
            HoldRequest hr = hRequests.get(i);            
            
           
            long days =  ChronoUnit.DAYS.between(today.toInstant(), hr.getRequestDate().toInstant());        
            days = 0-days;
            
            if(days>Library.getInstance().getHoldRequestExpiry())
            {
                removeHoldRequest();
                hr.getBorrower().removeHoldRequest(hr);
            } 
        }
               
        if (isIssued)
        {
            System.out.println("\nThe book " + title + " is already issued.");
            System.out.println("Would you like to place the book on hold? (y/n)");
             
            Scanner sc = new Scanner(System.in);
            String choice = sc.next();
            
            if (choice.equals("y"))
            {                
                makeHoldRequest(borrower);
            }
        }
        
        else
        {               
            if (!holdRequests.isEmpty())
            {
                boolean hasRequest = false;
                
                for (int i = 0; i < holdRequests.size() && !hasRequest;i++)
                {
                    if (holdRequests.get(i).getBorrower() == borrower)
                        hasRequest = true;
                        
                }
                
                if (hasRequest)
                {
                    
                    if (holdRequests.get(0).getBorrower() == borrower)
                        serviceHoldRequest(holdRequests.get(0));       

                    else
                    {
                        System.out.println("\nSorry some other users have requested for this book earlier than you. So you have to wait until their hold requests are processed.");
                        return;
                    }
                }
                else
                {
                    System.out.println("\nSome users have already placed this book on request and you haven't, so the book can't be issued to you.");
                    
                    System.out.println("Would you like to place the book on hold? (y/n)");

                    Scanner sc = new Scanner(System.in);
                    String choice = sc.next();
                    
                    if (choice.equals("y"))
                    {
                        makeHoldRequest(borrower); 
                    }                    
                    
                    return;
                }               
            }
                        
                        
            setIssuedStatus(true);
            
            
            
           
            System.out.println("\nThe book " + title + " is successfully issued to " + borrower.getName() + ".");
            System.out.println("\nIssued by: " + staff.getName());            
        }
    }
        
        
   
    public void returnBook(Borrower borrower, Loan l, Staff staff)
    {
        l.getBook().setIssuedStatus(false);        
        l.setReturnedDate(new Date());
        l.setReceiver(staff);        
        
        borrower.removeBorrowedBook(l);
        
        System.out.println("\nThe book " + l.getBook().getTitle() + " is successfully returned by " + borrower.getName() + ".");
        System.out.println("\nReceived by: " + staff.getName());            
    }
    
}  