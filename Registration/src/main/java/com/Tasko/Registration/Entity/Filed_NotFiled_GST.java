package com.Tasko.Registration.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Filed_NotFiled_GST 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userid;

	private Long subUserid;

    private Long clientid;

    private String category;
    
    private String month;
    
    private String financialYear;
    
    private String  filednotfiled;

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

	public Long getSubUserid() {
		return subUserid;
	}

	public void setSubUserid(Long subUserid) {
		this.subUserid = subUserid;
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

	public String getFilednotfiled() {
		return filednotfiled;
	}

	public void setFilednotfiled(String filednotfiled) {
		this.filednotfiled = filednotfiled;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Filed_NotFiled_GST(Long id, Long userid, Long subUserid, Long clientid, String category, String month, String financialYear, String filednotfiled, Date lastUpdateDate) {
		this.id = id;
		this.userid = userid;
		this.subUserid = subUserid;
		this.clientid = clientid;
		this.category = category;
		this.month = month;
		this.financialYear = financialYear;
		this.filednotfiled = filednotfiled;
		this.lastUpdateDate = lastUpdateDate;
	}

	public Filed_NotFiled_GST() {
	}

	@Override
	public String toString() {
		return "Filed_NotFiled_GST{" +
				"id=" + id +
				", userid=" + userid +
				", subUserid=" + subUserid +
				", clientid=" + clientid +
				", category='" + category + '\'' +
				", month='" + month + '\'' +
				", financialYear='" + financialYear + '\'' +
				", filednotfiled='" + filednotfiled + '\'' +
				", lastUpdateDate=" + lastUpdateDate +
				'}';
	}
}
