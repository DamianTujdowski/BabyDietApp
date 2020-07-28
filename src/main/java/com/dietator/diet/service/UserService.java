package com.dietator.diet.service;

import com.dietator.diet.domain.User;
import com.dietator.diet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    public Set<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User editUser(User user) {
        User editedUser = userRepository.findById(user.getId()).orElseThrow();
        editedUser.setNickname(user.getNickname());
        editedUser.setEmail(user.getEmail());
        editedUser.setChildren(user.getChildren());
        return editedUser;
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
