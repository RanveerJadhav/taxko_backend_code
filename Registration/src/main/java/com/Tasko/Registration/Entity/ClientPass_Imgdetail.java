package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
@Entity
public class ClientPass_Imgdetail {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
    @Column(unique=true)
	private String pan;	
	
	private String email;
	
	private String otp;
	
	private String imageName;

	private String imagePath;
	private  String password;
	@Column(name = "last_update_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastUpdateDate;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	@Override
	public String toString() {
		return "ClientPass_Imgdetail [id=" + id + ", pan=" + pan + ", email=" + email + ", otp=" + otp + ", imageName="
				+ imageName + ", imagePath=" + imagePath + ", password=" + password + ", lastUpdateDate="
				+ lastUpdateDate + ", getId()=" + getId() + ", getPan()=" + getPan() + ", getEmail()=" + getEmail()
				+ ", getOtp()=" + getOtp() + ", getImageName()=" + getImageName() + ", getImagePath()=" + getImagePath()
				+ ", getPassword()=" + getPassword() + ", getLastUpdateDate()=" + getLastUpdateDate() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	public ClientPass_Imgdetail(Long id, String pan, String email, String otp, String imageName, String imagePath,
			String password, Date lastUpdateDate) {
		super();
		this.id = id;
		this.pan = pan;
		this.email = email;
		this.otp = otp;
		this.imageName = imageName;
		this.imagePath = imagePath;
		this.password = password;
		this.lastUpdateDate = lastUpdateDate;
	}
	public ClientPass_Imgdetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
