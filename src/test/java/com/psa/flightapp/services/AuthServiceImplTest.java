package com.psa.flightapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.psa.flightapp.dto.AuthResponse;
import com.psa.flightapp.dto.LoginRequest;
import com.psa.flightapp.dto.RegisterRequest;
import com.psa.flightapp.entities.Role;
import com.psa.flightapp.entities.User;
import com.psa.flightapp.repositories.UserRepository;
import com.psa.flightapp.security.JwtUtil;

class AuthServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private AuthServiceImpl authService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void register_shouldCreateUserAndReturnToken() {
		RegisterRequest request = new RegisterRequest();
		request.setFirstName("Anita");
		request.setLastName("Patel");
		request.setEmail("anita@example.com");
		request.setPassword("secret123");

		when(userRepository.findByEmail("anita@example.com")).thenReturn(Optional.empty());
		when(passwordEncoder.encode("secret123")).thenReturn("encodedPassword");

		Authentication authentication = new UsernamePasswordAuthenticationToken("anita@example.com", "secret123");
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
		when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

		AuthResponse response = authService.register(request);

		assertNotNull(response);
		assertEquals("jwt-token", response.getToken());
		assertEquals("anita@example.com", response.getEmail());
	}

	@Test
	void login_shouldReturnTokenForExistingUser() {
		LoginRequest request = new LoginRequest();
		request.setEmail("john@example.com");
		request.setPassword("pass123");

		User user = new User();
		user.setEmail("john@example.com");
		user.setPassword("encodedPass");
		user.setRole(Role.USER);

		Authentication authentication = new UsernamePasswordAuthenticationToken("john@example.com", "pass123");
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
		when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("login-token");
		when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

		AuthResponse response = authService.login(request);

		assertNotNull(response);
		assertEquals("login-token", response.getToken());
		assertEquals("john@example.com", response.getEmail());
		assertEquals("USER", response.getRole());
	}
}
