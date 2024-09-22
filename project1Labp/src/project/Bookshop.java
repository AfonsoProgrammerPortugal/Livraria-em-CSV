package project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

/**
 * 
 * @author Afonso Santos - fc59808
 *
 */
public class Bookshop {
	//atributos
	private Book[] catalog;
	private double totalRevenue;
	private double totalProfit;
	
	public static final String EOL = System.getProperty("line.separator");

	/**
	 * Constructor for Bookshop class
	 * 
	 * @param fileName – The name of the file containing:
	 * 					 1st line: Contains the header
	 * 					 following n lines: Identifies the present books in the file
	 * @param numberOfBooks
	 * @requires fileName != null && numberOfBooks > 0
	 * @throws FileNotFoundException
	 */
	public Bookshop(String fileName, int numberOfBooks) 
			throws FileNotFoundException {
		
		int bookCounter = -1;
		Scanner reader = new Scanner(new File(fileName));
		Scanner reader2 = new Scanner(new File(fileName));
		String bookInformation;

		while (reader.hasNextLine()) {
			bookCounter++;
			reader.nextLine();
		}
		
		reader2.nextLine();
		if (bookCounter < numberOfBooks) {
			this.catalog = new Book[bookCounter];
			int i = 0;
			while (reader2.hasNextLine()) {
				bookInformation = reader2.nextLine();
				this.catalog[i] = new Book(bookInformation);
				i++;
			}
		}
		else {
			this.catalog = new Book[numberOfBooks];
			for (int i = 0; i < numberOfBooks; i++) {
				bookInformation = reader2.nextLine();
				this.catalog[i] = new Book(bookInformation);
			}
		}
		reader.close();
		reader2.close();
	}
	
	/**
	 * Returns the number of unique books present in the bookstore catalog
	 */
	public int getNumberOfBooks() {
		return this.catalog.length;
	}
	
	/**
	 * Returns the number of unique books that are still available 
	 * for purchase in the bookstore
	 */
	public int availableBooks() {
		
		int bookCounter = 0;
		
		for (int i = 0; i < this.catalog.length; i++) {
			if (this.catalog[i].getQuantity() > 0)
				bookCounter++;
		}
		
		return bookCounter;
	}
	
	/**
	 * return a book in position i
	 * 
	 * @param i the index
	 * @requires @code{1 <= i <= getNumberOfBooks()}
	 */
	public Book getBook(int i) {
		return this.catalog[i-1];
	}
	
	/**
	 * Returns the total raised (revenue) with all sales of the day
	 */
	public double getTotalRevenue() {
		return this.totalRevenue;		
	}
	
	/**
	 * Returns the total profit with all sales for the day
	 */
	public double getTotalProfit() {
		return this.totalProfit;
	}
	
	/**
	 * Returns a String with all the books written by author, divided into
	 * lines, where each line contains the title and price of a book
	 * 
	 * @param author the author to filter from
	 */
	public String filterByAuthor(String author) {
		
		StringBuilder bob = new StringBuilder();
		
		for (int i = 0; i < catalog.length; i++) {
			bob.append(authorFilter(i, author));
		}
		return bob.toString();
	}
	
	/**
	 * Returns a String, divided into lines, where each line contains the title and the
	 * author of books whose price is less than price
	 * 
	 * @param price the price to filter from
	 */
	public String filterByPrice(double price) {
		
		StringBuilder bob = new StringBuilder();
		
		for (int i = 0; i < catalog.length; i++) {
			bob.append(priceFilter(i,price));
		}
		return bob.toString();
	}
	
	/**
	 * Reads the CSV file identified by fileName and, if possible, performs the
	 * purchases that are described therein
	 * 
	 * @param fileName
	 * @return Returns a String containing the information
	 * 		   about purchases that have been made.
	 * @throws FileNotFoundException
	 */
	public String readPurchase(String fileName) 
			throws FileNotFoundException {
		
		Scanner reader = new Scanner(new File(fileName));
		StringBuilder bob = new StringBuilder();
		String[] consumerID = new String[2];
		
		reader.nextLine();
		
		while (reader.hasNextLine()) {
			boolean exists = false;
			consumerID = reader.nextLine().split(",");
			
			for (int i = 0; i < catalog.length; i++) {				
				String title = catalog[i].getTitle();
				String isbn = catalog[i].getISBN();
				String author = catalog[i].getAuthor();
				int quantity = catalog[i].getQuantity();
				double price = catalog[i].getPrice();
				
				if (consumerID[0].equals(title) || consumerID[0].equals(isbn)) {
					if (quantity > 0) {
						profitMade(i);
						catalog[i].updateQuantity(quantity);
						bob.append("Purchase successful: " + consumerID[1] + " bought " + title 
									+ " by " + author + ", price: $" + price);
						bob.append(System.lineSeparator());
						exists = true;
					}
					else {
						bob.append("Book out of stock: " + consumerID[1] + " asked for " + title
									+ " by " + author + ", price: $" + price);
						bob.append(System.lineSeparator());
						exists = true;
					}
				}
			}
			if (exists == false) {
				bob.append("Book not found: " + consumerID[1] + " asked for " + consumerID[0]);
				bob.append(System.lineSeparator());
			}
			exists = false;
		}
		String profit = String.format(Locale.US,"%.2f",this.totalProfit);
		
		bob.append("Total: $" + this.totalRevenue + " [$" + profit + "]");

		reader.close();
		
		return bob.toString();
	}
	
	/**
	 * Updates stock to reflect sales made for the day
	 * Write to a file CSV identified by fileName the stock status
	 * 
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public void updateStock(String fileName) 
			throws FileNotFoundException {
		
		PrintWriter writer = new PrintWriter(new File(fileName));
		StringBuilder bob = new StringBuilder();
		
		bob.append("Title,ISBN,Author,Quantity,Price,Tax");
		bob.append(System.lineSeparator());
		
		for (int i = 0; i < catalog.length; i++) {
			bob.append(catalog[i].getTitle() + "," 
					 + catalog[i].getISBN() + ","
					 + catalog[i].getAuthor() + ","
					 + catalog[i].getQuantity() + ","
					 + catalog[i].getPrice() + ","
					 + catalog[i].getTax());
			bob.append(System.lineSeparator());
			
		}
		
		writer.write(bob.toString());

		writer.close();
	}
	/**
	 * Updates the total Revenue and the total Profit after
	 * each successful purchase
	 * @param index
	 */
	private void profitMade(int index) {
		
		double price = catalog[index].getPrice();
		double tax = catalog[index].getTax();
		
		this.totalRevenue += price;
		this.totalProfit += price - (price*(tax/100));
	}
	private StringBuilder authorFilter (int index, String author) {

		StringBuilder bob = new StringBuilder();
		
		if (author.equals(catalog[index].getAuthor())) {
			bob.append("Title:" + catalog[index].getTitle() + ",Price:$" + catalog[index].getPrice());
			bob.append(System.lineSeparator());
		}
		return bob;
	}
	private StringBuilder priceFilter (int index, double price) {
		
		StringBuilder bob = new StringBuilder();
		
		if (catalog[index].getPrice() < price) {
			bob.append("Title:" + catalog[index].getTitle() + ",Author:" + catalog[index].getAuthor());
			bob.append(System.lineSeparator());
		}
		return bob;
	}
}
