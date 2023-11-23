package com.Tasko.Registration.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class Filed_NotFiled
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userid;

    private Long clientid;

    private String accountyear;

    private String  filednotfiled;

    @Column(name = "last_update_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastUpdateDate;

    private String category;

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

	public String getAccountyear() {
		return accountyear;
	}

	public void setAccountyear(String accountyear) {
		this.accountyear = accountyear;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Filed_NotFiled(Long id, Long userid, Long clientid, String accountyear, String filednotfiled,
			Date lastUpdateDate, String category) {
		super();
		this.id = id;
		this.userid = userid;
		this.clientid = clientid;
		this.accountyear = accountyear;
		this.filednotfiled = filednotfiled;
		this.lastUpdateDate = lastUpdateDate;
		this.category = category;
	}

	public Filed_NotFiled() {
		super();
	}

	@Override
	public String toString() {
		return "Filed_NotFiled [id=" + id + ", userid=" + userid + ", clientid=" + clientid + ", accountyear="
				+ accountyear + ", filednotfiled=" + filednotfiled + ", lastUpdateDate=" + lastUpdateDate
				+ ", category=" + category + "]";
	}
    
    
}
