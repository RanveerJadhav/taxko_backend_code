package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Salesmanager_target_disactivate_salesmanager {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Salesmanager_target_disactivate_salesmanager [id=");
		builder.append(id);
		builder.append(", salesmanid=");
		builder.append(salesmanid);
		builder.append(", pan=");
		builder.append(pan);
		builder.append(", date=");
		builder.append(date);
		builder.append(", year=");
		builder.append(year);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getSalesmanid()=");
		builder.append(getSalesmanid());
		builder.append(", getPan()=");
		builder.append(getPan());
		builder.append(", getDate()=");
		builder.append(getDate());
		builder.append(", getYear()=");
		builder.append(getYear());
		builder.append(", getAmount()=");
		builder.append(getAmount());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	public Salesmanager_target_disactivate_salesmanager(Long id, Long salesmanid, String pan, Date date, String year,
			Long amount) {
		super();
		this.id = id;
		this.salesmanid = salesmanid;
		this.pan = pan;
		this.date = date;
		this.year = year;
		this.amount = amount;
	}

	public Salesmanager_target_disactivate_salesmanager() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
