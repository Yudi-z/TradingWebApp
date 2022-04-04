<%--
  Created by IntelliJ IDEA.
  User: Yudi
  Date: 4/3/22
  Time: 2:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="/header.jspf"/>

<div id="wrapper" style="width: 99%;">
    <jsp:include page="membertoc.jspf"/>
        <td valign="top" colspan="3" class="bb">
            <%@page import="com.ibm.security.appscan.altoromutual.model.Account"%>
            <%@page import="com.ibm.security.appscan.altoromutual.util.DBUtil"%>
            <div class="fl" style="width: 99%;">
<%--                <jsp:include page="port_sidebar.jspf.jspf"/>--%>

                <%
                    com.ibm.security.appscan.altoromutual.model.User user = (com.ibm.security.appscan.altoromutual.model.User)request.getSession().getAttribute("user");
                %>

        <h3>Watch List</h3>
        <form method="post" name="stockForm" action="addStock" id="stockForm" >
            Enter Ticker Symbol: <input type="text" name="ticker">
            <input type="submit" name="addButton" value="Add">
        </form>


        <td valign="top" class="cc br bb">
            <br style="line-height: 10px;"/>
            <b> <%= user.getFirstName() + " " + user.getLastName() %> 's Portfolio </b>
            <table cellspacing="0" width="100%">
                <tr>
                    <th>Ticker Symbol</th>
                    <th>Shares Held</th>
                    <th>Price</th>
                </tr>
                <tr>
                    <td>FB</td>
                    <td>30</td>
                    <td>221.82</td>
                </tr>

            </table>

            <b>Account Balance: </b>
            <form>
                <input type="radio" id="buy" name="BUYSELL" value="BUY">
                <label for="buy">BUY</label>
                <input type="radio" id="sell" name="BUYSELL" value="SELL">
                <label for="sell">SELL</label><br>

                <label for="orderType">Order Type:</label>
                <select id="orderType" name="orderType">
                    <option value="market">Market</option>
                    <option value="limit">Limit</option>
                </select><br>

                <label for="share">Share</label>
                <input type="number" id="share" name="share" value="100"><br>

                <input type="submit" value="Review Order">
            </form>

            <table>

            </table>
        </td>




</div>

<jsp:include page="/footer.jspf"/>