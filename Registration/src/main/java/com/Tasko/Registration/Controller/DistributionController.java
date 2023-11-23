package com.Tasko.Registration.Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.Tasko.Registration.Entity.DistributionReg;
import com.Tasko.Registration.Entity.Distributor_email_help;
import com.Tasko.Registration.Entity.Distributor_paymentdetail;
import com.Tasko.Registration.Entity.Invest_Now;
import com.Tasko.Registration.Entity.Kyc_client_detail;
import com.Tasko.Registration.Entity.Payment_Details;
import com.Tasko.Registration.Entity.Renewalprice_distributordata;
import com.Tasko.Registration.Entity.Subscription_Userdata;
import com.Tasko.Registration.Entity.Totalearnings_distrubutor;
import com.Tasko.Registration.Entity.UserEmail_help;
import com.Tasko.Registration.Entity.User_RegistrationsForm;
import com.Tasko.Registration.Entity.authDistribution;

import com.Tasko.Registration.Repository.DistributionRepo;
import com.Tasko.Registration.Repository.Distributor_email_helpRepository;
import com.Tasko.Registration.Repository.Distributor_paymentdetailRepository;
import com.Tasko.Registration.Repository.Invest_NowRepository;
import com.Tasko.Registration.Repository.Renewalprice_distributordataRepository;
import com.Tasko.Registration.Repository.Subscritpion_userdataRepository;
import com.Tasko.Registration.Repository.TaskoRepository;
import com.Tasko.Registration.Repository.Totalearnings_distrubutorRepository;
import com.Tasko.Registration.Repository.paymentDetailsRepo;
import com.Tasko.Registration.Service.JwtService;
import com.Tasko.Registration.Service.TaskoService;
import com.Tasko.Registration.dto.AuthRequest;
import com.Tasko.Registration.dto.ClientCount;
import com.Tasko.Registration.dto.DistrubutorCount;
import com.Tasko.Registration.dto.ProfessionCount;
import com.Tasko.Registration.dto.RegistrationWithCountResponse;
import com.Tasko.Registration.dto.RegistrationWithrecordResponse;
import com.Tasko.Registration.dto.SubscriptionCount;
import com.Tasko.Registration.dto.SubscrpitonList;
import com.Tasko.Registration.error.EmailMandatoryException;
import com.Tasko.Registration.error.UserAlreadyExist;
import com.Tasko.Registration.error.UserNotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.SelectObjectContentEvent.RecordsEvent;
import com.amazonaws.util.IOUtils;

@RestController
@CrossOrigin(origins = "*")
public class DistributionController 
{
	@Autowired
	private TaskoRepository taskoRepository;
	 @Autowired
		private PasswordEncoder passwordEncoder;
	 
	 @Autowired
		private AuthenticationManager authenticationManager;
	 @Autowired
		private Subscritpion_userdataRepository subscritpion_userdataRepository;
	 @Autowired
	 private Renewalprice_distributordataRepository renewalprice_distributordataRepository;
	 
	 @Autowired
	 private Totalearnings_distrubutorRepository totalearnings_distrubutorRepository;
	 
	 @Autowired
	    private Invest_NowRepository invest_NowRepository;
	 
	 @Autowired
	 private Distributor_email_helpRepository distributor_email_helpRepository;
	 @Autowired
	 private Distributor_paymentdetailRepository distributor_paymentdetailRepository;
	 @Autowired
		private DistributionRepo distributionRepo;
		
		@Autowired
		private JwtService jwtService;
		@Autowired
		   private TaskoService taskoService;
		@Autowired
		private AmazonS3 amazonS3;
		String bucketName="arkonet";
		Logger logger= LoggerFactory.getLogger(ClientMobileViewController.class);
		
	
	
	@PostMapping("/savedistributor")
	public DistributionReg saveDistribution(@RequestBody DistributionReg distributor) throws UserAlreadyExist, EmailMandatoryException
	{	
		Optional<User_RegistrationsForm> data = taskoRepository.findByPan(distributor.getPan());
		Optional<DistributionReg> disdata=distributionRepo.findByPan(distributor.getPan());
		Optional<User_RegistrationsForm> data1 = taskoRepository.findByEmail(distributor.getEmail());
		Optional<DistributionReg> disdata1=distributionRepo.findByEmail(distributor.getEmail());
		if(data.isPresent()||disdata.isPresent())
		{
			throw new UserAlreadyExist("PAN Already Exist");
		}
		if(data.isPresent()||disdata1.isPresent())
		{
			throw new EmailMandatoryException("Email Already Exist");
		}
		Optional<Distributor_paymentdetail> paymentdetails=distributor_paymentdetailRepository.findByPan(distributor.getPan());
		if(paymentdetails.isEmpty())
		{
			Distributor_paymentdetail records= new Distributor_paymentdetail();
			records.setPan(distributor.getPan());
			distributor_paymentdetailRepository.save(records);
		}
		Optional<Totalearnings_distrubutor> distributorspend= totalearnings_distrubutorRepository.findByPan(distributor.getPan());
		
		if(distributorspend.isEmpty())
		{
			Totalearnings_distrubutor record=new Totalearnings_distrubutor();
			record.setPan(distributor.getPan());
			record.setSpendrenewalPrice(0);
			totalearnings_distrubutorRepository.save(record);
		}
		distributor.setRegistrationdate(new Date());
		distributor.setStatus(false);
		distributor.setPassword(passwordEncoder.encode(distributor.getPassword()));
		return distributionRepo.save(distributor);		
	}
	
	
	
