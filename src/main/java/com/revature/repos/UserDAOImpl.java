package com.revature.repos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;
import com.revature.utils.ConnectionUtil;




public class UserDAOImpl implements UserDAO {
	private static Logger log = LoggerFactory.getLogger(UserDAOImpl.class);
	@Override
	public List<User> findAll() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users";
			log.debug(sql);
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<User> list = new ArrayList<>();
			
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				User user = new User();
				user.setId(result.getInt("user_id"));
				user.setUserName(result.getString("user_name"));
				user.setPassword(result.getString("password"));
				user.setFirstName(result.getString("first_name"));
				user.setLastName(result.getString("last_name"));
				user.setEmail(result.getString("email"));
				user.setRole(result.getString("role"));
				list.add(user);
			}
			
			return list;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public boolean checkLoginDetails(String username, String password)
	{
		try(Connection conn = ConnectionUtil.getConnection()){



			

			String sql = "SELECT * FROM get_password('" + username +"');";
			log.debug(sql);
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			String encryptedStoredPassword = "";
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				encryptedStoredPassword = result.getString("user_password");
			}
			System.out.println(encryptedStoredPassword);
		    StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		    return encryptor.checkPassword(password, encryptedStoredPassword);
	
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public User getUser(String username)
	{
		User user = new User();
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM get_user('" + username +"');";
			log.debug(sql);
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				user.setId(result.getInt("id"));
				user.setUserName(result.getString("username"));
				user.setPassword(result.getString("user_password"));
				user.setFirstName(result.getString("first_name"));
				user.setLastName(result.getString("last_name"));
				user.setEmail(result.getString("email"));
				user.setRole(result.getString("user_role"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User getUser(int id)
	{
		User user = new User();
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM find_user(" + id + ");";
			log.debug(sql);
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				user.setId(result.getInt("id"));
				user.setUserName(result.getString("username"));
				user.setPassword(result.getString("user_password"));
				user.setFirstName(result.getString("first_name"));
				user.setLastName(result.getString("last_name"));
				user.setEmail(result.getString("email"));
				user.setRole(result.getString("user_role"));
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
	
	
}
