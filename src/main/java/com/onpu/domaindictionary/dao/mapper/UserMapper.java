package com.onpu.domaindictionary.dao.mapper;

import com.onpu.domaindictionary.model.User;
import com.onpu.domaindictionary.model.enumeration.Role;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        BigInteger id = BigInteger.valueOf(resultSet.getLong("id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
       String  roleStr = (resultSet.getString("role"));
       Role role = Role.ROLE_USER;
        String password = resultSet.getString("password");
        return new User(id, name, email,password,role);
    }
}
