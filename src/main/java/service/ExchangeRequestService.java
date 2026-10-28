
package com.campusskillswap.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.campusskillswap.domain.ExchangeRequest;
import com.campusskillswap.domain.Learner;
import com.campusskillswap.domain.Mentor;
import com.campusskillswap.repository.ExchangeRequestRepository;
import com.campusskillswap.repository.LearnerRepository;
import com.campusskillswap.repository.MentorRepository;

@Service
public class ExchangeRequestService {

    private final ExchangeRequestRepository exchangeRequestRepository;
    private final LearnerRepository learnerRepository;
    private final MentorRepository mentorRepository;

    public ExchangeRequestService(ExchangeRequestRepository exchangeRequestRepository,
                                  LearnerRepository learnerRepository,
                                  MentorRepository mentorRepository) {
        this.exchangeRequestRepository = exchangeRequestRepository;
        this.learnerRepository = learnerRepository;
        this.mentorRepository = mentorRepository;
    }

    public ExchangeRequest saveExchangeRequest(ExchangeRequest exchangeRequest) {
        if (exchangeRequest.getLearner() != null) {
            Learner learner = learnerRepository.findById(
                    exchangeRequest.getLearner().getId()).orElseThrow();
            exchangeRequest.setLearner(learner);
        }

        if (exchangeRequest.getMentor() != null) {
            Mentor mentor = mentorRepository.findById(
                    exchangeRequest.getMentor().getId()).orElseThrow();
            exchangeRequest.setMentor(mentor);
        }

        return exchangeRequestRepository.save(exchangeRequest);
    }

    public List<ExchangeRequest> getAllExchangeRequests() {
        return exchangeRequestRepository.findAll();
    }

    public Optional<ExchangeRequest> getExchangeRequestById(UUID id) {
        return exchangeRequestRepository.findById(id);
    }

    public Optional<ExchangeRequest> updateExchangeRequest(UUID id, ExchangeRequest exchangeRequest) {
        Optional<ExchangeRequest> existingRequest = exchangeRequestRepository.findById(id);

        if (existingRequest.isPresent()) {
            ExchangeRequest updatedRequest = existingRequest.get();

            updatedRequest.setSessionDate(exchangeRequest.getSessionDate());
            updatedRequest.setStatus(exchangeRequest.getStatus());
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

    public boolean deleteExchangeRequest(UUID id) {
        if (exchangeRequestRepository.existsById(id)) {
            exchangeRequestRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
