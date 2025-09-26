package com.privacy.DaoImpl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.privacy.BLLImpl.SNSService;
import com.privacy.Dao.ProfileDao;
import com.privacy.Mapper.ClientMapper;
import com.privacy.Mapper.LoginMapper;
import com.privacy.Model.DTO.ConsentDTO;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.Entity.Client;

@Repository
public class ProfileDaoImpl implements ProfileDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ClientMapper clientMapper;
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Autowired
	private SNSService snsService;
	
	public ConsentDTO getConsent(Map<String, Object> spRow) {
	    return modelMapper.map(spRow, ConsentDTO.class);
	}
	
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
	    
	    String query = "SELECT DB_NAME() as CurrentDB, @@SERVERNAME as Server, HOST_NAME() as Host";
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


	@Override
	public boolean updateConsent(List<ConsentDTO> consentList, Integer userID) {
	
	    ArrayList<Integer> newConsentIds = new ArrayList<>();
	
	    for (ConsentDTO c : consentList) {
	        System.out.println(c.toString());
	        c.setConsentScope();
	
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                .withProcedureName("sp_update_consent")
	                .declareParameters(
	                        new SqlParameter("UserID", Types.INTEGER),
	                        new SqlParameter("Purpose", Types.VARCHAR),
	                        new SqlParameter("Granted", Types.BIT),
	                        new SqlParameter("ConsentScope", Types.VARCHAR),
	                        new SqlOutParameter("TopicARN", Types.VARCHAR),
	                        new SqlOutParameter("UserEmail", Types.VARCHAR),
	                        new SqlOutParameter("SubscriptionID", Types.VARCHAR)
	                )
	                .returningResultSet("result", (rs, rowNum) -> rs.getInt("NewConsentId"));
	
	        SqlParameterSource params = new MapSqlParameterSource()
	                .addValue("UserID", userID)
	                .addValue("Purpose", c.getPurpose())
	                .addValue("Granted", c.getGranted())
	                .addValue("ConsentScope", c.getConsentScope());
	
	        Map<String, Object> resMap = jdbcCall.execute(params);
	        
	        List<Integer> resultIds = (List<Integer>) resMap.get("result");
	        
	        Integer newConsentId = resultIds.get(0);
	        
	        newConsentIds.add(newConsentId);
	
	        String topicArn = (String) resMap.get("TopicARN");
	        String email = ((String) resMap.get("UserEmail")).trim();
	        String subscriptionID = (String) resMap.get("SubscriptionID");
	
	        if (topicArn != null && !topicArn.isEmpty()) 
	        {
	            if (c.getGranted() == 1 && (subscriptionID == null || subscriptionID.isEmpty())) 
	            {
	                String sID = snsService.subscribeEmail(topicArn, email);
	                
	                SimpleJdbcCall updateSubCall = new SimpleJdbcCall(jdbcTemplate)
	                        .withProcedureName("sp_update_arn_subscriptions");
	                
	                updateSubCall.execute(new MapSqlParameterSource()
	                        .addValue("UserId", userID)
	                        .addValue("Purpose", c.getPurpose())
	                        .addValue("SubscriptionID",sID)
	                );
	            } else if (c.getGranted() == 0 && subscriptionID != null && !subscriptionID.isEmpty()) {
	            	
	                snsService.unsubscribe(subscriptionID);
	                
	                SimpleJdbcCall updateSubCall = new SimpleJdbcCall(jdbcTemplate)
	                        .withProcedureName("sp_update_arn_subscriptions");
	                
	                updateSubCall.execute(new MapSqlParameterSource()
	                        .addValue("UserId", userID)
	                        .addValue("Purpose", c.getPurpose())
	                        .addValue("SubscriptionID", null)
	                );
	            }
	        }
	
	        System.out.println("NewConsentId = " + newConsentId);
	    }
	
	    return consentList.size() == newConsentIds.size();
	}



	@Override
	public List<ConsentDTO> viewPreferences(Integer userID, Integer internalUID) {

	    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withProcedureName("sp_view_preferences");

	    SqlParameterSource params = new MapSqlParameterSource()
	            .addValue("UserID", userID)
	            .addValue("InternalUID", internalUID);

	    Map<String, Object> res = jdbcCall.execute(params);

	    List<Map<String, Object>> spResult = (List<Map<String, Object>>) res.get("#result-set-1");

	    if (spResult == null || spResult.isEmpty()) {
	        return Collections.emptyList();
	    }

	    List<ConsentDTO> dtos = spResult.stream()
	            .map(row -> modelMapper.map(row, ConsentDTO.class))
	            .collect(Collectors.toList());

	    return dtos;
	}



	@Override
	public List<DataAuditLogDTO> viewDataAccess(Integer userID, Integer internalUID) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withProcedureName("sp_view_data_access");

	    SqlParameterSource params = new MapSqlParameterSource()
	            .addValue("UserID", userID)
	            .addValue("InternalUID", internalUID);

	    Map<String, Object> res = jdbcCall.execute(params);

	    List<Map<String, Object>> spResult = (List<Map<String, Object>>) res.get("#result-set-1");

	    if (spResult == null || spResult.isEmpty()) {
	        return Collections.emptyList();
	    }

	    List<DataAuditLogDTO> dtos = spResult.stream()
	            .map(row -> modelMapper.map(row, DataAuditLogDTO.class))
	            .collect(Collectors.toList());

	    return dtos;
	}


	@Override
	public Client getProfile(Integer userID, Integer internalUID) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withProcedureName("sp_get_client");

	    SqlParameterSource params = new MapSqlParameterSource()
	            .addValue("UserID", userID)
	            .addValue("InternalUID", internalUID);

	    Map<String, Object> res = jdbcCall.execute(params);

	    List<Map<String, Object>> spResult = (List<Map<String, Object>>) res.get("#result-set-1");

	    if (spResult == null || spResult.isEmpty()) {
	        return null;
	    }

	    List<Client> dtos = spResult.stream()
	            .map(row -> modelMapper.map(row, Client.class))
	            .collect(Collectors.toList());

	    return dtos.get(0);
	}


	@Override
	public void requestErasure(Integer userID) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withProcedureName("sp_request_erasure");

	    SqlParameterSource params = new MapSqlParameterSource()
	            .addValue("UserID", userID);

	    Map<String, Object> res = jdbcCall.execute(params);
	}

}
