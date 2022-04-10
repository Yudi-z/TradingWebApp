package com.ibm.security.appscan.altoromutual.util;

import com.ibm.security.appscan.altoromutual.model.Position;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class DBUtilTest {

    @Test
    void updatePortfolio() {
    }

    @Test
    void addNewPosition() {
        Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR,-1);
        Position posGoogle = new Position("GOOG", 100, 2265.4399);
        DBUtil.addNewPosition(posGoogle,"username");
    }
}