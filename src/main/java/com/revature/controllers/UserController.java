package com.revature.controllers;


import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import com.revature.web.ResponseContainer;
import com.revature.web.RequestContainer;

public class UserController {
	
	private static UserService userService = new UserService();
	private ObjectMapper objectMapper = new ObjectMapper();
	private static ReimbursementService reimbursementService = new ReimbursementService();
	/*
	public void getAllUsers(HttpServletResponse response) throws IOException {
		List<User> list = userService.usersAssemble();
		
		String json = objectMapper.writeValueAsString(list);
		
		System.out.println(json);
		
		PrintWriter printWriter = response.getWriter();
		
		printWriter.print(json);
		
		response.setStatus(200);
		
	}
	*/
	public void login(RequestContainer requestContainer, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
	
		ResponseContainer responseContainer = new ResponseContainer();
		String reimbursementsJson = "";
		if(userService.login(requestContainer.username, requestContainer.password)) {
			User user = userService.getUser(requestContainer.username, requestContainer.password);
			System.out.println("login success!");
			
			if(user.getRole().equals("MANAGER"))
			{
				System.out.println("Manager Logged in!");
				List<Reimbursement> reimbursements = reimbursementService.findAll();
				reimbursementsJson += ", \n \"reimbursements\": [";
				for(int i = 0; i < reimbursements.size(); ++i)
				{
					reimbursementsJson += reimbursements.get(i).jsonResponse();
				}
				reimbursementsJson = reimbursementsJson.substring(0, reimbursementsJson.length() - 1);
				reimbursementsJson = reimbursementsJson.substring(0, reimbursementsJson.length() - 1);
				reimbursementsJson += "]\n}";
				//System.out.println(reimbursementsJson);
				responseContainer.state = "manager_menu";
				responseContainer.id = user.getId();
			}else if(user.getRole().equals("EMPLOYEE"))
			{
				System.out.println("Employee Logged in!");
				List<Reimbursement> reimbursements = reimbursementService.findByUser(user.getId());
				reimbursementsJson += ", \n \"reimbursements\": [";
				for(int i = 0; i < reimbursements.size(); ++i)
				{
					reimbursementsJson += reimbursements.get(i).jsonResponse();
				}
				reimbursementsJson = reimbursementsJson.substring(0, reimbursementsJson.length() - 1);
				reimbursementsJson = reimbursementsJson.substring(0, reimbursementsJson.length() - 1);
				reimbursementsJson += "]\n}";
				//System.out.println(reimbursementsJson);
				responseContainer.state = "employee_menu";
				responseContainer.id = user.getId();
			}else
			{
				System.out.println("unidentified user role!");
				responseContainer.state = "login_failed";
				return;
			}
			
			

		}else{

			System.out.println("login failed!");
			responseContainer.state = "login_failed";
			resp.setStatus(200); 
			return;
		}


		String json = objectMapper.writeValueAsString(responseContainer);
		json = json.substring(0, json.length() - 1);
		json += reimbursementsJson;
		System.out.println(json);
		PrintWriter printWriter = resp.getWriter();;
		printWriter.print(json);

		resp.setStatus(200); 
		return;
		}


}
	

