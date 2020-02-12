package com.lifelinehealthcare.service;

import java.util.List;

import com.lifelinehealthcare.dto.DoctorDto;
import com.lifelinehealthcare.dto.SearchRequestDto;

public interface UserService {

	public List<DoctorDto> getUsersBySearchValue(SearchRequestDto searchRequestDto);
}
