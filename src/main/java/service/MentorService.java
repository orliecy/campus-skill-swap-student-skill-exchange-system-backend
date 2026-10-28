
package com.campusskillswap.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.campusskillswap.domain.Mentor;
import com.campusskillswap.domain.Skill;
import com.campusskillswap.domain.StudySpot;
import com.campusskillswap.repository.MentorRepository;
import com.campusskillswap.repository.SkillRepository;
import com.campusskillswap.repository.StudySpotRepository;

@Service
public class MentorService {

    private final MentorRepository mentorRepository;
    private final SkillRepository skillRepository;
    private final StudySpotRepository studySpotRepository;

    public MentorService(MentorRepository mentorRepository,
                         SkillRepository skillRepository,
                         StudySpotRepository studySpotRepository) {
        this.mentorRepository = mentorRepository;
        this.skillRepository = skillRepository;
        this.studySpotRepository = studySpotRepository;
    }

    public Mentor saveMentor(Mentor mentor) {
        if (mentor.getStudySpot() != null) {
            StudySpot studySpot = studySpotRepository.findById(
                    mentor.getStudySpot().getId()).orElseThrow();
            mentor.setStudySpot(studySpot);
        }

        if (mentor.getSkills() != null) {
            List<UUID> skillIds = mentor.getSkills().stream()
                    .map(Skill::getId)
                    .toList();

            List<Skill> skills = skillRepository.findAllById(skillIds);
            mentor.setSkills(skills);
        }

        return mentorRepository.save(mentor);
    }

    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    public Optional<Mentor> getMentorById(UUID id) {
        return mentorRepository.findById(id);
    }

    public Optional<Mentor> updateMentor(UUID id, Mentor mentor) {
        Optional<Mentor> existingMentor = mentorRepository.findById(id);

        if (existingMentor.isPresent()) {
            Mentor updatedMentor = existingMentor.get();

            updatedMentor.setFirstName(mentor.getFirstName());
            updatedMentor.setLastName(mentor.getLastName());
            updatedMentor.setBio(mentor.getBio());

            if (mentor.getStudySpot() != null) {
                StudySpot studySpot = studySpotRepository.findById(
                        mentor.getStudySpot().getId()).orElseThrow();
                updatedMentor.setStudySpot(studySpot);
            }

            if (mentor.getSkills() != null) {
                List<UUID> skillIds = mentor.getSkills().stream()
                        .map(Skill::getId)
                        .toList();

                List<Skill> skills = skillRepository.findAllById(skillIds);
                updatedMentor.setSkills(skills);
            }

            Mentor savedMentor = mentorRepository.save(updatedMentor);
            return Optional.of(savedMentor);
        }

        return Optional.empty();
    }

    public boolean deleteMentor(UUID id) {
        if (mentorRepository.existsById(id)) {
            mentorRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
