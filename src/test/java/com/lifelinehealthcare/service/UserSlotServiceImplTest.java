package com.lifelinehealthcare.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.lifelinehealthcare.common.LifeLineHealthEnum.BookingStatus;
import com.lifelinehealthcare.dto.BookingSlotRequestDto;
import com.lifelinehealthcare.dto.SlotRequestDto;
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

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserSlotServiceImplTest {

	@InjectMocks
	UserSlotServiceImpl userSlotServiceImpl;

	@Mock
	UserRepository userRepository;

	@Mock
	UserSlotRepository userSlotRepository;

	@Mock
	UserSlotBookRepository userSlotBookRepository;

	@Mock
	LocationRepository locationRepository;

	User user = new User();
	UserSlot userSlot = new UserSlot();
	Location location = new Location();
	UserSlotBook userSlotBook = new UserSlotBook();
	List<UserSlot> userSlots = new ArrayList<>();
	SlotRequestDto slotRequestDto = new SlotRequestDto();
	BookingSlotRequestDto bookingSlotRequestDto = new BookingSlotRequestDto();

	@Before
	public void init() {
		user.setUserId(1);

		location.setLocationId(1);
		userSlotBook.setUserSlotBookId(1);
		
		slotRequestDto.setLocationId(1);
		slotRequestDto.setSlotTimeFrom("01");
		slotRequestDto.setSlotTimeTo("02");
		slotRequestDto.setSlotDate("2020-02-13");

		userSlot.setUserSlotBook(userSlotBook);
		userSlot.setUser(user);
		userSlot.setUserSlotId(1);
		userSlots.add(userSlot);

		bookingSlotRequestDto.setPatientName("Moorthy");
	}

	@Test
	public void testGetUserSlotsByType() throws UserNotFoundException, InvalidBookingStatusException {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userSlotRepository.findAllByStatus(BookingStatus.AVAILABLE)).thenReturn(userSlots);
		List<UserSlotDto> response = userSlotServiceImpl.getUserSlotsByType(1, BookingStatus.AVAILABLE);
		assertThat(response).hasSize(1);
	}

	@Test(expected = UserNotFoundException.class)
	public void testGetUserSlotsByTypeForUserNotFoundEx() throws UserNotFoundException, InvalidBookingStatusException {
		when(userRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		userSlotServiceImpl.getUserSlotsByType(1, BookingStatus.AVAILABLE);
	}

	@Test(expected = InvalidBookingStatusException.class)
	public void testGetUserSlotsByTypeForInvalidBookingStatusEx()
			throws UserNotFoundException, InvalidBookingStatusException {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		userSlotServiceImpl.getUserSlotsByType(1, null);
	}

	@Test
	public void testCreateAvailableSlot()
			throws UserNotFoundException, AlreadySlotBookedException, LocationNotFoundException {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(locationRepository.findById(1)).thenReturn(Optional.of(location));
		when(userSlotRepository.findByUserAndSlotDateAndSlotRange(user, LocalDate.now(), "01:00-02:00"))
				.thenReturn(userSlots);
		userSlotServiceImpl.createAvailableSlot(1, slotRequestDto);
		assertEquals(1, location.getLocationId());
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testCreateAvailableSlotForUserNotFoundEx()
			throws UserNotFoundException, AlreadySlotBookedException, LocationNotFoundException {
		when(userRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		userSlotServiceImpl.createAvailableSlot(1, slotRequestDto);
	}
	
	@Test(expected = LocationNotFoundException.class)
	public void testCreateAvailableSlotForLocationNotFoundEx()
			throws UserNotFoundException, AlreadySlotBookedException, LocationNotFoundException {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(locationRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		userSlotServiceImpl.createAvailableSlot(1, slotRequestDto);
	}
	
	@Test
	public void testConfirmBookingSlot() throws UserNotFoundException, SlotNotFoundException {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userSlotRepository.findById(1)).thenReturn(Optional.of(userSlot));
		userSlotServiceImpl.confirmBookingSlot(1, 1, bookingSlotRequestDto);
		assertEquals(1, user.getUserId());
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testConfirmBookingSlotForUserNotFoundEx() throws UserNotFoundException, SlotNotFoundException {
		when(userRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		userSlotServiceImpl.confirmBookingSlot(1, 1, bookingSlotRequestDto);
	}
	
	@Test(expected = SlotNotFoundException.class)
	public void testConfirmBookingSlotForSlotNotFoundEx() throws UserNotFoundException, SlotNotFoundException {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userSlotRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		userSlotServiceImpl.confirmBookingSlot(1, 1, bookingSlotRequestDto);
	}
}
