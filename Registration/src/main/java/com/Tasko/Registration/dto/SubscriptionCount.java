package com.Tasko.Registration.dto;

public class SubscriptionCount {
	private String SubscriptionCount;
    private Long count;
	public String getSubscriptionCount() {
		return SubscriptionCount;
	}
	public void setSubscriptionCount(String subscriptionCount) {
		SubscriptionCount = subscriptionCount;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public SubscriptionCount(String subscriptionCount, Long count) {
		super();
		SubscriptionCount = subscriptionCount;
		this.count = count;
	}
	public SubscriptionCount(String subscriptionCount2, Object count2) {
		// TODO Auto-generated constructor stub
	}
	
}
