package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class User_diacivate_salesmanager {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	
	private Long userid;  
	
	private String pan;
    
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
		builder.append("User_diacivate_salesmanager [id=");
		builder.append(id);
		builder.append(", userid=");
		builder.append(userid);
		builder.append(", pan=");
		builder.append(pan);
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

	public User_diacivate_salesmanager(Long id, Long userid, String pan, String salespersonId, Long dissalespersonId) {
		super();
		this.id = id;
		this.userid = userid;
		this.pan = pan;
		this.salespersonId = salespersonId;
		this.dissalespersonId = dissalespersonId;
	}

	public User_diacivate_salesmanager() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
