package com.privacy.Model.DTO;

public class DataAuditLogDTO {

	Integer AuditID, CustomerUserID, InternalUserID;
	String Action, ActionTimestamp, Details;
	
	public DataAuditLogDTO(Integer auditID, Integer customerUserID, Integer internalUserID, String action,
			String actionTimestamp, String details) {
		super();
		AuditID = auditID;
		CustomerUserID = customerUserID;
		InternalUserID = internalUserID;
		Action = action;
		ActionTimestamp = actionTimestamp;
		Details = details;
	}

	public DataAuditLogDTO() {
		super();
	}

	public Integer getAuditID() {
		return AuditID;
	}

	public void setAuditID(Integer auditID) {
		AuditID = auditID;
	}

	public Integer getCustomerUserID() {
		return CustomerUserID;
	}

	public void setCustomerUserID(Integer customerUserID) {
		CustomerUserID = customerUserID;
	}

	public Integer getInternalUserID() {
		return InternalUserID;
	}

	public void setInternalUserID(Integer internalUserID) {
		InternalUserID = internalUserID;
	}

	public String getAction() {
		return Action;
	}

	public void setAction(String action) {
		Action = action;
	}

	public String getActionTimestamp() {
		return ActionTimestamp;
	}

	public void setActionTimestamp(String actionTimestamp) {
		ActionTimestamp = actionTimestamp;
	}

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
	}

	@Override
	public String toString() {
		if (this.getInternalUserID()!=null)
		{
			return "An employee performed Action=" + Action + " at ActionTimestamp=" + ActionTimestamp + " whose Details="+ Details;
		}
		return "You performed Action=" + Action + " at ActionTimestamp=" + ActionTimestamp + " whose Details="+ Details;
	}
	
	
	
}
