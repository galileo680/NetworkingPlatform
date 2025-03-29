package com.bartek.NetworkingPlatform.dto.request.application;

import com.bartek.NetworkingPlatform.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusUpdateRequest {

    @NotNull(message = "Status cannot be empty")
    private ApplicationStatus status;
}