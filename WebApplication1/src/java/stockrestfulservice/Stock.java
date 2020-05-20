package stockrestfulservice;

/**
 *
 * @author Rob
 */
public class Stock {
    private String companyName;
    private String currencyType;
    private String marketPrice;
    private String marketChange;
    private String changePercent;
    private String marketVolume;
    
    public Stock() {
        
    }
    
    public Stock(String companyName, String currencyType, String marketPrice, String marketChange, String changePercent, String marketVolume) {
        this.companyName = companyName;
        this.currencyType = currencyType;
        this.marketPrice = marketPrice;
        this.marketChange = marketChange;
        this.changePercent = changePercent;
        this.marketVolume = marketVolume;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getXMLString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<stock>");
        buffer.append("<companyName>").append(companyName).append("</companyName>");
        buffer.append("<currencyType>").append(currencyType).append("</currencyType>");
        buffer.append("<marketPrice>").append(marketPrice).append("</marketPrice>");
        buffer.append("<marketChange>").append(marketChange).append("</marketChange>");
        buffer.append("<changePercent>").append(changePercent).append("</changePercent>");
        buffer.append("<marketVolume>").append(marketVolume).append("</marketVolume>");
        buffer.append("</stock>");
        return buffer.toString();
    }
    
    @Override
    public String toString() {
        return "Name: " + companyName + ", Currency: " + currencyType + ", Market Price: " + marketPrice + ", Market Change: " + marketChange + ", Change %: " + changePercent + ", Volume: " + marketVolume;
    }
}
