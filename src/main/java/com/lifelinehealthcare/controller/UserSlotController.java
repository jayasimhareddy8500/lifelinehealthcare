package com.lifelinehealthcare.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lifelinehealthcare.common.LifeLineHealthEnum.BookingStatus;
import com.lifelinehealthcare.constant.AppConstant;
import com.lifelinehealthcare.dto.BookingSlotRequestDto;
import com.lifelinehealthcare.dto.ResponseDto;
import com.lifelinehealthcare.dto.SlotRequestDto;
import com.lifelinehealthcare.dto.UserSlotDto;
import com.lifelinehealthcare.dto.UserSlotResponseDto;
import com.lifelinehealthcare.exception.AlreadySlotBookedException;
import com.lifelinehealthcare.exception.InvalidBookingStatusException;
import com.lifelinehealthcare.exception.LocationNotFoundException;
import com.lifelinehealthcare.exception.SlotNotFoundException;
import com.lifelinehealthcare.exception.UserNotFoundException;
import com.lifelinehealthcare.service.UserSlotService;

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
@RestController
@RequestMapping("/users")
@CrossOrigin
@Slf4j
public class UserSlotController {

	@Autowired
	UserSlotService userSlotService;

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
	@GetMapping("{userId}/slots")
	public ResponseEntity<UserSlotResponseDto> getUserSlotsByType(@PathVariable Integer userId,
			@RequestParam("statusType") BookingStatus statusType)
			throws UserNotFoundException, InvalidBookingStatusException {
		log.info("get the user slots based on the status type...");
		List<UserSlotDto> userSlots = userSlotService.getUserSlotsByType(userId, statusType);
		UserSlotResponseDto userSlotResponseDto = new UserSlotResponseDto();
		userSlotResponseDto.setSlots(userSlots);
		userSlotResponseDto.setStatusCode(AppConstant.SUCCESS_STATUS_CODE);
		userSlotResponseDto.setMessage(AppConstant.SUCCESS_MESSAGE);
		log.info("return the getting the user slots based on the status type...");
		return new ResponseEntity<>(userSlotResponseDto, HttpStatus.OK);
	}

	/**
	 * Create a available slots based on the details as locationId, hospital detail,
	 * slot date with time
	 * 
	 * @param slotRequestDto - details of the slot create such as locationId,
	 *                       hospital detail, slot date with time
	 * @return responseDto with the status code and success message;
	 * @author Govindasamy.C
	 * @throws UserNotFoundException
	 * @throws AlreadySlotBookedException
	 * @throws LocationNotFoundException
	 * @since 12-02-2020
	 */
	@PostMapping("{userId}/slots")
	public ResponseEntity<ResponseDto> createAvailableSlot(@PathVariable Integer userId,
			@Valid @RequestBody SlotRequestDto slotRequestDto)
			throws UserNotFoundException, AlreadySlotBookedException, LocationNotFoundException {
		log.info("create a avaialble slots based on the slotRequestDto...");
		userSlotService.createAvailableSlot(userId, slotRequestDto);
		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage(AppConstant.SLOT_CREATED_SCCUESS_MESSAGE);
		responseDto.setStatusCode(HttpStatus.OK.value());
		log.info("returning the success code and message in create a avaialble slots...");
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@PostMapping("{userId}/slots/{slotId}")
	public ResponseEntity<ResponseDto> confirmBookingSlot(@PathVariable Integer userId, @PathVariable Integer slotId,
			@Valid @RequestBody BookingSlotRequestDto requestDto) throws UserNotFoundException, SlotNotFoundException {
		log.info("booking the available slot based on the slot ID...");
		userSlotService.confirmBookingSlot(userId, slotId, requestDto);
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setMessage(AppConstant.SLOT_BOOKED_SCCUESS_MESSAGE);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
}
