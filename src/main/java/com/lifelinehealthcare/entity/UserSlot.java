package com.lifelinehealthcare.entity;

import java.time.LocalDate;

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
public class UserSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userSlotId;
	private LocalDate slotDate;
	private Integer slotTimeFrom;
	private Integer slotTimeTo;
	private String slotRange;
	private String hospitalDetail;
	private String status;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "user_location_id")
	private Location location;
}
