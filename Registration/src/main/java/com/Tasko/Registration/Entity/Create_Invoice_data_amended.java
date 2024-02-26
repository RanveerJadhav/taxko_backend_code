package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Create_Invoice_data_amended {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	
	private  Long invoiceid;
	
	private String supplytype;
	
	private String subtype;
	
	private String documenttype;
	
	private String documentno;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date documentdate;
	
    private String bill_from_Name;
    
	private String bill_from_GSTIN;
		
	private String bill_from_State;
	
	private String dispatch_from_Address;
	
	private String dispatch_from_Place;
	
	private String dispatch_from_Pincode;
	
	private String bill_to_Name;
	
	private String bill_to_GSTIN;
	
	private String bill_to_State;
	
	private String ship_to_Address;
	
	private String ship_to_Place;
	
	private String  ship_to_Pincode;
	
	private Double  total_taxiable_value;
	
	private Double  cgst_amount;
	
	private Double sgst_amount;
	
	private Double igst_amount;
	
	private Double rate;
	
	private Double cess_advol_amount;
	
	private Double cess_non_advol_amount;
	
	private Double other_amount;
	
	private Double total_inv_amount;
	
	private String transportation_transpoter_id;
	
	private String transportation_transpoter_Name;
	
	private Double transportation_Approxiamte_distance;
	
	private  String part_b_mode;
	
	private  String part_b_vechiletype;
	
	private  String part_b_vechileNo;
	
	private  String Part_b_Transper_doc;
	
	@Column(name = "transper_doc_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private  Date part_b_Transper_doc_no_date;
	
	
	private boolean invoiceImport;
	
	private boolean cancelled;
	
	private boolean amended;
	
	private boolean interstate;
	
	private boolean rcm;
	
	private boolean deemexport;
	
	private boolean sez;
	
	private boolean credit_note;
	
	private Double credit_amount;

	@Column(name = "issueDate")
	@JsonFormat(pattern="yyyy-MM-dd")
	private  Date issueDate;
	
	private String invoiceyear;
	
	private String invoicemonth;
	
	private String dispatch_from_State;
	private String ship_to_State;
	private String transaction_Type;
	@Column(name = "invoicedate")
	@JsonFormat(pattern="yyyy-MM-dd")
	private  Date invoicedate;
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
	public String getSupplytype() {
		return supplytype;
	}
	public void setSupplytype(String supplytype) {
		this.supplytype = supplytype;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getDocumenttype() {
		return documenttype;
	}
	public void setDocumenttype(String documenttype) {
		this.documenttype = documenttype;
	}
	public String getDocumentno() {
		return documentno;
	}
	public void setDocumentno(String documentno) {
		this.documentno = documentno;
	}
	public Date getDocumentdate() {
		return documentdate;
	}
	public void setDocumentdate(Date documentdate) {
		this.documentdate = documentdate;
	}
	public String getBill_from_Name() {
		return bill_from_Name;
	}
	public void setBill_from_Name(String bill_from_Name) {
		this.bill_from_Name = bill_from_Name;
	}
	public String getBill_from_GSTIN() {
		return bill_from_GSTIN;
	}
	public void setBill_from_GSTIN(String bill_from_GSTIN) {
		this.bill_from_GSTIN = bill_from_GSTIN;
	}
	public String getBill_from_State() {
		return bill_from_State;
	}
	public void setBill_from_State(String bill_from_State) {
		this.bill_from_State = bill_from_State;
	}
	public String getDispatch_from_Address() {
		return dispatch_from_Address;
	}
	public void setDispatch_from_Address(String dispatch_from_Address) {
		this.dispatch_from_Address = dispatch_from_Address;
	}
	public String getDispatch_from_Place() {
		return dispatch_from_Place;
	}
	public void setDispatch_from_Place(String dispatch_from_Place) {
		this.dispatch_from_Place = dispatch_from_Place;
	}
	public String getDispatch_from_Pincode() {
		return dispatch_from_Pincode;
	}
	public void setDispatch_from_Pincode(String dispatch_from_Pincode) {
		this.dispatch_from_Pincode = dispatch_from_Pincode;
	}
	public String getBill_to_Name() {
		return bill_to_Name;
	}
	public void setBill_to_Name(String bill_to_Name) {
		this.bill_to_Name = bill_to_Name;
	}
	public String getBill_to_GSTIN() {
		return bill_to_GSTIN;
	}
	public void setBill_to_GSTIN(String bill_to_GSTIN) {
		this.bill_to_GSTIN = bill_to_GSTIN;
	}
	public String getBill_to_State() {
		return bill_to_State;
	}
	public void setBill_to_State(String bill_to_State) {
		this.bill_to_State = bill_to_State;
	}
	public String getShip_to_Address() {
		return ship_to_Address;
	}
	public void setShip_to_Address(String ship_to_Address) {
		this.ship_to_Address = ship_to_Address;
	}
	public String getShip_to_Place() {
		return ship_to_Place;
	}
	public void setShip_to_Place(String ship_to_Place) {
		this.ship_to_Place = ship_to_Place;
	}
	public String getShip_to_Pincode() {
		return ship_to_Pincode;
	}
	public void setShip_to_Pincode(String ship_to_Pincode) {
		this.ship_to_Pincode = ship_to_Pincode;
	}
	public Double getTotal_taxiable_value() {
		return total_taxiable_value;
	}
	public void setTotal_taxiable_value(Double total_taxiable_value) {
		this.total_taxiable_value = total_taxiable_value;
	}
	public Double getCgst_amount() {
		return cgst_amount;
	}
	public void setCgst_amount(Double cgst_amount) {
		this.cgst_amount = cgst_amount;
	}
	public Double getSgst_amount() {
		return sgst_amount;
	}
	public void setSgst_amount(Double sgst_amount) {
		this.sgst_amount = sgst_amount;
	}
	public Double getIgst_amount() {
		return igst_amount;
	}
	public void setIgst_amount(Double igst_amount) {
		this.igst_amount = igst_amount;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getCess_advol_amount() {
		return cess_advol_amount;
	}
	public void setCess_advol_amount(Double cess_advol_amount) {
		this.cess_advol_amount = cess_advol_amount;
	}
	public Double getCess_non_advol_amount() {
		return cess_non_advol_amount;
	}
	public void setCess_non_advol_amount(Double cess_non_advol_amount) {
		this.cess_non_advol_amount = cess_non_advol_amount;
	}
	public Double getOther_amount() {
		return other_amount;
	}
	public void setOther_amount(Double other_amount) {
		this.other_amount = other_amount;
	}
	public Double getTotal_inv_amount() {
		return total_inv_amount;
	}
	public void setTotal_inv_amount(Double total_inv_amount) {
		this.total_inv_amount = total_inv_amount;
	}
	public String getTransportation_transpoter_id() {
		return transportation_transpoter_id;
	}
	public void setTransportation_transpoter_id(String transportation_transpoter_id) {
		this.transportation_transpoter_id = transportation_transpoter_id;
	}
	public String getTransportation_transpoter_Name() {
		return transportation_transpoter_Name;
	}
	public void setTransportation_transpoter_Name(String transportation_transpoter_Name) {
		this.transportation_transpoter_Name = transportation_transpoter_Name;
	}
	public Double getTransportation_Approxiamte_distance() {
		return transportation_Approxiamte_distance;
	}
	public void setTransportation_Approxiamte_distance(Double transportation_Approxiamte_distance) {
		this.transportation_Approxiamte_distance = transportation_Approxiamte_distance;
	}
	public String getPart_b_mode() {
		return part_b_mode;
	}
	public void setPart_b_mode(String part_b_mode) {
		this.part_b_mode = part_b_mode;
	}
	public String getPart_b_vechiletype() {
		return part_b_vechiletype;
	}
	public void setPart_b_vechiletype(String part_b_vechiletype) {
		this.part_b_vechiletype = part_b_vechiletype;
	}
	public String getPart_b_vechileNo() {
		return part_b_vechileNo;
	}
	public void setPart_b_vechileNo(String part_b_vechileNo) {
		this.part_b_vechileNo = part_b_vechileNo;
	}
	public String getPart_b_Transper_doc() {
		return Part_b_Transper_doc;
	}
	public void setPart_b_Transper_doc(String part_b_Transper_doc) {
		Part_b_Transper_doc = part_b_Transper_doc;
	}
	public Date getPart_b_Transper_doc_no_date() {
		return part_b_Transper_doc_no_date;
	}
	public void setPart_b_Transper_doc_no_date(Date part_b_Transper_doc_no_date) {
		this.part_b_Transper_doc_no_date = part_b_Transper_doc_no_date;
	}
	public boolean isInvoiceImport() {
		return invoiceImport;
	}
	public void setInvoiceImport(boolean invoiceImport) {
		this.invoiceImport = invoiceImport;
	}
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	public boolean isAmended() {
		return amended;
	}
	public void setAmended(boolean amended) {
		this.amended = amended;
	}
	public boolean isInterstate() {
		return interstate;
	}
	public void setInterstate(boolean interstate) {
		this.interstate = interstate;
	}
	public boolean isRcm() {
		return rcm;
	}
	public void setRcm(boolean rcm) {
		this.rcm = rcm;
	}
	public boolean isDeemexport() {
		return deemexport;
	}
	public void setDeemexport(boolean deemexport) {
		this.deemexport = deemexport;
	}
	public boolean isSez() {
		return sez;
	}
	public void setSez(boolean sez) {
		this.sez = sez;
	}
	public boolean isCredit_note() {
		return credit_note;
	}
	public void setCredit_note(boolean credit_note) {
		this.credit_note = credit_note;
	}
	public Double getCredit_amount() {
		return credit_amount;
	}
	public void setCredit_amount(Double credit_amount) {
		this.credit_amount = credit_amount;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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
	public String getDispatch_from_State() {
		return dispatch_from_State;
	}
	public void setDispatch_from_State(String dispatch_from_State) {
		this.dispatch_from_State = dispatch_from_State;
	}
	public String getShip_to_State() {
		return ship_to_State;
	}
	public void setShip_to_State(String ship_to_State) {
		this.ship_to_State = ship_to_State;
	}
	public String getTransaction_Type() {
		return transaction_Type;
	}
	public void setTransaction_Type(String transaction_Type) {
		this.transaction_Type = transaction_Type;
	}
	public Date getInvoicedate() {
		return invoicedate;
	}
	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}
	@Override
	public String toString() {
		return "Create_Invoice_data_amended [id=" + id + ", invoiceid=" + invoiceid + ", supplytype=" + supplytype
				+ ", subtype=" + subtype + ", documenttype=" + documenttype + ", documentno=" + documentno
				+ ", documentdate=" + documentdate + ", bill_from_Name=" + bill_from_Name + ", bill_from_GSTIN="
				+ bill_from_GSTIN + ", bill_from_State=" + bill_from_State + ", dispatch_from_Address="
				+ dispatch_from_Address + ", dispatch_from_Place=" + dispatch_from_Place + ", dispatch_from_Pincode="
				+ dispatch_from_Pincode + ", bill_to_Name=" + bill_to_Name + ", bill_to_GSTIN=" + bill_to_GSTIN
				+ ", bill_to_State=" + bill_to_State + ", ship_to_Address=" + ship_to_Address + ", ship_to_Place="
				+ ship_to_Place + ", ship_to_Pincode=" + ship_to_Pincode + ", total_taxiable_value="
				+ total_taxiable_value + ", cgst_amount=" + cgst_amount + ", sgst_amount=" + sgst_amount
				+ ", igst_amount=" + igst_amount + ", rate=" + rate + ", cess_advol_amount=" + cess_advol_amount
				+ ", cess_non_advol_amount=" + cess_non_advol_amount + ", other_amount=" + other_amount
				+ ", total_inv_amount=" + total_inv_amount + ", transportation_transpoter_id="
				+ transportation_transpoter_id + ", transportation_transpoter_Name=" + transportation_transpoter_Name
				+ ", transportation_Approxiamte_distance=" + transportation_Approxiamte_distance + ", part_b_mode="
				+ part_b_mode + ", part_b_vechiletype=" + part_b_vechiletype + ", part_b_vechileNo=" + part_b_vechileNo
				+ ", Part_b_Transper_doc=" + Part_b_Transper_doc + ", part_b_Transper_doc_no_date="
				+ part_b_Transper_doc_no_date + ", invoiceImport=" + invoiceImport + ", cancelled=" + cancelled
				+ ", amended=" + amended + ", interstate=" + interstate + ", rcm=" + rcm + ", deemexport=" + deemexport
				+ ", sez=" + sez + ", credit_note=" + credit_note + ", credit_amount=" + credit_amount + ", issueDate="
				+ issueDate + ", invoiceyear=" + invoiceyear + ", invoicemonth=" + invoicemonth
				+ ", dispatch_from_State=" + dispatch_from_State + ", ship_to_State=" + ship_to_State
				+ ", transaction_Type=" + transaction_Type + ", invoicedate=" + invoicedate + ", getId()=" + getId()
				+ ", getInvoiceid()=" + getInvoiceid() + ", getSupplytype()=" + getSupplytype() + ", getSubtype()="
				+ getSubtype() + ", getDocumenttype()=" + getDocumenttype() + ", getDocumentno()=" + getDocumentno()
				+ ", getDocumentdate()=" + getDocumentdate() + ", getBill_from_Name()=" + getBill_from_Name()
				+ ", getBill_from_GSTIN()=" + getBill_from_GSTIN() + ", getBill_from_State()=" + getBill_from_State()
				+ ", getDispatch_from_Address()=" + getDispatch_from_Address() + ", getDispatch_from_Place()="
				+ getDispatch_from_Place() + ", getDispatch_from_Pincode()=" + getDispatch_from_Pincode()
				+ ", getBill_to_Name()=" + getBill_to_Name() + ", getBill_to_GSTIN()=" + getBill_to_GSTIN()
				+ ", getBill_to_State()=" + getBill_to_State() + ", getShip_to_Address()=" + getShip_to_Address()
				+ ", getShip_to_Place()=" + getShip_to_Place() + ", getShip_to_Pincode()=" + getShip_to_Pincode()
				+ ", getTotal_taxiable_value()=" + getTotal_taxiable_value() + ", getCgst_amount()=" + getCgst_amount()
				+ ", getSgst_amount()=" + getSgst_amount() + ", getIgst_amount()=" + getIgst_amount() + ", getRate()="
				+ getRate() + ", getCess_advol_amount()=" + getCess_advol_amount() + ", getCess_non_advol_amount()="
				+ getCess_non_advol_amount() + ", getOther_amount()=" + getOther_amount() + ", getTotal_inv_amount()="
				+ getTotal_inv_amount() + ", getTransportation_transpoter_id()=" + getTransportation_transpoter_id()
				+ ", getTransportation_transpoter_Name()=" + getTransportation_transpoter_Name()
				+ ", getTransportation_Approxiamte_distance()=" + getTransportation_Approxiamte_distance()
				+ ", getPart_b_mode()=" + getPart_b_mode() + ", getPart_b_vechiletype()=" + getPart_b_vechiletype()
				+ ", getPart_b_vechileNo()=" + getPart_b_vechileNo() + ", getPart_b_Transper_doc()="
				+ getPart_b_Transper_doc() + ", getPart_b_Transper_doc_no_date()=" + getPart_b_Transper_doc_no_date()
				+ ", isInvoiceImport()=" + isInvoiceImport() + ", isCancelled()=" + isCancelled() + ", isAmended()="
				+ isAmended() + ", isInterstate()=" + isInterstate() + ", isRcm()=" + isRcm() + ", isDeemexport()="
				+ isDeemexport() + ", isSez()=" + isSez() + ", isCredit_note()=" + isCredit_note()
				+ ", getCredit_amount()=" + getCredit_amount() + ", getIssueDate()=" + getIssueDate()
				+ ", getInvoiceyear()=" + getInvoiceyear() + ", getInvoicemonth()=" + getInvoicemonth()
				+ ", getDispatch_from_State()=" + getDispatch_from_State() + ", getShip_to_State()="
				+ getShip_to_State() + ", getTransaction_Type()=" + getTransaction_Type() + ", getInvoicedate()="
				+ getInvoicedate() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	public Create_Invoice_data_amended(Long id, Long invoiceid, String supplytype, String subtype, String documenttype,
			String documentno, Date documentdate, String bill_from_Name, String bill_from_GSTIN, String bill_from_State,
			String dispatch_from_Address, String dispatch_from_Place, String dispatch_from_Pincode, String bill_to_Name,
			String bill_to_GSTIN, String bill_to_State, String ship_to_Address, String ship_to_Place,
			String ship_to_Pincode, Double total_taxiable_value, Double cgst_amount, Double sgst_amount,
			Double igst_amount, Double rate, Double cess_advol_amount, Double cess_non_advol_amount,
			Double other_amount, Double total_inv_amount, String transportation_transpoter_id,
			String transportation_transpoter_Name, Double transportation_Approxiamte_distance, String part_b_mode,
			String part_b_vechiletype, String part_b_vechileNo, String part_b_Transper_doc,
			Date part_b_Transper_doc_no_date, boolean invoiceImport, boolean cancelled, boolean amended,
			boolean interstate, boolean rcm, boolean deemexport, boolean sez, boolean credit_note, Double credit_amount,
			Date issueDate, String invoiceyear, String invoicemonth, String dispatch_from_State, String ship_to_State,
			String transaction_Type, Date invoicedate) {
		super();
		this.id = id;
		this.invoiceid = invoiceid;
		this.supplytype = supplytype;
		this.subtype = subtype;
		this.documenttype = documenttype;
		this.documentno = documentno;
		this.documentdate = documentdate;
		this.bill_from_Name = bill_from_Name;
		this.bill_from_GSTIN = bill_from_GSTIN;
		this.bill_from_State = bill_from_State;
		this.dispatch_from_Address = dispatch_from_Address;
		this.dispatch_from_Place = dispatch_from_Place;
		this.dispatch_from_Pincode = dispatch_from_Pincode;
		this.bill_to_Name = bill_to_Name;
		this.bill_to_GSTIN = bill_to_GSTIN;
		this.bill_to_State = bill_to_State;
		this.ship_to_Address = ship_to_Address;
		this.ship_to_Place = ship_to_Place;
		this.ship_to_Pincode = ship_to_Pincode;
		this.total_taxiable_value = total_taxiable_value;
		this.cgst_amount = cgst_amount;
		this.sgst_amount = sgst_amount;
		this.igst_amount = igst_amount;
		this.rate = rate;
		this.cess_advol_amount = cess_advol_amount;
		this.cess_non_advol_amount = cess_non_advol_amount;
		this.other_amount = other_amount;
		this.total_inv_amount = total_inv_amount;
		this.transportation_transpoter_id = transportation_transpoter_id;
		this.transportation_transpoter_Name = transportation_transpoter_Name;
		this.transportation_Approxiamte_distance = transportation_Approxiamte_distance;
		this.part_b_mode = part_b_mode;
		this.part_b_vechiletype = part_b_vechiletype;
		this.part_b_vechileNo = part_b_vechileNo;
		Part_b_Transper_doc = part_b_Transper_doc;
		this.part_b_Transper_doc_no_date = part_b_Transper_doc_no_date;
		this.invoiceImport = invoiceImport;
		this.cancelled = cancelled;
		this.amended = amended;
		this.interstate = interstate;
		this.rcm = rcm;
		this.deemexport = deemexport;
		this.sez = sez;
		this.credit_note = credit_note;
		this.credit_amount = credit_amount;
		this.issueDate = issueDate;
		this.invoiceyear = invoiceyear;
		this.invoicemonth = invoicemonth;
		this.dispatch_from_State = dispatch_from_State;
		this.ship_to_State = ship_to_State;
		this.transaction_Type = transaction_Type;
		this.invoicedate = invoicedate;
	}
	public Create_Invoice_data_amended() {
		super();
		// TODO Auto-generated constructor stub
	}
		
}
