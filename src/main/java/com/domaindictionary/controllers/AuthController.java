package com.domaindictionary.controllers;

import com.domaindictionary.model.JwtResponse;
import com.domaindictionary.model.LoginRequest;
import com.domaindictionary.model.User;
import com.domaindictionary.model.enumeration.Role;
import com.domaindictionary.secutity.jwt.JwtUtils;
import com.domaindictionary.secutity.services.UserDetailsImpl;
import com.domaindictionary.secutity.services.UserDetailsServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = Logger.getLogger(AuthController.class.getName());
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  UserDetailsServiceImpl userDetailsService;
    @Autowired
    private  JwtUtils jwtUtils;

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
        if (userDetailsService.existsByUsername(signUpRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userDetailsService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(BigInteger.ONE, signUpRequest.getName(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(), signUpRequest.getRole());
        userDetailsService.save(user);
        try {
            return (ResponseEntity<?>) ResponseEntity.ok();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @GetMapping("{number}")
    public User getUserById(@PathVariable @NotNull BigInteger number) {
        User user = userDetailsService.getUserById(number);
        return user;
    }
}
