package com.Tasko.Registration.dto;

public class ProfessionCountDTO {
	 private String profession;
	    private long count;

	    public ProfessionCountDTO(String profession, long count) {
	        this.profession = profession;
	        this.count = count;
	    }

		public String getProfession() {
			return profession;
		}

		public void setProfession(String profession) {
			this.profession = profession;
		}

		public long getCount() {
			return count;
		}

		public void setCount(long count) {
			this.count = count;
		}

}
