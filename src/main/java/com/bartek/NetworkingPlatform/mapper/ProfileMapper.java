package com.bartek.NetworkingPlatform.mapper;

import com.bartek.NetworkingPlatform.dto.request.ProfileCreateDTO;
import com.bartek.NetworkingPlatform.dto.response.ProfileResponseDTO;
import com.bartek.NetworkingPlatform.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileResponseDTO toDTO(Profile profile) {
        return ProfileResponseDTO.builder()
                .headline(profile.getHeadline())
                .bio(profile.getBio())
                .location(profile.getLocation())
                .build();
    }
}
