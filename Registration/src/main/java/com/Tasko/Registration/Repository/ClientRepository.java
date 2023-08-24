package com.Tasko.Registration.Repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.Client_Registation_Form;

public interface ClientRepository extends JpaRepository<Client_Registation_Form,Long>
{	
	//@Query(value ="select * from Client_Registation_Form where userid=:userid", nativeQuery = true)
	List<Client_Registation_Form> findAllByUserid(Long userid);

	Optional<Client_Registation_Form> findByPan(String pan);
	List<Client_Registation_Form> findByCategoryAndUserid(String category, Long userid);

	Client_Registation_Form findByUseridAndClientId(Long userid,Long clientId);

	Client_Registation_Form findByCategoryAndUseridAndClientId(String category,Long userid,Long clientId);


	Long countByUserid(Long userid);

	Long countByCategoryAndUserid(String category, Long userid);
}
