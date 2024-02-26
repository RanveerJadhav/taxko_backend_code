   package com.Tasko.Registration.Controller;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.print.DocFlavor.STRING;
import javax.xml.crypto.Data;

import com.Tasko.Registration.Entity.*;
import com.Tasko.Registration.Repository.*;
import com.Tasko.Registration.Service.JwtService;
import com.Tasko.Registration.dto.*;
import com.Tasko.Registration.error.*;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.razorpay.RazorpayException;

import jakarta.mail.internet.MimeMessage;
import org.apache.catalina.User;
import org.aspectj.lang.annotation.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.Tasko.Registration.Service.TaskoService;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private TaskoService taskoService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private TaskoRepository taskoRepository;


	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private Filed_NotFiledRepo filed_notFiledRepo;

	@Autowired
	private Filed_NotFiled_GST_repo gstfiled_notfiledRepo;

	@Autowired
	private Client_Payment_Details_Repo clientPaymentrepo;

	@Autowired
	private ClientPassRepository clientPassRepository;

	@Autowired
	private KycclientRepository kycclientRepository;

	@Autowired
	private DocinfoRepository docinfoRepository;

	@Autowired
	private UserEmail_helpRepository email_helpRepository;

	@Autowired
	private Subscritpion_userdataRepository subscritpion_userdataRepository;
	@Autowired
	private master_adminRepository master_adminRepository;

	@Autowired
	private Subscriptionpack_historyRepository subscriptionpack_historyRepository;
	//private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private Renewalprice_distributordataRepository renewalprice_distributordataRepository;
	@Autowired
	private DistributionRepo distributionRepo;

	@Autowired
	private UserInvestNowemailListRepository userInvestNowemailListRepository;

	@Autowired
	private User_tally_backupfileRepository user_tally_backupfileRepository;
	@Autowired
	private User_CLient_tally_backupfileRepository user_CLient_tally_backupfileRepository;

	@Autowired
	private Client_Registation_Form_Temp_Repo tempRepo;
	@Autowired
    private Salesman_RegisterRepository salesmanRegisterRepository;
	
	@Autowired
	private TransactioRepo transactioRepo;

	@Autowired
	private CA_SubUsersRepo caSubUsersRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private JavaMailSender javaMailSender;

	Logger logger = LoggerFactory.getLogger(UserController.class);


	@PostMapping("/createuser")
	public User_RegistrationsForm saveUser(@RequestBody CreateUserRequest request) throws Exception {
		User_RegistrationsForm user = request.getUser();
		List<String> invest_now_email = request.getInvest_now_email();
		logger.info("Received a request to create a user: {}", user.getPan());

		try {
			System.out.println("hii");
			Optional<DistributionReg> disdata = distributionRepo.findByPan(user.getPan());
			Optional<DistributionReg> disdata1 = distributionRepo.findByEmail(user.getEmail());
			Optional<User_RegistrationsForm> existingUserWithEmail = taskoRepository.findByEmail(user.getEmail());
			Optional<CA_SubUsers> pan=caSubUsersRepo.findByPan(request.getUser().getPan());


			if (taskoRepository.findByPan(user.getPan()).isPresent() || pan.isPresent())
			{
				throw new UserAlreadyExist("PAN Already Exist");
			}
			if (existingUserWithEmail.isPresent()) {
				throw new EmailMandatoryException("Email Already Exist");
			}
			if (disdata.isPresent()) {
				throw new UserAlreadyExist("PAN Already Exist");
			}
			if (disdata1.isPresent()) {
				throw new EmailMandatoryException("Email Already Exist");
			}
			Optional<Subscription_Userdata> usersub = subscritpion_userdataRepository.findByPan(user.getPan());
			if (user.getRefrenceId() != null) {
				Optional<DistributionReg> distrubutor = distributionRepo.findByPan(user.getRefrenceId());
				Optional<Salesman_Register> salesman= salesmanRegisterRepository.findByPan(user.getRefrenceId());
				if (distrubutor.isPresent()) {
					System.out.println("1");
					DistributionReg data=distrubutor.get();
					user.setDisrefrenceId(user.getRefrenceId());
					user.setRefrenceId(null);
					user.setDissalespersonId(data.getSalesmanid());	
					user.setSalespersonId(null);
				}
				else if(salesman.isPresent())
				{
					System.out.println("2");
					user.setDisrefrenceId(null);
					user.setDissalespersonId(null);	
					System.out.println(user.getRefrenceId());
					user.setSalespersonId(user.getRefrenceId());
				    user.setRefrenceId(null);
				}
				else {
					System.out.println("3");
					user.setDisrefrenceId(null);
					user.setDissalespersonId(null);	
					user.setSalespersonId(null);
				}
			}
			if (usersub.isEmpty()) {
				Subscription_Userdata data = new Subscription_Userdata();
				data.setAcessclient(null);
				data.setPan(user.getPan());
				data.setRegistrationdate(new Date());
				if (user.getDisrefrenceId() != null) {
					Optional<DistributionReg> distrubutor1 = distributionRepo.findByPan(user.getDisrefrenceId());
					//Optional<Salesman_Register> salesman1= salesmanRegisterRepository.findByPan(user.getRefrenceId());
					if (distrubutor1.isPresent()) {
						DistributionReg distrubutor2 = distrubutor1.get();
						data.setDisrefrenceId(distrubutor2.getPan());
						data.setDissalespersonId(distrubutor2.getSalesmanid());
						data.setSalespersonId(null);
						System.out.println("hii");
						data.setRefrenceId(null);
					} else {
						data.setDisrefrenceId(null);
						data.setRefrenceId(user.getRefrenceId());
					}
				}
				else if(user.getSalespersonId()!=null)
				{
					data.setDisrefrenceId(null);
					data.setRefrenceId(null);
					data.setDissalespersonId(null);	
					data.setSalespersonId(user.getSalespersonId());
				}
				else {
					data.setDisrefrenceId(null);
					data.setDissalespersonId(null);	
					data.setSalespersonId(null);
					data.setRefrenceId(user.getRefrenceId());
				}
				if (!invest_now_email.isEmpty()) {
					for (String email : invest_now_email) {
						UserInvest_Now_email_List investemail = new UserInvest_Now_email_List();
						investemail.setInvestNow_Email(email);
						investemail.setPan(user.getPan());
						userInvestNowemailListRepository.save(investemail);
					}
				}
				data.setForcestop(false);
				data.setName(user.getName());
				data.setMobile(user.getMobile());
				subscritpion_userdataRepository.save(data);
			}
			return taskoService.saveUser(user);
		} catch (Exception e) {
			logger.error("An error occurred while saving the user: {}", e.getMessage());
			throw e;
		}
	}

	////////////////////////////////////invest now extra email add and save by user////////////////
	@PostMapping("/Invest_now/save-emails/{pan}")
	public ResponseEntity<String> saveEmails(@PathVariable String pan, @RequestBody List<String> invest_now_email) {
		try {
			if (!invest_now_email.isEmpty()) {
				for (String email : invest_now_email) {
					UserInvest_Now_email_List investemail = new UserInvest_Now_email_List();
					investemail.setInvestNow_Email(email);
					investemail.setPan(pan);
					userInvestNowemailListRepository.save(investemail);
				}

				return new ResponseEntity<>("Emails saved successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No emails provided", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to save emails", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/////////////////get investnow email by user for update a record///////////////////////
	@GetMapping("/Invest_now/get_all/by-pan/{pan}")
	public List<UserInvest_Now_email_List> getInvestNowEmailsByPan(@PathVariable String pan) {
		List<UserInvest_Now_email_List> result = userInvestNowemailListRepository.findByPan(pan);
		return result;
	}

	////////////// Invest_Now_email delete email by //////////////////////////////////////////////
	@DeleteMapping("/Invest_now/delete_by_id")
	public void deleteInvestNowEmailByIds(@RequestBody List<Long> ids) {
		for (Long id : ids) {
			Optional<UserInvest_Now_email_List> existingRecord = userInvestNowemailListRepository.findById(id);

			if (existingRecord.isPresent()) {
				userInvestNowemailListRepository.deleteById(id);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record with ID " + id + " not found");
			}
		}
	}

	///////////////////////create admin//////////////////////////////////////////////////
	@PostMapping("/api/master-admin")
	public ResponseEntity<String> createMasterAdmin(@RequestBody MasterAdminRequest request) {
		try {
			master_admin masterAdmin = new master_admin();
			masterAdmin.setUsername(request.getUsername());
			masterAdmin.setPassword(passwordEncoder.encode(request.getPassword()));
			master_adminRepository.save(masterAdmin);

			return ResponseEntity.ok("MasterAdmin created successfully.");
		} catch (DataIntegrityViolationException e) {
			// Handle the exception for duplicate username, if that's the case
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
		} catch (Exception e) {
			// Handle other exceptions here
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the MasterAdmin.");
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/getuser")
	public List<User_RegistrationsForm> FetchUser(Filed_NotFiled f) {
		logger.info("Received a request to fetch users.");

		try {
			List<User_RegistrationsForm> users = taskoService.FetchUser();
			logger.info("Fetched {} users.", users.size());
			return users;
		} catch (Exception e) {
			logger.error("An error occurred while fetching users: {}", e.getMessage());
			throw e;
		}
	}

	@DeleteMapping("/deleteuser/{id}")
	public String deleteUserById(@PathVariable("id") Long RegId) {
		logger.info("Received a request to delete user with ID: {}", RegId);

		try {
			taskoService.deleteUserById(RegId);
			logger.info("User with ID {} has been deleted.", RegId);
			return "User Deleted!";
		} catch (Exception e) {
			logger.error("An error occurred while deleting user with ID {}: {}", RegId, e.getMessage());
			throw e;
		}
	}

	@PutMapping("/updateuser/{id}")
	public User_RegistrationsForm updateUser(@RequestBody User_RegistrationsForm user, @PathVariable("id") Long RegId) {
		logger.info("Received a request to update user with ID: {}", RegId);

		try {
			User_RegistrationsForm updatedUser = taskoService.updateUser(user, RegId);
			logger.info("User with ID {} has been updated.", RegId);
			return updatedUser;
		} catch (Exception e) {
			logger.error("An error occurred while updating user with ID {}: {}", RegId, e.getMessage());
			throw e;
		}
	}

	@PutMapping("/updateClient/{id}")
	public Client_Registation_Form updateClient(@RequestBody Client_Registation_Form client, @PathVariable("id") Long clientId) {
		logger.info("Received a request to update client with ID: {}", clientId);

		try {
			Client_Registation_Form updatedClient = taskoService.updateClient(client, clientId);
			logger.info("Client with ID {} has been updated.", clientId);
			return updatedClient;
		} catch (Exception e) {
			logger.error("An error occurred while updating client with ID {}: {}", clientId, e.getMessage());
			throw e;
		}
	}

	@Transactional
	@PostMapping("/createclient")
	public ResponseEntity<String> saveclient(@RequestBody Client_Registation_Form client) {
		try {

			Long count = clientRepository.countoftotalclientByUserid(client.getUserid());
			count = (count != null) ? count : 0L;
			Optional<Subscription_Userdata> infodata = subscritpion_userdataRepository.findByUserid(client.getUserid());
			Subscription_Userdata info = infodata.get();
			Long a = info.getAcessclient();

			Optional<Client_Registation_Form_Temp> temppan=tempRepo.findByPan(client.getPan());
			if(temppan.isPresent())
			{
				tempRepo.deleteByPan(client.getPan());
			}


			if (count >= a) {
				return ResponseEntity.ok("your subscription pack is not valid for create extra client");
			} else {
				// Check if the client already exists in the database based on PAN
				Optional<ClientPass_Imgdetail> client1 = clientPassRepository.findByPan(client.getPan());

				if (client1.isEmpty()) {
					// If the client doesn't exist, perform the necessary actions (e.g., saving details)
					taskoService.savedetail(client.getPan(), client.getEmail());
				}

				Optional<Kyc_client_detail> client2 = kycclientRepository.findByPan(client.getPan());

				if (client2.isEmpty()) {
					Kyc_client_detail client3 = new Kyc_client_detail();
					client3.setPan(client.getPan());
					// If the client doesn't exist, perform the necessary actions (e.g., saving details)
					kycclientRepository.save(client3);
				}

				Optional<docinfo> client4 = docinfoRepository.findByPan(client.getPan());

				if (client4.isEmpty()) {
					docinfo client5 = new docinfo();
					client5.setPan(client.getPan());
					// If the client doesn't exist, perform the necessary actions (e.g., saving details)
					docinfoRepository.save(client5);
				}

				System.out.println("con1");
				// Save the client registration form
				ResponseEntity<Client_Registation_Form> savedClient = taskoService.saveclient(client);
				System.out.println("user registered");

				// Return a success response
				return ResponseEntity.ok("Client registered successfully");
			}
		} catch (Exception e) {
			// Handle the exception here, you can log it or return an error response
			e.printStackTrace(); // Log the exception for debugging

			// Return an error response with a specific error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create client account: " + e.getMessage());
		}
	}


	@GetMapping("getClientById/{userid}/{clientId}")
	public Client_Registation_Form getClientByClientidUserid(@PathVariable Long userid,
															 @PathVariable Long clientId) throws UserNotFoundException {

		logger.info("Received a request to get client with ID {} for user ID {}", clientId, userid);
		try {
			Client_Registation_Form client = taskoService.getClientByClientidUserid(userid, clientId);
			logger.info("Client with ID {} for user ID {} has been fetched.", clientId, userid);
			return client;
		} catch (UserNotFoundException e) {
			logger.error("Client not found for user ID {} and client ID {}: {}", userid, clientId, e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("An error occurred while fetching client: {}", e.getMessage());
			throw e;
		}
	}

	@GetMapping("getClientByIdAndcategory/{userid}/{clientId}")
	public Client_Registation_Form getClientByClientidUseridAndcategory(@PathVariable Long userid,
																		@PathVariable Long clientId) throws UserNotFoundException {

		logger.info("Received a request to get client with ID {} and category for user ID {}", clientId, userid);

		try {
			Client_Registation_Form client = taskoService.getClientByClientidUseridAndcategory("Income_Tax", userid, clientId);
			logger.info("Client with ID {} and category for user ID {} has been fetched.", clientId, userid);
			return client;
		} catch (UserNotFoundException e) {
			logger.error("Client not found for user ID {} and client ID {}: {}", userid, clientId, e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("An error occurred while fetching client: {}", e.getMessage());
			throw e;
		}

	}

	@GetMapping("/counts/{userid}")
	public ResponseEntity<ClientCountsDTO> getClientCountsByUserId(@PathVariable("userid") Long userid) {

		logger.info("Received a request to get client counts for user ID: {}", userid);

		try {
			ClientCountsDTO countsDTO = taskoService.getClientCountsByUserId(userid);
			logger.info("Client counts fetched successfully for user ID: {}", userid);
			return ResponseEntity.ok(countsDTO);
		} catch (Exception e) {
			logger.error("An error occurred while fetching client counts: {}", e.getMessage());
			throw e;
		}
	}

	@GetMapping("/getClientByUserid/{id}")
	public List<Client_Registation_Form> getClientByUserid(@PathVariable("id") Long userid) {
		logger.info("Received a request to get clients for user ID: {}", userid);

		try {
			List<Client_Registation_Form> clients = taskoService.getClientByUserid(userid);
			logger.info("Clients fetched successfully for user ID: {}", userid);
			return clients;
		} catch (Exception e) {
			logger.error("An error occurred while fetching clients: {}", e.getMessage());
			throw e;
		}
	}

	@GetMapping("/getClientByIncomeTax/{id}")
	public List<Client_Registation_Form> getcategory(@PathVariable("id") Long userid) {
		logger.info("Received a request to get clients with category 'Income_Tax' for user ID: {}", userid);

		try {
			List<Client_Registation_Form> clients = taskoService.getcategory(userid);
			logger.info("Clients with category 'Income_Tax' fetched successfully for user ID: {}", userid);
			return clients;
		} catch (Exception e) {
			logger.error("An error occurred while fetching clients with category 'Income_Tax': {}", e.getMessage());
			throw e;
		}
	}


	@GetMapping("/getClientByGst/{id}")
	public List<Client_Registation_Form> GetCategoryGst(@PathVariable("id") Long userid) {
		return clientRepository.findOfGSTByUserid(userid);
	}


	//--------------------------File Upload--------------------------------------------------------
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file,
							 @RequestParam("userid") Long userid,
							 @RequestParam("clientid") Long clientid,
							 @RequestParam("accountyear") String accountyear,
							 @RequestParam("filename") String fileName) throws MultipartException, FileAlreadyExists, IOException {
		logger.info("Received a request to upload a file for user ID {} and client ID {} with account year {} and filename {}",
				userid, clientid, accountyear, fileName);

		try {
			taskoService.uploadFile(userid, clientid, accountyear, file, fileName);
			logger.info("File uploaded successfully for user ID {} and client ID {}.", userid, clientid);
			return "File uploaded successfully";
		} catch (MultipartException | FileAlreadyExists e) {
			logger.error("Error occurred during file upload: {}", e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.error("An I/O error occurred during file upload: {}", e.getMessage());
			throw e;
		}
	}

	@GetMapping("getfile/{userid}/{clientid}/{accountyear}")
	public ResponseEntity<List<FileEntity>> getFiles(@PathVariable Long userid,
													 @PathVariable Long clientid,
													 @PathVariable String accountyear) {
		logger.info("Received a request to get files for user ID {}, client ID {}, and account year {}",
				userid, clientid, accountyear);

		try {
			List<FileEntity> files = taskoService.getFilesByUserAndClientAndYear(userid, clientid, accountyear);
			logger.info("Files fetched successfully for user ID {}, client ID {}, and account year {}",
					userid, clientid, accountyear);
			return ResponseEntity.ok(files);
		} catch (Exception e) {
			logger.error("An error occurred while fetching files: {}", e.getMessage());
			throw e;
		}

	}

	@GetMapping("/download/{id}/{userid}/{clientid}/{accountyear}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id,
												 @PathVariable Long userid,
												 @PathVariable Long clientid,
												 @PathVariable String accountyear) throws IOException {
		logger.info("Received a request to download a file with ID {} for user ID {}, client ID {}, and account year {}",
				id, userid, clientid, accountyear);

		try {
			Resource resource = taskoService.getFile(id, userid, clientid, accountyear);
			logger.info("File with ID {} fetched successfully for download for user ID {}, client ID {}, and account year {}",
					id, userid, clientid, accountyear);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (IOException e) {
			logger.error("An I/O error occurred while downloading file: {}", e.getMessage());
			throw e;
		}
	}

	//-----------------------------delete File By id-----------------------
	@Autowired
	private AmazonS3 amazonS3;
	String bucketName = "arkonet";

	@DeleteMapping("/deletefile")
	@Transactional
	public ResponseEntity<String> deleteFiles(@RequestBody DeleteFilesRequest request) {
		try {
			for (Long fileId : request.getFileIds()) {
				Optional<FileEntity> fileEntityOptional = fileRepository.findById(fileId);
				fileEntityOptional.ifPresent(fileEntity -> {
					String s3Key = fileEntity.getFilePath();// Assuming the S3 object key is the same as the filename

					// Find the last index of '/' character
					int lastIndex = s3Key.lastIndexOf('/');

					// Extract the filename from the input string
					String newVariable = s3Key.substring(lastIndex + 1);


					try {
						// Delete the file from S3
						amazonS3.deleteObject(new DeleteObjectRequest(bucketName, newVariable));
					} catch (AmazonServiceException e) {
						// Handle AWS S3 service exceptions
						e.printStackTrace();
						// Log the error for debugging purposes
						logger.error("Failed to delete file from S3: {}", e.getMessage());
						throw e; // Re-throw the exception to trigger transaction rollback
					}
					// Delete the record from the database
					fileRepository.deleteById(fileId);
				});
			}
			return ResponseEntity.ok("Files deleted successfully.");
		} catch (Exception e) {
			// Handle any other exceptions that might occur during file deletion or database operations
			e.printStackTrace();
			// Log the error for debugging purposes
			logger.error("Failed to delete files: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete files.");
		}
	}

	//----------------------------Open File------------------------

	@GetMapping("/openfile/{fileId}")
	public ResponseEntity<Resource> openFile(@PathVariable Long fileId) {
		try {
			// Fetch the file information from your database (file repository)
			Optional<FileEntity> fileEntityOptional = fileRepository.findById(fileId);

			if (fileEntityOptional.isPresent()) {
				FileEntity fileEntity = fileEntityOptional.get();
				String s3Key = getS3KeyFromFilePath(fileEntity.getFilePath());

				// Fetch the file content from S3
				S3Object s3Object = amazonS3.getObject(bucketName, s3Key);
				byte[] content = IOUtils.toByteArray(s3Object.getObjectContent());

				// Set up headers
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.setContentDisposition(ContentDisposition.attachment().filename(fileEntity.getFileName()).build());

				// Create a resource from the S3 content
				InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(content));

				return ResponseEntity.ok()
						.headers(headers)
						.body(resource);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			// Handle exceptions appropriately
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Helper method to extract S3 key from the S3 URL
	private String getS3KeyFromFilePath(String s3FilePath) {
		// Assuming your S3 URL format is "s3://bucketName/s3Key"
		String[] parts = s3FilePath.split("/");
		return parts[parts.length - 1];
	}

	//------------------------Change Password----------------------------------------------------------------
	@PutMapping("/changePassword/{regId}")
	public ResponseEntity<String> changePassword(@PathVariable long regId, @RequestBody ChangePassword request) {
		logger.info("Received a request to change password for user with ID: {}", regId);

		try {
			if (taskoService.isOldPasswordCorrect(regId, request.getOldPassword())) {
				taskoService.updatePassword(regId, request.getNewPassword());
				logger.info("Password changed successfully for user with ID: {}", regId);
				return ResponseEntity.ok("Password changed successfully.");
			} else {
				logger.warn("Attempt to change password failed for user with ID {}. Invalid old password.", regId);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid old password.");
			}
		} catch (Exception e) {
			logger.error("An error occurred while changing the password for user with ID {}: {}", regId, e.getMessage());
			throw e;
		}
	}

	//-------------------------Forget Password----------------------------------------------------------------
	@PostMapping("/send-otp")
	public ResponseEntity<String> sendOTPByEmail(@RequestParam String pan) throws UserNotFoundException {
		logger.info("Received a request to send OTP by email for PAN: {}", pan);

		try {
			taskoService.sendOTPByEmail(pan);
			logger.info("OTP sent successfully for PAN: {}", pan);
			return ResponseEntity.ok("OTP sent successfully.");
		} catch (UserNotFoundException e) {
			logger.error("User not found for PAN {}: {}", pan, e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("An error occurred while sending OTP for PAN {}: {}", pan, e.getMessage());
			throw e;
		}
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOTP(@RequestParam String otp) throws OtpNotVaild {
		logger.info("Received a request to verify OTP");

		try {
			boolean isValidOTP = taskoService.verifyOTP(otp);

			if (isValidOTP) {
				logger.info("OTP verification successful!");
				return ResponseEntity.ok("OTP verification successful!");
			} else {
				logger.warn("Invalid OTP provided");
				return ResponseEntity.badRequest().body("Invalid OTP!");
			}
		} catch (OtpNotVaild e) {
			logger.error("OTP verification failed: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("An error occurred while verifying OTP: {}", e.getMessage());
			throw e;
		}
	}

	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam String otp, @RequestParam String newPassword) throws OtpNotVaild {
		logger.info("Received a request to reset password");

		try {
			taskoService.resetPassword(otp, newPassword);
			logger.info("Password reset successful!");
			return ResponseEntity.ok("Password reset successful!");
		} catch (OtpNotVaild e) {
			logger.error("Password reset failed: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("An error occurred while resetting password: {}", e.getMessage());
			throw e;
		}
	}
	//-------------------------------Filed_Notfiled save data---------------------------------------------------------


	@PostMapping("/saveData")
	public ResponseEntity<String> saveData(@RequestBody Filed_NotFiled data) {
		try {
			logger.info("Received a request to save data for user ID {}, client ID {}, and account year {}",
					data.getUserid(), data.getClientid(), data.getAccountyear());

			if (filed_notFiledRepo.existsByUseridAndClientidAndAccountyear(
					data.getUserid(), data.getClientid(), data.getAccountyear())) {
				logger.warn("Data already exists for user ID {}, client ID {}, and account year {}",
						data.getUserid(), data.getClientid(), data.getAccountyear());
				return ResponseEntity.ok("Data already exists!");
			}

			filed_notFiledRepo.save(data);
			logger.info("Data saved successfully for user ID {}, client ID {}, and account year {}",
					data.getUserid(), data.getClientid(), data.getAccountyear());
			return ResponseEntity.ok("Data saved successfully!");
		} catch (Exception e) {
			logger.error("An error occurred while saving data: {}", e.getMessage());
			throw e;
		}
	}

	//-------------filed_Notfiled Update----------
	@PutMapping("/updateFiledNotFiled/{userid}/{clientid}/{accountyear}")
	public void updateFiledNotFiled(@PathVariable Long userid,
									@PathVariable Long clientid,
									@PathVariable String accountyear) {
		logger.info("Received a request to update filed/not filed status for user ID {}, client ID {}, and account year {}",
				userid, clientid, accountyear);

		try {
			taskoService.updateFiledNotFiled(userid, clientid, accountyear);
			logger.info("Filed/not filed status updated successfully for user ID {}, client ID {}, and account year {}",
					userid, clientid, accountyear);
		} catch (Exception e) {
			logger.error("An error occurred while updating filed/not filed status: {}", e.getMessage());
			throw e;
		}
	}

	//---------------------------Counts Of filed-Notfiled-----------------------

	@GetMapping("/filedNotfiledCounts/{userid}")
	public ResponseEntity<List<filed_NotfiledDTO>> getFileCountsByUser(@PathVariable Long userid) {
		logger.info("Received a request to get filed/not filed counts for user ID {}", userid);

		try {
			List<filed_NotfiledDTO> fileCounts = taskoService.getFileCountsByUser(userid);
			logger.info("Filed/not filed counts fetched successfully for user ID {}", userid);
			return ResponseEntity.ok(fileCounts);
		} catch (Exception e) {
			logger.error("An error occurred while fetching filed/not filed counts: {}", e.getMessage());
			throw e;
		}
	}

	//--------------get status of filed-Notfiled-----------------------
	@GetMapping("/getfilednotfiled/{userid}/{clientid}/{accountyear}")
	public ResponseEntity<List<Map<String, String>>> getData(
			@PathVariable Long userid,
			@PathVariable Long clientid,
			@PathVariable String accountyear) {
		logger.info("Received a request to get filed/not filed data for user ID {}, client ID {}, and account year {}",
				userid, clientid, accountyear);

		try {
			List<Filed_NotFiled> dataEntities = filed_notFiledRepo.findFilednotfiledByUseridAndClientidAndAccountyear(userid, clientid, accountyear);

			List<Map<String, String>> response = new ArrayList<>();
			for (Filed_NotFiled entity : dataEntities) {
				Map<String, String> item = new HashMap<>();
				item.put("filednotfiled", entity.getFilednotfiled());
				response.add(item);
			}

			logger.info("Filed/not filed data fetched successfully for user ID {}, client ID {}, and account year {}",
					userid, clientid, accountyear);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("An error occurred while fetching filed/not filed data: {}", e.getMessage());
			throw e;
		}
	}

	//-------------------------------------------Find Max Date Of filed-Notfiled-----------------------------------
	@GetMapping("/maxLastUpdateDate/{id}")
	public ResponseEntity<Map<String, String>> getMaxLastUpdateDateByUserId(@PathVariable("id") Long userid) {
		Optional<LocalDate> maxLastUpdateDate = Optional.ofNullable(filed_notFiledRepo.findMaxLastUpdateDateByUserid(userid));
		if (maxLastUpdateDate.isPresent()) {
			Map<String, String> response = new HashMap<>();
			response.put("lastUpdateDate", maxLastUpdateDate.get().toString());
			return ResponseEntity.ok(response);
		}
		else
		{
			Map<String, String> response = new HashMap<>();
			response.put("message", "Not_Found");
			return ResponseEntity.ok(response);
		}
	}



	//----------------------------------------------Login Page------------------------------------------------------------------------------------------------
	@PostMapping("/authenticate")
	public ResponseEntity<Object> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws Exception
	{
		logger.info("Received an authentication request for username: {}", authRequest.getUsername());

		try {
			// Check if PAN is present in the specified tables
			Optional<User_RegistrationsForm> user = taskoRepository.findByPan(authRequest.getUsername());

			Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(authRequest.getUsername());

			Optional<CA_SubUsers> caSubuser = caSubUsersRepo.findByPan(authRequest.getUsername());

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			if (user.isPresent() || subscription.isPresent() || caSubuser.isPresent())
			{
				// Check subscription and update if needed
				Subscription_Userdata subscriptionInfo = subscription.orElse(null);

				if (subscriptionInfo != null && subscriptionInfo.getSubendtdate() != null)
				{
					Date currentDate = new Date();
					Date end_date = subscriptionInfo.getSubendtdate();
					long differenceInMillis = currentDate.getTime() - end_date.getTime();
					long days = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
					if (currentDate.after(end_date))
					{
						subscriptionInfo.setPaid(false);
						subscritpion_userdataRepository.save(subscriptionInfo);
					}
				}

				if (authentication.isAuthenticated())
				{
					Subscription_Userdata infodata = subscriptionInfo;
					if (infodata == null) {
						infodata = subscritpion_userdataRepository.findByPan(authRequest.getUsername()).orElse(null);
					}

					String jwt = jwtService.generateToken(authRequest.getUsername());
					boolean isPaid = infodata != null && infodata.isPaid();
					authuser response = new authuser(user, jwt, infodata != null ? infodata.getSubendtdate() : null, isPaid);
					response.setToken(jwt);
					response.setUser(Optional.ofNullable(user.orElse(null)));
					response.setSub_user(Optional.ofNullable(caSubuser.orElse(null)));
					response.setSubenddate(infodata != null ? infodata.getSubendtdate() : null);
					response.setValue(isPaid);

					logger.info("User {} has been authenticated successfully.", authRequest.getUsername());
					return ResponseEntity.ok(response);
				}
			} else {
				throw new Exception("User Not Found");
			}

		} catch (Exception e) {
			logger.error("Authentication failed for user {}: {}", authRequest.getUsername(), e.getMessage());
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(e.getMessage());
		}
		return ResponseEntity.ok(null);
	}
//	@PostMapping("/authenticate")
//	public ResponseEntity<Object> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws Exception {
//		logger.info("Received an authentication request for username: {}", authRequest.getUsername());
//		try {
//			Optional<User_RegistrationsForm> user = taskoRepository.findByPan(authRequest.getUsername());
//			Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(authRequest.getUsername());
//			Optional<CA_SubUsers> caSubuser = caSubUsersRepo.findByPan(authRequest.getUsername());
//
//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//
//			if (user.isPresent() || subscription.isPresent())
//			{
//				Subscription_Userdata subscriptionInfo = subscription.get();
//
//				if (subscriptionInfo.getSubendtdate() != null)
//				{
//					Date currentDate = new Date();
//					Date end_date = subscriptionInfo.getSubendtdate();
//					long differenceInMillis = currentDate.getTime() - end_date.getTime();
//					long days = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
//					if (currentDate.after(end_date))
//					{
//						subscriptionInfo.setPaid(false);
//						subscritpion_userdataRepository.save(subscriptionInfo);
//					}
//				}
//				if (authentication.isAuthenticated()) {
//					Optional<Subscription_Userdata> inforamtion = subscritpion_userdataRepository.findByPan(authRequest.getUsername());
//					Subscription_Userdata infodata = inforamtion.get();
//					String jwt = jwtService.generateToken(authRequest.getUsername());
//					boolean isPaid = infodata.isPaid();
//					authuser response = new authuser(user, jwt, infodata.getSubendtdate(), isPaid);
//					response.setToken(jwt);
//					response.setUser(user);
//					response.setSub_user(Optional.ofNullable(caSubuser.orElse(null)));
//					response.setSubenddate(infodata.getSubendtdate());
//					response.setValue(isPaid);
//					System.out.println(infodata.getSubendtdate());
//					logger.info("User {} has been authenticated successfully.", authRequest.getUsername());
//					return ResponseEntity.ok(response);
//				}
//			} else {
//				throw new Exception("User Not Found");
//			}
//
//		} catch (Exception e)
//		{
//			logger.error("Authentication failed for user {}: {}", authRequest.getUsername(), e.getMessage());
//			return ResponseEntity
//					.status(HttpStatus.UNAUTHORIZED)
//					.body(e.getMessage());
//		}
//		return ResponseEntity.ok(null);
//
//	}


	//////////////////////admin login////////////////////////////////////////////////////

	@PostMapping("/authenticate/admin")
	public ResponseEntity<Object> authenticateAndGetTokenadmin(@RequestBody AuthRequest authRequest) throws Exception
	{
		logger.info("Received an authentication request for username: {}", authRequest.getUsername());

		try {

			Optional<master_admin> user = master_adminRepository.findByusername(authRequest.getUsername());

			if (user.isPresent()) {
				master_admin data = user.get();
				if (!data.getUsername().equals(authRequest.getUsername())) {
					throw new Exception("Invalid User-Name");
				}
			}
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			if (user.isPresent()) {
				if (authentication.isAuthenticated())
				{
					String jwt = jwtService.generateToken(authRequest.getUsername());

					authadmin response = new authadmin(user, jwt);
					response.setToken(jwt);
					response.setUser(user);

					logger.info("User {} has been authenticated successfully.", authRequest.getUsername());
					return ResponseEntity.ok(response);
				}
			}
			else {
				throw new Exception("User Not Found");
			}


		} catch (Exception e) {
			logger.error("Authentication failed for user {}: {}", authRequest.getUsername(), e.getMessage());
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(e.getMessage());
		}
		return ResponseEntity.ok(null);
	}
	///////////////

	@GetMapping("/check")
	public String getLoggingData() {
		logger.info("Data from getdata()");
		return "usercontroller";
	}

	//------------------------------------------------Get User By Id----------------------------------------------------------------

	@GetMapping("/getuserByid/{id}")
	public ResponseEntity<User_RegistrationsForm> getUserById(@PathVariable("id") Long regId) {
		Optional<User_RegistrationsForm> user = taskoRepository.findById(regId);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	//------------------------------------------------Save Payment Details-----------------------------------------------------------
	@PostMapping("/paymentDetails")
	public ResponseEntity<String> savepaymentDetails(@RequestParam("userid") Long userid,
													 @RequestParam("image") MultipartFile image,
													 @RequestParam("QR") MultipartFile qrCode,
													 @RequestParam("Bank_Name") String bank_name,
													 @RequestParam("AccountName") String accountName,
													 @RequestParam("AccountNumber") Long accountNumber,
													 @RequestParam("IFSC") String ifsc,
													 @RequestParam("UPI_ID") String upiId,
													 @RequestParam("UPI_Number") String upiNumber) throws IOException, UserAlreadyExist {

		taskoService.savepaymentDetails(userid, image, qrCode, bank_name, accountName, accountNumber, ifsc, upiId, upiNumber);
		return ResponseEntity.ok("Payment Details Saved!");
	}

	//------------------------------------------Update Payment Details-----------------------------------------------------------
	@Autowired
	private paymentDetailsRepo detailsRepo;


	@PutMapping("/UpdatePaymentDetails/{userid}")
	public ResponseEntity<Object> updatePaymentDetails(
			@PathVariable Long userid,
			@RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam(value = "QR", required = false) MultipartFile qrCode,
			@RequestParam(value = "Bank_Name", required = false) String bankName,
			@RequestParam(value = "AccountName", required = false) String accountName,
			@RequestParam(value = "AccountNumber", required = false) Long accountNumber,
			@RequestParam(value = "IFSC", required = false) String ifsc,
			@RequestParam(value = "UPI_ID", required = false) String upiId,
			@RequestParam(value = "UPI_Number", required = false) String upiNumber) throws IOException {

		Optional<Payment_Details> existingPaymentDetailsOptional = detailsRepo.findByUserid(userid);

		if (!existingPaymentDetailsOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Payment_Details existingPaymentDetails = existingPaymentDetailsOptional.get();

		// Update the payment details if new values are provided
		if (image != null) {
			User_RegistrationsForm u = taskoRepository.findById(userid).get();
			String pan = u.getPan();
			String imageName = generateUniqueName(userid, pan);
			//  String imageName = generateUniqueName(userid, accountName);
			String name = image.getOriginalFilename();
			String[] result = name.split("\\.");
			String fileExtension = result[result.length - 1];

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(image.getContentType());
			InputStream inputStream = image.getInputStream();
			String s3Key = imageName + "." + fileExtension;
			//    amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));


			// String s4Key = imageName;

			// Check if the object with the same key exists in the S3 bucket
			if (amazonS3.doesObjectExist(bucketName, s3Key)) {
				// If it exists, delete it
				amazonS3.deleteObject(bucketName, s3Key);
			}

			// Upload the new object
			amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

			existingPaymentDetails.setImageName(imageName);
			existingPaymentDetails.setImagePath("s3://" + bucketName + "/" + s3Key);
		}

		if (qrCode != null) {
			existingPaymentDetails.setQrCode(qrCode.getBytes());
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

		if (upiId != null) {
			existingPaymentDetails.setUpiId(upiId);
		}

		if (upiNumber != null) {
			existingPaymentDetails.setUpiNumber(upiNumber);
		}

		// Save the updated payment details

		Payment_Details updatedPaymentDetails = detailsRepo.save(existingPaymentDetails);

		return ResponseEntity.ok("Data Updated Sucessfull!");
	}


	private String generateUniqueName(Long userid, String pan) {
		return userid + "_" + pan;
	}
	//-------------------------------------------Get Payment Details-----------------------------------------------------------
	@GetMapping("/getpaymentDetails/{userid}")
	public ResponseEntity<?> getAllPaymentDetails(@PathVariable Long userid) {
		logger.info("Fetching payment details for userid: {}", userid);

		Optional<Payment_Details> paymentDetailsOptional = detailsRepo.findByUserid(userid);

		if (paymentDetailsOptional.isPresent()) {
			Payment_Details paymentDetails = paymentDetailsOptional.get();
			String s3Key = getS3KeyFromFilePath(paymentDetails.getImagePath());

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
				headers.setContentDisposition(ContentDisposition.attachment().filename(paymentDetails.getImagePath()).build());

				// Create a response map with both paymentDetails and content
				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("paymentDetails", paymentDetails);
				responseMap.put("content", content);

				logger.info("Payment details found for userid: {}", userid);

				// Return ResponseEntity with headers and content length
				return ResponseEntity.ok(responseMap);
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
			logger.warn("Payment details not found for userid: {}", userid);
			return ResponseEntity.notFound().build();
		}
	}

	//----------------------------------------------GST file upload-------------------------------------------------------------
	@PostMapping("/GSTFileUpload")
	public String GSTFileUpload(@RequestParam("file") MultipartFile file,
								@RequestParam("userid") Long userid,
								@RequestParam("clientid") Long clientid,
								@RequestParam("category") String category,
								@RequestParam("month") String month,
								@RequestParam("financialYear") String financialYear) throws MultipartException, FileAlreadyExists, IOException {
		logger.info("Received GST file upload request for user={}, client={}, category={}, month={}, financialYear={}",
				userid, clientid, category, month, financialYear);
		try {
			taskoService.GSTFileUpload(file, userid, clientid, category, month, financialYear);
			logger.info("File uploaded successfully for user ID {} and client ID {}.", userid, clientid);
			return "File uploaded successfully";
		} catch (MultipartException e) {
			logger.error("Error occurred during file upload: {}", e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.error("An I/O error occurred during file upload: {}", e.getMessage());
			throw e;
		}
	}

	//////////////////////////////////Get GST file by user//////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/Gstgetfile/{userid}/{clientid}/{financialYear}/{category}")
	public ResponseEntity<List<GST_FileUpload>> getFiles1(@PathVariable Long userid,
														  @PathVariable Long clientid,
														  @PathVariable String financialYear, @PathVariable String category) {
		logger.info("Received a request to get files for user ID {}, client ID {}, and account year {}",
				userid, clientid);

		try {
			List<GST_FileUpload> files = gst_FileUploadRepo.findByUseridAndClientidAndFinancialYearAndCategory(userid, clientid, financialYear, category);
			logger.info("Files fetched successfully for user ID {}, client ID {}, and account year {}",
					userid, clientid);
			return ResponseEntity.ok(files);
		} catch (Exception e) {
			logger.error("An error occurred while fetching files: {}", e.getMessage());
			throw e;
		}

	}

/////////////////////////////////////////////////Get delete by user/////////////////////////////////////////////////////////////////////////

	@Autowired
	private GST_FileUploadRepo gst_FileUploadRepo;

	@DeleteMapping("/gstdeletefile")
	@Transactional
	public ResponseEntity<String> deleteFiles1(@RequestBody DeleteFilesRequest request) {
		try {
			for (Long fileId : request.getFileIds()) {
				Optional<GST_FileUpload> fileEntityOptional = gst_FileUploadRepo.findById(fileId);
				fileEntityOptional.ifPresent(GST_FileUpload -> {
					String s3Key = GST_FileUpload.getFilePath();// Assuming the S3 object key is the same as the filename

// Find the last index of '/' character
					int lastIndex = s3Key.lastIndexOf('/');

// Extract the filename from the input string
					String newVariable = s3Key.substring(lastIndex + 1);

					try {
// Delete the file from S3
						amazonS3.deleteObject(new DeleteObjectRequest(bucketName, newVariable));
					} catch (AmazonServiceException e) {
// Handle AWS S3 service exceptions
						e.printStackTrace();
// Log the error for debugging purposes
						logger.error("Failed to delete file from S3: {}", e.getMessage());
						throw e; // Re-throw the exception to trigger transaction rollback
					}
// Delete the record from the database
					gst_FileUploadRepo.deleteById(fileId);
				});
			}
			return ResponseEntity.ok("Files deleted successfully.");
		} catch (Exception e) {
// Handle any other exceptions that might occur during file deletion or database operations
			e.printStackTrace();
// Log the error for debugging purposes
			logger.error("Failed to delete files: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete files.");
		}
	}

	////////////////////////////////////////////////////////open file gst//////////////////////////////////////////////////////////////
	@GetMapping("/openGstfile/{fileId}")
	public ResponseEntity<Resource> openFile1(@PathVariable Long fileId) {
		try {
// Fetch the file information from your database (file repository)
			Optional<GST_FileUpload> fileEntityOptional = gst_FileUploadRepo.findById(fileId);

			if (fileEntityOptional.isPresent()) {
				GST_FileUpload fileEntity = fileEntityOptional.get();
				String s3Key = getS3KeyFromFilePath(fileEntity.getFilePath());

// Fetch the file content from S3
				S3Object s3Object = amazonS3.getObject(bucketName, s3Key);
				byte[] content = IOUtils.toByteArray(s3Object.getObjectContent());

// Set up headers
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.setContentDisposition(ContentDisposition.attachment().filename(fileEntity.getFileName()).build());

// Create a resource from the S3 content
				InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(content));

				return ResponseEntity.ok()
						.headers(headers)
						.body(resource);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
// Handle exceptions appropriately
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	//
	@DeleteMapping("/deleteFile1/{id}")
	public ResponseEntity<String> deleteFileById(@PathVariable Long id) {
		try {
			gst_FileUploadRepo.deleteById(id);
			return ResponseEntity.noContent().build(); // 204 No Content for success
		} catch (Exception e) {
// Handle any exceptions that might occur during deletion
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file: " + e.getMessage());
		}
	}

	//-------------------------------------------------------------GST Filed_Notfiled Update----------------------------------------------------
	@PutMapping("/GSTupdateFiledNotFiled/{userid}/{clientid}/{month}/{financialYear}/{category}")
	public ResponseEntity<String> GSTupdateFiledNotFiled(
			@PathVariable Long userid,
			@PathVariable Long clientid,
			@PathVariable String month,
			@PathVariable String financialYear,
			@PathVariable String category) {
		try {
			// Call your service to update the "FiledNotFiled" status
			taskoService.GSTupdateFiledNotFiled(userid, clientid, month, financialYear, category);

			// Return a success response
			return ResponseEntity.ok("Update successful");
		} catch (Exception e) {
			// Handle exceptions and return an error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed: " + e.getMessage());
		}
	}

	//---------------------------Counts Of GST Filed_Notfiled-----------------------
	@GetMapping("/getGSTData")
	public ResponseEntity<Map<String, List<GST_filed_NotfiledDTO>>> getGSTDataByCategory(@RequestParam Long userid) {
		Map<String, List<Map<String, Object>>> data = taskoService.getDataByCategory(userid);

		// You need to map the data to the GSTDataResponse DTO
		Map<String, List<GST_filed_NotfiledDTO>> response = new LinkedHashMap<>();

		data.forEach((category, entries) -> {
			List<GST_filed_NotfiledDTO> categoryEntries = new ArrayList<>();
			for (Map<String, Object> entry : entries) {
				GST_filed_NotfiledDTO dto = new GST_filed_NotfiledDTO();
				dto.setCategory((String) entry.get("category"));
				dto.setMonth((String) entry.get("month"));
				dto.setFiled((Long) entry.get("filed"));
				dto.setNotfiled((Long) entry.get("notfiled"));
				categoryEntries.add(dto);
			}
			response.put(category, categoryEntries);
		});

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
//--------------------------------------------Status OF GST Filed_Notfiled-----------------------------
//output month add in response

	@GetMapping("/GST_Statusfilednotfiled/{userid}/{clientid}/{financialYear}/{category}")
	public ResponseEntity<List<Map<String, String>>> GSTgetData(
			@PathVariable Long userid,
			@PathVariable Long clientid,
			@PathVariable String financialYear,
			@PathVariable String category) {

		List<Filed_NotFiled_GST> data = gstfiled_notfiledRepo.findFilednotfiledByUseridAndClientidAndFinancialYearAndCategory(userid, clientid, financialYear, category);

		List<Map<String, String>> response = new ArrayList<>();
		for (Filed_NotFiled_GST entity : data) {
			Map<String, String> item = new HashMap<>();
			item.put("month", entity.getMonth()); // Assuming you have a "month" field
			item.put("filednotfiled", entity.getFilednotfiled());
			response.add(item);
		}

		return ResponseEntity.ok(response);
	}
//@GetMapping("/GST_Statusfilednotfiled/{userid}/{clientid}/{financialYear}/{category}")
//public ResponseEntity<List<Map<String, String>>> GSTgetData(
//		@PathVariable Long userid,
//        @PathVariable Long clientid,
//        @PathVariable String financialYear,
//        @PathVariable String category)
//{
//
//
//
//List<Filed_NotFiled_GST> data = gstfiled_notfiledRepo.findFilednotfiledByUseridAndClientidAndFinancialYearAndCategory(userid, clientid, financialYear,category);
//
//List<Map<String, String>> response = new ArrayList<>();
//for (Filed_NotFiled_GST entity : data) 
//{
//	Map<String, String> item = new HashMap<>();
//	item.put("filednotfiled", entity.getFilednotfiled());
//	response.add(item);	
//}
//    return ResponseEntity.ok(response);
//}
//-------------------------------------------Find Max Date Of GST Filed_Notfiled-----------------------------------

	@GetMapping("/GSTmaxLastUpdateDate/{userid}")
	public ResponseEntity<Map<String, String>> GSTgetMaxLastUpdateDateByUserId(@PathVariable Long userid) {
		LocalDate maxLastUpdateDate = gstfiled_notfiledRepo.findMaxLastUpdateDateByUserid(userid);

		if (maxLastUpdateDate != null) {
			Map<String, String> result = new HashMap<>();
			result.put("MaxDate", maxLastUpdateDate.toString());
			return ResponseEntity.ok(result);
		}
		else
		{
			Map<String, String> response = new HashMap<>();
			response.put("message", "Not_Found");
			return ResponseEntity.ok(response);
		}
	}

	/////////////////////////////////////////////Client_Payment_Details Save////////////////////////////////////////////////
	@PostMapping("/Client_Payment_Details")
	public Client_Payment_Details saveClient_Payment_Details(@RequestBody Client_Payment_Details pay) {
		return taskoService.saveClient_Payment_Details(pay);
	}

	//---------------------------------------------------Sum Of Client_Payment_Details----------------------------------------------
	@GetMapping("/sumOFPaymentClientByUserid/{userid}")
	public Map<String, Object> getPaymentSumsByUserid(@PathVariable Long userid) {
		return taskoService.getPaymentSumsByUserid(userid);

	}


//------------------------------------------update ReceivedPayment Client_Payment_Details---------------------------------------

	@PutMapping("/updateClientPaymentDetails/{userid}/{clientid}/{receivedPayment}/{discount}/{totalPayment}")
	public Client_Payment_Details updatePayment(@PathVariable Long userid, @PathVariable Long clientid, @PathVariable Long receivedPayment, @PathVariable Long discount, @PathVariable Long totalPayment
	) throws EnterValidAmount {
		Client_Payment_Details payment = clientPaymentrepo.findByUseridAndClientid(userid, clientid);
		if (payment != null) {
			Long totalPaym = payment.getTotalPayment() + totalPayment;
			payment.setTotalPayment(totalPaym);

			Long checkReceivedPayment = receivedPayment + payment.getReceivedPayment();
			payment.setReceivedPayment(checkReceivedPayment);

			Long pendingPayment = totalPaym - checkReceivedPayment;
			Long p = pendingPayment - payment.getDiscount();
			payment.setPendingPayment(p);

			if (discount != 0) {
				Long newdiscount = discount + payment.getDiscount();
				payment.setDiscount(newdiscount);

				Long totalDis = payment.getTotalPayment() - newdiscount;

				Long pendingP = totalDis - payment.getReceivedPayment();
				payment.setPendingPayment(pendingP);

				if (pendingP < 0) {
					throw new EnterValidAmount("Enter Valid Amount!");
				}

			}

			if (pendingPayment < 0) {
				throw new EnterValidAmount("Enter Valid Amount!");
			}
			payment.setLastUpdateDate(new Date());
			return clientPaymentrepo.save(payment);
		} else {
			return null;
		}
	}

	//------------------------------------------------------------Sum Of Client_Payment_Details----------------------------------
	@Autowired
	private ClientPayment clientPayment; // Ensure you have the correct repository interface


	@GetMapping("/sumOFPaymentClient/{userid}/{clientid}")
	public Map<String, Object> getPaymentSumsByUseridAndClientid(@PathVariable Long userid, @PathVariable Long clientid) {
		List<Client_Payment_Details> payments = clientPayment.findByUseridAndClientid(userid, clientid);

		Long totalPaymentSum = payments.stream().mapToLong(Client_Payment_Details::getTotalPayment).sum();
		Long receivedPaymentSum = payments.stream().mapToLong(Client_Payment_Details::getReceivedPayment).sum();
		Long pendingPaymentSum = payments.stream().mapToLong(Client_Payment_Details::getPendingPayment).sum();
		Long discountPaymentSum = payments.stream().mapToLong(Client_Payment_Details::getDiscount).sum();

		Map<String, Object> sums = new HashMap<>();
		sums.put("totalPayment", totalPaymentSum);
		sums.put("receivedPayment", receivedPaymentSum);
		sums.put("pendingPayment", pendingPaymentSum);
		sums.put("discountPayment", discountPaymentSum);

		// Find the latest update date
		Date latestUpdateDate = null;
		for (Client_Payment_Details payment : payments) {
			if (latestUpdateDate == null || payment.getLastUpdateDate().after(latestUpdateDate)) {
				latestUpdateDate = payment.getLastUpdateDate();
			}
		}

		if (latestUpdateDate != null) {
			java.text.SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			String formattedDate = dateFormat.format(latestUpdateDate);
			sums.put("Last Update Date", formattedDate);
		} else {
			sums.put("Last Update Date", null); // Handle the case where there are no payment details.
		}

		return sums;
	}

	//------------------------------------------------Total Pending List---------------------------------
	@GetMapping("/clientInfoByPayment/{userid}/{year}")
	public ResponseEntity<List<Client_Registation_Form>> getClientInfoByPayment(@PathVariable Long userid, @PathVariable String year) {
		Long pendingPaymentThreshold = 0L; // You can adjust this value as needed

		List<Client_Payment_Details> paymentDetails = clientPaymentrepo.findByUseridAndYearAndPendingPaymentGreaterThan(userid, year, pendingPaymentThreshold);

		if (paymentDetails.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Long> clientIds = paymentDetails.stream().map(Client_Payment_Details::getClientid).collect(Collectors.toList());

			List<Client_Registation_Form> clientInfoList = clientRepository.findByClientIdIn(clientIds);

			if (clientInfoList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(clientInfoList, HttpStatus.OK);
			}
		}
	}


	//////////////////////////////////////////////////////////////////////////////////////send invest now email/////////////////////////
	@PostMapping("/sendemailuser/help")
	public String sendEmail(
			@RequestParam Long userid,
			@RequestParam String subject,
			@RequestBody String body
	) {

		logger.info("Client with ID {} for user ID {} has been fetched.", userid);


		Optional<User_RegistrationsForm> user = taskoRepository.findById(userid);
		User_RegistrationsForm userinfo = user.get();
		UserEmail_help investdetail = new UserEmail_help();
		investdetail.setUserid(userid);
		investdetail.setSubject(subject);
		investdetail.setDetail(body);
		investdetail.setDate(new Date());
		investdetail.setName(userinfo.getName());
		email_helpRepository.save(investdetail);
		User_RegistrationsForm userdata = user.get();
		List<String> recipientEmails = new ArrayList<>();
		recipientEmails.add(userdata.getEmail());
//		if(userdata.getInvestNow_Email()!=null)
//		{
//			recipientEmails.add(userdata.getInvestNow_Email());
//		}
		for (String to : recipientEmails) {
			taskoService.sendEmailwithattachmentUserhelp(to, subject, body);
		}

		return "Email sent successfully.";
	}

	/////////////////////get only which file current update in income tax//////////////////////////////////////

	@GetMapping("/getlastUpdateallIncome_Taxonefileinfo/{id}")
	public ResponseEntity<Map<String, Object>> getMaxLastUpdateDateincometaxtable(@PathVariable("id") Long clientid) throws IOException {
		Long id = fileRepository.findIdByClientId2(clientid);
		//System.out.println(maxLastUpdateDate7);
		Optional<FileEntity> pdfdetail = fileRepository.findById(id);
		FileEntity cliemtpdf = pdfdetail.get();
		cliemtpdf.getFileName();
		cliemtpdf.getFilePath();

		if (pdfdetail.isPresent()) {
			FileEntity clientDetails = pdfdetail.get();
			String s3Key = getS3KeyFromFilePath(clientDetails.getFilePath());
			System.out.println(s3Key);
// Create a response map with both paymentDetails and content
//				Map<String, Object> responseMap = new HashMap<>();
//				responseMap.put("data", clientDetails);
//				return ResponseEntity.ok(responseMap);
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("imagePath", clientDetails.getFilePath());
			responseMap.put("imageName", clientDetails.getFileName());
			responseMap.put("lastUpdateDate", clientDetails.getLastUpdateDate());
			return ResponseEntity.ok(responseMap);
			// Return ResponseEntity with headers and content length
		} else {
			return ResponseEntity.notFound().build();
		}

	}


/////////////////////get only which actual file current update in income tax//////////////////////////////////////

	@GetMapping("/getlastUpdateallIncome_Taxonefile/{id}")
	public ResponseEntity<InputStreamResource> getMaxLastUpdateDateincometaxtablefile(@PathVariable("id") Long clientid) throws IOException {
		Long id = fileRepository.findIdByClientId2(clientid);
//System.out.println(maxLastUpdateDate7);
		Optional<FileEntity> pdfdetail = fileRepository.findById(id);
		FileEntity cliemtpdf = pdfdetail.get();
		cliemtpdf.getFileName();
		cliemtpdf.getFilePath();

		if (pdfdetail.isPresent()) {
			FileEntity fileEntity = pdfdetail.get();
			String s3Key = getS3KeyFromFilePath(fileEntity.getFilePath());

			// Fetch the file content from S3
			S3Object s3Object = amazonS3.getObject(bucketName, s3Key);
			byte[] content = IOUtils.toByteArray(s3Object.getObjectContent());

			// Set up headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDisposition(ContentDisposition.attachment().filename(fileEntity.getFileName()).build());

			// Create a resource from the S3 content
			InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(content));

			return ResponseEntity.ok()
					.headers(headers)
					.body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	/////////////// gets the GST file Upload info in gst upload photo
	@GetMapping("/getlastUpdateallGSTonefileinfo/{id}")
	public ResponseEntity<Map<String, Object>> getMaxLastUpdateDateGSTtable(@PathVariable("id") Long clientid) throws IOException {
		Long sid = gst_FileUploadRepo.findIdByClientId2(clientid);
		System.out.println(sid);
		Optional<GST_FileUpload> fileEntityOptional = gst_FileUploadRepo.findById(sid);
		if (fileEntityOptional.isPresent()) {
			GST_FileUpload clientDetails = fileEntityOptional.get();
			String s3Key = getS3KeyFromFilePath(clientDetails.getFilePath());
			System.out.println(s3Key);
			// Create a response map with both paymentDetails and content
//			Map<String, Object> responseMap = new HashMap<>();
//			responseMap.put("data", clientDetails);
//			return ResponseEntity.ok(responseMap);
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("imagePath", clientDetails.getFilePath());
			responseMap.put("imageName", clientDetails.getFileName());
			responseMap.put("lastUpdateDate", clientDetails.getLastUpdateDate());
			return ResponseEntity.ok(responseMap);
			// Return ResponseEntity with headers and content length
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	/////////////////gets the actual file in gst upload table/////////////////////////////
	@GetMapping("/getlastUpdateallGSTactualonefile/{id}")
	public ResponseEntity<InputStreamResource> getMaxLastUpdateDateGSTtablefile(@PathVariable("id") Long clientid) throws IOException {
		Long sid = gst_FileUploadRepo.findIdByClientId2(clientid);
		System.out.println(sid);
		Optional<GST_FileUpload> fileEntityOptional = gst_FileUploadRepo.findById(sid);
		if (fileEntityOptional.isPresent()) {
			GST_FileUpload fileEntity = fileEntityOptional.get();
			String s3Key = getS3KeyFromFilePath(fileEntity.getFilePath());

			// Fetch the file content from S3
			S3Object s3Object = amazonS3.getObject(bucketName, s3Key);
			byte[] content = IOUtils.toByteArray(s3Object.getObjectContent());

			// Set up headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDisposition(ContentDisposition.attachment().filename(fileEntity.getFileName()).build());

			// Create a resource from the S3 content
			InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(content));

			return ResponseEntity.ok()
					.headers(headers)
					.body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	//////////all data show in subscription pack table/////////////////////////////
	@Autowired
	private Subscription_packRepository subscriptionPackRepository;

	@GetMapping("/subscriptionPacks")
	public List<subsciption_Pack> findSubscriptionPacks() {
		try {
			return subscriptionPackRepository.findAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving subscription packs", e);
		}
	}

	/////////////////////////////////////////////get subscriptionpackbyid//////////////////////
	@GetMapping("/subscriptionPacks/byid/{id}")
	public ResponseEntity<Optional<subsciption_Pack>> findSubscriptionPacksbyid(@PathVariable("id") Long id) {
		try {
			Optional<subsciption_Pack> subscriptionPack = subscriptionPackRepository.findById(id);

			if (subscriptionPack != null) {
				return ResponseEntity.ok(subscriptionPack);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving subscription pack", e);
		}
	}


	////////////////////////////add subcription pack a new record///////////////////////////////////////
	@PostMapping("/addnew/saveSubscriptionPack")
	public ResponseEntity<subsciption_Pack> saveSubscriptionPack(@RequestBody subsciption_Pack subscriptionPack) {
		try {
			subsciption_Pack savedSubscriptionPack = subscriptionPackRepository.save(subscriptionPack);
			return new ResponseEntity<>(savedSubscriptionPack, HttpStatus.CREATED);
		} catch (Exception e) {
			// Handle the exception and return an appropriate error response
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	/////////////update subscritption pack////////////////////////

	@PutMapping("/update/saveSubscriptionPack/{id}")
	public ResponseEntity<subsciption_Pack> updateSubscriptionPack(@PathVariable Long id, @RequestBody subsciption_Pack updatedSubscriptionPack) {
		try {
			subsciption_Pack existingSubscriptionPack = subscriptionPackRepository.findById(id).orElse(null);

			if (existingSubscriptionPack != null) {
				// Update the existing record with the new data
				existingSubscriptionPack.setSubtype(updatedSubscriptionPack.getSubtype());
				existingSubscriptionPack.setAccesscliet(updatedSubscriptionPack.getAccesscliet());
				existingSubscriptionPack.setSubscriptionprice(updatedSubscriptionPack.getSubscriptionprice());

				subsciption_Pack updatedPack = subscriptionPackRepository.save(existingSubscriptionPack);
				return new ResponseEntity<>(updatedPack, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// Handle the exception and return an appropriate error response
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//////delete a particular subscription pack///////////

	@DeleteMapping("/delete/saveSubscriptionPack/{id}")
	public ResponseEntity<String> deleteSubscriptionPack(@PathVariable Long id) {
		try {
			subsciption_Pack existingSubscriptionPack = subscriptionPackRepository.findById(id).orElse(null);

			if (existingSubscriptionPack != null) {
				subscriptionPackRepository.delete(existingSubscriptionPack);
				return ResponseEntity.ok("Record deleted successfully");
			} else {
				return ResponseEntity.ok("Record not found");
			}
		} catch (Exception e) {
			// Handle the exception and return an appropriate error response
			return new ResponseEntity<>("Failed to delete record: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

/////////////////////////////master admin first board total user and user data///////////////////////

	@GetMapping("/allusersdata/counts")
	public ResponseEntity<Map<String, Object>> alladminsdata() {
		try {
			String count = taskoRepository.CountOfTotaluser();
			List<User_RegistrationsForm> users = taskoRepository.findAll();
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("count", count);
			responseMap.put("users", users);

			return ResponseEntity.ok(responseMap);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving subscription packs", e);
		}
	}

	//////////////////////////master admin first board find data by user regid/////////////////////
	@GetMapping("/allusersdata/{id}") // Corrected the URL mapping
	public ResponseEntity<User_RegistrationsForm> allAdminsDataByRegId(@PathVariable("id") long regid) {
		try {
			User_RegistrationsForm user = taskoRepository.findByRegId(regid);

			if (user != null) {
				return ResponseEntity.ok(user);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving user data", e);
		}
	}


///////////////////this api use to board 2 all user and profession count///////

	@GetMapping("/countByProfession")
	public ResponseEntity<List<ProfessionCountDTO>> countByProfession() {
		List<Object[]> result = taskoRepository.CountOfByProfession(); // Execute the query

		List<ProfessionCountDTO> professionCounts = new ArrayList<>();
		for (Object[] row : result) {
			String profession = (String) row[0];
			long count = (Long) row[1];
			professionCounts.add(new ProfessionCountDTO(profession, count));
		}
		return ResponseEntity.ok(professionCounts);
	}


///////////////////////////////every  profession by////////////////////////////////////////////////////////

	@GetMapping("/user/countbyprofession")
	public ResponseEntity<List<ProfessionCount>> countprofessionby() {
		try {
			Long count1 = taskoRepository.countOfTotalprofession1();
			Long count2 = taskoRepository.countOfTotalprofession2();
			Long count3 = taskoRepository.countOfTotalprofession3();
			Long count4 = taskoRepository.countOfTotalprofession4();
			Long count5 = taskoRepository.countOfTotalprofession5();
			Long count6 = taskoRepository.countOfTotalprofession6();
			Long count7 = taskoRepository.countOfTotalprofession7();

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
	@GetMapping("/by-profession/{profession}")
	public ResponseEntity<List<RegistrationWithCountResponse>> getUserRegistrationsByProfession(@PathVariable String profession)
	{
		try {
			List<RegistrationWithCountResponse> responseList = new ArrayList<>();

			List<User_RegistrationsForm> registrations = taskoRepository.findByprofession(profession);
			for (User_RegistrationsForm registration : registrations) {
				String pan = registration.getPan();
				Long userid=registration.getRegId();
				Long countSubuser=caSubUsersRepo.countOfByUserid(userid);
				countSubuser = (countSubuser != null) ? countSubuser : 0L;
				System.out.println(pan);
				Long count = taskoRepository.countOfByRefrenceId(pan);
				// Ensure count is not null, default to 0L if null
				count = (count != null) ? count : 0L;
				Optional<Subscription_Userdata> data = subscritpion_userdataRepository.findByPan(pan);
				Subscription_Userdata infodata = data.get();
				boolean status = infodata.isPaid();
				boolean forceStatus=infodata.isForcestop();
				String type=infodata.getSubscriptiontype();
				Date a = infodata.getSubstartdatebyuser();
				if (a == null) {
					a = null; // Setting it to null if it's not already null
				}
				System.out.println(count);
				Date b = infodata.getSubendtdate();
				if (b == null) {
					b = null;
				}
				RegistrationWithCountResponse response = new RegistrationWithCountResponse(registration, count,countSubuser ,status, a, b,forceStatus,type);
				responseList.add(response);
			}
			return ResponseEntity.ok(responseList);
		} catch (Exception e) {
			// Handle the exception here, you can log it or return an error response
			e.printStackTrace(); // Replace with proper logging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	//////////////////////////////////////////////////////////all records of admin///////////////////////////////////////////////
///////////////////////////this data is table 2 then all record in profession wise////////////////////
	@GetMapping("/by-profession/all")
	public ResponseEntity<List<RegistrationWithCountResponse>> getUserRegistrationsall()
	{
		try {
			List<RegistrationWithCountResponse> responseList = new ArrayList<>();

			List<User_RegistrationsForm> registrations = taskoRepository.findAll();
			for (User_RegistrationsForm registration : registrations)
			{
				String pan = registration.getPan();
				Long userid = registration.getRegId();

				Long countSubuser = caSubUsersRepo.countOfByUserid(userid);
				countSubuser = (countSubuser != null) ? countSubuser : 0L;

				Long count = taskoRepository.countOfByRefrenceId(pan);
// Ensure count is not null, default to 0L if null
				count = (count != null) ? count : 0L;
				Optional<Subscription_Userdata> data = subscritpion_userdataRepository.findByPan(pan);
				Subscription_Userdata infodata = data.get();
				boolean status = infodata.isPaid();
				boolean forceStatus=infodata.isForcestop();
				String type=infodata.getSubscriptiontype();
				Date a = infodata.getSubstartdatebyuser();

				if (a == null) {
					a = null; // Setting it to null if it's not already null
				}
				Date b = infodata.getSubendtdate();
				if (b == null) {
					b = null;
				}
				System.out.println(count);
				RegistrationWithCountResponse response = new RegistrationWithCountResponse(registration, count, countSubuser, status, a, b,forceStatus,type);
				responseList.add(response);
			}
			return ResponseEntity.ok(responseList);
		} catch (Exception e) {
// Handle the exception here, you can log it or return an error response
			e.printStackTrace(); // Replace with proper logging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


	/////////////////////// all total client pan and name table 3///////////////////////////////////////////////
	@GetMapping("/allclient/distinct-pan")
	public List<Object[]> getDistinctPanValues() {
		try {
			return clientRepository.countDistinctPanAndName();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching distinct PAN values.", e);
		}
	}


	/////////////////////////////////////all total count client pan and name table 3////////////////////////////////////
	@GetMapping("/allclient/client-count")
	public List<ClientCount> getClientCount() {
		try {
			Long totalClientCount = clientRepository.CountOfTotalclient();
			Long incomeTaxCount = clientRepository.countOfTotalClientIncomeTax();
			Long gstCount = clientRepository.countOfTotalClientGST();

			// Set default values to 0 if a count is not available
			totalClientCount = (totalClientCount != null) ? totalClientCount : 0L;
			incomeTaxCount = (incomeTaxCount != null) ? incomeTaxCount : 0L;
			gstCount = (gstCount != null) ? gstCount : 0L;

			List<ClientCount> responseList = new ArrayList<>();
			responseList.add(new ClientCount("Total Client", totalClientCount));
			responseList.add(new ClientCount("Income Tax", incomeTaxCount));
			responseList.add(new ClientCount("GST", gstCount));

			return responseList;
		} catch (Exception e) {
			// You can log the exception here if needed
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching client count.", e);
		}
	}

	//////////////////////////////////client  by all client//////////////////////////////////////////////
	@GetMapping("/listofallclients/allclient")
	public ResponseEntity<Map<String, Object>> getClientsByAll() {
		try {
			List<Client_Registation_Form> users = clientRepository.findAll();
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("users", users);
			return ResponseEntity.ok(responseMap);
		} catch (Exception e) {
// Handle exceptions (e.g., database errors)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.emptyMap()); // or handle the error accordingly
		}
	}

	//////////////////////////////////client  by category//////////////////////////////////////////////
	@GetMapping("/clients-by-categories/{categories}")
	public Map<String, Object> getClientsByCategories(@PathVariable List<String> categories) {
		List<Client_Registation_Form> User = clientRepository.findAllByCategoryIn(categories);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("users", User);
		return responseMap;

	}

	////////////////////////////////////////////////counts of income tax clients///////////////////////////////////////////
	@GetMapping("/countIncomeTaxClients")
	public ResponseEntity<Long> countIncomeTaxClients() {
		try {
			Long count = clientRepository.countOfTotalClientIncomeTax();
			return ResponseEntity.ok(count);
		} catch (Exception e) {
			// Handle the exception here, you can log it or return an error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	////////////////////////////////////////////////counts of income tax GST///////////////////////////////////////////
	@GetMapping("/countGSTClients")
	public ResponseEntity<Long> countGSTClients() {
		try {
			Long count = clientRepository.countOfTotalClientGST();
			return ResponseEntity.ok(count);
		} catch (Exception e) {
			// Handle the exception here, you can log it or return an error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

///////////////////////////////////////subscription logic///////////////////////////////////////////////////
/////////////add subscription////////////////////////
//@PutMapping("/Subscription/{pan}")
//public ResponseEntity<String> updateSubscription(@PathVariable String pan, @RequestParam("aceesclient") Long aceesclient,@RequestParam("userid") Long userid,@RequestParam("image") MultipartFile image,@RequestParam("subscriptionprice") Long subscriptionprice,@RequestParam("subscriptiontype") String subscriptiontype) throws IOException {
//    // Find the subscription record by PAN
//    Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(pan);
//    Subscription_Userdata subscriptionInfo=subscription.get();
//   
//    if (subscription == null) {
//    	
//        return ResponseEntity.notFound().build();
//        
//    }
//
//    // Update the fields with data from the DTO
//    subscriptionInfo.setAcessclient(aceesclient);
//    if (subscriptionInfo.getSubstartdatebyuser() == null) {
//        subscriptionInfo.setSubstartdatebyuser(new Date());
//    }
//    
//        subscriptionInfo.setSubstartdate(new Date());
//    if (image != null && !image.isEmpty()) {
//        String pannum = subscriptionInfo.getPan();
//        Date date=subscriptionInfo.getSubstartdate();
//        String imageName = generateUniqueDoc(pannum,date);
//        String name = image.getOriginalFilename();
//        String[] result = name.split("\\.");
//        String fileExtension = result[result.length - 1];
//
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType(image.getContentType());
//        InputStream inputStream = image.getInputStream();
//        String s3Key = imageName +"_"+name+"." + fileExtension;
//
//        // Check if the object with the same key exists in the S3 bucket
//        if (amazonS3.doesObjectExist(bucketName, s3Key)) {
//            // If it exists, delete it
//            amazonS3.deleteObject(bucketName, s3Key);
//        }
//
//        // Upload the new object
//        amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
//
//        subscriptionInfo.setImageName(name);
//        subscriptionInfo.setImagePath("s3://" + bucketName + "/" + s3Key);
//    } 
//    subscriptionInfo.setSubscriptionprice(subscriptionprice);
//    subscriptionInfo.setSubscriptiontype(subscriptiontype);
//    subscriptionInfo.setUserid(userid);
//    // Update other fields as needed
//
//    // Save the updated subscription record
//     subscritpion_userdataRepository.save(subscriptionInfo);
//
//    return ResponseEntity.ok("Subscription updated successfully");
//    
//}

	//private String generateUniqueDoc(String pannum,Date date) {
//    return pannum +"_"+date;
//}
	@PutMapping("/Subscription/{pan}")
	public ResponseEntity<String> updateSubscription(@PathVariable String pan, @RequestParam("aceesclient") Long aceesclient, @RequestParam("userid") Long userid, @RequestParam("attachmentContent") MultipartFile attachmentContent, @RequestParam("subscriptionprice") Long subscriptionprice, @RequestParam("subscriptiontype") String subscriptiontype, @RequestParam("subject") String subject, @RequestParam("text") String text) throws IOException {
		try {	// Find the subscription record by PAN
		Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(pan);
		Subscription_Userdata subscriptionInfo = subscription.get();

		if (subscription == null) {
			return ResponseEntity.notFound().build();
		}
		if (subscriptiontype.equals("Trial")) {
			subscriptionInfo.setSubstartdatebyadmin(new Date());
			Calendar calendar = Calendar.getInstance();
			// Get the current date
			Date currentDate = new Date();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DATE, 15);
			Date endDate = calendar.getTime();
			subscriptionInfo.setSubendtdate(endDate);
			subscriptionInfo.setPaid(true);
			subscritpion_userdataRepository.save(subscriptionInfo);
			return ResponseEntity.ok("Subscription updated successfully");
		}
		else
		{
		if (aceesclient == 50 || aceesclient == 100) {
			Long a = subscriptionInfo.getAcessclient();
			subscriptionInfo.setAcessclient(aceesclient + a);
			subscritpion_userdataRepository.save(subscriptionInfo);
			if (attachmentContent != null && !attachmentContent.isEmpty()) {
				String recipient = "txkspprt@gmail.com";
				String attachmentFileName = attachmentContent.getOriginalFilename();
				taskoService.sendEmailWithAttachment11(recipient, subject, text, attachmentContent, attachmentFileName);
			}
		}
		else if (subscriptionInfo.getSubendtdate() != null && subscriptionInfo.getSubendtdate().compareTo(new Date()) >= 0) 
		{
		    // Your code here
			subscriptionInfo.setAcessclient(aceesclient);
			if (subscriptionInfo.getSubstartdatebyuser() == null) {
				subscriptionInfo.setSubstartdatebyuser(new Date());
			}

			subscriptionInfo.setSubstartdate(new Date());
			if (attachmentContent != null && !attachmentContent.isEmpty()) {
				String recipient = "txkspprt@gmail.com";
				String attachmentFileName = attachmentContent.getOriginalFilename();
				taskoService.sendEmailWithAttachment11(recipient, subject, text, attachmentContent, attachmentFileName);
			}
			Date startDate = subscriptionInfo.getSubendtdate();
			LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localEndDate = localStartDate.plusDays(365);
			// Convert LocalDate back to Date
			Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

			subscriptionInfo.setSubendtdate(endDate);
			subscriptionInfo.setPaid(true);
			subscriptionInfo.setSubscriptionprice(subscriptionprice);
			subscriptionInfo.setSubscriptiontype(subscriptiontype);
			subscriptionInfo.setUserid(userid);
			// Update other fields as needed

			// Save the updated subscription record
			subscritpion_userdataRepository.save(subscriptionInfo);
			if (subscriptionInfo.getDisrefrenceId() != null) {
				Renewalprice_distributordata data = new Renewalprice_distributordata();
				data.setName(subscriptionInfo.getName());
				data.setPan(subscriptionInfo.getDisrefrenceId());
				data.setSubstartdate(subscriptionInfo.getSubstartdate());
				data.setSubstartdatebyadmin(subscriptionInfo.getSubstartdatebyadmin());
				data.setUserpan(subscriptionInfo.getPan());
				Long a = subscriptionInfo.getSubscriptionprice();
				double bPercentage = 0.15;
				double b = a * bPercentage;
				// If you want the result as a Long, you can cast it
				Long bAsLong = (long) b;
				data.setRenewalPrice(bAsLong);
				renewalprice_distributordataRepository.save(data);
			}
		}
		else if(subscriptionInfo.getSubendtdate() != null && subscriptionInfo.getSubendtdate().compareTo(new Date()) < 0)
		{
			subscriptionInfo.setSubstartdatebyadmin(new Date());
			subscriptionInfo.setAcessclient(aceesclient);
			if (subscriptionInfo.getSubstartdatebyuser() == null) {
				subscriptionInfo.setSubstartdatebyuser(new Date());
			}

			subscriptionInfo.setSubstartdate(new Date());
			Date currentDate = new Date();

			// Add 365 days to the current date
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DATE, 365);
			Date endDate = calendar.getTime();

			subscriptionInfo.setSubendtdate(endDate);
			subscriptionInfo.setPaid(true);
			if (attachmentContent != null && !attachmentContent.isEmpty()) {
				String recipient = "txkspprt@gmail.com";
				String attachmentFileName = attachmentContent.getOriginalFilename();
				taskoService.sendEmailWithAttachment11(recipient, subject, text, attachmentContent, attachmentFileName);
			}
			subscriptionInfo.setSubscriptionprice(subscriptionprice);
			subscriptionInfo.setSubscriptiontype(subscriptiontype);
			subscriptionInfo.setUserid(userid);
			// Update other fields as needed
			// Save the updated subscription record
			subscritpion_userdataRepository.save(subscriptionInfo);
			if (subscriptionInfo.getDisrefrenceId() != null) {
				Renewalprice_distributordata data = new Renewalprice_distributordata();
				data.setName(subscriptionInfo.getName());
				data.setPan(subscriptionInfo.getDisrefrenceId());
				data.setSubstartdate(subscriptionInfo.getSubstartdate());
				data.setSubstartdatebyadmin(subscriptionInfo.getSubstartdatebyadmin());
				data.setUserpan(subscriptionInfo.getPan());
				Long a = subscriptionInfo.getSubscriptionprice();
				double bPercentage = 0.15;
				double b = a * bPercentage;
				// If you want the result as a Long, you can cast it
				Long bAsLong = (long) b;
				data.setRenewalPrice(bAsLong);
				renewalprice_distributordataRepository.save(data);
			}
			
		}
	  else {
			// Update the fields with data from the DTO
		  subscriptionInfo.setSubstartdatebyadmin(new Date());
			subscriptionInfo.setAcessclient(aceesclient);
			if (subscriptionInfo.getSubstartdatebyuser() == null) {
				subscriptionInfo.setSubstartdatebyuser(new Date());
			}

			subscriptionInfo.setSubstartdate(new Date());
			Date currentDate = new Date();

			// Add 365 days to the current date
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DATE, 365);
			Date endDate = calendar.getTime();

			subscriptionInfo.setSubendtdate(endDate);
			subscriptionInfo.setPaid(true);
			if (attachmentContent != null && !attachmentContent.isEmpty()) {
				String recipient = "txkspprt@gmail.com";
				String attachmentFileName = attachmentContent.getOriginalFilename();
				taskoService.sendEmailWithAttachment11(recipient, subject, text, attachmentContent, attachmentFileName);
			}
			subscriptionInfo.setSubscriptionprice(subscriptionprice);
			subscriptionInfo.setSubscriptiontype(subscriptiontype);
			subscriptionInfo.setUserid(userid);
			// Update other fields as needed

			// Save the updated subscription record
			subscritpion_userdataRepository.save(subscriptionInfo);
			if (subscriptionInfo.getRefrenceId() != null) {
				String pan1 = subscriptionInfo.getRefrenceId();
				Optional<Subscription_Userdata> subscriptionrefer = subscritpion_userdataRepository.findByPan(pan1);
				Subscription_Userdata subscriptionrefer1 = subscriptionrefer.get();
				if (subscriptionrefer1.getSubendtdate() != null && new Date().compareTo(subscriptionrefer1.getSubendtdate()) <= 0) {
					Date date = subscriptionrefer1.getSubendtdate();
					Calendar calendar2 = Calendar.getInstance();
					calendar2.setTime(date); // Set the calendar to the provided date
					calendar2.add(Calendar.DAY_OF_MONTH, 30); // Add 30 days to the date
					Date newDate = calendar2.getTime();
					subscriptionrefer1.setSubendtdate(newDate);
					subscritpion_userdataRepository.save(subscriptionrefer1);
				}
				subscriptionInfo.setRefrenceId(null);
				subscritpion_userdataRepository.save(subscriptionInfo);

			}
			if (subscriptionInfo.getDisrefrenceId() != null) {
				Renewalprice_distributordata data = new Renewalprice_distributordata();
				data.setName(subscriptionInfo.getName());
				data.setPan(subscriptionInfo.getDisrefrenceId());
				data.setSubstartdate(subscriptionInfo.getSubstartdate());
				data.setSubstartdatebyadmin(subscriptionInfo.getSubstartdatebyadmin());
				data.setUserpan(subscriptionInfo.getPan());
				Long a = subscriptionInfo.getSubscriptionprice();
				double bPercentage = 0.25;
				double b = a * bPercentage;
				// If you want the result as a Long, you can cast it
				Long bAsLong = (long) b;
				data.setRenewalPrice(bAsLong);
				renewalprice_distributordataRepository.save(data);
			}
		}
		Subscriptionpack_history historyInfo = new Subscriptionpack_history();
		historyInfo.setUserid(subscriptionInfo.getUserid());
		historyInfo.setPan(subscriptionInfo.getPan());
		historyInfo.setDisrefrenceId(subscriptionInfo.getDisrefrenceId());
		historyInfo.setDissalespersonId(subscriptionInfo.getDissalespersonId());
		historyInfo.setSalespersonId(subscriptionInfo.getSalespersonId());
		historyInfo.setAcessclient(aceesclient);
		historyInfo.setSubstartdate(subscriptionInfo.getSubstartdate());
		historyInfo.setSubscriptionprice(subscriptionprice);
		historyInfo.setSubscriptiontype(subscriptiontype);
		historyInfo.setSubstartdatebyadmin(new Date());
		historyInfo.setSubendtdate(subscriptionInfo.getSubendtdate());
		subscriptionpack_historyRepository.save(historyInfo);
		
		return ResponseEntity.ok("Subscription active successfully");
		}
		}catch (Exception e) {
			// Handle exceptions here, you can log the error or return an error response
			e.printStackTrace(); // Replace with proper logging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}	
		
	}


	/////////////////////////////////////////////////////////////status    d by admin//////////////////////////////////////////////////
	@PutMapping("/Approve/Subscription/{pan}")
	public ResponseEntity<String> updateSubscriptionAdmin(@PathVariable String pan) {
		try {
			// Find the subscription record by PAN
			Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(pan);

			if (subscription.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			Subscription_Userdata subscriptionInfo = subscription.get();
			if (subscriptionInfo.getSubscriptiontype().equals("Trial")) {
				subscriptionInfo.setSubstartdatebyadmin(new Date());
				Calendar calendar = Calendar.getInstance();
				// Get the current date
				Date currentDate = new Date();
				calendar.setTime(currentDate);
				calendar.add(Calendar.DATE, 15);
				Date endDate = calendar.getTime();
				subscriptionInfo.setSubendtdate(endDate);
				subscriptionInfo.setPaid(true);
				subscritpion_userdataRepository.save(subscriptionInfo);
				return ResponseEntity.ok("Subscription updated successfully");
			}
			else
			{
			if (subscriptionInfo.getRefrenceId() != null) {
				String pan1 = subscriptionInfo.getRefrenceId();
				Optional<Subscription_Userdata> subscriptionrefer = subscritpion_userdataRepository.findByPan(pan1);
				Subscription_Userdata subscriptionrefer1 = subscriptionrefer.get();
				if (subscriptionrefer1.getSubendtdate() != null && new Date().compareTo(subscriptionrefer1.getSubendtdate()) <= 0) {
					Date date = subscriptionrefer1.getSubendtdate();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date); // Set the calendar to the provided date
					calendar.add(Calendar.DAY_OF_MONTH, 30); // Add 30 days to the date
					Date newDate = calendar.getTime();
					subscriptionrefer1.setSubendtdate(newDate);
					subscritpion_userdataRepository.save(subscriptionrefer1);
				}
				subscriptionInfo.setRefrenceId(null);
				subscritpion_userdataRepository.save(subscriptionInfo);

			}
			subscriptionInfo.setSubstartdatebyadmin(new Date());
			if (subscriptionInfo.getDisrefrenceId() != null) {
				if (subscriptionInfo.getSubendtdate() == null) {
					// Optional<Renewalprice_distributordata> distrubutordata=renewalprice_distributordataRepository.findByPan(subscriptionInfo.getDisrefrenceId());

					Renewalprice_distributordata data = new Renewalprice_distributordata();
					data.setName(subscriptionInfo.getName());
					data.setPan(subscriptionInfo.getDisrefrenceId());
					data.setSubstartdate(subscriptionInfo.getSubstartdate());
					data.setUserpan(subscriptionInfo.getPan());
					data.setSubstartdatebyadmin(subscriptionInfo.getSubstartdatebyadmin());

					Long a = subscriptionInfo.getSubscriptionprice();
					double bPercentage = 0.25;
					double b = a * bPercentage;
					// If you want the result as a Long, you can cast it
					Long bAsLong = (long) b;
					data.setRenewalPrice(bAsLong);
					renewalprice_distributordataRepository.save(data);
				} else {
					Renewalprice_distributordata data = new Renewalprice_distributordata();
					data.setName(subscriptionInfo.getName());
					data.setPan(subscriptionInfo.getDisrefrenceId());
					data.setSubstartdate(subscriptionInfo.getSubstartdate());
					data.setSubstartdatebyadmin(subscriptionInfo.getSubstartdatebyadmin());
					data.setUserpan(subscriptionInfo.getPan());
					Long a = subscriptionInfo.getSubscriptionprice();
					double bPercentage = 0.15;
					double b = a * bPercentage;
					// If you want the result as a Long, you can cast it
					Long bAsLong = (long) b;
					data.setRenewalPrice(bAsLong);
					renewalprice_distributordataRepository.save(data);
				}
			}

			// Get the current date
			Date currentDate = new Date();

			// Add 365 days to the current date
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DATE, 365);
			Date endDate = calendar.getTime();

			subscriptionInfo.setSubendtdate(endDate);
			subscriptionInfo.setPaid(true);

			Subscriptionpack_history historyInfo = new Subscriptionpack_history();
			historyInfo.setUserid(subscriptionInfo.getUserid());
			historyInfo.setPan(subscriptionInfo.getPan());
			historyInfo.setDisrefrenceId(subscriptionInfo.getDisrefrenceId());
			historyInfo.setDissalespersonId(subscriptionInfo.getDissalespersonId());
			historyInfo.setSalespersonId(subscriptionInfo.getSalespersonId());
			historyInfo.setAcessclient(subscriptionInfo.getAcessclient());
			historyInfo.setSubstartdate(subscriptionInfo.getSubstartdate());
			historyInfo.setSubscriptionprice(subscriptionInfo.getSubscriptionprice());
			historyInfo.setSubscriptiontype(subscriptionInfo.getSubscriptiontype());
			historyInfo.setSubstartdatebyadmin(new Date());
			historyInfo.setSubendtdate(endDate);

			subscriptionpack_historyRepository.save(historyInfo);

			// Save the updated subscription record
			subscritpion_userdataRepository.save(subscriptionInfo);
			return ResponseEntity.ok("Subscription updated successfully");
		} 
		}catch (Exception e) {
			// Handle exceptions here, you can log the error or return an error response
			e.printStackTrace(); // Replace with proper logging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}


	/////////////////////////////////check status of user////////////////////////////////////////////////////////////////////
	
	@GetMapping("/checkstatus/Subscription/{pan}")
	public ResponseEntity<Boolean> paidStatusOfUser(@PathVariable String pan) {
		try {
			Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(pan);
			if (subscription.isPresent()) {
				Subscription_Userdata subscriptionInfo = subscription.get();
				boolean isPaid = subscriptionInfo.isPaid();
				return ResponseEntity.ok(isPaid);
			} else {
				// Handle the case when no subscription is found for the provided PAN
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
			}
		} catch (Exception e) {
			// Handle other exceptions here, you can log them or return an error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/checkstatus/sufficient/{pan}")
	public ResponseEntity<Map<String, Object>> subscriptionSufficient(@PathVariable String pan) {
		Optional<User_RegistrationsForm> subscription1 = taskoRepository.findByPan(pan);
		Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(pan);
		if (subscription1.isPresent()) {
			User_RegistrationsForm useridfound = subscription1.get();
			Long count = clientRepository.countoftotalclientByUserid(useridfound.getRegId());
			count = (count != null) ? count : 0L;
			Subscription_Userdata subscriptionInfo = subscription.get();
			boolean isPaid = subscriptionInfo.isPaid();

			Map<String, Object> response = new HashMap<>();
			response.put("count", count);
			response.put("isPaid", isPaid);

			return ResponseEntity.ok(response);

		} else {
			return ResponseEntity.notFound().build();
		}
	}


	///////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/flashmessage/Subscription/{pan}")
	public ResponseEntity<String> flashmessage(@PathVariable String pan) {
		try {
			Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(pan);

			if (subscription.isPresent()) {
				Subscription_Userdata subscriptionInfo = subscription.get();
				if (subscriptionInfo.isForcestop() == true) {
					return ResponseEntity.ok("Your are forcefully stoped by system");
				} else if (subscriptionInfo.getSubendtdate() != null) {
					Date currentDate = new Date();
					Date end_date = subscriptionInfo.getSubendtdate();
					long differenceInMillis = currentDate.getTime() - end_date.getTime();
					long days = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);

					if (days >= 31) {
						subscriptionInfo.setPaid(false);
						subscritpion_userdataRepository.save(subscriptionInfo);
						return ResponseEntity.ok("Your subscription is over. Please renew it.");
					} else {
						return ResponseEntity.ok("Your subscription is still active.");
					}
				} else {
					return ResponseEntity.ok("please first get a subscription."); // Handle the case when no end date is available
				}
			} else {
				return ResponseEntity.notFound().build(); // Handle the case when no subscription is found
			}
		} catch (Exception e) {
			// Handle other exceptions here, you can log them or return an error response
			e.printStackTrace(); // Replace with proper logging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	///////////////////////////////////////find by refence id list/////////////////////////////////////////////////////
	@GetMapping("/findByreferenceid/{pan}")
	public ResponseEntity<List<CustomResponse>> findCustomResponseByReferenceId(@PathVariable("pan") String pan) {
		try {
			List<CustomResponse> responseList = new ArrayList<>();
			List<User_RegistrationsForm> result = taskoRepository.findByRefrenceId(pan); // Execute the query

			for (User_RegistrationsForm registration : result) {
				String pan2 = registration.getPan();

				// Ensure count is not null, default to false if null
				Optional<Subscription_Userdata> data = subscritpion_userdataRepository.findByPan(pan2);

				if (data.isPresent()) {
					Subscription_Userdata infodata = data.get();
					boolean status = infodata.isPaid();
					Date a = infodata.getSubstartdatebyuser();
					if (a == null) {
						a = null; // Setting it to null if it's not already null
					}
					CustomResponse response = new CustomResponse(registration, status, a);
					responseList.add(response);
				} else {
					return null;
				}
			}   

			return ResponseEntity.ok(responseList);
		} catch (Exception e) {
			// Handle the exception here, you can log it or return an error response
			e.printStackTrace(); // Replace with proper logging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


////////////////////////save data rfer a friend////////////////////////////////////////////////////////

	@Autowired
	private Refer_a_friendRepository referAFriendRepository;

	@PostMapping("/save/Refear_a_friend")
	public ResponseEntity<Refer_a_friend> saveReferAFriend(@RequestParam("userid") Long userid, @RequestParam("pan") String pan, @RequestParam("name") String name, @RequestParam("contactno") String contactno, @RequestParam("profession") String profession, @RequestParam String subject,
														   @RequestParam String text) {
		try {
			System.out.println(userid);
			Refer_a_friend referAFriend = new Refer_a_friend();
			referAFriend.setUserid(userid);
			referAFriend.setPan(pan);
			referAFriend.setName(name);
			referAFriend.setContactno(contactno);
			referAFriend.setProfession(profession);
			referAFriend.setDate(new Date());
			Refer_a_friend savedReferAFriend = referAFriendRepository.save(referAFriend);
			String to = "inquirytaxko@gmail.com";
			taskoService.sendEmailwithattachmentClientprofessional(to, subject, text);
			System.out.println("hii");
			return ResponseEntity.ok(savedReferAFriend);
		} catch (Exception e) {
			e.printStackTrace(); // Replace with proper logging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


	////////////////////////save  the User_seggesion////////////////////////////
	@Autowired
	private User_seggesionRepository user_seggesionRepository;

	@PostMapping("/save/User_seggesion")
	public ResponseEntity<User_seggesion> saveUser_seggesion(@RequestBody User_seggesion referAFriend) {
		try {
			referAFriend.setDate(new Date());
			User_seggesion savedReferAFriend = user_seggesionRepository.save(referAFriend);
			return ResponseEntity.ok(savedReferAFriend);
		} catch (Exception e) {
			e.printStackTrace(); // Replace with proper logging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

/////////save the clinet and taxprofessional data//////////////////////////////////////////////////////////////////////////////////////////

	@Autowired
	private Client_TaxProfessional_dataRepository client_TaxProfessional_dataRepository;

	@PostMapping("/save/Client_TaxProfessional_data")
	public ResponseEntity<Client_TaxProfessional_data> saveUser_seggesion(
			@RequestParam("yourname") String yourname,
			@RequestParam("yourmobileno") String yourmobileno,
			@RequestParam("taxprofessionalname") String taxprofessionalname,
			@RequestParam("taxprofessionalmobile") String taxprofessionalmobile,
			@RequestParam("subject") String subject,
			@RequestParam("text") String text) {
		try {
			// Populate Client_TaxProfessional_data object with values from request parameters
			Client_TaxProfessional_data clientData = new Client_TaxProfessional_data();
			clientData.setYourname(yourname);
			clientData.setYourmobileno(yourmobileno);
			clientData.setTaxprofessionalname(taxprofessionalname);
			clientData.setTaxprofessionalmobile(taxprofessionalmobile);
			clientData.setDate(new Date());
			// Save Client_TaxProfessional_data object
			Client_TaxProfessional_data savedData = client_TaxProfessional_dataRepository.save(clientData);
			// Send email
			String to = "inquirytaxko@gmail.com";
			taskoService.sendEmailwithattachmentClientprofessional(to, subject, text);

			return ResponseEntity.ok(savedData);
		} catch (Exception e) {
			e.printStackTrace(); // Replace with proper logging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	@PostMapping("/send-email/withattachment")
	public ResponseEntity<String> sendEmail(
			@RequestParam("recipient") String recipient,
			@RequestParam("subject") String subject,
			@RequestParam("text") String text,
			@RequestParam("attachmentContent") MultipartFile attachmentContent
	) {
		try {
			String attachmentFileName = attachmentContent.getOriginalFilename();
			taskoService.sendEmailWithAttachment11(recipient, subject, text, attachmentContent, attachmentFileName);
			return ResponseEntity.ok("Email sent successfully");
		} catch (Exception e) {
			// Handle the exception, log it, or return an error response
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
		}
	}
	
////////////////////////////////send email with attachment///////////////////////
@PostMapping("/salesmanager/send-email/withattachment")
public ResponseEntity<String> sendEmailunauthrized(
@RequestParam("recipient") String recipient,
@RequestParam("subject") String subject,
@RequestParam("text") String text,
@RequestParam("attachmentContent") MultipartFile attachmentContent
) {
try {
String attachmentFileName = attachmentContent.getOriginalFilename();
taskoService.sendEmailWithAttachment11(recipient, subject, text, attachmentContent, attachmentFileName);
return ResponseEntity.ok("Email sent successfully");
} catch (Exception e) {
// Handle the exception, log it, or return an error response
e.printStackTrace();
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
}
}



	///////////////////////////////////////////////////////change status forcefully////////////////////////////////////////////////////
	@PutMapping("/changestatus/Subscription/{pan}/{value}")
	public ResponseEntity<String> ChangeStatusbyadmin(@PathVariable String pan, @PathVariable Boolean value) {
		try {
			Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(pan);
			Optional<User_RegistrationsForm> user = taskoRepository.findByPan(pan);
			if (subscription.isPresent()) {
				Subscription_Userdata subscriptionInfo = subscription.get();
				User_RegistrationsForm userinfo = user.get();
				if (value == true) {
					System.out.println(userinfo.getEmail());
					String to = userinfo.getEmail();
					String subject = "appication stop forcefully";
					String text = "Please contact administration if the authorizations for your account have been stopped by the system.";
					taskoService.sendEmailwithattachmentforcestop(to, subject, text);
				}
				if (value == false) {
					String to = userinfo.getEmail();
					String subject = "appication start regularly";
					String text = "We are pleased to inform you that the service has now been restored and is fully operational.Thank You.";
					taskoService.sendEmailwithattachmentforcestop(to, subject, text);
				}
				subscriptionInfo.setForcestop(value);
				subscritpion_userdataRepository.save(subscriptionInfo);
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

	/////////////////////////////////check status of user////////////////////////////////////////////////////////////////////
	@GetMapping("/checkstatus/forcestop/{pan}")
	public ResponseEntity<Boolean> forceStatusOfadmin(@PathVariable String pan) {
		try {
			Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(pan);
			if (subscription.isPresent()) {
				Subscription_Userdata subscriptionInfo = subscription.get();
				boolean isPaid = subscriptionInfo.isForcestop();
				return ResponseEntity.ok(isPaid);
			} else {
// Handle the case when no subscription is found for the provided PAN
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
			}
		} catch (Exception e) {
// Handle other exceptions here, you can log them or return an error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


	////////////////////////////////////////////add client button status///////////////////////////////////////////////////////////
	@GetMapping("/checkstatus/addclient/{pan}")
	public ResponseEntity<String> createclientuserstatus(@PathVariable String pan) {
		try {
			Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(pan);
			if (subscription.isPresent()) {
				Subscription_Userdata subscriptionInfo = subscription.get();
				if (subscriptionInfo.isPaid() == false) {
					return ResponseEntity.ok("Your subscription is invalid");
				}
				if (subscriptionInfo.getSubendtdate() != null) {
					Date newdate1 = subscriptionInfo.getSubendtdate(); // Declare newdate1 outside the if block

					if (newdate1 != null) {
						Date currentDate = new Date();

						long differenceInMillis = currentDate.getTime() - newdate1.getTime();  // Subtract currentDate from newdate1
						long days = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
						if (days >= 15) {
							return ResponseEntity.ok("Your subscription is over. Please renew it.");
						} else {
							return null;
						}
					}
				}
			} else {
// Handle the case when no subscription is found for the provided PAN
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
			}
		} catch (Exception e) {
// Handle other exceptions here, you can log them or return an error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		return null;
	}


	///////////////////////////////////////////////////////////
///////////////////////////////////////get data in subscritpion_userdata/////////////////////////////////////////////////////
	@GetMapping("/subscriptionpackuserdata/{pan}")
	public ResponseEntity<Object> subscriptionpackuserdata(@PathVariable("pan") String pan) {
		try {
			Optional<Subscription_Userdata> result = subscritpion_userdataRepository.findByPan(pan);

			if (result.isPresent()) {
				Optional<User_RegistrationsForm> info = taskoRepository.findByPan(pan);

				if (info.isPresent()) {
					User_RegistrationsForm info2 = info.get();
					String refrenceId = info2.getRefrenceId();

					if (refrenceId != null) {
						Optional<User_RegistrationsForm> info3 = taskoRepository.findByPan(refrenceId);

						if (info3.isPresent()) {
							Subscription_Userdata data = result.get();
							Date formattedResult = null;
							if (data.getSubendtdate() != null) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
								String formattedDateString = dateFormat.format(data.getSubendtdate());
								try {
									formattedResult = dateFormat.parse(formattedDateString);
								} catch (ParseException e) {
									// Handle the ParseException if necessary
									e.printStackTrace();
								}
							}
							User_RegistrationsForm info4 = info3.get();
							String name = info4.getName();
							if (name == null) {
								name = null;
							}
							// You can return both result and name in a map or another custom object
							Map<String, Object> response = new HashMap<>();
							response.put("subscriptionData", result);
							response.put("Refered_by_name", name);
							response.put("getSubendtdate", formattedResult);
							return ResponseEntity.ok(response);
						}
					}
				}
				String name = null;
				Subscription_Userdata data = result.get();
				Date formattedResult = null;
				if (data.getSubendtdate() != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					String formattedDateString = dateFormat.format(data.getSubendtdate());
					try {
						formattedResult = dateFormat.parse(formattedDateString);
					} catch (ParseException e) {
						// Handle the ParseException if necessary
						e.printStackTrace();
					}
				}
				Map<String, Object> response = new HashMap<>();
				response.put("subscriptionData", result);
				response.put("Refered_by_name", name);
				response.put("getSubendtdate", formattedResult);
				return ResponseEntity.ok(response);
				// If no reference ID or other conditions are not met, return only the subscription data
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No subscription data found for PAN: " + pan);
			}
		} catch (Exception e) {
			// Handle the exception here, you can log it or return an error response
			e.printStackTrace(); // Replace with proper logging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	///////////////////////////////////////////////////////////////
///////////////////////////////////////get data in subscritpion_userdata by userid/////////////////////////////////////////////////////
	@GetMapping("/subscriptionpackuserdata/userid/{userid}")
	public ResponseEntity<Map<String, Object>> subscriptionpackuserdatabyid(@PathVariable("userid") Long userid) {
		try {
			Optional<Subscription_Userdata> result = subscritpion_userdataRepository.findByUserid(userid);

			if (result.isPresent()) {
//            Subscription_Userdata userdata = result.get();
				Subscription_Userdata data = result.get();
				Date formattedResult = null;
				if (data.getSubendtdate() != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					String formattedDateString = dateFormat.format(data.getSubendtdate());
					try {
						formattedResult = dateFormat.parse(formattedDateString);
					} catch (ParseException e) {
						// Handle the ParseException if necessary
						e.printStackTrace();
					}
				}
				Map<String, Object> response = new HashMap<>();
				response.put("subscriptionData", result);
				response.put("getSubendtdate", formattedResult);
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			// Handle exceptions here, for example, you can log the error
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

/////////

	@GetMapping("/master/countsubscription")
	public ResponseEntity<List<SubscriptionCount>> countsubscription() {
		try {
			Long count1 = subscritpion_userdataRepository.countOftodaysubscription(new Date());
			Date currentDate = new Date();

			// Create a Calendar instance and set it to the current date
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);

			// Subtract 1 day
			calendar.add(Calendar.DAY_OF_MONTH, -1);

			// Get the new date after subtracting 1 day
			Date newDate = calendar.getTime();
			Long count2 = subscritpion_userdataRepository.countOfyestardaysubscription(newDate);
			calendar.setTime(new Date());

			// Subtract 7 days from the current date
			calendar.add(Calendar.DAY_OF_MONTH, -7);

			// Get the start date for the last 7 days
			Date startDate = calendar.getTime();

			// Call the method with the startDate

			Long count3 = subscritpion_userdataRepository.countOfTotalProfessionWithinLast7Days(startDate);
			int currentYear = calendar.get(Calendar.YEAR);
			Long count4 = subscritpion_userdataRepository.countOfTotalProfessionInCurrentYear(currentYear);
			// Calculate the previous year
			int previousYear = currentYear - 1;
			String currentpreviousAsString = Integer.toString(previousYear);
			Long count5 = subscritpion_userdataRepository.countOfTotalProfessionInPreviousYear(previousYear);
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


	//////////////////////////////////////////list of subscrpiton////////////////////////////////////////////////////////
	@GetMapping("/master/listsubscription")
	public ResponseEntity<List<SubscrpitonList>> listsubscription() {
		try {
			// Get the current date and time
			Date currentDate = new Date();

			// Create a Calendar instance and set it to the current date
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);

			// Subtract 1 day
			calendar.add(Calendar.DAY_OF_MONTH, -1);

			// Get the new date after subtracting 1 day
			Date newDate = calendar.getTime();

			// Query the repository for the counts
			List<Subscription_Userdata> count1 = subscritpion_userdataRepository.listOftodaysubscription(currentDate);
			List<Subscription_Userdata> count2 = subscritpion_userdataRepository.listOfyestardaysubscription(newDate);

			// Subtract 7 days from the current date
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_MONTH, -7);

			// Get the start date for the last 7 days
			Date startDate = calendar.getTime();

			// Query the repository for counts within the last 7 days
			List<Subscription_Userdata> count3 = subscritpion_userdataRepository.listofweeksubscription(startDate);

			// Get the current year
			int currentYear = calendar.get(Calendar.YEAR);

			// Query the repository for counts in the current year
			List<Subscription_Userdata> count4 = subscritpion_userdataRepository.listOfTotalProfessionInCurrentYear(currentYear);

			// Calculate the previous year
			int previousYear = currentYear - 1;
			String currentYearAsString = Integer.toString(currentYear);

			// Query the repository for counts in the previous year
			List<Subscription_Userdata> count5 = subscritpion_userdataRepository.listOfTotalProfessionInPreviousYear(previousYear);
			String currentpreviousAsString = Integer.toString(previousYear);
			count1 = (count1 != null) ? count1 : null;
			count2 = (count2 != null) ? count2 : null;
			count3 = (count3 != null) ? count3 : null;
			count4 = (count4 != null) ? count4 : null;
			count5 = (count5 != null) ? count5 : null;

			// Create SubscriptionCount objects
			SubscrpitonList todayCount = new SubscrpitonList("Today", count1);
			SubscrpitonList yesterdayCount = new SubscrpitonList("Yesterday", count2);
			SubscrpitonList weeklyCount = new SubscrpitonList("Week", count3);
			SubscrpitonList currentYearCount = new SubscrpitonList(currentYearAsString, count4);
			SubscrpitonList previousYearCount = new SubscrpitonList(currentpreviousAsString, count5);

			// Create a list of SubscriptionCount objects
			List<SubscrpitonList> SubscrpitonList = new ArrayList<>();
			SubscrpitonList.add(todayCount);
			SubscrpitonList.add(yesterdayCount);
			SubscrpitonList.add(weeklyCount);
			SubscrpitonList.add(currentYearCount);
			SubscrpitonList.add(previousYearCount);

			return ResponseEntity.ok(SubscrpitonList);
		} catch (Exception e) {
			// Handle the exception here, you can log it or return an error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


	///////////////////////////////////////////////////subscriton list//////////////////////////////////////////////////////////////
//////////////////////////////////////////today//////////////////////////////////////////////////
	@GetMapping("/subscriptions/today")
	public List<Subscription_Userdata> getTodaySubscriptions() {
		// Get the current date and time
		Date currentDate = new Date();
		List<Subscription_Userdata> count1 = subscritpion_userdataRepository.listOftodaysubscription(currentDate);
		count1 = (count1 != null) ? count1 : null;
		return (count1);
	}

	/////////////////////////////////////////////////////yesstarday///////////////////////////////////////////////////////////////////
	@GetMapping("/subscriptionslist/yestarday")
	public List<Subscription_Userdata> listOfyestardaysubscription() {
		// Get the current date and time
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		// Subtract 1 day
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		// Get the new date after subtracting 1 day
		Date newDate = calendar.getTime();
		List<Subscription_Userdata> count2 = subscritpion_userdataRepository.listOfyestardaysubscription(newDate);
		count2 = (count2 != null) ? count2 : null;
		return (count2);
	}

	/////////////////////////////////////////////////week////////////////////////////////////////////////////////////////////////
	@GetMapping("/subscriptionslist/week")
	public List<Subscription_Userdata> listOfweeksubscription() {
		// Get the current date and time
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_MONTH, -7);

		// Get the start date for the last 7 days
		Date startDate = calendar.getTime();

		// Query the repository for counts within the last 7 days
		List<Subscription_Userdata> count3 = subscritpion_userdataRepository.listofweeksubscription(startDate);
		count3 = (count3 != null) ? count3 : null;
		return (count3);
	}

	/////////////////////////////////////////////current year//////////////////////////////////////////////////////////////////
	@GetMapping("/subscriptionslist/year")
	public List<Subscription_Userdata> listOfyearsubscription() {
		// Get the current date and time
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		// Query the repository for counts in the current year
		List<Subscription_Userdata> count4 = subscritpion_userdataRepository.listOfTotalProfessionInCurrentYear(currentYear);
		count4 = (count4 != null) ? count4 : null;
		return (count4);
	}


	/////////////////////////////////////////////previous year//////////////////////////////////////////////////////////////////
	@GetMapping("/subscriptionslist/previousyear")
	public List<Subscription_Userdata> listOfpreviousubscription() {
// Get the current date and time
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int previousYear = currentYear - 1;
		String currentYearAsString = Integer.toString(currentYear);

// Query the repository for counts in the previous year
		List<Subscription_Userdata> count5 = subscritpion_userdataRepository.listOfTotalProfessionInPreviousYear(previousYear);
		count5 = (count5 != null) ? count5 : null;
		return (count5);
	}


/////////////////////////////////////////////////Renewal of subscription///////////////////////////////////////////

	@GetMapping("/master/countreneval")
	public ResponseEntity<List<SubscriptionCount>> countrenewal() {
		try {
			System.out.println("hii");
			Long count1 = subscritpion_userdataRepository.countOftodayRenewal(new Date());
			Date currentDate = new Date();

			// Create a Calendar instance and set it to the current date
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);

			// Subtract 1 day
			calendar.add(Calendar.DAY_OF_MONTH, +1);

			// Get the new date after subtracting 1 day
			Date newDate = calendar.getTime();
			Long count2 = subscritpion_userdataRepository.countOfyestardayRenewal(newDate);
			calendar.setTime(new Date());

			// Subtract 7 days from the current date
			calendar.add(Calendar.DAY_OF_MONTH, +7);

			// Get the start date for the last 7 days
			Date startDate = calendar.getTime();

			// Call the method with the startDate

			Long count3 = subscritpion_userdataRepository.countOfTotalProfessionWithinLast7Daysreneval(startDate, currentDate);
			
			calendar.setTime(new Date());

			// Subtract 7 days from the current date
			calendar.add(Calendar.DAY_OF_MONTH, -7);

			// Get the start date for the last 7 days
			Date enddate = calendar.getTime();

			Long count7=subscritpion_userdataRepository.countOfTotalProfessionWithinLast7Daysrenevalbefore(enddate, currentDate);

			LocalDate currentDate1 = LocalDate.now();
			int year = currentDate1.getYear();
			int month = currentDate1.getMonthValue();

			Long count4 = subscritpion_userdataRepository.countOfTotalProfessionInCurrentMonthreneval(year, month, currentDate);
			Calendar calendar1 = Calendar.getInstance();
			LocalDate currentDate2 = LocalDate.now();

			calendar1.setTime(new Date()); // Set your initial date here

			// Add 90 days
			calendar1.add(Calendar.DAY_OF_MONTH, 90);

			// Get the new date
			Date newDate1 = calendar1.getTime();
			Long count5 = subscritpion_userdataRepository.countOfTotalProfessionInthreemonthreneval(newDate1, currentDate);
			Calendar calendar2 = Calendar.getInstance();
			LocalDate currentDate3 = LocalDate.now();

			calendar2.setTime(new Date()); // Set your initial date here
			// Add 90 days
			calendar2.add(Calendar.DAY_OF_MONTH, 180);
			Date newDate2 = calendar2.getTime();
			Long count6 = subscritpion_userdataRepository.countOfTotalProfessionInsixmonthreneval(newDate2, currentDate);

			Long count8 =subscritpion_userdataRepository.countOfTotalProfessionWithinall(currentDate);

			// Set default values to 0 if a count is not available
			count1 = (count1 != null) ? count1 : 0L;
			count2 = (count2 != null) ? count2 : 0L;
			count3 = (count3 != null) ? count3 : 0L;
			count4 = (count4 != null) ? count4 : 0L;
			count5 = (count5 != null) ? count5 : 0L;
			count7 = (count7 != null) ? count7 : 0L;
			count8=(count8 != null) ? count8 : 0L;
//	    count6 = (count6 != null) ? count6 : 0L;
			System.out.println(count1);
			System.out.println(count2);
			System.out.println(count3);
			System.out.println(count4);
			System.out.println(count5);
//	    System.out.println(count6);


			List<SubscriptionCount> professionCounts = new ArrayList<>();
			professionCounts.add(new SubscriptionCount("Today", count1));
			professionCounts.add(new SubscriptionCount("Tomorrow", count2));
			professionCounts.add(new SubscriptionCount("Weak", count3));
			professionCounts.add(new SubscriptionCount("Month", count4));
			professionCounts.add(new SubscriptionCount("last_week", count7));
			professionCounts.add(new SubscriptionCount("all_subscitption_over", count8));
			//professionCounts.add(new SubscriptionCount("3 Month", count5));
			//professionCounts.add(new SubscriptionCount("6 Month", count5));

			return ResponseEntity.ok(professionCounts);
		} catch (Exception e) {
			// Handle the exception here, you can log it or return an error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


//////////////////////////////list of renveal////////////////////////////////////////////////////////////////////

	@GetMapping("/Renewal/today")
	public List<Subscription_Userdata> getTodayRenewal() {
		// Get the current date and time
		Date currentDate = new Date();
		List<Subscription_Userdata> count1 = subscritpion_userdataRepository.listOftodayRenewal(currentDate);
		count1 = (count1 != null) ? count1 : null;
		return (count1);
	}


/////////////////////////////////////////////////////////

	@GetMapping("/Renewal/tommarow")
	public List<Subscription_Userdata> getTommarowRenewal() {
		Date currentDate = new Date();

		// Create a Calendar instance and set it to the current date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		// Subtract 1 day
		calendar.add(Calendar.DAY_OF_MONTH, +1);

		// Get the new date after subtracting 1 day
		Date newDate = calendar.getTime();
		List<Subscription_Userdata> count2 = subscritpion_userdataRepository.listOfyestardayRenewal(newDate);
		count2 = (count2 != null) ? count2 : null;
		return (count2);
	}

	/////////////////////////////////////////////////////////////////////////
	@GetMapping("/Renewal/week")
	public List<Subscription_Userdata> getweekRenewal() {
		Date currentDate = new Date();

		// Create a Calendar instance and set it to the current date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		// Subtract 7 days from the current date
		calendar.add(Calendar.DAY_OF_MONTH, +7);

		// Get the start date for the last 7 days
		Date startDate = calendar.getTime();

		// Call the method with the startDate

		List<Subscription_Userdata> count3 = subscritpion_userdataRepository.listOfTotalProfessionWithinLast7Daysreneval(startDate, currentDate);
		count3 = (count3 != null) ? count3 : null;
		return (count3);
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/Renewal/month")
	public List<Subscription_Userdata> getmonthRenewal() {
		Date currentDate = new Date();
		LocalDate currentDate1 = LocalDate.now();
		int year = currentDate1.getYear();
		int month = currentDate1.getMonthValue();

		List<Subscription_Userdata> count4 = subscritpion_userdataRepository.listOfTotalProfessionInCurrentMonthreneval(year, month, currentDate);
		count4 = (count4 != null) ? count4 : null;
		return (count4);
	}


	/////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/Renewal/threemonth")
	public List<Subscription_Userdata> getthreemonthRenewal() {
		Date currentDate = new Date();
		Calendar calendar1 = Calendar.getInstance();
		LocalDate currentDate2 = LocalDate.now();

		calendar1.setTime(new Date()); // Set your initial date here

		// Add 90 days
		calendar1.add(Calendar.DAY_OF_MONTH, 90);

		// Get the new date
		Date newDate1 = calendar1.getTime();
		List<Subscription_Userdata> count5 = subscritpion_userdataRepository.listOfTotalProfessionInthreemonthreneval(newDate1, currentDate);
		count5 = (count5 != null) ? count5 : null;
		return (count5);
	}


	////////////////////////////////////////////////////////////////
	@GetMapping("/Renewal/sixmonth")
	public List<Subscription_Userdata> getsixmonthRenewal() {
		Date currentDate = new Date();
		Calendar calendar2 = Calendar.getInstance();
		LocalDate currentDate3 = LocalDate.now();

		calendar2.setTime(new Date()); // Set your initial date here
		// Add 90 days
		calendar2.add(Calendar.DAY_OF_MONTH, 180);
		Date newDate2 = calendar2.getTime();
		List<Subscription_Userdata> count5 = subscritpion_userdataRepository.listOfTotalProfessionInsixmonthreneval(newDate2, currentDate);
		count5 = (count5 != null) ? count5 : null;
		return (count5);
	}
	///////////////////////renewal of last week/////////////////////////
	@GetMapping("/last_week/Renewal/week")
	public List<Subscription_Userdata> getweekRenewallast() {
		Date currentDate = new Date();

		// Create a Calendar instance and set it to the current date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		// Subtract 7 days from the current date
		calendar.add(Calendar.DAY_OF_MONTH, -7);

		// Get the start date for the last 7 days
		Date startDate = calendar.getTime();

		// Call the method with the startDate

		List<Subscription_Userdata> count3 = subscritpion_userdataRepository.listOfTotalProfessionWithinLastweek7Daysreneval(startDate, currentDate);
		count3 = (count3 != null) ? count3 : null;
		return (count3);
	}

///////////////////////////////////// list of all USER of subscritpion is over//////////////////////////////
	 @GetMapping("/list/user/end/of/subscriptions")
	    public ResponseEntity<List<Subscription_Userdata>> getSubscriptionsBeforeDate() {
	        try {
	        	Date currentDate = new Date();

				// Create a Calendar instance and set it to the current date
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentDate);
	            List<Subscription_Userdata> subscriptions = subscritpion_userdataRepository.listOfTotalProfessionWithinall(currentDate);
	            if (subscriptions.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	            return new ResponseEntity<>(subscriptions, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	
	///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////add days forcefully////////////////////////////////////////////////////
	@PutMapping("/adddays/Subscription/{pan}/{value}")
	public ResponseEntity<String> Changedatebyadmin(@PathVariable String pan, @PathVariable String value) {
		try {
			// Convert the 'value' path variable to a Date as needed
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			Date date = dateFormat.parse(value);

			Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(pan);
			if (subscription.isPresent()) {
				Subscription_Userdata subscriptionInfo = subscription.get();
				subscriptionInfo.getSubendtdate();
				subscriptionInfo.setSubendtdate(date);
				subscritpion_userdataRepository.save(subscriptionInfo);
				return ResponseEntity.ok("days add successfully");
			} else {
				// Handle the case when no subscription is found for the provided PAN
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("record not found");
			}
		} catch (ParseException e) {
			// Handle the case when date parsing fails
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format");
		} catch (Exception e) {
			// Handle other exceptions here, you can log them or return an error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


///////////////////////////////////////////////add client forcefully/////////////////////////////////

	@PutMapping("/addclient/Subscription/{pan}/{value}")
	public ResponseEntity<String> addclientbyadmin(@PathVariable String pan, @PathVariable Long value) {
		try {
			Optional<Subscription_Userdata> subscription = subscritpion_userdataRepository.findByPan(pan);
			if (subscription.isPresent()) {
				Subscription_Userdata subscriptionInfo = subscription.get();
				Long value1 = subscriptionInfo.getAcessclient() + value;
				subscriptionInfo.setAcessclient(value1);
				subscritpion_userdataRepository.save(subscriptionInfo);
				return ResponseEntity.ok("client add succesfully");
			} else {
// Handle the case when no subscription is found for the provided PAN
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("cleint not add");
			}
		} catch (Exception e) {
// Handle other exceptions here, you can log them or return an error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


	///////////////////////////send email for contact//////////////////////////////////
	@PostMapping("/send-email/forcontact")
	public ResponseEntity<String> sendEmailcontact(
			@RequestParam("subject") String subject,
			@RequestBody String text
	) {
		try {
			String to = "txkspprt@gmail.com";
			taskoService.sendEmailwithattachmentContact(to, subject, text);
			return ResponseEntity.ok("Email sent successfully");
		} catch (Exception e) {
			// Handle the exception, log it, or return an error response
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
		}
	}

	//--------------------------------------------Filed List--------------------------------------------------------
	@GetMapping("/IncomeTax_Filed_List/{userid}/{accountyear}")
	public ResponseEntity<List<Client_Registation_Form>> getFiledData(@PathVariable Long userid, @PathVariable String accountyear) {
		List<Filed_NotFiled> FiledData = filed_notFiledRepo.findByUseridAndAccountyearAndFilednotfiled(userid, accountyear, "yes");
		if (FiledData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Long> clientIds = FiledData.stream().map(Filed_NotFiled::getClientid).collect(Collectors.toList());

			List<Client_Registation_Form> clientInfoList = clientRepository.findByClientIdIn(clientIds);

			if (clientInfoList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(clientInfoList, HttpStatus.OK);
			}
		}
	}

	//--------------------------------------------Not_Filed List--------------------------------------------------------
	@GetMapping("/IncomeTax_NotFiled_List/{userid}/{accountyear}")
	public ResponseEntity<List<Client_Registation_Form>> getNotFiledData(@PathVariable Long userid, @PathVariable String accountyear) {
		List<Filed_NotFiled> notFiledData = filed_notFiledRepo.findByUseridAndAccountyearAndFilednotfiled(userid, accountyear, "no");
		if (notFiledData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Long> clientIds = notFiledData.stream().map(Filed_NotFiled::getClientid).collect(Collectors.toList());

			List<Client_Registation_Form> clientInfoList = clientRepository.findByClientIdIn(clientIds);

			if (clientInfoList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(clientInfoList, HttpStatus.OK);
			}
		}
	}

	//-------------------------------------------GST GSTR-1 filed List-----------------------------------
	@GetMapping("/GST_GSTR-1_Filed_List/{userid}/{month}")
	public ResponseEntity<List<Client_Registation_Form>> GSTgetFiledData(@PathVariable Long userid, @PathVariable String month) {
		List<Filed_NotFiled_GST> FiledData = gstfiled_notfiledRepo.findByUseridAndMonthAndCategoryAndFilednotfiled(userid, month, "GSTR-1", "yes");
		if (FiledData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Long> clientIds = FiledData.stream().map(Filed_NotFiled_GST::getClientid).collect(Collectors.toList());

			List<Client_Registation_Form> clientInfoList = clientRepository.findByClientIdIn(clientIds);

			if (clientInfoList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(clientInfoList, HttpStatus.OK);
			}
		}
	}

	//-------------------------------------------GST GSTR-3B filed List-----------------------------------
	@GetMapping("/GST_GSTR-3B_Filed_List/{userid}/{month}")
	public ResponseEntity<List<Client_Registation_Form>> GSTGSTR_3BgetFiledData(@PathVariable Long userid, @PathVariable String month) {
		List<Filed_NotFiled_GST> GSTR_3BFiledData = gstfiled_notfiledRepo.findByUseridAndMonthAndCategoryAndFilednotfiled(userid, month, "GSTR-3B", "yes");
		if (GSTR_3BFiledData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Long> clientIds = GSTR_3BFiledData.stream().map(Filed_NotFiled_GST::getClientid).collect(Collectors.toList());

			List<Client_Registation_Form> clientInfoList = clientRepository.findByClientIdIn(clientIds);

			if (clientInfoList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(clientInfoList, HttpStatus.OK);
			}
		}
	}

	//-------------------------------------------GST GSTR-1 Not_filed List-----------------------------------
	@GetMapping("/GST_GSTR-1_NotFiled_List/{userid}/{month}")
	public ResponseEntity<List<Client_Registation_Form>> GSTgetNotFiledData(@PathVariable Long userid, @PathVariable String month) {
		List<Filed_NotFiled_GST> notFiledData = gstfiled_notfiledRepo.findByUseridAndMonthAndCategoryAndFilednotfiled(userid, month, "GSTR-1", "no");

		if (notFiledData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Long> clientIds = notFiledData.stream().map(Filed_NotFiled_GST::getClientid).collect(Collectors.toList());

			List<Client_Registation_Form> clientInfoList = clientRepository.findByClientIdIn(clientIds);

			if (clientInfoList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(clientInfoList, HttpStatus.OK);
			}
		}

	}

	//-------------------------------------------GST GSTR-3B Not_filed List-----------------------------------
	@GetMapping("/GST_GSTR-3B_NotFiled_List/{userid}/{month}")
	public ResponseEntity<List<Client_Registation_Form>> GSTGSTR_3BgetNotFiledData(@PathVariable Long userid, @PathVariable String month) {
		List<Filed_NotFiled_GST> GSTR_3BnotFiledData = gstfiled_notfiledRepo.findByUseridAndMonthAndCategoryAndFilednotfiled(userid, month, "GSTR-3B", "no");

		if (GSTR_3BnotFiledData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Long> clientIds = GSTR_3BnotFiledData.stream().map(Filed_NotFiled_GST::getClientid).collect(Collectors.toList());

			List<Client_Registation_Form> clientInfoList = clientRepository.findByClientIdIn(clientIds);

			if (clientInfoList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(clientInfoList, HttpStatus.OK);
			}
		}

	}


	/////////////////////////////////upload Tally backup file for user/////////////////////////////////////
	@PostMapping("/user/upload/tallybackupfile")
	public ResponseEntity<Object> uploadtallybackupfile(
			@RequestParam Long UserId,
			@RequestParam String pan,
			@RequestParam(value = "imagePathBackupfile", required = false) MultipartFile imagePathBackupfile) {
		try {
			User_tally_backupfile data = new User_tally_backupfile();
			data.setUserid(UserId);
			data.setPan(pan);
			data.setDate(new Date());
			if (!imagePathBackupfile.isEmpty()) {
				String name = imagePathBackupfile.getOriginalFilename();
				String[] result = name.split("\\.");
				String fileExtension = result[result.length - 1];
				String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
				System.out.println("File Name without Extension: " + fileNameWithoutExtension);
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentType(imagePathBackupfile.getContentType());
				InputStream inputStream = imagePathBackupfile.getInputStream();
				String filename = generateUniqueFiletally(pan, fileNameWithoutExtension);
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

				data.setImageNameBackupfile(name);
				data.setImagePathBackupfile("s3://" + bucketName + "/" + s3Key);

			}
			// Save the updated payment details
			user_tally_backupfileRepository.save(data);

			return ResponseEntity.ok("Upload Sucessfully!");
		} catch (Exception e) {
			// Handle exceptions here, log them, and return an error response
			e.printStackTrace(); // Log the exception for debugging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
					+ "ing image");
		}
	}

	private String generateUniqueFiletally(String pan, String fileNameWithoutExtension) {
		return pan + "_" + "tally" + "_" + "backup" + "_" + fileNameWithoutExtension;
	}


	//////////////////////////update Tally backup file for user ////////////////////////////////////

	@PutMapping("/user/update/tallybackupfile")
	public ResponseEntity<Object> updatetallybackupfile(
			@RequestParam Long UserId,
			@RequestParam(value = "imagePathBackupfile", required = false) MultipartFile imagePathBackupfile) {
		try {
			Optional<User_tally_backupfile> tallydata = user_tally_backupfileRepository.findByUserid(UserId);
			User_tally_backupfile data = tallydata.get();
			if (!imagePathBackupfile.isEmpty()) {
				String name = imagePathBackupfile.getOriginalFilename();
				String[] result = name.split("\\.");
				String fileExtension = result[result.length - 1];
				String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
				System.out.println("File Name without Extension: " + fileNameWithoutExtension);
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentType(imagePathBackupfile.getContentType());
				InputStream inputStream = imagePathBackupfile.getInputStream();
				String filename = generateUniqueFiletally(data.getPan(), fileNameWithoutExtension);
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

				data.setImageNameBackupfile(name);
				data.setImagePathBackupfile("s3://" + bucketName + "/" + s3Key);

			}
			data.setDate(new Date());
			// Save the updated payment details
			user_tally_backupfileRepository.save(data);

			return ResponseEntity.ok("Upload Sucessfully!");
		} catch (Exception e) {
			// Handle exceptions here, log them, and return an error response
			e.printStackTrace(); // Log the exception for debugging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
					+ "ing image");
		}
	}

	//////////////////////////////////////////////////////////delete user tally backupfile//////////////////////////////////////////
	@PutMapping("/user/delete/userbackupfile")
	public ResponseEntity<String> uploadcheck1(@RequestParam Long UserId) {
		try {
//Check if the client with the given PAN exists
			Optional<User_tally_backupfile> tallydata = user_tally_backupfileRepository.findByUserid(UserId);
			if (!tallydata.isPresent()) {
				return ResponseEntity.notFound().build();
			}

			User_tally_backupfile existingPaymentDetails = tallydata.get();
			String s3Key = getS3KeyFromFilePath(existingPaymentDetails.getImagePathBackupfile());

//Check if the object with the same key exists in the S3 bucket
			if (amazonS3.doesObjectExist(bucketName, s3Key)) {
//If it exists, delete it
				amazonS3.deleteObject(bucketName, s3Key);
			}

//Modify the specific column you want to delete
			existingPaymentDetails.setImageNameBackupfile(null); // Set it to null, assuming you want to "delete" it.
			existingPaymentDetails.setImagePathBackupfile(null);

//Save the updated entity back to the database
			user_tally_backupfileRepository.save(existingPaymentDetails);

			return ResponseEntity.ok("deleted successfully.");
		} catch (Exception e) {
//Handle exceptions here
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update: " + e.getMessage());
		}
	}


	/////////////////////////////////////////////////////get backupfile user////////////////////////////////////////////////////
	@GetMapping("/getuserbackupfile/{UserId}")
	public ResponseEntity<?> getdistributoradhar(@PathVariable Long UserId) {

		Optional<User_tally_backupfile> tallydata = user_tally_backupfileRepository.findByUserid(UserId);

		if (tallydata.isPresent()) {
			User_tally_backupfile clientDetails = tallydata.get();
			String s3Key = getS3KeyFromFilePath(clientDetails.getImagePathBackupfile());
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
				headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePathBackupfile()).build());

//Create a response map with both paymentDetails and content

				Map<String, Object> responseMap = new HashMap<>();
//responseMap.put("paymentDetails", clientDetails);
				responseMap.put("content", content);
				responseMap.put("content", resource);

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
			logger.warn(" details not found for userid: {}", UserId);
			return ResponseEntity.notFound().build();
		}
	}

	/////////////////////////////////get all userbackupfile detail information/////////////////////////////////////////////
	@GetMapping("/getuserbackupfiledetail/{UserId}")
	public ResponseEntity<User_tally_backupfile> getDistributorDetail(@PathVariable Long UserId) {
		Optional<User_tally_backupfile> tallydata = user_tally_backupfileRepository.findByUserid(UserId);

		if (tallydata.isPresent()) {
			User_tally_backupfile distributorDetails = tallydata.get();
			return ResponseEntity.ok().body(distributorDetails);
		} else {
			return ResponseEntity.ok().body(null);
		}
	}


///////////////////////////////////////////// tally backup for client and user /////////////////////////////////////////////////


	/////////////////////////////////upload Tally backup file for user/////////////////////////////////////
	@PostMapping("/client/user/upload/tallybackupfile")
	public ResponseEntity<Object> uploadtallybackupfileuserclient(
			@RequestParam Long UserId,
			@RequestParam Long ClientId,
			@RequestParam(value = "imagePathBackupfile", required = false) MultipartFile imagePathBackupfile) {
		try {
			User_CLient_tally_backupfile data = new User_CLient_tally_backupfile();
			data.setUserid(UserId);
			data.setClientid(ClientId);
			data.setDate(new Date());
			if (!imagePathBackupfile.isEmpty()) {
				String name = imagePathBackupfile.getOriginalFilename();
				String[] result = name.split("\\.");
				String fileExtension = result[result.length - 1];
				String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
				System.out.println("File Name without Extension: " + fileNameWithoutExtension);
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentType(imagePathBackupfile.getContentType());
				InputStream inputStream = imagePathBackupfile.getInputStream();
				String filename = generateUniqueuserclientFiletally(ClientId, UserId, fileNameWithoutExtension);
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

				data.setImageNameBackupfile(name);
				data.setImagePathBackupfile("s3://" + bucketName + "/" + s3Key);
			}
// Save the updated payment details
			user_CLient_tally_backupfileRepository.save(data);

			return ResponseEntity.ok("Upload Sucessfully!");
		} catch (Exception e) {
// Handle exceptions here, log them, and return an error response
			e.printStackTrace(); // Log the exception for debugging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
					+ "ing image");
		}
	}

	private String generateUniqueuserclientFiletally(Long clientId, Long userId, String fileNameWithoutExtension) {
		return clientId + "_" + userId + "_" + "backup" + "_" + fileNameWithoutExtension;
	}


//////////////////////////update Tally backup file for user ////////////////////////////////////

	@PutMapping("/client/user/update/tallybackupfile")
	public ResponseEntity<Object> updatetallybackupfile(
			@RequestParam Long UserId,
			@RequestParam Long ClientId,
			@RequestParam(value = "imagePathBackupfile", required = false) MultipartFile imagePathBackupfile) {
		try {
			Optional<User_CLient_tally_backupfile> tallydata = user_CLient_tally_backupfileRepository.findByClientidAndUserid(ClientId, UserId);
			User_CLient_tally_backupfile data = tallydata.get();
			if (!imagePathBackupfile.isEmpty()) {
				String name = imagePathBackupfile.getOriginalFilename();
				String[] result = name.split("\\.");
				String fileExtension = result[result.length - 1];
				String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
				System.out.println("File Name without Extension: " + fileNameWithoutExtension);
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentType(imagePathBackupfile.getContentType());
				InputStream inputStream = imagePathBackupfile.getInputStream();
				String filename = generateUniqueuserclientFiletally(ClientId, UserId, fileNameWithoutExtension);
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

				data.setImageNameBackupfile(name);
				data.setImagePathBackupfile("s3://" + bucketName + "/" + s3Key);

			}
			data.setDate(new Date());
// Save the updated payment details
			user_CLient_tally_backupfileRepository.save(data);

			return ResponseEntity.ok("Upload Sucessfully!");
		} catch (Exception e) {
// Handle exceptions here, log them, and return an error response
			e.printStackTrace(); // Log the exception for debugging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
					+ "ing image");
		}
	}

	//////////////////////////////////////////////////////////delete user tally backupfile//////////////////////////////////////////
	@PutMapping("/client/user/delete/userbackupfile")
	public ResponseEntity<String> deleteuserclientbackupfile(@RequestParam Long ClientId, @RequestParam Long UserId) {
		try {
//Check if the client with the given PAN exists
			Optional<User_CLient_tally_backupfile> tallydata = user_CLient_tally_backupfileRepository.findByClientidAndUserid(ClientId, UserId);
			if (!tallydata.isPresent()) {
				return ResponseEntity.notFound().build();
			}

			User_CLient_tally_backupfile existingPaymentDetails = tallydata.get();
			String s3Key = getS3KeyFromFilePath(existingPaymentDetails.getImagePathBackupfile());

//Check if the object with the same key exists in the S3 bucket
			if (amazonS3.doesObjectExist(bucketName, s3Key)) {
//If it exists, delete it
				amazonS3.deleteObject(bucketName, s3Key);
			}

//Modify the specific column you want to delete
			existingPaymentDetails.setImageNameBackupfile(null); // Set it to null, assuming you want to "delete" it.
			existingPaymentDetails.setImagePathBackupfile(null);

//Save the updated entity back to the database
			user_CLient_tally_backupfileRepository.save(existingPaymentDetails);

			return ResponseEntity.ok("deleted successfully.");
		} catch (Exception e) {
//Handle exceptions here
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update: " + e.getMessage());
		}
	}


	/////////////////////////////////////////////////////get backupfile user////////////////////////////////////////////////////
	@GetMapping("/client/user/getuserbackupfile/{ClientId}/{UserId}")
	public ResponseEntity<?> getclientuserbackupfile(@PathVariable Long ClientId, @PathVariable Long UserId) {

		Optional<User_CLient_tally_backupfile> tallydata = user_CLient_tally_backupfileRepository.findByClientidAndUserid(ClientId, UserId);

		if (tallydata.isPresent()) {
			User_CLient_tally_backupfile clientDetails = tallydata.get();
			String s3Key = getS3KeyFromFilePath(clientDetails.getImagePathBackupfile());
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
				headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePathBackupfile()).build());

//Create a response map with both paymentDetails and content

				Map<String, Object> responseMap = new HashMap<>();
//responseMap.put("paymentDetails", clientDetails);
				responseMap.put("content", content);
				responseMap.put("content", resource);

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
			logger.warn(" details not found for userid: {}", UserId);
			return ResponseEntity.notFound().build();
		}
	}

	/////////////////////////////////get all userbackupfile detail information/////////////////////////////////////////////
	@GetMapping("/client/user/getuserbackupfiledetail/{ClientId}/{UserId}")
	public ResponseEntity<User_CLient_tally_backupfile> getclientuserdetail(@PathVariable Long ClientId, @PathVariable Long UserId) {
		Optional<User_CLient_tally_backupfile> tallydata = user_CLient_tally_backupfileRepository.findByClientidAndUserid(ClientId, UserId);

		if (tallydata.isPresent()) {
			User_CLient_tally_backupfile distributorDetails = tallydata.get();
			return ResponseEntity.ok().body(distributorDetails);
		} else {
			return ResponseEntity.ok().body(null);
		}
	}

	///////////////////////////////////////////Save TermsOfService//////////////////////////////////////
	@Autowired
	private paymentAndTermsRepository paymentAndTermsRepository;


	@PostMapping("/TermsOfService")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile data,@RequestParam String category) throws IOException {
		PaymentAndTerms fileterms = new PaymentAndTerms();
		fileterms.setName(data.getOriginalFilename());
		fileterms.setData(data.getBytes());
		fileterms.setDate(new Date());
		fileterms.setCategory(category);

		paymentAndTermsRepository.save(fileterms);

		return ResponseEntity.ok("File uploaded successfully!");
	}

	@GetMapping("/TermsOfServiceDownload/{category}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable String category) {
		PaymentAndTerms fileterms1 = (PaymentAndTerms) paymentAndTermsRepository.findByCategory(category).orElse(null);

		if (fileterms1 != null) {
			return ResponseEntity.ok()
					.header("Content-Disposition", "attachment; filename=" + fileterms1.getName())
					.body(fileterms1.getData());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/TermsOfServiceUpdate/{category}")
	public ResponseEntity<String> updateFileById(@PathVariable String category, @RequestParam("file") MultipartFile file) throws IOException {
		PaymentAndTerms existingFile = (PaymentAndTerms) paymentAndTermsRepository.findByCategory(category).orElse(null);

		if (existingFile != null) {
			existingFile.setName(file.getOriginalFilename());
			existingFile.setData(file.getBytes());
			existingFile.setDate(new Date());

			paymentAndTermsRepository.save(existingFile);

			return ResponseEntity.ok("File updated successfully!");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	//---------------------------------------WhatsApp Massage---------------------------
	@PostMapping("/send-whatsapp")
	public String sendWhatsApp(@RequestBody SmsRequest smsRequest)
	{
		return taskoService.sendWhatsAppMessage(smsRequest.getTo(), smsRequest.getMessage());

	}

	
	
	
	//---------------------------Payment Gateway-------------------------------
		@PostMapping("/create-order")
		public String createOrder(@RequestParam double amount, @RequestParam String currency) throws RazorpayException
		{
			return taskoService.createOrder(amount, currency);
		}

		@PostMapping("/saveTransaction")
		public Transaction saveTransaction(@RequestBody Transaction transaction)
		{
			transaction.setTransactionDate(new Date());
			return transactioRepo.save(transaction);
		}
	
		
		@PutMapping("/change-password")
		public ResponseEntity<String> changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
			logger.info("Received a request to change password for user with ID: {}", username);

			try {
				if (taskoService.isOldPasswordCorrect3(username, oldPassword)) {
					taskoService.updatePassword3(username, newPassword);
					logger.info("Password changed successfully for user with ID: {}", username);
					return ResponseEntity.ok("Password changed successfully.");
				} else {
					logger.warn("Attempt to change password failed for user with ID {}. Invalid old password.", username);
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid old password.");
				}
			} catch (Exception e) {
				logger.error("An error occurred while changing the password for user with ID {}: {}", username, e.getMessage());
				// Consider handling the exception more appropriately based on your application's requirements
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while changing the password.");
			}
		}
		
		  @PostMapping("/sendEmail/invite")
		    public String sendEmailinvite(@RequestParam String to ,@RequestParam String subject ,@RequestBody String body) {
		        try {		   
		          taskoService.sendEmailinvite(to, subject, body);
		            return "Emails sent successfully";
		        } catch (Exception e) {
		            return "Failed to send emails: " + e.getMessage();
		        }
		    }

	//----------------------------------------------Notification Save---------------------------------------------
	@PostMapping("/SaveNotification")
	public ResponseEntity<String> uploadImage(@RequestParam(value = "file", required = false) MultipartFile file,
											  @RequestParam("clientIds") List<Long> clientIds,
											  @RequestParam Long userid,
											  @RequestParam String text,
											  @RequestParam String sendDate,
											  @RequestParam String from,
											  @RequestParam String to,
											  @RequestParam String category)
	{
		try {
			// Save image path to database for each client ID
			for (Long clientId : clientIds)
			{
				Optional<Client_Registation_Form> findbyC=clientRepository.findByClientId(clientId);
				if(findbyC.isPresent())
				{
					Client_Registation_Form mail=findbyC.get();
					MimeMessage message = javaMailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
					User_RegistrationsForm caid=taskoRepository.findByRegId(mail.getUserid());

					helper.setTo(mail.getEmail());
					helper.setSubject("Notification in TAXKO.");
					helper.setText("Dear " + mail.getName() +",\n"+
							"You have a notification in the TAXKO application from "+caid.getName()+"\n"+
							"Notification as follows:\n"+
							text +"\n\n"+
							"If you have any questions or need further clarification, please don't hesitate to reach out to your CA. You can view this notification in the app or on the notification page. We are here to assist you.\n" +
							"Thank you for your attention to this matter.\n"+
							"Best Regards,\n"+
							"Team TAXKO");

					// Attach the file
					if (file != null && !file.isEmpty())
					{
						helper.addAttachment(file.getOriginalFilename(), file);
					}

					javaMailSender.send(message);
				}
				Notification fileEntity = new Notification();
				if (file != null)
				{
					try
					{
						String s3Key = file.getOriginalFilename();

						amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, file.getInputStream(), new ObjectMetadata()));
						if(!file.isEmpty())
						{
							fileEntity.setImagePath("s3://" + bucketName + "/" + s3Key);
						}
					}
					catch (IOException e) {
						e.printStackTrace();
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file to S3.");
					}
				}
				fileEntity.setClientid(clientId);
				fileEntity.setUserid(userid);
				fileEntity.setText(text);
				fileEntity.setSendDate(sendDate);
				fileEntity.setNotificationFrom(from);
				fileEntity.setNotificationTo(to);
				fileEntity.setCategory(category);
				fileEntity.setNotificationView(false);
				notificationRepo.save(fileEntity);
			}

			return ResponseEntity.ok("Data saved successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save data.");
		}
	}


	@PutMapping("/changeNotificationView/{id}")
	public ResponseEntity<String> changeView(@PathVariable Long id)
	{
		Optional<Notification> change=notificationRepo.findById(id);
		if(change.isPresent())
		{
			Notification x=change.get();
			x.setNotificationView(true);
			notificationRepo.save(x);
		}
		return ResponseEntity.ok("Change View");
	}

	@GetMapping("/GetSingleCategoey/{userid}")
	public List<Notification> getDataByUseridAndSingle(@PathVariable Long userid)
	{
		List<Notification> g=notificationRepo.findAllByUseridAndCategory(userid,"Single");
		return g;
	}


	@GetMapping("/GetMultipleCategory/{userid}")
	public List<Notification> getDataByUseridAndMultiple(@PathVariable Long userid)
	{
		List<Notification> notifications = notificationRepo.findAllByUseridAndCategoryStartingWith(userid);
		return notifications;
	}

	@GetMapping("/getListByClientid/{clientid}")
	public List<Notification> getListByClientid(@PathVariable Long clientid)
	{
		List<Notification> c=notificationRepo.findAllByClientid(clientid);
		return c;
	}

	@GetMapping("/notification/{id}")
	public ResponseEntity<?> getNotificationById(@PathVariable Long id)
	{
		logger.info("Fetching payment details for id: {}",id);


		Optional<Notification> paymentDetailsOptional = notificationRepo.findById(id);

		if (paymentDetailsOptional.isPresent()) {

			Notification clientDetails = paymentDetailsOptional.get();
			//String s3Key = getS3Key(clientDetails.getImagePath());
			String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath());

			System.out.println(s3Key);

			try {
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
				headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePath()).build());

				// Create a response map with both paymentDetails and content
				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("paymentDetails", clientDetails);
				responseMap.put("content", content);


				// Return ResponseEntity with headers and content length
				return ResponseEntity.ok(responseMap);



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
			logger.warn("Payment details not found for userid: {}", id);
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/getNotificationDataByUseridAndCategory/{userid}/{category}")
	public ResponseEntity<List<Notification>> getNotificationDataByUserid(@PathVariable Long userid,@PathVariable String category)
	{
		List<Notification> data = notificationRepo.findByUseridAndCategory(userid,category);
		if (data.isEmpty())
		{
			return ResponseEntity.ok(data);
		}
		return ResponseEntity.ok(data);

	}


}
