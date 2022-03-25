<%@ page import="com.ibm.security.appscan.altoromutual.util.ServletUtil" %><%--
  Created by IntelliJ IDEA.
  User: Yudi
  Date: 3/25/22
  Time: 2:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/header.jspf"/>
<html>
<head>
    <title>Create a new account</title>
</head>
<body>
<!-- action="addUser" -->
<form method="post" name="addUser" action="<%=ServletUtil.getAppProperty("enableAdminFunctions").equalsIgnoreCase("true")?"addUser":"" %>" id="addUser" onsubmit="return confirmpass(this);">
    <tr>
        <td colspan="4"><h2><br><br>Add an new user</h2></td>
    </tr>
    <tr>
        <th>
            First Name:
            <br>
            Last Name:
        </th>
        <th>
            Username:
        </th>
        <th>
            Password:
            <br>
            Confirm:
        </th>
        <th>
            &nbsp;</th>
    </tr>
    <tr>
        <td>
            <input type="text" name="firstname">
            <br>
            <input type="text" name="lastname">
        </td>
        <td>
            <input type="text" name="username">
        </td>
        <td>
            <input type="password" name="password1">
            <br>
            <input type="password" name="password2">
        </td>
        <td>
            <input type="submit" name="add" value="Add User">
        </td>
    </tr>
    <tr>
        <td colspan="4">It is highly recommended that you leave the username as first
            initial last name.
        </td>
    </tr>
</form>
</body>
</html>

<jsp:include page="/footer.jspf"/>