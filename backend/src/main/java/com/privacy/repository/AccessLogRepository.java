package com.privacy.repository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class AccessLogRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insertAccessLog(Integer userId, String endpoint, String httpMethod, LocalDateTime requestTime, String ipAddress, int statusCode) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withProcedureName("sp_update_access_log");

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("UserID", userId)
            .addValue("Endpoint", endpoint)
            .addValue("HttpMethod", httpMethod)
            .addValue("RequestTime", requestTime)
            .addValue("IpAddress", ipAddress)
            .addValue("StatusCode", statusCode);

        jdbcCall.execute(params);
		
	}
	
	
}
