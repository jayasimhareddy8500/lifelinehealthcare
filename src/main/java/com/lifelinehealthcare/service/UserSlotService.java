package com.lifelinehealthcare.service;

import java.util.List;

import com.lifelinehealthcare.common.LifeLineHealthEnum.BookingStatus;
import com.lifelinehealthcare.dto.BookingSlotRequestDto;
import com.lifelinehealthcare.dto.SlotRequestDto;
import com.lifelinehealthcare.dto.UserSlotDto;
import com.lifelinehealthcare.exception.AlreadySlotBookedException;
import com.lifelinehealthcare.exception.InvalidBookingStatusException;
import com.lifelinehealthcare.exception.LocationNotFoundException;
import com.lifelinehealthcare.exception.SlotNotFoundException;
import com.lifelinehealthcare.exception.UserNotFoundException;

public interface UserSlotService {

	public List<UserSlotDto> getUserSlotsByType(Integer userId, BookingStatus statusType)
			throws UserNotFoundException, InvalidBookingStatusException;

	public void createAvailableSlot(Integer userId, SlotRequestDto slotRequestDto)
			throws UserNotFoundException, AlreadySlotBookedException, LocationNotFoundException;

	public void confirmBookingSlot(Integer userId, Integer slotId, BookingSlotRequestDto requestDto)
			throws UserNotFoundException, SlotNotFoundException;
}
