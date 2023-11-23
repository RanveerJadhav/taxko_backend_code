package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Help;

public interface HelpRepository extends JpaRepository<Help,Long>{

	List<Help> findAllByUserid(Long userId);

}
