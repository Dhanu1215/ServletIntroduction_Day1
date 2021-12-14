package com.myfirstservlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;


@WebServlet (
        description = "Login Servlet Testing",
        urlPatterns = {"/LoginServlet"},
        initParams = {
                @WebInitParam( name = "user", value = "Dhanashree"),
                @WebInitParam( name = "password", value = "Dhan@12345")
        }
)

public class LoginServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
                //get request parameters for userID and password
                String user = request.getParameter("user");
                String pwd = request.getParameter("pwd");
                //get servlet config init params ,
                String userID = getServletConfig().getInitParameter("user");
                boolean name = Pattern.matches("^[A-Z][a-z]{3,}$",user);
                String password = getServletConfig().getInitParameter("password");
                boolean passWord = Pattern.matches("^[[A-Z1-9a-z]{1,}[@#$%*&]{1}]{8,}$",pwd);
                if ( !name ) {
                        request.getRequestDispatcher("Invalid.jsp").forward(request, response);
                } else if (!passWord ) {
                        request.getRequestDispatcher("ValidPass.jsp").forward(request, response);
                } else if ( userID.equals(user) && password.equals(pwd) ) {
                        request.setAttribute("user", user);
                        request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
                } else {
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                        PrintWriter out = response.getWriter();
                        out.println("<font color=red>Either user name or password is wrong.</font>");
                        rd.include(request, response);
                }
        }
}
