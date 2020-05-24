package stockrestfulservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Rob
 */

// Class which is used to add new items into the stock database if they dont exist already
public class PopulateDatabase {
    private ArrayList<Stock> stockArray;
    
    public PopulateDatabase() {
        stockArray = new ArrayList<>();
        createStockList();
        insertIntoDatabase();
        //retrieveFromDatabase();
    }
    
    public static void main(String[] args) {
        PopulateDatabase p = new PopulateDatabase();
    }

    public void retrieveFromDatabase() {
        try {
            String DB_URL = "jdbc:mysql://raptor2.aut.ac.nz:3306/testUnrestricted";
            Connection conn = DriverManager.getConnection(DB_URL, "student", "fpn871");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from Stock");
            
            while(rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
                System.out.println(rs.getString(5));
                System.out.println(rs.getString(6));
            }
        } catch(SQLException ex) {
            Logger.getLogger(StockBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public final void insertIntoDatabase() {
        try {
            String DB_URL = "jdbc:mysql://raptor2.aut.ac.nz:3306/testUnrestricted";
            Connection conn = DriverManager.getConnection(DB_URL, "student", "fpn871");
            Statement st = conn.createStatement();
            ResultSet rs;
            
            for(int i = 0; i < stockArray.size(); i++) {
                int rowCount;
                String companyName = stockArray.get(i).getCompanyName();
                
                if(companyName.contains("'")) {
                    companyName = companyName.replaceAll("'","");
                }
                
                if(companyName.contains("&")) {
                    companyName = companyName.replaceAll("&","+");
                }
                
                rs = st.executeQuery("SELECT COUNT(*) FROM Stock WHERE companyname LIKE '" + companyName + "'");
                rs.next();
                rowCount = rs.getInt(1);
                
                if(rowCount == 0) {
                    st.executeUpdate("INSERT INTO Stock VALUES('" + companyName 
                        + "', '" + stockArray.get(i).getCurrencyType()  + "', '" 
                            + stockArray.get(i).getMarketPrice() + "', '" 
                                + stockArray.get(i).getMarketChange() + "', '" 
                                    + stockArray.get(i).getChangePercent() + "', '" 
                                        + stockArray.get(i).getMarketVolume() + "')");
                }
            }
        } catch(SQLException ex) {
            Logger.getLogger(StockBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Method which pulls information from the yahoo stock website, gets the needed information then
    // stores it in an arraylist to be used
    public final void createStockList() {
        URL url;
        URLConnection urlConn;
        InputStreamReader inStream;
        BufferedReader buff;
        
        try {
            url = new URL("https://nz.finance.yahoo.com/screener/predefined/most_actives?count=100&offset=0");
            urlConn = url.openConnection();
            inStream = new InputStreamReader(urlConn.getInputStream());
            buff = new BufferedReader(inStream);
            
            String line = buff.readLine();
            
            while(line != null) {
                if(line.contains(":[{\"symbol")){
                    String[] str = line.split(Pattern.quote("{") + "\"symbol\"");

                    for(int i = 1; i < str.length-1; i++) {
                        String[] str2 = str[i].split("\",\"");

                        String name = "";
                        String currency = "";
                        String price = "";
                        String change = "";
                        String percent = "";
                        String volume = "";

                        for(int j = 0; j < str2.length; j++) {
                            if(str2[j].contains("shortName")) {
                                if(!str2[j].contains("data")) {
                                    String[] str3 = str2[j].split("\"");
                                    name = str3[2];
                                }

                            } else if(str2[j].contains("currency")) {
                                String[] str3 = str2[j].split("\"");
                                currency = str3[2];

                            } else if(str2[j].contains("regularMarketPrice")) {
                                String[] str3 = str2[j].split(Pattern.quote("{"));

                                for(int k = 1; k < str3.length; k++) {
                                    String[] str4 = str3[k].split("\"");

                                    switch(k) {
                                        case 1:
                                            price = str4[5];
                                            break;
                                        case 2:
                                            change = str4[5];
                                            break;
                                        case 3:
                                            percent = str4[5];
                                            percent = percent.substring(0, percent.length() - 1);
                                            break;
                                        case 4:
                                            volume = str4[5];
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                        
                        if(!name.equals("")) {
                            Stock newStock = new Stock(name, currency, Double.parseDouble(price), Double.parseDouble(change), Double.parseDouble(percent), volume);
                            stockArray.add(newStock);
                        }
                    }
                }
                line = buff.readLine();
            }
        } catch(MalformedURLException ex) {
            Logger.getLogger(PopulateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex) {
            Logger.getLogger(PopulateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
