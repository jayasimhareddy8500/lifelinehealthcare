package com.lifelinehealthcare.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSlotResponseDto extends ResponseDto{

	private List<UserSlotDto> slots;
}
