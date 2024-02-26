package com.Tasko.Registration.dto;


public class CA_SubUser_RenewPack
{
    private Long subUserId;
    private CharSequence endDate;

    public Long getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(Long subUserId) {
        this.subUserId = subUserId;
    }

    public CharSequence getEndDate() {
        return endDate;
    }

    public void setEndDate(CharSequence endDate) {
        this.endDate = endDate;
    }

    public CA_SubUser_RenewPack(Long subUserId, CharSequence endDate) {
        this.subUserId = subUserId;
        this.endDate = endDate;
    }

    public CA_SubUser_RenewPack() {
    }
}
