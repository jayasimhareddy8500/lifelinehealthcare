package com.lifelinehealthcare.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	private Long otpValue;
	private String remarks;
	@ManyToOne
	@JoinColumn(name = "user_slot_id")
	private UserSlot userSlot;

}
