package stockrestfulservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 *
 * @author Rob
 */
@Singleton
public class StockBean {
    private List<Stock> stockList;
    
    @PostConstruct
    public void initaliseStockCollection() {
        stockList = new ArrayList();
        setStock();
    }
    
    public Collection<Stock> getStock() {
        return stockList;
    }
    
    public void setStock() {
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
                            Stock newStock = new Stock(name, currency, price, change, percent, volume);
                            stockList.add(newStock);
                        }
                    }
                }
                line = buff.readLine();
            }
        } catch(MalformedURLException ex) {
            Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex) {
            Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
