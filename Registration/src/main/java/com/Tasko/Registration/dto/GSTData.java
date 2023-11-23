package com.Tasko.Registration.dto;

public class GSTData 
{
	  private String month;
	    private Long GSTR1FD;
	    private Long GSTR1NFD;
	    private Long GSTR3BFD;
	    private Long GSTR3BNFD;
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public Long getGSTR1FD() {
			return GSTR1FD;
		}
		public void setGSTR1FD(Long gSTR1FD) {
			GSTR1FD = gSTR1FD;
		}
		public Long getGSTR1NFD() {
			return GSTR1NFD;
		}
		public void setGSTR1NFD(Long gSTR1NFD) {
			GSTR1NFD = gSTR1NFD;
		}
		public Long getGSTR3BFD() {
			return GSTR3BFD;
		}
		public void setGSTR3BFD(Long gSTR3BFD) {
			GSTR3BFD = gSTR3BFD;
		}
		public Long getGSTR3BNFD() {
			return GSTR3BNFD;
		}
		public void setGSTR3BNFD(Long gSTR3BNFD) {
			GSTR3BNFD = gSTR3BNFD;
		}
		public GSTData(String month, Long gSTR1FD, Long gSTR1NFD, Long gSTR3BFD, Long gSTR3BNFD) {
			super();
			this.month = month;
			GSTR1FD = gSTR1FD;
			GSTR1NFD = gSTR1NFD;
			GSTR3BFD = gSTR3BFD;
			GSTR3BNFD = gSTR3BNFD;
		}

	    
	    
    
}
