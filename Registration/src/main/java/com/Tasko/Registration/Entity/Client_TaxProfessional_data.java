package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Client_TaxProfessional_data {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	
	@Column(name ="date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
	
	private String yourname;
	
	private String yourmobileno;
	
	private String taxprofessionalname;
	
	private String taxprofessionalmobile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getYourname() {
		return yourname;
	}

	public void setYourname(String yourname) {
		this.yourname = yourname;
	}

	public String getYourmobileno() {
		return yourmobileno;
	}

	public void setYourmobileno(String yourmobileno) {
		this.yourmobileno = yourmobileno;
	}

	public String getTaxprofessionalname() {
		return taxprofessionalname;
	}

	public void setTaxprofessionalname(String taxprofessionalname) {
		this.taxprofessionalname = taxprofessionalname;
	}

	public String getTaxprofessionalmobile() {
		return taxprofessionalmobile;
	}

	public void setTaxprofessionalmobile(String taxprofessionalmobile) {
		this.taxprofessionalmobile = taxprofessionalmobile;
	}

	@Override
	public String toString() {
		return "Client_TaxProfessional_data [id=" + id + ", date=" + date + ", yourname=" + yourname + ", yourmobileno="
				+ yourmobileno + ", taxprofessionalname=" + taxprofessionalname + ", taxprofessionalmobile="
				+ taxprofessionalmobile + ", getId()=" + getId() + ", getDate()=" + getDate() + ", getYourname()="
				+ getYourname() + ", getYourmobileno()=" + getYourmobileno() + ", getTaxprofessionalname()="
				+ getTaxprofessionalname() + ", getTaxprofessionalmobile()=" + getTaxprofessionalmobile()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public Client_TaxProfessional_data(Long id, Date date, String yourname, String yourmobileno,
			String taxprofessionalname, String taxprofessionalmobile) {
		super();
		this.id = id;
		this.date = date;
		this.yourname = yourname;
		this.yourmobileno = yourmobileno;
		this.taxprofessionalname = taxprofessionalname;
		this.taxprofessionalmobile = taxprofessionalmobile;
	}

	public Client_TaxProfessional_data() {
		super();
		// TODO Auto-generated constructor stub
	}


}
