package ru.podelochki.otus.homework16.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/resources/*", "/gwt/*" })
public class StaticResourcesServlet extends HttpServlet {
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println(request.getRequestURI());
        String address = request.getRequestURI().replaceAll(request.getContextPath(), "");
        InputStream is = getClass().getResourceAsStream(address);
        OutputStream os = response.getOutputStream();
        int count;
        byte[] b = new byte[1024];
        while ((count = is.read(b)) >0 ) {
        	os.write(b, 0, count);
        }
        is.close();
        os.flush();
    }
}
