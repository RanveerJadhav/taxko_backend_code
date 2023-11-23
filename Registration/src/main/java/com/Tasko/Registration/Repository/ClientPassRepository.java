package com.Tasko.Registration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.ClientPass_Imgdetail;

public interface ClientPassRepository extends JpaRepository<ClientPass_Imgdetail,Long> {
	 Optional<ClientPass_Imgdetail> findByPan(String pan);
}
