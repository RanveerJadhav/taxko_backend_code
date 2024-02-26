package com.Tasko.Registration.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Controller.DistributionController;
import com.Tasko.Registration.Entity.DistributionReg;
import com.Tasko.Registration.Entity.Subscription_Userdata;

public interface DistributionRepo extends JpaRepository<DistributionReg, Long>
{

	DistributionController save(DistributionController dis);
	Optional<DistributionReg> findByPan(String pan);
	Optional<DistributionReg> findByEmail(String email);
	List<DistributionReg> findBySalesmanid(Long salespersonId);
	List<DistributionReg> findBySalesmanidAndProfession(Long salesmanid, String profession);

}
