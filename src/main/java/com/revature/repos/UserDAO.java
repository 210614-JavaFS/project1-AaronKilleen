package com.revature.repos;

import java.util.List;

import com.revature.models.User;

public interface UserDAO {
	
	public List<User> findAll();
	public boolean checkLoginDetails(String username, String password);
	public User getUser(String username, String password);
	public User getUser(int id);
}
