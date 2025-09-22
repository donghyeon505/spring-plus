package org.example.expert.domain.log.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.dto.LogResponse;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.repository.LogRepository;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LogService {

    private final LogRepository logRepository;
    private final UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(Long requesterId, Long targetManagerId, boolean success, LocalDateTime requestTime) {

        User requester = userRepository.findById(requesterId).orElse(null);
        User targetManager = userRepository.findById(targetManagerId).orElse(null);

        Log log = Log.fromLog(requester, targetManager, success, requestTime);

        logRepository.save(log);
    }

    @Transactional(readOnly = true)
    public Page<LogResponse> getLogs(Pageable pageable) {

        Page<Log> logs = logRepository.findAllLogs(pageable);

        return logs.map(LogResponse::from);
    }
}
