package com.Tasko.Registration.Repository;


import java.util.Optional;
import java.sql.Timestamp;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.Kyc_client_detail;

public interface KycclientRepository  extends JpaRepository<Kyc_client_detail,Long> {
	 Optional<Kyc_client_detail> findByPan(String pan);
	 @Query("SELECT DATE(MAX(CASE " +
		        "  WHEN e.lastUpdateDate1 >= e.lastUpdateDate2 AND e.lastUpdateDate1 >= e.lastUpdateDate3 THEN e.lastUpdateDate1 " +
		        "  WHEN e.lastUpdateDate2 >= e.lastUpdateDate1 AND e.lastUpdateDate2 >= e.lastUpdateDate3 THEN e.lastUpdateDate2 " +
		        "  ELSE e.lastUpdateDate3 " +
		        "END)) " +
		        "FROM Kyc_client_detail e WHERE e.pan = :pan")
		LocalDate findMaxLocalDateByPan( String pan);
	 
	 @Query("SELECT DATE(max(fnf.lastUpdateDate1)) FROM Kyc_client_detail fnf WHERE fnf.pan = :pan")
	    LocalDate findMaxLastUpdateDateByPan(String pan);
	 @Query("SELECT max(fnf.lastUpdateDate1) FROM Kyc_client_detail fnf WHERE fnf.pan = :pan")
	 Timestamp findMaxLastUpdateDate4ByPan(String pan);
	 @Query("SELECT DATE(max(fnf.lastUpdateDate2)) FROM Kyc_client_detail fnf WHERE fnf.pan = :pan")
	    LocalDate findMaxLast2UpdateDateByPan(String pan);
	 @Query("SELECT max(fnf.lastUpdateDate2) FROM Kyc_client_detail fnf WHERE fnf.pan = :pan")
	 Timestamp findMaxLastUpdateDate5ByPan(String pan);
	 @Query("SELECT DATE(max(fnf.lastUpdateDate3)) FROM Kyc_client_detail fnf WHERE fnf.pan = :pan")
	    LocalDate findMaxLast3UpdateDateByPan(String pan);
	 @Query("SELECT max(fnf.lastUpdateDate3) FROM Kyc_client_detail fnf WHERE fnf.pan = :pan")
	 Timestamp findMaxLastUpdateDate6ByPan(String pan);
}