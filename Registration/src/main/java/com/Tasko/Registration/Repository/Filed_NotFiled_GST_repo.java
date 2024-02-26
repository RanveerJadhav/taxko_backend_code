package com.Tasko.Registration.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.Tasko.Registration.Entity.Filed_NotFiled_GST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Filed_NotFiled_GST_repo extends JpaRepository<Filed_NotFiled_GST,Long>
{
	Optional<Filed_NotFiled_GST> findByUseridAndClientidAndFinancialYear(Long userid, Long clientid,String yearString);

	Optional<Filed_NotFiled_GST> findByUseridAndClientidAndFinancialYearAndCategory(Long userid, Long clientid,
			String financialYear, String category);

	Filed_NotFiled_GST save(Optional<Filed_NotFiled_GST> record);

	Filed_NotFiled_GST findByUseridAndClientidAndMonthAndFinancialYearAndCategory(Long userid, Long clientid,
			String month, String financialYear, String category);


	
	Optional<Filed_NotFiled_GST> findByFinancialYear(String financialYear);

	
	@Query("SELECT f.month AS monthYear, " +"SUM(CASE WHEN f.filednotfiled = 'yes' THEN 1 ELSE 0 END) AS filed, " + "SUM(CASE WHEN f.filednotfiled = 'no' THEN 1 ELSE 0 END) AS notfiled " +
		       "FROM Filed_NotFiled_GST f " +
		       "WHERE f.userid = :userid " +
		       "AND f.financialYear = :financialYear " +
		       "GROUP BY monthYear")
		List<Object[]> countFiledNotFiledByUseridAndFinancialYear(@Param("userid") Long userid,@Param("financialYear") String financialYear);

	List<Filed_NotFiled_GST> findFilednotfiledByUseridAndClientidAndMonthAndFinancialYearAndCategory(Long userid, Long clientid, String month,
			String financialYear, String category);

	   @Query("SELECT DATE(max(fnf.lastUpdateDate)) FROM Filed_NotFiled_GST fnf WHERE fnf.userid = :userid")
	    LocalDate findMaxLastUpdateDateByUserid(Long userid);

	@Query("SELECT f.category, " +
			"f.month AS monthYear, " +
			"SUM(CASE WHEN f.filednotfiled = 'yes' THEN 1 ELSE 0 END) AS filed, " +
			"SUM(CASE WHEN f.filednotfiled = 'no' THEN 1 ELSE 0 END) AS notfiled " +
			"FROM Filed_NotFiled_GST f " +
			"WHERE f.userid = :userId " +
			"AND (f.financialYear = :currentFinancialYear OR f.financialYear = :previousFinancialYear) " +
			"GROUP BY f.category, f.month")
	List<Object[]> getDataByCategoryAndFinancialYear(@Param("userId") Long userId, @Param("currentFinancialYear") String currentFinancialYear
			,@Param("previousFinancialYear") String previousFinancialYear);

	List<Filed_NotFiled_GST> findByUseridAndFinancialYear(Long userid, String financialYear);

	List<Filed_NotFiled_GST> findFilednotfiledByUseridAndClientidAndFinancialYearAndCategory(Long userid,
			Long clientid, String financialYear, String category);


	@Query("SELECT DATE(max(fnf.lastUpdateDate)) FROM Filed_NotFiled_GST fnf WHERE fnf.clientid = :clientid")
	LocalDate findMaxLastUpdateDateByclientid(Long clientid);

	List<Filed_NotFiled_GST> findByUseridAndMonthAndCategoryAndFilednotfiled(Long userid, String month, String string,
			String string2);

    List<Filed_NotFiled_GST> findByClientid(Long clientId);

	@Query("SELECT DATE(max(fnf.lastUpdateDate)) FROM Filed_NotFiled_GST fnf WHERE fnf.userid = :userid AND fnf.subUserid = :subUserid")
	LocalDate findMaxLastUpdateDateByUseridAndSubUserid(Long userid, Long subUserid);

	@Query("SELECT f.category, " +
			"f.month AS monthYear, " +
			"SUM(CASE WHEN f.filednotfiled = 'yes' THEN 1 ELSE 0 END) AS filed, " +
			"SUM(CASE WHEN f.filednotfiled = 'no' THEN 1 ELSE 0 END) AS notfiled " +
			"FROM Filed_NotFiled_GST f " +
			"WHERE f.userid = :userId " +
			"AND f.subUserid= :subUserid "+
			"AND f.financialYear = :financialYear " +
			"GROUP BY f.category, f.month")
	List<Object[]> getDataByCategoryAndSubUseridAndFinancialYear(@Param("userId") Long userId,@Param("subUserid")  Long subUserid, @Param("financialYear") String financialYear);
}
