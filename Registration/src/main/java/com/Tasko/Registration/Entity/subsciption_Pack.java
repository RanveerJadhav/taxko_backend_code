package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class subsciption_Pack {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	
	private String subtype;
	
	private Long accesscliet;
	
	private Long subscriptionprice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public Long getAccesscliet() {
		return accesscliet;
	}

	public void setAccesscliet(Long accesscliet) {
		this.accesscliet = accesscliet;
	}

	public Long getSubscriptionprice() {
		return subscriptionprice;
	}

	public void setSubscriptionprice(Long subscriptionprice) {
		this.subscriptionprice = subscriptionprice;
	}

	public subsciption_Pack(Long id, String subtype, Long accesscliet, Long subscriptionprice) {
		super();
		this.id = id;
		this.subtype = subtype;
		this.accesscliet = accesscliet;
		this.subscriptionprice = subscriptionprice;
	}

	public subsciption_Pack() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "subsciption_Pack [id=" + id + ", subtype=" + subtype + ", accesscliet=" + accesscliet
				+ ", subscriptionprice=" + subscriptionprice + ", getId()=" + getId() + ", getSubtype()=" + getSubtype()
				+ ", getAccesscliet()=" + getAccesscliet() + ", getSubscriptionprice()=" + getSubscriptionprice()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
