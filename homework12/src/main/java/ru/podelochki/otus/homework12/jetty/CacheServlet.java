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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.podelochki.otus.homework12.services.DBService;

public class CacheServlet extends HttpServlet {


	private DBService dbService;
	private Configuration cfg;
    public CacheServlet(DBService dbService, Configuration cfg) {
        this.dbService = dbService;
        this.cfg = cfg;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        
        //URL fileUrl = getClass().getResourceAsStream("/web/cache.html");
        //FileInputStream is = new FileInputStream(new File(fileUrl.getFile()));
        
        Map<String, Object> root = new HashMap<>();
        root.put("result", new Response(String.valueOf(dbService.getHitCount() + dbService.getMissCount()),
        		String.valueOf(dbService.getHitCount()), String.valueOf(dbService.getMissCount())));
        Template temp = cfg.getTemplate("/web/cache.html");

        //BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/web/cache.html")));
        //String line = null;
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
        //StringBuilder responseData = new StringBuilder();
        /*while((line = in.readLine()) != null) {
        	line = line.replaceAll("totalvalue", String.valueOf(dbService.getHitCount() + dbService.getMissCount()));
        	line = line.replaceAll("hitvalue", String.valueOf(dbService.getHitCount()));
        	line = line.replaceAll("missvalue", String.valueOf(dbService.getMissCount()));
            responseData.append(line);
            out.write(line);
        }
        in.close();
        */
        try {
			temp.process(root, out);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        out.close();
        //OutputStream os = response.getOutputStream();
    }
    
    public class Response {
    	private String totalValue;
    	private String hitValue;
    	private String missValue;
    	
    	public Response() {
    		
    	}
    	
    	public Response(String totalValue, String hitValue, String missValue) {
    		this.totalValue = totalValue;
    		this.hitValue = hitValue;
    		this.missValue = missValue;
    	}

		public String getTotalValue() {
			return totalValue;
		}

		public void setTotalValue(String totalValue) {
			this.totalValue = totalValue;
		}

		public String getHitValue() {
			return hitValue;
		}

		public void setHitValue(String hitValue) {
			this.hitValue = hitValue;
		}

		public String getMissValue() {
			return missValue;
		}

		public void setMissValue(String missValue) {
			this.missValue = missValue;
		}
    	
    }
   
}
