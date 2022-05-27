package com.pullenti.domaindictionary.secutity.services;

import com.pullenti.domaindictionary.dao.UserDao;
import com.pullenti.domaindictionary.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    @Autowired
    private UserDao userRepository;

    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.findByUsernameOrEmail(email);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage(), e);
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }
        if (user == null) {
            AuthenticationException e = new UsernameNotFoundException("User Not Found with email: " + email);
            throw e;
        }
        log.info("User found " + user.getEmail());
        return UserDetailsImpl.build(user);
    }

    public User getUserById(BigInteger id) throws UsernameNotFoundException {
       return userRepository.getUserById(id);
    }

    public User findByUsernameOrEmail(String username) {
       return userRepository.findByUsernameOrEmail(username);
    }

    public void save(User user) {
       userRepository.save(user);
    }

    public Boolean existsByUsername(String username) {
       return  userRepository.existsByUsername(username);
    }


    public Boolean existsByEmail(String email) {
      return userRepository.existsByEmail(email);
    }

}
