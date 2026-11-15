package com.psa.flightapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.psa.flightapp.dto.ReservationRequest;
import com.psa.flightapp.entities.Flight;
import com.psa.flightapp.entities.Passenger;
import com.psa.flightapp.entities.Reservation;
import com.psa.flightapp.repositories.FlightRepository;
import com.psa.flightapp.repositories.PassengerRepository;
import com.psa.flightapp.repositories.ReservationRepository;

class ReservationServiceImplTest {

	@Mock
	private FlightRepository flightRepo;

	@Mock
	private PassengerRepository passengerRepo;

	@Mock
	private ReservationRepository reservationRepo;

	@InjectMocks
	private ReservationServiceImpl reservationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void bookFlight_shouldCreatePassengerAndReservation() {
		Flight flight = new Flight();
		flight.setId(10L);
		flight.setOperatingAirlines("Air India");
		flight.setDepartureCity("Delhi");
		flight.setArrivalCity("Mumbai");
		flight.setDateOfDeparture(new Date());

		ReservationRequest request = new ReservationRequest();
		request.setId(10L);
		request.setFirstName("Rahul");
		request.setLastName("Sharma");
		request.setMiddleName("Kumar");
		request.setEmail("rahul@example.com");
		request.setPhone("9999999999");

		when(flightRepo.findById(10L)).thenReturn(Optional.of(flight));
		when(passengerRepo.save(any(Passenger.class))).thenAnswer(invocation -> invocation.getArgument(0));
		when(reservationRepo.save(any(Reservation.class))).thenAnswer(invocation -> {
			Reservation res = invocation.getArgument(0);
			res.setId(100L);
			return res;
		});

		Reservation reservation = reservationService.bookFlight(request);

		assertNotNull(reservation);
		assertEquals("Air India", reservation.getFlight().getOperatingAirlines());
		assertEquals("Rahul", reservation.getPassenger().getFirstName());
		assertEquals(false, reservation.isCheckedIn());
		verify(passengerRepo).save(any(Passenger.class));
		verify(reservationRepo).save(any(Reservation.class));
	}
}
