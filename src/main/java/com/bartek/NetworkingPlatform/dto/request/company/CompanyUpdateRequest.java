package com.bartek.NetworkingPlatform.dto.request.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateRequest {
    @NotBlank(message = "The company name cannot be empty")
    @Size(min = 2, max = 100, message = "The company name must be between 2 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1,000 characters")
    private String description;

    @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\.-]+)\\.([a-z\\.]{2,6})([\\w\\.-]*)*\\/?$", message = "Invalid website address")
    private String website;

    @Size(max = 100, message = "Location name cannot exceed 100 characters")
    private String location;

    private String logoUrl;
}
