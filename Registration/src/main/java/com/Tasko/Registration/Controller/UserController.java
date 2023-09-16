package com.Tasko.Registration.Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import com.Tasko.Registration.Entity.*;
import com.Tasko.Registration.Repository.FileRepository;
import com.Tasko.Registration.Repository.Filed_NotFiledRepo;
import com.Tasko.Registration.Service.JwtService;
import com.Tasko.Registration.dto.*;
import com.Tasko.Registration.error.*;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.Tasko.Registration.Repository.TaskoRepository;
import com.Tasko.Registration.Service.TaskoService;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

@RestController
@CrossOrigin(origins = "*")
public class UserController
{
	@Autowired
	private JwtService jwtService;
	@Autowired
   private TaskoService taskoService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private TaskoRepository taskoRepository;

	//private static final Logger logger = LogManager.getLogger(UserController.class);

	Logger logger=LoggerFactory.getLogger(UserController.class);


	@PostMapping("/createuser")
	public User_RegistrationsForm saveUser(@RequestBody User_RegistrationsForm user) throws Exception {
		logger.info("Received a request to create a user: {}", user.getPan());

		try {
			return taskoService.saveUser(user);
		} catch (Exception e) {
			logger.error("An error occurred while saving the user: {}", e.getMessage());
			throw e;
		}
	}

