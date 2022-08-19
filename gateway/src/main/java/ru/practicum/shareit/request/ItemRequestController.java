package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;


    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestBody ItemRequestDto dto, @RequestHeader("X-Sharer-User-Id") int requesterId) {
        return itemRequestClient.addNew(requesterId, dto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllForRequester(@RequestHeader("X-Sharer-User-Id") int requesterId) {
        return itemRequestClient.getForRequester(requesterId);
    }

    @GetMapping(path = "/{requestId}")
    public ResponseEntity<Object> getAllForRequester(@RequestHeader("X-Sharer-User-Id") int requesterId, @PathVariable int requestId) {
        return itemRequestClient.getById(requestId, requesterId);
    }


    @GetMapping(path = "all")
    ResponseEntity<Object> getWithPagination(@RequestHeader("X-Sharer-User-Id") int requesterId, @RequestParam(defaultValue = "0") int from, @RequestParam(required = false) Integer size) {
        return itemRequestClient.getWithPagination(requesterId, from, size);
    }
}

