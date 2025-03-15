package com.bartek.NetworkingPlatform.service;

import com.bartek.NetworkingPlatform.dto.request.connection.ConnectionRequest;
import com.bartek.NetworkingPlatform.dto.response.connection.ConnectionResponse;
import com.bartek.NetworkingPlatform.enums.ConnectionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ConnectionService {

    ConnectionResponse sendConnectionRequest(ConnectionRequest request);
    ConnectionResponse acceptConnectionRequest(Long connectionId);
    ConnectionResponse rejectConnectionRequest(Long connectionId);
    void removeConnection(Long connectionId);
    Page<ConnectionResponse> getUserConnections(Pageable pageable);
    Page<ConnectionResponse> getPendingSentRequests(Pageable pageable);
    Page<ConnectionResponse> getPendingReceivedRequests(Pageable pageable);
    ConnectionStatus getConnectionStatus(Long user1Id, Long user2Id);
    List<Long> getConnectedUserIds(Long userId);
    boolean areUsersConnected(Long user1Id, Long user2Id);
}
