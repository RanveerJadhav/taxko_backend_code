package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Subscriptionpack_history {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	
	private Long userid;
	

	private String pan;
	
	@Column(name ="substartdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date substartdate;
	
	@Column(name ="substartdatebyadmin")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date substartdatebyadmin;
	
	@Column(name ="subendtdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date subendtdate;
	
	private String subscriptiontype;
	
	private Long acessclient;
	
	private String imageName;

	private String imagePath;
	
	private Long subscriptionprice;

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

	public Date getSubendtdate() {
		return subendtdate;
	}

	public void setSubendtdate(Date subendtdate) {
		this.subendtdate = subendtdate;
	}

	public String getSubscriptiontype() {
		return subscriptiontype;
	}

	public void setSubscriptiontype(String subscriptiontype) {
		this.subscriptiontype = subscriptiontype;
	}

	public Long getAcessclient() {
		return acessclient;
	}

	public void setAcessclient(Long acessclient) {
		this.acessclient = acessclient;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Long getSubscriptionprice() {
		return subscriptionprice;
	}

	public void setSubscriptionprice(Long subscriptionprice) {
		this.subscriptionprice = subscriptionprice;
	}

	@Override
	public String toString() {
		return "Subscriptionpack_history [id=" + id + ", userid=" + userid + ", pan=" + pan + ", substartdate="
				+ substartdate + ", substartdatebyadmin=" + substartdatebyadmin + ", subendtdate=" + subendtdate
				+ ", subscriptiontype=" + subscriptiontype + ", acessclient=" + acessclient + ", imageName=" + imageName
				+ ", imagePath=" + imagePath + ", subscriptionprice=" + subscriptionprice + ", getId()=" + getId()
				+ ", getUserid()=" + getUserid() + ", getPan()=" + getPan() + ", getSubstartdate()=" + getSubstartdate()
				+ ", getSubstartdatebyadmin()=" + getSubstartdatebyadmin() + ", getSubendtdate()=" + getSubendtdate()
				+ ", getSubscriptiontype()=" + getSubscriptiontype() + ", getAcessclient()=" + getAcessclient()
				+ ", getImageName()=" + getImageName() + ", getImagePath()=" + getImagePath()
				+ ", getSubscriptionprice()=" + getSubscriptionprice() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	public Subscriptionpack_history(Long id, Long userid, String pan, Date substartdate, Date substartdatebyadmin,
			Date subendtdate, String subscriptiontype, Long acessclient, String imageName, String imagePath,
			Long subscriptionprice) {
		super();
		this.id = id;
		this.userid = userid;
		this.pan = pan;
		this.substartdate = substartdate;
		this.substartdatebyadmin = substartdatebyadmin;
		this.subendtdate = subendtdate;
		this.subscriptiontype = subscriptiontype;
		this.acessclient = acessclient;
		this.imageName = imageName;
		this.imagePath = imagePath;
		this.subscriptionprice = subscriptionprice;
	}

	public Subscriptionpack_history() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	

}
