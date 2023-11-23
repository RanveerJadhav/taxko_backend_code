package com.Tasko.Registration.Repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.User_RegistrationsForm;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface TaskoRepository extends JpaRepository<User_RegistrationsForm,Long>
{
    Optional<User_RegistrationsForm> findByEmail(String email);
   // User_RegistrationsForm findByPan(String pan);
    Optional<User_RegistrationsForm> findByPan(String username);

    User_RegistrationsForm findByRegId(Long regId);
    @Query("SELECT COUNT(DISTINCT pan) FROM User_RegistrationsForm")
    String CountOfTotaluser();
    
    
    @Query("SELECT profession, count(profession) FROM User_RegistrationsForm GROUP BY profession")
    List<Object[]> CountOfByProfession();
    
    
    
    @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Chartered Accountant'")
	 Long countOfTotalprofession1();

    
    @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Tax Consultant'")
	 Long countOfTotalprofession2();
    
    @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Tax Return Preparer(TRP)'")
	 Long countOfTotalprofession3();
    
    @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Accountant'")
	 Long countOfTotalprofession4();
    
    @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Certified Consultant'")
	 Long countOfTotalprofession5();
    
    @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Advocate'")
	 Long countOfTotalprofession6();
    
    @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Other'")
	 Long countOfTotalprofession7();
    
    
     List<User_RegistrationsForm>findByprofession(String profession);
     
     @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.refrenceId = :refrenceId")
     Long countOfByRefrenceId(String refrenceId);

      
     List<User_RegistrationsForm>findByRefrenceId(String pan);
     
     /////////////distrubutor all query////////////////////////////////////////////
     
     @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.disrefrenceId = :disrefrenceId")
     Long countOfByDisrefrenceId(String disrefrenceId);
     
     List<User_RegistrationsForm>findByDisrefrenceId(String disrefrenceId);
     
     List<User_RegistrationsForm> findByprofessionAndDisrefrenceId(String profession, String disrefrenceId);
     
     @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Chartered Accountant' AND c.disrefrenceId = :disrefrenceId")
     Long countdisOfTotalprofession1(String disrefrenceId);
     
     @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Tax Consultant' AND c.disrefrenceId = :disrefrenceId")
	 Long countdisOfTotalprofession2(String disrefrenceId);
     
     @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Tax Return Preparer(TRP)'  AND c.disrefrenceId = :disrefrenceId")
	 Long countdisOfTotalprofession3(String disrefrenceId);
     
     @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Accountant'  AND c.disrefrenceId = :disrefrenceId")
	 Long countdisOfTotalprofession4(String disrefrenceId);
     
     @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Certified Consultant'  AND c.disrefrenceId = :disrefrenceId")
   	 Long countdisOfTotalprofession5(String disrefrenceId);
     
     @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Advocate'  AND c.disrefrenceId = :disrefrenceId")
	 Long countdisOfTotalprofession6(String disrefrenceId);
     
     @Query("SELECT COUNT(c) FROM User_RegistrationsForm c WHERE c.profession = 'Other' AND c.disrefrenceId = :disrefrenceId")
   	 Long countdisOfTotalprofession7(String disrefrenceId);
       
    
   
     
}
