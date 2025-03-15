package com.bartek.NetworkingPlatform.service.Impl;

import com.bartek.NetworkingPlatform.dto.request.connection.ConnectionRequest;
import com.bartek.NetworkingPlatform.dto.response.connection.ConnectionResponse;
import com.bartek.NetworkingPlatform.entity.Connection;
import com.bartek.NetworkingPlatform.entity.User;
import com.bartek.NetworkingPlatform.enums.ConnectionStatus;
import com.bartek.NetworkingPlatform.exception.BadRequestException;
import com.bartek.NetworkingPlatform.exception.NotFoundException;
import com.bartek.NetworkingPlatform.exception.UnauthorizedException;
import com.bartek.NetworkingPlatform.mapper.ConnectionMapper;
import com.bartek.NetworkingPlatform.repository.ConnectionRepository;
import com.bartek.NetworkingPlatform.repository.UserRepository;
import com.bartek.NetworkingPlatform.service.ConnectionService;
import com.bartek.NetworkingPlatform.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;

    private final ConnectionMapper connectionMapper;

    private final UserUtil userUtil;

    @Override
    @Transactional
    public ConnectionResponse sendConnectionRequest(ConnectionRequest request) {
        User sender = userUtil.getCurrentUser();
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new NotFoundException("User with id " + request.getReceiverId() + " not found"));

        if (sender.getId().equals(receiver.getId())) {
            throw new BadRequestException("Invalid connection request");
        }

        Optional<Connection> existingConnection = connectionRepository.findConnectionBetweenUsers(sender.getId(), receiver.getId());

        if (existingConnection.isPresent()) {
            Connection connection = existingConnection.get();

           if (connection.getStatus() == ConnectionStatus.ACCEPTED) {
               throw new BadRequestException("Connection already accepted");
           } else if (connection.getStatus() == ConnectionStatus.PENDING) {
                throw new BadRequestException("Connection already pending");
           } else if (connection.getStatus() == ConnectionStatus.REJECTED) {
               connection.setSender(sender);
               connection.setReceiver(receiver);
               connection.setStatus(ConnectionStatus.PENDING);
               Connection updatedConnection = connectionRepository.save(connection);
               return connectionMapper.mapToConnectionResponse(updatedConnection);
           }
        }

        Connection connection = Connection.builder()
                .sender(sender)
                .receiver(receiver)
                .status(ConnectionStatus.PENDING)
                .build();

        Connection savedConnection = connectionRepository.save(connection);
        return connectionMapper.mapToConnectionResponse(savedConnection);
    }

    @Override
    @Transactional
    public ConnectionResponse acceptConnectionRequest(Long connectionId) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new NotFoundException("Connection with id " + connectionId + " not found"));

        User receiver = userUtil.getCurrentUser();

        if (!connection.getReceiver().getId().equals(receiver.getId())) {
            throw new UnauthorizedException("You do not have permission to accept this connection");
        }

        if (connection.getStatus() != ConnectionStatus.PENDING) {
            throw new BadRequestException("Connection is not pending");
        }

        connection.setStatus(ConnectionStatus.ACCEPTED);
        Connection updatedConnection = connectionRepository.save(connection);

        return connectionMapper.mapToConnectionResponse(updatedConnection);
    }

    @Override
    @Transactional
    public ConnectionResponse rejectConnectionRequest(Long connectionId) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new NotFoundException("Connection with id " + connectionId + " not found"));

        User receiver = userUtil.getCurrentUser();

        if (!connection.getReceiver().getId().equals(receiver.getId())) {
            throw new UnauthorizedException("You do not have permission to accept this connection");
        }

        if (connection.getStatus() != ConnectionStatus.PENDING) {
            throw new BadRequestException("Connection is not pending");
        }

        connection.setStatus(ConnectionStatus.REJECTED);
        Connection updatedConnection = connectionRepository.save(connection);

        return connectionMapper.mapToConnectionResponse(updatedConnection);
    }

    @Override
    @Transactional
    public void removeConnection(Long connectionId) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new NotFoundException("Connection with id " + connectionId + " not found"));

        User user = userUtil.getCurrentUser();

        if (!connection.getSender().getId().equals(user.getId()) && !connection.getReceiver().getId().equals(user.getId())) {
            throw new UnauthorizedException("You do not have permission to remove this connection");
        }

        connectionRepository.delete(connection);
    }

    @Override
    public Page<ConnectionResponse> getUserConnections(Pageable pageable) {
        Long userId = userUtil.getCurrentUser().getId();

        if(!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id " + userId + " not found");
        }

        Page<Connection> connections = connectionRepository.findAcceptedConnectionsByUserId(userId, pageable);

        return connections.map(connectionMapper::mapToConnectionResponse);
    }

    @Override
    public Page<ConnectionResponse> getPendingSentRequests(Pageable pageable) {
        Long userId = userUtil.getCurrentUser().getId();

        if(!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id " + userId + " not found");
        }

        Page<Connection> connections = connectionRepository.findBySenderIdAndStatus(userId, ConnectionStatus.PENDING, pageable);

        return connections.map(connectionMapper::mapToConnectionResponse);
    }

    @Override
    public Page<ConnectionResponse> getPendingReceivedRequests(Pageable pageable) {
        Long userId = userUtil.getCurrentUser().getId();

        if(!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id " + userId + " not found");
        }

        Page<Connection> connections = connectionRepository.findByReceiverIdAndStatus(userId, ConnectionStatus.PENDING, pageable);

        return connections.map(connectionMapper::mapToConnectionResponse);
    }

    @Override
    public ConnectionStatus getConnectionStatus(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new NotFoundException("User with id " + user1Id + " not found"));

        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new NotFoundException("User with id " + user1Id + " not found"));

        Optional<Connection> connection = connectionRepository.findConnectionBetweenUsers(user1Id, user2Id);

        return connection.map(Connection::getStatus).orElse(null);
    }

    @Override
    public List<Long> getConnectedUserIds(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id " + userId + " not found");
        }

        return connectionRepository.findConnectedUserIds(userId);
    }

    @Override
    public boolean areUsersConnected(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new NotFoundException("User with id " + user1Id + " not found"));

        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new NotFoundException("User with id " + user1Id + " not found"));

        return connectionRepository.areUsersConnected(user1Id, user2Id);
    }
}
