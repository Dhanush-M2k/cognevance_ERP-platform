package com.erp.platform.service;

import com.erp.platform.model.Feedback;
import com.erp.platform.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> findRecent() {
        return feedbackRepository.findTop20ByOrderBySubmittedAtDesc();
    }

    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }

    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

    public double averageRating() {
        List<Feedback> all = feedbackRepository.findAll();
        if (all.isEmpty()) {
            return 0.0;
        }
        return all.stream().mapToInt(Feedback::getRating).average().orElse(0.0);
    }

    public long count() {
        return feedbackRepository.count();
    }
}
