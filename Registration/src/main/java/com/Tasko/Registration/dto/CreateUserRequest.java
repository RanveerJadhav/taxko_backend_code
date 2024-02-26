package com.Tasko.Registration.dto;

import java.util.List;

import com.Tasko.Registration.Entity.User_RegistrationsForm;

public class CreateUserRequest {
    private User_RegistrationsForm user;
    private List<String> invest_now_email;
	public User_RegistrationsForm getUser() {
		return user;
	}
	public void setUser(User_RegistrationsForm user) {
		this.user = user;
	}
	public List<String> getInvest_now_email() {
		return invest_now_email;
	}
	public void setInvest_now_email(List<String> invest_now_email) {
		this.invest_now_email = invest_now_email;
	}
	@Override
	public String toString() {
		return "CreateUserRequest [user=" + user + ", invest_now_email=" + invest_now_email + ", getUser()=" + getUser()
				+ ", getInvest_now_email()=" + getInvest_now_email() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	public CreateUserRequest(User_RegistrationsForm user, List<String> invest_now_email) {
		super();
		this.user = user;
		this.invest_now_email = invest_now_email;
	}
	public CreateUserRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

   
}