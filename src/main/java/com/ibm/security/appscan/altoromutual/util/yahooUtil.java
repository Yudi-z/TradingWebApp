package com.ibm.security.appscan.altoromutual.util;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
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
        from.add(Calendar.YEAR, -5); // from 5 years ago

        try {
            Stock stock = YahooFinance.get(Ticker);
            List<HistoricalQuote> googleHistQuotes = stock.getHistory(from, to, Interval.DAILY);
//            System.out.println(stock.getQuote());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
