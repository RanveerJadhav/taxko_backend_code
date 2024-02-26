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
public class Salesmanager_register_disactivate_salesmanager{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
	
	 private Long salesmanid;

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

	public Long getSalesmanid() {
		return salesmanid;
	}

	public String getName() {
		return name;
	}

	public Date getDatebirth() {
		return datebirth;
	}

	public String getProfession() {
		return profession;
	}

	public String getPan() {
		return pan;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public String getPin_code() {
		return pin_code;
	}

	public String getState() {
		return state;
	}

	public String getWhatsApp_Link() {
		return whatsApp_Link;
	}

	public boolean isStatus() {
		return status;
	}

	public Date getRegistrationdate() {
		return registrationdate;
	}

	public String getPassword() {
		return password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSalesmanid(Long salesmanid) {
		this.salesmanid = salesmanid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDatebirth(Date datebirth) {
		this.datebirth = datebirth;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPin_code(String pin_code) {
		this.pin_code = pin_code;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setWhatsApp_Link(String whatsApp_Link) {
		this.whatsApp_Link = whatsApp_Link;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Salesmanager_register_disactivate_salesmanager [id=");
		builder.append(id);
		builder.append(", salesmanid=");
		builder.append(salesmanid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", datebirth=");
		builder.append(datebirth);
		builder.append(", profession=");
		builder.append(profession);
		builder.append(", pan=");
		builder.append(pan);
		builder.append(", telephone=");
		builder.append(telephone);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", email=");
		builder.append(email);
		builder.append(", address=");
		builder.append(address);
		builder.append(", pin_code=");
		builder.append(pin_code);
		builder.append(", state=");
		builder.append(state);
		builder.append(", whatsApp_Link=");
		builder.append(whatsApp_Link);
		builder.append(", status=");
		builder.append(status);
		builder.append(", registrationdate=");
		builder.append(registrationdate);
		builder.append(", password=");
		builder.append(password);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getSalesmanid()=");
		builder.append(getSalesmanid());
		builder.append(", getName()=");
		builder.append(getName());
		builder.append(", getDatebirth()=");
		builder.append(getDatebirth());
		builder.append(", getProfession()=");
		builder.append(getProfession());
		builder.append(", getPan()=");
		builder.append(getPan());
		builder.append(", getTelephone()=");
		builder.append(getTelephone());
		builder.append(", getMobile()=");
		builder.append(getMobile());
		builder.append(", getEmail()=");
		builder.append(getEmail());
		builder.append(", getAddress()=");
		builder.append(getAddress());
		builder.append(", getPin_code()=");
		builder.append(getPin_code());
		builder.append(", getState()=");
		builder.append(getState());
		builder.append(", getWhatsApp_Link()=");
		builder.append(getWhatsApp_Link());
		builder.append(", isStatus()=");
		builder.append(isStatus());
		builder.append(", getRegistrationdate()=");
		builder.append(getRegistrationdate());
		builder.append(", getPassword()=");
		builder.append(getPassword());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	public Salesmanager_register_disactivate_salesmanager(Long id, Long salesmanid,
			@NotBlank(message = "Name is mandatory") String name, Date datebirth,
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
		this.salesmanid = salesmanid;
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

	public Salesmanager_register_disactivate_salesmanager() {
		super();
		// TODO Auto-generated constructor stub
	}
     
     
     
}
