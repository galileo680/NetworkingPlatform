package com.bartek.NetworkingPlatform.service.Impl;

import com.bartek.NetworkingPlatform.exception.InvalidCredentialsException;
import com.bartek.NetworkingPlatform.exception.NotFoundException;
import com.bartek.NetworkingPlatform.dto.UserDTO;
import com.bartek.NetworkingPlatform.dto.request.auth.LoginRequest;
import com.bartek.NetworkingPlatform.dto.request.auth.RegisterRequest;
import com.bartek.NetworkingPlatform.dto.response.LoginResponse;
import com.bartek.NetworkingPlatform.entity.Role;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.mapper.UserMapper;
import com.bartek.NetworkingPlatform.repository.RoleRepository;
import com.bartek.NetworkingPlatform.repository.UserRepository;
import com.bartek.NetworkingPlatform.security.JwtService;
import com.bartek.NetworkingPlatform.service.UserService;
import com.bartek.NetworkingPlatform.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final JwtService jwtService;

    private final UserMapper userMapper;

    private final UserUtil userUtil;

    @Override
    public void registerUser(RegisterRequest registerRequest) {
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email is already in use");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));

        User user = User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .roles(Set.of(userRole))
                .build();

        User savedUser = userRepository.save(user);

        //sendValidationEmail(user);

    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new NotFoundException("Email not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password does not match");
        }

        //TODO: check if account has been activated via email

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public User getCurrentUser() {
        return userUtil.getCurrentUser();
    }

    //TODO: implement activateAccount method
    @Override
    public void activateAccount(String activationToken) {

    }
}
