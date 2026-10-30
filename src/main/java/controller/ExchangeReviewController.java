
package com.campusskillswap.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campusskillswap.domain.ExchangeReview;
import com.campusskillswap.service.ExchangeReviewService;

@RestController
@RequestMapping("/api/exchange-reviews")
public class ExchangeReviewController {

    private final ExchangeReviewService exchangeReviewService;

    public ExchangeReviewController(ExchangeReviewService exchangeReviewService) {
        this.exchangeReviewService = exchangeReviewService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveExchangeReview(
            @RequestParam UUID exchangeRequestId,
            @RequestBody ExchangeReview exchangeReview) {

        String result = exchangeReviewService.saveExchangeReview(
                exchangeRequestId, exchangeReview);

        if (result.equals("The exchange request with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        if (result.equals("Exchange reviews are only for DONE exchange requests")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/learner/{regNumber}")
    public ResponseEntity<List<ExchangeReview>> getReviewsByLearnerRegNumber(
            @PathVariable String regNumber) {

        List<ExchangeReview> reviews =
                exchangeReviewService.getReviewsByLearnerRegNumber(regNumber);

        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExchangeReview(@PathVariable UUID id) {

        String result = exchangeReviewService.deleteExchangeReview(id);

        if (result.equals("The exchange review with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }
}
