package com.revature.repos;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.web.RequestContainer;

public interface ReimbursementDAO {
	public List<Reimbursement> findAll();
	public List<Reimbursement> findByUser(int id);
	public List<Reimbursement> findByStatus(int id);
	public void addReimbursement(RequestContainer requestContainer);
	public String getType(int id);
	public String getStatus(int id);
	public void approve(int id, int managerId);
	public void deny(int id, int managerId);
}
