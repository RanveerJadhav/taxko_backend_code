package com.Tasko.Registration.Repository;

import java.util.List;

import com.Tasko.Registration.Entity.Client_Payment_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Client_Payment_Details_Repo extends JpaRepository<Client_Payment_Details,Long>
{

	@SuppressWarnings("unchecked")
	Client_Payment_Details save(Client_Payment_Details pay);

	  List<Client_Payment_Details> findByUserid(Long userid);

	  Client_Payment_Details findByUseridAndClientid(Long userid, Long clientid);


    
    List<Client_Payment_Details> findByUseridAndYear(Long userid, String year);

	List<Client_Payment_Details> findByUseridAndYearAndPendingPaymentGreaterThan(Long userid, String year, Long pendingPaymentThreshold);

    List<Client_Payment_Details> findByUseridAndSubUserid(Long userid, Long subUserid);

	Client_Payment_Details findByUseridAndSubUseridAndClientid(Long userid, Long subUserid, Long clientid);

	List<Client_Payment_Details> findByUseridAndSubUseridAndYearAndPendingPaymentGreaterThan(Long userid, Long subUserid, String year, Long pendingPaymentThreshold);
}
