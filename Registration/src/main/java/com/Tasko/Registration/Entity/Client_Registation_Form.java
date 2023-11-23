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
public class Client_Registation_Form
{
	     @Id
	     @GeneratedValue(strategy = GenerationType.IDENTITY)
	     private Long clientId;
	     
	     @NotBlank(message = "Name is mandatory")
	     private String name;
	    
	     @JsonFormat(pattern="yyyy-MM-dd")
	     private Date dob;
	    
	     @NotBlank(message = "Profession is mandatory")
	     private String profession;
	     
	     @NotBlank(message = "PAN_No is mandatory")
	     private String pan;
	     private String telephone;
	     
	     @NotBlank(message ="Mobile is mandatory")
	     @Size(min = 10, max = 10)
	     private String mobile;
		 
	     private String email;
	     private String address;
	     private String pin_code;
	     private String state;
	     private String residential_status;
		
	     @NotBlank(message ="Category is mandatory")
	     private String category;
	     
	     private Long userid;
		 private String password;
		 
		   private String imageName;

		    private String imagePath;

			public Long getClientId() {
				return clientId;
			}

			public void setClientId(Long clientId) {
				this.clientId = clientId;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public Date getDob() {
				return dob;
			}

			public void setDob(Date dob) {
				this.dob = dob;
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

			public String getResidential_status() {
				return residential_status;
			}

			public void setResidential_status(String residential_status) {
				this.residential_status = residential_status;
			}

			public String getCategory() {
				return category;
			}

			public void setCategory(String category) {
				this.category = category;
			}

			public Long getUserid() {
				return userid;
			}

			public void setUserid(Long userid) {
				this.userid = userid;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
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

			public Client_Registation_Form(Long clientId, @NotBlank(message = "Name is mandatory") String name,
					Date dob, @NotBlank(message = "Profession is mandatory") String profession,
					@NotBlank(message = "PAN_No is mandatory") String pan, String telephone,
					@NotBlank(message = "Mobile is mandatory") @Size(min = 10, max = 10) String mobile, String email,
					String address, String pin_code, String state, String residential_status,
					@NotBlank(message = "Category is mandatory") String category, Long userid, String password,
					String imageName, String imagePath) {
				super();
				this.clientId = clientId;
				this.name = name;
				this.dob = dob;
				this.profession = profession;
				this.pan = pan;
				this.telephone = telephone;
				this.mobile = mobile;
				this.email = email;
				this.address = address;
				this.pin_code = pin_code;
				this.state = state;
				this.residential_status = residential_status;
				this.category = category;
				this.userid = userid;
				this.password = password;
				this.imageName = imageName;
				this.imagePath = imagePath;
			}

			public Client_Registation_Form() {
				super();
			}

			@Override
			public String toString() {
				return "Client_Registation_Form [clientId=" + clientId + ", name=" + name + ", dob=" + dob
						+ ", profession=" + profession + ", pan=" + pan + ", telephone=" + telephone + ", mobile="
						+ mobile + ", email=" + email + ", address=" + address + ", pin_code=" + pin_code + ", state="
						+ state + ", residential_status=" + residential_status + ", category=" + category + ", userid="
						+ userid + ", password=" + password + ", imageName=" + imageName + ", imagePath=" + imagePath
						+ "]";
			}
			
			
			
			
		 

}
