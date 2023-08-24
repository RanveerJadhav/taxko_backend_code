package com.Tasko.Registration.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Filed_NotFiled
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userid;

    private Long clientid;

    private String accountyear;

    private String  filednotfiled;

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

    public String getAccountyear() {
        return accountyear;
    }

    public void setAccountyear(String accountyear) {
        this.accountyear = accountyear;
    }

    public String getFilednotfiled() {
        return filednotfiled;
    }

    public void setFilednotfiled(String filednotfiled) {
        this.filednotfiled = filednotfiled;
    }

    public Filed_NotFiled(Long id, Long userid, Long clientid, String accountyear, String filednotfiled) {
        this.id = id;
        this.userid = userid;
        this.clientid = clientid;
        this.accountyear = accountyear;
        this.filednotfiled = filednotfiled;
    }

    @Override
    public String toString() {
        return "Filed_NotFiled{" +
                "id=" + id +
                ", userid=" + userid +
                ", clientid=" + clientid +
                ", accountyear='" + accountyear + '\'' +
                ", filednotfiled='" + filednotfiled + '\'' +
                '}';
    }

    public Filed_NotFiled() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filed_NotFiled)) return false;
        Filed_NotFiled other = (Filed_NotFiled) o;
        return Objects.equals(this.userid, other.userid) &&
                Objects.equals(this.clientid, other.clientid) &&
                Objects.equals(this.accountyear, other.accountyear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, clientid, accountyear);
    }
}
