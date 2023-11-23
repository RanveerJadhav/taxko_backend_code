package com.Tasko.Registration.Repository;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.Tasko.Registration.Entity.Subscription_Userdata;

public interface Subscritpion_userdataRepository extends JpaRepository<Subscription_Userdata,Long> {
	 Optional<Subscription_Userdata> findByPan(String pan);
	 
	 Optional<Subscription_Userdata> findByUserid(Long userid);
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) = DATE(:date)")
	    Long countOftodaysubscription(Date date);
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) = DATE(:date)")
	    Long countOfyestardaysubscription(Date date);
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) >= DATE(:startDate)")
	    Long countOfTotalProfessionWithinLast7Days( Date startDate);
	 
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE YEAR(c.substartdatebyadmin) = :currentYear")
	    Long countOfTotalProfessionInCurrentYear(int currentYear);
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE YEAR(c.substartdatebyadmin) = :previousYear")
	 Long countOfTotalProfessionInPreviousYear(int previousYear);
	 
	 
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) =DATE(:date)")
	 List<Subscription_Userdata> listOftodaysubscription( Date date);

	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) = DATE(:date)")
	 List<Subscription_Userdata> listOfyestardaysubscription(Date date);
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) >= DATE(:startDate)")
	 List<Subscription_Userdata> listofweeksubscription(Date startDate);

	 
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE YEAR(c.substartdatebyadmin) = :currentYear")
	 List<Subscription_Userdata>  listOfTotalProfessionInCurrentYear(int currentYear);
	 
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE YEAR(c.substartdatebyadmin) = :previousYear")
	 List<Subscription_Userdata>  listOfTotalProfessionInPreviousYear(int previousYear);
	 
	 
	 
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.subendtdate) = DATE(:date)")
	    Long countOftodayRenewal(Date date);
	 
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.subendtdate) = DATE(:date)")
	    Long countOfyestardayRenewal(Date date);
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate)")
	    Long countOfTotalProfessionWithinLast7Daysreneval( Date startDate, Date currentdate);

	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE YEAR(c.subendtdate) = :year AND MONTH(c.subendtdate) = :month AND DATE(c.subendtdate) >= DATE(:currentdate)")
	 Long countOfTotalProfessionInCurrentMonthreneval(int year,int month, Date currentdate);
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate)")
	    Long countOfTotalProfessionInthreemonthreneval( Date startDate, Date currentdate);
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate)")
	    Long countOfTotalProfessionInsixmonthreneval( Date startDate, Date currentdate);

	 
	 
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.subendtdate) = DATE(:date)")
	 List<Subscription_Userdata>  listOftodayRenewal(Date date);
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.subendtdate) = DATE(:date)")
	 List<Subscription_Userdata> listOfyestardayRenewal(Date date);
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate)")
	 List<Subscription_Userdata> listOfTotalProfessionWithinLast7Daysreneval( Date startDate, Date currentdate);
	 
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE YEAR(c.subendtdate) = :year AND MONTH(c.subendtdate) = :month AND DATE(c.subendtdate) >= DATE(:currentdate)")
	 List<Subscription_Userdata> listOfTotalProfessionInCurrentMonthreneval(int year,int month, Date currentdate);
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate)")
	 List<Subscription_Userdata> listOfTotalProfessionInthreemonthreneval( Date startDate, Date currentdate);
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate)")
	 List<Subscription_Userdata> listOfTotalProfessionInsixmonthreneval( Date startDate, Date currentdate);
	 
	 
//	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE YEAR(c.subendtdate) = :year AND MONTH(c.subendtdate) <= :month")
//	 Long countOfTotalProfessionInthreemonthreneval(int year,int month);

//	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE YEAR(c.subendtdate) = :year AND MONTH(c.subendtdate) <= :month")
//	 Long countOfTotalProfessionInsixmonthreneval(int year,int month);
//	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE MONTH(c.subendtdate) <= :currentMonth")
//	    Long countOfTotalProfessionInsixmonthreneval(Month currentMonth);
//	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE MONTH(c.subendtdate) <= :futureMonth")
//	 Long countOfTotalProfessionInthreemonthreneval( Month futureMonth); 
//	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE MONTH(c.subendtdate) <= :currentMonth")
//	    Long countOfTotalProfessionInsixmonthreneval(Month currentMonth);
	 
	 
	 
	 
	 
