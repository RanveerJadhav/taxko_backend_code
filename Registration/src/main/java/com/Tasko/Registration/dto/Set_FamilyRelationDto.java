package com.Tasko.Registration.dto;


import java.util.List;

public class Set_FamilyRelationDto
{
    private List<Long> primaryclientIds;

    private String setPrimaryRelation;

    private List<Long> secondaryclientIds;

    private String setSecondaryRelation;

    public List<Long> getPrimaryclientIds() {
        return primaryclientIds;
    }

    public void setPrimaryclientIds(List<Long> primaryclientIds) {
        this.primaryclientIds = primaryclientIds;
    }

    public String getSetPrimaryRelation() {
        return setPrimaryRelation;
    }

    public void setSetPrimaryRelation(String setPrimaryRelation) {
        this.setPrimaryRelation = setPrimaryRelation;
    }

    public List<Long> getSecondaryclientIds() {
        return secondaryclientIds;
    }

    public void setSecondaryclientIds(List<Long> secondaryclientIds) {
        this.secondaryclientIds = secondaryclientIds;
    }

    public String getSetSecondaryRelation() {
        return setSecondaryRelation;
    }

    public void setSetSecondaryRelation(String setSecondaryRelation) {
        this.setSecondaryRelation = setSecondaryRelation;
    }

    public Set_FamilyRelationDto(List<Long> primaryclientIds, String setPrimaryRelation, List<Long> secondaryclientIds, String setSecondaryRelation) {
        this.primaryclientIds = primaryclientIds;
        this.setPrimaryRelation = setPrimaryRelation;
        this.secondaryclientIds = secondaryclientIds;
        this.setSecondaryRelation = setSecondaryRelation;
    }
}
