package com.tomer.hostedftp.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.tomer.hostedftp.dao.UserDAO;
import com.tomer.hostedftp.entity.User;
import com.tomer.hostedftp.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserServlet.class.getName());
	private UserService userService;
	
	public UserServlet() {
		super();
		this.userService = new UserService(new UserDAO());
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("authenticated") != null && (boolean) session.getAttribute("authenticated")) {
			User user = null;
			String username = (String) session.getAttribute("username");
			try {
				user = userService.getUserByUsername(username);				
			} catch (SQLException exception) {
				logger.severe("A SQL exception occured: " + exception.getMessage());
			}
			
			request.setAttribute("username", user.getUsername());
			request.setAttribute("sport", user.getSport());
			request.setAttribute("drink", user.getDrink());
			request.getRequestDispatcher("/jsp/user.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

}
