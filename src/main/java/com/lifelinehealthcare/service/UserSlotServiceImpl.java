package com.lifelinehealthcare.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifelinehealthcare.common.LifeLineHealthEnum.BookingStatus;
import com.lifelinehealthcare.constant.AppConstant;
import com.lifelinehealthcare.dto.BookingSlotRequestDto;
import com.lifelinehealthcare.dto.SlotRequestDto;
import com.lifelinehealthcare.dto.TimeDto;
import com.lifelinehealthcare.dto.UserSlotDto;
import com.lifelinehealthcare.entity.Location;
import com.lifelinehealthcare.entity.User;
import com.lifelinehealthcare.entity.UserSlot;
import com.lifelinehealthcare.entity.UserSlotBook;
import com.lifelinehealthcare.exception.AlreadySlotBookedException;
import com.lifelinehealthcare.exception.InvalidBookingStatusException;
import com.lifelinehealthcare.exception.LocationNotFoundException;
import com.lifelinehealthcare.exception.SlotNotFoundException;
import com.lifelinehealthcare.exception.UserNotFoundException;
import com.lifelinehealthcare.repository.LocationRepository;
import com.lifelinehealthcare.repository.UserRepository;
import com.lifelinehealthcare.repository.UserSlotBookRepository;
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
@Transactional
public class UserSlotServiceImpl implements UserSlotService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserSlotRepository userSlotRepository;

	@Autowired
	UserSlotBookRepository userSlotBookRepository;

	@Autowired
	LocationRepository locationRepository;

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
	@Override
	public void createAvailableSlot(Integer userId, SlotRequestDto slotRequestDto)
			throws UserNotFoundException, AlreadySlotBookedException, LocationNotFoundException {
		log.info("create a avaialble slots based on the slotRequestDto...");
		// Check the user is present or not based on the userId.
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		}

		// Check the location is present or not based on the userId.
		Optional<Location> location = locationRepository.findById(userId);
		if (!location.isPresent()) {
			throw new LocationNotFoundException(AppConstant.LOCATION_NOT_FOUND);
		}

		String slotRange = slotRequestDto.getSlotTimeFrom().concat(AppConstant.SLOT_RANGE_DELIMITTER)
				.concat(slotRequestDto.getSlotTimeTo());

		LocalDate slotDate = LocalDate.parse(slotRequestDto.getSlotDate());
		log.info("before finding the time slot in user slots...");

		List<UserSlot> userSlots = userSlotRepository.findByUserAndSlotDateAndSlotRange(user.get(), slotDate,
				slotRange);
		if (!userSlots.isEmpty()) {
			throw new AlreadySlotBookedException(AppConstant.ALREADY_SLOT_BOOKED);
		}

		log.info("converting the from time and to time in user slots...");
		String fromTime = slotRequestDto.getSlotTimeFrom().concat(AppConstant.SLOT_RANGE_MINUTE);
		String toTime = slotRequestDto.getSlotTimeTo().concat(AppConstant.SLOT_RANGE_MINUTE);
		log.info("getting the list of time ranges in user slots...");

		List<TimeDto> listOfSlots = getTimeSlots(LocalTime.parse(fromTime), LocalTime.parse(toTime),
				AppConstant.SLOT_RANGE_LIMIT);
		listOfSlots.forEach(slot -> {
			// Save Each time slot
			UserSlot userSlot = new UserSlot();
			userSlot.setUser(user.get());

			userSlot.setHospitalDetail(slotRequestDto.getHospitalDetail());
			userSlot.setSlotTimeFrom(slot.getFromTime());
			userSlot.setSlotTimeTo(slot.getToTime());
			userSlot.setLocation(location.get());
			userSlot.setSlotDate(slotDate);
			userSlot.setSlotRange(slot.getFromTime().concat("-").concat(slot.getToTime()));
			userSlot.setStatus(BookingStatus.AVAILABLE);
			log.debug("save the user slot details...");
			userSlotRepository.save(userSlot);

			UserSlotBook userSlotBook = new UserSlotBook();
			userSlotBook.setUserSlot(userSlot);
			log.debug("save the user slot booking details...");
			userSlotBookRepository.save(userSlotBook);

		});

	}

	/**
	 * get the time slots based on the time range of each 15 mins.
	 * 
	 * @param startTime - start time
	 * @param endTime   - end time
	 * @param slotRange - 15 mins per Each Hour
	 * @return - list of time slots
	 * @author Govindasamy.C
	 * @since 12-02-2020
	 */
	private List<TimeDto> getTimeSlots(LocalTime startTime, LocalTime endTime, int slotRange) {
		log.debug("getting the time slots...");
		List<TimeDto> slots = new ArrayList<>();
		
		while(startTime.isBefore(endTime)) {
			TimeDto timeDto = new TimeDto();
			LocalTime nextTime = startTime.plusMinutes(slotRange);
			if (nextTime.isAfter(endTime))
				break;
			timeDto.setFromTime(startTime.toString());
			timeDto.setToTime(nextTime.toString());
			slots.add(timeDto);
			startTime = nextTime;
		}
		
		return slots;
	}

	@Override
	public void confirmBookingSlot(Integer userId, Integer slotId, BookingSlotRequestDto requestDto)
			throws UserNotFoundException, SlotNotFoundException {
		log.info("confirm the booking slots based on the slot ID...");
		// Check the user is present or not based on the userId.
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		}

		// Check the slot is present or not based on the slotId.
		Optional<UserSlot> isUserSlot = userSlotRepository.findById(slotId);
		if (!isUserSlot.isPresent()) {
			throw new SlotNotFoundException(AppConstant.USER_NOT_FOUND);
		}

		log.debug("before updating the slot details...");
		UserSlot userSlot = isUserSlot.get();
		userSlot.setStatus(BookingStatus.BOOKED);
		userSlotRepository.save(userSlot);

		log.debug("before updating the slot booking details...");
		UserSlotBook userSlotBook = isUserSlot.get().getUserSlotBook();
		BeanUtils.copyProperties(requestDto, userSlotBook);
		userSlotBook.setPatientName(requestDto.getPatientName());
		userSlotBook.setPatientPhoneNumber(requestDto.getPatientPhoneNumber());
		userSlotBookRepository.save(userSlotBook);
	}
}
