package com.Tasko.Registration.Controller;

import com.Tasko.Registration.Entity.*;
import com.Tasko.Registration.Repository.*;
import com.Tasko.Registration.Service.JwtService;
import com.Tasko.Registration.Service.TaskoService;
import com.Tasko.Registration.dto.AuthRequestClient;
import com.Tasko.Registration.dto.ChangePassword;
import com.Tasko.Registration.dto.ClientGetFile;
import com.Tasko.Registration.dto.SetClientPassword;
import com.Tasko.Registration.error.OtpNotVaild;
import com.Tasko.Registration.error.UserNotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.swing.Box.Filler;


@RestController
@CrossOrigin(origins = "*")
public class ClientMobileViewController
{
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TaskoService taskoService;
    
    @Autowired
    private TaskoRepository taskoRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private paymentDetailsRepo detailsRepository;
    
    @Autowired
    private Filed_NotFiledRepo filed_notFiledRepo;
    @Autowired
    private ClientPassRepository clientPassRepository;
    @Autowired
    private DocinfoRepository docinfoRepository;

    @Autowired
    private FileRepository fileRepository;
    
    @Autowired
    private KycclientRepository kycclientRepository;
    
    @Autowired
    private GST_FileUploadRepo gst_FileUploadRepo;
    
    
    @Autowired
	private Filed_NotFiled_GST_repo filed_GST_repo;
    
    @Autowired
    private Invest_NowRepository invest_NowRepository;
    
    @Autowired
    private HelpRepository helpRepository;
    
    @Autowired
    private Client_tally_backupfileRepository client_tally_backupfileRepository;

    @Autowired
    private Client_Registation_Form_Temp_Repo tempRepo;
    
    
   Logger logger= LoggerFactory.getLogger(ClientMobileViewController.class);
   //  Logger logger=LoggerFactory.getLogger(UserController.class);
    
  ///////////////////////////////////////all inormation about user to fetch user to pan//////////////////////////////////////
  @GetMapping("/pan")
  public ResponseEntity<Map<String, Object>> getUsersByPan(@RequestParam("pan") String pan)
  {
      List<Client_Registation_Form> users = clientRepository.findByPan1(pan);
      List<Client_Registation_Form_Temp> tempclient = tempRepo.findByPan1(pan);

      if (!tempclient.isEmpty())
      {
          Map<String, Object> r = new HashMap<>();
          r.put("users", tempclient);
          return ResponseEntity.ok(r);
      }


      if (!users.isEmpty()) {
          // Create a map to hold multiple values in the response
          Map<String, Object> response = new HashMap<>();
          response.put("users", users);
          int mapSize = response.size();

//            if (mapSize == 2) {
//                // Return an error response with a specific status code, such as 400 Bad Request
//                return ResponseEntity.badRequest().build();
          //           }
          if (mapSize == 1)
          {
              // Return a successful response with the map containing user data
              return ResponseEntity.ok(response);
          }
          else
          {
              // You can add additional values to the response map if needed
              // response.put("someOtherData", someOtherData);

              // Return an error response with a specific status code, such as 400 Bad Request
              return ResponseEntity.badRequest().build();
          }
      } else {
          // Return a not found response
          return ResponseEntity.notFound().build();
      }
  }
    /////////////////this api remove autorizAation//////////////////////////
    @GetMapping("/taxreturn/pan")
    public ResponseEntity<Map<String, Object>> getUsersByPan2(@RequestParam("pan") String pan) 
    {
        List<Client_Registation_Form> users = clientRepository.findByPan1(pan);
   
        if (!users.isEmpty()) {
            // Create a map to hold multiple values in the response
            Map<String, Object> response = new HashMap<>();
            response.put("users", users);
            int mapSize = response.size();

//            if (mapSize == 2) {
//                // Return an error response with a specific status code, such as 400 Bad Request
//                return ResponseEntity.badRequest().build();
 //           } 
         if (mapSize == 1) 
         {
                // Return a successful response with the map containing user data
                return ResponseEntity.ok(response);
            } 
         else 
         {
                // You can add additional values to the response map if needed
                // response.put("someOtherData", someOtherData);

                // Return an error response with a specific status code, such as 400 Bad Request
                return ResponseEntity.badRequest().build();
            }
        } else {
            // Return a not found response
            return ResponseEntity.notFound().build();
        }
    }


    //----------------------------------check client password status---------------------------------------------------
//    @PostMapping("/client/isPasswordNull")
//    public ResponseEntity<Map<String, Object>> checkPassword(@RequestBody SetClientPassword setClientPassword) {
//        Optional<Client_Registation_Form> check = clientRepository.findByPan(setClientPassword.getPan());
//
//        if (check.isPresent()) {
//            Client_Registation_Form client = check.get();
//            boolean isPasswordNull = client.getPassword() == null;
//
//            logger.info("Password check for PAN {}: Is password null? {}", setClientPassword.getPan(), isPasswordNull);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("isPasswordNull", isPasswordNull);
//
//            return ResponseEntity.ok(response);
//        } else {
//            logger.warn("Password check failed for PAN: {}", setClientPassword.getPan());
//            return ResponseEntity.notFound().build();
//        }
//    }
  //-----------------------------check client password status new--------------------------------//
    
    // @PostMapping("/clientnew/isPasswordNull")
    @PostMapping("/client/isPasswordNull")
    public ResponseEntity<Map<String, Object>> checkPassword1(@RequestBody SetClientPassword setClientPassword)
    {
        Optional<ClientPass_Imgdetail> check = clientPassRepository.findByPan(setClientPassword.getPan());

        Optional<Client_Registation_Form_Temp> temppan=tempRepo.findByPan(setClientPassword.getPan());

        if (temppan.isPresent()) {
            Client_Registation_Form_Temp client = temppan.get();
            boolean isPasswordNull = client.getPassword() == null;

            logger.info("Password check for PAN {}: Is password null? {}", setClientPassword.getPan(), isPasswordNull);

            Map<String, Object> response = new HashMap<>();
            response.put("isPasswordNull", isPasswordNull);
            response.put("TableName", "Temp");

            return ResponseEntity.ok(response);
        }

        if (check.isPresent()) {
            ClientPass_Imgdetail client = check.get();
            boolean isPasswordNull = client.getPassword() == null;

            logger.info("Password check for PAN {}: Is password null? {}", setClientPassword.getPan(), isPasswordNull);

            Map<String, Object> response = new HashMap<>();
            response.put("isPasswordNull", isPasswordNull);
            response.put("TableName", "ClientReg");

            return ResponseEntity.ok(response);
        }
        else {
            logger.warn("Password check failed for PAN: {}", setClientPassword.getPan());
            return ResponseEntity.notFound().build();
        }
    }

   //-----------------------------------set image client in s3 bucke an save path-------------------------------------------//
     @PutMapping("/client/uploadimage")
     public ResponseEntity<Object> uploadImage(
             @RequestParam String pan,
             @RequestParam("image") MultipartFile image) {
         try {
             // Check if the client with the given PAN exists
             Optional<ClientPass_Imgdetail> existingPaymentDetailsOptional = clientPassRepository.findByPan(pan);

             if (!existingPaymentDetailsOptional.isPresent()) {
                 return ResponseEntity.notFound().build();
             }

             ClientPass_Imgdetail existingPaymentDetails = existingPaymentDetailsOptional.get();

             if (image != null && !image.isEmpty()) {
                 String pannum = existingPaymentDetails.getPan();
                 String imageName = generateUniqueName(pannum);

                 String name = image.getOriginalFilename();
                 String[] result = name.split("\\.");
                 String fileExtension = result[result.length - 1];

                 ObjectMetadata metadata = new ObjectMetadata();
                 metadata.setContentType(image.getContentType());
                 InputStream inputStream = image.getInputStream();
                 String s3Key = imageName + "." + fileExtension;

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
             // Save the updated payment details
             ClientPass_Imgdetail updatedPaymentDetails = clientPassRepository.save(existingPaymentDetails);

             return ResponseEntity.ok(updatedPaymentDetails);
         } catch (Exception e) {
             // Handle exceptions here, log them, and return an error response
             e.printStackTrace(); // Log the exception for debugging
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
             		+ "ing image");
         }
     }

