import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;   
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Transaction {
	
	//code based on geeks for geeks
	//https://www.geeksforgeeks.org/singleton-class-java/
	public static Transaction instance = null;
	private Transaction() {
	}
	public static synchronized Transaction tTransacon() {
		if (instance == null) {
			instance = new Transaction();
		}
		return instance;
	}

    // Perform the borrowing of a book
    public boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.borrowBook();
            member.borrowBook(book); 
            String transactionDetails = getCurrentDateTime() + " - Borrowing: " + member.getName() + " borrowed " + book.getTitle();
            saveTransaction(transactionDetails);
            System.out.println(transactionDetails);
            return true;
        } else {
            System.out.println("The book is not available.");
            return false;
        }
    }

    // Perform the returning of a book
    public void returnBook(Book book, Member member) {
        if (member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.returnBook();
            String transactionDetails = getCurrentDateTime() + " - Returning: " + member.getName() + " returned " + book.getTitle();
            saveTransaction(transactionDetails);
            System.out.println(transactionDetails);
        } else {
            System.out.println("This book was not borrowed by the member.");
        }
    }

    // Get the current date and time in a readable format
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    
    public void saveTransaction(String transaction ) {
    	try {
    		FileWriter saveFile = new FileWriter("transactions.txt", true);
    		saveFile.write(transaction+"\n");
    		saveFile.close();
    	}catch (Exception e) {
    		System.out.println("error saving file");
    	}
    }
    
    public void displayTransactionHistory() {
    	try {
    		//code based on digitalocean
    		//https://www.digitalocean.com/community/tutorials/java-read-file-line-by-line
    		BufferedReader transactions = new BufferedReader(new FileReader("transactions.txt"));
    		String line = transactions.readLine();
    		while (line != null) {
    			System.out.println(line);
    			line =transactions.readLine();
    		}
    		
    		
    	}catch (Exception e) {
    		System.out.println("error reading file");
    	}
    	
    }
}