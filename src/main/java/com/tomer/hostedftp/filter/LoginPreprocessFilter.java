package com.tomer.hostedftp.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.tomer.hostedftp.util.RequestProcess;

@WebFilter("/login")
public class LoginPreprocessFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String body = RequestProcess.getBody(req, res);
		String[] parts = body.split("&");
		for (String part : parts) {
			String[] kvp = part.split("=");
			if (kvp.length == 2) {
				String key = kvp[0];
				String val = kvp[1];
				if (key.equals("username")) {
					req.setAttribute("username", val);
				} else if (key.equals("password")) {
					req.setAttribute("password", val);
				}
			}
		}
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		
	}

}
