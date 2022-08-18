package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private final UserClient userClient;

//    @GetMapping
//    private Collection<UserDto> getAllUsers() {
//    }
//
//    @GetMapping("/{id}")
//    private UserDto getUser(@PathVariable("id") int id) {
//    }

    @PostMapping
    private ResponseEntity<Object> addUser(@RequestBody @Valid UserDto user) {
        log.info("Creating, user={}", user);
        return userClient.add(user);
    }

//    private void validate(UserDto user) {
//
//    }
//
    @PatchMapping("/{id}")
    private ResponseEntity<Object> updateUser(@RequestBody @Valid UserDto user, @Positive @PathVariable int id) {
        log.info("Updating, user={}, userId={}", user, id);
        return userClient.update(user, id);
    }
//
//    @DeleteMapping("/{id}")
//    private void deleteUser(@PathVariable int id) {
//    }
}

