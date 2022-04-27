package com.example.springjwt.controller;

import com.example.springjwt.dto.RequestData;
import com.example.springjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String home(){
        return "Hola Authenticated!!! Welcome!";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody RequestData data) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getUsername(),data.getPassword()));
        }catch (Exception exe){
            throw new Exception("Invalid Credentials");
        }
        return jwtUtil.generateToken(data.getUsername());
    }
}
