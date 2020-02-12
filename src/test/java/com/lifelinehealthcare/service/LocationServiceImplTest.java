package com.lifelinehealthcare.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.lifelinehealthcare.dto.LocationDto;
import com.lifelinehealthcare.entity.Location;
import com.lifelinehealthcare.repository.LocationRepository;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceImplTest {

	@InjectMocks
	LocationServiceImpl locationServiceImpl;
	
	@Mock
	LocationRepository locationRepository;
	
	Location location = new Location();
	List<Location> locations= new ArrayList<>();
	@Before
	public void init() {
		location.setLocationId(1);
		location.setLocationName("Bangalore");
		
		locations.add(location);
	}
	
	@Test
	public void testGetAllLocations() {
		when(locationRepository.findAll()).thenReturn(locations);
		List<LocationDto> response = locationServiceImpl.getAllLocations();
		assertThat(response).hasSize(1);
	}
}
