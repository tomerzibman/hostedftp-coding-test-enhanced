package com.tomer.hostedftp.filter;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;


public class ExceptionHandlingFilter implements Filter {
	private static final Logger logger = Logger.getLogger(ExceptionHandlingFilter.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception exception) {
			logger.severe("Exception occured: " + exception.getMessage());
		}
		
	}

	@Override
	public void destroy() {
		
	}

}
