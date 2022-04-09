package com.ibm.security.appscan.altoromutual.model;

import com.ibm.security.appscan.altoromutual.util.yahooUtil;

public class Position {
  private String ticker = null;
  private String name = null;
  private int num_share = 0;
  private double avg_cost_per_share = 0;

  public Position(String ticker, int num_share, double price) {
    this.ticker = ticker;
//    this.name = name;
    this.num_share = num_share;
    this.avg_cost_per_share = price;
  }

  public double setAvgCost(double cost, int shares) {
    num_share+=shares;
    double total = getCost() + cost * shares;
    avg_cost_per_share = total / num_share;
    return avg_cost_per_share;
  }

  public double getPrice() {return this.avg_cost_per_share;}

  public int getShares() {return this.num_share;}

  public double getCost(){
    return num_share * avg_cost_per_share;
  }

  public double getCurrentValue() {
    return yahooUtil.getStockPrice(ticker) * num_share;
  }

  public String getTicker() {
    return ticker;
  }
}
