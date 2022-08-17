package ru.practicum.shareit.requests.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.requests.RequestMapper;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.storage.ItemRequestRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserService userService;

    private ItemService itemService;

    public ItemRequestService(ItemRequestRepository itemRequestRepository, UserService userService) {
        this.itemRequestRepository = itemRequestRepository;
        this.userService = userService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public ItemRequestDto addNew(ItemRequestDto dto) {
        if (dto.getDescription() == null || dto.getDescription().trim().equals("")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "empty description");
        }
        var added = itemRequestRepository.saveAndFlush(RequestMapper.toRequest(dto, userService.getById(dto.getRequesterId())));
        return RequestMapper.toDto(
                added,
                itemService.findItemsByRequest(added.getId()));
    }

    public Collection<ItemRequestDto> getForRequester(int requesterId) {
        assertThatUserExists(requesterId);
        return itemRequestRepository
                .findByRequesterId(requesterId)
                .stream()
                .map(entity -> RequestMapper.toDto(entity, itemService.findItemsByRequest(entity.getId())))
                .collect(Collectors.toUnmodifiableList());
    }

    public Collection<ItemRequestDto> getAllForNotRequester(int requesterId) {
        assertThatUserExists(requesterId);
        return itemRequestRepository.findForNotRequester(requesterId).stream().map(entity -> RequestMapper.toDto(entity, itemService.findItemsByRequest(entity.getId()))).collect(Collectors.toUnmodifiableList());
    }

    public void assertThatUserExists(int userId) {
        userService.getById(userId);
    }

    public Page<ItemRequestDto> getAllForNotRequester(int requesterId, int from, int size) {
        if (from < 0) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "negative from");
        }
        if (size < 0) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "negative size");
        }
        if (size == 0) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "size is zero");
        }

        assertThatUserExists(requesterId);
        return itemRequestRepository
                .findForNotRequester(requesterId, PageRequest.of(from, size, Sort.by("created").descending()))
                .map(entity -> RequestMapper.toDto(entity, itemService.findItemsByRequest(entity.getId())));
    }

    public ItemRequest getById(int id) {
        return (itemRequestRepository.findById(id).orElseThrow());
    }

    public ItemRequestDto getDtoById(int id) {
        return RequestMapper.toDto(itemRequestRepository.findById(id).orElseThrow(), itemService.findItemsByRequest(id));
    }


}

