package com.Tasko.Registration.dto;

import java.util.List;

import com.Tasko.Registration.Entity.Subscription_Userdata;

public class SubscrpitonList {
    private String label;
    private List<Subscription_Userdata> counts;

    public SubscrpitonList(String label, List<Subscription_Userdata> count1) {
        this.label = label;
        this.counts = count1;
    }

    // Getter and setter methods for label and counts

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Subscription_Userdata> getCounts() {
        return counts;
    }

    public void setCounts(List<Subscription_Userdata> counts) {
        this.counts = counts;
    }
}