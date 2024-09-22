package project;

/**
 * 
 * @author Afonso Santos - fc59808
 *
 */
public class Book {
	//atributos
	private String title;
	private String ISBN;
	private String author;
	private int quantity;
	private double price;
	private double tax;
	
	/**
	 * Constructor for Book class
	 * 
	 * @param information the necessary information for a book
	 */
	public Book(String information) {
		
		StringBuilder bob = new StringBuilder();
		String[] categories = new String[6];
		
		categories = information.split(",");
		
		this.title = categories[0];
		this.ISBN = categories[1];
		this.author = categories[2];
		this.quantity = Integer.parseInt(categories[3]);
		this.price = Double.parseDouble(categories[4]);
		//Remove the % in tax
		bob.append(categories[5]);
		if (categories[5].contains("%")) {
			bob.replace(categories[5].length()-1, categories[5].length(),"");			
		}
		this.tax = Double.parseDouble(bob.toString());
		
	}
	
	/**
	 * Gives the title of the book
	 * 
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Gives the ISBN of the book
	 * 
	 * @return ISBN
	 */
	public String getISBN() {
		return this.ISBN;
	}
	
	/**
	 * Gives the author of the book
	 * 
	 * @return author
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Gives the available quantity for this book
	 *
	 * @return quantity
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Gives the price for the book
	 * 
	 * @return price
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * Gives the commission charged (tax) by publisher for the book
	 * 
	 * @return tax
	 */
	public double getTax() {
		return this.tax;
	}
	
	/**
	 * Updates the available stock of this book
	 * 
	 * @param quantity
	 */
	public void updateQuantity(int quantity) {
		this.quantity--;
	}

}
