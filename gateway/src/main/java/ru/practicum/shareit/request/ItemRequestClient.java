package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@Service
public class ItemRequestClient extends BaseClient {

    private static final String API_PREFIX = "/requests";

    @Autowired
    public ItemRequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addNew(int requesterId, ItemRequestDto dto) {
        return post("", requesterId, dto);
    }

    public ResponseEntity<Object> getForRequester(int requesterId) {
        return get("", requesterId);
    }

    public ResponseEntity<Object> getWithPagination(int requesterId, int from, Integer size) {
        var path = "/all?from=" + from;
        if (size != null){
            path += "&size=" + size;
        }
        return get(path, requesterId);
    }

    public ResponseEntity<Object> getById(int requestId, int requesterId) {
        return get("/" + requestId, requesterId);
    }
}
