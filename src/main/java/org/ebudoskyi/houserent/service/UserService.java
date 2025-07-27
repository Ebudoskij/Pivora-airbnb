package org.ebudoskyi.houserent.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.ebudoskyi.houserent.Exceptions.CustomExceptions.UserNotFoundException;
import org.ebudoskyi.houserent.dto.UserEditProfileDTO;
import org.ebudoskyi.houserent.dto.UserLoginDTO;
import org.ebudoskyi.houserent.dto.UserRegisterDTO;
import org.ebudoskyi.houserent.dto.UserProfileDTO;
import org.ebudoskyi.houserent.mapper.UserMapper;
import org.ebudoskyi.houserent.model.Message;
import org.ebudoskyi.houserent.model.Property;
import org.ebudoskyi.houserent.model.User;
import org.ebudoskyi.houserent.model.UserPrincipal;
import org.ebudoskyi.houserent.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       AuthenticationManager authenticationManager,
                       JWTService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
    
    public Optional<String> login(UserLoginDTO userDTO) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            String token = jwtService.generateToken(userPrincipal.getUsername());

            return Optional.of(token);
        } catch (AuthenticationException ex) {
            return Optional.empty();
        }
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

    
    public Optional<UserLoginDTO> registerNewUser(UserRegisterDTO userRegisterDTO) {
        if (existsByEmail(userRegisterDTO.getEmail())) {
            return Optional.empty();
        }
        User user = userMapper.toEntity(userRegisterDTO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        UserLoginDTO  userLoginDTO = new  UserLoginDTO();
        userLoginDTO.setEmail(userRegisterDTO.getEmail());
        userLoginDTO.setPassword(userRegisterDTO.getPassword());
        return Optional.of(userLoginDTO);
    }

    @Transactional(readOnly = true)
    public UserProfileDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return userMapper.toDTO(user);
    }

    public UserEditProfileDTO getUserEditInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return new UserEditProfileDTO(user.getName(), user.getEmail(), user.getPhoneNumber());
    }

    public void updateUser(Long userId, String name, String email, String password, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        user.setName(name);
        user.setEmail(email);
        if (!password.isBlank()) {
            user.setPassword(bCryptPasswordEncoder.encode(password));
        }
        if (!phoneNumber.isBlank()) {
            user.setPhoneNumber(phoneNumber);
        }
        userRepository.save(user);
    }
}
