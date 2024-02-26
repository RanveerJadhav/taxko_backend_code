package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Salesmanager_incentive_records_data;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;


public interface Salesmanager_incentive_records_dataRepository extends JpaRepository<Salesmanager_incentive_records_data, Long>{
List<Salesmanager_incentive_records_data> findByStartdate(LocalDate startdate);
List<Salesmanager_incentive_records_data> findByEnddateAndQuerter(LocalDate enddate,String quarter);
    @Query("SELECT SUM(c.incetiveamount) FROM Salesmanager_incentive_records_data c WHERE c.pan = :pan")
    Long calculateSumOfRenewalPricesales(String pan);
	List<Salesmanager_incentive_records_data> findByPan(String pan);
}
   