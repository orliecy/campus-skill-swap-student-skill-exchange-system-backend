
package com.campusskillswap.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campusskillswap.domain.Skill;

public interface SkillRepository extends JpaRepository<Skill, UUID> {

    @Query("SELECT s FROM Skill s WHERE s.mentors IS EMPTY")
    List<Skill> findUnusedSkills();

}
