package com.Tasko.Registration.Repository;

import com.Tasko.Registration.Entity.PaymentAndTerms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface paymentAndTermsRepository extends JpaRepository<PaymentAndTerms,Long>
{


    Optional<Object> findByCategory(String category);
}
