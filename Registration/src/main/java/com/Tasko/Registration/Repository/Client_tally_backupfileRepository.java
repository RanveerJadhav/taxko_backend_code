package com.Tasko.Registration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Client_tally_backupfile;
import com.Tasko.Registration.Entity.User_tally_backupfile;

public interface Client_tally_backupfileRepository extends JpaRepository<Client_tally_backupfile, Long> {

	Optional<Client_tally_backupfile> findByPan(String pan);

}
