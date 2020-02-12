package com.lifelinehealthcare.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoctorDto {

	private Integer userId;
	private String userName;
	private String educationQualification;
	private String categorySpecialist;
	private Double yearsOfExperience;
	private String feedBack;
}
