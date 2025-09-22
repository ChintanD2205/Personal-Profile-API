package com.privacy.Model.DTO;

public class ConsentDTO {

	String purpose;
	String requiredField;
	String optionalField;
	String consentScope = requiredField+optionalField;
	Integer granted;
	
	
	
	public ConsentDTO(String purpose, String requiredField, String optionalField, Integer granted) {
		super();
		this.purpose = purpose;
		this.requiredField = requiredField;
		this.optionalField = optionalField;
		this.consentScope = requiredField + optionalField;
		this.granted = granted;
	}
	
	
	public ConsentDTO() {
		super();
	}


	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getRequiredField() {
		return requiredField;
	}
	public void setRequiredField(String requiredField) {
		this.requiredField = requiredField;
	}
	public String getOptionalField() {
		return optionalField;
	}
	public void setOptionalField(String optionalField) {
		this.optionalField = optionalField;
	}
	public String getConsentScope() {
		return consentScope;
	}
	public void setConsentScope() {
		this.consentScope = this.requiredField + this.optionalField;
	}


	public Integer getGranted() {
		return granted;
	}


	public void setGranted(Integer granted) {
		this.granted = granted;
	}


	@Override
	public String toString() {
		return "ConsentDTO [purpose=" + purpose + ", requiredField=" + requiredField + ", optionalField="
				+ optionalField + ", consentScope=" + consentScope + ", granted=" + granted + "]";
	}
	
}
