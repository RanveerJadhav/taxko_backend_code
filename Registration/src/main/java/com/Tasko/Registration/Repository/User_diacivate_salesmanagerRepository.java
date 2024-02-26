package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.User_diacivate_salesmanager;

public interface User_diacivate_salesmanagerRepository extends JpaRepository<User_diacivate_salesmanager, Long>{

	List<User_diacivate_salesmanager> findByDissalespersonId(Long id);

	List<User_diacivate_salesmanager> findBySalespersonId(String pan);

}
