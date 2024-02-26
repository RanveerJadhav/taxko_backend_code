package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Salemanager_target {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	
	private Long salesmanid;
	

	private String pan;
	
	
	@Column(name ="date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
	      
	private String year;
	
	private Long amount;
	
	@Column(name ="fixdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fixdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSalesmanid() {
		return salesmanid;
	}

	public void setSalesmanid(Long salesmanid) {
		this.salesmanid = salesmanid;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getFixdate() {
		return fixdate;
	}

	public void setFixdate(Date fixdate) {
		this.fixdate = fixdate;
	}

	@Override
	public String toString() {
		return "Salemanager_target [id=" + id + ", salesmanid=" + salesmanid + ", pan=" + pan + ", date=" + date
				+ ", year=" + year + ", amount=" + amount + ", fixdate=" + fixdate + ", getId()=" + getId()
				+ ", getSalesmanid()=" + getSalesmanid() + ", getPan()=" + getPan() + ", getDate()=" + getDate()
				+ ", getYear()=" + getYear() + ", getAmount()=" + getAmount() + ", getFixdate()=" + getFixdate()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public Salemanager_target(Long id, Long salesmanid, String pan, Date date, String year, Long amount, Date fixdate) {
		super();
		this.id = id;
		this.salesmanid = salesmanid;
		this.pan = pan;
		this.date = date;
		this.year = year;
		this.amount = amount;
		this.fixdate = fixdate;
	}

	public Salemanager_target() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
