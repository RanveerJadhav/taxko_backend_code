package com.Tasko.Registration.Repository;

import com.Tasko.Registration.Entity.FileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long>
{
    List<FileEntity> findByUseridAndClientidAndAccountyear(Long userid, Long clientid, String accountyear);

    Optional<FileEntity> findFirstByUseridAndClientidAndAccountyearAndFileName(Long userid, Long clientid, String accountyear,String fileName);


    FileEntity findByIdAndUseridAndClientidAndAccountyear(Long id,Long userid, Long clientid,String accountyear);

    List<FileEntity> findByClientidAndAccountyear(Long clientid,String accountyear);
    
    List<FileEntity> findByUseridAndAccountyear(Long userid, String accountyear);
    Optional<FileEntity> findByClientidAndLastUpdateDate(Long clientid, String accountyear);

	void deleteByUseridAndAccountyear(Long userid, String yearToDeleteStr);
	
	@Query("SELECT DATE(max(fnf.lastUpdateDate)) FROM FileEntity fnf WHERE fnf.clientid = :clientid")
    LocalDate findMaxLastUpdateDateByclientid(Long clientid);
	@Query("SELECT(max(fnf.lastUpdateDate)) FROM FileEntity fnf WHERE fnf.clientid = :clientid")
    LocalDate findMaxLastUpdateDateByclientid1(Long clientid);
	@Query("SELECT fnf.id FROM FileEntity fnf WHERE fnf.lastUpdateDate = (SELECT MAX(f.lastUpdateDate) FROM FileEntity f WHERE f.clientid = :clientid)")
	Long findIdByClientId2(Long clientid);
	 Optional<FileEntity> findByClientidAndLastUpdateDate(Long clientid, Date lastUpdateDate);
	Optional<FileEntity> findByClientid(Long clientid);

	Optional<FileEntity> findByClientidAndLastUpdateDate(Long clientid, Optional<LocalDate> maxLastUpdateDate7);

    @Query("SELECT max(fnf.lastUpdateDate) FROM FileEntity fnf WHERE fnf.clientid = :clientid")
    Timestamp findMaxLastUpdateDate1Byclientid(Long clientid);

	List<FileEntity> findAllByClientid(Long clientid1);


    
//    List<FileEntity> DeleteByUseridAndAccountyear(Long userid, String accountyear);
    
 


   // FileEntity findByUserIdAndClientIdAndAccountYearAndFileName(Long userid, Long clientid, String accountyear, String fileName);
}
