package com.privacy.BLL;

import java.util.List;

import com.privacy.Model.Entity.Client;
import com.privacy.Model.Entity.Employee;

public interface AuthBLL {

	Client login(String email, String password);

	List<String> getRolesForUser(int userId);

	Employee adminLogin(String email, String password);

}
