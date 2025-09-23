package com.privacy.BLLImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privacy.BLL.EmployeeBLL;
import com.privacy.Dao.EmployeeDao;
import com.privacy.Model.DTO.DataAuditLogDTO;

@Service
public class EmployeeBLLImpl implements EmployeeBLL{

	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public Map<String, Object> getUserDataForAdmin(Integer userID, Integer employeeID) {
		return employeeDao.getUserDataForAdmin(userID, employeeID);
	}

	@Override
	public List<DataAuditLogDTO> getAuditLogs(Integer id, Integer employeeID) {
		return employeeDao.getAuditLogs(id, employeeID);
	}

}
