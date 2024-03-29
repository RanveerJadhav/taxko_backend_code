package com.Tasko.Registration.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.Tasko.Registration.Entity.*;
import com.Tasko.Registration.Repository.*;
import com.Tasko.Registration.dto.ClientCountsDTO;
import com.Tasko.Registration.dto.filed_NotfiledDTO;
import com.Tasko.Registration.error.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.twilio.Twilio;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;

import java.io.IOException;

@Service
public class ServiceImpl implements TaskoService
{
 

   @Autowired
   private TaskoRepository taskoRepository;
   
   @Autowired
	private PasswordEncoder passwordEncoder;

   
   @Autowired
   private ClientRepository clientRepository;

   @Autowired
   private FileRepository fileRepository;

	@Autowired
	private Client_Registation_Form_Temp_Repo tempRepo;

   @Autowired
	private Filed_NotFiledRepo filed_notFiledRepo;
   
   @Autowired
   private Filed_NotFiled_GST_repo filed_GST_repo;
   
   @Autowired
   private Client_Payment_Details_Repo client_Payment_Details_Repo;

	@Autowired
	private ClientPassRepository clientPassRepository;
	@Autowired
	private Subscription_packRepository subscriptionPackRepository;
	@Autowired
	private Subscritpion_userdataRepository subscritpion_userdataRepository;
	@Autowired
    private Salesman_RegisterRepository salesmanRegisterRepository;

	@Autowired
	private CA_SubUsersRepo caSubUsersRepo;

	@Autowired
	private  master_adminRepository master_adminRepository;
	@Override
	public User_RegistrationsForm saveUser(User_RegistrationsForm user) throws UserAlreadyExist, EmailMandatoryException {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return taskoRepository.save(user);
	}


@Override
public List<User_RegistrationsForm> FetchUser() {
	// TODO Auto-generated method stub
	return taskoRepository.findAll();
}

@Override
public void deleteUserById(Long regId) 
{
	taskoRepository.deleteById(regId);
}





	public ResponseEntity<Client_Registation_Form> saveclient(Client_Registation_Form client) throws UserAlreadyExist, EmailMandatoryException
	{
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(client.getEmail());
		mailMessage.setSubject("Welcome to our platform!");
		mailMessage.setText("Dear " + client.getName() + ",\n\n" +
				"I hope this email finds you well.\n\n" +
				"I'm pleased to inform you that your account has been successfully created on TAXKO, a cutting-edge cloud-based platform designed to simplify post-filing data management for our esteemed clients like you.\n\n" +
				"With TAXKO, you now have access to a range of powerful features to streamline your tax-related processes, ensuring efficiency, accuracy, and compliance every step of the way. From organizing and accessing your financial data to collaborating seamlessly with our team, TAXKO is here to make your tax journey smoother than ever before.\n\n" +
				"What TAXKO offers you:\n" +
				"Effortless Data Access: Access your financial data securely from anywhere, at any time.\n" +
				"Streamlined Collaboration: Collaborate with us seamlessly to ensure accuracy and efficiency in your tax filings.\n" +
				"Automated Reminders: Stay updated on important deadlines and tasks with automated reminders, ensuring timely compliance.\n\n" +
				"To get started, simply click on the link below to log in to your TAXKO account using the credentials provided: [Insert Login Link]\n\n" +
				"Should you have any questions or require assistance with navigating the platform, please don't hesitate to reach out to me directly. I'm here to support you every step of the way and ensure your experience with TAXKO is nothing short of exceptional.\n\n" +
				"Thank you for entrusting us with your tax needs. We're excited to embark on this journey together and look forward to making your tax management experience as smooth and efficient as possible.\n\n" +
				"Kindly visit https://taxko.in for detailed understanding about product.\n\n" +
				"Best Regards,\n\n"+
				"Team TAXKO"
		);


		javaMailSender.send(mailMessage);
		
		List<Client_Registation_Form> existingpancount = clientRepository.findByPan1(client.getPan());
		int count = existingpancount.size();
		if(count>=2)
		{
			throw new UserAlreadyExist(client.getCategory() + "Already Exists");
		}
		Optional<Client_Registation_Form> existingpan = clientRepository.findByPan(client.getPan());
		Long totalemail = clientRepository.countOfTotalEmailClient(client.getEmail());
		totalemail = (totalemail != null) ? totalemail : 0L;
		if (totalemail>=2) {
	        throw new EmailMandatoryException("Your email is already registered with another Pan 2");
	    }
		Optional<Client_Registation_Form> existingEmail = clientRepository.findByEmail(client.getEmail());
		
		int Year = LocalDate.now().getYear();
		String yearString = Integer.toString(Year);

// Loop through the months and display them
		DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH);
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);

		if (existingpan.isPresent() || existingEmail.isPresent()) {
		    if (existingpan.isPresent() && !existingpan.get().getEmail().equals(client.getEmail())) {
		        throw new UserAlreadyExist("Your PAN is already registered with another email");
		    }
		    if (existingEmail.isPresent() && !existingEmail.get().getPan().equals(client.getPan())) {
		        throw new EmailMandatoryException("Your email is already registered with another Pan");
		    }
		    if (existingpan.isPresent() && existingpan.get().getCategory().equals(client.getCategory())) {
		        throw new UserAlreadyExist(client.getCategory() + " Already Exists");
		    }
		    if (existingpan.isPresent() && existingpan.get().getCategory().equals("Both")) {
		        throw new UserAlreadyExist(client.getCategory() + " Already Exists");
		    }
		    if (existingpan.isPresent() && existingpan.get().getCategory().equals("Both")) {
		        throw new UserAlreadyExist(client.getCategory() + " Already Exists");
		    }
		    if (existingpan.isPresent() && client.getCategory().equals("Both") && existingpan.get().getUserid() != client.getUserid()) {
		        throw new UserAlreadyExist(client.getCategory() + " Already Exists");
		    }

		    if (existingEmail.isPresent() && existingEmail.get().getCategory().equals(client.getCategory())) {
		        throw new EmailMandatoryException("You Already Exist");
		    }
		}
		
