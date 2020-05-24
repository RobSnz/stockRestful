package stockrestfulservice;

/**
 *
 * @author Rob
 */
public class Stock {
    private String companyName;
    private String currencyType;
    private String marketVolume;
    private double marketPrice;
    private double marketChange;
    private double changePercent;

    public Stock() {   
    }
    
    public Stock(String companyName, String currencyType, double marketPrice, double marketChange, double changePercent, String marketVolume) {
        this.companyName = companyName;
        this.currencyType = currencyType;
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

    public double getMarketPrice() {
        return marketPrice;
    }

    public double getMarketChange() {
        return marketChange;
    }

    public double getChangePercent() {
        return changePercent;
    }
    
    @Override
    public String toString() {
        return "Name: " + companyName + ", Currency: " + currencyType + ", Market Price: " + marketPrice + ", Market Change: " + marketChange + ", Change %: " + changePercent + ", Volume: " + marketVolume;
    }
}
