package com.Tasko.Registration.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.docinfo;

public interface DocinfoRepository extends JpaRepository <docinfo,Long> {
	 Optional<docinfo> findByPan(String pan);
	 @Query("SELECT DATE(max(fnf.lastUpdateDate1)) FROM docinfo fnf WHERE fnf.pan = :pan")	    
   LocalDate findmaxLocalDateByPan(String pan);
	 @Query("SELECT max(fnf.lastUpdateDate1) FROM docinfo fnf WHERE fnf.pan = :pan")	    
	 Timestamp findmaxLocalDate1ByPan(String pan);

	
}
