package ru.practicum.shareit.requests;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public class RequestMapper {

    public static ItemRequest toRequest(ItemRequestDto dto, User requester) {
        return new ItemRequest(dto.getId(), dto.getDescription(), requester, dto.getCreated());
    }

    public static ItemRequestDto toDto(ItemRequest entity, Collection<ItemDto> items) {
        return new ItemRequestDto(entity.getId(), entity.getDescription(), entity.getRequester().getId(), entity.getCreated(), items);
    }
}

