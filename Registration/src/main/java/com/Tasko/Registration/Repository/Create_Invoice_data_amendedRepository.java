package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.Create_Invoice_data;
import com.Tasko.Registration.Entity.Create_Invoice_data_amended;

public interface Create_Invoice_data_amendedRepository extends JpaRepository<Create_Invoice_data_amended, Long>{
	@Query("SELECT c FROM Create_Invoice_data_amended c WHERE c.bill_from_GSTIN = :bill_from_GSTIN AND c.invoicemonth = :invoicemonth AND c.invoiceyear = :invoiceyear")
	List<Create_Invoice_data_amended> findByBill_from_GSTINAndInvoicemonthAndInvoiceyear(String bill_from_GSTIN,String invoicemonth,String invoiceyear);
	
	@Query("SELECT c FROM Create_Invoice_data_amended c WHERE c.bill_to_GSTIN = :bill_to_GSTIN AND c.invoicemonth = :invoicemonth AND c.invoiceyear = :invoiceyear")
	List<Create_Invoice_data_amended> findByBill_to_GSTINAndInvoicemonthAndInvoiceyear(String bill_to_GSTIN,String invoicemonth,String invoiceyear);
}