//Optional<Filed_NotFiled_GST> o=filed_GST_repo.findByUseridAndClientidAndFinancialYear(client.getUserid(),client.getClientId(),yearString);
		String c[]= {"GSTR-1","GSTR-3B"};
		if(clientRepository.findByPan(client.getPan()).isPresent()&&client.getCategory().equals("Both"))
		{
			System.out.println("present in both");
			Client_Registation_Form information =existingpan.get();
			if(!information.getCategory().equals("Income_Tax"))
			{
				for (int i = currentYear; i > currentYear - 5; i--)
				{
					Filed_NotFiled filedNotFiled = new Filed_NotFiled();

					filedNotFiled.setUserid(client.getUserid());
					filedNotFiled.setClientid(information.getClientId());

					filedNotFiled.setAccountyear(String.valueOf(i)+"-"+String.valueOf(i+1).substring(String.valueOf(i).length() - 2));
					//{currentYear - 1}-{currentYear.toString().slice(-2)}

					filedNotFiled.setFilednotfiled("no"); // You can set the initial value as needed
					filedNotFiled.setCategory("Income_Tax");
					//filedNotFiled.setLastUpdateDate(new Date()); // Set the current date
					filed_notFiledRepo.save(filedNotFiled);
				}
			}
			if(!information.getCategory().equals("GST"))
			{
				if(Objects.nonNull(client.getCategory()) &&
						!"".equalsIgnoreCase(client.getCategory()))
				{
					information.setCategory(client.getCategory());
					System.out.println("2");
					clientRepository.save(information);
				}
				for (String category : c)
				{
					for (Month month : Month.values())
					{
						LocalDate date = LocalDate.of(currentYear, month, 1);
						String monthName = date.format(monthFormatter);

						Filed_NotFiled_GST gst = new Filed_NotFiled_GST();

						gst.setUserid(client.getUserid());
						gst.setClientid(information.getClientId());
						gst.setCategory(category); // Set the category for each record
						gst.setFilednotfiled("no");
						gst.setMonth(monthName + " " + yearString);
						gst.setFinancialYear(yearString);
						filed_GST_repo.save(gst);
					}
				}

			}
			information.setCategory(client.getCategory());
			System.out.println("1");
//          clientRepository.save(information);
			clientRepository.updateUserNameByclientId(client.getClientId(),"Both");
		}


		if(clientRepository.findByPan(client.getPan()).isEmpty()&&client.getCategory().equals("Both"))
		{
			Client_Registation_Form savedClient = clientRepository.save(client);
			for (int i = currentYear; i > currentYear - 5; i--)
			{
				Filed_NotFiled filedNotFiled = new Filed_NotFiled();

				filedNotFiled.setUserid(client.getUserid());
				filedNotFiled.setClientid(savedClient.getClientId());

				filedNotFiled.setAccountyear(String.valueOf(i)+"-"+String.valueOf(i+1).substring(String.valueOf(i).length() - 2));
				//{currentYear - 1}-{currentYear.toString().slice(-2)}

				filedNotFiled.setFilednotfiled("no"); // You can set the initial value as needed
				filedNotFiled.setCategory("Income_Tax");
				//filedNotFiled.setLastUpdateDate(new Date()); // Set the current date
				filed_notFiledRepo.save(filedNotFiled);
			}

			for (String category : c)
			{
				for (Month month : Month.values())
				{
					LocalDate date = LocalDate.of(currentYear, month, 1);
					String monthName = date.format(monthFormatter);

					Filed_NotFiled_GST gst = new Filed_NotFiled_GST();

					gst.setUserid(client.getUserid());
					gst.setClientid(savedClient.getClientId());
					gst.setCategory(category); // Set the category for each record
					gst.setFilednotfiled("no");
					gst.setMonth(monthName + " " + yearString);
					gst.setFinancialYear(yearString);

					filed_GST_repo.save(gst);
				}
			}
		}
		if(client.getCategory().equals("Income_Tax"))
		{
			Client_Registation_Form savedClient = clientRepository.save(client);
			for (int i = currentYear; i > currentYear - 5; i--)
			{
				Filed_NotFiled filedNotFiled = new Filed_NotFiled();

				filedNotFiled.setUserid(client.getUserid());
				filedNotFiled.setClientid(savedClient.getClientId());

				filedNotFiled.setAccountyear(String.valueOf(i)+"-"+String.valueOf(i+1).substring(String.valueOf(i).length() - 2));
				//{currentYear - 1}-{currentYear.toString().slice(-2)}

				filedNotFiled.setFilednotfiled("no"); // You can set the initial value as needed
				filedNotFiled.setCategory(client.getCategory());
				//filedNotFiled.setLastUpdateDate(new Date()); // Set the current date
				filed_notFiledRepo.save(filedNotFiled);
			}
		}

		if(client.getCategory().equals("GST"))
		{
			Client_Registation_Form savedClient = clientRepository.save(client);
			for (String category : c)
			{
				for (Month month : Month.values())
				{
					LocalDate date = LocalDate.of(currentYear, month, 1);
					String monthName = date.format(monthFormatter);

					Filed_NotFiled_GST gst = new Filed_NotFiled_GST();

					gst.setUserid(client.getUserid());
					gst.setClientid(savedClient.getClientId());
					gst.setCategory(category); // Set the category for each record
					gst.setFilednotfiled("no");
					gst.setMonth(monthName + " " + yearString);
					gst.setFinancialYear(yearString);

					filed_GST_repo.save(gst);
				}

			}
		}
		return null;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Client_Registation_Form updateClient(Client_Registation_Form client, Long clientId)
	{
		Client_Registation_Form c = clientRepository.findById(clientId).orElse(null);

		if (Objects.nonNull(client.getName()) && !client.getName().trim().isEmpty()) {
			c.setName(client.getName());
		} else {
			c.setName(null);
		}

		if (Objects.nonNull(client.getProfession()) && !client.getProfession().trim().isEmpty()) {
			c.setProfession(client.getProfession());
		} else {
			c.setProfession(null);
		}


		if (Objects.nonNull(client.getEmail()) && !client.getEmail().trim().isEmpty()) {
			c.setEmail(client.getEmail());
		} else {
			c.setEmail(null);
		}

		//---------------------ClientPass_Imgdetail change email-------------------
		ClientPass_Imgdetail imgDetail = clientPassRepository.findByPan1(c.getPan());
		if (imgDetail != null)
		{
			imgDetail.setEmail(client.getEmail()); // Assuming you want to update email with pan
			clientPassRepository.save(imgDetail);
		}

		if (Objects.nonNull(client.getMobile()) && !client.getMobile().trim().isEmpty()) {
			c.setMobile(client.getMobile());
		} else {
			c.setMobile(null);
		}

		if (Objects.nonNull(client.getDob())) {
			c.setDob(client.getDob());
		} else {
			c.setDob(null);
		}

		if (Objects.nonNull(client.getTelephone()) && !client.getTelephone().trim().isEmpty()) {
			c.setTelephone(client.getTelephone());
		} else {
			c.setTelephone(null);
		}

		if (Objects.nonNull(client.getAddress()) && !client.getAddress().trim().isEmpty()) {
			c.setAddress(client.getAddress());
		} else {
			c.setAddress(null);
		}

		if (Objects.nonNull(client.getPin_code()) && !client.getPin_code().trim().isEmpty()) {
			c.setPin_code(client.getPin_code());
		} else {
			c.setPin_code(null);
		}

		if (Objects.nonNull(client.getState()) && !client.getState().trim().isEmpty()) {
			c.setState(client.getState());
		} else {
			c.setState(null);
		}

		if (Objects.nonNull(client.getResidential_status()) && !client.getResidential_status().trim().isEmpty()) {
			c.setResidential_status(client.getResidential_status());
		} else {
			c.setResidential_status(null);
		}

		if (Objects.nonNull(client.getInvest_now_email()) && !client.getInvest_now_email().trim().isEmpty()) {
			c.setInvest_now_email(client.getInvest_now_email());
		} else {
			c.setInvest_now_email(null);
		}

		return clientRepository.save(c);
	}


	@Override
	public List<Client_Registation_Form> getcategory( Long userid)
	{
		return clientRepository.findOfIncomeTaxByUserid(userid);
	}

	@Override
	public List<Client_Registation_Form> getClientByUserid(Long userid)
	{
		return clientRepository.findAllByUserid(userid);
	}



	@Override
	public User_RegistrationsForm updateUser(User_RegistrationsForm user, Long regId)
	{
		User_RegistrationsForm u=taskoRepository.findById(regId).get();
		
		Optional<Subscription_Userdata> y=subscritpion_userdataRepository.findByPan(u.getPan());
		if(Objects.nonNull(user.getName()) &&
				!"".equalsIgnoreCase(user.getName())||Objects.nonNull(user.getMobile()) &&
				!"".equalsIgnoreCase(user.getMobile()))
		{
			Subscription_Userdata z=y.get();
			z.setName(user.getName());
			z.setMobile(user.getMobile());
			subscritpion_userdataRepository.save(z);
		}

		if(Objects.nonNull(user.getName()) &&
				!"".equalsIgnoreCase(user.getName()))
		{
			u.setName(user.getName());
		}

		if(Objects.nonNull(user.getDatebirth()))
		{
			u.setDatebirth(user.getDatebirth());
		}

		if(Objects.nonNull(user.getMembership_No()))
		{
			u.setMembership_No(user.getMembership_No());
		}

		if(Objects.nonNull(user.getProfession()) &&
				!"".equalsIgnoreCase(user.getProfession()))
		{
			u.setProfession(user.getProfession());
		}

		if(Objects.nonNull(user.getPan()) &&
				!"".equalsIgnoreCase(user.getPan()))
		{
			u.setPan(user.getPan());
		}

		if(Objects.nonNull(user.getMobile()) &&
				!"".equalsIgnoreCase(user.getMobile()))
		{
			u.setMobile(user.getMobile());
		}

		if(Objects.nonNull(user.getEmail()) &&
				!"".equalsIgnoreCase(user.getEmail()))
		{
			u.setEmail(user.getEmail());
		}

		if(Objects.nonNull(user.getOffice_Address()) &&
				!"".equalsIgnoreCase(user.getOffice_Address()))
		{
			u.setOffice_Address(user.getOffice_Address());
		}

		if(Objects.nonNull(user.getPin_code()) &&
				!"".equalsIgnoreCase(user.getPin_code()))
		{
			u.setPin_code(user.getPin_code());
		}

		if(Objects.nonNull(user.getState()) &&
				!"".equalsIgnoreCase(user.getState()))
		{
			u.setState(user.getState());
		}

		if(Objects.nonNull(user.getWhatsApp_Link()) &&
				!"".equalsIgnoreCase(user.getWhatsApp_Link()))
		{
			u.setWhatsApp_Link(user.getWhatsApp_Link());
		}

//		if(Objects.nonNull(user.getInvestNow_Email()) &&
//				!"".equalsIgnoreCase(user.getInvestNow_Email()))
//		{
//			u.setInvestNow_Email(user.getInvestNow_Email());
//		}

		return taskoRepository.save(u);
	}

	@Override
	public Client_Registation_Form getClientByClientidUserid(Long userid, Long clientId) throws UserNotFoundException 
	{
		Client_Registation_Form c=clientRepository.findByUseridAndClientId(userid, clientId);
		if(c ==null)
		{
			throw new UserNotFoundException("User Not Found");
		}
		else
			return c;
	}

	@Override
	public Client_Registation_Form getClientByClientidUseridAndcategory(String category, Long userid, Long clientId) throws UserNotFoundException
	{
		Client_Registation_Form c=clientRepository.findByCategoryAndUseridAndClientId(category,userid,clientId);
		if(c ==null)
		{
			throw new UserNotFoundException("User Not Found");
		}
		else
			return c;
	}


	public ClientCountsDTO getClientCountsByUserId(Long userid)
	{
		ClientCountsDTO countsDTO = new ClientCountsDTO();

		countsDTO.setTotalClientCount(clientRepository.countByUserid(userid));
		countsDTO.setIncomeTaxClientCount(clientRepository.countOfTotalClientIncomeTaxByUserid(userid));
		countsDTO.setGst_ClientCount(clientRepository.countOfTotalClientGSTByUserid(userid));

		return countsDTO;
	}



	//---------------------------Save Payment Details-----------------------------------------
	@Autowired
	private paymentDetailsRepo detailsRepo;
	@Override
	public Payment_Details savepaymentDetails(Long userid, MultipartFile image, MultipartFile qrCode, String bank_name, String accountName, Long accountNumber, String ifsc,String upiId,String upiNumber) throws IOException, UserAlreadyExist {
		User_RegistrationsForm u=taskoRepository.findById(userid).get();
		String pan=   u.getPan();
		String imagename = generateUniqueName(userid,pan);
//		Optional<Payment_Details> u= detailsRepo.findByUserid(userid);
//		if(u.isPresent())
//		{
//			throw new UserAlreadyExist("user already exist");
//		}
		String name = image.getOriginalFilename();
		String[] result = name.split("\\.");
		String fileExtension = result[result.length - 1];

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(image.getContentType());
		InputStream inputStream = image.getInputStream();
		String s3Key = imagename + "." + fileExtension;
		amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

		Payment_Details details=new Payment_Details();
		details.setUserid(userid);
		details.setImageName(imagename);
		details.setImagePath("s3://" + bucketName + "/" + s3Key);
		details.setQrCode(qrCode.getBytes());
		details.setBank_name(bank_name);
		details.setAccountName(accountName);
		details.setAccountNumber(accountNumber);
		details.setIfsc(ifsc);
		details.setUpiId(upiId);
		details.setUpiNumber(upiNumber);
		

		return detailsRepo.save(details);
	}


