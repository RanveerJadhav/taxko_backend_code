package com.Tasko.Registration.dto;

public class AuthRequestClient
{
    private String clientusername ;
    private String Clientpassword;

    public String getClientusername() {
        return clientusername;
    }

    public void setClientusername(String clientusername) {
        this.clientusername = clientusername;
    }

    public String getClientpassword() {
        return Clientpassword;
    }

    public void setClientpassword(String clientpassword) {
        Clientpassword = clientpassword;
    }

    public AuthRequestClient(String clientusername, String clientpassword) {
        this.clientusername = clientusername;
        Clientpassword = clientpassword;
    }

    public AuthRequestClient() {
    }

}
