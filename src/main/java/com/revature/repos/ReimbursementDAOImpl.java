package com.revature.repos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.utils.ConnectionUtil;
import com.revature.web.RequestContainer;

public class ReimbursementDAOImpl implements ReimbursementDAO{
	
	
	
	public List<Reimbursement> findAll()
	{
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM ers_reimbursement;";
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<Reimbursement> list = new ArrayList<>();
			

			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				Reimbursement reimbursement = new Reimbursement();
				reimbursement.setId(result.getInt("reimb_id"));
				reimbursement.setAmount(result.getDouble("reimb_amount"));
				reimbursement.setSubmitted(result.getString("reimb_submitted"));
				reimbursement.setResolved(result.getString("reimb_resolved"));
				reimbursement.setDescription(result.getString("reimb_description"));
				reimbursement.setReceipt(result.getString("reimb_receipt"));
				reimbursement.setAuthorId(result.getInt("reimb_author"));
				reimbursement.setResolverId(result.getInt("reimb_resolver"));
				reimbursement.setStatusId(result.getInt("reimb_status_id"));
				reimbursement.setTypeId(result.getInt("reimb_type_id"));
				list.add(reimbursement);
			}
			
			return list;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}
	

	public List<Reimbursement> findByUser(int id)
	{
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM view_past_reimbursements("+ id + ");";
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<Reimbursement> list = new ArrayList<>();
			

			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				Reimbursement reimbursement = new Reimbursement();
				reimbursement.setId(result.getInt("id"));
				reimbursement.setAmount(result.getDouble("amount"));
				reimbursement.setSubmitted(result.getString("submitted"));
				reimbursement.setResolved(result.getString("resolved"));
				reimbursement.setDescription(result.getString("description"));
				reimbursement.setReceipt(result.getString("receipt"));
				reimbursement.setAuthorId(result.getInt("author"));
				reimbursement.setResolverId(result.getInt("resolver"));
				reimbursement.setStatusId(result.getInt("status_id"));
				reimbursement.setTypeId(result.getInt("type_id"));
				list.add(reimbursement);
			}
			
			return list;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
				
	}
	

	public List<Reimbursement> findByStatus(int id)
	{
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM view_filtered_reimbursements("+ id + ");";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<Reimbursement> list = new ArrayList<>();
			

			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				Reimbursement reimbursement = new Reimbursement();
				reimbursement.setId(result.getInt("reimb_id"));
				reimbursement.setAmount(result.getDouble("reimb_amount"));
				reimbursement.setSubmitted(result.getString("reimb_submitted"));
				reimbursement.setResolved(result.getString("reimb_resolved"));
				reimbursement.setDescription(result.getString("reimb_description"));
				reimbursement.setReceipt(result.getString("reimb_receipt"));
				reimbursement.setAuthorId(result.getInt("reimb_author"));
				reimbursement.setResolverId(result.getInt("reimb_resolver"));
				reimbursement.setStatusId(result.getInt("reimb_status_id"));
				reimbursement.setTypeId(result.getInt("reimb_type_id"));
				list.add(reimbursement);
			}
			
			return list;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
				
	}
	
	public void addReimbursement(RequestContainer requestContainer)
	{
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "CALL create_reimbursement(" + requestContainer.amount + "', '" + requestContainer.description + "', '" + requestContainer.receipt + "', " + requestContainer.userId + ", " + requestContainer.typeId + ");";
			Statement statement = conn.createStatement();
			statement.execute(sql);
	
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public String getType(int id)
	{
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = 	"SELECT * FROM check_type(" + id + ");";
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			String type = "";

			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				type = result.getString("reimbursement_type");
			}
			
			return type;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		
			return "";
		
	}
	
	
	public String getStatus(int id)
	{
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = 	"SELECT * FROM check_status(" + id + ");";
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			String status = "";

			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				status = result.getString("status");
			}
			
			return status;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		
			return "";
	}

	public void approve(int id, String time, int managerId)
	{
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = 	"CALL approve_reimbursement(" + id + " , '"+ time + "' , " + id + " );";
			Statement statement = conn.createStatement();
			statement.execute(sql);
	
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deny(int id, String time, int managerId)
	{
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = 	"CALL deny_reimbursement(" + id + " , '"+ time + "' , " + id + " );";
			Statement statement = conn.createStatement();
			statement.execute(sql);
	
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
}
