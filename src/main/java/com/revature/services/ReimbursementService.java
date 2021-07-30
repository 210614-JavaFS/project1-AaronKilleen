package com.revature.services;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.repos.ReimbursementDAO;
import com.revature.repos.ReimbursementDAOImpl;
import com.revature.web.RequestContainer;

public class ReimbursementService {
	public ReimbursementDAO reimbursementDao = new ReimbursementDAOImpl();
	
	public List<Reimbursement> findAll()
	{
		return reimbursementDao.findAll();
	}
	public List<Reimbursement> findByUser(int id)
	{
		return reimbursementDao.findByUser(id);
	}
	public List<Reimbursement> findByStatus(int id)
	{
		return reimbursementDao.findByStatus(id);
	}
	public void addReimbursement(RequestContainer requestContainer)
	{
		reimbursementDao.addReimbursement(requestContainer);
	}
	public String getType(int id)
	{
		return reimbursementDao.getType(id);
	}
	public String getStatus(int id)
	{
		return reimbursementDao.getStatus(id);
	}
	public void approve(int id, String time, int managerId)
	{
		reimbursementDao.approve(id, time, managerId);
	}
	public void deny(int id, String time, int managerId)
	{
		reimbursementDao.deny(id, time, managerId);
	}
	
	
}
