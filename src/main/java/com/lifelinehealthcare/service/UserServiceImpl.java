package com.lifelinehealthcare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifelinehealthcare.dto.DoctorDto;
import com.lifelinehealthcare.dto.SearchRequestDto;
import com.lifelinehealthcare.entity.User;
import com.lifelinehealthcare.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public List<DoctorDto> getUsersBySearchValue(SearchRequestDto searchRequestDto) {

		List<User> users = userRepository.getUsersBySearchValue(searchRequestDto.getDoctorName(),
				searchRequestDto.getCategory());
		return users.stream().map(this::convertUserToDoctortDto).collect(Collectors.toList());
	}

	private DoctorDto convertUserToDoctortDto(User user) {
		log.info("converting the user detail to dto...");
		DoctorDto userDto = new DoctorDto();
		BeanUtils.copyProperties(user, userDto);
		BeanUtils.copyProperties(user.getUserDetail(), userDto);
		userDto.setUserName(user.getName());
		userDto.setCategorySpecialist(user.getCategory());
		return userDto;
	}

}
