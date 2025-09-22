package org.example.expert.domain.todo.condition;

import java.time.LocalDate;

public record TodoSearchCondition(
        LocalDate startDate,
        LocalDate endDate,
        String keyword,
        String managerNickname
) {}
