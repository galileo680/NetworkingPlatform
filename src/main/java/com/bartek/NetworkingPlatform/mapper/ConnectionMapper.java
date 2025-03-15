package com.bartek.NetworkingPlatform.mapper;

import com.bartek.NetworkingPlatform.dto.response.connection.ConnectionResponse;
import com.bartek.NetworkingPlatform.entity.Connection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConnectionMapper {

    private final UserMapper userMapper;

    public ConnectionResponse mapToConnectionResponse(Connection connection) {
        return ConnectionResponse.builder()
                .id(connection.getId())
                .sender(userMapper.mapToUserSummaryResponse(connection.getSender()))
                .receiver(userMapper.mapToUserSummaryResponse(connection.getReceiver()))
                .status(connection.getStatus())
                .createdAt(connection.getCreatedAt())
                .updatedAt(connection.getUpdatedAt())
                .build();
    }
}
