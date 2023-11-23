package com.Tasko.Registration.Entity;

import java.math.BigDecimal;
import java.util.Date;



import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Renewalprice_distributordata {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	
	private String name;
	
	private String userpan;
	
	private String pan;
	
	@Column(name ="substartdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date substartdate;
	
	@Column(name ="substartdatebyadmin")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date substartdatebyadmin;
	
	private double renewalPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserpan() {
		return userpan;
	}

	public void setUserpan(String userpan) {
		this.userpan = userpan;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public Date getSubstartdate() {
		return substartdate;
	}

	public void setSubstartdate(Date substartdate) {
		this.substartdate = substartdate;
	}

	public Date getSubstartdatebyadmin() {
		return substartdatebyadmin;
	}

	public void setSubstartdatebyadmin(Date substartdatebyadmin) {
		this.substartdatebyadmin = substartdatebyadmin;
	}

	public double getRenewalPrice() {
		return renewalPrice;
	}

	public void setRenewalPrice(double b) {
		this.renewalPrice = b;
	}

	@Override
	public String toString() {
		return "Renewalprice_distributordata [id=" + id + ", name=" + name + ", userpan=" + userpan + ", pan=" + pan
				+ ", substartdate=" + substartdate + ", substartdatebyadmin=" + substartdatebyadmin + ", renewalPrice="
				+ renewalPrice + ", getId()=" + getId() + ", getName()=" + getName() + ", getUserpan()=" + getUserpan()
				+ ", getPan()=" + getPan() + ", getSubstartdate()=" + getSubstartdate() + ", getSubstartdatebyadmin()="
				+ getSubstartdatebyadmin() + ", getRenewalPrice()=" + getRenewalPrice() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public Renewalprice_distributordata(Long id, String name, String userpan, String pan, Date substartdate,
			Date substartdatebyadmin, double renewalPrice) {
		super();
		this.id = id;
		this.name = name;
		this.userpan = userpan;
		this.pan = pan;
		this.substartdate = substartdate;
		this.substartdatebyadmin = substartdatebyadmin;
		this.renewalPrice = renewalPrice;
	}

	public Renewalprice_distributordata() {
		super();
		// TODO Auto-generated constructor stub
	}


}
