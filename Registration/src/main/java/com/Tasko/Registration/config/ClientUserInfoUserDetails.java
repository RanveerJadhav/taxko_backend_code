package com.Tasko.Registration.config;

import com.Tasko.Registration.Entity.Client_Registation_Form;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ClientUserInfoUserDetails implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientusername; // Use client's PAN as username
    private String clientpassword;
    private List<GrantedAuthority> authorities;

    public ClientUserInfoUserDetails(Client_Registation_Form clientInfo) {
        this.clientusername = clientInfo.getPan(); // Set username as client's PAN
        this.clientpassword = clientInfo.getPassword(); // Set password from client's registration data
        // You can set authorities if needed based on client's roles or permissions
        // Example: this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENT"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return clientpassword;
    }

    @Override
    public String getUsername() {
        return clientusername;
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