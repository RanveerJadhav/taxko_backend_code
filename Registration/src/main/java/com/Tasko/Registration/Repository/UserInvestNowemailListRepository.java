package com.Tasko.Registration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.UserInvest_Now_email_List;
import java.util.List;


public interface UserInvestNowemailListRepository extends JpaRepository<UserInvest_Now_email_List,Long>{
List<UserInvest_Now_email_List> findByPan(String pan);
}
