package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.CommentsService;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.stream.Collectors;

@Component
public class ItemMapper {
    private final CommentsService commentsService;
    private final BookingService bookingService;

    public ItemMapper(CommentsService commentsService, BookingService bookingService) {
        this.commentsService = commentsService;
        this.bookingService = bookingService;
    }

    public ItemDto toDto(Item item) {
        ItemBookingDto lastBooking = bookingService.getLastBooking(item.getId());
        ItemBookingDto nextBooking = bookingService.getNextBooking(item.getId());
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner().getId(),
                lastBooking,
                nextBooking,
                commentsService.getForItem(item.getId()).stream().map(CommentMapper::toDto).collect(Collectors.toList()),
                item.getRequest() == null ? null : item.getRequest().getId()
        );
    }

    public Item toItem(ItemDto itemDto, User owner, ItemRequest itemRequest) {
        return new Item(-1, itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable(),
                owner, itemRequest);
    }

    public static Item copyNotEmpty(Item item, ItemDto itemDto) {
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        return item;
    }
}
