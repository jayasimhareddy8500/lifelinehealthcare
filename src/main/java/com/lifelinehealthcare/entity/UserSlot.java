package com.lifelinehealthcare.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.lifelinehealthcare.common.LifeLineHealthEnum.BookingStatus;

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
	private String slotTimeFrom;
	private String slotTimeTo;
	private String slotRange;
	private String hospitalDetail;
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "user_location_id")
	private Location location;
	
	@OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "userSlot")
    private UserSlotBook userSlotBook;
}
