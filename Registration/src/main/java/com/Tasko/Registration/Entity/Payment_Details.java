package com.Tasko.Registration.Entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
public class Payment_Details
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private Long userid;

    private String imageName;

    private String imagePath;


    @Column(name = "QR_Code",  length = 100000)
    private byte[] qrCode;

    @Column(name = "UPI_ID")
    private String upiId;

    private String bank_name;

    private String accountName;

    private Long accountNumber;

    @Column(name = "IFSC")
    private String ifsc;
    
    private String upiNumber;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
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

	public byte[] getQrCode() {
		return qrCode;
	}

	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
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

	public String getUpiNumber() {
		return upiNumber;
	}

	public void setUpiNumber(String upiNumber) {
		this.upiNumber = upiNumber;
	}

	public Payment_Details(Long id, Long userid, String imageName, String imagePath, byte[] qrCode, String upiId,
			String bank_name, String accountName, Long accountNumber, String ifsc, String upiNumber) {
		super();
		this.id = id;
		this.userid = userid;
		this.imageName = imageName;
		this.imagePath = imagePath;
		this.qrCode = qrCode;
		this.upiId = upiId;
		this.bank_name = bank_name;
		this.accountName = accountName;
		this.accountNumber = accountNumber;
		this.ifsc = ifsc;
		this.upiNumber = upiNumber;
	}

	public Payment_Details() {
		super();
	}

	@Override
	public String toString() {
		return "Payment_Details [id=" + id + ", userid=" + userid + ", imageName=" + imageName + ", imagePath="
				+ imagePath + ", qrCode=" + Arrays.toString(qrCode) + ", upiId=" + upiId + ", bank_name=" + bank_name
				+ ", accountName=" + accountName + ", accountNumber=" + accountNumber + ", ifsc=" + ifsc
				+ ", upiNumber=" + upiNumber + "]";
	}
    
    

   
}
