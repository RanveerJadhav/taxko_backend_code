package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Total_salesmanger_totalentry_deactivate_salesmanager {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	private String pan;
	private Long totalpaid;
	@Column(name ="paymentdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date paymentdate;
	public Long getId() {
		return id;
	}
	public String getPan() {
		return pan;
	}
	public Long getTotalpaid() {
		return totalpaid;
	}
	public Date getPaymentdate() {
		return paymentdate;
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
	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Total_salesmanger_totalentry_deactivate_salesmanager [id=");
		builder.append(id);
		builder.append(", pan=");
		builder.append(pan);
		builder.append(", totalpaid=");
		builder.append(totalpaid);
		builder.append(", paymentdate=");
		builder.append(paymentdate);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getPan()=");
		builder.append(getPan());
		builder.append(", getTotalpaid()=");
		builder.append(getTotalpaid());
		builder.append(", getPaymentdate()=");
		builder.append(getPaymentdate());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	public Total_salesmanger_totalentry_deactivate_salesmanager(Long id, String pan, Long totalpaid, Date paymentdate) {
		super();
		this.id = id;
		this.pan = pan;
		this.totalpaid = totalpaid;
		this.paymentdate = paymentdate;
	}
	public Total_salesmanger_totalentry_deactivate_salesmanager() {
		super();
		// TODO Auto-generated constructor stub
	}
}
