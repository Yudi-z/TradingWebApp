<%--
  Created by IntelliJ IDEA.
  User: fredpeng
  Date: 2022/04/11
  Time: 0:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.jfree.chart.servlet.ServletUtilities" %>
<%@ page import="com.ibm.security.appscan.altoromutual.api.ChartAPI" %>
<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="utf-8"%>



<div id="wrapper" style="width: 99%;">
  <jsp:include page="/bank/membertoc.jspf"/>
  <td valign="top" colspan="3" class="bb">
    <div class="fl" style="width: 99%;">
      <%
        String symbol = request.getParameter("symbol");
        String priceFileName= null;
        String returnFileName= null;
        String autoCorrFileName= null;
        String histFileName= null;
        String cumRetFileName= null;
        String dailyPctFileName= null;
        String CAPMFileName= null;
        try {
//          priceFileName = ServletUtilities.saveChartAsPNG(ChartAPI.getPricePlot(symbol), 600, 400, null);
//          System.out.println(priceFileName);
//          System.out.println("hek");

        } catch (Exception e) {
          e.printStackTrace();
        }
      %>
      <h1> Charts</h1>
      <form method="post" name="chart" action="chart" id="chart">
        <input type="submit" value="Back to Chart">
      </form>
      <img src="../images/priceplot.png" alt="API exceeds limit" width="800" height="300" />
      <img src="../images/PriceCompplot.png" class="w3-round" style="height:300px;width:600px" alt="Avatar">
      <img src="../images/returnplot.png" class="w3-round" style="height:300px;width:600px" alt="Avatar">
      <img src="../images/prevVScurrReturnplot.png" class="w3-round" style="height:300px;width:600px" alt="Avatar">
      <img src="../images/Histplot.png" class="w3-round" style="height:300px;width:600px" alt="Avatar">
      <img src="../images/RetCompplot.png" class="w3-round" style="height:300px;width:600px" alt="Avatar">
      <img src="../images/ScatterCompRetplot.png" class="w3-round" style="height:300px;width:600px" alt="Avatar">
      <img src="../images/ComCumRetplot.png" class="w3-round" style="height:300px;width:600px" alt="Avatar">

    </div>
  </td>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
