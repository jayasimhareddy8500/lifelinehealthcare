package com.lifelinehealthcare.common;

/**
 * LifeLineHealthEnum is the enum for the maintaining the values of the
 * Booking status details.
 * 
 * @author Amala.S
 * @since 12-02-2020
 * @version V1.1
 *
 */
public class LifeLineHealthEnum {

	/**
	 * Maintaining the transfer status type such as Transfered Pending, Cancelled
	 * 
	 * @author Amala
	 * @since 11-02-2020
	 */
	public enum BookinStatus {
		PENDING, BOOKED;

	}
	
	/**
	 * Maintaining the role type such as doctor and patient
	 * 
	 * @author Govindasamy
	 * @since 12-02-2020
	 */
	public enum Role {
		DOCTOR, PATIENT;

	}

	
}
