package com.Tasko.Registration.Entity;

import java.util.Date;



import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Subscription_Userdata {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
	
	private Long userid;
	
	private String name;
	
	private String mobile;
	
	@Column(unique=true)
	private String pan;
	
	@Column(name ="registrationdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date registrationdate;
	
	@Column(name ="substartdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date substartdate;
	
	@Column(name ="substartdatebyuser")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date substartdatebyuser;
	
	@Column(name ="substartdatebyadmin")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date substartdatebyadmin;
	
	@Column(name ="subendtdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date subendtdate;
	
	private String subscriptiontype;
	
	private Long acessclient;
	
	private String imageName;

	private String imagePath;
	
	private boolean paid;

	private Long subscriptionprice;
	
	private String refrenceId;
	
	private  String disrefrenceId;
	
    private  String salespersonId;
	
	private Long dissalespersonId;
	
	private boolean forcestop;

	public Long getId() {
		return id;
	}

	public Long getUserid() {
		return userid;
	}

	public String getName() {
		return name;
	}

	public String getMobile() {
		return mobile;
	}

	public String getPan() {
		return pan;
	}

	public Date getRegistrationdate() {
		return registrationdate;
	}

	public Date getSubstartdate() {
		return substartdate;
	}

	public Date getSubstartdatebyuser() {
		return substartdatebyuser;
	}

	public Date getSubstartdatebyadmin() {
		return substartdatebyadmin;
	}

	public Date getSubendtdate() {
		return subendtdate;
	}

	public String getSubscriptiontype() {
		return subscriptiontype;
	}

	public Long getAcessclient() {
		return acessclient;
	}

	public String getImageName() {
		return imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public boolean isPaid() {
		return paid;
	}

	public Long getSubscriptionprice() {
		return subscriptionprice;
	}

	public String getRefrenceId() {
		return refrenceId;
	}

	public String getDisrefrenceId() {
		return disrefrenceId;
	}

	public String getSalespersonId() {
		return salespersonId;
	}

	public Long getDissalespersonId() {
		return dissalespersonId;
	}

	public boolean isForcestop() {
		return forcestop;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}

	public void setSubstartdate(Date substartdate) {
		this.substartdate = substartdate;
	}

	public void setSubstartdatebyuser(Date substartdatebyuser) {
		this.substartdatebyuser = substartdatebyuser;
	}

	public void setSubstartdatebyadmin(Date substartdatebyadmin) {
		this.substartdatebyadmin = substartdatebyadmin;
	}

	public void setSubendtdate(Date subendtdate) {
		this.subendtdate = subendtdate;
	}

	public void setSubscriptiontype(String subscriptiontype) {
		this.subscriptiontype = subscriptiontype;
	}

	public void setAcessclient(Long acessclient) {
		this.acessclient = acessclient;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public void setSubscriptionprice(Long subscriptionprice) {
		this.subscriptionprice = subscriptionprice;
	}

	public void setRefrenceId(String refrenceId) {
		this.refrenceId = refrenceId;
	}

	public void setDisrefrenceId(String disrefrenceId) {
		this.disrefrenceId = disrefrenceId;
	}

	public void setSalespersonId(String salespersonId) {
		this.salespersonId = salespersonId;
	}

	public void setDissalespersonId(Long dissalespersonId) {
		this.dissalespersonId = dissalespersonId;
	}

	public void setForcestop(boolean forcestop) {
		this.forcestop = forcestop;
	}

	@Override
	public String toString() {
		return "Subscription_Userdata [id=" + id + ", userid=" + userid + ", name=" + name + ", mobile=" + mobile
				+ ", pan=" + pan + ", registrationdate=" + registrationdate + ", substartdate=" + substartdate
				+ ", substartdatebyuser=" + substartdatebyuser + ", substartdatebyadmin=" + substartdatebyadmin
				+ ", subendtdate=" + subendtdate + ", subscriptiontype=" + subscriptiontype + ", acessclient="
				+ acessclient + ", imageName=" + imageName + ", imagePath=" + imagePath + ", paid=" + paid
				+ ", subscriptionprice=" + subscriptionprice + ", refrenceId=" + refrenceId + ", disrefrenceId="
				+ disrefrenceId + ", salespersonId=" + salespersonId + ", dissalespersonId=" + dissalespersonId
				+ ", forcestop=" + forcestop + ", getId()=" + getId() + ", getUserid()=" + getUserid() + ", getName()="
				+ getName() + ", getMobile()=" + getMobile() + ", getPan()=" + getPan() + ", getRegistrationdate()="
				+ getRegistrationdate() + ", getSubstartdate()=" + getSubstartdate() + ", getSubstartdatebyuser()="
				+ getSubstartdatebyuser() + ", getSubstartdatebyadmin()=" + getSubstartdatebyadmin()
				+ ", getSubendtdate()=" + getSubendtdate() + ", getSubscriptiontype()=" + getSubscriptiontype()
				+ ", getAcessclient()=" + getAcessclient() + ", getImageName()=" + getImageName() + ", getImagePath()="
				+ getImagePath() + ", isPaid()=" + isPaid() + ", getSubscriptionprice()=" + getSubscriptionprice()
				+ ", getRefrenceId()=" + getRefrenceId() + ", getDisrefrenceId()=" + getDisrefrenceId()
				+ ", getSalespersonId()=" + getSalespersonId() + ", getDissalespersonId()=" + getDissalespersonId()
				+ ", isForcestop()=" + isForcestop() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public Subscription_Userdata(Long id, Long userid, String name, String mobile, String pan, Date registrationdate,
			Date substartdate, Date substartdatebyuser, Date substartdatebyadmin, Date subendtdate,
			String subscriptiontype, Long acessclient, String imageName, String imagePath, boolean paid,
			Long subscriptionprice, String refrenceId, String disrefrenceId, String salespersonId,
			Long dissalespersonId, boolean forcestop) {
		super();
		this.id = id;
		this.userid = userid;
		this.name = name;
		this.mobile = mobile;
		this.pan = pan;
		this.registrationdate = registrationdate;
		this.substartdate = substartdate;
		this.substartdatebyuser = substartdatebyuser;
		this.substartdatebyadmin = substartdatebyadmin;
		this.subendtdate = subendtdate;
		this.subscriptiontype = subscriptiontype;
		this.acessclient = acessclient;
		this.imageName = imageName;
		this.imagePath = imagePath;
		this.paid = paid;
		this.subscriptionprice = subscriptionprice;
		this.refrenceId = refrenceId;
		this.disrefrenceId = disrefrenceId;
		this.salespersonId = salespersonId;
		this.dissalespersonId = dissalespersonId;
		this.forcestop = forcestop;
	}

	public Subscription_Userdata() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
