package com.bartek.NetworkingPlatform.dto.request;

import lombok.Data;

@Data
public class ProfileUpdateRequest {
    private String headline;
    private String bio;
    private String location;
}
