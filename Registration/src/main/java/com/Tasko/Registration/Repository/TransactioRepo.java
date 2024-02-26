package com.Tasko.Registration.Repository;

import com.Tasko.Registration.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactioRepo extends JpaRepository<Transaction,Long> {
}