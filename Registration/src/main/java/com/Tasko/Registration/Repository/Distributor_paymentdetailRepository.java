package com.Tasko.Registration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Distributor_paymentdetail;

public interface Distributor_paymentdetailRepository extends JpaRepository<Distributor_paymentdetail, Long> {

	Optional<Distributor_paymentdetail> findByPan(String pan);
//Optional<Distributor_paymentdetail>
}
