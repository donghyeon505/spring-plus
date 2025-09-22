package org.example.expert.domain.log.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.dto.LogResponse;
import org.example.expert.domain.log.service.LogService;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/admin")
public class LogController {

    private final LogService logService;

    @Secured(UserRole.Authority.ADMIN)
    @GetMapping("/logs")
    public Page<LogResponse> getLogs(
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "createdAt"));

        return logService.getLogs(pageable);
    }
}
