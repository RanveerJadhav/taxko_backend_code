package com.Tasko.Registration.Repository;


import java.util.List;


import com.Tasko.Registration.Entity.Client_Payment_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientPayment extends JpaRepository<Client_Payment_Details,Long>
{


   List<Client_Payment_Details> findByUseridAndClientid(Long userid, Long clientid);


    List<Client_Payment_Details> findByUseridAndSubUseridAndClientid(Long userid, Long subUserid, Long clientid);
}