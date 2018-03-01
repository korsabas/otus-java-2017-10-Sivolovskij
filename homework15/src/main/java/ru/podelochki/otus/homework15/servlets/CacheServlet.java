package ru.podelochki.otus.homework15.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.podelochki.otus.homework15.services.DBService;
import ru.podelochki.otus.homework15.services.TemplateProcessor;

@WebServlet( name="cacheServlet", displayName="Cache Servlet", urlPatterns = {""}, loadOnStartup=1)
public class CacheServlet extends HttpServlet {

	@Autowired
	private DBService dbService;
	
	@Autowired
	private TemplateProcessor processor;
	
	public CacheServlet() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
    	System.out.print(req.getContextPath());
    	response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);        
        Map<String, Object> root = new HashMap<>();
        root.put("result", new Response("1","1","1"));
        Template temp = processor.getCfg().getTemplate("/web/cache.html");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
        try {
			temp.process(root, out);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        out.close();
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
