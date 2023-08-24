package com.Tasko.Registration.config;


import com.Tasko.Registration.Entity.User_RegistrationsForm;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;


@SuppressWarnings("serial")
public class UserInfoUserDetails implements UserDetails
{


    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(User_RegistrationsForm userInfo)
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
