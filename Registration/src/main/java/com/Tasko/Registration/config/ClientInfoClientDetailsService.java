package com.Tasko.Registration.config;

import com.Tasko.Registration.Entity.Client_Registation_Form;
import com.Tasko.Registration.Entity.User_RegistrationsForm;
import com.Tasko.Registration.Repository.ClientRepository;
import com.Tasko.Registration.Repository.TaskoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class ClientInfoClientDetailsService implements UserDetailsService
{
    @Autowired
    private ClientRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<Client_Registation_Form> userInfo = repository.findByPan(username);
        return userInfo.map(ClientInfoClientDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }

}
