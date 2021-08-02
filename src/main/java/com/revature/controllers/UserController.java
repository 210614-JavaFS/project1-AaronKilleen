package com.revature.controllers;


import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import com.revature.web.ResponseContainer;
import com.revature.web.RequestContainer;

public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	private static UserService userService = new UserService();
	private ObjectMapper objectMapper = new ObjectMapper();
	private static ReimbursementService reimbursementService = new ReimbursementService();
	public void login(RequestContainer requestContainer, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
	
		ResponseContainer responseContainer = new ResponseContainer();
		String reimbursementsJson = "";
		if(userService.login(requestContainer.username, requestContainer.password)) {
			User user = userService.getUser(requestContainer.username);
			
			if(user.getRole().equals("MANAGER"))
			{
				List<Reimbursement> reimbursements = reimbursementService.findAll();
				reimbursementsJson += ", \n \"reimbursements\": [";
				for(int i = 0; i < reimbursements.size(); ++i)
				{
					reimbursementsJson += reimbursements.get(i).jsonResponse();
				}
				reimbursementsJson = reimbursementsJson.substring(0, reimbursementsJson.length() - 1);
				reimbursementsJson = reimbursementsJson.substring(0, reimbursementsJson.length() - 1);
				reimbursementsJson += "]\n}";
				responseContainer.state = "manager_menu";
				responseContainer.id = user.getId();
				String message = "Welcome " + user.getFirstName() + ".";
				responseContainer.message = message;
			}else if(user.getRole().equals("EMPLOYEE"))
			{
				List<Reimbursement> reimbursements = reimbursementService.findByUser(user.getId());
				reimbursementsJson += ", \n \"reimbursements\": [";
				for(int i = 0; i < reimbursements.size(); ++i)
				{
					reimbursementsJson += reimbursements.get(i).jsonResponse();
				}
				reimbursementsJson = reimbursementsJson.substring(0, reimbursementsJson.length() - 1);
				reimbursementsJson = reimbursementsJson.substring(0, reimbursementsJson.length() - 1);
				reimbursementsJson += "]\n}";
				responseContainer.state = "employee_menu";
				responseContainer.id = user.getId();
				String message = "Welcome " + user.getFirstName() + ".";
				responseContainer.message = message;
			}else
			{
				System.out.println("unidentified user role!");
				responseContainer.state = "logged_out";
				responseContainer.message = "Login Failed, unidentified user role.";
				String json = objectMapper.writeValueAsString(responseContainer);
				log.debug(json);
				PrintWriter printWriter = resp.getWriter();;
				printWriter.print(json);
				return;
			}
			
			

		}else{

			System.out.println("login failed!");
			responseContainer.state = "logged_out";
			responseContainer.message = "Login Failed, invalid username or password.";
			String json = objectMapper.writeValueAsString(responseContainer);
			log.debug(json);
			PrintWriter printWriter = resp.getWriter();;
			printWriter.print(json);
			resp.setStatus(200); 
			return;
		}


		String json = objectMapper.writeValueAsString(responseContainer);
		json = json.substring(0, json.length() - 1);
		json += reimbursementsJson;
		log.debug(json);
		PrintWriter printWriter = resp.getWriter();;
		printWriter.print(json);

		resp.setStatus(200); 
		return;
		}


}
	

