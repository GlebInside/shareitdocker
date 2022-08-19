package ru.practicum.shareit.item.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ItemRequestService itemRequestService;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper, ItemRequestService itemRequestService) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.itemRequestService = itemRequestService;
        itemRequestService.setItemService(this);
    }

    @Override
    public Collection<ItemDto> getAllUserItems(int userId) {
        return itemRepository
                .findAll()
                .stream()
                .filter(i -> i.getOwner().getId() == userId)
                .sorted(Comparator.comparingInt(Item::getId))
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Item getById(int id) {
        if (!itemRepository.existsById(id)) {
            throw new ValidationException(HttpStatus.NOT_FOUND, "");
        }
        return itemRepository.getReferenceById(id);
    }

    @Override
    public List<ItemDto> searchAvailable(String text) {
        if (text.isEmpty()) {
            return List.of();
        }
        return itemRepository.findAll().stream()
                .filter(Item::getAvailable)
                .filter(x -> contains(x.getName().toUpperCase(), text.toUpperCase()) || contains(x.getDescription().toUpperCase(), text.toUpperCase()))
                .map(x -> itemMapper.toDto(x)).collect(Collectors.toList());
    }

    private boolean contains(String str, String find) {
        return str != null && str.contains(find);
    }

    @Override
    public ItemDto addItem(ItemDto itemDto, User owner) {
        var item = itemMapper.toItem(itemDto, owner,itemDto.getRequestId() == null ? null : itemRequestService.getById(itemDto.getRequestId()));
        validate(item);
        item = itemRepository.saveAndFlush(item);
        return itemMapper.toDto(item);
    }

    @Override
    public Item update(int itemId, ItemDto itemDto, User owner) {
        var old = itemRepository.getReferenceById(itemId);
        if (old.getOwner().getId() != owner.getId()) {
            throw new ValidationException(HttpStatus.NOT_FOUND, "");
        }
        var newModel = ItemMapper.copyNotEmpty(old, itemDto);
        itemRepository.saveAndFlush(newModel);
        return getById(itemId);
    }

    @Override
    public Collection<ItemDto> findItemsByRequest(int requestId) {
        return itemRepository.findItemsByRequest(requestId).stream().map(itemMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    private void validate(Item item) {
        if (item.getAvailable() == null) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Item isn't available");
        }
        if (item.getName() == null || item.getName().equals("")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Item has no name");
        }
        if (item.getDescription() == null || item.getDescription().equals("")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Item has no description");
        }
    }
}

