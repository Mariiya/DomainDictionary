package com.onpu.domaindictionary.dao;

import com.onpu.domaindictionary.dao.mapper.UserMapper;
import com.onpu.domaindictionary.model.User;
import com.onpu.domaindictionary.model.enumeration.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class UserDao{

    private static final Logger log = Logger.getLogger(UserDao.class.getName());
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public User findByUsernameOrEmail(String username) {
       log.debug("findByUsernameOrEmail " + username);
       try {
           return jdbcTemplate.queryForObject(Constants.GET_USER_BY_NAME_OR_EMAIL, new UserMapper(), username, username);
       }catch (EmptyResultDataAccessException e){
           log.error("user not found " +  username);
           return null;
       }
    }

    public void save(User user) {
        log.debug("Register new user " + user);
        if(user.getRole() == null) {
            user.setRole(Role.ROLE_USER);
        }
       jdbcTemplate.update(Constants.CREATE_USER, user.getName(),user.getEmail(),user.getPassword(),user.getRole().name());
    }

    public Boolean existsByUsername(String username) {
        Integer res = jdbcTemplate.queryForObject(Constants.IS_USER_BY_NAME, Integer.class,username);
        if (res != null) {
            return res != 0;
        }
        return true;
    }


    public Boolean existsByEmail(String email) {
        Integer res = jdbcTemplate.queryForObject(Constants.IS_USER_BY_EMAIL, Integer.class,email);
        if (res != null) {
            return res != 0;
        }
        return true;
    }


}
