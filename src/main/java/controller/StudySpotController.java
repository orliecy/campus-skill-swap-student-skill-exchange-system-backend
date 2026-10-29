
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

import com.campusskillswap.domain.StudySpot;
import com.campusskillswap.service.StudySpotService;

@RestController
@RequestMapping("/api/study-spots")
public class StudySpotController {

    private final StudySpotService studySpotService;

    public StudySpotController(StudySpotService studySpotService) {
        this.studySpotService = studySpotService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveStudySpot(@RequestBody StudySpot studySpot) {
        studySpotService.saveStudySpot(studySpot);
        return new ResponseEntity<>("Study spot saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudySpot>> getAllStudySpots() {
        return ResponseEntity.ok(studySpotService.getAllStudySpots());
    }

    @GetMapping("/free")
    public ResponseEntity<List<StudySpot>> getFreeStudySpots() {
        return ResponseEntity.ok(studySpotService.getFreeStudySpots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudySpotById(@PathVariable UUID id) {
        Optional<StudySpot> studySpot = studySpotService.getStudySpotById(id);

        if (studySpot.isPresent()) {
            return ResponseEntity.ok(studySpot.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The study spot with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudySpot(@PathVariable UUID id, @RequestBody StudySpot studySpot) {
        Optional<StudySpot> updatedStudySpot = studySpotService.updateStudySpot(id, studySpot);

        if (updatedStudySpot.isPresent()) {
            return ResponseEntity.ok(updatedStudySpot.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The study spot with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudySpot(@PathVariable UUID id) {
        boolean deleted = studySpotService.deleteStudySpot(id);

        if (deleted) {
            return ResponseEntity.ok("Study spot deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The study spot with that id does not exist");
    }
}
