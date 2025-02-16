package com.bartek.NetworkingPlatform.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ProfileCreateRequest {

    @NotBlank(message = "Headline is required")
    @Size(min = 2, max = 50,  message = "Wrong headline")
    private String headline;

    @NotBlank(message = "Bio is required")
    @Size(min = 2, max = 100,  message = "Wrong bio")
    private String bio;

    @NotBlank(message = "Location is required")
    @Size(min = 5, max = 25, message = "Wrong location format")
    private String location;
}
