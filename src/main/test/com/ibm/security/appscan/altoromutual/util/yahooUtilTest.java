package com.ibm.security.appscan.altoromutual.util;

import org.junit.jupiter.api.Test;
import yahoofinance.Stock;
import yahoofinance.histquotes.HistoricalQuote;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class yahooUtilTest {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void getStock() {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.set(2022, Calendar.JANUARY, 1);
        to.set(2022, Calendar.JANUARY, 31);
        List<HistoricalQuote> list = yahooUtil.getStock("GOOG", from, to);
        for (HistoricalQuote quote : list) {
            System.out.println(format.format(quote.getDate().getTime()) + " " + quote.getAdjClose());
        }
    }

    @Test
    void getStock5Year() {
        List<HistoricalQuote> list = yahooUtil.getStock("GOOG");
        int i = 0;
        for (HistoricalQuote quote : list) {
            if (i < 10 || i > 1000) {
                System.out.println(format.format(quote.getDate().getTime()) + " " + quote.getAdjClose());
            }
            i++;
        }
    }
}