//	private String generateUniqueName(Long userid,String accountName)
	private String generateUniqueName(Long userid,String pan)
	{
		return userid+"_"+pan;
	}


	private String generateUniqueFilename(long userId, long clientId, String accountYear, String filename) {
		return userId + "_" + clientId + "_" + accountYear + "_" + filename;
	}




	@SuppressWarnings("unused")
	private String getFileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf(".");
		if (lastDotIndex != -1) {
			return fileName.substring(lastDotIndex);
		}
		return ""; // If there's no file extension, return an empty string
	}



	public Resource getFile(Long id, Long userid, Long clientid, String accountyear) throws IOException {
		Optional<FileEntity> fileInfoOptional = Optional.ofNullable(fileRepository.findByIdAndUseridAndClientidAndAccountyear(id, userid, clientid, accountyear));

		if (fileInfoOptional.isPresent()) {
			FileEntity fileInfo = fileInfoOptional.get();
			String s3Key = fileInfo.getFileName(); // Assuming the S3 object key is the same as the filename

			// Fetch the file from S3
			S3Object s3Object = amazonS3.getObject(bucketName, s3Key);
			S3ObjectInputStream inputStream = s3Object.getObjectContent();

			// Create a Resource from the S3ObjectInputStream
			Resource resource = new InputStreamResource(inputStream);

			return resource;
		} else {
			throw new RuntimeException("File information not found.");
		}
	}

	//------------------------------------Get File By Client Id---------------------------------------------------
	@Override
	public List<FileEntity> getfileByclient(Long clientid, String accountyear) 
	{
		return fileRepository.findByClientidAndAccountyear(clientid, accountyear);
	}

	@Override
	public List<FileEntity> getFilesByUserAndClientAndYear(Long userid, Long clientid, String accountyear)
	{
		return fileRepository.findByUseridAndClientidAndAccountyear(userid, clientid, accountyear);
	}



	//---------------------------------Filed_Not Update---------------------------------------------

	@Override
	public Filed_NotFiled updateFiledNotFiled(Long userid, Long clientid, String accountyear)
	{
		Filed_NotFiled record = filed_notFiledRepo.findByUseridAndClientidAndAccountyear(userid, clientid, accountyear);
		if (record != null)
		{
			record.setFilednotfiled("yes");

			// Set the lastUpdateDate to the current system date and time
			record.setLastUpdateDate(new Date());

			return filed_notFiledRepo.save(record);
		}
		return record;
	}

	//-------------------------------------get Income_Tax filed_Notfiled-------------------
	@Transactional
	@Override
	public List<filed_NotfiledDTO> getFileCountsByUser(Long userid)
	{
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);

		String currentYearStr = currentYear + "-" + (currentYear + 1) % 100; // Improved year formatting

		// Check if the account year is already present for the user
		List<Long> clientIds = clientRepository.findClientIdByUserId(userid);

		for (Long clientId : clientIds) {
		    List<Filed_NotFiled> existingRecords = filed_notFiledRepo.findByClientid(clientId);
		    
		    // Check if the list is not empty
		    if (!existingRecords.isEmpty()) {
		        System.out.println(existingRecords);

		        Filed_NotFiled existingRecord = filed_notFiledRepo.findByClientidAndAccountyear(clientId, currentYearStr);

		        if (existingRecord == null) {
		            // If not present, add it
		            Filed_NotFiled newRecord = new Filed_NotFiled();
		            newRecord.setUserid(userid);
		            newRecord.setAccountyear(currentYearStr);
		            newRecord.setClientid(clientId);
		            newRecord.setFilednotfiled("no"); // You can set the initial value as needed
		            newRecord.setCategory("Income_Tax");
		            filed_notFiledRepo.save(newRecord);
		        }
		    }
		}
	    // Delete records for the last 5th year
	    int lastYearToDelete = currentYear - 5;
	    String yearToDeleteStr = String.valueOf(lastYearToDelete) + "-" + String.valueOf(lastYearToDelete + 1).substring(String.valueOf(lastYearToDelete).length() - 2);

	    List<Filed_NotFiled> recordsToDelete = filed_notFiledRepo.findByUseridAndAccountyear(userid, yearToDeleteStr);
	    for (Filed_NotFiled record : recordsToDelete)
	    {
	        filed_notFiledRepo.delete(record);
	    }

	    List<FileEntity> s3 = fileRepository.findByUseridAndAccountyear(userid, yearToDeleteStr);
	    for (FileEntity r : s3) 
	    {
	        String s3Key = r.getFilePath(); // Get the file path from FileEntity

	        // Find the last index of '/' character
	        int lastIndex = s3Key.lastIndexOf('/');

	        // Extract the filename from the input string
	        String newVariable = s3Key.substring(lastIndex + 1);

	        // Delete the file from S3
	        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, newVariable));
	    }

	    // Delete associated records from fileRepository
	    fileRepository.deleteByUseridAndAccountyear(userid, yearToDeleteStr);

	    // Now retrieve the counts for all account years
	    List<Object[]> results = filed_notFiledRepo.countFiledNotFiledByAccountyear(userid);
	    List<filed_NotfiledDTO> fileCountDTOs = new ArrayList();

	    for (Object[] result : results) {
	        String accountYear = (String) result[0];
	        Long filedCount = ((Number) result[1]).longValue();
	        Long notFiledCount = ((Number) result[2]).longValue();

	        // Assuming category is a property of Filed_NotFiled entity
	     
	            filed_NotfiledDTO fileCountDTO = new filed_NotfiledDTO();
	            fileCountDTO.setAccountyear(accountYear);
	            fileCountDTO.setFiled(filedCount);
	            fileCountDTO.setNotfiled(notFiledCount);

	            fileCountDTOs.add(fileCountDTO);
	        
	    }

	    return fileCountDTOs;
	}

	//--------------------------------------Set Client Password--------------------------------------------------------

