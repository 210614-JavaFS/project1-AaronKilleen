package com.revature.models;

import com.revature.services.ReimbursementService;
import com.revature.services.UserService;

public class Reimbursement {
public int id;
public double amount;
public String submitted;
public String resolved;
public String description;
public String receipt;
public int authorId;
public int resolverId;
public int statusId;
public int typeId;

public String jsonResponse()
{

	UserService userService = new UserService();
	ReimbursementService reimbursementService = new ReimbursementService();
	User author = userService.getUser(this.authorId);
	String authorName = author.getFirstName() + " " + author.getLastName();
	User resolver = userService.getUser(this.resolverId);
	String resolverName = resolver.getFirstName() + " " + resolver.getLastName();
	String status = reimbursementService.getStatus(statusId);
	String type = reimbursementService.getType(typeId);
	
	
	String response = "{ \"id\": \"";
	response += this.id;
	response += "\",\n\"amount\": \"";
	response += this.amount;
	response += "\",\n\"submitted\": \"";
	response += this.submitted;
	response += "\",\n\"resolved\": \"";
	response += this.resolved;
	response += "\",\n\"description\": \"";
	response += this.description;	
	response += "\",\n\"receipt\": \"";
	response += this.receipt;
	response += "\",\n\"author\": \"";
	response += authorName;
	response += "\",\n\"resolver\": \"";
	response += resolverName;
	response += "\",\n\"status\": \"";
	response += status;
	response += "\",\n\"type\": \"";
	response += type;
	response += "\" },\n";
	return response;
	/*
	JSON String example
	{
	"id": "id",
	"amount": "amount",
	"submitted": "submitted",
	"resolved": "resolved",
	"description": "description",
	"receipt": "receipt",
	"author": "author",
	"resolver": "resolver",
	"status": "status",
	"type": "type"
	}
	*/
	
	
}

public Reimbursement() {
	super();
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public double getAmount() {
	return amount;
}

public void setAmount(double amount) {
	this.amount = amount;
}

public String getSubmitted() {
	return submitted;
}

public void setSubmitted(String submitted) {
	this.submitted = submitted;
}

public String getResolved() {
	return resolved;
}

public void setResolved(String resolved) {
	this.resolved = resolved;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getReceipt() {
	return receipt;
}

public void setReceipt(String receipt) {
	this.receipt = receipt;
}

public int getAuthorId() {
	return authorId;
}

public void setAuthorId(int authorId) {
	this.authorId = authorId;
}

public int getResolverId() {
	return resolverId;
}

public void setResolverId(int resolverId) {
	this.resolverId = resolverId;
}

public int getStatusId() {
	return statusId;
}

public void setStatusId(int statusId) {
	this.statusId = statusId;
}

public int getTypeId() {
	return typeId;
}

public void setTypeId(int typeId) {
	this.typeId = typeId;
}


}





