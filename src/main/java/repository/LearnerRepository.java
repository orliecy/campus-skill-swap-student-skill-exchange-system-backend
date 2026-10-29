
package com.campusskillswap.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campusskillswap.domain.Learner;

public interface LearnerRepository extends JpaRepository<Learner, UUID> {

    List<Learner> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT e.learner FROM ExchangeRequest e WHERE e.mentor.id = :mentorId")
    List<Learner> findLearnersByMentorId(@Param("mentorId") UUID mentorId);

}
