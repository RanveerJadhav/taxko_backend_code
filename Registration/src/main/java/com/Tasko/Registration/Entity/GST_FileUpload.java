package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GST_FileUpload 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    private Long userid;
    
    private Long clientid;
    
    private String category;
    
    private String month;
    
    private String financialYear;
    
    private String fileName;
    
    private String filePath;

    @Column(name = "last_update_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastUpdateDate;

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

	public Long getClientid() {
		return clientid;
	}

	public void setClientid(Long clientid) {
		this.clientid = clientid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
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

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public String toString() {
		return "GST_FileUpload [id=" + id + ", userid=" + userid + ", clientid=" + clientid + ", category=" + category
				+ ", month=" + month + ", financialYear=" + financialYear + ", fileName=" + fileName + ", filePath="
				+ filePath + ", lastUpdateDate=" + lastUpdateDate + ", getId()=" + getId() + ", getUserid()="
				+ getUserid() + ", getClientid()=" + getClientid() + ", getCategory()=" + getCategory()
				+ ", getMonth()=" + getMonth() + ", getFinancialYear()=" + getFinancialYear() + ", getFileName()="
				+ getFileName() + ", getFilePath()=" + getFilePath() + ", getLastUpdateDate()=" + getLastUpdateDate()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public GST_FileUpload(Long id, Long userid, Long clientid, String category, String month, String financialYear,
			String fileName, String filePath, Date lastUpdateDate) {
		super();
		this.id = id;
		this.userid = userid;
		this.clientid = clientid;
		this.category = category;
		this.month = month;
		this.financialYear = financialYear;
		this.fileName = fileName;
		this.filePath = filePath;
		this.lastUpdateDate = lastUpdateDate;
	}

	public GST_FileUpload() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
