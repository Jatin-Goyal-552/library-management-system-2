package LMS;

import java.util.Date;

public class HoldRequest {
    
    Borrower borrower;
    Book book;
    Date requestDate;
    
    public HoldRequest(Borrower bor, Book b, Date reqDate)  
    {
        borrower = bor;
        book = b;
        requestDate = reqDate;
    }
    
   
    public Borrower getBorrower()
    {
        return borrower;
    }
    
    public Book getBook()
    {
        return book;
    }
    
    public Date getRequestDate()
    {
        return requestDate;
    }
    
    public void print()
    {
        System.out.print(book.getTitle() + "\t\t\t\t" + borrower.getName() + "\t\t\t\t"  + requestDate + "\n");
    }
}
