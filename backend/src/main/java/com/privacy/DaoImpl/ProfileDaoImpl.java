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
import com.privacy.Model.Entity.Client;

@Repository
public class ProfileDaoImpl implements ProfileDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ClientMapper clientMapper;
	
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

}
