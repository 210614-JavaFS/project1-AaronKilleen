package com.revature.services;

import java.util.List;

import com.revature.models.User;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;

public class UserService {
	
	private static UserDAO userDao = new UserDAOImpl();
	
	public List<User> findAll(){
		return userDao.findAll();
	}
	
	public User getUser(String username, String password)
	{
		return userDao.getUser(username, password);
	}
	public User getUser(int id)
	{
		return userDao.getUser(id);
	}	
	
	
	public boolean login(String username, String password)
	{
		return userDao.checkLoginDetails(username, password);
	}
	

	

}
