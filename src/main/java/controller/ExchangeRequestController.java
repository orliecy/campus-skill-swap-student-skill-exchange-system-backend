
package com.campusskillswap.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campusskillswap.domain.ExchangeRequest;
import com.campusskillswap.domain.ExchangeStatus;
import com.campusskillswap.service.ExchangeRequestService;

@RestController
@RequestMapping("/api/exchange-requests")
public class ExchangeRequestController {

    private final ExchangeRequestService exchangeRequestService;

    public ExchangeRequestController(ExchangeRequestService exchangeRequestService) {
        this.exchangeRequestService = exchangeRequestService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveExchangeRequest(@RequestBody ExchangeRequest exchangeRequest) {
        exchangeRequestService.saveExchangeRequest(exchangeRequest);

        return new ResponseEntity<>("Exchange request saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExchangeRequest>> getAllExchangeRequests() {
        return ResponseEntity.ok(exchangeRequestService.getAllExchangeRequests());
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<ExchangeRequest>> getExchangeRequestsByStatus(
            @RequestParam ExchangeStatus status) {
        return ResponseEntity.ok(exchangeRequestService.getExchangeRequestsByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<ExchangeRequest>> getExchangeRequestsBetweenDates(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        return ResponseEntity.ok(exchangeRequestService.getExchangeRequestsBetweenDates(start, end));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExchangeRequestById(@PathVariable UUID id) {
        Optional<ExchangeRequest> exchangeRequest = exchangeRequestService.getExchangeRequestById(id);

        if (exchangeRequest.isPresent()) {
            return ResponseEntity.ok(exchangeRequest.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The exchange request with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateExchangeRequest(@PathVariable UUID id,
                                                    @RequestBody ExchangeRequest exchangeRequest) {
        Optional<ExchangeRequest> updatedRequest =
                exchangeRequestService.updateExchangeRequest(id, exchangeRequest);

        if (updatedRequest.isPresent()) {
            return ResponseEntity.ok(updatedRequest.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The exchange request with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExchangeRequest(@PathVariable UUID id) {
        boolean deleted = exchangeRequestService.deleteExchangeRequest(id);

        if (deleted) {
            return ResponseEntity.ok("Exchange request deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The exchange request with that id does not exist");
        }
    }
}
