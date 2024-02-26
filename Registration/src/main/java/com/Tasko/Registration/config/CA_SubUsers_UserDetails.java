package com.Tasko.Registration.config;

import com.Tasko.Registration.Entity.CA_SubUsers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CA_SubUsers_UserDetails implements UserDetails
{
    private static final long serialVersionUID = 1L;
    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public CA_SubUsers_UserDetails(CA_SubUsers userInfo)
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
