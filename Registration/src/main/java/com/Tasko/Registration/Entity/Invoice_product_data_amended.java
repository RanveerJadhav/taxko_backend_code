package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Invoice_product_data_amended {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	
	private  Long invoiceid;
	
	private  String productname;
	
	private  String  description;
	
	private String hsn;
	
	private Long quantity;
	
	private String unit;
	
	private String value;

	private Double cgst_sgst_rate;
	
	private Double igst_rate;
	
	private Double cess_Advol_rate;
	
	private Double cess_non_Advol_rate;
	
	private String bill_from_GSTIN;
    
	private String invoiceyear;
	
	private String invoicemonth;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvoiceid() {
		return invoiceid;
	}

	public void setInvoiceid(Long invoiceid) {
		this.invoiceid = invoiceid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHsn() {
		return hsn;
	}

	public void setHsn(String hsn) {
		this.hsn = hsn;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Double getCgst_sgst_rate() {
		return cgst_sgst_rate;
	}

	public void setCgst_sgst_rate(Double cgst_sgst_rate) {
		this.cgst_sgst_rate = cgst_sgst_rate;
	}

	public Double getIgst_rate() {
		return igst_rate;
	}

	public void setIgst_rate(Double igst_rate) {
		this.igst_rate = igst_rate;
	}

	public Double getCess_Advol_rate() {
		return cess_Advol_rate;
	}

	public void setCess_Advol_rate(Double cess_Advol_rate) {
		this.cess_Advol_rate = cess_Advol_rate;
	}

	public Double getCess_non_Advol_rate() {
		return cess_non_Advol_rate;
	}

	public void setCess_non_Advol_rate(Double cess_non_Advol_rate) {
		this.cess_non_Advol_rate = cess_non_Advol_rate;
	}

	public String getBill_from_GSTIN() {
		return bill_from_GSTIN;
	}

	public void setBill_from_GSTIN(String bill_from_GSTIN) {
		this.bill_from_GSTIN = bill_from_GSTIN;
	}

	public String getInvoiceyear() {
		return invoiceyear;
	}

	public void setInvoiceyear(String invoiceyear) {
		this.invoiceyear = invoiceyear;
	}

	public String getInvoicemonth() {
		return invoicemonth;
	}

	public void setInvoicemonth(String invoicemonth) {
		this.invoicemonth = invoicemonth;
	}

	@Override
	public String toString() {
		return "Invoice_product_data_amended [id=" + id + ", invoiceid=" + invoiceid + ", productname=" + productname
				+ ", description=" + description + ", hsn=" + hsn + ", quantity=" + quantity + ", unit=" + unit
				+ ", value=" + value + ", cgst_sgst_rate=" + cgst_sgst_rate + ", igst_rate=" + igst_rate
				+ ", cess_Advol_rate=" + cess_Advol_rate + ", cess_non_Advol_rate=" + cess_non_Advol_rate
				+ ", bill_from_GSTIN=" + bill_from_GSTIN + ", invoiceyear=" + invoiceyear + ", invoicemonth="
				+ invoicemonth + ", getId()=" + getId() + ", getInvoiceid()=" + getInvoiceid() + ", getProductname()="
				+ getProductname() + ", getDescription()=" + getDescription() + ", getHsn()=" + getHsn()
				+ ", getQuantity()=" + getQuantity() + ", getUnit()=" + getUnit() + ", getValue()=" + getValue()
				+ ", getCgst_sgst_rate()=" + getCgst_sgst_rate() + ", getIgst_rate()=" + getIgst_rate()
				+ ", getCess_Advol_rate()=" + getCess_Advol_rate() + ", getCess_non_Advol_rate()="
				+ getCess_non_Advol_rate() + ", getBill_from_GSTIN()=" + getBill_from_GSTIN() + ", getInvoiceyear()="
				+ getInvoiceyear() + ", getInvoicemonth()=" + getInvoicemonth() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	

}
