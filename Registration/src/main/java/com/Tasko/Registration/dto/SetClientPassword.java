package com.Tasko.Registration.dto;

public class SetClientPassword
{
    private String pan;
    private String newPassword;

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public SetClientPassword(String pan, String newPassword) {
        this.pan = pan;
        this.newPassword = newPassword;
    }

    public SetClientPassword() {
    }
}
