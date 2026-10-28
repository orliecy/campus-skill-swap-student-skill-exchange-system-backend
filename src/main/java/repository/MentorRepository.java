
package com.campusskillswap.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusskillswap.domain.Mentor;

public interface MentorRepository extends JpaRepository<Mentor, UUID> {
}
