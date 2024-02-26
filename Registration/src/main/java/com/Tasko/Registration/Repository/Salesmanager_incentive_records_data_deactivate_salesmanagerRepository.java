package com.Tasko.Registration.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Salesmanager_incentive_records_data_deactivate_salesmanager;
import com.Tasko.Registration.Entity.Salesmanager_register_disactivate_salesmanager;

public interface Salesmanager_incentive_records_data_deactivate_salesmanagerRepository extends JpaRepository<Salesmanager_incentive_records_data_deactivate_salesmanager, Long>{
	List<Salesmanager_incentive_records_data_deactivate_salesmanager> findBySalesmanid(Long id);

	Optional<Salesmanager_register_disactivate_salesmanager> findByPan(String pan);
}
