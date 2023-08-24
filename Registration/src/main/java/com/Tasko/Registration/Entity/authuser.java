package com.Tasko.Registration.Entity;


import java.util.Optional;
public class authuser
{

	private String token;
	private Optional<User_RegistrationsForm> user;

	public authuser(Optional<User_RegistrationsForm> user) {

	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Optional<User_RegistrationsForm> getUser() {
		return user;
	}

	public void setUser(Optional<User_RegistrationsForm> user) {
		this.user = user;
	}

	public authuser(String token, Optional<User_RegistrationsForm> user) {
		this.token = token;
		this.user = user;
	}

	public authuser()
	{
	}
}

