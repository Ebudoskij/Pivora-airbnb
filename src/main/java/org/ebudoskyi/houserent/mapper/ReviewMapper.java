package org.ebudoskyi.houserent.mapper;

import org.ebudoskyi.houserent.dto.ReviewDTO;
import org.ebudoskyi.houserent.model.Review;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {

    public ReviewDTO toDTO(Review review) {
        if (review == null) {
            return null;
        }
        ReviewDTO reviewDTO = new ReviewDTO(
                review.getId(),
                review.getAuthor().getId(),
                review.getProperty().getId(),
                review.getRating(),
                review.getContent(),
                review.getDate()
        );
        return reviewDTO;
    }

    public Review toEntity(ReviewDTO reviewDTO) {
        if (reviewDTO == null) {
            return null;
        }
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setRating(reviewDTO.getRating());
        review.setContent(reviewDTO.getContent());
        review.setDate(reviewDTO.getDate());
        return review;
    }

    public List<ReviewDTO> toDTOList(List<Review> reviews) {
        if (reviews == null) {
            return null;
        }
        return reviews.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Review>  toEntityList(List<ReviewDTO> reviewDTOs) {
        if (reviewDTOs == null) {
            return null;
        }
        return reviewDTOs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
