package com.lifelinehealthcare.service;

import javax.validation.Valid;

import com.lifelinehealthcare.dto.LoginRequsetDto;
import com.lifelinehealthcare.dto.LoginResponseDto;
import com.lifelinehealthcare.exception.UserNotFoundException;

public interface LoginService {

	LoginResponseDto authenticateUser(@Valid LoginRequsetDto loginRequestDto) throws UserNotFoundException;

}
