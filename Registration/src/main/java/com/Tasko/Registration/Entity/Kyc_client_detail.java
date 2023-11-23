package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Kyc_client_detail { 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
	private String pan;
	@Column(name = "last_update_date1")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastUpdateDate1;
	@Column(name = "last_update_date2")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastUpdateDate2;
	@Column(name = "last_update_date3")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastUpdateDate3;
	
	private String imageName;

	private String imagePath;
	
	private String imageName2;

	private String imagePath2;
	
	private String imageName3;

	private String imagePath3;

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

	public Date getLastUpdateDate1() {
		return lastUpdateDate1;
	}

	public void setLastUpdateDate1(Date lastUpdateDate1) {
		this.lastUpdateDate1 = lastUpdateDate1;
	}

	public Date getLastUpdateDate2() {
		return lastUpdateDate2;
	}

	public void setLastUpdateDate2(Date lastUpdateDate2) {
		this.lastUpdateDate2 = lastUpdateDate2;
	}

	public Date getLastUpdateDate3() {
		return lastUpdateDate3;
	}

	public void setLastUpdateDate3(Date lastUpdateDate3) {
		this.lastUpdateDate3 = lastUpdateDate3;
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

	public String getImageName2() {
		return imageName2;
	}

	public void setImageName2(String imageName2) {
		this.imageName2 = imageName2;
	}

	public String getImagePath2() {
		return imagePath2;
	}

	public void setImagePath2(String imagePath2) {
		this.imagePath2 = imagePath2;
	}

	public String getImageName3() {
		return imageName3;
	}

	public void setImageName3(String imageName3) {
		this.imageName3 = imageName3;
	}

	public String getImagePath3() {
		return imagePath3;
	}

	public void setImagePath3(String imagePath3) {
		this.imagePath3 = imagePath3;
	}

	@Override
	public String toString() {
		return "Kyc_client_detail [id=" + id + ", pan=" + pan + ", lastUpdateDate1=" + lastUpdateDate1
				+ ", lastUpdateDate2=" + lastUpdateDate2 + ", lastUpdateDate3=" + lastUpdateDate3 + ", imageName="
				+ imageName + ", imagePath=" + imagePath + ", imageName2=" + imageName2 + ", imagePath2=" + imagePath2
				+ ", imageName3=" + imageName3 + ", imagePath3=" + imagePath3 + ", getId()=" + getId() + ", getPan()="
				+ getPan() + ", getLastUpdateDate1()=" + getLastUpdateDate1() + ", getLastUpdateDate2()="
				+ getLastUpdateDate2() + ", getLastUpdateDate3()=" + getLastUpdateDate3() + ", getImageName()="
				+ getImageName() + ", getImagePath()=" + getImagePath() + ", getImageName2()=" + getImageName2()
				+ ", getImagePath2()=" + getImagePath2() + ", getImageName3()=" + getImageName3() + ", getImagePath3()="
				+ getImagePath3() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public Kyc_client_detail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Kyc_client_detail(Long id, String pan, Date lastUpdateDate1, Date lastUpdateDate2, Date lastUpdateDate3,
			String imageName, String imagePath, String imageName2, String imagePath2, String imageName3,
			String imagePath3) {
		super();
		this.id = id;
		this.pan = pan;
		this.lastUpdateDate1 = lastUpdateDate1;
		this.lastUpdateDate2 = lastUpdateDate2;
		this.lastUpdateDate3 = lastUpdateDate3;
		this.imageName = imageName;
		this.imagePath = imagePath;
		this.imageName2 = imageName2;
		this.imagePath2 = imagePath2;
		this.imageName3 = imageName3;
		this.imagePath3 = imagePath3;
	}		
}
