package com.psa.flightapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ReservationUpdateRequest {
	@NotNull(message = "Reservation id is required")
	@Positive(message = "Reservation id must be positive")
	private Long id;
	private boolean checkedIn;
	@Min(value = 0, message = "Number of bags cannot be negative")
	private int numberOfBags;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isCheckedIn() {
		return checkedIn;
	}
	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}
	public int getNumberOfBags() {
		return numberOfBags;
	}
	public void setNumberOfBags(int numberOfBags) {
		this.numberOfBags = numberOfBags;
	}
}
