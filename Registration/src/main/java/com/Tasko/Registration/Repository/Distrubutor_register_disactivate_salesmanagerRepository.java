package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Distrubutor_register_disactivate_salesmanager;

public interface Distrubutor_register_disactivate_salesmanagerRepository extends JpaRepository<Distrubutor_register_disactivate_salesmanager, Long>{

	List<Distrubutor_register_disactivate_salesmanager> findBySalesmanid(Long id);

}
