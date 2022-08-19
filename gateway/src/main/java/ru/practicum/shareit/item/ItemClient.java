package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getBookings(long userId, BookingState state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }


    public ResponseEntity<Object> addItem(ItemDto itemDto, int userId) {
        return post("", userId, itemDto);
    }

    public ResponseEntity<Object> update(int itemId, ItemDto itemDto, int userId) {
        return patch("/" + itemId, userId, itemDto);
    }

    public ResponseEntity<Object> getById(int userId, int itemId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> getAllUserItems(int userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> searchAvailable(String text) {
        return get("/search?text=" + text);
    }

    public ResponseEntity<Object> addComment(int itemId, CommentDto dto, int authorId) {
        return post("/" + itemId + "/comment", authorId, dto);
    }
}
