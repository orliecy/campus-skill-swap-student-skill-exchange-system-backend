
package com.campusskillswap.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusskillswap.domain.StudySpot;

public interface StudySpotRepository extends JpaRepository<StudySpot, UUID> {

}