@Override
public void setpassword(String pan, String newPassword) {
	Optional<ClientPass_Imgdetail> optionalForm = clientPassRepository.findByPan(pan);

	if (optionalForm.isPresent()) {
		ClientPass_Imgdetail form = optionalForm.get();
	//	form.setPassword(passwordEncoder.encode(newPassword)); // Encode the new password
		form.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
		clientPassRepository.save(form);
	} else {
		throw new EntityNotFoundException("Client with PAN " + pan + " not found.");
	}
}

	//---------------------Change Password---------------------
	public boolean isOldPasswordCorrect(long regId, String oldPassword)
	{
		User_RegistrationsForm user = taskoRepository.findByRegId(regId);
		return BCrypt.checkpw(oldPassword, user.getPassword());
	}

	// Method to update the password
	public void updatePassword(long regId, String newPassword)
	{
		User_RegistrationsForm user = taskoRepository.findByRegId(regId);
		user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
		taskoRepository.save(user);
	}
	//---------------------Forget Password---------------------
	@Autowired
	private ForgetRepo forgetRepo;

	public void sendOTPByEmail(String pan) throws UserNotFoundException
	{
		User_RegistrationsForm panotp = forgetRepo.findByPan(pan);
		if (panotp == null)
		{
			throw new UserNotFoundException("User Not Found");
		}

		String otp = generateOTP();
		panotp.setOtp(otp);
		forgetRepo.save(panotp);

		String emailContent = "Your OTP is: " + otp;
		sendEmail(panotp.getEmail(), "OTP Verification", emailContent);
	}

	private String generateOTP()
	{
		int otpLength = 6; // Set the desired length of the OTP
		String allowedChars = "0123456789"; // You can customize the characters to include alphabets or special characters if needed.
		Random random = new Random();

		StringBuilder otp = new StringBuilder(otpLength);
		for (int i = 0; i < otpLength; i++) {
			int randomIndex = random.nextInt(allowedChars.length());
			char otpChar = allowedChars.charAt(randomIndex);
			otp.append(otpChar);
		}

		return otp.toString();
	}

	@Autowired
	private JavaMailSender javaMailSender;
	private void sendEmail(String to, String subject, String body)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		javaMailSender.send(message);
	}
	
	
	
	
	public boolean verifyOTP(String otp) throws OtpNotVaild
	{
		User_RegistrationsForm user = forgetRepo.findByOtp(otp);
		if (user == null)
		{
			throw new OtpNotVaild("Invaild OTP!");
		}

		return otp.equals(user.getOtp());
	}

	public void resetPassword(String otp,String newPassword) throws OtpNotVaild
	{
		User_RegistrationsForm user = forgetRepo.findByOtp(otp);
		if (user == null)
		{
			throw new OtpNotVaild("Invaild OTP!");
		}

		// Update the password and clear the OTP
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setOtp(null);
		forgetRepo.save(user);
	}

	//------------------------------------------------File Uplaod-------------------------------------------------------------------------------------------

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${aws.s3.bucketName}")
	private String bucketName;
	public FileEntity uploadFile(Long userid, Long clientid, String accountyear, MultipartFile file, String fileName) throws FileAlreadyExists, IOException {
		String filename = generateUniqueFilename(userid, clientid, accountyear, fileName);

		Optional<FileEntity> fileHandler = fileRepository.findFirstByUseridAndClientidAndAccountyearAndFileName(userid, clientid, accountyear, filename);
		if (fileHandler.isPresent()) {
			throw new FileAlreadyExists("File Already Exists!");
		}

		String name = file.getOriginalFilename();
		String[] result = name.split("\\.");
		String fileExtension = result[result.length - 1];

		// Upload the file to S3
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		InputStream inputStream = file.getInputStream();
		String s3Key = filename + "." + fileExtension;
		amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

		System.out.println("File uploaded to S3: " + s3Key);

		FileEntity fileInfo = new FileEntity();
		fileInfo.setUserid(userid);
		fileInfo.setClientid(clientid);
		fileInfo.setFileName(filename);
		fileInfo.setFilePath("s3://" + bucketName + "/" + s3Key);
		fileInfo.setAccountyear(accountyear);
		fileInfo.setLastUpdateDate(new Date());
		return fileRepository.save(fileInfo);
	}
	//----------------------------------------------GST file upload----------------------------------------------------------
	@Autowired
	private GST_FileUploadRepo gstfilerepo;

	@Override
	public GST_FileUpload GSTFileUpload(MultipartFile file, Long userid, Long clientid, String category, String month,String financialYear) throws IOException
	{
//		String name = file.getOriginalFilename();

//		String[] result = name.split("\\.");
//		String fileExtension = result[result.length - 1];
		String name = file.getOriginalFilename();
		String[] result = name.split("\\.");
		String fileExtension = result[result.length - 1];
		String fileNameWithoutExtension = name.substring(0, name.length() - (fileExtension.length() + 1)); // Add 1 to account for the dot
		System.out.println("File Name without Extension: " + fileNameWithoutExtension);

		// Remove the original file extension from the name


		// Upload the file to S3
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		InputStream inputStream = file.getInputStream();
		String filename = generateUniqueFilen(userid, clientid, category,fileNameWithoutExtension, month);
		String s3Key = filename + "." + fileExtension;

		amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

		System.out.println("File uploaded to S3: " + s3Key);

		GST_FileUpload f = new GST_FileUpload();
		f.setUserid(userid);
		f.setClientid(clientid);
		f.setCategory(category);
		f.setMonth(month);
		f.setFinancialYear(financialYear);
		f.setFileName(filename);
		f.setFilePath("s3://" + bucketName + "/" + s3Key);
		f.setLastUpdateDate(new Date() );
		return gstfilerepo.save(f);

	}

	private String generateUniqueFilen(long userid, long clientid,String category,String fileNameWithoutExtension,String month) {
		return userid + "_" + clientid + "_" + category + "_" + fileNameWithoutExtension+ "_" + month;
	}


	///////////////////change password for client////////////////////////////////////////////
	//---------------------Change Password---------------------
	public boolean isOldPasswordCorrect1(String pan, String oldPassword)
	{   Optional<ClientPass_Imgdetail> user = clientPassRepository.findByPan(pan);
		ClientPass_Imgdetail user2 = user.get();
		return BCrypt.checkpw(oldPassword, user2.getPassword());
	}

	// Method to update the password
	public void updatePassword1(String pan, String newPassword)
	{
		Optional<ClientPass_Imgdetail> user = clientPassRepository.findByPan(pan);
		ClientPass_Imgdetail user2 = user.get();
		user2.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
		clientPassRepository.save(user2);
	}
	
	///////////////////////////////chage password for salesmanger//////////////////////
