package com.lifelinehealthcare.dto;

import javax.validation.constraints.NotNull;

import com.lifelinehealthcare.constant.AppConstant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchRequestDto {

	@NotNull(message = AppConstant.DOCTOR_SHOULD_NOT_BE_NULL)
	private String doctorName;

	@NotNull(message = AppConstant.CATEGORY_SHOULD_NOT_BE_NULL)
	private String category;

}
