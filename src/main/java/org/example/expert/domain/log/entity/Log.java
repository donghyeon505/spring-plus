package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_manager_id")
    private User targetManager;

    @Column(nullable = false)
    private boolean success;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private Log(User requester, User targetManager, boolean success, LocalDateTime createdAt) {
        this.requester = requester;
        this.targetManager = targetManager;
        this.success = success;
        this.createdAt = createdAt;
    }

    public static Log fromLog(User requester, User targetManager, boolean success, LocalDateTime createdAt) {
        return new Log(requester, targetManager, success, createdAt);
    }
}
