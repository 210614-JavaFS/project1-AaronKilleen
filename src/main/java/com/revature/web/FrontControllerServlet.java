package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ReimbursementController;
import com.revature.controllers.UserController;

//import com.revature.controllers.UserController;

@SuppressWarnings("serial")
public class FrontControllerServlet extends HttpServlet {

	private ObjectMapper objectMapper = new ObjectMapper();
	private UserController userController = new UserController();
	private ReimbursementController reimbursementController = new ReimbursementController();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
				doPost(req, resp);
	}



	@Override
	protected void doPost(HttpServletRequest req , HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader br=new BufferedReader(req.getReader());    
        String body = "";
        int i;    
        while((i=br.read())!=-1){  
        body += (char)i;  
        }  
        br.close();  
		RequestContainer requestContainer;
		requestContainer = objectMapper.readValue(body, RequestContainer.class);
		switch(requestContainer.state)
		{
		case "logged_out":
		{
			userController.login(requestContainer, req, resp);
			break;
		}
		case "add_reimbursement":
		{
			reimbursementController.addReimbursement(requestContainer, req, resp);
			break;
		}
		case "resolve_reimbursement":
		{
			reimbursementController.resolveReimbursement(requestContainer, req, resp);
			break;
		}
		
		default:
		System.out.println("State not recognized.");
		ResponseContainer responseContainer = new ResponseContainer();
		responseContainer.state = "logged_out";
		responseContainer.message = "unrecognized state.";
		String json = objectMapper.writeValueAsString(responseContainer);
		PrintWriter printWriter = resp.getWriter();;
		printWriter.print(json);
		resp.setStatus(200); 
			
		}
		return;

	}

}
