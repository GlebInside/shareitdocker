package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/items")
@Slf4j
@AllArgsConstructor
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    private ResponseEntity<Object> addItem(@Valid @RequestBody ItemDto itemDto, @Positive @RequestHeader("X-Sharer-User-Id") int userId) {
        return itemClient.addItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    private ResponseEntity<Object> updateItem(@PathVariable @Positive int itemId, @RequestBody @Valid ItemDto itemDto, @Positive @RequestHeader("X-Sharer-User-Id") int userId) {
        return itemClient.update(itemId, itemDto, userId);
    }

    @GetMapping
    private ResponseEntity<Object> getAllUserItems(@RequestHeader("X-Sharer-User-Id") @Positive int userId) {
        return itemClient.getAllUserItems(userId);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> getItem(@PathVariable("id") @Positive int itemId, @RequestHeader("X-Sharer-User-Id") @Positive int userId) {
        return itemClient.getById(userId, itemId);
    }

    @GetMapping("/search")
    private ResponseEntity<Object> searchItem(@RequestParam String text) {
        return itemClient.searchAvailable(text);
    }

    @PostMapping("/{itemId}/comment")
    private ResponseEntity<Object> addComment(@PathVariable int itemId, @RequestBody CommentDto dto, @RequestHeader("X-Sharer-User-Id") int authorId) {
        return itemClient.addComment(itemId, dto, authorId);
    }

}
