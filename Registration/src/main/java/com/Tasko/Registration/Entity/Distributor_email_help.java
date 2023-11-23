package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Distributor_email_help {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long distributorid;
    
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

	public Long getDistributorid() {
		return distributorid;
	}

	public void setDistributorid(Long distributorid) {
		this.distributorid = distributorid;
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
		return "distributor_email_help [id=" + id + ", distributorid=" + distributorid + ", name=" + name + ", date="
				+ date + ", detail=" + detail + ", subject=" + subject + ", getId()=" + getId()
				+ ", getDistributorid()=" + getDistributorid() + ", getName()=" + getName() + ", getDate()=" + getDate()
				+ ", getDetail()=" + getDetail() + ", getSubject()=" + getSubject() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public Distributor_email_help(Long id, Long distributorid, String name, Date date, String detail, String subject) {
		super();
		this.id = id;
		this.distributorid = distributorid;
		this.name = name;
		this.date = date;
		this.detail = detail;
		this.subject = subject;
	}

	public Distributor_email_help() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
