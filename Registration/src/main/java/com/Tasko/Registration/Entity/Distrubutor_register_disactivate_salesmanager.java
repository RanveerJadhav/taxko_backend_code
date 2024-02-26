package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Distrubutor_register_disactivate_salesmanager {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
	
	private Long distrubutorid;
	private String pan;
	private  Long salesmanid;
	 private String salesmanagerpan;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDistrubutorid() {
		return distrubutorid;
	}
	public void setDistrubutorid(Long distrubutorid) {
		this.distrubutorid = distrubutorid;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public Long getSalesmanid() {
		return salesmanid;
	}
	public void setSalesmanid(Long salesmanid) {
		this.salesmanid = salesmanid;
	}
	public String getSalesmanagerpan() {
		return salesmanagerpan;
	}
	public void setSalesmanagerpan(String salesmanagerpan) {
		this.salesmanagerpan = salesmanagerpan;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Distrubutor_register_disactivate_salesmanager [id=");
		builder.append(id);
		builder.append(", distrubutorid=");
		builder.append(distrubutorid);
		builder.append(", pan=");
		builder.append(pan);
		builder.append(", salesmanid=");
		builder.append(salesmanid);
		builder.append(", salesmanagerpan=");
		builder.append(salesmanagerpan);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getDistrubutorid()=");
		builder.append(getDistrubutorid());
		builder.append(", getPan()=");
		builder.append(getPan());
		builder.append(", getSalesmanid()=");
		builder.append(getSalesmanid());
		builder.append(", getSalesmanagerpan()=");
		builder.append(getSalesmanagerpan());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	public Distrubutor_register_disactivate_salesmanager(Long id, Long distrubutorid, String pan, Long salesmanid,
			String salesmanagerpan) {
		super();
		this.id = id;
		this.distrubutorid = distrubutorid;
		this.pan = pan;
		this.salesmanid = salesmanid;
		this.salesmanagerpan = salesmanagerpan;
	}
	public Distrubutor_register_disactivate_salesmanager() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
}
