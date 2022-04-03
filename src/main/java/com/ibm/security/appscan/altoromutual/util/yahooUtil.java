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

    public static void getStock(String Ticker){
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -1); // from 5 years ago

        try {
            Stock stock = YahooFinance.get(Ticker);
            List<HistoricalQuote> stockHistQuotes = stock.getHistory(from, to, Interval.MONTHLY);
            for (int i=0;i<stockHistQuotes.size();i++) {
                Double adjClose = stockHistQuotes.get(i).getAdjClose().doubleValue();
                Timestamp time = new Timestamp(stockHistQuotes.get(i).getDate().getTimeInMillis());
                String ret = "Time: "+time + ", Stock: "+ Ticker + ", Adjusted close price: "+ adjClose;
                System.out.println(ret);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getStock("GOOG");
//        yahooUtil.getGOOG();
//    System.out.println(google);
    }
}
