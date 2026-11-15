package com.psa.flightapp.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psa.flightapp.dto.ReservationRequest;
import com.psa.flightapp.entities.Flight;
import com.psa.flightapp.entities.Reservation;
import com.psa.flightapp.repositories.FlightRepository;
import com.psa.flightapp.services.ReservationService;

@RestController
@RequestMapping("/api/user")
public class UserBookingController {

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private ReservationService reservationService;

	@GetMapping("/flights")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public List<Flight> searchFlights(@RequestParam("from") String from,
			@RequestParam("to") String to,
			@RequestParam("departureDate") @DateTimeFormat(pattern = "MM-dd-yyyy") Date departureDate) {
		return flightRepository.findFlights(from, to, departureDate);
	}

	@PostMapping("/reservations")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public Reservation createReservation(@Valid @RequestBody ReservationRequest request) {
		return reservationService.bookFlight(request);
	}
}
