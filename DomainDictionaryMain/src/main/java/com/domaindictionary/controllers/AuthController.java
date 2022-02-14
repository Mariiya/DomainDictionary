package com.domaindictionary.controllers;

import com.domaindictionary.dao.UserDao;
import com.domaindictionary.model.enumeration.Role;
import com.domaindictionary.secutity.jwt.JwtUtils;
import com.domaindictionary.secutity.services.UserDetailsImpl;
import com.domaindictionary.model.JwtResponse;
import com.domaindictionary.model.LoginRequest;
import com.domaindictionary.model.MessageResponse;
import com.domaindictionary.model.User;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = Logger.getLogger(AuthController.class.getName());
    private final AuthenticationManager authenticationManager;

    private final UserDao userDao;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserDao userDao, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.debug("/signin " + loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getNameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Role role;
        String roleStr = String.valueOf(userDetails.getAuthorities());

        if ("[ROLE_EXPERT]".equals(roleStr)) {
            role = Role.ROLE_EXPERT;
        } else {
            role = Role.ROLE_USER;
        }
        return ResponseEntity.ok(new JwtResponse(jwt, new User(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getEmail(),
                role)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User signUpRequest) {
        if (userDao.existsByUsername(signUpRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userDao.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(BigInteger.ONE, signUpRequest.getName(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(), signUpRequest.getRole());
        userDao.save(user);
        try {
            return ResponseEntity.ok(new MessageResponse());
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}