package com.Tasko.Registration.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Tasko.Registration.Entity.DistributionReg;



public class DistributorUserDetails implements UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
    private String password;
    private List<GrantedAuthority> authorities;
    
    public DistributorUserDetails(DistributionReg userInfo)
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
