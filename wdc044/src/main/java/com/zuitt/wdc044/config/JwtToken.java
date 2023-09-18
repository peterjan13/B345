package com.zuitt.wdc044.config;
//JwtToken - contains the properties and methods needed by a JWT object.

import com.zuitt.wdc044.models.User;
import com.zuitt.wdc044.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//This class will generate the token.
//This class will also be able to unwrap your token (decode).
@Component
public class JwtToken implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserRepository userRepository;

    private static final long serialVersionUID = -4918895179396868299L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; //5 hours


    private String doGenerateToken(Map<String,Object> claims, String subject) {

        //.setClaims() allows us to set the "claims" within our token. Claims are the information contained within the token which is the username.
        //setSubject allows us to add information about the subject.
        //setIssuedAt() allows us to set the date and time when the token was created.
        //.setExpiration() allows us to set the expiration of the token.
        //.signWith() creates the token using the declared algorithm and signed with our secret key. The secret key is a passphrase that only the application knows.

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String,Object> claims = new HashMap<>();
        User user = userRepository.findByUsername(userDetails.getUsername());
        claims.put("user", user.getId());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        String claim = getClaimFromToken(token, Claims::getSubject);
        return  claim;
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }

}
