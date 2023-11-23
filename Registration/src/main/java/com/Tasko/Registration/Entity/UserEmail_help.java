package com.Tasko.Registration.Entity;

import java.util.Date;



import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
public class UserEmail_help {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userid;
    
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
	@Column(length = 1000)
    private String detail;
	@Column(length = 1000)
    private String subject;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "UserEmail_help [id=" + id + ", userid=" + userid + ", name=" + name + ", date=" + date + ", detail="
				+ detail + ", subject=" + subject + ", getId()=" + getId() + ", getUserid()=" + getUserid()
				+ ", getName()=" + getName() + ", getDate()=" + getDate() + ", getDetail()=" + getDetail()
				+ ", getSubject()=" + getSubject() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public UserEmail_help(Long id, Long userid, String name, Date date, String detail, String subject) {
		super();
		this.id = id;
		this.userid = userid;
		this.name = name;
		this.date = date;
		this.detail = detail;
		this.subject = subject;
	}

	public UserEmail_help() {
		super();
		// TODO Auto-generated constructor stub
	}

	
    
    
}
