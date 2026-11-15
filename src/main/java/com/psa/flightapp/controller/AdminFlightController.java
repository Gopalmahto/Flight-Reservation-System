package com.psa.flightapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psa.flightapp.entities.Flight;
import com.psa.flightapp.repositories.FlightRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminFlightController {

	@Autowired
	private FlightRepository flightRepository;

	@GetMapping("/flights")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Flight> getAllFlights() {
		return flightRepository.findAll();
	}

	@PostMapping("/flights")
	@PreAuthorize("hasRole('ADMIN')")
	public Flight createFlight(@Valid @RequestBody Flight flight) {
		return flightRepository.save(flight);
	}

	@DeleteMapping("/flights/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
		flightRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
