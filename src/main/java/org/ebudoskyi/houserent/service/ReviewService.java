package org.ebudoskyi.houserent.service;

import org.ebudoskyi.houserent.dto.ReviewDTO;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.Review;
import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.repository.PropertyRepository;
import org.ebudoskyi.houserent.repository.ReviewRepository;
import org.ebudoskyi.houserent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         PropertyRepository propertyRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    public void saveReview(ReviewDTO dto) {
        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Користувача не знайдено"));

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new IllegalArgumentException("Помешкання не знайдено"));

        Review review = Review.builder()
                .author(author)
                .property(property)
                .rating(dto.getRating())
                .content(dto.getContent())
                .date(dto.getDate())
                .build();

        reviewRepository.save(review);
    }

    public List<Review> getReviewsByPropertyId(Long propertyId) {
        return reviewRepository.findByPropertyId(propertyId);
    }

    public void deleteById(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
