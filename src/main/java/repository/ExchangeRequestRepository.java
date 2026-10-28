
package com.campusskillswap.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusskillswap.domain.ExchangeRequest;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, UUID> {

}
