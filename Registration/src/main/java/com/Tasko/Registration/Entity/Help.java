package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
public class Help 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    private Long userid;
    
    private Long clientid;
    
    private String name;
    private String query;
	@Column(length = 1000)
    private String detail;
    
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Help(Long id, Long userid, Long clientid, String name, String query, String detail, Date date) {
		super();
		this.id = id;
		this.userid = userid;
		this.clientid = clientid;
		this.name = name;
		this.query = query;
		this.detail = detail;
		this.date = date;
	}

	public Help() {
		super();
	}

	@Override
	public String toString() {
		return "Help [id=" + id + ", userid=" + userid + ", clientid=" + clientid + ", name=" + name + ", query="
				+ query + ", detail=" + detail + ", date=" + date + "]";
	}
    
    

}