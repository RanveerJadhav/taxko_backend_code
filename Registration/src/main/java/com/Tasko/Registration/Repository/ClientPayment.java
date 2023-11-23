package com.Tasko.Registration.Repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.Tasko.Registration.Entity.Client_Payment_Details;


@Repository
public interface ClientPayment extends JpaRepository<Client_Payment_Details,Long>
{


   List<Client_Payment_Details> findByUseridAndClientid(Long userid, Long clientid);


}