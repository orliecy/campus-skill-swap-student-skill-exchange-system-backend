
package com.campusskillswap.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campusskillswap.domain.ExchangeReview;

public interface ExchangeReviewRepository extends JpaRepository<ExchangeReview, UUID> {

    @Query("SELECT r FROM ExchangeReview r WHERE r.exchangeRequest.learner.regNumber = :regNumber ORDER BY r.reviewDate DESC")
    List<ExchangeReview> findReviewsByLearnerRegNumber(@Param("regNumber") String regNumber);

    boolean existsByExchangeRequestId(UUID exchangeRequestId);

}
