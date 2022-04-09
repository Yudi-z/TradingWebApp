package com.ibm.security.appscan.altoromutual.model;

import java.util.HashMap;
import java.util.List;

public class Portfolio {
  private double cash_balance;
  private HashMap<String, Position> positions = new HashMap<>();

  public void buy(Position position) {
    if(positions.containsKey(position.getTicker())) {

    }

  }

  public void sell() {

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

  public List<Position> getPositions() {
    List<Position> positionList = null;
    for(Position p: positions.values()){
      positionList.add(p);
    }
    return positionList;
  }

}
