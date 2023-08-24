package com.Tasko.Registration.Service;


import java.util.*;


import com.Tasko.Registration.Entity.FileEntity;
import com.Tasko.Registration.Entity.Filed_NotFiled;
import com.Tasko.Registration.Repository.*;
import com.Tasko.Registration.dto.ClientCountsDTO;
import com.Tasko.Registration.dto.filed_NotfiledDTO;
import com.Tasko.Registration.error.FileAlreadyExists;
import com.Tasko.Registration.error.OtpNotVaild;
import com.Tasko.Registration.error.UserAlreadyExist;
import com.Tasko.Registration.error.UserNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Tasko.Registration.Entity.Client_Registation_Form;
import com.Tasko.Registration.Entity.User_RegistrationsForm;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.web.servlet.function.ServerResponse.status;


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
	public User_RegistrationsForm saveUser(User_RegistrationsForm user) throws UserAlreadyExist
	{

		if(taskoRepository.findByPan(user.getPan()).isPresent())
		{
			throw new UserAlreadyExist("User Already Exist");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		System.out.println("User Registrations Is Done");
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
	public Client_Registation_Form saveclient(Client_Registation_Form client) throws UserAlreadyExist
	{
		if(clientRepository.findByPan(client.getPan()).isPresent())
		{
			throw new UserAlreadyExist("User Already Exist");
		}
		System.out.println("Client Registrations Is Done");
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

	//private String uploadDirectory = "/home/ubuntu/upoladedFiles/"; //Set your path
	private String uploadDirectory = "G:\\File_Upload\\";
	@Override
	public FileEntity uploadFile(Long userid, Long clientid, String accountyear, MultipartFile file,String fileName) throws MultipartException,FileAlreadyExists, IOException {
		String filename = generateUniqueFilename(userid, clientid, accountyear, fileName);
		Optional<FileEntity> filehandler=fileRepository.findFirstByUseridAndClientidAndAccountyearAndFileName(userid,clientid,accountyear,filename);

		String name=file.getOriginalFilename().toString();
		String result[]=name.split("\\.");
		Path filePath = Paths.get(uploadDirectory + filename +"."+result[1]);
		if (filehandler.isPresent())
		{
			throw new FileAlreadyExists("File Already Exists!");
		}
		Files.copy(file.getInputStream(), filePath);
		System.out.println("File uploaded and saved as: " + filePath);
		FileEntity fileInfo = new FileEntity();
		fileInfo.setUserid(userid);
		fileInfo.setClientid(clientid);
		fileInfo.setFileName(filename);
		fileInfo.setFilePath(String.valueOf(filePath));
		fileInfo.setAccountyear(accountyear);
		return fileRepository.save(fileInfo);

	}

	private String generateUniqueFilename(long userId, long clientId, String accountYear, String filename) {
		return userId + "_" + clientId + "_" + accountYear + "_" + filename;
	}


	public Resource getFile(Long id, Long userid, Long clientid,String accountyear) throws IOException
	{
		Optional<FileEntity> fileInfoOptional = Optional.ofNullable(fileRepository.findByIdAndUseridAndClientidAndAccountyear(id, userid, clientid, accountyear));
		if (fileInfoOptional.isPresent()) {
			FileEntity fileInfo = fileInfoOptional.get();
			Path filePath = Paths.get(uploadDirectory, fileInfo.getFileName());
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				return resource;
			} else {
				throw new RuntimeException("File not found.");
			}
		} else {
			throw new RuntimeException("File information not found.");
		}
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
		Filed_NotFiled record = filed_notFiledRepo.findByUseridAndClientidAndAccountyear(userid, clientid,accountyear);
		if (record != null)
		{
			record.setFilednotfiled("yes");
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
	@Override
	public void deleteMultipleFiles(List<Long> id) throws IOException
	{
		for (Long fileId : id) {
			Optional<FileEntity> fileEntityOptional = fileRepository.findById(fileId);
			fileEntityOptional.ifPresent(fileEntity -> {
				String filePath = fileEntity.getFilePath();
				Path fullPath = Paths.get(filePath);
				try {
					Files.delete(fullPath);
				} catch (IOException e) {
					e.printStackTrace();

				}
			});
			fileRepository.deleteById(fileId);
		}
	}
	//--------------------------------------Set Client Password--------------------------------------------------------
	@Override
	public void setpassword(String pan,String password)
	{
		Optional<Client_Registation_Form> optionalForm = clientRepository.findByPan(pan);
		if (optionalForm.isPresent()) {
			Client_Registation_Form form = optionalForm.get();
			form.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
			clientRepository.save(form);
		} else {
			// Handle PAN not found case
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







