package com.Tasko.Registration.dto;

public class ClientCountsDTO
{
    private Long totalClientCount;
    private Long incomeTaxClientCount;
    private Long gst_ClientCount;
	public Long getTotalClientCount() {
		return totalClientCount;
	}
	public void setTotalClientCount(Long totalClientCount) {
		this.totalClientCount = totalClientCount;
	}
	public Long getIncomeTaxClientCount() {
		return incomeTaxClientCount;
	}
	public void setIncomeTaxClientCount(Long incomeTaxClientCount) {
		this.incomeTaxClientCount = incomeTaxClientCount;
	}
	public Long getGst_ClientCount() {
		return gst_ClientCount;
	}
	public void setGst_ClientCount(Long gst_ClientCount) {
		this.gst_ClientCount = gst_ClientCount;
	}
	public ClientCountsDTO(Long totalClientCount, Long incomeTaxClientCount, Long gst_ClientCount) {
		super();
		this.totalClientCount = totalClientCount;
		this.incomeTaxClientCount = incomeTaxClientCount;
		this.gst_ClientCount = gst_ClientCount;
	}
	public ClientCountsDTO() {
		super();
	}
	@Override
	public String toString() {
		return "ClientCountsDTO [totalClientCount=" + totalClientCount + ", incomeTaxClientCount="
				+ incomeTaxClientCount + ", gst_ClientCount=" + gst_ClientCount + "]";
	}
	
    
    


}