package ru.practicum.shareit.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    private Collection<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    private UserDto getUser(@PathVariable("id") int id) {
        if (!userService.existsById(id)) {
            throw new ValidationException(HttpStatus.NOT_FOUND, "");
        }
        return UserMapper.toUserDto(userService.getById(id));
    }

    @PostMapping
    private UserDto addUser(@RequestBody UserDto user) {
        log.info("Creating, user={}", user);
//        validate(user);
        var s = userService.addNew(UserMapper.toUser(user));
        return UserMapper.toUserDto(s);
    }

    @PatchMapping("/{id}")
    private UserDto updateUser(@RequestBody UserDto user, @PathVariable int id) {
        var oldUser = userService.getById(id);
        var newUser = UserMapper.toUser(user);
        newUser.setId(id);
        newUser.fillEmpty(oldUser);
        userService.save(newUser);
        return UserMapper.toUserDto(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    private void deleteUser(@PathVariable int id) {
        userService.deleteById(id);
    }
}