///////////////////change password for client////////////////////////////////////////////
//---------------------Change Password---------------------
public boolean isOldPasswordCorrect2(String pan, String oldPassword)
{   Optional<Salesman_Register> user = salesmanRegisterRepository.findByPan(pan);
Salesman_Register user2 = user.get();
return BCrypt.checkpw(oldPassword, user2.getPassword());
}

// Method to update the password
public void updatePassword2(String pan, String newPassword)
{
Optional<Salesman_Register> user = salesmanRegisterRepository.findByPan(pan);
Salesman_Register user2 = user.get();
user2.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
salesmanRegisterRepository.save(user2);
}
///////////////////////////////////////////////
	public void  sendEmailwithattachment(String to, String subject, String body)
	{

		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
//		        for (String attachmentPath : attachmentPaths) {
//		            FileSystemResource fileSystemResource = new FileSystemResource(new File(attachmentPath));
//		            helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
//		        }
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			// Handle the exception appropriately
		}
	}
		
		//-----------------------send email with attachment------------------
		@Override
		public void sendByEmail(long id, List<Long> sid) throws UserNotFoundException {
		    Optional<Client_Registation_Form> userOptional = clientRepository.findById(id);

		    if (!userOptional.isPresent()) {
		        throw new UserNotFoundException("User Not Found");
		    }

		    Client_Registation_Form user = userOptional.get();
		    String recipientEmail = user.getEmail();
		    
		    String emailContent = "Please find the attached files.";
		    
		    
		    
//		    for (Long fileId : sid) {
//		        Optional<FileEntity> fileEntityOptional = fileRepository.findById(fileId);
//		        
//		        if (fileEntityOptional.isPresent()) {
//		            FileEntity fileEntity = fileEntityOptional.get();
//		            Path filePath = Paths.get(fileEntity.getFilePath());
//		            attachmentPaths.add(filePath.toString()); // Add attachment path to the list
//		        }
//		    }
		    
		        sendEmailwithattachment(recipientEmail, "Attachment Email", emailContent);
		    
		}
		
		@Override
		public Client_Registation_Form getClientByPancategory(String pan, String category)
				throws UserNotFoundException {
			Client_Registation_Form c=clientRepository.findByPanAndCategory(pan,category);
			if(c ==null)
			{
				throw new UserNotFoundException("User Not Found");
			}
			else
				return c;
		}
		
		
		@Override
		public Client_Registation_Form getClientByClientidcategory(Long userid, String category)
				throws UserNotFoundException {
			Client_Registation_Form c=clientRepository.findByClientIdAndCategory(userid,category);
			if(c ==null)
			{
				throw new UserNotFoundException("User Not Found");
			}
			else
				return c;
		}



