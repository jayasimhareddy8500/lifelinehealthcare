package com.lifelinehealthcare.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocationResponseDto {
	private Integer statusCode;
	private String message;
	private List<LocationDto> LocationDto;
	
}
