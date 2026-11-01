
package com.campusskillswap.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.campusskillswap.domain.ExchangeStatus;
import com.campusskillswap.repository.ExchangeRequestRepository;
import com.campusskillswap.repository.ExchangeReviewRepository;
import com.campusskillswap.repository.LearnerRepository;
import com.campusskillswap.repository.MentorRepository;
import com.campusskillswap.repository.StudySpotRepository;

@Service
public class DashboardService {

    private final LearnerRepository learnerRepository;
    private final MentorRepository mentorRepository;
    private final StudySpotRepository studySpotRepository;
    private final ExchangeRequestRepository exchangeRequestRepository;
    private final ExchangeReviewRepository exchangeReviewRepository;

    public DashboardService(LearnerRepository learnerRepository,
                            MentorRepository mentorRepository,
                            StudySpotRepository studySpotRepository,
                            ExchangeRequestRepository exchangeRequestRepository,
                            ExchangeReviewRepository exchangeReviewRepository) {
        this.learnerRepository = learnerRepository;
        this.mentorRepository = mentorRepository;
        this.studySpotRepository = studySpotRepository;
        this.exchangeRequestRepository = exchangeRequestRepository;
        this.exchangeReviewRepository = exchangeReviewRepository;
    }

    public Map<String, Long> getDashboardSummary() {

        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("learners", learnerRepository.count());
        summary.put("mentors", mentorRepository.count());
        summary.put("freeStudySpots", studySpotRepository.countFreeStudySpots());
        summary.put("exchangesToday", exchangeRequestRepository.countBySessionDate(LocalDate.now()));
        summary.put("requested", exchangeRequestRepository.countByStatus(ExchangeStatus.REQUESTED));
        summary.put("reviews", exchangeReviewRepository.count());

        return summary;
    }
}
