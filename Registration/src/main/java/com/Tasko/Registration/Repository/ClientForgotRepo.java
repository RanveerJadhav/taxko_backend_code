package com.Tasko.Registration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.ClientPass_Imgdetail;



public interface ClientForgotRepo extends JpaRepository<ClientPass_Imgdetail,Long>
{
	ClientPass_Imgdetail findByEmail(String email);

	ClientPass_Imgdetail findByPan(String pan);
	ClientPass_Imgdetail findByPanAndEmail(String pan, String email);

	ClientPass_Imgdetail findByOtp(String otp);
}