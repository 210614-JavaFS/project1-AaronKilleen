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
import com.revature.web.RequestContainer;
import com.revature.web.ResponseContainer;

public class ReimbursementController {

	private ObjectMapper objectMapper = new ObjectMapper();
	private static ReimbursementService reimbursementService = new ReimbursementService();
	private UserService userService = new UserService();
	
	public void addReimbursement(RequestContainer requestContainer, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		
		System.out.println("add_reimbursement_function");
		
		User user = userService.getUser(requestContainer.userId);		
		ResponseContainer responseContainer = new ResponseContainer();
		String reimbursementsJson = "";
		reimbursementService.addReimbursement(requestContainer);
		responseContainer.message = "reimbursement added!";
	
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
		String json = objectMapper.writeValueAsString(responseContainer);
		json = json.substring(0, json.length() - 1);
		json += reimbursementsJson;
		PrintWriter printWriter = resp.getWriter();;
		printWriter.print(json);

		resp.setStatus(200); 
		return;
	}
	public void resolveReimbursement(RequestContainer requestContainer, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		
		System.out.println("resolve_reimbursement_function");
		
		User user = userService.getUser(requestContainer.userId);		
		ResponseContainer responseContainer = new ResponseContainer();
		String reimbursementsJson = "";
		
		if(requestContainer.resolveStatus.equals("APPROVED"))
		{
			reimbursementService.approve(requestContainer.reimbursementId, requestContainer.userId);
			responseContainer.message = "reimbursement approved!";
			System.out.println("reimbursement approved!");
		}else if(requestContainer.resolveStatus.equals("DENIED"))
		{
			reimbursementService.deny(requestContainer.reimbursementId, requestContainer.userId);
			responseContainer.message = "reimbursement denied!";
			System.out.println("reimbursement denied!");
		}

	
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
		String json = objectMapper.writeValueAsString(responseContainer);
		json = json.substring(0, json.length() - 1);
		json += reimbursementsJson;
		PrintWriter printWriter = resp.getWriter();;
		printWriter.print(json);

		resp.setStatus(200); 
		return;
	}
	
}
