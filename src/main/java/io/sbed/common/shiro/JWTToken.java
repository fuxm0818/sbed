package io.sbed.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class JWTToken implements AuthenticationToken {

    private String username;
    private String password;
    private String token;
    private boolean isLoginRequest;


    public JWTToken(String username,String password,String token){
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public JWTToken(String username,String password,String token,boolean isLoginRequest){
        this.username = username;
        this.password = password;
        this.token = token;
        this.isLoginRequest = isLoginRequest;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLoginRequest() {
        return isLoginRequest;
    }

    public void setLoginRequest(boolean loginRequest) {
        isLoginRequest = loginRequest;
    }
}
