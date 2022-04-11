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
          priceFileName = ServletUtilities.saveChartAsPNG(ChartAPI.getPricePlot(symbol), 600, 400, null);
          System.out.println(priceFileName);
          System.out.println("hek");

        } catch (Exception e) {
          e.printStackTrace();
        }
      %>
      <h1><%=symbol%> Historical Charts</h1>
      <form method="post" name="chart" action="chart" id="chart">
        <input type="submit" value="Back to Chart">
      </form>
      <img src="DisplayChart?filename=<%=priceFileName %>" alt="API exceeds limit"
           width="400" height="250" />

    </div>
  </td>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
