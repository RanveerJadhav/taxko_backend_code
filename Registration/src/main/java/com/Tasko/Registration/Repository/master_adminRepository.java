package com.Tasko.Registration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.Tasko.Registration.Entity.master_admin;

public interface master_adminRepository extends JpaRepository<master_admin, Long>
{

    Optional<master_admin> findByusername(String username);

    master_admin findByUsername(String username);
    
}
