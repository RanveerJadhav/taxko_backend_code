package com.Tasko.Registration.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Distributor_paymentdetail {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private  Long id;
	    
	    private String  pan;

	    private String imageNameprofile;

	    private String imagePathProfile;
	    
	    private String imageNameAdhar;

	    private String imagePathAdhar;
	    
	    private String imageNamepan;

	    private String imagePathpan;
	    
	    private String imageNamecheque;

	    private String imagePathcheque;

	    private String bank_name;

	    private String accountName;

	    private Long accountNumber;

	    @Column(name = "IFSC")
	    private String ifsc;

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

		public String getImageNameprofile() {
			return imageNameprofile;
		}

		public void setImageNameprofile(String imageNameprofile) {
			this.imageNameprofile = imageNameprofile;
		}

		public String getImagePathProfile() {
			return imagePathProfile;
		}

		public void setImagePathProfile(String imagePathProfile) {
			this.imagePathProfile = imagePathProfile;
		}

		public String getImageNameAdhar() {
			return imageNameAdhar;
		}

		public void setImageNameAdhar(String imageNameAdhar) {
			this.imageNameAdhar = imageNameAdhar;
		}

		public String getImagePathAdhar() {
			return imagePathAdhar;
		}

		public void setImagePathAdhar(String imagePathAdhar) {
			this.imagePathAdhar = imagePathAdhar;
		}

		public String getImageNamepan() {
			return imageNamepan;
		}

		public void setImageNamepan(String imageNamepan) {
			this.imageNamepan = imageNamepan;
		}

		public String getImagePathpan() {
			return imagePathpan;
		}

		public void setImagePathpan(String imagePathpan) {
			this.imagePathpan = imagePathpan;
		}

		public String getImageNamecheque() {
			return imageNamecheque;
		}

		public void setImageNamecheque(String imageNamecheque) {
			this.imageNamecheque = imageNamecheque;
		}

		public String getImagePathcheque() {
			return imagePathcheque;
		}

		public void setImagePathcheque(String imagePathcheque) {
			this.imagePathcheque = imagePathcheque;
		}

		public String getBank_name() {
			return bank_name;
		}

		public void setBank_name(String bank_name) {
			this.bank_name = bank_name;
		}

		public String getAccountName() {
			return accountName;
		}

		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}

		public Long getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(Long accountNumber) {
			this.accountNumber = accountNumber;
		}

		public String getIfsc() {
			return ifsc;
		}

		public void setIfsc(String ifsc) {
			this.ifsc = ifsc;
		}

		@Override
		public String toString() {
			return "Distributor_paymentdetail [id=" + id + ", pan=" + pan + ", imageNameprofile=" + imageNameprofile
					+ ", imagePathProfile=" + imagePathProfile + ", imageNameAdhar=" + imageNameAdhar
					+ ", imagePathAdhar=" + imagePathAdhar + ", imageNamepan=" + imageNamepan + ", imagePathpan="
					+ imagePathpan + ", imageNamecheque=" + imageNamecheque + ", imagePathcheque=" + imagePathcheque
					+ ", bank_name=" + bank_name + ", accountName=" + accountName + ", accountNumber=" + accountNumber
					+ ", ifsc=" + ifsc + ", getId()=" + getId() + ", getPan()=" + getPan() + ", getImageNameprofile()="
					+ getImageNameprofile() + ", getImagePathProfile()=" + getImagePathProfile()
					+ ", getImageNameAdhar()=" + getImageNameAdhar() + ", getImagePathAdhar()=" + getImagePathAdhar()
					+ ", getImageNamepan()=" + getImageNamepan() + ", getImagePathpan()=" + getImagePathpan()
					+ ", getImageNamecheque()=" + getImageNamecheque() + ", getImagePathcheque()="
					+ getImagePathcheque() + ", getBank_name()=" + getBank_name() + ", getAccountName()="
					+ getAccountName() + ", getAccountNumber()=" + getAccountNumber() + ", getIfsc()=" + getIfsc()
					+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
					+ "]";
		}

		public Distributor_paymentdetail(Long id, String pan, String imageNameprofile, String imagePathProfile,
				String imageNameAdhar, String imagePathAdhar, String imageNamepan, String imagePathpan,
				String imageNamecheque, String imagePathcheque, String bank_name, String accountName,
				Long accountNumber, String ifsc) {
			super();
			this.id = id;
			this.pan = pan;
			this.imageNameprofile = imageNameprofile;
			this.imagePathProfile = imagePathProfile;
			this.imageNameAdhar = imageNameAdhar;
			this.imagePathAdhar = imagePathAdhar;
			this.imageNamepan = imageNamepan;
			this.imagePathpan = imagePathpan;
			this.imageNamecheque = imageNamecheque;
			this.imagePathcheque = imagePathcheque;
			this.bank_name = bank_name;
			this.accountName = accountName;
			this.accountNumber = accountNumber;
			this.ifsc = ifsc;
		}

		public Distributor_paymentdetail() {
			super();
			// TODO Auto-generated constructor stub
		}

		

		
		
	    
	    
	    
	    
	    
}
