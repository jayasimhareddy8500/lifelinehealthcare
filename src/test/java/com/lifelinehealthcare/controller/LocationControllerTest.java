package com.lifelinehealthcare.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.lifelinehealthcare.dto.LocationDto;
import com.lifelinehealthcare.dto.LocationResponseDto;
import com.lifelinehealthcare.service.LocationService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LocationControllerTest {
	@InjectMocks
	LocationController locationController;
	@Mock
	LocationService locationService;

	@Test
	public void testGetAllLocations() {

		List<LocationDto> listOfLocations = new ArrayList<LocationDto>();
		LocationDto locationDto = new LocationDto();
		locationDto.setLocationName("Electronic City");
		LocationDto locationDto1 = new LocationDto();
		locationDto1.setLocationName("majestic");
		listOfLocations.add(locationDto);
		listOfLocations.add(locationDto1);
		Mockito.when(locationService.getAllLocations()).thenReturn(listOfLocations);

		ResponseEntity<LocationResponseDto> response = locationController.getAllLocations();
		assertThat(response.getBody().getLocationDto()).hasSize(2);

	}

}
