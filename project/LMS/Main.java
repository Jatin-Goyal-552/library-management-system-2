
package LMS;


import java.io.*;
import java.util.*;


public class Main 
{
    
    public static void clrscr()
    {
        for (int i = 0; i < 20; i++)
            System.out.println();
    }

   
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

    
    public static void allFunctionalities(Person person, int choice) throws IOException
    {
        Library lib = Library.getInstance();
        
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        
        
        if (choice == 1)
        {
            lib.searchForBooks();
        }
        
       
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
            ArrayList<Book> books = lib.searchForBooks();
            
            if (books != null)
            {
                input = takeInput(-1,books.size());
                books.get(input).printHoldRequests();
            }
        }
                       
        
        else if (choice == 4)
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

        
        else if (choice == 5)
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

        
       
        else if (choice == 6)
        {
            lib.createPerson('b');
        }        

      
        else if (choice == 7)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\nEnter Title:");
            String title = reader.readLine();

            System.out.println("\nEnter Author:");
            String author = reader.readLine();
            
            lib.createBook(title, author);
        }        
        
       
        System.out.println("\nPress any key to continue..\n");
        scanner.next();
    }
    

    
    
   
    
    
    
    public static void main(String[] args)
    {
        Scanner admin = new Scanner(System.in);
        
        
        
        Library lib = Library.getInstance();
        
        
        lib.setFine(20);
        lib.setRequestExpiry(7);
        lib.setReturnDeadline(5);
        lib.setName("FAST Library");
        
       
        try {

         
         
        boolean stop = false;
        while(!stop)
        {   
            clrscr();
        
            
            System.out.println("--------------------------------------------------------");
            System.out.println("\tWelcome to IIITV Library Management System");
            System.out.println("--------------------------------------------------------");
            
            System.out.println("Following Functionalities are available: \n");
            System.out.println("1- Login");
            System.out.println("2- Exit");
            System.out.println("3- Admininstrative Functions"); 
            
            System.out.println("-----------------------------------------\n");        
            
            int choice = 0;

            choice = takeInput(0,4);
            
            if (choice == 3)
            {                   
                System.out.println("\nEnter Password: ");
                String aPass = admin.next();
                
                if(aPass.equals("lib"))
                {
                    while (true)    
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
                    while (true)   
                    {
                        clrscr();
                                        
                        System.out.println("--------------------------------------------------------");
                        System.out.println("\tWelcome to Borrower's Portal");
                        System.out.println("--------------------------------------------------------");
                        System.out.println("Following Functionalities are available: \n");
                        System.out.println("1- Search a Book");
                        System.out.println("2- Place a Book on hold");                      
                        System.out.println("3- Logout");
                        System.out.println("--------------------------------------------------------");
                        
                        choice = takeInput(0,4);

                        if (choice == 3)
                            break;
                        
                        allFunctionalities(person,choice);
                    }
                }
                
                
                else if (person.getClass().getSimpleName().equals("Librarian"))
                {
                    while(true) 
                    {
                        clrscr();
                                        
                        System.out.println("--------------------------------------------------------");
                        System.out.println("\tWelcome to Librarian's Portal");
                        System.out.println("--------------------------------------------------------");
                        System.out.println("Following Functionalities are available: \n");
                        System.out.println("1- Search a Book");
                       System.out.println("2- Place a Book on hold");
                      
                           
                       System.out.println("3- Check Hold Requests Queue of a Book");                        
                        System.out.println("4- Check out a Book");
                        System.out.println("5- Check in a Book");                        
                     
                        System.out.println("6- Add a new Borrower");
                       
                        System.out.println("7- Add new Book");
                                     
                        System.out.println("8- Logout");
                        System.out.println("--------------------------------------------------------");
                        
                        choice = takeInput(0,9);

                        if (choice == 8)
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
        
   
        }
        catch(Exception e)
        {
            System.out.println("\nExiting...\n");
        }   
       
    }    
    
}   

