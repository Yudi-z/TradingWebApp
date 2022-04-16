package com.ibm.security.appscan.altoromutual.servlet;
import com.ibm.security.appscan.altoromutual.api.ChartAPI;
import com.ibm.security.appscan.altoromutual.util.DBUtil;
import com.ibm.security.appscan.altoromutual.util.OperationsUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

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

    }



    else if(request.getRequestURL().toString().endsWith("searchStock")){
      String ticker = request.getParameter("tickr");
      if(ticker == null) {
        message = "An error has occured for ticker. Please try again.";
      }
      try {
//        System.out.println(ticker);
//        String priceFileName= null;
//        String returnFileName= null;
//        String autoCorrFileName= null;
//        String histFileName= null;
//        String cumRetFileName= null;
//        String dailyPctFileName= null;
//        String CAPMFileName= null;
//        priceFileName = ServletUtilities.saveChartAsPNG(ChartAPI.getPricePlot(ticker), 800, 300, null);
//        request.setAttribute("priceFileName",priceFileName);
//        RequestDispatcher rd=request.getRequestDispatcher("trade.jsp");
//        rd.forward(request, response);
//        ChartUtils.saveChartAsPNG(new File("../../../ideaProjects/fintech-512-bigbucks/src/main/webapp/images/priceplot.png"),ChartAPI.getPricePlot(ticker) , 800, 300);
//        ChartUtils.saveChartAsPNG(new File("../../../ideaProjects/fintech-512-bigbucks/src/main/webapp/images/PriceCompplot.png"),ChartAPI.getCompPricePlot(ticker) , 800, 300);
//        ChartUtils.saveChartAsPNG(new File("../../../ideaProjects/fintech-512-bigbucks/src/main/webapp/images/ComCumRetplot.png"),ChartAPI.getComCumRetAndStkCumRetPlot(ticker) , 800, 300);
//        ChartUtils.saveChartAsPNG(new File("../../../ideaProjects/fintech-512-bigbucks/src/main/webapp/images/Histplot.png"),ChartAPI.getHistPlot(ticker) , 800, 300);
//        ChartUtils.saveChartAsPNG(new File("../../../ideaProjects/fintech-512-bigbucks/src/main/webapp/images/ScatterCompRetplot.png"),ChartAPI.getScatterCompRetPlot(ticker) , 800, 300);
//        ChartUtils.saveChartAsPNG(new File("../../../ideaProjects/fintech-512-bigbucks/src/main/webapp/images/returnplot.png"),ChartAPI.getReturnsPlot(ticker) , 800, 300);
//        ChartUtils.saveChartAsPNG(new File("../../../ideaProjects/fintech-512-bigbucks/src/main/webapp/images/prevVScurrReturnplot.png"),ChartAPI.getPrevCurrPlot(ticker) , 800, 300);
//        ChartUtils.saveChartAsPNG(new File("../../../ideaProjects/fintech-512-bigbucks/src/main/webapp/images/RetCompplot.png"),ChartAPI.getCompRetPlot(ticker) , 800, 300);
        JFreeChart chart = null;
        String chartType = String.valueOf(request.getParameter("chartSelection"));

        switch(chartType){
          case "1":
            chart=ChartAPI.getPricePlot(ticker);
            ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 300);
            break;
          case "2":
            chart=ChartAPI.getReturnsPlot(ticker);
            ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 300);
          case "3":
            chart=ChartAPI.getPrevCurrPlot(ticker);
            ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 300);
          case "4":
            chart=ChartAPI.getHistPlot(ticker);
            ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 300);

          case "5":
            chart=ChartAPI.getCompPricePlot(ticker);
            ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 300);
          case "6":
            chart=ChartAPI.getCompRetPlot(ticker);
            ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 300);
          case "7":
            chart=ChartAPI.getScatterCompRetPlot(ticker);
            ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 300);
          case "8":
            chart=ChartAPI.getComCumRetAndStkCumRetPlot(ticker);
            ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 300);


        }






      } catch (SQLException e) {
        e.printStackTrace();
      } catch (ParseException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }


    }


    else if (request.getRequestURL().toString().endsWith("tradeStock")){
      String action = request.getParameter("action");
      String ticker = request.getParameter("ticker");
      System.out.println("Input Ticker is " +ticker);
      String orderType = request.getParameter("orderType");
      String limit = request.getParameter("lmtPrice");
      int shares = Integer.parseInt(request.getParameter("share"));
      System.out.println("Get to TradeServlet tradeStock");
      System.out.println("action is " + action);
      if(action.equals("BUY")) { //buy: negative shares
        shares = -shares;
        System.out.println("BUY: shares " + shares);
      }else{
        System.out.println("SELL: shares " + shares);
      }

      if(ticker == null) {
        System.out.println("Ticker is null");
        message = "An error has occured for ticker. Please try again.";
      }else
        message = OperationsUtil.doTradeStock(request,ticker,orderType,0.0,shares);
    }

    if (message != null)
     message = "Error: " + message;
    else
     message = "Requested operation has completed successfully.";

    RequestDispatcher dispatcher = request.getRequestDispatcher("trade.jsp");
    request.setAttribute("message", message);
    dispatcher.forward(request, response);
    response.sendRedirect(request.getContextPath()+"/trade.jsp");
    return ;
  }
}
