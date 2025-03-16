package com.bartek.NetworkingPlatform.controller;

import com.bartek.NetworkingPlatform.dto.request.connection.ConnectionRequest;
import com.bartek.NetworkingPlatform.dto.response.connection.ConnectionResponse;
import com.bartek.NetworkingPlatform.enums.ConnectionStatus;
import com.bartek.NetworkingPlatform.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connections")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping
    public ResponseEntity<ConnectionResponse> sendConnectionRequest(
            @RequestBody ConnectionRequest request
    ) {
        ConnectionResponse connectionResponse = connectionService.sendConnectionRequest(request);
        return new ResponseEntity<>(connectionResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{connectionId}/accept")
    public ResponseEntity<ConnectionResponse> acceptConnectionRequest(
            @PathVariable Long connectionId
    ) {
        ConnectionResponse response = connectionService.acceptConnectionRequest(connectionId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{connectionId}/reject")
    public ResponseEntity<ConnectionResponse> rejectConnectionRequest(
            @PathVariable Long connectionId
    ) {
        ConnectionResponse response = connectionService.rejectConnectionRequest(connectionId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{connectionId}")
    public ResponseEntity<ConnectionResponse> removeConnection(
            @PathVariable Long connectionId
    ) {
        connectionService.removeConnection(connectionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Page<ConnectionResponse>> getMyConnections(
        @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<ConnectionResponse> connections = connectionService.getUserConnections(pageable);
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/me/sent")
    public ResponseEntity<Page<ConnectionResponse>> getMySentRequests(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<ConnectionResponse> connections = connectionService.getPendingSentRequests(pageable);
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/me/received")
    public ResponseEntity<Page<ConnectionResponse>> getMyReceivedRequests(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<ConnectionResponse> connections = connectionService.getPendingReceivedRequests(pageable);
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<ConnectionResponse>> getUserConnections(
            @PathVariable Long userId,
            Pageable pageable) {
        Page<ConnectionResponse> connections = connectionService.getUserConnections(pageable);
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/status")
    public ResponseEntity<ConnectionStatus> getConnectionStatus(
            @RequestParam Long user2Id,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        ConnectionStatus status = connectionService.getConnectionStatus(user2Id);
        return ResponseEntity.ok(status);
    }
}















































