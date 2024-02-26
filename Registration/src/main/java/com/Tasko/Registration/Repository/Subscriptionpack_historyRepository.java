package com.Tasko.Registration.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.Subscription_Userdata;
import com.Tasko.Registration.Entity.Subscriptionpack_history;



public interface Subscriptionpack_historyRepository extends JpaRepository<Subscriptionpack_history,Long> {
	@Query("SELECT SUM(c.subscriptionprice) FROM Subscriptionpack_history c WHERE c.salespersonId = :salespersonId")
    Long calculateSumOfRenewalPricesales(String salespersonId);
             
    @Query("SELECT SUM(c.subscriptionprice) FROM Subscriptionpack_history c WHERE YEAR(c.substartdatebyadmin) = :year AND MONTH(c.substartdatebyadmin) = :month AND c.salespersonId = :salespersonId")
	 Long calculateSumOfRenewalPricelastmonthsales(int year,int month,String salespersonId);
    
    @Query("SELECT SUM(c.subscriptionprice) FROM Subscriptionpack_history c WHERE c.dissalespersonId = :dissalespersonId")
    Long calculateSumOfRenewalPricedissales(Long dissalespersonId);
             
    @Query("SELECT SUM(c.subscriptionprice) FROM Subscriptionpack_history c WHERE YEAR(c.substartdatebyadmin) = :year AND MONTH(c.substartdatebyadmin) = :month AND c.dissalespersonId = :dissalespersonId")
	 Long calculateSumOfRenewalPricelastmonthdissales(int year,int month,Long dissalespersonId);
    
    
    
    
    @Query("SELECT SUM(c.subscriptionprice) FROM Subscriptionpack_history c WHERE DATE(c.substartdatebyadmin) = DATE(:date) AND (c.salespersonId = :salespersonId OR c.dissalespersonId = :dissalespersonId)")
    Long sumoftodayearning(Date date,String salespersonId,Long dissalespersonId);
 
    @Query("SELECT SUM(c.subscriptionprice) FROM Subscriptionpack_history c WHERE DATE(c.substartdatebyadmin) <= DATE(:weekstartDate) AND DATE(c.substartdatebyadmin) >= DATE(:currentdate) AND (c.salespersonId = :salespersonId OR c.dissalespersonId = :dissalespersonId)")
    Long sumofweekearning( Date weekstartDate, Date currentdate,String salespersonId,Long dissalespersonId);

    @Query("SELECT SUM(c.subscriptionprice) FROM Subscriptionpack_history c WHERE YEAR(c.substartdatebyadmin) = :year AND MONTH(c.substartdatebyadmin) = :month AND (c.salespersonId = :salespersonId OR c.dissalespersonId = :dissalespersonId)")
    Long sumofmonthearning(int year,int month,String salespersonId,Long dissalespersonId);
 
    @Query("SELECT SUM(c.subscriptionprice) FROM Subscriptionpack_history c WHERE DATE(c.substartdatebyadmin) <= DATE(:startDate) AND DATE(c.substartdatebyadmin) >= DATE(:currentdate) AND (c.salespersonId = :salespersonId OR c.dissalespersonId = :dissalespersonId)")
    Long sumofquarterearning( Date startDate, Date currentdate,String salespersonId,Long dissalespersonId);
 
    @Query("SELECT SUM(c.subscriptionprice) FROM Subscriptionpack_history c WHERE YEAR(c.substartdatebyadmin) = :currentYear AND (c.salespersonId = :salespersonId OR c.dissalespersonId = :dissalespersonId)")
	Long  sumofcurrentyearearning(int currentYear,String salespersonId,Long dissalespersonId);

	List<Subscriptionpack_history>findBySalespersonId(String pan);
	List<Subscriptionpack_history>findByDissalespersonId(Long dissalespersonId);
	
	 
}
