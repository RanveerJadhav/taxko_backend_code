package com.Tasko.Registration.config;





import java.util.Optional;

import com.Tasko.Registration.Entity.ClientPass_Imgdetail;

//import java.util.Optional;

import com.Tasko.Registration.Entity.Client_Registation_Form;
import com.Tasko.Registration.Entity.DistributionReg;
import com.Tasko.Registration.Repository.ClientPassRepository;
import com.Tasko.Registration.Repository.ClientRepository;
import com.Tasko.Registration.Repository.DistributionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.Tasko.Registration.Entity.User_RegistrationsForm;
import com.Tasko.Registration.Entity.master_admin;
import com.Tasko.Registration.Repository.TaskoRepository;
import com.Tasko.Registration.Repository.master_adminRepository;




@Component
public class UserInfoUserDetailsService implements UserDetailsService
{
	@Autowired
    private TaskoRepository repository;
	@Autowired
	private ClientPassRepository clientPassRepository;
	
	@Autowired 
	private master_adminRepository master_adminRepository;
	@Autowired
	private DistributionRepo distributionRepo;

	
	  @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	    {
			Optional<User_RegistrationsForm> userInfo = repository.findByPan(username);
			Optional<ClientPass_Imgdetail> clientInfo = clientPassRepository.findByPan(username);
			Optional<master_admin> admininfo =master_adminRepository.findByusername(username);
			Optional<DistributionReg> distributioninfo = distributionRepo.findByPan(username);

			if (userInfo.isPresent())
			{

				return userInfo.map(UserInfoUserDetails::new)
						.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
			}
			else if(admininfo.isPresent())
			{
				return admininfo.map(AdminUserInfoUserDetails::new)
						.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
			}
			else if(distributioninfo.isPresent())
			{
				return distributioninfo.map(DistributorUserDetails::new)
						.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
			}
			else
			{
				return clientInfo.map(ClientUserInfoUserDetails::new)
						.orElseThrow(() -> new UsernameNotFoundException("Client not found: " + username));
			}
			}
}
