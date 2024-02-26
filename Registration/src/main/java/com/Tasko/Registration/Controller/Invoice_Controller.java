package com.Tasko.Registration.Controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.sound.midi.Soundbank;

import org.hibernate.type.descriptor.java.CurrencyJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.Tasko.Registration.Entity.Create_Invoice_data;
import com.Tasko.Registration.Entity.Create_Invoice_data_amended;
import com.Tasko.Registration.Entity.Invoice_product_data;
import com.Tasko.Registration.Entity.Invoice_product_data_amended;
import com.Tasko.Registration.Repository.Create_Invoice_dataRepository;
import com.Tasko.Registration.Repository.Create_Invoice_data_amendedRepository;
import com.Tasko.Registration.Repository.Invoice_product_dataRepository;
import com.Tasko.Registration.Repository.Invoice_product_data_amendedRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

@RestController
@CrossOrigin(origins = "*")
public class Invoice_Controller {
	
	@Autowired
	private Create_Invoice_dataRepository  create_Invoice_dataRepository;
	
	@Autowired
	private Invoice_product_dataRepository invoice_product_dataRepository;
	
	@Autowired
	private Create_Invoice_data_amendedRepository create_Invoice_data_amendedRepository;
	
	@Autowired
	private Invoice_product_data_amendedRepository invoice_product_data_amendedRepository;
	
	@PostMapping("/create-invoice")
    public ResponseEntity<Create_Invoice_data> createInvoice(@RequestBody Create_Invoice_data createInvoiceData) {
		 try {
			 Date currentDate = new Date();
			 LocalDate currentDate1 = LocalDate.now();
			 createInvoiceData.setInvoicedate(currentDate);
        create_Invoice_dataRepository.save(createInvoiceData);
        return ResponseEntity.ok(createInvoiceData);
		    } catch (Exception e) {
		        // Handle exceptions appropriately, log the error, and return a meaningful response
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		    }
    }

