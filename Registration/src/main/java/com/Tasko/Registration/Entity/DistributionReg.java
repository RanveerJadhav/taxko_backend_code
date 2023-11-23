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
public class DistributionReg 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

	@NotBlank(message = "Name is mandatory")
	private String name;
	
	 @JsonFormat(pattern="yyyy-MM-dd")
	 private Date datebirth;

     @NotBlank(message = "Profession is mandatory")
     private String profession;

	 @NotBlank(message = "PAN_No is mandatory")
     @Column(unique=true)
     private String pan;

	 private String telephone;
	 
     @NotBlank(message ="Mobile is mandatory")
     @Size(min = 10,max = 10)
	 private String mobile;

     @NotBlank(message ="Email is mandatory")
	 @Column(unique=true)
     private String email;

     @NotBlank(message ="Address is mandatory")
     private String address;

     @NotBlank(message ="Pin Code is mandatory")
	 private String pin_code;

     @NotBlank(message ="State is mandatory")
     private String state;

     private String whatsApp_Link;
     
     private boolean status;
     
     @Column(name ="registrationdate")
     @JsonFormat(pattern="yyyy-MM-dd")
     private Date registrationdate;
     
     @NotBlank(message = "Password is mandatory")
     private  String password;

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

	public Date getDatebirth() {
		return datebirth;
	}

	public void setDatebirth(Date datebirth) {
		this.datebirth = datebirth;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getRegistrationdate() {
		return registrationdate;
	}

	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "DistributionReg [id=" + id + ", name=" + name + ", datebirth=" + datebirth + ", profession="
				+ profession + ", pan=" + pan + ", telephone=" + telephone + ", mobile=" + mobile + ", email=" + email
				+ ", address=" + address + ", pin_code=" + pin_code + ", state=" + state + ", whatsApp_Link="
				+ whatsApp_Link + ", status=" + status + ", registrationdate=" + registrationdate + ", password="
				+ password + ", getId()=" + getId() + ", getName()=" + getName() + ", getDatebirth()=" + getDatebirth()
				+ ", getProfession()=" + getProfession() + ", getPan()=" + getPan() + ", getTelephone()="
				+ getTelephone() + ", getMobile()=" + getMobile() + ", getEmail()=" + getEmail() + ", getAddress()="
				+ getAddress() + ", getPin_code()=" + getPin_code() + ", getState()=" + getState()
				+ ", getWhatsApp_Link()=" + getWhatsApp_Link() + ", isStatus()=" + isStatus()
				+ ", getRegistrationdate()=" + getRegistrationdate() + ", getPassword()=" + getPassword()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public DistributionReg(Long id, @NotBlank(message = "Name is mandatory") String name, Date datebirth,
			@NotBlank(message = "Profession is mandatory") String profession,
			@NotBlank(message = "PAN_No is mandatory") String pan, String telephone,
			@NotBlank(message = "Mobile is mandatory") @Size(min = 10, max = 10) String mobile,
			@NotBlank(message = "Email is mandatory") String email,
			@NotBlank(message = "Address is mandatory") String address,
			@NotBlank(message = "Pin Code is mandatory") String pin_code,
			@NotBlank(message = "State is mandatory") String state, String whatsApp_Link, boolean status,
			Date registrationdate, @NotBlank(message = "Password is mandatory") String password) {
		super();
		this.id = id;
		this.name = name;
		this.datebirth = datebirth;
		this.profession = profession;
		this.pan = pan;
		this.telephone = telephone;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
		this.pin_code = pin_code;
		this.state = state;
		this.whatsApp_Link = whatsApp_Link;
		this.status = status;
		this.registrationdate = registrationdate;
		this.password = password;
	}

	public DistributionReg() {
		super();
		// TODO Auto-generated constructor stub
	}

     
     
}
