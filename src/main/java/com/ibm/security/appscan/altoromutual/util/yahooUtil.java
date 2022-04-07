package com.ibm.security.appscan.altoromutual.util;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class yahooUtil {

    public static void getGOOG(){
        try {
            Stock google = YahooFinance.get("GOOG");
            System.out.println(google.getQuote());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<HistoricalQuote> getStock(String Ticker){
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -5); // from 5 years ago

        try {
            Stock stock = YahooFinance.get(Ticker);
            List<HistoricalQuote> stockHistQuotes = stock.getHistory(from, to, Interval.DAILY);
            return stockHistQuotes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double getStockPrice(String Ticker) {
        try {
            Stock stock = YahooFinance.get(Ticker);
            double price = stock.getQuote().getPrice().doubleValue();
            return price;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Only for testing usage
     * @param args
     */
    public static void main(String[] args) {
        double googlePirce = getStockPrice("GOOG");
        System.out.println("google's price: " + googlePirce);
        /*
        List<HistoricalQuote> stockHistQuotes = getStock("GOOG");
        for (int i=0;i<stockHistQuotes.size();i++) {
            Double adjClose = stockHistQuotes.get(i).getAdjClose().doubleValue();
            Timestamp time = new Timestamp(stockHistQuotes.get(i).getDate().getTimeInMillis());
            String ret = "Time: "+time + ", Stock: "+ "GOOG" + ", Adjusted close price: "+ adjClose;
            System.out.println(ret);
        }
         */
//        yahooUtil.getGOOG();
//    System.out.println(google);
    }
}
