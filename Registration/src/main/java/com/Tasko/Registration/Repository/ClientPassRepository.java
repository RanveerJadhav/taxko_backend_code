package com.Tasko.Registration.Repository;

import java.util.Optional;

import com.Tasko.Registration.Entity.Client_Registation_Form;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.ClientPass_Imgdetail;
import org.springframework.data.jpa.repository.Query;

public interface ClientPassRepository extends JpaRepository<ClientPass_Imgdetail,Long> {
	 Optional<ClientPass_Imgdetail> findByPan(String pan);

	@Query(value ="SELECT * FROM tasko.client_pass_imgdetail WHERE pan = :pan", nativeQuery = true)
	ClientPass_Imgdetail findByPan1(String pan);


}
