
package LMS;

// Including Header Files.
import java.io.*;
import java.util.*;
import java.sql.*;

public class Main 
{
    // Clearing Required Area of Screen
    public static void clrscr()
    {
        for (int i = 0; i < 20; i++)
            System.out.println();
    }

    // Asking for Input as Choice
    public static int takeInput(int min, int max)
    {    
        String choice;
        Scanner input = new Scanner(System.in);        
        
        while(true)
        {
            System.out.println("\nEnter Choice: ");

            choice = input.next();

            if((!choice.matches(".*[a-zA-Z]+.*")) && (Integer.parseInt(choice) > min && Integer.parseInt(choice) < max))
            {
                return Integer.parseInt(choice);
            }
            
            else
                System.out.println("\nInvalid Input.");
        }
          
    }

    // Functionalities of all Persons
    public static void allFunctionalities(Person person, int choice) throws IOException
    {
        Library lib = Library.getInstance();
        
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        
        //Search Book
        if (choice == 1)
        {
            lib.searchForBooks();
        }
        
        //Do Hold Request
        else if (choice == 2)
        {
            ArrayList<Book> books = lib.searchForBooks();
            
            if (books != null)
            {
                input = takeInput(-1,books.size());
                
                Book b = books.get(input);
                
                if("Librarian".equals(person.getClass().getSimpleName()))
                {                
                    Borrower bor = lib.findBorrower();

                    if (bor != null)
                        b.makeHoldRequest(bor);
                }
                else                
                    b.makeHoldRequest((Borrower)person);
            }
        }
        
       
        else if (choice == 3)
        {
            if("Librarian".equals(person.getClass().getSimpleName()))
            {
                Borrower bor = lib.findBorrower();
                
                if(bor!=null)
                {
                    double totalFine = lib.computeFine2(bor);
                    System.out.println("\nYour Total Fine is : Rs " + totalFine );                     
                }
            }
            else
            {
                double totalFine = lib.computeFine2((Borrower)person);
                System.out.println("\nYour Total Fine is : Rs " + totalFine );                 
            }
        }
        
        //Check hold request queue of a book
        else if (choice == 4)
        {
            ArrayList<Book> books = lib.searchForBooks();
            
            if (books != null)
            {
                input = takeInput(-1,books.size());
                books.get(input).printHoldRequests();
            }
        }
                       
        //Issue a Book
        else if (choice == 5)
        {
            ArrayList<Book> books = lib.searchForBooks();

            if (books != null)
            {
                input = takeInput(-1,books.size());
                Book b = books.get(input);
                
                Borrower bor = lib.findBorrower();

                if(bor!=null)
                {
                    b.issueBook(bor, (Staff)person);            
                }
            }
        }        

        //Return a Book
        else if (choice == 6)
        {
            Borrower bor = lib.findBorrower();

            if(bor!=null)
            {
                bor.printBorrowedBooks();
                ArrayList<Loan> loans = bor.getBorrowedBooks();
                
                if (!loans.isEmpty())
                {
                    input = takeInput(-1,loans.size());
                    Loan l = loans.get(input);
                    
                    l.getBook().returnBook(bor, l, (Staff)person);            
                }
                else
                    System.out.println("\nThis borrower " + bor.getName() + " has no book to return.");
            }
        }        

        
        //Add new Borrower
        else if (choice == 7)
        {
            lib.createPerson('b');
        }        

      
        else if (choice == 8)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\nEnter Title:");
            String title = reader.readLine();

            System.out.println("\nEnter Subject:");
            String subject = reader.readLine();

            System.out.println("\nEnter Author:");
            String author = reader.readLine();
            
            lib.createBook(title, subject, author);
        }        
        
       
        System.out.println("\nPress any key to continue..\n");
        scanner.next();
    }
    

    
    
   
    
    /*-------------------------------------MAIN---------------------------------------------------*/
    
    public static void main(String[] args)
    {
        Scanner admin = new Scanner(System.in);
        
        //-------------------INTERFACE---------------------------//
        
        Library lib = Library.getInstance();
        
        // Setting some by default information like name of library ,fine, deadline and limit of hold request
        lib.setFine(20);
        lib.setRequestExpiry(7);
        lib.setReturnDeadline(5);
        lib.setName("FAST Library");
        
        // Making connection with Database.
        Connection con = lib.makeConnection();
        
        if (con == null)    // Oops can't connnect !
        {
          System.out.println("\nError connecting to Database. Exiting.");
           return;
       }
        
        try {

       lib.populateLibrary(con);   // Populating Library with all Records
         
        boolean stop = false;
        while(!stop)
        {   
            clrscr();
        
            // FRONT END //
            System.out.println("--------------------------------------------------------");
            System.out.println("\tWelcome to Library Management System");
            System.out.println("--------------------------------------------------------");
            
            System.out.println("Following Functionalities are available: \n");
            System.out.println("1- Login");
            System.out.println("2- Exit");
            System.out.println("3- Admininstrative Functions"); // Administration has access only 
            
            System.out.println("-----------------------------------------\n");        
            
            int choice = 0;

            choice = takeInput(0,4);
            
            if (choice == 3)
            {                   
                System.out.println("\nEnter Password: ");
                String aPass = admin.next();
                
                if(aPass.equals("lib"))
                {
                    while (true)    // Way to Admin Portal
                    {
                        clrscr();

                        System.out.println("--------------------------------------------------------");
                        System.out.println("\tWelcome to Admin's Portal");
                        System.out.println("--------------------------------------------------------");
                        System.out.println("Following Functionalities are available: \n");

                       System.out.println("1- Add Librarian");
                        System.out.println("2- View All Books in Library");
                        System.out.println("3- Logout");
                        System.out.println("---------------------------------------------");
                        choice = takeInput(0,4);
                        if (choice == 3)
                               break;
                        else if (choice == 2)
                            lib.viewAllBooks();
                        if (choice == 1)  
                            lib.createPerson('l');
                        System.out.println("\nPress any key to continue..\n");
                        admin.next();                        
                    }
                }
                else
                    System.out.println("\nSorry! Wrong Password.");
            }
 
            else if (choice == 1)
            {
                Person person = lib.login();

                if (person == null){}
                
                else if (person.getClass().getSimpleName().equals("Borrower"))
                {                    
                    while (true)    // Way to Borrower's Portal
                    {
                        clrscr();
                                        
                        System.out.println("--------------------------------------------------------");
                        System.out.println("\tWelcome to Borrower's Portal");
                        System.out.println("--------------------------------------------------------");
                        System.out.println("Following Functionalities are available: \n");
                        System.out.println("1- Search a Book");
                        System.out.println("2- Place a Book on hold");
                        System.out.println("3- Check Personal Info of Borrower");
                        System.out.println("4- Check Total Fine of Borrower"); 
                        System.out.println("5- Check Hold Requests Queue of a Book");                         
                        System.out.println("6- Logout");
                        System.out.println("--------------------------------------------------------");
                        
                        choice = takeInput(0,7);

                        if (choice == 6)
                            break;
                        
                        allFunctionalities(person,choice);
                    }
                }
                
                
                else if (person.getClass().getSimpleName().equals("Librarian"))
                {
                    while(true) // Way to Librarian Portal
                    {
                        clrscr();
                                        
                        System.out.println("--------------------------------------------------------");
                        System.out.println("\tWelcome to Librarian's Portal");
                        System.out.println("--------------------------------------------------------");
                        System.out.println("Following Functionalities are available: \n");
                        System.out.println("1- Search a Book");
                       System.out.println("2- Place a Book on hold");
                      
                        System.out.println("3- Check Total Fine of Borrower");      
                       System.out.println("4- Check Hold Requests Queue of a Book");                        
                        System.out.println("5- Check out a Book");
                        System.out.println("6- Check in a Book");                        
                     
                        System.out.println("7- Add a new Borrower");
                       
                        System.out.println("8- Add new Book");
                                     
                        System.out.println("9- Logout");
                        System.out.println("--------------------------------------------------------");
                        
                        choice = takeInput(0,10);

                        if (choice == 9)
                            break;
                                               
                        allFunctionalities(person,choice);                        
                    }                    
                }
                
            }

            else
                stop = true;

            System.out.println("\nPress any key to continue..\n");
            Scanner scanner = new Scanner(System.in);
            scanner.next();            
        }
        
        //Loading back all the records in database
        lib.fillItBack(con);
        }
        catch(Exception e)
        {
            System.out.println("\nExiting...\n");
        }   // System Closed!
       
    }    // Main Closed
    
}   // Class closed.

