package project;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

public class RunProject1 {
    
    public static void main(String[] args) throws FileNotFoundException {
        new RunProject1().execute();
    }
    
    private void execute() throws FileNotFoundException {

        int exec = 1;
        // 1 pedido
        try(PrintWriter writer = new PrintWriter(String.format("./log/log%d.txt", exec))){
            int count = 1;
            Bookshop lib = new Bookshop(String.format("./csv/stockIn/stockIn%d.csv",exec), 2);
            String purchaseLog = lib.readPurchase("./csv/purchase/purchase1.csv");
            writeLog(writer, purchaseLog, count);
            updateLogAndStock(exec, writer, lib);
        };
        
        exec++;
        // 2 pedidos      
        try(PrintWriter writer = new PrintWriter(String.format("./log/log%d.txt", exec))){
            Bookshop lib = new Bookshop(String.format("./csv/stockIn/stockIn%d.csv",exec), 5);
            for(int count = 1; count <= 2; count++) {
                String purchaseLog = lib.readPurchase("./csv/purchase/purchase"+count+".csv");
                writeLog(writer, purchaseLog, count);   
            }
            updateLogAndStock(exec, writer, lib);
        };
        
        exec++;
        // 3 pedidos - 20 livros  
        try(PrintWriter writer = new PrintWriter(String.format("./log/log%d.txt", exec))){
            Bookshop lib = new Bookshop(String.format("./csv/stockIn/stockIn%d.csv",exec), 20);
            for(int count = 1; count <= 3; count++) {
                String purchaseLog = lib.readPurchase("./csv/purchase/purchase"+count+".csv");
                writeLog(writer, purchaseLog, count);   
            }
            updateLogAndStock(exec, writer, lib);
        };
        
        exec++;
        // 3 pedidos - 5 livros      
        try(PrintWriter writer = new PrintWriter(String.format("./log/log%d.txt", exec))){
            Bookshop lib = new Bookshop(String.format("./csv/stockIn/stockIn%d.csv",exec-1), 5);
            for(int count = 1; count <= 3; count++) {
                String purchaseLog = lib.readPurchase("./csv/purchase/purchase"+count+".csv");
                writeLog(writer, purchaseLog, count);   
            }
            updateLogAndStock(exec, writer, lib);
        };
        
    }


    private void updateLogAndStock(int exec, PrintWriter writer, Bookshop lib) throws FileNotFoundException {
        writer.println(String.format(Locale.US,"Revenue: $%.2f",lib.getTotalRevenue()));
        writer.println(String.format(Locale.US,"Profit: $%.2f",lib.getTotalProfit()));
        lib.updateStock(String.format("./csv/stockOut/stockOut%d.csv", exec));
    }

    private void writeLog(PrintWriter writer, String purchaseLog, int i) {
        writer.println(">> Log from purchase " + i);
        writer.println(purchaseLog);
        writer.println();
    }
}
