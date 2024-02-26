package com.Tasko.Registration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.User_CLient_tally_backupfile;
import com.Tasko.Registration.Entity.User_tally_backupfile;

public interface User_CLient_tally_backupfileRepository extends JpaRepository<User_CLient_tally_backupfile, Long>{

	Optional<User_CLient_tally_backupfile> findByClientidAndUserid(Long ClientId,Long UserId);

}
