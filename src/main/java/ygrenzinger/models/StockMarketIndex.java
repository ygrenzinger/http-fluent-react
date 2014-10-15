package ygrenzinger.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ygrenzinger on 15/10/2014.
 */
public class StockMarketIndex {

    List<Stock> stocks  = new ArrayList<>();

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
