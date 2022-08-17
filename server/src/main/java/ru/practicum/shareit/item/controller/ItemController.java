package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.CommentMapper;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.CommentsService;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
@AllArgsConstructor
public class ItemController {
    private final UserService userService;
    private final ItemService itemService;
    private final CommentsService commentsService;
    private final ItemMapper itemMapper;

    @GetMapping
    private Collection<ItemDto> getAllUserItems(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemService.getAllUserItems(userId);
    }

    @GetMapping("/{id}")
    private ItemDto getItem(@PathVariable("id") int id, @RequestHeader("X-Sharer-User-Id") int userId) {
        var item = itemService.getById(id);
        var dto = itemMapper.toDto(item);
        if (userId != item.getOwner().getId()) {
            dto.setLastBooking(null);
            dto.setNextBooking(null);
        }
        return dto;
    }

    @GetMapping("/search")
    private List<ItemDto> searchItem(@RequestParam String text) {
        return itemService.searchAvailable(text);
    }

    @PostMapping
    private ItemDto addItem(@RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") int userId) {
        return itemService.addItem(itemDto, userService.getById(userId));
    }

    @PatchMapping("/{itemId}")
    private ItemDto updateItem(@PathVariable int itemId, @RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") int userId) {
        return itemMapper.toDto(itemService.update(itemId, itemDto, userService.getById(userId)));
    }

    @PostMapping("/{itemId}/comment")
    private CommentDto addComment(@PathVariable int itemId, @RequestBody CommentDto dto, @RequestHeader("X-Sharer-User-Id") int authorId) {
        return CommentMapper.toDto(commentsService.addNew(itemService.getById(itemId), dto, authorId));
    }

}
