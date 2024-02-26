package com.Tasko.Registration.Repository;

import com.Tasko.Registration.Entity.Client_Registation_Form_Temp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface Client_Registation_Form_Temp_Repo extends JpaRepository<Client_Registation_Form_Temp,Long>
{
    Optional<Client_Registation_Form_Temp> findByPan(String username);

    List<Client_Registation_Form_Temp> findByUserid(Long userid);

    void deleteByPan(String pan);

    Optional<Client_Registation_Form_Temp> findByEmail(String email);

    @Query(value ="SELECT * FROM tasko.client_registation_form_temp WHERE pan = :pan", nativeQuery = true)
    List<Client_Registation_Form_Temp> findByPan1(String pan);
}
