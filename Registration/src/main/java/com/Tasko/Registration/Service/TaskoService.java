package com.Tasko.Registration.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.Tasko.Registration.Entity.*;
//import com.Tasko.Registration.Entity.FileData;
import com.Tasko.Registration.dto.ClientCountsDTO;
import com.Tasko.Registration.dto.filed_NotfiledDTO;
import com.Tasko.Registration.error.*;
import com.razorpay.RazorpayException;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

public interface TaskoService
{
	public User_RegistrationsForm saveUser(User_RegistrationsForm user) throws Exception;
	public List<User_RegistrationsForm> FetchUser();
	public void deleteUserById(Long regId);
//	public Client_Registation_Form saveclient(Client_Registation_Form client) throws UserAlreadyExist, EmailMandatoryException;
	public List<Client_Registation_Form> getClientByUserid(Long userid);


	public Client_Registation_Form updateClient(Client_Registation_Form client, Long clientId);
	public List<Client_Registation_Form> getcategory( Long userid);
	public User_RegistrationsForm updateUser(User_RegistrationsForm user, Long regId);

    public Client_Registation_Form getClientByClientidUserid(Long userid, Long clientId) throws UserNotFoundException;

	Client_Registation_Form getClientByClientidUseridAndcategory(String category,Long userid, Long clientId) throws UserNotFoundException;



    ClientCountsDTO getClientCountsByUserId(Long userid);



	Resource getFile(Long id, Long userid, Long clientid, String accountyear)throws IOException;
	boolean isOldPasswordCorrect(long regId, String oldPassword);
	void updatePassword(long regId, String newPassword);

    void sendOTPByEmail(String pan) throws UserNotFoundException;

	void resetPassword(String otp,String newPassword) throws OtpNotVaild;

	boolean verifyOTP(String otp) throws OtpNotVaild;

	FileEntity uploadFile(Long userid, Long clientid, String accountyear, MultipartFile file,String fileName) throws MultipartException, FileAlreadyExists, IOException;
	List<FileEntity> getFilesByUserAndClientAndYear(Long userid, Long clientid, String accountyear);


	Filed_NotFiled updateFiledNotFiled(Long userid, Long clientid, String accountyear);





	public List<FileEntity> getfileByclient(Long clientid, String accountyear);

	List<filed_NotfiledDTO> getFileCountsByUser(Long userid);


	void setpassword(String pan, String newPassword);
	public GST_FileUpload GSTFileUpload(MultipartFile file, Long userid, Long clientid, String category, String month,
			String financialYear) throws IOException;
	
	public Payment_Details savepaymentDetails(Long userid, MultipartFile image, MultipartFile qrCode, String bank_name,
			String accountName, Long accountNumber, String ifsc, String upiId, String upiNumber) throws IOException, UserAlreadyExist;

	boolean isOldPasswordCorrect1(String pan, String oldPassword);
	void updatePassword1(String pan, String newPassword);
	public void sendEmailwithattachment(String to, String subject, String body);
	public void sendEmailinvite(String to, String subject, String body);
	
	public void sendByEmail(long id, List<Long> sid) throws UserNotFoundException;
	public Client_Registation_Form getClientByPancategory(String pan, String category) throws UserNotFoundException;
	public Client_Registation_Form getClientByClientidcategory(Long clientId, String category) throws UserNotFoundException;
	public Filed_NotFiled_GST GSTupdateFiledNotFiled(Long userid, Long clientid, String month, String financialYear, String category);

	public Client_Payment_Details saveClient_Payment_Details(Client_Payment_Details pay);

	public Map<String, Object> getPaymentSumsByUserid(Long userid);
	public Map<String, List<Map<String, Object>>> getDataByCategory(Long userid);

	public ResponseEntity<Client_Registation_Form> saveclient(Client_Registation_Form client) throws UserAlreadyExist, EmailMandatoryException;

	public ClientPass_Imgdetail savedetail(String pan,String email);

	void sendEmailwithattachmentUserhelp(String to, String subject, String body);
	
	void sendEmailwithattachmentContact(String to, String subject, String body);
	void sendEmailwithattachmentClientprofessional(String to, String subject, String body);
	
	void sendEmailwithattachmentforcestop(String to, String subject, String body);

	void sendOTPByEmail1(String pan) throws UserNotFoundException;

	boolean verifyOTP1(String otp) throws OtpNotVaild;

	void resetPassword1(String otp, String newPassword) throws OtpNotVaild;



	void sendEmailwithattachmenthelp(String to, String subject, String body);
	public void sendEmailWithAttachment11(String recipient, String subject, String text, MultipartFile attachmentContent,
			String attachmentFileName);


    String sendWhatsAppMessage(String to, String message);
	boolean isOldPasswordCorrect2(String pan, String oldPassword);
	void updatePassword2(String pan, String newPassword);
	public String createOrder(double amount, String currency) throws RazorpayException;

    void sendOTPByEmailToSubUser(String pan) throws UserNotFoundException;

	void SubUserResetPassword(String otp, String newPassword)throws OtpNotVaild;

	List<filed_NotfiledDTO> getFileCountsByUseridAndsubUserid(Long userid, Long subUserid)throws UserNotFoundException;

	Map<String, List<Map<String, Object>>> getGSTDataByCategoryAndSubUSerid(Long userid, Long subUserid);

    Map<String, Object> checkPanStatus(String pan)throws UserNotFoundException;


	boolean changePassword(String username, String oldPassword, String newPassword);
	
	boolean isOldPasswordCorrect3(String username, String oldPassword);

	void updatePassword3(String username, String newPassword);
}
