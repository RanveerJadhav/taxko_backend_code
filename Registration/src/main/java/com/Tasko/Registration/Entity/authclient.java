package com.Tasko.Registration.Entity;

import java.util.Optional;

public class authclient
{
    private String token;
    private Optional<ClientPass_Imgdetail> client;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Optional<ClientPass_Imgdetail> getClient() {
        return client;
    }

    public void setClient(Optional<ClientPass_Imgdetail> client2) {
        this.client = client2;
    }

    public authclient(String token, Optional<ClientPass_Imgdetail> client) {
        this.token = token;
        this.client = client;
    }

    public authclient() {
    }
}