///////////////////////////	 distributor Reneval all query///////////////////////
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.subendtdate) = DATE(:date) AND c.disrefrenceId = :disrefrenceId")
	    Long countOfdistodayRenewal(Date date,String disrefrenceId);
	 
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.subendtdate) = DATE(:date) AND c.disrefrenceId = :disrefrenceId")
	    Long countOfdisyestardayRenewal(Date date,String disrefrenceId);
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate) AND c.disrefrenceId = :disrefrenceId")
	 Long countOfdisTotalProfessionWithinLast7Daysreneval(Date startDate, String disrefrenceId, Date currentdate);


	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE YEAR(c.subendtdate) = :year AND MONTH(c.subendtdate) = :month AND DATE(c.subendtdate) >= DATE(:currentdate)  AND c.disrefrenceId = :disrefrenceId")
	 Long countOfdisTotalProfessionInCurrentMonthreneval(int year,int month,String disrefrenceId, Date currentdate);
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate)  AND c.disrefrenceId = :disrefrenceId")
	    Long countOfdisTotalProfessionInthreemonthreneval( Date startDate,String disrefrenceId, Date currentdate);
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate)  AND c.disrefrenceId = :disrefrenceId")
	    Long countOfdisTotalProfessionInsixmonthreneval( Date startDate,String disrefrenceId, Date currentdate);

	 
	 
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.subendtdate) = DATE(:date) AND c.disrefrenceId = :disrefrenceId ")
	 List<Subscription_Userdata>  listOfdistodayRenewal(Date date,String disrefrenceId);
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.subendtdate) = DATE(:date) AND c.disrefrenceId = :disrefrenceId")
	 List<Subscription_Userdata> listOfdisyestardayRenewal(Date date,String disrefrenceId);
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate) AND c.disrefrenceId = :disrefrenceId")
	 List<Subscription_Userdata> listOfdisTotalProfessionWithinLast7Daysreneval( Date startDate,String disrefrenceId, Date currentdate);
	 
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE YEAR(c.subendtdate) = :year AND MONTH(c.subendtdate) = :month AND DATE(c.subendtdate) >= DATE(:currentdate) AND c.disrefrenceId = :disrefrenceId")
	 List<Subscription_Userdata> listOfdisTotalProfessionInCurrentMonthreneval(int year,int month,String disrefrenceId, Date currentdate);
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate) AND c.disrefrenceId = :disrefrenceId")
	 List<Subscription_Userdata> listOfdisTotalProfessionInthreemonthreneval( Date startDate,String disrefrenceId, Date currentdate);
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.subendtdate) <= DATE(:startDate) AND DATE(c.subendtdate) >= DATE(:currentdate) AND c.disrefrenceId = :disrefrenceId")
	 List<Subscription_Userdata> listOfdisTotalProfessionInsixmonthreneval( Date startDate,String disrefrenceId, Date currentdate);
	 
	 
	 
	//////////////////////////distrubution all business query////////////////////////////
	  
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) = DATE(:date) AND c.disrefrenceId = :disrefrenceId")
	    Long countOfdistodaysubscription(Date date,String disrefrenceId);
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) = DATE(:date) AND c.disrefrenceId = :disrefrenceId")
	    Long countOfdisyestardaysubscription(Date date,String disrefrenceId);
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) >= DATE(:startDate) AND c.disrefrenceId = :disrefrenceId")
	    Long countOfdisTotalProfessionWithinLast7Days( Date startDate,String disrefrenceId);
	 
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE YEAR(c.substartdatebyadmin) = :currentYear AND c.disrefrenceId = :disrefrenceId")
	    Long countOfdisTotalProfessionInCurrentYear(int currentYear,String disrefrenceId);
	 
	 @Query("SELECT COUNT(c) FROM Subscription_Userdata c WHERE YEAR(c.substartdatebyadmin) = :previousYear AND c.disrefrenceId = :disrefrenceId")
	    Long countOfdisTotalProfessionInPreviousYear(int previousYear,String disrefrenceId);
	 
	 
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) =DATE(:date) AND c.disrefrenceId = :disrefrenceId")
	 List<Subscription_Userdata> listOfdistodaysubscription( Date date,String disrefrenceId);

	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) = DATE(:date) AND c.disrefrenceId = :disrefrenceId")
	 List<Subscription_Userdata> listOfdisyestardaysubscription(Date date,String disrefrenceId);
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE DATE(c.substartdatebyadmin) >= DATE(:startDate) AND c.disrefrenceId = :disrefrenceId")
	 List<Subscription_Userdata> listofdisweeksubscription(Date startDate,String disrefrenceId);

	 
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE YEAR(c.substartdatebyadmin) = :currentYear AND c.disrefrenceId = :disrefrenceId")
	 List<Subscription_Userdata>  listOfdisTotalProfessionInCurrentYear(int currentYear,String disrefrenceId);
	 
	 
	 @Query("SELECT c FROM Subscription_Userdata c WHERE YEAR(c.substartdatebyadmin) = :previousYear AND c.disrefrenceId = :disrefrenceId")
	 List<Subscription_Userdata>  listOfdisTotalProfessionInPreviousYear(int previousYear,String disrefrenceId);

	
	 
	 
	 
}
