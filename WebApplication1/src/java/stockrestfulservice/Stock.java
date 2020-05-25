package stockrestfulservice;

/**
 *
 * @author Rob
 */
public class Stock {
    private String companyName;
    private String currencyType;
    private String marketVolume;
    private String companySymbol;
    private double marketPrice;
    private double marketChange;
    private double changePercent;

    public Stock() {   
    }
    
    public Stock(String companyName, String currencyType, String companySymbol, double marketPrice, double marketChange, double changePercent, String marketVolume) {
        this.companyName = companyName;
        this.currencyType = currencyType;
        this.companySymbol = companySymbol;
        this.marketPrice = marketPrice;
        this.marketChange = marketChange;
        this.changePercent = changePercent;
        this.marketVolume = marketVolume;
    }
    
    public String getXMLString() {
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("<stock>");
        buffer.append("<companyName>").append(companyName).append("</companyName>");
        buffer.append("<currencyType>").append(currencyType).append("</currencyType>");
        buffer.append("<companySymbol>").append(companySymbol).append("</companySymbol>");
        buffer.append("<marketPrice>").append(marketPrice).append("</marketPrice>");
        buffer.append("<marketChange>").append(marketChange).append("</marketChange>");
        buffer.append("<changePercent>").append(changePercent).append("%").append("</changePercent>");
        buffer.append("<marketVolume>").append(marketVolume).append("</marketVolume>");
        buffer.append("</stock>");
        
        return buffer.toString();
    }
    
    public String getCompanyName() {
        return companyName;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public String getMarketVolume() {
        return marketVolume;
    }
    
    public String getCompanySymbol() {
        return companySymbol;
    }
    
    public double getMarketPrice() {
        return marketPrice;
    }

    public double getMarketChange() {
        return marketChange;
    }

    public double getChangePercent() {
        return changePercent;
    }
}
