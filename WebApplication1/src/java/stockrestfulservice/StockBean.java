package stockrestfulservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 *
 * @author Rob
 */
@Singleton
public class StockBean {
    private ArrayList<Stock> stockList;
    
    @PostConstruct
    public void initaliseStockCollection() {
        stockList = new ArrayList();
        //testingMethod();
        addStockFromDatabase();
    }
    
    public ArrayList<Stock> getStock() {
        return stockList;
    }
    
    // Method which pulls the data from the database and then saves it in the stockList
    public void addStockFromDatabase() {
        try {
            String DB_URL = "jdbc:mysql://raptor2.aut.ac.nz:3306/testUnrestricted";
            Connection conn = DriverManager.getConnection(DB_URL, "student", "fpn871");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from Stock");
            System.out.println(rs.getMetaData().getColumnCount());
            
            while(rs.next()) {
                String name = rs.getString(1);
                String currency = rs.getString(2);
                double price = Double.parseDouble(rs.getString(3));
                double change = Double.parseDouble(rs.getString(4));
                double percent = Double.parseDouble(rs.getString(5));
                String volume = rs.getString(6);
                String symbol = rs.getString(7);
                
                stockList.add(new Stock(name, currency, symbol, price, change, percent, volume));
            }
        } catch(SQLException ex) {
            Logger.getLogger(StockBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Method to test adding into stockList without a database for debugging purposes
    public void testingMethod() { 
        for(int i = 0; i < 100; i++) {
            String s = Integer.toString(i);
            Stock newStock = new Stock(s, s, s, i, i, i, s);
            stockList.add(newStock);
        }
    }
}
