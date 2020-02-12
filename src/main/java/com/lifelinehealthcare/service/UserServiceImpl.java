package com.lifelinehealthcare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifelinehealthcare.common.LifeLineHealthEnum.BookingStatus;
import com.lifelinehealthcare.constant.AppConstant;
import com.lifelinehealthcare.dto.AvilableSlotDto;
import com.lifelinehealthcare.dto.DoctorDto;
import com.lifelinehealthcare.dto.SearchRequestDto;
import com.lifelinehealthcare.dto.UserDetailsResponceDto;
import com.lifelinehealthcare.entity.DoctorFeedback;
import com.lifelinehealthcare.entity.User;
import com.lifelinehealthcare.entity.UserDetail;
import com.lifelinehealthcare.entity.UserSlot;
import com.lifelinehealthcare.exception.UserNotFoundException;
import com.lifelinehealthcare.repository.DoctorFeedbackRepository;
import com.lifelinehealthcare.repository.UserDetailRepository;
import com.lifelinehealthcare.repository.UserRepository;
import com.lifelinehealthcare.repository.UserSlotRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	UserSlotRepository userSlotRepository;
	@Autowired
	DoctorFeedbackRepository doctorFeedbackRepository;

	@Override
	public UserDetailsResponceDto getUserDetailsById(Integer userId) throws UserNotFoundException {
		UserDetailsResponceDto userDetailsResponceDto= new UserDetailsResponceDto();
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		}
		userDetailsResponceDto.setUserId(user.get().getUserId());
		userDetailsResponceDto.setName(user.get().getName());
		
		Optional<UserDetail> userDetail = userDetailRepository.findByUser(user.get());
		if(!userDetail.isPresent()) {
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		}
		
		BeanUtils.copyProperties(userDetail, userDetailsResponceDto);
		
		List<UserSlot> userSlots = userSlotRepository.findAllByUserAndStatus(user.get(), BookingStatus.AVAILABLE);
		List<AvilableSlotDto> availableSlots = userSlots.stream().map(userSlot ->convertToAvaialbleSlot(userSlot))
				.collect(Collectors.toList());
		userDetailsResponceDto.setAvilableslot(availableSlots);
		return userDetailsResponceDto;
	}

	@Override
	public List<DoctorDto> getUsersBySearchValue(SearchRequestDto searchRequestDto) {

		List<User> users = userRepository.getUsersBySearchValue(searchRequestDto.getDoctorName(),
				searchRequestDto.getCategory());
		return users.stream().map(this::convertUserToDoctortDto).collect(Collectors.toList());
	}
	
	private AvilableSlotDto convertToAvaialbleSlot(UserSlot userSlot) {
		AvilableSlotDto avilableSlotDto = new AvilableSlotDto();
		BeanUtils.copyProperties(userSlot, avilableSlotDto);
		return avilableSlotDto;
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
