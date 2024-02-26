package com.Tasko.Registration.Entity;

import java.util.Optional;

public class authsalesmanager 
	{
		private String token;
	    private Optional<Salesman_Register> user;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public Optional<Salesman_Register> getUser() {
			return user;
		}
		public void setUser(Optional<Salesman_Register> user) {
			this.user = user;
		}
		@Override
		public String toString() {
			return "authsalesmanager [token=" + token + ", user=" + user + ", getToken()=" + getToken() + ", getUser()="
					+ getUser() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
					+ super.toString() + "]";
		}
		public authsalesmanager(String token, Optional<Salesman_Register> user) {
			super();
			this.token = token;
			this.user = user;
		}
		public authsalesmanager() {
			super();
			// TODO Auto-generated constructor stub
		}
			    
	}