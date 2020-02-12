package com.lifelinehealthcare.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;

import com.lifelinehealthcare.dto.UserDetailsResponceDto;
import com.lifelinehealthcare.exception.UserNotFoundException;
import com.lifelinehealthcare.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {
	@InjectMocks
	UserController userController;
	@Mock
	UserService userService;

	UserDetailsResponceDto userDetailsResponceDto = new UserDetailsResponceDto();

	@Before
	public void init() {

	}

	@Test
	public void testGetUserDetailsById() throws UserNotFoundException {
		Mockito.when(userService.getUserDetailsById(1)).thenReturn(userDetailsResponceDto);
		ResponseEntity<UserDetailsResponceDto> userDetailsById = userController.getUserDetailsById(1);
		assertEquals(200, userDetailsById.getBody().getStatusCode());
	}

}
