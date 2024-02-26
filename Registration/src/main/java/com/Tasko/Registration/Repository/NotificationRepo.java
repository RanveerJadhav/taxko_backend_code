package com.Tasko.Registration.Repository;

import com.Tasko.Registration.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification,Long>
{


    List<Notification> findAllByUseridAndCategory(Long userid, String single);

//    List<Notification> findAllByUseridAndCategoryStartingWith(Long userid, String m);

    @Query("SELECT n FROM Notification n WHERE n.userid = :userid AND n.category LIKE 'a%'")
    List<Notification> findAllByUseridAndCategoryStartingWith(@Param("userid") Long userid);

    List<Notification> findAllByClientid(Long clientid);


    List<Notification> findByUseridAndCategory(Long userid, String category);
}
