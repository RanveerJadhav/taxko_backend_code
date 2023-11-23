package com.Tasko.Registration.dto;

public class ClientPaymentSums 
{
	  private Long totalPaymentSum;
	    private Long receivedPaymentSum;
	    private Long pendingPaymentSum;
		public Long getTotalPaymentSum() {
			return totalPaymentSum;
		}
		public void setTotalPaymentSum(Long totalPaymentSum) {
			this.totalPaymentSum = totalPaymentSum;
		}
		public Long getReceivedPaymentSum() {
			return receivedPaymentSum;
		}
		public void setReceivedPaymentSum(Long receivedPaymentSum) {
			this.receivedPaymentSum = receivedPaymentSum;
		}
		public Long getPendingPaymentSum() {
			return pendingPaymentSum;
		}
		public void setPendingPaymentSum(Long pendingPaymentSum) {
			this.pendingPaymentSum = pendingPaymentSum;
		}
		public ClientPaymentSums(Long totalPaymentSum, Long receivedPaymentSum, Long pendingPaymentSum) {
			super();
			this.totalPaymentSum = totalPaymentSum;
			this.receivedPaymentSum = receivedPaymentSum;
			this.pendingPaymentSum = pendingPaymentSum;
		}
	    
	    

}
