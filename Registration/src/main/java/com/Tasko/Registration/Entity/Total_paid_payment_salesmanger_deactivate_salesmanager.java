package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Total_paid_payment_salesmanger_deactivate_salesmanager {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	private String pan;
	private Long totalpaid;
	public Long getId() {
		return id;
	}
	public String getPan() {
		return pan;
	}
	public Long getTotalpaid() {
		return totalpaid;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public void setTotalpaid(Long totalpaid) {
		this.totalpaid = totalpaid;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Total_paid_payment_salesmanger_deactivate_salesmanager [id=");
		builder.append(id);
		builder.append(", pan=");
		builder.append(pan);
		builder.append(", totalpaid=");
		builder.append(totalpaid);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getPan()=");
		builder.append(getPan());
		builder.append(", getTotalpaid()=");
		builder.append(getTotalpaid());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	public Total_paid_payment_salesmanger_deactivate_salesmanager(Long id, String pan, Long totalpaid) {
		super();
		this.id = id;
		this.pan = pan;
		this.totalpaid = totalpaid;
	}
	public Total_paid_payment_salesmanger_deactivate_salesmanager() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
