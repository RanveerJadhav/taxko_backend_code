package com.Tasko.Registration.dto;

import java.util.Date;

import com.Tasko.Registration.Entity.User_RegistrationsForm;

public class RegistrationWithrecordResponse {
	private User_RegistrationsForm registration;
	private Date subendtdate;
	public User_RegistrationsForm getRegistration() {
		return registration;
	}
	public void setRegistration(User_RegistrationsForm registration) {
		this.registration = registration;
	}
	public Date getSubendtdate() {
		return subendtdate;
	}
	public void setSubendtdate(Date subendtdate) {
		this.subendtdate = subendtdate;
	}
	@Override
	public String toString() {
		return "RegistrationWithrecordResponse [registration=" + registration + ", subendtdate=" + subendtdate
				+ ", getRegistration()=" + getRegistration() + ", getSubendtdate()=" + getSubendtdate()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	public RegistrationWithrecordResponse(User_RegistrationsForm registration, Date subendtdate) {
		super();
		this.registration = registration;
		this.subendtdate = subendtdate;
	}
	public RegistrationWithrecordResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
