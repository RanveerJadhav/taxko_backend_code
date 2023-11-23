package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class docinfo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
	private String pan;
	@Column(name = "last_update_date1")
    @JsonFormat(pattern="yyyy-MM-dd")
	 private Date lastUpdateDate1;
	private String imageName;

	private String imagePath;

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

	@Override
	public String toString() {
		return "docinfo [id=" + id + ", pan=" + pan + ", lastUpdateDate1=" + lastUpdateDate1 + ", imageName="
				+ imageName + ", imagePath=" + imagePath + ", getId()=" + getId() + ", getPan()=" + getPan()
				+ ", getLastUpdateDate1()=" + getLastUpdateDate1() + ", getImageName()=" + getImageName()
				+ ", getImagePath()=" + getImagePath() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public docinfo(Long id, String pan, Date lastUpdateDate1, String imageName, String imagePath) {
		super();
		this.id = id;
		this.pan = pan;
		this.lastUpdateDate1 = lastUpdateDate1;
		this.imageName = imageName;
		this.imagePath = imagePath;
	}

	public docinfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
