<%--
  Created by IntelliJ IDEA.
  User: fredpeng
  Date: 2022/04/11
  Time: 0:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jspf"/>
<div id="wrapper" style="width: 99%;">
    <jsp:include page="bank/membertoc.jspf"/>
    <td valign="top" colspan="3" class="bb">
        <div class="fl" style="width: 99%;">
            <h1>Historical Charts</h1>
            <form method="post" name="chart" action="chartDetails.jsp" id="chart">
                <label for="symbol">Input Symbol:</label>
                <input type="text" id="symbol" name="symbol"><br><br>
                <input type="submit" value="Submit">
            </form>
        </div>
    </td>
</div>


<jsp:include page="footer.jspf"/>

