package com.Tasko.Registration.Controller;

import com.Tasko.Registration.Entity.Client_Registation_Form;


import com.Tasko.Registration.Entity.FileEntity;
import com.Tasko.Registration.Entity.authclient;
import com.Tasko.Registration.Repository.ClientRepository;
import com.Tasko.Registration.Service.JwtService;
import com.Tasko.Registration.Service.TaskoService;
import com.Tasko.Registration.dto.AuthRequestClient;
import com.Tasko.Registration.dto.ClientGetFile;
import com.Tasko.Registration.dto.SetClientPassword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ClientMobileViewController
{
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TaskoService taskoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientRepository clientRepository;

    Logger logger= LoggerFactory.getLogger(ClientMobileViewController.class);
    //----------------------------------check client password status---------------------------------------------------
    @PostMapping("/client/isPasswordNull")
    public ResponseEntity<Map<String, Object>> checkPassword(@RequestBody SetClientPassword setClientPassword) {
        Optional<Client_Registation_Form> check = clientRepository.findByPan(setClientPassword.getPan());

        if (check.isPresent()) {
            Client_Registation_Form client = check.get();
            boolean isPasswordNull = client.getPassword() == null;

            logger.info("Password check for PAN {}: Is password null? {}", setClientPassword.getPan(), isPasswordNull);

            Map<String, Object> response = new HashMap<>();
            response.put("isPasswordNull", isPasswordNull);

            return ResponseEntity.ok(response);
        } else {
            logger.warn("Password check failed for PAN: {}", setClientPassword.getPan());
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

    //--------------------------------------Login Page-------------------------------------------------------------
    @PostMapping("/client/authenticate")
    public ResponseEntity<Object> authenticateAndGetClientToken(@RequestBody AuthRequestClient authRequestClient) throws Exception
    {
        logger.info("Received an authentication request for username: {}", authRequestClient.getClientusername());

        try {
            Optional<Client_Registation_Form> client = clientRepository.findByPan(authRequestClient.getClientusername());

            Authentication authenticationclient = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestClient.getClientusername(), authRequestClient.getClientpassword()));

            if(client.isPresent())
            {
                if (authenticationclient.isAuthenticated())
                {

                    System.out.println("get Client");
                    String jwt = jwtService.generateToken(authRequestClient.getClientusername());

                    authclient response = new authclient();
                    response.setToken(jwt);
                    response.setClient(client);

                    logger.info("User {} has been authenticated successfully.", authRequestClient.getClientusername());
                    return ResponseEntity.ok(response);
                }
            }
            else
            {
              throw new Exception("Client Not Found");
            }


        } catch (Exception e)
        {
            logger.error("Authentication failed for user {}: {}", authRequestClient.getClientusername(), e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }

        return null;
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

}
