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
public class UserDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userDetailId;
	private String educationQualification;
	private Double yearsOfExperience;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
}
