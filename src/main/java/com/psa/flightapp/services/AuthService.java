package com.psa.flightapp.services;

import com.psa.flightapp.dto.AuthResponse;
import com.psa.flightapp.dto.LoginRequest;
import com.psa.flightapp.dto.RegisterRequest;

public interface AuthService {

	AuthResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);
}
