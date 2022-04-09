<%--
  Created by IntelliJ IDEA.
  User: Yudi
  Date: 4/3/22
  Time: 2:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.ibm.security.appscan.altoromutual.util.yahooUtil"%>
<html>
<head>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-blue-grey.css">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body class="w3-theme-l5">
<jsp:include page="/header.jspf"/>

<div id="wrapper" style="width: 99%;">
    <jsp:include page="membertoc.jspf"/>
        <td valign="top" colspan="3" class="bb">
            <%@page import="com.ibm.security.appscan.altoromutual.model.Account"%>
            <%@page import="com.ibm.security.appscan.altoromutual.util.DBUtil"%>
            <%@ page import="com.ibm.security.appscan.altoromutual.util.yahooUtil" %>
            <%--                <jsp:include page="port_sidebar.jspf.jspf"/>--%>

        <%
        com.ibm.security.appscan.altoromutual.model.User user = (com.ibm.security.appscan.altoromutual.model.User)request.getSession().getAttribute("user");
    %>

    <!-- The Grid -->
<%--    <div class="w3-row">--%>
        <!-- Left Column -->
<%--        <div class="w3-col m3">--%>
            <!-- Portfolio card -->
            <div class="w3-card w3-round w3-white">
                <div class="w3-container">
                    <h4 class="w3-center"><%= user.getFirstName() + " " + user.getLastName() %> 's Portfolio</h4>
                    <%--                        <p class="w3-center"><img src="/w3images/avatar3.png" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>--%>
                    <hr>
                    <table cellspacing="0" width="80%">
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
                </div>
            </div>
            <br>
            <!-- buy and sell tab -->
            <div class="w3-card w3-round w3-theme-l1">
                <div class="w3-bar w3-black">
                    <button class="w3-bar-item w3-button tablink w3-blue" onclick="openTrade(event,'BUY')">BUY</button>
                    <button class="w3-bar-item w3-button tablink" onclick="openTrade(event,'SELL')">SELL</button>
                </div>
            </div>

            <div id="BUY" class="w3-container w3-border w3-white trade">
                <h2>BUY</h2>
                <div class="w3-container w3-padding">
                    <form method="post" name="stockForm" id="stockForm" width="80%">
                        Enter Ticker Symbol: <input type="text" name="buy_ticker" id="buy_ticker">
                        <input type="submit" class="w3-button w3-theme" name="addButton" value="Review">
                    </form>
            <%
               java.lang.String buyTicker1 = request.getParameter("buy_ticker");

            %>
                    <p id="Text_stockPrice" >Stock Price for <%=buyTicker1%> is <%=yahooUtil.getStockPrice(buyTicker1)%></p>


                    <label for="orderType">Order Type:</label>
                    <select id="orderType" name="orderType">
                        <option value="market">Market</option>
                        <option value="limit">Limit</option>
                    </select>
                    <br> <br>

                    <label for="lmtPrice">Limit Price</label>
                    <input type="number" step="0.01" id="lmtPrice" name="lmPrice" value="0">
                    <br> <br>

                    <label for="share">Share</label>
                    <input type="number" id="share" name="share" value="100">
                    <br> <br>

                    <input type="submit" class="w3-button w3-theme" value="Execute Order" action="tradeStock">
                </div>
            </div>

            <div id="SELL" class="w3-container w3-border w3-white trade " style="display:none">
                <h2>SELL</h2>

            </div>
            <!-- End Left Column -->
<%--        </div>--%>
        <!-- Middle Column -->

<%--    </div>--%>

    <script>
        function openTrade(evt, name) {
            var i, x, tablinks;
            x = document.getElementsByClassName("trade");
            for (i = 0; i < x.length; i++) {
                x[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablink");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" w3-blue", "");
            }
            document.getElementById(name).style.display = "block";
            evt.currentTarget.className += " w3-blue";
        }
    </script>


        <td valign="top" class="cc br bb">
            <br style="line-height: 10px;"/>

<%--            <b> <%= user.getFirstName() + " " + user.getLastName() %> 's Portfolio </b>--%>

        <div class="w3-row-padding">
            <div class="w3-col m12">
                <!-- WatchList: search for stock BEGINS!-->
                <div class="w3-card w3-round w3-white">
                    <div class="w3-container w3-padding">
                        <h6 class="w3-opacity">Watch List: </h6>
                        <form method="post" name="stockForm" action="addStock" id="stockForm" width="80%">
                            Enter Ticker Symbol: <input type="text" name="ticker">
                            <input type="submit" class="w3-button w3-theme" name="addButton" value="Add">
                        </form>
                    </div>
                <!-- Watch list ENDS! -->
                </div>
                <br>
                <!-- Performance card BEGINS!-->
                <div class="w3-card w3-round w3-white">
                    <div class="w3-container w3-padding">
                        <b>Account Balance: </b>

                        <h6 class="w3-opacity">Social Media template by w3.css</h6>
                        <p contenteditable="true" class="w3-border w3-padding">Status: Feeling Blue</p>
                        <button type="button" class="w3-button w3-theme"><i class="fa fa-pencil"></i> Post</button>
                    </div>
                <!-- Performance card ENDS! -->
                </div>
            </div>
        </div>


        </td>


<jsp:include page="/footer.jspf"/>
</body>
</html>
