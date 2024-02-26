package com.Tasko.Registration.Controller;



import com.Tasko.Registration.Entity.Client_Registation_Form;
import com.Tasko.Registration.Repository.ClientRepository;
import com.Tasko.Registration.dto.FamilyClientids;
import com.Tasko.Registration.dto.Set_FamilyRelationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class Family_Group_Controller
{

    @Autowired
    private ClientRepository clientRepository;

    @PutMapping("/SaveFamily")
    public ResponseEntity<String> saveFamily(@RequestBody FamilyClientids ids)
    {
        for (Long id : ids.getClientIds())
        {
            Optional<Client_Registation_Form> existingRecord = clientRepository.findByClientId(id);

            if (existingRecord.isPresent())
            {
                Client_Registation_Form client = existingRecord.get();
                client.setFamilyId(ids.getFamilyIds());
                clientRepository.save(client);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Client Id  " + id + " not found");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Family information saved successfully");
    }

//    @GetMapping("/countFamilyIds/{userid}")
//    public ResponseEntity<String> countFamilyIds(@PathVariable Long userid) {
//        List<Object[]> counts = clientRepository.countFamilyIdsByUserId(userid);
//
//        // Assuming you want to build a response string with family_id and count
//        StringBuilder response = new StringBuilder("Counts:\n");
//        for (Object[] count : counts) {
//            Object familyIdObject = count[0];
//            Object familyCountObject = count[1];
//
//            // Handle potential casting issues
//            try {
//                String familyId = familyIdObject.toString();
//                Long familyCount = (familyCountObject instanceof Number) ? ((Number) familyCountObject).longValue() : Long.parseLong(familyCountObject.toString());
//
//                response.append(familyId).append(" = ").append(familyCount).append("\n");
//            } catch (NumberFormatException e) {
//                // Log or skip invalid values
//                System.out.println("Skipping invalid values: " + e.getMessage());
//            }
//        }
//
//        return ResponseEntity.ok(response.toString());
//    }





    @GetMapping("/countFamilyIds/{userid}")
    public ResponseEntity<Long> countFamilyIds(@PathVariable Long userid)
    {
        Long count = clientRepository.countByUserId(userid);
        return ResponseEntity.ok( count);
    }

    @GetMapping("/getClientByFamilyId/{familyId}")
    public ResponseEntity<List<Client_Registation_Form>> getClientByFamilyId(@PathVariable String familyId)
    {
        List<Client_Registation_Form> clients = clientRepository.findAllByFamilyId(familyId);

        if (clients.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(clients);
        }
    }

    @PutMapping("/setFamilyrelation")
    public ResponseEntity<String> setFamilyrelation(@RequestBody Set_FamilyRelationDto relation)
    {

            for (Long id : relation.getPrimaryclientIds())
            {
                Optional<Client_Registation_Form> primary = clientRepository.findByClientId(id);

                if (primary.isPresent())
                {
                    Client_Registation_Form client = primary.get();
                    client.setfRelation(relation.getSetPrimaryRelation());
                    clientRepository.save(client);
                }

            }

        for (Long ids: relation.getSecondaryclientIds())
        {
            Optional<Client_Registation_Form> Secondary = clientRepository.findByClientId(ids);

            if (Secondary.isPresent())
            {
                Client_Registation_Form client = Secondary.get();
                client.setfRelation(relation.getSetSecondaryRelation());
                clientRepository.save(client);
            }

        }
        return ResponseEntity.status(HttpStatus.OK).body("Family information saved successfully");

    }




}
