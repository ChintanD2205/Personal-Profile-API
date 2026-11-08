package com.privacy.Model.DTO;

public class ErasureRequestDTO {

	private Integer requestID;
	private Integer userID;
	private String requestType;
	private String status;
	private String requestedAt;
	private String completedAt;
	private String notes;
	
	
	public ErasureRequestDTO(Integer requestID, Integer userID, String requestType, String status, String requestedAt,
			String completedAt, String notes) {
		super();
		this.requestID = requestID;
		this.userID = userID;
		this.requestType = requestType;
		this.status = status;
		this.requestedAt = requestedAt;
		this.completedAt = completedAt;
		this.notes = notes;
	}


	public ErasureRequestDTO() {
		super();
	}


	public Integer getRequestID() {
		return requestID;
	}


	public void setRequestID(Integer requestID) {
		this.requestID = requestID;
	}


	public Integer getUserID() {
		return userID;
	}


	public void setUserID(Integer userID) {
		this.userID = userID;
	}


	public String getRequestType() {
		return requestType;
	}


	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getRequestedAt() {
		return requestedAt;
	}


	public void setRequestedAt(String requestedAt) {
		this.requestedAt = requestedAt;
	}


	public String getCompletedAt() {
		return completedAt;
	}


	public void setCompletedAt(String completedAt) {
		this.completedAt = completedAt;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	@Override
	public String toString() {
		return "ErasureRequestDTO [requestID=" + requestID + ", userID=" + userID + ", requestType=" + requestType
				+ ", status=" + status + ", requestedAt=" + requestedAt + ", completedAt=" + completedAt + ", notes="
				+ notes + "]";
	}
	
	
}
