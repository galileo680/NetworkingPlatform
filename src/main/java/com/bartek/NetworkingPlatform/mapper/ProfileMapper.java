package com.bartek.NetworkingPlatform.mapper;

import com.bartek.NetworkingPlatform.dto.ProfileDTO;
import com.bartek.NetworkingPlatform.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileDTO toDTO(Profile profile) {
        return ProfileDTO.builder()
                .headline(profile.getHeadline())
                .bio(profile.getBio())
                .location(profile.getLocation())
                .build();
    }
}
