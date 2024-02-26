package com.Tasko.Registration.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
 @Entity 
public class Client_tally_backupfile {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private  Long id;
	 
	    private String  pan;
	    @JsonFormat(pattern="yyyy-MM-dd")
	    private Date date;

	    private String imageNameBackupfile;

	    private String imagePathBackupfile;

		public Long getId() {
			return id;
		}

		public String getPan() {
			return pan;
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

		public void setPan(String pan) {
			this.pan = pan;
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
			return "Client_tally_backupfile [id=" + id + ", pan=" + pan + ", date=" + date + ", imageNameBackupfile="
					+ imageNameBackupfile + ", imagePathBackupfile=" + imagePathBackupfile + ", getId()=" + getId()
					+ ", getPan()=" + getPan() + ", getDate()=" + getDate() + ", getImageNameBackupfile()="
					+ getImageNameBackupfile() + ", getImagePathBackupfile()=" + getImagePathBackupfile()
					+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
					+ "]";
		}

		public Client_tally_backupfile(Long id, String pan, Date date, String imageNameBackupfile,
				String imagePathBackupfile) {
			super();
			this.id = id;
			this.pan = pan;
			this.date = date;
			this.imageNameBackupfile = imageNameBackupfile;
			this.imagePathBackupfile = imagePathBackupfile;
		}

		public Client_tally_backupfile() {
			super();
			// TODO Auto-generated constructor stub
		}

		
	    
		
		
	    
}
