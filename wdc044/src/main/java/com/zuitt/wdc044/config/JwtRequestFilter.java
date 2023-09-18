package com.zuitt.wdc044.config;

//JwtRequestFilter - contains the implementation logic for generating JWT using the methods define in JwtToken class.

import com.zuitt.wdc044.services.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// OncePerRequestFilter is a class that guarantees a single execution per request received
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    // Dependency Injection - a concept applied to modern framework; Not all needed dependencies are loaded at once.
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    private final JwtToken jwtTokenUtil;

    public JwtRequestFilter(JwtToken jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    //Runs when a request is made to our spring boot API.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)

            throws ServletException, IOException {

        //Gets the token from the Auth header from request.
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;

        String jwtToken = null;


        if (requestTokenHeader != null) {

            //Get the token and save it as jwtToken
            jwtToken = requestTokenHeader;

            try {

                // unwraps the token to get the subject of the token which is the username of the owner of the token.
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);

            } catch (IllegalArgumentException e) {

                System.out.println("Unable to get JWT Token");

            } catch (ExpiredJwtException e) {

                System.out.println("JWT Token has expired");

            }

        } else {

            logger.warn("JWT Token is incomplete");

        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);


            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(

                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}