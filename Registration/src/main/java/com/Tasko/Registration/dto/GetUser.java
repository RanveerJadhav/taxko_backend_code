package com.Tasko.Registration.dto;

public class GetUser
{
    private Long regId;

    public Long getRegId() {
        return regId;
    }

    public void setRegId(Long regId) {
        this.regId = regId;
    }

    public GetUser(Long regId) {
        this.regId = regId;
    }
}
