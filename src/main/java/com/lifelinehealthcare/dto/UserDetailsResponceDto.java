package com.lifelinehealthcare.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDetailsResponceDto extends ResponseDto {
	private Integer userId;
	private String name;
	private String educationQualification;
	private Double yearsOfExperience;
	private String category;
	private String feedBack;

	
	List<AvilableSlotDto> avilableslot;
}
