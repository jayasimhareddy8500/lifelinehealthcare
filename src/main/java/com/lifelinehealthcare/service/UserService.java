package com.lifelinehealthcare.service;

import java.util.List;

import com.lifelinehealthcare.dto.DoctorDto;
import com.lifelinehealthcare.dto.SearchRequestDto;
import com.lifelinehealthcare.dto.UserDetailsResponceDto;
import com.lifelinehealthcare.exception.UserNotFoundException;

public interface UserService {

	UserDetailsResponceDto getUserDetailsById(Integer userId) throws UserNotFoundException;
	
	public List<DoctorDto> getUsersBySearchValue(SearchRequestDto searchRequestDto);
}
