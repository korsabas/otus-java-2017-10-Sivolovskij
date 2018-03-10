package ru.podelochki.otus.socketchat.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ru.podelochki.otus.socketchat.models.AppUser;
import ru.podelochki.otus.socketchat.services.DBService;
import ru.podelochki.otus.socketchat.services.ServiceMessageHandler;
import ru.podelochki.otus.socketchat.services.TemplateProcessor;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    @Autowired
	private DBService dbService;
	
	@Autowired
	private TemplateProcessor processor;
	
	@Autowired
	private ServiceMessageHandler webSocketMessageHandler;
	
	public LoginServlet() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        InputStream is = getClass().getResourceAsStream("/web/login.html");
        OutputStream os = response.getOutputStream();
        int count;
        byte[] b = new byte[1024];
        while ((count = is.read(b)) >0 ) {
        	os.write(b, 0, count);
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
    	AppUser user = dbService.getUserByUsername(request.getParameter("login"));
    	String currentNode = webSocketMessageHandler.getRegisterMessage().getName();
    	user.setActiveNode(currentNode);
    	dbService.updateNode(user);
    	if (user != null) {
    		if (user.getPassword().equals(request.getParameter("password"))) {
    			//request.setAttribute("username", user.getUsername());
    			request.getSession().setAttribute("username", user.getUsername());
    			//activeUsers.setUserForSession(request.getSession(), user);
    			return true;
    		}
    	}
    	return false;
    	
        //return dbService.equalsIgnoreCase(request.getParameter("login")) && PASSWORD.equalsIgnoreCase(request.getParameter("password"));
    }

}
