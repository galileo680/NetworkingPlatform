package com.bartek.NetworkingPlatform.dto.request.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillCreateRequest {

    @NotBlank(message = "Nazwa umiejętności nie może być pusta")
    @Size(min = 2, max = 50, message = "Nazwa umiejętności musi mieć od 2 do 50 znaków")
    private String name;
}
