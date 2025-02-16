package com.bartek.NetworkingPlatform.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ExperienceDTO {

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 20,  message = "Wrong company name")
    private String companyName;

    @NotBlank(message = "Position is required")
    @Size(min = 3, max = 20,  message = "Wrong position name")
    private String position;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 100,  message = "Wrong name for a product")
    private String description;
}
