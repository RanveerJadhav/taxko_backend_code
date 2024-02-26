package com.Tasko.Registration.Entity;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Salesmanager_incentive_records_data_deactivate_salesmanager {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	
	private Long salesmanid;
	

	private String pan;
	      
	private String year;
	
	private Long targrtamount;
	
	@Column(name ="fixdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fixdate;
	
	
	@Column(name ="startdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startdate;
	
	@Column(name ="enddate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate enddate;
	
	private Long incetiveamount;

	private String querter;
	
	
	

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Long getTargrtamount() {
		return targrtamount;
	}

	public void setTargrtamount(Long targrtamount) {
		this.targrtamount = targrtamount;
	}

	public Date getFixdate() {
		return fixdate;
	}

	public void setFixdate(Date fixdate) {
		this.fixdate = fixdate;
	}

	public LocalDate getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}

	public LocalDate getEnddate() {
		return enddate;
	}

	public void setEnddate(LocalDate enddate) {
		this.enddate = enddate;
	}

	public Long getIncetiveamount() {
		return incetiveamount;
	}

	public void setIncetiveamount(Long incetiveamount) {
		this.incetiveamount = incetiveamount;
	}

	public String getQuerter() {
		return querter;
	}

	public void setQuerter(String querter) {
		this.querter = querter;
	}

	@Override
	public String toString() {
		return "Salesmanager_incentive_records_data_deactivate_salesmanager [id=" + id + ", salesmanid=" + salesmanid
				+ ", pan=" + pan + ", year=" + year + ", targrtamount=" + targrtamount + ", fixdate=" + fixdate
				+ ", startdate=" + startdate + ", enddate=" + enddate + ", incetiveamount=" + incetiveamount
				+ ", querter=" + querter + ", getId()=" + getId() + ", getSalesmanid()=" + getSalesmanid()
				+ ", getPan()=" + getPan() + ", getYear()=" + getYear() + ", getTargrtamount()=" + getTargrtamount()
				+ ", getFixdate()=" + getFixdate() + ", getStartdate()=" + getStartdate() + ", getEnddate()="
				+ getEnddate() + ", getIncetiveamount()=" + getIncetiveamount() + ", getQuerter()=" + getQuerter()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public Salesmanager_incentive_records_data_deactivate_salesmanager(Long id, Long salesmanid, String pan,
			String year, Long targrtamount, Date fixdate, LocalDate startdate, LocalDate enddate, Long incetiveamount,
			String querter) {
		super();
		this.id = id;
		this.salesmanid = salesmanid;
		this.pan = pan;
		this.year = year;
		this.targrtamount = targrtamount;
		this.fixdate = fixdate;
		this.startdate = startdate;
		this.enddate = enddate;
		this.incetiveamount = incetiveamount;
		this.querter = querter;
	}

	public Salesmanager_incentive_records_data_deactivate_salesmanager() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
