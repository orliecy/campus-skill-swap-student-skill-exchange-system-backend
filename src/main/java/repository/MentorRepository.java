
package com.campusskillswap.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campusskillswap.domain.Mentor;

public interface MentorRepository extends JpaRepository<Mentor, UUID> {

    @Query("SELECT DISTINCT m FROM Mentor m JOIN m.skills s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Mentor> findMentorsBySkillName(@Param("name") String name);

}
