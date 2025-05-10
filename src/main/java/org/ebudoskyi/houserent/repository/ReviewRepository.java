package org.ebudoskyi.houserent.repository;

import org.ebudoskyi.houserent.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPropertyId(Long propertyId);

    List<Review> findByAuthorId(Long authorId);

    List<Review> findByDate(LocalDate date);

    List<Review> findByDateBetween(LocalDate start, LocalDate end);

    List<Review> findByRatingGreaterThanEqual(int minRating);
}

