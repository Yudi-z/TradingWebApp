package com.ibm.security.appscan.altoromutual.model;

import com.ibm.security.appscan.altoromutual.util.DBUtil;

import java.util.HashMap;
import java.util.List;

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
      } else {
        return true;
      }
    }
    return false;
  }

  public double getTotalCurrentValue() {
    double sum=0;
    for(int i=0; i< positions.size();i++) {
      sum += positions.get(i).getCurrentValue();
    }
    return sum+cash_balance;
  }

  public double getTotalCost() {
    double sum=0;
    for(int i=0; i< positions.size();i++) {
      sum += positions.get(i).getCost();
    }
    return sum;
  }

  public double getUnrealizedProfit() {
    return getTotalCurrentValue() - getTotalCost();
  }

  public HashMap<String,Position> getPositions() {
    return this.positions;
  }

  public List<Position> getPositionList() {
    List<Position> positionList = null;
    for(Position p: positions.values()){
      positionList.add(p);
    }
    return positionList;
  }



}
