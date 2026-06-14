package com.example.demo.service;

import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserResponseDto save(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        userRepository.save(user);
        return new UserResponseDto(
                user.getId(),
                user.getEmail()
        );
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password,
                        u.getPassword()))
                .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));

        return jwtUtil.generateToken(user.getEmail());
    }

    public UserResponseDto getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return new UserResponseDto(
                user.getId(),
                user.getEmail()
        );
    }
}
