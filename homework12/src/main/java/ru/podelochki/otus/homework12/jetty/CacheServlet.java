package ru.podelochki.otus.homework12.jetty;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.podelochki.otus.homework12.services.DBService;

public class CacheServlet extends HttpServlet {


	private DBService dbService;

    public CacheServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        
        //URL fileUrl = getClass().getResourceAsStream("/web/cache.html");
        //FileInputStream is = new FileInputStream(new File(fileUrl.getFile()));
        BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/web/cache.html")));
        String line = null;
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
        StringBuilder responseData = new StringBuilder();
        while((line = in.readLine()) != null) {
        	line = line.replaceAll("totalvalue", String.valueOf(dbService.getHitCount() + dbService.getMissCount()));
        	line = line.replaceAll("hitvalue", String.valueOf(dbService.getHitCount()));
        	line = line.replaceAll("missvalue", String.valueOf(dbService.getMissCount()));
            responseData.append(line);
            out.write(line);
        }
        in.close();
        out.close();
        //OutputStream os = response.getOutputStream();
    }
}
