package com.Tasko.Registration.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Client_Payment_Details 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userid;
    private Long clientid;

	private Long subUserid;
    private Long totalPayment = 0L;
    private Long receivedPayment = 0L;
    private Long pendingPayment = 0L;
    
    private Long discount= 0L;
    
    @Column(name = "last_update_date")
    @JsonFormat(pattern="dd-MMM-YYYY")
    private Date lastUpdateDate;
    
    private String year;

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

	public Long getClientid() {
		return clientid;
	}

	public void setClientid(Long clientid) {
		this.clientid = clientid;
	}

	public Long getSubUserid() {
		return subUserid;
	}

	public void setSubUserid(Long subUserid) {
		this.subUserid = subUserid;
	}

	public Long getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Long totalPayment) {
		this.totalPayment = totalPayment;
	}

	public Long getReceivedPayment() {
		return receivedPayment;
	}

	public void setReceivedPayment(Long receivedPayment) {
		this.receivedPayment = receivedPayment;
	}

	public Long getPendingPayment() {
		return pendingPayment;
	}

	public void setPendingPayment(Long pendingPayment) {
		this.pendingPayment = pendingPayment;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Client_Payment_Details(Long id, Long userid, Long clientid, Long subUserid, Long totalPayment, Long receivedPayment, Long pendingPayment, Long discount, Date lastUpdateDate, String year) {
		this.id = id;
		this.userid = userid;
		this.clientid = clientid;
		this.subUserid = subUserid;
		this.totalPayment = totalPayment;
		this.receivedPayment = receivedPayment;
		this.pendingPayment = pendingPayment;
		this.discount = discount;
		this.lastUpdateDate = lastUpdateDate;
		this.year = year;
	}

	public Client_Payment_Details() {
	}

	@Override
	public String toString() {
		return "Client_Payment_Details{" +
				"id=" + id +
				", userid=" + userid +
				", clientid=" + clientid +
				", subUserid=" + subUserid +
				", totalPayment=" + totalPayment +
				", receivedPayment=" + receivedPayment +
				", pendingPayment=" + pendingPayment +
				", discount=" + discount +
				", lastUpdateDate=" + lastUpdateDate +
				", year='" + year + '\'' +
				'}';
	}
}
