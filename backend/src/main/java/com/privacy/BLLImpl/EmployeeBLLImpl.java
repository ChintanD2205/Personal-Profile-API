package com.privacy.BLLImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privacy.BLL.EmployeeBLL;
import com.privacy.Dao.EmployeeDao;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.DTO.ErasureRequestDTO;

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

	@Override
	public List<ErasureRequestDTO> getErasureRequests() {
		return employeeDao.getErasureRequests();
	}

	@Override
	public String hardDelete(Integer id, Integer employeeID) {
		return employeeDao.hardDelete(id, employeeID);
	}

	@Override
	public List<Map<String, String>> getMarketingUsers(Integer id, Integer employeeID) {
		if (id!= null)
		{
			return employeeDao.getMarketingUsersById(id, employeeID);
		}
		else {
			return employeeDao.getMarketingUsers(employeeID);
		}
	}

}
