package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
public class Invest_Now 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    private Long userid;
    
    private Long clientid;
    
    private String category;
	@Column(length = 1000)
   private String detail;
	@Column(length = 1000)
   private String subject;
   
   private String name;
    
   @JsonFormat(pattern="yyyy-MM-dd")
   private Date date;
   
   private String investNow_Email;

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

public String getInvestNow_Email() {
	return investNow_Email;
}

public void setInvestNow_Email(String investNow_Email) {
	this.investNow_Email = investNow_Email;
}

@Override
public String toString() {
	return "Invest_Now [id=" + id + ", userid=" + userid + ", clientid=" + clientid + ", category=" + category
			+ ", detail=" + detail + ", subject=" + subject + ", name=" + name + ", date=" + date + ", investNow_Email="
			+ investNow_Email + ", getId()=" + getId() + ", getUserid()=" + getUserid() + ", getClientid()="
			+ getClientid() + ", getCategory()=" + getCategory() + ", getDetail()=" + getDetail() + ", getSubject()="
			+ getSubject() + ", getName()=" + getName() + ", getDate()=" + getDate() + ", getInvestNow_Email()="
			+ getInvestNow_Email() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
			+ super.toString() + "]";
}

public Invest_Now(Long id, Long userid, Long clientid, String category, String detail, String subject, String name,
		Date date, String investNow_Email) {
	super();
	this.id = id;
	this.userid = userid;
	this.clientid = clientid;
	this.category = category;
	this.detail = detail;
	this.subject = subject;
	this.name = name;
	this.date = date;
	this.investNow_Email = investNow_Email;
}

public Invest_Now() {
	super();
	// TODO Auto-generated constructor stub
}

   
   

}