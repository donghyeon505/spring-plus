package org.example.expert.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.log.service.LogService;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ManagerAdditionLoggingAspect {

    private final LogService logService;

    @AfterReturning(pointcut = "execution(* org.example.expert.domain.manager.service.ManagerService.saveManager(..))")
    public void logAfterManagerSave(JoinPoint joinPoint) {
        try {
            Long requesterId = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

            Long targetManagerId = extractTargetManagerId(joinPoint);

            boolean success = true;

            logService.saveLog(requesterId, targetManagerId, success, LocalDateTime.now());
        } catch (Exception e) {
            log.error("로그 기록 중 오류 발생", e);
        }
    }

    @AfterThrowing(pointcut = "execution(* org.example.expert.domain.manager.service.ManagerService.saveManager(..))")
    public void logAfterManagerSaveException(JoinPoint joinPoint) {
        try {
            Long requesterId = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

            Long targetManagerId = extractTargetManagerId(joinPoint);

            logService.saveLog(requesterId, targetManagerId, false, LocalDateTime.now());
        } catch (Exception e) {
            log.error("로그 기록 중 오류 발생", e);
        }
    }

    private Long extractTargetManagerId(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof ManagerSaveRequest req) {
                return req.getManagerUserId();
            }
        }
        return null;
    }
}
