package com.Tasko.Registration.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Tasko.Registration.Entity.DistributionReg;
import com.Tasko.Registration.Entity.Salesman_Register;

public class Salesmanageruserdetails implements UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
    private String password;
    private List<GrantedAuthority> authorities;
    
    public Salesmanageruserdetails(Salesman_Register userInfo)
    {
        name=userInfo.getPan();
        password=userInfo.getPassword();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() 
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() 
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
