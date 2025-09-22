package com.privacy.Dao;

import java.util.Map;

import com.privacy.Model.Entity.Employee;

public interface EmployeeDao {

	Map<String, Object> getUserDataForAdmin(Integer userID, Integer employeeID);

	Employee adminLogin(String email, String password);

}
