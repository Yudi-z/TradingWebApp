package com.ibm.security.appscan.altoromutual.model;

import com.ibm.security.appscan.altoromutual.util.DBUtil;
import com.ibm.security.appscan.altoromutual.util.yahooUtil;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.*;

import static com.ibm.security.appscan.altoromutual.util.yahooUtil.getStock;
import static com.ibm.security.appscan.altoromutual.util.yahooUtil.mean;

public class Portfolio {
  private String username;
  private double cash_balance;
  private HashMap<String, Position> positions = null;
  boolean init = false;

  public Portfolio(String username, HashMap<String,Position> positions) {
    this.username=username;
    this.positions = positions;
  }

  public boolean contain(Position newPosition) {
    String ticker = newPosition.getTicker();
    if(positions.containsKey(ticker)) {
      return true;
    }
    return false;
  }

  public void add(Position newPosition) {
    String ticker = newPosition.getTicker();
    if (contain(newPosition)) {
      positions.get(ticker).setAvgCost(newPosition.getPrice(), newPosition.getShares());
    } else {
      positions.put(ticker, newPosition);
    }
    // sell: have already validated that there are enough stock to sell
    if(newPosition.getShares()<0) {
      if(positions.get(ticker).getShares()==0) {
        // remove from the list?
      }
    }

    print();
  }

  public void print() {
    int i = 0;
    for(String ticker: positions.keySet()) {
      int shares = positions.get(ticker).getShares();
      System.out.println((i++) +username + " " + ticker + " " + shares);
    }
  }

  public boolean notEnoughStock(Position newPosition) {
    if(newPosition.getShares()>0) {
      if(contain(newPosition)) {
        int sharesOnHand = positions.get(newPosition.getTicker()).getShares();
        if(sharesOnHand < (-newPosition.getShares())){
          return true;
        }
    }

    public boolean notEnoughStock(Position newPosition) {
        if (newPosition.getShares() > 0) {
            if (contain(newPosition)) {
                int sharesOnHand = positions.get(newPosition.getTicker()).getShares();
                if (sharesOnHand < (-newPosition.getShares())) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public double getTotalCurrentValue() {
        double sum = 0;
        for (Map.Entry<String, Position> pos : positions.entrySet()) {
            sum += pos.getValue().getCurrentValue();
        }
        return sum + cash_balance;
    }

    public double getTotalCost() {
        double sum = 0;
        for (Map.Entry<String, Position> pos : positions.entrySet()) {
            sum += pos.getValue().getCost();
        }
        return sum;
    }

    public double getUnrealizedProfit() {
        return getTotalCurrentValue() - getTotalCost();
    }

    public HashMap<String, Position> getPositions() {
        return this.positions;
    }

    public List<Position> getPositionList() {
        List<Position> positionList = new ArrayList<>();
        for (Position p : positions.values()) {
            positionList.add(p);
        }
        return positionList;
    }

    public List<Double> getList(Map<String, Double> map) {
        return new ArrayList<>(map.values());
    }

    /**
     * A map of <b>Ticker</b> to <b>Weight</b> of each position in the portfolio
     * @return a Map
     */
    public Map<String, Double> getWeight() {
        Map<String, Double> weights = new HashMap<>();
        for (Map.Entry<String, Position> pos : positions.entrySet()) {
            weights.put(pos.getKey(), pos.getValue().getCurrentValue() / getTotalCurrentValue());
        }
        return weights;
    }


    public double getCov(Position pos1, Position pos2) {
        // use the later start date of the positions
        Calendar start = pos1.getStart().after(pos2.getStart()) ? pos1.getStart() : pos2.getStart();
        List<Double> X = pos1.getDailyYield(start, Calendar.getInstance());
        List<Double> Y = pos2.getDailyYield(start, Calendar.getInstance());
//        System.out.println(X);
        assert (X.size() == Y.size());
        List<Double> temp = new ArrayList<>();
        double Ex = mean(X);
        double Ey = mean(Y);
//        System.out.println("Ex = " + Ex + " EY = " + Ey);
        for (int i = 0; i < X.size(); i++) {
            temp.add((X.get(i) - Ex) * (Y.get(i) - Ey));
        }
        return mean(temp);
    }


    public Map<String, Double> getAssetVol() {
        Map<String, Double> assetVol = new HashMap<>();
        for (Map.Entry<String, Position> pos : positions.entrySet()) {
            assetVol.put(pos.getKey(), pos.getValue().getVolatility());
        }
        return assetVol;
    }

    public double getPortfolioVol() {
        Map<String, Double> assetVol = getAssetVol();
        double step_1 = 0;
        Map<String, Double> weights = getWeight();

        for (String ticker : assetVol.keySet()) {
            step_1 += Math.pow(assetVol.get(ticker), 2) * Math.pow(weights.get(ticker), 2);
        }
        double step_2 = 0;
        for (Map.Entry<String, Double> w1 : weights.entrySet()) {
            for (Map.Entry<String, Double> w2 : weights.entrySet()) {
                step_2 += w1.getValue() * w2.getValue() * getCov(positions.get(w1.getKey()), positions.get((w2.getKey())));
            }
        }
        return Math.sqrt(step_1 + 2 * step_2);

    }

    public double getROR() {
        return getUnrealizedProfit() / getTotalCost();
    }

    public double sharpe() {
        double s = (getROR() - yahooUtil.getRf(getEarliestDate())) / getPortfolioVol();
        return s;
    }

    public Calendar getEarliestDate() {
        Calendar startDate = Calendar.getInstance();
        for (Position pos : positions.values()) {
            if (pos.getStart().before(startDate)) {
                startDate = pos.getStart();
            }
        }
        return startDate;
    }




}