     private String generateUniqueName(String pannum )
     {
     	return pannum;
     }
 //--------------------------------get image api for client--------------------------------------------------------//
     @GetMapping("/getclientimage/{pan}")
     public ResponseEntity<?> getclientiamge(@PathVariable String pan) {
         logger.info("Fetching payment details for userid: {}", pan);

         Optional<ClientPass_Imgdetail> paymentDetailsOptional = clientPassRepository.findByPan(pan);
     	
         if (paymentDetailsOptional.isPresent()) {
             ClientPass_Imgdetail clientDetails = paymentDetailsOptional.get();
             String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath());

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
                 headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePath()).build());

                 // Create a response map with both paymentDetails and content
                 Map<String, Object> responseMap = new HashMap<>();
               //  responseMap.put("paymentDetails", clientDetails);
                 responseMap.put("content",content);

                 logger.info("Payment details found for userid: {}", pan);

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
             logger.warn("Payment details not found for userid: {}", pan);
             return ResponseEntity.notFound().build();
         }
     }

    
    
    //--------------------------------------Set Client Password--------------------------------------------------------
    @PutMapping("/client/SetPassword")
    public ResponseEntity<String> setpassword(@RequestBody SetClientPassword setClientPassword) {
        try {
            taskoService.setpassword(setClientPassword.getPan(), setClientPassword.getNewPassword());
            logger.info("Password changed for PAN: {}", setClientPassword.getPan());
            return ResponseEntity.ok("Password changed successfully.");
        } catch (Exception e) {
            logger.error("Error changing password for PAN: " + setClientPassword.getPan(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error changing password.");
        }
    }

    //--------------------------------------.0. Page-------------------------------------------------------------
//    @PostMapping("/client/authenticate")
//    public ResponseEntity<Object> authenticateAndGetClientToken(@RequestBody AuthRequestClient authRequestClient) {
//
//        try {
//            String clientPAN = authRequestClient.getClientusername();
//            Optional<ClientPass_Imgdetail> client = clientPassRepository.findByPan(clientPAN);
//
//            if (client.isPresent()) {
//                // Replace the PAN with the actual username from the retrieved client
//                String clientUsername = client.get().getPan();
//                String clientPassword = authRequestClient.getClientpassword();
//
//                Authentication authenticationClient = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(clientUsername, clientPassword));
//
//                if (authenticationClient.isAuthenticated()) {
//                    System.out.println("get Client");
//                    String jwt = jwtService.generateToken(clientUsername);
//
//                    authclient response = new authclient();
//                    response.setToken(jwt);
////                    response.setClient(client);
//
//                    logger.info("User {} has been authenticated successfully.", clientPAN);
//                    return ResponseEntity.ok(response);
//               }
//            } else {
//                throw new Exception("Client Not Found");
//            }
//        } catch (Exception e) {
//            logger.error("Authentication failed for user {}: {}", authRequestClient.getClientusername(), e.getMessage());
//            return ResponseEntity
//                .status(HttpStatus.UNAUTHORIZED)
//                .body(e.getMessage());
//        }
//
//        // Return an unauthorized response for unexpected cases
//       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Failed");
//    }
    //-----------------------------------------Login------------------------------------------------
    @PostMapping("/client/authenticate")
    public ResponseEntity<Object> authenticateAndGetClientToken(@RequestBody AuthRequestClient authRequestClient)
    {
        try {
            String clientPAN = authRequestClient.getClientusername();

            // Check in ClientPass_Imgdetail table
            Optional<ClientPass_Imgdetail> clientPass = clientPassRepository.findByPan(clientPAN);

            if (clientPass.isPresent()) {
                String clientUsername = clientPass.get().getPan();
                String clientPassword = authRequestClient.getClientpassword();

                // Authenticate using ClientPass_Imgdetail credentials
                Authentication authenticationClient = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(clientUsername, clientPassword));

                if (authenticationClient.isAuthenticated())
                {
                    System.out.println("Authentication successful for ClientPass_Imgdetail");
                    String jwt = jwtService.generateToken(clientUsername);

                    authclient response = new authclient();
                    response.setToken(jwt);
                    //         response.setClient(clientPass); // Set relevant client information
                    logger.info("User {} has been authenticated successfully.", clientPAN);
                    return ResponseEntity.ok(response);
                }
            }

            // Check in Client_Registation_Form_Temp table
            Optional<Client_Registation_Form_Temp> clientTemp = tempRepo.findByPan(clientPAN);

            if (clientTemp.isPresent()) {
                String tempclientUsername = clientTemp.get().getPan();
                String clientPassword = authRequestClient.getClientpassword();

                // Authenticate using Client_Registation_Form_Temp credentials
                Authentication authenticationTemp = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(tempclientUsername, clientPassword));

                if (authenticationTemp.isAuthenticated())
                {
                    System.out.println("Authentication successful for Client_Registation_Form_Temp");
                    String jwt = jwtService.generateToken(tempclientUsername);

                    authclient response = new authclient();
                    response.setToken(jwt);
                    //             response.setTempClient(clientTemp);

                    logger.info("User {} has been authenticated successfully.", clientPAN);
                    return ResponseEntity.ok(response);
                }
            }

            throw new Exception("Client Not Found");
        } catch (Exception e) {
            logger.error("Authentication failed for user {}: {}", authRequestClient.getClientusername(), e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }






    //------------------------------------Get File By Client Id---------------------------------------------------
    @PostMapping("/client/files")
    public ResponseEntity<List<FileEntity>> getfileByclient(@RequestBody ClientGetFile clientGetFile)
    {
        logger.info("Received a request to get files for client: {} with account year: {}", clientGetFile.getClientid(), clientGetFile.getAccountyear());

        try {
            List<FileEntity> getfiles = taskoService.getfileByclient(clientGetFile.getClientid(), clientGetFile.getAccountyear());

            logger.info("Retrieved {} files for client: {} with account year: {}", getfiles.size(), clientGetFile.getClientid(), clientGetFile.getAccountyear());
            return ResponseEntity.ok(getfiles);
        } catch (Exception e) {
            logger.error("Error getting files for client: {} with account year: {}", clientGetFile.getClientid(), clientGetFile.getAccountyear(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
///////////////////////////////////////////////max last update for clientN///////////////////////////////////////////////////////////////////
@GetMapping("/maxLastUpdateDate1/{id}")
public ResponseEntity<Map<String, String>> getMaxLastUpdateDateByUserId1(@PathVariable("id") Long userid)
{
Optional<LocalDate> maxLastUpdateDate = Optional.ofNullable(filed_notFiledRepo.findMaxLastUpdateDateByclientid(userid));
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
//////////////////////////////max pdf between all table //////////////////////////////////////////////////////
@GetMapping("/getlastUpdateallonefile/{id}/{gid}/{pan}")
public ResponseEntity<InputStreamResource> getMaxLastUpdateDatealltable(@PathVariable("id") Long clientid,@PathVariable("gid") Long clientid1, @PathVariable("pan") String pan) throws IOException {
//	if(clientid!=null)
//	{
//	 Optional<Timestamp> maxLastUpdateDate = Optional.ofNullable(fileRepository.findMaxLastUpdateDate1Byclientid(clientid));
//	}
//	 if(clientid1!=null)
//	{
//	   Optional<Timestamp> maxLastUpdateDate1 =Optional.ofNullable(gst_FileUploadRepo.findMaxLastUpdateDate1Byclientid(clientid1));
//	}
	Optional<Timestamp> maxLastUpdateDate2 = Optional.ofNullable(kycclientRepository.findMaxLastUpdateDate4ByPan(pan));
    Optional<Timestamp> maxLastUpdateDate3 = Optional.ofNullable(kycclientRepository.findMaxLastUpdateDate5ByPan(pan));
    Optional<Timestamp> maxLastUpdateDate4 = Optional.ofNullable(kycclientRepository.findMaxLastUpdateDate6ByPan(pan));
    Optional<Timestamp> maxLastUpdateDate5= Optional.ofNullable(docinfoRepository.findmaxLocalDate1ByPan(pan));
    
    List<FileEntity> inidList = fileRepository.findAllByClientid(clientid);
    List<GST_FileUpload> inidList1 = gst_FileUploadRepo.findAllByClientid(clientid1);
    
    
    System.out.println("gst1 iamge");
    Map<String, Optional<Timestamp>> maxDates = new HashMap<>();
    if (!inidList.isEmpty()) {
        maxDates.put("maxLastUpdateDate", Optional.ofNullable(fileRepository.findMaxLastUpdateDate1Byclientid(clientid)));
    }
    if (!inidList1.isEmpty()) {
        maxDates.put("maxLastUpdateDate1", Optional.ofNullable(gst_FileUploadRepo.findMaxLastUpdateDate1Byclientid(clientid1)));
    }
    maxDates.put("maxLastUpdateDate2", maxLastUpdateDate2);
    maxDates.put("maxLastUpdateDate3", maxLastUpdateDate3);
    maxDates.put("maxLastUpdateDate4", maxLastUpdateDate4);
    maxDates.put("maxLastUpdateDate5", maxLastUpdateDate5);
    System.out.println("gst2 iamge");
    // Find the maximum date among the available dates
    Optional<Timestamp> maxDate = maxDates.values().stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .max(Timestamp::compareTo);

    Map<String, String> response = new HashMap<>();
    System.out.println("gst 3iamge");
    if (maxDate.isPresent()) {
        String maxDateSource = maxDates.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent() && entry.getValue().get().equals(maxDate.get()))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse("Unknown");

        //response.put("maxDateSource", maxDateSource);
        System.out.println();
        
        if("maxLastUpdateDate1".equals( maxDateSource))
        {
        	System.out.println("gst iamge");
        	Long id = gst_FileUploadRepo.findIdByClientId2(clientid1);
       	       Optional<GST_FileUpload> fileEntityOptional = gst_FileUploadRepo.findById(id);
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
        
        
        if("maxLastUpdateDate".equals(maxDateSource))
        {
        	//Optional<LocalDate> maxLastUpdateDate7 = Optional.ofNullable(fileRepository.findMaxLastUpdateDateByclientid1(clientid));
        	System.out.println("file iamge");
        	Long id = fileRepository.findIdByClientId2(clientid);
//        	System.out.println(maxLastUpdateDate7);
        	Optional<FileEntity> pdfdetail = fileRepository.findById(id);
        	  FileEntity  cliemtpdf= pdfdetail.get();
        	  cliemtpdf.getFileName();
        	  cliemtpdf.getFilePath();
        	 
        	  System.out.println(cliemtpdf.getFilePath());

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
        
        if("maxLastUpdateDate2".equals(maxDateSource))
        {
        	System.out.println("kyc1 iamge");
            Optional<Kyc_client_detail> paymentDetailsOptional = kycclientRepository.findByPan(pan);
        	
            if (paymentDetailsOptional.isPresent()) {
                Kyc_client_detail clientDetails = paymentDetailsOptional.get();
                String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath());
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
                    headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePath()).build());

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
        
        if("maxLastUpdateDate3".equals(maxDateSource))
        {
        	System.out.println("kyc2 iamge");
            Optional<Kyc_client_detail> paymentDetailsOptional = kycclientRepository.findByPan(pan);
        	
            if (paymentDetailsOptional.isPresent()) {
                Kyc_client_detail clientDetails = paymentDetailsOptional.get();
                String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath2());
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
                    headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePath2()).build());

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
       
       
       
       if("maxLastUpdateDate4".equals(maxDateSource))
       {
    	   System.out.println("kyc3 iamge");
    	    Optional<Kyc_client_detail> paymentDetailsOptional = kycclientRepository.findByPan(pan);
    		
    	    if (paymentDetailsOptional.isPresent()) {
    	        Kyc_client_detail clientDetails = paymentDetailsOptional.get();
    	        String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath3());
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
    	            headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePath3()).build());

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
       
       if("maxLastUpdateDate5".equals( maxDateSource))
       {System.out.println("doc iamge");
    	   Optional<docinfo> paymentDetailsOptional = docinfoRepository.findByPan(pan);
    		
    	    if (paymentDetailsOptional.isPresent()) {
    	        docinfo clientDetails = paymentDetailsOptional.get();
    	        String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath());

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
    	            headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePath()).build());

    	            // Create a response map with both paymentDetails and content
    	            Map<String, Object> responseMap = new HashMap<>();
    	          //  responseMap.put("paymentDetails", clientDetails);
    	            responseMap.put("content",content);
    	            responseMap.put("content",resource);
    	            
    	            return ResponseEntity.ok()
    	            		.headers(headers)
    	            		.body(resource);
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
    } else {
        // If no maximum date is available, add available records
        maxDates.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent())
                .forEach(entry -> response.put(entry.getKey(), entry.getValue().get().toString()));

        response.put("record", "No record available");
    }
	return null;


}





//////////////////////////////max pdf between all table //////////////////////////////////////////////////////
@GetMapping("/getlastUpdateallinformation/{id}/{gid}/{pan}")
public ResponseEntity<Map<String,Object>> getMaxLastUpdateDatealltable1(@PathVariable("id") Long clientid,@PathVariable("gid") Long clientid1, @PathVariable("pan") String pan) throws IOException {
//	Optional<Timestamp> maxLastUpdateDate = Optional.ofNullable(fileRepository.findMaxLastUpdateDate1Byclientid(clientid));
//	Optional<Timestamp> maxLastUpdateDate1 =Optional.ofNullable(gst_FileUploadRepo.findMaxLastUpdateDate1Byclientid(clientid1));
    Optional<Timestamp> maxLastUpdateDate2 = Optional.ofNullable(kycclientRepository.findMaxLastUpdateDate4ByPan(pan));
    Optional<Timestamp> maxLastUpdateDate3 = Optional.ofNullable(kycclientRepository.findMaxLastUpdateDate5ByPan(pan));
    Optional<Timestamp> maxLastUpdateDate4 = Optional.ofNullable(kycclientRepository.findMaxLastUpdateDate6ByPan(pan));
    Optional<Timestamp> maxLastUpdateDate5= Optional.ofNullable(docinfoRepository.findmaxLocalDate1ByPan(pan));
    List<FileEntity> inidList = fileRepository.findAllByClientid(clientid);
    List<GST_FileUpload> inidList1 = gst_FileUploadRepo.findAllByClientid(clientid1);
    Map<String, Optional<Timestamp>> maxDates = new HashMap<>();
//    maxDates.put("maxLastUpdateDate", maxLastUpdateDate);
//    maxDates.put("maxLastUpdateDate1", maxLastUpdateDate1);
    if (!inidList.isEmpty()) {
        maxDates.put("maxLastUpdateDate", Optional.ofNullable(fileRepository.findMaxLastUpdateDate1Byclientid(clientid)));
    }
    if (!inidList1.isEmpty()) {
        maxDates.put("maxLastUpdateDate1", Optional.ofNullable(gst_FileUploadRepo.findMaxLastUpdateDate1Byclientid(clientid1)));
    }
    maxDates.put("maxLastUpdateDate2", maxLastUpdateDate2);
    maxDates.put("maxLastUpdateDate3", maxLastUpdateDate3);
    maxDates.put("maxLastUpdateDate4", maxLastUpdateDate4);
    maxDates.put("maxLastUpdateDate5", maxLastUpdateDate5);
  
    // Find the maximum date among the available dates
    Optional<Timestamp> maxDate = maxDates.values().stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .max(Timestamp::compareTo);

    Map<String, String> response = new HashMap<>();
   
    if (maxDate.isPresent()) {
        String maxDateSource = maxDates.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent() && entry.getValue().get().equals(maxDate.get()))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse("Unknown");

        //response.put("maxDateSource", maxDateSource);
        System.out.println(maxDateSource+"datasource");

if("maxLastUpdateDate1".equals(maxDateSource))
{
System.out.println("gst iamge");
Long sid = gst_FileUploadRepo.findIdByClientId2(clientid1);
System.out.println(sid);
Optional<GST_FileUpload> fileEntityOptional = gst_FileUploadRepo.findById(sid);
if (fileEntityOptional.isPresent()) {
	GST_FileUpload clientDetails = fileEntityOptional.get();
	String s3Key = getS3KeyFromFilePath(clientDetails.getFilePath());
	System.out.println(s3Key);
		// Create a response map with both paymentDetails and content
//		Map<String, Object> responseMap = new HashMap<>();
//		responseMap.put("data", clientDetails);
//		return ResponseEntity.ok(responseMap);
	Map<String, Object> responseMap = new HashMap<>();
	responseMap.put("imagePath", clientDetails.getFilePath());
	responseMap.put("imageName",clientDetails.getFileName());
	responseMap.put("lastUpdateDate1",clientDetails.getLastUpdateDate());
	return ResponseEntity.ok(responseMap);
	// Return ResponseEntity with headers and content length
	} else {
	logger.warn("Payment details not found for userid: {}", pan);
	return ResponseEntity.notFound().build();
	}


}

if("maxLastUpdateDate".equals(maxDateSource))
{
//Optional<LocalDate> maxLastUpdateDate7 = Optional.ofNullable(fileRepository.findMaxLastUpdateDateByclientid1(clientid));
System.out.println("file iamge");
Long id = fileRepository.findIdByClientId2(clientid);
//System.out.println(maxLastUpdateDate7);
Optional<FileEntity> pdfdetail = fileRepository.findById(id);
FileEntity  cliemtpdf= pdfdetail.get();
cliemtpdf.getFileName();
cliemtpdf.getFilePath();

if (pdfdetail.isPresent()) {
	FileEntity clientDetails = pdfdetail.get();
	String s3Key = getS3KeyFromFilePath(clientDetails.getFilePath());
	System.out.println(s3Key);
		// Create a response map with both paymentDetails and content
//		Map<String, Object> responseMap = new HashMap<>();
//		responseMap.put("data", clientDetails);
//		return ResponseEntity.ok(responseMap);
	Map<String, Object> responseMap = new HashMap<>();
	responseMap.put("imagePath", clientDetails.getFilePath());
	responseMap.put("imageName",clientDetails.getFileName());
	responseMap.put("lastUpdateDate1",clientDetails.getLastUpdateDate());
	return ResponseEntity.ok(responseMap);
	// Return ResponseEntity with headers and content length
	} else {
	logger.warn("Payment details not found for userid: {}", pan);
	return ResponseEntity.notFound().build();
	}

}

if("maxLastUpdateDate2".equals(maxDateSource))
{
	System.out.println("kyc2 iamge");
	Optional<Kyc_client_detail> paymentDetailsOptional = kycclientRepository.findByPan(pan);
	if (paymentDetailsOptional.isPresent()) {
	Kyc_client_detail clientDetails = paymentDetailsOptional.get();
	String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath());
	System.out.println(s3Key);
		// Create a response map with both paymentDetails and content
//		Map<String, Object> responseMap = new HashMap<>();
//		responseMap.put("data", clientDetails);
//		return ResponseEntity.ok(responseMap);
	Map<String, Object> responseMap = new HashMap<>();
	responseMap.put("imagePath", clientDetails.getImagePath());
	responseMap.put("imageName",clientDetails.getImageName());
	responseMap.put("lastUpdateDate1",clientDetails.getLastUpdateDate1());
	return ResponseEntity.ok(responseMap);
	// Return ResponseEntity with headers and content length
	} else {
	logger.warn("Payment details not found for userid: {}", pan);
	return ResponseEntity.notFound().build();
	}
}


if("maxLastUpdateDate3".equals(maxDateSource))
{
	System.out.println("kyc2 iamge");
	Optional<Kyc_client_detail> paymentDetailsOptional = kycclientRepository.findByPan(pan);
	if (paymentDetailsOptional.isPresent()) {
	Kyc_client_detail clientDetails = paymentDetailsOptional.get();
	String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath2());
	System.out.println(s3Key);
		// Create a response map with both paymentDetails and content
//		Map<String, Object> responseMap = new HashMap<>();
//		responseMap.put("data", clientDetails);
//		return ResponseEntity.ok(responseMap);
	Map<String, Object> responseMap = new HashMap<>();
	responseMap.put("imagePath", clientDetails.getImagePath2());
	responseMap.put("imageName",clientDetails.getImageName2());
	responseMap.put("lastUpdateDate1",clientDetails.getLastUpdateDate2());
	return ResponseEntity.ok(responseMap);
	// Return ResponseEntity with headers and content length
	} else {
	logger.warn("Payment details not found for userid: {}", pan);
	return ResponseEntity.notFound().build();
	}
}




if("maxLastUpdateDate4".equals(maxDateSource))
{
System.out.println("kyc3 iamge");
Optional<Kyc_client_detail> paymentDetailsOptional = kycclientRepository.findByPan(pan);
if (paymentDetailsOptional.isPresent()) {
Kyc_client_detail clientDetails = paymentDetailsOptional.get();
String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath3());
System.out.println(s3Key);
	// Create a response map with both paymentDetails and content
Map<String, Object> responseMap = new HashMap<>();
responseMap.put("imagePath", clientDetails.getImagePath3());
responseMap.put("imageName",clientDetails.getImageName3());
responseMap.put("lastUpdateDate1",clientDetails.getLastUpdateDate3());
return ResponseEntity.ok(responseMap);
// Return ResponseEntity with headers and content length
} else {
logger.warn("Payment details not found for userid: {}", pan);
return ResponseEntity.notFound().build();
}
}

if("maxLastUpdateDate5".equals( maxDateSource))
{System.out.println("doc iamge");
Optional<docinfo> paymentDetailsOptional = docinfoRepository.findByPan(pan);

if (paymentDetailsOptional.isPresent()) {
docinfo clientDetails = paymentDetailsOptional.get();
String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath());
System.out.println(s3Key);
	// Create a response map with both paymentDetails and content
//	Map<String, Object> responseMap = new HashMap<>();
//	responseMap.put("data", clientDetails);
//	return ResponseEntity.ok(responseMap);
Map<String, Object> responseMap = new HashMap<>();
responseMap.put("imagePath", clientDetails.getImagePath());
responseMap.put("imageName",clientDetails.getImageName());
responseMap.put("lastUpdateDate1",clientDetails.getLastUpdateDate1());
return ResponseEntity.ok(responseMap);
// Return ResponseEntity with headers and content length
} else {
logger.warn("Payment details not found for userid: {}", pan);
return ResponseEntity.notFound().build();
}
}   
} else {
// If no maximum date is available, add available records
maxDates.entrySet()
.stream()
.filter(entry -> entry.getValue().isPresent())
.forEach(entry -> response.put(entry.getKey(), entry.getValue().get().toString()));

response.put("record", "No record available");
}
return null;


}



