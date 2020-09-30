
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
    
    
    public Loan(Borrower bor, Book b, Staff i, Staff r, Date iDate, Date rDate, boolean fPaid)  // Para cons.
    {
        borrower = bor;
        book = b;
        issuer = i;
        receiver = r;
        issuedDate = iDate;
        dateReturned = rDate;
    }
    
    /*----- Getter FUNCs.------------*/
    
    public Book getBook()       //Returns the book
    {
        return book;
    }
    
    public Staff getIssuer()     //Returns the Staff Member who issued the book
    {
        return issuer;
    }
    
    public Staff getReceiver()  //Returns the Staff Member to whom book is returned
    {
        return receiver;
    }
    
    public Date getIssuedDate()     //Returns the date on which this particular book was issued
    {
        return issuedDate;
    } 

    public Date getReturnDate()     //Returns the date on which this particular book was returned
    {
        return dateReturned;
    }
    
    public Borrower getBorrower()   //Returns the Borrower to whom the book was issued
    {
        return borrower;
    }
    
   
    /*---------------------------------------------*/
    
    
    /*----------Setter FUNCs.---------------------*/
    public void setReturnedDate(Date dReturned)
    {
        dateReturned = dReturned;
    }
    
    
    
    public void setReceiver(Staff r)
    {
        receiver = r;
    }
    /*-------------------------------------------*/
    



    //Computes fine for a particular loan only
   
    
}   // Loan class Closed
