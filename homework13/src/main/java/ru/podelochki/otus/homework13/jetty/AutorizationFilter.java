package ru.podelochki.otus.homework13.jetty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebFilter("/*")
public class AutorizationFilter implements Filter  {
	private static final int BUFFER = 1024;
	private FilterConfig filterConfig;
	private ServletContext context;
	
	public void init(FilterConfig filterConfig) {
		this.context = filterConfig.getServletContext();
		this.context.log("RequestLoggingFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("hello");
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getRequestURI();
        if (path.startsWith(context.getContextPath() + "/login")) chain.doFilter(request, response); else
            if (!isAuthorized(request)) response.sendRedirect(context.getContextPath() + "/login");
            else chain.doFilter(request, response);
    }
	private boolean isAuthorized(HttpServletRequest httpRequest) {
        return Objects.equals(httpRequest.getSession().getAttribute("authorized"), true);
    }

	@Override
	public void destroy() {
		Filter.super.destroy();
	}
	
}
