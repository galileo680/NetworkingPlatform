package com.bartek.NetworkingPlatform.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSkillsRequest {

    @NotEmpty(message = "Lista ID umiejętności nie może być pusta")
    private Set<Long> skillIds;
}