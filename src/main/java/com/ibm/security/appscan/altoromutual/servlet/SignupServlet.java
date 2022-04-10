package com.ibm.security.appscan.altoromutual.servlet;

import com.ibm.security.appscan.altoromutual.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet handles site signup operation
 * @author Yudi
 */
public class SignupServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String message = null;
    if (request.getRequestURL().toString().endsWith("addUser")){
      String firstname = request.getParameter("firstname");
      String lastname = request.getParameter("lastname");
      String username = request.getParameter("username");
      String password1 = request.getParameter("password1");
      String password2 = request.getParameter("password2");
      if (username == null || username.trim().length() == 0
          || password1 == null || password1.trim().length() == 0
          || password2 == null || password2.trim().length() == 0)
        message = "An error has occurred. Please try again later.";

      if (firstname == null){
        firstname = "";
      }

      if (lastname == null){
        lastname = "";
      }

      if (message == null && !password1.equals(password2)){
        message = "Entered passwords did not match.";
      }

      if (message == null){
        String error = DBUtil.addUser(username, password1, firstname, lastname);
        error+= DBUtil.addNewAccount(username, "Cash");
        if (error != null)
          message = error;
      }

    }

    if (message != null)
      message = "Error: " + message;
    else
      message = "Requested operation has completed successfully.";

    request.getSession().setAttribute("message", message);
    response.sendRedirect(request.getContextPath()+"/login.jsp");
    return ;
  }
}
