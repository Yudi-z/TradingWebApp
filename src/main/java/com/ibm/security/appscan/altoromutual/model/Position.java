package com.ibm.security.appscan.altoromutual.model;

import com.ibm.security.appscan.altoromutual.util.yahooUtil;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ibm.security.appscan.altoromutual.util.yahooUtil.getStock;

public class Position {
    private String ticker = null;
    private String name = null;
    private int num_share = 0;
    private double avg_cost_per_share = 0;
    private Calendar start;
    private static final int ANNUAL_TRADE_DATE = 252;

    public Position(String ticker, int num_share, double price) {
        this.ticker = ticker;
        this.name = yahooUtil.getName(ticker);
        this.num_share = num_share;
        this.avg_cost_per_share = price;
        start = Calendar.getInstance();
        start.add(Calendar.YEAR,-1);
    }

    public Position(String ticker, int num_share, double price, Calendar start) {
        this.ticker = ticker;
        this.name = yahooUtil.getName(ticker);
        this.num_share = num_share;
        this.avg_cost_per_share = price;
        this.start = start;
    }

    public double setAvgCost(double cost, int shares) {
        num_share += shares;
        double total = getCost() + cost * shares;
        if(num_share>0) {
            avg_cost_per_share = total / num_share;
        }

        return avg_cost_per_share;
    }

    public double getPrice() {
        return this.avg_cost_per_share;
    }

    public int getShares() {
        return this.num_share;
    }

    public double getCost() {
        return num_share * avg_cost_per_share;
    }

    public double getCurrentValue() {
        return yahooUtil.getStockPrice(ticker) * num_share;
    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {return name;}

    /**
     * Helper method for getDailyYield()
     *
     * @param historicalQuoteList from yahooUtil.getStock()
     * @return daily yield list
     */
    private List<Double> getDailyYieldFromQuoteList(List<HistoricalQuote> historicalQuoteList) {
        List<Double> daily_yield = new ArrayList<>();
        daily_yield.add((double) 0);
        for (int i = 1; i < historicalQuoteList.size(); i++) {
            double this_close = historicalQuoteList.get(i).getAdjClose().doubleValue();
            double prev_close = historicalQuoteList.get(i - 1).getAdjClose().doubleValue();
            double yield = (this_close - prev_close) / prev_close;
            daily_yield.add(yield);
        }
        return daily_yield;
    }

    /**
     * Get daily yield in the interval with the ticker for this position
     *
     * @param from start date of calculation, determined by the portfolio start date
     * @param to   end date of calculation
     * @return daily yield in a list
     */
    public List<Double> getDailyYield(Calendar from, Calendar to) {

        return getDailyYieldFromQuoteList(yahooUtil.getStock(this.ticker, from, to));
    }

    public static double mean(List<Double> table) {
        double total = 0;
        for (double currentNum : table) {
            total += currentNum;
        }
        return total / (double)table.size();
    }

    /**
     * calculate std for return
     *
     * @param Returns a list of returns generate from getDailyYield()
     * @return standard deviation
     */
    public double getStdev(List<Double> Returns) {
        double mean = mean(Returns);
        double temp = 0;
        for (double val : Returns) {
            double squrDiffToMean = Math.pow(val - mean, 2);
            temp += squrDiffToMean;
        }
        double meanOfDiffs = temp / (double) (Returns.size());
        return Math.sqrt(meanOfDiffs);
    }


    /**
     * get volatility till TODAY
     *
     * @return annualized volatility of this position
     */
    public double getVolatility() {
        List<Double> dailyYield = getDailyYield(start, Calendar.getInstance());
        return Math.sqrt(ANNUAL_TRADE_DATE) * getStdev(dailyYield);
    }

    public Calendar getStart(){
        return start;
    }

}
