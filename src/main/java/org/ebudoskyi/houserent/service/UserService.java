package org.ebudoskyi.houserent.service;

import jakarta.validation.Valid;
import org.ebudoskyi.houserent.dto.UserLoginDTO;
import org.ebudoskyi.houserent.dto.UserRegisterDTO;
import org.ebudoskyi.houserent.mapper.UserMapper;
import org.ebudoskyi.houserent.model.Message;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.model.UserPrincipal;
import org.ebudoskyi.houserent.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<Property>  getPropertiesByUserId(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        return user.getOwnedProperties().stream().toList();
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
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


    public void registerNewUser(@Valid UserRegisterDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
