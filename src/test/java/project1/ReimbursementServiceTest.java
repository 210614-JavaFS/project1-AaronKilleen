package project1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.revature.models.Reimbursement;
import com.revature.repos.ReimbursementDAO;
import com.revature.repos.ReimbursementDAOImpl;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import com.revature.web.RequestContainer;

public class ReimbursementServiceTest {
	public ReimbursementService reimbursementSurvice = new ReimbursementService();
	public ReimbursementDAO reimbursementDao = new ReimbursementDAOImpl();
	public UserService userService = new UserService();
	public RequestContainer requestContainer = new RequestContainer();
	int id = 1;
	int employeeId = 111222333;
	int managerId = 333222111;
	 @Test	
	public void findAllTest() {
		 List<Reimbursement> testReturnList  = reimbursementSurvice.findAll();
	 assertEquals(testReturnList.size(), true);
	}
	 @Test	
	public void findByUser() {
		 List<Reimbursement> testReturnList  = reimbursementSurvice.findByUser(id);
		 assertEquals(testReturnList.size(), true);
	}
	 @Test	
	public void findByStatusTest(){
		 List<Reimbursement> testReturnList  = reimbursementSurvice.findByStatus(id);
		 assertEquals(testReturnList.size() , true);
	}
	
	 @Test	
	public void addReimbursementTest()
	{
	
		requestContainer.userId = 111222333;
		requestContainer.amount = 50.00;
		requestContainer.description = "test";
		requestContainer.receipt = "test";
		requestContainer.typeId = 2;
		 boolean testReturn  = reimbursementSurvice.addReimbursement(requestContainer);
		 assertEquals(testReturn , true);
	}
	
	 @Test	
	public void getTypeTest(){
		 String testReturnString  = reimbursementSurvice.getType(id);
		 assertEquals((testReturnString.length() > 0) , true);
	}
	
	 @Test	
	public void getStatusQuest(){
		 String testReturnString  = reimbursementSurvice.getStatus(id);
		 assertEquals((testReturnString.length() > 0) , true);
	}
	 @Test	
	public void approveTest() {
		 boolean testReturn = reimbursementSurvice.approve(id, managerId);
		 assertEquals(testReturn , true);
	}
	
	 @Test	
	public void denyTest() {
		 boolean testReturn  = reimbursementSurvice.deny( id, managerId);
		 assertEquals(testReturn , true);
	}

	
}
