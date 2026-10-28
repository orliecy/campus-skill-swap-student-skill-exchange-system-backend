
package com.campusskillswap.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.campusskillswap.domain.Skill;
import com.campusskillswap.repository.SkillRepository;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Optional<Skill> getSkillById(UUID id) {
        return skillRepository.findById(id);
    }

    public Optional<Skill> updateSkill(UUID id, Skill skill) {
        Optional<Skill> existingSkill = skillRepository.findById(id);

        if (existingSkill.isPresent()) {
            Skill updatedSkill = existingSkill.get();
            updatedSkill.setName(skill.getName());
            skillRepository.save(updatedSkill);
            return Optional.of(updatedSkill);
        }

        return Optional.empty();
    }

    public boolean deleteSkill(UUID id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
