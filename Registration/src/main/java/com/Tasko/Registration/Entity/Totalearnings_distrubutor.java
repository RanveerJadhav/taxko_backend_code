package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Totalearnings_distrubutor {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	private String pan;
	private double spendrenewalPrice;
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
	public double getSpendrenewalPrice() {
		return spendrenewalPrice;
	}
	public void setSpendrenewalPrice(double spendrenewalPrice) {
		this.spendrenewalPrice = spendrenewalPrice;
	}
	@Override
	public String toString() {
		return "Totalearnings_distrubutor [id=" + id + ", pan=" + pan + ", spendrenewalPrice=" + spendrenewalPrice
				+ ", getId()=" + getId() + ", getPan()=" + getPan() + ", getSpendrenewalPrice()="
				+ getSpendrenewalPrice() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	public Totalearnings_distrubutor(Long id, String pan, double spendrenewalPrice) {
		super();
		this.id = id;
		this.pan = pan;
		this.spendrenewalPrice = spendrenewalPrice;
	}
	public Totalearnings_distrubutor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
