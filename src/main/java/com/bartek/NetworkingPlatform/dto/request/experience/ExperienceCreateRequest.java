package com.bartek.NetworkingPlatform.dto.request.experience;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ExperienceCreateRequest {

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 20,  message = "Wrong company name")
    private String companyName;

    @NotBlank(message = "Position is required")
    @Size(min = 2, max = 50,  message = "Wrong position name")
    private String position;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 150,  message = "Wrong Description")
    private String description;
}
