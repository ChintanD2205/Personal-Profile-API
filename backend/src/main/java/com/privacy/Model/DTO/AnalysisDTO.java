package com.privacy.Model.DTO;

public class AnalysisDTO {
	private Integer totalActiveUsers;
	private Integer erasureRequested;
	private Integer marketingOptIn;
	private Integer marketingOptOut;
	private Integer totalApiCalls;
	private String mostAccessedEndpoint;
	private Integer erasureCompleted;
	
	public AnalysisDTO() {
		super();
	}
	public AnalysisDTO(Integer totalActiveUsers, Integer erasureRequested, Integer marketingOptIn,
			Integer marketingOptOut, Integer totalApiCalls, String mostAccessedEndpoint, Integer erasureCompleted) {
		super();
		this.totalActiveUsers = totalActiveUsers;
		this.erasureRequested = erasureRequested;
		this.marketingOptIn = marketingOptIn;
		this.marketingOptOut = marketingOptOut;
		this.totalApiCalls = totalApiCalls;
		this.mostAccessedEndpoint = mostAccessedEndpoint;
		this.erasureCompleted = erasureCompleted;
	}
	public Integer getTotalActiveUsers() {
		return totalActiveUsers;
	}
	public void setTotalActiveUsers(Integer totalActiveUsers) {
		this.totalActiveUsers = totalActiveUsers;
	}
	public Integer getErasureRequested() {
		return erasureRequested;
	}
	public void setErasureRequested(Integer erasureRequested) {
		this.erasureRequested = erasureRequested;
	}
	public Integer getMarketingOptIn() {
		return marketingOptIn;
	}
	public void setMarketingOptIn(Integer marketingOptIn) {
		this.marketingOptIn = marketingOptIn;
	}
	public Integer getMarketingOptOut() {
		return marketingOptOut;
	}
	public void setMarketingOptOut(Integer marketingOptOut) {
		this.marketingOptOut = marketingOptOut;
	}
	public Integer getTotalApiCalls() {
		return totalApiCalls;
	}
	public void setTotalApiCalls(Integer totalApiCalls) {
		this.totalApiCalls = totalApiCalls;
	}
	public String getMostAccessedEndpoint() {
		return mostAccessedEndpoint;
	}
	public void setMostAccessedEndpoint(String mostAccessedEndpoint) {
		this.mostAccessedEndpoint = mostAccessedEndpoint;
	}
	public Integer getErasureCompleted() {
		return erasureCompleted;
	}
	public void setErasureCompleted(Integer erasureCompleted) {
		this.erasureCompleted = erasureCompleted;
	}
	
	
}
