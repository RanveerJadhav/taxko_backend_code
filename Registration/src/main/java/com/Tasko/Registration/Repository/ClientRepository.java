package com.Tasko.Registration.Repository;

import java.util.List;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.Tasko.Registration.Entity.Client_Registation_Form;

public interface ClientRepository extends JpaRepository<Client_Registation_Form,Long>
{	
	//@Query(value ="select * from Client_Registation_Form where userid=:userid", nativeQuery = true)
	List<Client_Registation_Form> findAllByUserid(Long userid);

	Optional<Client_Registation_Form> findByPan(String pan);
	
	//List<Client_Registation_Form> findByPan11(String pan);
	Optional<Client_Registation_Form> findByEmail(String email);
	@Query("SELECT COUNT(c) FROM Client_Registation_Form c WHERE c.email = :email")
	Long countOfTotalEmailClient(String email);

//	List<Client_Registation_Form> findAllByPan(String pan);

	List<Client_Registation_Form> findByCategoryAndUserid(String category, Long userid);

	Client_Registation_Form findByUseridAndClientId(Long userid,Long clientId);

	Client_Registation_Form findByCategoryAndUseridAndClientId(String category,Long userid,Long clientId);


	Long countByUserid(Long userid);

	Long countByCategoryAndUserid(String category, Long userid);
	
	
//	@Query("SELECT c.clientid FROM Client_Registation_Form c WHERE c.userid = :userid")
//    List<Long> findAllClientidByUserid(Long userid);
	
//	 List<Long> findAllClientidByUserid(Long userid);

	 
	   
	   @Query(value ="SELECT client_id FROM tasko.client_registation_form WHERE userid = :userid", nativeQuery = true)
	   List<Long> findClientIdByUserId(Long userid);

	   @Query(value ="SELECT * FROM tasko.client_registation_form WHERE pan = :pan", nativeQuery = true)
	   List<Client_Registation_Form> findByPan1(String pan);
	   


	Client_Registation_Form findByPanAndCategory(String pan, String category);

	Client_Registation_Form findByClientIdAndCategory(Long userid, String category);

	List<Client_Registation_Form> findAllByPan(String pan);
	
	
	 @Modifying
	    @Transactional
	    @Query("UPDATE Client_Registation_Form u SET u.category = :category WHERE u.clientId = :clientId")
	    void updateUserNameByclientId(Long clientId, String category);
	 
	 
	 
	 @Query("SELECT COUNT(DISTINCT pan) FROM Client_Registation_Form")
	    Long CountOfTotalclient();
	 
	 @Query("SELECT c.pan, c.name FROM Client_Registation_Form c GROUP BY c.pan, c.name")
	 List<Object[]> countDistinctPanAndName();

//	 @Query("SELECT c FROM Client_Registation_Form c WHERE c.category = 'Income_Tax' OR c.category = 'Both'")
//	 List<Client_Registation_Form> selectClientsByCategory();
	 List<Client_Registation_Form> findAllByCategoryIn(List<String> categories);
	 
	 
	 
	 @Query("SELECT COUNT(c) FROM Client_Registation_Form c WHERE c.category = 'Income_Tax' OR c.category = 'Both'")
	 Long countOfTotalClientIncomeTax();
	 @Query("SELECT COUNT(c) FROM Client_Registation_Form c WHERE c.category = 'GST' OR c.category = 'Both'")
	 Long countOfTotalClientGST();
	 
//	 @Query("SELECT COUNT(c) FROM Client_Registation_Form (c WHERE c.category = 'Income_Tax' OR c.category = 'Both')AND c.userid = :userid")
//	 Long countOfTotalClientIncomeTaxByUserid(Long userid);
	 @Query("SELECT COUNT(c) FROM Client_Registation_Form c WHERE (c.category = 'Income_Tax' OR c.category = 'Both') AND c.userid = :userid")
	 Long countOfTotalClientIncomeTaxByUserid(@Param("userid") Long userid);

	 
//	 @Query("SELECT COUNT(c) FROM Client_Registation_Form c WHERE c.category = 'GST' OR c.category = 'Both')AND c.userid = :userid")
//	 Long countOfTotalClientGSTByUserid(Long userid);
	 @Query("SELECT COUNT(c) FROM Client_Registation_Form c WHERE (c.category = 'GST' OR c.category = 'Both') AND c.userid = :userid")
	 Long countOfTotalClientGSTByUserid(@Param("userid") Long userid);


	 @Query("SELECT COUNT(c) FROM Client_Registation_Form c WHERE c.userid = :userid")
	 Long countoftotalclientByUserid(Long userid);


    List<Client_Registation_Form> findByClientIdIn(List<Long> clientIds);
    
    @Query("SELECT c FROM Client_Registation_Form c WHERE (c.category = 'Income_Tax' OR c.category = 'Both') AND c.userid = :userid")
    List<Client_Registation_Form> findOfIncomeTaxByUserid(Long userid);

    
//    @Query(value = "SELECT * FROM Client_Registation_Form c WHERE c.category = 'GST' OR c.category = 'Both' AND c.userid = :userid", nativeQuery = true)
//    List<Client_Registation_Form> findOfGSTByUserid(Long userid);
	@Query(value ="SELECT * FROM tasko.client_registation_form WHERE pan = :pan", nativeQuery = true)
	Client_Registation_Form findByPan2(String pan);

    Optional<Client_Registation_Form> findByClientId(Long clientId);

	@Query(value ="SELECT client_id FROM tasko.client_registation_form WHERE userid = :userid And sub_user_id=:subUserid", nativeQuery = true)
	List<Long> findClientIdByUserIdAndSubUserid(Long userid, Long subUserid);

	List<Client_Registation_Form> findAllByFamilyId(String familyId);

	@Query("SELECT COUNT(DISTINCT familyId) FROM Client_Registation_Form c WHERE c.userid = :userid")
	Long countByUserId(@Param("userid") Long userid);

	@Query(value = "SELECT * FROM Client_Registation_Form c WHERE (c.category = 'GST' OR c.category = 'Both') AND c.userid = :userid", nativeQuery = true)
	List<Client_Registation_Form> findOfGSTByUserid(@Param("userid") Long userid);
}