////////////////////////////////////MAX DATE IN FILE TABLE THEN YOU UPLOAD A FILE///////////////////////////////////////
@GetMapping("/maxLastUpdateDatefile/{id}")
public ResponseEntity<Map<String, String>> getMaxLastUpdateDateByUserIdFILE(@PathVariable("id") Long userid)
{
Optional<LocalDate> maxLastUpdateDate = Optional.ofNullable(fileRepository.findMaxLastUpdateDateByclientid(userid));
Optional<LocalDate> maxLastUpdateDate1 = Optional.ofNullable(gst_FileUploadRepo.findMaxLastUpdateDateByclientid(userid));

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

///////////////////////////////max date between filed_notfild table and file table//////////////////////////////////////////// 
@GetMapping("/maxLastUpdateDatefileandfilednotfiled/{id}")
public ResponseEntity<Map<String, String>> getMaxLastUpdateDateByUserId2(@PathVariable("id") Long userid)
{
 Optional<LocalDate> maxLastUpdateDate = Optional.ofNullable(filed_notFiledRepo.findMaxLastUpdateDateByclientid(userid));
 
 Optional<LocalDate> maxLastUpdateDate1 = Optional.ofNullable(fileRepository.findMaxLastUpdateDateByclientid(userid));
  
 if (maxLastUpdateDate.isPresent()&&maxLastUpdateDate1.isPresent())
 {
	 int comparison = maxLastUpdateDate.get().compareTo(maxLastUpdateDate1.get());

	if (comparison < 0) {
		Map<String, String> response = new HashMap<>();
		response.put("lastUpdateDate", maxLastUpdateDate1.get().toString());
		return ResponseEntity.ok(response);
	} else if (comparison > 0) {
		Map<String, String> response = new HashMap<>();
		response.put("lastUpdateDate", maxLastUpdateDate.get().toString());
		return ResponseEntity.ok(response);
	} else {
		if(maxLastUpdateDate.isPresent())
		{
			Map<String, String> response = new HashMap<>();
			response.put("lastUpdateDate", maxLastUpdateDate.get().toString());
			return ResponseEntity.ok(response);	
		}
		else
		{
			Map<String, String> response = new HashMap<>();
			response.put("lastUpdateDate", maxLastUpdateDate1.get().toString());
			return ResponseEntity.ok(response);	
			
		}
	}
	
}
else if(maxLastUpdateDate.isPresent()||maxLastUpdateDate1.isPresent())
		{
	if(maxLastUpdateDate.isPresent())
	{
		Map<String, String> response = new HashMap<>();
		response.put("lastUpdateDate", maxLastUpdateDate.get().toString());
		return ResponseEntity.ok(response);	
	}
	else
	{
		Map<String, String> response = new HashMap<>();
		response.put("lastUpdateDate", maxLastUpdateDate1.get().toString());
		return ResponseEntity.ok(response);	
		
	}
		}
else
{
return ResponseEntity.notFound().build();
}
}



////////////////////////////////////MAX DATE IN FILE TABLE THEN YOU UPLOAD A GSTFILE///////////////////////////////////////
@GetMapping("/maxLastUpdateDateGSTfile/{id}")
public ResponseEntity<Map<String, String>> getMaxLastUpdateDateByUserIdGSTFILE(@PathVariable("id") Long userid)
{
Optional<LocalDate> maxLastUpdateDate = Optional.ofNullable(gst_FileUploadRepo.findMaxLastUpdateDateByclientid(userid));
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



////////////////////////////////////MAX DATE IN  A GSTFiledNotFiled///////////////////////////////////////
@GetMapping("/maxLastUpdateDateGSTfilednotfiled/{id}")
public ResponseEntity<Map<String, String>> getMaxLastUpdateDateByUserIdGSTFILEDNOTFILED(@PathVariable("id") Long userid)
{
	 Optional<LocalDate> maxLastUpdateDate = Optional.ofNullable(filed_GST_repo.findMaxLastUpdateDateByclientid(userid));
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



///////////////////////////////max date between GSTfiled_notfild table and GSTfile table//////////////////////////////////////////// 
@GetMapping("/maxLastUpdateDateGSTfileandGSTfilednotfiled/{id}")
public ResponseEntity<Map<String, String>> getMaxLastUpdateDateByUserIdGSTfile(@PathVariable("id") Long userid)
{
  Optional<LocalDate> maxLastUpdateDate = Optional.ofNullable(gst_FileUploadRepo.findMaxLastUpdateDateByclientid(userid));

   Optional<LocalDate> maxLastUpdateDate1 = Optional.ofNullable(filed_GST_repo.findMaxLastUpdateDateByclientid(userid));

 if (maxLastUpdateDate.isPresent()&&maxLastUpdateDate1.isPresent())
 {
     int comparison = maxLastUpdateDate.get().compareTo(maxLastUpdateDate1.get());

    if (comparison < 0) {
    Map<String, String> response = new HashMap<>();
    response.put("lastUpdateDate", maxLastUpdateDate1.get().toString());
    return ResponseEntity.ok(response);
 } else if (comparison > 0) {
   Map<String, String> response = new HashMap<>();
   response.put("lastUpdateDate", maxLastUpdateDate.get().toString());
   return ResponseEntity.ok(response);
 } else {
  if(maxLastUpdateDate.isPresent())
  {
    Map<String, String> response = new HashMap<>();
    response.put("lastUpdateDate", maxLastUpdateDate.get().toString());
    return ResponseEntity.ok(response);	
  }
 else
{
   Map<String, String> response = new HashMap<>();
   response.put("lastUpdateDate", maxLastUpdateDate1.get().toString());
   return ResponseEntity.ok(response);	
}
}

}
 else if(maxLastUpdateDate.isPresent()||maxLastUpdateDate1.isPresent())
{
if(maxLastUpdateDate.isPresent())
{
  Map<String, String> response = new HashMap<>();
  response.put("lastUpdateDate", maxLastUpdateDate.get().toString());
  return ResponseEntity.ok(response);	
}
else
{
Map<String, String> response = new HashMap<>();
response.put("lastUpdateDate", maxLastUpdateDate1.get().toString());
return ResponseEntity.ok(response);	
}
}
else
{
 return ResponseEntity.notFound().build();
}
}



///////////////////////////////////////////////max last update for Document table///////////////////////////////////////////////////////////////////
@GetMapping("/maxLastUpdateDateforDocument/{pan}")
public ResponseEntity<Map<String, String>> getMaxLastUpdateDatedocumentpan(@PathVariable("pan") String  pan)
{
Optional<LocalDate> maxLastUpdateDate = Optional.ofNullable(docinfoRepository.findmaxLocalDateByPan(pan));
if (maxLastUpdateDate.isPresent())
{
Map<String, String> response = new HashMap<>();
response.put("lastUpdateDate1", maxLastUpdateDate.get().toString());
return ResponseEntity.ok(response);
}
else
{
 return ResponseEntity.notFound().build();
}
}


/////////////////////////////////////////////////max last update for Document table///////////////////////////////////////////////////////////////////
//@GetMapping("/maxLastUpdateDateforkycdoc/{pan}")
//public ResponseEntity<Map<String, String>> getMaxLastUpdateDateByclientKyc(@PathVariable("pan") String  pan)
//{
//Optional<LocalDate> maxLastUpdateDate = Optional.ofNullable(kycclientRepository.findMaxLocalDateByPan(pan));
//if (maxLastUpdateDate.isPresent())
//{
//	System.out.println("hello");
//Map<String, String> response = new HashMap<>();
//response.put("lastUpdateDate", maxLastUpdateDate.get().toString());
//return ResponseEntity.ok(response);
//}
//else
//{
//return ResponseEntity.notFound().build();
//}
//}


///////////////////////////////////////////////max last update for Document table///////////////////////////////////////////////////////////////////
//@GetMapping("/maxLastUpdateDateforkycdoc/{pan}")
@GetMapping("/maxLastUpdateDateforkycdoc/{pan}")
public ResponseEntity<LocalDate> getMaxLastUpdateDateByclientKyc(@PathVariable("pan") String pan) {
    Optional<LocalDate> maxLastUpdateDate1 = Optional.ofNullable(kycclientRepository.findMaxLastUpdateDateByPan(pan));
    Optional<LocalDate> maxLastUpdateDate2 = Optional.ofNullable(kycclientRepository.findMaxLast2UpdateDateByPan(pan));
    Optional<LocalDate> maxLastUpdateDate3 = Optional.ofNullable(kycclientRepository.findMaxLast3UpdateDateByPan(pan));
    
    Optional<LocalDate> maxDate = Stream.of(maxLastUpdateDate1, maxLastUpdateDate2, maxLastUpdateDate3)
            .filter(Optional::isPresent)  // Filter out empty optionals
            .map(Optional::get)  // Extract the LocalDate from each Optional
            .max(LocalDate::compareTo);  // Find the maximum date

    if (maxDate.isPresent()) {
        // You have the maximum date among the three options
        LocalDate maxUpdateDate = maxDate.get();
        // Add your logic to use maxUpdateDate
        return ResponseEntity.ok(maxUpdateDate);
    } else {
        // None of the dates were available
        return ResponseEntity.notFound().build();
    }
}


//////////////////////////////////////////max 
//Helper method to extract S3 key from the S3 URL
private String getS3KeyFromFilePath(String s3FilePath) {
//Assuming your S3 URL format is "s3://bucketName/s3Key"
String[] parts = s3FilePath.split("/");
return parts[parts.length - 1];
}


//////////////////////////////////////////get user payment detail  by client N/////////////////////////////////////////////////////////
@Autowired
private AmazonS3 amazonS3;
String bucketName="arkonet";

@GetMapping("/getclientpaymentDetails/{userid}")
public ResponseEntity<?> getAllPaymentDetails(@PathVariable Long userid) {
logger.info("Fetching payment details for userid: {}", userid);

Optional<Payment_Details> paymentDetailsOptional = detailsRepository.findByUserid(userid);


if (paymentDetailsOptional.isPresent()) {
Payment_Details paymentDetails = paymentDetailsOptional.get();
String s3Key = getS3KeyFromFilePath(paymentDetails.getImagePath());

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
  headers.setContentDisposition(ContentDisposition.attachment().filename(paymentDetails.getImagePath()).build());

//Create a response map with both paymentDetails and content
  Map<String, Object> responseMap = new HashMap<>();
  responseMap.put("paymentDetails", paymentDetails);
  responseMap.put("content",content);

  logger.info("Payment details found for userid: {}", userid);

//Return ResponseEntity with headers and content length
  return ResponseEntity.ok(responseMap);
//.headers(headers)
//.contentLength(content.length)
//.body(responseMap);
} catch (AmazonS3Exception e) {
//Handle S3 exception (e.g., object not found)
  logger.error("Error fetching payment details from S3: {}", e.getMessage());
  return ResponseEntity.notFound().build();
} catch (IOException e) {
//Handle IO exception
 logger.error("Error reading payment details from S3: {}", e.getMessage());
  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
 }
}
else {
  logger.warn("Payment details not found for userid: {}", userid);
  return ResponseEntity.notFound().build();
 }
}



///////////////////////////////////get income tax user and gst user by client id N////////////////////////////
@GetMapping("getuserClientById/{clientId}/{category}")
public ResponseEntity<User_RegistrationsForm> getClientByClientidCategory(@PathVariable Long clientId,@PathVariable String category) throws UserNotFoundException {

logger.info("Received a request to get client with ID {} for user ID {}", clientId, category);
try {
Client_Registation_Form client = taskoService.getClientByClientidcategory(clientId,category);

Long regId=client.getUserid();

logger.info("Client with ID {} for user ID {} has been fetched.", clientId, category);
Optional<User_RegistrationsForm> user = taskoRepository.findById(regId);

if (user.isPresent()) {
return ResponseEntity.ok(user.get());
} else {
return ResponseEntity.notFound().build();
}
} catch (UserNotFoundException e) {
logger.error("Client not found for user ID {} and client ID {}: {}", clientId, category,e.getMessage());
throw e;
} catch (Exception e) {
logger.error("An error occurred while fetching client: {}", e.getMessage());
throw e;
}
}


///////////////////////////////////get income tax user and gst user by pan N////////////////////////////
@GetMapping("getuserBypan/{pan}/{category}")
public ResponseEntity<Map<String,Object>> getClientByPanCategory(@PathVariable String pan,@PathVariable String category) throws Exception {

logger.info("Received a request to get client with ID {} for user ID {}", pan, category);
try {
Client_Registation_Form client = taskoService.getClientByPancategory(pan,category);
Long regId=client.getUserid();

logger.info("Client with ID {} for user ID {} has been fetched.", pan, category);
Optional<User_RegistrationsForm> user = taskoRepository.findById(regId);
Optional<Payment_Details> paymentDetailsOptional = detailsRepository.findByUserid(regId);
if (paymentDetailsOptional.isPresent()){
Payment_Details paymentDetails = paymentDetailsOptional.get();
String s3Key = getS3KeyFromFilePath(paymentDetails.getImagePath());

S3Object s3Object = amazonS3.getObject(bucketName, s3Key);

//Get the S3 object content
InputStream s3InputStream = s3Object.getObjectContent();
byte[] content1 = IOUtils.toByteArray(s3InputStream);

//Close the S3 object and release resources
s3InputStream.close();
s3Object.close();

//Create an InputStreamResource from the byte array content
InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(content1));

//Set up headers for the response
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
headers.setContentDisposition(ContentDisposition.attachment().filename(paymentDetails.getImagePath()).build());
if(content1!=null)
{
byte[]  content=content1;
Map<String, Object> responseMap = new HashMap<>();
responseMap.put("userinfo", user.get());
responseMap.put("content",content);
return ResponseEntity.ok(responseMap);
}
}
byte[]  content=null;
Map<String, Object> responseMap = new HashMap<>();
responseMap.put("userinfo", user.get());
responseMap.put("content",content);
return ResponseEntity.ok(responseMap);

} catch (UserNotFoundException e) {
logger.error("Client not found for user ID {} and client ID {}: {}", pan, category,e.getMessage());
throw e;
} catch (Exception e) {
logger.error("An error occurred while fetching client: {}", e.getMessage());
throw e;
}
}




//--------------------------------------------------------------------send to client mail///////////////////////////////////////////////////////////////
@PostMapping("/sendemail_with_attachment")
public ResponseEntity<String> sendByEmail(@PathVariable long id,@RequestBody List<Long> sid) throws UserNotFoundException
{
logger.info("Received a request to send OTP by email for PAN: {}", id,sid);

try {
taskoService.sendByEmail(id, sid);
logger.info("email sent successfully for client: {}", id);
return ResponseEntity.ok("email sent successfully.");
} catch (UserNotFoundException e) {
logger.error("User not found for PAN {}: {}", id, e.getMessage());
throw e;
} catch (Exception e) {
logger.error("An error occurred while sending OTP for PAN {}: {}", id, e.getMessage());
throw e;
}
}


/////////////////////////////////////////////////////////////////////////Invest NO Email///////////////////////////////////////////////////

//@PostMapping("/sendemailclient")
//public String sendEmail(
//@RequestParam Long clientid,
//@RequestParam  Long userid,
//@RequestParam String subject,		
//@RequestBody String body
//) 
//{
//
//Optional<Client_Registation_Form> client = clientRepository.findById(clientid);
//Client_Registation_Form clientdata =client.get();
//logger.info("Client with ID {} for user ID {} has been fetched.", clientid, userid);
//String from=clientdata.getEmail();;
//Optional<User_RegistrationsForm> user = taskoRepository.findById(userid);
//User_RegistrationsForm userdata =user.get();
//String to=userdata.getEmail();
//taskoService.sendEmailwithattachment(to, subject, body);
//return "Email sent successfully.";
//}


//////////////////////////////////////////////////////////////////////////////////////send invest now email/////////////////////////
@PostMapping("/sendemailclient")
public String sendEmail(
@RequestParam Long clientid,
@RequestParam  Long userid,
@RequestParam String subject,
@RequestParam String category,
@RequestParam String mobile,
@RequestBody String body
) 
{

 Optional<Client_Registation_Form> client = clientRepository.findById(clientid);

 Client_Registation_Form clientdata =client.get();

 logger.info("Client with ID {} for user ID {} has been fetched.", clientid, userid);

 String from=clientdata.getEmail();;

 Optional<User_RegistrationsForm> user = taskoRepository.findById(userid);
 User_RegistrationsForm userdata =user.get();
 Invest_Now investdetail= new Invest_Now();
 investdetail.setClientid(clientid);
 investdetail.setUserid(userid);
 investdetail.setSubject(subject);
 investdetail.setDetail(body);
 investdetail.setDate(new Date());
 investdetail.setCategory(category);
 investdetail.setName(clientdata.getName());
 investdetail.setMobile(mobile);
 if(clientdata.getInvest_now_email()!=null)
 {
	 investdetail.setInvestNow_Email(clientdata.getInvest_now_email());
 }
 else
 {
	 investdetail.setInvestNow_Email(null);
 }
 invest_NowRepository.save(investdetail);

 List<String> recipientEmails = new ArrayList<>();
 recipientEmails.add(userdata.getEmail());
if(clientdata.getInvest_now_email()!=null)
{
  recipientEmails.add(clientdata.getInvest_now_email());
}
for (String to : recipientEmails) {
    taskoService.sendEmailwithattachment(to, subject, body);
}

return "Email sent successfully.";
}
//////////////////////////////////send help email for client//////////////////////////////////////////////////
@PostMapping("/sendemailclient/help")
public String sendEmailhelp(
@RequestParam Long clientid,
@RequestParam  Long userid,
@RequestParam String subject,		
@RequestBody String body,
@RequestParam String category
) 
{

 Optional<Client_Registation_Form> client = clientRepository.findById(clientid);

 Client_Registation_Form clientdata =client.get();

 logger.info("Client with ID {} for user ID {} has been fetched.", clientid, userid);

 String from=clientdata.getEmail();

 Optional<User_RegistrationsForm> user = taskoRepository.findById(userid);

 Help investdetail= new Help();
 investdetail.setClientid(clientid);
 investdetail.setUserid(userid);
 investdetail.setQuery(subject);
 investdetail.setDetail(body);
 investdetail.setDate(new Date());
 investdetail.setName(clientdata.getName());
 helpRepository.save(investdetail);
 User_RegistrationsForm userdata =user.get();
 List<String> recipientEmails = new ArrayList<>();
 recipientEmails.add(userdata.getEmail());
 String to =userdata.getEmail();
 taskoService.sendEmailwithattachmenthelp(to, subject, body);
  return "Email sent successfully.";
}


///////////////////////////////////////////////////////////////////////change password for client///////////////////////////////

@PutMapping("/changePasswordclient/{pan}")
public ResponseEntity<String> changePassword(@PathVariable String pan, @RequestBody ChangePassword request) {
logger.info("Received a request to change password for user with ID: {}", pan);

try {
if (taskoService.isOldPasswordCorrect1(pan, request.getOldPassword())) {
taskoService.updatePassword1(pan, request.getNewPassword());
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

//------------------------------get client status file is filed or not file-----------------------------

	@GetMapping("/getclientfilednotfiled/{clientid}/{accountyear}")
	public ResponseEntity<Map<String, String>> getData(
	@PathVariable Long clientid,
	@PathVariable String accountyear)
{
logger.info("Received a request to get filed/not filed data for user ID {}, client ID {}, and account year {}",clientid, accountyear);

try {
	List<Filed_NotFiled> dataEntities = filed_notFiledRepo.findFilednotfiledByClientidAndAccountyear(clientid, accountyear);

  Map<String, String> response = new HashMap<>();

  if (!dataEntities.isEmpty()) {
      Filed_NotFiled entity = dataEntities.get(0);
      response.put("filednotfiled", entity.getFilednotfiled());
  } else {
      // Handle the case when dataEntities is empty, e.g., return a default value or throw an exception.
      // You can customize this part based on your requirements.
      // For now, we'll set a default value as an example.
      response.put("filednotfiled", "Default Value");
  }

	logger.info("Filed/not filed data fetched successfully for user ID {}, client ID {}, and account year {}",
			 clientid, accountyear);
	return ResponseEntity.ok(response);
} catch (Exception e) {
	logger.error("An error occurred while fetching filed/not filed data: {}", e.getMessage());
	throw e;
}
}
	
	
	
	
//////////////////////////////////////send client otp/////////////////////////////////////////////
	
@PostMapping("/client/send-otp")
public ResponseEntity<String> sendOTPByEmail(@RequestParam String pan) throws UserNotFoundException
{
logger.info("Received a request to send OTP by email for PAN: {}", pan);

try {
taskoService.sendOTPByEmail1(pan);
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
///////////////////////////////////////cleint otp verify///////////////////////////////
@PostMapping("/client/verify-otp")
public ResponseEntity<String> verifyOTP( @RequestParam String otp) throws OtpNotVaild
{
logger.info("Received a request to verify OTP");

try {
boolean isValidOTP = taskoService.verifyOTP1(otp);

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
//////////////////////////////////////reset client password/////////////////////////////////////////////

@PostMapping("/client/reset-password")
public ResponseEntity<String> resetPassword(@RequestParam String otp,@RequestParam String newPassword) throws OtpNotVaild
{
logger.info("Received a request to reset password");

try {
taskoService.resetPassword1(otp, newPassword);
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

/////////////////////////////////////////////////////////////kyc details  api/////////////////////////////////////////////////



////////////////////////////////////get document information api////////////////////////////////
@GetMapping("/getclientKycinformation/{pan}")
public ResponseEntity<?> getclientKyctinformation(@PathVariable String pan) {
  logger.info("Fetching payment details for userid: {}", pan);

 Optional<Kyc_client_detail> paymentDetailsOptional = kycclientRepository.findByPan(pan);
 Kyc_client_detail clientDetails = paymentDetailsOptional.get();
if (paymentDetailsOptional.isPresent()) {
   return ResponseEntity.ok(clientDetails);

 }
 else
 {
   return ResponseEntity.notFound().build();
 }

}



//----------------------------------------------upload adhar-----------------------------------------------------------//
@PutMapping("/client/Kycadharimage")
public ResponseEntity<Object> uploadadhar(
        @RequestParam String pan,
        @RequestParam("image") MultipartFile image) {
    try {
        // Check if the client with the given PAN exists
        Optional<Kyc_client_detail> existingPaymentDetailsOptional = kycclientRepository.findByPan(pan);

        if (!existingPaymentDetailsOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Kyc_client_detail existingPaymentDetails = existingPaymentDetailsOptional.get();

        if (image != null && !image.isEmpty()) {
            String pannum = existingPaymentDetails.getPan();
//            String imageName = generateUniqueNameadhar(pannum);
//
//            String name = image.getOriginalFilename();
//            String[] result = name.split("\\.");
//            String fileExtension = result[result.length - 1];
//
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(image.getContentType());
//            InputStream inputStream = image.getInputStream();
            String name = image.getOriginalFilename();
    		String[] result = name.split("\\.");
    		String fileExtension = result[result.length - 1];
    		String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
    		System.out.println("File Name without Extension: " + fileNameWithoutExtension);
    		ObjectMetadata metadata = new ObjectMetadata();
    		metadata.setContentType(image.getContentType());
    		InputStream inputStream = image.getInputStream();
    		String filename = generateUniqueFilenadhar(pan,fileNameWithoutExtension);
            String s3Key = filename + "." + fileExtension;

            // Check if the object with the same key exists in the S3 bucket
            if (amazonS3.doesObjectExist(bucketName, s3Key)) {
                // If it exists, delete it
                amazonS3.deleteObject(bucketName, s3Key);
            }

            // Upload the new object
            amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

            existingPaymentDetails.setImageName(name);
            existingPaymentDetails.setImagePath("s3://" + bucketName + "/" + s3Key);
            existingPaymentDetails.setLastUpdateDate1(new Date());
        } 
        // Save the updated payment details
        Kyc_client_detail updatedPaymentDetails = kycclientRepository.save(existingPaymentDetails);

        return ResponseEntity.ok(updatedPaymentDetails);
    } catch (Exception e) {
        // Handle exceptions here, log them, and return an error response
        e.printStackTrace(); // Log the exception for debugging
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
        		+ "ing image");
    }
}

private String generateUniqueFilenadhar(String pan,String fileNameWithoutExtension) {
	return pan + "_" + "adahr" + "_" + fileNameWithoutExtension;
}

///////////////////////////////////////////////////////////////////////upload pan image////////////////////////////////////////////////////////////////
@PutMapping("/client/Kycpanimage")
public ResponseEntity<Object> uploadpan(
        @RequestParam String pan,
        @RequestParam("image") MultipartFile image) {
    try {
        // Check if the client with the given PAN exists
        Optional<Kyc_client_detail> existingPaymentDetailsOptional = kycclientRepository.findByPan(pan);

        if (!existingPaymentDetailsOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Kyc_client_detail existingPaymentDetails = existingPaymentDetailsOptional.get();

        if (image != null && !image.isEmpty()) {
            String pannum = existingPaymentDetails.getPan();
            String name = image.getOriginalFilename();
    		String[] result = name.split("\\.");
    		String fileExtension = result[result.length - 1];
    		String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
    		System.out.println("File Name without Extension: " + fileNameWithoutExtension);
    		ObjectMetadata metadata = new ObjectMetadata();
    		metadata.setContentType(image.getContentType());
    		InputStream inputStream = image.getInputStream();
    		String filename = generateUniqueFilenpan(pan,fileNameWithoutExtension);
            String s3Key = filename + "." + fileExtension;

            // Check if the object with the same key exists in the S3 bucket
            if (amazonS3.doesObjectExist(bucketName, s3Key)) {
                // If it exists, delete it
                amazonS3.deleteObject(bucketName, s3Key);
            }

            // Upload the new object
            amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

            existingPaymentDetails.setImageName2(name);
            existingPaymentDetails.setImagePath2("s3://" + bucketName + "/" + s3Key);
            existingPaymentDetails.setLastUpdateDate2(new Date());
        } 
        // Save the updated payment details
        Kyc_client_detail updatedPaymentDetails = kycclientRepository.save(existingPaymentDetails);

        return ResponseEntity.ok(updatedPaymentDetails);
    } catch (Exception e) {
        // Handle exceptions here, log them, and return an error response
        e.printStackTrace(); // Log the exception for debugging
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
        		+ "ing image");
    }
}

private String generateUniqueFilenpan(String pan,String fileNameWithoutExtension) {
	return pan + "_" + "pan" + "_" + fileNameWithoutExtension;
}
////////////////////////////////////////////////////upload check detail/////////////////////////////////////////////////////////////////////////////
@PutMapping("/client/Kycchequeimage")
public ResponseEntity<Object> uploadcheck(
        @RequestParam String pan,
        @RequestParam("image") MultipartFile image) {
    try {
        // Check if the client with the given PAN exists
        Optional<Kyc_client_detail> existingPaymentDetailsOptional = kycclientRepository.findByPan(pan);

        if (!existingPaymentDetailsOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Kyc_client_detail existingPaymentDetails = existingPaymentDetailsOptional.get();

        if (image != null && !image.isEmpty()) {
            String pannum = existingPaymentDetails.getPan();
            String name = image.getOriginalFilename();
    		String[] result = name.split("\\.");
    		String fileExtension = result[result.length - 1];
    		String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
    		System.out.println("File Name without Extension: " + fileNameWithoutExtension);
    		ObjectMetadata metadata = new ObjectMetadata();
    		metadata.setContentType(image.getContentType());
    		InputStream inputStream = image.getInputStream();
    		String filename = generateUniqueFilencheck(pan,fileNameWithoutExtension);
            String s3Key = filename + "." + fileExtension;

            // Check if the object with the same key exists in the S3 bucket
            if (amazonS3.doesObjectExist(bucketName, s3Key)) {
                // If it exists, delete it
                amazonS3.deleteObject(bucketName, s3Key);
            }

            // Upload the new object
            amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

            existingPaymentDetails.setImageName3(name);
            existingPaymentDetails.setImagePath3("s3://" + bucketName + "/" + s3Key);
            existingPaymentDetails.setLastUpdateDate3(new Date());
        } 
        // Save the updated payment details
        Kyc_client_detail updatedPaymentDetails = kycclientRepository.save(existingPaymentDetails);

        return ResponseEntity.ok(updatedPaymentDetails);
    } catch (Exception e) {
        // Handle exceptions here, log them, and return an error response
        e.printStackTrace(); // Log the exception for debugging
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
        		+ "ing image");
    }
}

private String generateUniqueFilencheck(String pan,String fileNameWithoutExtension) {
	return pan + "_" + "check" + "_" + fileNameWithoutExtension;
}



/////////////////////////////////////////////////////get images////////////////////////////////////////////////////
@GetMapping("/getclientkycadhar/{pan}")
public ResponseEntity<?> getclientiamgekyc(@PathVariable String pan) {

    Optional<Kyc_client_detail> paymentDetailsOptional = kycclientRepository.findByPan(pan);
	
    if (paymentDetailsOptional.isPresent()) {
        Kyc_client_detail clientDetails = paymentDetailsOptional.get();
        String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath());
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
            headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePath()).build());

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




//////////////////////////////////////////////////////////////////get pan image//////////////////////////////////////////////////////////////////////
@GetMapping("/getclientkycapan/{pan}")
public ResponseEntity<?> getclientiamgekycpan(@PathVariable String pan) {
    logger.info("Fetching payment details for userid: {}", pan);

    Optional<Kyc_client_detail> paymentDetailsOptional = kycclientRepository.findByPan(pan);
	
    if (paymentDetailsOptional.isPresent()) {
        Kyc_client_detail clientDetails = paymentDetailsOptional.get();
        String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath2());
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
            headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePath2()).build());

            // Create a response map with both paymentDetails and content
         // Create a response map with both paymentDetails and content
            Map<String, Object> responseMap = new HashMap<>();
          //  responseMap.put("paymentDetails", clientDetails);
            responseMap.put("content",content);
            responseMap.put("content",resource);
            
            return ResponseEntity.ok()
            		.headers(headers)
            		.body(resource);

            //logger.info("Payment details found for userid: {}", pan);

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


////////////////////////////////////////////////////////////////////////kyc image///////////////////////////////////////////////////////////////////////
@GetMapping("/getclientkyccheck/{pan}")
public ResponseEntity<?> getclientiamgekyccheck(@PathVariable String pan) {
    logger.info("Fetching payment details for userid: {}", pan);

    Optional<Kyc_client_detail> paymentDetailsOptional = kycclientRepository.findByPan(pan);
	
    if (paymentDetailsOptional.isPresent()) {
        Kyc_client_detail clientDetails = paymentDetailsOptional.get();
        String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath3());
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
            headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePath3()).build());

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



//////////////////////////////////////////////////////////
@PutMapping("/client/deleteKycadharrimage")
public ResponseEntity<String> uploadcheck1(@RequestParam String pan) {
    try {
        // Check if the client with the given PAN exists
        Optional<Kyc_client_detail> existingPaymentDetailsOptional = kycclientRepository.findByPan(pan);

        if (!existingPaymentDetailsOptional.isPresent()) {
            // Handle the case where the record with the given PAN doesn't exist
            return ResponseEntity.badRequest().body("Client not found for PAN: " + pan);
        }

        Kyc_client_detail existingPaymentDetails = existingPaymentDetailsOptional.get();
        String s3Key = getS3KeyFromFilePath(existingPaymentDetails.getImagePath());

        // Check if the object with the same key exists in the S3 bucket
        if (amazonS3.doesObjectExist(bucketName, s3Key)) {
            // If it exists, delete it
            amazonS3.deleteObject(bucketName, s3Key);
        }

        // Modify the specific column you want to delete
        existingPaymentDetails.setImageName(null); // Set it to null, assuming you want to "delete" it.
        existingPaymentDetails.setImagePath(null);
        existingPaymentDetails.setLastUpdateDate1(null);
        // Save the updated entity back to the database
        kycclientRepository.save(existingPaymentDetails);

        return ResponseEntity.ok("Column deleted successfully.");
    } catch (Exception e) {
        // Handle exceptions here
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update: " + e.getMessage());
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
@PutMapping("/client/deleteKycpankrimage")
public ResponseEntity<String> uploadadhardelete(@RequestParam String pan) {
    try {
        // Check if the client with the given PAN exists
        Optional<Kyc_client_detail> existingPaymentDetailsOptional = kycclientRepository.findByPan(pan);

        if (!existingPaymentDetailsOptional.isPresent()) {
            // Handle the case where the record with the given PAN doesn't exist
            return ResponseEntity.badRequest().body("Client not found for PAN: " + pan);
        }

        Kyc_client_detail existingPaymentDetails = existingPaymentDetailsOptional.get();
        String s3Key = getS3KeyFromFilePath(existingPaymentDetails.getImagePath2());

        // Check if the object with the same key exists in the S3 bucket
        if (amazonS3.doesObjectExist(bucketName, s3Key)) {
            // If it exists, delete it
            amazonS3.deleteObject(bucketName, s3Key);
        }

        // Modify the specific column you want to delete
        existingPaymentDetails.setImageName2(null); // Set it to null, assuming you want to "delete" it.
        existingPaymentDetails.setImagePath2(null); // Set it to null, assuming you want to "delete" it.
        existingPaymentDetails.setLastUpdateDate2(null);
        // Save the updated entity back to the database
        kycclientRepository.save(existingPaymentDetails);

        return ResponseEntity.ok("Column deleted successfully.");
    } catch (Exception e) {
        // Handle exceptions here
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update: " + e.getMessage());
    }
}
///////////////////////////////////////////////////////delete check image

@PutMapping("/client/deleteKyccheckrimage")
public ResponseEntity<String> uploadpandelete(@RequestParam String pan) {
    try {
        // Check if the client with the given PAN exists
        Optional<Kyc_client_detail> existingPaymentDetailsOptional = kycclientRepository.findByPan(pan);

        if (!existingPaymentDetailsOptional.isPresent()) {
            // Handle the case where the record with the given PAN doesn't exist
            return ResponseEntity.badRequest().body("Client not found for PAN: " + pan);
        }

        Kyc_client_detail existingPaymentDetails = existingPaymentDetailsOptional.get();
        String s3Key = getS3KeyFromFilePath(existingPaymentDetails.getImagePath3());

        // Check if the object with the same key exists in the S3 bucket
        if (amazonS3.doesObjectExist(bucketName, s3Key)) {
            // If it exists, delete it
            amazonS3.deleteObject(bucketName, s3Key);
        }

        // Modify the specific column you want to delete
        existingPaymentDetails.setImageName3(null); // Set it to null, assuming you want to "delete" it.

        // Save the updated entity back to the database
        existingPaymentDetails.setImageName3(null); // Set it to null, assuming you want to "delete" it.
        existingPaymentDetails.setImagePath3(null);
        existingPaymentDetails.setLastUpdateDate3(null);
        kycclientRepository.save(existingPaymentDetails);

        return ResponseEntity.ok("Column deleted successfully.");
    } catch (Exception e) {
        // Handle exceptions here
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update: " + e.getMessage());
    }
}


/////////////////////////////document upload//////////////////////////////////
@PutMapping("/client/uploaddocument")
public ResponseEntity<Object> uploaddoc(
        @RequestParam String pan,
        @RequestParam("image") MultipartFile image) {
    try {
        // Check if the client with the given PAN exists
        Optional<docinfo> existingPaymentDetailsOptional = docinfoRepository.findByPan(pan);

        if (!existingPaymentDetailsOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        docinfo existingPaymentDetails = existingPaymentDetailsOptional.get();

        if (image != null && !image.isEmpty()) {
            String pannum = existingPaymentDetails.getPan();
            String imageName = generateUniquedoc(pannum);

            String name = image.getOriginalFilename();
            String[] result = name.split("\\.");
            String fileExtension = result[result.length - 1];

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(image.getContentType());
            InputStream inputStream = image.getInputStream();
            String s3Key = imageName + "." + fileExtension;

            // Check if the object with the same key exists in the S3 bucket
            if (amazonS3.doesObjectExist(bucketName, s3Key)) {
                // If it exists, delete it
                amazonS3.deleteObject(bucketName, s3Key);
            }

            // Upload the new object
            amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

            existingPaymentDetails.setImageName(name);
            existingPaymentDetails.setImagePath("s3://" + bucketName + "/" + s3Key);
            existingPaymentDetails.setLastUpdateDate1(new Date());
        } 
        // Save the updated payment details
        docinfo updatedPaymentDetails = docinfoRepository.save(existingPaymentDetails);

        return ResponseEntity.ok(updatedPaymentDetails);
    } catch (Exception e) {
        // Handle exceptions here, log them, and return an error response
        e.printStackTrace(); // Log the exception for debugging
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
        		+ "ing image");
    }
}

private String generateUniquedoc(String pannum )
{
	return pannum  +"_" +"document";
}

////////////////////////////////////get document information api////////////////////////////////
@GetMapping("/getclientdocumentinformation/{pan}")
public ResponseEntity<?> getclientdocumnetinformation(@PathVariable String pan) {
    logger.info("Fetching payment details for userid: {}", pan);

    Optional<docinfo> paymentDetailsOptional = docinfoRepository.findByPan(pan);
    docinfo clientDetails = paymentDetailsOptional.get();
    if (paymentDetailsOptional.isPresent()) {
    	return ResponseEntity.ok(clientDetails);
    	
    }
    else
    {
        return ResponseEntity.notFound().build();
    }
    }

    

///////////////////////////////////get documnet api--------------------------------------------------------//
@GetMapping("/getclientdocument/{pan}")
public ResponseEntity<?> getclientdocumnet(@PathVariable String pan) {
    logger.info("Fetching payment details for userid: {}", pan);

    Optional<docinfo> paymentDetailsOptional = docinfoRepository.findByPan(pan);
	
    if (paymentDetailsOptional.isPresent()) {
        docinfo clientDetails = paymentDetailsOptional.get();
        String s3Key = getS3KeyFromFilePath(clientDetails.getImagePath());

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
            headers.setContentDisposition(ContentDisposition.attachment().filename(clientDetails.getImagePath()).build());

            // Create a response map with both paymentDetails and content
            Map<String, Object> responseMap = new HashMap<>();
          //  responseMap.put("paymentDetails", clientDetails);
            responseMap.put("content",content);
            responseMap.put("content",resource);
            
            return ResponseEntity.ok()
            		.headers(headers)
            		.body(resource);
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


///////////////////////////////////////////delete image////////////////////////////////////////////////////////////////////////////////
@PutMapping("/client/deletedocumnet")
public ResponseEntity<String> uploadpandeletedocumnet(@RequestParam String pan) {
    try {
        // Check if the client with the given PAN exists
        Optional<docinfo> existingPaymentDetailsOptional = docinfoRepository.findByPan(pan);

        if (!existingPaymentDetailsOptional.isPresent()) {
            // Handle the case where the record with the given PAN doesn't exist
            return ResponseEntity.badRequest().body("Client not found for PAN: " + pan);
        }

        docinfo existingPaymentDetails = existingPaymentDetailsOptional.get();
        String s3Key = getS3KeyFromFilePath(existingPaymentDetails.getImagePath());

        // Check if the object with the same key exists in the S3 bucket
        if (amazonS3.doesObjectExist(bucketName, s3Key)) {
            // If it exists, delete it
            amazonS3.deleteObject(bucketName, s3Key);
        }

        // Modify the specific column you want to delete
        existingPaymentDetails.setImageName(null); // Set it to null, assuming you want to "delete" it.

        // Save the updated entity back to the database
        existingPaymentDetails.setImageName(null); // Set it to null, assuming you want to "delete" it.
        existingPaymentDetails.setImagePath(null);
        existingPaymentDetails.setLastUpdateDate1(null);
        docinfoRepository.save(existingPaymentDetails);

        return ResponseEntity.ok("Column deleted successfully.");
    } catch (Exception e) {
        // Handle exceptions here
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update: " + e.getMessage());
    }
}




////////////////////////////////////////////get api on help table for client/////////////////////////////////////////////////
@GetMapping("/help/allrecords")
	public ResponseEntity<List<Help>> getAllHelp(@RequestParam("userId") Long userId) {
        try {
            List<Help> helpList = helpRepository.findAllByUserid(userId);
            return new ResponseEntity<>(helpList, HttpStatus.OK);
        } catch (Exception ex) {
            // You can customize the exception handling here
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
}

////////////////////////////////get api for sing record view in helip table for user by id/////////////////////////////////
@GetMapping("/help/record/byid")
public ResponseEntity<Help> getHelpById(@RequestParam("id") Long id) {
    try {
        Optional<Help> help = helpRepository.findById(id);
        if (help.isPresent()) {
            return new ResponseEntity<>(help.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception ex) {
        // You can customize the exception handling here
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


/////////////////////////delete particular record in help table by single id ////////////////////////////////////////////////
@DeleteMapping("/help/deleterecord/byid")
public ResponseEntity<String> deleteHelpById(@RequestParam("id") Long id) {
    try {
        Optional<Help> help = helpRepository.findById(id);
        if (help.isPresent()) {
            helpRepository.deleteById(id);
            return ResponseEntity.ok("Record deleted successfully");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception ex) {
        // You can customize the exception handling here
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


/////////////////////////get all invest now table information by userid///////////////////////////////////////////////
@GetMapping("/Invest/allrecords")
public ResponseEntity<List<Invest_Now>> getAllinvest(@RequestParam("userId") Long userId,@RequestParam("category") String category) {
    try {
        List<Invest_Now> investlist = invest_NowRepository.findAllByUseridAndCategory(userId,category);
        return new ResponseEntity<>(investlist, HttpStatus.OK);
    } catch (Exception ex) {
        // You can customize the exception handling here
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}



//////////////////////////////get date for invest now email category///////////////////////
@GetMapping("/investnow/findDate")
public ResponseEntity<Timestamp> findDateByClientIdAndCategory(
        @RequestParam("clientId") Long clientId,
        @RequestParam("category") String category) {

    Timestamp date = invest_NowRepository.findDateByClientIdAndCategory(clientId, category);

    if (date != null) {
        return ResponseEntity.ok(date);
    } else {
        return ResponseEntity.notFound().build();
    }
}

/////////////////////////////////upload Tally backup file for client/////////////////////////////////////
@PostMapping("/client/upload/tallybackupfile")
public ResponseEntity<Object> clientuploadtallybackupfile(
@RequestParam String pan,
@RequestParam(value = "imagePathBackupfile", required = false) MultipartFile imagePathBackupfile) {
try {
	Client_tally_backupfile data=new Client_tally_backupfile();
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
String filename = generateUniqueFiletally(pan,fileNameWithoutExtension);
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
client_tally_backupfileRepository.save(data);

return ResponseEntity.ok("Upload Sucessfully!");
} catch (Exception e) {
// Handle exceptions here, log them, and return an error response
e.printStackTrace(); // Log the exception for debugging
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
+ "ing image");
}
}
private String generateUniqueFiletally(String pan,String fileNameWithoutExtension) {
return pan +"Client"+ "_" +"tally"+ "_" +"backup" + "_" + fileNameWithoutExtension;
}


//////////////////////////update Tally backup file for client ////////////////////////////////////

@PutMapping("/client/update/tallybackupfile")
public ResponseEntity<Object> cleitnupdatetallybackupfile(
@RequestParam String pan,
@RequestParam(value = "imagePathBackupfile", required = false) MultipartFile imagePathBackupfile) {
try {
Optional<Client_tally_backupfile> tallydata= client_tally_backupfileRepository.findByPan(pan);
Client_tally_backupfile data=tallydata.get();
if (!imagePathBackupfile.isEmpty()) { 	  
String name = imagePathBackupfile.getOriginalFilename();
String[] result = name.split("\\.");
String fileExtension = result[result.length - 1];
String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
System.out.println("File Name without Extension: " + fileNameWithoutExtension);
ObjectMetadata metadata = new ObjectMetadata();
metadata.setContentType(imagePathBackupfile.getContentType());
InputStream inputStream = imagePathBackupfile.getInputStream();
String filename = generateUniqueFiletally(data.getPan(),fileNameWithoutExtension);
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
client_tally_backupfileRepository.save(data);

return ResponseEntity.ok("Upload Sucessfully!");
} catch (Exception e) {
// Handle exceptions here, log them, and return an error response
e.printStackTrace(); // Log the exception for debugging
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "
+ "ing image");
}
}

//////////////////////////////////////////////////////////delete user tally backupfile//////////////////////////////////////////
@PutMapping("/client/delete/clientbackupfile")
public ResponseEntity<String> deleteclienttally(@RequestParam String pan) {
try {
//Check if the client with the given PAN exists
	Optional<Client_tally_backupfile> tallydata= client_tally_backupfileRepository.findByPan(pan);
if (!tallydata.isPresent()) {
return ResponseEntity.notFound().build();
}
Client_tally_backupfile existingPaymentDetails=tallydata.get();
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
client_tally_backupfileRepository.save(existingPaymentDetails);

return ResponseEntity.ok("deleted successfully.");
} catch (Exception e) {
//Handle exceptions here
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update: " + e.getMessage());
}
}


/////////////////////////////////////////////////////get backupfile client////////////////////////////////////////////////////
@GetMapping("/getclientbackupfile/{pan}")
public ResponseEntity<?> getclienttallybackup(@PathVariable String pan) {

Optional<Client_tally_backupfile> tallydata= client_tally_backupfileRepository.findByPan(pan);

if (tallydata.isPresent()) {
Client_tally_backupfile clientDetails = tallydata.get();
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
logger.warn(" details not found for userid: {}", pan);   
return ResponseEntity.notFound().build();
}
}
/////////////////////////////////get all userbackupfile detail information/////////////////////////////////////////////
@GetMapping("/getcleintbackupfiledetail/{pan}")
public ResponseEntity<Client_tally_backupfile> getDistributorDetail(@PathVariable String pan) {
Optional<Client_tally_backupfile> tallydata= client_tally_backupfileRepository.findByPan(pan);
if (tallydata.isPresent()) {
Client_tally_backupfile distributorDetails = tallydata.get();
return ResponseEntity.ok().body(distributorDetails);
} else {
return ResponseEntity.ok().body(null);
}
}
}