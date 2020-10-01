package LMS;



import java.io.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Library {
    
    private String name;                                
    private Librarian librarian;                                         
    private ArrayList <Person> persons;                 
    private ArrayList <Book> booksInLibrary;          
    
    private ArrayList <Loan> loans;                     
        
    public int book_return_deadline;                   
    public double per_day_fine;
    
    public int hold_request_expiry;                   
    
    
    
    private static Library obj;
   
    public static Library getInstance()
    {
        if(obj==null)
        {
            obj = new Library();
        }
        
        return obj;
    }
    
    
    private Library()  
    {
        name = null;
        librarian = null;
        persons = new ArrayList();
    
        booksInLibrary = new ArrayList();
        loans = new ArrayList();
    }

    
  
    
    public void setReturnDeadline(int deadline)
    {
        book_return_deadline = deadline;
    }

    public void setFine(double perDayFine)
    {
        per_day_fine = perDayFine;
    }

    public void setRequestExpiry(int hrExpiry)
    {
        hold_request_expiry = hrExpiry;
    }
   
    public void setName(String n)   
    {
        name = n;
    }
     
   
    
    public int getHoldRequestExpiry()
    {
        return hold_request_expiry;
    }
    
    public ArrayList<Person> getPersons()
    {
        return persons;
    }
    
    public Librarian getLibrarian()
    {
        return librarian;
    }
      
    public String getLibraryName()
    {
        return name;
    }

    public ArrayList<Book> getBooks()
    {
        return booksInLibrary;
    }
    
   
    public boolean addLibrarian(Librarian lib)
    {
        
        if (librarian == null)
        {
            librarian = lib;
            persons.add(librarian);
            return true;
        }
        else
            System.out.println("\nSorry, the library already has one librarian. New Librarian can't be created.");
        return false;
    }
    
    
    public void addBorrower(Borrower b)
    {
        persons.add(b);
    }

    
    public void addLoan(Loan l)
    {
        loans.add(l);
    }
    
   
    public Borrower findBorrower()
    {
        System.out.println("\nEnter Borrower's ID: ");
        
        int id = 0;
        
        Scanner scanner = new Scanner(System.in);
        
        try{
            id = scanner.nextInt();
        }
        catch (java.util.InputMismatchException e)
        {
            System.out.println("\nInvalid Input");
        }

        for (int i = 0; i < persons.size(); i++)
        {
            if (persons.get(i).getID() == id && persons.get(i).getClass().getSimpleName().equals("Borrower"))
                return (Borrower)(persons.get(i));
        }
        
        System.out.println("\nSorry this ID didn't match any Borrower's ID.");
        return null;
    }
    
    
   
    public void addBookinLibrary(Book b)
    {
        booksInLibrary.add(b);
    }
    
   
    public ArrayList<Book> searchForBooks() throws IOException
    {
        String choice;
        String title = "", subject = "", author = "";
                
        Scanner sc = new Scanner(System.in);  
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        while (true)
        {
            System.out.println("\nEnter either '1' or '2'  for search by Title or Author of Book respectively: ");  
            choice = sc.next();
            
            if (choice.equals("1") || choice.equals("2") )
                break;
            else
                System.out.println("\nWrong Input!");
        }

        if (choice.equals("1"))
        {
            System.out.println("\nEnter the Title of the Book: ");              
            title = reader.readLine();  
        }

        else if (choice.equals("2"))
        {
            
            System.out.println("\nEnter the Author of the Book: ");              
            author = reader.readLine();              
        }
        
        ArrayList<Book> matchedBooks = new ArrayList();
        
        for(int i = 0; i < booksInLibrary.size(); i++)
        {
            Book b = booksInLibrary.get(i);
            
            if (choice.equals("1"))
            { 
                if (b.getTitle().equals(title))
                    matchedBooks.add(b);
            }
            else if (choice.equals("2"))
            { 
               
                if (b.getAuthor().equals(author))
                    matchedBooks.add(b);                
            }
        }
        
      
        if (!matchedBooks.isEmpty())
        {
            System.out.println("\nThese books are found: \n");
                        
            System.out.println("------------------------------------------------------------------------------");            
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");
            
            for (int i = 0; i < matchedBooks.size(); i++)
            {                      
                System.out.print(i + "-" + "\t\t");
                matchedBooks.get(i).printInfo();
                System.out.print("\n");
            }
            
            return matchedBooks;
        }
        else
        {
            System.out.println("\nSorry. No Books were found related to your query.");
            return null;
        }
    }
    
    
    
   
     public void viewAllBooks()
    {
        if (!booksInLibrary.isEmpty())
        { 
            System.out.println("\nBooks are: ");
            
            System.out.println("------------------------------------------------------------------------------");            
            System.out.println("No.\t\tTitle\t\t\tAuthor");
            System.out.println("------------------------------------------------------------------------------");
            
            for (int i = 0; i < booksInLibrary.size(); i++)
            {                      
                System.out.print(i + "-" + "\t\t");
                booksInLibrary.get(i).printInfo();
                System.out.print("\n");
            }
        }
        else
            System.out.println("\nCurrently, Library has no books.");                
    }

     
    
    
    
    
    public void createPerson(char x)
    {
        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
              
        System.out.println("\nEnter Name: ");
        String n = "";
        try {
            n = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(x=='l')
        {
           System.out.println("Enter Address: "); 
        }
        else{
        System.out.println("Enter Room Number: ");
        }
        String address = "";
        try {
            address = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int phone = 0;
        
        try{
            System.out.println("Enter Phone Number: ");
            phone = sc.nextInt();
        }
        catch (java.util.InputMismatchException e)
        {
            System.out.println("\nInvalid Input.");
        }
            
        
       
        
        if (x == 'l')
        {
            double salary = 0;            
            try{
                System.out.println("Enter Salary: ");
                salary = sc.nextDouble();
            }
            catch (java.util.InputMismatchException e)
            {
                System.out.println(e);
                System.out.println("\nInvalid Input.");
            }
            
            Librarian l = new Librarian(-1,n,address,phone,salary,-1); 
            if(addLibrarian(l))
            {
                System.out.println("\nLibrarian with name " + n + " created successfully.");
                System.out.println("\nYour ID is : " + l.getID());
                System.out.println("Your Password is : " + l.getPassword());
            }
        }

       
        else
        {
            Borrower b = new Borrower(-1,n,address,phone);
            addBorrower(b);            
            System.out.println("\nBorrower with name " + n + " created successfully.");

            System.out.println("\nYour ID is : " + b.getID());
            System.out.println("Your Password is : " + b.getPassword());            
        }        
    }
     

       
    public void createBook(String title, String author)
    {
        Book b = new Book(-1,title,author,false);
        
        addBookinLibrary(b);
        
        System.out.println("\nBook with Title " + b.getTitle() + " is successfully created.");
    }
    

    
   
    public Person login()
    {
        Scanner input = new Scanner(System.in);
        
        int id = 0;
        String password = "";
        
        System.out.println("\nEnter ID: ");
        
        try{
            id = input.nextInt();
        }
        catch (java.util.InputMismatchException e)
        {
            System.out.println("\nInvalid Input");
        }
        
        System.out.println("Enter Password: ");
        password = input.next();
        
        for (int i = 0; i < persons.size(); i++)
        {
            if (persons.get(i).getID() == id && persons.get(i).getPassword().equals(password))
            {
                System.out.println("\nLogin Successful");
                return persons.get(i);
            }
        }
        
        if(librarian!=null)
        {
            if (librarian.getID() == id && librarian.getPassword().equals(password))
            {
                System.out.println("\nLogin Successful");
                return librarian;
            }
        }
        
        System.out.println("\nSorry! Wrong ID or Password");        
        return null;
    }

    
    
}