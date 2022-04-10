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
    public static ArrayList<Double>getStockPrice(String Ticker){
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
        ArrayList<Double> prices = getStockPrice(Ticker);
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
     * @param args
     */

    public static void main(String[] args) {
        List<HistoricalQuote> stockHistQuotes = getStock("GOOG");
        for (int i=0;i<stockHistQuotes.size();i++) {
            Double adjClose = stockHistQuotes.get(i).getAdjClose().doubleValue();
            Timestamp time = new Timestamp(stockHistQuotes.get(i).getDate().getTimeInMillis());
            String ret = "Time: "+time + ", Stock: "+ "GOOG" + ", Adjusted close price: "+ adjClose;
            System.out.println(ret);
           //System.out.println(getReturns("GOOG").get(i));
        }
//        yahooUtil.getGOOG();
//    System.out.println(google);
    }
}
