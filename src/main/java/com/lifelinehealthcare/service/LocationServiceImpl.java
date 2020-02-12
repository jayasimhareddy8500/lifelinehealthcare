package com.lifelinehealthcare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifelinehealthcare.dto.LocationDto;
import com.lifelinehealthcare.entity.Location;
import com.lifelinehealthcare.repository.LocationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {
	@Autowired
	LocationRepository locationRepository;

	@Override
	public List<LocationDto> getAllLocations() {
		log.info("startin the getAllLocations()method, inside locationServiceImpl..");

		List<Location> locationOfList = locationRepository.findAll();

		return locationOfList.stream().map(this::convertEntityToDto).collect(Collectors.toList());

	}

	private LocationDto convertEntityToDto(Location location) {
		LocationDto locationDto = new LocationDto();
		locationDto.setLocationId(location.getLocationId());
		locationDto.setLocationName(location.getLocationName());
		return locationDto;
	}

}
