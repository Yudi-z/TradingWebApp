package com.ibm.security.appscan.altoromutual.servlet;
import com.ibm.security.appscan.altoromutual.util.DBUtil;
import com.ibm.security.appscan.altoromutual.util.OperationsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TradeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String message = null;
    if (request.getRequestURL().toString().endsWith("addStock")){
      String ticker = request.getParameter("ticker");

      if(ticker == null) {
        message = "An error has occured for ticker. Please try again.";
      }
      message += DBUtil.storeStock(ticker);
    }else if (request.getRequestURL().toString().endsWith("tradeStock")){
      String action = request.getParameter("action");
      String ticker = request.getParameter("ticker");
      System.out.println("Input Ticker is " +ticker);
      String orderType = request.getParameter("orderType");
      String limit = request.getParameter("lmtPrice");
      int shares = Integer.parseInt(request.getParameter("share"));
      System.out.println("Get to TradeServlet tradeStock");
      System.out.println("action is " + action);
      if(action.equals("BUY")) {
        shares = -shares;
      }

      if(ticker == null) {
        System.out.println("Ticker is null");
        message = "An error has occured for ticker. Please try again.";
      }
      message += OperationsUtil.doTradeStock(request,ticker,orderType,0.0,shares);
    }

    if (message != null)
     message = "Error: " + message;
    else
     message = "Requested operation has completed successfully.";

    RequestDispatcher dispatcher = request.getRequestDispatcher("transfer.jsp");
    request.setAttribute("message", message);
    dispatcher.forward(request, response);
    response.sendRedirect(request.getContextPath()+"/trade.jsp");
    return ;
  }
}
