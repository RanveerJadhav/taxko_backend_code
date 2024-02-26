package com.Tasko.Registration.config;





import java.util.Optional;

import com.Tasko.Registration.Entity.*;

//import java.util.Optional;

import com.Tasko.Registration.Repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


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

	@Autowired
	private Client_Registation_Form_Temp_Repo tempClientRepo;

	@Autowired
	private Salesman_RegisterRepository salesman_RegisterRepository;

	@Autowired
	private CA_SubUsersRepo caSubUsersRepo;
	
	  @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	    {
			Optional<User_RegistrationsForm> userInfo = repository.findByPan(username);


			Optional<ClientPass_Imgdetail> clientInfo = clientPassRepository.findByPan(username);
			Optional<master_admin> admininfo =master_adminRepository.findByusername(username);
			Optional<DistributionReg> distributioninfo = distributionRepo.findByPan(username);
			Optional<Client_Registation_Form_Temp> tempClient=tempClientRepo.findByPan(username);

			Optional<Salesman_Register> salesmanager= salesman_RegisterRepository.findByPan(username);

			Optional<CA_SubUsers> CA_Subusers=caSubUsersRepo.findByPan(username);

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
			else if(tempClient.isPresent())
			{
				return tempClient.map(Client_Registation_Form_Temp_UserDetails::new)
						.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
			}
			else if(salesmanager.isPresent())
			{
				return salesmanager.map(Salesmanageruserdetails::new)
						.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
			}
			else if(CA_Subusers.isPresent())
			{
				return CA_Subusers.map(CA_SubUsers_UserDetails::new)
						.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
			}
			else
			{
				return clientInfo.map(ClientUserInfoUserDetails::new)
						.orElseThrow(() -> new UsernameNotFoundException("Client not found: " + username));
			}
			}
}
