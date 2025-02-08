package com.bartek.NetworkingPlatform.service;

import com.bartek.NetworkingPlatform.dto.UserDTO;
import com.bartek.NetworkingPlatform.dto.request.LoginRequest;
import com.bartek.NetworkingPlatform.dto.request.RegisterRequest;
import com.bartek.NetworkingPlatform.dto.response.LoginResponse;
import com.bartek.NetworkingPlatform.entity.User;

import java.util.List;

public interface UserService {
    void registerUser(RegisterRequest registerRequest);
    LoginResponse loginUser(LoginRequest loginRequest);
    List<UserDTO> getAllUsers();
    User getCurrentUser();
    void activateAccount(String activationToken);
}
