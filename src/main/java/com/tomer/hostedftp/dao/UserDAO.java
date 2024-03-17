package com.tomer.hostedftp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import com.tomer.hostedftp.entity.User;
import com.tomer.hostedftp.util.ConnectionManager;


public class UserDAO {	
	public UserDAO() {

	}
	
	public User findById(int id) throws SQLException {
		User user = null;
		String sql = "SELECT * FROM user WHERE id = ?";
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			String username = resultSet.getString("username");
			String passwordHash = resultSet.getString("password_hash");
			String sport = resultSet.getString("sport");
			String drink = resultSet.getString("drink");
			user = new User(id, username, passwordHash, sport, drink);
		}
		
		resultSet.close();
		statement.close();
		
		ConnectionManager.closeConnection(connection);
		
		return user;
	}
	
	public User findByUsername(String username) throws SQLException {
		User user = null;
		String sql = "SELECT * FROM user WHERE username = ?";
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, username);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			int id = resultSet.getInt("id");
			String passwordHash = resultSet.getString("password_hash");
			String sport = resultSet.getString("sport");
			String drink = resultSet.getString("drink");
			user = new User(id, username, passwordHash, sport, drink);
		}
		
		resultSet.close();
		statement.close();
		
		ConnectionManager.closeConnection(connection);
		
		return user;
	}
	
	
}
