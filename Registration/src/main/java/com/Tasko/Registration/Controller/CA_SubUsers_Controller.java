package com.Tasko.Registration.Controller;

import com.Tasko.Registration.Entity.*;

import com.Tasko.Registration.Repository.*;
import com.Tasko.Registration.Service.TaskoService;

import com.Tasko.Registration.dto.*;
import com.Tasko.Registration.error.EnterValidAmount;
import com.Tasko.Registration.error.OtpNotVaild;
import com.Tasko.Registration.error.UserAlreadyExist;
import com.Tasko.Registration.error.UserNotFoundException;
import jakarta.validation.constraints.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class CA_SubUsers_Controller {



    @Autowired
    private CA_SubUsersRepo caSubUsersRepo;

    @Autowired
    private TaskoService taskoService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TaskoRepository taskoRepository;

    @Autowired
    private Filed_NotFiledRepo filed_notFiledRepo;

    @Autowired
    private Client_Payment_Details_Repo client_Payment_Details_Repo;

    @Autowired
    private Filed_NotFiled_GST_repo gstfiled_notfiledRepo;

    @Autowired
    private Filed_NotFiledRepo filed_notFiledRep;





    Logger logger = LoggerFactory.getLogger(CA_SubUsers_Controller.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @PostMapping("/saveCA_Users")
    public ResponseEntity<List<CA_SubUsers>> saveCA_SubUsers(@RequestBody SaveCASubUsersRequest request)
    {

        List<CA_SubUsers> caSubUsersList = new ArrayList<>();

        // Define the DateTimeFormatter with the pattern matching the date format


        for (int i = 0; i < request.getTotalClients(); i++)
        {
            CA_SubUsers caSubUsers = new CA_SubUsers();
            caSubUsers.setUserid(request.getUserid());
            caSubUsers.setUserPan(request.getUserpan());
            caSubUsers.setStatus(true);

            LocalDateTime startDate = LocalDateTime.parse(request.getStartDate(), formatter);
            caSubUsers.setStartDate(startDate);

            // Parse the request.getEndDate() with the defined formatter
            LocalDateTime endDate = LocalDateTime.parse(request.getEndDate(), formatter);
            caSubUsers.setEndDate(endDate);

            caSubUsersList.add(caSubUsersRepo.save(caSubUsers));
        }

        return ResponseEntity.ok(caSubUsersList);
    }



    @GetMapping("/getSub_Users/{userid}")
    public List<CA_SubUsers> getSub_Users(@PathVariable Long userid)
    {
        List<CA_SubUsers> x = caSubUsersRepo.findByUserid(userid);
        return (x);
    }

    @PutMapping("/updateSub_Users/{id}")
    public ResponseEntity<List<CA_SubUsers>> updateub_Users(@PathVariable Long id, @RequestBody CA_SubUsers updatedClient) throws UserAlreadyExist
    {

        // Find all clients with the given ID
        List<CA_SubUsers> existingClients = caSubUsersRepo.findCA_SubUserById(id);

        Optional<CA_SubUsers> pan=caSubUsersRepo.findByPan(updatedClient.getPan());

        Optional<User_RegistrationsForm> CA=taskoRepository.findByPan(updatedClient.getPan());

        if (pan.isPresent() || CA.isPresent())
        {
            throw new UserAlreadyExist("Pan Already Exist!");
        }
        // Update the fields for each client (assuming there is only one client with the given ID)
        CA_SubUsers existingClient = existingClients.get(0);
        existingClient.setName(updatedClient.getName());
        existingClient.setPan(updatedClient.getPan());
        existingClient.setMobile(updatedClient.getMobile());
        existingClient.setEmail(updatedClient.getEmail());
        // Save the updated client
        caSubUsersRepo.save(existingClient);
        return new ResponseEntity<>(existingClients, HttpStatus.OK);
    }

//------------------------------------------is passwoed null--------------------------------------

    @PostMapping("/SubUsers/isPasswordNull/{pan}")
    public ResponseEntity<Map<String, Object>> SubUsercheckPassword1(@PathVariable String pan) throws UserNotFoundException {
        Optional<CA_SubUsers> check = caSubUsersRepo.findByPan(pan);

        Optional<User_RegistrationsForm> user=taskoRepository.findByPan(pan);




        if (check.isPresent()) {
            CA_SubUsers client = check.get();
            boolean isPasswordNull = client.getPassword() == null;

            logger.info("Password check for PAN {}: Is password null? {}", pan, isPasswordNull);

            Map<String, Object> response = new HashMap<>();
            response.put("isPasswordNull", isPasswordNull);
            response.put("Category","Sub User");

            CA_SubUsers sub_user = check.orElse(null);

            if (sub_user != null)
            {
                LocalDateTime currentDate = LocalDateTime.now();
                LocalDateTime endDate = sub_user.getEndDate();

                if (currentDate.isAfter(endDate) || currentDate.isEqual(endDate)) {
                    sub_user.setStatus(false);
                    caSubUsersRepo.save(sub_user);
                }
            }


            return ResponseEntity.ok(response);
        }
        if (user.isPresent())
        {
            User_RegistrationsForm u = user.get();
            boolean isPasswordNull = u.getPassword() == null;

            logger.info("Password check for PAN {}: Is password null? {}", pan, isPasswordNull);

            Map<String, Object> response = new HashMap<>();
            response.put("isPasswordNull", isPasswordNull);
            response.put("Category","User");


            return ResponseEntity.ok(response);
        }
        else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    //---------------------------------------------------Send Otp to SubUser--------------------------------------------------------
    @PostMapping("/send-otp_ToSubUser")
    public ResponseEntity<String> sendOTPByEmail(@RequestParam String pan) throws UserNotFoundException
    {
        logger.info("Received a request to send OTP by email for PAN: {}", pan);

        try {
            taskoService.sendOTPByEmailToSubUser(pan);
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

    //--------------------------------------------------verify OTP---------------------------------
    @PostMapping("/verify_SubUserOtp")
    public ResponseEntity<String> verifyOTPsubUser(@RequestParam String otp) {
        logger.info("Received a request to verify OTP");

        CA_SubUsers user = caSubUsersRepo.findByOtp(otp);
        User_RegistrationsForm ca = taskoRepository.findByOtp(otp);

        if (user != null)
        {
            logger.info("OTP verification successful!");
//            user.setOtp(null);
//            caSubUsersRepo.save(user);
            return ResponseEntity.ok("OTP verification successful!");
        }
        else if (ca != null)
        {
            logger.info("OTP verification successful!");
            ca.setOtp(null);
            taskoRepository.save(ca);
            return ResponseEntity.ok("OTP verification successful!");
        } else {
            logger.warn("Invalid OTP provided");
            return ResponseEntity.badRequest().body("Invalid OTP!");
        }
    }



    //----------------------------------------------Set Password---------------------------------------
    @PostMapping("/SubUser_SetPassword")
    public ResponseEntity<String> SubUserResetPassword(@RequestParam String otp, @RequestParam String newPassword) throws OtpNotVaild {
        logger.info("Received a request to reset password");

        try {
            taskoService.SubUserResetPassword(otp, newPassword);
            logger.info("Password reset successful!");
            return ResponseEntity.ok("Password set successful!");
        } catch (OtpNotVaild e) {
            logger.error("Password reset failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while resetting password: {}", e.getMessage());
            throw e;
        }
    }
    @PutMapping("/Assign_Client")
    public ResponseEntity<String> assignClient(@RequestBody clientIds request) {
        try {
            for (Long clientId : request.getClientIds()) {
                Optional<Client_Registation_Form> fileEntityOptional = clientRepository.findByClientId(clientId);

                fileEntityOptional.ifPresent(fileEntity -> {
                    fileEntity.setSubUserId(request.getSubUserId());
                    clientRepository.save(fileEntity);
                });

                List<Filed_NotFiled> filedNotFiledList = filed_notFiledRepo.findByClientid(clientId);
                for (Filed_NotFiled filedNotFiled : filedNotFiledList) {
                    filedNotFiled.setSubUserid(request.getSubUserId());
                    filed_notFiledRepo.save(filedNotFiled);
                }

                List<Filed_NotFiled_GST> filedNotFiledGstList = gstfiled_notfiledRepo.findByClientid(clientId);

                for (Filed_NotFiled_GST filedNotFiledGstEntity : filedNotFiledGstList) {
                    filedNotFiledGstEntity.setSubUserid(request.getSubUserId());
                    gstfiled_notfiledRepo.save(filedNotFiledGstEntity);
                }
            }

            return ResponseEntity.ok("Client is assigned successfully.");
        } catch (DataAccessException e) {
            // Handle database-related exceptions
            // Log the exception details for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accessing database: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            // Log the exception details for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning client: " + e.getMessage());
        }
    }

    @PutMapping("/RenewPack")
    public ResponseEntity<String> RenewPack(@RequestBody CA_SubUser_RenewPack request)
    {
        Optional<CA_SubUsers> a=caSubUsersRepo.findById(request.getSubUserId());
        a.ifPresent(update->
        {
            update.setStatus(true);
            update.setStartDate(LocalDateTime.now());

            LocalDateTime endDate = LocalDateTime.parse(request.getEndDate(), formatter);
            update.setEndDate(endDate);

            caSubUsersRepo.save(update);

        });
        return ResponseEntity.ok("Sub User is successfully updated.");
    }


//    /////////////////////////////////////////////Client_Payment_Details Save////////////////////////////////////////////////
//
//    //---------------------------------------------------Sum Of Client_Payment_Details----------------------------------------------

    @GetMapping("/sumOFPaymentClientBySubUserid/{userid}/{subUserid}")
    public Map<String, Object> getPaymentSumsBySubUserid(@PathVariable Long userid,@PathVariable Long subUserid)
    {
        List<Client_Payment_Details> payments = client_Payment_Details_Repo.findByUseridAndSubUserid(userid,subUserid);

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


    //------------------------------------------update ReceivedPayment Client_Payment_Details---------------------------------------
    @PutMapping("/updateClientPaymentDetailsBySubUser/{userid}/{subUserid}/{receivedPayment}/{discount}/{totalPayment}")
    public Client_Payment_Details updatePayment(@PathVariable Long userid,@PathVariable Long subUserid, @PathVariable Long clientid,
                                                @PathVariable Long receivedPayment,
                                                @PathVariable Long discount, @PathVariable Long totalPayment) throws EnterValidAmount
    {
        Client_Payment_Details payment = client_Payment_Details_Repo.findByUseridAndSubUseridAndClientid(userid,subUserid, clientid);
        if (payment != null)
        {
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
            return client_Payment_Details_Repo.save(payment);
        } else {
            return null;
        }
    }

    //------------------------------------------------------------Sum Of Client_Payment_Details----------------------------------
    @Autowired
    private ClientPayment clientPayment; // Ensure you have the correct repository interface

    @GetMapping("/sumOFPaymentClientBySubUserid/{userid}/{subUserid}/{clientid}")
    public Map<String, Object> getPaymentSumsByUseridAndClientid(@PathVariable Long userid,@PathVariable Long subUserid, @PathVariable Long clientid)
    {
        List<Client_Payment_Details> payments = clientPayment.findByUseridAndSubUseridAndClientid(userid,subUserid, clientid);

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
    @GetMapping("/clientInfoByPaymentBySubUserid/{userid}/{subUserid}/{year}")
    public ResponseEntity<List<Client_Registation_Form>> getClientInfoByPayment(@PathVariable Long userid,@PathVariable Long subUserid, @PathVariable String year)
    {
        Long pendingPaymentThreshold = 0L; // You can adjust this value as needed

        List<Client_Payment_Details> paymentDetails = client_Payment_Details_Repo.findByUseridAndSubUseridAndYearAndPendingPaymentGreaterThan(userid,subUserid, year, pendingPaymentThreshold);

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

    /////////////////////////////////////////////////// filed-Notfiled(Income Tax) //////////////////////////////////////////////

    //-------------------------------------Counts Of filed-Notfiled-----------------------

    @GetMapping("/filedNotfiledCountsBySubUserid/{userid}/{subUserid}")
    public ResponseEntity<List<filed_NotfiledDTO>> getFileCountsBySubUser(@PathVariable Long userid,@PathVariable Long subUserid) throws UserNotFoundException {
        logger.info("Received a request to get filed/not filed counts for user ID {}", subUserid);

        try {
            List<filed_NotfiledDTO> fileCounts = taskoService.getFileCountsByUseridAndsubUserid(userid,subUserid);
            logger.info("Filed/not filed counts fetched successfully for user ID {}", subUserid);
            return ResponseEntity.ok(fileCounts);
        } catch (Exception e) {
            logger.error("An error occurred while fetching filed/not filed counts: {}", e.getMessage());
            throw e;
        }
    }

    //-------------------------------------------Find Max Date Of filed-Notfiled-----------------------------------
    @GetMapping("/maxLastUpdateDateBySubUserid/{userid}/{subUserid}")
    public ResponseEntity<Map<String, String>> getMaxLastUpdateDateByUserIdAndSubUserId(
            @PathVariable("userid") Long userid,
            @PathVariable("subUserid") Long subUserid) {

        Optional<Optional<LocalDate>> maxLastUpdateDate = Optional.ofNullable(filed_notFiledRepo.findMaxLastUpdateDateByUseridAndSubUserid(userid, subUserid));

        if (maxLastUpdateDate.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("lastUpdateDate", maxLastUpdateDate.get().toString());
            return ResponseEntity.ok(response);
        }
        else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }



    /////////////////////////////////////////////////// filed-Notfiled_GST(GST) //////////////////////////////////////////////

    //---------------------------Counts Of GST Filed_Notfiled-----------------------
    @GetMapping("/getGSTDataBySubUserid")
    public ResponseEntity<Map<String, List<GST_filed_NotfiledDTO>>> getGSTDataByCategoryAndSubUSerid(@RequestParam Long userid,@RequestParam Long subUserid) {
        Map<String, List<Map<String, Object>>> data = taskoService.getGSTDataByCategoryAndSubUSerid(userid,subUserid);

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

    //-------------------------------------------Find Max Date Of GST Filed_Notfiled-----------------------------------
    @GetMapping("/GSTmaxLastUpdateDateBySubuser/{userid}/{subUserid}")
    public ResponseEntity<Map<String, String>> GSTgetMaxLastUpdateDateByUserIdAndSubUserId(
            @PathVariable Long userid,
            @PathVariable Long subUserid) throws UserNotFoundException {

        LocalDate maxLastUpdateDate = gstfiled_notfiledRepo.findMaxLastUpdateDateByUseridAndSubUserid(userid, subUserid);

        if (maxLastUpdateDate != null)
        {
            Map<String, String> result = new HashMap<>();
            result.put("MaxDate", maxLastUpdateDate.toString());
            return ResponseEntity.ok(result);
        }
        else
        {
            throw new UserNotFoundException("Not Found");
        }
    }

    //--------------------------------------------Get Sub Users By Userid-----------------------------
    @GetMapping("/getSubUSersByUserid/{userid}")
    public  ResponseEntity<List<CA_SubUsers>> getSubUSersByUserid(@PathVariable Long userid)
    {

        List<CA_SubUsers> user=caSubUsersRepo.findByUserid(userid);
        if (user.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.ok(user);

    }
}
