package org.example.expert.domain.log.repository;

import org.example.expert.domain.log.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {

    @EntityGraph(attributePaths = {"requester", "targetManager"})
    default Page<Log> findAllLogs(Pageable pageable) {
        return findAll(pageable);
    }
}
