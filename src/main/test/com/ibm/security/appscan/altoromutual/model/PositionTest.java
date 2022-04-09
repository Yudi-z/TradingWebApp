package com.ibm.security.appscan.altoromutual.model;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

class PositionTest {

    @Test
    void getVolatility() {
        Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR,-1);
        Position posGoogle = new Position("GOOG", 100, 2265.4399, lastYear.getTime());
        System.out.println(posGoogle.getVolatility());
    }
}