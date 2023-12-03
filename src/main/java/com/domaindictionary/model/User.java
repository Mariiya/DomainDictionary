package com.domaindictionary.model;

import com.domaindictionary.model.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private BigInteger id;
    private String name;
    private String email;
    private String password;
    private Role role;
}
