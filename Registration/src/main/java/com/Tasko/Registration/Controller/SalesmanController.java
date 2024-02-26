  package com.Tasko.Registration.Controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.relation.RelationNotFoundException;
import javax.management.relation.RoleInfoNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.Tasko.Registration.Entity.DistributionReg;
import com.Tasko.Registration.Entity.Distrubutor_register_disactivate_salesmanager;
import com.Tasko.Registration.Entity.Salemanager_target;
import com.Tasko.Registration.Entity.Salesman_Register;
import com.Tasko.Registration.Entity.Salesmanager_incentive_records_data;
import com.Tasko.Registration.Entity.Salesmanager_incentive_records_data_deactivate_salesmanager;
import com.Tasko.Registration.Entity.Salesmanager_register_disactivate_salesmanager;
import com.Tasko.Registration.Entity.Salesmanager_target_disactivate_salesmanager;
import com.Tasko.Registration.Entity.Subscription_Userdata;
import com.Tasko.Registration.Entity.Subscriptionpack_history;
import com.Tasko.Registration.Entity.Subscriptionpack_histroy_disactivate_salesmanager;
import com.Tasko.Registration.Entity.Total_paid_payment_salesmanger;
import com.Tasko.Registration.Entity.Total_paid_payment_salesmanger_deactivate_salesmanager;
import com.Tasko.Registration.Entity.Total_paid_payment_salesmanger_totalentry;
import com.Tasko.Registration.Entity.Total_salesmanger_totalentry_deactivate_salesmanager;
import com.Tasko.Registration.Entity.User_RegistrationsForm;
import com.Tasko.Registration.Entity.User_diacivate_salesmanager;
import com.Tasko.Registration.Entity.authDistribution;
import com.Tasko.Registration.Entity.authsalesmanager;
import com.Tasko.Registration.Repository.DistributionRepo;
import com.Tasko.Registration.Repository.Distrubutor_register_disactivate_salesmanagerRepository;
import com.Tasko.Registration.Repository.Salemanager_targetRepository;
import com.Tasko.Registration.Repository.Salesman_RegisterRepository;
import com.Tasko.Registration.Repository.Salesmanager_incentive_records_dataRepository;
import com.Tasko.Registration.Repository.Salesmanager_incentive_records_data_deactivate_salesmanagerRepository;
import com.Tasko.Registration.Repository.Salesmanager_register_disactivate_salesmanagerRepository;
import com.Tasko.Registration.Repository.Salesmanager_target_disactivate_salesmanagerRepository;
import com.Tasko.Registration.Repository.Subscriptionpack_historyRepository;
import com.Tasko.Registration.Repository.Subscriptionpack_histroy_disactivate_salesmanagerRepository;
import com.Tasko.Registration.Repository.Subscritpion_userdataRepository;
import com.Tasko.Registration.Repository.TaskoRepository;
import com.Tasko.Registration.Repository.Total_paid_payment_salesmangerRepository;
import com.Tasko.Registration.Repository.Total_paid_payment_salesmanger_deactivate_salesmanagerRepository;
import com.Tasko.Registration.Repository.Total_paid_payment_salesmanger_totalentryRepository;
import com.Tasko.Registration.Repository.Total_paid_payment_salesmanger_totalentry_deactivate_salesmanagerRepository;
import com.Tasko.Registration.Repository.User_diacivate_salesmanagerRepository;
import com.Tasko.Registration.Service.JwtService;
import com.Tasko.Registration.Service.TaskoService;
import com.Tasko.Registration.dto.AuthRequest;
import com.Tasko.Registration.dto.ChangePassword;
import com.Tasko.Registration.dto.ClientCount;
import com.Tasko.Registration.dto.ProfessionCount;
import com.Tasko.Registration.dto.SubscriptionCount;
import com.Tasko.Registration.error.UserAlreadyExist;


@RestController
@CrossOrigin(origins = "*")
@EnableScheduling
public class SalesmanController {

	 @Autowired
		private PasswordEncoder passwordEncoder;
	 
	 @Autowired
		private AuthenticationManager authenticationManager;
	 @Autowired
		private JwtService jwtService;
	 @Autowired
		private TaskoRepository taskoRepository;
	    @Autowired
	    private Salesman_RegisterRepository salesmanRegisterRepository;
	    @Autowired
		private Subscritpion_userdataRepository subscritpion_userdataRepository;
	    @Autowired
	    private Subscriptionpack_historyRepository subscriptionpack_historyRepository;
	    @Autowired
	    private Salemanager_targetRepository salemanager_targetRepository;
	    @Autowired
	    private User_diacivate_salesmanagerRepository user_diacivate_salesmanagerRepository;
	    @Autowired
	    private  Subscriptionpack_histroy_disactivate_salesmanagerRepository subscriptionpack_histroy_disactivate_salesmanagerRepository;
	    @Autowired 
	    private Salesmanager_target_disactivate_salesmanagerRepository salesmanager_target_disactivate_salesmanagerRepository;
	    @Autowired
	    private Distrubutor_register_disactivate_salesmanagerRepository distrubutor_register_disactivate_salesmanagerRepository;
	    @Autowired
	    private Salesmanager_register_disactivate_salesmanagerRepository salesmanager_register_disactivate_salesmanagerRepository;
	    
	    @Autowired
	    private Salesmanager_incentive_records_dataRepository salesmanager_incentive_records_dataRepository;
	    
	    @Autowired
	   private Total_paid_payment_salesmanger_totalentryRepository total_paid_payment_salesmanger_totalentryRepository;
	    
	    @Autowired
	    private Total_paid_payment_salesmangerRepository total_paid_payment_salesmangerRepository;
	    
	    @Autowired
	    private Salesmanager_incentive_records_data_deactivate_salesmanagerRepository  salesmanager_incentive_records_data_deactivate_salesmanagerRepository;
	    
       @Autowired
       private Total_paid_payment_salesmanger_deactivate_salesmanagerRepository total_paid_payment_salesmanger_deactivate_salesmanagerRepository;
       
       @Autowired
       private Total_paid_payment_salesmanger_totalentry_deactivate_salesmanagerRepository total_paid_payment_salesmanger_totalentry_deactivate_salesmanagerRepository;

	    @Autowired
	private DistributionRepo distributionRepo;
    
    Logger logger= LoggerFactory.getLogger(ClientMobileViewController.class);
    @Autowired
    private TaskoService taskoService;

