package com.lifelinehealthcare.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifelinehealthcare.constant.AppConstant;
import com.lifelinehealthcare.dto.LoginRequsetDto;
import com.lifelinehealthcare.dto.LoginResponseDto;
import com.lifelinehealthcare.entity.User;
import com.lifelinehealthcare.exception.UserNotFoundException;
import com.lifelinehealthcare.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
	@Autowired
	UserRepository userRepository;

	@Override
	public LoginResponseDto authenticateUser(@Valid LoginRequsetDto loginRequestDto) throws UserNotFoundException {
		Optional<User> user = userRepository.findByPhoneNumberAndPassword(loginRequestDto.getPhoneNumber(),
				loginRequestDto.getPassword());
		log.info("Authenticating the User, inside LoginServiceImpl...");

		LoginResponseDto loginResponseDto = new LoginResponseDto();

		if (user.isPresent()) {
			BeanUtils.copyProperties(user.get(), loginResponseDto);
			loginResponseDto.setName(user.get().getName());
			loginResponseDto.setUserId(user.get().getUserId());
			loginResponseDto.setMessage(AppConstant.LOGIN_SCCUESS_MESSAGE);
			loginResponseDto.setStatusCode(AppConstant.SUCCESS_STATUS_CODE);
			log.info("UserServiceImpl authenticateUser ---> user signed in");
			return loginResponseDto;
		} else {
			log.error("UserServiceImpl authenticateUser ---> NotFoundException occured");
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		}

	}

}
