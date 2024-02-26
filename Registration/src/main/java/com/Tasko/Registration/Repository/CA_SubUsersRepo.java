package com.Tasko.Registration.Repository;

import com.Tasko.Registration.Entity.CA_SubUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CA_SubUsersRepo extends JpaRepository<CA_SubUsers,Long>
{

    List<CA_SubUsers> findByUserid(Long userid);

    List<CA_SubUsers> findCA_SubUserById(Long id);

    Optional<CA_SubUsers> findByPan(String username);
    @Query(value ="SELECT * FROM tasko.ca_sub_users WHERE pan = :pan", nativeQuery = true)
    CA_SubUsers findByPan1(String pan);

    CA_SubUsers findByOtp(String otp);

//    @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.refrenceId = :refrenceId")
//    Long countOfByRefrenceId(String refrenceId);

    @Query("SELECT COUNT(c) FROM CA_SubUsers c WHERE c.userid = :userid")
    Long countOfByUserid(Long userid);

//    @Query(value ="SELECT * FROM tasko.ca_sub_users WHERE pan = :pan", nativeQuery = true)
//    CA_SubUsers findByPan1(String username);


}
