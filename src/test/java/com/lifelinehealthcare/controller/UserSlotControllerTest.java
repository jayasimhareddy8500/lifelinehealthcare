package com.lifelinehealthcare.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.lifelinehealthcare.common.LifeLineHealthEnum.BookingStatus;
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

@RunWith(MockitoJUnitRunner.class)
public class UserSlotControllerTest {

	@InjectMocks
	UserSlotController userSlotController;

	@Mock
	UserSlotService userSlotService;

	UserSlotDto userSlotDto = new UserSlotDto();
	List<UserSlotDto> userSlotDtos = new ArrayList<>();
	SlotRequestDto slotRequestDto = new SlotRequestDto();
	BookingSlotRequestDto bookingSlotRequestDto = new BookingSlotRequestDto();
	
	@Before
	public void init() {
		userSlotDto.setPatientName("Moorthy");

		userSlotDtos.add(userSlotDto);

		slotRequestDto.setLocationId(1);
		
		bookingSlotRequestDto.setPatientName("Moorthy");
	}

	@Test
	public void testGetUserSlotsByType() throws UserNotFoundException, InvalidBookingStatusException {
		when(userSlotService.getUserSlotsByType(1, BookingStatus.AVAILABLE)).thenReturn(userSlotDtos);
		ResponseEntity<UserSlotResponseDto> response = userSlotController.getUserSlotsByType(1,
				BookingStatus.AVAILABLE);
		assertThat(response.getBody().getSlots()).hasSize(1);
	}

	@Test
	public void testCreateAvailableSlot() throws UserNotFoundException, InvalidBookingStatusException,
			AlreadySlotBookedException, LocationNotFoundException {
		ResponseEntity<ResponseDto> response = userSlotController.createAvailableSlot(1, slotRequestDto);
		assertEquals(200, response.getBody().getStatusCode());
	}
	
	@Test
	public void testConfirmBookingSlot() throws UserNotFoundException, SlotNotFoundException {
		ResponseEntity<ResponseDto> response = userSlotController.confirmBookingSlot(1, 1, bookingSlotRequestDto);
		assertEquals(200, response.getBody().getStatusCode());
	}

}
