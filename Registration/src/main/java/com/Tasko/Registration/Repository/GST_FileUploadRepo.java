package com.Tasko.Registration.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.GST_FileUpload;

public interface GST_FileUploadRepo extends JpaRepository<GST_FileUpload,Long>
{

	List<GST_FileUpload> findByUseridAndClientidAndFinancialYearAndCategory(Long userid, Long clientid,
																			String financialYear, String category);
	@Query("SELECT DATE(max(fnf.lastUpdateDate)) FROM GST_FileUpload fnf WHERE fnf.clientid = :clientid")
	LocalDate findMaxLastUpdateDateByclientid(Long clientid);


	Optional<GST_FileUpload>  findByClientidAndLastUpdateDate(Long clientid, String accountyear);
	@Query("SELECT fnf.id FROM GST_FileUpload fnf WHERE fnf.lastUpdateDate = (SELECT MAX(f.lastUpdateDate) FROM GST_FileUpload f WHERE f.clientid = :clientid)")
	Long findIdByClientId2(Long clientid);

	@Query("SELECT max(fnf.lastUpdateDate) FROM GST_FileUpload fnf WHERE fnf.clientid = :clientid")
	Timestamp findMaxLastUpdateDate1Byclientid(Long clientid);
	List<GST_FileUpload> findAllByClientid(Long clientid);
}
