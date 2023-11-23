package com.Tasko.Registration.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Tasko.Registration.Entity.User_RegistrationsForm;
import com.Tasko.Registration.Entity.master_admin;



public class AdminUserInfoUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public AdminUserInfoUserDetails(master_admin userInfo)
    {
        name=userInfo.getUsername();
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
