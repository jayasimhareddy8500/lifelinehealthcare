package com.lifelinehealthcare.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lifelinehealthcare.constant.AppConstant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SlotRequestDto {

	@JsonFormat(pattern = AppConstant.SLOT_DATE_PATTERN)
	private String slotDate;
	private String slotTimeFrom;
	private String slotTimeTo;
	private Integer locationId;
	private String hospitalDetail;
}
