package com.Tasko.Registration.Repository;

import com.Tasko.Registration.Entity.User_RegistrationsForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgetRepo extends JpaRepository<User_RegistrationsForm,Long>
{
    User_RegistrationsForm findByEmail(String email);

    User_RegistrationsForm findByPan(String pan);

    User_RegistrationsForm findByPanAndEmail(String pan, String email);

    User_RegistrationsForm findByOtp(String otp);
}

