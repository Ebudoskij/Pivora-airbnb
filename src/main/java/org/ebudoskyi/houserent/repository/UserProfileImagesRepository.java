package org.ebudoskyi.houserent.repository;

import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.model.UserProfileImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileImagesRepository extends JpaRepository<UserProfileImages, Long> {

    Optional<UserProfileImages> findByUserId(Long userId);

    Optional<UserProfileImages> getByUserId(Long userId);
}
