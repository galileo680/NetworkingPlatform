package com.bartek.NetworkingPlatform.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponseDTO {
    private String headline;
    private String bio;
    private String location;
}
