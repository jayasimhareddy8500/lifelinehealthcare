package com.lifelinehealthcare.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class AvilableSlotDto {
	private Integer userSlotId;
	private LocalDate slotDate;
	private String slotTimeFrom;
	private String slotTimeTo;
	private String hospitalDetail;

}
