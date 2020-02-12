package com.lifelinehealthcare.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lifelinehealthcare.constant.AppConstant;
import com.lifelinehealthcare.dto.LocationDto;
import com.lifelinehealthcare.dto.LocationResponseDto;
import com.lifelinehealthcare.service.LocationService;

import lombok.extern.slf4j.Slf4j;

/**
 * This Controller is used to get the List of locations
 * 
 * 
 * @author Amala
 * @version V1.1
 * @since 12-02-2020
 */

@RestController
@RequestMapping
@CrossOrigin
@Slf4j
public class LocationController {

	@Autowired
	LocationService locationService;

	@GetMapping("/locations")
	public ResponseEntity<LocationResponseDto> getAllLocations() {
		log.info("Get the list of locations...");

		List<LocationDto> response = locationService.getAllLocations();
		LocationResponseDto locationResponseDto = new LocationResponseDto();
		locationResponseDto.setMessage(AppConstant.SUCCESS_MESSAGE);
		locationResponseDto.setStatusCode(HttpStatus.OK.value());

		locationResponseDto.setLocationDto(response);

		return new ResponseEntity<>(locationResponseDto, HttpStatus.OK);
	}

}
