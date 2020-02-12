package com.lifelinehealthcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingSlotRequestDto {

	private String patientName;
	private Long patientPhoneNumber;
	private String diseaseDetail;
	private String remarks;
}
