package com.dietator.diet.service;

import com.dietator.diet.domain.Child;
import com.dietator.diet.domain.User;
import com.dietator.diet.error.EntityNotFoundException;
import com.dietator.diet.projections.UserInfo;
import com.dietator.diet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserInfo getUserById(long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new EntityNotFoundException(User.class, id));
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
        editedUser.getChildren().addAll(Objects.requireNonNull(filterNewChildren(user.getChildren(), editedUser.getChildren())));
        return editedUser;
    }

    private Set<Child> filterNewChildren(Set<Child> childrenFromUser, Set<Child> childrenFromDb) {
        childrenFromUser.removeAll(childrenFromDb);
        return childrenFromUser;
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
