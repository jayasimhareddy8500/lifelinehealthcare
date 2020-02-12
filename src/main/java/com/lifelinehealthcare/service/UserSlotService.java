package com.lifelinehealthcare.service;

import java.util.List;

import com.lifelinehealthcare.common.LifeLineHealthEnum.BookingStatus;
import com.lifelinehealthcare.dto.UserSlotDto;
import com.lifelinehealthcare.exception.InvalidBookingStatusException;
import com.lifelinehealthcare.exception.UserNotFoundException;

public interface UserSlotService {

	public List<UserSlotDto> getUserSlotsByType(Integer userId, BookingStatus statusType)
			throws UserNotFoundException, InvalidBookingStatusException;
}
