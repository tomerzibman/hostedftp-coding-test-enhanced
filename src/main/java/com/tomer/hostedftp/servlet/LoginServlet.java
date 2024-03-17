package com.tomer.hostedftp.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tomer.hostedftp.dao.UserDAO;
//import com.tomer.hostedftp.entity.User;
import com.tomer.hostedftp.service.UserService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
	private UserService userService;
	
	public LoginServlet() {
		super();
		this.userService = new UserService(new UserDAO());
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) request.getAttribute("username");
		String password = (String) request.getAttribute("password");
		
		if (username == null || password == null) {
			response.sendRedirect(request.getContextPath() + "/loginfailed");
			return;
		}
		try {
			boolean matched = userService.authenticate(username, password);
			if (matched) {
				HttpSession session = request.getSession();
				session.setAttribute("authenticated", true);
				session.setAttribute("username", username);
				response.sendRedirect(request.getContextPath() + "/user");
				return;
			}
			
		} catch (SQLException exception) {
			logger.severe("A SQL exception occured during login: " + exception.getMessage());
		}
		response.sendRedirect(request.getContextPath() + "/loginfailed");
	}
}
