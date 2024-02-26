package com.Tasko.Registration.dto;

import java.util.Date;

import com.Tasko.Registration.Entity.User_RegistrationsForm;

public class RegistrationWithCountResponse {
	private User_RegistrationsForm registration;
	private Long count;

	private Long countSubuser;
	private boolean status;
	private Date substartdatebyuser;
	private Date subendtdate;

	private boolean forceStopStatus;

	private String subscriptiontype;

	public RegistrationWithCountResponse(User_RegistrationsForm registration, Long count, boolean status, Date a, Date b) {
	}

	public User_RegistrationsForm getRegistration() {
		return registration;
	}

	public void setRegistration(User_RegistrationsForm registration) {
		this.registration = registration;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getCountSubuser() {
		return countSubuser;
	}

	public void setCountSubuser(Long countSubuser) {
		this.countSubuser = countSubuser;
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

	public Date getSubendtdate() {
		return subendtdate;
	}

	public void setSubendtdate(Date subendtdate) {
		this.subendtdate = subendtdate;
	}

	public boolean isForceStopStatus() {
		return forceStopStatus;
	}

	public void setForceStopStatus(boolean forceStopStatus) {
		this.forceStopStatus = forceStopStatus;
	}

	public String getSubscriptiontype() {
		return subscriptiontype;
	}

	public void setSubscriptiontype(String subscriptiontype) {
		this.subscriptiontype = subscriptiontype;
	}

	public RegistrationWithCountResponse(User_RegistrationsForm registration, Long count, Long countSubuser, boolean status, Date substartdatebyuser, Date subendtdate, boolean forceStopStatus, String subscriptiontype) {
		this.registration = registration;
		this.count = count;
		this.countSubuser = countSubuser;
		this.status = status;
		this.substartdatebyuser = substartdatebyuser;
		this.subendtdate = subendtdate;
		this.forceStopStatus = forceStopStatus;
		this.subscriptiontype = subscriptiontype;
	}

	@Override
	public String toString() {
		return "RegistrationWithCountResponse{" +
				"registration=" + registration +
				", count=" + count +
				", countSubuser=" + countSubuser +
				", status=" + status +
				", substartdatebyuser=" + substartdatebyuser +
				", subendtdate=" + subendtdate +
				", forceStopStatus=" + forceStopStatus +
				", subscriptiontype='" + subscriptiontype + '\'' +
				'}';
	}

	public RegistrationWithCountResponse() {
		super();
		// TODO Auto-generated constructor stub
	}







}
