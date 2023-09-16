package com.Tasko.Registration.Service;

import java.io.InputStream;
import java.util.*;
import com.Tasko.Registration.Entity.FileEntity;
import com.Tasko.Registration.Entity.Filed_NotFiled;
import com.Tasko.Registration.Repository.*;
import com.Tasko.Registration.dto.ClientCountsDTO;
import com.Tasko.Registration.dto.filed_NotfiledDTO;
import com.Tasko.Registration.error.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.Tasko.Registration.Entity.Client_Registation_Form;
import com.Tasko.Registration.Entity.User_RegistrationsForm;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

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
	private Filed_NotFiledRepo filed_notFiledRepo;



	@Override
	public User_RegistrationsForm saveUser(User_RegistrationsForm user) throws UserAlreadyExist, EmailMandatoryException {

		Optional<User_RegistrationsForm> existingUserWithEmail = taskoRepository.findByEmail(user.getEmail());

		if(taskoRepository.findByPan(user.getPan()).isPresent())
		{
			throw new UserAlreadyExist("PAN Already Exist");
		}
		if (existingUserWithEmail.isPresent())
		{
			throw new EmailMandatoryException("Email Already Exist");
		}
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





	@Override
	public Client_Registation_Form saveclient(Client_Registation_Form client) throws UserAlreadyExist, EmailMandatoryException
	{
		Optional<Client_Registation_Form> existingEmail = clientRepository.findByEmail(client.getEmail());
		
		if(clientRepository.findByPan(client.getPan()).isPresent())
		{
			throw new UserAlreadyExist("PAN Already Exist");
		}
		if (existingEmail.isPresent())
		{
			throw new EmailMandatoryException("Email Already Exist");
		}
		return clientRepository.save(client);
	}

	@Override
	public Client_Registation_Form updateClient(Client_Registation_Form client,Long clientId)
	{
		Client_Registation_Form c=clientRepository.findById(clientId).get();

		if(Objects.nonNull(client.getName()) &&
		!"".equalsIgnoreCase(client.getName()))
		{
			c.setName(client.getName());
		}

		if(Objects.nonNull(client.getProfession()) &&
				!"".equalsIgnoreCase(client.getProfession()))
		{
			c.setProfession(client.getProfession());
		}

		if(Objects.nonNull(client.getPan()) &&
				!"".equalsIgnoreCase(client.getPan()))
		{
			c.setPan(client.getPan());
		}

		if(Objects.nonNull(client.getEmail()) &&
				!"".equalsIgnoreCase(client.getEmail()))
		{
			c.setEmail(client.getEmail());
		}

		if(Objects.nonNull(client.getMobile()) &&
				!"".equalsIgnoreCase(client.getMobile()))
		{
			c.setMobile(client.getMobile());
		}

		if(Objects.nonNull(client.getDob()))
		{
			c.setDob(client.getDob());
		}

		if(Objects.nonNull(client.getTelephone()) &&
				!"".equalsIgnoreCase(client.getTelephone()))
		{
			c.setTelephone(client.getTelephone());
		}

		if(Objects.nonNull(client.getAddress()) &&
				!"".equalsIgnoreCase(client.getAddress()))
		{
			c.setAddress(client.getAddress());
		}

		if(Objects.nonNull(client.getPin_code()) &&
				!"".equalsIgnoreCase(client.getPin_code()))
		{
			c.setPin_code(client.getPin_code());
		}

		if(Objects.nonNull(client.getState()) &&
				!"".equalsIgnoreCase(client.getState()))
		{
			c.setState(client.getState());
		}

		if(Objects.nonNull(client.getCategory()) &&
				!"".equalsIgnoreCase(client.getCategory()))
		{
			c.setCategory(client.getCategory());
		}

		if(Objects.nonNull(client.getResidential_status()) &&
				!"".equalsIgnoreCase(client.getResidential_status()))
		{
			c.setResidential_status(client.getResidential_status());
		}

		if(Objects.nonNull(client.getUserid()))
		{
			c.setUserid(client.getUserid());
		}
		
		return clientRepository.save(c);
	}

	@Override
	public List<Client_Registation_Form> getcategory(String category, Long userid)
	{
		return clientRepository.findByCategoryAndUserid(category,userid);
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

		if(Objects.nonNull(user.getInvestNow_Email()) &&
				!"".equalsIgnoreCase(user.getInvestNow_Email()))
		{
			u.setInvestNow_Email(user.getInvestNow_Email());
		}

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
		countsDTO.setIncomeTaxClientCount(clientRepository.countByCategoryAndUserid("Income_Tax",userid));

		return countsDTO;
	}


	//------------------------------File---------------------------

//	private String uploadDirectory = "/home/ubuntu/upoladedFiles/"; //Set your path
//	//private String uploadDirectory = "G:\\File_Upload\\";
//	@Override
//	public FileEntity uploadFile(Long userid, Long clientid, String accountyear, MultipartFile file,String fileName) throws MultipartException,FileAlreadyExists, IOException {
//		String filename = generateUniqueFilename(userid, clientid, accountyear, fileName);
//		Optional<FileEntity> filehandler=fileRepository.findFirstByUseridAndClientidAndAccountyearAndFileName(userid,clientid,accountyear,filename);
//
//		String name=file.getOriginalFilename().toString();
//		String result[]=name.split("\\.");
//		Path filePath = Paths.get(uploadDirectory + filename +"."+result[1]);
//		if (filehandler.isPresent())
//		{
//			throw new FileAlreadyExists("File Already Exists!");
//		}
//		Files.copy(file.getInputStream(), filePath);
//		System.out.println("File uploaded and saved as: " + filePath);
//		FileEntity fileInfo = new FileEntity();
//		fileInfo.setUserid(userid);
//		fileInfo.setClientid(clientid);
//		fileInfo.setFileName(filename);
//		fileInfo.setFilePath(String.valueOf(filePath));
//		fileInfo.setAccountyear(accountyear);
//		return fileRepository.save(fileInfo);
//
//	}

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

		return fileRepository.save(fileInfo);
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


//	public Resource getFile(Long id, Long userid, Long clientid,String accountyear) throws IOException
//	{
//		Optional<FileEntity> fileInfoOptional = Optional.ofNullable(fileRepository.findByIdAndUseridAndClientidAndAccountyear(id, userid, clientid, accountyear));
//		if (fileInfoOptional.isPresent()) {
//			FileEntity fileInfo = fileInfoOptional.get();
//			Path filePath = Paths.get(uploadDirectory, fileInfo.getFileName());
//			Resource resource = new UrlResource(filePath.toUri());
//
//			if (resource.exists()) {
//				return resource;
//			} else {
//				throw new RuntimeException("File not found.");
//			}
//		} else {
//			throw new RuntimeException("File information not found.");
//		}
//	}

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
	public List<FileEntity> getfileByclient(Long clientid, String accountyear) {
		// TODO Auto-generated method stub
		return fileRepository.findByClientidAndAccountyear(clientid, accountyear);
	}

	@Override
	public List<FileEntity> getFilesByUserAndClientAndYear(Long userid, Long clientid, String accountyear)
	{
		return fileRepository.findByUseridAndClientidAndAccountyear(userid, clientid, accountyear);
	}



	//---------------------------------Filed_Not Update---------------------------------------------

//	@Override
//	public Filed_NotFiled updateFiledNotFiled(Long userid, Long clientid, String accountyear)
//	{
//		Filed_NotFiled record = filed_notFiledRepo.findByUseridAndClientidAndAccountyear(userid, clientid,accountyear);
//		if (record != null)
//		{
//			record.setFilednotfiled("yes");
//			return filed_notFiledRepo.save(record);
//		}
//		return record;
//	}

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


	@Override
	public List<filed_NotfiledDTO> getFileCountsByUser(Long userid) {
		List<Object[]> results = filed_notFiledRepo.countFiledNotFiledByAccountyear(userid);
		List<filed_NotfiledDTO> fileCountDTOs = new ArrayList<>();

		for (Object[] result : results) {
			String accountYear = (String) result[0];
			Long filedCount = (Long) result[1];
			Long notFiledCount = (Long) result[2];

			filed_NotfiledDTO fileCountDTO = new filed_NotfiledDTO();
			fileCountDTO.setAccountyear(accountYear);
			fileCountDTO.setFiled(filedCount);
			fileCountDTO.setNotfiled(notFiledCount);

			fileCountDTOs.add(fileCountDTO);
		}

		return fileCountDTOs;
	}

	//------------Delete File-----------
//	@Override
//	public void deleteMultipleFiles(List<Long> id) throws IOException
//	{
//		for (Long fileId : id) {
//			Optional<FileEntity> fileEntityOptional = fileRepository.findById(fileId);
//			fileEntityOptional.ifPresent(fileEntity -> {
//				String filePath = fileEntity.getFilePath();
//				Path fullPath = Paths.get(filePath);
//				try {
//					Files.delete(fullPath);
//				} catch (IOException e) {
//					e.printStackTrace();
//
//				}
//			});
//			fileRepository.deleteById(fileId);
//		}
//	}


//	public void deleteMultipleFiles(List<Long> ids) throws IOException {
//		for (Long fileId : ids) {
//			Optional<FileEntity> fileEntityOptional = fileRepository.findById(fileId);
//			fileEntityOptional.ifPresent(fileEntity -> {
//				String s3Key = fileEntity.getFileName(); // Assuming the S3 object key is the same as the filename
//				try {
//					// Delete the file from S3
//					amazonS3.deleteObject(new DeleteObjectRequest(bucketName, s3Key));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			});
//			fileRepository.deleteById(fileId);
//		}
//	}



	//--------------------------------------Set Client Password--------------------------------------------------------

@Override
public void setpassword(String pan, String newPassword) {
	Optional<Client_Registation_Form> optionalForm = clientRepository.findByPan(pan);

	if (optionalForm.isPresent()) {
		Client_Registation_Form form = optionalForm.get();
		form.setPassword(passwordEncoder.encode(newPassword)); // Encode the new password
		clientRepository.save(form);
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




}







