package com.privacy.Model.Entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Client {
	@Id
	private Integer userID;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
	
	
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, password, userID, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(userID, other.userID) && Objects.equals(username, other.username);
	}

	public Client(Integer userID, String firstName, String lastName, String username, String email, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Client(String firstName, String lastName, String username, String email, String password) {
        this(null, firstName, lastName, username, email, password);
    }
	
	public Client() {
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
