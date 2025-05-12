package org.ebudoskyi.houserent.service;

import org.ebudoskyi.houserent.dto.UserLoginDTO;
import org.ebudoskyi.houserent.dto.UserRegisterDTO;
import org.ebudoskyi.houserent.mapper.UserMapper;
import org.ebudoskyi.houserent.model.Message;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository,  UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<Property>  getPropertiesByUserId(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        return user.getOwnedProperties().stream().toList();
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }


    public User registerUser(UserRegisterDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = userMapper.toEntity(userDTO);
        return userRepository.save(user); // без шифрування
    }

    public User login(UserLoginDTO userDTO) {
        return userRepository.findByEmail(userDTO.getEmail())
                .filter(user -> user.getPassword().equals(userDTO.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<Message> getSentMessages(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        return user.getSentMessages().stream().toList();
    }

    public List<Message> getReceivedMessages(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        return user.getReceivedMessages().stream().toList();
    }
}
