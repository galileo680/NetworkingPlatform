package com.bartek.NetworkingPlatform.mapper;

import com.bartek.NetworkingPlatform.dto.UserDTO;
import com.bartek.NetworkingPlatform.dto.response.user.UserSummaryResponse;
import com.bartek.NetworkingPlatform.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                //.role(user.getRole().name())
                .build();
    }

    public UserSummaryResponse mapToUserSummaryResponse(User user) {
        return UserSummaryResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .headline("")
                .profileImageUrl("")
                .build();
    }
}
