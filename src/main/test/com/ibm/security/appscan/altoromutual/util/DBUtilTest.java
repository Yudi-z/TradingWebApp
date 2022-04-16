package com.ibm.security.appscan.altoromutual.util;

import com.ibm.security.appscan.altoromutual.model.Portfolio;
import com.ibm.security.appscan.altoromutual.model.Position;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class DBUtilTest {

    @Test
    void updatePortfolio() {
    }

    @Test
    void addNewPosition() {
        Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        Position posGoogle = new Position("GOOG", -100, 2265.4399);
        Position posTesla = new Position("TSLA", -100, 677.02);
        Position posNIO = new Position("NIO", -200, 38.12);
        Position posApple = new Position("AAPL", -300, 129.59);
        DBUtil.addNewPosition(posGoogle, "un");
        DBUtil.addNewPosition(posTesla, "un");
        DBUtil.addNewPosition(posNIO, "un");
        DBUtil.addNewPosition(posApple, "un");
    }

    @Test
    void sharpe() {
        try {
            Portfolio portfolio = DBUtil.getPortfolio("un");
            System.out.println(portfolio.sharpe());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}