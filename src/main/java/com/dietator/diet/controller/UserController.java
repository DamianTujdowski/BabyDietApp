package com.dietator.diet.controller;

import com.dietator.diet.domain.User;
import com.dietator.diet.dto.UserBasicInfoDto;
import com.dietator.diet.dto.UserWithBelongingChildrenDto;
import com.dietator.diet.service.UserService;
import com.dietator.diet.utils.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public UserWithBelongingChildrenDto getUserById(@PathVariable int id) {
        return UserDtoMapper.mapToSingleUserDto(userService.getUserById(id));
    }

    @GetMapping("/users")
    public List<UserBasicInfoDto> getAllUsers() {
        return UserDtoMapper.mapToUserBasicInfoDtos(userService.getAllUsers());
    }

    @GetMapping("/users/children")
    public List<UserWithBelongingChildrenDto> getAllUsersWithBelongingChildren() {
        return UserDtoMapper.mapToUserWithBelongingChildrenDtos(userService.getAllUsers());
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
