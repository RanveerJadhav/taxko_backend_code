package com.Tasko.Registration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Total_paid_payment_salesmanger_deactivate_salesmanager;

public interface Total_paid_payment_salesmanger_deactivate_salesmanagerRepository extends JpaRepository<Total_paid_payment_salesmanger_deactivate_salesmanager, Long>{

	Optional<Total_paid_payment_salesmanger_deactivate_salesmanager> findByPan(String pan);

}
