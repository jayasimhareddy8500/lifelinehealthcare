package com.lifelinehealthcare.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSearchResponseDto extends ResponseDto {

	private List<DoctorDto> users;
}
