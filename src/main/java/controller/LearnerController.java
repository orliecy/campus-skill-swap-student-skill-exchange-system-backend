
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

import com.campusskillswap.domain.Learner;
import com.campusskillswap.service.LearnerService;

@RestController
@RequestMapping("/api/learners")
public class LearnerController {

    private final LearnerService learnerService;

    public LearnerController(LearnerService learnerService) {
        this.learnerService = learnerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveLearner(@RequestBody Learner learner) {
        learnerService.saveLearner(learner);
        return new ResponseEntity<>("Learner saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Learner>> getAllLearners() {
        return ResponseEntity.ok(learnerService.getAllLearners());
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Learner>> getLearnersByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(learnerService.getLearnersByLastName(lastName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLearnerById(@PathVariable UUID id) {
        Optional<Learner> learner = learnerService.getLearnerById(id);

        if (learner.isPresent()) {
            return ResponseEntity.ok(learner.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The learner with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLearner(@PathVariable UUID id,
                                           @RequestBody Learner learner) {
        Optional<Learner> updatedLearner = learnerService.updateLearner(id, learner);

        if (updatedLearner.isPresent()) {
            return ResponseEntity.ok(updatedLearner.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The learner with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLearner(@PathVariable UUID id) {
        boolean deleted = learnerService.deleteLearner(id);

        if (deleted) {
            return ResponseEntity.ok("Learner deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The learner with that id does not exist");
        }
    }
}
