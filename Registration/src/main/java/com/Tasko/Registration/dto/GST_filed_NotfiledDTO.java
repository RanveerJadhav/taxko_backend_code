package com.Tasko.Registration.dto;

public class GST_filed_NotfiledDTO 
{
		private String category;
	  private String month;
	    private Long filed;
	    private Long Notfiled;
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public Long getFiled() {
			return filed;
		}
		public void setFiled(Long filed) {
			this.filed = filed;
		}
		public Long getNotfiled() {
			return Notfiled;
		}
		public void setNotfiled(Long notfiled) {
			Notfiled = notfiled;
		}
		public GST_filed_NotfiledDTO(String category, String month, Long filed, Long notfiled) {
			super();
			this.category = category;
			this.month = month;
			this.filed = filed;
			Notfiled = notfiled;
		}
		public GST_filed_NotfiledDTO() {
			super();
		}
		@Override
		public String toString() {
			return "GST_filed_NotfiledDTO [category=" + category + ", month=" + month + ", filed=" + filed
					+ ", Notfiled=" + Notfiled + "]";
		}

	    
	    
	    

}
