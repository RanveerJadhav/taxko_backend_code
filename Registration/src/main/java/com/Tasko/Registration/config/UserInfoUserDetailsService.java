package com.Tasko.Registration.config;





import java.util.Optional;



//import java.util.Optional;

import com.Tasko.Registration.Entity.Client_Registation_Form;
import com.Tasko.Registration.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.Tasko.Registration.Entity.User_RegistrationsForm;
import com.Tasko.Registration.Repository.TaskoRepository;




@Component
public class UserInfoUserDetailsService implements UserDetailsService
{
	@Autowired
    private TaskoRepository repository;
	@Autowired
	private ClientRepository clientRepository;

	
	  @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	    {
			Optional<User_RegistrationsForm> userInfo = repository.findByPan(username);
			Optional<Client_Registation_Form> clientInfo = clientRepository.findByPan(username);

			if (userInfo.isPresent())
			{

				return userInfo.map(UserInfoUserDetails::new)
						.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
			}
			else
				return clientInfo.map(ClientUserInfoUserDetails::new)
						.orElseThrow(() -> new UsernameNotFoundException("Client not found: " + username));

	    }
}
