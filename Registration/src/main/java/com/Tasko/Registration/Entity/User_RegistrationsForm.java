package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@Entity
public class User_RegistrationsForm 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long regId;

	@NotBlank(message = "Name is mandatory")
	private String name;

	 @JsonFormat(pattern="yyyy-MM-dd")
	 private Date datebirth;

     private Long membership_No;

     @NotBlank(message = "Profession is mandatory")
     private String profession;

	 @NotBlank(message = "PAN_No is mandatory")
     @Column(unique=true)
     private String pan;

	 private String telephone;
     @NotBlank(message ="Mobile is mandatory")
     @Size(min = 10,max = 10)

	 private String mobile;

     //@jakarta.validation.constraints.Email(message = "Enter valid Email")
	 @Column(unique=true)
     private String email;

     private String office_Address;

	 private String pin_code;

     private String state;

     private String whatsApp_Link;

     private String investNow_Email;
     
     @NotBlank(message = "Password is mandatory")
     private  String password;

	private String otp;
	
	private String refrenceId;
	
	private  String disrefrenceId;

	public Long getRegId() {
		return regId;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDatebirth() {
		return datebirth;
	}

	public void setDatebirth(Date datebirth) {
		this.datebirth = datebirth;
	}

	public Long getMembership_No() {
		return membership_No;
	}

	public void setMembership_No(Long membership_No) {
		this.membership_No = membership_No;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOffice_Address() {
		return office_Address;
	}

	public void setOffice_Address(String office_Address) {
		this.office_Address = office_Address;
	}

	public String getPin_code() {
		return pin_code;
	}

	public void setPin_code(String pin_code) {
		this.pin_code = pin_code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWhatsApp_Link() {
		return whatsApp_Link;
	}

	public void setWhatsApp_Link(String whatsApp_Link) {
		this.whatsApp_Link = whatsApp_Link;
	}

	public String getInvestNow_Email() {
		return investNow_Email;
	}

	public void setInvestNow_Email(String investNow_Email) {
		this.investNow_Email = investNow_Email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getRefrenceId() {
		return refrenceId;
	}

	public void setRefrenceId(String refrenceId) {
		this.refrenceId = refrenceId;
	}

	public String getDisrefrenceId() {
		return disrefrenceId;
	}

	public void setDisrefrenceId(String disrefrenceId) {
		this.disrefrenceId = disrefrenceId;
	}

	@Override
	public String toString() {
		return "User_RegistrationsForm [regId=" + regId + ", name=" + name + ", datebirth=" + datebirth
				+ ", membership_No=" + membership_No + ", profession=" + profession + ", pan=" + pan + ", telephone="
				+ telephone + ", mobile=" + mobile + ", email=" + email + ", office_Address=" + office_Address
				+ ", pin_code=" + pin_code + ", state=" + state + ", whatsApp_Link=" + whatsApp_Link
				+ ", investNow_Email=" + investNow_Email + ", password=" + password + ", otp=" + otp + ", refrenceId="
				+ refrenceId + ", disrefrenceId=" + disrefrenceId + ", getRegId()=" + getRegId() + ", getName()="
				+ getName() + ", getDatebirth()=" + getDatebirth() + ", getMembership_No()=" + getMembership_No()
				+ ", getProfession()=" + getProfession() + ", getPan()=" + getPan() + ", getTelephone()="
				+ getTelephone() + ", getMobile()=" + getMobile() + ", getEmail()=" + getEmail()
				+ ", getOffice_Address()=" + getOffice_Address() + ", getPin_code()=" + getPin_code() + ", getState()="
				+ getState() + ", getWhatsApp_Link()=" + getWhatsApp_Link() + ", getInvestNow_Email()="
				+ getInvestNow_Email() + ", getPassword()=" + getPassword() + ", getOtp()=" + getOtp()
				+ ", getRefrenceId()=" + getRefrenceId() + ", getDisrefrenceId()=" + getDisrefrenceId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public User_RegistrationsForm(Long regId, @NotBlank(message = "Name is mandatory") String name, Date datebirth,
			Long membership_No, @NotBlank(message = "Profession is mandatory") String profession,
			@NotBlank(message = "PAN_No is mandatory") String pan, String telephone,
			@NotBlank(message = "Mobile is mandatory") @Size(min = 10, max = 10) String mobile, String email,
			String office_Address, String pin_code, String state, String whatsApp_Link, String investNow_Email,
			@NotBlank(message = "Password is mandatory") String password, String otp, String refrenceId,
			String disrefrenceId) {
		super();
		this.regId = regId;
		this.name = name;
		this.datebirth = datebirth;
		this.membership_No = membership_No;
		this.profession = profession;
		this.pan = pan;
		this.telephone = telephone;
		this.mobile = mobile;
		this.email = email;
		this.office_Address = office_Address;
		this.pin_code = pin_code;
		this.state = state;
		this.whatsApp_Link = whatsApp_Link;
		this.investNow_Email = investNow_Email;
		this.password = password;
		this.otp = otp;
		this.refrenceId = refrenceId;
		this.disrefrenceId = disrefrenceId;
	}

	public User_RegistrationsForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
}
