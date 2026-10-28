
package com.campusskillswap.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusskillswap.domain.Learner;

public interface LearnerRepository extends JpaRepository<Learner, UUID> {

}
