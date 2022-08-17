package ru.practicum.shareit.requests.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.service.ItemRequestService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ItemRequestDto addRequest(@RequestBody ItemRequestDto dto, @RequestHeader("X-Sharer-User-Id") int requesterId) {
        dto.setRequesterId(requesterId);
        return itemRequestService.addNew(dto);
    }

    @GetMapping
    public Collection<ItemRequestDto> getAllForRequester(@RequestHeader("X-Sharer-User-Id") int requesterId) {
        return itemRequestService.getForRequester(requesterId);
    }

    @GetMapping(path = "/{requestId}")
    public ItemRequestDto getAllForRequester(@RequestHeader("X-Sharer-User-Id") int requesterId, @PathVariable int requestId) {
        itemRequestService.assertThatUserExists(requesterId);
        return itemRequestService.getDtoById(requestId);
    }

    public Collection<ItemRequestDto> getAllForNotRequester(@RequestHeader("X-Sharer-User-Id") int requesterId) {
        return itemRequestService.getAllForNotRequester(requesterId);
    }

    @GetMapping(path = "all")
    Collection<ItemRequestDto> getWithPagination(@RequestHeader("X-Sharer-User-Id") int requesterId, @RequestParam(defaultValue = "0") int from, @RequestParam(required = false) Integer size) {
        if (size == null) {
            return getAllForNotRequester(requesterId);
        }
        return itemRequestService.getAllForNotRequester(requesterId, from, size).stream().collect(Collectors.toUnmodifiableList());
    }
}

