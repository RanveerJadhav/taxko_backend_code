package com.Tasko.Registration.Entity;

import java.util.Optional;

public class authclient
{
    private String token;
    private Optional<Client_Registation_Form> client;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Optional<Client_Registation_Form> getClient() {
        return client;
    }

    public void setClient(Optional<Client_Registation_Form> client) {
        this.client = client;
    }

    public authclient(String token, Optional<Client_Registation_Form> client) {
        this.token = token;
        this.client = client;
    }

    public authclient() {
    }
}
