package com.Tasko.Registration.Controller;
import com.Tasko.Registration.Entity.ClientPass_Imgdetail;
import com.Tasko.Registration.Entity.Client_Registation_Form;
import com.Tasko.Registration.Repository.ClientPassRepository;
import com.Tasko.Registration.Repository.ClientRepository;
import com.Tasko.Registration.dto.ClientInfoResponse; // Add this import statement

import com.Tasko.Registration.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
public class File_My_Tax_Rerurn
{
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientPassRepository clientPassRepository;

    @GetMapping("/getByPan/{pan}")
    public ClientInfoResponse getClientInfoByPan(@PathVariable String pan) throws UserNotFoundException
    {
        // Fetch client information based on PAN
        Client_Registation_Form client = clientRepository.findByPan2(pan);
        if (client ==null)
        {
            throw new UserNotFoundException("Pan Not Found");
        }
        // Create a response object with only the required fields
        ClientInfoResponse response = new ClientInfoResponse();
        response.setName(client.getName());
        response.setMobile(client.getMobile());
        response.setEmail(client.getEmail());

        return response;
    }

    @PutMapping("/updateByPanClient/{pan}")
    public ResponseEntity<List<Client_Registation_Form>> updateClientByPan(
            @PathVariable String pan,
            @RequestBody Client_Registation_Form updatedClient) {

        // Find all clients with the given PAN
        List<Client_Registation_Form> existingClients = clientRepository.findAllByPan(pan);

        if (existingClients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the fields for each client
        for (Client_Registation_Form existingClient : existingClients) {
            existingClient.setName(updatedClient.getName());
            existingClient.setDob(updatedClient.getDob());
            existingClient.setProfession(updatedClient.getProfession());
            existingClient.setTelephone(updatedClient.getTelephone());
            existingClient.setMobile(updatedClient.getMobile());
            existingClient.setEmail(updatedClient.getEmail());
            //---------------------ClientPass_Imgdetail change email-------------------
            ClientPass_Imgdetail imgDetail = clientPassRepository.findByPan1(pan);
            if (imgDetail != null)
            {
                imgDetail.setEmail(updatedClient.getEmail()); // Assuming you want to update email with pan
                clientPassRepository.save(imgDetail);
            }

            existingClient.setInvest_now_email(updatedClient.getInvest_now_email());
            existingClient.setAddress(updatedClient.getAddress());
            existingClient.setPin_code(updatedClient.getPin_code());
            existingClient.setState(updatedClient.getState());
            existingClient.setResidential_status(updatedClient.getResidential_status());

            // Save the updated client
            clientRepository.save(existingClient);
        }

        return new ResponseEntity<>(existingClients, HttpStatus.OK);
    }


}
