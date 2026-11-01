
package com.campusskillswap.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campusskillswap.domain.StudySpot;

public interface StudySpotRepository extends JpaRepository<StudySpot, UUID> {

    @Query("SELECT s FROM StudySpot s WHERE NOT EXISTS (SELECT m FROM Mentor m WHERE m.studySpot = s) ORDER BY s.spotNumber ASC")
    List<StudySpot> findFreeStudySpots();

    Optional<StudySpot> findBySpotNumber(int spotNumber);

    @Query("SELECT COUNT(s) FROM StudySpot s WHERE NOT EXISTS (SELECT m FROM Mentor m WHERE m.studySpot = s)")
    long countFreeStudySpots();

}
