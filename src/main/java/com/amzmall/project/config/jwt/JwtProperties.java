package com.amzmall.project.config.jwt;

public interface JwtProperties {
    String SECRET = "Popcon";
    int EXPIRATION_TIME =  864000000; //60000 1분 //864000000 10일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}