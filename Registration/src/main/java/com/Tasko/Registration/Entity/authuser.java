package com.Tasko.Registration.Entity;


import java.util.Date;
import java.util.Optional;

public class authuser
{

	private String token;
	private Optional<User_RegistrationsForm> user;

	private Optional<CA_SubUsers>Sub_user;
	private Date subenddate;	
	private boolean value;

	public authuser(User_RegistrationsForm userRegistrationsForm, String jwt, Subscription_Userdata infoData, Optional<CA_SubUsers> caSubuser, boolean isPaid) {
	}

	public Optional<CA_SubUsers> getSub_user() {
		return Sub_user;
	}

	public void setSub_user(Optional<CA_SubUsers> sub_user) {
		Sub_user = sub_user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Optional<User_RegistrationsForm> getUser() {
		return user;
	}

	public void setUser(Optional<User_RegistrationsForm> user) {
		this.user = user;
	}

	public Date getSubenddate() {
		return subenddate;
	}

	public void setSubenddate(Date subenddate) {
		this.subenddate = subenddate;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public authuser(Optional<User_RegistrationsForm> user, String token, Date subenddate, boolean value) {
		super();
		this.token = token;
		this.user = user;
		this.subenddate = subenddate;
		this.value = value;
	}

	public authuser(Optional<CA_SubUsers> sub_user) {
		Sub_user = sub_user;
	}
}

