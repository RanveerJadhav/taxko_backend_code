package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Total_paid_payment_salesmanger_totalentry;

public interface Total_paid_payment_salesmanger_totalentryRepository extends JpaRepository<Total_paid_payment_salesmanger_totalentry, Long> {

	List<Total_paid_payment_salesmanger_totalentry> findByPan(String pan);

}
