package com.Tasko.Registration.Repository;


import com.Tasko.Registration.Entity.Filed_NotFiled;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface Filed_NotFiledRepo extends JpaRepository<Filed_NotFiled,Long>
{
    boolean existsByUseridAndClientidAndAccountyear(Long userid, Long clientid, String accountyear);

//    @Query("SELECT d.accountyear, SUM(CASE WHEN d.filednotfiled = true THEN 1 ELSE 0 END) AS filedCount, " +
//            "SUM(CASE WHEN d.filednotfiled = false THEN 1 ELSE 0 END) AS notFiledCount " +
//            "FROM Filed_NotFiled d " +
//            "WHERE d.userid = :userid " +
//            "GROUP BY d.accountYear")

    Filed_NotFiled findByUseridAndClientidAndAccountyear(Long userid, Long clientid, String accountyear);


    List<Filed_NotFiled> findFilednotfiledByUseridAndClientidAndAccountyear(Long userid, Long clientid, String accountyear);


    @Query("SELECT f.accountyear, " +
            "SUM(CASE WHEN f.filednotfiled = 'yes' THEN 1 ELSE 0 END) as filedCount, " +
            "SUM(CASE WHEN f.filednotfiled = 'no' THEN 1 ELSE 0 END) as notFiledCount " +
            "FROM Filed_NotFiled f WHERE f.userid = :userid GROUP BY f.accountyear")
    List<Object[]> countFiledNotFiledByAccountyear(@Param("userid") Long userid);
    @Query("SELECT DATE(max(fnf.lastUpdateDate)) FROM Filed_NotFiled fnf WHERE fnf.userid = :userid")
    LocalDate findMaxLastUpdateDateByUserid(Long userid);
    
}
