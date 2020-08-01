package com.dietator.diet.controller;

import com.dietator.diet.domain.User;
import com.dietator.diet.projections.UserInfo;
import com.dietator.diet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public UserInfo getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public List<UserInfo> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/users")
    public User editUser(@RequestBody User user) {
        return userService.editUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteById(id);
    }

}
