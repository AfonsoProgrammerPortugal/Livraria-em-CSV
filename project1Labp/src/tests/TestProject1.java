package tests;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import project.Book;
import project.Bookshop;

public class TestProject1 {

    private static final String path = "./csv/tests/";
    private static final String STOCK_OUT_CSV = path+"stockOut.csv";
    private static final String PURCHASE4_CSV = path+"purchase4.csv";
    private static final String PURCHASE3_CSV = path+"purchase3.csv";
    private static final String PURCHASE2_CSV = path+"purchase2.csv";
    private static final String PURCHASE1_CSV = path+"purchase1.csv";
    private static final String STOCK_IN_CSV = path+"stockIn.csv";
   

    @Test
    public void testConstructor() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        assertEquals(2, lib.getNumberOfBooks());
    }
    
    @Test
    public void testConstructor2() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 12);
        assertEquals(11, lib.availableBooks());
    }

    @Test
    public void testLoadBooks() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        Book book = lib.getBook(1);
        assertEquals("The Lord of the Rings", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals(10, book.getQuantity());
        assertEquals(99.99, book.getPrice(), 0.01);
        assertEquals("978054400341-5", book.getISBN());
        assertEquals(10.13, book.getTax(), 0.01);
    }
   
    @Test
    public void testLoadBooks2() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        Book book = lib.getBook(2);
        assertEquals("The Hobbit", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals(1, book.getQuantity());
        assertEquals(51.32, book.getPrice(), 0.01);
        assertEquals("978054792822-7", book.getISBN());
        assertEquals(10.986, book.getTax(), 0.01);
    }
    
    @Test
    public void testBooksByAuthor() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 11);
        String books = lib.filterByAuthor("J.K. Rowling");
        StringBuilder sb = new StringBuilder();
        sb.append("Title:Harry Potter and the Philosopher's Stone,Price:$14.99");
        sb.append(System.lineSeparator());
        sb.append("Title:Harry Potter and the Chamber of Secrets,Price:$15.99");
        sb.append(System.lineSeparator());
        sb.append("Title:Harry Potter and the Prisoner of Azkaban,Price:$17.99");
        sb.append(System.lineSeparator());
        sb.append("Title:Harry Potter and the Goblet of Fire,Price:$18.99");
        sb.append(System.lineSeparator());
        sb.append("Title:Harry Potter and the Order of Phoenix,Price:$20.99");
        sb.append(System.lineSeparator());
        sb.append("Title:Harry Potter and the Half-Blood Prince,Price:$21.99");
        sb.append(System.lineSeparator());
        sb.append("Title:Harry Potter and the Deathly Hallows,Price:$22.99");
        sb.append(System.lineSeparator());

        assertEquals(sb.toString(), books);
    }

    @Test
    public void testBooksByPrice() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 11);
        String books = lib.filterByPrice(16);
        StringBuilder sb = new StringBuilder();
        sb.append("Title:Harry Potter and the Philosopher's Stone,Author:J.K. Rowling");
        sb.append(System.lineSeparator());
        sb.append("Title:Harry Potter and the Chamber of Secrets,Author:J.K. Rowling");
        sb.append(System.lineSeparator());
        sb.append("Title:The Hunger Games,Author:Suzanne Collins");
        sb.append(System.lineSeparator());
        sb.append("Title:The Da Vinci Code,Author:Dan Brown");
        sb.append(System.lineSeparator());

        assertEquals(sb.toString(), books);
    }

    @Test
    public void testReadPurchase() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        String purchaseLog = lib.readPurchase(PURCHASE1_CSV);
        StringBuilder expected = new StringBuilder();
        expected.append("Purchase successful: Ana bought The Lord of the Rings by J.R.R. Tolkien, price: $99.99");
        expected.append(System.lineSeparator());
        expected.append("Total: $99.99 [$89.86]");
        assertEquals(expected.toString(), purchaseLog);
    }

    @Test
    public void testReadPurchase2() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        String purchaseLog = lib.readPurchase(PURCHASE2_CSV);
        StringBuilder expected = new StringBuilder();
        expected.append("Purchase successful: Ana bought The Lord of the Rings by J.R.R. Tolkien, price: $99.99");
        expected.append(System.lineSeparator());
        expected.append("Purchase successful: Ana bought The Hobbit by J.R.R. Tolkien, price: $51.32");
        expected.append(System.lineSeparator());
        expected.append("Total: $151.31 [$135.54]");
        assertEquals(expected.toString(), purchaseLog);
    }

    @Test
    public void testReadPurchase3() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        String purchaseLog = lib.readPurchase(PURCHASE3_CSV);
        StringBuilder expected = new StringBuilder();
        expected.append("Purchase successful: Ana bought The Lord of the Rings by J.R.R. Tolkien, price: $99.99");
        expected.append(System.lineSeparator());
        expected.append("Purchase successful: Ana bought The Hobbit by J.R.R. Tolkien, price: $51.32");
        expected.append(System.lineSeparator());
        expected.append("Book not found: Rita asked for Twiligth");
        expected.append(System.lineSeparator());
        expected.append("Total: $151.31 [$135.54]");
        assertEquals(expected.toString(), purchaseLog);
    }

    @Test
    public void testReadPurchase4() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        String purchaseLog = lib.readPurchase(PURCHASE4_CSV);
        StringBuilder expected = new StringBuilder();
        expected.append("Purchase successful: Ana bought The Lord of the Rings by J.R.R. Tolkien, price: $99.99");
        expected.append(System.lineSeparator());
        expected.append("Purchase successful: Ana bought The Hobbit by J.R.R. Tolkien, price: $51.32");
        expected.append(System.lineSeparator());
        expected.append("Book not found: Rita asked for Twiligth");
        expected.append(System.lineSeparator());
        expected.append("Book out of stock: Pedro asked for The Hobbit by J.R.R. Tolkien, price: $51.32");
        expected.append(System.lineSeparator());
        expected.append("Total: $151.31 [$135.54]");
        assertEquals(expected.toString(), purchaseLog);
    }
    
    @Test
    public void testAvailableBooks () throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        assertEquals(2, lib.availableBooks());
        lib.readPurchase(PURCHASE4_CSV);
        assertEquals(1, lib.availableBooks());
        
    }
    
    @Test
    public void testNumberOfBooks () throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        assertEquals(2, lib.getNumberOfBooks());
        
    }


    @Test
    public void testTotalRevenue() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        lib.readPurchase(PURCHASE1_CSV);
        lib.readPurchase(PURCHASE2_CSV);
        lib.readPurchase(PURCHASE3_CSV);
        lib.readPurchase(PURCHASE4_CSV);
        assertEquals(451.28, lib.getTotalRevenue(), 0.01);
    }
    
    @Test
    public void testTotalProfit() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        lib.readPurchase(PURCHASE1_CSV);
        lib.readPurchase(PURCHASE2_CSV);
        lib.readPurchase(PURCHASE3_CSV);
        lib.readPurchase(PURCHASE4_CSV);
        assertEquals(405.126, lib.getTotalProfit(), 0.01);
    }

    @Test
    public void testUpdateStock() throws FileNotFoundException {
        Bookshop lib = new Bookshop(STOCK_IN_CSV, 2);
        lib.readPurchase(PURCHASE1_CSV);
        lib.readPurchase(PURCHASE2_CSV);
        lib.readPurchase(PURCHASE3_CSV);
        lib.readPurchase(PURCHASE4_CSV);
        lib.updateStock(STOCK_OUT_CSV);
        Bookshop lib2 = new Bookshop(STOCK_OUT_CSV, 2);
        Book book = lib2.getBook(1);
        assertEquals("The Lord of the Rings", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals(6, book.getQuantity());
        assertEquals(99.99, book.getPrice(), 0.01);
        assertEquals("978054400341-5", book.getISBN());
        assertEquals(10.13, book.getTax(), 0.01);
        book = lib2.getBook(2);
        assertEquals("The Hobbit", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals(0, book.getQuantity());
        assertEquals(51.32, book.getPrice(), 0.01);
        assertEquals("978054792822-7", book.getISBN());
        assertEquals(10.986, book.getTax(), 0.01);
    }





}
