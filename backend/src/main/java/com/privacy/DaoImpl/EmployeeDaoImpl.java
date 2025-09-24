package com.privacy.DaoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.privacy.Dao.EmployeeDao;
import com.privacy.Dao.ProfileDao;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.DTO.ErasureRequestDTO;
import com.privacy.Model.Entity.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ProfileDao profileDao;
	
	@Override
	public Employee adminLogin(String email, String password) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("sp_login_admin");

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("Email", email)
            .addValue("Password", password);

        Map<String, Object> resMap = jdbcCall.execute(params);
        
        List<Map<String, Object>> spResult = (List<Map<String, Object>>) resMap.get("#result-set-1");

        if (spResult == null || spResult.isEmpty()) {
	        return null;
	    }

	    List<Employee> employees = spResult.stream()
	            .map(row -> modelMapper.map(row, Employee.class))
	            .collect(Collectors.toList());
        
        return employees.get(0);
	}
	
	
	@Override
	public Map<String, Object> getUserDataForAdmin(Integer userID, Integer employeeID) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("Client", profileDao.getProfile(userID, employeeID));
		result.put("Preferences", profileDao.viewPreferences(userID, employeeID));
		return result;
	}


	@Override
	public List<DataAuditLogDTO> getAuditLogs(Integer id, Integer employeeID) {
		return profileDao.viewDataAccess(id, employeeID);
	}


	@Override
	public List<ErasureRequestDTO> getErasureRequests() {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_get_erasure_requests");
		
		Map<String, Object> resMap = jdbcCall.execute();
		
		List<Map<String, Object>> spResult = (List<Map<String, Object>>) resMap.get("#result-set-1");

        if (spResult == null || spResult.isEmpty()) {
	        return null;
	    }

	    List<ErasureRequestDTO> reqs = spResult.stream()
	            .map(row -> modelMapper.map(row, ErasureRequestDTO.class))
	            .collect(Collectors.toList());
        
        return reqs;
	}


	@Override
	public String hardDelete(Integer id, Integer employeeID) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_get_erasure_requests");
		
		Map<String, Object> resMap = jdbcCall.execute();
		
		List<Map<String, Object>> spResult = (List<Map<String, Object>>) resMap.get("#result-set-1");

        if (spResult == null || spResult.isEmpty()) {
	        return null;
	    }

	    List<ErasureRequestDTO> reqs = spResult.stream()
	            .map(row -> modelMapper.map(row, ErasureRequestDTO.class))
	            .collect(Collectors.toList());
        
        return "";
	}


	@Override
	public List<Map<String, String>> getMarketingUsers(Integer employeeID) {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withProcedureName("sp_get_all_marketing_users");

	        SqlParameterSource params = new MapSqlParameterSource()
	            .addValue("InternalUID", employeeID);

	        Map<String, Object> resMap = jdbcCall.execute(params);
	        
	        List<Map<String, Object>> spResult = (List<Map<String, Object>>) resMap.get("#result-set-1");

	        if (spResult == null || spResult.isEmpty()) {
		        return new ArrayList<>();
		    }

		    List<Map<String, String>> resList = spResult.stream()
		    		.map(row-> {
		    			Map<String, String> sMap = new HashMap<>();
		    			row.forEach((key,value)->
		    				sMap.put(key, value!=null ? value.toString() : null)
		    			);
		    			return sMap;
		    		})
		    		.collect(Collectors.toList());
		    
		    return resList;
	}


	@Override
	public List<Map<String, String>> getMarketingUsersById(Integer id, Integer employeeID) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withProcedureName("sp_get_marketing_users_by_id");

	        SqlParameterSource params = new MapSqlParameterSource()
	            .addValue("InternalUID", employeeID)
	            .addValue("UserID", id);

	        Map<String, Object> resMap = jdbcCall.execute(params);
	        
	        List<Map<String, Object>> spResult = (List<Map<String, Object>>) resMap.get("#result-set-1");

	        if (spResult == null || spResult.isEmpty()) {
		        return new ArrayList<>();
		    }

		    List<Map<String, String>> resList = spResult.stream()
		    		.map(row-> {
		    			Map<String, String> sMap = new HashMap<>();
		    			row.forEach((key,value)->
		    				sMap.put(key, value!=null ? value.toString() : null)
		    			);
		    			return sMap;
		    		})
		    		.collect(Collectors.toList());
		    
		    return resList;
	}

}
