package com.Tasko.Registration.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.Help;
import com.Tasko.Registration.Entity.Invest_Now;



public interface Invest_NowRepository extends JpaRepository<Invest_Now,Long> {

	Optional<Invest_Now> findByClientid(Long clientid);
	List<Invest_Now> findAllByUseridAndCategory(Long userId,String category);
	@Query("SELECT max(fnf.date) FROM Invest_Now fnf WHERE fnf.clientid = :clientid and fnf.category = :category")
	Timestamp findDateByClientIdAndCategory(Long clientid,String category);	
	
	//List<Invest_Now> findAllByInvestNow_Email(String investNowEmail);
	@Query("SELECT fnf FROM Invest_Now fnf WHERE fnf.investNow_Email = :investNowEmail and fnf.category = :category")
	List<Invest_Now> listfindAllByInvestNow_EmailAndCategory(String investNowEmail,String category);
	
	
}
