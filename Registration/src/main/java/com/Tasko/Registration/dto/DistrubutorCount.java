package com.Tasko.Registration.dto;

import java.util.Date;

import com.Tasko.Registration.Entity.DistributionReg;
import com.Tasko.Registration.Entity.User_RegistrationsForm;

public class DistrubutorCount {
    private DistributionReg distibutor;
    private Long TotalEarning;
    private Long paid;
    private Long unpaid;
	public DistributionReg getDistibutor() {
		return distibutor;
	}
	public void setDistibutor(DistributionReg distibutor) {
		this.distibutor = distibutor;
	}
	public Long getTotalEarning() {
		return TotalEarning;
	}
	public void setTotalEarning(Long totalEarning) {
		TotalEarning = totalEarning;
	}
	public Long getPaid() {
		return paid;
	}
	public void setPaid(Long paid) {
		this.paid = paid;
	}
	public Long getUnpaid() {
		return unpaid;
	}
	public void setUnpaid(Long unpaid) {
		this.unpaid = unpaid;
	}
	@Override
	public String toString() {
		return "DistrubutorCount [distibutor=" + distibutor + ", TotalEarning=" + TotalEarning + ", paid=" + paid
				+ ", unpaid=" + unpaid + ", getDistibutor()=" + getDistibutor() + ", getTotalEarning()="
				+ getTotalEarning() + ", getPaid()=" + getPaid() + ", getUnpaid()=" + getUnpaid() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	public DistrubutorCount(DistributionReg records1, Long totalEarning, Long paid, Long unpaid) {
		super();
		this.distibutor = records1;
		TotalEarning = totalEarning;
		this.paid = paid;
		this.unpaid = unpaid;
	}
	public DistrubutorCount() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
    
	
   
}
