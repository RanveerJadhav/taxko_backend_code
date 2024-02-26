package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class User_CLient_tally_backupfile {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private  Long id;
	 
	     private Long userid;
	    
	     private Long clientid;
	     
	     @JsonFormat(pattern="yyyy-MM-dd")
	     private Date date;

	    private String imageNameBackupfile;

	    private String imagePathBackupfile;

		public Long getId() {
			return id;
		}

		public Long getUserid() {
			return userid;
		}

		public Long getClientid() {
			return clientid;
		}

		public Date getDate() {
			return date;
		}

		public String getImageNameBackupfile() {
			return imageNameBackupfile;
		}

		public String getImagePathBackupfile() {
			return imagePathBackupfile;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setUserid(Long userid) {
			this.userid = userid;
		}

		public void setClientid(Long clientid) {
			this.clientid = clientid;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public void setImageNameBackupfile(String imageNameBackupfile) {
			this.imageNameBackupfile = imageNameBackupfile;
		}

		public void setImagePathBackupfile(String imagePathBackupfile) {
			this.imagePathBackupfile = imagePathBackupfile;
		}

		@Override
		public String toString() {
			return "User_CLient_tally_backupfile [id=" + id + ", userid=" + userid + ", clientid=" + clientid
					+ ", date=" + date + ", imageNameBackupfile=" + imageNameBackupfile + ", imagePathBackupfile="
					+ imagePathBackupfile + ", getId()=" + getId() + ", getUserid()=" + getUserid() + ", getClientid()="
					+ getClientid() + ", getDate()=" + getDate() + ", getImageNameBackupfile()="
					+ getImageNameBackupfile() + ", getImagePathBackupfile()=" + getImagePathBackupfile()
					+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
					+ "]";
		}

		public User_CLient_tally_backupfile(Long id, Long userid, Long clientid, Date date, String imageNameBackupfile,
				String imagePathBackupfile) {
			super();
			this.id = id;
			this.userid = userid;
			this.clientid = clientid;
			this.date = date;
			this.imageNameBackupfile = imageNameBackupfile;
			this.imagePathBackupfile = imagePathBackupfile;
		}

		public User_CLient_tally_backupfile() {
			super();
			// TODO Auto-generated constructor stub
		}

		
	    
	    

}
