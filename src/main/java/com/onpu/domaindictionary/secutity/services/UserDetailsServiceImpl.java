package com.onpu.domaindictionary.secutity.services;

import com.onpu.domaindictionary.dao.UserDao;
import com.onpu.domaindictionary.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username);
        if (user == null) {
            AuthenticationException e = new UsernameNotFoundException("User Not Found with username: " + username);
            log.error(e.getMessage(), e);
            throw e;
        }
        log.info("User found " + user.getName());
        return UserDetailsImpl.build(user);
    }

}
