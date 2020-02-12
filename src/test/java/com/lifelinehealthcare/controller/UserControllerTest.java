package com.lifelinehealthcare.controller;

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

import com.lifelinehealthcare.dto.DoctorDto;
import com.lifelinehealthcare.dto.SearchRequestDto;
import com.lifelinehealthcare.dto.UserDetailsResponceDto;
import com.lifelinehealthcare.dto.UserSearchResponseDto;
import com.lifelinehealthcare.entity.User;
import com.lifelinehealthcare.entity.UserSlot;
import com.lifelinehealthcare.exception.UserNotFoundException;
import com.lifelinehealthcare.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	User user = new User();
	UserSlot userSlot = new UserSlot();
	SearchRequestDto searchRequestDto = new SearchRequestDto();

	@Before
	public void init() {
		user.setUserId(1);

		searchRequestDto.setDoctorName("Moorthy");
	}

	@Test
	public void testGetUserDetailsById() throws UserNotFoundException {
		when(userService.getUserDetailsById(1)).thenReturn(new UserDetailsResponceDto());
		ResponseEntity<UserDetailsResponceDto> response = userController.getUserDetailsById(1);
		assertEquals(200, response.getBody().getStatusCode());
	}

	@Test
	public void testGetUsersBySearchValue() {
		List<DoctorDto> doctors = new ArrayList<>();
		when(userService.getUsersBySearchValue(searchRequestDto)).thenReturn(doctors);
		ResponseEntity<UserSearchResponseDto> response = userController.getUsersBySearchValue(searchRequestDto);
		assertEquals(200, response.getBody().getStatusCode());

	}
}