//-------------------------------------------------------------GST Filed_Notfiled Update----------------------------------------------------
		@Override
		public Filed_NotFiled_GST GSTupdateFiledNotFiled(Long userid, Long clientid, String month, String financialYear,String category) 
		{
			Filed_NotFiled_GST record = filed_GST_repo.findByUseridAndClientidAndMonthAndFinancialYearAndCategory(userid, clientid,month,financialYear,category);
			if (record != null)
			{
				record.setFilednotfiled("yes");

				// Set the lastUpdateDate to the current system date and time
				record.setLastUpdateDate(new Date());

				return filed_GST_repo.save(record);
			}
			return record;
			
		}



	public Map<String, List<Map<String, Object>>> getDataByCategory(Long userid)
	{
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int previousYear = currentYear - 1;
		String currentFinancialYearString = String.valueOf(currentYear);
		String previousFinancialYearString = String.valueOf(previousYear);

		List<Object[]> results = filed_GST_repo.getDataByCategoryAndFinancialYear(userid, currentFinancialYearString, previousFinancialYearString);

		Map<String, List<Map<String, Object>>> dataByCategory = new LinkedHashMap<>();

		for (Object[] result : results) {
			String category = (String) result[0];
			String monthYear = (String) result[1];
			Long filedCount = ((Number) result[2]).longValue();
			Long notFiledCount = ((Number) result[3]).longValue();

			Map<String, Object> entry = new HashMap<>();
			entry.put("category", category);
			entry.put("month", monthYear);
			entry.put("filed", filedCount);
			entry.put("notfiled", notFiledCount);

			// Check if the category already exists in the map; if not, create a new list
			dataByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(entry);
		}

		return dataByCategory;
	}




	//----------------------------------------------------------------------Client_Payment_Details Save-----------------------------------------
		@Override
		public Client_Payment_Details saveClient_Payment_Details(Client_Payment_Details pay) 
		{
			  Long totalPayment = pay.getTotalPayment();
		      Long receivedPayment = pay.getReceivedPayment();
		      pay.setPendingPayment(totalPayment - receivedPayment);
			
			return client_Payment_Details_Repo.save(pay);
		}



	/////////////////////////////////////////////Sum Of Client_Payment_Details////////////////////////////////////////////////
	public Map<String, Object> getPaymentSumsByUserid(Long userid)
	{
		List<Client_Payment_Details> payments = client_Payment_Details_Repo.findByUserid(userid);

		Long totalPaymentSum = payments.stream().mapToLong(Client_Payment_Details::getTotalPayment).sum();
		Long receivedPaymentSum = payments.stream().mapToLong(Client_Payment_Details::getReceivedPayment).sum();
		Long pendingPaymentSum = payments.stream().mapToLong(Client_Payment_Details::getPendingPayment).sum();
		Long discountPaymentSum=payments.stream().mapToLong(Client_Payment_Details::getDiscount).sum();

		Map<String, Object> sums = new HashMap<>();
		sums.put("totalPayment", totalPaymentSum);
		sums.put("receivedPayment", receivedPaymentSum);
		sums.put("pendingPayment", pendingPaymentSum);
		sums.put("discountPayment", discountPaymentSum);




		return sums;
	}







	/////////////////////////user help///////////////////////////////////////////////
	@Override
	public void sendEmailwithattachmentUserhelp(String to, String subject, String body) {
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// helper.setFrom("");
			helper.setTo("taxkohelp@gmail.com");
			helper.setSubject(subject);
			helper.setText(body);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			// Handle the exception appropriately
		}

	}
	@Override
	public ClientPass_Imgdetail savedetail(String pan, String email) {
		ClientPass_Imgdetail client = new ClientPass_Imgdetail();
		client.setPan(pan);
		client.setEmail(email);
		return clientPassRepository.save(client);
	}
	@Autowired
	private ClientForgotRepo clientForgotRepo;
	@Override
	public void sendOTPByEmail1(String pan) throws UserNotFoundException {
		ClientPass_Imgdetail panotp = clientForgotRepo.findByPan(pan);
		if (panotp == null)
		{
			throw new UserNotFoundException("User Not Found");
		}

		String otp = generateOTP();
		panotp.setOtp(otp);
		clientForgotRepo.save(panotp);

		String emailContent = "Your OTP is: " + otp;

		sendEmail(panotp.getEmail(), "OTP Verification", emailContent);

	}

	@Override
	public boolean verifyOTP1(String otp) throws OtpNotVaild {
		ClientPass_Imgdetail user = clientForgotRepo.findByOtp(otp);
		if (user == null)
		{
			throw new OtpNotVaild("Invaild OTP!");
		}

		return otp.equals(user.getOtp());
	}

	@Override
	public void resetPassword1(String otp, String newPassword) throws OtpNotVaild {
		ClientPass_Imgdetail user = clientForgotRepo.findByOtp(otp);
		if (user == null)
		{
			throw new OtpNotVaild("Invaild OTP!");
		}

		// Update the password and clear the OTP
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setOtp(null);
		clientForgotRepo.save(user);

	}

	@Override
	public void sendEmailwithattachmenthelp(String to, String subject, String body) {
		
			 MimeMessage message = javaMailSender.createMimeMessage();

			    try {
			        MimeMessageHelper helper = new MimeMessageHelper(message, true);
			       // helper.setFrom("");
			        helper.setTo(to);
			        helper.setSubject(subject);
			        helper.setText(body);
			        javaMailSender.send(message);
			    } catch (MessagingException e) {
			        e.printStackTrace();
			        // Handle the exception appropriately
			    }

	}


	@Override
	public void sendEmailWithAttachment11(String recipient, String subject, String text, MultipartFile attachmentContent, String attachmentFileName) {
		 MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        try {
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	            helper.setTo(recipient);
	            helper.setSubject(subject);
	            helper.setText(text, true); // Set the text as HTML if needed
                  System.out.println("hii");
	            // Attach the attachment content
	            helper.addAttachment(attachmentFileName, new ByteArrayDataSource(attachmentContent.getBytes(),"application/pdf"));
	            javaMailSender.send(mimeMessage);
	        } catch (Exception e) {
	            // Handle the exception, log it, or throw a custom exception
	            e.printStackTrace();
	        }
	}



	@Override
	public void sendEmailwithattachmentContact(String to, String subject, String body) {
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// helper.setFrom("");
			helper.setTo("txkspprt@gmail.com");
			helper.setSubject(subject);
			helper.setText(body);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			// Handle the exception appropriately
		}
	}

	@Override
	public void sendEmailwithattachmentforcestop(String to, String subject, String body) {
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// helper.setFrom("");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			// Handle the exception appropriately
		}
	}
