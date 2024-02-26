
package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String order_id;

    private String payment_id;

    private String signature;

    private String pan;

    private String name;

    private Double amount;

    private Date transactionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Transaction(Long id, String order_id, String payment_id, String signature, String pan, String name, Double amount, Date transactionDate) {
        this.id = id;
        this.order_id = order_id;
        this.payment_id = payment_id;
        this.signature = signature;
        this.pan = pan;
        this.name = name;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Transaction() {
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", order_id='" + order_id + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", signature='" + signature + '\'' +
                ", pan='" + pan + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                '}';
    }
}