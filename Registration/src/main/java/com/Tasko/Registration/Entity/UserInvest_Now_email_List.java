package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class UserInvest_Now_email_List {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	 
	private String pan;
	 
	private String investNow_Email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getInvestNow_Email() {
		return investNow_Email;
	}

	public void setInvestNow_Email(String investNow_Email) {
		this.investNow_Email = investNow_Email;
	}

	@Override
	public String toString() {
		return "UserInvest_Now_email_List [id=" + id + ", pan=" + pan + ", investNow_Email=" + investNow_Email
				+ ", getId()=" + getId() + ", getPan()=" + getPan() + ", getInvestNow_Email()=" + getInvestNow_Email()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public UserInvest_Now_email_List(Long id, String pan, String investNow_Email) {
		super();
		this.id = id;
		this.pan = pan;
		this.investNow_Email = investNow_Email;
	}

	public UserInvest_Now_email_List() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
