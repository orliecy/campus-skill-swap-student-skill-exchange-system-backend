
package com.campusskillswap.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.campusskillswap.domain.ExchangeRequest;
import com.campusskillswap.domain.ExchangeReview;
import com.campusskillswap.domain.ExchangeStatus;
import com.campusskillswap.repository.ExchangeRequestRepository;
import com.campusskillswap.repository.ExchangeReviewRepository;

@Service
public class ExchangeReviewService {

    private final ExchangeReviewRepository exchangeReviewRepository;
    private final ExchangeRequestRepository exchangeRequestRepository;

    public ExchangeReviewService(ExchangeReviewRepository exchangeReviewRepository,
                                 ExchangeRequestRepository exchangeRequestRepository) {
        this.exchangeReviewRepository = exchangeReviewRepository;
        this.exchangeRequestRepository = exchangeRequestRepository;
    }

    public String saveExchangeReview(UUID exchangeRequestId, ExchangeReview exchangeReview) {

        Optional<ExchangeRequest> existingRequest =
                exchangeRequestRepository.findById(exchangeRequestId);

        if (existingRequest.isEmpty()) {
            return "The exchange request with that id does not exist";
        }

        ExchangeRequest exchangeRequest = existingRequest.get();

        if (exchangeRequest.getStatus() != ExchangeStatus.DONE) {
            return "Exchange reviews are only for DONE exchange requests";
        }

        exchangeReview.setExchangeRequest(exchangeRequest);
        exchangeReview.setReviewDate(LocalDate.now());

        exchangeReviewRepository.save(exchangeReview);

        return "Exchange review saved successfully";
    }

    public List<ExchangeReview> getReviewsByLearnerRegNumber(String regNumber) {
        return exchangeReviewRepository.findReviewsByLearnerRegNumber(regNumber);
    }

    public String deleteExchangeReview(UUID id) {

        if (!exchangeReviewRepository.existsById(id)) {
            return "The exchange review with that id does not exist";
        }

        exchangeReviewRepository.deleteById(id);

        return "Exchange review deleted successfully";
    }
}
