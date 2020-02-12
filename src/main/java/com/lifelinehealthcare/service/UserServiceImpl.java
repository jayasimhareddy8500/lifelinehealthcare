package com.lifelinehealthcare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		Optional<User> user = userRepository.findById(userId);

		if (!user.isPresent()) {
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		}

		Optional<UserDetail> userDetails = userDetailRepository.findByUser(user.get());

		Optional<UserSlot> slotdetails = userSlotRepository.findById(userId);

		Optional<DoctorFeedback> doctorFeedBack = doctorFeedbackRepository.findById(userId);

		UserDetailsResponceDto UserDetailsResponceDto = new UserDetailsResponceDto();
		UserDetailsResponceDto.setUserId(user.get().getUserId());

		UserDetailsResponceDto.setName(user.get().getName());

		UserDetailsResponceDto.setEducationQualification(userDetails.get().getEducationQualification());
		UserDetailsResponceDto.setCategory(user.get().getCategory());

		UserDetailsResponceDto.setYearsOfExperience(userDetails.get().getYearsOfExperience());
		UserDetailsResponceDto.setFeedBack(doctorFeedBack.get().getFeedBack());

		AvilableSlotDto avilableSlotDto = new AvilableSlotDto();

		avilableSlotDto.setUserSlotId(slotdetails.get().getUserSlotId());

		avilableSlotDto.setSlotTimeFrom(slotdetails.get().getSlotTimeFrom());
		avilableSlotDto.setSlotTimeTo(slotdetails.get().getSlotTimeTo());

		avilableSlotDto.setSlotDate(slotdetails.get().getSlotDate());
		avilableSlotDto.setHospitalDetail(slotdetails.get().getHospitalDetail());

		List<AvilableSlotDto> availableSlotList = new ArrayList<AvilableSlotDto>();
		availableSlotList.add(avilableSlotDto);

		UserDetailsResponceDto.setAvilableslot(availableSlotList);

		return UserDetailsResponceDto;
	}

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
