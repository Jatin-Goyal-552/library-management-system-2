
package LMS;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;

public class Loan 
{
    private Borrower borrower;      
    private Book book;
    
    private Staff issuer;
    private Date issuedDate;
    
    private Date dateReturned;
    private Staff receiver;
    
    
   
    
    
    public Book getBook()       
    {
        return book;
    }
    
    public Staff getIssuer()   
    {
        return issuer;
    }
    
    public Staff getReceiver()  
    {
        return receiver;
    }
    
    public Date getIssuedDate()     
    {
        return issuedDate;
    } 

    public Date getReturnDate()    
    {
        return dateReturned;
    }
    
    public Borrower getBorrower()  
    {
        return borrower;
    }
    
   
    
    public void setReturnedDate(Date dReturned)
    {
        dateReturned = dReturned;
    }
    
    
    
    public void setReceiver(Staff r)
    {
        receiver = r;
    }
    
    
}   
