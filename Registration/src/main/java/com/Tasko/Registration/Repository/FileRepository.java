package com.Tasko.Registration.Repository;

import com.Tasko.Registration.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long>
{
    List<FileEntity> findByUseridAndClientidAndAccountyear(Long userid, Long clientid, String accountyear);

    Optional<FileEntity> findFirstByUseridAndClientidAndAccountyearAndFileName(Long userid, Long clientid, String accountyear,String fileName);


    FileEntity findByIdAndUseridAndClientidAndAccountyear(Long id,Long userid, Long clientid,String accountyear);

    List<FileEntity> findByClientidAndAccountyear(Long clientid,String accountyear);


   // FileEntity findByUserIdAndClientIdAndAccountYearAndFileName(Long userid, Long clientid, String accountyear, String fileName);
}
