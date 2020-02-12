package com.lifelinehealthcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifelinehealthcare.common.LifeLineHealthEnum.BookingStatus;
import com.lifelinehealthcare.entity.UserSlot;

@Repository
public interface UserSlotRepository extends JpaRepository<UserSlot, Integer> {

	List<UserSlot> findAllByStatus(BookingStatus bookingStatus);
}
