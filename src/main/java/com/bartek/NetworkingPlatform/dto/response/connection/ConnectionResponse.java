package com.bartek.NetworkingPlatform.dto.response.connection;

import com.bartek.NetworkingPlatform.dto.response.user.UserSummaryResponse;
import com.bartek.NetworkingPlatform.enums.ConnectionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionResponse {
    private Long id;
    private UserSummaryResponse sender;
    private UserSummaryResponse receiver;
    private ConnectionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}