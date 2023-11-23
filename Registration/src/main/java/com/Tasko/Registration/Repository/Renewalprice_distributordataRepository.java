package com.Tasko.Registration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.Renewalprice_distributordata;
import java.util.List;


public interface Renewalprice_distributordataRepository extends JpaRepository<Renewalprice_distributordata,Long>{
                Optional<Renewalprice_distributordata> findByPan(String pan);
                @Query("SELECT SUM(c.renewalPrice) FROM Renewalprice_distributordata c WHERE c.pan = :pan")
                Long calculateSumOfRenewalPrice(String pan);
                         
                @Query("SELECT SUM(c.renewalPrice) FROM Renewalprice_distributordata c WHERE YEAR(c.substartdatebyadmin) = :year AND MONTH(c.substartdatebyadmin) = :month AND c.pan = :pan")
           	    Long calculateSumOfRenewalPricelastmonth(int year,int month,String pan);
                

}
