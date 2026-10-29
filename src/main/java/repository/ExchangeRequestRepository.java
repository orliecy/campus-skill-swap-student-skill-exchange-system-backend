
package com.campusskillswap.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusskillswap.domain.ExchangeRequest;
import com.campusskillswap.domain.ExchangeStatus;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, UUID> {

    List<ExchangeRequest> findByStatusOrderBySessionDateAsc(ExchangeStatus status);

    List<ExchangeRequest> findBySessionDateBetweenOrderBySessionDateAsc(
            LocalDate start, LocalDate end);

}
