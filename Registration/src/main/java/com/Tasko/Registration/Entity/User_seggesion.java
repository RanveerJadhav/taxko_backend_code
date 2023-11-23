package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User_seggesion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	
	private Long userid;
	
	private String pan;
	
	@Column(name ="date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
	
	@Column(length = 1000)
	private String seggesion;

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

	public String getSeggesion() {
		return seggesion;
	}

	public void setSeggesion(String seggesion) {
		this.seggesion = seggesion;
	}

	@Override
	public String toString() {
		return "User_seggesion [id=" + id + ", userid=" + userid + ", pan=" + pan + ", date=" + date + ", seggesion="
				+ seggesion + ", getId()=" + getId() + ", getUserid()=" + getUserid() + ", getPan()=" + getPan()
				+ ", getDate()=" + getDate() + ", getSeggesion()=" + getSeggesion() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public User_seggesion(Long id, Long userid, String pan, Date date, String seggesion) {
		super();
		this.id = id;
		this.userid = userid;
		this.pan = pan;
		this.date = date;
		this.seggesion = seggesion;
	}

	public User_seggesion() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