	//----------------------------------------------Login Page------------------------------------------------------------------------------------------------
		@PostMapping("/authenticate/Distribution")
		public ResponseEntity<Object> authenticateAndGetTokenForDistribution(@RequestBody AuthRequest authRequest) throws Exception
		{

			try {

				Optional<DistributionReg> user = distributionRepo.findByPan(authRequest.getUsername());
				DistributionReg s=user.get();
	  

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

				if(user.isPresent())
					{
					   if(s.isStatus()==true)
					   {
					
						if (authentication.isAuthenticated()) 
						{
							String jwt = jwtService.generateToken(authRequest.getUsername());

							authDistribution response = new authDistribution(jwt,user);
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
	
	//////////////////////////get all distrubutor/////////////////////////////////////////////////
	 @GetMapping("/all/distributor")
	    public ResponseEntity<List<DistributionReg>> getAllDistributionData() {
	        try {
	            List<DistributionReg> distributionList = distributionRepo.findAll();
	            return new ResponseEntity<>(distributionList, HttpStatus.OK);
	        } catch (Exception e) {
	            // Log the exception for troubleshooting
	            e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	 ////////////////////find by distrubutor id///////////////////////////////////
	 
	 @GetMapping("/all/distributorbyid/{id}")
	    public ResponseEntity<DistributionReg> getDistributionById(@PathVariable Long id) {
	        try {
	            Optional<DistributionReg> distributionOptional = distributionRepo.findById(id);

	            if (distributionOptional.isPresent()) {
	                return new ResponseEntity<>(distributionOptional.get(), HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	        } catch (Exception e) {
	            // Log the exception for troubleshooting
	            e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	 
	 
	 /////////////////////change the active status///////////////////////////////////////////
	 @PutMapping("/update/distributor/{id}/{value}")
	    public ResponseEntity<DistributionReg> updateDistributionById(@PathVariable Long id,@PathVariable Boolean value) {
	        try {
	            Optional<DistributionReg> existingDistributionOptional = distributionRepo.findById(id);

	            if (existingDistributionOptional.isPresent()) {
	                DistributionReg existingDistribution = existingDistributionOptional.get();

	                // Update the fields with the new values
	                existingDistribution.setStatus(value);
	                // Save the updated entity
	                DistributionReg savedDistribution = distributionRepo.save(existingDistribution);

	                return new ResponseEntity<>(savedDistribution, HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	        } catch (Exception e) {
	            // Log the exception for troubleshooting
	            e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	 
	 
	 ////////////////delete distrubotor by id///////////////////////////////////////////////////
	 @DeleteMapping("/delete/distributor/{id}")
	    public ResponseEntity<Void> deleteDistributionById(@PathVariable Long id) {
	        try {
	            Optional<DistributionReg> distributionOptional = distributionRepo.findById(id);

	            if (distributionOptional.isPresent()) {
	                distributionRepo.deleteById(id);
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            } else {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	        } catch (Exception e) {
	            // Log the exception for troubleshooting
	            e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	 /////////////
	 
	 
/////////////////////////////master admin first board total user and user data///////////////////////

@GetMapping("/distrubutor/userscounts/{pan}")
public ResponseEntity<Map<String,Object>> alladminsdata(@PathVariable String pan) {
try {
Long count = taskoRepository.countOfByDisrefrenceId(pan);
count = (count != null) ? count : 0L;
List<User_RegistrationsForm> users = taskoRepository.findByDisrefrenceId(pan);
users = (users != null) ? users : null;
Map<String, Object> responseMap = new HashMap<>();
responseMap.put("count", count);
responseMap.put("users", users);

return ResponseEntity.ok(responseMap);
} catch (Exception e) {
throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving subscription packs", e);
}
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@GetMapping("/distrubutor/userscounts/subenddate/{pan}")
public ResponseEntity<List<RegistrationWithrecordResponse>> allAdminsData1(@PathVariable String pan) {
    try {
        List<User_RegistrationsForm> users = taskoRepository.findByDisrefrenceId(pan);
        users = (users != null) ? users : new ArrayList<>();

        List<RegistrationWithrecordResponse> responseList = new ArrayList<>();

        for (User_RegistrationsForm registration : users) {
            Optional<Subscription_Userdata> data = subscritpion_userdataRepository.findByPan(registration.getPan());

            if (data.isPresent()) {
                Subscription_Userdata infodata = data.get();
                Date subscriptionEndDate = (infodata.getSubendtdate() != null) ? infodata.getSubendtdate() : null;

                RegistrationWithrecordResponse response = new RegistrationWithrecordResponse(registration, subscriptionEndDate);
                responseList.add(response);
            } else {
                // Handle the case when there is no subscription data for the user
                // You might want to log this or handle it differently based on your requirements.
            }
        }

        return ResponseEntity.ok(responseList);
    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving subscription packs", e);
    }
}



///////////////////////////////every  profession by////////////////////////////////////////////////////////

@GetMapping("/distrubutoruser/countbyprofession/{pan}")
public ResponseEntity<List<ProfessionCount>> countprofessionby(@PathVariable String pan) {
try {
Long count1 = taskoRepository.countdisOfTotalprofession1(pan);
Long count2 = taskoRepository.countdisOfTotalprofession2(pan);
Long count3 = taskoRepository.countdisOfTotalprofession3(pan);
Long count4 = taskoRepository.countdisOfTotalprofession4(pan);
Long count5 = taskoRepository.countdisOfTotalprofession5(pan);
Long count6 = taskoRepository.countdisOfTotalprofession6(pan);
Long count7 = taskoRepository.countdisOfTotalprofession7(pan);

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
	 


///////////////////////////this data is table 2 then all record in profession wise////////////////////
@GetMapping("/distrubutoruser/by-profession/{profession}/{dispan}")
public ResponseEntity<List<RegistrationWithCountResponse>> getUserRegistrationsByProfession(@PathVariable String dispan,@PathVariable String profession) {
try {
List<RegistrationWithCountResponse> responseList = new ArrayList<>();

List<User_RegistrationsForm> registrations = taskoRepository.findByprofessionAndDisrefrenceId(profession,dispan);
for (User_RegistrationsForm registration : registrations) {
String pan = registration.getPan();
System.out.println(pan);
Long count = taskoRepository.countOfByRefrenceId(pan);
// Ensure count is not null, default to 0L if null
count = (count != null) ? count : 0L;
Optional<Subscription_Userdata> data=subscritpion_userdataRepository.findByPan(pan);
Subscription_Userdata infodata=data.get();
boolean status=infodata.isPaid();
Date a=infodata.getSubstartdatebyuser();
if (a == null) {
a = null; // Setting it to null if it's not already null
}
System.out.println(count);
Date b=infodata.getSubendtdate();
if(b==null)
{
b = null;
}
RegistrationWithCountResponse response = new RegistrationWithCountResponse(registration, count,status,a,b);
responseList.add(response);
}
return ResponseEntity.ok(responseList);
} catch (Exception e) {
// Handle the exception here, you can log it or return an error response
e.printStackTrace(); // Replace with proper logging
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
}
}







/////////////////////////////////////////////////////cout of table 3/////////////////////////////

@GetMapping("/everydistrubutor/incomecount/{pan}")
public List<ClientCount> getClientCount(@PathVariable String pan) {
    try {
        Long totalincomeCount = renewalprice_distributordataRepository.calculateSumOfRenewalPrice(pan);
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        // Get the previous month
        LocalDate previousMonth = currentDate.minusMonths(1);

        int previousYear = previousMonth.getYear();
        int previousMonthValue = previousMonth.getMonthValue();

        // Corrected parameter: use previousMonthValue directly
        Long previousMonthincomeCount = renewalprice_distributordataRepository.calculateSumOfRenewalPricelastmonth(previousYear, previousMonthValue, pan);
      

        int currentYear = currentDate.getYear();
        int currentMonthValue = currentDate.getMonthValue();

        Long currentMonthIncomeCount = renewalprice_distributordataRepository.calculateSumOfRenewalPricelastmonth(currentYear, currentMonthValue, pan);
        
        

        // Set default values to 0 if a count is not available
        totalincomeCount = (totalincomeCount != null) ? totalincomeCount : 0L;
        previousMonthincomeCount = (previousMonthincomeCount != null) ? previousMonthincomeCount : 0L;
        currentMonthIncomeCount = (currentMonthIncomeCount != null) ? currentMonthIncomeCount : 0L;
        
        List<ClientCount> responseList = new ArrayList<>();
        responseList.add(new ClientCount("Total Earning", totalincomeCount));
        responseList.add(new ClientCount("Last Month", previousMonthincomeCount));
        responseList.add(new ClientCount("unpaid", currentMonthIncomeCount));

        return responseList;
    } catch (Exception e) {
        // You can log the exception here if needed
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching client count.", e);
    }
}

	 
//-----------------------------update Distribution----------------------------------------
@PutMapping("/updateDistribution/{id}")
public DistributionReg updateDistribution(@RequestBody DistributionReg distributionReg,@PathVariable("id")Long id) throws UserNotFoundException
{
	 DistributionReg existingDistributionReg = distributionRepo.findById(id).orElse(null);

   if (existingDistributionReg != null) {
       if (Objects.nonNull(distributionReg.getName()) && !"".equalsIgnoreCase(distributionReg.getName())) {
           existingDistributionReg.setName(distributionReg.getName());
       }

       if (Objects.nonNull(distributionReg.getDatebirth())) {
           existingDistributionReg.setDatebirth(distributionReg.getDatebirth());
       }

       if (Objects.nonNull(distributionReg.getProfession()) && !"".equalsIgnoreCase(distributionReg.getProfession())) {
           existingDistributionReg.setProfession(distributionReg.getProfession());
       }

       if (Objects.nonNull(distributionReg.getTelephone()) && !"".equalsIgnoreCase(distributionReg.getTelephone())) {
      	    existingDistributionReg.setTelephone(distributionReg.getTelephone());
      	}

      	if (Objects.nonNull(distributionReg.getMobile()) && !"".equalsIgnoreCase(distributionReg.getMobile())) {
      	    existingDistributionReg.setMobile(distributionReg.getMobile());
      	}

      	if (Objects.nonNull(distributionReg.getEmail()) && !"".equalsIgnoreCase(distributionReg.getEmail())) {
      	    existingDistributionReg.setEmail(distributionReg.getEmail());
      	}

      	if (Objects.nonNull(distributionReg.getAddress()) && !"".equalsIgnoreCase(distributionReg.getAddress())) {
      	    existingDistributionReg.setAddress(distributionReg.getAddress());
      	}

      	if (Objects.nonNull(distributionReg.getPin_code()) && !"".equalsIgnoreCase(distributionReg.getPin_code())) {
      	    existingDistributionReg.setPin_code(distributionReg.getPin_code());
      	}

      	if (Objects.nonNull(distributionReg.getState()) && !"".equalsIgnoreCase(distributionReg.getState())) {
      	    existingDistributionReg.setState(distributionReg.getState());
      	}

      	if (Objects.nonNull(distributionReg.getWhatsApp_Link()) && !"".equalsIgnoreCase(distributionReg.getWhatsApp_Link())) {
      	    existingDistributionReg.setWhatsApp_Link(distributionReg.getWhatsApp_Link());
      	}
  	
          return distributionRepo.save(existingDistributionReg);
   } 
   else 
   {
     
  	 throw new UserNotFoundException("User Not Found");
   }
}	 



/////////////////////////////////////////////////Renewal of subscription///////////////////////////////////////////

@GetMapping("/distrubutor/countreneval/{pan}")
public ResponseEntity<List<SubscriptionCount>> countrenewal(@PathVariable String pan) {
try {
System.out.println("hii");
Long count1 = subscritpion_userdataRepository.countOfdistodayRenewal(new Date(),pan);
Date currentDate = new Date();

// Create a Calendar instance and set it to the current date
Calendar calendar = Calendar.getInstance();
calendar.setTime(currentDate);

// Subtract 1 day
calendar.add(Calendar.DAY_OF_MONTH, +1);

// Get the new date after subtracting 1 day
Date newDate = calendar.getTime();
Long count2 = subscritpion_userdataRepository.countOfdisyestardayRenewal(newDate,pan);
calendar.setTime(new Date());

// Subtract 7 days from the current date
calendar.add(Calendar.DAY_OF_MONTH, +7);

// Get the start date for the last 7 days
Date startDate = calendar.getTime();

// Call the method with the startDate

Long count3 = subscritpion_userdataRepository.countOfdisTotalProfessionWithinLast7Daysreneval(startDate,pan,currentDate);
System.out.println(currentDate);

LocalDate currentDate1 = LocalDate.now();
int year = currentDate1.getYear();
int month = currentDate1.getMonthValue();

Long count4 = subscritpion_userdataRepository.countOfdisTotalProfessionInCurrentMonthreneval(year, month,pan,currentDate);
Calendar calendar1 = Calendar.getInstance();
LocalDate currentDate2 = LocalDate.now();

calendar1.setTime(new Date()); // Set your initial date here

// Add 90 days
calendar1.add(Calendar.DAY_OF_MONTH, 90);

// Get the new date
Date newDate1 = calendar1.getTime();
Long count5 = subscritpion_userdataRepository.countOfdisTotalProfessionInthreemonthreneval(newDate1,pan,currentDate);
Calendar calendar2 = Calendar.getInstance();
LocalDate currentDate3 = LocalDate.now();

calendar2.setTime(new Date()); // Set your initial date here
// Add 90 days
calendar2.add(Calendar.DAY_OF_MONTH, 180);
Date newDate2 = calendar2.getTime();
Long count6 = subscritpion_userdataRepository.countOfdisTotalProfessionInsixmonthreneval(newDate2,pan,currentDate);



// Set default values to 0 if a count is not available
count1 = (count1 != null) ? count1 : 0L;
count2 = (count2 != null) ? count2 : 0L;
count3 = (count3 != null) ? count3 : 0L;
count4 = (count4 != null) ? count4 : 0L;
count5 = (count5 != null) ? count5 : 0L;
//count6 = (count6 != null) ? count6 : 0L;
System.out.println(count1);
System.out.println(count2);
System.out.println(count3);
System.out.println(count4);
System.out.println(count5);
//System.out.println(count6);


List<SubscriptionCount> professionCounts = new ArrayList<>();
professionCounts.add(new SubscriptionCount("Today", count1));
professionCounts.add(new SubscriptionCount("Tomorrow", count2));
professionCounts.add(new SubscriptionCount("Weak", count3));
professionCounts.add(new SubscriptionCount("Month", count4));
professionCounts.add(new SubscriptionCount("3 Month", count5));
professionCounts.add(new SubscriptionCount("6 Month", count5));

return ResponseEntity.ok(professionCounts);
} catch (Exception e) {
// Handle the exception here, you can log it or return an error response
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
}
}




//////////////////////////////list of renveal////////////////////////////////////////////////////////////////////

@GetMapping("/Renewal/today/distrubutor/{pan}")
public List<Subscription_Userdata> getTodayRenewal(@PathVariable String pan) {
// Get the current date and time
Date currentDate = new Date();
List<Subscription_Userdata> count1 = subscritpion_userdataRepository.listOfdistodayRenewal(currentDate,pan);
count1 = (count1 != null) ? count1 : null;
return (count1);
}




/////////////////////////////////////////////////////////

@GetMapping("/Renewal/tommarow/distrubutor/{pan}")
public List<Subscription_Userdata> getTommarowRenewal(@PathVariable String pan) {
Date currentDate = new Date();

// Create a Calendar instance and set it to the current date
Calendar calendar = Calendar.getInstance();
calendar.setTime(currentDate);

// Subtract 1 day
calendar.add(Calendar.DAY_OF_MONTH, +1);

// Get the new date after subtracting 1 day
Date newDate = calendar.getTime();
List<Subscription_Userdata> count2 = subscritpion_userdataRepository.listOfdisyestardayRenewal(newDate,pan);
count2 = (count2 != null) ? count2 : null;
return (count2);
}

/////////////////////////////////////////////////////////////////////////
@GetMapping("/Renewal/week/distrubutor/{pan}")
public List<Subscription_Userdata> getweekRenewal(@PathVariable String pan) {
Date currentDate = new Date();

// Create a Calendar instance and set it to the current date
Calendar calendar = Calendar.getInstance();
calendar.setTime(new Date());

// Subtract 7 days from the current date
calendar.add(Calendar.DAY_OF_MONTH, +7);

// Get the start date for the last 7 days
Date startDate = calendar.getTime();

// Call the method with the startDate

List<Subscription_Userdata> count3 = subscritpion_userdataRepository.listOfdisTotalProfessionWithinLast7Daysreneval(startDate,pan,currentDate);
count3 = (count3 != null) ? count3 : null;
return (count3);
}




////////////////////////////////////////////////////////////////////////////////////////////////////////
@GetMapping("/Renewal/month/distrubutor/{pan}")
public List<Subscription_Userdata> getmonthRenewal(@PathVariable String pan) {
LocalDate currentDate1 = LocalDate.now();
int year = currentDate1.getYear();
int month = currentDate1.getMonthValue();
Date currentDate = new Date();
List<Subscription_Userdata> count4 = subscritpion_userdataRepository.listOfdisTotalProfessionInCurrentMonthreneval(year, month,pan,currentDate);
count4 = (count4 != null) ? count4 : null;
return (count4);
}


/////////////////////////////////////////////////////////////////////////////////
@GetMapping("/Renewal/threemonth/distrubutor/{pan}")
public List<Subscription_Userdata> getthreemonthRenewal(@PathVariable String pan) {
	Date currentDate = new Date();
	Calendar calendar1 = Calendar.getInstance();
LocalDate currentDate2 = LocalDate.now();

calendar1.setTime(new Date()); // Set your initial date here

// Add 90 days
calendar1.add(Calendar.DAY_OF_MONTH, 90);

// Get the new date
Date newDate1 = calendar1.getTime();
List<Subscription_Userdata> count5 = subscritpion_userdataRepository.listOfdisTotalProfessionInthreemonthreneval(newDate1,pan,currentDate);
count5 = (count5 != null) ? count5 : null;
return (count5);
}


////////////////////////////////////////////////////////////////
@GetMapping("/Renewal/sixmonth/distrubutor/{pan}")
public List<Subscription_Userdata> getsixmonthRenewal(@PathVariable String pan) {
Calendar calendar2 = Calendar.getInstance();
LocalDate currentDate3 = LocalDate.now();

calendar2.setTime(new Date()); // Set your initial date here
// Add 90 days
calendar2.add(Calendar.DAY_OF_MONTH, 180);
Date newDate2 = calendar2.getTime();
Date currentDate = new Date();
List<Subscription_Userdata> count5 = subscritpion_userdataRepository.listOfdisTotalProfessionInsixmonthreneval(newDate2,pan,currentDate);
count5 = (count5 != null) ? count5 : null;
return (count5);
}



/////////////////////////
//////////////////////////////////////////list of subscrpiton////////////////////////////////////////////////////////
@GetMapping("/distrubutor/listsubscription/{pan}")
public ResponseEntity<List<SubscriptionCount>> countsubscription(@PathVariable String pan) {
	try {
	    Long count1 = subscritpion_userdataRepository.countOfdistodaysubscription(new Date(),pan);
	    Date currentDate = new Date();

	 // Create a Calendar instance and set it to the current date
	 Calendar calendar = Calendar.getInstance();
	 calendar.setTime(currentDate);

	 // Subtract 1 day
	 calendar.add(Calendar.DAY_OF_MONTH, -1);

	 // Get the new date after subtracting 1 day
	 Date newDate = calendar.getTime();
	    Long count2 = subscritpion_userdataRepository.countOfdisyestardaysubscription(newDate,pan);
	    calendar.setTime(new Date());

	    // Subtract 7 days from the current date
	    calendar.add(Calendar.DAY_OF_MONTH, -7);

	    // Get the start date for the last 7 days
	    Date startDate = calendar.getTime();

	    // Call the method with the startDate
	   
	    Long count3 = subscritpion_userdataRepository.countOfdisTotalProfessionWithinLast7Days(startDate,pan);
	    int currentYear = calendar.get(Calendar.YEAR);
	    Long count4 = subscritpion_userdataRepository.countOfdisTotalProfessionInCurrentYear(currentYear,pan);
        // Calculate the previous year
        int previousYear = currentYear - 1;
        String currentpreviousAsString = Integer.toString(previousYear);
	    Long count5 = subscritpion_userdataRepository.countOfdisTotalProfessionInPreviousYear(previousYear,pan);
	    String currentYearAsString = Integer.toString(currentYear);
	    // Set default values to 0 if a count is not available
	    count1 = (count1 != null) ? count1 : 0L;
	    count2 = (count2 != null) ? count2 : 0L;
	    count3 = (count3 != null) ? count3 : 0L;
	    count4 = (count4 != null) ? count4 : 0L;
	    count5 = (count5 != null) ? count5 : 0L;
	  
	    List<SubscriptionCount> professionCounts = new ArrayList<>();
	    professionCounts.add(new SubscriptionCount("Today", count1));
	    professionCounts.add(new SubscriptionCount("Yestarday", count2));
	    professionCounts.add(new SubscriptionCount("Weak", count3));
	    professionCounts.add(new SubscriptionCount(currentYearAsString, count4));
	    professionCounts.add(new SubscriptionCount(currentpreviousAsString, count5));

	    return ResponseEntity.ok(professionCounts);
	} catch (Exception e) {
	    // Handle the exception here, you can log it or return an error response
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}



///////////////////////////////////////////////////subscriton list//////////////////////////////////////////////////////////////
//////////////////////////////////////////today//////////////////////////////////////////////////
@GetMapping("/distrubutor/subscriptions/today/{pan}")
public List<Subscription_Userdata> getTodaySubscriptions(@PathVariable String pan) {
// Get the current date and time
Date currentDate = new Date();
List<Subscription_Userdata> count1 = subscritpion_userdataRepository.listOfdistodaysubscription(currentDate,pan);
count1 = (count1 != null) ? count1 : null;
return (count1);
}
/////////////////////////////////////////////////////yesstarday///////////////////////////////////////////////////////////////////
@GetMapping("/distrubutor/subscriptionslist/yestarday/{pan}")
public List<Subscription_Userdata> listOfyestardaysubscription(@PathVariable String pan) {
// Get the current date and time
Date currentDate = new Date();
Calendar calendar = Calendar.getInstance();
calendar.setTime(currentDate);

// Subtract 1 day
calendar.add(Calendar.DAY_OF_MONTH, -1);

// Get the new date after subtracting 1 day
Date newDate = calendar.getTime();
List<Subscription_Userdata> count2 = subscritpion_userdataRepository.listOfdisyestardaysubscription(newDate,pan);
count2 = (count2 != null) ? count2 : null;
return (count2);
}

/////////////////////////////////////////////////week////////////////////////////////////////////////////////////////////////
@GetMapping("/distrubutor/subscriptionslist/week/{pan}")
public List<Subscription_Userdata> listOfweeksubscription(@PathVariable String pan) {
// Get the current date and time
Date currentDate = new Date();
Calendar calendar = Calendar.getInstance();
calendar.setTime(currentDate);
calendar.add(Calendar.DAY_OF_MONTH, -7);

// Get the start date for the last 7 days
Date startDate = calendar.getTime();

// Query the repository for counts within the last 7 days
List<Subscription_Userdata> count3 = subscritpion_userdataRepository.listofdisweeksubscription(startDate,pan);
count3 = (count3 != null) ? count3 : null;
return (count3);
}

/////////////////////////////////////////////current year//////////////////////////////////////////////////////////////////
@GetMapping("/distrubutor/subscriptionslist/year/{pan}")
public List<Subscription_Userdata> listOfyearsubscription(@PathVariable String pan) {
// Get the current date and time
Calendar calendar = Calendar.getInstance();
int currentYear = calendar.get(Calendar.YEAR);
// Query the repository for counts in the current year
List<Subscription_Userdata> count4 = subscritpion_userdataRepository.listOfdisTotalProfessionInCurrentYear(currentYear,pan);
count4 = (count4 != null) ? count4 : null;
return (count4);
}



/////////////////////////////////////////////previous year//////////////////////////////////////////////////////////////////
@GetMapping("/distrubutor/subscriptionslist/previousyear/{pan}")
public List<Subscription_Userdata> listOfpreviousubscription(@PathVariable String pan) {
//Get the current date and time
Calendar calendar = Calendar.getInstance();
int currentYear = calendar.get(Calendar.YEAR);
int previousYear = currentYear - 1;
String currentYearAsString = Integer.toString(currentYear);
//Query the repository for counts in the previous year
List<Subscription_Userdata> count5 = subscritpion_userdataRepository.listOfdisTotalProfessionInPreviousYear(previousYear,pan);
count5 = (count5 != null) ? count5 : null;
return (count5);
}



/////////////////////////get all invest now table information by userid///////////////////////////////////////////////
@GetMapping("/distrubutor/Invest/allrecords")
public ResponseEntity<List<Invest_Now>> getAllinvest1(@RequestParam("InvestNow_Email") String investNowEmail, @RequestParam("category") String category) {
    try {
        List<Invest_Now> investlist = invest_NowRepository.listfindAllByInvestNow_EmailAndCategory(investNowEmail, category);

        if (investlist.isEmpty()) {
            // No records found, return null or an empty list as per your requirement
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(investlist, HttpStatus.OK);
    } catch (Exception ex) {
        // You can customize the exception handling here
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


////////////////////////////////////////////////////////////////////////////////////// send distributor help email/////////////////////////
@PostMapping("/sendemaildistributor/help")
public ResponseEntity<String> sendEmail(
        @RequestParam Long distributorId,
        @RequestParam String subject,
        @RequestBody String body
) {
    try {
        Optional<DistributionReg> userOptional = distributionRepo.findById(distributorId);
        DistributionReg data = userOptional.get();

        Distributor_email_help distributor = new Distributor_email_help();
        distributor.setDistributorid(distributorId);
        distributor.setSubject(subject);
        distributor.setDetail(body);
        distributor.setDate(new Date());
        distributor.setName(data.getName());
        distributor_email_helpRepository.save(distributor);
        String to = "taxkohelp@gmail.com";
        taskoService.sendEmailwithattachmentUserhelp(to, subject, body);

        return ResponseEntity.ok("Email sent successfully.");
    } catch (Exception e) {
        // Handle exceptions appropriately
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: " + e.getMessage());
    }
}


//////////////////////////////////////////// update a payment///////////////////////////////////////////////
@PutMapping("/update/everydistrubutor/incomecount/{pan}/{value}")
public ResponseEntity<String> putClientCount(@PathVariable String pan, @PathVariable Long value) {
    try {
        Long totalIncomeCount = renewalprice_distributordataRepository.calculateSumOfRenewalPrice(pan);
        Optional<Totalearnings_distrubutor> recordData = totalearnings_distrubutorRepository.findByPan(pan);

        if (recordData.isPresent()) {
            Totalearnings_distrubutor recordearning = recordData.get();

            if (value != null) {
                // Check if the spendrenewalPrice is null and initialize it to zero if needed
            	recordearning.setSpendrenewalPrice(recordearning.getSpendrenewalPrice()+value);

                // Save the updated record
                totalearnings_distrubutorRepository.save(recordearning);
            }
        } else {
            // Handle the case where the record is not found for the given PAN
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found for PAN: " + pan);
        }

        return ResponseEntity.ok(null);
    } catch (Exception e) {
        // You can log the exception here if needed
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while updating income count.", e);
    }
}

////////////////////////////////////get all earnnig data for master dash board///////////////////////////////////////////
@GetMapping("/master/everydistrubutor/incomecount")
public List<DistrubutorCount> getMasterDistributorCount() {
    try {
        List<DistributionReg> data = distributionRepo.findAll();

        // Check if data is not empty before processing
        if (!data.isEmpty()) {
            List<DistrubutorCount> responseList = new ArrayList<>();

            for (DistributionReg datadetails : data) {
                Long totalIncomeCount = renewalprice_distributordataRepository.calculateSumOfRenewalPrice(datadetails.getPan());
                Long previousMonthIncomeCount = totalearnings_distrubutorRepository.calculateSumOfRenewalPrice(datadetails.getPan());

                totalIncomeCount = (totalIncomeCount != null) ? totalIncomeCount : 0L;
                previousMonthIncomeCount = (previousMonthIncomeCount != null) ? previousMonthIncomeCount : 0L;

                Long currentMonthIncomeCount = totalIncomeCount - previousMonthIncomeCount;
                // Set default values to 0 if a count is not available
                currentMonthIncomeCount = (currentMonthIncomeCount != null) ? currentMonthIncomeCount : 0L;

                Optional<DistributionReg> datadistributor = distributionRepo.findByPan(datadetails.getPan());
                DistributionReg records1 = datadistributor.get();
                records1 = (records1 != null) ? records1 : null;
                DistrubutorCount response = new DistrubutorCount(records1, totalIncomeCount,previousMonthIncomeCount,currentMonthIncomeCount);
                responseList.add(response);   
            }

            return responseList;
        } else {
            // Handle the case where no data is available
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data available for calculating income counts.");
        }
    } catch (Exception e) {
        // You can log the exception here if needed
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching income counts.", e);
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//------------------------------------------Update Payment Details-----------------------------------------------------------
	 @Autowired
	    private paymentDetailsRepo detailsRepo;
	    @PutMapping("/UpdatedistributorPaymentDetails/{pan}")
	    public ResponseEntity<Object> updatePaymentDetails(
	            @PathVariable String  pan,
	            @RequestParam(value = "imagePathProfile", required = false) MultipartFile imagePathProfile,
	            @RequestParam(value = "Bank_Name", required = false) String bankName,
	            @RequestParam(value = "AccountName", required = false) String accountName,
	            @RequestParam(value = "AccountNumber", required = false) Long accountNumber,
	            @RequestParam(value = "IFSC", required = false) String ifsc  
	           ) throws IOException {

	        Optional<Distributor_paymentdetail> existingPaymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

	        if (!existingPaymentDetailsOptional.isPresent()) {
	            return ResponseEntity.notFound().build();
	        }

	        Distributor_paymentdetail existingPaymentDetails = existingPaymentDetailsOptional.get();

	        // Update the payment details if new values are provided
	        if(imagePathProfile.isEmpty())
	        {
	        	 existingPaymentDetails.setImageNameprofile(null);
		            existingPaymentDetails.setImagePathProfile(null);
	        }
	        if (!imagePathProfile.isEmpty()) {
	        	 String name = imagePathProfile.getOriginalFilename();
	     		String[] result = name.split("\\.");
	     		String fileExtension = result[result.length - 1];
	     		String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
	     		System.out.println("File Name without Extension: " + fileNameWithoutExtension);
	     		ObjectMetadata metadata = new ObjectMetadata();
	     		metadata.setContentType(imagePathProfile.getContentType());
	     		InputStream inputStream = imagePathProfile.getInputStream();
	     		String filename = generateUniqueFileprofile(pan,fileNameWithoutExtension);
	             String s3Key = filename + "." + fileExtension;
	        //    amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
	            
	            
	            
    	           // String s4Key = imageName;

	         // Check if the object with the same key exists in the S3 bucket
  	         if (amazonS3.doesObjectExist(bucketName, s3Key)) {
   	             // If it exists, delete it
  	             amazonS3.deleteObject(bucketName, s3Key);
  	         }

	         // Upload the new object
	         amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

	            existingPaymentDetails.setImageNameprofile(name);
	            existingPaymentDetails.setImagePathProfile("s3://" + bucketName + "/" + s3Key);
	            
	            
	        }

	        if (bankName != null) {
	            existingPaymentDetails.setBank_name(bankName);
	        }

	        if (accountName != null) {
	            existingPaymentDetails.setAccountName(accountName);
	        }

	        if (accountNumber != null) {
	            existingPaymentDetails.setAccountNumber(accountNumber);
	        }

	        if (ifsc != null) {
	            existingPaymentDetails.setIfsc(ifsc);
	        }
    

	        // Save the updated payment details
	    
			Distributor_paymentdetail updatedPaymentDetails = distributor_paymentdetailRepository.save(existingPaymentDetails);

	        return ResponseEntity.ok("Data Updated Sucessfull!");
	    }
	


private String generateUniqueName(Long userid,String pan)
{
	return userid+"_"+pan;
}

private String generateUniqueFileprofile(String pan,String fileNameWithoutExtension) {
	return pan + "_" +"distributor"+ "_" +"profile" + "_" + fileNameWithoutExtension;
}







//Helper method to extract S3 key from the S3 URL
private String getS3KeyFromFilePath(String s3FilePath) {
//Assuming your S3 URL format is "s3://bucketName/s3Key"
String[] parts = s3FilePath.split("/");
return parts[parts.length - 1];
}



//----------------------------------------------upload adhar-----------------------------------------------------------//
@PutMapping("/distributor/upload/Kycadharimage")
public ResponseEntity<Object> uploadadhar(
      @RequestParam String pan,
      @RequestParam(value = "imagePathAdhar", required = false) MultipartFile imagePathAdhar) {
  try {

      Optional<Distributor_paymentdetail> existingPaymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

      if (!existingPaymentDetailsOptional.isPresent()) {
          return ResponseEntity.notFound().build();
      }

      Distributor_paymentdetail existingPaymentDetails = existingPaymentDetailsOptional.get();
      if (!imagePathAdhar.isEmpty()) {
     	 String name = imagePathAdhar.getOriginalFilename();
	     		String[] result = name.split("\\.");
	     		String fileExtension = result[result.length - 1];
	     		String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
	     		System.out.println("File Name without Extension: " + fileNameWithoutExtension);
	     		ObjectMetadata metadata = new ObjectMetadata();
	     		metadata.setContentType(imagePathAdhar.getContentType());
	     		InputStream inputStream = imagePathAdhar.getInputStream();
	     		String filename = generateUniqueFilepAdhar(pan,fileNameWithoutExtension);
	             String s3Key = filename + "." + fileExtension;
	        //    amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
	            
	            
	            
 	           // String s4Key = imageName;

	         // Check if the object with the same key exists in the S3 bucket
	         if (amazonS3.doesObjectExist(bucketName, s3Key)) {
	             // If it exists, delete it
	             amazonS3.deleteObject(bucketName, s3Key);
	         }

	         // Upload the new object
	         amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

	            existingPaymentDetails.setImageNameAdhar(name);
	            existingPaymentDetails.setImagePathAdhar("s3://" + bucketName + "/" + s3Key);
     }
      // Save the updated payment details
		Distributor_paymentdetail updatedPaymentDetails = distributor_paymentdetailRepository.save(existingPaymentDetails);

        return ResponseEntity.ok("Upload Sucessfully!");
  } catch (Exception e) {
      // Handle exceptions here, log them, and return an error response
      e.printStackTrace(); // Log the exception for debugging
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
      		+ "ing image");
  }
}
private String generateUniqueFilepAdhar(String pan,String fileNameWithoutExtension) {
	return pan + "_" +"distributor"+ "_" +"Adhar" + "_" + fileNameWithoutExtension;
}

//----------------------------------------------upload pan-----------------------------------------------------------//
@PutMapping("/distributor/upload/Kycpanimage")
public ResponseEntity<Object> uploadpandistributor(
    @RequestParam String pan,
    @RequestParam(value = "imagePathpan", required = false) MultipartFile imagePathpan) {
try {

    Optional<Distributor_paymentdetail> existingPaymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

    if (!existingPaymentDetailsOptional.isPresent()) {
        return ResponseEntity.notFound().build();
    }

    Distributor_paymentdetail existingPaymentDetails = existingPaymentDetailsOptional.get();
    if (!imagePathpan.isEmpty()) {
    	String name = imagePathpan.getOriginalFilename();
 		String[] result = name.split("\\.");
 		String fileExtension = result[result.length - 1];
 		String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
 		System.out.println("File Name without Extension: " + fileNameWithoutExtension);
 		ObjectMetadata metadata = new ObjectMetadata();
 		metadata.setContentType(imagePathpan.getContentType());
 		InputStream inputStream = imagePathpan.getInputStream();
 		String filename = generateUniqueFilepan(pan,fileNameWithoutExtension);
         String s3Key = filename + "." + fileExtension;
    //    amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
        
        
        
           // String s4Key = imageName;

     // Check if the object with the same key exists in the S3 bucket
       if (amazonS3.doesObjectExist(bucketName, s3Key)) {
            // If it exists, delete it
           amazonS3.deleteObject(bucketName, s3Key);
       }

     // Upload the new object
     amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

        existingPaymentDetails.setImageNamepan(name);
        existingPaymentDetails.setImagePathpan("s3://" + bucketName + "/" + s3Key);
        
    }
    // Save the updated payment details
		Distributor_paymentdetail updatedPaymentDetails = distributor_paymentdetailRepository.save(existingPaymentDetails);

      return ResponseEntity.ok("Upload Sucessfully!");
} catch (Exception e) {
    // Handle exceptions here, log them, and return an error response
    e.printStackTrace(); // Log the exception for debugging
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
    		+ "ing image");
}
}
private String generateUniqueFilepan(String pan,String fileNameWithoutExtension) {
	return pan + "_" +"distributor"+ "_" +"pan" + "_" + fileNameWithoutExtension;
}

//----------------------------------------------upload cheque-----------------------------------------------------------//
@PutMapping("/distributor/upload/Kycchequeimage")
public ResponseEntity<Object> uploadchequedistributor(
  @RequestParam String pan,
  @RequestParam(value = "imagePathcheque", required = false) MultipartFile imagePathcheque) {
try {

  Optional<Distributor_paymentdetail> existingPaymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

  if (!existingPaymentDetailsOptional.isPresent()) {
      return ResponseEntity.notFound().build();
  }

  Distributor_paymentdetail existingPaymentDetails = existingPaymentDetailsOptional.get();
  if(!imagePathcheque.isEmpty())
  {
  	String name = imagePathcheque.getOriginalFilename();
		String[] result = name.split("\\.");
		String fileExtension = result[result.length - 1];
		String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
		System.out.println("File Name without Extension: " + fileNameWithoutExtension);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(imagePathcheque.getContentType());
		InputStream inputStream = imagePathcheque.getInputStream();
		String filename = generateUniqueFilecheque(pan,fileNameWithoutExtension);
       String s3Key = filename + "." + fileExtension;
  //    amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
      
      
      
         // String s4Key = imageName;

   // Check if the object with the same key exists in the S3 bucket
     if (amazonS3.doesObjectExist(bucketName, s3Key)) {
          // If it exists, delete it
         amazonS3.deleteObject(bucketName, s3Key);
     }

   // Upload the new object
   amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
   existingPaymentDetails.setImageNamecheque(name);
      existingPaymentDetails.setImagePathcheque("s3://" + bucketName + "/" + s3Key);
  }
  // Save the updated payment details
		Distributor_paymentdetail updatedPaymentDetails = distributor_paymentdetailRepository.save(existingPaymentDetails);

    return ResponseEntity.ok("Upload Sucessfully!");
} catch (Exception e) {
  // Handle exceptions here, log them, and return an error response
  e.printStackTrace(); // Log the exception for debugging
  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
  		+ "ing image");
}
}

private String generateUniqueFilecheque(String pan,String fileNameWithoutExtension) {
	return pan + "_" +"distributor"+ "_" +"cheque" + "_" + fileNameWithoutExtension;
}





//////////////////////////////////////delete images///////////////////////////////////
//////////////////////////////////////////////////////////delete adhar//////////////////////////////////////////
@PutMapping("/distributor/delete/Kycadharimage")
public ResponseEntity<String> uploadcheck1(@RequestParam String pan) {
try {
// Check if the client with the given PAN exists
	Optional<Distributor_paymentdetail> existingPaymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

	  if (!existingPaymentDetailsOptional.isPresent()) {
	      return ResponseEntity.notFound().build();
	  }

	  Distributor_paymentdetail existingPaymentDetails = existingPaymentDetailsOptional.get();
String s3Key = getS3KeyFromFilePath(existingPaymentDetails.getImagePathAdhar());

// Check if the object with the same key exists in the S3 bucket
if (amazonS3.doesObjectExist(bucketName, s3Key)) {
// If it exists, delete it
amazonS3.deleteObject(bucketName, s3Key);
}

// Modify the specific column you want to delete
existingPaymentDetails.setImageNameAdhar(null); // Set it to null, assuming you want to "delete" it.
existingPaymentDetails.setImagePathAdhar(null);

// Save the updated entity back to the database
distributor_paymentdetailRepository.save(existingPaymentDetails);

return ResponseEntity.ok("deleted successfully.");
} catch (Exception e) {
// Handle exceptions here
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update: " + e.getMessage());
}
}

//////////////////////////////////////////////////////////delete adhar//////////////////////////////////////////
@PutMapping("/distributor/delete/Kycpanimage")
public ResponseEntity<String> uploadcheck2(@RequestParam String pan) {
try {
//Check if the client with the given PAN exists
Optional<Distributor_paymentdetail> existingPaymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

if (!existingPaymentDetailsOptional.isPresent()) {
return ResponseEntity.notFound().build();
}

Distributor_paymentdetail existingPaymentDetails = existingPaymentDetailsOptional.get();
String s3Key = getS3KeyFromFilePath(existingPaymentDetails.getImagePathpan());

//Check if the object with the same key exists in the S3 bucket
if (amazonS3.doesObjectExist(bucketName, s3Key)) {
//If it exists, delete it
amazonS3.deleteObject(bucketName, s3Key);
}

//Modify the specific column you want to delete
existingPaymentDetails.setImageNamepan(null); // Set it to null, assuming you want to "delete" it.
existingPaymentDetails.setImagePathpan(null);

//Save the updated entity back to the database
distributor_paymentdetailRepository.save(existingPaymentDetails);

return ResponseEntity.ok("deleted successfully.");
} catch (Exception e) {
//Handle exceptions here
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update: " + e.getMessage());
}
}


//////////////////////////////////////////////////////////delete cheque//////////////////////////////////////////
@PutMapping("/distributor/delete/Kycachequeimage")
public ResponseEntity<String> uploadcheck3(@RequestParam String pan) {
try {
//Check if the client with the given PAN exists
Optional<Distributor_paymentdetail> existingPaymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

if (!existingPaymentDetailsOptional.isPresent()) {
return ResponseEntity.notFound().build();
}

Distributor_paymentdetail existingPaymentDetails = existingPaymentDetailsOptional.get();
String s3Key = getS3KeyFromFilePath(existingPaymentDetails.getImagePathcheque());

//Check if the object with the same key exists in the S3 bucket
if (amazonS3.doesObjectExist(bucketName, s3Key)) {
//If it exists, delete it
amazonS3.deleteObject(bucketName, s3Key);
}

//Modify the specific column you want to delete
existingPaymentDetails.setImageNamecheque(null); // Set it to null, assuming you want to "delete" it.
existingPaymentDetails.setImagePathcheque(null);

//Save the updated entity back to the database
distributor_paymentdetailRepository.save(existingPaymentDetails);

return ResponseEntity.ok("deleted successfully.");
} catch (Exception e) {
//Handle exceptions here
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update: " + e.getMessage());
}
}
/////////////////////////////////////////////////////get images////////////////////////////////////////////////////
/////////////////////////////////////////////////////get profile ////////////////////////////////////////////////////
@GetMapping("/getdistributorprofile/{pan}")
public ResponseEntity<?> getdistributorprofile(@PathVariable String pan) {

Optional<Distributor_paymentdetail> paymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

if (paymentDetailsOptional.isPresent()) {
Distributor_paymentdetail clientDetails = paymentDetailsOptional.get();
String s3Key = getS3KeyFromFilePath(clientDetails.getImagePathProfile());
try {
// Fetch the file content from S3
S3Object s3Object = amazonS3.getObject(bucketName, s3Key);

// Get the S3 object content
InputStream s3InputStream = s3Object.getObjectContent();
byte[] content = IOUtils.toByteArray(s3InputStream);

// Close the S3 object and release resources
s3InputStream.close();
s3Object.close();

// Create an InputStreamResource from the byte array content
InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(content));

// Set up headers for the response
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePathProfile()).build());

// Create a response map with both paymentDetails and content

Map<String, Object> responseMap = new HashMap<>();
//  responseMap.put("paymentDetails", clientDetails);
responseMap.put("content",content);
responseMap.put("content",resource);

return ResponseEntity.ok()
.headers(headers)
.body(resource);

// Return ResponseEntity with headers and content length

} catch (AmazonS3Exception e) {
// Handle S3 exception (e.g., object not found)
logger.error("Error fetching payment details from S3: {}", e.getMessage());
return ResponseEntity.notFound().build();
} catch (IOException e) {
// Handle IO exception
logger.error("Error reading payment details from S3: {}", e.getMessage());
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
}
} else {
logger.warn("Payment details not found for userid: {}", pan);
return ResponseEntity.notFound().build();
}
}


/////////////////////////////////////////////
/////////////////////////////////////////////////////get adhar ////////////////////////////////////////////////////
@GetMapping("/getdistributoradhar/{pan}")
public ResponseEntity<?> getdistributoradhar(@PathVariable String pan) {

Optional<Distributor_paymentdetail> paymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

if (paymentDetailsOptional.isPresent()) {
Distributor_paymentdetail clientDetails = paymentDetailsOptional.get();
String s3Key = getS3KeyFromFilePath(clientDetails.getImagePathAdhar());
try {
//Fetch the file content from S3
S3Object s3Object = amazonS3.getObject(bucketName, s3Key);

//Get the S3 object content
InputStream s3InputStream = s3Object.getObjectContent();
byte[] content = IOUtils.toByteArray(s3InputStream);

//Close the S3 object and release resources
s3InputStream.close();
s3Object.close();

//Create an InputStreamResource from the byte array content
InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(content));

//Set up headers for the response
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePathAdhar()).build());

//Create a response map with both paymentDetails and content

Map<String, Object> responseMap = new HashMap<>();
//responseMap.put("paymentDetails", clientDetails);
responseMap.put("content",content);
responseMap.put("content",resource);

return ResponseEntity.ok()
.headers(headers)
.body(resource);

//Return ResponseEntity with headers and content length

} catch (AmazonS3Exception e) {
//Handle S3 exception (e.g., object not found)
logger.error("Error fetching payment details from S3: {}", e.getMessage());
return ResponseEntity.notFound().build();
} catch (IOException e) {
//Handle IO exception
logger.error("Error reading payment details from S3: {}", e.getMessage());
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
}
} else {
logger.warn("Payment details not found for userid: {}", pan);
return ResponseEntity.notFound().build();
}
}



////////////////
/////////////////////////////////////////////////////get pancard ////////////////////////////////////////////////////
@GetMapping("/getdistributorpan/{pan}")
public ResponseEntity<?> getclientiamgekyc(@PathVariable String pan) {

Optional<Distributor_paymentdetail> paymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

if (paymentDetailsOptional.isPresent()) {
Distributor_paymentdetail clientDetails = paymentDetailsOptional.get();
String s3Key = getS3KeyFromFilePath(clientDetails.getImagePathpan());
try {
//Fetch the file content from S3
S3Object s3Object = amazonS3.getObject(bucketName, s3Key);

//Get the S3 object content
InputStream s3InputStream = s3Object.getObjectContent();
byte[] content = IOUtils.toByteArray(s3InputStream);

//Close the S3 object and release resources
s3InputStream.close();
s3Object.close();

//Create an InputStreamResource from the byte array content
InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(content));

//Set up headers for the response
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePathpan()).build());

//Create a response map with both paymentDetails and content

Map<String, Object> responseMap = new HashMap<>();
//responseMap.put("paymentDetails", clientDetails);
responseMap.put("content",content);
responseMap.put("content",resource);

return ResponseEntity.ok()
.headers(headers)
.body(resource);

//Return ResponseEntity with headers and content length

} catch (AmazonS3Exception e) {
//Handle S3 exception (e.g., object not found)
logger.error("Error fetching payment details from S3: {}", e.getMessage());
return ResponseEntity.notFound().build();
} catch (IOException e) {
//Handle IO exception
logger.error("Error reading payment details from S3: {}", e.getMessage());
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
}
} else {
logger.warn("Payment details not found for userid: {}", pan);
return ResponseEntity.notFound().build();
}
}




/////////////////////////////////////////////////////get cheque ////////////////////////////////////////////////////
@GetMapping("/getdistributorcheque/{pan}")
public ResponseEntity<?> getdistributorcheque(@PathVariable String pan) {

Optional<Distributor_paymentdetail> paymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

if (paymentDetailsOptional.isPresent()) {
Distributor_paymentdetail clientDetails = paymentDetailsOptional.get();
String s3Key = getS3KeyFromFilePath(clientDetails.getImagePathcheque());
try {
//Fetch the file content from S3
S3Object s3Object = amazonS3.getObject(bucketName, s3Key);

//Get the S3 object content
InputStream s3InputStream = s3Object.getObjectContent();
byte[] content = IOUtils.toByteArray(s3InputStream);

//Close the S3 object and release resources
s3InputStream.close();
s3Object.close();

//Create an InputStreamResource from the byte array content
InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(content));

//Set up headers for the response
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePathcheque()).build());

//Create a response map with both paymentDetails and content

Map<String, Object> responseMap = new HashMap<>();
//responseMap.put("paymentDetails", clientDetails);
responseMap.put("content",content);
responseMap.put("content",resource);

return ResponseEntity.ok()
.headers(headers)
.body(resource);

//Return ResponseEntity with headers and content length

} catch (AmazonS3Exception e) {
//Handle S3 exception (e.g., object not found)
logger.error("Error fetching payment details from S3: {}", e.getMessage());
return ResponseEntity.notFound().build();
} catch (IOException e) {
//Handle IO exception
logger.error("Error reading payment details from S3: {}", e.getMessage());
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
}
} else {
logger.warn("Payment details not found for userid: {}", pan);
return ResponseEntity.notFound().build();
}
}
/////////////////////////////////get all paymnet detail information/////////////////////////////////////////////
@GetMapping("/getdistributordetail/{pan}")
public ResponseEntity<Distributor_paymentdetail> getDistributorDetail(@PathVariable String pan) {
    Optional<Distributor_paymentdetail> paymentDetailsOptional = distributor_paymentdetailRepository.findByPan(pan);

    if (paymentDetailsOptional.isPresent()) {
        Distributor_paymentdetail distributorDetails = paymentDetailsOptional.get();
        return ResponseEntity.ok().body(distributorDetails);
    } else {
        return ResponseEntity.ok().body(null);
    }
}



}
