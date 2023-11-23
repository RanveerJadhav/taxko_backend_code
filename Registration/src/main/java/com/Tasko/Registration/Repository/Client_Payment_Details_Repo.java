package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Tasko.Registration.Entity.Client_Payment_Details;

@Repository
public interface Client_Payment_Details_Repo extends JpaRepository<Client_Payment_Details,Long>
{

	@SuppressWarnings("unchecked")
	Client_Payment_Details save(Client_Payment_Details pay);

	  List<Client_Payment_Details> findByUserid(Long userid);

	  Client_Payment_Details findByUseridAndClientid(Long userid, Long clientid);


    
    List<Client_Payment_Details> findByUseridAndYear(Long userid, String year);

	List<Client_Payment_Details> findByUseridAndYearAndPendingPaymentGreaterThan(Long userid, String year, Long pendingPaymentThreshold);
}
