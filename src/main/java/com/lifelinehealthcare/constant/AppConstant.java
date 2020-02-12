package com.lifelinehealthcare.constant;

/**
 * Set the all constant values and use that values in the whole application
 * 
 * @author Govindasamy.C
 * @since 12-02-2020
 * @version V1.1
 *
 */
public class AppConstant {

	private AppConstant() {

	}

	// Common
	public static final String FAILURE_MESSAGE = "FAILURE";
	public static final String SUCCESS_MESSAGE = "SUCCESS";
	public static final String NO_RECORDS_FOUND = "No Records Found";

	// User
	public static final String USER_NOT_FOUND = "User not found";
	public static final String USER_DETAIL_NOT_FOUND = "User Detail not found";
	public static final Integer SUCCESS_STATUS_CODE = 200;
	public static final String LOGIN_SCCUESS_MESSAGE = "User Login Successfully";

	// Booking User Slots
	public static final String SLOT_CREATED_SCCUESS_MESSAGE = "Slot Created Successfully";
	public static final String SLOT_BOOKED_SCCUESS_MESSAGE = "Slot Booked Successfully";
	public static final String INVALID_BOOKING_STATUS_TYPE = "Invalid Booking Status Type.";
	public static final String ALREADY_SLOT_BOOKED = "Already Slot Booked for this Date and Time";
	public static final String SLOT_DATE_FROM_TIME_SHOULD_NOT_LESS_1 = "slotTimeFrom can't be less than 1";
	public static final String SLOT_DATE_TO_TIME_SHOULD_NOT_LESS_1 = "slotTimeTo can't be less than 1";

	public static final String SLOT_RANGE_DELIMITTER = "-";
	public static final String SLOT_RANGE_MINUTE = ":00";
	public static final int SLOT_RANGE_LIMIT = 15;
	public static final String SLOT_DATE_PATTERN = "yyyy-MM-dd";

	public static final String LOCATION_NOT_FOUND = "Location not found";
	public static final String SLOT_NOT_FOUND = "Slot not found";

	public static final String DOCTOR_SHOULD_NOT_BE_NULL = "Doctor Name should not be null";
	public static final String PATIENT_SHOULD_NOT_BE_NULL = "Patient Name should not be null";
	public static final String CATEGORY_SHOULD_NOT_BE_NULL = "Category should not be null";
	public static final String LOCATION_SHOULD_NOT_BE_NULL = "Location should not be null";

}
