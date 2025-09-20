package com.privacy.Model.DTO;


import java.util.Objects;

public class ClientDTO {
	
	
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private Integer userID;
	@Override
	public String toString() {
		return "ClientDTO [userID=" + userID + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
				+ username + ", email=" + email + "]";
	}

	

	public ClientDTO(Integer userID, String firstName, String lastName, String username, String email) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }

    public ClientDTO(String firstName, String lastName, String username, String email) {
        this(null, firstName, lastName, username, email);
    }
	
	public ClientDTO() {
		super();
	}

	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, userID, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientDTO other = (ClientDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(userID, other.userID) && Objects.equals(username, other.username);
	}
}
