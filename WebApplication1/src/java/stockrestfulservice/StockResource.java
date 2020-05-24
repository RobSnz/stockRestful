package stockrestfulservice;

import java.util.Collection;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Rob
 */

@Named
@Path("/stock")
public class StockResource {
    @EJB
    private StockBean stockBean;
    @Context
    private UriInfo context;
    private static final char QUOTE = '\"';
    
    public StockResource() {
        
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getAllStock() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<stock uri=").append(QUOTE).append(context.getAbsolutePath()).append(QUOTE).append(">");

        Collection<Stock> allStock = stockBean.getStock();

        for(Stock stock : allStock) {
            buffer.append(stock.getXMLString());
        }

        buffer.append("</stock>");

        return buffer.toString();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{percent}")
    public String getPercentageStock(@PathParam("percent") String percent) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<stock uri=").append(QUOTE).append(context.getAbsolutePath()).append(QUOTE).append(">");

        Collection<Stock> allStock = stockBean.getStock();

        for(Stock stock : allStock) {
            if(percent.equals("positive")) {
                if(stock.getChangePercent() > 0) {
                    buffer.append(stock.getXMLString());
                }
            } else if(percent.equals("negative")) {
                if(stock.getChangePercent() < 0) {
                    buffer.append(stock.getXMLString());
                }
            }
        }

        buffer.append("</stock>");

        return buffer.toString();
    }
}
