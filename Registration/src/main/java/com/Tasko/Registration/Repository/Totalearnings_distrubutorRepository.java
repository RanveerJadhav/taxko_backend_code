package com.Tasko.Registration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.Totalearnings_distrubutor;


public interface Totalearnings_distrubutorRepository extends JpaRepository<Totalearnings_distrubutor, Long>{
	 Optional<Totalearnings_distrubutor> findByPan(String pan);
	 
	 @Query("SELECT c.spendrenewalPrice FROM Totalearnings_distrubutor c WHERE c.pan = :pan")
	    Long calculateSumOfRenewalPrice(String pan);
	 
}
