package com.Tasko.Registration.Entity;

import java.util.Optional;

public class authDistribution 
{
	private String token;
    private Optional<DistributionReg> user;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Optional<DistributionReg> getUser() {
		return user;
	}
	public void setUser(Optional<DistributionReg> user) {
		this.user = user;
	}
	public authDistribution(String token, Optional<DistributionReg> user) {
		super();
		this.token = token;
		this.user = user;
	}
	public authDistribution() {
	
	}
	@Override
	public String toString() {
		return "authDistribution [token=" + token + ", user=" + user + "]";
	}
	
	
    
    

    

    
}