	@GetMapping("/getuser")
	public List<User_RegistrationsForm> FetchUser(Filed_NotFiled f)
	{
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
	public String deleteUserById(@PathVariable("id") Long RegId)
	{
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
	public User_RegistrationsForm updateUser(@RequestBody User_RegistrationsForm user,@PathVariable("id") Long RegId)
	{
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
	public Client_Registation_Form updateClient(@RequestBody Client_Registation_Form client,@PathVariable("id")Long clientId)
	{
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

	@PostMapping("/createclient")
	public Client_Registation_Form saveclient(@RequestBody Client_Registation_Form client) throws UserAlreadyExist, EmailMandatoryException {
		logger.info("Received a request to create a client: {}", client.getPan());

		try {
			Client_Registation_Form savedClient = taskoService.saveclient(client);
			logger.info("Client with ID {} has been created.", savedClient.getClientId());
			return savedClient;
		} catch (UserAlreadyExist e) {
			logger.error("User already exists: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("An error occurred while saving client: {}", e.getMessage());
			throw e;
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
															 @PathVariable Long clientId) throws UserNotFoundException
	{

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
	public ResponseEntity<ClientCountsDTO> getClientCountsByUserId(@PathVariable("userid") Long userid)
	{

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
	public List<Client_Registation_Form> getClientByUserid(@PathVariable("id") Long userid)
	{
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
	public List<Client_Registation_Form> getcategory(@PathVariable("id") Long userid)
	{
		logger.info("Received a request to get clients with category 'Income_Tax' for user ID: {}", userid);

		try {
			List<Client_Registation_Form> clients = taskoService.getcategory("Income_Tax", userid);
			logger.info("Clients with category 'Income_Tax' fetched successfully for user ID: {}", userid);
			return clients;
		} catch (Exception e) {
			logger.error("An error occurred while fetching clients with category 'Income_Tax': {}", e.getMessage());
			throw e;
		}
	}

	//--------------------------File Upload And Download--------------------------------------------------------
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file,
							 @RequestParam("userid") Long userid,
							 @RequestParam("clientid") Long clientid,
							 @RequestParam("accountyear") String accountyear,
							 @RequestParam("filename") String fileName) throws MultipartException,FileAlreadyExists, IOException
	{
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
													 @PathVariable String accountyear)
	{
		logger.info("Received a request to get files for user ID {}, client ID {}, and account year {}",
				userid, clientid, accountyear);

		try
		{
			List<FileEntity> files = taskoService.getFilesByUserAndClientAndYear(userid, clientid, accountyear);
			logger.info("Files fetched successfully for user ID {}, client ID {}, and account year {}",
					userid, clientid, accountyear);
			return  ResponseEntity.ok(files);
		}
		catch (Exception e) {
			logger.error("An error occurred while fetching files: {}", e.getMessage());
			throw e;
		}

	}

	@GetMapping("/download/{id}/{userid}/{clientid}/{accountyear}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id,
												 @PathVariable Long userid,
												 @PathVariable Long clientid,
												 @PathVariable String accountyear) throws IOException
	{
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
//	@DeleteMapping("/deletefile")
//	public ResponseEntity<String> deleteMultipleFiles(@RequestBody List<Long> id){
//		try {
//			taskoService.deleteMultipleFiles(id);
//			logger.info("Files with IDs {} deleted successfully.", id);
//			return ResponseEntity.ok("Files deleted successfully.");
//		} catch (IOException e) {
//			logger.error("Error deleting files with IDs " + id, e);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting files.");
//		}
//	}

	@Autowired
	private AmazonS3 amazonS3;

//	@Value("${aws.s3.bucketName}")
//	private String bucketName;

	String bucketName="arkonet";

	@DeleteMapping("/deletefile")
	@Transactional
	public ResponseEntity<String> deleteFiles(@RequestBody DeleteFilesRequest request) {
		try {
			for (Long fileId : request.getFileIds())
			{
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
//	@GetMapping("/openfile/{fileId}")
//	public ResponseEntity<Resource> openFile(@PathVariable Long fileId) {
//		try {
//			Optional<FileEntity> fileEntityOptional = fileRepository.findById(fileId);
//
//			if (fileEntityOptional.isPresent()) {
//				FileEntity fileEntity = fileEntityOptional.get();
//				Path filePath = (Path) Paths.get(fileEntity.getFilePath());
//
//				InputStreamResource resource = new InputStreamResource(Files.newInputStream((java.nio.file.Path) filePath));
//
//				HttpHeaders headers = new HttpHeaders();
//				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//				headers.setContentDisposition(ContentDisposition.attachment().filename(fileEntity.getFileName()).build());
//
//				return ResponseEntity.ok()
//						.headers(headers)
//						.body(resource);
//			} else {
//				return ResponseEntity.notFound().build();
//			}
//		} catch (IOException e) {
//			logger.error("An I/O error occurred while opening the file: {}", e.getMessage());
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//	}




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
	public ResponseEntity<String> sendOTPByEmail(@RequestParam String pan) throws UserNotFoundException
	{
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
	public ResponseEntity<String> verifyOTP( @RequestParam String otp) throws OtpNotVaild
	{
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
	public ResponseEntity<String> resetPassword(@RequestParam String otp,@RequestParam String newPassword) throws OtpNotVaild
	{
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
	@Autowired
	private Filed_NotFiledRepo filed_notFiledRepo;

	@PostMapping("/saveData")
	public ResponseEntity<String> saveData(@RequestBody Filed_NotFiled data)
	{
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
									@PathVariable String accountyear)
	{
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
	public ResponseEntity<List<filed_NotfiledDTO>> getFileCountsByUser(@PathVariable Long userid)
	{
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
			@PathVariable String accountyear)
	{
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
	public ResponseEntity<Map<String, String>> getMaxLastUpdateDateByUserId(@PathVariable("id") Long userid)
	{
		Optional<LocalDate> maxLastUpdateDate = Optional.ofNullable(filed_notFiledRepo.findMaxLastUpdateDateByUserid(userid));
		if (maxLastUpdateDate.isPresent())
		{
			Map<String, String> response = new HashMap<>();
			response.put("lastUpdateDate", maxLastUpdateDate.get().toString());
			return ResponseEntity.ok(response);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}



	//----------------------------------------------Login Page------------------------------------------------------------------------------------------------
	@PostMapping("/authenticate")
	public ResponseEntity<Object> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws Exception
	{
		logger.info("Received an authentication request for username: {}", authRequest.getUsername());

		try {

			Optional<User_RegistrationsForm> user = taskoRepository.findByPan(authRequest.getUsername());

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

				if(user.isPresent())
				{
					if (authentication.isAuthenticated()) {
						String jwt = jwtService.generateToken(authRequest.getUsername());

						authuser response = new authuser(user, jwt);
						response.setToken(jwt);
						response.setUser(user);

						logger.info("User {} has been authenticated successfully.", authRequest.getUsername());
						return ResponseEntity.ok(response);
					}
				}
				else
				{
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

	@GetMapping("/check")
	public String getLoggingData()
	{
		logger.info("Data from getdata()");
		return"usercontroller";
	}

}