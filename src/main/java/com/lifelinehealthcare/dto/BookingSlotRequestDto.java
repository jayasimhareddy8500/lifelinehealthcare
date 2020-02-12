package com.lifelinehealthcare.dto;

import javax.validation.constraints.NotNull;

import com.lifelinehealthcare.constant.AppConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingSlotRequestDto {

	@NotNull(message = AppConstant.)
	private String patientName;
	private String patientPhoneNumber;
	private String diseaseDetail;
	private String remarks;
}
