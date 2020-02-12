package com.lifelinehealthcare.exception;

public class InvalidBookingStatusException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidBookingStatusException(String exception) {
		super(exception);
	}
}
