
package com.campusskillswap.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.campusskillswap.domain.ExchangeRequest;
import com.campusskillswap.domain.ExchangeStatus;
import com.campusskillswap.domain.Learner;
import com.campusskillswap.domain.Mentor;
import com.campusskillswap.repository.ExchangeRequestRepository;
import com.campusskillswap.repository.ExchangeReviewRepository;
import com.campusskillswap.repository.LearnerRepository;
import com.campusskillswap.repository.MentorRepository;

@Service
public class ExchangeRequestService {

    private final ExchangeRequestRepository exchangeRequestRepository;
    private final LearnerRepository learnerRepository;
    private final MentorRepository mentorRepository;
    private final ExchangeReviewRepository exchangeReviewRepository;

    public ExchangeRequestService(ExchangeRequestRepository exchangeRequestRepository,
                                  LearnerRepository learnerRepository,
                                  MentorRepository mentorRepository,
                                  ExchangeReviewRepository exchangeReviewRepository) {
        this.exchangeRequestRepository = exchangeRequestRepository;
        this.learnerRepository = learnerRepository;
        this.mentorRepository = mentorRepository;
        this.exchangeReviewRepository = exchangeReviewRepository;
    }

    public ExchangeRequest saveExchangeRequest(ExchangeRequest exchangeRequest) {

        if (exchangeRequest.getSessionDate() == null ||
                exchangeRequest.getSessionDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Exchange request date cannot be in the past");
        }

        if (exchangeRequest.getLearner() != null) {
            Learner learner = learnerRepository.findById(
                    exchangeRequest.getLearner().getId()).orElseThrow();
            exchangeRequest.setLearner(learner);
        }

        if (exchangeRequest.getMentor() != null) {
            Mentor mentor = mentorRepository.findById(
                    exchangeRequest.getMentor().getId()).orElseThrow();
            exchangeRequest.setMentor(mentor);

            boolean alreadyBooked =
                    exchangeRequestRepository.existsByMentorIdAndSessionDateAndStatusNot(
                            mentor.getId(),
                            exchangeRequest.getSessionDate(),
                            ExchangeStatus.REJECTED);

            if (alreadyBooked) {
                throw new IllegalStateException("Mentor already has an exchange on that date");
            }
        }

        exchangeRequest.setStatus(ExchangeStatus.REQUESTED);

        return exchangeRequestRepository.save(exchangeRequest);
    }

    public List<ExchangeRequest> getAllExchangeRequests() {
        return exchangeRequestRepository.findAll();
    }

    public List<ExchangeRequest> getExchangeRequestsByStatus(ExchangeStatus status) {
        return exchangeRequestRepository.findByStatusOrderBySessionDateAsc(status);
    }

    public List<ExchangeRequest> getExchangeRequestsBetweenDates(LocalDate start, LocalDate end) {
        return exchangeRequestRepository.findBySessionDateBetweenOrderBySessionDateAsc(start, end);
    }

    public Optional<ExchangeRequest> getExchangeRequestById(UUID id) {
        return exchangeRequestRepository.findById(id);
    }

    public String updateExchangeRequestStatus(UUID id, ExchangeStatus newStatus) {

        Optional<ExchangeRequest> existingRequest = exchangeRequestRepository.findById(id);

        if (existingRequest.isEmpty()) {
            return "The exchange request with that id does not exist";
        }

        ExchangeRequest exchangeRequest = existingRequest.get();
        ExchangeStatus currentStatus = exchangeRequest.getStatus();

        boolean allowed = false;

        if (currentStatus == ExchangeStatus.REQUESTED) {
            allowed = newStatus == ExchangeStatus.ACCEPTED ||
                      newStatus == ExchangeStatus.REJECTED;
        } else if (currentStatus == ExchangeStatus.ACCEPTED) {
            allowed = newStatus == ExchangeStatus.DONE ||
                      newStatus == ExchangeStatus.REJECTED;
        }

        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }

        exchangeRequest.setStatus(newStatus);
        exchangeRequestRepository.save(exchangeRequest);

        return "Exchange request status updated successfully";
    }

    public Optional<ExchangeRequest> updateExchangeRequest(UUID id, ExchangeRequest exchangeRequest) {
        Optional<ExchangeRequest> existingRequest = exchangeRequestRepository.findById(id);

        if (existingRequest.isPresent()) {
            ExchangeRequest updatedRequest = existingRequest.get();

            updatedRequest.setSessionDate(exchangeRequest.getSessionDate());
            updatedRequest.setSkillWanted(exchangeRequest.getSkillWanted());
            updatedRequest.setSkillOffered(exchangeRequest.getSkillOffered());

            if (exchangeRequest.getLearner() != null) {
                Learner learner = learnerRepository.findById(
                        exchangeRequest.getLearner().getId()).orElseThrow();
                updatedRequest.setLearner(learner);
            }

            if (exchangeRequest.getMentor() != null) {
                Mentor mentor = mentorRepository.findById(
                        exchangeRequest.getMentor().getId()).orElseThrow();
                updatedRequest.setMentor(mentor);
            }

            ExchangeRequest savedRequest = exchangeRequestRepository.save(updatedRequest);

            return Optional.of(savedRequest);
        }

        return Optional.empty();
    }

    public String deleteExchangeRequest(UUID id) {

        if (!exchangeRequestRepository.existsById(id)) {
            return "The exchange request with that id does not exist";
        }

        if (exchangeReviewRepository.existsByExchangeRequestId(id)) {
            return "That exchange request has exchange reviews, delete them first";
        }

        exchangeRequestRepository.deleteById(id);

        return "Exchange request deleted successfully";
    }
}
