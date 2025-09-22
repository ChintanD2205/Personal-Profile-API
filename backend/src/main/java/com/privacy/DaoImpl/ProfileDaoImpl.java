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
	    System.out.println(">>> DAO LOGIN START - Email: " + email + " Password: " + password);
	    
	    String query = "SELECT DB_NAME() as Database, @@SERVERNAME as Server, HOST_NAME() as Host";
	    Map<String, Object> info = jdbcTemplate.queryForMap(query);
	    System.out.println("App connected to: " + info);
	    
	    try {
	        // Debug: Fetch all clients first to check DB connectivity
	        System.out.println(">>> DAO - Testing database connection by fetching all clients");
	        String debugSql = "SELECT TOP 10 Username, Email, FirstName, LastName FROM CLIENTS";
	        List<Map<String, Object>> allClients = jdbcTemplate.queryForList(debugSql);
	        System.out.println(">>> DAO - Total clients found: " + allClients.size());
	        
	        for (Map<String, Object> client : allClients) {
	            System.out.println(">>> DAO - Client: " + client.get("Username") + " | " + client.get("Email"));
	        }
	        
	        System.out.println(">>> DAO - About to call sp_login_client");
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withProcedureName("sp_login_client")
	            .returningResultSet("resultSet", loginMapper);

	        SqlParameterSource params = new MapSqlParameterSource()
	            .addValue("Email", email)
	            .addValue("Password", password);

	        System.out.println(">>> DAO - Executing stored procedure");
	        Map<String, Object> resMap = jdbcCall.execute(params);
	        System.out.println(">>> DAO - SP result: " + resMap);
	        
	        List<Client> clients = (List<Client>) resMap.get("resultSet");
	        System.out.println(">>> DAO - Clients from SP: " + (clients != null ? clients.size() : "NULL"));

	        if (clients == null || clients.isEmpty()) {
	            System.out.println(">>> DAO - No clients found, returning null");
	            return null;
	        }
	        
	        Client foundClient = clients.get(0);
	        System.out.println(">>> DAO - Found client: " + foundClient.getUsername() + " ID: " + foundClient.getUserID());
	        return foundClient;
	        
	    } catch (Exception e) {
	        System.out.println(">>> DAO - Exception in login: " + e.getClass().getSimpleName() + " - " + e.getMessage());
	        e.printStackTrace();
	        throw e;
	    }
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