    @PostMapping("/create-invoice/product")
    public ResponseEntity<String> productdata(@RequestBody List<Invoice_product_data> invoiceProductDataList) {
    	System.out.println("hii");
    	
        
        // Assuming createInvoiceDataId is a field in Create_Invoice_data class
        

        for (Invoice_product_data invoiceProductData : invoiceProductDataList) {
            invoice_product_dataRepository.save(invoiceProductData);
        }
        return new ResponseEntity<>("Invoice created successfully", HttpStatus.CREATED);
    }
    
    
    @GetMapping("/list/ofinvoice/findByBillFromGSTIN")
    public ResponseEntity<List<Create_Invoice_data>> findByBillFromGSTIN(@RequestParam String bill_from_GSTIN,@RequestParam String Invoicemonth,@RequestParam String Invoiceyear) {
        try {
            List<Create_Invoice_data> invoices = create_Invoice_dataRepository.findByBill_from_GSTINAndInvoicemonthAndInvoiceyear(bill_from_GSTIN,Invoicemonth,Invoiceyear);
            return new ResponseEntity<>(invoices, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or perform other actions as needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/list/ofinvoice/findByBillToGSTIN")
    public ResponseEntity<List<Create_Invoice_data>> findByBillToGSTIN(@RequestParam String bill_to_GSTIN,@RequestParam String Invoicemonth,@RequestParam String Invoiceyear) {
        try {
            List<Create_Invoice_data> invoices = create_Invoice_dataRepository.findByBill_to_GSTINAndInvoicemonthAndInvoiceyear(bill_to_GSTIN,Invoicemonth,Invoiceyear);
            return new ResponseEntity<>(invoices, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or perform other actions as needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/list/product/ofinvoice/findByBillFromGSTIN")
    public ResponseEntity<List<Invoice_product_data>> productfindByBillToGSTIN(@RequestParam String bill_from_GSTIN,@RequestParam String Invoicemonth,@RequestParam String Invoiceyear,@RequestParam Long invoiceid) {
        try {
            List<Invoice_product_data> invoices = invoice_product_dataRepository.findByBill_to_GSTINAndInvoicemonthAndInvoiceyearAndInvoiceid(bill_from_GSTIN, Invoicemonth, Invoiceyear,invoiceid);
            return new ResponseEntity<>(invoices, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or perform other actions as needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @DeleteMapping("/delete/invoice-products/{id}")
    public ResponseEntity<String> deleteInvoiceProductById(@PathVariable Long id) {
        try {
        	invoice_product_dataRepository.deleteById(id);
            return new ResponseEntity<>("Invoice product with ID " + id + " has been deleted", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            return new ResponseEntity<>("Failed to delete invoice product with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/list/amend/ofinvoice/findByBillFromGSTIN")
    public ResponseEntity<List<Create_Invoice_data_amended>> findByBillFromGSTINamend(@RequestParam String bill_from_GSTIN,@RequestParam String Invoicemonth,@RequestParam String Invoiceyear) {
        try {
            List<Create_Invoice_data_amended> invoices = create_Invoice_data_amendedRepository.findByBill_from_GSTINAndInvoicemonthAndInvoiceyear(bill_from_GSTIN,Invoicemonth,Invoiceyear);
            return new ResponseEntity<>(invoices, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or perform other actions as needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/list/amend/ofinvoice/findByBillToGSTIN")
    public ResponseEntity<List<Create_Invoice_data_amended>> findByBillToGSTINamend(@RequestParam String bill_to_GSTIN,@RequestParam String Invoicemonth,@RequestParam String Invoiceyear) {
        try {
            List<Create_Invoice_data_amended> invoices = create_Invoice_data_amendedRepository.findByBill_to_GSTINAndInvoicemonthAndInvoiceyear(bill_to_GSTIN,Invoicemonth,Invoiceyear);
            return new ResponseEntity<>(invoices, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or perform other actions as needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/amend/ofinvoice/findById/{id}")
    public ResponseEntity<Create_Invoice_data_amended> findById(@PathVariable Long id) {
        try {
            Optional<Create_Invoice_data_amended> invoiceOptional = create_Invoice_data_amendedRepository.findById(id);
            
            if (invoiceOptional.isPresent()) {
                Create_Invoice_data_amended invoice = invoiceOptional.get();
                return new ResponseEntity<>(invoice, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception or perform other actions as needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @GetMapping("/list/amend/ofinvoice/findByBillToGSTIN")
//    public ResponseEntity<List<Create_Invoice_data_amended>> findByBillToGSTINamend(@RequestParam String bill_to_GSTIN,@RequestParam String Invoicemonth,@RequestParam String Invoiceyear) {
//        try {
//            List<Create_Invoice_data_amended> invoices = create_Invoice_data_amendedRepository.findByBill_to_GSTINAndInvoicemonthAndInvoiceyear(bill_to_GSTIN,Invoicemonth,Invoiceyear);
//            return new ResponseEntity<>(invoices, HttpStatus.OK);
//        } catch (Exception e) {
//            // Log the exception or perform other actions as needed
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    
    @GetMapping("/list/amend/product/ofinvoice/findByBillFromGSTIN")
    public ResponseEntity<List<Invoice_product_data_amended>> productfindByBillToGSTINamend(@RequestParam String bill_from_GSTIN,@RequestParam String Invoicemonth,@RequestParam String Invoiceyear,@RequestParam Long invoiceid) {
        try {
            List<Invoice_product_data_amended> invoices = invoice_product_data_amendedRepository.findByBill_to_GSTINAndInvoicemonthAndInvoiceyearAndInvoiceid(bill_from_GSTIN, Invoicemonth, Invoiceyear,invoiceid);
            return new ResponseEntity<>(invoices, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or perform other actions as needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/amend/product/ofinvoice/findById/{id}")
    public ResponseEntity<Invoice_product_data_amended> productFindById(@PathVariable Long id) {
        try {
            Optional<Invoice_product_data_amended> invoiceProductOptional = invoice_product_data_amendedRepository.findById(id);
            
            if (invoiceProductOptional.isPresent()) {
                Invoice_product_data_amended invoiceProduct = invoiceProductOptional.get();
                return new ResponseEntity<>(invoiceProduct, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception or perform other actions as needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update/product/ofinvoice/{id}")
    public ResponseEntity<String> updateProductOfInvoice(@PathVariable Long id,@RequestBody Invoice_product_data updatedProductData) {
        try {
        	System.out.println("hii");
            // Find the existing record to update
            Optional<Invoice_product_data> existingProduct = invoice_product_dataRepository.findById(id);      
            // Check if the record exists
            if (existingProduct != null) {
            	Invoice_product_data existingProductdata = existingProduct.get();
                // Update the existing record with the new data
            existingProductdata.setInvoiceid(updatedProductData.getInvoiceid());
            existingProductdata.setProductname(updatedProductData.getProductname());
            existingProductdata.setDescription(updatedProductData.getDescription());
            existingProductdata.setHsn(updatedProductData.getHsn());
            existingProductdata.setQuantity(updatedProductData.getQuantity());
            existingProductdata.setUnit(updatedProductData.getUnit());
            existingProductdata.setValue(updatedProductData.getValue());
            existingProductdata.setCgst_sgst_rate(updatedProductData.getCgst_sgst_rate());
            existingProductdata.setIgst_rate(updatedProductData.getIgst_rate());
            existingProductdata.setCess_Advol_rate(updatedProductData.getCess_Advol_rate());
            existingProductdata.setCess_non_Advol_rate(updatedProductData.getCess_non_Advol_rate());
            existingProductdata.setBill_from_GSTIN(updatedProductData.getBill_from_GSTIN());
            existingProductdata.setInvoiceyear(updatedProductData.getInvoiceyear());
            existingProductdata.setInvoicemonth(updatedProductData.getInvoicemonth());
            // Update fields accordingly
                // Save the updated record
               invoice_product_dataRepository.save(existingProductdata);
                
                return new ResponseEntity<>("Record updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Record not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception or perform other actions as needed
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/invoice/byid/{id}")
    public ResponseEntity<Optional<Create_Invoice_data>> getInvoiceById(@PathVariable Long id) {
        try {
            Optional<Create_Invoice_data> optionalInvoice = create_Invoice_dataRepository.findById(id);

            return ResponseEntity.ok(optionalInvoice);

        } catch (Exception e) {
        	 return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get_next_invoice_id/{bill_from_GSTIN}")
    public ResponseEntity<Long> getNextInvoiceId(@PathVariable String bill_from_GSTIN) {
        try {
        	System.out.println("hii");
            Long nextInvoiceId = create_Invoice_dataRepository.countByBill_from_GSTIN(bill_from_GSTIN);
            nextInvoiceId = (nextInvoiceId != null) ? nextInvoiceId : 0L;
            return ResponseEntity.ok(nextInvoiceId + 1);
        } catch (Exception e) {
            // Log the exception or handle it according to your application's needs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/highest-invoice-id")
    public ResponseEntity<Long> getHighestInvoiceId(@RequestParam String bill_from_GSTIN, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date invoicestartdate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date invoiceenddate) {
        try {
        	System.out.println("hii");
            Long highestInvoiceId = create_Invoice_dataRepository.findHighestInvoiceId(bill_from_GSTIN, invoicestartdate,invoiceenddate);
            highestInvoiceId = (highestInvoiceId != null) ? highestInvoiceId : 0L;
            return ResponseEntity.ok(highestInvoiceId+1);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PostMapping("/api/invoice/amended/save")
    public ResponseEntity<Create_Invoice_data_amended> saveInvoiceData(@RequestBody Create_Invoice_data_amended invoiceData) {
        try {
        	Create_Invoice_data_amended savedInvoice = create_Invoice_data_amendedRepository.save(invoiceData);
            return ResponseEntity.ok(savedInvoice);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save invoice data", e);
        }
    }
    
    @PostMapping("/api/invoice/product/amended/save")
    public ResponseEntity<List<Invoice_product_data_amended>> saveInvoiceProductData(@RequestBody List<Invoice_product_data_amended> invoiceProductDataList) {
        try {
            List<Invoice_product_data_amended> savedInvoiceProducts = invoice_product_data_amendedRepository.saveAll(invoiceProductDataList);
            return ResponseEntity.ok(savedInvoiceProducts);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save invoice product data", e);
        }
    }
    
    
    
    // Update an existing invoice
    @PutMapping("/updateinvoice/{id}")
    public ResponseEntity<String> updateInvoice(@PathVariable Long id, @RequestBody Create_Invoice_data updatedInvoiceData) {
        try {
            Optional<Create_Invoice_data> optionalInvoice = create_Invoice_dataRepository.findById(id);

            if (optionalInvoice.isPresent()) {
                Create_Invoice_data existingInvoice = optionalInvoice.get();

                // Update the fields of existingInvoice with the corresponding values from updatedInvoiceData
                existingInvoice.setSupplytype(updatedInvoiceData.getSupplytype());
                existingInvoice.setSubtype(updatedInvoiceData.getSubtype());
                existingInvoice.setDocumenttype(updatedInvoiceData.getDocumenttype());
                existingInvoice.setDocumentno(updatedInvoiceData.getDocumentno());
                existingInvoice.setDocumentdate(updatedInvoiceData.getDocumentdate());
                existingInvoice.setBill_from_Name(updatedInvoiceData.getBill_from_Name());
                existingInvoice.setBill_from_GSTIN(updatedInvoiceData.getBill_from_GSTIN());
                existingInvoice.setBill_from_State(updatedInvoiceData.getBill_from_State());
                existingInvoice.setDispatch_from_Address(updatedInvoiceData.getDispatch_from_Address());
                existingInvoice.setDispatch_from_Place(updatedInvoiceData.getDispatch_from_Place());
                existingInvoice.setDispatch_from_Pincode(updatedInvoiceData.getDispatch_from_Pincode());
                existingInvoice.setBill_to_Name(updatedInvoiceData.getBill_to_Name());
                existingInvoice.setBill_to_GSTIN(updatedInvoiceData.getBill_to_GSTIN());
                existingInvoice.setBill_to_State(updatedInvoiceData.getBill_from_State());
                existingInvoice.setShip_to_Address(updatedInvoiceData.getShip_to_Address());
                existingInvoice.setShip_to_Place(updatedInvoiceData.getShip_to_Place());
                existingInvoice.setShip_to_Pincode(updatedInvoiceData.getShip_to_Pincode());
                existingInvoice.setTotal_taxiable_value(updatedInvoiceData.getTotal_taxiable_value());
                existingInvoice.setCgst_amount(updatedInvoiceData.getCgst_amount());
                existingInvoice.setSgst_amount(updatedInvoiceData.getSgst_amount());
                existingInvoice.setIgst_amount(updatedInvoiceData.getIgst_amount());
                existingInvoice.setRate(updatedInvoiceData.getRate());
                existingInvoice.setCess_advol_amount(updatedInvoiceData.getCess_advol_amount());
                existingInvoice.setCess_non_advol_amount(updatedInvoiceData.getCess_non_advol_amount());
                existingInvoice.setOther_amount(updatedInvoiceData.getOther_amount());
                existingInvoice.setTotal_inv_amount(updatedInvoiceData.getTotal_inv_amount());
                existingInvoice.setTransportation_transpoter_id(updatedInvoiceData.getTransportation_transpoter_id());
                existingInvoice.setTransportation_transpoter_Name(updatedInvoiceData.getTransportation_transpoter_Name());
                existingInvoice.setTransportation_Approxiamte_distance(updatedInvoiceData.getTransportation_Approxiamte_distance());
                existingInvoice.setPart_b_mode(updatedInvoiceData.getPart_b_mode());
                existingInvoice.setPart_b_vechiletype(updatedInvoiceData.getPart_b_vechiletype());
                existingInvoice.setPart_b_vechileNo(updatedInvoiceData.getPart_b_vechileNo());
                existingInvoice.setPart_b_Transper_doc(updatedInvoiceData.getPart_b_Transper_doc());
                existingInvoice.setInvoicemonth(updatedInvoiceData.getInvoicemonth());
                existingInvoice.setInvoiceyear(updatedInvoiceData.getInvoiceyear());
                existingInvoice.setDispatch_from_State(updatedInvoiceData.getDispatch_from_State());
                existingInvoice.setShip_to_State(updatedInvoiceData.getShip_to_State());
                existingInvoice.setTransaction_Type(updatedInvoiceData.getTransaction_Type());
                existingInvoice.setAmended(updatedInvoiceData.isAmended());
                existingInvoice.setInvoiceImport(updatedInvoiceData.isInvoiceImport());
                existingInvoice.setCancelled(updatedInvoiceData.isCancelled());
                existingInvoice.setInterstate(updatedInvoiceData.isInterstate());
                existingInvoice.setRcm(updatedInvoiceData.isRcm());
                existingInvoice.setDeemexport(updatedInvoiceData.isDeemexport());
                existingInvoice.setSez(updatedInvoiceData.isSez());
                existingInvoice.setCredit_note(updatedInvoiceData.isCredit_note());
                existingInvoice.setInvoicedate(existingInvoice.getInvoicedate());
                create_Invoice_dataRepository.save(existingInvoice);

                return new ResponseEntity<>("Invoice updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invoice not found", HttpStatus.NOT_FOUND);
            }

            
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



//System.out.println(createInvoiceData.getDocumentdate()); 
//// Save Create_Invoice_data
//Create_Invoice_data savedCreateInvoiceData = new Create_Invoice_data();
//savedCreateInvoiceData.setSupplytype(createInvoiceData.getSupplytype());
//savedCreateInvoiceData.setSubtype(createInvoiceData.getSubtype());
//savedCreateInvoiceData.setDocumenttype(createInvoiceData.getDocumenttype());
//savedCreateInvoiceData.setDocumentno(createInvoiceData.getDocumentno());
//savedCreateInvoiceData.setDocumentdate(createInvoiceData.getDocumentdate());
//savedCreateInvoiceData.setBill_from_Name(createInvoiceData.getBill_from_Name());
//savedCreateInvoiceData.setBill_from_GSTIN(createInvoiceData.getBill_from_GSTIN());
//savedCreateInvoiceData.setBill_from_State(createInvoiceData.getBill_from_State());
//savedCreateInvoiceData.setDispatch_from_Address(createInvoiceData.getDispatch_from_Address());
//savedCreateInvoiceData.setDispatch_from_Place(createInvoiceData.getDispatch_from_Place());
//savedCreateInvoiceData.setDispatch_from_Pincode(createInvoiceData.getDispatch_from_Pincode());
//savedCreateInvoiceData.setBill_to_Name(createInvoiceData.getBill_to_Name());
//savedCreateInvoiceData.setBill_to_GSTIN(createInvoiceData.getBill_from_GSTIN());
//savedCreateInvoiceData.setBill_to_State(createInvoiceData.getBill_from_State());
//savedCreateInvoiceData.setShip_to_Address(createInvoiceData.getShip_to_Address());
//savedCreateInvoiceData.setShip_to_Place(createInvoiceData.getShip_to_Place());
//savedCreateInvoiceData.setShip_to_Pincode(createInvoiceData.getShip_to_Pincode());
//savedCreateInvoiceData.setTotal_taxiable_value(createInvoiceData.getTotal_taxiable_value());
//savedCreateInvoiceData.setCgst_amount(createInvoiceData.getCgst_amount());
//savedCreateInvoiceData.setSgst_amount(createInvoiceData.getSgst_amount());
//savedCreateInvoiceData.setIgst_amount(createInvoiceData.getIgst_amount());
//savedCreateInvoiceData.setRate(createInvoiceData.getRate());
//savedCreateInvoiceData.setCess_advol_amount(createInvoiceData.getCess_advol_amount());
//savedCreateInvoiceData.setCess_non_advol_amount(createInvoiceData.getCess_non_advol_amount());
//savedCreateInvoiceData.setOther_amount(createInvoiceData.getOther_amount());
//savedCreateInvoiceData.setTotal_inv_amount(createInvoiceData.getTotal_inv_amount());
//savedCreateInvoiceData.setTransportation_transpoter_id(createInvoiceData.getTransportation_transpoter_id());
//savedCreateInvoiceData.setTransportation_transpoter_Name(createInvoiceData.getTransportation_transpoter_Name());
//savedCreateInvoiceData.setTransportation_Approxiamte_distance(createInvoiceData.getTransportation_Approxiamte_distance());
//savedCreateInvoiceData.setPart_b_mode(createInvoiceData.getPart_b_mode());
//savedCreateInvoiceData.setPart_b_vechiletype(createInvoiceData.getPart_b_vechiletype());
//savedCreateInvoiceData.setPart_b_vechileNo(createInvoiceData.getPart_b_vechileNo());
//savedCreateInvoiceData.setPart_b_Transper_doc(createInvoiceData.getPart_b_Transper_doc());
//savedCreateInvoiceData.setInvoicemonth(createInvoiceData.getInvoicemonth());
//savedCreateInvoiceData.setInvoiceyear(createInvoiceData.getInvoiceyear());
//savedCreateInvoiceData.setDispatch_from_State(createInvoiceData.getDispatch_from_State());
//savedCreateInvoiceData.setShip_to_State(createInvoiceData.getShip_to_State());
//savedCreateInvoiceData.setTransaction_Type(createInvoiceData.getTransaction_Type());
//savedCreateInvoiceData.setAmended(createInvoiceData.isAmended());
//savedCreateInvoiceData.setInvoiceImport(createInvoiceData.isInvoiceImport());
//savedCreateInvoiceData.setCancelled(createInvoiceData.isCancelled());
//savedCreateInvoiceData.setInterstate(createInvoiceData.isInterstate());
//savedCreateInvoiceData.setRcm(createInvoiceData.isRcm());
//savedCreateInvoiceData.setDeemexport(createInvoiceData.isDeemexport());
//savedCreateInvoiceData.setSez(createInvoiceData.isSez());
//savedCreateInvoiceData.setCredit_note(createInvoiceData.isCredit_note());
// Set the ID of the saved Create_Invoice_data to the related Invoice_product_data
