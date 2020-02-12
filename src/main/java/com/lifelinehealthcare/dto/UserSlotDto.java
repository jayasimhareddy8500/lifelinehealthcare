package com.lifelinehealthcare.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSlotDto {

	private LocalDate slotDate;
	private Integer slotTimeFrom;
	private Integer slotTimeTo;
	private String hospitalDetail;
	private String patientName;
	private Long patientPhoneNumber;
	private String diseaseDetail;
}
