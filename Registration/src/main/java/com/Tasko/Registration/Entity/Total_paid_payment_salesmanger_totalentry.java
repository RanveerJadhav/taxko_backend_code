package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Total_paid_payment_salesmanger_totalentry {
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
	public void setId(Long id) {
		this.id = id;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public Long getTotalpaid() {
		return totalpaid;
	}
	public void setTotalpaid(Long totalpaid) {
		this.totalpaid = totalpaid;
	}
	public Date getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}
	@Override
	public String toString() {
		return "Total_paid_payment_salesmanger_totalentry [id=" + id + ", pan=" + pan + ", totalpaid=" + totalpaid
				+ ", paymentdate=" + paymentdate + ", getId()=" + getId() + ", getPan()=" + getPan()
				+ ", getTotalpaid()=" + getTotalpaid() + ", getPaymentdate()=" + getPaymentdate() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	public Total_paid_payment_salesmanger_totalentry(Long id, String pan, Long totalpaid, Date paymentdate) {
		super();
		this.id = id;
		this.pan = pan;
		this.totalpaid = totalpaid;
		this.paymentdate = paymentdate;
	}
	public Total_paid_payment_salesmanger_totalentry() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
