package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import com.revature.web.RequestContainer;

public class ReimbursementController {

	private static UserService userService = new UserService();
	private ObjectMapper objectMapper = new ObjectMapper();
	private static ReimbursementService reimbursementService = new ReimbursementService();
	
	public void addReimbursement(RequestContainer requestContainer, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		

		reimbursementService.addReimbursement(requestContainer);
		
		
	}
	
	
}
