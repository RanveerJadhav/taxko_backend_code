package com.Tasko.Registration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Salemanager_target;
import com.Tasko.Registration.Entity.Subscriptionpack_history;

import java.util.List;


public interface Salemanager_targetRepository extends JpaRepository<Salemanager_target,Long>{
Optional<Salemanager_target>findBySalesmanidAndYear(Long salesmanid, String year);
Optional<Salemanager_target>findByPan(String pan);
List<Salemanager_target> findBySalesmanid(Long id);
}