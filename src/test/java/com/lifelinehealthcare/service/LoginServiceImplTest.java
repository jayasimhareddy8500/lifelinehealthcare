package com.lifelinehealthcare.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import com.lifelinehealthcare.dto.LoginRequsetDto;
import com.lifelinehealthcare.dto.LoginResponseDto;
import com.lifelinehealthcare.entity.User;
import com.lifelinehealthcare.exception.UserNotFoundException;
import com.lifelinehealthcare.repository.UserRepository;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginServiceImplTest {
	@InjectMocks
	LoginServiceImpl loginServiceImpl;
	@Mock
	UserRepository userRepository;
	LoginRequsetDto loginRequestDto = new LoginRequsetDto();

	@Test
	public void testAuthenticateCustomer() throws NotFoundException, UserNotFoundException {

		loginRequestDto.setPhoneNumber(7680920258l);
		loginRequestDto.setPassword("sweety");
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginRequestDto.setPhoneNumber(7680920258l);
		loginResponseDto.setUserId(1);
		loginResponseDto.setName("amala");
		loginRequestDto.setPhoneNumber(7680920258l);

		User customer = new User();
		BeanUtils.copyProperties(loginResponseDto, customer);

		Mockito.when(userRepository.findByPhoneNumberAndPassword(loginRequestDto.getPhoneNumber(),
				loginRequestDto.getPassword())).thenReturn(Optional.of(customer));

		LoginResponseDto authenticateCustomer = loginServiceImpl.authenticateUser(loginRequestDto);

		assertEquals("amala", authenticateCustomer.getName());

	}

}
