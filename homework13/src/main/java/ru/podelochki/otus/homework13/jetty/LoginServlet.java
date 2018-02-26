package ru.podelochki.otus.homework13.jetty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final String LOGIN = "admin";
    private static final String PASSWORD = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        InputStream is = getClass().getResourceAsStream("/web/login.html");
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[1024];
        while (is.read(b) != -1) {
        	os.write(b);
        }
        is.close();
        os.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if (isCorrectCredentials(request)) {
            request.getSession().setAttribute("authorized", true);
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
    private boolean isCorrectCredentials(HttpServletRequest request) {
        return LOGIN.equalsIgnoreCase(request.getParameter("login")) && PASSWORD.equalsIgnoreCase(request.getParameter("password"));
    }

}
