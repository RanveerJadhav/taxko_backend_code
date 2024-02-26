package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Subscriptionpack_histroy_disactivate_salesmanager {
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
	
	private Long subscriptionprice;
	
    private  String disrefrenceId;
	
	private  String salespersonId;
	
	private Long dissalespersonId;

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

	public Long getSubscriptionprice() {
		return subscriptionprice;
	}

	public void setSubscriptionprice(Long subscriptionprice) {
		this.subscriptionprice = subscriptionprice;
	}

	public String getDisrefrenceId() {
		return disrefrenceId;
	}

	public void setDisrefrenceId(String disrefrenceId) {
		this.disrefrenceId = disrefrenceId;
	}

	public String getSalespersonId() {
		return salespersonId;
	}

	public void setSalespersonId(String salespersonId) {
		this.salespersonId = salespersonId;
	}

	public Long getDissalespersonId() {
		return dissalespersonId;
	}

	public void setDissalespersonId(Long dissalespersonId) {
		this.dissalespersonId = dissalespersonId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Subscriptionpack_histroy_disactivate_salesmanager [id=");
		builder.append(id);
		builder.append(", userid=");
		builder.append(userid);
		builder.append(", pan=");
		builder.append(pan);
		builder.append(", substartdate=");
		builder.append(substartdate);
		builder.append(", substartdatebyadmin=");
		builder.append(substartdatebyadmin);
		builder.append(", subendtdate=");
		builder.append(subendtdate);
		builder.append(", subscriptiontype=");
		builder.append(subscriptiontype);
		builder.append(", acessclient=");
		builder.append(acessclient);
		builder.append(", subscriptionprice=");
		builder.append(subscriptionprice);
		builder.append(", disrefrenceId=");
		builder.append(disrefrenceId);
		builder.append(", salespersonId=");
		builder.append(salespersonId);
		builder.append(", dissalespersonId=");
		builder.append(dissalespersonId);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getUserid()=");
		builder.append(getUserid());
		builder.append(", getPan()=");
		builder.append(getPan());
		builder.append(", getSubstartdate()=");
		builder.append(getSubstartdate());
		builder.append(", getSubstartdatebyadmin()=");
		builder.append(getSubstartdatebyadmin());
		builder.append(", getSubendtdate()=");
		builder.append(getSubendtdate());
		builder.append(", getSubscriptiontype()=");
		builder.append(getSubscriptiontype());
		builder.append(", getAcessclient()=");
		builder.append(getAcessclient());
		builder.append(", getSubscriptionprice()=");
		builder.append(getSubscriptionprice());
		builder.append(", getDisrefrenceId()=");
		builder.append(getDisrefrenceId());
		builder.append(", getSalespersonId()=");
		builder.append(getSalespersonId());
		builder.append(", getDissalespersonId()=");
		builder.append(getDissalespersonId());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	public Subscriptionpack_histroy_disactivate_salesmanager(Long id, Long userid, String pan, Date substartdate,
			Date substartdatebyadmin, Date subendtdate, String subscriptiontype, Long acessclient,
			Long subscriptionprice, String disrefrenceId, String salespersonId, Long dissalespersonId) {
		super();
		this.id = id;
		this.userid = userid;
		this.pan = pan;
		this.substartdate = substartdate;
		this.substartdatebyadmin = substartdatebyadmin;
		this.subendtdate = subendtdate;
		this.subscriptiontype = subscriptiontype;
		this.acessclient = acessclient;
		this.subscriptionprice = subscriptionprice;
		this.disrefrenceId = disrefrenceId;
		this.salespersonId = salespersonId;
		this.dissalespersonId = dissalespersonId;
	}

	public Subscriptionpack_histroy_disactivate_salesmanager() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
