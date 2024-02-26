package com.Tasko.Registration.Entity;

import java.util.Optional;

public class authclient
{
    private String token;
    private String client;

    private String tempClient;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTempClient() {
        return tempClient;
    }

    public void setTempClient(String tempClient) {
        this.tempClient = tempClient;
    }

    public authclient(String token, String client, String tempClient) {
        this.token = token;
        this.client = client;
        this.tempClient = tempClient;
    }

    public authclient() {

    }

    @Override
    public String toString() {
        return "authclient{" +
                "token='" + token + '\'' +
                ", client='" + client + '\'' +
                ", tempClient='" + tempClient + '\'' +
                '}';
    }
}
