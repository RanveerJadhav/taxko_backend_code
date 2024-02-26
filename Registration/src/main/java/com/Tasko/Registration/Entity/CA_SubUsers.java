package com.Tasko.Registration.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
public class CA_SubUsers
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique=true)
    private String pan;

    @Column(unique=true)
    private String email;

    private String mobile;

    private String password;

    private Long userid;

    private boolean status;

    private String otp;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String userPan;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public String getUserPan() {
		return userPan;
	}

	public void setUserPan(String userPan) {
		this.userPan = userPan;
	}

	public CA_SubUsers(Long id, String name, String pan, String email, String mobile, String password, Long userid,
			boolean status, String otp, LocalDateTime startDate, LocalDateTime endDate, String userPan) {
		super();
		this.id = id;
		this.name = name;
		this.pan = pan;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.userid = userid;
		this.status = status;
		this.otp = otp;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userPan = userPan;
	}

	public CA_SubUsers() {
		super();
	}

	@Override
	public String toString() {
		return "CA_SubUsers [id=" + id + ", name=" + name + ", pan=" + pan + ", email=" + email + ", mobile=" + mobile
				+ ", password=" + password + ", userid=" + userid + ", status=" + status + ", otp=" + otp
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", userPan=" + userPan + "]";
	}

    

}

