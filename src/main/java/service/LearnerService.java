
package com.campusskillswap.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.campusskillswap.domain.Learner;
import com.campusskillswap.repository.LearnerRepository;

@Service
public class LearnerService {

    private final LearnerRepository learnerRepository;

    public LearnerService(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    public Learner saveLearner(Learner learner) {
        return learnerRepository.save(learner);
    }

    public List<Learner> getAllLearners() {
        return learnerRepository.findAll();
    }

    public Optional<Learner> getLearnerById(UUID id) {
        return learnerRepository.findById(id);
    }

    public Optional<Learner> updateLearner(UUID id, Learner learner) {
        Optional<Learner> existingLearner = learnerRepository.findById(id);

        if (existingLearner.isPresent()) {
            Learner updatedLearner = existingLearner.get();

            updatedLearner.setFirstName(learner.getFirstName());
            updatedLearner.setLastName(learner.getLastName());
            updatedLearner.setRegNumber(learner.getRegNumber());
            updatedLearner.setEmail(learner.getEmail());
            updatedLearner.setFaculty(learner.getFaculty());

            learnerRepository.save(updatedLearner);

            return Optional.of(updatedLearner);
        }

        return Optional.empty();
    }

    public boolean deleteLearner(UUID id) {
        if (learnerRepository.existsById(id)) {
            learnerRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
