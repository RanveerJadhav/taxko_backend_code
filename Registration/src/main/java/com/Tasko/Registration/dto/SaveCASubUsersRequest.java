package com.Tasko.Registration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class SaveCASubUsersRequest
{
    private Long userid;
    private Long totalClients;

    private CharSequence endDate;

    private CharSequence startDate;

    private String userpan;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getTotalClients() {
        return totalClients;
    }

    public void setTotalClients(Long totalClients) {
        this.totalClients = totalClients;
    }

    public CharSequence getEndDate() {
        return endDate;
    }

    public void setEndDate(CharSequence endDate) {
        this.endDate = endDate;
    }

    public CharSequence getStartDate() {
        return startDate;
    }

    public void setStartDate(CharSequence startDate) {
        this.startDate = startDate;
    }

    public String getUserpan() {
        return userpan;
    }

    public void setUserpan(String userpan) {
        this.userpan = userpan;
    }

    public SaveCASubUsersRequest(Long userid, Long totalClients, CharSequence endDate, CharSequence startDate, String userpan) {
        this.userid = userid;
        this.totalClients = totalClients;
        this.endDate = endDate;
        this.startDate = startDate;
        this.userpan = userpan;
    }

    public SaveCASubUsersRequest() {
    }


}
