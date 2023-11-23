package com.Tasko.Registration.Repository;

import com.Tasko.Registration.Entity.Client_Registation_Form;
import com.Tasko.Registration.Entity.Payment_Details;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface paymentDetailsRepo extends JpaRepository<Payment_Details,Long>
{
   Optional<Payment_Details>  findByUserid(Long userid);
}
