package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;



@Entity
@Table(name = "Income Tax files")
public class FileEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique=true)
    private String fileName;
    
    private String filePath;
    private Long userid;
    private Long clientid;
    private String accountyear;
    
    @Column(name = "last_update_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastUpdateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getClientid() {
		return clientid;
	}

	public void setClientid(Long clientid) {
		this.clientid = clientid;
	}

	public String getAccountyear() {
		return accountyear;
	}

	public void setAccountyear(String accountyear) {
		this.accountyear = accountyear;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public String toString() {
		return "FileEntity [id=" + id + ", fileName=" + fileName + ", filePath=" + filePath + ", userid=" + userid
				+ ", clientid=" + clientid + ", accountyear=" + accountyear + ", lastUpdateDate=" + lastUpdateDate
				+ ", getId()=" + getId() + ", getFileName()=" + getFileName() + ", getFilePath()=" + getFilePath()
				+ ", getUserid()=" + getUserid() + ", getClientid()=" + getClientid() + ", getAccountyear()="
				+ getAccountyear() + ", getLastUpdateDate()=" + getLastUpdateDate() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public FileEntity(Long id, String fileName, String filePath, Long userid, Long clientid, String accountyear,
			Date lastUpdateDate) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.filePath = filePath;
		this.userid = userid;
		this.clientid = clientid;
		this.accountyear = accountyear;
		this.lastUpdateDate = lastUpdateDate;
	}

	public FileEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
    
   
}