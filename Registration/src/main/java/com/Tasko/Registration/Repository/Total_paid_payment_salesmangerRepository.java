package com.Tasko.Registration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Total_paid_payment_salesmanger;

public interface Total_paid_payment_salesmangerRepository extends  JpaRepository<Total_paid_payment_salesmanger, Long>{

	Optional<Total_paid_payment_salesmanger> findByPan(String pan);

}
