package com.ibm.security.appscan.altoromutual.util;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class yahooUtil {

    public static void getGOOG() {
        try {
            Stock google = YahooFinance.get("GOOG");
            System.out.println(google.getQuote());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<HistoricalQuote> getStock(String Ticker) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -5); // from 5 years ago

        return getStock(Ticker, from, to);
    }

    /**
     * @param Ticker Stock ticker on Yahoo Finance
     * @param from   inclusive start date
     * @param to     inclusive end date
     * @return HistoricalQuote from yahoo
     */
    public static List<HistoricalQuote> getStock(String Ticker, Calendar from, Calendar to) {
        List<HistoricalQuote> stockHistQuotes = null;
        try {
            Stock stock = YahooFinance.get(Ticker);
            stockHistQuotes = stock.getHistory(from, to, Interval.DAILY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stockHistQuotes;
    }

    public static double getStockPrice(String Ticker) {
        if (Ticker == null) {
            return -1;
        }
        try {
            Stock stock = YahooFinance.get(Ticker);
            double price = stock.getQuote().getPrice().doubleValue();
            return price;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Stock get(String Ticker) {
        if (Ticker == null) {
            return null;
        }
        try {
            Stock stock = YahooFinance.get(Ticker);
            return stock;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    /**
     * Only for testing usage
     *
     * @param args
     */
    public static void main(String[] args) {
        Stock google = get("GOOG");
        System.out.println(google.getName());
        google.print();
//        double googlePirce = getStockPrice("GOOG");
//        System.out.println("google's price: " + googlePirce);
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

    public static double mean(List<Double> table) {
        double total = 0;
        for (double currentNum : table) {
            total += currentNum;
        }
        return total / table.size();
    }

    public static double getRf(Calendar start, Calendar end){
        List<HistoricalQuote> rf_quote_list = getStock("^TNX", start, end);
        List<Double> rf_list = new ArrayList<>();
        for (HistoricalQuote rf_quote:rf_quote_list) {
            rf_list.add(rf_quote.getAdjClose().doubleValue());
        }
        double Rf = mean(rf_list);
        return Rf;
    }

    public static double getRf(Calendar start){
        return getRf(start, Calendar.getInstance());
    }
}
