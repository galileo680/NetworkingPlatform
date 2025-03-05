package com.bartek.NetworkingPlatform.dto.request.profile;

import lombok.Data;

@Data
public class ProfileUpdateRequest {
    private String headline;
    private String bio;
    private String location;
}
