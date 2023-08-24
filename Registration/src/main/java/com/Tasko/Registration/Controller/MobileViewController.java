package com.Tasko.Registration.Controller;


import com.Tasko.Registration.Entity.Client_Registation_Form;
import com.Tasko.Registration.Entity.User_RegistrationsForm;
import com.Tasko.Registration.Entity.authclient;
import com.Tasko.Registration.Entity.authuser;
import com.Tasko.Registration.Repository.ClientRepository;
import com.Tasko.Registration.Repository.FileRepository;
import com.Tasko.Registration.Repository.TaskoRepository;
import com.Tasko.Registration.Service.JwtService;
import com.Tasko.Registration.Service.TaskoService;
import com.Tasko.Registration.dto.AuthRequest;
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
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class MobileViewController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TaskoService taskoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TaskoRepository taskoRepository;

    //private static final Logger logger = LogManager.getLogger(UserController.class);

    Logger logger = LoggerFactory.getLogger(MobileViewController.class);

    //----------------------------------check client password status---------------------------------------------------
    @GetMapping("/client/isPasswordNull")
    public ResponseEntity<Map<String, Object>> checkPassword(@RequestParam String pan) {
        Optional<Client_Registation_Form> check = clientRepository.findByPan(pan);

        if (check.isPresent()) {
            Client_Registation_Form client = check.get();
            boolean isPasswordNull = client.getPassword() == null;

            Map<String, Object> response = new HashMap<>();
            response.put("isPasswordNull", isPasswordNull);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //--------------------------------------Set Client Password--------------------------------------------------------
    @PutMapping("/clientSetPassword")
    public ResponseEntity<String> setpassword(@RequestParam String pan,
                                              @RequestParam String password)
    {
        taskoService.setpassword(pan,password);
        return ResponseEntity.ok("Password changed successfully.");
    }

    //--------------------------------------Login Page-------------------------------------------------------------
//    @PostMapping("/client/login")
//    public ResponseEntity<Object> clientauthenticateAndGetToken(@RequestBody AuthRequest authRequest) throws Exception
//    {
//        logger.info("Received an authentication request for username: {}", authRequest.getUsername());
//
//        try {
//            Optional<Client_Registation_Form> client = clientRepository.findByPan(authRequest.getUsername());
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//
//            if (authentication.isAuthenticated()) {
//                String jwt = jwtService.generateToken(authRequest.getUsername());
//
//                authclient response = new authclient(client);
//                response.setToken(jwt);
//                response.setClient(client);
//
//                logger.info("User {} has been authenticated successfully.", authRequest.getUsername());
//                return ResponseEntity.ok(response);
//            }
//        } catch (Exception e) {
//            logger.error("Authentication failed for user {}: {}", authRequest.getUsername(), e.getMessage());
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body(e.getMessage());
//        }
//        return ResponseEntity.ok(null);
//    }

    //------------------------------------Get File By Client Id---------------------------------------------------

}
