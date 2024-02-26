package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Subscriptionpack_histroy_disactivate_salesmanager;

public interface Subscriptionpack_histroy_disactivate_salesmanagerRepository extends JpaRepository<Subscriptionpack_histroy_disactivate_salesmanager,Long>{

	List<Subscriptionpack_histroy_disactivate_salesmanager> findBySalespersonId(String pan);

	List<Subscriptionpack_histroy_disactivate_salesmanager> findByDissalespersonId(Long id);

}
