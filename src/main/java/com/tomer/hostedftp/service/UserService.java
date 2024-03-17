package com.tomer.hostedftp.service;

import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.tomer.hostedftp.dao.UserDAO;
import com.tomer.hostedftp.entity.User;

public class UserService {
	private UserDAO dao;
	
	public UserService(UserDAO dao) {
		this.dao = dao;
	}
	
	public boolean authenticate(String username, String password) throws SQLException {
		User user = null;
		user = dao.findByUsername(username);
		if (user == null) {
			return false;
		}
		return BCrypt.checkpw(password, user.getPasswordHash());
	}
	
	public User getUserByUsername(String username) throws SQLException {
		return dao.findByUsername(username);
	}
}
