package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Salesmanager_target_disactivate_salesmanager;

public interface Salesmanager_target_disactivate_salesmanagerRepository extends JpaRepository<Salesmanager_target_disactivate_salesmanager, Long>{

	List<Salesmanager_target_disactivate_salesmanager> findBySalesmanid(Long id);

}
