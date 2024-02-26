package com.Tasko.Registration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.Create_Invoice_data;

import java.util.Date;
import java.util.List;


public interface Create_Invoice_dataRepository extends JpaRepository<Create_Invoice_data, Long>{
	@Query("SELECT c FROM Create_Invoice_data c WHERE c.bill_from_GSTIN = :bill_from_GSTIN AND c.invoicemonth = :invoicemonth AND c.invoiceyear = :invoiceyear")
	List<Create_Invoice_data> findByBill_from_GSTINAndInvoicemonthAndInvoiceyear(String bill_from_GSTIN,String invoicemonth,String invoiceyear);
	
	@Query("SELECT c FROM Create_Invoice_data c WHERE c.bill_to_GSTIN = :bill_to_GSTIN AND c.invoicemonth = :invoicemonth AND c.invoiceyear = :invoiceyear")
	List<Create_Invoice_data> findByBill_to_GSTINAndInvoicemonthAndInvoiceyear(String bill_to_GSTIN,String invoicemonth,String invoiceyear);
  
	
	@Query("SELECT COUNT(c) FROM Create_Invoice_data c WHERE c.bill_from_GSTIN = :bill_from_GSTIN")
	Long countByBill_from_GSTIN(String bill_from_GSTIN);
	
	@Query("SELECT MAX(c.invoiceid) FROM Create_Invoice_data c WHERE c.bill_from_GSTIN = :bill_from_GSTIN AND DATE(c.invoicedate) >= :startdate AND DATE(c.invoicedate) <= :enddate")
	Long findHighestInvoiceId(String bill_from_GSTIN,Date startdate,Date enddate);

//	@Query("SELECT MAX(c.invoiceid) FROM Create_Invoice_data c WHERE c.bill_from_GSTIN = :bill_from_GSTIN")
//	Long findHighestInvoiceId(String bill_from_GSTIN);

}
