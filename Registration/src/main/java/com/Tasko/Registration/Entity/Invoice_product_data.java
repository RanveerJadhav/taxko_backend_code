package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Invoice_product_data {

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

	public Long getInvoiceid() {
		return invoiceid;
	}

	public String getProductname() {
		return productname;
	}

	public String getDescription() {
		return description;
	}

	public String getHsn() {
		return hsn;
	}

	public Long getQuantity() {
		return quantity;
	}

	public String getUnit() {
		return unit;
	}

	public String getValue() {
		return value;
	}

	public Double getCgst_sgst_rate() {
		return cgst_sgst_rate;
	}

	public Double getIgst_rate() {
		return igst_rate;
	}

	public Double getCess_Advol_rate() {
		return cess_Advol_rate;
	}

	public Double getCess_non_Advol_rate() {
		return cess_non_Advol_rate;
	}

	public String getBill_from_GSTIN() {
		return bill_from_GSTIN;
	}

	public String getInvoiceyear() {
		return invoiceyear;
	}

	public String getInvoicemonth() {
		return invoicemonth;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInvoiceid(Long invoiceid) {
		this.invoiceid = invoiceid;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHsn(String hsn) {
		this.hsn = hsn;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setCgst_sgst_rate(Double cgst_sgst_rate) {
		this.cgst_sgst_rate = cgst_sgst_rate;
	}

	public void setIgst_rate(Double igst_rate) {
		this.igst_rate = igst_rate;
	}

	public void setCess_Advol_rate(Double cess_Advol_rate) {
		this.cess_Advol_rate = cess_Advol_rate;
	}

	public void setCess_non_Advol_rate(Double cess_non_Advol_rate) {
		this.cess_non_Advol_rate = cess_non_Advol_rate;
	}

	public void setBill_from_GSTIN(String bill_from_GSTIN) {
		this.bill_from_GSTIN = bill_from_GSTIN;
	}

	public void setInvoiceyear(String invoiceyear) {
		this.invoiceyear = invoiceyear;
	}

	public void setInvoicemonth(String invoicemonth) {
		this.invoicemonth = invoicemonth;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Invoice_product_data [id=");
		builder.append(id);
		builder.append(", invoiceid=");
		builder.append(invoiceid);
		builder.append(", productname=");
		builder.append(productname);
		builder.append(", description=");
		builder.append(description);
		builder.append(", hsn=");
		builder.append(hsn);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", value=");
		builder.append(value);
		builder.append(", cgst_sgst_rate=");
		builder.append(cgst_sgst_rate);
		builder.append(", igst_rate=");
		builder.append(igst_rate);
		builder.append(", cess_Advol_rate=");
		builder.append(cess_Advol_rate);
		builder.append(", cess_non_Advol_rate=");
		builder.append(cess_non_Advol_rate);
		builder.append(", bill_from_GSTIN=");
		builder.append(bill_from_GSTIN);
		builder.append(", invoiceyear=");
		builder.append(invoiceyear);
		builder.append(", invoicemonth=");
		builder.append(invoicemonth);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getInvoiceid()=");
		builder.append(getInvoiceid());
		builder.append(", getProductname()=");
		builder.append(getProductname());
		builder.append(", getDescription()=");
		builder.append(getDescription());
		builder.append(", getHsn()=");
		builder.append(getHsn());
		builder.append(", getQuantity()=");
		builder.append(getQuantity());
		builder.append(", getUnit()=");
		builder.append(getUnit());
		builder.append(", getValue()=");
		builder.append(getValue());
		builder.append(", getCgst_sgst_rate()=");
		builder.append(getCgst_sgst_rate());
		builder.append(", getIgst_rate()=");
		builder.append(getIgst_rate());
		builder.append(", getCess_Advol_rate()=");
		builder.append(getCess_Advol_rate());
		builder.append(", getCess_non_Advol_rate()=");
		builder.append(getCess_non_Advol_rate());
		builder.append(", getBill_from_GSTIN()=");
		builder.append(getBill_from_GSTIN());
		builder.append(", getInvoiceyear()=");
		builder.append(getInvoiceyear());
		builder.append(", getInvoicemonth()=");
		builder.append(getInvoicemonth());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	public Invoice_product_data(Long id, Long invoiceid, String productname, String description, String hsn,
			Long quantity, String unit, String value, Double cgst_sgst_rate, Double igst_rate, Double cess_Advol_rate,
			Double cess_non_Advol_rate, String bill_from_GSTIN, String invoiceyear, String invoicemonth) {
		super();
		this.id = id;
		this.invoiceid = invoiceid;
		this.productname = productname;
		this.description = description;
		this.hsn = hsn;
		this.quantity = quantity;
		this.unit = unit;
		this.value = value;
		this.cgst_sgst_rate = cgst_sgst_rate;
		this.igst_rate = igst_rate;
		this.cess_Advol_rate = cess_Advol_rate;
		this.cess_non_Advol_rate = cess_non_Advol_rate;
		this.bill_from_GSTIN = bill_from_GSTIN;
		this.invoiceyear = invoiceyear;
		this.invoicemonth = invoicemonth;
	}

	public Invoice_product_data() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
}
