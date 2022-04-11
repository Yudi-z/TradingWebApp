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

    public static String getName(String Ticker) {
        if(Ticker==null) {
            return null;
        }
        try{
            Stock stock = YahooFinance.get(Ticker);
            return stock.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Double>getStockP(String Ticker){
        ArrayList<Double> prices=new ArrayList<>();
        List<HistoricalQuote> stockHistQuotes = getStock(Ticker);
        for (int i=0;i<stockHistQuotes.size();i++) {
            Double adjClose = stockHistQuotes.get(i).getAdjClose().doubleValue();
            prices.add(adjClose);

        }
        return prices;
    }
    public static ArrayList<Double>getReturns(String Ticker){
        ArrayList<Double> daily_yield = new ArrayList<>();
        ArrayList<Double> prices = getStockP(Ticker);
        daily_yield.add((double) 0);
        for (int i = 1; i < prices.size(); i++) {
            double this_close = prices.get(i);
            double prev_close = prices.get(i - 1);
            double yield = (this_close - prev_close) / prev_close;
            daily_yield.add(yield);
        }
        return daily_yield;
    }

    public static ArrayList<String>getDate(String Ticker){
        ArrayList<String> dates=new ArrayList<>();
        List<HistoricalQuote> stockHistQuotes = getStock(Ticker);
        for (int i=0;i<stockHistQuotes.size();i++) {
            Timestamp times = new Timestamp(stockHistQuotes.get(i).getDate().getTimeInMillis());
            String time=times.toString();
            dates.add(time);
        }
        return dates;
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
           //System.out.println(getReturns("GOOG").get(i));
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

    public static double getRf(Calendar start, Calendar end) {
        List<HistoricalQuote> rf_quote_list = getStock("^TNX", start, end);
        List<Double> rf_list = new ArrayList<>();
        BigDecimal temp = new BigDecimal(0);
        for (HistoricalQuote rf_quote : rf_quote_list) {
            if (rf_quote.getAdjClose() == null) {
                rf_list.add(temp.doubleValue());
            }else {
                rf_list.add(rf_quote.getAdjClose().doubleValue());
                temp = rf_quote.getAdjClose();
            }
        }
        double Rf = mean(rf_list)/100;
        return Rf;
    }

    public static double getRf(Calendar start) {
        return getRf(start, Calendar.getInstance());
    }
}
