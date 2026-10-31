
package com.campusskillswap.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.campusskillswap.domain.ExchangeRequest;
import com.campusskillswap.domain.ExchangeStatus;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, UUID> {

    List<ExchangeRequest> findByStatusOrderBySessionDateAsc(ExchangeStatus status);

    List<ExchangeRequest> findBySessionDateBetweenOrderBySessionDateAsc(
            LocalDate start, LocalDate end);

    boolean existsByMentorIdAndSessionDateAndStatusNot(
            UUID mentorId, LocalDate sessionDate, ExchangeStatus status);

    @Query("SELECT e.status, COUNT(e) FROM ExchangeRequest e GROUP BY e.status")
    List<Object[]> countExchangeRequestsByStatus();

    @Modifying
    @Transactional
    @Query("UPDATE ExchangeRequest e SET e.status = :rejected WHERE e.mentor.id = :mentorId AND e.sessionDate = :date AND e.status <> :done")
    int cancelExchangeRequestsByMentorAndDate(
            @Param("mentorId") UUID mentorId,
            @Param("date") LocalDate date,
            @Param("rejected") ExchangeStatus rejected,
            @Param("done") ExchangeStatus done);

}
