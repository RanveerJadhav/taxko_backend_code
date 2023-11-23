package com.Tasko.Registration.dto;

import java.util.Date;

import com.Tasko.Registration.Entity.User_RegistrationsForm;

public class CustomResponse {
    private User_RegistrationsForm registration;
    private boolean status;
    private Date substartdatebyuser;
	public User_RegistrationsForm getRegistration() {
		return registration;
	}
	public void setRegistration(User_RegistrationsForm registration) {
		this.registration = registration;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getSubstartdatebyuser() {
		return substartdatebyuser;
	}
	public void setSubstartdatebyuser(Date substartdatebyuser) {
		this.substartdatebyuser = substartdatebyuser;
	}
	@Override
	public String toString() {
		return "CustomResponse [registration=" + registration + ", status=" + status + ", substartdatebyuser="
				+ substartdatebyuser + ", getRegistration()=" + getRegistration() + ", isStatus()=" + isStatus()
				+ ", getSubstartdatebyuser()=" + getSubstartdatebyuser() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	public CustomResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomResponse(User_RegistrationsForm registration, boolean status, Date substartdatebyuser) {
		super();
		this.registration = registration;
		this.status = status;
		this.substartdatebyuser = substartdatebyuser;
	}

    

    
}
