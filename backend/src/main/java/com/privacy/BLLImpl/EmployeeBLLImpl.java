package com.privacy.BLLImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privacy.BLL.EmployeeBLL;
import com.privacy.Dao.EmployeeDao;

@Service
public class EmployeeBLLImpl implements EmployeeBLL{

	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public Map<String, Object> getUserDataForAdmin(Integer userID, Integer employeeID) {
		return employeeDao.getUserDataForAdmin(userID, employeeID);
	}

}
