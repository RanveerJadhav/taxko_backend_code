package com.Tasko.Registration.Entity;

import java.util.Optional;

public class authadmin {
	private String token;
    private Optional<master_admin> user;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Optional<master_admin> getUser() {
		return user;
	}

	public void setUser(Optional<master_admin> user) {
		this.user = user;
	}

	public authadmin(Optional<master_admin> user, String token) {
		this.token = token;
		this.user = user;
	}
}
