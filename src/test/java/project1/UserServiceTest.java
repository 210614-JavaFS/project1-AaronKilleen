package project1;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;


import com.revature.models.User;
import com.revature.services.UserService;

public class UserServiceTest{
	
	
	User user = new User();
	UserService userService = new UserService();
	int id = 111222333;
	String username = "JohnQPublic";
	String password = "password";
	
	public void findAllTest() {
	List<User> testResult = userService.findAll();
	assertEquals((testResult.size() != 0) , true);
	}
	
	public void getUserTest(){
	User testResult = userService.getUser(username);
	assertEquals((testResult != null), true);
	}
	
	public void getUserTest(int myId){
	User testResult = userService.getUser(myId);
	assertEquals((testResult != null) , true);
	}	
	
	public void loginTest() {
	boolean testResult = userService.login(username, password);
	assertEquals(testResult , true);
	}
	
	
		
	

}