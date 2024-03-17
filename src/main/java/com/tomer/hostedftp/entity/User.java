package com.tomer.hostedftp.entity;

public class User {
	private int id;
	private String username;
	private String passwordHash;
	private String sport;
	private String drink;
	
	public User() {
		
	}

	public User(int id) {
		super();
		this.id = id;
	}

	public User(String username, String passwordHash, String sport, String drink) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.sport = sport;
		this.drink = drink;
	}
	
	public User(int id, String username, String passwordHash, String sport, String drink) {
		super();
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.sport = sport;
		this.drink = drink;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getDrink() {
		return drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}
	
	
}
