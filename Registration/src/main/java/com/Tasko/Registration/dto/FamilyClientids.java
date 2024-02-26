package com.Tasko.Registration.dto;

import java.util.List;

public class FamilyClientids
{
    private List<Long> clientIds;

    private String familyIds;

    public List<Long> getClientIds() {
        return clientIds;
    }

    public void setClientIds(List<Long> clientIds) {
        this.clientIds = clientIds;
    }

    public String getFamilyIds() {
        return familyIds;
    }

    public void setFamilyIds(String familyIds) {
        this.familyIds = familyIds;
    }

    public FamilyClientids(List<Long> clientIds, String familyIds) {
        this.clientIds = clientIds;
        this.familyIds = familyIds;
    }

    public FamilyClientids() {
    }
}
