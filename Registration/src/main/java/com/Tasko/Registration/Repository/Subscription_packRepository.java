package com.Tasko.Registration.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tasko.Registration.Entity.Subscription_Userdata;
import com.Tasko.Registration.Entity.subsciption_Pack;

public interface Subscription_packRepository  extends JpaRepository <subsciption_Pack,Long> {
	List<subsciption_Pack> findAll();

	void save(Subscription_Userdata data);

}
