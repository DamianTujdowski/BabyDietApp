package com.dietator.diet.service;

import com.dietator.diet.domain.User;
import com.dietator.diet.projections.UserInfo;
import com.dietator.diet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserInfo getUserById(long id) {
        return userRepository.findUserById(id).orElseThrow();
    }

    public List<UserInfo> getAllUsers() {
        return userRepository.findAllBy();
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

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
