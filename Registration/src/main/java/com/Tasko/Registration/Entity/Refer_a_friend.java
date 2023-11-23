package com.Tasko.Registration.Entity;



import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Refer_a_friend {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	
	private Long userid;
	

	private String pan;
	
	
	@Column(name ="date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
	
	private String name;
	private String  contactno;
	private String profession;
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
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	@Override
	public String toString() {
		return "Refer_a_friend [id=" + id + ", userid=" + userid + ", pan=" + pan + ", date=" + date + ", name=" + name
				+ ", contactno=" + contactno + ", profession=" + profession + ", getId()=" + getId() + ", getUserid()="
				+ getUserid() + ", getPan()=" + getPan() + ", getDate()=" + getDate() + ", getName()=" + getName()
				+ ", getContactno()=" + getContactno() + ", getProfession()=" + getProfession() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	public Refer_a_friend() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Refer_a_friend(Long id, Long userid, String pan, Date date, String name, String contactno,
			String profession) {
		super();
		this.id = id;
		this.userid = userid;
		this.pan = pan;
		this.date = date;
		this.name = name;
		this.contactno = contactno;
		this.profession = profession;
	}
	
	
}
