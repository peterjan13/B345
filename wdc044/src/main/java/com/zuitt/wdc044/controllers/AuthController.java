package com.zuitt.wdc044.controllers;

import com.zuitt.wdc044.config.JwtToken;
import com.zuitt.wdc044.models.JwtRequest;
import com.zuitt.wdc044.models.JwtResponse;
import com.zuitt.wdc044.services.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

//@RestController - to tell the application this is a REST API controller
//@CrossOrigin - it disables CORS.
@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        //load the username with the jwtUserDetailsService to add the user in the spring security scheme
        //then the userDetails objects to create the token
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        //Generation of Token
        final String token = jwtToken.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}