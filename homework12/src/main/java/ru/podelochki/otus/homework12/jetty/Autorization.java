package ru.podelochki.otus.homework12.jetty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Autorization extends HttpServlet {
	private static final int BUFFER = 1024;

    public Autorization() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        /*response.getWriter().append("<html><form method='POST' action='/j_security_check'>"
        	      + "<input type='text' name='j_username'/>"
        	      + "<input type='password' name='j_password'/>"
        	      + "<input type='submit' value='Login'/></form></html>");
        	      */
        //URL fileUrl = getClass().getResource("/web/login.html");
        InputStream is = getClass().getResourceAsStream("/web/login.html");
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[BUFFER];
        while (is.read(b) != -1) {
        	os.write(b);
        }
        is.close();
        os.flush();
        os.close();
    }
}
