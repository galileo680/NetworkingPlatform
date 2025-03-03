package com.bartek.NetworkingPlatform.mapper;

import com.bartek.NetworkingPlatform.dto.response.SkillResponse;
import com.bartek.NetworkingPlatform.entity.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {
    private SkillResponse mapToSkillResponse(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .createdAt(skill.getCreatedAt())
                .updatedAt(skill.getUpdatedAt())
                .build();
    }
}
