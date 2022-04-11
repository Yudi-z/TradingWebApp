package com.ibm.security.appscan.altoromutual.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class PositionTest {

    @Test
    void getVolatility() {
        Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR,-1);
        Position posGoogle = new Position("GOOG", 100, 2265.4399, lastYear);
        System.out.println(posGoogle.getVolatility());
    }

    @Test
    void setAvgCost() {
    }

    @Test
    void getPrice() {
    }

    @Test
    void getShares() {
    }

    @Test
    void getCost() {
    }

    @Test
    void getCurrentValue() {
    }

    @Test
    void getTicker() {
    }

    @Test
    void getDailyYield() {
    }

    @Test
    void mean() {
        ArrayList<Double> list = new ArrayList<Double>();
        for(int i =1; i <=5; i++){
            list.add((double)i);
        }
        System.out.println(Position.mean(list));
    }

    @Test
    void getStdev() {
    }

    @Test
    void getStart() {
    }
}