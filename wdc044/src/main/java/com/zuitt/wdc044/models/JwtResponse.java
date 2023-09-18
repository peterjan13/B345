package com.zuitt.wdc044.models;

import java.io.Serializable;

public class JwtResponse implements Serializable{
    private static final long serialVersionUID = 7660993934201187817L;
    private final String jwtToken;

    public JwtResponse(String jwtToken){
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return this.jwtToken;
    }
}
