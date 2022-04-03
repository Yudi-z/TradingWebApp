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
            <div class="fl" style="width: 99%;">

                <%
                    com.ibm.security.appscan.altoromutual.model.User user = (com.ibm.security.appscan.altoromutual.model.User)request.getSession().getAttribute("user");
                %>

                <h1>Hello <%= user.getFirstName() + " " + user.getLastName() %>
                </h1>


</div>

<jsp:include page="/footer.jspf"/>