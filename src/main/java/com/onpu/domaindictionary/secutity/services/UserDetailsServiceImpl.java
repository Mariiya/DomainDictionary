package com.onpu.domaindictionary.secutity.services;

import com.onpu.domaindictionary.dao.UserDao;
import com.onpu.domaindictionary.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
