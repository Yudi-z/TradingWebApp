<%--
  Created by IntelliJ IDEA.
  User: Yudi
  Date: 4/11/22
  Time: 3:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/header.jspf"/>
<div id="wrapper" style="width: 99%;">
    <jsp:include page="/bank/membertoc.jspf"/>
    <td valign="top" colspan="3" class="bb">
        <%@page import="com.ibm.security.appscan.altoromutual.util.ServletUtil"%>
        <%@ page import="java.util.List" %>
        <%@ page import="com.ibm.security.appscan.altoromutual.model.Position" %>
        <%@ page import="com.ibm.security.appscan.altoromutual.util.DBUtil" %>
        <%@ page import="com.ibm.security.appscan.altoromutual.model.Portfolio" %>
        <%@ page import="java.util.HashMap" %>


        <!-- Be careful what you change.  All changes are made directly to AltoroJ database. -->
        <div class="fl" style="width: 99%;">
            <p><span style="color:#FF0066;font-size:12pt;font-weight:bold;">
		<%
            java.lang.String error = (String)request.getSession().getAttribute("message");

            if (error != null && error.trim().length() > 0){
                out.print(error);
            }
        %>
		</span></p>

            <h1>Summary Report</h1>

            <table width="100%" border="0">

                <hr>
                <!-- users position summary-->
                <form>
                <tr>
                    <td colspan="4"><h2><br><br>All users' position summary</h2></td>
                </tr>
                <tr>
                    <th>Ticker Symbol</th>
                    <th>Name</th>
                    <th>Shares Held</th>
                    <th>Price</th>
                </tr>
                <%
                    List<Position> positionList = DBUtil.getAllPosition();
                    System.out.println("size of all the positions: " + positionList.size());
                    for(Position p: positionList) {
                        System.err.println(p.getTicker());
                %>
                <tr>
                    <td><%=p.getTicker()%></td>
                    <td><%=p.getName()%></td>
                    <td><%=(-p.getShares())%></td>
                    <td><%=p.getPrice()%></td>
                </tr>
                <%
                    }
                %>

                <%
                    HashMap<String, Position> positionHashMap = new HashMap<>();
                    for(Position p :positionList){
                        positionHashMap.put(p.getTicker(),p);
                    }
                    Portfolio portfolio = new Portfolio("admin",positionHashMap);
                %>
                    Sharpe ratio of all users is <%=portfolio.sharpe()%>

                <tr>
                    <td colspan="4"><h2><br><br>Today's order summary</h2></td>
                </tr>
                </form>

                <!--  today's summary-->
                <form>
                <tr>
                    <th>Ticker Symbol</th>
                    <th>Name</th>
                    <th>Shares bought</th>
                    <th>Shares sold</th>
                </tr>
                <%
                    List<Position> buyList = DBUtil.getTodayBUY();
                    System.out.println("size of all buys: " + buyList.size());
                    for(int i=0;i<buyList.size()-1;i++) {
                      Position p = buyList.get(i);
                      if(p.getTicker().equals(buyList.get(i+1).getTicker())) { // have both buy and sell
                %>
                <tr>
                    <td><%=p.getTicker()%></td>
                    <td><%=p.getName()%></td>
                    <td><%=(-p.getShares())%></td>
                    <td><%=buyList.get(i+1).getShares()%></td>
                </tr>

                <%
                      i++;
                      }else {
                            if(p.getShares()<0) {  // have only buy
                %>

                <tr>
                    <td><%=p.getTicker()%></td>
                    <td><%=p.getName()%></td>
                    <td><%=(-p.getShares())%></td>
                    <td><%=0%></td>
                </tr>

                <%
                            }else {
                %>

                <tr>
                    <td><%=p.getTicker()%></td>
                    <td><%=p.getName()%></td>
                    <td><%=0%></td>
                    <td><%=p.getShares()%></td>
                </tr>

                <%
                            }
                        }
                    }
                %>
                </form>

            </table>
        </div>
    </td>
</div>

<jsp:include page="/footer.jspf"/>