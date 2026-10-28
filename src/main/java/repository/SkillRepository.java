
package com.campusskillswap.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusskillswap.domain.Skill;

public interface SkillRepository extends JpaRepository<Skill, UUID> {

}
