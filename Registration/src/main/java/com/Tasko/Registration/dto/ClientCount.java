package com.Tasko.Registration.dto;

public class ClientCount {
    private String category;
    private Long count;

    public ClientCount(String category, Long count) {
        this.category = category;
        this.count = count;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

    
    // Getters and setters for category and count
}
