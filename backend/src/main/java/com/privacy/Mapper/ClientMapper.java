package com.privacy.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.privacy.Model.Entity.Client;

@Component
public class ClientMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client user = new Client();
        user.setUserID(rs.getInt("NewUserId"));
        return user;
    }
}