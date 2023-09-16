package com.Tasko.Registration.dto;

import java.util.Date;

public class filed_NotfiledDTO
{
    private String accountyear;
    private Long filed;
    private Long Notfiled;

    private Date lastUpdateDate;

    public Date getLastUpdateDate(Date lastUpdateDate) {
        return this.lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getAccountyear() {
        return accountyear;
    }

    public void setAccountyear(String accountyear) {
        this.accountyear = accountyear;
    }

    public Long getFiled() {
        return filed;
    }

    public void setFiled(Long filed) {
        this.filed = filed;
    }

    public Long getNotfiled() {
        return Notfiled;
    }

    public filed_NotfiledDTO(String accountyear, Long filed, Long notfiled) {
        this.accountyear = accountyear;
        this.filed = filed;
        Notfiled = notfiled;
    }

    public void setNotfiled(Long notfiled) {
        Notfiled = notfiled;
    }

    public filed_NotfiledDTO() {
    }
}