    @PostMapping("/register/salesmanregister")
    public ResponseEntity<Object> registerSalesman(@RequestBody Salesman_Register salesman) {
        try {
            // Add any additional validation or logic if needed
        	salesman.setPassword(passwordEncoder.encode(salesman.getPassword()));
        	Optional<Salesman_Register> records =salesmanRegisterRepository.findByPan(salesman.getPan());
        	if(records.isEmpty())
            {
        		System.out.println("hii");
                	 Total_paid_payment_salesmanger data= new Total_paid_payment_salesmanger();
                	 data.setPan(salesman.getPan());
                	 data.setTotalpaid(0L);
                     Total_paid_payment_salesmanger savedPayment = total_paid_payment_salesmangerRepository.save(data);
            }
            Salesman_Register registeredSalesman = salesmanRegisterRepository.save(salesman);    
            return new ResponseEntity<>(registeredSalesman, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            // Handle unique constraint violation (e.g., duplicate email or pan)
            return new ResponseEntity<>("Email or PAN already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Log the exception for further analysis
            e.printStackTrace();
            // Handle other exceptions
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Custom exception handler for uncaught exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception e) {
        // Log the exception for further analysis
        e.printStackTrace();

        return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    
    
    /////////////////////////////////////////////////update state manager/////////////////////////////////////////////////////////////////
    @PutMapping("/update/salesmanregister/{id}")
    public ResponseEntity<Object> updateSalesman(@PathVariable Long id, @RequestBody Salesman_Register updatedSalesman) {
        try {
        	Salesman_Register existingSalesman = salesmanRegisterRepository.findById(id)
                    .orElse(null);
            // Update fields as needed
            existingSalesman.setName(updatedSalesman.getName());
            existingSalesman.setDatebirth(updatedSalesman.getDatebirth());
            existingSalesman.setProfession(updatedSalesman.getProfession());
            existingSalesman.setAddress(updatedSalesman.getAddress());
            existingSalesman.setEmail(updatedSalesman.getEmail());
            existingSalesman.setPin_code(updatedSalesman.getPin_code());
            existingSalesman.setTelephone(updatedSalesman.getTelephone());
            existingSalesman.setWhatsApp_Link(updatedSalesman.getWhatsApp_Link());
            existingSalesman.setState(updatedSalesman.getState());
            // ... update other fields

            // Save the updated entity
            Salesman_Register savedSalesman = salesmanRegisterRepository.save(existingSalesman);
            return new ResponseEntity<>(savedSalesman, HttpStatus.OK);
        }  catch (Exception e) {
            // Log the exception for further analysis
            e.printStackTrace();

            // Handle other exceptions
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Custom exception handler  
    @GetMapping("/get/salesmanregister/{id}")
    public ResponseEntity<Object> getSalesmanById(@PathVariable Long id) {
        Salesman_Register existingSalesman = salesmanRegisterRepository.findById(id)
                .orElse(null);

        if (existingSalesman != null) {
            return new ResponseEntity<>(existingSalesman, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Salesman not found", HttpStatus.NOT_FOUND);
        }
    }
    
    
    
    
    
    //////////////////////////////////login controller/////////////////////////////////////////////////////////
    
    @PostMapping("/authenticate/salesmanger")
	public ResponseEntity<Object> authenticateAndGetTokenForDistribution(@RequestBody AuthRequest authRequest) throws Exception
	{

		try {

			Optional<Salesman_Register> user = salesmanRegisterRepository.findByPan(authRequest.getUsername());
			Salesman_Register s=user.get();
  

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			if(user.isPresent())
				{
				   if(s.isStatus()==true)
				   {
				
					if (authentication.isAuthenticated()) 
					{
						String jwt = jwtService.generateToken(authRequest.getUsername());

						authsalesmanager response = new authsalesmanager(jwt,user);
						response.setToken(jwt);
						response.setUser(user);

						
						return ResponseEntity.ok(response);
					}
				   }
					else
					{
						throw new Exception("User Not Authorize");
					}
			}
				else
				{
					throw new Exception("User Not Found");
				}


		} 
		catch (Exception e) 
		{
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(e.getMessage());
		}
		return ResponseEntity.ok(null);

	}
    
    
    
    ////////////////////////list of distrubutor by salesman id/////////////////////////////////////////////////////
    @GetMapping("/distrubutorby/salespersonid/{salespersonId}")
    public ResponseEntity<List<DistributionReg>> getDistributorsBySalespersonId(@PathVariable Long salespersonId) {
        try {
            List<DistributionReg> distributors = distributionRepo.findBySalesmanid(salespersonId);
            return new ResponseEntity<>(distributors, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
   ///////////////////////list of distributor and profession by salesmanid//////////////////////////////////
    @GetMapping("/distrubutior/salesman/profession/{salesmanId}/{profession}")
    public ResponseEntity<List<DistributionReg>> getDistributorsBySalesmanIdAndProfession(
            @PathVariable Long salesmanId, @PathVariable String profession) {
        try {
            List<DistributionReg> distributors = distributionRepo.findBySalesmanidAndProfession(salesmanId, profession);
            return new ResponseEntity<>(distributors, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
///////////////////////////////////////////////////////////////////////change password for client///////////////////////////////

@PutMapping("/changePasswordsalesman/{pan}")
public ResponseEntity<String> changePassword(@PathVariable String pan, @RequestBody ChangePassword request) {
logger.info("Received a request to change password for user with ID: {}", pan);

try {
if (taskoService.isOldPasswordCorrect2(pan, request.getOldPassword())) {
taskoService.updatePassword2(pan, request.getNewPassword());
logger.info("Password changed successfully for user with ID: {}", pan);
return ResponseEntity.ok("Password changed successfully.");
} else {
logger.warn("Attempt to change password failed for user with ID {}. Invalid old password.", pan);
return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid old password.");
}
} catch (Exception e) {
logger.error("An error occurred while changing the password for user with ID {}: {}", pan, e.getMessage());
throw e;
}
}
//////////////////////////////////////list of all salemanger of  master admin/////////////////////////////////////////////////////////////
@GetMapping("/listofsalesmanager/formasteradmin")
public ResponseEntity<List<Salesman_Register>> getAllSalesmen() {
    try {
        List<Salesman_Register> salesmen = salesmanRegisterRepository.findAll();
        return new ResponseEntity<>(salesmen, HttpStatus.OK);
    } catch (Exception e) {
        // Log the exception or handle it accordingly
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
       
/////////////////////////////////////////////sales manager dashboard api/////////////////////////////////////

/////////////////////////salesmanger api for list of all ca under salemanger and distributor ////////////////////////////////
@GetMapping("/forsalesmanager/combined-lists-CA")
public ResponseEntity<Map<String, List<User_RegistrationsForm>>> getCombinedLists(
        @RequestParam String pan, @RequestParam Long id) {
    try {
        List<User_RegistrationsForm> salespersonList = taskoRepository.findBySalespersonId(pan);
        List<User_RegistrationsForm> dissalespersonList = taskoRepository.findByDissalespersonId(id);
        Map<String, List<User_RegistrationsForm>> result = new HashMap<>();
        result.put("salesmanager_personal_ca", salespersonList);
        result.put("Salesmanager_distributor_ca", dissalespersonList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


////////////////////////////salesmanager api for list of all ca under salesmanager and distributor by profession///////////////
@GetMapping("/salesmanager/users/displayBothLists/by-profession")
public ResponseEntity<Map<String, List<User_RegistrationsForm>>> displayBothLists(
        @RequestParam String profession,
        @RequestParam String pan,
        @RequestParam Long id) {
    try {
        List<User_RegistrationsForm> list1 = taskoRepository.findByprofessionAndSalespersonId(profession, pan);
        List<User_RegistrationsForm> list2 = taskoRepository.findByprofessionAndDissalespersonId(profession, id);

        Map<String, List<User_RegistrationsForm>> result = new HashMap<>();
        result.put("salesmanager_personal_ca", list1);
        result.put("Salesmanager_distributor_ca", list2);

        return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (Exception e) {
        // Handle exceptions here
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}












                  //////////////////////////////////renewal/////////////////////////////////////////////////
//////////////////////////////list of renveal////////////////////////////////////////////////////////////////////

@GetMapping("/Renewal/today/salesmanger/{pan}/{id}")
public ResponseEntity<Map<String, List<Subscription_Userdata>>> getTodayRenewalsales(@PathVariable String pan, @PathVariable Long id) {
    try {
        // Get the current date and time
        Date currentDate = new Date();
        
        // Fetch data from repositories
        List<Subscription_Userdata> list1 = subscritpion_userdataRepository.listOfsalestodaysubscription(currentDate, pan);
        List<Subscription_Userdata> list2 = subscritpion_userdataRepository.listOfdissalestodaysubscription(currentDate, id);
        
        // Prepare response map
        Map<String, List<Subscription_Userdata>> result = new HashMap<>();
        result.put("salesmanager_personal_ca", list1);
        result.put("Salesmanager_distributor_ca", list2);
        
        return new ResponseEntity<>(result, HttpStatus.OK);

    } catch (Exception e) {
        // Handle exceptions and return a meaningful response
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "An error occurred while processing the request.");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}




/////////////////////////////////////////////////////////
@GetMapping("/Renewal/tomorrow/salesmanager/{pan}/{id}")
public ResponseEntity<Map<String, List<Subscription_Userdata>>> getTomorrowRenewalSales(@PathVariable String pan, @PathVariable Long id) {
    try {
        // Get the current date and time
        Date currentDate = new Date();

        // Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Add 1 day
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // Get the new date after adding 1 day
        Date newDate = calendar.getTime();

        // Fetch data from repositories
        List<Subscription_Userdata> list1 = subscritpion_userdataRepository.listOfsalesyestardaysubscription(newDate, pan);
        List<Subscription_Userdata> list2 = subscritpion_userdataRepository.listOfdissalesyestardaysubscription(newDate, id);

        // Prepare response map
        Map<String, List<Subscription_Userdata>> result = new HashMap<>();
        result.put("salesmanager_personal_ca", list1);
        result.put("Salesmanager_distributor_ca", list2);

        return new ResponseEntity<>(result, HttpStatus.OK);

    } catch (Exception e) {
        // Handle exceptions and return a meaningful response
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "An error occurred while processing the request.");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


/////////////////////////////////////////////////////////////////////////
@GetMapping("/Renewal/week/salesmanager/{pan}/{id}")
public ResponseEntity<Map<String, List<Subscription_Userdata>>> getWeekRenewal(@PathVariable String pan,@PathVariable Long id) {
    try {
        // Get the current date and time
        Date currentDate = new Date();

        // Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // Subtract 7 days from the current date
        calendar.add(Calendar.DAY_OF_MONTH,7);

        // Get the start date for the last 7 days
        Date startDate = calendar.getTime();

        // Call the method with the startDate
        List<Subscription_Userdata> list1 = subscritpion_userdataRepository.listofsalesweeksubscription(startDate, pan, currentDate);
        List<Subscription_Userdata> list2 = subscritpion_userdataRepository.listofdissalesweeksubscription(startDate, id, currentDate);

        // Prepare response map
        Map<String, List<Subscription_Userdata>> result = new HashMap<>();
        result.put("salesmanager_personal_ca", list1);
        result.put("Salesmanager_distributor_ca", list2);

        return new ResponseEntity<>(result, HttpStatus.OK);

    } catch (Exception e) {
        // Handle exceptions and return a meaningful response
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "An error occurred while processing the request.");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}





////////////////////////////////////////////////////////////////////////////////////////////////////////
@GetMapping("/Renewal/month/salesmanger/{pan}/{id}")
public ResponseEntity<Map<String, List<Subscription_Userdata>>> getMonthRenewal(@PathVariable String pan, @PathVariable Long id) {
    try {
LocalDate currentDate1 = LocalDate.now();
int year = currentDate1.getYear();
int month = currentDate1.getMonthValue();
Date currentDate = new Date();
List<Subscription_Userdata> list1 = subscritpion_userdataRepository.listOfsalesTotalProfessionInCurrentMonthreneval(year, month,pan,currentDate);
List<Subscription_Userdata> list2 = subscritpion_userdataRepository.listOfdissalesTotalProfessionInCurrentMonthreneval(year, month,id,currentDate);
Map<String, List<Subscription_Userdata>> result = new HashMap<>();
result.put("salesmanager_personal_ca", list1);
result.put("Salesmanager_distributor_ca", list2);
return new ResponseEntity<>(result, HttpStatus.OK);

} catch (Exception e) {
    // Handle exceptions and return a meaningful response
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", "An error occurred while processing the request.");
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
}
    
}

/////////////////////////////////////////////////////////////////////////////////
@GetMapping("/Renewal/threemonth/salesmanager/{pan}/{id}")
public ResponseEntity<Map<String, List<Subscription_Userdata>>> getthreemonthRenewalsales(@PathVariable String pan,@PathVariable Long id) {
try {
Date currentDate = new Date();
Calendar calendar1 = Calendar.getInstance();
LocalDate currentDate2 = LocalDate.now();
calendar1.setTime(new Date()); // Set your initial date here
//Add 90 days
calendar1.add(Calendar.DAY_OF_MONTH, 90);
//Get the new date
Date newDate1 = calendar1.getTime();
List<Subscription_Userdata> list1 = subscritpion_userdataRepository.listOfsalesTotalProfessionInthreemonthreneval(newDate1,pan,currentDate);
List<Subscription_Userdata> list2 = subscritpion_userdataRepository.listOfdissalesTotalProfessionInthreemonthreneval(newDate1,id,currentDate);
Map<String, List<Subscription_Userdata>> result = new HashMap<>();
result.put("salesmanager_personal_ca", list1);
result.put("Salesmanager_distributor_ca", list2);
return new ResponseEntity<>(result, HttpStatus.OK);

} catch (Exception e) {
    // Handle exceptions and return a meaningful response
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", "An error occurred while processing the request.");
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
}
}


////////////////////////////////////////////////////////////////
@GetMapping("/Renewal/sixmonth/salesmanager/{pan}/{id}")
public ResponseEntity<Map<String, List<Subscription_Userdata>>>  getsixmonthRenewal(@PathVariable String pan,@PathVariable Long id) {
try 
{
	Calendar calendar2 = Calendar.getInstance();
LocalDate currentDate3 = LocalDate.now();

calendar2.setTime(new Date()); // Set your initial date here
//Add 90 days
calendar2.add(Calendar.DAY_OF_MONTH, 180);
Date newDate2 = calendar2.getTime();
Date currentDate = new Date();
List<Subscription_Userdata> list1 = subscritpion_userdataRepository.listOfsalesTotalProfessionInsixmonthreneval(newDate2,pan,currentDate);
List<Subscription_Userdata> list2 = subscritpion_userdataRepository.listOfdissalesTotalProfessionInsixmonthreneval(newDate2,id,currentDate);
Map<String, List<Subscription_Userdata>> result = new HashMap<>();
result.put("salesmanager_personal_ca", list1);
result.put("Salesmanager_distributor_ca", list2);
return new ResponseEntity<>(result, HttpStatus.OK);

} catch (Exception e) {
    // Handle exceptions and return a meaningful response
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", "An error occurred while processing the request.");
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
}
}
/////////////////////////////////////////////////Renewal of subscription///////////////////////////////////////////

@GetMapping("/salesmanager/countreneval/{pan}/{id}")
public ResponseEntity<List<SubscriptionCount>> countrenewal(@PathVariable String pan,@PathVariable Long id) {
try {
System.out.println("hii");
Long count12 = subscritpion_userdataRepository.countOfsalestodayRenewal(new Date(),pan);
Long count13 = subscritpion_userdataRepository.countOfdissalestodayRenewal(new Date(),id);
Long count1=count12+count13;
Date currentDate = new Date();

//Create a Calendar instance and set it to the current date
Calendar calendar = Calendar.getInstance();
calendar.setTime(currentDate);

//Subtract 1 day
calendar.add(Calendar.DAY_OF_MONTH, +1);

//Get the new date after subtracting 1 day
Date newDate = calendar.getTime();
Long count14 = subscritpion_userdataRepository.countOfsalesyestardayRenewal(newDate,pan);
Long count15 = subscritpion_userdataRepository.countOfdissalesyestardayRenewal(newDate,id);
Long count2=count14+count15;
calendar.setTime(new Date());

//Subtract 7 days from the current date
calendar.add(Calendar.DAY_OF_MONTH, +7);

//Get the start date for the last 7 days
Date startDate = calendar.getTime();

//Call the method with the startDate

Long count16 = subscritpion_userdataRepository.countOfsalesTotalProfessionWithinLast7Daysreneval(startDate,pan,currentDate);
Long count17= subscritpion_userdataRepository.countOfdissalesTotalProfessionWithinLast7Daysreneval(startDate,id,currentDate);
Long count3=count16+count17;
System.out.println(currentDate);

LocalDate currentDate1 = LocalDate.now();
int year = currentDate1.getYear();
int month = currentDate1.getMonthValue();

Long count18 = subscritpion_userdataRepository.countOfsalesTotalProfessionInCurrentMonthreneval(year, month,pan,currentDate);
Long count19= subscritpion_userdataRepository.countOfdissalesTotalProfessionInCurrentMonthreneval(year, month,id,currentDate);
Long count4=count18+count19;
Calendar calendar1 = Calendar.getInstance();
LocalDate currentDate2 = LocalDate.now();

calendar1.setTime(new Date()); // Set your initial date here

//Add 90 days
calendar1.add(Calendar.DAY_OF_MONTH, 90);

//Get the new date
Date newDate1 = calendar1.getTime();
Long count20 = subscritpion_userdataRepository.countOfsalesTotalProfessionInthreemonthreneval(newDate1,pan,currentDate);
Long count21 = subscritpion_userdataRepository.countOfdissalesTotalProfessionInthreemonthreneval(newDate1,id,currentDate);
Long count5=count20+count21;
Calendar calendar2 = Calendar.getInstance();
LocalDate currentDate3 = LocalDate.now();

calendar2.setTime(new Date()); // Set your initial date here
//Add 90 days
calendar2.add(Calendar.DAY_OF_MONTH, 180);
Date newDate2 = calendar2.getTime();
Long count22 = subscritpion_userdataRepository.countOfsalesTotalProfessionInsixmonthreneval(newDate2,pan,currentDate);
Long count23= subscritpion_userdataRepository.countOfdissalesTotalProfessionInsixmonthreneval(newDate2,id,currentDate);
Long count6=count22+count23;

//Set default values to 0 if a count is not available
count1 = (count1 != null) ? count1 : 0L;
count2 = (count2 != null) ? count2 : 0L;
count3 = (count3 != null) ? count3 : 0L;
count4 = (count4 != null) ? count4 : 0L;
count5 = (count5 != null) ? count5 : 0L;
count6 = (count6 != null) ? count6 : 0L;
System.out.println(count1);
System.out.println(count2);
System.out.println(count3);
System.out.println(count4);
System.out.println(count5);
System.out.println(count6);


List<SubscriptionCount> professionCounts = new ArrayList<>();
professionCounts.add(new SubscriptionCount("Today", count1));
professionCounts.add(new SubscriptionCount("Tomorrow", count2));
professionCounts.add(new SubscriptionCount("Weak", count3));
professionCounts.add(new SubscriptionCount("Month", count4));
professionCounts.add(new SubscriptionCount("3 Month", count5));
professionCounts.add(new SubscriptionCount("6 Month", count6));

return ResponseEntity.ok(professionCounts);
} catch (Exception e) {
//Handle the exception here, you can log it or return an error response
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
}
}



////////////////////////////////////////////////count by profession//////////////////////////////////
@GetMapping("/salesmanager/user/countbyprofession/{pan}/{id}")
public ResponseEntity<List<ProfessionCount>> countprofessionby(@PathVariable String pan,@PathVariable Long id) {
	try {
		Long count1 = taskoRepository.countsalesOfTotalprofession1(pan)+ taskoRepository.countdissalesOfTotalprofession1(id);
		Long count2 = taskoRepository.countsalesOfTotalprofession2(pan)+taskoRepository.countdissalesOfTotalprofession2(id);
		Long count3 = taskoRepository.countsalesOfTotalprofession3(pan)+taskoRepository.countdissalesOfTotalprofession3(id);
		Long count4 = taskoRepository.countsalesOfTotalprofession4(pan)+taskoRepository.countdissalesOfTotalprofession4(id);
		Long count5 = taskoRepository.countsalesOfTotalprofession5(pan)+taskoRepository.countdissalesOfTotalprofession5(id);
		Long count6 = taskoRepository.countsalesOfTotalprofession6(pan)+taskoRepository.countdissalesOfTotalprofession6(id);
		Long count7 = taskoRepository.countsalesOfTotalprofession7(pan)+taskoRepository.countdissalesOfTotalprofession7(id);

		// Set default values to 0 if a count is not available
		count1 = (count1 != null) ? count1 : 0L;
		count2 = (count2 != null) ? count2 : 0L;
		count3 = (count3 != null) ? count3 : 0L;
		count4 = (count4 != null) ? count4 : 0L;
		count5 = (count5 != null) ? count5 : 0L;
		count6 = (count6 != null) ? count6 : 0L;
		count7 = (count7 != null) ? count7 : 0L;

		List<ProfessionCount> professionCounts = new ArrayList<>();
		professionCounts.add(new ProfessionCount("Chartered Accountant", count1));
		professionCounts.add(new ProfessionCount("Tax Consultant", count2));
		professionCounts.add(new ProfessionCount("Tax Return Preparer(TRP)", count3));
		professionCounts.add(new ProfessionCount("Accountant", count4));
		professionCounts.add(new ProfessionCount("Certified Consultant", count5));
		professionCounts.add(new ProfessionCount("Advocate", count6));
		professionCounts.add(new ProfessionCount("Other", count7));

		return ResponseEntity.ok(professionCounts);
	} catch (Exception e) {
		// Handle the exception here, you can log it or return an error response
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}



////////////////////////////////////////////total sale of salesmanager////////////////////////////
@GetMapping("/salesmanager/earning/{pan}/{id}")
public Map<String, Long> getsalesmanagersalesCount(@PathVariable String pan, @PathVariable Long id) {
    try {
       Long count1 = subscriptionpack_historyRepository.calculateSumOfRenewalPricesales(pan);
    	Long count2=subscriptionpack_historyRepository.calculateSumOfRenewalPricedissales(id);
       count1 = (count1 != null) ? count1 : 0L;
       count2 = (count2 != null) ? count2 : 0L;
       Long totalincomeCount=count1+count2;
         LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        // Get the previous month
        LocalDate previousMonth = currentDate.minusMonths(1);

        int previousYear = previousMonth.getYear();
        int previousMonthValue = previousMonth.getMonthValue();

        // Corrected parameter: use previousMonthValue directly
       Long count3 = subscriptionpack_historyRepository.calculateSumOfRenewalPricelastmonthsales(previousYear, previousMonthValue, pan);
       Long count4= subscriptionpack_historyRepository.calculateSumOfRenewalPricelastmonthdissales(previousYear, previousMonthValue, id);
       count3 = (count3 != null) ? count3 : 0L;
       count4 = (count4 != null) ? count4 : 0L;
       Long previousMonthincomeCount=count3+count4; 
        int currentYear = currentDate.getYear();
        int currentMonthValue = currentDate.getMonthValue();

          Long count5 = subscriptionpack_historyRepository.calculateSumOfRenewalPricelastmonthsales(currentYear, currentMonthValue, pan);
          Long count6= subscriptionpack_historyRepository.calculateSumOfRenewalPricelastmonthdissales(currentYear, currentMonthValue, id);
          count5 = (count5 != null) ? count5 : 0L;
          count6 = (count6 != null) ? count6 : 0L;
         Long currentMonthIncomeCount=count5+count6;
        // Set default values to 0 if a count is not available
        totalincomeCount = (totalincomeCount != null) ? totalincomeCount : 0L;
        previousMonthincomeCount = (previousMonthincomeCount != null) ? previousMonthincomeCount : 0L;
        currentMonthIncomeCount = (currentMonthIncomeCount != null) ? currentMonthIncomeCount : 0L;

        Map<String, Long> responseMap = new HashMap<>();
        responseMap.put("Total Earning", totalincomeCount);
        responseMap.put("Last Month", previousMonthincomeCount);
        responseMap.put("Current Month", currentMonthIncomeCount);

        return responseMap;
    } catch (Exception e) {
        // You can log the exception here if needed
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching getsalesmanagersalesCount.", e);
    }
}

////////////////////////////////////////////total sale of salesmanager////////////////////////////
@GetMapping("/salesmanager/group/personal/earning/{pan}/{id}")
public Map<String, Long> getsalesmanagersalesCountindividual(@PathVariable String pan, @PathVariable Long id) {
try {
Long count1 = subscriptionpack_historyRepository.calculateSumOfRenewalPricesales(pan);
Long count2=subscriptionpack_historyRepository.calculateSumOfRenewalPricedissales(id);
count1 = (count1 != null) ? count1 : 0L;
count2 = (count2 != null) ? count2 : 0L;
Long totalincomeCount=count1+count2;
LocalDate currentDate = LocalDate.now();
int year = currentDate.getYear();
int month = currentDate.getMonthValue();

// Get the previous month
LocalDate previousMonth = currentDate.minusMonths(1);

int previousYear = previousMonth.getYear();
int previousMonthValue = previousMonth.getMonthValue();

// Corrected parameter: use previousMonthValue directly
Long count3 = subscriptionpack_historyRepository.calculateSumOfRenewalPricesales(pan);
count3 = (count3 != null) ? count3 : 0L;
Long previousMonthincomeCount=count3; 
int currentYear = currentDate.getYear();
int currentMonthValue = currentDate.getMonthValue();

Long count5 = subscriptionpack_historyRepository.calculateSumOfRenewalPricedissales(id);

count5 = (count5 != null) ? count5 : 0L;
Long currentMonthIncomeCount=count5;
// Set default values to 0 if a count is not available
totalincomeCount = (totalincomeCount != null) ? totalincomeCount : 0L;
previousMonthincomeCount = (previousMonthincomeCount != null) ? previousMonthincomeCount : 0L;
currentMonthIncomeCount = (currentMonthIncomeCount != null) ? currentMonthIncomeCount : 0L;

Map<String, Long> responseMap = new HashMap<>();
responseMap.put("Total Earning", totalincomeCount);
responseMap.put("individual", previousMonthincomeCount);
responseMap.put("group", currentMonthIncomeCount);

return responseMap;
} catch (Exception e) {
// You can log the exception here if needed
throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching getsalesmanagersalesCount.", e);
}
}
/////////////////////////////////////////////////earning api for salesman///////////////////////////////////////////
@GetMapping("/slaemanager/earnig/table/{pan}/{id}")
public ResponseEntity<List<SubscriptionCount>> salesmanagerearning(
        @PathVariable String pan,
        @PathVariable Long id,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date weekstartdate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date weekcurrentdate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date monthstartdate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date monthcurrentdate) {

    try {
        System.out.println("hii");
        Long count1 = subscriptionpack_historyRepository.sumoftodayearning(new Date(), pan, id);
        Date currentDate = new Date();
        Long count3 = subscriptionpack_historyRepository.sumofweekearning(weekstartdate, weekcurrentdate, pan, id);
        LocalDate currentDate1 = LocalDate.now();
        int year = currentDate1.getYear();
        int month = currentDate1.getMonthValue();
        Long count4 = subscriptionpack_historyRepository.sumofmonthearning(year, month,pan, id);
        Long count5 = subscriptionpack_historyRepository.sumofquarterearning(monthstartdate, monthcurrentdate, pan, id);
        LocalDate currentDate5 = LocalDate.now();
        int currentYear = currentDate5.getYear();
        Long count6 = subscriptionpack_historyRepository.sumofcurrentyearearning(currentYear, pan, id);

        // Set default values to 0 if a count is not available
        count1 = (count1 != null) ? count1 : 0L;
        count3 = (count3 != null) ? count3 : 0L;
        count4 = (count4 != null) ? count4 : 0L;
        count5 = (count5 != null) ? count5 : 0L;
        count6 = (count6 != null) ? count6 : 0L;

        System.out.println(count1);
        System.out.println(count3);
        System.out.println(count4);
        System.out.println(count5);
        System.out.println(count6);

        List<SubscriptionCount> professionCounts = new ArrayList<>();
        professionCounts.add(new SubscriptionCount("Today", count1));
        professionCounts.add(new SubscriptionCount("Weak", count3));
        professionCounts.add(new SubscriptionCount("Month", count4));
        professionCounts.add(new SubscriptionCount("Quarter", count5)); // Update the comment accordingly
        professionCounts.add(new SubscriptionCount("Year", count6));

        return ResponseEntity.ok(professionCounts);
    } catch (Exception e) {
        // Handle the exception here, you can log it or return an error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
/////////////////////////////////////////////////quater wise earning///////////////////////////////////////////
@GetMapping("/quarter-earning/{pan}/{id}")
public ResponseEntity<Long> sumOfQuarterEarning(
        @PathVariable String pan,
        @PathVariable Long id,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date monthstartdate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date monthcurrentdate) {
    try {
        Long count5 = subscriptionpack_historyRepository.sumofquarterearning(monthstartdate, monthcurrentdate, pan, id);
        // Set default value to 0 if the count is not available
        count5 = (count5 != null) ? count5 : 0L;
        return ResponseEntity.ok(count5);
    } catch (Exception e) {
        // Handle the exception here, you can log it or return an error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}


//@Scheduled(cron = "1 0 0 * * ?") // Run every day at 12:00 PM
@PostMapping("/records")
public void printHi() {
  System.out.println("hii");
  LocalDate date1 = LocalDate.now();
  LocalDate originalDate = date1.withDayOfMonth(1);

  // Adding three months
  LocalDate newDate = originalDate.plusMonths(3).minusDays(1);

  // Printing the original and new dates
  System.out.println("Original Date: " + originalDate.format(DateTimeFormatter.ISO_DATE));
  System.out.println("New Date: " + newDate.format(DateTimeFormatter.ISO_DATE));

  // Quarter 1
  LocalDate quarter1StartDate = originalDate;
  LocalDate quarter1EndDate = newDate;
  System.out.println("Quarter 1 Start Date: " + quarter1StartDate.format(DateTimeFormatter.ISO_DATE));
  System.out.println("Quarter 1 End Date: " + quarter1EndDate.format(DateTimeFormatter.ISO_DATE));

  // Quarter 2
  LocalDate quarter2StartDate = quarter1EndDate.plusDays(1);
  LocalDate quarter2EndDate = quarter2StartDate.plusMonths(3).minusDays(1);
  System.out.println("Quarter 2 Start Date: " + quarter2StartDate.format(DateTimeFormatter.ISO_DATE));
  System.out.println("Quarter 2 End Date: " + quarter2EndDate.format(DateTimeFormatter.ISO_DATE));

  // Quarter 3
  LocalDate quarter3StartDate = quarter2EndDate.plusDays(1);
  LocalDate quarter3EndDate = quarter3StartDate.plusMonths(3).minusDays(1);
  System.out.println("Quarter 3 Start Date: " + quarter3StartDate.format(DateTimeFormatter.ISO_DATE));
  System.out.println("Quarter 3 End Date: " + quarter3EndDate.format(DateTimeFormatter.ISO_DATE));

  // Quarter 4
  LocalDate quarter4StartDate = quarter3EndDate.plusDays(1);
  LocalDate quarter4EndDate = quarter4StartDate.plusMonths(3).minusDays(1);
  System.out.println("Quarter 4 Start Date: " + quarter4StartDate.format(DateTimeFormatter.ISO_DATE));
  System.out.println("Quarter 4 End Date: " + quarter4EndDate.format(DateTimeFormatter.ISO_DATE));
  
  
  LocalDate[] startdate = {quarter1StartDate,quarter2StartDate,quarter3StartDate,quarter4StartDate};
  LocalDate[] enddate = {quarter1EndDate,quarter2EndDate,quarter3EndDate,quarter4EndDate};
  String[] quarter= {"q1","q2","q3","q4"};
  
  for (int i = 0; i < startdate.length; i++) {
      System.out.println("Quarter: " + quarter[i]);
      System.out.println("Start Date: " + startdate[i]);
      System.out.println("End Date: " + enddate[i]);
      System.out.println();
  }
}



//////////////////////////////add salesman target///////////////////////////////////////////////
@PostMapping("/save/targer/salesmanager")
public ResponseEntity<String> saveSaleManagerTarget(@RequestBody Salemanager_target salemanager_target)throws Exception {
    try {
    	Optional<Salemanager_target>data=salemanager_targetRepository.findBySalesmanidAndYear(salemanager_target.getSalesmanid(), salemanager_target.getYear());
        if(data.isPresent())
        {
        	throw new UserAlreadyExist("target Already Exist");
        }
        LocalDate date1 = LocalDate.now();
        LocalDate originalDate = date1.withDayOfMonth(1);

        // Adding three months
        LocalDate newDate = originalDate.plusMonths(3).minusDays(1);

        // Quarter 1
        LocalDate quarter1StartDate = originalDate;
        LocalDate quarter1EndDate = newDate;

        LocalDate quarter2StartDate = quarter1EndDate.plusDays(1);
        LocalDate quarter2EndDate = quarter2StartDate.plusMonths(3).minusDays(1);
        
        // Quarter 3
        LocalDate quarter3StartDate = quarter2EndDate.plusDays(1);
        LocalDate quarter3EndDate = quarter3StartDate.plusMonths(3).minusDays(1);
        
        // Quarter 4
        LocalDate quarter4StartDate = quarter3EndDate.plusDays(1);
        LocalDate quarter4EndDate = quarter4StartDate.plusMonths(3).minusDays(1);
        
        
        
        LocalDate[] startdate = {quarter1StartDate,quarter2StartDate,quarter3StartDate,quarter4StartDate};
        LocalDate[] enddate = {quarter1EndDate,quarter2EndDate,quarter3EndDate,quarter4EndDate};
        String[] quarter= {"q1","q2","q3","q4"};
        
        for (int i = 0; i < startdate.length; i++) {
        	
        	Salesmanager_incentive_records_data incentivedata = new Salesmanager_incentive_records_data();
        	incentivedata.setSalesmanid(salemanager_target.getSalesmanid());
        	incentivedata.setFixdate(salemanager_target.getFixdate());
        	incentivedata.setPan(salemanager_target.getPan());
        	incentivedata.setTargrtamount(salemanager_target.getAmount());
        	incentivedata.setYear(salemanager_target.getYear());
        	incentivedata.setStartdate(startdate[i]);
        	incentivedata.setEnddate(enddate[i]);
        	incentivedata.setQuerter(quarter[i]);
        	salesmanager_incentive_records_dataRepository.save(incentivedata);
        }    
    	Salemanager_target savedTarget = salemanager_targetRepository.save(salemanager_target);
        return ResponseEntity.ok("Record saved successfully");
    } catch (Exception e) {
        // Handle the exception here, you can log it or return an error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save the record");
    }
}







//////////////////////

///@Scheduled(cron = "1 * * * * ?")
@Scheduled(cron = "0 0 0 1 * ?")
@PostMapping("/records/salesmanager")
public void printHi1() {
	//LocalDate b = LocalDate.parse("2025-01-01");
	//List<Salesmanager_incentive_records_data> data = salesmanager_incentive_records_dataRepository.findByStartdate(b);
	List<Salesmanager_incentive_records_data> data = salesmanager_incentive_records_dataRepository.findByStartdate(LocalDate.now());
	List<Salesmanager_incentive_records_data> data2 = salesmanager_incentive_records_dataRepository.findByEnddateAndQuerter(LocalDate.now().minusDays(1),"q4");
	//List<Salesmanager_incentive_records_data> data2 = salesmanager_incentive_records_dataRepository.findByEnddateAndQuerter(b.minusDays(1),"q4");
	System.out.println("hii2");
	for (Salesmanager_incentive_records_data fourthquater : data2) {
		System.out.println("Record ID: " + fourthquater.getId());
	    System.out.println("Start Date: " + fourthquater.getStartdate());
	 // Assuming firstQuater is a LocalDate
	    LocalDate firstQuaterStartDate = fourthquater.getStartdate();
	    // Convert LocalDate to Date
	    Date startDateAsDate = Date.from(firstQuaterStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	    LocalDate firstQuaterEndDate = fourthquater.getEnddate();
	    // Convert LocalDate to Date
	    Date endDateAsDate = Date.from(firstQuaterEndDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	     Long count5 = subscriptionpack_historyRepository.sumofquarterearning(endDateAsDate, startDateAsDate, fourthquater.getPan(), fourthquater.getSalesmanid());
	     count5 = (count5 != null) ? count5 : 0L;
	     Long quartertarget=fourthquater.getTargrtamount()/4;
	     Long halfquartertarget=fourthquater.getTargrtamount()/2;
	     LocalDate halfyearStartDate = fourthquater.getStartdate().minusMonths(3);
	     LocalDate fullyearStartDate = fourthquater.getStartdate().minusMonths(9);
	     Date fullstartDateAsDate = Date.from(fullyearStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	     Date halfstartDateAsDate = Date.from(halfyearStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	     Long sales_of_half_year  = subscriptionpack_historyRepository.sumofquarterearning(endDateAsDate, halfstartDateAsDate, fourthquater.getPan(), fourthquater.getSalesmanid());
	     Long sales_of_full_year  = subscriptionpack_historyRepository.sumofquarterearning(endDateAsDate, fullstartDateAsDate, fourthquater.getPan(), fourthquater.getSalesmanid());
	     if(sales_of_full_year>=fourthquater.getTargrtamount())
	     {
	    	 Long one_per_amount= (sales_of_full_year*1)/100;
	    	 fourthquater.setIncetiveamount(one_per_amount);
	    	 salesmanager_incentive_records_dataRepository.save(fourthquater);
	     }
	     else
	     {
	    	 Long one_per_amount=0L;
	    	 fourthquater.setIncetiveamount(one_per_amount);
	    	 salesmanager_incentive_records_dataRepository.save(fourthquater);
	     }
	     if(halfquartertarget<=sales_of_half_year)
	     {
	    	 Long two_per_amount= (sales_of_half_year*2)/100;
	    	 fourthquater.setIncetiveamount(two_per_amount+fourthquater.getIncetiveamount());
	    	 salesmanager_incentive_records_dataRepository.save(fourthquater);
	     }
	     else
	     {
	    	 Long two_per_amount=0L;
	    	 fourthquater.setIncetiveamount(two_per_amount+fourthquater.getIncetiveamount());
	    	 salesmanager_incentive_records_dataRepository.save(fourthquater);
	     }
	    
	     Long differece_target_sales=count5-quartertarget;
	     if(differece_target_sales > 0)
	     {
	    	 double incetativepercentag = ((double) differece_target_sales / quartertarget) * 100;
	    	if(incetativepercentag < 10)
	    	{
	    		Long incetative= ( count5*5)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else if(incetativepercentag >= 10 &&incetativepercentag < 20)
	    	{
	    		Long incetative= (count5*6)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else if(incetativepercentag >= 20 &&incetativepercentag < 30)
	    	{
	    		Long incetative= (count5*7)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else if(incetativepercentag >= 30 &&incetativepercentag < 40)
	    	{
	    		Long incetative= (count5*8)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else if(incetativepercentag >= 40 &&incetativepercentag < 50)
	    	{
	    		Long incetative= (count5*9)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else if(incetativepercentag >= 50 &&incetativepercentag < 60)
	    	{
	    		Long incetative= (count5*10)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else if(incetativepercentag >= 60 &&incetativepercentag < 70)
	    	{
	    		Long incetative= (count5*11)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else if(incetativepercentag >= 70 &&incetativepercentag < 80)
	    	{
	    		Long incetative= (count5*11)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else if(incetativepercentag >= 80 &&incetativepercentag < 90)
	    	{
	    		Long incetative= (count5*12)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else if(incetativepercentag >= 90 &&incetativepercentag < 100)
	    	{
	    		Long incetative= (count5*13)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else if(incetativepercentag >= 100 &&incetativepercentag < 110)
	    	{
	    		Long incetative= (count5*14)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else if(incetativepercentag >= 110 &&incetativepercentag < 120)
	    	{
	    		Long incetative= (count5*14)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	    	else
	    	{
	    		Long incetative= (count5*15)/100;
	    		fourthquater.setIncetiveamount(incetative+fourthquater.getIncetiveamount());
		    	salesmanager_incentive_records_dataRepository.save(fourthquater);
	    	}
	     }
	     else
	     {
	    	 fourthquater.setIncetiveamount(0L+fourthquater.getIncetiveamount());
	    	 salesmanager_incentive_records_dataRepository.save(fourthquater);
	     }

		
	}
	System.out.println("hii2");
	for (Salesmanager_incentive_records_data record : data) {
		if(record.getQuerter().equals("q2"))
		{
			LocalDate a= record.getStartdate().minusMonths(3);
			List<Salesmanager_incentive_records_data> firstquaterdate = salesmanager_incentive_records_dataRepository.findByStartdate(a);
		
			for (Salesmanager_incentive_records_data firstquater : firstquaterdate) {
			    // Access and do something with each record
			    System.out.println("Record ID: " + firstquater.getId());
			    System.out.println("Start Date: " + firstquater.getStartdate());
			 // Assuming firstQuater is a LocalDate
			    LocalDate firstQuaterStartDate = firstquater.getStartdate();
			    // Convert LocalDate to Date
			    Date startDateAsDate = Date.from(firstQuaterStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			    LocalDate firstQuaterEndDate = firstquater.getEnddate();
			    // Convert LocalDate to Date
			    Date endDateAsDate = Date.from(firstQuaterEndDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			     Long count5 = subscriptionpack_historyRepository.sumofquarterearning(endDateAsDate, startDateAsDate, firstquater.getPan(), firstquater.getSalesmanid());
			     count5 = (count5 != null) ? count5 : 0L;
			     Long quartertarget=firstquater.getTargrtamount()/4;
			     System.out.println("count"+count5);
			     Long differece_target_sales=count5-quartertarget;
			     System.out.println("differece_target_sales"+differece_target_sales);
			     if(differece_target_sales > 0)
			     {
			    	 System.out.println("incetativepercentag" + differece_target_sales);
			    	 System.out.println("incetativepercentag" + quartertarget);	    	 
//			         Long incetativepercentag = (differece_target_sales/quartertarget)*100;
			    	 double incetativepercentag = ((double) differece_target_sales / quartertarget) * 100;
			    	System.out.println("incetativepercentg" + incetativepercentag);
			    	if(incetativepercentag < 10)
			    	{
			    		Long incetative= (count5*5)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else if(incetativepercentag >= 10 &&incetativepercentag < 20)
			    	{
			    		Long incetative= (count5*6)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else if(incetativepercentag >= 20 &&incetativepercentag < 30)
			    	{
			    		Long incetative= (count5*7)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else if(incetativepercentag >= 30 &&incetativepercentag < 40)
			    	{
			    		Long incetative= (count5*8)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else if(incetativepercentag >= 40 &&incetativepercentag < 50)
			    	{
			    		Long incetative= (count5*9)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else if(incetativepercentag >= 50 &&incetativepercentag < 60)
			    	{
			    		Long incetative= (count5*10)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else if(incetativepercentag >= 60 &&incetativepercentag < 70)
			    	{
			    		Long incetative= (count5*11)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else if(incetativepercentag >= 70 &&incetativepercentag < 80)
			    	{
			    		Long incetative= (count5*11)/100;
			    		firstquater.setIncetiveamount(incetative);
			    		System.out.println("incetative"+incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else if(incetativepercentag >= 80 &&incetativepercentag < 90)
			    	{
			    		Long incetative= (count5*12)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else if(incetativepercentag >= 90 &&incetativepercentag < 100)
			    	{
			    		Long incetative= (count5*13)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else if(incetativepercentag >= 100 &&incetativepercentag < 110)
			    	{
			    		Long incetative= (count5*14)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else if(incetativepercentag >= 110 &&incetativepercentag < 120)
			    	{
			    		Long incetative= (count5*14)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			    	else
			    	{
			    		Long incetative= (count5*15)/100;
			    		firstquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(firstquater);
			    	}
			     }
			     else
			     {
			    	 firstquater.setIncetiveamount(0L);
			    	 salesmanager_incentive_records_dataRepository.save(firstquater);
			     }
			     
			}
			
		}
		if(record.getQuerter().equals("q3"))
		{
			System.out.println("quater3");
			LocalDate a= record.getStartdate().minusMonths(3);
			List<Salesmanager_incentive_records_data> secondquaterdate = salesmanager_incentive_records_dataRepository.findByStartdate(a);	
			for (Salesmanager_incentive_records_data secondquater : secondquaterdate) {
				System.out.println("Record ID: " + secondquater.getId());
			    System.out.println("Start Date: " + secondquater.getStartdate());
			 // Assuming firstQuater is a LocalDate
			    LocalDate firstQuaterStartDate = secondquater.getStartdate();
			    // Convert LocalDate to Date
			    Date startDateAsDate = Date.from(firstQuaterStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			    LocalDate firstQuaterEndDate = secondquater.getEnddate();
			    // Convert LocalDate to Date
			    Date endDateAsDate = Date.from(firstQuaterEndDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			     Long count5 = subscriptionpack_historyRepository.sumofquarterearning(endDateAsDate, startDateAsDate, secondquater.getPan(), secondquater.getSalesmanid());
			     count5 = (count5 != null) ? count5 : 0L;
			     Long quartertarget=secondquater.getTargrtamount()/4;
			     Long halfquartertarget=secondquater.getTargrtamount()/2;
			     LocalDate halfyearStartDate = secondquater.getStartdate().minusMonths(3);
			     Date halfstartDateAsDate = Date.from(halfyearStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			     Long sales_of_half_year  = subscriptionpack_historyRepository.sumofquarterearning(endDateAsDate, halfstartDateAsDate, secondquater.getPan(), secondquater.getSalesmanid());
			     if(halfquartertarget<sales_of_half_year)
			     {
			    	 Long two_per_amount= (sales_of_half_year*2)/100;
			    	 secondquater.setIncetiveamount(two_per_amount);
			    	 salesmanager_incentive_records_dataRepository.save(secondquater);
			     }
			     else
			     {
			    	 Long two_per_amount=0L;
			    	 secondquater.setIncetiveamount(two_per_amount);
			    	 salesmanager_incentive_records_dataRepository.save(secondquater);
			     }
			    System.out.println("quater 2 incetative");
			     Long differece_target_sales=count5-quartertarget;
			     if(differece_target_sales > 0)
			     {
			    	 System.out.println("quater 2 1 incetative");
			    	 double incetativepercentag = ((double) differece_target_sales / quartertarget) * 100;
			    	if(incetativepercentag < 10)
			    	{
			    		Long incetative= ( count5*5)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else if(incetativepercentag >= 10 &&incetativepercentag < 20)
			    	{
			    		Long incetative= (count5*6)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else if(incetativepercentag >= 20 &&incetativepercentag < 30)
			    	{
			    		Long incetative= (count5*7)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else if(incetativepercentag >= 30 &&incetativepercentag < 40)
			    	{
			    		Long incetative= (count5*8)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else if(incetativepercentag >= 40 &&incetativepercentag < 50)
			    	{
			    		Long incetative= (count5*9)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else if(incetativepercentag >= 50 &&incetativepercentag < 60)
			    	{
			    		Long incetative= (count5*10)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else if(incetativepercentag >= 60 &&incetativepercentag < 70)
			    	{
			    		Long incetative= (count5*11)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else if(incetativepercentag >= 70 &&incetativepercentag < 80)
			    	{
			    		Long incetative= (count5*11)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else if(incetativepercentag >= 80 &&incetativepercentag < 90)
			    	{
			    		Long incetative= (count5*12)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else if(incetativepercentag >= 90 &&incetativepercentag < 100)
			    	{
			    		Long incetative= (count5*13)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else if(incetativepercentag >= 100 &&incetativepercentag < 110)
			    	{
			    		Long incetative= (count5*14)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else if(incetativepercentag >= 110 &&incetativepercentag < 120)
			    	{
			    		Long incetative= (count5*14)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			    	else
			    	{
			    		Long incetative= (count5*15)/100;
			    		secondquater.setIncetiveamount(incetative+secondquater.getIncetiveamount());
				    	salesmanager_incentive_records_dataRepository.save(secondquater);
			    	}
			     }
			     else
			     {
			    	 secondquater.setIncetiveamount(0L+secondquater.getIncetiveamount());
			    	 salesmanager_incentive_records_dataRepository.save(secondquater);
			     }

				
			}
		}
		if(record.getQuerter().equals("q4"))
		{
			LocalDate a= record.getStartdate().minusMonths(3);
			List<Salesmanager_incentive_records_data>  thirdquaterdate = salesmanager_incentive_records_dataRepository.findByStartdate(a);
		
			for (Salesmanager_incentive_records_data thirdquater : thirdquaterdate) {
			    // Access and do something with each record
			    System.out.println("Record ID: " + thirdquater.getId());
			    System.out.println("Start Date: " + thirdquater.getStartdate());
			 // Assuming firstQuater is a LocalDate
			    LocalDate firstQuaterStartDate = thirdquater.getStartdate();
			    // Convert LocalDate to Date
			    Date startDateAsDate = Date.from(firstQuaterStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			    LocalDate firstQuaterEndDate = thirdquater.getEnddate();
			    // Convert LocalDate to Date
			    Date endDateAsDate = Date.from(firstQuaterEndDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			     Long count5 = subscriptionpack_historyRepository.sumofquarterearning(endDateAsDate, startDateAsDate, thirdquater.getPan(), thirdquater.getSalesmanid());
			     count5 = (count5 != null) ? count5 : 0L;
			     Long quartertarget=thirdquater.getTargrtamount()/4;
			     Long differece_target_sales=count5-quartertarget;
			     if(differece_target_sales > 0)
			     {
			    	 double incetativepercentag = ((double) differece_target_sales / quartertarget) * 100;
			    	System.out.println("incetativepercentag"+incetativepercentag);
			    	if(incetativepercentag < 10)
			    	{
			    		Long incetative= ( count5*5)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else if(incetativepercentag >= 10 &&incetativepercentag < 20)
			    	{
			    		Long incetative= (count5*6)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else if(incetativepercentag >= 20 &&incetativepercentag < 30)
			    	{
			    		Long incetative= (count5*7)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else if(incetativepercentag >= 30 &&incetativepercentag < 40)
			    	{
			    		Long incetative= (count5*8)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else if(incetativepercentag >= 40 &&incetativepercentag < 50)
			    	{
			    		Long incetative= (count5*9)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else if(incetativepercentag >= 50 &&incetativepercentag < 60)
			    	{
			    		Long incetative= (count5*10)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else if(incetativepercentag >= 60 &&incetativepercentag < 70)
			    	{
			    		Long incetative= (count5*11)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else if(incetativepercentag >= 70 &&incetativepercentag < 80)
			    	{
			    		Long incetative= (count5*11)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else if(incetativepercentag >= 80 &&incetativepercentag < 90)
			    	{
			    		Long incetative= (count5*12)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else if(incetativepercentag >= 90 &&incetativepercentag < 100)
			    	{
			    		Long incetative= (count5*13)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else if(incetativepercentag >= 100 &&incetativepercentag < 110)
			    	{
			    		Long incetative= (count5*14)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else if(incetativepercentag >= 110 &&incetativepercentag < 120)
			    	{
			    		Long incetative= (count5*14)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			    	else
			    	{
			    		Long incetative= (count5*15)/100;
			    		thirdquater.setIncetiveamount(incetative);
				    	salesmanager_incentive_records_dataRepository.save(thirdquater);
			    	}
			     }
			     else
			     {
			    	 thirdquater.setIncetiveamount(0L);
			    	 salesmanager_incentive_records_dataRepository.save(thirdquater);
			     }
			     
			}
			
		}
	    // Perform operations on each record
	    System.out.println("Record: " + record);
	    // Add your logic here
	}
	// Long count5 = subscriptionpack_historyRepository.sumofquarterearning(monthstartdate, monthcurrentdate, pan, id);
     // Set default value to 0 if the count is not available
    // count5 = (count5 != null) ? count5 : 0L;
System.out.println("hii");
}


    //////////////////////////////////update sales manager/////////////////////////////////////////
@PutMapping("/update/targer/salesmanager")
public ResponseEntity<String> updateRecord(@RequestBody Salemanager_target updatedRecord) {
	 Optional<Salemanager_target> existingRecord = salemanager_targetRepository.findBySalesmanidAndYear(updatedRecord.getSalesmanid(), updatedRecord.getYear());

     if (existingRecord.isPresent()) {
         // Update the existing record with the new data
         Salemanager_target existingTarget = existingRecord.get();
         existingTarget.setPan(updatedRecord.getPan());
         existingTarget.setDate(updatedRecord.getDate());
         existingTarget.setFixdate(updatedRecord.getFixdate());
         existingTarget.setAmount(updatedRecord.getAmount());

         // Save the updated record
         Salemanager_target savedRecord = salemanager_targetRepository.save(existingTarget);
         return ResponseEntity.ok("Record updated successfully");
     } else {
         return ResponseEntity.notFound().build();
     }
 }
	



/////////////////////////////////get a salesmanager  record by id///////////////////////////////////////////////
//@GetMapping("/get/target/getBySalesmanIdAndYear")
//public ResponseEntity<?> getRecordBySalesmanIdAndYear(@RequestParam Long salesmanId, @RequestParam String year) {
//    // Find the record based on salesmanId and year
//	System.out.println("hii");
//    Optional<Salemanager_target> existingRecord = salemanager_targetRepository.findBySalesmanidAndYear(salesmanId, year);
//
//    if (existingRecord.isPresent()) {
//    	Salemanager_target data =existingRecord.get();
//        return ResponseEntity.ok(data);
//    } else {
//        return ResponseEntity.notFound().build();
//    }
//}
///////////////////////////////get a salesmanager  record by id///////////////////////////////////////////////
@GetMapping("/get/target/getBySalesmanIdAndYear")
public ResponseEntity<List<Salemanager_target>> getRecordBySalesmanIdAndYear(@RequestParam Long salesmanId) {
    // Find the records based on salesmanId
    System.out.println("hii");
    List<Salemanager_target> existingRecords = salemanager_targetRepository.findBySalesmanid(salesmanId);

    if (!existingRecords.isEmpty()) {
        return ResponseEntity.ok(existingRecords);
    } else {
        return ResponseEntity.notFound().build();
    }
}
////////////////////////////get all saleman targt/////////////////////////////////////////////////////
@GetMapping("/getAll/salesmanager/target")
public ResponseEntity<List<Salemanager_target>> getAllRecords() {
    List<Salemanager_target> allRecords = salemanager_targetRepository.findAll();

    if (!allRecords.isEmpty()) {
        return ResponseEntity.ok(allRecords);
    } else {
        return ResponseEntity.notFound().build();
    }
}


/////////////////////////////change status of sales manager////////////////////////////////////////
@PutMapping("/changestatus/salesmanager/{pan}/{value}")
public ResponseEntity<String> ChangeStatusbyadmin(@PathVariable String pan, @PathVariable Boolean value) {
	try {
		Optional<Salesman_Register> user = salesmanRegisterRepository.findByPan(pan);
		if (user.isPresent()) {
			Salesman_Register userinfo = user.get();
			if (value == false) {
				System.out.println(userinfo.getEmail());
				String to = userinfo.getEmail();
				String subject = "appication stop forcefully";
				String text = "Please contact administration if the authorizations for your account have been stopped by the system.";
				taskoService.sendEmailwithattachmentforcestop(to, subject, text);
			}
			if (value == true) {
				String to = userinfo.getEmail();
				String subject = "appication start regularly";
				String text = "We are pleased to inform you that the service has now been restored and is fully operational.Thank You.";
				taskoService.sendEmailwithattachmentforcestop(to, subject, text);
			}
			userinfo.setStatus(value);
			salesmanRegisterRepository.save(userinfo);
			return ResponseEntity.ok("status change succesfully");
		} else {
			// Handle the case when no subscription is found for the provided PAN
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("status not changed");
		}
	} catch (Exception e) {
		// Handle other exceptions here, you can log them or return an error response
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}


/////////////////////////////////////////////deactivate a salesmanager/////////////////////////////////////////////
@DeleteMapping("/salesmanager/delete/{pan}")
public ResponseEntity<String> deleteByPan(@PathVariable String pan) {
    // Find the record by PAN
    Optional<Salesman_Register> user = salesmanRegisterRepository.findByPan(pan);  
    // Check if the record exists
    if (user.isPresent()) {
    	Salesman_Register data=user.get();	 
      ///////////////////////////update a user registration table//////////////////////////////////////////////
    	List<User_RegistrationsForm> userrecords = taskoRepository.findByDissalespersonId(data.getId());
    	if(!userrecords.isEmpty())
    	{
    	 for (User_RegistrationsForm existingRecord : userrecords) {
             // Perform the update on each record based on your requirements
             // For example, assuming you have a setter method for a field named "fieldName":
    		User_diacivate_salesmanager record = new User_diacivate_salesmanager();
    		record.setUserid(existingRecord.getRegId());
    		record.setPan(existingRecord.getPan());
    		record.setDissalespersonId(existingRecord.getDissalespersonId());
    		record.setSalespersonId(existingRecord.getSalespersonId());
    		user_diacivate_salesmanagerRepository.save(record);
             // Update other fields as needed
          }

         // Save the updated records back to the database
    	}
    	
    	List<User_RegistrationsForm> userrecords1 = taskoRepository.findBySalespersonId(data.getPan());
    	if(!userrecords1.isEmpty())
    	{
    	 for (User_RegistrationsForm existingRecord : userrecords1) {
             // Perform the update on each record based on your requirements
             // For example, assuming you have a setter method for a field named "fieldName":
    		User_diacivate_salesmanager record = new User_diacivate_salesmanager();
    		record.setUserid(existingRecord.getRegId());
    		record.setPan(existingRecord.getPan());
    		record.setDissalespersonId(existingRecord.getDissalespersonId());
    		record.setSalespersonId(existingRecord.getSalespersonId());
    		user_diacivate_salesmanagerRepository.save(record);
             // Update other fields as needed
          }

         // Save the updated records back to the database
    	}
    	List<Subscriptionpack_history> subsriptiondata = subscriptionpack_historyRepository.findBySalespersonId(data.getPan());
    	if(!subsriptiondata.isEmpty())
    	{
    	for (Subscriptionpack_history existingRecord : subsriptiondata) {
    		Subscriptionpack_histroy_disactivate_salesmanager subdata = new Subscriptionpack_histroy_disactivate_salesmanager();
    		subdata.setPan(existingRecord.getPan());
    		subdata.setDisrefrenceId(existingRecord.getDisrefrenceId());
    		subdata.setAcessclient(existingRecord.getAcessclient());
    		subdata.setSubendtdate(existingRecord.getSubendtdate());
    		subdata.setSubscriptionprice(existingRecord.getSubscriptionprice());
    		subdata.setDissalespersonId(existingRecord.getDissalespersonId());
    		subdata.setSalespersonId(existingRecord.getSalespersonId());
    		subdata.setSubstartdate(existingRecord.getSubstartdate());
    		subdata.setSubstartdatebyadmin(existingRecord.getSubstartdatebyadmin());
    		subdata.setSubscriptiontype(existingRecord.getSubscriptiontype());
    		subdata.setUserid(existingRecord.getUserid());
    		subscriptionpack_histroy_disactivate_salesmanagerRepository.save(subdata);
         }
         // Save the updated records back to the database
    	}
    	
    	List<Subscriptionpack_history> subsriptiondata1 = subscriptionpack_historyRepository.findByDissalespersonId(data.getId());
    	if (!subsriptiondata1.isEmpty()) {
    	    for (Subscriptionpack_history existingRecord : subsriptiondata1) {
    	    	Subscriptionpack_histroy_disactivate_salesmanager subdata = new Subscriptionpack_histroy_disactivate_salesmanager();
        		subdata.setPan(existingRecord.getPan());
        		subdata.setDisrefrenceId(existingRecord.getDisrefrenceId());
        		subdata.setAcessclient(existingRecord.getAcessclient());
        		subdata.setSubendtdate(existingRecord.getSubendtdate());
        		subdata.setSubscriptionprice(existingRecord.getSubscriptionprice());
        		subdata.setDissalespersonId(existingRecord.getDissalespersonId());
        		subdata.setSalespersonId(existingRecord.getSalespersonId());
        		subdata.setSubstartdate(existingRecord.getSubstartdate());
        		subdata.setSubstartdatebyadmin(existingRecord.getSubstartdatebyadmin());
        		subdata.setSubscriptiontype(existingRecord.getSubscriptiontype());
        		subdata.setUserid(existingRecord.getUserid());
        		subscriptionpack_histroy_disactivate_salesmanagerRepository.save(subdata);
    	    }

    	}
    	
    	
    	List<Salemanager_target> salesmantargetdata = salemanager_targetRepository.findBySalesmanid(data.getId());

        if (!salesmantargetdata.isEmpty()) {
        	for (Salemanager_target existingRecord : salesmantargetdata) {
        		Salesmanager_target_disactivate_salesmanager salesmantarget = new Salesmanager_target_disactivate_salesmanager();
        		salesmantarget.setPan(existingRecord.getPan());
        		salesmantarget.setDate(existingRecord.getDate());
        		salesmantarget.setAmount(existingRecord.getAmount());
        		salesmantarget.setSalesmanid(existingRecord.getSalesmanid());
        		salesmantarget.setYear(existingRecord.getYear());
        		salesmanager_target_disactivate_salesmanagerRepository.save(salesmantarget);
        	}
        }
    	
        List<DistributionReg> distrubutordata= distributionRepo.findBySalesmanid(data.getId());
    	if(!distrubutordata.isEmpty())
    	{
    	for (DistributionReg existingRecord : distrubutordata) {
    		Distrubutor_register_disactivate_salesmanager distributordata= new Distrubutor_register_disactivate_salesmanager(); 
    		distributordata.setPan(existingRecord.getPan());
    		distributordata.setSalesmanagerpan(existingRecord.getSalesmanagerpan());
    		distributordata.setSalesmanid(existingRecord.getSalesmanid());
    		distrubutor_register_disactivate_salesmanagerRepository.save(distributordata);
         }
    	}
    	List<Total_paid_payment_salesmanger_totalentry> total_paid_salelsmanager_entry = total_paid_payment_salesmanger_totalentryRepository.findByPan(data.getPan());
       if(!total_paid_salelsmanager_entry.isEmpty())
    	{
    	   for (Total_paid_payment_salesmanger_totalentry entry : total_paid_salelsmanager_entry) {
    		   Total_salesmanger_totalentry_deactivate_salesmanager totalentry_paid_salesmanager= new Total_salesmanger_totalentry_deactivate_salesmanager();
    		   totalentry_paid_salesmanager.setPan(entry.getPan());
    		   totalentry_paid_salesmanager.setPaymentdate(entry.getPaymentdate());
    		   totalentry_paid_salesmanager.setTotalpaid(entry.getTotalpaid());
    		   total_paid_payment_salesmanger_totalentry_deactivate_salesmanagerRepository.save(totalentry_paid_salesmanager);
    		   
    	   }
    	}
    	Optional<Total_paid_payment_salesmanger> totalpaid_salesmanager =total_paid_payment_salesmangerRepository.findByPan(data.getPan());
    	if(!totalpaid_salesmanager.isEmpty())
    	{
    		Total_paid_payment_salesmanger_deactivate_salesmanager totalpaid = new  Total_paid_payment_salesmanger_deactivate_salesmanager();
    		totalpaid.setPan(totalpaid_salesmanager.get().getPan());
    		totalpaid.setTotalpaid(totalpaid_salesmanager.get().getTotalpaid());
    		total_paid_payment_salesmanger_deactivate_salesmanagerRepository.save(totalpaid);   		
    	}
    	if(user.isPresent())
    	{
    		Salesmanager_register_disactivate_salesmanager salesmanagerregister= new Salesmanager_register_disactivate_salesmanager();
    		salesmanagerregister.setAddress(data.getAddress());
    		salesmanagerregister.setDatebirth(data.getDatebirth());
    		salesmanagerregister.setEmail(data.getEmail());
    		salesmanagerregister.setMobile(data.getMobile());
    		salesmanagerregister.setName(data.getName());
    		salesmanagerregister.setPan(data.getPan());
    		salesmanagerregister.setPassword(data.getPin_code());
    		salesmanagerregister.setPin_code(data.getPin_code());
    		salesmanagerregister.setProfession(data.getProfession());
    		salesmanagerregister.setRegistrationdate(data.getRegistrationdate());
    		salesmanagerregister.setState(data.getState());
    		salesmanagerregister.setStatus(false);
    		salesmanagerregister.setTelephone(data.getTelephone());
    		salesmanagerregister.setWhatsApp_Link(data.getWhatsApp_Link());	  
    		salesmanagerregister.setSalesmanid(data.getId());
    		salesmanager_register_disactivate_salesmanagerRepository.save(salesmanagerregister);
    	}
    	
    	 List<Salesmanager_incentive_records_data> salesincentive= salesmanager_incentive_records_dataRepository.findByPan(data.getPan());
    	 if(!salesincentive.isEmpty())
    	 {
    		
    		 for (Salesmanager_incentive_records_data incentiveData : salesincentive) {
    			    Salesmanager_incentive_records_data_deactivate_salesmanager salesinsetive = new Salesmanager_incentive_records_data_deactivate_salesmanager();
    		
    			    salesinsetive.setPan(incentiveData.getPan());
    			    salesinsetive.setIncetiveamount(incentiveData.getIncetiveamount());
    			    salesinsetive.setQuerter(incentiveData.getQuerter());
    			    salesinsetive.setEnddate(incentiveData.getEnddate());
    			    salesinsetive.setFixdate(incentiveData.getFixdate());
    			    salesinsetive.setTargrtamount(incentiveData.getTargrtamount());
    			    salesinsetive.setYear(incentiveData.getYear());
    			    salesinsetive.setSalesmanid(incentiveData.getSalesmanid());
    			    salesinsetive.setEnddate(incentiveData.getEnddate());
    			  //  salesinsetive.setFixdate(incentiveData.get)
    			    salesmanager_incentive_records_data_deactivate_salesmanagerRepository.save(salesinsetive);
    			}
    	 }
    
    	 List<Salesmanager_incentive_records_data> deletesalesincentive = salesmanager_incentive_records_dataRepository.findByPan(data.getPan());

    	 if (!deletesalesincentive.isEmpty()) {   	   
    	     salesmanager_incentive_records_dataRepository.deleteAll(deletesalesincentive);	    
    	 }
    	 
    	 List<Total_paid_payment_salesmanger_totalentry> deletetotal_paid_salelsmanager_entry = total_paid_payment_salesmanger_totalentryRepository.findByPan(data.getPan());   	
    	 if(!deletetotal_paid_salelsmanager_entry.isEmpty())
    	 {
    		 total_paid_payment_salesmanger_totalentryRepository.deleteAll(deletetotal_paid_salelsmanager_entry);
    	 }
    	 Optional<Total_paid_payment_salesmanger> delete_totalpaid_salesmanager = total_paid_payment_salesmangerRepository.findByPan(data.getPan());

    	 if (delete_totalpaid_salesmanager.isPresent()) {
    	     total_paid_payment_salesmangerRepository.delete(delete_totalpaid_salesmanager.get());
    	 }
    	List<User_RegistrationsForm> existingRecords = taskoRepository.findByDissalespersonId(data.getId());
    	if(!existingRecords.isEmpty())
    	{
    	for (User_RegistrationsForm existingRecord : existingRecords) {
             // Perform the update on each record based on your requirements
             // For example, assuming you have a setter method for a field named "fieldName":
        	 Long a=(long) 1;
             existingRecord.setDissalespersonId(a);
             // Update other fields as needed
         }

         // Save the updated records back to the database
         taskoRepository.saveAll(existingRecords);
    	}
    	List<User_RegistrationsForm> existingRecords1 = taskoRepository.findBySalespersonId(data.getPan());
    	if(!existingRecords1.isEmpty())
    	{
    	for (User_RegistrationsForm existingRecord : existingRecords1) {
             // Perform the update on each record based on your requirements
             // For example, assuming you have a setter method for a field named "fieldName":
             existingRecord.setSalespersonId(null);
             // Update other fields as needed
         }

         // Save the updated records back to the database
         taskoRepository.saveAll(existingRecords1);
    	}
    	List<Subscriptionpack_history> existingRecords3 = subscriptionpack_historyRepository.findBySalespersonId(data.getPan());
    	if(!existingRecords3.isEmpty())
    	{
    	for (Subscriptionpack_history existingRecord : existingRecords3) {
             // Perform the update on each record based on your requirements
             // For example, assuming you have a setter method for a field named "fieldName":
        	 
             existingRecord.setSalespersonId(null);
             // Update other fields as needed
         }
         // Save the updated records back to the database
    	subscriptionpack_historyRepository.saveAll(existingRecords3);
    	}
    	
    	List<Subscriptionpack_history> existingRecords4 = subscriptionpack_historyRepository.findByDissalespersonId(data.getId());

    	if (!existingRecords4.isEmpty()) {
    	    for (Subscriptionpack_history existingRecord : existingRecords4) {
    	        // Perform the update on each record based on your requirements
    	        // For example, assuming you have a setter method for a field named "DissalespersonId":
    	        Long a = (long) 1;
    	        existingRecord.setDissalespersonId(a);
    	        // Update other fields as needed
    	    }

    	    // Save the updated records back to the database
    	    subscriptionpack_historyRepository.saveAll(existingRecords4);
    	}
    	List<Subscription_Userdata> existingRecords7 = subscritpion_userdataRepository.findBySalespersonId(data.getPan());
    	if(!existingRecords7.isEmpty())
    	{
    		
    	for (Subscription_Userdata existingRecord : existingRecords7) {
             // Perform the update on each record based on your requirements
             // For example, assuming you have a setter method for a field named "fieldName":
        	 System.out.println(existingRecord.getSalespersonId());
             existingRecord.setSalespersonId(null);
             // Update other fields as needed
         }
         // Save the updated records back to the database
    	subscritpion_userdataRepository.saveAll(existingRecords7);
    	}
    	
    	List<Subscription_Userdata> existingRecords8 = subscritpion_userdataRepository.findByDissalespersonId(data.getId());

    	if (!existingRecords8.isEmpty()) {
    	    for (Subscription_Userdata existingRecord : existingRecords8) {
    	        // Perform the update on each record based on your requirements
    	        // For example, assuming you have a setter method for a field named "DissalespersonId":
    	        Long a = (long) 1;
    	        existingRecord.setDissalespersonId(a);
    	        // Update other fields as needed
    	    }

    	    // Save the updated records back to the database
    	    subscritpion_userdataRepository.saveAll(existingRecords8);
    	}
    	
    	
    	 List<Salemanager_target> existingRecords5 = salemanager_targetRepository.findBySalesmanid(data.getId());

         if (!existingRecords5.isEmpty()) {
             // Delete all records for the given salesmanId
        	 salemanager_targetRepository.deleteAll(existingRecords5);
         }
     	List<DistributionReg> existingRecords6= distributionRepo.findBySalesmanid(data.getId());
    	if(!existingRecords6.isEmpty())
    	{
    	for (DistributionReg existingRecord : existingRecords6) {
             // Perform the update on each record based on your requirements
             // For example, assuming you have a setter method for a field named "fieldName":
        	 
             existingRecord.setSalesmanagerpan("XXXXX1111X");
             Long a = (long) 1;
             existingRecord.setSalesmanid(a);
             // Update other fields as needed
         }
    	
    	
         // Save the updated records back to the database
    	distributionRepo.saveAll(existingRecords6);
    	}
        // Delete the record
        salesmanRegisterRepository.delete(user.get());
        return ResponseEntity.ok("Record deleted successfully");
    } else {
        // Return a response indicating that the record was not found
        return ResponseEntity.status(404).body("Record not found");
    }
}




/////////////////////////////////////////////deactivate a salesmanager/////////////////////////////////////////////
@DeleteMapping("/salesmanager/permentdelete/{pan}")
public ResponseEntity<String> deletesalesmanByPan(@PathVariable String pan) {
// Find the record by PAN
Optional<Salesmanager_register_disactivate_salesmanager> user = salesmanager_register_disactivate_salesmanagerRepository.findByPan(pan);  
// Check if the record exists
if (user.isPresent()) {
	Salesmanager_register_disactivate_salesmanager data=user.get();	 

List<User_diacivate_salesmanager> existingRecords = user_diacivate_salesmanagerRepository.findByDissalespersonId(data.getSalesmanid());
if(!existingRecords.isEmpty())
{
	user_diacivate_salesmanagerRepository.deleteAll(existingRecords);
     // Update other fields as needed
}
List<User_diacivate_salesmanager> existingRecords1 = user_diacivate_salesmanagerRepository.findBySalespersonId(data.getPan());
if(!existingRecords1.isEmpty())
{
	user_diacivate_salesmanagerRepository.deleteAll(existingRecords1);
}
List< Subscriptionpack_histroy_disactivate_salesmanager> existingRecords3 = subscriptionpack_histroy_disactivate_salesmanagerRepository.findBySalespersonId(data.getPan());
if(!existingRecords3.isEmpty())
{ 
	subscriptionpack_histroy_disactivate_salesmanagerRepository.deleteAll(existingRecords3);
}

List<Subscriptionpack_histroy_disactivate_salesmanager> existingRecords4 = subscriptionpack_histroy_disactivate_salesmanagerRepository.findByDissalespersonId(data.getSalesmanid());

if (!existingRecords4.isEmpty()) {
    // Save the updated records back to the database
	subscriptionpack_histroy_disactivate_salesmanagerRepository.deleteAll(existingRecords4);
}

 List<Salesmanager_target_disactivate_salesmanager> existingRecords5 = salesmanager_target_disactivate_salesmanagerRepository.findBySalesmanid(data.getSalesmanid());

 if (!existingRecords5.isEmpty()) {
     // Delete all records for the given salesmanId
	 salesmanager_target_disactivate_salesmanagerRepository.deleteAll(existingRecords5);
 }
 List<Salesmanager_incentive_records_data_deactivate_salesmanager> existingRecords7= salesmanager_incentive_records_data_deactivate_salesmanagerRepository.findBySalesmanid(data.getSalesmanid());
 if(!existingRecords7.isEmpty())
 {
	 salesmanager_incentive_records_data_deactivate_salesmanagerRepository.deleteAll();
 }
	List<Distrubutor_register_disactivate_salesmanager> existingRecords6= distrubutor_register_disactivate_salesmanagerRepository.findBySalesmanid(data.getSalesmanid());
if(!existingRecords6.isEmpty())
{
 // Save the updated records back to the database
	distrubutor_register_disactivate_salesmanagerRepository.deleteAll(existingRecords6);
}
Optional<Total_paid_payment_salesmanger_deactivate_salesmanager> existingRecords8 =total_paid_payment_salesmanger_deactivate_salesmanagerRepository.findByPan(data.getPan());
if(!existingRecords8.isEmpty())
{
	total_paid_payment_salesmanger_deactivate_salesmanagerRepository.delete(existingRecords8.get());
}
List<Total_salesmanger_totalentry_deactivate_salesmanager> existingRecords9 = total_paid_payment_salesmanger_totalentry_deactivate_salesmanagerRepository.findByPan(data.getPan());
if(!existingRecords9.isEmpty())
{
	total_paid_payment_salesmanger_totalentry_deactivate_salesmanagerRepository.deleteAll();
}
// Delete the record
salesmanager_register_disactivate_salesmanagerRepository.delete(user.get());
return ResponseEntity.ok("Record deleted successfully");
} else {
// Return a response indicating that the record was not found
return ResponseEntity.status(404).body("Record not found");
}
}




/////////////////////////////all data  of disactive salesmanager//////////////////////////////////////////////////
@GetMapping("/all/disactivate/salesmanager/data/{pan}")
public ResponseEntity<Map<String, Object>> getTotalPaidPaymentsList1(@PathVariable String pan)
{
	Optional<Salesmanager_register_disactivate_salesmanager> user = salesmanager_register_disactivate_salesmanagerRepository.findByPan(pan);  
	List<User_RegistrationsForm> userList = new ArrayList<>();
	List<User_RegistrationsForm> userList1 = new ArrayList<>();
	List<DistributionReg> userList2 = new ArrayList<>();
	List<DistributionReg> userList3 = new ArrayList<>();

	if(user.isPresent())
	{
		Salesmanager_register_disactivate_salesmanager data=user.get();	
		List<User_diacivate_salesmanager> existingRecords = user_diacivate_salesmanagerRepository.findByDissalespersonId(data.getSalesmanid());
		if(!existingRecords.isEmpty()){
			  for (User_diacivate_salesmanager record : existingRecords) {
			        System.out.println("User Diacivate Sales Manager Details:");
			        System.out.println("ID: " + record.getId());
			        System.out.println("Salesperson ID: " + record.getSalespersonId());
			        Optional<User_RegistrationsForm> users=taskoRepository.findByPan(record.getPan());
			        users.get().setPassword(null);
			        users.get().setOtp(null);
			        users.ifPresent(userList::add);
			        // Add other relevant fields as needed
			    }
		}
		List<User_diacivate_salesmanager> existingRecords1 = user_diacivate_salesmanagerRepository.findBySalespersonId(data.getPan());
		if(!existingRecords1.isEmpty()){
			  for (User_diacivate_salesmanager record : existingRecords1) {
			        System.out.println("User Diacivate Sales Manager Details:");
			        System.out.println("ID: " + record.getId());
			        System.out.println("Salesperson ID: " + record.getSalespersonId());
			        Optional<User_RegistrationsForm> users=taskoRepository.findByPan(record.getPan());
			        users.get().setPassword(null);
			        users.get().setOtp(null);
			        users.ifPresent(userList1::add);
			        // Add other relevant fields as needed
			    }
		}
		List<Distrubutor_register_disactivate_salesmanager> existingRecords6= distrubutor_register_disactivate_salesmanagerRepository.findBySalesmanid(data.getSalesmanid());
		if(!existingRecords.isEmpty()){
		 for (Distrubutor_register_disactivate_salesmanager record : existingRecords6) {
	        System.out.println("ID: " + record.getId());
	        System.out.println("Salesperson ID: " + record.getSalesmanagerpan());
	        Optional<DistributionReg> distributor = distributionRepo.findByPan(record.getPan());
	        distributor.get().setPassword(null);
	        distributor.get().setPassword(null);
	        distributor.ifPresent(userList2::add);
	        // Add other relevant fields as needed
	    }
		}
	
		List< Subscriptionpack_histroy_disactivate_salesmanager> existingRecords3 = subscriptionpack_histroy_disactivate_salesmanagerRepository.findBySalespersonId(data.getPan());
	
		List<Subscriptionpack_histroy_disactivate_salesmanager> existingRecords4 = subscriptionpack_histroy_disactivate_salesmanagerRepository.findByDissalespersonId(data.getSalesmanid());

		List<Salesmanager_target_disactivate_salesmanager> existingRecords5 = salesmanager_target_disactivate_salesmanagerRepository.findBySalesmanid(data.getSalesmanid());
	
		List<Salesmanager_incentive_records_data_deactivate_salesmanager> existingRecords7= salesmanager_incentive_records_data_deactivate_salesmanagerRepository.findBySalesmanid(data.getSalesmanid());
//		if (!existingRecords7.isEmpty()) {
//		    System.out.println("List of Sales Manager Incentive Records:");
//
//		    for (Salesmanager_incentive_records_data_deactivate_salesmanager record : existingRecords7) {
//		        System.out.println("ID: " + record.getId());
//		        System.out.println("Salesperson ID: " + record.getSalesmanid());
//		        Optional<Salesmanager_register_disactivate_salesmanager>records = salesmanager_incentive_records_data_deactivate_salesmanagerRepository.findByPan(record.getPan());
//		        
//		        // Add other relevant fields as needed
//		    }
//		}
		Optional<Total_paid_payment_salesmanger_deactivate_salesmanager> existingRecords8 =total_paid_payment_salesmanger_deactivate_salesmanagerRepository.findByPan(data.getPan());
		List<Total_salesmanger_totalentry_deactivate_salesmanager> existingRecords9 = total_paid_payment_salesmanger_totalentry_deactivate_salesmanagerRepository.findByPan(data.getPan());
		Map<String, Object> responseMap = new HashMap<>();
     responseMap.put("sales_manager",data);
     responseMap.put("salesman_manager_distributor_ca", userList);
     responseMap.put("salesman_manager_ca", userList1);
     responseMap.put("distributor", userList2);    
//     responseMap.put("sales_manager_personal_ca",existingRecords3);
//     responseMap.put("sales_manager_distributor_ca", existingRecords4);
     responseMap.put("saleas_manager_all_target_peryear", existingRecords5);
     responseMap.put("saleas_manager_all_incentive_records", existingRecords7);  
     responseMap.put("saleas_manager_total_paid", existingRecords8); 
     responseMap.put("saleas_manager_total_paid_total_entry", existingRecords9);
     return ResponseEntity.ok(responseMap);
    } else {
        return ResponseEntity.notFound().build();
    }
     
}

///////////////////////////list of all deactivate all sales manager////////////////////////////////////////////////////////////////////////////////////////////////////
@GetMapping("/salesmanager/deactivate/allsalesmanager")
public ResponseEntity<List<Salesmanager_register_disactivate_salesmanager>> getAllSalesManagers() {
    try {
        List<Salesmanager_register_disactivate_salesmanager> salesManagers = salesmanager_register_disactivate_salesmanagerRepository.findAll();
        return new ResponseEntity<>(salesManagers, HttpStatus.OK);
    } catch (Exception e) {
        // Log the exception for troubleshooting
        logger.error("Error occurred while fetching sales managers", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

///////////////////////////////////deactivate salesmaner find by id//////////////////////////////////////////////////////////

@GetMapping("/salesmanager/deactivate/findbyid/{id}")
public ResponseEntity<Salesmanager_register_disactivate_salesmanager> getSalesManagerById(@PathVariable Long id) {
    try {
        Optional<Salesmanager_register_disactivate_salesmanager> salesManager = salesmanager_register_disactivate_salesmanagerRepository.findById(id);

        if (salesManager.isPresent()) {
            return new ResponseEntity<>(salesManager.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    } catch (Exception e) {
        // Log the exception for troubleshooting
        logger.error("Error occurred while fetching sales manager with ID: " + id, e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}





//@PostMapping("/save/salesmanager/paidamount")
//public ResponseEntity<Total_paid_payment_salesmanger> saveTotalPaidPayment(@RequestBody Total_paid_payment_salesmanger totalPaidPayment) {
//    try {
//        Optional<Total_paid_payment_salesmanger> records = total_paid_payment_salesmangerRepository.findByPan(totalPaidPayment.getPan());
//
//        if (records.isEmpty()) {
//            Total_paid_payment_salesmanger savedPayment = total_paid_payment_salesmangerRepository.save(totalPaidPayment);
//
//            Total_paid_payment_salesmanger_totalentry data = new Total_paid_payment_salesmanger_totalentry();
//            data.setPan(totalPaidPayment.getPan());
//            data.setPaymentdate(new Date());
//            data.setTotalpaid(totalPaidPayment.getTotalpaid());
//            total_paid_payment_salesmanger_totalentryRepository.save(data);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
//        }
//
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
//}


@PutMapping("/save/updated/slasemanager/amount")
public ResponseEntity<Total_paid_payment_salesmanger> saveOrUpdateTotalPaidPayment(@RequestBody Total_paid_payment_salesmanger totalPaidPayment) {
    try {
        Optional<Total_paid_payment_salesmanger> existingRecord = total_paid_payment_salesmangerRepository.findByPan(totalPaidPayment.getPan());

        if (existingRecord.isPresent()) {
            // Update the existing record
            Total_paid_payment_salesmanger updatedPayment = existingRecord.get();
            
            // Update the fields as needed
            updatedPayment.setTotalpaid(totalPaidPayment.getTotalpaid() + updatedPayment.getTotalpaid());

            // Save the updated record
            total_paid_payment_salesmangerRepository.save(updatedPayment);

            // Save a new entry in Total_paid_payment_salesmanger_totalentry
            Total_paid_payment_salesmanger_totalentry data = new Total_paid_payment_salesmanger_totalentry();
            data.setPan(totalPaidPayment.getPan());
            data.setPaymentdate(new Date());
            data.setTotalpaid(totalPaidPayment.getTotalpaid());
            total_paid_payment_salesmanger_totalentryRepository.save(data);

            return ResponseEntity.status(HttpStatus.OK).body(updatedPayment);
        } else {
            // If the record doesn't exist, you may want to handle it accordingly
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

    @GetMapping("/list/allpaid/amount")
    public ResponseEntity<List<Map<String, Object>>> getTotalPaidPaymentsList()
    {
        try {
            System.out.println("hii");
            List<Salesman_Register> salesmen = salesmanRegisterRepository.findAll();
            List<Map<String, Object>> resultList = new ArrayList<>();
            for (Salesman_Register salesman : salesmen) {
                // Assuming 'pan' and 'id' are properties of Salesman_Register
                Long count1 = subscriptionpack_historyRepository.calculateSumOfRenewalPricesales(salesman.getPan());
                Long count2 = subscriptionpack_historyRepository.calculateSumOfRenewalPricedissales(salesman.getId());

                Long incentative =salesmanager_incentive_records_dataRepository.calculateSumOfRenewalPricesales(salesman.getPan());
                incentative = (incentative != null) ? incentative : 0L;
                count1 = (count1 != null) ? count1 : 0L;
                count2 = (count2 != null) ? count2 : 0L;

                Long totalIncomeCount = count1 + count2;
                System.out.println(totalIncomeCount);
                Optional<Total_paid_payment_salesmanger> totalPaidPayments = total_paid_payment_salesmangerRepository.findByPan(salesman.getPan());

                if (totalPaidPayments.isPresent()) {
                    Total_paid_payment_salesmanger data = totalPaidPayments.get();
                    Long totalpaidamount = data.getTotalpaid();
                    totalpaidamount = (totalpaidamount != null) ? totalpaidamount : 0L;

                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("saleman_name", salesman.getName());
                    responseMap.put("salesman_pan", salesman.getPan());
                    responseMap.put("totalIncomeCount", totalIncomeCount);
                    responseMap.put("Total_incetative_amunt",incentative);
                    responseMap.put("totalpaidamount", totalpaidamount);

                    resultList.add(responseMap);
                }
            }

            if (resultList.isEmpty()) {
                // You may want to handle the case where no records are found
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            return ResponseEntity.ok(resultList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/incentative/unpaid/amount/by/salesmanager")
    public ResponseEntity<Map<String, Object>> getSalesmanDetails(@RequestParam String pan) {
        Optional<Total_paid_payment_salesmanger> totalPaidPayments = total_paid_payment_salesmangerRepository.findByPan(pan);

        if (totalPaidPayments.isPresent()) {
            Long incentive = salesmanager_incentive_records_dataRepository.calculateSumOfRenewalPricesales(pan);
            incentive = (incentive != null) ? incentive : 0L;
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("TotalPaidPayments", totalPaidPayments.get().getTotalpaid()); // Assuming you want to retrieve the actual object
            responseMap.put("TotalIncentive", incentive);
            return ResponseEntity.ok(responseMap);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/list/allpaid/amoun/find/{id}")
public ResponseEntity<Total_paid_payment_salesmanger> findTotalPaidPaymentById(@PathVariable Long id) {
    try {
        Optional<Total_paid_payment_salesmanger> totalPaidPayment = total_paid_payment_salesmangerRepository.findById(id);

        return totalPaidPayment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}




}



