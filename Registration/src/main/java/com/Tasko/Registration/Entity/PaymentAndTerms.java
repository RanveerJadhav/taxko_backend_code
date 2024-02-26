package com.Tasko.Registration.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;


import java.util.Arrays;
import java.util.Date;

@Entity
public class PaymentAndTerms
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column( length = 100000)
    private byte[] data;




    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PaymentAndTerms(Long id, String name, byte[] data, Date date, String category)
    {
        this.id = id;
        this.name = name;
        this.data = data;
        this.date = date;
        this.category = category;
    }

    public PaymentAndTerms() {
    }
}
