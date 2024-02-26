package com.Tasko.Registration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.DistributionReg;
import com.Tasko.Registration.Entity.Salesman_Register;

public interface Salesman_RegisterRepository extends JpaRepository<Salesman_Register, Long> {

	Optional<Salesman_Register> findByPan(String username);

}
