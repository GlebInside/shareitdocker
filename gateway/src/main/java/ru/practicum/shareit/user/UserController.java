package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@AllArgsConstructor
@Validated
public class UserController {
    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("getting all users");
        return  userClient.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") @Positive int id) {
        log.info("getting user by id = {}", id);
        return userClient.getById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody @Valid UserDto user) {
        log.info("Creating, user={}", user);
        validate(user);
        return userClient.add(user);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UserDto user, @Positive @PathVariable int id) {
        log.info("Updating, user={}, userId={}", user, id);
        return userClient.update(user, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable @Positive int id) {
        log.info("delete userId={}", id);
        return userClient.delete(id);
    }

    public void validate(UserDto user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
           throw new ValidationException(HttpStatus.BAD_REQUEST, "email cannot be empty");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "email should contain @");
        }
    }

}

