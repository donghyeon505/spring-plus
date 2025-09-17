package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryQuery {

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT t FROM Todo t " +
            "WHERE t.modifiedAt >= :startDate AND t.modifiedAt <= :endDate")
    Page<Todo> findAllBetweenDate(Pageable pageable,
                                   @Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT t FROM Todo t " +
            "WHERE t.modifiedAt >= :startDate AND t.modifiedAt <= :endDate AND t.weather LIKE :weather")
    Page<Todo> findAllByWeatherWithDate(Pageable pageable,
                                        @Param("weather") String weather,
                                        @Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);
}
