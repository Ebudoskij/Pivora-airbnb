package org.ebudoskyi.houserent.service;

import org.ebudoskyi.houserent.Exceptions.CustomExceptions.IllegalProfilePictureException;
import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.model.UserProfileImages;
import org.ebudoskyi.houserent.repository.UserProfileImagesRepository;
import org.ebudoskyi.houserent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class UserProfileImagesService {
    private final UserProfileImagesRepository userProfileImagesRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserProfileImagesService(UserProfileImagesRepository userProfileImagesRepository, UserRepository userRepository) {
        this.userProfileImagesRepository = userProfileImagesRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public void uploadImage(Long userId, MultipartFile file){
        if (file == null || file.isEmpty()) {
            throw new IllegalProfilePictureException("Uploaded file is empty");
        }

        UserProfileImages userProfileImages = userProfileImagesRepository.findByUserId(userId)
                .orElseGet(() -> {
            UserProfileImages newImage = new UserProfileImages();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            newImage.setUser(user);
            return newImage;
        });
        try {
            userProfileImages.setData(file.getBytes());
        } catch (IOException e) {
            throw new IllegalProfilePictureException("An error occurred while uploading profile picture");
        }
        userProfileImagesRepository.save(userProfileImages);
    }

    public String getImage(Long userId) {
        return userProfileImagesRepository.findByUserId(userId)
                .map(image -> "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(image.getData()))
                .orElse(null);
    }
}
