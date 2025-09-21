package com.privacy.DaoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.privacy.Dao.ProfileDao;
import com.privacy.Mapper.ClientMapper;
import com.privacy.Mapper.LoginMapper;
import com.privacy.Model.Entity.Client;

@Repository
public class ProfileDaoImpl implements ProfileDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ClientMapper clientMapper;
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public Client save(Client client) {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("profile_create_client")
				.returningResultSet("resultSet", clientMapper);
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("username", client.getUsername())
				.addValue("Password", "sample")
				.addValue("Email", client.getEmail())
				.addValue("FirstName", client.getFirstName())
				.addValue("LastName", client.getLastName())
				.addValue("Status", 1);
		
		Map<String, Object> res = jdbcCall.execute(params);
		System.out.println(res.toString());
		List<Client> clients = (List<Client>) res.get("resultSet");
		return clients.get(0);
	}

	
	public Client login(String email, String password) {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_login_client")
				.returningResultSet("resultSet", loginMapper);
		
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("Email", email)
				.addValue("Password", password);
		
		Map<String, Object> resMap = jdbcCall.execute(params);
		List<Client> clients = (List<Client>) resMap.get("resultSet");
		
		if (clients == null || clients.isEmpty()) {
	        return null;
	    }
		return clients.get(0);
	}
	
	@Override
	public List<String> getRolesByUserId(int userId) {
	    try {
	        
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withProcedureName("sp_get_user_roles")
	            .returningResultSet("roles", (rs, rowNum) -> rs.getString("RoleName"));

	        SqlParameterSource params = new MapSqlParameterSource()
	            .addValue("UserId", userId);

	        Map<String, Object> res = jdbcCall.execute(params);
	        List<String> roles = (List<String>) res.get("roles");
	        return roles != null ? roles : List.of("CLIENT");
	        
	    } catch (Exception e) {
	        return List.of("CLIENT");
	    }
	}

}
