package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.Tasko.Registration.Entity.Total_salesmanger_totalentry_deactivate_salesmanager;

public interface Total_paid_payment_salesmanger_totalentry_deactivate_salesmanagerRepository extends JpaRepository<Total_salesmanger_totalentry_deactivate_salesmanager, Long>{

	List<Total_salesmanger_totalentry_deactivate_salesmanager> findByPan(String pan);

}
