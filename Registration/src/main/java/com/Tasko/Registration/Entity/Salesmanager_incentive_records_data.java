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
public class Salesmanager_incentive_records_data {
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

	public Long getSalesmanid() {
		return salesmanid;
	}

	public String getPan() {
		return pan;
	}

	public String getYear() {
		return year;
	}

	public Long getTargrtamount() {
		return targrtamount;
	}

	public Date getFixdate() {
		return fixdate;
	}

	public LocalDate getStartdate() {
		return startdate;
	}

	public LocalDate getEnddate() {
		return enddate;
	}

	public Long getIncetiveamount() {
		return incetiveamount;
	}

	public String getQuerter() {
		return querter;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSalesmanid(Long salesmanid) {
		this.salesmanid = salesmanid;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setTargrtamount(Long targrtamount) {
		this.targrtamount = targrtamount;
	}

	public void setFixdate(Date fixdate) {
		this.fixdate = fixdate;
	}

	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}

	public void setEnddate(LocalDate enddate) {
		this.enddate = enddate;
	}

	public void setIncetiveamount(Long incetiveamount) {
		this.incetiveamount = incetiveamount;
	}

	public void setQuerter(String querter) {
		this.querter = querter;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Salesmanager_incentive_records_data [id=");
		builder.append(id);
		builder.append(", salesmanid=");
		builder.append(salesmanid);
		builder.append(", pan=");
		builder.append(pan);
		builder.append(", year=");
		builder.append(year);
		builder.append(", targrtamount=");
		builder.append(targrtamount);
		builder.append(", fixdate=");
		builder.append(fixdate);
		builder.append(", startdate=");
		builder.append(startdate);
		builder.append(", enddate=");
		builder.append(enddate);
		builder.append(", incetiveamount=");
		builder.append(incetiveamount);
		builder.append(", querter=");
		builder.append(querter);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getSalesmanid()=");
		builder.append(getSalesmanid());
		builder.append(", getPan()=");
		builder.append(getPan());
		builder.append(", getYear()=");
		builder.append(getYear());
		builder.append(", getTargrtamount()=");
		builder.append(getTargrtamount());
		builder.append(", getFixdate()=");
		builder.append(getFixdate());
		builder.append(", getStartdate()=");
		builder.append(getStartdate());
		builder.append(", getEnddate()=");
		builder.append(getEnddate());
		builder.append(", getIncetiveamount()=");
		builder.append(getIncetiveamount());
		builder.append(", getQuerter()=");
		builder.append(getQuerter());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	public Salesmanager_incentive_records_data(Long id, Long salesmanid, String pan, String year, Long targrtamount,
			Date fixdate, LocalDate startdate, LocalDate enddate, Long incetiveamount, String querter) {
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

	public Salesmanager_incentive_records_data() {
		super();
		// TODO Auto-generated constructor stub
	}

}