//	@Override
//	public void sendEmailWithAttachment(String recipient, String subject, String text, ,
//			String attachmentFileName) {
//			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//			try {
//	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//	            helper.setTo(recipient);
//	            helper.setSubject(subject);
//	            helper.setText(text, true); // Set the text as HTML if needed
//
//	            // Attach the attachment content
//	            helper.addAttachment(attachmentFileName, new ByteArrayDataSource(attachmentContent, attachmentFileName));
//
//	            javaMailSender.send(mimeMessage);
//	        } catch (Exception e) {
//	            // Handle the exception, log it, or throw a custom exception
//	            e.printStackTrace();
//	        }
//			
//		}


	@Override
	public void sendEmailwithattachmentClientprofessional(String to, String subject, String body) {
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// helper.setFrom("");
			helper.setTo("inquirytaxko@gmail.com");
			helper.setSubject(subject);
			helper.setText(body);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			// Handle the exception appropriately
		}
		
	}


	@Value("${twilio.accountSid}")
	private String accountSid;

	@Value("${twilio.authToken}")
	private String authToken;

	@Value("${twilio.whatsapp.number}")
	private String twilioWhatsappNumber;

	@Override
	public String sendWhatsAppMessage(String to, String message) {
		Twilio.init(accountSid, authToken);

		// Use Twilio WhatsApp number for 'To' and 'From'
		Message whatsappMessage = Message.creator(
				new com.twilio.type.PhoneNumber("whatsapp:" + to),
				new com.twilio.type.PhoneNumber("whatsapp:" + twilioWhatsappNumber),
				message
		).create();

		System.out.println(whatsappMessage.getSid());
		return to;
	}

	
	
	
	//--------------------------------------------------Payment Gateway---------------------------------------------------------------------


		@Value("${razorpay.api.key.id}")
		private String keyId;

		@Value("${razorpay.api.key.secret}")
		private String keySecret;

		public String createOrder(double amount, String currency) throws RazorpayException {
			RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amount * 100); // Convert to paisa
			orderRequest.put("currency", currency);
			orderRequest.put("receipt", "order_rcptid_" + System.currentTimeMillis());
			// Add other order details as needed

			Order order = razorpayClient.orders.create(orderRequest);

			return order.get("id");
		}

	@Override
	public void sendOTPByEmailToSubUser(String pan) throws UserNotFoundException {
		CA_SubUsers panotp = caSubUsersRepo.findByPan1(pan);
		Optional<User_RegistrationsForm> userpan = taskoRepository.findByPan(pan);

		if (panotp != null) {
			String otp = generateOTP();
			panotp.setOtp(otp);
			caSubUsersRepo.save(panotp);

			String emailContent = "Your OTP is: " + otp;
			sendEmail(panotp.getEmail(), "OTP Verification", emailContent);
		}
		else if (userpan.isPresent())
		{
			String otp = generateOTP();
			User_RegistrationsForm userOtp = userpan.get();
			userOtp.setOtp(otp);
			taskoRepository.save(userOtp);

			String emailContent = "Your OTP is: " + otp;
			sendEmail(userOtp.getEmail(), "OTP Verification", emailContent);
		} else {
			throw new UserNotFoundException("User Not Found");
		}
	}

	@Override
	public void SubUserResetPassword(String otp, String newPassword) throws OtpNotVaild {
		CA_SubUsers user = caSubUsersRepo.findByOtp(otp);
		if (user == null)
		{
			throw new OtpNotVaild("Invaild OTP!");
		}

		// Update the password and clear the OTP
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setOtp(null);
		caSubUsersRepo.save(user);
	}

	@Override
	public List<filed_NotfiledDTO> getFileCountsByUseridAndsubUserid(Long userid, Long subUserid) throws UserNotFoundException {
		// Check if the account year is already present for the user
		List<Long> clientIds = clientRepository.findClientIdByUserIdAndSubUserid(userid, subUserid);

		if (clientIds.isEmpty()) {
			throw new UserNotFoundException("Not Found!");
		}

		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based

		// Adjust the year based on the start of the new fiscal year (April)
		int currentYear;
		if (currentMonth >= 4) {
			currentYear = calendar.get(Calendar.YEAR);
		} else {
			currentYear = calendar.get(Calendar.YEAR) - 1;
		}

		String currentYearStr = currentYear + "-" + (currentYear + 1) % 100; // Improved year formatting

		for (Long clientId : clientIds) {
			List<Filed_NotFiled> existingRecords = filed_notFiledRepo.findByClientid(clientId);

			// Check if the list is not empty
			if (!existingRecords.isEmpty()) {
				System.out.println(existingRecords);

				Filed_NotFiled existingRecord = filed_notFiledRepo.findByClientidAndAccountyear(clientId, currentYearStr);

				if (existingRecord == null) {
					// If not present, add it
					Filed_NotFiled newRecord = new Filed_NotFiled();
					newRecord.setUserid(userid);
					newRecord.setAccountyear(currentYearStr);
					newRecord.setClientid(clientId);
					newRecord.setFilednotfiled("no"); // You can set the initial value as needed
					newRecord.setCategory("Income_Tax");
					filed_notFiledRepo.save(newRecord);
				}
			}
		}
		// Delete records for the last 5th year
		int lastYearToDelete = currentYear - 5;
		String yearToDeleteStr = String.valueOf(lastYearToDelete) + "-" + String.valueOf(lastYearToDelete + 1).substring(String.valueOf(lastYearToDelete).length() - 2);

		List<Filed_NotFiled> recordsToDelete = filed_notFiledRepo.findByUseridAndAccountyear(userid, yearToDeleteStr);
		for (Filed_NotFiled record : recordsToDelete)
		{
			filed_notFiledRepo.delete(record);
		}

		List<FileEntity> s3 = fileRepository.findByUseridAndAccountyear(userid, yearToDeleteStr);
		for (FileEntity r : s3)
		{
			String s3Key = r.getFilePath(); // Get the file path from FileEntity

			// Find the last index of '/' character
			int lastIndex = s3Key.lastIndexOf('/');

			// Extract the filename from the input string
			String newVariable = s3Key.substring(lastIndex + 1);

			// Delete the file from S3
			amazonS3.deleteObject(new DeleteObjectRequest(bucketName, newVariable));
		}

		// Delete associated records from fileRepository
		fileRepository.deleteByUseridAndAccountyear(userid, yearToDeleteStr);

		// Now retrieve the counts for all account years
		List<Object[]> results = filed_notFiledRepo.countFiledNotFiledByAccountyear(userid);
		List<filed_NotfiledDTO> fileCountDTOs = new ArrayList();

		for (Object[] result : results) {
			String accountYear = (String) result[0];
			Long filedCount = ((Number) result[1]).longValue();
			Long notFiledCount = ((Number) result[2]).longValue();

			// Assuming category is a property of Filed_NotFiled entity

			filed_NotfiledDTO fileCountDTO = new filed_NotfiledDTO();
			fileCountDTO.setAccountyear(accountYear);
			fileCountDTO.setFiled(filedCount);
			fileCountDTO.setNotfiled(notFiledCount);

			fileCountDTOs.add(fileCountDTO);

		}

		return fileCountDTOs;
	}

	@Override
	public Map<String, List<Map<String, Object>>> getGSTDataByCategoryAndSubUSerid(Long userid, Long subUserid)
	{
		Calendar calendar = Calendar.getInstance();
		int financialYear = calendar.get(Calendar.YEAR);
		String financialYearString = String.valueOf(financialYear);

		List<Object[]> results = filed_GST_repo.getDataByCategoryAndSubUseridAndFinancialYear(userid,subUserid,financialYearString);

		Map<String, List<Map<String, Object>>> dataByCategory = new LinkedHashMap<>();

		for (Object[] result : results)
		{
			String category = (String) result[0];
			String monthYear = (String) result[1];
			Long filedCount = ((Number) result[2]).longValue();
			Long notFiledCount = ((Number) result[3]).longValue();

			Map<String, Object> entry = new HashMap<>();
			entry.put("category", category);
			entry.put("month", monthYear);
			entry.put("filed", filedCount);
			entry.put("notfiled", notFiledCount);

			// Check if the category already exists in the map; if not, create a new list
			dataByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(entry);
		}

		return dataByCategory;
	}

	@Override
	public Map<String, Object> checkPanStatus(String pan) throws UserNotFoundException {
		Map<String, Object> response = new HashMap<>();


		Optional<Client_Registation_Form_Temp> tempResult = tempRepo.findByPan(pan);
		Optional<Client_Registation_Form> formResult = clientRepository.findByPan(pan);

		if (tempResult.isPresent()) {
			response.put("Pan", tempResult.get().getPan());
			response.put("Name", tempResult.get().getName());
			response.put("Status", "True");
		} else if (formResult.isPresent()) {
			response.put("Pan", formResult.get().getPan());
			response.put("Name", formResult.get().getName());
			response.put("Status", "False");
		}
		else
		{
			throw new UserNotFoundException("Client Not Found");
		}

		return response;
	}

	@Override
	public boolean changePassword(String username, String oldPassword, String newPassword)
	{
		master_admin admin = master_adminRepository.findByUsername(username);

		if (admin != null && passwordEncoder.matches(oldPassword, admin.getPassword())) {
			admin.setPassword(passwordEncoder.encode(newPassword));
			master_adminRepository.save(admin);
			return true;
		}

		return false;
	}
	@Override
	public boolean isOldPasswordCorrect3(String username, String oldPassword)
	{
		Optional<master_admin> user = Optional.ofNullable(master_adminRepository.findByUsername(username));
		master_admin user2 = user.get();
		return BCrypt.checkpw(oldPassword, user2.getPassword());
	}

	@Override
	public void updatePassword3(String username, String newPassword)
	{
		Optional<master_admin> user = Optional.ofNullable(master_adminRepository.findByUsername(username));
		master_admin user2 = user.get();
		user2.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
		master_adminRepository.save(user2);
	}


	@Override
	public void sendEmailinvite(String to, String subject, String body)
	{
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// helper.setFrom("");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			// Handle the exception appropriately
		}
	}
}


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	



		









