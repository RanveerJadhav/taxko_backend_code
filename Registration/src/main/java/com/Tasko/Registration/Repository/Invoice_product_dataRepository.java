package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tasko.Registration.Entity.Invoice_product_data;

public interface Invoice_product_dataRepository extends JpaRepository<Invoice_product_data, Long>{
	
	@Query("SELECT c FROM Invoice_product_data c WHERE c.bill_from_GSTIN = :bill_from_GSTIN AND c.invoicemonth = :invoicemonth AND c.invoiceyear = :invoiceyear AND c.invoiceid = :invoiceid")
	List<Invoice_product_data> findByBill_to_GSTINAndInvoicemonthAndInvoiceyearAndInvoiceid(String bill_from_GSTIN,String invoicemonth,String invoiceyear,Long invoiceid);

}
