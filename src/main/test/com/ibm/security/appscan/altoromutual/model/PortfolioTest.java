package com.ibm.security.appscan.altoromutual.model;

import com.ibm.security.appscan.altoromutual.util.yahooUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioTest {
    Portfolio portfolio;

    @BeforeEach
    void initTest() {
        HashMap<String, Position> positions = new HashMap<>();
        Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        positions.put("GOOG", new Position("GOOG", 100, 2265.4399, lastYear));
        positions.put("TSLA", new Position("TSLA", 100, 677.02, lastYear));
//        positions.put("^GSPC", new Position("^GSPC", 100, 4127.99, lastYear));
        this.portfolio = new Portfolio("username", positions);
    }


    @Test
    void getWeight() {
        Map<String, Double> weights = this.portfolio.getWeight();
        assert weights.get("GOOG") == yahooUtil.getStockPrice("GOOG") / (yahooUtil.getStockPrice("GOOG") + yahooUtil.getStockPrice("TSLA"));
    }

    @Test
    void sharpeRatio() {
        System.out.println(portfolio.sharpe());
    }

    @Test
    void contain() {
    }

    @Test
    void add() {
    }

    @Test
    void print() {
        this.portfolio.print();
    }

    @Test
    void notEnoughStock() {
    }

    @Test
    void getTotalCurrentValue() {
        System.out.println("Current value = " + portfolio.getTotalCurrentValue());
    }

    @Test
    void getTotalCost() {
        System.out.println("Cost = " + portfolio.getTotalCost());
    }

    @Test
    void getUnrealizedProfit() {
        System.out.println("Profit = " + portfolio.getUnrealizedProfit());
    }

    @Test
    void getPositions() {
    }

    @Test
    void getPositionList() {
        List<Position> positionList = portfolio.getPositionList();
        for (Position pos: positionList){
            System.out.println(pos.getTicker());
        }
    }

    @Test
    void getList() {
    }

    @Test
    void getCov() {
        System.out.println(portfolio.getCov(portfolio.getPositionList().get(0), portfolio.getPositionList().get(0)));
    }

    @Test
    void getAssetVol() {
        System.out.println(portfolio.getAssetVol());
    }

    @Test
    void getPortfolioVol() {
        System.out.println(portfolio.getPortfolioVol());
    }

    @Test
    void getROR() {
        System.out.println("ROR = " + this.portfolio.getROR());
    }

    @Test
    void mean(){

    }

    @Test
    void sharpe() {
    }

    @Test
    void getEarliestDate() {
    }
}