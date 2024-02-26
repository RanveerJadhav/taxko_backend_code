package com.Tasko.Registration.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userid;

    private Long clientid;

    private String text;

    private String imagePath;

    private String sendDate;

    private String notificationFrom;

    private String notificationTo;

    private String category;

   private boolean notificationView;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getNotificationFrom() {
        return notificationFrom;
    }

    public void setNotificationFrom(String notificationFrom) {
        this.notificationFrom = notificationFrom;
    }

    public String getNotificationTo() {
        return notificationTo;
    }

    public void setNotificationTo(String notificationTo) {
        this.notificationTo = notificationTo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isNotificationView() {
        return notificationView;
    }

    public void setNotificationView(boolean notificationView) {
        this.notificationView = notificationView;
    }

    public Notification(Long id, Long userid, Long clientid, String text, String imagePath, String sendDate, String notificationFrom, String notificationTo, String category, boolean notificationView) {
        this.id = id;
        this.userid = userid;
        this.clientid = clientid;
        this.text = text;
        this.imagePath = imagePath;
        this.sendDate = sendDate;
        this.notificationFrom = notificationFrom;
        this.notificationTo = notificationTo;
        this.category = category;
        this.notificationView = notificationView;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", userid=" + userid +
                ", clientid=" + clientid +
                ", text='" + text + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", sendDate='" + sendDate + '\'' +
                ", notificationFrom='" + notificationFrom + '\'' +
                ", notificationTo='" + notificationTo + '\'' +
                ", category='" + category + '\'' +
                ", notificationView=" + notificationView +
                '}';
    }

    public Notification() {
    }
}
