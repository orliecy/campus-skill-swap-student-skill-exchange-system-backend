
package com.campusskillswap.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.campusskillswap.domain.StudySpot;
import com.campusskillswap.repository.StudySpotRepository;

@Service
public class StudySpotService {

    private final StudySpotRepository studySpotRepository;

    public StudySpotService(StudySpotRepository studySpotRepository) {
        this.studySpotRepository = studySpotRepository;
    }

    public StudySpot saveStudySpot(StudySpot studySpot) {
        return studySpotRepository.save(studySpot);
    }

    public List<StudySpot> getAllStudySpots() {
        return studySpotRepository.findAll();
    }

    public Optional<StudySpot> getStudySpotById(UUID id) {
        return studySpotRepository.findById(id);
    }

    public Optional<StudySpot> updateStudySpot(UUID id, StudySpot studySpot) {
        Optional<StudySpot> existingStudySpot = studySpotRepository.findById(id);

        if (existingStudySpot.isPresent()) {
            StudySpot updatedStudySpot = existingStudySpot.get();
            updatedStudySpot.setSpotNumber(studySpot.getSpotNumber());
            updatedStudySpot.setName(studySpot.getName());
            studySpotRepository.save(updatedStudySpot);
            return Optional.of(updatedStudySpot);
        }

        return Optional.empty();
    }

    public boolean deleteStudySpot(UUID id) {
        if (studySpotRepository.existsById(id)) {
            studySpotRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
