package ygrenzinger.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ygrenzinger on 15/10/2014.
 */
public class StockMarketIndex {

    private final List<Stock> stocks;

    ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(2);

    public StockMarketIndex(List<Stock> _stocks) {
        this.stocks = _stocks;
        Random random = new Random();
        stocks.parallelStream().forEach(stock -> stock.setPrice(random.nextDouble() * 100.0));
        scheduledExecutorService.scheduleAtFixedRate(new UpdatingStockRunnable(), 100, 100, TimeUnit.MILLISECONDS);
    }


    public List<Stock> stocks() {
        return stocks;
    }


    private class UpdatingStockRunnable implements Runnable {


        private void updateStockPrice(Stock stock) {
            Random random = new Random();
            stock.setVariation(Math.round(random.nextDouble()*100.0)/100.0);
            if (random.nextInt(2) == 0) {
                stock.setVariation(-stock.getVariation());
            }

            double newPrice = (stock.getPrice() * stock.getVariation() / 100.0) + stock.getPrice();

            if ( newPrice > 0 ) {
                stock.setPrice(newPrice);
            }
        }

        @Override
        public void run() {
            Random random = new Random();
            random.ints(10, 0, stocks.size()).parallel().forEach(i -> updateStockPrice(stocks.get(i)));
        }
    }


}
