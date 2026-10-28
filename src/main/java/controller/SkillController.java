
package com.campusskillswap.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.campusskillswap.domain.Skill;
import com.campusskillswap.service.SkillService;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveSkill(@RequestBody Skill skill) {
        skillService.saveSkill(skill);
        return new ResponseEntity<>("Skill saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable UUID id) {
        Optional<Skill> skill = skillService.getSkillById(id);

        if (skill.isPresent()) {
            return ResponseEntity.ok(skill.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The skill with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable UUID id, @RequestBody Skill skill) {
        Optional<Skill> updatedSkill = skillService.updateSkill(id, skill);

        if (updatedSkill.isPresent()) {
            return ResponseEntity.ok(updatedSkill.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The skill with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable UUID id) {
        boolean deleted = skillService.deleteSkill(id);

        if (deleted) {
            return ResponseEntity.ok("Skill deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The skill with that id does not exist");
    }
}
