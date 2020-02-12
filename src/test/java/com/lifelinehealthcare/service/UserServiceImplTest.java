package com.lifelinehealthcare.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.lifelinehealthcare.common.LifeLineHealthEnum.BookingStatus;
import com.lifelinehealthcare.dto.DoctorDto;
import com.lifelinehealthcare.dto.SearchRequestDto;
import com.lifelinehealthcare.dto.UserDetailsResponceDto;
import com.lifelinehealthcare.entity.User;
import com.lifelinehealthcare.entity.UserDetail;
import com.lifelinehealthcare.entity.UserSlot;
import com.lifelinehealthcare.exception.UserNotFoundException;
import com.lifelinehealthcare.repository.DoctorFeedbackRepository;
import com.lifelinehealthcare.repository.UserDetailRepository;
import com.lifelinehealthcare.repository.UserRepository;
import com.lifelinehealthcare.repository.UserSlotRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	UserRepository userRepository;

	@Mock
	UserDetailRepository userDetailRepository;

	@Mock
	UserSlotRepository userSlotRepository;

	@Mock
	DoctorFeedbackRepository doctorFeedbackRepository;

	User user = new User();
	UserDetail userDetail = new UserDetail();

	UserSlot userSlot = new UserSlot();
	List<UserSlot> userSlots = new ArrayList<>();
	List<User> users = new ArrayList<>();
	SearchRequestDto searchRequestDto = new SearchRequestDto();
	@Before
	public void init() {

		user.setUserId(1);
		
		userDetail.setUser(user);
		userDetail.setUserDetailId(1);
		
		searchRequestDto.setDoctorName("Moorthy");
		searchRequestDto.setCategory("Heart Specialist");
		
		userSlot.setUser(user);
		userSlot.setUserSlotId(1);
		userSlots.add(userSlot);
		
		user.setUserDetail(userDetail);
		users.add(user);
	}

	@Test
	public void testgetUserDetailsById() throws UserNotFoundException {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userDetailRepository.findByUser(user)).thenReturn(Optional.of(userDetail));
		when(userSlotRepository.findAllByUserAndStatus(user, BookingStatus.AVAILABLE)).thenReturn(userSlots);
		UserDetailsResponceDto response = userServiceImpl.getUserDetailsById(1);
		assertThat(response.getAvilableslot()).hasSize(1);

	}
	
	@Test(expected = UserNotFoundException.class)
	public void testgetUserDetailsByIdForUserNotFoundEx() throws UserNotFoundException {
		when(userRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		userServiceImpl.getUserDetailsById(1);
	}
	
	@Test
	public void testGetUsersBySearchValue() {
		when(userRepository.getUsersBySearchValue(searchRequestDto.getDoctorName(), searchRequestDto.getCategory()))
		.thenReturn(users);
		List<DoctorDto> doctors = userServiceImpl.getUsersBySearchValue(searchRequestDto);
		assertThat(doctors).hasSize(1);
	}

}
