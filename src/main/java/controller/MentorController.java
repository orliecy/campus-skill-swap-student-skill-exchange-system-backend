
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campusskillswap.domain.Mentor;
import com.campusskillswap.service.MentorService;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMentor(@RequestBody Mentor mentor) {
        mentorService.saveMentor(mentor);
        return new ResponseEntity<>("Mentor saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Mentor>> getAllMentors() {
        return ResponseEntity.ok(mentorService.getAllMentors());
    }

    @GetMapping("/by-skill")
    public ResponseEntity<List<Mentor>> getMentorsBySkillName(@RequestParam String name) {
        return ResponseEntity.ok(mentorService.getMentorsBySkillName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMentorById(@PathVariable UUID id) {
        Optional<Mentor> mentor = mentorService.getMentorById(id);

        if (mentor.isPresent()) {
            return ResponseEntity.ok(mentor.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The mentor with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMentor(@PathVariable UUID id, @RequestBody Mentor mentor) {
        Optional<Mentor> updatedMentor = mentorService.updateMentor(id, mentor);

        if (updatedMentor.isPresent()) {
            return ResponseEntity.ok(updatedMentor.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The mentor with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMentor(@PathVariable UUID id) {
        boolean deleted = mentorService.deleteMentor(id);

        if (deleted) {
            return ResponseEntity.ok("Mentor deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The mentor with that id does not exist");
    }
}
