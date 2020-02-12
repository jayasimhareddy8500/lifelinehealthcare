package com.lifelinehealthcare.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserSlotBook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userSlotBookId;
	private String patientName;
	private Long patientPhoneNumber;
	private String diseaseDetail;
	private Long otpValue;
	private String remarks;
	@OneToOne
	@JoinColumn(name = "user_slot_id")
	private UserSlot userSlot;

}
