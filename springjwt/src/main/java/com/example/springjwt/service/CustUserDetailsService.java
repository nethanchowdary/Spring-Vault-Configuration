package com.example.springjwt.service;


import com.example.springjwt.entity.Users;
import com.example.springjwt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = repo.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(),new ArrayList<>());
    }
}
