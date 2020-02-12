package com.lifelinehealthcare.service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifelinehealthcare.common.LifeLineHealthEnum.BookingStatus;
import com.lifelinehealthcare.constant.AppConstant;
import com.lifelinehealthcare.dto.UserSlotDto;
import com.lifelinehealthcare.entity.User;
import com.lifelinehealthcare.entity.UserSlot;
import com.lifelinehealthcare.exception.InvalidBookingStatusException;
import com.lifelinehealthcare.exception.UserNotFoundException;
import com.lifelinehealthcare.repository.UserRepository;
import com.lifelinehealthcare.repository.UserSlotRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * User Slot Controller Class, We can take the rest api call's for user slot
 * available slots and booked slots for the user.
 * 
 * @author Govindasamy.C
 * @since 12-02-2020
 * @version V1.1
 *
 */
@Service
@Slf4j
public class UserSlotServiceImpl implements UserSlotService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserSlotRepository userSlotRepository;

	/**
	 * Get the User slots based on the status type for the userId
	 * 
	 * @param userId     - Id of the user
	 * @param statusType - user input gives the status type like booked or available
	 * @return details of the response with status code and message.
	 * @author Govindasamy.C
	 * @throws UserNotFoundException         - If giving the invalid user id that
	 *                                       time we will throw the user not found
	 *                                       exception.
	 * @throws InvalidBookingStatusException - Giving invalid booking status rather
	 *                                       than pending and booked, it will throw
	 *                                       the invalid booking status exception.
	 * @since 12-02-2020
	 */
	@Override
	public List<UserSlotDto> getUserSlotsByType(Integer userId, BookingStatus statusType)
			throws UserNotFoundException, InvalidBookingStatusException {
		log.info("get the user slots based on the status type...");
		// Check the user is present or not based on the userId.
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		}

		// Check the given booking status is valid or not.
		List<BookingStatus> allBookingStatus = new ArrayList<>(EnumSet.allOf(BookingStatus.class));
		Boolean isBookingStatusPresent = allBookingStatus.stream()
				.anyMatch(bookingStatus -> bookingStatus.equals(statusType));
		if (!isBookingStatusPresent) {
			throw new InvalidBookingStatusException(AppConstant.INVALID_BOOKING_STATUS_TYPE);
		}

		log.debug("get the user slots before repository call...");
		// Get the all user slots based on the status.
		List<UserSlot> userSlots = userSlotRepository.findAllByStatus(statusType);

		log.info("return the getting user slot details...");
		return userSlots.stream().map(this::convertUserSlotToUserSlotDto).collect(Collectors.toList());
	}

	/**
	 * Converting the user slot detail to dto based on the response.
	 * 
	 * @param userSlot - Details of the user slot details based on the user
	 * @return return the converted the details of the user slot based on the
	 *         response.
	 * @author Govindasamy.C
	 * @since 12-02-2020
	 */
	private UserSlotDto convertUserSlotToUserSlotDto(UserSlot userSlot) {
		log.info("converting the user slot detail to dto...");
		UserSlotDto userSlotDto = new UserSlotDto();
		BeanUtils.copyProperties(userSlot, userSlotDto);
		BeanUtils.copyProperties(userSlot.getUserSlotBook(), userSlotDto);
		return userSlotDto;
	}

}
