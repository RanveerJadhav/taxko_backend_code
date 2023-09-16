package com.Tasko.Registration.Repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.User_RegistrationsForm;

import java.util.Optional;


public interface TaskoRepository extends JpaRepository<User_RegistrationsForm,Long>
{
    Optional<User_RegistrationsForm> findByEmail(String email);
   // User_RegistrationsForm findByPan(String pan);
    Optional<User_RegistrationsForm> findByPan(String username);

    User_RegistrationsForm findByRegId(Long regId);


}
