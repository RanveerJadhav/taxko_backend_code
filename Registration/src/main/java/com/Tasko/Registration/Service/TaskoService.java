package com.Tasko.Registration.Service;

import java.io.IOException;
import java.util.List;

import com.Tasko.Registration.Entity.Client_Registation_Form;
//import com.Tasko.Registration.Entity.FileData;
import com.Tasko.Registration.Entity.FileEntity;
import com.Tasko.Registration.Entity.Filed_NotFiled;
import com.Tasko.Registration.Entity.User_RegistrationsForm;
import com.Tasko.Registration.dto.ClientCountsDTO;
import com.Tasko.Registration.dto.filed_NotfiledDTO;
import com.Tasko.Registration.error.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

public interface TaskoService
{
	public User_RegistrationsForm saveUser(User_RegistrationsForm user) throws Exception;
	public List<User_RegistrationsForm> FetchUser();
	public void deleteUserById(Long regId);
	public Client_Registation_Form saveclient(Client_Registation_Form client) throws UserAlreadyExist, EmailMandatoryException;
	public List<Client_Registation_Form> getClientByUserid(Long userid);


	public Client_Registation_Form updateClient(Client_Registation_Form client, Long clientId);
	public List<Client_Registation_Form> getcategory(String category, Long userid);
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


}
