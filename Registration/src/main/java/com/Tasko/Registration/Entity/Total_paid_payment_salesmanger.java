package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Total_paid_payment_salesmanger {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	private String pan;
	private Long totalpaid;
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
	@Override
	public String toString() {
		return "Total_paid_payment_salesmanger [id=" + id + ", pan=" + pan + ", totalpaid=" + totalpaid + ", getId()="
				+ getId() + ", getPan()=" + getPan() + ", getTotalpaid()=" + getTotalpaid() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	public Total_paid_payment_salesmanger(Long id, String pan, Long totalpaid) {
		super();
		this.id = id;
		this.pan = pan;
		this.totalpaid = totalpaid;
	}
	public Total_paid_payment_salesmanger() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
