package com.Tasko.Registration.Entity;

import jakarta.persistence.*;



@Entity
@Table(name = "files")
public class FileEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique=true)
    private String fileName;
    
    private String filePath;
    private Long userid;
    private Long clientid;
    private String accountyear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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


    public FileEntity(Long id, String fileName, String filePath, Long userid, Long clientid, String accountyear) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.userid = userid;
        this.clientid = clientid;
        this.accountyear = accountyear;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", userid=" + userid +
                ", clientid=" + clientid +
                ", accountyear='" + accountyear + '\'' +
                '}';
    }

    public FileEntity() {
    }

}