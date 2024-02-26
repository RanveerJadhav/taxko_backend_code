package com.Tasko.Registration.Controller;

import com.Tasko.Registration.Entity.Client_Registation_Form;
import com.Tasko.Registration.Entity.Client_Registation_Form_Temp;
import com.Tasko.Registration.Entity.User_RegistrationsForm;
import com.Tasko.Registration.Repository.ClientRepository;
import com.Tasko.Registration.Repository.Client_Registation_Form_Temp_Repo;
import com.Tasko.Registration.Repository.TaskoRepository;
import com.Tasko.Registration.Service.TaskoService;
import com.Tasko.Registration.error.EmailMandatoryException;
import com.Tasko.Registration.error.UserAlreadyExist;
import com.Tasko.Registration.error.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class Client_Registation_Form_Temp_Controller
{
    @Autowired
    private Client_Registation_Form_Temp_Repo tempRepo;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TaskoRepository UserRepo;

    @Autowired
    private TaskoService taskoService;





    Logger logger = LoggerFactory.getLogger(Client_Registation_Form_Temp_Controller.class);

    //----------------------------------------------Save Client----------------------------------------------------
    @PostMapping("/saveClientTemp")
    public Client_Registation_Form_Temp saveClientTemp(@RequestBody Client_Registation_Form_Temp temp) throws UserAlreadyExist, EmailMandatoryException
    {
        Optional<Client_Registation_Form_Temp> temppan=tempRepo.findByPan(temp.getPan());
        Optional<Client_Registation_Form> client=clientRepository.findByPan(temp.getPan());

        Optional<Client_Registation_Form_Temp> existingEmail = tempRepo.findByEmail(temp.getEmail());

        if(temppan.isPresent() || client.isPresent())
        {
            throw new UserAlreadyExist("PAN Already Exist");
        }
        if (existingEmail.isPresent())
        {
            throw new EmailMandatoryException("Email Already Exist");
        }
        temp.setPassword(passwordEncoder.encode(temp.getPassword()));
        return tempRepo.save(temp);
    }


    //------------------------------------------Get CA By Pin_Code-------------------------
    @GetMapping("/GetUserByPinCode/{pincode}")
    public List<User_RegistrationsForm> getuserByPin_Code(@PathVariable String pincode)
    {
        return UserRepo.findByPinCode(pincode);
    }

    //------------------------------------------Set Userid In Temp Table--------------------
    @PutMapping("/updateUserInTemp/{pan}")
    public ResponseEntity<Client_Registation_Form_Temp> updateUserInTemp(
            @RequestBody Client_Registation_Form_Temp client,
            @PathVariable String pan)
    {
        Optional<Client_Registation_Form_Temp> optionalClient = tempRepo.findByPan(pan);

        if (optionalClient.isPresent()) {
            Client_Registation_Form_Temp existingClient = optionalClient.get();

            if (Objects.nonNull(client.getUserid()) && !String.valueOf(client.getUserid()).trim().isEmpty()) {
                // Convert Long to String and then trim
                existingClient.setUserid(Long.valueOf(String.valueOf(client.getUserid()).trim()));
            } else {
                existingClient.setUserid(null);
            }


            // Save the updated Client_Registation_Form_Temp
            Client_Registation_Form_Temp updatedClient = tempRepo.save(existingClient);
            return ResponseEntity.ok(updatedClient);
        } else {
            // Handle the case when Client_Registation_Form_Temp is not found
            return ResponseEntity.notFound().build();
        }
    }

    //--------------------------------------------Get Temp Client By Userid----------------------------------
    @GetMapping("/getTempClientByUserid/{userid}")
    public List<Client_Registation_Form_Temp> getTempClient(@PathVariable Long userid)
    {
        return tempRepo.findByUserid(userid);
    }

    @GetMapping("/getClientException")
    public String getexception(@RequestParam String pan) throws UserAlreadyExist
    {
        Optional<Client_Registation_Form_Temp> temppan=tempRepo.findByPan(pan);
        Optional<Client_Registation_Form> client=clientRepository.findByPan(pan);

        if(temppan.isPresent() || client.isPresent())
        {
            throw new UserAlreadyExist("PAN Already Exist");
        }
        return pan;
    }

    //--------------------------Check client Status---------------------------------------
    @GetMapping("/CheckClientStatus/{pan}")
    public ResponseEntity<Map<String, Object>> checkPanStatus(@PathVariable String pan) throws UserNotFoundException
    {
        Map<String, Object> response = taskoService.checkPanStatus(pan);
        return ResponseEntity.ok(response);
    }

}
