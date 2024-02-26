package com.Tasko.Registration.dto;

import java.util.List;

public class clientIds
{
    private List<Long> clientIds;

   private Long subUserId;

    public List<Long> getClientIds() {
        return clientIds;
    }

    public void setClientIds(List<Long> clientIds) {
        this.clientIds = clientIds;
    }

    public Long getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(Long subUserId) {
        this.subUserId = subUserId;
    }

    public clientIds(List<Long> clientIds, Long subUserId) {
        this.clientIds = clientIds;
        this.subUserId = subUserId;
    }

    public clientIds() {
    }
}
