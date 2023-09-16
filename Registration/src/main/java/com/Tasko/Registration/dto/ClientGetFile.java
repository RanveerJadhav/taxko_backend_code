package com.Tasko.Registration.dto;

public class ClientGetFile
{
    private Long clientid;
    private String accountyear;

    public Long getClientid() {
        return clientid;
    }

    public void setClientid(Long clientid) {
        this.clientid = clientid;
    }

    public String getAccountyear() {
        return accountyear;
    }

    public void setAccountyear(String accountyear) {
        this.accountyear = accountyear;
    }

    public ClientGetFile(Long clientid, String accountyear) {
        this.clientid = clientid;
        this.accountyear = accountyear;
    }

    public ClientGetFile() {
    }
}
